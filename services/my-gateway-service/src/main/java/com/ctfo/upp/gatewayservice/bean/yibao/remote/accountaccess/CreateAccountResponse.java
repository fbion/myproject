package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

public class CreateAccountResponse {
	private String command;
	private String resp01_code;
	private String resp02_merchantIdentityNumber;
	private String resp03_userIdentityNumber;
	private String resp04_requestId;
	private String resp05_timestamp;
	private String resp06_desc;
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
	public String getResp06_desc() {
		return resp06_desc;
	}
	public void setResp06_desc(String resp06_desc) {
		this.resp06_desc = resp06_desc;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
