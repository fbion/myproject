package com.ctfo.upp.accountservice.payment.internal.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.HOrderInfo;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.intf.internal.IOrderManager;
import com.ctfo.upp.accountservice.utils.TradeNumberHelper;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.manager.support.UppGenericManagerImpl;
import com.ctfo.upp.models.PaginationResult;

//@Service("iOrderManager")
public class OrderManagerImpl extends UppGenericManagerImpl<Object, Object> implements IOrderManager {

	private static Log logger = LogFactory.getLog(OrderManagerImpl.class);

	private String saveOrderInfoHistory(String id) throws UPPException{
		try{

			OrderInfo order = new OrderInfo();
			order.setId(id);
			order = super.getModelById(order);
			HOrderInfo hOrder = new HOrderInfo();
			PropertyUtils.copyProperties(hOrder, order);
			hOrder.setRecordCreateTime(new Date().getTime());//创建时间
			hOrder.setPayOrderId(order.getId());

			return super.addModel(hOrder);

		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("保存订单历史时异常", e);
			throw new UPPException("保存订单历史时异常");
		}
	}

	/**
	 * 根据订单类型生成订单号
	 * 类型＋时间＋随机数
	 * @param type
	 * @return 生成订单号
	 * @throws Exception
	 */
	private String getOrderNo(String type)throws Exception{
		//return TradeNumberHelper.getPayOrderNo();
		String orderNo=StringUtils.isNotBlank(type)?type+(type.length()==1?"00":type.length()==2?"0":""):"999";
		orderNo += new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		orderNo += String.valueOf(Math.random()).substring(2).substring(0, 3);		
		return orderNo;
	}
	/**
	 * 根据订单类型生成内部交易流水号
	 * 类型＋时间＋随机数
	 * @param type
	 * @return 内部交易流水号
	 * @throws Exception
	 */
	private String getTradeExternalNo(String type)throws Exception{
		//return "BN"+this.getOrderNo(type);
		return TradeNumberHelper.getTradeExternalNo();
	}

