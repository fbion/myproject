package com.ctfo.upp.gatewayservice.bean.mongoDB.log;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Transient;

@Entity(value="TL_Callback_To_Business_log", noClassnameStored=true)
public class CallBackToBusinessLog implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -3177070588035352380L;
	
	@Id
	private String id;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 时间，毫秒值
	 */
	private String time;
	/**
	 * 状态，0初始，2表示成功，1表示失败
	 */
	private String status;
	/**
	 * 通知的参数
	 */
	private String callBackParam;

	public String getCallBackParam() {
		return callBackParam;
	}

	public void setCallBackParam(String callBackParam) {
		this.callBackParam = callBackParam;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
