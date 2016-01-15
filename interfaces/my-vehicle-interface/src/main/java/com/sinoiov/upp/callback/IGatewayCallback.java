package com.sinoiov.upp.callback;

import java.math.BigDecimal;

import com.ctfo.upp.exception.UPPException;

public interface IGatewayCallback {
	
	public boolean handleYeepayFast(String orderNo, BigDecimal money, boolean status, long time) throws UPPException;
	
	public boolean handleYeepayWeb(String orderNo, BigDecimal money, boolean status, long time) throws UPPException;
}
