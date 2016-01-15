package com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.exception.UPPRuntimeException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.utils.BeanUtil;
import com.ctfo.upp.gateway.fastpay.utils.RandomUtil;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpParameter;
import com.ctfo.upp.gatewayservice.fastpay.utils.yibao.ErrorJson;
import com.ctfo.upp.gatewayservice.fastpay.utils.yibao.RespondJson;
import com.ctfo.upp.gatewayservice.util.base.AbstractFastPayment;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.security.ConvertUtils;
import com.ctfo.upp.security.encrypt.RSA;

public abstract class AbstractFastPaymentYB extends AbstractFastPayment {
	private static Log logger = LogFactory.getLog(AbstractFastPaymentYB.class);
	static protected final String DEFAULT_CHARSET = "utf-8";

	static protected String _sendRemoteRequest(String url, HttpParameter httpParameter) throws IOException {
		return _sendRemoteRequest(url, httpParameter, "utf-8", true);
	}

	static protected void objectNotNull(Object object) {
		if (object == null)
			throw new NullPointerException("The object parameter is null.");
	}

	static protected void checkResponsed(String responsed) throws UPPException {
		if (responsed == null || responsed.isEmpty())
			throw new UPPException("网关返回结果 is null or empty.");
		ErrorJson errorJson = null;
		try {
			errorJson = JSONObject.parseObject(responsed, ErrorJson.class);
		} catch (Throwable ignore) {
			errorJson = null;
		}
		if (errorJson != null && errorJson.getError() != null && !errorJson.getError().isEmpty())
			throw new UPPException(errorJson.getError());
	}

	static protected String _sendRemoteRequest(String url, HttpParameter httpParameter, String charset) throws IOException {
		return _sendRemoteRequest(url, httpParameter, charset, true);
	}

	static protected String _sendRemoteRequest(String url, HttpParameter httpParameter, boolean post) throws IOException {
		return _sendRemoteRequest(url, httpParameter, "utf-8", post);
	}

	static protected String _sendRemoteRequest(String url, HttpParameter httpParameter, String charset, boolean post) throws IOException {
		return sendRemoteRequest(url, httpParameter, charset, post);
	}

	static protected String buildSign(Map<?, ?> paramsMap) {
		
		try{
			//return SignUtil.buildSignOfYB(BeanUtil.convertMapValues2Str(paramsMap));	
			String beanOfValues = BeanUtil.convertMapValues2Str(paramsMap);
			if (beanOfValues == null || beanOfValues.isEmpty())
				throw new IllegalArgumentException("Invalid parameter.");
			
			final String merchantPrivateKey = ConfigUtil.getPrivateKey();
			if (merchantPrivateKey == null || merchantPrivateKey.isEmpty())
				throw new IllegalStateException("私钥无效.");
			
			return RSA.sign(beanOfValues, merchantPrivateKey);
			
		}catch(Exception e){
			logger.warn("生成签名异常", e);
		}
		return null;
	}

	static protected String getDefaultMerchantAccount() {
		return ConfigUtil.getValue("YB_FAST_MER_ID");
	}

	static protected String getCallbackUPPURL() {
		return ConfigUtil.getValue("ZJ_MOBILE_FAST_PAY_UPP_CALLBACL_URL");
	}

	static protected final String MERCHANT_ACCOUNT_KEY = "merchantaccount";

	static protected String getMerchantAccount(Map<String, Object> paramsMap) {
		if (paramsMap.containsKey(MERCHANT_ACCOUNT_KEY)) {
			if (paramsMap.get(MERCHANT_ACCOUNT_KEY) == null || paramsMap.get(MERCHANT_ACCOUNT_KEY).toString().equals("")){
				String yb_fast_mer_id = ConfigUtil.getValue("YB_FAST_MER_ID");
				paramsMap.put(MERCHANT_ACCOUNT_KEY, yb_fast_mer_id);
			}
				
			return (String) paramsMap.get(MERCHANT_ACCOUNT_KEY);
		} else
			throw new IllegalStateException("未发现有效的商户号参数.");
	}
	static protected final String PROPERTY_MERCHANT_ACCOUNT = "merchantAccount";
	static protected void fillDefaultPropertyValue(FastPayDomain fastPayDomain) {
		if (fastPayDomain == null)
			return;
		final Field field = ReflectionUtils.findField(fastPayDomain.getClass(), PROPERTY_MERCHANT_ACCOUNT);
		if (field != null) {
			field.setAccessible(true);
			String yb_fast_mer_id = ConfigUtil.getValue("YB_FAST_MER_ID");
			ReflectionUtils.setField(field, fastPayDomain, yb_fast_mer_id);
		}
	}

	static protected final String CALLBACK_URL_KEY = "callbackurl";

	static protected Map<String, Object> object2Map(FastPayDomain fastPayDomain) throws UPPException {
		Map<String, Object> paramsMap = _mapClean(describe(fastPayDomain, true));
		if (paramsMap == null || paramsMap.isEmpty())
			throw new UPPException("将请求参数转换为Map不成功.");
		return paramsMap;
	}

	static protected String buildMerchantAesKey() {
		// merchantAesKey
		return RandomUtil.getRandom(16);
	}

