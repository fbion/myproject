package com.ctfo.sinoiov.mobile.webapi.bean.request;

/**
 * 验证账户相关各种信息(短信\支付密码\单个安全问题)
 * 
 * @author sunchuanfu
 */
public class ValidateAccountInfosReq extends BaseBeanReq {
	private static final long serialVersionUID = 1517231551146538830L;

	private String accountNo;
	private String mobileNo;
	private String smsCode;
	private String payPassword;
	private String securityQuestion;
	private String securityAnswer;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

}
