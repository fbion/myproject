package com.ctfo.upp.service.sms.bean;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 短信模板实体
 * 
 * @author jichao
 */
@Entity(value = "Sms", noClassnameStored = true)
public class Sms implements Serializable{

	private static final long serialVersionUID = 1L;

	public Sms() {
	}

	/**
	 * 模板编码
	 */
	@Id
	private String templateCode;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 内容（参数标识：<_>，短信拆分标识：>_<）
	 */
	private String templateContent;

	/**
	 * 状态(启用/禁用)
	 */
	private int status;

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
