package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.RequestBean;

public class RechargeResultQueryRequestYB  extends RequestBean implements Serializable{
	
	

	private String command;
	private String req01_merchantIdentityNumber;
	private String req03_requestId;
	private String req14_timestamp;
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getReq01_merchantIdentityNumber() {
		return req01_merchantIdentityNumber;
	}
	public void setReq01_merchantIdentityNumber(String req01_merchantIdentityNumber) {
		this.req01_merchantIdentityNumber = req01_merchantIdentityNumber;
	}
	public String getReq03_requestId() {
		return req03_requestId;
	}
	public void setReq03_requestId(String req03_requestId) {
		this.req03_requestId = req03_requestId;
	}
	public String getReq14_timestamp() {
		return req14_timestamp;
	}
	public void setReq14_timestamp(String req14_timestamp) {
		this.req14_timestamp = req14_timestamp;
	}
	
	

}
