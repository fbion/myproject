package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class FireClientNoticeBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2630567580469914069L;
	
	@Necessary
	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
