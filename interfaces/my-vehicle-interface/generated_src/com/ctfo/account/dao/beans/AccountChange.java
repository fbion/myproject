package com.ctfo.account.dao.beans;

import com.ctfo.upp.models.BaseSerializable;

public class AccountChange extends BaseSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***
	 * 内部账号
	 */
	private String accountNo;
	/***
	 * 支付平台编号
	 */
	private String serviceProviderCode;
	/***
	 * 入账金额，没有小数点
	 */
	private Long money;
	/***
	 * 内部交易流水号
	 */
	private String tradeInternalNo;
	/***
	 * 科目
	 */
	private String subject;
	/***
	 * 卡类型
	 */
	private String cardType;
	/***
	 * 商户编号
	 */
	private String storeCode;
	/***
	 * 操作人UUID
	 */
	private String operatorId;
	/**
	 * 记账日期，表示该账户操作属于哪一个账期
	 */
	private String accountDate;
	/**
	 * 对应订单ID
	 */
	private String orderId;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getTradeInternalNo() {
		return tradeInternalNo;
	}

	public void setTradeInternalNo(String tradeInternalNo) {
		this.tradeInternalNo = tradeInternalNo;
	}

	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	
}
