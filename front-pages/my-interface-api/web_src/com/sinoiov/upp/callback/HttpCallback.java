package com.sinoiov.upp.callback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dto.beans.OrderRecord;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.impl.multithread.TaskPool;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.security.ConvertUtils;
import com.sinoiov.pay.utils.InteractiveRecordUtil;
import com.sinoiov.pay.utils.UPPConfigUtil;

@Service("httpBusinessCallback")
public class HttpCallback implements IBusinessCallBack{

	private static Log logger = LogFactory.getLog(HttpCallback.class);

	@Override
	public void callBack(final OrderInfo order) throws UPPException {
		try{
			OrderRecord record =new OrderRecord();
			String[] ignoreProperties = {};
			BeanUtils.copyProperties(order, record, ignoreProperties);
			InteractiveRecordUtil.saveCallbackOrderRecord(record);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		try {

			logger.info("--->>>order:"+order);
			
			String url = order.getCallbackUrl();
			if(StringUtils.isNotBlank(url) && !"-1".equals(url)){
				TaskPool.named("__HttpCallback_callback_thread_pool__").setPoolSize(2).submit(new Runnable() {
					@Override
					public void run() {
						try{
							int sleepTime = Integer.parseInt(UPPConfigUtil.getValue("CALLBACK_SLIPT_TIME"));
							int callNumber = Integer.parseInt(UPPConfigUtil.getValue("CALLBACK_NUMBER"));
							for(int i=0; i<callNumber;i++){
								String result = "";
								try{
									result = callBackSure(order);	
								}catch(Exception e){
									logger.warn("回调业务系统错误："+order.getCallbackUrl());
								}
								if(StringUtils.isNotBlank(result) && "SUCCESS".equalsIgnoreCase(result.trim())){
									break;
								}else{
									Thread.sleep(sleepTime);
								}	
							}	
						} catch (Exception e) {
							logger.error("发送异常！", e);		
						}
					}
				});
			}else{
				logger.warn("没有回调地址："+url);
			}
			
		} catch (Exception e) {
			logger.error("发送异常！", e);			
		}

	}


	private String callBackSure(OrderInfo order) throws Exception{

		Map<String,String> map = new HashMap<String, String>();
		map.put("workOrderNo", order.getWorkOrderNo());			
		map.put("orderNo", order.getOrderNo());
		map.put("payType", order.getPayChannel());			
		String orderStatus = PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(order.getOrderStatus())?"1":"-1";			
		map.put("result", orderStatus);
		map.put("merchantOrderAmount",AmountUtil.getAmount(order.getOrderAmount()));
		map.put("tradeExternalNo", order.getTradeExternalNo());
		//支付确认时间
		String payConfirmDate = order.getPayConfirmDate() == null?(new Date().getTime())+"":order.getPayConfirmDate()+"";
		map.put("payConfirmDate", payConfirmDate);

		String merchantcode = order.getStoreCode();
		String publickey = UPPConfigUtil.getPublicKey(merchantcode);
		String uppcode = Long.parseLong(merchantcode.substring(0,8)) < Long.parseLong("201503120000000000000".substring(0,8))?
				UPPConfigUtil.getValue("UPP_CODE"):UPPConfigUtil.getValue("UPP_CODE_NEW");	
		String privatekey = UPPConfigUtil.getPrivateKey(uppcode);
		String url = order.getCallbackUrl();
		if(StringUtils.isNotBlank(url)){			
			//加密
			JSONObject jsonMap = JSONObject.fromObject(map);
			//兼容1.0.0
			String json = "";
			if(Long.parseLong(merchantcode.substring(0, 8)) < Long.parseLong("201503120000000000000".substring(0, 8))){
				json = ConvertUtils.encodeReturnJson_1_0_0(jsonMap.toString(), privatekey, publickey, merchantcode);
			}else{
				json = ConvertUtils.encodeReturnJson(jsonMap.toString(), privatekey, publickey);
				json = json.substring(0,json.length()-1) + ",\"merchantcode\":\""+merchantcode+"\"}";
			}
			//String json = ConvertUtils.encodeReturnJson(jsonMap.toString(), privatekey, publickey);
			
			logger.info("回调发送URL："+url);
			logger.info("回调发送参数（加密前）："+jsonMap.toString());
			String result = HttpUtils.sendPost(url, HttpUtils.setParameterValue(json));
			logger.info("收到响应："+result);
			//保存
			InteractiveRecordUtil.saveRecord(url+"?"+jsonMap.toString()+","+json, "{\"result\":\""+result+"\"}", merchantcode, "callBackSure");
						
			return result;

		}else{				
			logger.warn("没有回调地址："+url);
		}
		return null;
	}


}
