package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

public class QueryRechargeInfoResponse {
	private String command;
	private String resp01_code;
	private String resp02_merchantIdentityNumber;
	private String resp03_userIdentityNumber;
	private String resp04_requestId;
	private String resp29_externalId;
	private String resp31_trxInAmount;
	private String resp36_orderStatus;
	private String resp30_balanceAmount;
	private String resp38_balanceAct;
	private String resp05_timestamp;
	private String resp40_rechargeChannel;
	private String resp41_frpChannel;
	private String resp42_completeTime;
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
	public String getResp31_trxInAmount() {
		return resp31_trxInAmount;
	}
	public void setResp31_trxInAmount(String resp31_trxInAmount) {
		this.resp31_trxInAmount = resp31_trxInAmount;
	}
	public String getResp36_orderStatus() {
		return resp36_orderStatus;
	}
	public void setResp36_orderStatus(String resp36_orderStatus) {
		this.resp36_orderStatus = resp36_orderStatus;
	}
	public String getResp30_balanceAmount() {
		return resp30_balanceAmount;
	}
	public void setResp30_balanceAmount(String resp30_balanceAmount) {
		this.resp30_balanceAmount = resp30_balanceAmount;
	}
	public String getResp38_balanceAct() {
		return resp38_balanceAct;
	}
	public void setResp38_balanceAct(String resp38_balanceAct) {
		this.resp38_balanceAct = resp38_balanceAct;
	}
	public String getResp05_timestamp() {
		return resp05_timestamp;
	}
	public void setResp05_timestamp(String resp05_timestamp) {
		this.resp05_timestamp = resp05_timestamp;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public String getResp29_externalId() {
		return resp29_externalId;
	}
	public void setResp29_externalId(String resp29_externalId) {
		this.resp29_externalId = resp29_externalId;
	}
	public String getResp40_rechargeChannel() {
		return resp40_rechargeChannel;
	}
	public void setResp40_rechargeChannel(String resp40_rechargeChannel) {
		this.resp40_rechargeChannel = resp40_rechargeChannel;
	}
	public String getResp41_frpChannel() {
		return resp41_frpChannel;
	}
	public void setResp41_frpChannel(String resp41_frpChannel) {
		this.resp41_frpChannel = resp41_frpChannel;
	}
	public String getResp42_completeTime() {
		return resp42_completeTime;
	}
	public void setResp42_completeTime(String resp42_completeTime) {
		this.resp42_completeTime = resp42_completeTime;
	}
}
