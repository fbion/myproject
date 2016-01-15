package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

public class SyncAccountResponse {
	private String command;
	private String resp01_code;
	private String resp02_merchantIdentityNumber;
	private String resp04_requestId;
	private String resp05_timestamp;
	private String resp39_errorRecordId;
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
	public String getResp02_merchantIdentityNumber() {
		return resp02_merchantIdentityNumber;
	}
	public void setResp02_merchantIdentityNumber(
			String resp02_merchantIdentityNumber) {
		this.resp02_merchantIdentityNumber = resp02_merchantIdentityNumber;
	}
	public String getResp39_errorRecordId() {
		return resp39_errorRecordId;
	}
	public void setResp39_errorRecordId(String resp39_errorRecordId) {
		this.resp39_errorRecordId = resp39_errorRecordId;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
