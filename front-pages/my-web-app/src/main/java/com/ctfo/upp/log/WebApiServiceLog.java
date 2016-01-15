package com.ctfo.upp.log;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


@Entity(value="TL_webapi_service_log", noClassnameStored=true)
public class WebApiServiceLog implements Serializable{
	private static final long serialVersionUID = -2133056682133050669L;
	@Id
	private String id;
	/**
	 * 渠道编码
	 */
	private String channelCode;
	/**
	 * 操作类型
	 */
	private String action;
	/**
	 * 请求XML
	 */
	private String request;
	/**
	 * 响应XML
	 */
	private String response;
	/**
	 * 处理时间
	 */
	private String handleTime;
	/**
	 * 结果状态;success/failed
	 */
	private String state;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
