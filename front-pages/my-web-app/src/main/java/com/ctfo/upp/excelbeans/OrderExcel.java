package com.ctfo.upp.excelbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class OrderExcel implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "exp(订单号;NOMAL_ENUM)imp", required = false)
	private String orderNo;

	@XmlElement(name = "exp(业务订单号;NOMAL_ENUM)imp", required = false)
	private String workOrderNo;
	
	@XmlElement(name = "exp(会员编号;NOMAL_ENUM)imp", required = false)
	private String ownerUserNo;
	
	@XmlElement(name = "exp(流水号;MAP_ENUM)imp", required = false)
	private String tradeExternalNo;
	
	@XmlElement(name = "exp(科目;NOMAL_ENUM)imp", required = false)
	private String orderType;

	@XmlElement(name = "exp(账户号;NOMAL_ENUM)imp", required = false)
	private String accountNo;
	
	@XmlElement(name = "exp(交易金额（元）;NOMAL_ENUM)imp", required = false)
	private String orderAmount;

	@XmlElement(name = "exp(交易时间;MAP_ENUM)imp", required = false)
	private String createTime;

	/*@XmlElement(name = "exp(支付平台;MAP_ENUM)imp", required = false)
	private String serviceProvider;*/

	@XmlElement(name = "exp(订单状态;MAP_ENUM)imp", required = false)
	private String orderStatus;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

/*	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}*/
	
	public String getAccountNo() {
		return accountNo;
	}

	public String getTradeExternalNo() {
		return tradeExternalNo;
	}

	public void setTradeExternalNo(String tradeExternalNo) {
		this.tradeExternalNo = tradeExternalNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

/*	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
*/
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	
}