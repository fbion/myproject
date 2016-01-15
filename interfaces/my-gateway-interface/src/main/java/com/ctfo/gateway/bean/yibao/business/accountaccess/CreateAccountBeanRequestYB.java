package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class CreateAccountBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8316319306556234970L;
	
	//账户ID
	@Necessary
	private String accountId;
	//用户注册名称，authStatus为1的时候必填
	private String userName;
	//用户联系电话
	private String mobile;
	//身份证类型，authStatus为1的时候必填，1为15位，2为18位
	private String idCardType;
	//身份证号，authStatus为1的时候必填
	private String idCardNo;
	//实名认证状态，若用户已经实名认证，则填1，否则不填
	private String authStatus;
	//用户交易密码
	private String trxPsd;
	//流水号
	@Necessary
	private String requestId;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public String getTrxPsd() {
		return trxPsd;
	}
	public void setTrxPsd(String trxPsd) {
		this.trxPsd = trxPsd;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
