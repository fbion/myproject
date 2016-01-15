package com.sinoiov.upp.business.payment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.PayOrderSubjectDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.sinoiov.upp.manager.payment.IOrderManager;
import com.sinoiov.upp.service.dto.OrderDto;

@Service("orderBusiness")
public class OrderBusinessImpl implements IOrderBusiness {
	private static Log logger = LogFactory.getLog(OrderBusinessImpl.class);
	
	@Autowired
	@Qualifier("orderManager")
	private IOrderManager orderManager;
	
	@Autowired
	@Qualifier("paymentTradeBusiness")
	private IPaymentTradeBusiness paymentTradeBusiness;
		
	/**
	 * 把OrderInfo对象转为OrderDto对象
	 */
	private void copyOrderInfoToOrderDto(OrderInfo orderInfo, OrderDto orderDto) {
		String[] ignoreProperties = { "orderAmount", "createTime", "identityType", "payConfirmDate" };
		BeanUtils.copyProperties(orderInfo, orderDto, ignoreProperties);
		orderDto.setOrderAmount(AmountUtil.getAmount(orderInfo.getOrderAmount()));
		orderDto.setCreateTime(String.valueOf(orderInfo.getCreateTime()));
		orderDto.setIdentityType(String.valueOf(orderInfo.getIdentityType()));
		orderDto.setPayConfirmDate(String.valueOf(orderInfo.getPayConfirmDate()));
	}
	
	private List<OrderDto> copyOrderInfoToOrderDto(Collection<OrderInfo> coll){
		List<OrderDto> list = new ArrayList<OrderDto>();
		OrderDto orderDto = null;
		for(OrderInfo orderInfo : coll) {
			orderDto = new OrderDto();
			this.copyOrderInfoToOrderDto(orderInfo, orderDto);
			list.add(orderDto);
		}
		return list;
	}
	
	
	
	@Override
	public List<OrderDto> queryOrderInfo(DynamicSqlParameter requestParam)
			throws UPPException {
		try{
			
			OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);	
			List<OrderInfo> list = orderManager.queryOrderInfo(exampleExtended);
			return this.copyOrderInfoToOrderDto(list);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询订单集合异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询订单集合异常:"+e.getLocalizedMessage());
		}
		
	}

	@Override
	public OrderDto getOrderInfoById(String id) throws UPPException {
		try{
			OrderInfo orderInfo = orderManager.getOrderInfoById(id);
			OrderDto dto = new OrderDto();
			this.copyOrderInfoToOrderDto(orderInfo, dto);
			return dto;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据ID查询订单对象异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据ID查询订单对象异常:"+e.getLocalizedMessage());
		}
		
	}
	@Override
	public Integer getOrderCount(DynamicSqlParameter requestParam) throws UPPException {
		try{
			
			OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);
			
			return orderManager.getOrderCount(exampleExtended);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据查询条件查询订单分页对象异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据查询条件查询订单分页对象异常:"+e.getLocalizedMessage());
		}
	}
	
	
	@Override
	public PaginationResult<OrderDto> queryOrderInfoPage(
			DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<OrderDto> result = null;
		try{
			
			OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);		
			PaginationResult<OrderInfo> resultdb = orderManager.queryOrderInfoPage(exampleExtended);
			if(resultdb!=null){
				result = new PaginationResult<OrderDto>();
				result.setData(this.copyOrderInfoToOrderDto(resultdb.getData()));
				result.setTotal(resultdb.getTotal());
			}
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据查询条件查询订单分页对象异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据查询条件查询订单分页对象异常:"+e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public OrderDto getOrderByOrderNo(String orderNo) throws UPPException {
		try{		
			OrderInfo orderInfo = orderManager.getOrderByOrderNo(orderNo);
			OrderDto dto = new OrderDto();
			this.copyOrderInfoToOrderDto(orderInfo, dto);
			return dto;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据订单号查询订单对象异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据订单号查询订单对象异常:"+e.getLocalizedMessage());
		}
		
	}

	@Override
	public String modifyOrderRemark(String id, String remarks)
			throws UPPException {
		try{
			
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(id);
			orderInfo.setRemarks(remarks);
			orderManager.editOrderInfo(orderInfo);
		
			return "SUCCESS";
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("修改订单备注异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改订单备注异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public List<OrderDto> queryOrderByWorkOrderNo(String storeCode,
			String workOrderNo) throws UPPException {
		try{
			
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> equal = new HashMap<String, String>();
			equal.put("workOrderNo", workOrderNo);
			equal.put("storeCode", storeCode);
			requestParam.setEqual(equal);
			
			return this.queryOrderInfo(requestParam);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据业务订单号查询订单异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据业务订单号查询订单异常:"+e.getLocalizedMessage());
		}
	}
	
	@Override
	public OrderDto getCallbackOrderInfo(String workOrderNo, String tradeExternalNo)
			throws UPPException{
		try{
			OrderInfo orderInfo = orderManager.getCallbackOrderInfo(workOrderNo, tradeExternalNo);
			OrderDto dto = new OrderDto();
			this.copyOrderInfoToOrderDto(orderInfo, dto);
			return dto;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}
	}

	@Override
	public List<OrderDto> handleOrderData(List<OrderDto> list)
			throws UPPException {
		try{
			for(OrderDto dto : list){
				if(StringUtils.isNotBlank(dto.getOwnerUserNo()))
					orderManager.updateOwnerUserNoById(dto.getId(), dto.getOwnerUserNo());
			}
			return list;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}
	}
	@Override
	public List<OrderDto> handleOrderInfoBatchRechargeToUserAccount(List<OrderDto> list)
			throws UPPException {
		try{
			for(OrderDto dto : list){		
				OrderInfo orderInfo = new OrderInfo();
				String[] ignoreProperties = {"identityType", "createSubareaTime", "version", "orderAmount","payConfirmDate","createTime"};				
				BeanUtils.copyProperties(dto, orderInfo, ignoreProperties);
				if(StringUtils.isBlank(orderInfo.getAccountNo()))
					orderInfo.setAccountNo(dto.getCollectMoneyAccountNo());
				orderInfo.setOrderAmount(AmountUtil.getAmount(dto.getOrderAmount()));
				orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_ZJXL_PAY);
				orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_BATCH_RECHARGE);
				try{
					paymentTradeBusiness.handleBatchAccountRecharge(orderInfo);	
				}catch(Exception e){
					logger.error("====结算返利时异常："+e.getMessage(), e);
				}
			}
			return list;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}
	}
}
