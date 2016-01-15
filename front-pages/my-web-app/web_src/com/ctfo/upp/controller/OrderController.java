package com.ctfo.upp.controller;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.excel.exp.ExportExcel;
import com.ctfo.excel.parse.ExpObj;
import com.ctfo.excel.util.ExcelUtil;
import com.ctfo.excel.util.ExcelVersion;
import com.ctfo.upp.excelbeans.OrderExcel;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.order.OrderService;
import com.ctfo.upp.service.simplecode.ISimplecodeService;

/***
* 类描述：交易管理-交易订单查询
* @author：liuguozhong  
* @date：2014年12月10日下午6:50:42 
* @version 1.0
* @since JDK1.6
*/
@Component
@Controller
@Scope("prototype")
@RequestMapping("/order")
public class OrderController extends BaseController {
	private static Log logger = LogFactory.getLog(OrderController.class);
	@Resource(name = "orderService", description = "service参数接口")
	private OrderService orderService;
	@Resource
	private ISimplecodeService simplecodeService;
	private PaginationResult<?> result = new PaginationResult<Object>();
	/**
	 * 分页交易管理-交易订单查询
	 * @return
	 */
	@RequestMapping(value="/queryList.action" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryParamByPage(DynamicSqlParameter requestParam){
		try {
			result = orderService.queryTradeOrderByPage(requestParam);
		} catch (Exception e) {
			result.setMessage("交易管理-交易订单查询信息异常!");
			logger.error("交易管理-交易订单查询信息异常!",e);
		}		
		return result;	
	}
	/**
	 * 通过订单号查询订单信息-交易订单查询
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/queryByOrderNo.action" ,produces = "application/json")
	@ResponseBody
	public List queryParamByOrderNo(String orderNo){
		List list = new ArrayList();
		try {
			 list = orderService.queryTradeOrderByOrderNo(orderNo);
		} catch (Exception e) {
			result.setMessage("交易管理-交易订单查询信息异常!");
			logger.error("交易管理-交易订单查询信息异常!",e);
		}		
		return list;	
	}
	/**
	 * 通过订单号查询订单信息-交易订单查询
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/queryParamByOrderId.action" ,produces = "application/json")
	@ResponseBody
	public List queryParamByOrderId(String orderId){
		List list = new ArrayList();
		try {
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("orderId", orderId);
			 list = orderService.queryPaymentTransByParam(param);
		} catch (Exception e) {
			result.setMessage("交易管理-交易订单查询信息异常!");
			logger.error("交易管理-交易订单查询信息异常!",e);
		}		
		return list;	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportExcel.action", method = RequestMethod.GET)
	@ResponseBody
	public void exportExcel(@RequestParam String orderNo){
		try {
			response.setDateHeader("Expires", 0);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String encodedfileName = new String("交易订单数据".getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");

			OutputStream out = response.getOutputStream();
			ExportExcel<OrderExcel> exp = new ExportExcel<OrderExcel>();
			List<ExpObj> expObjs = ExcelUtil.getExpObjs(OrderExcel.class, 0, "EXP");
			// 定义的方法
			List<Method> methods = new ArrayList<Method>(expObjs.size());
			// 定义执行方法的对象
			List<Object> objs = new ArrayList<Object>(expObjs.size());
			OrderExcel excelObj = new OrderExcel();
			excelObj = orderService.exportById(orderNo);
			//支付平台
			SimpleCode simpleCode = new SimpleCode();
			//订单状态
			simpleCode = simplecodeService.getByTypeCodeAndCode("PAY_ORDER_STATUS",excelObj.getOrderStatus());
			excelObj.setOrderStatus(simpleCode.getName());
			//科目
			simpleCode = simplecodeService.getByTypeCodeAndCode("ORDER_SUBJECT", excelObj.getOrderType());
			excelObj.setOrderType(simpleCode.getName());
			for (int i = 0; i < expObjs.size(); i++) {
				objs.add(null);
				methods.add(null);
				switch (i) {
				case 8:
					break;
				default:
					break;
				}
			}

			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
			List<OrderExcel> result = new ArrayList<OrderExcel>();
			result.add(excelObj);
			exp.singleExportExcel(result, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("导出单条订单交易信息异常", e);
		}
	}

	/**
	 * 通过条件导出所有数据
	 * 
	 * @param requestParam
	 */

	@RequestMapping(value = "/exportExcelAll.action", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcelAll(DynamicSqlParameter requestParam){
		try {
			//添加排序
			requestParam.setOrder("createTime");
			requestParam.setSort("desc");
			orderService.exportAllNew(requestParam);
		} catch (Exception e) {
			logger.error("导出交易订单列表EXCEL异常",e);
		}
	}
//	public void exportExcelAll(DynamicSqlParameter requestParam) {
//		try {
//			response.setDateHeader("Expires", 0);
//			response.addHeader("Pragma", "no-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//			String encodedfileName = new String("交易订单数据".getBytes("GBK"), "ISO8859-1");
//			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");
//
//			OutputStream out = response.getOutputStream();
//			ExportExcel<OrderExcel> exp = new ExportExcel<OrderExcel>();
//			List<ExpObj> expObjs = ExcelUtil.getExpObjs(OrderExcel.class, 0, "EXP");
//			// 定义的方法
//			List<Method> methods = new ArrayList<Method>(expObjs.size());
//			// 定义执行方法的对象
//			List<Object> objs = new ArrayList<Object>(expObjs.size());
//			List<OrderExcel> result = new ArrayList<OrderExcel>();
//			// 通过条件查找到商户信息
//			result = orderService.exportAll(requestParam);
//			OrderExcel excelObj = new OrderExcel();
//			for(int i=0;i<result.size();i++){
//				OrderExcel excel = result.get(i);
//				SimpleCode code = new SimpleCode();
//				if(excel.getOrderType()!=null){
//					code = simplecodeService.getByTypeCodeAndCode("ORDER_SUBJECT", excel.getOrderType());
//					if(code!=null){
//						excel.setOrderType(code.getName());
//					}else{
//						excel.setOrderType("");
//					}
//				}else{
//					excel.setOrderType("");
//				}
//				if(excel.getOrderStatus()!=null){
//					code = simplecodeService.getByTypeCodeAndCode("PAY_ORDER_STATUS", excel.getOrderStatus());
//					if(code!=null){
//						excel.setOrderStatus(code.getName());
//					}else{
//						excel.setOrderStatus("");
//					}
//				}else{
//					excel.setOrderStatus("");
//				}
//			}
//			for (int i = 0; i < expObjs.size(); i++) {
//				objs.add(null);
//				methods.add(null);
//			}
//			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
//			exp.singleExportExcel(result, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
//			response.getOutputStream().flush();
//			response.getOutputStream().close();
//		} catch (Exception e) {
//			logger.error("按条件导出所有交易信息异常", e);
//		}
//
//	}
}
