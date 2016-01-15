package com.ctfo.upp.service;

import org.bson.types.ObjectId;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.upp.log.ServiceLog;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;


@AnnotationName(name = "服务日志管理")
public interface ISLogManageService {
	@AnnotationName(name = "查询调用接口日志数据")
	public PaginationResult<ServiceLog> queryListPage(
			DynamicSqlParameter requestParam, Operator op) throws Exception;

	@AnnotationName(name = "删除接口日志数据")
	public boolean batchDeleteLog(String[] ids, Operator op) throws Exception;

	@AnnotationName(name = "通过id查询接口日志数据")
	public PaginationResult<ServiceLog> queryLogById(
			ObjectId _id, Operator op) throws Exception;
	
}
