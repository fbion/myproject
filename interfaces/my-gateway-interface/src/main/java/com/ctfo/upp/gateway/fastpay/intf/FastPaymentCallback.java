package com.ctfo.upp.gateway.fastpay.intf;

import javax.security.auth.callback.Callback;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;

public interface FastPaymentCallback extends Callback, FastPay {
	FastPayDomain fastPaymentCallback(FastPayDomain responseDomain) throws UPPException;
}
