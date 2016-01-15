package com.ctfo.upp.gatewayservice.fastpay.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;
import com.ctfo.upp.gateway.fastpay.intf.FilterChan;
import com.ctfo.upp.gateway.fastpay.intf.Router;
import com.ctfo.upp.gateway.fastpay.intf.RunHandler;
import com.ctfo.upp.gatewayservice.fastpay.impl.FilterChanHandler;

public class FastPaymentRouter implements Router {
	static private Log logger = LogFactory.getLog(FastPaymentRouter.class);
	static private final RunHandler handler = new RunHandlerImpl();

	@Override
	public FastPay route(FastPayDomain fastPayDomain) throws UPPException {
		// TODO Auto-generated method stub
		final FilterChan filterChan = FilterChanHandler.getInstance();
		return filterChan.doFilter(fastPayDomain);
	}

	@Override
	public FastPayDomain routeAndRun(FastPayDomain fastPayDomain) throws UPPException {
		// TODO Auto-generated method stub
		final FilterChan filterChan = FilterChanHandler.getInstance();
		final FastPay fastPay = filterChan.doFilter(fastPayDomain);
		if (fastPay == null)
			throw new UPPException("根据传递的实体没有找到对应的实现.");
		if (logger.isDebugEnabled())
			logger.debug("Implementation of class filter to:" + fastPay.getClass());
		return handler.handle(fastPay, fastPayDomain);
	}

}
