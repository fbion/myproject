package com.sinoiov.upp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.business.payment.IOrderBusiness;
import com.sinoiov.upp.service.dto.OrderDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service
public class OrderServiceImpl extends AbstractService implements IOrderService {

	private IOrderBusiness orderBusiness;

	public OrderServiceImpl() {
		orderBusiness = (IOrderBusiness) ServiceFactory.getFactory().getService(IOrderBusiness.class);
	}

	

	@Override
	public void modifyOrderRemark(String id, String remarks) throws Exception {
		try {
			orderBusiness.modifyOrderRemark(id, remarks);
		} catch (Exception e) {
			logger.error("根据ID修改流水信息备注时报错!", e);
			throw new UPPServiceException("根据ID修改流水信息备注时报错!", e);
		}
	}

	@Override
	public OrderDto getOrderById(String id) throws UPPServiceException {
		try {
			return orderBusiness.getOrderInfoById(id);
		} catch (UPPException e) {
			logger.error("根据ID查询订单信息时报错!", e);
			throw new UPPServiceException("根据ID查询订单信息时报错!", e);
		}
	}

	@Override
	public OrderDto getOrderByOrderNo(String orderNo) throws UPPServiceException {
		try {
			return orderBusiness.getOrderByOrderNo(orderNo);
		} catch (UPPException e) {
			logger.error("根据订单号查询订单对象信息时报错!", e);
			throw new UPPServiceException("根据订单号查询订单对象信息时报错!", e);
		}
	}

	@Override
	public List<OrderDto> queryOrderList(DynamicSqlParameter parameter) throws UPPServiceException {
		try {
			return orderBusiness.queryOrderInfo(parameter);
		} catch (UPPException e) {
			logger.error("根据条件查询订单集合信息时报错!", e);
			throw new UPPServiceException("根据条件查询订单集合信息时报错!", e);
		}
	}

	@Override
	public Integer getOrderCount(DynamicSqlParameter parameter) throws UPPServiceException {
		try {
			return	orderBusiness.getOrderCount(parameter);
		} catch (UPPException e) {
			logger.error("分页查询交易订单时报错!", e);
			throw new UPPServiceException("分页查询交易订单时报错!", e);
		}
	}	
	@Override
	public PaginationResult<OrderDto> queryOrderByPage(DynamicSqlParameter parameter) throws UPPServiceException {
		try {
			
			return orderBusiness.queryOrderInfoPage(parameter);
			
		} catch (UPPException e) {
			logger.error("分页查询交易订单时报错!", e);
			throw new UPPServiceException("分页查询交易订单时报错!", e);
		}
	}

	@Override
	public List<OrderDto> queryPayOrderInfoByWorkOrderNo(String merchantcode, String workOrderNo)
			throws UPPServiceException {
		try {
			return orderBusiness.queryOrderByWorkOrderNo(merchantcode, workOrderNo);
		} catch (UPPException e) {
			logger.error("根据业务订单号和商户编号查询订单信息报错!", e);
			throw new UPPServiceException("根据业务订单号和商户编号查询订单信息报错!", e);
		}
	}
	@Override
	public boolean getCallbackOrderInfo(String workOrderNo,String tradeExternalNo) throws UPPServiceException {
		try {
			
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> equal = new HashMap<String, String>();
			equal.put("workOrderNo", workOrderNo);
			equal.put("tradeExternalNo", tradeExternalNo);
			requestParam.setEqual(equal);
			int count = orderBusiness.getOrderCount(requestParam);
			return count>0?true:false;
//			OrderDto dto = orderBusiness.getCallbackOrderInfo(workOrderNo, tradeExternalNo);
//			if(dto!=null){
//				return true;
//			}
//			return false;
		} catch (UPPException e) {
			logger.error("根据业务订单号和商户编号查询订单信息报错!", e);
			throw new UPPServiceException("根据业务订单号和商户编号查询订单信息报错!", e);
		}
	}
}
