package com.ctfo.upp.test.sms;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.ctfo.upp.service.sms.bean.Sms;
import com.google.code.morphia.query.Query;

/**
 * 短信模板测试
 * 
 * @author jichao
 */
@SuppressWarnings("unchecked")
public class SmsTest extends BaseTest {
	
	private static Log logger = LogFactory.getLog(SmsTest.class);
	
	@Test
	public void testMongoConn(){
		Sms sms = new Sms();
		sms.setStatus(1);
		sms.setTemplateCode("tcode002");
		sms.setTemplateName("tname002");
		sms.setTemplateContent("tcontent002");
		try {
			mongoService.setDatasource("BUSINESS");
			mongoService.save(sms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Query<Sms> query = mongoService.getQuery(Sms.class);
		try {
			List<Sms> list = mongoService.query(Sms.class, query);
			for(Sms s: list){
				logger.info(s.getTemplateCode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
