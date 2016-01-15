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
import com.sinoiov.upp.service.IFastPay;

@Service("fastPayImpl")
public class FastPayImpl extends AbstractService implements IFastPay {
	
	private static final Log logger = LogFactory
			.getLog(FastPayImpl.class);
	
	// 储蓄卡支付请求
	@Override
	public Map<String, String> rechargeByApiSaveRequest(String type, String workOrderNo,
			String storeCode, String fCallbackUrl, String callbackUrl,
			String identityId, String phone, String cardNo, String idCard,
			BigDecimal money, String productName, String productCatalog, String accountNo, String clientType, String remark) throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService发起储蓄卡支付请求:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		OrderInfo orderInfo = this.getOrderInfoInstance(workOrderNo, storeCode, fCallbackUrl, callbackUrl, identityId, money, productName, productCatalog, accountNo, clientType, remark);

		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("cardNo", cardNo);
		map.put("idCard", idCard);
		
		String result = "";
		if(type.equals("1")) {
			result = super.getService(IPaymentTradeBusiness.class)
					.fastPayApiRecharge(orderInfo, "3", map);
		}
		if(type.equals("2")) {
			result = super.getService(IPaymentTradeBusiness.class)
					.fastPayApi(orderInfo, "3", map);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("phone", result);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService发起储蓄卡支付请求:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return resultMap;
	}

	// 信用卡支付请求
	@Override
	public Map<String, String> rechargeByApiCreditRequest(String type, String workOrderNo,
			String storeCode, String fCallbackUrl, String callbackUrl,
			String identityId, String phone, String cardNo, String validThru,
			String cvv2, BigDecimal money, String productName, String productCatalog, String accountNo, String clientType, String remark) throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService发起信用卡支付请求:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		OrderInfo orderInfo = this.getOrderInfoInstance(workOrderNo, storeCode, fCallbackUrl, callbackUrl, identityId, money, productName, productCatalog, accountNo, clientType, remark);

		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("cardNo", cardNo);
		map.put("endTime", validThru);
		map.put("cvv", cvv2);
		
		String result = "";
		if(type.equals("1")) {
			result = super.getService(IPaymentTradeBusiness.class)
					.fastPayApiRecharge(orderInfo, "2", map);
		}
		if(type.equals("2")) {
			result = super.getService(IPaymentTradeBusiness.class)
					.fastPayApi(orderInfo, "2", map);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("phone", result);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService发起信用卡支付请求:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return resultMap;
	}
	
	// 绑卡支付请求
	@Override
	public Map<String, String> rechargeByApiRequest(String type, String workOrderNo,
			String storeCode, String fCallbackUrl, String callbackUrl,
			String identityId, String bingCardId,
			BigDecimal money, String productName, String productCatalog, String accountNo, String clientType, String remark) throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService发起绑卡支付请求:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		OrderInfo orderInfo = this.getOrderInfoInstance(workOrderNo, storeCode, fCallbackUrl, callbackUrl, identityId, money, productName, productCatalog, accountNo, clientType, remark);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("bindCardId", bingCardId);
		
		String result = "";
		if(type.equals("1")) {
			result = super.getService(IPaymentTradeBusiness.class)
					.fastPayApiRecharge(orderInfo, "1", map);
		}
		if(type.equals("2")) {
			result = super.getService(IPaymentTradeBusiness.class)
					.fastPayApi(orderInfo, "1", map);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("phone", result);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService发起绑卡支付请求:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return resultMap;
	}
	
	// 支付请求确认
	@Override
	public Map<String, String> rechargeByApiSure(String workOrderNo, String messageCode) throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService确认支付请求:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		String result = super.getService(IPaymentTradeBusiness.class).fastPayApiSure(workOrderNo, messageCode);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", result);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService确认支付请求:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return resultMap;
	}
	
	//PC端支付
	@Override
	public Map<String, String> rechargeByClient(String type, String workOrderNo,
			String storeCode, String fCallbackUrl, String callbackUrl,
			String identityId, BigDecimal money, String productName, String productCatalog, String accountNo, String clientType, String remark,String tradeExternalNo,String ownerUserNo) throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService终端支付请求:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		OrderInfo orderInfo = this.getOrderInfoInstance(workOrderNo, storeCode, fCallbackUrl, callbackUrl, identityId, money, productName, productCatalog, accountNo, clientType, remark);
		orderInfo.setTradeExternalNo(tradeExternalNo);
		orderInfo.setOwnerUserNo(ownerUserNo);
		String result = "";
		if(type.equals("1")) {
			result = super.getService(IPaymentTradeBusiness.class)
					.fastPayRecharge(orderInfo);
		}
		if(type.equals("2")) {
			result = super.getService(IPaymentTradeBusiness.class)
					.fastPay(orderInfo);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("url", result);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService终端支付请求:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return resultMap;
	}
	
	private OrderInfo getOrderInfoInstance(String workOrderNo,
			String storeCode, String fCallbackUrl, String callbackUrl,
			String identityId, BigDecimal money, String productName, String productCatalog, String accountNo, String clientType, String remark) throws Exception {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setUserId(identityId);
		orderInfo.setOrderAmount(money.multiply(new BigDecimal(100)).longValue());
		orderInfo.setProductName(productName);
		orderInfo.setWorkOrderNo(workOrderNo);
		orderInfo.setStoreCode(storeCode);
		orderInfo.setCallbackUrl(callbackUrl);
		orderInfo.setFcallbackUrl(fCallbackUrl);
		orderInfo.setProductCatalog(productCatalog);
		orderInfo.setRemarks(remark);
		orderInfo.setAccountNo(accountNo);
		orderInfo.setClentType(clientType);
		orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_FAST_PAY);
		orderInfo.setCreateTime(System.currentTimeMillis());
		return orderInfo;
	}
}
