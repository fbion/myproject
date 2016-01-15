package com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain;

import com.ctfo.upp.gateway.fastpay.mobile.base.domain.MobileDomain;

public class YBFastPayRequestResponseDomain extends MobileDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 跳转url
	private String jumpURL;

	public String getJumpURL() {
		return jumpURL;
	}

	public void setJumpURL(String jumpURL) {
		this.jumpURL = jumpURL;
	}

}
