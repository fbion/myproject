package com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain;

import com.ctfo.upp.gateway.fastpay.annotation.FieldLength;
import com.ctfo.upp.gateway.fastpay.annotation.NotNullOrEmpty;
import com.ctfo.upp.gateway.fastpay.mobile.base.domain.MobileDomain;

public class YBMessageRequestDomain extends MobileDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户账户编号
	@NotNullOrEmpty
	private String merchantAccount;
	// 商户生成的唯一订单号，最长50位
	@FieldLength(50)
	@NotNullOrEmpty
	private String orderId;

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
}
