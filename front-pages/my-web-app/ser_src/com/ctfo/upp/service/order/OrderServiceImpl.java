package com.ctfo.upp.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.daox.beans.OrderInfoExtend;
import com.ctfo.upp.excelbeans.OrderExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.root.utils.TimeHandleUtil;
import com.ctfo.upp.service.TaskServiceImpl;
import com.ctfo.upp.service.simplecode.ISimplecodeService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.OrderDto;


/***
* 类描述：交易管理-交易订单查询
* @author：liuguozhong  
* @date：2014年12月10日下午6:51:02 
* @version 1.0
* @since JDK1.6
*/ 
@Service("orderService")
public class OrderServiceImpl extends TaskServiceImpl<OrderDto, OrderExcel> implements OrderService{
	private static Log logger = LogFactory.getLog(OrderServiceImpl.class);
	private Map<String, String> codeMap = new HashMap<String, String>();
	@Resource
	private ISimplecodeService simpleCodeService;
	//获取资金账户表manager
//	private IPaymentBaseManager manager = null;
//	private IPaymentBaseManager getManager() {
//		if (this.manager == null) {
//			manager = (IPaymentBaseManager) ServiceFactory.getFactory().getService(IPaymentBaseManager.class);
//		}
//		return this.manager;
//	}
	@Override
	public PaginationResult<?> queryTradeOrderByPage(DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<Object> result = new PaginationResult<Object>();
		
		try {
//			List<?> list= getManager().queryPaymentTransByPage(requestParam);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ORDER_PAYMENT_BY_PAGE_PARAM: " 
							+ JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(JSONObject.fromObject(requestParam)
								.toString(), DefaultConfig
								.getValue("UPP_QUERY_ORDER_PAYMENT_BY_PAGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ORDER_PAYMENT_BY_PAGE: " + json);
			
//			PaginationResult getPaginationResult(String json, Class clazz)
			
			PaginationResult pResult = super.getPaginationResult(json, OrderDto.class);
			
			result.setData(pResult.getData());
			result.setTotal(pResult.getTotal());
//			JSONArray dataObj = super.getJSONArrayResult(json);
//			List<TradeDto> list = null;
//			if (dataObj != null) {
//				list = (List<TradeDto>)JSONArray.toCollection(dataObj, TradeDto.class);
//			}
//			
//			result.setData((list != null) ? Arrays.asList(list.toArray(new Object[] {})) : null);
//			
////			result.setTotal(getManager().queryPaymentTradeCount(requestParam));
//			
//			String countJson = super.sendRequest(JSONObject.fromObject(requestParam)
//					.toString(), DefaultConfig
//					.getValue("UPP_QUERY_PAYMENT_TRADE_COUNT"));
//			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRADE_COUNT: " + countJson);
//			result.setTotal(Integer.valueOf(super.getReturnString(countJson, RESULT_DATA_SINGLE_STR_NAME)));
			
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("条件分页交易管理-交易订单查询集合异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("条件分页交易管理-交易订单查询集合异常", e);
		}
		return result;
	}
	@Override
	public OrderExcel exportById(String orderNo) throws UPPException {
		try {
			List<Map<? extends Object, ? extends Object>> list = new ArrayList<Map<? extends Object, ? extends Object>>();
			OrderInfoExtend orderInfo=new OrderInfoExtend();
			
//		list = getManager().queryPaymentTransByOrderNo(orderNo);
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderNo", orderNo);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRANS_BY_ORDERNO_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map)
					.toString(), DefaultConfig
					.getValue("UPP_QUERY_PAYMENT_TRANS_BY_ORDERNO"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRANS_BY_ORDERNO: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			/*if (dataObj != null) {
				list = (List<Map<? extends Object, ? extends Object>>)JSONObject.toBean(dataObj, List.class);
			}*/
			OrderInfo order = new OrderInfo();
			order  = (OrderInfo) JSONObject.toBean(dataObj, OrderInfo.class);
			
			OrderExcel excel = new OrderExcel();
			if(StringUtils.isNotEmpty(order.getOrderNo())){
				excel.setOrderNo(order.getOrderNo());
			}
			if(StringUtils.isNotEmpty(order.getWorkOrderNo())){
				excel.setWorkOrderNo(order.getWorkOrderNo());
			}
			if(StringUtils.isNotEmpty(order.getTradeExternalNo())){
				excel.setTradeExternalNo(order.getTradeExternalNo());
			}
			if(StringUtils.isNotEmpty(order.getOrderType())){
				excel.setOrderType(order.getOrderType());
			}
			if(StringUtils.isNotEmpty(order.getAccountNo())){
				excel.setAccountNo(order.getAccountNo());
			}
			
			Long orderAmount=order.getOrderAmount();
			if(orderAmount!=null && !"".equals(orderAmount)){
				excel.setOrderAmount(AmountUtil.getAmount(orderAmount));
			}
			Long createTime=order.getCreateTime();
			if(createTime!=null && !"".equals(createTime)){
				excel.setCreateTime(TimeHandleUtil.longToDate(createTime,"yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotEmpty(order.getOrderStatus())){
				excel.setOrderStatus(order.getOrderStatus());
			}
			return excel;
		} catch (Exception e) {
			logger.error("order_service_exportById_error", e);
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<OrderExcel> exportAll(DynamicSqlParameter requestParam) throws UPPException {
		//添加查询顺序
		requestParam.setOrder("createTime");
		requestParam.setSort("desc");
		try {
			List<OrderExcel> excelList = new ArrayList<OrderExcel>(); 
//		List list= getManager().queryPaymentAll(requestParam);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ORDER_PAYMENT_BY_PAGE_ALL_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(JSONObject.fromObject(requestParam)
					.toString(), DefaultConfig
					.getValue("UPP_QUERY_ORDER_PAYMENT_BY_PAGE"));
			logger.info("UPP_QUERY_ORDER_PAYMENT_BY_PAGE_ALL: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			JSONArray jArray = (JSONArray)(( dataObj ).get(RESULT_DATA_NAME));
			List<OrderDto> list = new ArrayList();
			if (dataObj != null) {
				list = (List)JSONArray.toCollection(jArray, OrderDto.class); 
			}
			
			if (list != null) {
				excelList = copy(list);
			}
			return excelList;
		} catch (Exception e) {
			logger.error("orderservice_exportAll_error", e);
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<OrderExcel> copy(List<OrderDto> list) {
		List excelList = new ArrayList();
		if (list.size() >= 0) {
			for (int i = 0; i < list.size(); i++) {
				OrderDto order = new OrderDto();
				OrderExcel excel = new OrderExcel();
				order = list.get(i);
				if(StringUtils.isNotEmpty(order.getOrderNo())){
					excel.setOrderNo(order.getOrderNo());
				}
				if(StringUtils.isNotEmpty(order.getWorkOrderNo())){
					excel.setWorkOrderNo(order.getWorkOrderNo());
				}
				if(StringUtils.isNotEmpty(order.getTradeExternalNo())){
					excel.setTradeExternalNo(order.getTradeExternalNo());
				}
				if(StringUtils.isNotEmpty(order.getOrderType())){
					excel.setOrderType(order.getOrderType());
				}
				if(StringUtils.isNotEmpty(order.getAccountNo())){
					excel.setAccountNo(order.getAccountNo());
				}
				
				String orderAmount=order.getOrderAmount();
				if(orderAmount!=null && !"".equals(orderAmount)){
					excel.setOrderAmount(AmountUtil.getAmount(Long.parseLong(orderAmount)));
				}
				String createTime=order.getCreateTime();
				if(createTime!=null && !"".equals(createTime)){
					excel.setCreateTime(TimeHandleUtil.longToDate(Long.parseLong(createTime),"yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotEmpty(order.getOrderStatus())){
					excel.setOrderStatus(order.getOrderStatus());
				}
				excelList.add(excel);
			}
		}
			return excelList;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List queryTradeOrderByOrderNo(String orderNo) throws UPPException {
		try {
			List<OrderDto> list = new ArrayList<OrderDto>();
//		list = getManager().queryPaymentTransByOrderNo(orderNo);
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderNo", orderNo);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRANS_BY_ORDERNO_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map)
					.toString(), DefaultConfig
					.getValue("UPP_QUERY_PAYMENT_TRANS_BY_ORDERNO"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRANS_BY_ORDERNO: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				OrderDto tradeDto = (OrderDto)JSONObject.toBean(dataObj, OrderDto.class);
				list.add(tradeDto);
			}
			
			return list;
		} catch (Exception e) {
			logger.error("queryTradeOrderByOrderNo_error", e);
		}
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List queryPaymentTransByParam(Map<String, Object> param) throws UPPException {
		try {
			List<TradeDto> list = new ArrayList<TradeDto>();
//		list = getManager().queryPaymentTransByParam(param);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRANS_BY_PARAM_PARAM: " + param);
			String json = super.sendRequest(JSONObject.fromObject(param)
					.toString(), DefaultConfig
					.getValue("UPP_QUERY_PAYMENT_TRANS_BY_PARAM"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRANS_BY_PARAM: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				TradeDto tradeDto = (TradeDto)JSONObject.toBean(dataObj, TradeDto.class);
				list.add(tradeDto);
			}
			
			return list;
		} catch (Exception e) {
			logger.error("queryPaymentTransByParam_error", e);
		}
		return null;
	}
	@Override
	public void exportAllNew(DynamicSqlParameter requestParam)
			throws UPPException {
		try{
			long aa = System.currentTimeMillis();
			
			String taskName = "交易订单EXCEL下载";
			String countUrl = DefaultConfig.getValue("UPP_COUNT_ORDER");
			String queryUrl = DefaultConfig.getValue("UPP_QUERY_ORDER_PAYMENT_BY_PAGE");
			super.runExportExcelTask(taskName, countUrl, queryUrl, requestParam, OrderDto.class, OrderExcel.class);
		
			logger.info("---->>>导出用时:"+(System.currentTimeMillis()-aa));
			
		}catch(Exception e){
			logger.error("导出交易订单异常："+e.getLocalizedMessage(),e);
		}
		
	}
	@Override
	public List<OrderExcel> copyTask(List<?> list) throws Exception {
		List<OrderExcel> result = new ArrayList<OrderExcel>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				OrderExcel excel = new OrderExcel();
				OrderDto orderDto = new OrderDto();
				orderDto = (OrderDto) list.get(i);
				String orderType = orderDto.getOrderSubject();
				String orderStatus = orderDto.getOrderStatus();
				String ownerUserNo = orderDto.getOwnerUserNo();
				//TODO
				excel.setOrderType(StringUtils.isNotBlank(orderType)?this.getCodeName("ORDER_SUBJECT", orderType):"");
				excel.setOrderStatus(StringUtils.isNotBlank(orderStatus)?this.getCodeName("PAY_ORDER_STATUS",orderStatus):"");
				if(StringUtils.isNotEmpty(orderDto.getOrderNo())){
					excel.setOrderNo(orderDto.getOrderNo());
				}
				if(StringUtils.isNotEmpty(orderDto.getWorkOrderNo())){
					excel.setWorkOrderNo(orderDto.getWorkOrderNo());
				}
				if(StringUtils.isNotEmpty(orderDto.getTradeExternalNo())){
					excel.setTradeExternalNo(orderDto.getTradeExternalNo());
				}
				if(StringUtils.isNotEmpty(orderDto.getAccountNo())){
					excel.setAccountNo(orderDto.getAccountNo());
				}
				excel.setOwnerUserNo(StringUtils.isNotBlank(ownerUserNo)?ownerUserNo:"");
			    excel.setOrderAmount(orderDto.getOrderAmount());
				String createTime=orderDto.getCreateTime();
				if(createTime!=null && !"".equals(createTime)){
					excel.setCreateTime(TimeHandleUtil.longToDate(Long.parseLong(createTime),"yyyy-MM-dd HH:mm:ss"));
				}
				result.add(excel);
			}
		}
		return result;
	}
}