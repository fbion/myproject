package com.ctfo.upp.dto;


public class UPPOrderRechargeDto extends UPPDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 账号
	 */
	private String accountNo;
	/**
	 * 金额,保留2位小数
	 */
	private String amount;
	/**
	 * 终端类型，固定值(PC, MOBILE)
	 */
	private String clentType;
	/**
	 * 支付渠道，填英文固定值(在线-NET，快捷-FASTPAY，手机-WAP)
	 */
	private String payChannel;
	/**
	 * 银行代码
	 */
	private String bankCardCode;
	/**
	 * 银行卡类型
	 */
	private String bankCardType;
	/**
	 * 银行名称
	 */
	private String bankCardName;
	
	/**
	 * 商户编号
	 */
	private String storeCode;
	/**
	 * 备注（非必填）
	 */
	private String remarks;
	private String productCatalog;
	private String productName;//商品名称 
	private String userIp;//Client IP
	//标识类型:0=IMEI,1=MAC地址,2=用户ID,3=用户Email,4=用户手机号 ,5=用户身份证号,6=用户纸质订单协议号
	private String identityType;
	private String identityId;//标识ID
	private String callbackUrl;//后台回调URL
	private String fcallbackUrl;//前台回调URL
	private String workOrderNo;//业务订单号
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getClentType() {
		return clentType;
	}
	public void setClentType(String clentType) {
		this.clentType = clentType;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getBankCardCode() {
		return bankCardCode;
	}
	public void setBankCardCode(String bankCardCode) {
		this.bankCardCode = bankCardCode;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
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
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getFcallbackUrl() {
		return fcallbackUrl;
	}
	public void setFcallbackUrl(String fcallbackUrl) {
		this.fcallbackUrl = fcallbackUrl;
	}
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	
	
}
