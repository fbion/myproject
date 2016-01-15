package com.ctfo.upp.gateway.fastpay.intf;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;

public interface FastPaymentTradeQuery extends FastPay {
	FastPayDomain fastPaymentTradeQuery(FastPayDomain queryTradeDomain) throws UPPException;
}
