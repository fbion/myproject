package com.ctfo.upp.encryptkey;

import java.io.Serializable;

public class KeyBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String merchantCode;
	
	private String privateKey;
	
	private String publicKey;
	
	private String status;
	
	private String merchantName;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
}
