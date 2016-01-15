package com.ctfo.upp.gatewayservice.bean.yibao.callback.accountaccess;

import com.ctfo.gateway.bean.yibao.callback.WithdrawCallBackBeanResponseYB;

public class WithdrawCallBackResponseYB {
	private String command;
	private String requestId;
	private String amount;
	private String result;
	private String errorMsg;
	private String zhttimestamp;
	private String hmac;
	public WithdrawCallBackBeanResponseYB toBeanResponse() throws Exception{
		WithdrawCallBackBeanResponseYB response = new WithdrawCallBackBeanResponseYB();
		response.setAmount(this.amount);
		response.setErrorMsg(this.errorMsg);
		response.setFlag(this.result);
		response.setRequestId(this.requestId);
		response.setTime(this.zhttimestamp);
		response.setType("1");;
		return response;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getZhttimestamp() {
		return zhttimestamp;
	}
	public void setZhttimestamp(String zhttimestamp) {
		this.zhttimestamp = zhttimestamp;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
