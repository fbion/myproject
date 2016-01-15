package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 验证支付密码是否正确
 * 
 * @author sunchuanfu
 */
public class IsPayPasswordReq extends BaseBeanReq {
	private static final long serialVersionUID = -8632738159850513581L;

	private String accountNo;// 账户号码
	private String md5;// MD5加密后的支付密码
	private String smsCode;// 短信验证码
	private String mobileNo;// 电话号码

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

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

}