	static protected String encryptKey(String merchantAesKey) {
		String encryptedKey = null;
		try {
			
			String yb_fast_mer_id = ConfigUtil.getValue("YB_FAST_MER_ID");
			
			String yb_fast_publickey = ConfigUtil.getPublicKey(yb_fast_mer_id);
			
			encryptedKey = RSA.encrypt(merchantAesKey, yb_fast_publickey);
					
		} catch (Exception e) {
			throw new UPPRuntimeException("加密秘钥时产生错误,原因:", e);
		}
		return encryptedKey;
	}

	static protected HttpParameter createHttpParameter(final String merchantAccount, final String data, final String encryptKey, boolean encode, String charset) {
		if (merchantAccount == null || merchantAccount.isEmpty())
			throw new IllegalArgumentException("商户编号参数不合法.");
		else if (data == null || data.isEmpty())
			throw new IllegalArgumentException("data参数不合法.");
		else if (encryptKey == null || encryptKey.isEmpty())
			throw new IllegalArgumentException("秘钥参数不合法.");
		HttpParameter httpParameter = null;
		try {
			httpParameter = httpParameterFactory.createHttpParameter();
			httpParameter.add("merchantaccount", ((encode == true && charset != null && !charset.isEmpty()) ? URLEncoder.encode(merchantAccount, charset) : merchantAccount));
			httpParameter.add("data", ((encode == true && charset != null && !charset.isEmpty()) ? URLEncoder.encode(data, charset) : data));
			httpParameter.add("encryptkey", ((encode == true && charset != null && !charset.isEmpty()) ? URLEncoder.encode(encryptKey, charset) : encryptKey));
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}
		return httpParameter;
	}

	static protected HttpParameter createHttpParameter(final String merchantAccount, final String data, final String encryptKey) {
		return createHttpParameter(merchantAccount, data, encryptKey, false, null);
	}

	static protected HttpParameter createHttpParameter(final String merchantAccount, final String data, final String encryptKey, String encodeCharset) {
		return createHttpParameter(merchantAccount, data, encryptKey, true, encodeCharset);
	}

	@SuppressWarnings("unchecked")
	static protected Map<String, Object> _mapClean(Map<?, ?> map) {
		if (map == null || map.isEmpty())
			throw new NullPointerException("The map parameter is null.");
		synchronized (map) {
			map = mapClean(map);
			final Set<?> keySet = map.keySet();
			for (Iterator<?> iter = keySet.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (map.get(key) == null || map.get(key).toString().isEmpty() || map.get(key).toString().equals("") || map.get(key).toString().trim().equals(""))
					iter.remove();
			}
		}
		return (Map<String, Object>) map;
	}

	protected String sendRequest(final String url, HttpParameter httpParameter) throws UPPException {
		if (url == null || url.isEmpty())
			throw new IllegalArgumentException("The url parameter invalid.");
		else if (httpParameter == null)
			throw new NullPointerException("The httpParameter is null.");
		String response = null;
		try {
			response = _sendRemoteRequest(url, httpParameter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new UPPException("发送远程请求时产生错误，原因：", e);
		}
		return response;
	}

	protected String decryptYeepayResult(String response) throws UPPException {
		if (response == null || response.isEmpty())
			throw new IllegalArgumentException("返回结果不能为空.");
		String result = checkYeepayResult(response);
		if (result == null || result.isEmpty())
			throw new UPPException("Check yeepay return results failure.");
		return result;
	}

	/**
	 * 将一键支付返回的结果进行验证，并解密
	 * 
	 * @param yeepayResult
	 * @return
	 */
	public String checkYeepayResult(String yeepayResult) {
		if (yeepayResult == null || yeepayResult.isEmpty() || yeepayResult.equals(""))
			return null;
		String results = null;
		try {
			// 将易宝返回的字符串转为json对象，并通过解密获取明文处理结果
			RespondJson rj = JSONObject.parseObject(yeepayResult, RespondJson.class);
			String encryptkey = rj.getEncryptkey();
			String data = rj.getData();
			// 对易宝返回的结果进行验签
			String yb_fast_mer_id = ConfigUtil.getValue("YB_FAST_MER_ID");
			String yb_fast_publickey = ConfigUtil.getPublicKey(yb_fast_mer_id);
			String zj_privatekey = ConfigUtil.getPrivateKey();
			
			results = ConvertUtils.decodeParamJson(data, encryptkey, zj_privatekey, yb_fast_publickey);

		} catch (Exception e) {
			logger.error("验证易宝返回结果异常："+e.getLocalizedMessage(), e);
		}
		return results;
	}

	/**
	 * 将一键支付返回的清算接口结果进行验证，并解密
	 * 
	 * @param yeepayResult
	 * @return
	 */
	public String checkYeepayClearResult(String yeepayResult) {
		if (yeepayResult == null || yeepayResult.isEmpty() || yeepayResult.equals(""))
			return null;
		if (yeepayResult.indexOf("error") >= 0)
			return null;
		String results = null;
		try {
			if (yeepayResult.indexOf("data") >= 0)
				results = this.checkYeepayResult(yeepayResult);
		} catch (Exception e) {
			logger.error("解密易宝返回结果异常："+e.getLocalizedMessage(), e);
		}
		return results;
	}
}
