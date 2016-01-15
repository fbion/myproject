package com.ctfo.upp.dto;

import java.io.Serializable;

/**
 * 账户明细dto
 * 
 * @author sunchuanfu
 */
public class AccountDetailDto implements Serializable {
	private static final long serialVersionUID = -1590971191501294628L;
	
	private String id;
	
	private String accountDate;
	
	private String orderNo;
	
	private String orderRemarks;
	
	private String orderType;
	
	private String orderTypeLuc;
	
	private String accountTime; 
	
	private String bookAccountTime;
	
	private String bookAccountMoney;
	
	private String bookAccountTypr;
	
	private String bookCurrentMoney;
	
	private String productName;
	
	private String payChannel;
	
	private String orderId;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeLuc() {
		return orderTypeLuc;
	}

	public void setOrderTypeLuc(String orderTypeLuc) {
		this.orderTypeLuc = orderTypeLuc;
	}

	public String getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(String accountTime) {
		this.accountTime = accountTime;
	}

	public String getBookAccountTime() {
		return bookAccountTime;
	}

	public void setBookAccountTime(String bookAccountTime) {
		this.bookAccountTime = bookAccountTime;
	}

	public String getBookAccountMoney() {
		return bookAccountMoney;
	}

	public void setBookAccountMoney(String bookAccountMoney) {
		this.bookAccountMoney = bookAccountMoney;
	}

	public String getBookAccountTypr() {
		return bookAccountTypr;
	}

	public void setBookAccountTypr(String bookAccountTypr) {
		this.bookAccountTypr = bookAccountTypr;
	}

	public String getBookCurrentMoney() {
		return bookCurrentMoney;
	}

	public void setBookCurrentMoney(String bookCurrentMoney) {
		this.bookCurrentMoney = bookCurrentMoney;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}