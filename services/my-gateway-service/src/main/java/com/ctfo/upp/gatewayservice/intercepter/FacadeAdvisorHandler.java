package com.ctfo.upp.gatewayservice.intercepter;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctfo.upp.gatewayservice.bean.mongoDB.log.FacadeLog;
import com.ctfo.upp.gatewayservice.service.base.intf.LogService;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.root.utils.UUIDUtil;

public class FacadeAdvisorHandler implements MethodInterceptor {
	
	protected static final Log log = LogFactory.getLog(FacadeAdvisorHandler.class);
	
	@Autowired(required=false)
	private LogService logService;
	
	private ExecutorService es = null;
	
	public FacadeAdvisorHandler(){
		if(es==null){
			es = Executors.newFixedThreadPool(Integer.valueOf(ConfigUtil.getValue("FACADE_ADVISOR_HANDLER_THREAD_NUM")).intValue());
		}
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		String clazName = invocation.getThis().getClass().getSimpleName();
		String methodName = invocation.getMethod().getName();
		Object[] args = invocation.getArguments();
		
		String requestTime = String.valueOf(new Date().getTime());
		Object result = invocation.proceed();
		String responseTime = String.valueOf(new Date().getTime());
		
		try {
			es.execute(new LoggingFacade(clazName, methodName, args, result, requestTime, responseTime));
		} catch (Exception e) {
			// TODO: handle exception
			log.error(">>>>>>>>>> 拦截记录facade日志发生错误  <<<<<<<<<<", e);
		}
		return result;
	}
	
	public class LoggingFacade implements Runnable{
		
		private String clazName;
		private String methodName;
		private Object[] args;
		private Object result;
		private String requestTime;
		private String responseTime;
		
		public LoggingFacade(String clazName, String methodName, Object[] args, Object result, String requestTime, String responseTime){
			this.clazName = clazName;
			this.methodName = methodName;
			this.args = args;
			this.result = result;
			this.requestTime = requestTime;
			this.responseTime = responseTime;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			FacadeLog log = new FacadeLog();
			log.setId(UUIDUtil.newUUID2());
			log.setClassName(clazName);
			log.setMethodName(methodName);
			log.setRequest(JSONObject.fromObject(args[0]).toString());
			log.setResponse(JSONObject.fromObject(result).toString());
			log.setRequestTime(requestTime);
			log.setResponseTime(responseTime);
			logService.saveFacadeLog(log);
		}
		
	}
	
	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
