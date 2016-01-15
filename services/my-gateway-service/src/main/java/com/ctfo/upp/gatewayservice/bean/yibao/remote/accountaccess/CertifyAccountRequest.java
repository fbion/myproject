package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

import java.util.Date;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class CertifyAccountRequest {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req02_userIdentityNumber;
	private String req03_requestId;
	private String req14_timestamp;
	private String req08_userName;
	private String req09_idCardNo;
	private String req11_mobile;
	private String req18_bankCardNumber;
	private String req19_bankCode;
	private String req20_branchName;
	private String req21_province;
	private String req22_city;
	private String req23_idCardEndDate;
	private String req07_desc;
	private String hmac;
	public CertifyAccountRequest() {
		this.command = "FastAuth";
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
	public String getReq08_userName() {
		return req08_userName;
	}
	public void setReq08_userName(String req08_userName) {
		this.req08_userName = req08_userName;
	}
	public String getReq09_idCardNo() {
		return req09_idCardNo;
	}
	public void setReq09_idCardNo(String req09_idCardNo) {
		this.req09_idCardNo = req09_idCardNo;
	}
	public String getReq11_mobile() {
		return req11_mobile;
	}
	public void setReq11_mobile(String req11_mobile) {
		this.req11_mobile = req11_mobile;
	}
	public String getReq18_bankCardNumber() {
		return req18_bankCardNumber;
	}
	public void setReq18_bankCardNumber(String req18_bankCardNumber) {
		this.req18_bankCardNumber = req18_bankCardNumber;
	}
	public String getReq19_bankCode() {
		return req19_bankCode;
	}
	public void setReq19_bankCode(String req19_bankCode) {
		this.req19_bankCode = req19_bankCode;
	}
	public String getReq20_branchName() {
		return req20_branchName;
	}
	public void setReq20_branchName(String req20_branchName) {
		this.req20_branchName = req20_branchName;
	}
	public String getReq21_province() {
		return req21_province;
	}
	public void setReq21_province(String req21_province) {
		this.req21_province = req21_province;
	}
	public String getReq22_city() {
		return req22_city;
	}
	public void setReq22_city(String req22_city) {
		this.req22_city = req22_city;
	}
	public String getReq23_idCardEndDate() {
		return req23_idCardEndDate;
	}
	public void setReq23_idCardEndDate(String req23_idCardEndDate) {
		this.req23_idCardEndDate = req23_idCardEndDate;
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
