package com.ctfo.upp.service.logmanager;

import java.util.List;

import com.ctfo.csm.local.DynamicSqlParameter;
import com.ctfo.upp.log.WebApiServiceLog;
import com.ctfo.upp.logmanager.bean.LogSet;
import com.ctfo.upp.models.PaginationResult;

public interface ILogManagerService {
	/**
	 * 获取接口服务日志
	 * @param requestParam
	 * @return
	 */
	public PaginationResult<?> queryInteractiveRecord(DynamicSqlParameter requestParam) throws Exception;
	/**
	 * 获取单个接口服务日志
	 * @param requestParam
	 * @return
	 */
	public List queryInteractiveRecordDetail(DynamicSqlParameter requestParam) throws Exception;
	/**
	 * 获取网关日志
	 * @param requestParam
	 * @return
	 */
	public PaginationResult<?> queryTL_YB_log(DynamicSqlParameter requestParam) throws Exception;
	/**
	 * 获取单个网关日志
	 * @param requestParam
	 * @return
	 */
	public List queryTL_YB_logDetail(DynamicSqlParameter requestParam) throws Exception;
	/**
	 * 获取系统日志
	 * @param requestParam
	 * @return
	 */
	public PaginationResult<?> querySystemLog(DynamicSqlParameter requestParam) throws Exception;
	/**
	 * 获取单个系统日志
	 * @param requestParam
	 * @return
	 */
	public List querySystemLogDetail(String id) throws Exception;
	
	/**
	 * 查询日志级别设置
	 * @return
	 * @throws Exception
	 */
	public List<LogSet> querySystemLogSetList() throws Exception;
	/**
	 * 保存或更新日志级别设置
	 * @throws Exception
	 */
	public void saveOrUpdateLogSet(LogSet logSet) throws Exception;
	/**
	 *  删除日志, 用于删除无用日志信息
	 * @param dbName
	 * @param dateStr
	 * @throws Exception
	 */
	public void deleteLogs(String dbName, String dateStr, String systemName, String level) throws Exception;
	
	/**
	 * 获取手机服务端日志
	 * @param requestParam
	 * @return
	 */
	public PaginationResult<?> queryMobileLog(DynamicSqlParameter requestParam) throws Exception;
	/**
	 * 获取单个手机服务端日志
	 * @param requestParam
	 * @return
	 */
	public WebApiServiceLog queryMobileLogDetail(String id) throws Exception;
	
	/**
	 *  删除接口服务日志
	 * @throws Exception
	 */
	public void deleteInterfaceServiceLogs(String dateStr, String name) throws Exception;
	/**
	 *  删除手机服务日志
	 * @throws Exception
	 */
	public void deleteMobileApiServiceLogs(String dateStr, String code, String status) throws Exception;
	/**
	 *  删除网关日志
	 * @throws Exception
	 */
	public void deleteGatewayServiceLogs(String dateStr, String name, String type) throws Exception;
	
	
}
