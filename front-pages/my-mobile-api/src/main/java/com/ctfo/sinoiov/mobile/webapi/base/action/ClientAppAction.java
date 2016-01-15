package com.ctfo.sinoiov.mobile.webapi.base.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ctfo.sinoiov.mobile.webapi.base.intf.ICallService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 客户端app调用接口入口
 */
@Controller()
@Namespaces({ @Namespace("/business"), @Namespace("/trade") })
@Scope("prototype")
@Action("/call")
public class ClientAppAction extends ActionSupport {
	private static final long serialVersionUID = -1940819445399135681L;
	private static Log logger = LogFactory.getLog(ClientAppAction.class);

	/**
	 * 客户端上传参数
	 */
	private String args;

	@Autowired
	@Qualifier("ICallServiceMap")
	private ICallService callService;

	private String resultStr = null;

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		MultiPartRequestWrapper wrapper = null;
		File[] files = null;
		String[] fileNames = null;
		// 判断是否有附件(图片等)
		if (request instanceof MultiPartRequestWrapper) {
			wrapper = (MultiPartRequestWrapper) request;
			files = wrapper.getFiles("file");
			fileNames = wrapper.getFileNames("file");
		}

		logger.info(">>>>>>>>>>>>>>>>>手机APP调用开始>>>>>>>>>>>>>>>>>>>>" + request.getParameter("fileName"));
		args = this.getRequestStream(request);
		logger.info(">>>>>>>>>>>>>>>>>接收参数：【" + args + "】<<<<<<<<<<<<<<<<<<<<");
		// 调用业务接口，获取结果字符串
		resultStr = callService.call(args, files, fileNames);
		logger.info(">>>>>>>>>>>>>>>>>返回结果：【" + resultStr + "】<<<<<<<<<<<<<<<<<<<<");
		// 返回业务数据
		HttpServletResponse response = ServletActionContext.getResponse();
		sendResponseJson(resultStr, response);
		logger.info(">>>>>>>>>>>>>>>>>手机APP调用完毕>>>>>>>>>>>>>>>>>>>>");
		return null;
	}

	/**
	 * 获取请求参数转换json
	 */
	private String getRequestStream(HttpServletRequest request) {
		try {
			StringBuffer result = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(request.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				result.append(inputLine);
			}
			in.close();
			String resultStr = new String(result.toString().getBytes(), "utf-8");
			logger.debug("以Stream方式读取request中的参数：" + resultStr);
			if (resultStr.indexOf("param=") >= 0) {
				return resultStr.substring(resultStr.indexOf("=") + 1);
			} else {
				return request.getParameter("param");
			}
		} catch (IOException e) {
			return request.getParameter("param");
		}
	}

	/**
	 * 发送以json方式返回客户端
	 */
	private void sendResponseJson(String resultStr, HttpServletResponse response) {
		ServletOutputStream outstream;
		try {
			outstream = response.getOutputStream();
			outstream.write(resultStr.getBytes());
			outstream.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
}
