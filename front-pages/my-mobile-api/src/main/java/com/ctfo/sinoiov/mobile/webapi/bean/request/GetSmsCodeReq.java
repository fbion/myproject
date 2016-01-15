package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 发送短信参数对象
 * 
 * @author sunchuanfu
 */
public class GetSmsCodeReq extends BaseBeanReq {
	private static final long serialVersionUID = 9048744534851586702L;

	private String accountNo;// 账户号
	private String mobileNo;// 电话
	private String isCreateAccount = "1";// 0代表开户时获取验证码(此时账户号属性为空，手机APP服务端置其为商户号)；1代表其它情况
	private String isModifyMobileNo = "1";// 0代表是修改手机号码时需要发送给新手机号验证码；1代表其它情况

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

	public String getIsCreateAccount() {
		return isCreateAccount;
	}

	public void setIsCreateAccount(String isCreateAccount) {
		this.isCreateAccount = isCreateAccount;
	}

	public String getIsModifyMobileNo() {
		return isModifyMobileNo;
	}

	public void setIsModifyMobileNo(String isModifyMobileNo) {
		this.isModifyMobileNo = isModifyMobileNo;
	}

}
