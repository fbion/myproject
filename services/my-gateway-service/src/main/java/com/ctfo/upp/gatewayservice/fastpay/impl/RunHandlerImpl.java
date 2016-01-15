package com.ctfo.upp.gatewayservice.fastpay.impl;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;
import com.ctfo.upp.gateway.fastpay.intf.FastPayment;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentCallback;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentClearingData;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentMessage;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentTradeQuery;

public class RunHandlerImpl extends AbstractHandler {

	@Override
	public FastPayDomain handle(FastPay fastPay, FastPayDomain fastPayDomain) throws UPPException {
		// TODO Auto-generated method stub
		if (fastPayDomain == null)
			throw new NullPointerException("传递参数is null.");
		if (fastPay == null)
			throw new UPPException("没有找到对应支付平台的实现.");
		for (;;) {
			if (FastPayment.class.isInstance(fastPay)) {
				final FastPayment fastPayment = (FastPayment) fastPay;
				fastPayDomain = fastPayment.fastPayment(fastPayDomain);
			} else if (FastPaymentCallback.class.isInstance(fastPay)) {
				final FastPaymentCallback fastPaymentCallback = (FastPaymentCallback) fastPay;
				fastPayDomain = fastPaymentCallback.fastPaymentCallback(fastPayDomain);
			} else if (FastPaymentMessage.class.isInstance(fastPay)) {
				final FastPaymentMessage fastPaymentMessage = (FastPaymentMessage) fastPay;
				fastPayDomain = fastPaymentMessage.sendMessage(fastPayDomain);
			} else if (FastPaymentTradeQuery.class.isInstance(fastPay)) {
				final FastPaymentTradeQuery fastPaymentTradeQuery = (FastPaymentTradeQuery) fastPay;
				fastPayDomain = fastPaymentTradeQuery.fastPaymentTradeQuery(fastPayDomain);
			} else if (FastPaymentClearingData.class.isInstance(fastPay)) {
				final FastPaymentClearingData fastPaymentClearingData = (FastPaymentClearingData) fastPay;
				fastPayDomain = fastPaymentClearingData.fastPaymentGetClearingData(fastPayDomain);
			} else
				fastPayDomain = null;
			break;
		}
		return fastPayDomain;
	}

}
