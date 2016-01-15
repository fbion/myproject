package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.RequestBean;


public class QueryAccountInfoRequestYB extends RequestBean implements Serializable{
	private String accountNo;
	private String isMerchantBonusAccount;
	private String isMerchantMonitorAccount;
	private String isMerchantClearAccount;
	
	private String desc;
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getIsMerchantBonusAccount() {
		return isMerchantBonusAccount;
	}
	public void setIsMerchantBonusAccount(String isMerchantBonusAccount) {
		this.isMerchantBonusAccount = isMerchantBonusAccount;
	}
	public String getIsMerchantMonitorAccount() {
		return isMerchantMonitorAccount;
	}
	public void setIsMerchantMonitorAccount(String isMerchantMonitorAccount) {
		this.isMerchantMonitorAccount = isMerchantMonitorAccount;
	}
	public String getIsMerchantClearAccount() {
		return isMerchantClearAccount;
	}
	public void setIsMerchantClearAccount(String isMerchantClearAccount) {
		this.isMerchantClearAccount = isMerchantClearAccount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
