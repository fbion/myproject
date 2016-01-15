package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.upp.root.utils.EnvironmentUtil;

public class QueryAccountHistoryRequestYB extends RequestBean implements Serializable {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req02_userIdentityNumber;
	private String req51_startPoint;
	private String req52_endPoint;
	private String req53_accountType;	
	
	public QueryAccountHistoryRequestYB() {
		super();
	}
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
	public String getReq02_userIdentityNumber() {
		return req02_userIdentityNumber;
	}
	public void setReq02_userIdentityNumber(String req02_userIdentityNumber) {
		this.req02_userIdentityNumber = req02_userIdentityNumber;
	}
	public String getReq51_startPoint() {
		return req51_startPoint;
	}
	public void setReq51_startPoint(String req51_startPoint) {
		this.req51_startPoint = req51_startPoint;
	}
	public String getReq52_endPoint() {
		return req52_endPoint;
	}
	public void setReq52_endPoint(String req52_endPoint) {
		this.req52_endPoint = req52_endPoint;
	}
	public String getReq53_accountType() {
		return req53_accountType;
	}
	public void setReq53_accountType(String req53_accountType) {
		this.req53_accountType = req53_accountType;
	}
}
