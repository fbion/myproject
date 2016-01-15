package com.ctfo.upp.gatewayservice.service.base.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.gatewayservice.bean.mongoDB.log.YBLog;
import com.ctfo.upp.root.utils.UUIDUtil;
import com.ctfo.upp.utils.SpringBUtils;

public abstract class LogServiceUtils {
	
	protected static final Log log = LogFactory.getLog(LogServiceUtils.class);
			
	private static IMongoService mongoService = null;
	
	public static String saveYBLog(String command, String type, String requestContent,
			String responseContent, String requestTime, String responseTime){
		try {
				
			YBLog l = new YBLog();
			l.setId(UUIDUtil.newUUID2());
			l.setName(command);
			l.setType(type);
			l.setRequestTime(requestTime);
			l.setResponseTime(responseTime);
			l.setRequest(requestContent);
			l.setResponse(responseContent);
			
			if(mongoService==null){
				mongoService = (IMongoService)SpringBUtils.getBean("mongoService");
				mongoService.setDatasource("LOGS");
			}
			mongoService.save(l);
			return l.getId();
		} catch (Exception e) {
			log.error(">>>>>>>>>> 记录易宝交互日志发生错误  <<<<<<<<<<", e);
		}
		return null;
	}
	

}
