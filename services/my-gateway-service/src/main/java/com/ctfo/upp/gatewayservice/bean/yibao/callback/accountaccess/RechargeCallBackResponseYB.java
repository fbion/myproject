package com.ctfo.upp.gatewayservice.bean.yibao.callback.accountaccess;

import com.ctfo.gateway.bean.yibao.callback.RechargeCallBackBeanResponseYB;

public class RechargeCallBackResponseYB {
	private String command;
	private String flag;
	private String requestId;
	private String externalId;
	private String trxAmount;
	private String completeTime;
	private String balanceAmount;
	private String balanceAct;
	private String cardStatus;
	private String nType;
	private String channel;
	private String frpChannel;
	private String cardNo;
	private String hmac;
	public RechargeCallBackBeanResponseYB toBeanResponse() throws Exception{
		RechargeCallBackBeanResponseYB response = new RechargeCallBackBeanResponseYB();
		response.setRequestId(this.requestId);
		response.setFlag(this.flag);
		response.setCompleteTime(this.completeTime);
		response.setErrorMsg(this.cardStatus);
		response.setTrxAmount(this.trxAmount);
		response.setExternalId(this.externalId);
		return response;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(String trxAmount) {
		this.trxAmount = trxAmount;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getBalanceAct() {
		return balanceAct;
	}
	public void setBalanceAct(String balanceAct) {
		this.balanceAct = balanceAct;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getNType() {
		return nType;
	}
	public void setNType(String nType) {
		this.nType = nType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getFrpChannel() {
		return frpChannel;
	}
	public void setFrpChannel(String frpChannel) {
		this.frpChannel = frpChannel;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
