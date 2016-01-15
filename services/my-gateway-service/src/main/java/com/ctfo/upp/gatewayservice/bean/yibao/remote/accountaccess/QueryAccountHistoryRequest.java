package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class QueryAccountHistoryRequest {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req02_userIdentityNumber;
	private String req51_startPoint;
	private String req52_endPoint;
	private String req53_accountType;
	private String hmac;
	
	
	public QueryAccountHistoryRequest() {
		super();
		
		this.command = "QueryAccountHistory";
		this.req01_merchantIdentityNumber = ConfigUtil.getValue("YB_ACCOUNT_ACCESS_MER_ID");
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
	public String getReq51_startPoint() {
		return req51_startPoint;
	}
	public void setReq51_startPoint(String req51_startPoint) {
		this.req51_startPoint = req51_startPoint;
	}
	public String getReq52_endPoint() {
		return req52_endPoint;
	}
	public void setReq52_endPoint(String req52_endPoint) {
		this.req52_endPoint = req52_endPoint;
	}
	public String getReq53_accountType() {
		return req53_accountType;
	}
	public void setReq53_accountType(String req53_accountType) {
		this.req53_accountType = req53_accountType;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
