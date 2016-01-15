package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class WithDrawProcessBeanResponseYB extends ResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6466587609923106062L;
	
	//0：查询失败，1：处理中，2：成功，3：失败
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
