package com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPayment;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain.YBFastPayRequestDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain.YBFastPayRequestResponseDomain;
import com.ctfo.upp.gateway.fastpay.utils.encrypt.AES;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpParameter;
import com.ctfo.upp.gatewayservice.service.base.impl.LogServiceUtils;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class FastPaymentYBImpl extends AbstractFastPaymentYB implements FastPayment {
	
	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public FastPayDomain fastPayment(FastPayDomain paymentDomain) throws UPPException {
		String paramsJSON = "", requestTime="", responseTime="";
		FastPayDomain result = null;
		try{
			
			objectNotNull(paymentDomain);
			if (!(paymentDomain instanceof YBFastPayRequestDomain))
				throw new IllegalStateException("移动端调用一键支付传递的请求参数bean无效.");
			fillDefaultPropertyValue(paymentDomain);
			checkObjectField(paymentDomain);
			Map<String, Object> paramsMap = object2Map(paymentDomain);
			paramsMap.put("productcatalog", ConfigUtil.getValue("PRODUCTCATALOG"));
			fillSpecialPropertyValue(paramsMap);
			final String sign = super.buildSign(paramsMap);
			if (logger.isDebugEnabled())
				logger.debug(String.format("sign=[%s]", sign));
			paramsMap.put("sign", sign);
			
			try {
				paramsJSON = object2JSON(paramsMap);
			} catch (Exception e) {
				throw new UPPException("将一键支付请求参数转换为JSON格式时产生错误，原因：", e);
			}

			logger.info(String.format("MOBILE端发送易宝一键支付参数(明文)：paramsJSON=[%s]", paramsJSON));
		
			final String merchantAesKey = buildMerchantAesKey();
			final String data = AES.encryptToBase64(paramsJSON, merchantAesKey);
		
			final String merchantAccount = getMerchantAccount(paramsMap);
			String encryptKey = encryptKey(merchantAesKey);
			{
				if (paramsMap != null)
					paramsMap.clear();
			}
		
			logger.info("MOBILE端发送易宝一键支付参数(密文)：data="+data+"&encryptKey"+encryptKey);
			paramsJSON += ",{\"data\":\""+data+"\"encryptKey\":\""+encryptKey+"\"}";
			requestTime = String.valueOf(new Date().getTime());
			result = buildResponse(sendRequest(buildRequestURL(merchantAccount, data, encryptKey), createHttpParameter(merchantAccount, data, encryptKey)));
			responseTime = String.valueOf(new Date().getTime());
			return result;
	
		}catch(Exception e){
			logger.error("MOBILE发送易宝参数异常：", e);
			throw new UPPException(e.getLocalizedMessage());
		}finally{
			String command = ConfigUtil.getValue("YB_MOBILE_FAST_PAY_REQUEST_URL");
			String responseContent = result==null?"":((YBFastPayRequestResponseDomain)result).getJumpURL();	
			try{
				LogServiceUtils.saveYBLog(command, "1", paramsJSON, responseContent, requestTime, responseTime);
			}catch(Exception e){
				logger.warn("保存网关日志异常："+e.getLocalizedMessage(), e);
			}
		}
	}

	protected String buildRequestURL(final String merchantAccount, final String data, final String encryptKey) {
		final String requestURL = ConfigUtil.getValue("YB_MOBILE_FAST_PAY_REQUEST_URL");
		if (logger.isDebugEnabled())
			logger.debug(String.format("The fast payment request URL is:%s", requestURL));
		String requestFullURL = "";
		try {
			requestFullURL = String.format("%s?merchantaccount=%s&data=%s&encryptkey=%s", requestURL, URLEncoder.encode(merchantAccount, "UTF-8"), URLEncoder.encode(data, "UTF-8"), URLEncoder.encode(encryptKey, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug(String.format("The fast payment request full URL is:%s", requestFullURL));
		return requestFullURL;
	}

	protected String sendRequest(final String url, HttpParameter httpParameter) throws UPPException {
		return url;
	}

	protected FastPayDomain buildResponse(final String response) {
		if (logger.isDebugEnabled())
			logger.debug("response=" + response);
		YBFastPayRequestResponseDomain fastPaymentResponse = new YBFastPayRequestResponseDomain();
		fastPaymentResponse.setJumpURL(response);
		return fastPaymentResponse;
	}

	static protected void fillSpecialPropertyValue(Map<String, Object> paramsMap) {
		if (paramsMap == null || paramsMap.isEmpty())
			return;
		paramsMap.put(CALLBACK_URL_KEY, ConfigUtil.getValue("YB_MOBILE_FAST_PAY_GATEWAY_CALLBACK_URL"));
	}
}
