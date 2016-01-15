package com.ctfo.sinoiov.mobile.webapi.bean.request;

/**
 * 使用账户余额对油卡进行充值
 * 
 * @author sunchuanfu
 */
public class OilRechargeByAccountReq extends BaseBeanReq {
	private static final long serialVersionUID = 8672567440706247453L;

	private String md5;
	private String smsCode;
	private String mobileNo;
	private String accountNo;
	private String merchantOrderAmount;
	private String merchantOrderNo;// 业务订单号
	private String userId;
	private String callbackURL;// 服务端回调地址
	private String fcallbackURL;// 客户端回调地址
	private String remarks;
	private String productName;
	private String productCatalog = "11"; // 油卡充值：11；油卡返利12；账户充值13；其它10

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

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMerchantOrderAmount() {
		return merchantOrderAmount;
	}

	public void setMerchantOrderAmount(String merchantOrderAmount) {
		this.merchantOrderAmount = merchantOrderAmount;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(String productCatalog) {
		this.productCatalog = productCatalog;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

}
