package com.ctfo.upp.mgbeans;

import java.io.Serializable;
import java.util.List;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.google.code.morphia.annotations.Id;

/**
 * 同步交易失败的记录
 * @author majingyuan
 *
 */
public class SyncTradeFailRecord implements Serializable{
	@Id
	private String id;
	private Long startTime;
	private Long endTime;	
	private Long createTime;
	private List<PaymentTrade> sycFailTradeRecords;

	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public List<PaymentTrade> getSycFailTradeRecords() {
		return sycFailTradeRecords;
	}
	public void setSycFailTradeRecords(List<PaymentTrade> sycFailTradeRecords) {
		this.sycFailTradeRecords = sycFailTradeRecords;
	}

	
}
