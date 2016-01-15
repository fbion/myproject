package com.sinoiov.upp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dto.beans.FreezeAccountResultBean;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.business.payment.IPaymentTradeBusiness;
import com.sinoiov.upp.service.dto.OrderDto;
import com.sinoiov.upp.service.dto.OrderFreezeDto;
import com.sinoiov.upp.service.dto.TradeDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service("paymentTradeServiceImpl")
public class PaymentTradeServiceImpl extends AbstractService implements IPaymentTradeService {

	private IPaymentTradeBusiness paymentTradeBusiness;

	private PaymentTradeServiceImpl() {
		paymentTradeBusiness = (IPaymentTradeBusiness) ServiceFactory.getFactory().getService(
				IPaymentTradeBusiness.class);
	}

	@Override
	public OrderDto transferAccounts(OrderDto orderDto) throws UPPServiceException {
		OrderInfo orderInfo = new OrderInfo();
		try {
			if(StringUtils.isBlank(orderDto.getOrderAmount())) throw new UPPServiceException(ReturnCodeDict.ORDER_SERVICE_PROVIDER_CODE_IS_NULL,"订单金额不能为空！");
			
			String[] ignoreProperties = { "orderAmount", "createTime", "payConfirmDate", "identityType"};
			BeanUtils.copyProperties(orderDto, orderInfo, ignoreProperties);
			orderInfo.setOrderAmount(AmountUtil.getAmount(orderDto.getOrderAmount()));
			if(StringUtils.isNotBlank(orderDto.getIdentityType()))
				orderInfo.setIdentityType(Integer.parseInt(orderDto.getIdentityType().trim()));
			orderInfo = paymentTradeBusiness.transferAccounts(orderInfo);		
			orderDto.setId(orderInfo.getId());
			orderDto.setOrderStatus(orderInfo.getOrderStatus());
		} catch (Exception e) {
			logger.error("转账操作时报错!", e);
			throw new UPPServiceException("转账操作时报错!");
		}
		return orderDto;
	}

	@Override
	public Map<String, Object> freezeAccountMoney(OrderFreezeDto orderFreezeDto) throws UPPServiceException {
		Map<String, Object> mpReturn = null;
		try {
			OrderInfo orderInfo = new OrderInfo();
			String[] ignoreProperties = { "orderAmount" };
			BeanUtils.copyProperties(orderFreezeDto, orderInfo, ignoreProperties);
			orderInfo.setOrderAmount(AmountUtil.getAmount(orderFreezeDto.getOrderAmount()));
			
			FreezeAccountResultBean freezeAccountResultBean = paymentTradeBusiness.freezeAccountMoney(orderInfo);

			mpReturn = new HashMap<String, Object>();
			mpReturn.put("tradeExternalNo", freezeAccountResultBean.getTradeExternalNo());
		} catch (Exception e) {
			logger.error("冻结账户金额时x报错!", e);
			throw new UPPServiceException("冻结账户金额时报错!");
		}
		return mpReturn;
	}

	@Override
	public Map<String, Object> unFreezeAccountMoney(OrderFreezeDto orderFreezeDto) throws UPPServiceException {
		Map<String, Object> mpReturn = null;
		try {
			OrderInfo orderInfo = new OrderInfo();
			String[] ignoreProperties = { "orderAmount" };
			BeanUtils.copyProperties(orderFreezeDto, orderInfo, ignoreProperties);
			orderInfo.setOrderAmount(AmountUtil.getAmount(orderFreezeDto.getOrderAmount()));
			
			paymentTradeBusiness.unFreezeAccountMoney(orderInfo);

			mpReturn = new HashMap<String, Object>();
			mpReturn.put("orderNo", orderInfo.getOrderNo());
		} catch (Exception e) {
			logger.error("解冻账户金额时报错!", e);
			throw new UPPServiceException("解冻账户金额时报错!");
		}
		return mpReturn;
	}

	@Override
	public TradeDto getPaymentTradeById(String recordId) throws UPPServiceException {
		TradeDto tradeDto = new TradeDto();
		try {
			PaymentTrade paymentTrade = paymentTradeBusiness.getPaymentTradeById(recordId);
			BeanUtils.copyProperties(paymentTrade, tradeDto);
		} catch (Exception e) {
			logger.error("查询交易记录时报错!", e);
			throw new UPPServiceException("查询交易记录时报错!");
		}
		return tradeDto;
	}

	@Override
	public List<TradeDto> queryPaymentTradeListByOrderNo(String orderNo) throws UPPServiceException {
		try {
			DynamicSqlParameter parameter = new DynamicSqlParameter();
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderNo", orderNo);
			parameter.setEqual(map);

			return this.queryPaymentTradeList(parameter);
		} catch (Exception e) {
			logger.error("根据订单号查询交易记录集合时报错!", e);
			throw new UPPServiceException("根据订单号查询交易记录集合时报错!");
		}
	}

	@Override
	public List<TradeDto> queryPaymentTradeList(DynamicSqlParameter parameter) throws UPPServiceException {
		try {
			return paymentTradeBusiness.queryPaymentTrade(parameter);
		} catch (Exception e) {
			logger.error("查询交易记录集合时报错!", e);
			throw new UPPServiceException("查询交易记录集合时报错!");
		}
	}

	@Override
	public PaginationResult<TradeDto> queryPaymentTradeByPage(DynamicSqlParameter parameter) throws UPPServiceException {
		try {
			return paymentTradeBusiness.queryPaymentTradeByPage(parameter);
		} catch (Exception e) {
			logger.error("查询交易记录页面对象时报错!", e);
			throw new UPPServiceException("查询交易记录页面对象时报错!");
		}
	}

}
