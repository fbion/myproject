package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class AccountCountBeanResponseYB extends ResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8219569344933778090L;
	
	//账户ID
	private String accountId;
	//余额
	private String balanceAmount;
	//交易入款
	private String trxInAmount;
	//交易出款
	private String trxOutAmount;
	//充值金额
	private String rechargeAmount;
	//回退金额
	private String rechargeBackAmount;
	//体现金额
	private String withdrawAmount;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getTrxInAmount() {
		return trxInAmount;
	}
	public void setTrxInAmount(String trxInAmount) {
		this.trxInAmount = trxInAmount;
	}
	public String getTrxOutAmount() {
		return trxOutAmount;
	}
	public void setTrxOutAmount(String trxOutAmount) {
		this.trxOutAmount = trxOutAmount;
	}
	public String getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getRechargeBackAmount() {
		return rechargeBackAmount;
	}
	public void setRechargeBackAmount(String rechargeBackAmount) {
		this.rechargeBackAmount = rechargeBackAmount;
	}
	public String getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
}
