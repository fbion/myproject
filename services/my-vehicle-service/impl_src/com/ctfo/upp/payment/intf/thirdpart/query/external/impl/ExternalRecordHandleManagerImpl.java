package com.ctfo.upp.payment.intf.thirdpart.query.external.impl;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.intf.internal.ThirdPartPayCheckAccountHandworkManager;
import com.ctfo.payment.intf.thirdpartquery.external.ExternalRecordHandleManager;
import com.ctfo.upp.accountservice.utils.ClassRealNameMap;
import com.ctfo.upp.contrast.intf.internal.impl.WithYBContrastInternalManagerImpl;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.payment.intf.thirdpart.query.internal.ThirdPartAccountInspectManager;

//@Service("externalRecordHandleManager")
public class ExternalRecordHandleManagerImpl implements
		ExternalRecordHandleManager {
	private static Log logger = LogFactory.getLog(ExternalRecordHandleManagerImpl.class);
	@Autowired
	@Qualifier("thirdPartPayCheckAccountHandworkManager")  
	private ThirdPartPayCheckAccountHandworkManager thirdPartPayCheckAccountHandworkManager;

	@Autowired
	@Qualifier("thirdPartAccountInspectManager")
	private ThirdPartAccountInspectManager thirdPartAccountInspectManager;
	
	@Override
	public String queryMongoDbRecord(String name) throws UPPException {

		
		return JSONArray.fromObject(thirdPartPayCheckAccountHandworkManager.queryMongoDBRecord(name)).toString();
		 
	}
	@Override
	public String queryTableHeader(String name) throws UPPException {
		return ClassRealNameMap.tableHeaderMap.get(name);
	}
	
	@Override
	public void dealWithAccountInspect()throws UPPException {
		thirdPartAccountInspectManager.dealWithAccountInspect();
	}
}
