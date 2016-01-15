package com.ctfo.upp.gatewayservice.service.base.intf;

import java.util.List;

import com.ctfo.upp.gatewayservice.bean.mongoDB.log.CallBackToBusinessLog;
import com.ctfo.upp.gatewayservice.bean.mongoDB.log.FacadeLog;

public interface LogService {
	
	public void saveFacadeLog(FacadeLog facadeLog);
	
	public String saveYBLog(String command, String type, String requestContent, String responseContent, String requestTime, String responseTime);

	public void saveCallBackToBusinessLog(CallBackToBusinessLog log);

	List<CallBackToBusinessLog> query(
			CallBackToBusinessLog callBackToBusinessLog, Long startTime,
			Long endTime) throws Exception;

	public void saveReponseObject(Object object) throws Exception;
}
