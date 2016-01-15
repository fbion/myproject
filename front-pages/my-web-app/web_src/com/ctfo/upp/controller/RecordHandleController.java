package com.ctfo.upp.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.service.adjust.AdjustAccountService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/hecordHandle")
public class RecordHandleController  extends BaseController{
	private static Log logger = LogFactory.getLog(RecordHandleController.class);
	
	@Resource(name = "adjustAccountService", description = "service处理记录")
	private AdjustAccountService adjustAccountService;
	
	
	@RequestMapping(value = "/queryObject.action",produces = "application/json")
	public void queryObject(@RequestParam("objectName") String objectName) throws UPPException{
		
		try {
			
			String jStr= adjustAccountService.queryObject(objectName);
			response.getWriter().print(jStr);

		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}
	@RequestMapping(value = "/queryHeader.action",produces = "application/json")
	public void queryTableHeader(@RequestParam("objectName") String objectName) throws UPPException{
		try {
			String jStr= adjustAccountService.queryTableHeader(objectName);
			logger.debug("~~~~~~~~~~~~~queryTableHeader"+jStr);
			response.getWriter().print(jStr);

		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}
	@RequestMapping(value = "/gateWayQuery.action",produces = "application/json")
	public void gateWayQuery(@RequestParam("accountNo") String accountNo) throws UPPException{
		try {
			String jStr= adjustAccountService.gateWayQuery(accountNo);
			logger.debug("~~~~~~~~~~~~~queryTableHeader"+jStr);
			response.getWriter().print(jStr);

		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

}
