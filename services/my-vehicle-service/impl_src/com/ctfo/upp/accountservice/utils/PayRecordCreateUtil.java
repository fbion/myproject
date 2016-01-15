package com.ctfo.upp.accountservice.utils;

import org.apache.commons.lang.StringUtils;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.upp.dict.PayDict;

public class PayRecordCreateUtil {
	public static  void createOrder(OrderInfo orderInfo) {

		orderInfo.setCreateTime(System.currentTimeMillis());
		if(StringUtils.isBlank(orderInfo.getOrderNo())){
			orderInfo.setOrderNo(TradeNumberHelper.getPayOrderNo());
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_INIT);
		}
		
	}
	public static  PaymentTrade createTrade(OrderInfo orderInfo) {
		PaymentTrade paymentTrade = new PaymentTrade();
		paymentTrade.setTradeExternalNo(orderInfo.getTradeExternalNo());
		paymentTrade.setCreateTime(System.currentTimeMillis());
		paymentTrade.setOrderId(orderInfo.getId());
		paymentTrade.setTradeStatus(PayDict.PAY_TRADE_STATUS_INIT);
		paymentTrade.setVersion(0);
		paymentTrade.setPayConfirmDate(0l);
		paymentTrade.setOrderAmount(orderInfo.getOrderAmount());
		paymentTrade.setPayPoundScale(0l);
		paymentTrade.setAccountNo(orderInfo.getAccountNo());
		paymentTrade.setCollectMoneyAccountNo(orderInfo.getCollectMoneyAccountNo());
		paymentTrade.setTradeType(orderInfo.getOrderType()); // 交易类型（科目）
		paymentTrade.setServiceProviderCode(orderInfo.getServiceProviderCode());
		paymentTrade.setPayOrderId(orderInfo.getId());
		return paymentTrade;
	}
}
