package com.ctfo.upp.gatewayservice.intercepter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.impl.multithread.TaskPool;

@Component("fastPayLoggerAdvisor")
public class FastPayFacadeAdvisorLogger extends FacadeAdvisorHandler {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		String className = invocation.getThis().getClass().getCanonicalName();
		String methodName = invocation.getMethod().getName();
		Object[] args = invocation.getArguments();
		Object result = null;
		String requestTime = "", responseTime = "";
		try {
			requestTime = String.valueOf(new Date().getTime());
			result = invocation.proceed();
			responseTime = String.valueOf(new Date().getTime());
		} catch (Exception e) {
			// TODO: handle exception
			log.debug("记录一键支付Service Facade访问日志时产生错误，原因：", e);
			final StringWriter stackTrace = new StringWriter();
			e.printStackTrace(new PrintWriter(stackTrace));
			result = String.format("调用失败,原因：%s", stackTrace.toString());
			throw e;
		} finally {
			TaskPool.named("__facade_advisor_logger_task_pool__").setPoolSize(Integer.valueOf(ConfigUtil.getValue("FACADE_ADVISOR_HANDLER_THREAD_NUM")).intValue()).submit(new LoggingFacade(className, methodName, args, result, requestTime, responseTime));
		}
		return result;
	}

}
