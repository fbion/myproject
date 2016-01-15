package com.ctfo.upp.gatewayservice.facade;

import org.springframework.stereotype.Service;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.AbstractFastPaymentFacade;
import com.ctfo.upp.gateway.fastpay.intf.Router;
import com.ctfo.upp.gatewayservice.fastpay.impl.FastPaymentRouter;

@Service("fastPaymentServiceFacade")
public class FastPaymentFacadeImpl implements AbstractFastPaymentFacade {
	final private Router router = new FastPaymentRouter();

	@Override
	public FastPayDomain fastPayment(FastPayDomain paymentDomain) throws UPPException {
		// TODO Auto-generated method stub
		return router.routeAndRun(paymentDomain);
	}

	@Override
	public FastPayDomain fastPaymentTradeQuery(FastPayDomain queryTradeDomain) throws UPPException {
		// TODO Auto-generated method stub
		return router.routeAndRun(queryTradeDomain);
	}

	@Override
	public FastPayDomain fastPaymentCallback(FastPayDomain responseDomain) throws UPPException {
		// TODO Auto-generated method stub
		return router.routeAndRun(responseDomain);
	}

	@Override
	public FastPayDomain sendMessage(FastPayDomain messageDomain) throws UPPException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public FastPayDomain fastPaymentGetClearingData(FastPayDomain requestDomain) throws UPPException {
		// TODO Auto-generated method stub
		return router.routeAndRun(requestDomain);
	}

}
