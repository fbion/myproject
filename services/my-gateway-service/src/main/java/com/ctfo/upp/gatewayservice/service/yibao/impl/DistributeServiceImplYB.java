package com.ctfo.upp.gatewayservice.service.yibao.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.yibao.callback.CallBackBeanYB;
import com.ctfo.upp.gatewayservice.bean.mongoDB.log.CallBackToBusinessLog;
import com.ctfo.upp.gatewayservice.bean.yibao.callback.accountaccess.CheckAccountCallBackResponseYB;
import com.ctfo.upp.gatewayservice.bean.yibao.callback.accountaccess.RechargeCallBackResponseYB;
import com.ctfo.upp.gatewayservice.bean.yibao.callback.accountaccess.UnlockAccountCallBackResponseYB;
import com.ctfo.upp.gatewayservice.bean.yibao.callback.accountaccess.WithdrawCallBackResponseYB;
import com.ctfo.upp.gatewayservice.service.base.intf.LogService;
import com.ctfo.upp.gatewayservice.service.yibao.intf.DistributeServiceYB;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.gatewayservice.util.yibao.Constants;
import com.ctfo.upp.gatewayservice.util.yibao.HttpRequest;
import com.ctfo.upp.gatewayservice.util.yibao.PareseObjectParameter;
import com.ctfo.upp.root.utils.UUIDUtil;

@Service("distributeServiceYB")
public class DistributeServiceImplYB extends BaseServiceImpl implements DistributeServiceYB {
	
	@Autowired(required=false)
	private LogService logService;
	
	@Override
	public List<String> distributeRequest(Object object, int type) throws Exception {
		// TODO Auto-generated method stub
		String param = "";
		String url = "";
		switch (type) {
		case 1:
			param = PareseObjectParameter.getParameterString(object);
			url = ConfigUtil.getValue("YB_ACCOUNT_ACCESS_URL");
			break;
		case 2:
			param = PareseObjectParameter.convertObjectToXML(object);
			url = ConfigUtil.getValue("YB_ENTRUST_SETTLE_URL");
			break;
		default:
			throw new Exception(">>>>>>>>>> type不符合要求  <<<<<<<<<<");
		}
		List<String> result = new ArrayList<String>();
		String requestTime = "";
		String responseTime = "";
		try{
			requestTime = String.valueOf(new Date().getTime());
			result = HttpRequest.sendGet(url, param);
			responseTime = String.valueOf(new Date().getTime());
		}catch(Exception e){
			result.add("发送异常："+e.getLocalizedMessage());
			throw e;
		}finally{
			logService.saveYBLog(this.getCommandFromObject(object), "1", param, result.toString(), requestTime, responseTime);
		}
		return result;
	}
	
	private String getCommandFromObject(Object object) throws Exception {
		String command = "";
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		for(Field f : fields){
			if(f.getName().equals("command")){
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), object.getClass());
	            Method getMethod = pd.getReadMethod();
	            command = (String) getMethod.invoke(object, null);
			}
		}
		return command;
	}

	@Override
	public void distributeResponse(Object object, int type) throws Exception {
		
		if(!validateHMAC(object, type)){
			throw new Exception(">>>>>>>>>> 回调请求的验证码错误  <<<<<<<<<<");
		}
		String callbackBeantype=null;

		if(object instanceof RechargeCallBackResponseYB){
			RechargeCallBackResponseYB r = (RechargeCallBackResponseYB)object;


			log.info("nType的值为："+r.getNType()+",externalId:"+r.getExternalId());
			callbackBeantype="2";
			if(r.getNType().equals("1")){
				return;
			}
		}
		if(object instanceof CheckAccountCallBackResponseYB){
			callbackBeantype="1";
			CheckAccountCallBackResponseYB bean = (CheckAccountCallBackResponseYB)object;
			bean.setId(java.util.UUID.randomUUID().toString());
			logService.saveReponseObject(bean);

		}
		if(object instanceof UnlockAccountCallBackResponseYB){
			callbackBeantype="3";
		}
		if(object instanceof WithdrawCallBackResponseYB){
			callbackBeantype="4";
		}
		
		CallBackBeanYB callBackBeanYB = new CallBackBeanYB();
		
		Method method = object.getClass().getMethod("toBeanResponse", null);
		Object o = method.invoke(object, null);
		final String logId = UUIDUtil.newUUID2();
		
		callBackBeanYB.setCallBackBean(o);
		callBackBeanYB.setType(Constants.callBackToBusinessType.get(o.getClass().getSimpleName()));
		callBackBeanYB.setCallBackUrl(ConfigUtil.getValue("BUSINESS_CALL_BACK_TO_GATEWAY_URL"));
		callBackBeanYB.setTaskId(logId);
		callBackBeanYB.setType(callbackBeantype);
		
		
		final String param = JSONObject.fromObject(callBackBeanYB).toString();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int i;
				CallBackToBusinessLog l = new CallBackToBusinessLog();
				l.setId(logId);
				l.setContent(ConfigUtil.getValue("GATEWAY_CALL_BACK_TO_BUSINESS_URL") + param);
				l.setTime(String.valueOf(new Date()));
				l.setCallBackParam(param);//设置参数内容
				l.setStatus("0");
				logService.saveCallBackToBusinessLog(l);
				for(i = 0 ; i < Integer.valueOf(ConfigUtil.getValue("GATEWAY_CALL_BACK_TO_BUSINESS_SYSTEM_NUM")).intValue() ; i++){
					List result = new ArrayList();
					try {
						result = HttpRequest.sendGet(ConfigUtil.getValue("GATEWAY_CALL_BACK_TO_BUSINESS_URL"), param);
					} catch (Exception e) {
						// TODO: handle exception
						log.error(">>>>>>>>>> 回调给业务系统发生错误  <<<<<<<<<<", e);
					}
					if(result.get(0).toString().toUpperCase().equals("SUCCESS")){
						l.setStatus("2");
						logService.saveCallBackToBusinessLog(l);
						break;
					}else{
						try {
							Thread.sleep(Long.valueOf(ConfigUtil.getValue("GATEWAY_CALL_BACK_TO_BUSINESS_SLEEP_TIME")));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							log.error(">>>>>>>>>> 线程睡眠发生错误  <<<<<<<<<<", e);
						}
					}
				}
				if(i == Integer.valueOf(ConfigUtil.getValue("GATEWAY_CALL_BACK_TO_BUSINESS_SYSTEM_NUM")).intValue()){
					l.setStatus("1");
					logService.saveCallBackToBusinessLog(l);
				}
			}
		}).start();
	}

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
