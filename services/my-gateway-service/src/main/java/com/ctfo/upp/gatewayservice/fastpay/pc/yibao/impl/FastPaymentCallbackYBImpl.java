package com.ctfo.upp.gatewayservice.fastpay.pc.yibao.impl;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentCallback;
import com.ctfo.upp.gateway.fastpay.pc.yibao.response.domain.YBFastPayCallbackResultDomain;
import com.ctfo.upp.gateway.fastpay.pc.yibao.response.domain.YBFastPayCallbackUPPResponseDomain;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpParameter;
import com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl.AbstractFastPaymentYB;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class FastPaymentCallbackYBImpl extends AbstractFastPaymentYB implements FastPaymentCallback {
	private Log logger = LogFactory.getLog(getClass());

	@Override
	public FastPayDomain fastPaymentCallback(FastPayDomain responseDomain) throws UPPException {
		
		try{
			
			objectNotNull(responseDomain);
			
			if (!(responseDomain instanceof YBFastPayCallbackResultDomain))
				throw new UPPException("传递的PC端网关回调结果对象无效.");

			final String callbackUPPURL = getCallbackUPPURL();
			
			if(StringUtils.isBlank(callbackUPPURL))
				throw new UPPException("回调accountService的URL不能为空");
			
			final HttpParameter httpParameter = httpParameterFactory.createHttpParameter();
			
			final String data = URLEncoder.encode(object2JSON(responseDomain), DEFAULT_CHARSET);
			
			if(StringUtils.isBlank(callbackUPPURL))
				throw new UPPException("回调accountServicer的参数不能为空!value:"+data);
			
			httpParameter.add("data", data);
						
			return sendCallbackRequest(callbackUPPURL, httpParameter);			
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("回调accountServicer服务异常", e);
		}
		
		return null;	
	}

	private FastPayDomain sendCallbackRequest(String callbackUPPURL, HttpParameter httpParameter) throws Exception{
		
		String responseContent = null;
		YBFastPayCallbackUPPResponseDomain mobileDomain = null;
		
		int callbackCount = Integer.parseInt(ConfigUtil.getValue("GATEWAY_CALLBACK_RETRY_COUNT"));
		for(int i=0;i<callbackCount;i++){
			
			responseContent = sendRemoteReauestNew(callbackUPPURL, httpParameter);
		
			logger.info("第"+(i+1)+"次回调业务系统[URL:"+callbackUPPURL+", 参数:"+httpParameter+", 返回："+responseContent+"]");
			
			if (StringUtils.isNotBlank(responseContent)) {
				mobileDomain = new YBFastPayCallbackUPPResponseDomain();
				mobileDomain.setSuccess(true);
				mobileDomain.setResponseText(responseContent);
				break;
			}
			
			TimeUnit.SECONDS.sleep(Integer.parseInt(ConfigUtil.getValue("GATEWAY_CALLBACK_SLEEP_SECONDS")));	
		}

		return mobileDomain;
	}
	
	
	
	
//	@Override
//	public FastPayDomain fastPaymentCallback(FastPayDomain responseDomain) throws UPPException {
//		// TODO Auto-generated method stub
//		objectNotNull(responseDomain);
//		if (!(responseDomain instanceof YBFastPayCallbackResultDomain))
//			throw new IllegalStateException("传递的PC端网关回调结果对象无效.");
//		
//		final String callbackUPPURL = getCallbackUPPURL();
//		if (callbackUPPURL == null || callbackUPPURL.isEmpty())
//			throw new UPPException("Callback UPP URL is invalid.");
//		// The callback parameter name is data.
//		final HttpParameter httpParameter = httpParameterFactory.createHttpParameter();
//		try {
//			final String data = URLEncoder.encode(object2JSON(responseDomain), DEFAULT_CHARSET);
//			if (logger.isDebugEnabled())
//				logger.debug("The fast payment pc sent to the UPP parameter data value=" + data);
//			httpParameter.add("data", data);
//		} catch (Exception ignore) {
//			// TODO Auto-generated catch block
//			ignore.printStackTrace();
//		}
//		// Don't wait, otherwise it may timeout.
//		return sendCallbackRequest(callbackUPPURL, httpParameter);
//	}
//	
//	private FastPayDomain sendCallbackRequest(String callbackUPPURL, HttpParameter httpParameter) {
//		String responseContent = null;
//		YBFastPayCallbackUPPResponseDomain mobileDomain = null;
//		if (logger.isDebugEnabled())
//			logger.debug("The fast payment pc callback upp start.");
//		int resendCount = 0;
//		for (;;) {
//			try {
//				responseContent = _sendRemoteRequest(callbackUPPURL, httpParameter);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				if (logger.isDebugEnabled())
//					logger.debug("When send the fast payment pc callback request to unified payment platform generate errors,reasons: ", e);
//			}
//			if (responseContent != null && !responseContent.isEmpty() && !responseContent.trim().equals("")) {
//				mobileDomain = new YBFastPayCallbackUPPResponseDomain();
//				mobileDomain.setSuccess(true);
//				mobileDomain.setResponseText(responseContent);
//				break;
//			}
//			try {
//				if ((resendCount - ConfigUtil.getValue("GATEWAY_CALLBACK_RETRY_COUNT()) >= 0)
//					break;
//				else {
//					resendCount++;
//					TimeUnit.SECONDS.sleep(ConfigUtil.getValue("GATEWAY_CALLBACK_SLEEP_SECONDS());
//				}
//			} catch (InterruptedException ignore) {
//				// TODO Auto-generated catch block
//				ignore.printStackTrace();
//			}
//		}
//		if (logger.isDebugEnabled())
//			logger.debug("The fast payment pc callback upp end.");
//		return mobileDomain;
//	}
}
