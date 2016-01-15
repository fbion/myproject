package com.ctfo.upp.gatewayservice.bean.yibao.callback.entrustsettle;

import com.ctfo.gateway.bean.yibao.callback.WithdrawCallBackBeanResponseYB;

public class TransferNotifyRequest {
	
	private String cmd;
	private String version;
	private String group_Id;
	private String mer_Id;
	private String batch_No;
	private String order_Id;
	private String status;
	private String message;
	private String hmac;
	public WithdrawCallBackBeanResponseYB toBeanResponse() throws Exception{
		WithdrawCallBackBeanResponseYB response = new WithdrawCallBackBeanResponseYB();
		response.setErrorMsg(this.message);
		response.setFlag((this.status.equals("S")) ? "1" : "0");
		response.setRequestId(this.order_Id);
		response.setType("2");
		return response;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getGroup_Id() {
		return group_Id;
	}
	public void setGroup_Id(String group_Id) {
		this.group_Id = group_Id;
	}
	public String getMer_Id() {
		return mer_Id;
	}
	public void setMer_Id(String mer_Id) {
		this.mer_Id = mer_Id;
	}
	public String getBatch_No() {
		return batch_No;
	}
	public void setBatch_No(String batch_No) {
		this.batch_No = batch_No;
	}
	public String getOrder_Id() {
		return order_Id;
	}
	public void setOrder_Id(String order_Id) {
		this.order_Id = order_Id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
