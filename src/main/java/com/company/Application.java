package com.company;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class Application {

	public static void main(String[] args) {
		       //test
		ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext("r.xml");
		
//		QueueSender qs =  cpx.getBean(QueueSender.class);
//		qs.send("hello world");
		System.out.println("start...");
//		try {
//			Thread.sleep(Integer.MAX_VALUE);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		cpx.close();
	}
}
