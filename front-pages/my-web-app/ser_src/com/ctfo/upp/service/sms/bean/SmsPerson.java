package com.ctfo.upp.service.sms.bean;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 接收短信人员实体
 * 
 * @author lipeng
 */
@Entity(value = "SmsPerson", noClassnameStored = true)
public class SmsPerson implements Serializable{

	private static final long serialVersionUID = 1L;

	public SmsPerson() {
	}

	/**
	 * ID
	 */
	@Id
	private String uuid;

	/**
	 * 模板编码
	 */
	private String templateCode;

	/**
	 * 人员姓名
	 */
	private String name;
	/**
	 * 人员职务
	 */
	private String job;
	/**
	 * 手机号码
	 */
	private String mobileNo;
	/**
	 * 设定短信发送日期（目前只对前台收银台银行转账功能有效） 
	 */
	private String day;
	/**
	 * 设定短信发送时间（目前只对前台收银台银行转账功能有效）
	 */
	private String startTime;
	private String endTime;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
