package com.ctfo.upp.bean;
/**
 * 解冻账户金额实体类
 * @author lipeng01
 *
 */
public class UnfreezeAmountDto {
	private String accountNo;//账户编号
	private String orderAmount;//解冻金额
	private String workOrderNo;//业务订单号
	private String tradeExternalNo;//交易流水号
	public String getAccountNo() {
		return accountNo;
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
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	public String getTradeExternalNo() {
		return tradeExternalNo;
	}
	public void setTradeExternalNo(String tradeExternalNo) {
		this.tradeExternalNo = tradeExternalNo;
	}
	
}
