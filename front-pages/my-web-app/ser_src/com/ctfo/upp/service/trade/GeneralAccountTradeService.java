package com.ctfo.upp.service.trade;

import java.util.List;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.ITaskService;
import com.sinoiov.upp.service.dto.AccountDetailDto;

/***
* 类描述：账户管理-普通账户交易流水查询
* @author：liuguozhong  
* @date：2014年12月9日下午3:24:37 
* @version 1.0
* @since JDK1.6
*/ 
public interface GeneralAccountTradeService extends ITaskService {
	/**
	 * 条件分页账户管理-普通账户交易流水查询
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件分页账户管理-普通账户交易流水查询")
	public PaginationResult<?> queryGeneralAccountTradeByPage(DynamicSqlParameter requestParam)throws UPPException;
	
	@AnnotationName(name = "普通账户交易流水查询(按id)")
	public List<AccountDetailDto> queryById(DynamicSqlParameter requestParam)throws UPPException;
	/**
	 * 按条件导出对应EXCEL
	 * @param requestParam
	 * @return List<GeneralAccountExcel>
	 * @throws UPPException
	 */
	@AnnotationName(name = "按条件导出所有普通账户交易流水")
	public void exportExcel(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 查询普通账户出项及进项统计
	 * @param requestParam
	 * @return String
	 * @throws UPPException
	 */
	@AnnotationName(name = "查询普通账户出项及进项统计")
	public String queryCount(DynamicSqlParameter requestParam) throws UPPException;
}
