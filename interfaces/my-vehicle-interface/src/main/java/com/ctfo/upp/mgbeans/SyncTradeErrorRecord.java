package com.ctfo.upp.mgbeans;

import java.util.List;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.google.code.morphia.annotations.Id;

public class SyncTradeErrorRecord {
	@Id
	private String id;
	private String accountNo;
	private List<PaymentTrade> unSuccessTradeList;
	private Account account;
	private Long startTime;
	private Long endTime;
	
	
	public SyncTradeErrorRecord() {
		super();
	}
	public SyncTradeErrorRecord(SyncTradeFailRecord record) {
		
		super();
		
		this.startTime = record.getStartTime();
		this.endTime =  record.getEndTime();
	}
	public List<PaymentTrade> getUnSuccessTradeList() {
		return unSuccessTradeList;
	}
	public void setUnSuccessTradeList(List<PaymentTrade> unSuccessTradeList) {
		this.unSuccessTradeList = unSuccessTradeList;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
}
