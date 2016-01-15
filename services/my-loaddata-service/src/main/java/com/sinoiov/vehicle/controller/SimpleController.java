package com.sinoiov.vehicle.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinoiov.vehicle.ISimpleCodeService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/simpleCode")
public class SimpleController {
	
	private static Log logger = LogFactory.getLog(SimpleController.class);
	
	@Autowired
	private ISimpleCodeService simpleCodeService;
	
	
	@RequestMapping(value = "/queryList", produces = "text/plain;charset=UTF-8")
	public String loadVehicleData(HttpServletResponse response, CommonRequestParams params){
		
		try {
			String start = params.getStart();
			String dataType = params.getDataType();
			simpleCodeService.loadVihecleData(dataType, start);
			
		} catch (Exception e) {
			logger.error("下载车辆数据异常", e);
		}
		return "SUCCESS";
	}

}
