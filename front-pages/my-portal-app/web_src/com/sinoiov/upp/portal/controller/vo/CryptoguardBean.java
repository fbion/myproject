package com.sinoiov.upp.portal.controller.vo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CryptoguardBean {
	private String bankCardCode;
	private String bankCardName;
	private String bankCardType;
	private String code;
	private String description;
	private String id;
	private String name;
	private String pid;
	private String sort;
	private String status;
	private String typeCode;
	private String typeName;
	public String getBankCardCode() {
		return bankCardCode;
	}
	public void setBankCardCode(String bankCardCode) {
		this.bankCardCode = bankCardCode;
	}
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public CryptoguardBean() {
	}
	
}
