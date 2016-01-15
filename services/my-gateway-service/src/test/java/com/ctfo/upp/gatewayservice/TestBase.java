package com.ctfo.upp.gatewayservice;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctfo.upp.soa.ServiceFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-all.xml" })
public abstract class TestBase extends AbstractJUnit4SpringContextTests {
	

	@Before
	public void setUp() throws Exception {
	
	}

	@SuppressWarnings("unchecked")
	protected <S> S getService(Class<S> clazz) {
		return (S) ServiceFactory.getFactory().getService(clazz);
	}

	@After
	public void destroy() throws Exception {
		
	}

}
