package com.ctfo.sinoiov.mobile.webapi.exception;

/**
 * 返回给客户端异常类
 * 
 * @author tommy
 */
public class ClientException extends RuntimeException {
	private static final long serialVersionUID = 1687064926325206530L;
	protected String errorCode;
	protected String message;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ClientException() {
		super("ClientException");
	}

	public ClientException(String errorCode) {
		this.errorCode = errorCode;
	}

	public ClientException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public ClientException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.message = message;
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

	public static void throwExcetion(ClientException exception) {
		throw exception;
	}

}
