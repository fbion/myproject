package com.sinoiov.upp.mq.activemq;

import javax.jms.Destination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * 消息生产者, 发送消息到队列中, 使用Spring JmsTemplate 的消息生产者
 * @author malongqing
 * @date   2015-04-22
 */
//@Service("queueMessageProducer")
public class QueueMessageProducer implements IQueryMessage{

	private static final Log logger = LogFactory.getLog(QueueMessageProducer.class);
			
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination notifyQueue;
	@Autowired
	private MessageConverter messageConverter;

	private void sendMessage(Object bean)throws Exception{
		
		jmsTemplate.setMessageConverter(messageConverter);
		
		jmsTemplate.setPubSubDomain(false);
		
		jmsTemplate.convertAndSend(notifyQueue, bean);
		
	}

	

	@Override
	public void sendQueue(Object bean){
		try{
			
			sendMessage(bean);
			
		}catch(Exception e){
			logger.error("发送消息异常："+e.getLocalizedMessage(), e);
			//保存到mongo中
		}
		
	}	
	
}
