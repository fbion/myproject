package com.ctfo.upp.gatewayservice.servlet.yibao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.gatewayservice.bean.mongoDB.log.CallBackToBusinessLog;
import com.ctfo.upp.gatewayservice.service.base.intf.LogService;
import com.ctfo.upp.utils.SpringBUtils;

public class BusinessCallBackServlet extends HttpServlet {
	
	private static final Log logger = LogFactory.getLog(BusinessCallBackServlet.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String taskId = req.getParameter("taskId");
		String status = req.getParameter("status");
		logger.info("------------------ 接收到" + this.getRequestIp(req) + "的调用请求:taskId=" + taskId + "&status=" + status + " -------------------");
		if(!status.equals("0") && !status.equals("1")){
			logger.info("------------------ 参数status内容有误【" + status + "】 -------------------");
		}
		
		LogService logService = (LogService) SpringBUtils.getBean("logService");
		CallBackToBusinessLog l = new CallBackToBusinessLog();
		l.setId(taskId);
		if(status.equals("0")){
			l.setStatus("3");
		}
		if(status.equals("1")){
			l.setStatus("4");
		}
		logService.saveCallBackToBusinessLog(l);
	}
	
	private String getRequestIp(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
