package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class TransactionBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1693870531112338929L;
	
	//批次中的记录号
	@Necessary
	private String recordId;
	//交易订单号
	@Necessary
	private String requestId;
	//原账户ID
	@Necessary
	private String origAccountId;
	//目的账户ID
	@Necessary
	private String targetAccountId;
	//交易金额
	@Necessary
	private String amount;
	//交易时间戳
	@Necessary
	private String time;
	
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrigAccountId() {
		return origAccountId;
	}
	public void setOrigAccountId(String origAccountId) {
		this.origAccountId = origAccountId;
	}
	public String getTargetAccountId() {
		return targetAccountId;
	}
	public void setTargetAccountId(String targetAccountId) {
		this.targetAccountId = targetAccountId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
}
