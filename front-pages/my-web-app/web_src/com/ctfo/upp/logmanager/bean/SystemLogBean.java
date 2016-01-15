package com.ctfo.upp.logmanager.bean;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value="system_logs", noClassnameStored=true)
public class SystemLogBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String ip;
	private String systemName;
	private String VMName;
	private String thread;
	private String logTime;
	private String level;
	private String message;
	private String location;
	private String stackTrace;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getVMName() {
		return VMName;
	}
	public void setVMName(String vMName) {
		VMName = vMName;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	@Override
	public String toString() {
		return "SystemLogBean [id=" + id + ", ip=" + ip + ", systemName="
				+ systemName + ", VMName=" + VMName + ", thread=" + thread
				+ ", logTime=" + logTime + ", level=" + level + ", message="
				+ message + ", location=" + location + ", stackTrace="
				+ stackTrace + "]";
	}
	public SystemLogBean() {}

}
