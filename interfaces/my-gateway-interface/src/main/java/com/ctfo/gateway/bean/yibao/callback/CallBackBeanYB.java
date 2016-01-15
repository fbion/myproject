package com.ctfo.gateway.bean.yibao.callback;

import java.io.Serializable;

public class CallBackBeanYB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -784961784016416027L;

	private String type;
	
	private Object callBackBean;
	
	private String taskId;
	
	private String callBackUrl;

	public Object getCallBackBean() {
		return callBackBean;
	}

	public void setCallBackBean(Object callBackBean) {
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
