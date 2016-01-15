package com.ctfo.upp;

import javax.jms.Connection;  
import javax.jms.ConnectionFactory;  
import javax.jms.Destination;  
import javax.jms.JMSException;  
import javax.jms.Message;  
import javax.jms.MessageConsumer;  
import javax.jms.Session;  
import javax.jms.TextMessage;  
   
import org.apache.activemq.ActiveMQConnectionFactory; 

public class ReceiveMessage {

	 private static final String url = "tcp://192.168.100.58:61616";  
     private static final String QUEUE_NAME = "G2Queue";  

     public void receiveMessage() {  
               Connection connection = null;  
               try{  
                        try{  
                                 ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(  
                                                    url);  
                                 connection= connectionFactory.createConnection();  
                        }catch (Exception e) {  
                                 System.out.println(e.toString());  
                        }  
                        connection.start();  
                        Session session = connection.createSession(false,  
                                           Session.AUTO_ACKNOWLEDGE);  
                        Destination destination = session.createQueue(QUEUE_NAME);  
                        //消息接收者，也就是消费者  
                        MessageConsumer consumer = session.createConsumer(destination);  
                         
                        consumeMessagesAndClose(connection, session, consumer); 
                        
               }catch (Exception e) {  
                        System.out.println(e.toString());  
               }  
     }  
     /** 
      * 接收和关闭消息，如遇到消息内容为close则，关闭连接 
      * 
      * @param connection   JMS 客户端到JMSProvider 的连接 
      * @param session                   发送或接收消息的线程 
      * @param consumer              消息接收对象 
      * @throws JMSException 
      * 
      */  
     protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer) throws JMSException {  
               do{  
                        Message message = consumer.receive(1000);  
                        if("close".equals(message)){  
                                 consumer.close();  
                                 session.close();  
                                 connection.close();  
                        }  
                        if(message != null) {  
                                 onMessage(message);  
                        }  
               }while (true);  
                
     }  

     public void onMessage(Message message) {  
               try{  
                        if(message instanceof TextMessage) {  
                                 TextMessage txtMsg = (TextMessage) message;  
                                 String msg = txtMsg.getText();  
                                 System.out.println("Received:" + msg);  
                        }  
               }catch (Exception e) {  
                        e.printStackTrace();  
               }  

     }  

     public static void main(String args[]) {  
               ReceiveMessage rm = new ReceiveMessage();  
               rm.receiveMessage();  
     }  
     
}
