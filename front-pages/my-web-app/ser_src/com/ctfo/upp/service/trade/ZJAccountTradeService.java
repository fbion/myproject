package com.ctfo.upp.service.trade;

import java.util.List;

import com.ctfo.account.dao.beans.AccountDetail;
import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.excelbeans.ZJAccountTradExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.service.dto.AccountDetailDto;

/***
* 类描述：账户管理-中交账户交易流水查询
* @author：liuguozhong  
* @date：2014年12月9日下午3:24:37 
* @version 1.0
* @since JDK1.6
*/ 
public interface ZJAccountTradeService  {
	/**
	 * 条件分页账户管理-中交账户交易流水查询
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件分页账户管理-中交账户交易流水查询")
	public PaginationResult<?> queryZJAccountTradeByPage(DynamicSqlParameter requestParam)throws UPPException;
	
	@AnnotationName(name = "条件分页账户管理-中交账户交易流水查询")
	public List<AccountDetailDto> queryZJAccountTrade(DynamicSqlParameter requestParam)throws UPPException;
	
	/**
	 * 账户管理-中交账户交易流水导出
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "账户管理-中交账户交易流水导出")
	public List<AccountDetail> queryBookAccount(DynamicSqlParameter requestParam)throws UPPException;
	/***
	 * 写入Excel(旧版)
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public List<ZJAccountTradExcel> exportExcel(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 导出EXCEL(新版)
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public String exportExcelNew(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 查询普通账户出项及进项统计
	 * @param requestParam
	 * @return String
	 * @throws UPPException
	 */
	@AnnotationName(name = "查询普通账户出项及进项统计")
	public String queryCount(DynamicSqlParameter requestParam) throws UPPException;
}
