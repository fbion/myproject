package com.ctfo.upp.service.recharge.bean;

import com.ctfo.upp.models.BaseSerializable;

/***
 * 类描述：线下交易申请列表
 * @author：liuguozhong
 * @date：2014年12月8日下午2:34:18
 * @version 1.0
 * @since JDK1.6
 */
public class TradeApplyDto extends BaseSerializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	// 商户标识
	private String storeId;
	// 付款人标识
	private String remitterId;
	// 付款人姓名
	private String remitterName;
	// 付款人开户行的代码，从码表中获得
	private String remitterBankCode;
	// 付款人银行卡号或账号
	private String remitterBankcardNo;
	// 用户在本系统内的账号
	private String insideAccountNo;
	// 充值申请时间
	private Long applyTime;
	//申请人
	private String applyName;
	// 资金到账时间（记账日期）
	private Long amountArriveTime;
	// 交易金额（小写）
	private String tradeAmount;
	// 交易类型,1:充值，2：扣款
	private String tradeType;
	// 资金确认人ID
	private String fundsConfirmId;
	// 资金确认时间
	private Long fundsConfirmTime;
	// 申请状态
	private String applyStatus;
	// 处理步骤号
	private String stepNo;
	// 当前处理人ID
	private String approvalPersonId;
	// 当前处理人名称
	private String approvalPersonName;
	// 系统中标识交易的流水号，与订单中的流水号一致
	private String tradeExternalNo;
	// 支付订单ID
	private String orderId;
	// 付款方式，1：POS，2：线下汇款，3：线下消费
	private String payType;
	// 交易金额（大写）
	private String tradeAmountUpper;
	// 充值申请对应用途，扣款对应原因
	private String description;
	// 收款人名称
	private String payeeName;
	// 收款人帐号
	private String payeeNo;
	//客户名称
	private String customerName;
	// ----------------------线下充值凭证信息--------------------------------------
	// 线下交易申请标识
	private String applyId;
	// 描述
	private String voucherDesc;
	// 凭证图片地址
	private String voucherFileName;
	// 凭证类型
	private String voucherType;
	// ----------------------线下充值审批信息--------------------------------------
	//充值申请ID
	private String rechargeApplyId;
	//处理建议
	private String approvalSuggest;
	//处理结果
	private String approvalResult;
	//处理步骤号
	private String approvalUserId;
	//添加一个识别字段，后来用来区分是出纳还是财务(0：出纳，1：财务,2:业务,)
	private String identifier;
	
	//来款渠道
	private String channel;
	//来款子渠道
	private String subChannel;
	//商户编号
	private String merchantCode;
	//终端号
	private String terminalCode;
	//凭证号
	private String voucherCode;
	//凭证时间
	private String voucherTime;
	//凭证交易流水号
	private String voucherTradeNo;
	//会员编码
	private String ownerUserNo;
	//所属地区
	private String applyPersonPost;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getStoreId() {
		return storeId;
	}

	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getRemitterId() {
		return remitterId;
	}

	public void setRemitterId(String remitterId) {
		this.remitterId = remitterId;
	}

	public String getRemitterName() {
		return remitterName;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public String getRemitterBankCode() {
		return remitterBankCode;
	}

	public void setRemitterBankCode(String remitterBankCode) {
		this.remitterBankCode = remitterBankCode;
	}

	public String getRemitterBankcardNo() {
		return remitterBankcardNo;
	}

	public void setRemitterBankcardNo(String remitterBankcardNo) {
		this.remitterBankcardNo = remitterBankcardNo;
	}

	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	public Long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}

	public Long getAmountArriveTime() {
		return amountArriveTime;
	}

	public void setAmountArriveTime(Long amountArriveTime) {
		this.amountArriveTime = amountArriveTime;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getFundsConfirmId() {
		return fundsConfirmId;
	}

	public void setFundsConfirmId(String fundsConfirmId) {
		this.fundsConfirmId = fundsConfirmId;
	}

	public Long getFundsConfirmTime() {
		return fundsConfirmTime;
	}

	public void setFundsConfirmTime(Long fundsConfirmTime) {
		this.fundsConfirmTime = fundsConfirmTime;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getStepNo() {
		return stepNo;
	}

	public void setStepNo(String stepNo) {
		this.stepNo = stepNo;
	}

	public String getApprovalPersonId() {
		return approvalPersonId;
	}

	public void setApprovalPersonId(String approvalPersonId) {
		this.approvalPersonId = approvalPersonId;
	}

	public String getApprovalPersonName() {
		return approvalPersonName;
	}

	public void setApprovalPersonName(String approvalPersonName) {
		this.approvalPersonName = approvalPersonName;
	}

	public String getTradeExternalNo() {
		return tradeExternalNo;
	}

	public void setTradeExternalNo(String tradeExternalNo) {
		this.tradeExternalNo = tradeExternalNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTradeAmountUpper() {
		return tradeAmountUpper;
	}

	public void setTradeAmountUpper(String tradeAmountUpper) {
		this.tradeAmountUpper = tradeAmountUpper;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeNo() {
		return payeeNo;
	}

	public void setPayeeNo(String payeeNo) {
		this.payeeNo = payeeNo;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getVoucherDesc() {
		return voucherDesc;
	}

	public void setVoucherDesc(String voucherDesc) {
		this.voucherDesc = voucherDesc;
	}

	public String getVoucherFileName() {
		return voucherFileName;
	}

	public void setVoucherFileName(String voucherFileName) {
		this.voucherFileName = voucherFileName;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getRechargeApplyId() {
		return rechargeApplyId;
	}

	public void setRechargeApplyId(String rechargeApplyId) {
		this.rechargeApplyId = rechargeApplyId;
	}

	public String getApprovalSuggest() {
		return approvalSuggest;
	}

	public void setApprovalSuggest(String approvalSuggest) {
		this.approvalSuggest = approvalSuggest;
	}

	public String getApprovalUserId() {
		return approvalUserId;
	}

	public void setApprovalUserId(String approvalUserId) {
		this.approvalUserId = approvalUserId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(String subChannel) {
		this.subChannel = subChannel;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public String getVoucherTime() {
		return voucherTime;
	}

	public void setVoucherTime(String voucherTime) {
		this.voucherTime = voucherTime;
	}

	public String getVoucherTradeNo() {
		return voucherTradeNo;
	}

	public void setVoucherTradeNo(String voucherTradeNo) {
		this.voucherTradeNo = voucherTradeNo;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}