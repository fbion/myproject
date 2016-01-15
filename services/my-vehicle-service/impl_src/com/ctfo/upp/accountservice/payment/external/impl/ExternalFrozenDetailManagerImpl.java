package com.ctfo.upp.accountservice.payment.external.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.AccountFrozenDetail;
import com.ctfo.payment.intf.external.IExternalFrozenDetailManager;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.manager.payment.IFrozenDetailManager;

//@Service("externalFrozenDetailManager")
public class ExternalFrozenDetailManagerImpl implements IExternalFrozenDetailManager{
	@Autowired
	@Qualifier("frozenDetailManagerService")  
	private IFrozenDetailManager frozenDetailManager;
	
	@Override
	public AccountFrozenDetail queryAccountFrozenDetailMess(String storeCode,
			String workOrderNo) throws UPPException {

		return frozenDetailManager.queryAccountFrozenDetailMess(storeCode, workOrderNo);
	}
	
}
