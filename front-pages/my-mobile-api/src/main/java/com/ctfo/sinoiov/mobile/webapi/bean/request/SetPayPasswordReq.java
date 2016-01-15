package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 设置支付密码
 * 
 * @author sunchuanfu
 */
public class SetPayPasswordReq extends BaseBeanReq {
	private static final long serialVersionUID = -7714617558503942052L;

	private String userId;// 用户ID（重复定义一个用户id）
	private String ownerUserId;// 统一认证用户Id
	private String ownerLoginName;// 统一认证用户名称
	private String mobileNo;// 电话号码
	private String md5;// MD5加密后的支付密码
	private String smsCode;// 短信验证码

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

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
