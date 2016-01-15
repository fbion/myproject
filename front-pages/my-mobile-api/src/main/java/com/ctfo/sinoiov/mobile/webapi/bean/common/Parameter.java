package com.ctfo.sinoiov.mobile.webapi.bean.common;

/**
 * 客户端调用参数对象
 * 
 * @author wangpeng
 */
public class Parameter implements java.io.Serializable {

	private static final long serialVersionUID = 5305884151577731858L;

	/**
	 * 参数头信息
	 */
	private Head head;

	/**
	 * 参数体信息
	 */
	private Object body;

	/**
	 * 签名
	 */
	private String sign;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
