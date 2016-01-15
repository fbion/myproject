package com.ctfo.upp.payment.intf.thirdpart.query.external.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.intf.thirdpartquery.external.ExternalGateWayQueryManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.payment.intf.thirdpart.query.internal.GateWayQueryManager;

//@Service("externalGateWayQueryManager")
public class ExternalGateWayQueryManagerImpl implements ExternalGateWayQueryManager{
	@Autowired
	@Qualifier("gateWayQueryManager")
	private GateWayQueryManager gateWayQueryManager;
	
	public String queryAccountInfo(String accountNo)throws UPPException{
		return gateWayQueryManager.queryAccountInfo(accountNo);
	}
}
