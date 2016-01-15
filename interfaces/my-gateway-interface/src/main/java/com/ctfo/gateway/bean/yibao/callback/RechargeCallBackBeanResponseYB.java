package com.ctfo.gateway.bean.yibao.callback;

import java.io.Serializable;

public class RechargeCallBackBeanResponseYB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1171031227410870934L;
	//1，表示成功，其他表示失败
	private String flag;
	//订单号
	private String requestId;
	//充值金额
	private String trxAmount;
	//订单完成时间
	private String completeTime;
	//账户通充值交易流水
	private String externalId;
	//错误原因
	private String errorMsg;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(String trxAmount) {
		this.trxAmount = trxAmount;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}
