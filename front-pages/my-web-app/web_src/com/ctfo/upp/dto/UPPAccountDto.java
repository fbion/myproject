package com.ctfo.upp.dto;

/***
 * 类描述：中交账户开户所需传的字段
 * @author：liuguozhong
 * @date：2015年1月31日下午6:44:20
 * @version 1.0
 * @since JDK1.6
 */
public class UPPAccountDto extends UPPDto {

	private static final long serialVersionUID = 1L;
	/**
	 * 第三方支付编码
	 */
	private String serviceProviderCode;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 所属用户标识,UAA中的用户标识
	 */
	private String ownerUserId;
	/**
	 * 所属用户的中文名
	 */
	private String ownerLoginName;
	/**
	 * 加密存放用户的支付密码，用于第三方支付使用
	 */
	private String payPassword;
	/**
	 * 绑定手机号，用于下发支付验证码
	 */
	private String mobileNo;

	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getOwnerLoginName() {
		return ownerLoginName;
	}

	public void setOwnerLoginName(String ownerLoginName) {
		this.ownerLoginName = ownerLoginName;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

}
