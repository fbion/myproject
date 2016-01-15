package com.ctfo.upp.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.root.utils.TimeHandleUtil;
import com.ctfo.upp.service.transferBatchSynch.ITransferBatchSyncSercive;

@Scope("prototype")
@Controller
@RequestMapping(value = "/accountContrast")
public class AccountContrastController {
	private static Log logger = LogFactory.getLog(AccountContrastController.class);
	@Autowired
	private ITransferBatchSyncSercive transferBatchSyncSercive;
	
	@RequestMapping(value = "/accountReconciliationByTime.action")
	public void accountReconciliationByTime(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		try {
			String sendDate = request.getParameter("sendDate");
			String startTime = TimeHandleUtil.getTimesmorning(sendDate).toString();
			String endTime =TimeHandleUtil.getTimesnight(sendDate).toString();
			
			response.getWriter().print("Commit SUCCESS");
			
			transferBatchSyncSercive.accountReconciliationByTime(startTime, endTime, PayDict.PLATFORM_CODE_YEE_PAY);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.getWriter().print("FAIL");
		}
	}
}
