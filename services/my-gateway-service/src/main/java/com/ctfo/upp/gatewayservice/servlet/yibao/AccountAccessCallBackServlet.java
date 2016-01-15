package com.ctfo.upp.gatewayservice.servlet.yibao;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.gatewayservice.bean.mongoDB.log.ResponseParam;
import com.ctfo.upp.gatewayservice.service.base.intf.LogService;
import com.ctfo.upp.gatewayservice.service.yibao.intf.DistributeServiceYB;
import com.ctfo.upp.gatewayservice.util.yibao.Constants;
import com.ctfo.upp.gatewayservice.util.yibao.PareseObjectParameter;
import com.ctfo.upp.utils.SpringBUtils;

public class AccountAccessCallBackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory.getLog(AccountAccessCallBackServlet.class);
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info(">>>>>>>>>> 正在接收易宝账户通的回调信息  <<<<<<<<<<");
			
			Map<String, String> content = this.getRequestStream(request);
			
			logger.info(">>>>>>>>>> 账户通回调信息内容：" + content + " <<<<<<<<<<");
			
			try {
				ServletOutputStream outstream = response.getOutputStream();
				outstream.write("success".getBytes());
				outstream.close();
			} catch (IOException e) {
				logger.error(">>>>>>>>>> 确认收到回写账户通失败：" + e);
				return;
			}
			
			this.responseByCommand(content);
			
			logger.debug(">>>>>>>>>> 接收易宝的账户通回调信息结束  <<<<<<<<<<");
		} catch (Exception e) {
			logger.error(">>>>>>>>>> 接收账户通回调信息发生错误：", e);
		}
	}
	
	private void responseByCommand(Map<String, String> content){
		LogService logService = (LogService) SpringBUtils.getBean("logService");
		DistributeServiceYB distributeServiceYB = (DistributeServiceYB) SpringBUtils.getBean("distributeServiceYB");
		try {
			
			String command = content.get("command");
			logger.info("收到回调，command:"+command);
			String logId =logService.saveYBLog(command, "2", "", content.toString(), "", String.valueOf(new Date().getTime()));
			if(command.equals("CheckAccountCallBack")){
				ResponseParam resparam =new ResponseParam();
				resparam.setId(java.util.UUID.randomUUID().toString());
				resparam.setCommandMap(content);
				resparam.setLogId(logId);			
				resparam.setCommand(command);
				resparam.setSpecialSign(content.get("syncDate"));
				logService.saveReponseObject(resparam);
			}
			Object bean = PareseObjectParameter.pareseStringToObject2(content, Constants.callBackFromYibaoCommandToBean.get(command));

			distributeServiceYB.distributeResponse(bean, 1);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(">>>>>>>>>> 根据command分发账户通回调请求发生错误  <<<<<<<<<<", e);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private Map<String, String> getRequestStream(HttpServletRequest request) throws Exception {
		Map<String, String> resultStr = new HashMap<String, String>();
		Map<String, String[]> params = request.getParameterMap();
		Set<String> s = params.keySet();
		Iterator<String> it = s.iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = params.get(key)[0];
			resultStr.put(key, value);
		}
		return resultStr;
	}
}

