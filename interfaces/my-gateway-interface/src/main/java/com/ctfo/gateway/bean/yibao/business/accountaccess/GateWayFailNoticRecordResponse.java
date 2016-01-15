package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;
import java.util.List;

import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.gateway.bean.yibao.callback.CallbackRecord;

public class GateWayFailNoticRecordResponse extends ResponseBean implements Serializable{
	private List<CallbackRecord> beanList;

	public List<CallbackRecord> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<CallbackRecord> beanList) {
		this.beanList = beanList;
	}
	
	
}
