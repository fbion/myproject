package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 修改支付密码
 * 
 * @author sunchuanfu
 */
public class ModifyPayPasswordReq extends BaseBeanReq {

	private static final long serialVersionUID = 5180592141069717463L;

	private String accountNo;// 账户号
	private String oldMD5;// MD5加密后的旧支付密码
	private String newMD5;// MD5加密后的新支付密码
	private String smsCode;// 短信验证码
	private String mobileNo;// 电话号码

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getOldMD5() {
		return oldMD5;
	}

	public void setOldMD5(String oldMD5) {
		this.oldMD5 = oldMD5;
	}

	public String getNewMD5() {
		return newMD5;
	}

	public void setNewMD5(String newMD5) {
		this.newMD5 = newMD5;
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

}
