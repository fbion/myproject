package com.ctfo.sinoiov.mobile.webapi.bean.response;

import java.io.Serializable;

import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.common.PublicStaticParam;

/**
 * 设置密码返回对象
 * 
 * @author sunchuanfu
 */
public class SetPayPasswordRsp implements Body, Serializable {
	private static final long serialVersionUID = 5311049588573161363L;

	private String accountNo;// 如果设置密码操作前没有相应的账户，则返回新生成的账户号
	private String result = PublicStaticParam.RESULT_FAIL;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
}
