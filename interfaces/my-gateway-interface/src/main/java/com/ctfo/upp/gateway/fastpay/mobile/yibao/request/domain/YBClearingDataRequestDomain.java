package com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain;

import com.ctfo.upp.gateway.fastpay.annotation.NotNullOrEmpty;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;

public class YBClearingDataRequestDomain extends FastPayDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户账户编号
	@NotNullOrEmpty
	private String merchantAccount;
	// 开户时间
	@NotNullOrEmpty
	private String startDate;
	// 结束时间
	@NotNullOrEmpty
	private String endDate;

	public String getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
