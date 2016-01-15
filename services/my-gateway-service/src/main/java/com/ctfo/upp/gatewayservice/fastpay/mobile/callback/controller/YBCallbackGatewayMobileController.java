package com.ctfo.upp.gatewayservice.fastpay.mobile.callback.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.callback.GatewayCallback;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain.YBFastPayCallbackResultDomain;
import com.ctfo.upp.gatewayservice.fastpay.callback.controller.BaseController;
import com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl.AbstractFastPaymentYB;
import com.ctfo.upp.gatewayservice.service.base.intf.LogService;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.impl.multithread.TaskPool;

@Scope("prototype")
@Controller
@RequestMapping(value = "/yeepay/mobile")
public class YBCallbackGatewayMobileController extends BaseController implements GatewayCallback {
	static private Log logger = LogFactory.getLog(YBCallbackGatewayMobileController.class);
	
	@Autowired(required=false)
	private LogService logService;
	
	static public AbstractFastPaymentYB delegated = new AbstractFastPaymentYB() {
	};

	@ResponseBody
	@RequestMapping(value = "/callback", method = { RequestMethod.POST, RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> callback(@RequestParam("data") String data, @RequestParam("encryptkey") String encryptKey) throws UPPException {
		String paramsJSON = "", result = YEEPAY_RETURN_STR, requestTime="", responseTime="";
		try{
			requestTime = String.valueOf(new Date().getTime());
			if (data == null || data.isEmpty())
				return newResponseEntity("Callback fast payment gateway Mobile interface,The data parameter value is empty.", HttpStatus.BAD_REQUEST);
			else if (encryptKey == null || encryptKey.isEmpty())
				return newResponseEntity("Callback fast payment gateway Mobile interface,The encryptkey parameter value is empty.", HttpStatus.BAD_REQUEST);
			
			logger.info("收到易宝快捷支付回调： data=" + data+"&encryptkey="+ encryptKey);
			
			String plainTextResult = delegated.checkYeepayClearResult(String.format("{\"data\":\"%s\",\"encryptkey\":\"%s\"}", data, encryptKey));
			if (plainTextResult == null || plainTextResult.isEmpty())
				return newResponseEntity("解密明文失败", HttpStatus.BAD_REQUEST);
			
			logger.info("收到易宝快捷支付回调(明文):" + plainTextResult);
			paramsJSON = plainTextResult+",{data:"+data+", encryptkey:"+encryptKey+"}";
			
			FastPayDomain mobileDomain = null;
			try {
				mobileDomain = JSONObject.parseObject(plainTextResult, YBFastPayCallbackResultDomain.class);
			}catch (Exception e) {
				logger.error("json转对应异常:"+e.getLocalizedMessage(), e);
			}
			if (mobileDomain == null)
				return newResponseEntity("Callback fast payment gateway Mobile interface,Parse object failed reasons:Returns the content format may change.", HttpStatus.BAD_REQUEST);
			final FastPayDomain _mobileDomain = mobileDomain;
			TaskPool.named(GATEWAY_CALLBACK_HANDLER_POOL_NAME).setPoolSize(Integer.parseInt(ConfigUtil.getValue("GATEWAY_CALLBACK_HANDLE_THREAD_POOL_SIZE"))).submit(new Runnable() {

				@Override
				public void run() {
					try {
						getFastPaymentServiceFacade().fastPaymentCallback(_mobileDomain);
					} catch (UPPException e) {
						logger.error("回调业务系统异常："+e.getLocalizedMessage(), e);
					}
				}
			});
			
			return newResponseEntity(YEEPAY_RETURN_STR);
		
		}catch(Exception e){
			result = e.getLocalizedMessage();
			logger.error("易宝回调后异常：", e);
			throw new UPPException(e.getLocalizedMessage());
		}finally{
			responseTime = String.valueOf(new Date().getTime());
			String command = "/yeepay/mobile/callback";
			try{
				logService.saveYBLog(command, "2", paramsJSON, result, requestTime, responseTime);
			}catch(Exception e){
				logger.warn("保存网关日志异常："+e.getLocalizedMessage(), e);
			}
			
		}
	}

}
