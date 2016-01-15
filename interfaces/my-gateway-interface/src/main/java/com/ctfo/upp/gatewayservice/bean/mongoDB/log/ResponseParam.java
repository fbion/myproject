package com.ctfo.upp.gatewayservice.bean.mongoDB.log;

import java.io.Serializable;
import java.util.Map;

import com.google.code.morphia.annotations.Id;

public class ResponseParam implements Serializable{
	@Id
	private String id;
	private Map<String,String> commandMap;
	private String logId;
	private String command;
	private String specialSign;
	

	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getSpecialSign() {
		return specialSign;
	}
	public void setSpecialSign(String specialSign) {
		this.specialSign = specialSign;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, String> getCommandMap() {
		return commandMap;
	}
	public void setCommandMap(Map<String, String> commandMap) {
		this.commandMap = commandMap;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	
}
