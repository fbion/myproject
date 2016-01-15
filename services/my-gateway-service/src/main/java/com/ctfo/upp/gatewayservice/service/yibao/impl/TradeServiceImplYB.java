package com.ctfo.upp.gatewayservice.service.yibao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.gateway.bean.yibao.business.accountaccess.AccountBalanceInquiry;
import com.ctfo.gateway.bean.yibao.business.accountaccess.AccountBalanceInquiryResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.BankCardRechargeBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.BankCardRechargeBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeInfoBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeInfoBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeSyncBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeSyncBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TradeSyncBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TradeSyncBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TransactionBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TransactionBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawProcessBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawProcessBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.entrustsettle.WithDrawQuickBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.entrustsettle.WithDrawQuickBeanResponseYB;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.BankCardRechargeRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.BankCardRechargeResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.QueryRechargeInfoRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.QueryRechargeInfoResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.QueryWithDrawProcessRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.QueryWithDrawProcessResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.SyncTradeRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.SyncTradeResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.TradeDataSyncDetail;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle.AccountBalanceQueryRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle.AccountBalanceQueryResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle.BatchDetailQueryRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle.BatchDetailQueryResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle.TransferSingleRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle.TransferSingleResponse;
import com.ctfo.upp.gatewayservice.service.yibao.intf.DistributeServiceYB;
import com.ctfo.upp.gatewayservice.service.yibao.intf.TradeServiceYB;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.gatewayservice.util.base.RandomUtil;
import com.ctfo.upp.gatewayservice.util.yibao.Constants;
import com.ctfo.upp.gatewayservice.util.yibao.PareseObjectParameter;

@Service("tradeServiceYB")
public class TradeServiceImplYB extends BaseServiceImpl implements TradeServiceYB {
	
	protected static final Log log = LogFactory.getLog(TradeServiceImplYB.class);
	
