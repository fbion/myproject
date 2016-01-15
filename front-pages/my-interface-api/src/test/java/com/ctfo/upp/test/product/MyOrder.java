package com.ctfo.upp.test.product;

import java.io.Serializable;

public class MyOrder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String callbackUrl;
	private String accountNo;
	private String collectMoneyAccountNo;
	private String orderAmount;
	private String  productCatalog;
	private String  productName;
	private String  remarks;
	private String  storeCode;
	private String  userId;
	private String  workOrderNo;
	private String  ownerUserNo;
	private String  tradeExternalNo;
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getCollectMoneyAccountNo() {
		return collectMoneyAccountNo;
	}
	public void setCollectMoneyAccountNo(String collectMoneyAccountNo) {
		this.collectMoneyAccountNo = collectMoneyAccountNo;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getProductCatalog() {
		return productCatalog;
	}
	public void setProductCatalog(String productCatalog) {
		this.productCatalog = productCatalog;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	public String getOwnerUserNo() {
		return ownerUserNo;
	}
	public void setOwnerUserNo(String ownerUserNo) {
		this.ownerUserNo = ownerUserNo;
	}
	public String getTradeExternalNo() {
		return tradeExternalNo;
	}
	public void setTradeExternalNo(String tradeExternalNo) {
		this.tradeExternalNo = tradeExternalNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	

}
