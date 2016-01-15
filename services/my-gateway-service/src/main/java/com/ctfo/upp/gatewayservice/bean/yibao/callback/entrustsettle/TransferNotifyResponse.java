package com.ctfo.upp.gatewayservice.bean.yibao.callback.entrustsettle;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;


public class TransferNotifyResponse {
	
	private String cmd;
	private String mer_Id;
	private String batch_No;
	private String order_Id;
	private String ret_Code;
	private String hmac;
	public TransferNotifyResponse() {
		this.cmd = "TransferNotify";
		this.mer_Id = ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID");
		this.ret_Code = "S"; 
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getMer_Id() {
		return mer_Id;
	}
	public void setMer_Id(String mer_Id) {
		this.mer_Id = mer_Id;
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
	public String getRet_Code() {
		return ret_Code;
	}
	public void setRet_Code(String ret_Code) {
		this.ret_Code = ret_Code;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
