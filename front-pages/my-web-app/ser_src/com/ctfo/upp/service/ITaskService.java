package com.ctfo.upp.service;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;


public interface ITaskService {
	
	/**
	 * 查询下载任务列表
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "查询下载任务列表")
	public PaginationResult<?> queryTaskList(DynamicSqlParameter requestParam)throws UPPException;

	
}
