package com.ctfo.upp.test.sms;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ctfo.file.boss.IMongoService;

public class BaseTest {
	public static ApplicationContext ctx = null;
	@SuppressWarnings("rawtypes")
	public static IMongoService mongoService;

	@SuppressWarnings("rawtypes")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new ClassPathXmlApplicationContext(new String[] { "classpath:spring-all.xml" });
		mongoService = (IMongoService)ctx.getBean("mongoService");
	}

}
