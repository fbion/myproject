package com.ctfo.upp.accountservice.payment.internal.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.AccountingDay;
import com.ctfo.payment.dao.beans.AccountingDayExampleExtended;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.intf.internal.AccountingDayManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.manager.support.UppGenericManagerImpl;
import com.ctfo.upp.models.PaginationResult;

//@Service("accountingDayManager")
public class AccountingDayManagerImpl extends UppGenericManagerImpl<Object, Object> implements AccountingDayManager{
	private static Log logger = LogFactory.getLog(AccountingDayManagerImpl.class);
	@Override
	public String addAccountingDay(AccountingDay accountingDay)
			throws UPPException {
		try{
			return super.addModel(accountingDay);
		}catch(Exception e){
			logger.error("创建日切记录异常:"+e.getMessage(),e);
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public int modifyAccountingDay(AccountingDay accountingDay)
			throws UPPException {
		try{
			return super.updateModelPart(accountingDay);
		}catch(Exception e){
			logger.error("创建订单时异常",e);
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public int revokeAccountingDay(String orderUUID) throws UPPException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AccountingDay getAccountingDayById(String id) throws UPPException {
		
		try{
			AccountingDay accountingDay=new AccountingDay();
			accountingDay.setId(id);
			return super.getModelById(accountingDay);
		}catch(Exception e){
			logger.error("创建订单时异常",e);
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public List<AccountingDay> queryAccountingDayList(
			AccountingDayExampleExtended exampleExtended) throws UPPException {
		try{								

			return super.getModels(exampleExtended);

		}catch(Exception e){
			logger.error("根据查询条件查询订单集合时异常", e);
			throw new UPPException("根据查询条件查询订单集合时异常"+e.getMessage());
		}
	}

	@Override
	public PaginationResult<AccountingDay> queryAccountingDayPage(
			AccountingDayExampleExtended exampleExtended) throws UPPException {
		try{								

			return super.paginateModels(exampleExtended);

		}catch(Exception e){
			logger.error("根据查询条件查询订单集合时异常", e);
			throw new UPPException("根据查询条件查询订单集合时异常"+e.getMessage());
		}
	}
	public AccountingDay getAccountingDay(String accountNo,String accountDate) throws UPPException{
		try{			
			AccountingDayExampleExtended accountingDayExampleExtended = new AccountingDayExampleExtended();
			 accountingDayExampleExtended.createCriteria().andAccountDateEqualTo(accountDate).andInsideAccountNoEqualTo(accountNo);
			 List<AccountingDay> list=this.queryAccountingDayList(accountingDayExampleExtended);
			 if(list.size()>0){
				 return list.get(0);
			 }else{
				 return null;
			 }
		}catch(Exception e){
			logger.error("根据查询条件查询订单集合时异常", e);
			throw new UPPException("根据查询条件查询订单集合时异常:"+e.getMessage());
		}
	}
	
}
