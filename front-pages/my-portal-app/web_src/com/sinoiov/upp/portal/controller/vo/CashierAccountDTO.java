package com.sinoiov.upp.portal.controller.vo;

public class CashierAccountDTO {
	private String userId;
	private String ownerUserId;//与userId相同
	private String ownerLoginName;
	private String accountNo;
	private String mobileNo;
	private String ownerUserNo;//用户的会员编号
	private String applyPersonPost;//用户所属地区
	private String userName;//用户认证的真是名字
	//private String 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getOwnerUserNo() {
		return ownerUserNo;
	}
	public void setOwnerUserNo(String ownerUserNo) {
		this.ownerUserNo = ownerUserNo;
	}
	public String getApplyPersonPost() {
		return applyPersonPost;
	}
	public void setApplyPersonPost(String applyPersonPost) {
		this.applyPersonPost = applyPersonPost;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
