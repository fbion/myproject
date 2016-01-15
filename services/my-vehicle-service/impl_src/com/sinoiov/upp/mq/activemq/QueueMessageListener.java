package com.sinoiov.upp.mq.activemq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.intf.internal.IQueuePaymentManager;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.util.DefaultConfig;

/**
 * 消息消费者, 接收消息并进行相关业务处理
 * @author malongqing
 * @date   2015-04-22
 */
//@Service("queueMessageConsumer")
public class QueueMessageListener implements MessageListener {

	private static final Log logger = LogFactory.getLog(QueueMessageListener.class);
	
	@Autowired
	private MessageConverter messageConverter;

	/**
	 * 接收消息
	 */
	@Override
	public void onMessage(Message message){
		try {
			
			ObjectMessage objectMessage = (ObjectMessage)message;
			
			if(logger.isDebugEnabled()){
				logger.debug("-----model:"+objectMessage.getJMSDeliveryMode());  
				logger.debug("-----destination:"+objectMessage.getJMSDestination());  
				logger.debug("-----type:"+objectMessage.getJMSType());  
				logger.debug("-----messageId:"+objectMessage.getJMSMessageID());  
				logger.debug("-----time:"+objectMessage.getJMSTimestamp());  
				logger.debug("-----expiredTime:"+objectMessage.getJMSExpiration());  
				logger.debug("-----priority:"+objectMessage.getJMSPriority());
			}
			
			Object bean = messageConverter.fromMessage(objectMessage);
			
			if(bean instanceof OrderInfo){
				logger.debug("queue收到消息:"+bean);
				this.handleOrderInfo((OrderInfo)bean);	
			}else{
				logger.warn("没有对应的消息处理业务对象："+ bean);
			}
			
		}catch(UPPException ue){
			logger.error("处理业务逻辑时发生异常:"+ue.getLocalizedMessage(), ue);
			try {
				Thread.sleep(Integer.parseInt(DefaultConfig.getValue("QUERY_SLEEP_TIME")));
			}catch (InterruptedException e1){
				logger.info("队列处理订单异常", e1);
			}
			throw new RuntimeException("处理业务逻辑时发生异常:"+ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("处理消息时发生异常:"+e.getLocalizedMessage(), e);
			try {
				Thread.sleep(Integer.parseInt(DefaultConfig.getValue("QUERY_SLEEP_TIME")));
			}catch (InterruptedException e1){
				logger.info("队列处理订单异常", e1);
			}
			throw new RuntimeException("处理业务逻辑时发生异常:"+e.getLocalizedMessage());
		}
	}
	
	
	private void handleOrderInfo(OrderInfo orderInfo)throws Exception{
		
		//queuePaymentManager.receiveHandleOrderInfoFromMQ(orderInfo);

	}
	
}
