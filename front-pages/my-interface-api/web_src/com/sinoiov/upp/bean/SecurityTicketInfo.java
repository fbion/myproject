package com.sinoiov.upp.bean;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 安全密保用到的ticket信息
 * 
 * @author sunchuanfu
 */
@Entity(value = "SecurityTicketInfo", noClassnameStored = true)
public class SecurityTicketInfo implements Serializable {
	private static final long serialVersionUID = 7031747964315757788L;
	@Id
	private String id;

	private String accountNo;

	private String securityTicket;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSecurityTicket() {
		return securityTicket;
	}

	public void setSecurityTicket(String securityTicket) {
		this.securityTicket = securityTicket;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

}
