package com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class AccountBalanceQueryRequest {
	
	private String cmd;
	private String version;
	private String mer_Id;
	private String date;
	private String hmac;
	public AccountBalanceQueryRequest() {
		this.cmd = "AccountBalanceQuery";
		this.mer_Id = ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID");
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
	public String getMer_Id() {
		return mer_Id;
	}
	public void setMer_Id(String mer_Id) {
		this.mer_Id = mer_Id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
