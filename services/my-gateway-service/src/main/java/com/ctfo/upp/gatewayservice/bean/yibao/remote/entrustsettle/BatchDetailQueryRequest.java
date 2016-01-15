package com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle;

import com.ctfo.upp.gatewayservice.bean.yibao.remote.NotHmac;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class BatchDetailQueryRequest {
	
	private String cmd;
	private String version;
	private String group_Id;
	private String mer_Id;
	@NotHmac
	private String query_Mode;
	private String product;
	private String batch_No;
	private String order_Id;
	private String page_No;
	private String hmac;
	public BatchDetailQueryRequest() {
		this.cmd = "BatchDetailQuery";
		this.group_Id = ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID");
		this.mer_Id = ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID");
		this.query_Mode = "3";
		this.page_No = "1";
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getGroup_Id() {
		return group_Id;
	}
	public void setGroup_Id(String group_Id) {
		this.group_Id = group_Id;
	}
	public String getMer_Id() {
		return mer_Id;
	}
	public void setMer_Id(String mer_Id) {
		this.mer_Id = mer_Id;
	}
	public String getQuery_Mode() {
		return query_Mode;
	}
	public void setQuery_Mode(String query_Mode) {
		this.query_Mode = query_Mode;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBatch_No() {
		return batch_No;
	}
	public void setBatch_No(String batch_No) {
		this.batch_No = batch_No;
	}
	public String getOrder_Id() {
		return order_Id;
	}
	public void setOrder_Id(String order_Id) {
		this.order_Id = order_Id;
	}
	public String getPage_No() {
		return page_No;
	}
	public void setPage_No(String page_No) {
		this.page_No = page_No;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
