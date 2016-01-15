package com.sinoiov.upp.manager.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.file.boss.IMongoService;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.payment.dto.beans.OrderRecord;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.PayOrderSubjectDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.google.code.morphia.query.Query;
import com.sinoiov.root.util.TradeNumberHelper;
import com.sinoiov.upp.manager.AbstractManager;

@Service("orderManager")
public class OrderManagerImpl extends AbstractManager implements IOrderManager {
	private static Log logger = LogFactory.getLog(OrderManagerImpl.class);
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("mongoService")
	private IMongoService mongoService;
	
	@Override
	public OrderInfo createOrderInfo(OrderInfo orderInfo) throws UPPException {
		try{
			
			String userId = StringUtils.isNotBlank(orderInfo.getUserId())?orderInfo.getUserId():StringUtils.isNotBlank(orderInfo.getCollectMoneyUserId())?orderInfo.getCollectMoneyUserId():"";
			String ownerUserNo = StringUtils.isNotBlank(orderInfo.getOwnerUserNo())?orderInfo.getOwnerUserNo():this.getOwnerUserNo(userId);
			orderInfo.setOwnerUserNo(ownerUserNo);//会员编号	
			//订单号
			orderInfo.setOrderNo(TradeNumberHelper.getPayOrderNo(orderInfo.getOrderType()));
			orderInfo.setCreateTime(System.currentTimeMillis());//订单生成时间
			
			if(StringUtils.isBlank(orderInfo.getOrderStatus()))
				orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_INIT);//订单初始状态0
			
			orderInfo.setVersion(0);//	
			
			String uuid = super.addModel(orderInfo);
			orderInfo.setId(uuid);
			return orderInfo;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建订单时异常",e);
			throw new UPPException("创建订单时异常");
		}
	}	
	@Override
	public OrderInfo createPayConfirmOrderInfo(OrderInfo orderInfo) throws UPPException {
		try{
			
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_PAYOUT);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_PAY_CONFIRM);
			orderInfo.setBusinessStepNo((short) 4);
			orderInfo.setPayConfirmDate(System.currentTimeMillis());
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
			
			return this.createOrderInfo(orderInfo);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建订单时异常",e);
			throw new UPPException("创建订单时异常");
		}
	}	
	@Override
	public OrderInfo createPayCancelOrderInfo(OrderInfo orderInfo)
			throws UPPException {
		try{
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_UNFREEZE_MONEY);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_PAY_CANCEL);
			orderInfo.setBusinessStepNo((short) 4);
			orderInfo.setPayConfirmDate(System.currentTimeMillis());
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
			
			return this.createOrderInfo(orderInfo);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建订单时异常",e);
			throw new UPPException("创建订单时异常");
		}
	}
	@Override
	public OrderInfo createFreezeOrderInfo(OrderInfo orderInfo) throws UPPException {
		try{
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);//订单直接设置为成功状态		
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_FREEZE_MONEY);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FREEZE);
			orderInfo.setPayConfirmDate(System.currentTimeMillis());
			
			return this.createOrderInfo(orderInfo);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建订单时异常",e);
			throw new UPPException("创建订单时异常");
		}
	}

	@Override
	public List<OrderInfo> queryOrderInfo(OrderInfoExampleExtended exampleExtended)
			throws UPPException {
		try {
			
			return super.getModels(exampleExtended);
			
		} catch (Exception e) {
			logger.error("根据条件查询订单集合时异常",e);
			throw new UPPException("根据条件查询订单集合时异常");
		}
	}

	@Override
	public OrderInfo getOrderInfoById(String orderUUID) throws UPPException {
		OrderInfo order = new OrderInfo();
		try {
			order.setId(orderUUID);
			order = super.getModelById(order);
		} catch (Exception e) {
			logger.error("根据ID查询订单异常",e);
			throw new UPPException("根据ID查询订单异常");
		}
		return order;
	}

	@Override
	public PaginationResult<OrderInfo> queryOrderInfoPage(
			OrderInfoExampleExtended exampleExtended) throws UPPException {
		try {
			
			return super.paginateModels(exampleExtended);
			
		} catch (Exception e) {
			logger.error("根据条件分页查询订单集合时异常",e);
			throw new UPPException("根据条件分页查询订单集合时异常");
		}
		
	}

	@Override
	public OrderInfo getOrderByOrderNo(String orderNo) throws UPPException {
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		OrderInfo order = new OrderInfo();
		try {
			OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
			exampleExtended.createCriteria().andOrderNoEqualTo(orderNo);
			list = super.getModels(exampleExtended);
			if(list.size()>0){
				order = list.get(0);
			}
		} catch (Exception e) {
			logger.error("根据支付订单号查询订单信息时异常",e);
			throw new UPPException("根据支付订单号查询订单信息时异常");
		}
		return order;
	}
	@Override
	public boolean updateOrderInfoStatus(String orderId,String status,Long payConfirmDate) throws UPPException{
		try{
			this.saveOrderInfoHistory(orderId);//订单快照
			OrderInfo orderInfo = new OrderInfo ();
			orderInfo.setId(orderId);
			orderInfo.setOrderStatus(status);
			orderInfo.setPayConfirmDate(payConfirmDate);
			int count=super.updateModelPart(orderInfo);
			if(count>0){
				return true;
			}else{
				return false;
			}	
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("更新订单信息时异常", e);
			throw new UPPException("更新订单信息时异常");
		}
	}
	@Override
	public boolean editOrderInfo(OrderInfo orderInfo) throws UPPException {
		try{

			this.saveOrderInfoHistory(orderInfo.getId());//订单快照

			int count=super.updateModelPart(orderInfo);
			if(count>0){
				return true;
			}else{
				return false;
			}	
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("更新订单信息时异常", e);
			throw new UPPException("更新订单信息时异常");
		}
	}
	@Override
	public String saveOrderInfoHistory(String id) throws UPPException{
	//TODO
		return null;
	}

	@Override
	public boolean updateOrderInfoStatus(String orderId, String status,
			Long payConfirmDate, Short businessStep,String orderSubject,Integer version) throws UPPException {
		try{
			OrderInfo condition=new OrderInfo();
			condition.setId(orderId);
			condition.setVersion(version);
			
			this.saveOrderInfoHistory(orderId);//订单快照
			OrderInfo orderInfo = new OrderInfo ();

			orderInfo.setOrderStatus(status);
			orderInfo.setPayConfirmDate(payConfirmDate);
			orderInfo.setBusinessStepNo(businessStep);
			orderInfo.setOrderSubject(orderSubject);
			orderInfo.setVersion(version+1);
			int count=super.updateModelByOtherModel(orderInfo,condition);
			
			if(count>0){
				return true;
			}else{
				UPPException ue= new UPPException("更新不成功");
				throw ue;
			}	
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("更新订单信息时异常", e);
			throw new UPPException("更新订单信息时异常");
		}
	}
	@Override
	public String getTradeExternalNoByWorkOrderNo(String workOrderNo) throws Exception{
		try{
			OrderInfoExampleExtended orderInfoExampleExtended = new OrderInfoExampleExtended();
			orderInfoExampleExtended.createCriteria().andWorkOrderNoEqualTo(workOrderNo);
			
			List<OrderInfo> result = this.getModels(orderInfoExampleExtended);
			if(result!=null&&result.size()>0){
				return result.get(0).getTradeExternalNo();
			}else{
				throw new Exception("更新订单信息时异常");
			}
		}catch(Exception e){
			logger.error("更新订单信息时异常", e);
			throw new UPPException("更新订单信息时异常");
		}
	}


	
	@Override
	public OrderInfo getOrderByWorkOrderNo(String workOrderNo) throws UPPException {
		// TODO Auto-generated method stub
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		try {
			OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
			exampleExtended.createCriteria().andWorkOrderNoEqualTo(workOrderNo).andOrderTypeEqualTo(PayDict.ORDER_SUBJECT_RECHARGE);
			list = super.getModels(exampleExtended);
			if(list.size() > 0){
				return list.get(0);
			}else {
				return null;
			}
		} catch (Exception e) {
			logger.error("根据业务订单号查询订单信息时异常",e);
			throw new UPPException("根据业务订单号查询订单信息时异常");
		}
	}
	@Override
	public OrderInfo getCallbackOrderInfo(String workOrderNo,String tradeExternalNo) throws UPPException {
		try {
			mongoService.setDatasource("LOGS");
			Query<OrderRecord> query= mongoService.getQuery(OrderRecord.class);
			query.field("workOrderNo").equal(workOrderNo);
			query.field("tradeExternalNo").equal(tradeExternalNo);
			
			List<OrderRecord> list =mongoService.query(OrderRecord.class, query);
			
			
			if(list!=null&&list.size()>0){
				OrderRecord record = list.get(0);
				OrderInfo orderInfo = new OrderInfo();
				
				String[] ignoreProperties = {};
				BeanUtils.copyProperties(record,orderInfo,  ignoreProperties);
				return orderInfo;
			}
			OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
			exampleExtended.createCriteria()
			.andWorkOrderNoEqualTo(workOrderNo).andTradeExternalNoEqualTo(tradeExternalNo).andOrderStatusEqualTo(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
			List<OrderInfo> dbList=this.getModels(exampleExtended);
			if(dbList!=null&&dbList.size()>0){
				
				return dbList.get(0);
			}
				
			return null;
		
		} catch (Exception e) {
			UPPException ue = new UPPException(e.getMessage());
			ue.setErrorCode(ReturnCodeDict.ERROR);
			throw ue;
		}
	}
	@Override
	public Integer getOrderCount(OrderInfoExampleExtended exampleExtended) throws UPPException{
		try {
			
			return countModels(exampleExtended);
			
		} catch (Exception e) {
			logger.error("根据条件查询订单集合时异常",e);
			throw new UPPException("根据条件查询订单集合时异常");
		}
	}
	@Override
	public void updateOwnerUserNoById(String id, String ownerUserNo)
			throws UPPException {
		try{
			OrderInfo order = new OrderInfo();
			order.setId(id);
			order.setOwnerUserNo(ownerUserNo);
			super.updateModelPart(order);
		} catch (Exception e) {
			logger.error("修改会员编号时异常",e);
			throw new UPPException("修改会员编号时异常！");
		}
	}
}
