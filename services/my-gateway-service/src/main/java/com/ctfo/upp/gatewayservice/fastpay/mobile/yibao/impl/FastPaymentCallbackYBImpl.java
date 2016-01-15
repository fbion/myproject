package com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentCallback;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain.YBFastPayCallbackResultDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain.YBFastPayCallbackUPPResponseDomain;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpParameter;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class FastPaymentCallbackYBImpl extends AbstractFastPaymentYB implements FastPaymentCallback {
	private Log logger = LogFactory.getLog(getClass());

	@Override
	public FastPayDomain fastPaymentCallback(FastPayDomain responseDomain) throws UPPException {
		try{
			
			objectNotNull(responseDomain);
			
			if (!(responseDomain instanceof YBFastPayCallbackResultDomain))
				throw new UPPException("传递的移动端网关回调结果对象无效.");

			final String callbackUPPURL = getCallbackUPPURL();
			if (StringUtils.isBlank(callbackUPPURL))
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

	private FastPayDomain sendCallbackRequest(String callbackUPPURL, HttpParameter httpParameter)throws Exception{
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
	
}
