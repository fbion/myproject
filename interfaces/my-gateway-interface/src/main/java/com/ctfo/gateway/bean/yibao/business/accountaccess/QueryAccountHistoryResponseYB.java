package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;
import java.util.List;

import com.ctfo.gateway.bean.base.ResponseBean;

public class QueryAccountHistoryResponseYB  extends ResponseBean implements Serializable  {
	private List<String> recordList;

	public List<String> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<String> recordList) {
		this.recordList = recordList;
	}
	
}
