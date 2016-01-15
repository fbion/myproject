package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class QueryAccountInfoResponseYB extends ResponseBean implements Serializable{
	private String command;
	private String code;
	private String merchantIdentityNumber;
	private String accountNo;
	private String timestamp;
	private String desc;
	private String userName;
	private String mobile;
	private String merchantBonusAccount;
	private String merchantMonitorAccount;
	private String merchantClearAccount;
	private String totalBalance;				//总金额
	private String cashableBalance;				//可提现余额
	private String noncashableBalance;			//不可提现余额
	private String accountStatus;
	private String accountFreezeReason;
	private String hmac;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMerchantIdentityNumber() {
		return merchantIdentityNumber;
	}
	public void setMerchantIdentityNumber(String merchantIdentityNumber) {
		this.merchantIdentityNumber = merchantIdentityNumber;
	}

	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
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
	public String getMerchantBonusAccount() {
		return merchantBonusAccount;
	}
	public void setMerchantBonusAccount(String merchantBonusAccount) {
		this.merchantBonusAccount = merchantBonusAccount;
	}
	public String getMerchantMonitorAccount() {
		return merchantMonitorAccount;
	}
	public void setMerchantMonitorAccount(String merchantMonitorAccount) {
		this.merchantMonitorAccount = merchantMonitorAccount;
	}
	public String getMerchantClearAccount() {
		return merchantClearAccount;
	}
	public void setMerchantClearAccount(String merchantClearAccount) {
		this.merchantClearAccount = merchantClearAccount;
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
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
