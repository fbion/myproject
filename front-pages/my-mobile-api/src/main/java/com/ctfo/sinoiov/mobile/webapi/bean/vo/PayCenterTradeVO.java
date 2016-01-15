/**
 * 北京中交兴路车联网科技有限公司 2011 版权所有.
 */
package com.ctfo.sinoiov.mobile.webapi.bean.vo;

import java.io.Serializable;

/**
 * 账户流水实体
 * 
 * @author dxs
 */
public class PayCenterTradeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易ID
	 */
	private String id;

	/**
	 * 交易备注
	 */
	private String orderRemarks;

	/**
	 * 交易号
	 */
	private String orderNo;

	/**
	 * 交易钱数
	 */
	private String bookAccountMoney;

	/**
	 * 交易后钱数
	 */
	private String bookCurrentMoney;

	/**
	 * 交易时间
	 */
	private String bookAccountTime;

	/**
	 * 交易类型 （支出/收入）
	 */
	private String bookAccountType;

	/**
	 * 科目类型标识
	 */
	private String orderType;

	/**
	 * 科目类型名字 转账（付款）等
	 */
	private String orderTypeLuc;

	/**
	 * 产品名称
	 */
	private String productName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBookAccountMoney() {
		return bookAccountMoney;
	}

	public void setBookAccountMoney(String bookAccountMoney) {
		this.bookAccountMoney = bookAccountMoney;
	}

	public String getBookCurrentMoney() {
		return bookCurrentMoney;
	}

	public void setBookCurrentMoney(String bookCurrentMoney) {
		this.bookCurrentMoney = bookCurrentMoney;
	}

	public String getBookAccountTime() {
		return bookAccountTime;
	}

	public void setBookAccountTime(String bookAccountTime) {
		this.bookAccountTime = bookAccountTime;
	}

	public String getBookAccountType() {
		return bookAccountType;
	}

	public void setBookAccountType(String bookAccountType) {
		this.bookAccountType = bookAccountType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeLuc() {
		return orderTypeLuc;
	}

	public void setOrderTypeLuc(String orderTypeLuc) {
		this.orderTypeLuc = orderTypeLuc;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
