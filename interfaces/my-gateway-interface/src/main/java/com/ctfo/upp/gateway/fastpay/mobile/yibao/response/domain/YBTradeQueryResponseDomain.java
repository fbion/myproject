package com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain;

import com.ctfo.upp.gateway.fastpay.mobile.base.domain.MobileDomain;

public class YBTradeQueryResponseDomain extends MobileDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户账户编号
	private String merchantAccount;
	// 客户订单号
	private String orderId;
	// 易宝交易流水号
	private String ybOrderId;
	// 订单金额,以"分"为单位的整型
	private Long amount;
	// 交易币种,默认为人民币156（当前仅支持人民币）
	private Integer currency;
	// 付款方手续费,以"分"为单位的整型
	private Long sourcefee;
	// 收款方手续费,以"分"为单位的整型
	private Long targetfee;
	// 付款方实付金额,以"分"为单位的整型
	private Long sourceAmount;
	// 收款方实收金额,以"分"为单位的整型
	private Long targetAmount;
	// 下单时间,易宝收到交易请求时间 例如:1361324896,精确到秒
	private Long orderTime;
	// 交易时间,交易变成当前状态的时间
	private Long closeTime;
	// 商品类别码
	private Integer productCatalog;
	// 商品名称
	private String productName;
	// 商品描述
	private String productDesc;
	// 支付类型
	private Integer type;
	/*
	0：待付（创建的订单未支付成功）
	1：已付（订单已经支付成功）
	2：已撤销（待支付订单有效期为1天，过期后自动撤销）
	3：阻断交易（订单因为高风险而被阻断）*/
	private Integer status;
	// 累计退款金额 以"分"为单位的整型
	private Long refundTotal;

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

	public String getYbOrderId() {
		return ybOrderId;
	}

	public void setYbOrderId(String ybOrderId) {
		this.ybOrderId = ybOrderId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public Long getSourcefee() {
		return sourcefee;
	}

	public void setSourcefee(Long sourcefee) {
		this.sourcefee = sourcefee;
	}

	public Long getTargetfee() {
		return targetfee;
	}

	public void setTargetfee(Long targetfee) {
		this.targetfee = targetfee;
	}

	public Long getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(Long sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	public Long getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Long targetAmount) {
		this.targetAmount = targetAmount;
	}

	public Long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Long orderTime) {
		this.orderTime = orderTime;
	}

	public Long getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Long closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(Integer productCatalog) {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getRefundTotal() {
		return refundTotal;
	}

	public void setRefundTotal(Long refundTotal) {
		this.refundTotal = refundTotal;
	}
}
