package com.sinoiov.upp.mq.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;

/**
 * 消息适配转换器，如果发的消息需要转换，在此类中实现
 * @author malongqing
 * @date   2015-04-22
 */
//@Service("messageConverter")
public class MessageAdaptorConverter implements MessageConverter {

	private static final Log logger = LogFactory.getLog(MessageAdaptorConverter.class);
		
	/**
	 * 转换接收到的消息为对象
	 */
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException{
		
		if (logger.isDebugEnabled()) {
			logger.debug("Receive JMS message :"+message);
		}
		if (message instanceof ObjectMessage) {
			
			ObjectMessage oMsg = (ObjectMessage)message;
			
			if (oMsg instanceof ActiveMQObjectMessage) {
				
				ActiveMQObjectMessage aMsg = (ActiveMQObjectMessage)oMsg;
				
				if(aMsg.getObject() instanceof OrderInfo){					
					return (OrderInfo)aMsg.getObject();						
				}else{
					logger.error("Message:${} is not a instance of object."+message.toString());
					throw new JMSException("Message:"+message.toString()+"is not a instance of object."+message.toString());
				}
				
			}else{
				logger.error("Message:${} is not a instance of ActiveMQObjectMessage."+message.toString());
				throw new JMSException("Message:"+message.toString()+"is not a instance of ActiveMQObjectMessage."+message.toString());
			}
		}else {
			logger.error("Message:${} is not a instance of ObjectMessage."+message.toString());
			throw new JMSException("Message:"+message.toString()+"is not a instance of ObjectMessage."+message.toString());
		}
	}

	
	/**
	 * 转换对象到消息
	 */
	@Override
	public Message toMessage(Object obj, Session session) throws JMSException, MessageConversionException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Convert object to JMS message:${}"+obj.toString());
		}
		
		if (obj instanceof OrderInfo) {
			
			ActiveMQObjectMessage msg = (ActiveMQObjectMessage)session.createObjectMessage();
			
			msg.setObject((OrderInfo)obj);
			
			return msg;
			
		}else {
			logger.debug("Convert object to JMS message:${}"+obj.toString());
		}
		return null;
	}
}
