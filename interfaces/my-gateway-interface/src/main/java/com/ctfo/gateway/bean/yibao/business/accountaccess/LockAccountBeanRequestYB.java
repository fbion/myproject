package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class LockAccountBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2065225405685907565L;
	
	//账户ID
	@Necessary
	private String accountId;
	
	@Necessary
	private String requestId;
	//冻结原因
	@Necessary
	private String accountFreezeReason;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAccountFreezeReason() {
		return accountFreezeReason;
	}

	public void setAccountFreezeReason(String accountFreezeReason) {
		this.accountFreezeReason = accountFreezeReason;
	}
}
