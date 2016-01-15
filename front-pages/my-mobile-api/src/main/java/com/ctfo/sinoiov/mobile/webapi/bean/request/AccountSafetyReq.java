package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 账户安全reqest
 * 
 * @author sunchuanfu
 */
public class AccountSafetyReq extends BaseBeanReq {
	private static final long serialVersionUID = -319537727204305136L;
	// 对应内部账户ID
	private String accountNo;
	// 安全票据
	private String securityTicket;
	// 安全问题1
	private String securityQuestion1;
	// 安全问题2
	private String securityQuestion2;
	// 安全问题3
	private String securityQuestion3;
	// 答案1
	private String securityAnswer1;
	// 答案2
	private String securityAnswer2;
	// 答案3
	private String securityAnswer3;
	// 备注
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSecurityQuestion1() {
		return securityQuestion1;
	}

	public void setSecurityQuestion1(String securityQuestion1) {
		this.securityQuestion1 = securityQuestion1;
	}

	public String getSecurityQuestion2() {
		return securityQuestion2;
	}

	public void setSecurityQuestion2(String securityQuestion2) {
		this.securityQuestion2 = securityQuestion2;
	}

	public String getSecurityQuestion3() {
		return securityQuestion3;
	}

	public void setSecurityQuestion3(String securityQuestion3) {
		this.securityQuestion3 = securityQuestion3;
	}

	public String getSecurityAnswer1() {
		return securityAnswer1;
	}

	public void setSecurityAnswer1(String securityAnswer1) {
		this.securityAnswer1 = securityAnswer1;
	}

	public String getSecurityAnswer2() {
		return securityAnswer2;
	}

	public void setSecurityAnswer2(String securityAnswer2) {
		this.securityAnswer2 = securityAnswer2;
	}

	public String getSecurityAnswer3() {
		return securityAnswer3;
	}

	public void setSecurityAnswer3(String securityAnswer3) {
		this.securityAnswer3 = securityAnswer3;
	}

}
