package com.ctfo.upp.accountservice.payment.external.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.intf.internal.IWithdrawCashManager;
import com.ctfo.payment.intf.internal.IWithdrawCashRapidManager;
import com.ctfo.upp.exception.UPPException;

//@Service("WithdrawCashManagerExternalImpl")
public class WithdrawCashManagerExternalImpl implements com.ctfo.payment.intf.external.IWithdrawCashManager{
	
	private static final Log logger = LogFactory.getLog(WithdrawCashManagerExternalImpl.class);
	
	@Autowired
	@Qualifier("iWithdrawCashManager")  
	private IWithdrawCashManager iWithdrawCashManager;
	@Autowired
	@Qualifier("iWithdrawCashRapidManager") 
	private IWithdrawCashRapidManager iWithdrawCashRapidManager;

	@Override
	public String withdrawCash(OrderInfo orderInfo) throws UPPException {
		try{
			
			return iWithdrawCashManager.withdrawCash(orderInfo);
			
		}catch(Exception e){
			logger.error("提现异常！",e);
			throw new UPPException("提现异常");
		}
	}

	@Override
	public String withdrawCashRapid(OrderInfo orderInfo) throws UPPException {
		try{
			
			return iWithdrawCashRapidManager.withdrawCashRapid(orderInfo);
			
		}catch(Exception e){
			logger.error("快速提现异常！",e);
			throw new UPPException("快速提现异常");
		}
	}

}
