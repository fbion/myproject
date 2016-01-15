package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

public class InAccountCountResponse {
	private String command;
	private String resp01_code;
	private String resp02_merchantIdentityNumber;
	private String resp03_userIdentityNumber;
	private String resp30_balanceAmount;
	private String resp31_trxInAmount;
	private String resp32_trxOutAmount;
	private String resp33_rechargeAmount;
	private String resp34_rechargeBackAmount;
	private String resp35_cashAmount;
	private String resp05_timestamp;
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
	public String getResp30_balanceAmount() {
		return resp30_balanceAmount;
	}
	public void setResp30_balanceAmount(String resp30_balanceAmount) {
		this.resp30_balanceAmount = resp30_balanceAmount;
	}
	public String getResp31_trxInAmount() {
		return resp31_trxInAmount;
	}
	public void setResp31_trxInAmount(String resp31_trxInAmount) {
		this.resp31_trxInAmount = resp31_trxInAmount;
	}
	public String getResp32_trxOutAmount() {
		return resp32_trxOutAmount;
	}
	public void setResp32_trxOutAmount(String resp32_trxOutAmount) {
		this.resp32_trxOutAmount = resp32_trxOutAmount;
	}
	public String getResp33_rechargeAmount() {
		return resp33_rechargeAmount;
	}
	public void setResp33_rechargeAmount(String resp33_rechargeAmount) {
		this.resp33_rechargeAmount = resp33_rechargeAmount;
	}
	public String getResp34_rechargeBackAmount() {
		return resp34_rechargeBackAmount;
	}
	public void setResp34_rechargeBackAmount(String resp34_rechargeBackAmount) {
		this.resp34_rechargeBackAmount = resp34_rechargeBackAmount;
	}
	public String getResp35_cashAmount() {
		return resp35_cashAmount;
	}
	public void setResp35_cashAmount(String resp35_cashAmount) {
		this.resp35_cashAmount = resp35_cashAmount;
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
}
