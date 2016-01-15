package com.ctfo.upp.excelbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class RechargeExcel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "exp(申请流水号;NOMAL_ENUM)imp", required = false)
	String applyNo;
	
	@XmlElement(name = "exp(会员编号;NOMAL_ENUM)imp", required = false)
	String ownerUserNo;

	@XmlElement(name = "exp(来款渠道;NOMAL_ENUM)imp", required = false)
	String channel;
	
	@XmlElement(name = "exp(  ;NOMAL_ENUM)imp", required = false)
	String subChannel;
	
	@XmlElement(name = "exp(账户号;NOMAL_ENUM)imp", required = false)
	String storeId;
	
	@XmlElement(name = "exp(付款人银行卡号;NOMAL_ENUM)imp", required = false)
	String remitterBankcardNo;
	
	@XmlElement(name = "exp(充值申请时间;NOMAL_ENUM)imp", required = false)
	String applyTime;
	
	@XmlElement(name = "exp(充值金额（元）;NOMAL_ENUM)imp", required = false)
	String tradeAmount;
	
	@XmlElement(name = "exp(付款方式;NOMAL_ENUM)imp", required = false)
	String payType;
	
	@XmlElement(name = "exp(申请状态;NOMAL_ENUM)imp", required = false)
	String applyStatus;
	
	@XmlElement(name = "exp(当前处理人;NOMAL_ENUM)imp", required = false)
	String approvalPersonName;

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getOwnerUserNo() {
		return ownerUserNo;
	}

	public void setOwnerUserNo(String ownerUserNo) {
		this.ownerUserNo = ownerUserNo;
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

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getRemitterBankcardNo() {
		return remitterBankcardNo;
	}

	public void setRemitterBankcardNo(String remitterBankcardNo) {
		this.remitterBankcardNo = remitterBankcardNo;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getApprovalPersonName() {
		return approvalPersonName;
	}

	public void setApprovalPersonName(String approvalPersonName) {
		this.approvalPersonName = approvalPersonName;
	}
	
	
}
