package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;
import java.util.List;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class TradeSyncBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8562243763714734773L;
	
	private boolean lastOrNot = false;
	
	private String dateString;
	
	private List<Object> list;

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public boolean isLastOrNot() {
		return lastOrNot;
	}

	public void setLastOrNot(boolean lastOrNot) {
		this.lastOrNot = lastOrNot;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
}
