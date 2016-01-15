package com.ctfo.gateway.bean.yibao.callback;
import java.io.Serializable;



public class CallBackBean implements Serializable{
	private String completeTime;
	private String errorMsg;
	private String externalId;
	private String flag;
	private String requestId;
	private String trxAmount;
	
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
	
}
