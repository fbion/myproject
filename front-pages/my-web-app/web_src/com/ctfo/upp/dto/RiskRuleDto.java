package com.ctfo.upp.dto;

import java.io.Serializable;

public class RiskRuleDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String no;
	
	private String riskItem;

	private String createTime;
	
	private String creator;
	
	private String status;
	
	private String type; //1 ip; 2 fre; 3 amount; 4 account
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getRiskItem() {
		return riskItem;
	}

	public void setRiskItem(String riskItem) {
		this.riskItem = riskItem;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RiskRuleDto [riskItem=" + riskItem + ", createTime="
				+ createTime + ", creator=" + creator + ", status=" + status
				+ ", type=" + type + "]";
	}
}
