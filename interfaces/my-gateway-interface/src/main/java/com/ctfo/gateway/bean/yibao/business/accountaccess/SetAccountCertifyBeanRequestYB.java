package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class SetAccountCertifyBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1414958119159307927L;
	
	//账户ID
	@Necessary
	private String accountId;
	@Necessary
	private String requestId;
	//用户名
	@Necessary
	private String userName;
	//身份证类型
	@Necessary
	private String idCardType;
	//身份证号
	@Necessary
	private String idCardNo;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
}
