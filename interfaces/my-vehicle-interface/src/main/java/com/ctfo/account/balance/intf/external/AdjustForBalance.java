package com.ctfo.account.balance.intf.external;

import java.util.List;

import com.ctfo.upp.exception.UPPException;

public interface AdjustForBalance {
	public void adjust() throws UPPException;
	public List<?> queryTrans() throws UPPException;
	
}
