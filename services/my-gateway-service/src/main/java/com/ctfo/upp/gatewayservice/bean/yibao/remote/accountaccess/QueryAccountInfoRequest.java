package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class QueryAccountInfoRequest {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req02_userIdentityNumber;
	private String req07_desc;
	private String req14_timestamp;
	private String req15_merchantBonusAccount;
	private String req16_merchantMonitorAccount;
	private String req24_merchantClearAccount;
	private String hmac;
	
	
	
	
	public QueryAccountInfoRequest() {
		super();
		this.command = "QueryAccount";
		this.req01_merchantIdentityNumber = ConfigUtil.getValue("YB_ACCOUNT_ACCESS_MER_ID");
		this.req14_timestamp = String.valueOf(System.currentTimeMillis());
		
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
	public String getReq15_merchantBonusAccount() {
		return req15_merchantBonusAccount;
	}
	public void setReq15_merchantBonusAccount(String req15_merchantBonusAccount) {
		this.req15_merchantBonusAccount = req15_merchantBonusAccount;
	}
	public String getReq16_merchantMonitorAccount() {
		return req16_merchantMonitorAccount;
	}
	public void setReq16_merchantMonitorAccount(String req16_merchantMonitorAccount) {
		this.req16_merchantMonitorAccount = req16_merchantMonitorAccount;
	}
	public String getReq24_merchantClearAccount() {
		return req24_merchantClearAccount;
	}
	public void setReq24_merchantClearAccount(String req24_merchantClearAccount) {
		this.req24_merchantClearAccount = req24_merchantClearAccount;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
