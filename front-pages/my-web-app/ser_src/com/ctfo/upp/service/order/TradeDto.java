package com.ctfo.upp.service.order;

import java.io.Serializable;
import java.util.Date;

public class TradeDto implements Serializable{
	
	/**
	 * 
	 */
	private String id;
	/**
	 * 对应支付订单
	 */
	private String orderId;
	/**
	 * 交易创建分区时间
	 */
	private Date createSubareaTime;
	/**
	 * 交易创建时间
	 */
	private Long createTime;
	/**
	 * 调用支付平台后，支付平台回传确认的时间
	 */
	private Long payConfirmDate;
	/**
	 * 交易金额
	 */
	private Long orderAmount;
	/**
	 * 支付手续费
	 */
	private Long payPoundScale;
	/**
	 * 标识第三方支付平台，如：易宝、支付宝或中交支付
	 */
	private String serviceProviderCode;
	/**
	 * wap支付、网页支付
	 */
	private String payChannel;
	/**
	 * 第三方支付给定的交易流水号
	 */
	private String externalNo;
	/**
	 * 标识本交易是否已经结算
	 */
	private String paySettleStatus;
	/**
	 * 标识本交易的对账状态
	 */
	private String payCheckStatus;
	/**
	 * 支付交易的状态
	 */
	private String tradeStatus;
	/**
	 * 批量与第三方支付进行交易时使用该字段，对应一个交易批次
	 */
	private String payId;
	/**
	 * 银行的编码
	 */
	private String bankCode;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 直连网银或者连接第三方支付
	 */
	private String paymentType;
	/**
	 * 系统中标识交易的流水号，与订单中的流水号一致
	 */
	private String tradeExternalNo;
	/**
	 * 充值、提现、转账等
	 */
	private String tradeType;
	/**
	 * 该交易是否记账成功
	 */
	private String bookAccountStatus;
	/**
	 * 
	 */
	private String payOrderId;
	/**
	 * 支付的卡类型，信用卡-CREDIT,借记卡-DEBIT"
	 */
	private String cardType;
	/**
	 * 付款内部账户号，如果是充值交易付款和收款账户是同一个人
	 */
	private String accountNo;
	/**
	 * 收款内部账户号，如果是充值交易付款和收款账户是同一个人
	 */
	private String collectMoneyAccountNo;
	/**
	 * 提现需要使用的银行卡号
	 */
	private String bankCardNo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getCreateSubareaTime() {
		return createSubareaTime;
	}
	public void setCreateSubareaTime(Date createSubareaTime) {
		this.createSubareaTime = createSubareaTime;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getPayConfirmDate() {
		return payConfirmDate;
	}
	public void setPayConfirmDate(Long payConfirmDate) {
		this.payConfirmDate = payConfirmDate;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getPayPoundScale() {
		return payPoundScale;
	}
	public void setPayPoundScale(Long payPoundScale) {
		this.payPoundScale = payPoundScale;
	}
	public String getServiceProviderCode() {
		return serviceProviderCode;
	}
	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getExternalNo() {
		return externalNo;
	}
	public void setExternalNo(String externalNo) {
		this.externalNo = externalNo;
	}
	public String getPaySettleStatus() {
		return paySettleStatus;
	}
	public void setPaySettleStatus(String paySettleStatus) {
		this.paySettleStatus = paySettleStatus;
	}
	public String getPayCheckStatus() {
		return payCheckStatus;
	}
	public void setPayCheckStatus(String payCheckStatus) {
		this.payCheckStatus = payCheckStatus;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getTradeExternalNo() {
		return tradeExternalNo;
	}
	public void setTradeExternalNo(String tradeExternalNo) {
		this.tradeExternalNo = tradeExternalNo;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getBookAccountStatus() {
		return bookAccountStatus;
	}
	public void setBookAccountStatus(String bookAccountStatus) {
		this.bookAccountStatus = bookAccountStatus;
	}
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getCollectMoneyAccountNo() {
		return collectMoneyAccountNo;
	}
	public void setCollectMoneyAccountNo(String collectMoneyAccountNo) {
		this.collectMoneyAccountNo = collectMoneyAccountNo;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	
	

}
