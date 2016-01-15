package com.ctfo.upp.service.adjust;

import com.ctfo.upp.exception.UPPException;

public interface AdjustAccountService {
	public String queryObject(String name)throws UPPException;

	public String queryTableHeader(String name) throws UPPException;

	public String gateWayQuery(String accountNo) throws UPPException;
	
}
