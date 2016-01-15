package com.ctfo.upp.gateway.fastpay.pc.yibao.response.domain;

import com.ctfo.upp.gateway.fastpay.pc.base.domain.PCDomain;

public class YBFastPayCallbackUPPResponseDomain extends PCDomain {
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
