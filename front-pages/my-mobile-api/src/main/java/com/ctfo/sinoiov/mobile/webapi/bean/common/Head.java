package com.ctfo.sinoiov.mobile.webapi.bean.common;

/**
 * 参数头解析对象
 * 
 * @author wangpeng
 */
public class Head implements java.io.Serializable {
	private static final long serialVersionUID = 927427642571559938L;

	/**
	 * 服务接口编码
	 */
	private String servCode;

	/**
	 * 调用序列码（调用对应回传序列码）
	 */
	private String sequenceNum;

	/**
	 * 调用时间
	 */
	private String callTime;

	/**
	 * 返回结果
	 */
	private String result;

	/**
	 * 错误信息
	 */
	private String errorMessage;

	/**
	 * 类型 ios android ---已经转小写
	 */
	private String callSource;

	public String getCallSource() {
		return callSource;
	}

	public void setCallSource(String callSource) {
		this.callSource = callSource.toLowerCase();
	}

	public String getServCode() {
		return servCode;
	}

	public void setServCode(String servCode) {
		this.servCode = servCode;
	}

	public String getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(String sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
