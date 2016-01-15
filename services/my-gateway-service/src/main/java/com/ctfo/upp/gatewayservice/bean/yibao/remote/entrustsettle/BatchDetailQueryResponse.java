package com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle;

public class BatchDetailQueryResponse {
	
	private String cmd;
	private String ret_Code;
	private String error_msg;
	private String batch_No;
	private String total_Num;
	private String end_Flag;
	private String hmac;
	private BatchDetailQueryChildResponse list;
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
	public String getBatch_No() {
		return batch_No;
	}
	public void setBatch_No(String batch_No) {
		this.batch_No = batch_No;
	}
	public String getTotal_Num() {
		return total_Num;
	}
	public void setTotal_Num(String total_Num) {
		this.total_Num = total_Num;
	}
	public String getEnd_Flag() {
		return end_Flag;
	}
	public void setEnd_Flag(String end_Flag) {
		this.end_Flag = end_Flag;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public BatchDetailQueryChildResponse getList() {
		return list;
	}
	public void setList(BatchDetailQueryChildResponse list) {
		this.list = list;
	}
}
