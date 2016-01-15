package com.ctfo.upp.excelbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class ManageAccountExcel implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "exp(会员编号;NOMAL_ENUM)imp", required = false)
	String ownerUserNo;
	
	@XmlElement(name = "exp(账户名称;NOMAL_ENUM)imp", required = false)
	String insideAccountNo;
	
/*	@XmlElement(name = "exp(账户所属;NOMAL_ENUM)imp", required = false)
	String ownerLoginName;*/
	
	@XmlElement(name = "exp(账户类型;NOMAL_ENUM)imp", required = false)
	String accountType;
	
	@XmlElement(name = "exp(账户总余额;NOMAL_ENUM)imp", required = false)
	String totalBalance;
	
	@XmlElement(name = "exp(可用余额;NOMAL_ENUM)imp", required = false)
	String usableBalance;
	
	@XmlElement(name = "exp(冻结余额;NOMAL_ENUM)imp", required = false)
	String frozenBalance;
	
/*	@XmlElement(name = "exp(开通渠道;NOMAL_ENUM)imp", required = false)
	String countryCode;*/
	
	/*@XmlElement(name = "exp(身份证;NOMAL_ENUM)imp", required = false)
	String partShowIdcardNo;*/
	
	@XmlElement(name = "exp(手机号;NOMAL_ENUM)imp", required = false)
	String mobileNo;
	
	/*@XmlElement(name = "exp(实名状态;NOMAL_ENUM)imp", required = false)
	String authenticaState;*/
	
	@XmlElement(name = "exp(账户状态;NOMAL_ENUM)imp", required = false)
	String accountStatus;
	
	@XmlElement(name = "exp(创建时间;NOMAL_ENUM)imp", required = false)
	String createTime;

	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

/*	public String getOwnerLoginName() {
		return ownerLoginName;
	}

	public void setOwnerLoginName(String ownerLoginName) {
		this.ownerLoginName = ownerLoginName;
	}*/

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getUsableBalance() {
		return usableBalance;
	}

	public void setUsableBalance(String usableBalance) {
		this.usableBalance = usableBalance;
	}

	public String getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(String frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

/*	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}*/

	/*public String getPartShowIdcardNo() {
		return partShowIdcardNo;
	}

	public void setPartShowIdcardNo(String partShowIdcardNo) {
		this.partShowIdcardNo = partShowIdcardNo;
	}*/

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/*public String getAuthenticaState() {
		return authenticaState;
	}

	public void setAuthenticaState(String authenticaState) {
		this.authenticaState = authenticaState;
	}*/

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

	public String getOwnerUserNo() {
		return ownerUserNo;
	}

	public void setOwnerUserNo(String ownerUserNo) {
		this.ownerUserNo = ownerUserNo;
	}
	
}
