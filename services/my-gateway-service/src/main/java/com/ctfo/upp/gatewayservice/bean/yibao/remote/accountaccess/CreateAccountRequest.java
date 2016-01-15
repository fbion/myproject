package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

import java.util.Date;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;


public class CreateAccountRequest {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req02_userIdentityNumber;
	private String req03_requestId;
	private String req08_userName;
	private String req11_mobile;
	private String req10_idCardType;
	private String req09_idCardNo;
	private String req50_authStatus;
	private String req13_trxPsd;
	private String req07_desc;
	private String req14_timestamp;
	private String hmac;
	public CreateAccountRequest() {
		this.command = "InitializeAccount";
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
	public String getReq08_userName() {
		return req08_userName;
	}
	public void setReq08_userName(String req08_userName) {
		this.req08_userName = req08_userName;
	}
	public String getReq11_mobile() {
		return req11_mobile;
	}
	public void setReq11_mobile(String req11_mobile) {
		this.req11_mobile = req11_mobile;
	}
	public String getReq10_idCardType() {
		return req10_idCardType;
	}
	public void setReq10_idCardType(String req10_idCardType) {
		this.req10_idCardType = req10_idCardType;
	}
	public String getReq09_idCardNo() {
		return req09_idCardNo;
	}
	public void setReq09_idCardNo(String req09_idCardNo) {
		this.req09_idCardNo = req09_idCardNo;
	}
	public String getReq50_authStatus() {
		return req50_authStatus;
	}
	public void setReq50_authStatus(String req50_authStatus) {
		this.req50_authStatus = req50_authStatus;
	}
	public String getReq13_trxPsd() {
		return req13_trxPsd;
	}
	public void setReq13_trxPsd(String req13_trxPsd) {
		this.req13_trxPsd = req13_trxPsd;
	}
	public String getReq07_desc() {
		return req07_desc;
	}
	public void setReq07_desc(String req07_desc) {
		this.req07_desc = req07_desc;
	}
	public String getReq14_timestamp() {
		return req14_timestamp;
	}
	public void setReq14_timestamp(String req14_timestamp) {
		this.req14_timestamp = req14_timestamp;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
