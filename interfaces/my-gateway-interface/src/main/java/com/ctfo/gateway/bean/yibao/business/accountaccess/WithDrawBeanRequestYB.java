package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class WithDrawBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7767476953630217751L;
	//批次中的记录号
	@Necessary
	private String recordId;
	//交易订单号
	@Necessary
	private String requestId;
	//账户ID
	@Necessary
	private String accountId;
	//银行卡卡号
	@Necessary
	private String bankCardNo;
	//提现金额
	@Necessary
	private String amount;
	//提现时间戳
	@Necessary
	private String time;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
}
