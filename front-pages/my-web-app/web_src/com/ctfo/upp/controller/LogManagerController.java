package com.ctfo.upp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.csm.local.DynamicSqlParameter;
import com.ctfo.upp.log.WebApiServiceLog;
import com.ctfo.upp.logmanager.bean.LogSet;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.logmanager.ILogManagerService;

@Controller
@Scope("prototype")
@RequestMapping("/logmanager")
public class LogManagerController extends BaseController{
	
	private LogManagerController(){
			model = new LogSet();
	}
	
	@Autowired
	private ILogManagerService logManagerService;
	
	@RequestMapping(value="/queryInteractiveRecord.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public PaginationResult<?> queryInteractiveRecord(DynamicSqlParameter requestParam){
		
		PaginationResult<?> queryInteractiveRecord = null;
		
		try {
			queryInteractiveRecord = logManagerService.queryInteractiveRecord(requestParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryInteractiveRecord;
	}
	
	@RequestMapping(value="/queryInteractiveRecordDetail.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public List queryInteractiveRecordDetail(HttpServletRequest request){
		
		DynamicSqlParameter requestParam = new DynamicSqlParameter();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		requestParam.setEqual(map);
		requestParam.setPage(0);
		requestParam.setRows(10);
		List queryInteractiveRecordDetail=null;
		try {
			queryInteractiveRecordDetail = logManagerService.queryInteractiveRecordDetail(requestParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryInteractiveRecordDetail;
	}
	
	@RequestMapping(value="/queryTL_YB_log.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public PaginationResult<?> queryTL_YB_log(DynamicSqlParameter requestParam){
	PaginationResult<?> queryInteractiveRecord = null;
		
		try {
			queryInteractiveRecord = logManagerService.queryTL_YB_log(requestParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryInteractiveRecord;
		
	}
	@RequestMapping(value="/queryTL_YB_logDetail.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public List queryTL_YB_logDetail(HttpServletRequest request){
		
		DynamicSqlParameter requestParam = new DynamicSqlParameter();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		requestParam.setEqual(map);
		requestParam.setPage(0);
		requestParam.setRows(10);
		List queryTL_YB_logDetail=null;
		try {
			queryTL_YB_logDetail = logManagerService.queryTL_YB_logDetail(requestParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryTL_YB_logDetail;
	}
	
	@RequestMapping(value="/querySystemLog.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public PaginationResult<?> querySystemLog(DynamicSqlParameter requestParam){
	PaginationResult<?> querySystemLog = null;
		
		try {
			querySystemLog = logManagerService.querySystemLog(requestParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return querySystemLog;
		
	}

	@RequestMapping(value="/querySystemLogSetList.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public PaginationResult<?> querySystemLogSetList(){
		PaginationResult<?> result = null;
		
		try {
			
			List list = logManagerService.querySystemLogSetList();
			result = new PaginationResult();
			result.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	@RequestMapping(value="/saveOrUpdateLogSet.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public String saveOrUpdateLogSet(){

		try {
			
			logManagerService.saveOrUpdateLogSet((LogSet) model);
			
			return "操作成功";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "操作失败";
		
	}
	@RequestMapping(value="/deleteLogs.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteLogs(String dbName, String dateStr, String systemName, String level){
		try {
			
			logManagerService.deleteLogs(dbName, dateStr, systemName, level);
			
			return "操作成功";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作失败";
	}
	@RequestMapping(value="/deleteInterfaceServiceLogs.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteInterfaceServiceLogs(String dateStr, String operCode){
		try {	
			logManagerService.deleteInterfaceServiceLogs(dateStr, operCode);		
			return "操作成功";			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作失败";
	}
	@RequestMapping(value="/deleteGatewayServiceLogs.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteGatewayServiceLogs(String dateStr, String name, String type){
		try {
			
			logManagerService.deleteGatewayServiceLogs(dateStr, name, type);
			
			return "操作成功";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作失败";	
	}
	@RequestMapping(value="/deleteMobileApiServiceLogs.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteMobileApiServiceLogs(String dateStr, String code, String status){
		try {
			
			logManagerService.deleteMobileApiServiceLogs(dateStr, code, status);
			
			return "操作成功";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作失败";	
	}
	
	
	@RequestMapping(value="/querySystemLogDetail.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public List querySystemLogDetail(HttpServletRequest request){
		String id = request.getParameter("id");
		List querySystemLogDetail=null;
		try {
			querySystemLogDetail = logManagerService.querySystemLogDetail(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return querySystemLogDetail;
	}
	
	@RequestMapping(value="/queryMobileLog.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public PaginationResult<?> queryMobileLog(DynamicSqlParameter requestParam){
		PaginationResult<?> queryMobileLog = null;
		try {
			queryMobileLog = logManagerService.queryMobileLog(requestParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryMobileLog;
	}
	@RequestMapping(value="/queryMobileLogDetail.action" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public WebApiServiceLog queryMobileLogDetail(HttpServletRequest request){
		WebApiServiceLog mobileLogDetail=null;
		try {
			String id = request.getParameter("id");
			mobileLogDetail = logManagerService.queryMobileLogDetail(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mobileLogDetail;
	}
}
