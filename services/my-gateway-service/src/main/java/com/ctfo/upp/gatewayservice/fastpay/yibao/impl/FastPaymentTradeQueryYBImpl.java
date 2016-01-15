package com.ctfo.upp.gatewayservice.fastpay.yibao.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentTradeQuery;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain.YBTradeQueryRequestDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain.YBTradeQueryResponseDomain;
import com.ctfo.upp.gateway.fastpay.utils.encrypt.AES;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpParameter;
import com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl.AbstractFastPaymentYB;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class FastPaymentTradeQueryYBImpl extends AbstractFastPaymentYB implements FastPaymentTradeQuery {
	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public FastPayDomain fastPaymentTradeQuery(FastPayDomain queryTradeDomain) throws UPPException {
		// TODO Auto-generated method stub
		objectNotNull(queryTradeDomain);
		if (!(queryTradeDomain instanceof YBTradeQueryRequestDomain))
			throw new IllegalStateException("传递的交易查询请求参数bean无效.");
		fillDefaultPropertyValue(queryTradeDomain);
		checkObjectField(queryTradeDomain);
		final Map<String, Object> paramsMap = object2Map(queryTradeDomain);
		final String sign = super.buildSign(paramsMap);
		if (logger.isDebugEnabled())
			logger.debug(String.format("sign=[%s]", sign));
		paramsMap.put("sign", sign);
		String paramsJSON = "";
		try {
			paramsJSON = object2JSON(paramsMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UPPException("将交易查询请求参数转换为JSON格式时产生错误，原因：", e);
		}
		if (logger.isDebugEnabled())
			logger.debug(String.format("trade query paramsJSON=[%s]", paramsJSON));
		final String merchantAesKey = buildMerchantAesKey();
		final String data = AES.encryptToBase64(paramsJSON, merchantAesKey);
		if (logger.isDebugEnabled())
			logger.debug(String.format("trade query data=[%s]", data));
		final String merchantAccount = getMerchantAccount(paramsMap);
		String encryptKey = encryptKey(merchantAesKey);
		{
			if (paramsMap != null)
				paramsMap.clear();
		}
		return buildResponse(sendRequest(buildRequestURL(merchantAccount, data, encryptKey), createHttpParameter(merchantAccount, data, encryptKey)));
	}

	@Override
	protected String sendRequest(String url, HttpParameter httpParameter) throws UPPException {
		// TODO Auto-generated method stub
		if (url == null || url.isEmpty())
			throw new IllegalArgumentException("The url parameter invalid.");
		else if (httpParameter == null)
			throw new NullPointerException("The httpParameter is null.");
		String response = null;
		try {
			response = _sendRemoteRequest(url, httpParameter, false);// Get
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new UPPException("发送远程请求时产生错误，原因：", e);
		}
		return response;
	}

	protected String buildRequestURL(final String merchantAccount, final String data, final String encryptKey) {
		final String requestURL = ConfigUtil.getValue("YB_COMMON_FAST_PAY_TRADE_QUERY_REQUEST_URL");
		if (logger.isDebugEnabled())
			logger.debug(String.format("The trade query request URL is:%s", requestURL));
		String requestFullURL = "";
		try {
			requestFullURL = String.format("%s?merchantaccount=%s&data=%s&encryptkey=%s", requestURL, URLEncoder.encode(merchantAccount, "UTF-8"), URLEncoder.encode(data, "UTF-8"), URLEncoder.encode(encryptKey, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug(String.format("The trade query request full URL is:%s", requestFullURL));
		return requestURL;
	}

	protected FastPayDomain buildResponse(final String response) throws UPPException {
		if (logger.isDebugEnabled())
			logger.debug("response=" + response);
		final String plaintextResult = decryptYeepayResult(response);
		checkResponsed(response);
		YBTradeQueryResponseDomain tradeQueryResponse = null;
		try {
			tradeQueryResponse = JSONObject.parseObject(plaintextResult, YBTradeQueryResponseDomain.class);
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("将一键支付交易查询结果转换为对象时失败,原因:", e);
		}
		if (tradeQueryResponse == null)
			throw new UPPException("将一键支付交易查询结果转换为对象时失败.");
		return tradeQueryResponse;
	}

}
