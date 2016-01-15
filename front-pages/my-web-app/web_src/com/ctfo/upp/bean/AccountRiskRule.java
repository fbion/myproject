package com.ctfo.upp.bean;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value="acc_risk_rule", noClassnameStored=true)
public class AccountRiskRule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String acc_no;
	private String acc_deduct_once;
	private String acc_deduct_daily;
	private String acc_deduct_times_daily;
	private String acc_withdraw_once;
	private String acc_withdraw_daily;
	private String acc_withdraw_times_daily;
	private String acc_transfer_once;
	private String acc_transfer_daily;
	private String acc_transfer_times_daily;
	private String acc_process_method;
	private String acc_risk_status;
	private long acc_risk_rule_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getAcc_deduct_once() {
		return acc_deduct_once;
	}
	public void setAcc_deduct_once(String acc_deduct_once) {
		this.acc_deduct_once = acc_deduct_once;
	}
	public String getAcc_deduct_daily() {
		return acc_deduct_daily;
	}
	public void setAcc_deduct_daily(String acc_deduct_daily) {
		this.acc_deduct_daily = acc_deduct_daily;
	}
	public String getAcc_deduct_times_daily() {
		return acc_deduct_times_daily;
	}
	public void setAcc_deduct_times_daily(String acc_deduct_times_daily) {
		this.acc_deduct_times_daily = acc_deduct_times_daily;
	}
	public String getAcc_withdraw_once() {
		return acc_withdraw_once;
	}
	public void setAcc_withdraw_once(String acc_withdraw_once) {
		this.acc_withdraw_once = acc_withdraw_once;
	}
	public String getAcc_withdraw_daily() {
		return acc_withdraw_daily;
	}
	public void setAcc_withdraw_daily(String acc_withdraw_daily) {
		this.acc_withdraw_daily = acc_withdraw_daily;
	}
	public String getAcc_withdraw_times_daily() {
		return acc_withdraw_times_daily;
	}
	public void setAcc_withdraw_times_daily(String acc_withdraw_times_daily) {
		this.acc_withdraw_times_daily = acc_withdraw_times_daily;
	}
	public String getAcc_transfer_once() {
		return acc_transfer_once;
	}
	public void setAcc_transfer_once(String acc_transfer_once) {
		this.acc_transfer_once = acc_transfer_once;
	}
	public String getAcc_transfer_daily() {
		return acc_transfer_daily;
	}
	public void setAcc_transfer_daily(String acc_transfer_daily) {
		this.acc_transfer_daily = acc_transfer_daily;
	}
	public String getAcc_transfer_times_daily() {
		return acc_transfer_times_daily;
	}
	public void setAcc_transfer_times_daily(String acc_transfer_times_daily) {
		this.acc_transfer_times_daily = acc_transfer_times_daily;
	}
	public String getAcc_process_method() {
		return acc_process_method;
	}
	public void setAcc_process_method(String acc_process_method) {
		this.acc_process_method = acc_process_method;
	}
	public String getAcc_risk_status() {
		return acc_risk_status;
	}
	public void setAcc_risk_status(String acc_risk_status) {
		this.acc_risk_status = acc_risk_status;
	}
	
	public long getAcc_risk_rule_time() {
		return acc_risk_rule_time;
	}
	public void setAcc_risk_rule_time(long acc_risk_rule_time) {
		this.acc_risk_rule_time = acc_risk_rule_time;
	}
	@Override
	public String toString() {
		return "AccountRiskRule [id=" + id + ", acc_no=" + acc_no
				+ ", acc_deduct_once=" + acc_deduct_once
				+ ", acc_deduct_daily=" + acc_deduct_daily
				+ ", acc_deduct_times_daily=" + acc_deduct_times_daily
				+ ", acc_withdraw_once=" + acc_withdraw_once
				+ ", acc_withdraw_daily=" + acc_withdraw_daily
				+ ", acc_withdraw_times_daily=" + acc_withdraw_times_daily
				+ ", acc_transfer_once=" + acc_transfer_once
				+ ", acc_transfer_daily=" + acc_transfer_daily
				+ ", acc_transfer_times_daily=" + acc_transfer_times_daily
				+ ", acc_process_method=" + acc_process_method
				+ ", acc_risk_status=" + acc_risk_status
				+ ", acc_risk_rule_time=" + acc_risk_rule_time + "]";
	}
	
}
