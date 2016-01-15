package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

import java.util.Date;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class UnFreezeAccountRequest {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req02_userIdentityNumber;
	private String req03_requestId;
	private String req14_timestamp;
	private String req27_accountFreezeReason;
	private String req28_accountFreezeDesc;
	private String hmac;
	public UnFreezeAccountRequest() {
		this.command = "UnfreezeAccount";
		this.req01_merchantIdentityNumber = ConfigUtil.getValue("YB_ACCOUNT_ACCESS_MER_ID");
		this.req14_timestamp = String.valueOf((new Date().getTime()));
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getReq01_merchantIdentityNumber() {
		return req01_merchantIdentityNumber;
	}
	public void setReq01_merchantIdentityNumber(String req01_merchantIdentityNumber) {
		this.req01_merchantIdentityNumber = req01_merchantIdentityNumber;
	}
	public String getReq02_userIdentityNumber() {
		return req02_userIdentityNumber;
	}
	public void setReq02_userIdentityNumber(String req02_userIdentityNumber) {
		this.req02_userIdentityNumber = req02_userIdentityNumber;
	}
	public String getReq03_requestId() {
		return req03_requestId;
	}
	public void setReq03_requestId(String req03_requestId) {
		this.req03_requestId = req03_requestId;
	}
	public String getReq14_timestamp() {
		return req14_timestamp;
	}
	public void setReq14_timestamp(String req14_timestamp) {
		this.req14_timestamp = req14_timestamp;
	}
	public String getReq27_accountFreezeReason() {
		return req27_accountFreezeReason;
	}
	public void setReq27_accountFreezeReason(String req27_accountFreezeReason) {
		this.req27_accountFreezeReason = req27_accountFreezeReason;
	}
	public String getReq28_accountFreezeDesc() {
		return req28_accountFreezeDesc;
	}
	public void setReq28_accountFreezeDesc(String req28_accountFreezeDesc) {
		this.req28_accountFreezeDesc = req28_accountFreezeDesc;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
