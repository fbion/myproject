package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 获取"账户充值"易宝快捷支付URL地址参数对象
 * 
 * @author sunchuanfu
 */
public class GetFastPayUrlForAccountRechargeReq extends BaseBeanReq {
	private static final long serialVersionUID = -8421215828969550839L;

	private String userId;// 用户ID
	private String accountNo;// 账号
	private String amount;// 金额,单位是元并保留2位小数
	private String clentType= "MOBILE";// 终端类型，固定值(PC, MOBILE),这里传值为"MOBILE"
//	private String clentId;// 终端标识ID(如果终端类型为2,则这里即为用户id)
	private String payChannel = "FASTPAY";// 支付渠道，填英文固定值(在线-NET,快捷-FASTPAY,手机-WAP),这里传值为"FASTPAY"
	private String bankCardCode;// 银行代码(非必填)
	private String bankCardType;// 银行卡类型(非必填)
	private String bankCardName;// 银行名称(非必填)
	private String storeCode;// 商户编号(网关赋值)
	private String remarks; // 备注(非必填)
	private String productCatalog = "2";// 商品类型(账户充值值为2)
	private String productName;// 商品名称
	private String userIp;// 客户端IP
	private String identityType;// 标识类型:0=IMEI,1=MAC地址,2=用户ID(例如使用值2,则下面的标识Id为用户Id)
	private String identityId;// 标识ID
	private String fcallbackUrl;// 前台回调URL
	private String workOrderNo;// 业务订单号

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
