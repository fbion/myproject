package com.ctfo.upp.accountservice.payment.external.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.intf.internal.IQueuePaymentManager;
import com.ctfo.upp.exception.UPPException;

//@Service("QueuePaymentManagerExternalImpl")
public class QueuePaymentManagerExternalImpl implements com.ctfo.payment.intf.external.IQueuePaymentManager{

	private static final Log logger = LogFactory.getLog(QueuePaymentManagerExternalImpl.class);
	
	@Autowired
	@Qualifier("iQueuePaymentManager")  
	private IQueuePaymentManager queuePaymentManager;
	
	
	@Override
	public List<OrderInfo> batchTransferAccounts(List<OrderInfo> list)
			throws UPPException {
		try{
			
			list = queuePaymentManager.batchSaveOrderInfoAndSendMQ(list);
					
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("充值外部接口服务异常！",e);
			throw new UPPException("充值外部接口服务异常");
		}
		return list;
	}


	@Override
	public void receiveHandleOrderInfoFromMQ(OrderInfo orderInfo)
			throws UPPException {
		// TODO Auto-generated method stub
		
	}

}
