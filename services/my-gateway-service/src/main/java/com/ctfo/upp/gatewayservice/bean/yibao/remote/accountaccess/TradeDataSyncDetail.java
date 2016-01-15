package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

public class TradeDataSyncDetail {
	private String recordId;
	private String orderTpye;
	private String requestId;
	private String sourceUserNumber;
	private String attachment;
	private String amount;
	private String trxDate;
	private String remarks;
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getOrderTpye() {
		return orderTpye;
	}
	public void setOrderTpye(String orderTpye) {
		this.orderTpye = orderTpye;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getSourceUserNumber() {
		return sourceUserNumber;
	}
	public void setSourceUserNumber(String sourceUserNumber) {
		this.sourceUserNumber = sourceUserNumber;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
