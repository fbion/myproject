package com.ctfo.upp.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.upp.log.ServiceLog;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.ISLogManageService;

/**
 * @ClassName: LogAction
 * @Description:服务日志Action
 */
@Component
@Controller
@Scope("prototype")
@RequestMapping("/logJson")
public class SLogManageControler extends BaseController{
	private static final Log logger = LogFactory.getLog(SLogManageControler.class);
	@Resource(name="sLogService",description="日志查询接口")
	private ISLogManageService sLogService;

	@RequestMapping(value="/queryLogPage" )
	@ResponseBody
	public PaginationResult<ServiceLog>  getLogPage(DynamicSqlParameter requestParam) {
		PaginationResult<ServiceLog> result=null;
		try {
			result = this.sLogService.queryListPage(requestParam, op);
		} catch (Exception e) {
			logger.debug(String.format(
					"The query log data error, error reason:%s", e.getMessage()));
			setMessage(((e.getMessage() == null
					|| e.getMessage().isEmpty() || e.getMessage().equals(
					EMPTY_STRING)) ? "查询日志数据时产生异常!" : e.getMessage()));
		}
		return result;
	}
	
	@RequestMapping(value="/getLogById")
	@ResponseBody
	public PaginationResult<ServiceLog> getObjectById(String id) {
		PaginationResult<ServiceLog> result=null;
		try {
			result = this.sLogService.queryLogById(new ObjectId(id), op);
		} catch (Exception e) {
			setMessage(((e.getMessage() == null
					|| e.getMessage().isEmpty() || e.getMessage().equals(
					EMPTY_STRING)) ? "查询信息异常!" : e.getMessage()));
			logger.error("查询信息异常!", e);
		}
		return result;
	}
}
