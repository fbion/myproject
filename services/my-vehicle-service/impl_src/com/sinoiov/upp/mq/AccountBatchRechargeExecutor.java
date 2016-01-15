package com.sinoiov.upp.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.sinoiov.upp.business.payment.IPaymentTradeBusiness;
import com.sinoiov.yyzc.commons.kafka.YyzcKafkaMessageExecutor;
import com.sinoiov.yyzc.commons.kafka.exceptions.YyzcKafkaException;

@Service("pay_account_batch_recharge_executor")
public class AccountBatchRechargeExecutor implements YyzcKafkaMessageExecutor {
	
	private static final Log logger = LogFactory.getLog(AccountBatchRechargeExecutor.class);
	
	@Autowired
	@Qualifier("paymentTradeBusiness")
	private IPaymentTradeBusiness paymentTradeBusiness;

	@Override
	public void execute(Object message) throws YyzcKafkaException {
		try {
			
			OrderInfo orderInfo = (OrderInfo) message;
			
			paymentTradeBusiness.handleBatchAccountRecharge(orderInfo);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"处理订单消息时异常:"+e.getMessage());
		}
	}

//	@Override
//	public Object transfer(String json) throws YyzcKafkaException {
//	
//		try {
//			
//			return JSONUtil.json2Object(json, OrderInfo.class);
//		
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"将消息json转成订单对象时异常:"+e.getMessage());
//		}
//	}

	@Override
	public TypeReference<OrderInfo> transferToObjectClass() {
		try {
			TypeReference<OrderInfo> tr = new TypeReference<OrderInfo>() {};
			return tr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"将消息json转成订单对象时异常:"+e.getMessage());
		}
	}
}
