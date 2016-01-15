package com.sinoiov.vehicle.controller;

import java.io.Serializable;

public class CommonRequestParams implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dataType;
	
	private String start;
	
	private String end;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	

}
