package com.sinoiov.pay.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.impl.multithread.TaskPool;
import com.ctfo.upp.models.InteractiveRecord;

public class LogRecordUtil {
	private static Log logger = LogFactory.getLog(LogRecordUtil.class);
	private static ApplicationContext cxt = null;
	static private IMongoService mongoService;

	static protected final String __INTERACTION_LOG_EXECUTOR_TASK_POOL__ = "__interaction_log_executor_task_pool__";

	static {
		try {
			String[] classXmlContexts = { "spring-all.xml" };
			cxt = new ClassPathXmlApplicationContext(classXmlContexts);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		mongoService = (IMongoService) cxt.getBean("mongoService");
	}

	public static void saveRecord(final String paramJson, final String returnJson, final String storeCode,
			final String operCode) {
		TaskPool.named(__INTERACTION_LOG_EXECUTOR_TASK_POOL__).setPoolSize(4).submit(new Runnable() {

			@Override
			public void run() {
				doSaveRecord(paramJson, returnJson, storeCode, operCode);
			}

		});
	}

	public static void doSaveRecord(String paramJson, String returnJson, String storeCode, String operCode) {
		try {
			InteractiveRecord interactiveRecord = new InteractiveRecord();
			interactiveRecord.setId(java.util.UUID.randomUUID().toString());
			interactiveRecord.setCreateTime(System.currentTimeMillis());
			interactiveRecord.setOperCode(operCode);
			interactiveRecord.setRecordSystem("");
			interactiveRecord.setJsonData(paramJson);
			interactiveRecord.setReturnData(returnJson);
			mongoService.setDatasource("LOGS");
			mongoService.save(interactiveRecord);
		} catch (Exception e) {
			logger.warn("记录接口日志异常：" + e.getMessage(), e);
		}
	}

}
