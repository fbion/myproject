package com.ctfo.upp.service.order;

import java.util.List;
import java.util.Map;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.excelbeans.OrderExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;


/***
* 类描述：交易管理-交易订单查询
* @author：liuguozhong  
* @date：2014年12月10日下午6:51:11 
* @version 1.0
* @since JDK1.6
*/ 
public interface OrderService{
	
	/**
	 * 条件分页交易管理-交易订单查询
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件分页交易管理-交易订单查询")
	public PaginationResult<?> queryTradeOrderByPage(DynamicSqlParameter requestParam)throws UPPException;
	/**
	 * 通过订单号查询订单信息-交易订单查询
	 * @param orderNo
	 * @return List
	 * @throws UPPException
	 */
	@SuppressWarnings("rawtypes")
	@AnnotationName(name = "通过订单号查询-交易订单查询")
	public List queryTradeOrderByOrderNo(String orderNo)throws UPPException;
	
	/**
	 * 导出交易订单查询单条数据
	 * @param orderNo
	 * @return OrderExcel
	 * @throws UPPException
	 */
	@AnnotationName(name = "导出交易订单查询单条数据")
	public OrderExcel exportById(String orderNo) throws UPPException;
	/**
	 * 按条件导出所有交易订单数据(旧版)
	 * @param requestParam
	 * @return  List<OrderExcel>
	 * @throws UPPException
	 */
	@AnnotationName(name = "按条件导出所有交易订单数据")
	public List<OrderExcel> exportAll(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 按条件导出所有交易订单数据(新版)
	 * @param requestParam
	 * @return  List<OrderExcel>
	 * @throws UPPException
	 */
	@AnnotationName(name = "按条件导出所有交易订单数据")
	public void exportAllNew(DynamicSqlParameter requestParam) throws UPPException;
	
	public List queryPaymentTransByParam(Map<String, Object> param)
			throws UPPException;
}
