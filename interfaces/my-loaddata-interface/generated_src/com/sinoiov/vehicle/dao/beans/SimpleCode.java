package com.sinoiov.vehicle.dao.beans;

import com.sinoiov.beans.BaseSerializable;
import javax.xml.bind.annotation.XmlElement;

public class SimpleCode extends BaseSerializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_SIMPLE_CODE.ID DB Comment: 主键
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	@XmlElement(name = "主键", required = false)
	private String id;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_SIMPLE_CODE.TYPE_CODE DB Comment: 类型代码
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	@XmlElement(name = "类型代码", required = false)
	private String typeCode;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_SIMPLE_CODE.TYPE_NAME DB Comment: 类型名称
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	@XmlElement(name = "类型名称", required = false)
	private String typeName;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_SIMPLE_CODE.CODE DB Comment: 编码
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	@XmlElement(name = "编码", required = false)
	private String code;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_SIMPLE_CODE.NAME DB Comment: 名称
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	@XmlElement(name = "名称", required = false)
	private String name;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_SIMPLE_CODE.LEVEL DB Comment: 级别
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	@XmlElement(name = "级别", required = false)
	private String level;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_SIMPLE_CODE.REMARK DB Comment: 备注
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	@XmlElement(name = "备注", required = false)
	private String remark;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_SIMPLE_CODE.PID DB Comment: 上级主键
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	@XmlElement(name = "上级主键", required = false)
	private String pid;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_SIMPLE_CODE.ID
	 * @return  the value of TB_SIMPLE_CODE.ID
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_SIMPLE_CODE.ID
	 * @param id  the value for TB_SIMPLE_CODE.ID
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String fieldId() {
		return "ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_SIMPLE_CODE.TYPE_CODE
	 * @return  the value of TB_SIMPLE_CODE.TYPE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_SIMPLE_CODE.TYPE_CODE
	 * @param typeCode  the value for TB_SIMPLE_CODE.TYPE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String fieldTypeCode() {
		return "TYPE_CODE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_SIMPLE_CODE.TYPE_NAME
	 * @return  the value of TB_SIMPLE_CODE.TYPE_NAME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_SIMPLE_CODE.TYPE_NAME
	 * @param typeName  the value for TB_SIMPLE_CODE.TYPE_NAME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String fieldTypeName() {
		return "TYPE_NAME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_SIMPLE_CODE.CODE
	 * @return  the value of TB_SIMPLE_CODE.CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getCode() {
		return code;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_SIMPLE_CODE.CODE
	 * @param code  the value for TB_SIMPLE_CODE.CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String fieldCode() {
		return "CODE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_SIMPLE_CODE.NAME
	 * @return  the value of TB_SIMPLE_CODE.NAME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_SIMPLE_CODE.NAME
	 * @param name  the value for TB_SIMPLE_CODE.NAME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String fieldName() {
		return "NAME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_SIMPLE_CODE.LEVEL
	 * @return  the value of TB_SIMPLE_CODE.LEVEL
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_SIMPLE_CODE.LEVEL
	 * @param level  the value for TB_SIMPLE_CODE.LEVEL
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String fieldLevel() {
		return "LEVEL";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_SIMPLE_CODE.REMARK
	 * @return  the value of TB_SIMPLE_CODE.REMARK
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_SIMPLE_CODE.REMARK
	 * @param remark  the value for TB_SIMPLE_CODE.REMARK
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String fieldRemark() {
		return "REMARK";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_SIMPLE_CODE.PID
	 * @return  the value of TB_SIMPLE_CODE.PID
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_SIMPLE_CODE.PID
	 * @param pid  the value for TB_SIMPLE_CODE.PID
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String fieldPid() {
		return "PID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public SimpleCode() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String tableName() {
		return "TB_SIMPLE_CODE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String daoInterface() {
		return "com.sinoiov.vehicle.dao.SimpleCodeDAO";
	}
}