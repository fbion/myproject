package com.ctfo.sinoiov.mobile.webapi.bean.response;

/**
 * 使用账户余额对油卡进行充值rsp
 * 
 * @author sunchuanfu
 */
public class OilRechargeByAccountRsp extends BaseBeanRsp {
	private static final long serialVersionUID = 1438638232161776150L;

	private String result; // 充值确认结果

	private String payConfirmDate; // 支付确认时间

	private String fcallbackUrl; // 跳业务系统前台地址

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPayConfirmDate() {
		return payConfirmDate;
	}

	public void setPayConfirmDate(String payConfirmDate) {
		this.payConfirmDate = payConfirmDate;
	}

	public String getFcallbackUrl() {
		return fcallbackUrl;
	}

	public void setFcallbackUrl(String fcallbackUrl) {
		this.fcallbackUrl = fcallbackUrl;
	}

}