	@Autowired(required=false)
	private DistributeServiceYB distributeServiceYB;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public BankCardRechargeBeanResponseYB bankCardRecharge(
			BankCardRechargeBeanRequestYB bankCardRechargeBeanRequest)
			throws Exception {
		// TODO Auto-generated method stub
		BankCardRechargeRequest request = new BankCardRechargeRequest();
		request.setReq02_userIdentityNumber(bankCardRechargeBeanRequest.getAccountId());
		request.setReq03_requestId(bankCardRechargeBeanRequest.getRequestId());
		request.setReq04_amount(bankCardRechargeBeanRequest.getAmount());
		request.setReq43_platFee(bankCardRechargeBeanRequest.getPlatFee());
		request.setReq05_channel(bankCardRechargeBeanRequest.getChannel());
		request.setReq06_cardType(bankCardRechargeBeanRequest.getCardType());
		request.setReq30_frpChannel(bankCardRechargeBeanRequest.getFrpChannel());
		request.setReq31_clientType(bankCardRechargeBeanRequest.getClientType());
		request.setReq32_platTrxDate(bankCardRechargeBeanRequest.getPlatTrxDate());
		
		//回调地址
		String callBackUrl = ConfigUtil.getValue("YB_ACCOUNT_ACCESS_CALL_BACK_URL");
		if(StringUtils.isBlank(callBackUrl)){
			log.error(">>>>>>>>>> 没有配置卡充值回调地址，无法接收到具体的充值结果  <<<<<<<<<<");
			return null;
		}
		request.setReq36_callBackUrl(callBackUrl);
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		BankCardRechargeResponse response = (BankCardRechargeResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				return null;
			}
		}else{
 			return null;
		}
		BankCardRechargeBeanResponseYB bankCardRechargeBeanResponseYB = new BankCardRechargeBeanResponseYB();
		bankCardRechargeBeanResponseYB.setUrl(response.getResp43_redirectPayUrl());
		return bankCardRechargeBeanResponseYB;
	}

	@Override
	public WithDrawBeanResponseYB withDraw(
			WithDrawBeanRequestYB withDrawBeanRequest) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		Date date = new Date();//current time
		String recordId = RandomUtil.getRandomNumberWithDate(3);
		SyncTradeRequest request = new SyncTradeRequest();
		request.setReq37_syncDate(formatter.format(date));
		request.setReq38_syncCount("1");
		request.setReq41_isLastBatch("0");
		request.setReq39_startRecordId(recordId);
		request.setReq40_endRecordId(recordId);
		TradeDataSyncDetail detail = new TradeDataSyncDetail();
		detail.setRecordId(recordId);
		request.setReq03_requestId(withDrawBeanRequest.getRequestId());
		detail.setOrderTpye("WITHDRAW");
		detail.setRequestId(withDrawBeanRequest.getRequestId());
		detail.setSourceUserNumber(withDrawBeanRequest.getAccountId());
		detail.setAttachment(withDrawBeanRequest.getBankCardNo());
		detail.setAmount(withDrawBeanRequest.getAmount());
		detail.setTrxDate(withDrawBeanRequest.getTime());
		String syncData = PareseObjectParameter.getObjectParameterContentStringByChar(detail, "&");
		request.setReq42_dataSyncDetail(syncData);
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		SyncTradeResponse response = (SyncTradeResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!StringUtils.isBlank(response.getResp18_exceptionDesc())){
				log.error(">>>>>>>>>> 返回异常：" + response.getResp18_exceptionDesc() + " <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		WithDrawBeanResponseYB withDrawBeanResponseYB = new WithDrawBeanResponseYB();
		withDrawBeanResponseYB.setFlag(flag);
		return withDrawBeanResponseYB;
	}

	@Override
	public TransactionBeanResponseYB transacte(
			TransactionBeanRequestYB transactionBeanRequest) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		Date date = new Date();//current time
		String recordId = RandomUtil.getRandomNumberWithDate(3);
		SyncTradeRequest request = new SyncTradeRequest();
		request.setReq37_syncDate(formatter.format(date));
		request.setReq38_syncCount("1");
		request.setReq41_isLastBatch("0");
		request.setReq39_startRecordId(recordId);
		request.setReq40_endRecordId(recordId);
		TradeDataSyncDetail detail = new TradeDataSyncDetail();
		detail.setRecordId(recordId);
		request.setReq03_requestId(transactionBeanRequest.getRequestId());
		detail.setOrderTpye("ACCOUNT_TRANSACTION");
		detail.setRequestId(transactionBeanRequest.getRequestId());
		detail.setSourceUserNumber(transactionBeanRequest.getOrigAccountId());
		detail.setAttachment(transactionBeanRequest.getTargetAccountId());
		detail.setAmount(transactionBeanRequest.getAmount());
		detail.setTrxDate(transactionBeanRequest.getTime());
		String syncData = PareseObjectParameter.getObjectParameterContentStringByChar(detail, "&");
		request.setReq42_dataSyncDetail(syncData);
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		SyncTradeResponse response = (SyncTradeResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!StringUtils.isBlank(response.getResp18_exceptionDesc())){
				log.error(">>>>>>>>>> 返回异常：" + response.getResp18_exceptionDesc() + " <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		TransactionBeanResponseYB transactionBeanResponseYB = new TransactionBeanResponseYB();
		transactionBeanResponseYB.setFlag(flag);
		return transactionBeanResponseYB;
	}

	@Override
	public RechargeSyncBeanResponseYB rechargeSync(
			RechargeSyncBeanRequestYB rechargeSyncBeanRequest) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		Date date = new Date();//current time
		String recordId = RandomUtil.getRandomNumberWithDate(3);
		SyncTradeRequest request = new SyncTradeRequest();
		request.setReq37_syncDate(formatter.format(date));
		request.setReq38_syncCount("1");
		request.setReq41_isLastBatch("0");
		request.setReq39_startRecordId(recordId);
		request.setReq40_endRecordId(recordId);
		TradeDataSyncDetail detail = new TradeDataSyncDetail();
		detail.setRecordId(recordId);
		request.setReq03_requestId(rechargeSyncBeanRequest.getRequestId());
		detail.setOrderTpye("RECHARGE");
		detail.setRequestId(rechargeSyncBeanRequest.getRequestId());
		detail.setSourceUserNumber(rechargeSyncBeanRequest.getAccountId());
		detail.setAttachment(rechargeSyncBeanRequest.getExternalId());
		detail.setAmount(rechargeSyncBeanRequest.getAmount());
		detail.setTrxDate(rechargeSyncBeanRequest.getTime());
		String syncData = PareseObjectParameter.getObjectParameterContentStringByChar(detail, "&");
		request.setReq42_dataSyncDetail(syncData);
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		SyncTradeResponse response = (SyncTradeResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!StringUtils.isBlank(response.getResp18_exceptionDesc())){
				log.error(">>>>>>>>>> 返回异常：" + response.getResp18_exceptionDesc() + " <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		RechargeSyncBeanResponseYB rechargeSyncBeanResponseYB = new RechargeSyncBeanResponseYB();
		rechargeSyncBeanResponseYB.setFlag(flag);
		return rechargeSyncBeanResponseYB;
	}

	@Override
	public RechargeInfoBeanResponseYB rechargeInfo(
			RechargeInfoBeanRequestYB rechargeInfoBeanRequest) throws Exception {
		// TODO Auto-generated method stub
		QueryRechargeInfoRequest request = new QueryRechargeInfoRequest();
		request.setReq03_requestId(rechargeInfoBeanRequest.getRequestId());
		request.setReq01_merchantIdentityNumber(rechargeInfoBeanRequest.getAccountId());
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		QueryRechargeInfoResponse response = (QueryRechargeInfoResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回requestId不一致  <<<<<<<<<<");
				return null;
			}
			if(!request.getReq02_userIdentityNumber().equals(response.getResp03_userIdentityNumber())){
				log.error(">>>>>>>>>> 请求与返回的userIdentityNumber不一致  <<<<<<<<<<");
				return null;
			}
		}else{
			return null;
		}
		RechargeInfoBeanResponseYB rechargeInfoBeanResponseYB = new RechargeInfoBeanResponseYB();
		rechargeInfoBeanResponseYB.setAccountId(response.getResp03_userIdentityNumber());
		rechargeInfoBeanResponseYB.setRequestId(response.getResp04_requestId());
		rechargeInfoBeanResponseYB.setTrxInAmount(response.getResp31_trxInAmount());
		rechargeInfoBeanResponseYB.setOrderStatus(response.getResp36_orderStatus());
		rechargeInfoBeanResponseYB.setBalanceAmount(response.getResp30_balanceAmount());
		rechargeInfoBeanResponseYB.setBalanceAct(response.getResp38_balanceAct());
		
		return rechargeInfoBeanResponseYB;
	}

	@Override
	public WithDrawProcessBeanResponseYB withDrawProcess(
			WithDrawProcessBeanRequestYB withDrawProcessBeanRequest)
			throws Exception {
		// TODO Auto-generated method stub
		WithDrawProcessBeanResponseYB withDrawProcessBeanResponseYB = new WithDrawProcessBeanResponseYB();
		if(withDrawProcessBeanRequest.getType().equals("1")){
			QueryWithDrawProcessRequest request = new QueryWithDrawProcessRequest();
			request.setReq03_requestId(withDrawProcessBeanRequest.getRequestId());
			request.setHmac(createHMAC(request, 1));
			List<String> result = distributeServiceYB.distributeRequest(request, 1);
			QueryWithDrawProcessResponse response = (QueryWithDrawProcessResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
			if(super.validateResponse(request, response)){
				if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
					log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
					withDrawProcessBeanResponseYB.setStatus("0");
				}
			}else{
				withDrawProcessBeanResponseYB.setStatus("0");
			}
			if(response.getResp36_orderStatus().equals("INIT") || response.getResp36_orderStatus().equals("YEEPAYINDEAL")){
				withDrawProcessBeanResponseYB.setStatus("1");
			}
			if(response.getResp36_orderStatus().equals("SUCCESS")){
				withDrawProcessBeanResponseYB.setStatus("2");
			}
			if(response.getResp36_orderStatus().equals("FAIL")){
				withDrawProcessBeanResponseYB.setStatus("3");
			}
		}else{
			BatchDetailQueryRequest request = new BatchDetailQueryRequest();
			request.setBatch_No(withDrawProcessBeanRequest.getBatchNo());
			request.setOrder_Id(withDrawProcessBeanRequest.getRequestId());
			request.setHmac(createHMAC(request, 2));
			List<String> result = distributeServiceYB.distributeRequest(request, 2);
			BatchDetailQueryResponse response = new BatchDetailQueryResponse();
			PareseObjectParameter.convertXMLToObject(result.get(0).toString(), response);
			if(!validateHMAC(response, 2)){
				withDrawProcessBeanResponseYB.setStatus("0");
			}
			if(!request.getCmd().equals(response.getCmd())){
				log.error(">>>>>>>>>> 请求与返回的cmd不一致  <<<<<<<<<<");
				withDrawProcessBeanResponseYB.setStatus("0");
			}
			if(!request.getBatch_No().equals(response.getBatch_No())){
				log.error(">>>>>>>>>> 请求与返回的batchNo不一致  <<<<<<<<<<");
				withDrawProcessBeanResponseYB.setStatus("0");
			}
			if(!response.getTotal_Num().equals("1") || response.getList().getItems().size() != 1){
				log.error(">>>>>>>>>> 返回的条数不正确  <<<<<<<<<<");
				withDrawProcessBeanResponseYB.setStatus("0");
			}
			if(!response.getList().getMer_Id().equals(ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID"))){
				log.error(">>>>>>>>>> 返回的商户号不正确  <<<<<<<<<<");
				withDrawProcessBeanResponseYB.setStatus("0");
			}
			if(!response.getList().getItems().get(0).getItem().getOrder_Id().equals(request.getOrder_Id())){
				log.error(">>>>>>>>>> 请求与返回的orderId不一致  <<<<<<<<<<");
				withDrawProcessBeanResponseYB.setStatus("0");
			}
			if(response.getList().getItems().get(0).getItem().getBank_Status().equals("I") || response.getList().getItems().get(0).getItem().getBank_Status().equals("W") || response.getList().getItems().get(0).getItem().getBank_Status().equals("U")){
				withDrawProcessBeanResponseYB.setStatus("1");
			}
			if(response.getList().getItems().get(0).getItem().getBank_Status().equals("S")){
				withDrawProcessBeanResponseYB.setStatus("2");
			}
			if(response.getList().getItems().get(0).getItem().getBank_Status().equals("F")){
				withDrawProcessBeanResponseYB.setStatus("3");
			}
		}
		return withDrawProcessBeanResponseYB;
	}
	/**
	 * 委托结算提现
	 */
	@Override
	public WithDrawQuickBeanResponseYB withdrawQuick(
			WithDrawQuickBeanRequestYB withDrawQuickBeanRequestYB)
			throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		TransferSingleRequest request = new TransferSingleRequest();
		request.setBatch_No(withDrawQuickBeanRequestYB.getBatchNo());
		request.setOrder_Id(withDrawQuickBeanRequestYB.getOrderId());
		request.setBank_Code(withDrawQuickBeanRequestYB.getBankCode());
		request.setAmount(withDrawQuickBeanRequestYB.getAmount());
		request.setAccount_Name(withDrawQuickBeanRequestYB.getAccountName());
		request.setAccount_Number(withDrawQuickBeanRequestYB.getAccountNumber());
		request.setProduct(withDrawQuickBeanRequestYB.getProvince());
		request.setCity(withDrawQuickBeanRequestYB.getCity());
		request.setHmac(createHMAC(request, 2));
		List<String> result = distributeServiceYB.distributeRequest(request, 2);
		TransferSingleResponse response = new TransferSingleResponse();
		PareseObjectParameter.convertXMLToObject(result.get(0).toString(), response);
		if(!validateHMAC(response, 2)){
			flag = false;
		}
		if(!request.getCmd().equals(response.getCmd())){
			log.error(">>>>>>>>>> 请求与返回的cmd不一致  <<<<<<<<<<");
			flag = false;
		}
		if(!request.getOrder_Id().equals(response.getOrder_Id())){
			log.error(">>>>>>>>>> 请求与返回的order_Id不一致  <<<<<<<<<<");
			flag = false;
		}
		if(StringUtils.isBlank(response.getRet_Code()) || !response.getRet_Code().equals("1")){
			log.error(">>>>>>>>>> 返回异常：" + (StringUtils.isBlank(response.getRet_Code()) ? "返回码为空" : Constants.EntrustSettleReturnCode.get(response.getRet_Code())) + " <<<<<<<<<<");
			flag = false;
		}
		if(StringUtils.isBlank(response.getBank_Status()) || response.getBank_Status().equals("F")){
			log.error(">>>>>>>>>> 返回异常：" + (StringUtils.isBlank(response.getBank_Status()) ? "银行状态码为空" : Constants.BankManageResultCode.get(response.getBank_Status())) + " <<<<<<<<<<");
			flag = false;
		}
		WithDrawQuickBeanResponseYB withDrawQuickBeanResponseYB = new WithDrawQuickBeanResponseYB();
		withDrawQuickBeanResponseYB.setFlag(flag);
		return withDrawQuickBeanResponseYB;
	}

	@Override
	public TradeSyncBeanResponseYB tradeSync(TradeSyncBeanRequestYB tradeSyncBeanRequestYB) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		
		SyncTradeRequest request = new SyncTradeRequest();
		if(tradeSyncBeanRequestYB.getDateString() == null){
			Date date = new Date();
			request.setReq37_syncDate(formatter.format(date));
		}else{
			request.setReq37_syncDate(tradeSyncBeanRequestYB.getDateString());
		}
		request.setReq38_syncCount(String.valueOf(tradeSyncBeanRequestYB.getList().size()));
		request.setReq41_isLastBatch((tradeSyncBeanRequestYB.isLastOrNot() == true ? "1" : "0"));
		request.setReq03_requestId(RandomUtil.getRandomNumberWithDate(3));
		String startRecordId = "";
		String endRecordId = "";
		String syncData = "";
		for(int i = 0 ; i < tradeSyncBeanRequestYB.getList().size() ; i++){
			Object object = tradeSyncBeanRequestYB.getList().get(i);
			TradeDataSyncDetail detail = new TradeDataSyncDetail();
			if(object instanceof WithDrawBeanRequestYB){
				WithDrawBeanRequestYB withDrawBeanRequest = (WithDrawBeanRequestYB) object;
				detail.setRecordId(withDrawBeanRequest.getRecordId());
				if(i == 0){
					startRecordId = withDrawBeanRequest.getRecordId();
				}
				if(i == (tradeSyncBeanRequestYB.getList().size() - 1)){
					endRecordId = withDrawBeanRequest.getRecordId();
				}
				detail.setOrderTpye("WITHDRAW");
				detail.setRequestId(withDrawBeanRequest.getRequestId());
				detail.setSourceUserNumber(withDrawBeanRequest.getAccountId());
				detail.setAttachment(withDrawBeanRequest.getBankCardNo());
				detail.setAmount(withDrawBeanRequest.getAmount());
				detail.setTrxDate(withDrawBeanRequest.getTime());
			}
			if(object instanceof TransactionBeanRequestYB){
				TransactionBeanRequestYB transactionBeanRequest = (TransactionBeanRequestYB) object;
				detail.setRecordId(transactionBeanRequest.getRecordId());
				if(i == 0){
					startRecordId = transactionBeanRequest.getRecordId();
				}
				if(i == (tradeSyncBeanRequestYB.getList().size() - 1)){
					endRecordId = transactionBeanRequest.getRecordId();
				}
				detail.setOrderTpye("ACCOUNT_TRANSACTION");
				detail.setRequestId(transactionBeanRequest.getRequestId());
				detail.setSourceUserNumber(transactionBeanRequest.getOrigAccountId());
				detail.setAttachment(transactionBeanRequest.getTargetAccountId());
				detail.setAmount(transactionBeanRequest.getAmount());
				detail.setTrxDate(transactionBeanRequest.getTime());
			}
			if(object instanceof RechargeSyncBeanRequestYB){
				RechargeSyncBeanRequestYB rechargeSyncBeanRequest = (RechargeSyncBeanRequestYB) object;
				detail.setRecordId(rechargeSyncBeanRequest.getRecordId());
				if(i == 0){
					startRecordId = rechargeSyncBeanRequest.getRecordId();
				}
				if(i == (tradeSyncBeanRequestYB.getList().size() - 1)){
					endRecordId = rechargeSyncBeanRequest.getRecordId();
				}
				detail.setOrderTpye("RECHARGE");
				detail.setRequestId(rechargeSyncBeanRequest.getRequestId());
				detail.setSourceUserNumber(rechargeSyncBeanRequest.getAccountId());
				detail.setAttachment(rechargeSyncBeanRequest.getExternalId());
				detail.setAmount(rechargeSyncBeanRequest.getAmount());
				detail.setTrxDate(rechargeSyncBeanRequest.getTime());
			}
			syncData += PareseObjectParameter.getObjectParameterContentStringByChar(detail, "&") + "|";
		}
		
		if(!"".equals(syncData)){
			request.setReq42_dataSyncDetail(syncData.substring(0, (syncData.length() - 1)));
		}else{
//			request.setReq42_dataSyncDetail(syncData);
			startRecordId = "0";
			endRecordId="0";
		}	
		request.setReq39_startRecordId(startRecordId);
		request.setReq40_endRecordId(endRecordId);
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		SyncTradeResponse response = (SyncTradeResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!StringUtils.isBlank(response.getResp18_exceptionDesc())){
				log.error(">>>>>>>>>> 返回异常：" + response.getResp18_exceptionDesc() + " <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		TradeSyncBeanResponseYB tradeSyncBeanResponseYB = new TradeSyncBeanResponseYB();
		tradeSyncBeanResponseYB.setFlag(flag);
		tradeSyncBeanResponseYB.setLastSuccessRecordId(response.getResp16_lastSuccessRecordId());
		tradeSyncBeanResponseYB.setYbReturnCode(response.getResp01_code());
		return tradeSyncBeanResponseYB;
	}
	@Override
	public ResponseBean accountBalanceInquiryHandle(RequestBean requestBean) throws Exception{
		AccountBalanceInquiryResponseYB accountBalanceInquiryResponseYB=new AccountBalanceInquiryResponseYB();
		if(requestBean instanceof AccountBalanceInquiry){
			AccountBalanceInquiry accountBalanceInquiry = (AccountBalanceInquiry) requestBean;
			if("2".equals(accountBalanceInquiry.getQueryType())){
				AccountBalanceQueryRequest accountBalanceQueryRequest=new AccountBalanceQueryRequest();
				
				
				accountBalanceQueryRequest.setVersion("1.0");
				accountBalanceQueryRequest.setHmac(super.createHMAC(accountBalanceQueryRequest, 2));
				accountBalanceQueryRequest.setDate(new SimpleDateFormat("yyyy-MM-dd").format(
						new Date(accountBalanceInquiry.getQueryTime()))
					);
				List<String> result = distributeServiceYB.distributeRequest(accountBalanceQueryRequest, 2);
				AccountBalanceQueryResponse response = (AccountBalanceQueryResponse) PareseObjectParameter.pareseStringToObject(result, accountBalanceQueryRequest.getClass().getName());
				if(!"1".equals(response.getRet_Code())){
					log.error("查询委托结算垫付账户余额失败，错误码："+response.getRet_Code());
					accountBalanceInquiryResponseYB.setFlag(false);
					accountBalanceInquiryResponseYB.setErrorMessage(response.getError_msg());
				}
				accountBalanceInquiryResponseYB.setBalanceAmount(new BigDecimal(response.getBalance_Amount()).longValue());
				accountBalanceInquiryResponseYB.setFlag(true);
				
				return accountBalanceInquiryResponseYB;
			}			
		}
		return null;
	}
}
