package com.ctfo.gateway.bean.yibao.callback;

import java.io.Serializable;

public class UnlockAccountCallBackBeanResponseYB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7387792712579754933L;
	/**
	 * 账户ID
	 */
	private String accountId;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}
