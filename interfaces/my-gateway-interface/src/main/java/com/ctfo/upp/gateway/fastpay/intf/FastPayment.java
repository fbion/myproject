package com.ctfo.upp.gateway.fastpay.intf;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;

public interface FastPayment extends FastPay {
	FastPayDomain fastPayment(FastPayDomain fastPayDomain) throws UPPException;
}
