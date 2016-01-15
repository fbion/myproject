package com.ctfo.account.contrast.intf.external;

import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.upp.exception.UPPException;

public interface WithYBContrastTrade {
	public boolean synchronizingOper(PaymentTrade paymentTrade)throws UPPException;
	
	
	public String TransferSynchronizingByTime(Long startTime,Long endTime) throws UPPException;
}
