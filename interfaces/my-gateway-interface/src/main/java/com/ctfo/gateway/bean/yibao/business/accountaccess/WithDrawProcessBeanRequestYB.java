package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class WithDrawProcessBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6077239548690654222L;
	
	@Necessary
	private String requestId;
	
	//type为2的时候该字段必填
	private String batchNo;
	
	//1:普通提现，2：快速提现
	@Necessary
	private String type;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
