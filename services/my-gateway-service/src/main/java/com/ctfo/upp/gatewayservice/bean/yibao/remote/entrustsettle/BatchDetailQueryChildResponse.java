package com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle;

import java.util.List;

public class BatchDetailQueryChildResponse {
	
	private String mer_Id;
	private List<BatchDetailQueryChildChildResponse> items;
	public String getMer_Id() {
		return mer_Id;
	}
	public void setMer_Id(String mer_Id) {
		this.mer_Id = mer_Id;
	}
	public List<BatchDetailQueryChildChildResponse> getItems() {
		return items;
	}
	public void setItems(List<BatchDetailQueryChildChildResponse> items) {
		this.items = items;
	}
}
