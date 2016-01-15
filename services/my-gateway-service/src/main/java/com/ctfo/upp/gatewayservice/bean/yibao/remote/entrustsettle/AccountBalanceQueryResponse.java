package com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle;

public class AccountBalanceQueryResponse {
	
	private String cmd;
	private String ret_Code;
	private String error_msg;
	private String balance_Amount;
	private String valid_Amount;
	private String hmac;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getRet_Code() {
		return ret_Code;
	}
	public void setRet_Code(String ret_Code) {
		this.ret_Code = ret_Code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getBalance_Amount() {
		return balance_Amount;
	}
	public void setBalance_Amount(String balance_Amount) {
		this.balance_Amount = balance_Amount;
	}
	public String getValid_Amount() {
		return valid_Amount;
	}
	public void setValid_Amount(String valid_Amount) {
		this.valid_Amount = valid_Amount;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
