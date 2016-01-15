package com.zyzfpt.portal.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ctfo.upp.root.utils.SmsSender;

public class TestSMS extends BaseTest{
	
	public static ApplicationContext ctx;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	

	public static void main(String[] a){
		try {
			ctx = new ClassPathXmlApplicationContext(new String[] { "classpath*:spring-all.xml" });
		} catch (Exception e) {
			System.out.println("BaseTest启动Spring报错了！");
			e.printStackTrace();
		}
//		SmsSender.getInstance().sendSms("18611153002", "您已成功开户，账户余额为0元。");
		List<String> param = new ArrayList<String>();
		param.add("11");
		param.add("22");
		param.add("33");
		SmsSender.getInstance().sendSmsByTemplate("18611153002", "tyzfpt1006", param);
	}
	
}
