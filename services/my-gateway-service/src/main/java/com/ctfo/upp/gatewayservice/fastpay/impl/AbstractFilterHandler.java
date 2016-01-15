package com.ctfo.upp.gatewayservice.fastpay.impl;

import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;
import com.ctfo.upp.gateway.fastpay.intf.FastPayment;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentCallback;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentClearingData;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentMessage;
import com.ctfo.upp.gateway.fastpay.intf.FastPaymentTradeQuery;

public abstract class AbstractFilterHandler {
	protected FastPayment fastPayment;
	protected FastPaymentCallback fastPaymentCallback;
	protected FastPaymentMessage fastPaymentMessage;
	protected FastPaymentTradeQuery fastPaymentTradeQuery;
	protected FastPaymentClearingData fastPaymentClearingData;

	static public interface FastPaymentFactory {
		FastPay createFastPaymentImpl(FastPayDomain fastPayDomain);
	}
}
