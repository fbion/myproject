package com.ctfo.sinoiov.mobile.webapi.bean.vo;

/**
 * 支付系统返回对象
 * 
 * @author sunchuanfu
 */
public class UppResult {

	public static String SUCCESS = "1";

	public static String FAILURE = "-1";

	/**
	 * 成功失败标志位
	 */
	private String result;

	/**
	 * 返回数据
	 */
	private Object data;

	/**
	 * 本网关系统的商户编码
	 */
	private String merchantcode;

	/**
	 * 错误信息
	 */
	private String error;

	/**
	 * 错误编码
	 */
	private String errorcode;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMerchantcode() {
		return merchantcode;
	}

	public void setMerchantcode(String merchantcode) {
		this.merchantcode = merchantcode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

}