	@Override
	public String addOrderInfo(OrderInfo orderInfo) throws UPPException {
		try{
			
			orderInfo.setOrderNo(this.getOrderNo(orderInfo.getOrderType()));//订单号
			orderInfo.setCreateTime(new Date().getTime());//订单生成时间
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_INIT);//订单初始状态0
			orderInfo.setVersion(0);//			
			orderInfo.setTradeExternalNo(this.getTradeExternalNo(orderInfo.getOrderType()));
			
			if(StringUtils.isBlank(orderInfo.getAccountNo()))
				throw new UPPException("账号不能为空");
			if(orderInfo.getOrderAmount()<0)
				throw new UPPException("交易金额必须大于0");		
			
			return super.addModel(orderInfo);				

		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建订单时异常",e);
			throw new UPPException("创建订单时异常");
		}
	}

	@Override
	public int modifyOrderInfo(OrderInfo orderInfo) throws UPPException {

		try{

			this.saveOrderInfoHistory(orderInfo.getId());//订单快照

			return super.updateModelPart(orderInfo);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("更新订单信息时异常", e);
			throw new UPPException("更新订单信息时异常");
		}
	}

	@Override
	public int updateOrderState(String orderNo, String status, Long confirmDate)
			throws UPPException {

		try{

			super.notNull(orderNo, status);

			OrderInfoExampleExtended orderEE = new OrderInfoExampleExtended();
			orderEE.createCriteria().andOrderNoEqualTo(orderNo);
			List<OrderInfo> list = super.getModels(orderEE);
			if(list!=null && list.size()>1)
				throw new UPPException("参数不正确，订单号["+orderNo+"]找到多条订单");

			String id = orderNo;				
			if(list!=null && list.size()==1)
				id = list.get(0).getId();

			this.saveOrderInfoHistory(id);//订单快照

			OrderInfo order = new OrderInfo();
			order.setId(id);
			order.setOrderStatus(status);
			order.setPayConfirmDate(confirmDate);

			return super.updateModelPart(order);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("更新订单状态时异常", e);
			throw new UPPException("更新订单状态时异常");
		}

	}
	@Override
	public int updateOrderState(String orderNo, String status)
			throws UPPException {

		try{

			super.notNull(orderNo, status);

			OrderInfoExampleExtended orderEE = new OrderInfoExampleExtended();
			orderEE.createCriteria().andOrderNoEqualTo(orderNo);
			List<OrderInfo> list = super.getModels(orderEE);
			if(list!=null && list.size()>1)
				throw new UPPException("参数不正确，订单号["+orderNo+"]找到多条订单");

			String id = orderNo;				
			if(list!=null && list.size()==1)
				id = list.get(0).getId();

			this.saveOrderInfoHistory(id);//订单快照

			OrderInfo order = new OrderInfo();
			order.setId(id);
			order.setOrderStatus(status);

			return super.updateModelPart(order);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("更新订单状态时异常", e);
			throw new UPPException("更新订单状态时异常");
		}

	}

	@Override
	public int revokeOrderInfo(String orderUUID) throws UPPException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderInfo getOrderInfoById(String id) throws UPPException {
		try{

			OrderInfo order = new OrderInfo();
			order.setId(id);

			return super.getModelById(order);

		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("根据ID查询订单时异常", e);
			throw new UPPException("根据ID查询订单时异常");
		}
	}

	@Override
	public OrderInfo getOrderInfoByOrderNo(String orderNo)
			throws UPPException {
		try{

			OrderInfoExampleExtended orderEE = new OrderInfoExampleExtended();
			orderEE.createCriteria().andOrderNoEqualTo(orderNo);
			List<OrderInfo> list = super.getModels(orderEE);

			if(list!=null && list.size()==1)
				return list.get(0);
			else
				logger.warn("找不到订单号［"+orderNo+"］对应的订单！");
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());	
		}catch(Exception e){
			logger.error("根据订单号查询订单时异常", e);
			throw new UPPException("根据订单号查询订单时异常");
		}

		return null;
	}

	@Override
	public List<OrderInfo> queryOrderInfoList(
			OrderInfoExampleExtended exampleExtended) throws UPPException {
		try{								

			return super.getModels(exampleExtended);

		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("根据查询条件查询订单集合时异常", e);
			throw new UPPException("根据查询条件查询订单集合时异常");
		}
	}

	@Override
	public PaginationResult<OrderInfo> queryOrderInfoPage(
			OrderInfoExampleExtended exampleExtended) throws UPPException {
		try{								

			return super.paginateModels(exampleExtended);

		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("根据查询条件查询订单分页时异常", e);
			throw new UPPException("根据查询条件查询订单分页时异常");
		}
	}

	@Override
	public OrderInfo getOrderInfoByTradeExternalNo(String tradeExternalNo)
			throws UPPException {
		try{

			OrderInfoExampleExtended orderEE = new OrderInfoExampleExtended();
			orderEE.createCriteria().andTradeExternalNoEqualTo(tradeExternalNo);
			List<OrderInfo> list = super.getModels(orderEE);

			if(list!=null && list.size()==1)
				return list.get(0);
			else
				logger.warn("找不到内部交易流水号［"+tradeExternalNo+"］对应的订单！");
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());	
		}catch(Exception e){
			logger.error("根据内部交易流水号查询订单时异常", e);
			throw new UPPException("根据内部交易流水号查询订单时异常");
		}

		return null;
	}

	@Override
	public int updateOrderClearingState(String orderNo, String state) throws UPPException {
		try{

			super.notNull(orderNo, state);

			OrderInfoExampleExtended orderEE = new OrderInfoExampleExtended();
			orderEE.createCriteria().andOrderNoEqualTo(orderNo);
			List<OrderInfo> list = super.getModels(orderEE);
			if(list!=null && list.size()>1)
				throw new UPPException("参数不正确，订单号["+orderNo+"]找到多条订单");

			String id = orderNo;				
			if(list!=null && list.size()==1)
				id = list.get(0).getId();

			this.saveOrderInfoHistory(id);//订单快照

			OrderInfo order = new OrderInfo();
			order.setId(id);
			order.setOrderStatus(state);

			return super.updateModelPart(order);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("更新订单状态时异常", e);
			throw new UPPException("更新订单状态时异常");
		}
	}



}
