package com.ctfo.upp.gatewayservice.bean.mongoDB.log;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Transient;

@Entity(value="TL_YB_log", noClassnameStored=true)
public class YBLog implements Serializable {
	
	@Transient
	private static final long serialVersionUID = -6261747769495108977L;
	
	@Id
	private String id;
	
	private String name;
	
	private String type;
	
	private String requestTime;
	
	private String responseTime;
	
	private String request;
	
	private String response;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
}
