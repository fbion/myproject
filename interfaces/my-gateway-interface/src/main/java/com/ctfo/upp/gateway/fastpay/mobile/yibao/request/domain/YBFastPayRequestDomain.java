package com.ctfo.upp.gateway.fastpay.mobile.yibao.request.domain;

import com.ctfo.upp.gateway.fastpay.annotation.FieldLength;
import com.ctfo.upp.gateway.fastpay.annotation.GreaterThan;
import com.ctfo.upp.gateway.fastpay.annotation.NotNull;
import com.ctfo.upp.gateway.fastpay.annotation.NotNullOrEmpty;
import com.ctfo.upp.gateway.fastpay.mobile.base.domain.MobileDomain;

public class YBFastPayRequestDomain extends MobileDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户账户编号
	@NotNullOrEmpty
	private String merchantAccount;
	// 商户生成的唯一订单号，最长50位
	@FieldLength(50)
	@NotNullOrEmpty
	private String orderId;
	// 交易时间
	@NotNull
	private Integer transTime;
	// 默认156人民币(当前仅支持人民币)
	private Integer currency;
	// 以"分"为单位的整型，必须大于零
	@GreaterThan(0)
	@NotNull
	private Integer amount;
	// 商品类别码
	@NotNullOrEmpty
	private String productCatalog;
	// 商品名称 最长50位，出于风控考虑，请按下面的格式传递值：应用-商品名称，如“诛仙-3阶成品天琊”，此商品名在发送短信校验的时候会发给用户，所以描述内容不要加在此参数中，以提高用户的体验度。
	@NotNullOrEmpty
	@FieldLength(50)
	private String productName;
	// 商品描述 最长200位
	@FieldLength(200)
	private String productDesc;
	// 最长50位，商户生成的用户账号唯一标识
	@NotNullOrEmpty
	@FieldLength(50)
	private String identityId;
	// 用户标识类型
	@NotNull
	private Integer identityType;
	// 终端类型 0、IMEI；1、MAC；2、UUID；3、other
	@NotNull
	private Integer terminalType;
	// 终端ID
	@NotNullOrEmpty
	private String terminalId;
	// 用户支付时使用的网络终端IP
	@NotNullOrEmpty
	private String userIp;
	// 用户使用的移动终端的UA信息
	@NotNullOrEmpty
	private String userUa;
	// 商户后台系统的回调地址,用来通知商户支付结果，前后台回调地址的回调内容相同
	private String callbackUrl;
	// 商户前台系统提供的回调地址,用来通知商户支付结果，前后台回调地址的回调内容相同。用户在网页支付成功页面，点击“返回商户”时的回调地址
	private String fcallbackUrl;
	// 网页收银台版本,商户可以使用此参数定制调用的网页收银台版本，目前只支持wap版本（参数传值“0”或不传值）
	private Integer version;
	/*
	 * 格式：1|2|3|4 1- 借记卡支付； 2- 信用卡支付； 3- 手机充值卡支付； 4- 游戏点卡支付 注：该参数若不传此参数，则默认选择运营后台为该商户开通的支付方式。
	 */
	private String payTypes;
	// 银行卡序列号,在进行网页支付请求的时候，如果传此参数会把银行卡号直接在银行信息界面显示卡号
	private String cardNo;
	// 银行编号
	private String bank;
	// 订单有效期,单位：分钟，例如：60，表示订单有效期为60分钟
	private Integer orderExpdate;

	public String getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getTransTime() {
		return transTime;
	}

	public void setTransTime(Integer transTime) {
		this.transTime = transTime;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserUa() {
		return userUa;
	}

	public void setUserUa(String userUa) {
		this.userUa = userUa;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(String payTypes) {
		this.payTypes = payTypes;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Integer getOrderExpdate() {
		return orderExpdate;
	}

	public void setOrderExpdate(Integer orderExpdate) {
		this.orderExpdate = orderExpdate;
	}
}
