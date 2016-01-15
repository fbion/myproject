package com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;
import com.ctfo.upp.gateway.fastpay.intf.Filter;
import com.ctfo.upp.gateway.fastpay.intf.FilterChan;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain.YBClearingDataRequestDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain.YBFastPayRequestDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain.YBMessageRequestDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain.YBTradeQueryRequestDomain;
import com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain.YBFastPayCallbackResultDomain;
import com.ctfo.upp.gatewayservice.fastpay.impl.AbstractFilterHandler;
import com.ctfo.upp.gatewayservice.fastpay.yibao.impl.FastPaymentClearingDataYBImpl;
import com.ctfo.upp.gatewayservice.fastpay.yibao.impl.FastPaymentTradeQueryYBImpl;

public class FilterHandlerYB extends AbstractFilterHandler implements Filter {
	private FastPaymentFactory fastPaymentFactory = new FastPaymentFactory() {

		@Override
		public FastPay createFastPaymentImpl(FastPayDomain fastPayDomain) {
			// TODO Auto-generated method stub
			synchronized (this) {
				if (fastPayDomain instanceof YBFastPayRequestDomain) {
					if (fastPayment == null)
						fastPayment = new FastPaymentYBImpl();
					return fastPayment;
				} else if (fastPayDomain instanceof YBMessageRequestDomain) {
					if (fastPaymentMessage == null)
						fastPaymentMessage = new FastPaymentMessageYBImpl();
					return fastPaymentMessage;
				} else if (fastPayDomain instanceof YBTradeQueryRequestDomain) {
					if (fastPaymentTradeQuery == null)
						fastPaymentTradeQuery = new FastPaymentTradeQueryYBImpl();
					return fastPaymentTradeQuery;
				} else if (fastPayDomain instanceof YBFastPayCallbackResultDomain) {
					if (fastPaymentCallback == null)
						fastPaymentCallback = new FastPaymentCallbackYBImpl();
					return fastPaymentCallback;
				}else if (fastPayDomain instanceof YBClearingDataRequestDomain) {
					if (fastPaymentClearingData == null)
						fastPaymentClearingData = new FastPaymentClearingDataYBImpl();
					return fastPaymentClearingData;
				} else
					return null;
			}
		}
	};

	@Override
	public FastPay doFilter(FastPayDomain fastPayDomain, FilterChan fc) throws UPPException {
		// TODO Auto-generated method stub
		if (fastPayDomain == null || fc == null)
			throw new NullPointerException("传递的参数 is null.");
		FastPay fastPayImpl = fastPaymentFactory.createFastPaymentImpl(fastPayDomain);
		if (fastPayImpl != null)
			return fastPayImpl;
		return fc.doFilter(fastPayDomain);
	}
}
