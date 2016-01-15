package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class BankCardRechargeBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5077946309945438738L;
	
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
	//支付渠道，填英文固定值(在线-NET，快捷-FASTPAY，手机-WAP)
	@Necessary
	private String channel;
	//银行卡类型，信用卡-CREDIT,借记卡-DEBIT
	@Necessary
	private String cardType;
	//银行编码
	@Necessary
	private String frpChannel;
	//终端类型，固定值(PC, MOBILE)
	@Necessary
	private String clientType;
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
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getFrpChannel() {
		return frpChannel;
	}
	public void setFrpChannel(String frpChannel) {
		this.frpChannel = frpChannel;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
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
