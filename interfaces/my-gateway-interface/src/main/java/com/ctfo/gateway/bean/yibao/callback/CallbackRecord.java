package com.ctfo.gateway.bean.yibao.callback;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class CallbackRecord extends ResponseBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -784961788016416027L;

	private String type;
	
	private CallBackBean callBackBean;
	
	private String taskId;
	
	private String callBackUrl;

	

	public CallBackBean getCallBackBean() {
		return callBackBean;
	}

	public void setCallBackBean(CallBackBean callBackBean) {
		this.callBackBean = callBackBean;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
}
