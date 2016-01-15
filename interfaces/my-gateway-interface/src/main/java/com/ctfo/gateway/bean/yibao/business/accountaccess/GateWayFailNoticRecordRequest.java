package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;
import java.util.Date;

import com.ctfo.gateway.bean.base.RequestBean;

public class GateWayFailNoticRecordRequest extends RequestBean implements Serializable{
	private Long startTime;
	private Long endTime;
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
	
	
}
