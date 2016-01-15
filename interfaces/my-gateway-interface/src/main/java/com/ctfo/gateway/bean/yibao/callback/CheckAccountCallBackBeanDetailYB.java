package com.ctfo.gateway.bean.yibao.callback;

import java.io.Serializable;

public class CheckAccountCallBackBeanDetailYB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 650344871730834332L;
	
	//账户编号
	private String accountId;
	//账户余额
	private String Balance;
	//充值金额
	private String Recharge;
	//交易出款金额
	private String Trxout;
	//交易入款金额
	private String Trxin;
	//退款金额
	private String RechargeRefund;
	//提现金额
	private String Withdraw;
	//调账金额
	private String Adjust;
	//没有入账的充值订单号（逗号分隔）
	private String UnInAccountRecharge;
	
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
	}
	public String getRecharge() {
		return Recharge;
	}
	public void setRecharge(String recharge) {
		Recharge = recharge;
	}
	public String getTrxout() {
		return Trxout;
	}
	public void setTrxout(String trxout) {
		Trxout = trxout;
	}
	public String getTrxin() {
		return Trxin;
	}
	public void setTrxin(String trxin) {
		Trxin = trxin;
	}
	public String getRechargeRefund() {
		return RechargeRefund;
	}
	public void setRechargeRefund(String rechargeRefund) {
		RechargeRefund = rechargeRefund;
	}
	public String getWithdraw() {
		return Withdraw;
	}
	public void setWithdraw(String withdraw) {
		Withdraw = withdraw;
	}
	public String getAdjust() {
		return Adjust;
	}
	public void setAdjust(String adjust) {
		Adjust = adjust;
	}
	public String getUnInAccountRecharge() {
		return UnInAccountRecharge;
	}
	public void setUnInAccountRecharge(String unInAccountRecharge) {
		UnInAccountRecharge = unInAccountRecharge;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
