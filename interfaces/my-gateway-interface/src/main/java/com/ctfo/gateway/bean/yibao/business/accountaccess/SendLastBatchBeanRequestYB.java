package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class SendLastBatchBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8161780783714489821L;
	
	@Necessary
	private String date;//日志格式：yyyy-MM-dd

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
