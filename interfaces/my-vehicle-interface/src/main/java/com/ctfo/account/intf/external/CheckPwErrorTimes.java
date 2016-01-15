package com.ctfo.account.intf.external;

import java.io.Serializable;

import com.google.code.morphia.annotations.Id;

public class CheckPwErrorTimes implements Serializable {

	/**
	 * mongo账户支付密码输入错误次数表
	 */
	private static final long serialVersionUID = 1L;
	
	/*当前登录用户ID*/
	private String userId;
	
	/*密码输入错误次数*/
	private Integer times;
	
	/*输入时间*/
	private Long inputTime;
	
	@Id
	private String id;

	/*get set*/
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Long getInputTime() {
		return inputTime;
	}

	public void setInputTime(Long inputTime) {
		this.inputTime = inputTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
