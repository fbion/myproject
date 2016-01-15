package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 验证是否有支付密码
 * 
 * @author sunchuanfu
 */
public class IsSetPayPasswordReq extends BaseBeanReq {

	private static final long serialVersionUID = -886700264718021770L;
	
	private String accountNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
}
