package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

import java.util.Date;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class BankCardRechargeRequest {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req02_userIdentityNumber;
	private String req03_requestId;
	private String req04_amount;
	private String req43_platFee;
	private String req05_channel;
	private String req06_cardType;
	private String req30_frpChannel;
	private String req36_callBackUrl;
	private String req31_clientType;
	private String req32_platTrxDate;
	private String req14_timestamp;
	private String req07_desc;
	private String hmac;
	public BankCardRechargeRequest() {
		this.command = "Pay";
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
	public String getReq04_amount() {
		return req04_amount;
	}
	public void setReq04_amount(String req04_amount) {
		this.req04_amount = req04_amount;
	}
	public String getReq43_platFee() {
		return req43_platFee;
	}
	public void setReq43_platFee(String req43_platFee) {
		this.req43_platFee = req43_platFee;
	}
	public String getReq05_channel() {
		return req05_channel;
	}
	public void setReq05_channel(String req05_channel) {
		this.req05_channel = req05_channel;
	}
	public String getReq06_cardType() {
		return req06_cardType;
	}
	public void setReq06_cardType(String req06_cardType) {
		this.req06_cardType = req06_cardType;
	}
	public String getReq30_frpChannel() {
		return req30_frpChannel;
	}
	public void setReq30_frpChannel(String req30_frpChannel) {
		this.req30_frpChannel = req30_frpChannel;
	}
	public String getReq36_callBackUrl() {
		return req36_callBackUrl;
	}
	public void setReq36_callBackUrl(String req36_callBackUrl) {
		this.req36_callBackUrl = req36_callBackUrl;
	}
	public String getReq31_clientType() {
		return req31_clientType;
	}
	public void setReq31_clientType(String req31_clientType) {
		this.req31_clientType = req31_clientType;
	}
	public String getReq32_platTrxDate() {
		return req32_platTrxDate;
	}
	public void setReq32_platTrxDate(String req32_platTrxDate) {
		this.req32_platTrxDate = req32_platTrxDate;
	}
	public String getReq14_timestamp() {
		return req14_timestamp;
	}
	public void setReq14_timestamp(String req14_timestamp) {
		this.req14_timestamp = req14_timestamp;
	}
	public String getReq07_desc() {
		return req07_desc;
	}
	public void setReq07_desc(String req07_desc) {
		this.req07_desc = req07_desc;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
