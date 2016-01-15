package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class RechargeInfoBeanResponseYB extends ResponseBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7349468017709738469L;
	
	//账户ID
	private String accountId;
	//充值流水
	private String requestId;
	//订单金额
	private String trxInAmount;
	//订单状态，YEEPAYINDEAL-易宝处理中，SENDTOYEEPAYFAIL-易宝接受失败，SUCECSS-充值成功，FAIL-充值失败
	private String orderStatus;
	//卡余额，若是银行卡充值此字段无值，非银行卡充值则为未消卡的金额
	private String balanceAmount;
	//余额卡号，若是银行卡充值此字段无值，非银行卡充值则为未消卡的卡号
	private String balanceAct;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getTrxInAmount() {
		return trxInAmount;
	}
	public void setTrxInAmount(String trxInAmount) {
		this.trxInAmount = trxInAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getBalanceAct() {
		return balanceAct;
	}
	public void setBalanceAct(String balanceAct) {
		this.balanceAct = balanceAct;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
