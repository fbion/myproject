package com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess;

import java.util.Date;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class SyncAccountRequest {
	private String command;
	private String req01_merchantIdentityNumber;
	private String req03_requestId;
	private String req14_timestamp;
	private String req37_syncDate;
	private String req38_syncCount;
	private String req39_startRecordId;
	private String req40_endRecordId;
	private String req41_isLastBatch;
	private String req42_dataSyncDetail;
	private String hmac;
	public SyncAccountRequest() {
		this.command = "AccountDataSync";
		this.req01_merchantIdentityNumber = ConfigUtil.getValue("YB_ACCOUNT_ACCESS_MER_ID");
		this.req14_timestamp = String.valueOf((new Date().getTime()));
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getReq01_merchantIdentityNumber() {
		return req01_merchantIdentityNumber;
	}
	public void setReq01_merchantIdentityNumber(String req01_merchantIdentityNumber) {
		this.req01_merchantIdentityNumber = req01_merchantIdentityNumber;
	}
	public String getReq03_requestId() {
		return req03_requestId;
	}
	public void setReq03_requestId(String req03_requestId) {
		this.req03_requestId = req03_requestId;
	}
	public String getReq14_timestamp() {
		return req14_timestamp;
	}
	public void setReq14_timestamp(String req14_timestamp) {
		this.req14_timestamp = req14_timestamp;
	}
	public String getReq37_syncDate() {
		return req37_syncDate;
	}
	public void setReq37_syncDate(String req37_syncDate) {
		this.req37_syncDate = req37_syncDate;
	}
	public String getReq38_syncCount() {
		return req38_syncCount;
	}
	public void setReq38_syncCount(String req38_syncCount) {
		this.req38_syncCount = req38_syncCount;
	}
	public String getReq39_startRecordId() {
		return req39_startRecordId;
	}
	public void setReq39_startRecordId(String req39_startRecordId) {
		this.req39_startRecordId = req39_startRecordId;
	}
	public String getReq40_endRecordId() {
		return req40_endRecordId;
	}
	public void setReq40_endRecordId(String req40_endRecordId) {
		this.req40_endRecordId = req40_endRecordId;
	}
	public String getReq41_isLastBatch() {
		return req41_isLastBatch;
	}
	public void setReq41_isLastBatch(String req41_isLastBatch) {
		this.req41_isLastBatch = req41_isLastBatch;
	}
	public String getReq42_dataSyncDetail() {
		return req42_dataSyncDetail;
	}
	public void setReq42_dataSyncDetail(String req42_dataSyncDetail) {
		this.req42_dataSyncDetail = req42_dataSyncDetail;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
