package com.ctfo.upp.log;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 后端服务调用日志存储
 * */
@Entity("servicelog")
public class ServiceLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String callDate;// 调用日期
	private String ip;// 调用客户端IP
	private String clientGroupName;// 系统标识名称
	private String localIp;// 所运行在的集群ip
	private String systemName;// 调用系统名称
	private String calledClassName;// 所调用的类名
	private String calledMethodName;// 所调用类的方法名
	private String methodParamValue;// 传入的参数值
	private String calledAfterState;// 被调用后的状态 正常、失败
	private String calledAfterResult;// 被调用后的结果
	private long execTime;// 执行时间
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getCallDate() {
		return callDate;
	}



	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public String getClientGroupName() {
		return clientGroupName;
	}



	public void setClientGroupName(String clientGroupName) {
		this.clientGroupName = clientGroupName;
	}



	public String getLocalIp() {
		return localIp;
	}



	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}



	public String getSystemName() {
		return systemName;
	}



	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}



	public String getCalledClassName() {
		return calledClassName;
	}



	public void setCalledClassName(String calledClassName) {
		this.calledClassName = calledClassName;
	}



	public String getCalledMethodName() {
		return calledMethodName;
	}



	public void setCalledMethodName(String calledMethodName) {
		this.calledMethodName = calledMethodName;
	}



	public String getCalledAfterState() {
		return calledAfterState;
	}



	public void setCalledAfterState(String calledAfterState) {
		this.calledAfterState = calledAfterState;
	}



	public String getCalledAfterResult() {
		return calledAfterResult;
	}



	public void setCalledAfterResult(String calledAfterResult) {
		this.calledAfterResult = calledAfterResult;
	}



	public long getExecTime() {
		return execTime;
	}



	public void setExecTime(long execTime) {
		this.execTime = execTime;
	}


	public String getMethodParamValue() {
		return methodParamValue;
	}


	public void setMethodParamValue(String methodParamValue) {
		this.methodParamValue = methodParamValue;
	}


	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
				this.getCallDate(), this.getIp(), this.getClientGroupName(),
				this.getLocalIp(),this.getSystemName(), this.getCalledClassName(),
				this.getCalledMethodName(), this.getMethodParamValue(),
				this.getCalledAfterState(), this.getCalledAfterResult(),
				this.getExecTime());
	}
}
