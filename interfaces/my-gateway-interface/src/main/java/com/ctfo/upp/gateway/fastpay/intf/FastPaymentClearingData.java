package com.ctfo.upp.gateway.fastpay.intf;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;

/***
 * 一键支付数据清结算接口
 *
 */
public interface FastPaymentClearingData extends FastPay {
	FastPayDomain fastPaymentGetClearingData(FastPayDomain requestDomain) throws UPPException;
}
