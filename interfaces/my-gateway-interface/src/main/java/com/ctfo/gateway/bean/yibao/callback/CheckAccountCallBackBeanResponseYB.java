package com.ctfo.gateway.bean.yibao.callback;

import java.io.Serializable;
import java.util.List;

public class CheckAccountCallBackBeanResponseYB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -579401769342960196L;

	private String date;
	
	/**
	 * 对账没问题，则为null
	 */
	private List<CheckAccountCallBackBeanDetailYB> details;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<CheckAccountCallBackBeanDetailYB> getDetails() {
		return details;
	}

	public void setDetails(List<CheckAccountCallBackBeanDetailYB> details) {
		this.details = details;
	}

}
