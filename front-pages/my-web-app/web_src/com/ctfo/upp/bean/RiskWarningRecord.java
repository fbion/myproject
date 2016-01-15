package com.ctfo.upp.bean;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value="risk_warning_record", noClassnameStored=true)
public class RiskWarningRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private long warning_time; //预警时间
	private String risk_type; //风控类型
	private String risk_item; //风控指标
	private String member_no; //会员编号
	private String reg_mobile; //注册手机号
	private String account_no; //账户编码
	private String process_status; //处理状态 1：已处理 2：未处理
	private String process_result; //处理结果 
	private String process_person; // 处理人
	private String warning_info; //详情
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getWarning_time() {
		return warning_time;
	}
	public void setWarning_time(long warning_time) {
		this.warning_time = warning_time;
	}
	public String getRisk_type() {
		return risk_type;
	}
	public void setRisk_type(String risk_type) {
		this.risk_type = risk_type;
	}
	public String getRisk_item() {
		return risk_item;
	}
	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public String getReg_mobile() {
		return reg_mobile;
	}
	public void setReg_mobile(String reg_mobile) {
		this.reg_mobile = reg_mobile;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public String getProcess_status() {
		return process_status;
	}
	public void setProcess_status(String process_status) {
		this.process_status = process_status;
	}
	public String getProcess_result() {
		return process_result;
	}
	public void setProcess_result(String process_result) {
		this.process_result = process_result;
	}
	public String getProcess_person() {
		return process_person;
	}
	public void setProcess_person(String process_person) {
		this.process_person = process_person;
	}
	public String getWarning_info() {
		return warning_info;
	}
	public void setWarning_info(String warning_info) {
		this.warning_info = warning_info;
	}
	@Override
	public String toString() {
		return "RiskWarningRecord [id=" + id + ", warning_time=" + warning_time
				+ ", risk_type=" + risk_type + ", risk_item=" + risk_item
				+ ", member_no=" + member_no + ", reg_mobile=" + reg_mobile
				+ ", account_no=" + account_no + ", process_status="
				+ process_status + ", process_result=" + process_result
				+ ", process_person=" + process_person + ", warning_info="
				+ warning_info + "]";
	}
	
}
