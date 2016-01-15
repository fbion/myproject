package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class AccountInfoBeanResponseYB extends ResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4272324083111775124L;
	
	//账户ID
	private String accountId;
	//用户姓名
	private String userName;
	//用户手机号
	private String mobile;
	//账户余额
	private String totalBalance;
	//可提现余额
	private String cashableBalance;
	//不可提现余额
	private String noncashableBalance;
	//账户状态
	private String accountStatus;
	//冻结原因
	private String accountFreezeReason;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}
	public String getCashableBalance() {
		return cashableBalance;
	}
	public void setCashableBalance(String cashableBalance) {
		this.cashableBalance = cashableBalance;
	}
	public String getNoncashableBalance() {
		return noncashableBalance;
	}
	public void setNoncashableBalance(String noncashableBalance) {
		this.noncashableBalance = noncashableBalance;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountFreezeReason() {
		return accountFreezeReason;
	}
	public void setAccountFreezeReason(String accountFreezeReason) {
		this.accountFreezeReason = accountFreezeReason;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
