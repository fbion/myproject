package com.ctfo.upp.excelbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class MerchantExcel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="exp(商户名称;NOMAL_ENUM)imp", required=false)
	String storeName;
	
	@XmlElement(name="exp(商户编号;NOMAL_ENUM)imp", required=false)
	String storeCode;
	
	@XmlElement(name="exp(商户类型;MAP_ENUM)imp", required=false)
	String storeType;
	
	@XmlElement(name="exp(联系人;NOMAL_ENUM)imp", required=false)
	String contactUser;
	
	@XmlElement(name="exp(联系电话;NOMAL_ENUM)imp", required=false)
	String contactPhone;
	
	@XmlElement(name="exp(状态;MAP_ENUM)imp", required=false)
	String storeStatus;
	
	@XmlElement(name="exp(操作人;NOMAL_ENUM)imp", required=false)
	String opUser;
	
	@XmlElement(name="exp(接入时间;MAP_ENUM)imp", required=false)
	String regTime;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	
	

}
