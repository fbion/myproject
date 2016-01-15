package com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain;

import com.ctfo.upp.gateway.fastpay.annotation.NotNullOrEmpty;
import com.ctfo.upp.gateway.fastpay.mobile.base.domain.MobileDomain;

public class YBTradeQueryRequestDomain extends MobileDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户账户编号
	@NotNullOrEmpty
	private String merchantAccount;
	// 客户订单号,此两项不可同时为空，都不为空时以易宝交易流水号为准
	@NotNullOrEmpty
	private String orderId;
	// 易宝交易流水号
	private String ybOrderId;

	public String getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getYbOrderId() {
		return ybOrderId;
	}

	public void setYbOrderId(String ybOrderId) {
		this.ybOrderId = ybOrderId;
	}
}
