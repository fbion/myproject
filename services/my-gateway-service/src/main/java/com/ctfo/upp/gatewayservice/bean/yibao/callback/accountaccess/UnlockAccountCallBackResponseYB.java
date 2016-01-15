package com.ctfo.upp.gatewayservice.bean.yibao.callback.accountaccess;

import com.ctfo.gateway.bean.yibao.callback.UnlockAccountCallBackBeanResponseYB;


public class UnlockAccountCallBackResponseYB {
	private String command;
	private String zhttimestamp;
	private String userNumber;
	private String hmac;
	public UnlockAccountCallBackBeanResponseYB toBeanResponse() throws Exception{
		UnlockAccountCallBackBeanResponseYB unfreezeAccountCallBackBeanResponse = new UnlockAccountCallBackBeanResponseYB();
		unfreezeAccountCallBackBeanResponse.setAccountId(userNumber);
		return unfreezeAccountCallBackBeanResponse;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getZhttimestamp() {
		return zhttimestamp;
	}
	public void setZhttimestamp(String zhttimestamp) {
		this.zhttimestamp = zhttimestamp;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
