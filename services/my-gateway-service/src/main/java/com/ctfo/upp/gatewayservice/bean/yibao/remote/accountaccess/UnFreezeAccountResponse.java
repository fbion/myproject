package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

public class UnFreezeAccountResponse {
	private String command;
	private String resp01_code;
	private String resp02_merchantIdentityNumber;
	private String resp03_userIdentityNumber;
	private String resp04_requestId;
	private String resp05_timestamp;
	private String resp26_accountStatus;
	private String resp27_accountFreezeReason;
	private String resp28_accountFreezeDesc;
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
	public String getResp04_requestId() {
		return resp04_requestId;
	}
	public void setResp04_requestId(String resp04_requestId) {
		this.resp04_requestId = resp04_requestId;
	}
	public String getResp05_timestamp() {
		return resp05_timestamp;
	}
	public void setResp05_timestamp(String resp05_timestamp) {
		this.resp05_timestamp = resp05_timestamp;
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
	public String getResp28_accountFreezeDesc() {
		return resp28_accountFreezeDesc;
	}
	public void setResp28_accountFreezeDesc(String resp28_accountFreezeDesc) {
		this.resp28_accountFreezeDesc = resp28_accountFreezeDesc;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
