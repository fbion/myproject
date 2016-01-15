package com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain;

import com.ctfo.upp.gateway.fastpay.mobile.base.domain.MobileDomain;

public class YBMessageResponseDomain extends MobileDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户账户编号
	private String merchantAccount;
	// 商户提交订单号
	private String orderId;
	// 手机号
	private String phone;
	// 短信发生时间
	private Long sendTime;
	// 签名
	private String sign;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

}
