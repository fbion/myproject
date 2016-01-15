package com.ctfo.gateway.bean.yibao.callback;

import java.io.Serializable;

public class WithdrawCallBackBeanResponseYB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6575873946703630754L;
	//订单号
	private String requestId;
	//提现金额
	private String amount;
	//1表示成功，其他表示失败
	private String flag;
	//失败原因
	private String errorMsg;
	//时间
	private String time;
	//1:普通提现，2：快速提现
	private String type;
	
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
