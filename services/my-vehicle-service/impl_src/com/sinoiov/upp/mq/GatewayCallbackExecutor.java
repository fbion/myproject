package com.sinoiov.upp.mq;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.upp.dict.ReturnCodeDict;
import com.sinoiov.upp.callback.IGatewayCallback;
import com.sinoiov.yyzc.commons.kafka.YyzcKafkaMessageExecutor;
import com.sinoiov.yyzc.commons.kafka.exceptions.YyzcKafkaException;

@Service("pay_gateway_callback_accountservice_executor")
public class GatewayCallbackExecutor implements YyzcKafkaMessageExecutor {
	
	private static final Log logger = LogFactory.getLog(GatewayCallbackExecutor.class);
	
	@Autowired
	@Qualifier("gatewayCallbackImpl")
	private IGatewayCallback gatewayCallbackImpl;

	@Override
	public void execute(Object message) throws YyzcKafkaException {
		try {
			Map<String, String> map = (Map<String, String>) message;
			
			if(map.get("yeepayType").equals("fast")) {
				gatewayCallbackImpl.handleYeepayFast(map.get("orderNo"), new BigDecimal(map.get("money")), Boolean.valueOf(map.get("status")), Long.valueOf(map.get("time")));
			}
			if(map.get("yeepayType").equals("web")) {
				gatewayCallbackImpl.handleYeepayWeb(map.get("orderNo"), new BigDecimal(map.get("money")), Boolean.valueOf(map.get("status")), Long.valueOf(map.get("time")));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"处理账户历史消息时异常:"+e.getMessage());
		}
	}

//	@Override
//	public Object transfer(String json) throws YyzcKafkaException {
//	
//		try {
//			logger.info("接收到kafka的消息转化操作：" + json);
//			return JSONUtil.json2Object(json, Map.class);
//		
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return null;
////			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"将json转成消息对象时异常:"+e.getMessage());
//		}
//	}

	@Override
	public TypeReference transferToObjectClass() throws Exception {
		try {
			TypeReference<Map> tr = new TypeReference<Map>() {};
			return tr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new YyzcKafkaException(ReturnCodeDict.ERROR,"将json转成消息对象时异常:"+e.getMessage());
		}
	}
}
