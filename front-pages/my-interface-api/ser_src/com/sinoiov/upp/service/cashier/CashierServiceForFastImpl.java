package com.sinoiov.upp.service.cashier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.sinoiov.upp.business.payment.IPaymentTradeBusiness;
import com.sinoiov.upp.manager.payment.IOrderManager;
import com.sinoiov.upp.manager.payment.IPaymentBaseManager;
import com.sinoiov.upp.service.AbstractService;
import com.sinoiov.upp.service.ICashierServiceForFast;
import com.sinoiov.upp.yeepayfastgateway.bean.YeepayFastBankCard;
import com.sinoiov.upp.yeepayfastgateway.bean.YeepayFastBankCardList;
import com.sinoiov.upp.yeepayfastgateway.intf.IYeepayFastRechargeFacade;

@Service("cashierServiceForFastImpl")
public class CashierServiceForFastImpl extends AbstractService implements
		ICashierServiceForFast {

	private static final Log logger = LogFactory
			.getLog(CashierServiceForFastImpl.class);

	// 查询绑定的银行卡
	@Override
	public List<YeepayFastBankCardList> queryBindBankCard(String userId)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用快捷支付网关获取绑定银行卡开始:"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		List<YeepayFastBankCardList> list = super.getService(
				IYeepayFastRechargeFacade.class).queryBindBankCard(userId);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用快捷支付网关获取绑定银行卡结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return list;
	}

	// 发送短信
	@Override
	public Map<String, String> sendMessageCode(String workOrderNo) throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用快捷支付网发送验证码:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		OrderInfo orderInfo = super.getService(IOrderManager.class).getOrderByWorkOrderNo(workOrderNo);
		
		PaymentTrade paymentTrade = super.getService(IPaymentBaseManager.class).getPaymentTradeByOrderId(orderInfo.getId());
		
		String mobileNo = super.getService(IYeepayFastRechargeFacade.class)
				.sendMessageCode(paymentTrade.getPayNo());
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("phone", mobileNo);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用快捷支付网发送验证码:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return returnMap;
	}

	// 查询银行卡
	@Override
	public YeepayFastBankCard queryBankCard(String bankCardNo) throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用快捷支付网关查询银行卡结果:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		YeepayFastBankCard bankCard = super.getService(
				IYeepayFastRechargeFacade.class).queryBankCard(bankCardNo);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用快捷支付网关查询银行卡结果:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return bankCard;
	}
	
	// 解绑银行卡
	@Override
	public Map<String, String> unBindCard(String userId, String bindCardId) throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用快捷支付网关解绑银行卡:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		String result = super.getService(
				IYeepayFastRechargeFacade.class).unBindCard(userId, bindCardId);
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("result", result);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用快捷支付网关解绑银行卡:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return returnMap;
	}
	@Override
	public Map<String, String> queryOrderRechargeOverOrNot(String workOrderNo,String tradeExternalNo)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService查询快捷支付是否成功:开始"
				+ System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		OrderInfo orderInfo = super.getService(IPaymentTradeBusiness.class).queryOrderRechargeOverOrNot(workOrderNo,tradeExternalNo);
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", orderInfo.getOrderStatus());
		result.put("fCallbackUrl", orderInfo.getFcallbackUrl());
		
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 调用AccountService查询快捷支付是否成功:结束:"
				+ System.currentTimeMillis() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return result;
	}
}
