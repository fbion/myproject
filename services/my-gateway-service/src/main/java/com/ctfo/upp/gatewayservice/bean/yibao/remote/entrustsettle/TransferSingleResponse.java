package com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle;

public class TransferSingleResponse {
	
	private String cmd;
	private String ret_Code;
	private String order_Id;
	private String r1_Code;
	private String bank_Status;
	private String error_Msg;
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
	public String getOrder_Id() {
		return order_Id;
	}
	public void setOrder_Id(String order_Id) {
		this.order_Id = order_Id;
	}
	public String getR1_Code() {
		return r1_Code;
	}
	public void setR1_Code(String r1_Code) {
		this.r1_Code = r1_Code;
	}
	public String getBank_Status() {
		return bank_Status;
	}
	public void setBank_Status(String bank_Status) {
		this.bank_Status = bank_Status;
	}
	public String getError_Msg() {
		return error_Msg;
	}
	public void setError_Msg(String error_Msg) {
		this.error_Msg = error_Msg;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
