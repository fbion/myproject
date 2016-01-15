package com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain;

import com.ctfo.upp.gateway.fastpay.mobile.base.domain.MobileDomain;

public class YBFastPayCallbackResultDomain extends MobileDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户账户
	private String merchantaccount;
	// 易宝交易流水号
	private String yborderid;
	// 交易订单
	private String orderid;
	// 支付金额 int 以“分”为单位的整型
	private Long amount;
	// 银行编码 string 支付卡所属银行的编码，如ICBC
	private String bankcode;
	// 银行信息 string 支付卡所属银行的名称
	private String bank;
	// 卡类型 int 支付卡的类型，1为借记卡，2为信用卡
	private Integer cardtype;
	// 卡号后4位 string 支付卡卡号后4位
	private String lastno;
	// 订单状态 int 1：成功
	private Integer status;

	private String className = getClass().getCanonicalName();

	public String getMerchantaccount() {
		return merchantaccount;
	}

	public void setMerchantaccount(String merchantaccount) {
		this.merchantaccount = merchantaccount;
	}

	public String getYborderid() {
		return yborderid;
	}

	public void setYborderid(String yborderid) {
		this.yborderid = yborderid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Integer getCardtype() {
		return cardtype;
	}

	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}

	public String getLastno() {
		return lastno;
	}

	public void setLastno(String lastno) {
		this.lastno = lastno;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}
}
