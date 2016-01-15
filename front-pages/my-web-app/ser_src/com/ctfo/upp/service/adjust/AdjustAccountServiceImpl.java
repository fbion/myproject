package com.ctfo.upp.service.adjust;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.payment.intf.thirdpartquery.external.ExternalGateWayQueryManager;
import com.ctfo.payment.intf.thirdpartquery.external.ExternalRecordHandleManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.service.AbstractService;
import com.ctfo.upp.service.account.AccountServiceImpl;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.DefaultConfig;

@Service("adjustAccountService")
public class AdjustAccountServiceImpl extends AbstractService implements AdjustAccountService {
	private static Log logger = LogFactory.getLog(AdjustAccountServiceImpl.class);
	
	private ExternalRecordHandleManager manager = null;
//	private ExternalGateWayQueryManager gateWayQueryManager = null;
	
//	private ExternalRecordHandleManager getManager() {
//		if (this.manager == null) {
//			manager = (ExternalRecordHandleManager) ServiceFactory.getFactory().getService(ExternalRecordHandleManager.class);
//		}
//		return this.manager;
//	}
	
//	private ExternalGateWayQueryManager getGateWayQueryManager() {
//		if (this.gateWayQueryManager == null) {
//			gateWayQueryManager = (ExternalGateWayQueryManager) ServiceFactory.getFactory().getService(ExternalGateWayQueryManager.class);
//		}
//		return this.gateWayQueryManager;
//	}

	@Override
	public String queryObject(String name) throws UPPException {
//		return this.getManager().queryMongoDbRecord(name);
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_MONGO_RECORD_PARAM: " + JSONObject.fromObject(map).toString());
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_QUERY_MONGO_RECORD"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_MONGO_RECORD: " + json);
			return getReturnString(json, RESULT_DATA_SINGLE_STR_NAME);
		} catch (Exception e) {
			logger.error("queryMongoDbRecord_error", e);
			throw new UPPException("queryMongoDbRecord_error", e);
		}
	}
	
	@Override
	public String queryTableHeader(String name) throws UPPException {
//		return this.getManager().queryTableHeader(name);
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_TABLE_HEADER_PARAM: " + JSONObject.fromObject(map).toString());
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_QUERY_TABLE_HEADER"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_TABLE_HEADER: " + json);
			return getReturnString(json, RESULT_DATA_SINGLE_STR_NAME);
		} catch (Exception e) {
			logger.error("queryTableHeader_error", e);
			throw new UPPException("queryTableHeader_error", e);
		}
		
	}
	@Override
	public String gateWayQuery(String accountNo) throws UPPException {
		
//		return this.getGateWayQueryManager().queryAccountInfo(accountNo);
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", accountNo);
			logger.info("UPP_HTTP_INTERFACE_UPP_GATEWAY_QUERY_ACCOUNT_INFO_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_GATEWAY_QUERY_ACCOUNT_INFO"));
			logger.info("UPP_HTTP_INTERFACE_UPP_GATEWAY_QUERY_ACCOUNT_INFO: " + json);
			return getReturnString(json, RESULT_DATA_SINGLE_STR_NAME);
		} catch (Exception e) {
			logger.error("gateway_query_acc_info_err: ", e);
			throw new UPPException("gateway_query_acc_info_err", e);
		}
	}
}
