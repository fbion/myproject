package com.ctfo.upp.accountservice.payment.internal.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dto.beans.AbstractTransferResult;
import com.ctfo.payment.intf.external.IPaymentTradeManager;
import com.ctfo.payment.intf.internal.IQueuePaymentManager;
import com.ctfo.upp.callback.ICallback;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.mq.activemq.IQueryMessage;
import com.sinoiov.upp.util.DefaultConfig;

//@Service("iQueuePaymentManager")
public class QueuePaymentManagerImpl extends AbstractPayment implements IQueuePaymentManager{

	private static final Log logger = LogFactory.getLog(QueuePaymentManagerImpl.class);
		
	@Autowired
	private IQueryMessage queryMessage;
	
	@Autowired
	private IPaymentTradeManager paymentTradeManager;
	
	/**
	 * 批量保存发送订单, 主动发送方法
	 * 1.验证参数
	 * 2.保存订单
	 * 3.向MQ发送消息
	 */
	@Override
	public List<OrderInfo> batchSaveOrderInfoAndSendMQ(List<OrderInfo> list)
			throws UPPException {
		List<OrderInfo> result = new ArrayList<OrderInfo>();
		try{
			
			String uuid = "";		
			for(OrderInfo order : list){
				//检查参数正确性
				//uuid = iOrderManager.addOrderInfo(order);
				//order = iOrderManager.getOrderInfoById(uuid);
				logger.info("batchRechargeToUserAccount_sendMQ: " + order);
				queryMessage.sendQueue(order);		
				result.add(order);
			}
			
		}catch(Exception e){
			logger.error("保存发送批量订单异常："+e.getLocalizedMessage(), e);
			throw new UPPException("保存发送批量订单异常："+e.getLocalizedMessage());
		}

		return result;
	}

	/**
	 * 接收处理订单, 被动被调用方法
	 * 注意：此方法如果发生异常时，必须抛出UPPException, 否则队列消息认为业务逻辑已处理成功，当前消息会从队列中清除
	 * 1.接收MQ订阅消息
	 * 2.处理订单
	 * 3.回调业务系统通知处理结果
	 */
	@Override
	public void receiveHandleOrderInfoFromMQ(OrderInfo orderInfo) throws UPPException {
		
		try{
			logger.info("------接到回调对象："+orderInfo);
			
			long ss = System.currentTimeMillis();
			this.handleOrderInfo(orderInfo);
			//测试方法，注意注释
			//this.testHandleOrderInfo(orderInfo);
			long ee = System.currentTimeMillis();
			
			logger.info("------完成对象["+orderInfo+"]的处理,共用时："+(ee - ss));

			
		}catch(Exception e){
			logger.error("队列处理订单异常："+e.getLocalizedMessage(), e);
			throw new UPPException("队列处理订单异常："+e.getLocalizedMessage());
		}
		
	}
	
	/**
	 * 处理订单, 为保证每一个订单都被处理成功，避免多个线程对同一个账户进行操作，此方法为同步方法
	 * 1.接收MQ订阅消息
	 * 2.处理订单
	 * 3.回调业务系统通知处理结果
	 * 此方法目前只实现了订单类型为转帐的业务逻辑，请注意
	 */
	private synchronized void handleOrderInfo(OrderInfo orderInfo)throws Exception{
		
		logger.info("batchRechargeToUserAccount_consuerMQ: " + orderInfo);
		if(PayDict.ORDER_SUBJECT_TRANSFER_ACCOUNT.equals(orderInfo.getOrderType())){
			String orderNo = orderInfo.getOrderNo();
			
			//检查中交支出账户余额是否足够
			String zj_payout = super.getUPPAccountNo(DefaultConfig.getValue("ZJZF_PAYMENT_PAYOUT_ACCOUT_USERID"));
			if(orderInfo.getAccountNo().equals(zj_payout)){
				Account zj_pay_account =null;// internalAccountManager.getAccountByAccountNo(zj_payout);
				if(zj_pay_account.getUsableBalance() < orderInfo.getOrderAmount()){
					//发短信
					orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_FAIL);
				}else{
					AbstractTransferResult result = paymentTradeManager.transferAccounts(orderInfo);					
					//如果是业务系统帐户支付，回调业务系统
					orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
					orderInfo.setPayConfirmDate(result.getPayConfirmDate());
				}				
				if(StringUtils.isNotBlank(orderInfo.getCallbackUrl())){					
					orderInfo.setOrderNo(orderNo);
					//回调业务系统
					ICallback callback = (ICallback) ServiceFactory.getFactory().getService(ICallback.class);
					callback.rechargeCallBack(orderInfo);	
				}
			}		
		}
	}
	
	
	
	
	//测试用
	private synchronized void testHandleOrderInfo(OrderInfo orderInfo){
			
			try {
				
				System.out.println("==================================");
				System.out.println("======"+orderInfo.getId());
				System.out.println("======"+orderInfo.getAccountNo());
				System.out.println("======"+orderInfo.getCallbackUrl());
				System.out.println("======"+orderInfo.getClentId());
				System.out.println("======"+orderInfo.getClentType());
				System.out.println("======"+orderInfo.getCollectMoneyAccountNo());
				System.out.println("======"+orderInfo.getCollectMoneyUserId());
				System.out.println("======"+orderInfo.getOrderAmount());
				System.out.println("======"+orderInfo.getWorkOrderNo());
				System.out.println("======"+orderInfo.getPayChannel());	
				System.out.println("==================================");
				
				Thread.sleep(3000);
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	  
}
