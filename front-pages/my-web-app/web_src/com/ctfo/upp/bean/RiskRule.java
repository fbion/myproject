package com.ctfo.upp.bean;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value="risk_rule", noClassnameStored=true)
public class RiskRule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private long create_time;
	
	private String ip_no;
	private String ip_area;
	private String ip_req_num;
	private String ip_process_method;
	private String ip_risk_status;
	private long ip_time;
	private String ip_creator;
	
	private String fre_no;
	private String fre_ip_timesOfSec__times;
	private String fre_ip_timesOfSec__sec;
	private String fre_ip_timesOfDay__times;
	private String fre_ip_timesOfDay__day;
	private String fre_ip_process_method;
	
	private String fre_acc_timesOfSec__times;
	private String fre_acc_timesOfSec__sec;
	private String fre_acc_timesOfDay__times;
	private String fre_acc_timesOfDay__day;
	private String fre_acc_process_method;
	private String fre_risk_status;
	private long fre_time;
	private String fre_creator;

	private String amount_no;
	private String amount_deduct_once;
	private String amount_deduct_daily;
	private String amount_deduct_times_daily;
	private String amount_withdraw_once;
	private String amount_withdraw_daily;
	private String amount_withdraw_times_daily;
	private String amount_transfer_once;
	private String amount_transfer_daily;
	private String amount_transfer_times_daily;
	private String amount_process_method;
	private String amount_risk_status;
	private long amount_time;
	private String amount_creator;

	private String acc_no;
	private String acc_remain_max;
	private String acc_remain_min;
	private String acc_income_times_daily;
	private String acc_income_amount_daily;
	private String acc_outcome_times_daily;
	private String acc_outcome_amount_daily;
	private String acc_process_method;
	private String acc_risk_status;
	private long acc_time;
	private String acc_creator;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp_area() {
		return ip_area;
	}
	public void setIp_area(String ip_area) {
		this.ip_area = ip_area;
	}
	public String getIp_req_num() {
		return ip_req_num;
	}
	public void setIp_req_num(String ip_req_num) {
		this.ip_req_num = ip_req_num;
	}
	public String getIp_process_method() {
		return ip_process_method;
	}
	public void setIp_process_method(String ip_process_method) {
		this.ip_process_method = ip_process_method;
	}
	public String getIp_risk_status() {
		return ip_risk_status;
	}
	public void setIp_risk_status(String ip_risk_status) {
		this.ip_risk_status = ip_risk_status;
	}
	public String getFre_ip_timesOfSec__times() {
		return fre_ip_timesOfSec__times;
	}
	public void setFre_ip_timesOfSec__times(String fre_ip_timesOfSec__times) {
		this.fre_ip_timesOfSec__times = fre_ip_timesOfSec__times;
	}
	public String getFre_ip_timesOfSec__sec() {
		return fre_ip_timesOfSec__sec;
	}
	public void setFre_ip_timesOfSec__sec(String fre_ip_timesOfSec__sec) {
		this.fre_ip_timesOfSec__sec = fre_ip_timesOfSec__sec;
	}
	public String getFre_ip_timesOfDay__times() {
		return fre_ip_timesOfDay__times;
	}
	public void setFre_ip_timesOfDay__times(String fre_ip_timesOfDay__times) {
		this.fre_ip_timesOfDay__times = fre_ip_timesOfDay__times;
	}
	public String getFre_ip_timesOfDay__day() {
		return fre_ip_timesOfDay__day;
	}
	public void setFre_ip_timesOfDay__day(String fre_ip_timesOfDay__day) {
		this.fre_ip_timesOfDay__day = fre_ip_timesOfDay__day;
	}
	public String getFre_ip_process_method() {
		return fre_ip_process_method;
	}
	public void setFre_ip_process_method(String fre_ip_process_method) {
		this.fre_ip_process_method = fre_ip_process_method;
	}
	public String getFre_acc_timesOfSec__times() {
		return fre_acc_timesOfSec__times;
	}
	public void setFre_acc_timesOfSec__times(String fre_acc_timesOfSec__times) {
		this.fre_acc_timesOfSec__times = fre_acc_timesOfSec__times;
	}
	public String getFre_acc_timesOfSec__sec() {
		return fre_acc_timesOfSec__sec;
	}
	public void setFre_acc_timesOfSec__sec(String fre_acc_timesOfSec__sec) {
		this.fre_acc_timesOfSec__sec = fre_acc_timesOfSec__sec;
	}
	public String getFre_acc_timesOfDay__times() {
		return fre_acc_timesOfDay__times;
	}
	public void setFre_acc_timesOfDay__times(String fre_acc_timesOfDay__times) {
		this.fre_acc_timesOfDay__times = fre_acc_timesOfDay__times;
	}
	public String getFre_acc_timesOfDay__day() {
		return fre_acc_timesOfDay__day;
	}
	public void setFre_acc_timesOfDay__day(String fre_acc_timesOfDay__day) {
		this.fre_acc_timesOfDay__day = fre_acc_timesOfDay__day;
	}
	public String getFre_acc_process_method() {
		return fre_acc_process_method;
	}
	public void setFre_acc_process_method(String fre_acc_process_method) {
		this.fre_acc_process_method = fre_acc_process_method;
	}
	public String getFre_risk_status() {
		return fre_risk_status;
	}
	public void setFre_risk_status(String fre_risk_status) {
		this.fre_risk_status = fre_risk_status;
	}
	public String getAmount_deduct_once() {
		return amount_deduct_once;
	}
	public void setAmount_deduct_once(String amount_deduct_once) {
		this.amount_deduct_once = amount_deduct_once;
	}
	public String getAmount_deduct_daily() {
		return amount_deduct_daily;
	}
	public void setAmount_deduct_daily(String amount_deduct_daily) {
		this.amount_deduct_daily = amount_deduct_daily;
	}
	public String getAmount_deduct_times_daily() {
		return amount_deduct_times_daily;
	}
	public void setAmount_deduct_times_daily(String amount_deduct_times_daily) {
		this.amount_deduct_times_daily = amount_deduct_times_daily;
	}
	public String getAmount_withdraw_once() {
		return amount_withdraw_once;
	}
	public void setAmount_withdraw_once(String amount_withdraw_once) {
		this.amount_withdraw_once = amount_withdraw_once;
	}
	public String getAmount_withdraw_daily() {
		return amount_withdraw_daily;
	}
	public void setAmount_withdraw_daily(String amount_withdraw_daily) {
		this.amount_withdraw_daily = amount_withdraw_daily;
	}
	public String getAmount_withdraw_times_daily() {
		return amount_withdraw_times_daily;
	}
	public void setAmount_withdraw_times_daily(String amount_withdraw_times_daily) {
		this.amount_withdraw_times_daily = amount_withdraw_times_daily;
	}
	public String getAmount_transfer_once() {
		return amount_transfer_once;
	}
	public void setAmount_transfer_once(String amount_transfer_once) {
		this.amount_transfer_once = amount_transfer_once;
	}
	public String getAmount_transfer_daily() {
		return amount_transfer_daily;
	}
	public void setAmount_transfer_daily(String amount_transfer_daily) {
		this.amount_transfer_daily = amount_transfer_daily;
	}
	public String getAmount_transfer_times_daily() {
		return amount_transfer_times_daily;
	}
	public void setAmount_transfer_times_daily(String amount_transfer_times_daily) {
		this.amount_transfer_times_daily = amount_transfer_times_daily;
	}
	public String getAmount_process_method() {
		return amount_process_method;
	}
	public void setAmount_process_method(String amount_process_method) {
		this.amount_process_method = amount_process_method;
	}
	public String getAmount_risk_status() {
		return amount_risk_status;
	}
	public void setAmount_risk_status(String amount_risk_status) {
		this.amount_risk_status = amount_risk_status;
	}
	public String getAcc_remain_max() {
		return acc_remain_max;
	}
	public void setAcc_remain_max(String acc_remain_max) {
		this.acc_remain_max = acc_remain_max;
	}
	public String getAcc_remain_min() {
		return acc_remain_min;
	}
	public void setAcc_remain_min(String acc_remain_min) {
		this.acc_remain_min = acc_remain_min;
	}
	public String getAcc_income_times_daily() {
		return acc_income_times_daily;
	}
	public void setAcc_income_times_daily(String acc_income_times_daily) {
		this.acc_income_times_daily = acc_income_times_daily;
	}
	public String getAcc_income_amount_daily() {
		return acc_income_amount_daily;
	}
	public void setAcc_income_amount_daily(String acc_income_amount_daily) {
		this.acc_income_amount_daily = acc_income_amount_daily;
	}
	public String getAcc_outcome_times_daily() {
		return acc_outcome_times_daily;
	}
	public void setAcc_outcome_times_daily(String acc_outcome_times_daily) {
		this.acc_outcome_times_daily = acc_outcome_times_daily;
	}
	public String getAcc_outcome_amount_daily() {
		return acc_outcome_amount_daily;
	}
	public void setAcc_outcome_amount_daily(String acc_outcome_amount_daily) {
		this.acc_outcome_amount_daily = acc_outcome_amount_daily;
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
	
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public long getIp_time() {
		return ip_time;
	}
	public void setIp_time(long ip_time) {
		this.ip_time = ip_time;
	}
	public long getFre_time() {
		return fre_time;
	}
	public void setFre_time(long fre_time) {
		this.fre_time = fre_time;
	}
	public long getAmount_time() {
		return amount_time;
	}
	public void setAmount_time(long amount_time) {
		this.amount_time = amount_time;
	}
	public long getAcc_time() {
		return acc_time;
	}
	public void setAcc_time(long acc_time) {
		this.acc_time = acc_time;
	}
	
	public String getIp_creator() {
		return ip_creator;
	}
	public void setIp_creator(String ip_creator) {
		this.ip_creator = ip_creator;
	}
	public String getFre_creator() {
		return fre_creator;
	}
	public void setFre_creator(String fre_creator) {
		this.fre_creator = fre_creator;
	}
	public String getAmount_creator() {
		return amount_creator;
	}
	public void setAmount_creator(String amount_creator) {
		this.amount_creator = amount_creator;
	}
	public String getAcc_creator() {
		return acc_creator;
	}
	public void setAcc_creator(String acc_creator) {
		this.acc_creator = acc_creator;
	}
	
	public String getIp_no() {
		return ip_no;
	}
	public void setIp_no(String ip_no) {
		this.ip_no = ip_no;
	}
	public String getFre_no() {
		return fre_no;
	}
	public void setFre_no(String fre_no) {
		this.fre_no = fre_no;
	}
	public String getAmount_no() {
		return amount_no;
	}
	public void setAmount_no(String amount_no) {
		this.amount_no = amount_no;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	@Override
	public String toString() {
		return "RiskRule [id=" + id + ", create_time=" + create_time
				+ ", ip_area=" + ip_area + ", ip_req_num=" + ip_req_num
				+ ", ip_process_method=" + ip_process_method
				+ ", ip_risk_status=" + ip_risk_status + ", ip_time=" + ip_time
				+ ", ip_creator=" + ip_creator + ", fre_ip_timesOfSec__times="
				+ fre_ip_timesOfSec__times + ", fre_ip_timesOfSec__sec="
				+ fre_ip_timesOfSec__sec + ", fre_ip_timesOfDay__times="
				+ fre_ip_timesOfDay__times + ", fre_ip_timesOfDay__day="
				+ fre_ip_timesOfDay__day + ", fre_ip_process_method="
				+ fre_ip_process_method + ", fre_acc_timesOfSec__times="
				+ fre_acc_timesOfSec__times + ", fre_acc_timesOfSec__sec="
				+ fre_acc_timesOfSec__sec + ", fre_acc_timesOfDay__times="
				+ fre_acc_timesOfDay__times + ", fre_acc_timesOfDay__day="
				+ fre_acc_timesOfDay__day + ", fre_acc_process_method="
				+ fre_acc_process_method + ", fre_risk_status="
				+ fre_risk_status + ", fre_time=" + fre_time + ", fre_creator="
				+ fre_creator + ", amount_deduct_once=" + amount_deduct_once
				+ ", amount_deduct_daily=" + amount_deduct_daily
				+ ", amount_deduct_times_daily=" + amount_deduct_times_daily
				+ ", amount_withdraw_once=" + amount_withdraw_once
				+ ", amount_withdraw_daily=" + amount_withdraw_daily
				+ ", amount_withdraw_times_daily="
				+ amount_withdraw_times_daily + ", amount_transfer_once="
				+ amount_transfer_once + ", amount_transfer_daily="
				+ amount_transfer_daily + ", amount_transfer_times_daily="
				+ amount_transfer_times_daily + ", amount_process_method="
				+ amount_process_method + ", amount_risk_status="
				+ amount_risk_status + ", amount_time=" + amount_time
				+ ", amount_creator=" + amount_creator + ", acc_remain_max="
				+ acc_remain_max + ", acc_remain_min=" + acc_remain_min
				+ ", acc_income_times_daily=" + acc_income_times_daily
				+ ", acc_income_amount_daily=" + acc_income_amount_daily
				+ ", acc_outcome_times_daily=" + acc_outcome_times_daily
				+ ", acc_outcome_amount_daily=" + acc_outcome_amount_daily
				+ ", acc_process_method=" + acc_process_method
				+ ", acc_risk_status=" + acc_risk_status + ", acc_time="
				+ acc_time + ", acc_creator=" + acc_creator + "]";
	}
	
}
