package com.ctfo.upp.gateway.fastpay.intf;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;

public interface FilterChan {
	 FastPay doFilter(FastPayDomain fastPayDomain) throws UPPException;
}
