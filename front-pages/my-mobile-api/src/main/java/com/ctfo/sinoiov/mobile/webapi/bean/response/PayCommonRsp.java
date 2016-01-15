package com.ctfo.sinoiov.mobile.webapi.bean.response;

import java.io.Serializable;

import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.common.PublicStaticParam;

/**
 * 支付公共返回对象
 * 
 * @author sunchuanfu
 */
public class PayCommonRsp implements Body, Serializable {
	private static final long serialVersionUID = -6928055581863903870L;

	private String result = PublicStaticParam.RESULT_FAIL;// 0成功;1失败

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
