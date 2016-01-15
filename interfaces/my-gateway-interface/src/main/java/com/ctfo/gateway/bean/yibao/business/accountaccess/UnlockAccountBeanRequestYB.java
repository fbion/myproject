package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class UnlockAccountBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -590432878819785607L;
	
	//账户ID
	@Necessary
	private String accountId;
	
	@Necessary
	private String requestId;
	//解冻原因
	@Necessary
	private String accountUnFreezeReason;

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

	public String getAccountUnFreezeReason() {
		return accountUnFreezeReason;
	}

	public void setAccountUnFreezeReason(String accountUnFreezeReason) {
		this.accountUnFreezeReason = accountUnFreezeReason;
	}

}
