package com.ctfo.upp.dto;

import com.ctfo.account.dao.beans.Account;

public class AccountNoExtend extends Account {

	private static final long serialVersionUID = 1L;
	private String accountNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		super.setInsideAccountNo(accountNo);
		this.accountNo = accountNo;
	}
	
	
}
