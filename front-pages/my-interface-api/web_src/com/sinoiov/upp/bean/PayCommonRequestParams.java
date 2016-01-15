package com.sinoiov.upp.bean;

/**
 * 支付公用请求参数封装bean
 * 
 * @author sunchuanfu
 */
public class PayCommonRequestParams {
	private String data;// 加密后的参数数据
	private String encryptkey;// 商户公钥
	private String merchantcode;// 商户编码

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

	public String getMerchantcode() {
		return merchantcode;
	}

	public void setMerchantcode(String merchantcode) {
		this.merchantcode = merchantcode;
	}

}
