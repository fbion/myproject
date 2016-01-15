package com.ctfo.upp.gatewayservice.fastpay.pc.yibao.impl;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;
import com.ctfo.upp.gateway.fastpay.intf.Filter;
import com.ctfo.upp.gateway.fastpay.intf.FilterChan;
import com.ctfo.upp.gatewayservice.fastpay.impl.AbstractFilterHandler;

public class PCFilterHandlerYB extends AbstractFilterHandler implements Filter {

	private FastPaymentFactory fastPaymentFactory = new FastPaymentFactory() {

		@Override
		public FastPay createFastPaymentImpl(FastPayDomain fastPayDomain) {
			// TODO Auto-generated method stub
			synchronized (this) {
				if (fastPayDomain instanceof com.ctfo.upp.gateway.fastpay.pc.yibao.request.domain.YBFastPayRequestDomain) {
					if (fastPayment == null)
						fastPayment = new com.ctfo.upp.gatewayservice.fastpay.pc.yibao.impl.FastPaymentYBImpl();
					return fastPayment;
				} else if (fastPayDomain instanceof com.ctfo.upp.gateway.fastpay.pc.yibao.response.domain.YBFastPayCallbackResultDomain) {
					if (fastPaymentCallback == null)
						fastPaymentCallback = new com.ctfo.upp.gatewayservice.fastpay.pc.yibao.impl.FastPaymentCallbackYBImpl();
					return fastPaymentCallback;
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
