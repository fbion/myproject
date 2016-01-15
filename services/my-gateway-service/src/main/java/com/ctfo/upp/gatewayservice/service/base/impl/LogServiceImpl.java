package com.ctfo.upp.gatewayservice.service.base.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.gatewayservice.bean.mongoDB.log.CallBackToBusinessLog;
import com.ctfo.upp.gatewayservice.bean.mongoDB.log.FacadeLog;
import com.ctfo.upp.gatewayservice.bean.mongoDB.log.YBLog;
import com.ctfo.upp.gatewayservice.service.base.intf.LogService;
import com.ctfo.upp.root.utils.UUIDUtil;
import com.google.code.morphia.query.Query;

@Service("logService")
public class LogServiceImpl implements LogService {
	
	protected static final Log log = LogFactory.getLog(LogServiceImpl.class);
	
	@Autowired(required=false)
	private IMongoService mongoService;
	
	public LogServiceImpl(){
		
	}
	
	@Override
	public void saveFacadeLog(FacadeLog facadeLog) {
		// TODO Auto-generated method stub
		try {
			mongoService.setDatasource("LOGS");
			mongoService.save(facadeLog);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(">>>>>>>>>> 记录facade日志发生错误  <<<<<<<<<<", e);
		}
	}
	@Override
	public void saveReponseObject(Object object)throws Exception{
		mongoService.setDatasource("LOGS");
		mongoService.save(object);
	}
	
	@Override
	public String saveYBLog(String command, String type, String requestContent,
			String responseContent, String requestTime, String responseTime) {
		// TODO Auto-generated method stub
		try {
			mongoService.setDatasource("LOGS");
			
			YBLog l = new YBLog();
			l.setId(UUIDUtil.newUUID2());
			l.setName(command);
			l.setType(type);
			l.setRequestTime(requestTime);
			l.setResponseTime(responseTime);
			l.setRequest(requestContent);
			l.setResponse(responseContent);
			mongoService.save(l);
			return l.getId();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(">>>>>>>>>> 记录易宝交互日志发生错误  <<<<<<<<<<", e);
			return null;
		}
	}
	
	@Override
	public void saveCallBackToBusinessLog(CallBackToBusinessLog l) {
		// TODO Auto-generated method stub
		mongoService.setDatasource("LOGS");
		try {
			mongoService.save(l);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(">>>>>>>>>> 记录回调给业务系统的日志发生错误  <<<<<<<<<<", e);
		}
	}
	@Override
	public List<CallBackToBusinessLog> query(CallBackToBusinessLog callBackToBusinessLog,Long startTime,Long endTime) throws Exception{
		Query query =mongoService.getQuery(CallBackToBusinessLog.class);
		if(startTime!=null){
			query.field("time").greaterThanOrEq(startTime.toString());
			query.field("time").greaterThanOrEq(endTime.toString());
		}
		query.field("status").equal(callBackToBusinessLog.getStatus());
		List<CallBackToBusinessLog> list=mongoService.query(CallBackToBusinessLog.class, query);
		return list;
	}
	
	public IMongoService getMongoService() {
		return mongoService;
	}

	public void setMongoService(IMongoService mongoService) {
		this.mongoService = mongoService;
	}
}
