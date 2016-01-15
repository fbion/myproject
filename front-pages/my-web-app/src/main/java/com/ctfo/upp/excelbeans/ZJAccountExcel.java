package com.ctfo.upp.excelbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class ZJAccountExcel implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "exp(账户编号;NOMAL_ENUM)imp", required = false)
	String insideAccountNo;
	
	@XmlElement(name = "exp(账户名称;NOMAL_ENUM)imp", required = false)
	String ownerLoginName;
	
	@XmlElement(name = "exp(账户总余额;NOMAL_ENUM)imp", required = false)
	String totalBalance;
	
/*	@XmlElement(name = "exp(身份证;NOMAL_ENUM)imp", required = false)
	String partShowIdcardNo;
	
	@XmlElement(name = "exp(手机号;NOMAL_ENUM)imp", required = false)
	String mobileNo;*/
	
	/*@XmlElement(name = "exp(实名状态;NOMAL_ENUM)imp", required = false)
	String authenticaState;
	
	@XmlElement(name = "exp(实名银行卡;NOMAL_ENUM)imp", required = false)
	String bankAccountNo;*/
	
	@XmlElement(name = "exp(账户状态;NOMAL_ENUM)imp", required = false)
	String accountStatus;
	
	@XmlElement(name = "exp(创建时间;NOMAL_ENUM)imp", required = false)
	String createTime;
	
	/*@XmlElement(name = "exp(操作人;NOMAL_ENUM)imp", required = false)
	String operator;*/
	
	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	public String getOwnerLoginName() {
		return ownerLoginName;
	}

	public void setOwnerLoginName(String ownerLoginName) {
		this.ownerLoginName = ownerLoginName;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	/*public String getAuthenticaState() {
		return authenticaState;
	}

	public void setAuthenticaState(String authenticaState) {
		this.authenticaState = authenticaState;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	*/

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	


}
