package com.ctfo.upp.gateway.fastpay.utils.httpclient;

import java.io.UnsupportedEncodingException;

public class HttpResp {

	private int statusCode;

	private String text;

	private byte[] bytes;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getText(String charset) {
		if (text == null || text.isEmpty()) {
			try {
				text = new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return text;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
