package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class OtherCardRechargeBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2158046690423915349L;
	
	//账户ID
	@Necessary
	private String accountId;
	//流水号
	@Necessary
	private String requestId;
	//金额，保留小数点后2位
	@Necessary
	private String amount;
	//平台手续费，保留小数点后2位
	private String platFee;
	//终端类型，固定值(PC, MOBILE)
	@Necessary
	private String clientType;
	//银行编码
	@Necessary
	private String frpChannel;
	//非银行卡金额组合，多张卡时使用“,”分割
	@Necessary
	private String cardAmounts;
	//非银行卡卡号组合，多张卡时使用“,”分割
	@Necessary
	private String cardNos;
	//非银行卡卡密组合，多张卡时使用“,”分割
	@Necessary
	private String cardPwds;
	//平台商交易日期，毫秒值
	@Necessary
	private String platTrxDate;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPlatFee() {
		return platFee;
	}
	public void setPlatFee(String platFee) {
		this.platFee = platFee;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getFrpChannel() {
		return frpChannel;
	}
	public void setFrpChannel(String frpChannel) {
		this.frpChannel = frpChannel;
	}
	public String getCardAmounts() {
		return cardAmounts;
	}
	public void setCardAmounts(String cardAmounts) {
		this.cardAmounts = cardAmounts;
	}
	public String getCardNos() {
		return cardNos;
	}
	public void setCardNos(String cardNos) {
		this.cardNos = cardNos;
	}
	public String getCardPwds() {
		return cardPwds;
	}
	public void setCardPwds(String cardPwds) {
		this.cardPwds = cardPwds;
	}
	public String getPlatTrxDate() {
		return platTrxDate;
	}
	public void setPlatTrxDate(String platTrxDate) {
		this.platTrxDate = platTrxDate;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
