package com.sinoiov.upp.service.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.dict.PayDict;
import com.sinoiov.upp.business.payment.IPaymentTradeBusiness;
import com.sinoiov.upp.service.AbstractService;
import com.sinoiov.upp.service.IWebPay;

@Service("webPayImpl")
public class WebPayImpl extends AbstractService implements IWebPay {
	
	private static final Log logger = LogFactory
			.getLog(WebPayImpl.class);

	@Override
	public Map<String, String> recharge(String type, String workOrderNo, String storeCode,
			String fCallbackUrl, String callbackUrl, String identityId,
			String bankCode, BigDecimal money, String productName, String productCatalog, String accountNo, String clientType, String remark,String tradeExternalNo,String ownerUserNo)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService发起网银支付请求:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		OrderInfo orderInfo = this.getOrderInfoInstance(bankCode, workOrderNo, storeCode, fCallbackUrl, callbackUrl, identityId, money, productName, productCatalog, accountNo, clientType, remark);
		orderInfo.setTradeExternalNo(tradeExternalNo);
		orderInfo.setOwnerUserNo(ownerUserNo);
		String result = "";
		if(type.equals("1")) {
			result = super.getService(IPaymentTradeBusiness.class).netRecharge(orderInfo);
		}
		if(type.equals("2")) {
			result = super.getService(IPaymentTradeBusiness.class).netPay(orderInfo);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("url", result);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService发起网银支付请求:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return resultMap;
	}
	
	private OrderInfo getOrderInfoInstance(String bankCode, String workOrderNo,
			String storeCode, String fCallbackUrl, String callbackUrl,
			String identityId, BigDecimal money, String productName, String productCatalog, String accountNo, String clientType, String remark) throws Exception {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setBankCode(bankCode);
		orderInfo.setUserId(identityId);
		orderInfo.setOrderAmount(money.multiply(new BigDecimal(100)).longValue());
		orderInfo.setProductName(productName);
		orderInfo.setProductCatalog(productCatalog);
		orderInfo.setWorkOrderNo(workOrderNo);
		orderInfo.setStoreCode(storeCode);
		orderInfo.setCallbackUrl(callbackUrl);
		orderInfo.setFcallbackUrl(fCallbackUrl);
		orderInfo.setRemarks(remark);
		orderInfo.setAccountNo(accountNo);
		orderInfo.setCollectMoneyAccountNo(accountNo);
		orderInfo.setClentType(clientType);
		orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_WEB_PAY);
		return orderInfo;
	}
}
