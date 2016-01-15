package com.ctfo.upp.service.transferBatchSynch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.account.contrast.intf.external.WithYBContrast;
import com.ctfo.account.dao.beans.Account;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dto.beans.YBTradeRecordContrastDto;
import com.ctfo.payment.intf.external.IExternalClearingAndSettlementManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.service.AbstractService;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.DefaultConfig;

@Service
public class TransferBatchSyncServiceImpl extends AbstractService implements ITransferBatchSyncSercive{
	
//	private WithYBContrast withYBContrast;
	
//	private IExternalClearingAndSettlementManager externalClearingAndSettlementManager;
	
	private static Log logger = LogFactory.getLog(TransferBatchSyncServiceImpl.class);
	
//	private WithYBContrast getWithYBContrast() {
//		if (this.withYBContrast == null) {
//			withYBContrast = (WithYBContrast) ServiceFactory.getFactory().getService(WithYBContrast.class);
//		}
//		return this.withYBContrast;
//	}
	
//	private IExternalClearingAndSettlementManager getExternalClearingAndSettlementManager() {
//		if (this.externalClearingAndSettlementManager == null) {
//			externalClearingAndSettlementManager = (IExternalClearingAndSettlementManager) ServiceFactory.getFactory().getService(IExternalClearingAndSettlementManager.class);
//		}
//		return this.externalClearingAndSettlementManager;
//	}
	
	@SuppressWarnings("all")
	public List<PaymentTrade> transferBatchSynchronizingByPTradeNos(String param) throws UPPException {
		try {
			String[] paramList = param.split(",");


			List<String> list =Arrays.asList(paramList);

//		return this.getWithYBContrast().transferSynchronizingByPTradeNos(list);
			Map<String, List> map = new HashMap<String, List>();
			map.put("list", list);
			
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_TRANSFER_SYNCHRONIZEING_BY_PTRADE_NOS"));
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				return (List<PaymentTrade>)JSONObject.toBean(dataObj, List.class);
			}
			
		} catch (Exception e) {
			logger.error("transferBatchSynchronizingByPTradeNos_error", e);
			throw new UPPException("transferBatchSynchronizingByPTradeNos_error");
		}
		return null;
	}

	
	public void transferBatchSynchronizingByTime(String startTime, String endTime)
			throws UPPException {
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		long startTimeLong = 0;
		long endTimeLong = 0;
		try {
			startTimeLong = Long.valueOf(startTimeLong);
			endTimeLong =  Long.valueOf(endTime);
		} catch (Exception e) {
			logger.error("日期格式转换错误！", e);
		}
		if(startTimeLong!=0&&endTimeLong!=0){
//			getWithYBContrast().transferBatchSynchronizingByTime(startTimeLong,endTimeLong);
			try {
				Map<String, Long> map = new HashMap<String, Long>();
				map.put("startTimeLong", startTimeLong);
				map.put("endTimeLong", endTimeLong);
				super.sendRequest(JSONObject.fromObject(map)
									.toString(), DefaultConfig
									.getValue("UPP_TRANSFER_BATCH_SYNCHRONIZING_BY_TIME"));
			} catch (Exception e) {
				logger.error("transferBatchSynchronizingByTime_error", e);
				throw new UPPException("transferBatchSynchronizingByTime_error");
			}
						
		}else{
			new UPPException("参数错误！");
		}
		
	}

	
	public void sendLastTradeSign(String sendLastTradeSign) throws UPPException {
		
//		getWithYBContrast().sendLastTradeSign(sendLastTradeSign);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sendLastTradeSign", sendLastTradeSign);
			super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_SEND_LAST_TRADE_SIGN"));
		} catch (Exception e) {
			logger.error("sendLastTradeSign_error", e);
			throw new UPPException("sendLastTradeSign_error", e);
		}
		
	}

	
	public void accountReconciliationByTime(String startTime, String endTime, String numberSign) throws UPPException {
		long startTimeLong = 0;
		long endTimeLong = 0;
		try {
			startTimeLong = Long.valueOf(startTime);
			endTimeLong =  Long.valueOf(endTime);
		} catch (Exception e) {
			logger.error("日期格式转换错误！", e);
		}
		if(startTimeLong!=0&&endTimeLong!=0){
//			getExternalClearingAndSettlementManager().accountReconciliationByTime(startTimeLong, endTimeLong, numberSign);
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("startTimeLong", startTimeLong);
				map.put("endTimeLong", endTimeLong);
				map.put("numberSign", numberSign);
				super.sendRequest(JSONObject.fromObject(map)
									.toString(), DefaultConfig
									.getValue("UPP_ACCOUNT_RECONCILIATION_BY_TIME"));
			} catch (Exception e) {
				logger.error("accountReconciliationByTime_error", e);
				throw new UPPException("accountReconciliationByTime_error", e);
			}
		}else{
			new UPPException("参数错误！");
		}
	}
	public boolean unlockThirdPartPayAccount(String insideAccountNo)
			throws UPPException {
//		getExternalClearingAndSettlementManager().unlockThirdPartPayAccount(insideAccountNo);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("insideAccountNo", insideAccountNo);
			super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_UNLOCK_THIRDPARTY_ACCOUNT"));
			return true;
		} catch (Exception e) {
			logger.error("unlockThirdPartPayAccount_error", e);
			throw new UPPException("unlockThirdPartPayAccount_error", e);
		}
	}
	@Override
	public QueryAccountInfoResponseYB queryThirdPartPayAccount(String insideAccountNo)
			throws UPPException {
//		return	getExternalClearingAndSettlementManager().queryThirdPartPayAccount(insideAccountNo);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("insideAccountNo", insideAccountNo);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_QUERY_THIRDPARTY_ACCOUNT"));
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				return (QueryAccountInfoResponseYB)JSONObject.toBean(dataObj, QueryAccountInfoResponseYB.class);
			}
			
		} catch (Exception e) {
			logger.error("queryThirdPartPayAccount_error", e);
			throw new UPPException("queryThirdPartPayAccount_error", e);
		}
		return null;
	}
	
	@Override
	public List<YBTradeRecordContrastDto> queryAccountHistory(String accountNo,Long startTime,Long endTime)
			throws UPPException {
//		return	getExternalClearingAndSettlementManager().queryThirdPartPayAccountHistory(accountNo, startTime, endTime);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accountNo", accountNo);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_QUERY_THIRDPARTY_ACCOUNT_HISTORY"));
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				return (List<YBTradeRecordContrastDto>)JSONObject.toBean(dataObj, List.class);
			}
			
		} catch (Exception e) {
			logger.error("queryThirdPartPayAccountHistory_error", e);
			throw new UPPException("queryThirdPartPayAccountHistory_error", e);
		}
		return null;
	}
	
}
