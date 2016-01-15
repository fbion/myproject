package com.sinoiov.upp.callback;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.PayOrderSubjectDict;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.business.payment.IPaymentTradeBusiness;
import com.sinoiov.upp.manager.payment.IOrderManager;
import com.sinoiov.upp.manager.payment.IPaymentBaseManager;

@Service("gatewayCallbackImpl")
public class GatewayCallbackImpl implements IGatewayCallback{
	
	static private Log logger = LogFactory.getLog(GatewayCallbackImpl.class);
	
	@Autowired
	@Qualifier("orderManager")
	private IOrderManager orderManager;
	
	@Autowired
	@Qualifier("paymentBaseManager")
	private IPaymentBaseManager paymentBaseManager;
	
	@Autowired
	@Qualifier("paymentTradeBusiness")
	private IPaymentTradeBusiness paymentTradeBusiness;
	
	@Override
	public boolean handleYeepayFast(String orderNo, BigDecimal money, boolean status, long time) throws UPPException {
		// TODO Auto-generated method stub
		try {
			PaymentTrade trade = paymentBaseManager.getPaymentTradeByPayNo(orderNo);
			if(trade == null) {
				throw new UPPException("不存在PayNo=[" + orderNo + "]的交易");
			}
			OrderInfo order = orderManager.getOrderInfoById(trade.getOrderId());
			order.setPayConfirmDate(time);
			if(status == true) {
				order.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
				trade.setTradeStatus(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS);
			}else {
				order.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_FAIL);
				trade.setTradeStatus(PayDict.PAY_TRADE_STATUS_PAY_FAIL);
			}
			if(order.getOrderSubject().equals(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE)) {
				paymentTradeBusiness.fastPayRechargeCallBack(trade);
			}
			if(order.getOrderSubject().equals(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY)) {
				paymentTradeBusiness.fastPayCallBack(trade);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("处理易宝快捷支付回调发生错误", e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean handleYeepayWeb(String orderNo, BigDecimal money, boolean status, long time) throws UPPException {
		// TODO Auto-generated method stub
		try {
			PaymentTrade trade = paymentBaseManager.getPaymentTradeByPayNo(orderNo);
			if(trade == null) {
				throw new UPPException("不存在PayNo=[" + orderNo + "]的交易");
			}
			OrderInfo order = orderManager.getOrderInfoById(trade.getOrderId());
			order.setPayConfirmDate(time);
			if(status == true) {
				order.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
				trade.setTradeStatus(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS);
			}else {
				order.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_FAIL);
				trade.setTradeStatus(PayDict.PAY_TRADE_STATUS_PAY_FAIL);
			}
			if(order.getOrderSubject().equals(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_RECHARGE)) {
				paymentTradeBusiness.netRechargeCallBack(trade);
			}
			if(order.getOrderSubject().equals(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_PAY)) {
				paymentTradeBusiness.netPayCallBack(trade);
			}
		} catch (UPPException e) {
			// TODO: handle exception
			logger.error("处理易宝网银支付回调发生错误", e);
			return false;
		}
		return true;
	}

}
