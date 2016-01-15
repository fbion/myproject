package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 修改手机号
 * 
 * @author sunchuanfu
 */
public class ModifyMobileNoReq extends BaseBeanReq {
	private static final long serialVersionUID = -3064953985016839400L;

	private String accountNo;// 账户号码
	private String securityTicket;// 安全ticket
	private String smsCode;// 短信验证码
	private String mobileNo;// 新手机号

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSecurityTicket() {
		return securityTicket;
	}

	public void setSecurityTicket(String securityTicket) {
		this.securityTicket = securityTicket;
	}

}
