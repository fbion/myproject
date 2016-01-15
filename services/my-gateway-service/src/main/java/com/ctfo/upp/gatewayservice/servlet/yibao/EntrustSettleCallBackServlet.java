package com.ctfo.upp.gatewayservice.servlet.yibao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.gatewayservice.bean.yibao.callback.entrustsettle.TransferNotifyRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.callback.entrustsettle.TransferNotifyResponse;
import com.ctfo.upp.gatewayservice.service.base.intf.LogService;
import com.ctfo.upp.gatewayservice.service.yibao.impl.BaseServiceImpl;
import com.ctfo.upp.gatewayservice.service.yibao.intf.DistributeServiceYB;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.gatewayservice.util.yibao.PareseObjectParameter;
import com.ctfo.upp.utils.SpringBUtils;

public class EntrustSettleCallBackServlet extends HttpServlet {
	
private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory.getLog(EntrustSettleCallBackServlet.class);
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info(">>>>>>>>>> 正在接收易宝委托结算的回调信息  <<<<<<<<<<");
			
			String content = this.getRequestStream(request);
			
			logger.debug(">>>>>>>>>> 委托结算回调信息内容：" + content + " <<<<<<<<<<");
			
			TransferNotifyRequest bean = new TransferNotifyRequest();
			PareseObjectParameter.convertXMLToObject(content, bean);
			
			TransferNotifyResponse responseBean = new TransferNotifyResponse();
			responseBean.setCmd(bean.getCmd());
			responseBean.setMer_Id(bean.getMer_Id());
			responseBean.setBatch_No(bean.getBatch_No());
			responseBean.setOrder_Id(bean.getOrder_Id());
			responseBean.setRet_Code("S");
			responseBean.setHmac(BaseServiceImpl.createHMAC(responseBean, 2));
			try {
				ServletOutputStream outstream = response.getOutputStream();
				outstream.write(PareseObjectParameter.convertObjectToXML(responseBean).getBytes());
				outstream.close();
			} catch (IOException e) {
				logger.error(">>>>>>>>>> 确认收到回写委托结算失败：" + e);
				return;
			}
			
			LogService logService = (LogService) SpringBUtils.getBean("logService");
			logService.saveYBLog(bean.getCmd(), "2", "", content.toString(), "", String.valueOf(new Date().getTime()));
			
			this.responseByCommand(bean);
			
			logger.debug(">>>>>>>>>> 接收易宝的委托结算回调信息结束  <<<<<<<<<<");
		} catch (Exception e) {
			logger.error(">>>>>>>>>> 接收委托结算回调信息发生错误：", e);
		}
	}
	
	private void responseByCommand(TransferNotifyRequest bean){
		try {
			if(!bean.getMer_Id().equals(ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID"))){
				throw new Exception(">>>>>>>>>> 回调信息中的商户号并非我们的商户号  <<<<<<<<<<");
			}
			DistributeServiceYB distributeServiceYB = (DistributeServiceYB) SpringBUtils.getBean("distributeServiceYB");
			distributeServiceYB.distributeResponse(bean, 2);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(">>>>>>>>>> 根据command分发委托结算回调请求发生错误  <<<<<<<<<<", e);
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

	private String getRequestStream(HttpServletRequest request) throws Exception {
		StringBuffer result = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(
				request.getInputStream());
		BufferedReader in = new BufferedReader(isr);
		String inputLine;
		while (null != (inputLine = in.readLine())) {
			result.append(inputLine);
		}
		in.close();
		return new String(result.toString().getBytes(), "GBK");
	}
}
