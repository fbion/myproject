package com.ctfo.account.dto.beans;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.upp.models.BaseSerializable;

public class AccountDTO extends BaseSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Account account;
	private String serviceProviderCode;
	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
