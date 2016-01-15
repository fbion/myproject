package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class CertifyAccountBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 784670520139066774L;
	
	//账户ID
	@Necessary
	private String accountId;
	//姓名
	@Necessary
	private String userName;
	//身份证号
	@Necessary
	private String idCardNo;
	//银行卡绑定手机号
	private String mobile;
	//银行卡号
	@Necessary
	private String bankCardNumber;
	//银行代码
	@Necessary
	private String bankCode;
	//开户行名称
	private String branchName;
	//开户行省份
	private String province;
	//开户行城市
	private String city;
	//身份证有效截止日期，格式（2013-06-17）
	private String idCardEndDate;
	//流水号
	@Necessary
	private String requestId;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBankCardNumber() {
		return bankCardNumber;
	}
	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIdCardEndDate() {
		return idCardEndDate;
	}
	public void setIdCardEndDate(String idCardEndDate) {
		this.idCardEndDate = idCardEndDate;
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
