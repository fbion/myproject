package com.ctfo.sinoiov.mobile.webapi.bean.request;

/**
 * 获取易宝快捷支付URL(例如"油卡充值"等)参数对象
 * 
 * @author sunchuanfu
 */
public class GetFastPayUrlReq extends BaseBeanReq {
	private static final long serialVersionUID = 6154291263982891836L;

	private String accountNo;// 账户
	private String merchantOrderNo;// 业务订单号
	private String merchantOrderRemark;// 备注(例如"油卡充值")
	private String merchantOrderAmount;// 金额
	private String callbackURL;// 支付平台扣款后的后台回调地址
	private String fcallbackURL;// 支付平台扣款后的前台回调地址(返回页面成功或者失败,这里的结果是假数据)
	private String userId;// 用户ID(注：这里重复定义与基类相同的属性)
	private String productCatalog = "11";// 商品类型(油卡值值为11)
	private String productName;// 商品名称(例如"油卡充值")
	private String identityType;// 标识类型(例如"2")
	private String identityId;// 标识ID(如果标识类型为2,则这里即为用户id)
	private String clentType = "MOBILE";// 终端类型，固定值(PC, MOBILE),这里传值为"MOBILE"
	private String clentId;// 终端标示ID(如果终端类型为2,则这里即为用户id)
	private String userUa;// 商品UA(在页面显示的图片)
	private String userLoginName;// 登录用户名
	private String userIP;// 客户端IP

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getMerchantOrderRemark() {
		return merchantOrderRemark;
	}

	public void setMerchantOrderRemark(String merchantOrderRemark) {
		this.merchantOrderRemark = merchantOrderRemark;
	}

	public String getMerchantOrderAmount() {
		return merchantOrderAmount;
	}

	public void setMerchantOrderAmount(String merchantOrderAmount) {
		this.merchantOrderAmount = merchantOrderAmount;
	}

	public String getCallbackURL() {
		return callbackURL;
	}

	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}

	public String getFcallbackURL() {
		return fcallbackURL;
	}

	public void setFcallbackURL(String fcallbackURL) {
		this.fcallbackURL = fcallbackURL;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(String productCatalog) {
		this.productCatalog = productCatalog;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getClentType() {
		return clentType;
	}

	public void setClentType(String clentType) {
		this.clentType = clentType;
	}

	public String getClentId() {
		return clentId;
	}

	public void setClentId(String clentId) {
		this.clentId = clentId;
	}

	public String getUserUa() {
		return userUa;
	}

	public void setUserUa(String userUa) {
		this.userUa = userUa;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

}
