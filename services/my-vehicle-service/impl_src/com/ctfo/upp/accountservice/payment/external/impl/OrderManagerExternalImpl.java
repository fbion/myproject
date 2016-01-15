package com.ctfo.upp.accountservice.payment.external.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.payment.intf.internal.IOrderManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;

//@Service("OrderManagerExternalImpl")
public class OrderManagerExternalImpl implements com.ctfo.payment.intf.external.IOrderManager {

	private static final Log logger = LogFactory.getLog(OrderManagerExternalImpl.class);
	
	@Autowired
	@Qualifier("iOrderManager")  
	private IOrderManager iOrderManager;
	
	@Override
	public OrderInfo createOrderInfo(OrderInfo orderInfo) throws UPPException {
		try{
			
			String uuid = iOrderManager.addOrderInfo(orderInfo);
			orderInfo.setId(uuid);			
			return orderInfo;
			
		}catch(Exception e){
			logger.error("生成订单异常！",e);
			throw new UPPException("生成订单异常");
		}
		
	}

	@Override
	public List<OrderInfo> queryOrderInfo(
			OrderInfoExampleExtended exampleExtended) throws UPPException {
		try{
			
			 return iOrderManager.queryOrderInfoList(exampleExtended);
			
		}catch(Exception e){
			logger.error("根据条件查询订单信息异常！",e);
			throw new UPPException("根据条件查询订单信息异常");
		}
	}

	@Override
	public OrderInfo getOrderInfoById(String orderUUID) throws UPPException {
		try{
			
			 return iOrderManager.getOrderInfoById(orderUUID);
			
		}catch(Exception e){
			logger.error("根据ID查询订单信息异常！",e);
			throw new UPPException("根据ID查询订单信息异常!");
		}
	}

	@Override
	public PaginationResult<OrderInfo> queryOrderInfoByPage(
			OrderInfoExampleExtended exampleExtended) throws UPPException {
		try{
			
			 return iOrderManager.queryOrderInfoPage(exampleExtended);
			
		}catch(Exception e){
			logger.error("根据条件查询订单页面异常！",e);
			throw new UPPException("根据条件查询订单页面异常");
		}
	}

	@Override
	public OrderInfo getOrderByOrderNo(String orderNo) throws UPPException {
		try{
			
			return iOrderManager.getOrderInfoByOrderNo(orderNo);
			
		}catch(Exception e){
			logger.error("根据订单号查询订单异常！",e);
			throw new UPPException("根据订单号查询订单异常");
		}	
	}

	@Override
	public boolean editOrderInfo(OrderInfo orderInfo) throws UPPException {
		boolean flag = false;
		try{
			
			 iOrderManager.modifyOrderInfo(orderInfo);
			 flag = true;
		}catch(Exception e){
			logger.error("根据订单号查询订单异常！",e);
			throw new UPPException("根据订单号查询订单异常");
		}	
		return flag;
	}

}
