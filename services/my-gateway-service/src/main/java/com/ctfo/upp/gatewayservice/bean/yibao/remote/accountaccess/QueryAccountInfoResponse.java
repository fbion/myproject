package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

public class QueryAccountInfoResponse {
	private String command;
	private String resp01_code;
	private String resp02_merchantIdentityNumber;
	private String resp03_userIdentityNumber;
	private String resp05_timestamp;
	private String resp06_desc;
	private String resp07_userName;
	private String resp08_mobile;
	private String resp10_merchantBonusAccount;
	private String resp11_merchantMonitorAccount;
	private String resp17_merchantClearAccount;
	private String resp12_totalBalance;
	private String resp13_cashableBalance;
	private String resp14_noncashableBalance;
	private String resp26_accountStatus;
	private String resp27_accountFreezeReason;
	private String hmac;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getResp01_code() {
		return resp01_code;
	}
	public void setResp01_code(String resp01_code) {
		this.resp01_code = resp01_code;
	}
	public String getResp02_merchantIdentityNumber() {
		return resp02_merchantIdentityNumber;
	}
	public void setResp02_merchantIdentityNumber(
			String resp02_merchantIdentityNumber) {
		this.resp02_merchantIdentityNumber = resp02_merchantIdentityNumber;
	}
	public String getResp03_userIdentityNumber() {
		return resp03_userIdentityNumber;
	}
	public void setResp03_userIdentityNumber(String resp03_userIdentityNumber) {
		this.resp03_userIdentityNumber = resp03_userIdentityNumber;
	}
	public String getResp05_timestamp() {
		return resp05_timestamp;
	}
	public void setResp05_timestamp(String resp05_timestamp) {
		this.resp05_timestamp = resp05_timestamp;
	}
	public String getResp06_desc() {
		return resp06_desc;
	}
	public void setResp06_desc(String resp06_desc) {
		this.resp06_desc = resp06_desc;
	}
	public String getResp07_userName() {
		return resp07_userName;
	}
	public void setResp07_userName(String resp07_userName) {
		this.resp07_userName = resp07_userName;
	}
	public String getResp08_mobile() {
		return resp08_mobile;
	}
	public void setResp08_mobile(String resp08_mobile) {
		this.resp08_mobile = resp08_mobile;
	}
	public String getResp10_merchantBonusAccount() {
		return resp10_merchantBonusAccount;
	}
	public void setResp10_merchantBonusAccount(String resp10_merchantBonusAccount) {
		this.resp10_merchantBonusAccount = resp10_merchantBonusAccount;
	}
	public String getResp11_merchantMonitorAccount() {
		return resp11_merchantMonitorAccount;
	}
	public void setResp11_merchantMonitorAccount(
			String resp11_merchantMonitorAccount) {
		this.resp11_merchantMonitorAccount = resp11_merchantMonitorAccount;
	}
	public String getResp17_merchantClearAccount() {
		return resp17_merchantClearAccount;
	}
	public void setResp17_merchantClearAccount(String resp17_merchantClearAccount) {
		this.resp17_merchantClearAccount = resp17_merchantClearAccount;
	}
	public String getResp12_totalBalance() {
		return resp12_totalBalance;
	}
	public void setResp12_totalBalance(String resp12_totalBalance) {
		this.resp12_totalBalance = resp12_totalBalance;
	}
	public String getResp13_cashableBalance() {
		return resp13_cashableBalance;
	}
	public void setResp13_cashableBalance(String resp13_cashableBalance) {
		this.resp13_cashableBalance = resp13_cashableBalance;
	}
	public String getResp14_noncashableBalance() {
		return resp14_noncashableBalance;
	}
	public void setResp14_noncashableBalance(String resp14_noncashableBalance) {
		this.resp14_noncashableBalance = resp14_noncashableBalance;
	}
	public String getResp26_accountStatus() {
		return resp26_accountStatus;
	}
	public void setResp26_accountStatus(String resp26_accountStatus) {
		this.resp26_accountStatus = resp26_accountStatus;
	}
	public String getResp27_accountFreezeReason() {
		return resp27_accountFreezeReason;
	}
	public void setResp27_accountFreezeReason(String resp27_accountFreezeReason) {
		this.resp27_accountFreezeReason = resp27_accountFreezeReason;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
