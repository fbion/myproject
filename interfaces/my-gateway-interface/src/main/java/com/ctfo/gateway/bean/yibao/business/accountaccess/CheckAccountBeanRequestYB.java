package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;
import java.util.List;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class CheckAccountBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3586974579335896123L;
	
	@Necessary
	private String requestId;
	
	@Necessary
	private String date;//时间格式：yyyy-MM-dd
	
	@Necessary
	private List<CheckAccountBeanDetailRequestYB> checkAccountDetailList;
	
	@Necessary
	private String isLastBatch;//是否为最后一个批次

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<CheckAccountBeanDetailRequestYB> getCheckAccountDetailList() {
		return checkAccountDetailList;
	}

	public void setCheckAccountDetailList(List<CheckAccountBeanDetailRequestYB> checkAccountDetailList) {
		this.checkAccountDetailList = checkAccountDetailList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIsLastBatch() {
		return isLastBatch;
	}

	public void setIsLastBatch(String isLastBatch) {
		this.isLastBatch = isLastBatch;
	}

}
