package com.ctfo.upp.gatewayservice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.AbstractFastPaymentFacade;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain.YBFastPayRequestDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain.YBTradeQueryRequestDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain.YBFastPayCallbackResultDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain.YBTradeQueryResponseDomain;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpClient4Util;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpParameter;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpResp;
import com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl.AbstractFastPaymentYB;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.impl.multithread.TaskPool;
import com.ctfo.upp.root.utils.UUIDUtil;
import com.ctfo.upp.utils.SpringBUtils;
import com.google.code.morphia.utils.Assert;

public class FastPaymentTest extends TestBase {
	static private Log logger = LogFactory.getLog(FastPaymentTest.class);
	private AbstractFastPaymentFacade abstractFastPaymentFacade;

	@Override
	public void setUp() throws Exception {
		// TODO Auto-generated method stubsh
		if (abstractFastPaymentFacade == null)
			abstractFastPaymentFacade = (AbstractFastPaymentFacade) SpringBUtils.getBean("fastPaymentServiceFacade");
	}

	@Ignore
	public void testFastPayment() {
		YBFastPayRequestDomain requestDomain = new YBFastPayRequestDomain();
		//requestDomain.setMerchantAccount(ConfigUtil.getValue("ZJ_YB_MERCHANT_ACCOUNT());
		requestDomain.setOrderId("mobile10011");
		requestDomain.setCardNo("6226388002295420");
		Long l = System.currentTimeMillis() / 1000;
		requestDomain.setTransTime(l.intValue());
		requestDomain.setAmount(20 * 10 * 10);
		requestDomain.setProductCatalog("4");
		requestDomain.setProductName("中交-测试产品11");
		requestDomain.setIdentityId("identity2");
		requestDomain.setIdentityType(2);
		requestDomain.setTerminalType(2);
		requestDomain.setTerminalId(UUIDUtil.newUUID2());
		requestDomain.setUserIp("192.168.1.1");
		requestDomain.setUserUa("NokiaN70/3.0544.5.1 Series60/2.8 Profile/MIDP-2.0 Configuration/CLDC-1.1");
		requestDomain.setProductDesc("");
		try {
			abstractFastPaymentFacade.fastPayment(requestDomain);
		} catch (UPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testYeepayFastPaymentCallback() {
		String data = "IYeQuX55TytKUCrz5PwT3lAoGr+5bwmnpDd/xJwXQEGXIg777x0NkTwDhsKZy1LJs4gX5eF2Im1YImk++W3hnY4xr3NGmx2tf/nibvDUwDtupN/+fwnMvjGVUeEzTFVt8fLne6c+Yrkcbki2Sh/uSdwIkj5VHl37gDnB2sg6miajCYCEzazSzImxLgrtFQVc2lThAVc4YMi5fiJ0l9vSN6UF4i4MTBNKhK4HZUzke5+lrfk9nLtPE5Iv8OxGsquA69KZT1WpAz4ZLZswhajDTbZj3NEhZI3Qd9+Hwmn2kXzkG4R7kz2rAWOEMFElAQrmotnry0uESnF1jJ8zOTmKeTo41ELoX+slppBPPhXifdIQ6SPwThEMFPaw872+OJw8imxR47GmS3rYg0SP1YicSCrt1MXlAP72348wJ6AZZ7Bfc6lRghSthm2+mKb6S9pT4Sr9+O5cgzibnXNM/MOcKmYctqWuG5RK3t0CEf2bvgc=";
		String encryptKey = "uZRL4dzqjx8CAqTQItvrsblqOXByDPA1Ojat7t8VJxnJEHregvjfsdWjzR8iDVoNHoK2VZmVx79FmdrScTAiw/HtkyQBvTgJjTm67kXItyJexH67fbjMG1SsvdoJXDA+8bT6oIy9Oh1yKa6+CafMnJehnmPbIRtB0lSWljQxcnE=";
		String result = new AbstractFastPaymentYB() {
		}.checkYeepayClearResult(String.format("{\"data\":\"%s\",\"encryptkey\":\"%s\"}", data, encryptKey));
		Assert.isNotNull(result);
		FastPayDomain mobileDomain = null;
		try {
			mobileDomain = JSONObject.parseObject(result, YBFastPayCallbackResultDomain.class);
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("Callback fast payment gateway Mobile interface,Parse object failed reasons:Returns the content format may change.");
		}
		final FastPayDomain _mobileDomain = mobileDomain;
		TaskPool.named("test").setPoolSize(5).submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					abstractFastPaymentFacade.fastPaymentCallback(_mobileDomain);
				} catch (UPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// logger
				}
			}

		});

		try {
			TimeUnit.MINUTES.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Ignore
	public void testTradeQuery() {
		YBTradeQueryRequestDomain tradeQueryDomain = new YBTradeQueryRequestDomain();
		//tradeQueryDomain.setMerchantAccount(ConfigUtil.getValue("ZJ_YB_MERCHANT_ACCOUNT());
		tradeQueryDomain.setOrderId("pc100008");
		tradeQueryDomain.setYbOrderId("");
		FastPayDomain fastPayDomain = null;
		try {
			fastPayDomain = abstractFastPaymentFacade.fastPaymentTradeQuery(tradeQueryDomain);
		} catch (UPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("return object MerchantAccount=" + ((YBTradeQueryResponseDomain) fastPayDomain).getMerchantAccount());
		logger.debug("return object ProductName=" + ((YBTradeQueryResponseDomain) fastPayDomain).getProductName());
		Assert.isNotNull(fastPayDomain);
		try {
			TimeUnit.MINUTES.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Ignore
	public void testPCFastPayment() {
		com.ctfo.upp.gateway.fastpay.pc.yibao.request.domain.YBFastPayRequestDomain requestDomain = new com.ctfo.upp.gateway.fastpay.pc.yibao.request.domain.YBFastPayRequestDomain();
		//requestDomain.setMerchantAccount(ConfigUtil.getValue("ZJ_YB_MERCHANT_ACCOUNT());
		requestDomain.setOrderId("pc100010");
		Long l = System.currentTimeMillis() / 1000;
		requestDomain.setTransTime(l.intValue());
		requestDomain.setAmount(10 * 10 * 10);
		requestDomain.setProductCatalog("4");
		requestDomain.setProductName("中交-测试PC产品10");
		requestDomain.setIdentityId("identityPC");
		requestDomain.setCallbackUrl(ConfigUtil.getValue("YB_PC_FAST_PAY_GATEWAY_CALLBACK_URL"));
		requestDomain.setIdentityType(2);
		requestDomain.setTerminalType(2);
		requestDomain.setTerminalId(UUIDUtil.newUUID2());
		requestDomain.setUserIp("192.168.1.2");
		requestDomain.setUserUa("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/536.30.1 (KHTML, like Gecko) Safari/522.0");
		requestDomain.setProductDesc("");
		try {
			abstractFastPaymentFacade.fastPayment(requestDomain);
		} catch (UPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Ignore
	public void testYeepayPCFastPaymentCallback() throws UnsupportedEncodingException {
		String data = "Cuby7LiCzQFwag6HQ5QmrwwGQrI4AS+d4W8Xgss+2HkQuASopg/zPKUIDN7KCDM5WnTx3v1FnRJ8QL/ZrBAf+Xevs8rGjguAqwt14pwdLh/RRe2bNP2ct0oUx+wSR0E9oWsoRKoOKSWEsVSEzQC/9Rigxr2We2k3XOZ7kxbM4yu0yWFt1qQlk3W7PLRKwQoEey4vr6JOgYvF64DEgF5DE0+2dBAKPnfUM3bEBd+BlSJpO0kVzHoMB1E1YLQ/x78dt9V5IKUj5ItHrHAhIlZRKICtf/rAymrAHUzc8MstDqnpKeW5NQ0wbrI9+ZEKrK+3HTcaTv2yAvWTAd0rbjR5K9fDIbgF5lJjQxDHfjKeB89A+6tHOvXLZZK49ka8GnRucTjksKB9NvwZtQ8H6mdDUCVFE2awjOCH4wTBCujd/xGONTksVbULQKMADe1+Ha4eTh04zDKBh32hylD507dhb8WH3VytiVNDfoOwkGs9+U0=";
		data = java.net.URLEncoder.encode(data, "utf-8");
		String encryptKey = "uy71RtKqWiZ5au+oUwQwfiTD9DTRrU5QQhZkTtMCc+LnMKWkgU/+T7mQy++1vDSuQjjx4biiAbXBA8pTiTQkio0uhh5FTZ0/NhkwnG6iJ49pyl0c8d0Tk2IWy1oDQs+CJwWH4TpDvkzWpS2T95a2gzSsIJYx5LHYax7j5YGKIjA=";
		encryptKey = java.net.URLEncoder.encode(encryptKey, "utf-8");
		System.out.println("data=" + data);
		System.out.println("encryptKey=" + encryptKey);
		String result = new AbstractFastPaymentYB() {
		}.checkYeepayClearResult(String.format("{\"data\":\"%s\",\"encryptkey\":\"%s\"}", data, encryptKey));
		Assert.isNotNull(result);
		FastPayDomain pcDomain = null;
		try {
			pcDomain = JSONObject.parseObject(result, com.ctfo.upp.gateway.fastpay.pc.yibao.response.domain.YBFastPayCallbackResultDomain.class);
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("Callback fast payment gateway Mobile interface,Parse object failed reasons:Returns the content format may change.");
		}
		final FastPayDomain _pcDomain = pcDomain;

		TaskPool.named("abc").setPoolSize(5).submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					abstractFastPaymentFacade.fastPaymentCallback(_pcDomain);
				} catch (UPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// logger
				}
			}

		});

		try {
			TimeUnit.MINUTES.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Ignore
	public void testCallController() throws ClientProtocolException, IOException {
		HttpClient4Util httpClient = HttpClient4Util.createDefault();
		HttpParameter httpParameter = new HttpParameter();
		httpParameter.add("data", "abc");
		httpParameter.add("encryptkey", "def");
		HttpResp r = httpClient.doPost("http://localhost:8081/GateWayService/callback/yeepay/mobile/callback", httpParameter, "utf-8");
		Assert.isNotNull(r.getText("utf-8"));
		System.out.println(r.getText("utf-8"));
		httpClient.shutdown();
	}
}
