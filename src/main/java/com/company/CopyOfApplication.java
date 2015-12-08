package com.company;

import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ProducerAck;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class CopyOfApplication {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext("applicationContext2.xml");
		
//		QueueSender qs =  cpx.getBean(QueueSender.class);
//		qs.send("hello world");
	
		
		JmsTemplate  jt=  (JmsTemplate) cpx.getBean("myJmsTemplate");
		
		PooledConnectionFactory pcf = cpx.getBean(PooledConnectionFactory.class);
		QueueConnection qc =null;
		try {
		qc= pcf.createQueueConnection();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return;
		}
		
		System.out.println("qc"+qc);
		
		Session session  = null;
		try {
			session= qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println("session="+session);
		MessageProducer mp  = null;
		try {
		mp =   session.createProducer(ActiveMQDestination.createDestination("Queue.Name", ActiveMQDestination.QUEUE_TYPE));
			
			System.out.println("mp="+mp);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		ActiveMQTextMessage tm =  new ActiveMQTextMessage();
		try {
			tm.setText("you are good");
			mp.send(tm);
			
			mp.close();
			session.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		for(int i=0;i<1000;i++)
//		
//		{
//			System.out.println(".. " + i);
//			jt.convertAndSend("Queue.Name","ssss||ss||-"+new Random().nextInt(Integer.MAX_VALUE));
//		}
		
		
		cpx.close();
	}
}
