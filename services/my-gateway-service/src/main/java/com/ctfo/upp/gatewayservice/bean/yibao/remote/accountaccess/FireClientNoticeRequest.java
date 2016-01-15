package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

import java.util.Date;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class FireClientNoticeRequest {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req03_requestId;
	private String req14_timestamp;
	private String hmac;
	public FireClientNoticeRequest() {
		this.command = "FireClientNotice";
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
	public void setReq01_merchantIdentityNumber(
			String req01_merchantIdentityNumber) {
		this.req01_merchantIdentityNumber = req01_merchantIdentityNumber;
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
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
