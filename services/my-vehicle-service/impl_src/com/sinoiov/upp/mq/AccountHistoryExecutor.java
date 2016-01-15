package com.sinoiov.upp.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.sinoiov.upp.manager.account.IAccountHistoryManager;
import com.sinoiov.yyzc.commons.kafka.YyzcKafkaMessageExecutor;
import com.sinoiov.yyzc.commons.kafka.exceptions.YyzcKafkaException;

@Service("pay_account_history_executor")
public class AccountHistoryExecutor implements YyzcKafkaMessageExecutor {
	
	private static final Log logger = LogFactory.getLog(AccountHistoryExecutor.class);
	
	@Autowired
	@Qualifier("accountHistoryManager")
	private IAccountHistoryManager accountHistoryManager;

	@Override
	public void execute(Object message) throws YyzcKafkaException {
		try {
			
			Account account = (Account) message;
			
			accountHistoryManager.receiveHandleMQ(account);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"处理账户历史消息时异常:"+e.getMessage());
		}
	}

//	@Override
//	public Object transfer(String json) throws YyzcKafkaException {
//	
//		try {
//			
//			return JSONUtil.json2Object(json, Account.class);
//		
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"将json转成消息对象时异常:"+e.getMessage());
//		}
//	}

	@Override
	public TypeReference<Account> transferToObjectClass() throws Exception {
		try {
			TypeReference<Account> tr = new TypeReference<Account>() {};
			return tr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"将json转成消息对象时异常:"+e.getMessage());
		}
	}
}
