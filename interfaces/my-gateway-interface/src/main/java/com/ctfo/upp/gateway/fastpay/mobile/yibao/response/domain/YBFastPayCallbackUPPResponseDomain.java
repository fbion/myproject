package com.ctfo.upp.gateway.fastpay.mobile.yibao.response.domain;

import com.ctfo.upp.gateway.fastpay.mobile.base.domain.MobileDomain;

public class YBFastPayCallbackUPPResponseDomain extends MobileDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean success;

	private String responseText;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

}
