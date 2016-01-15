package com.ctfo.upp.contrast.intf.external.impl;

import com.ctfo.upp.accountservice.payment.internal.impl.AbstractPayment;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.mgbeans.SyncTradeFailRecord;

public class AbstractContrast extends AbstractPayment{
	protected SyncTradeFailRecord saveFailSyncTradeRecord(Long startTime,Long endTime) throws UPPException{
		try {
			SyncTradeFailRecord record =this.createFailSyncTradeRecord(startTime, endTime);

			//mongoService.save(record);
			return record;
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}
	
	protected SyncTradeFailRecord createFailSyncTradeRecord(Long startTime,Long endTime) throws UPPException{
		SyncTradeFailRecord record=new SyncTradeFailRecord();

		record.setId(java.util.UUID.randomUUID().toString());
		record.setStartTime(startTime);
		record.setEndTime(endTime);
		record.setCreateTime(endTime);
		return record;
	}
}
