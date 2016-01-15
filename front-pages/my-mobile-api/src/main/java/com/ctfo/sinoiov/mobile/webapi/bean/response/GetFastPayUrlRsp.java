package com.ctfo.sinoiov.mobile.webapi.bean.response;

import java.io.Serializable;

import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;

/**
 * 获取手机快捷支付URL地址返回对象（用于获取油卡或者账户快捷支付）
 * 
 * @author sunchuanfu
 */
public class GetFastPayUrlRsp implements Body, Serializable {
	private static final long serialVersionUID = -666272536591451676L;

	private String url;// 手机快捷支付URL
	private String orderNo;// 订单号
	private String payConfirmDate;// 确认时间（时间戳）

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayConfirmDate() {
		return payConfirmDate;
	}

	public void setPayConfirmDate(String payConfirmDate) {
		this.payConfirmDate = payConfirmDate;
	}

}
