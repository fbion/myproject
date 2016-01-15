package com.ctfo.upp.security;

import java.io.Serializable;

public class ParamJsonBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业务密文
	 */
	private String data;
	/**
	 * 密钥密文
	 */
	private String encryptkey;
	/**
	 * 商户编号
	 */
	private String merchantCode;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getEncryptkey() {
		return encryptkey;
	}

	public void setEncryptkey(String encryptkey) {
		this.encryptkey = encryptkey;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	

}
