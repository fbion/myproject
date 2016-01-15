package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class CheckAccountBeanDetailRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6020734711951725213L;
	
	//请求流水号
	@Necessary
	private String requestId;
	//账户ID
	@Necessary
	private String accountId;
	//账户余额
	@Necessary
	private String balance;
	//转账出金额
	@Necessary
	private String trxout;
	//转账进金额
	@Necessary
	private String trxin;
	//充值金额
	@Necessary
	private String recharge;
	//现金
	@Necessary
	private String cash;
	//充值退款金额
	@Necessary
	private String rechargeRefund;
	//调整金额
	@Necessary
	private String adjuset;
	//交易时间戳
	@Necessary
	private String time;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getTrxout() {
		return trxout;
	}
	public void setTrxout(String trxout) {
		this.trxout = trxout;
	}
	public String getTrxin() {
		return trxin;
	}
	public void setTrxin(String trxin) {
		this.trxin = trxin;
	}
	public String getRecharge() {
		return recharge;
	}
	public void setRecharge(String recharge) {
		this.recharge = recharge;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getRechargeRefund() {
		return rechargeRefund;
	}
	public void setRechargeRefund(String rechargeRefund) {
		this.rechargeRefund = rechargeRefund;
	}
	public String getAdjuset() {
		return adjuset;
	}
	public void setAdjuset(String adjuset) {
		this.adjuset = adjuset;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
