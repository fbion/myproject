package com.ctfo.upp.gateway.fastpay.pc.yibao.response.domain;

import com.ctfo.upp.gateway.fastpay.pc.base.domain.PCDomain;

public class YBFastPayRequestResponseDomain extends PCDomain {
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
