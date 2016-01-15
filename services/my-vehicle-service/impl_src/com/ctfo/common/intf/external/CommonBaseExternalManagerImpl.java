package com.ctfo.common.intf.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.upp.accountservice.payment.internal.impl.CommonBaseManagerImpl;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.service.smscode.CommonBaseManager;

//@Service("commonBaseExternalManager")
public class CommonBaseExternalManagerImpl implements CommonBaseExternalManager{
	@Autowired
	@Qualifier("commonBaseManager") 
	private CommonBaseManager commonBaseManager;


	@Override
	public boolean sendShortMessByAccountNo(String accountNo, String templetNo,
			List<String> contents) throws UPPException {
		
		return commonBaseManager.sendShortMessByAccountNo(accountNo, templetNo, contents);
	}

	@Override
	public boolean sendShortMess(String mobileNo, String templetNo,
			List<String> contents) throws UPPException {
		
		return commonBaseManager.sendShortMess(mobileNo, templetNo, contents);
	}
}
