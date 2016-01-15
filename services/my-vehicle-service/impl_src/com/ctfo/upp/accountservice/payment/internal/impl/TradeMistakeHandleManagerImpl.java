package com.ctfo.upp.accountservice.payment.internal.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.file.boss.IMongoService;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.TransferAccountsToDealWithResult;
import com.ctfo.payment.dto.beans.TransferAccountsToDealWithResultDTO;
import com.ctfo.payment.intf.internal.IPaymentBaseManager;
import com.ctfo.payment.intf.internal.IPaymentTransManager;
import com.ctfo.payment.intf.internal.ITradeMistakeHandleManager;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.manager.support.UppGenericManagerImpl;
import com.google.code.morphia.query.Query;

public class TradeMistakeHandleManagerImpl extends UppGenericManagerImpl<Object, Object> implements
		ITradeMistakeHandleManager {
	private static final Log logger = LogFactory
			.getLog(TradeMistakeHandleManagerImpl.class);
	private IMongoService mongoService;
	private IPaymentTransManager paymentTransManager;
	private IPaymentBaseManager paymentBaseManagerService;


	@Override
	public void dealWithMistakeResult() throws UPPException {
		try {
			Long time=System.currentTimeMillis()-3600000;   //超过一小时任然没有更新状态的记录

			Query query = mongoService.getQuery(TransferAccountsToDealWithResult.class);
			query.field("editTime").lessThanOrEq(time);
			query.field("dealWithCount").lessThanOrEq(5);
			List<TransferAccountsToDealWithResult> beanList =mongoService.query(TransferAccountsToDealWithResult.class, query);
			dealWithResultList(beanList);
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public List<TransferAccountsToDealWithResult> queryTransferAccountsToDealWithResult(
			TransferAccountsToDealWithResultDTO resultDTO) throws UPPException {
		try {
			Query query =mongoService.getQuery(TransferAccountsToDealWithResult.class);
			if(resultDTO.getBeginTime()!=null){
				query.field("editTime").greaterThanOrEq(resultDTO.getBeginTime());
			}
			if(resultDTO.getEndTime()!=null){
				query.field("editTime").lessThan(resultDTO.getEndTime());
			}
			if(resultDTO.getTransferAccountsToDealWithResult().getId()!=null&&!"".equals(resultDTO.getTransferAccountsToDealWithResult().getId())){
				query.field("id").equal(resultDTO.getTransferAccountsToDealWithResult().getId());
			}
		 
			 return mongoService.query(TransferAccountsToDealWithResult.class, query);
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}
	private void dealWithResultList(List<TransferAccountsToDealWithResult> resultList)throws UPPException{
		for(TransferAccountsToDealWithResult result:resultList){
			if(this.resultRecordCheck(result)){
				
			}
		}
	}
	
	
	/**
	 * 处理差异记录
	 * @param result
	 * @return
	 * @throws Exception 
	 */
	private boolean resultRecordDealing(TransferAccountsToDealWithResult result)throws Exception{
		try{
			PaymentTrade paymentTrade =paymentBaseManagerService.queryPaymentTransTradeExternalNo(result.getId());
			
			if(paymentTrade==null){
				this.addModel(result.getOrderInfo());
				this.addModel(result.getPaymentTrade());
				
			}
			
			
			paymentTransManager.accountAtomHandle(result.getOrderInfo());
			
			paymentTransManager.transferAccountsPostposition(result.getOrderInfo(), result.getPaymentTrade());
			
			return true;
		}catch(UPPException e){
			return false;
		}
		
	}
	private boolean resultRecordCheck(TransferAccountsToDealWithResult reuslt)throws UPPException{
		
		PaymentTrade paymentTrade =paymentBaseManagerService.queryPaymentTransTradeExternalNo(reuslt.getId());
		if(paymentTrade==null){  //查不到对应的交易记录，说明相关记录已经回滚
			return true;
			
		}else if(Integer.valueOf(paymentTrade.getTradeStatus())<Integer.valueOf(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS)){
			//数据库中的交易状态与实际不一致
			return true;
		}else{//数据库中的交易状态与实际一致，说明交易不需要再惊醒处理
			logger.error(reuslt.getId()+":该交易已经被处理,将删除结果记录");
			
			paymentBaseManagerService.handleTradeRecord(reuslt.getId());
			logger.error(reuslt.getId()+":该交易已经被处理,将删除结果记录");
			return false;
		}
	}
	
	
	public IMongoService getMongoService() {
		return mongoService;
	}

	public void setMongoService(IMongoService mongoService) {
		this.mongoService = mongoService;
	}

	public IPaymentTransManager getPaymentTransManager() {
		return paymentTransManager;
	}

	public void setPaymentTransManager(IPaymentTransManager paymentTransManager) {
		this.paymentTransManager = paymentTransManager;
	}

	public IPaymentBaseManager getPaymentBaseManagerService() {
		return paymentBaseManagerService;
	}

	public void setPaymentBaseManagerService(
			IPaymentBaseManager paymentBaseManagerService) {
		this.paymentBaseManagerService = paymentBaseManagerService;
	}
	

}
