package com.ctfo.upp.payment.intf.thirdpart.query.internal.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.file.boss.IMongoService;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountHistoryRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountHistoryResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryResponseYB;
import com.ctfo.gateway.intf.ThirdPartPayQueryFacade;
import com.ctfo.gateway.intf.TradeServiceFacade;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.dto.beans.RechargeSuccessMess;
import com.ctfo.payment.dto.beans.YbPayTradeHistoryRecord;
import com.ctfo.payment.intf.internal.IPaymentBaseManager;
import com.ctfo.payment.intf.internal.IRechargeManager;
import com.ctfo.upp.accountservice.utils.CheckAccountHelper;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.payment.intf.thirdpart.query.internal.ThirdPartyPaymentQueryManager;
import com.ctfo.upp.soa.ServiceFactory;

//@Service("thirdPartyPaymentQueryManager")
public class ThirdPartyPaymentQueryManagerImpl implements
		ThirdPartyPaymentQueryManager {
	@Autowired
	@Qualifier("paymentBaseManagerService")
	private IPaymentBaseManager paymentBaseManagerService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("mongoService") 
	private IMongoService mongoService;
	
	@Autowired
	@Qualifier("iRechargeManager")
	private  IRechargeManager iRechargeManager;
	
	@Override
	public QueryAccountInfoResponseYB queryThirdPartyAccountInfo(String code, String accountNo)
			throws UPPException {	
		if(PayDict.PLATFORM_CODE_YEE_PAY.equals(code)){
			try{
				//TODO 调用网关接口		
				ThirdPartPayQueryFacade	accountServiceFacade = (ThirdPartPayQueryFacade) ServiceFactory.getFactory().getService(ThirdPartPayQueryFacade.class);
				QueryAccountInfoRequestYB requestBean= new QueryAccountInfoRequestYB();
				requestBean.setAccountNo(accountNo);
				QueryAccountInfoResponseYB response = (QueryAccountInfoResponseYB)accountServiceFacade.queryAccountInfo(requestBean);
				return response;
			}catch(Exception e){
				throw new UPPException(e.getLocalizedMessage(),e);
			}
		}else{
			throw new UPPException("目前不支持该支付平台");
		}
		
	}
	/**
	 * 1.查询易宝充值的状态
	 * 2.
	 */
	@Override
	public RechargeSuccessMess queryRechargeResult(PaymentTrade paymentTrade)throws UPPException{
		try{
			if( paymentTrade==null){ 
				
				paymentTrade = paymentBaseManagerService.queryPaymentTransTradeExternalNo(paymentTrade.getTradeExternalNo());
			}
			
			//TODO 调用网关接口		
			ThirdPartPayQueryFacade	accountServiceFacade = (ThirdPartPayQueryFacade) ServiceFactory.getFactory().getService(ThirdPartPayQueryFacade.class);
			RechargeResultQueryRequestYB rechargeResultQueryRequestYB= new RechargeResultQueryRequestYB();
			rechargeResultQueryRequestYB.setReq03_requestId(paymentTrade.getTradeExternalNo());
//			rechargeResultQueryRequestYB.setReq01_merchantIdentityNumber(paymentTrade.getCollectMoneyAccountNo());
			
			RechargeResultQueryResponseYB res=  (RechargeResultQueryResponseYB) accountServiceFacade.queryAccountInfo(rechargeResultQueryRequestYB);
//			logger.info();
			if("SUCCESS".equals(res.getResp36_orderStatus())){
				res.getResp29_externalId();
				
				
				RechargeSuccessMess rsm = new RechargeSuccessMess();
				rsm.setId(java.util.UUID.randomUUID().toString());
				rsm.setFlag("1");
				rsm.setExternalId(res.getResp29_externalId());
				rsm.setPayConfirmDate(Long.valueOf(res.getResp42_completeTime()));
				rsm.setOrderAmount(new BigDecimal(Double.valueOf(res.getResp31_trxInAmount())*100).longValue());
				rsm.setTradeNo(paymentTrade.getTradeExternalNo());
				return rsm;
			}else{
				return null;
			}
			
		}catch(Exception e){
			throw new UPPException(e.getLocalizedMessage(),e);
		}
	}
	@Override
	public Integer handleRechargeResult(Long startTime,Long endTime)throws UPPException{
		List<String> statusList = new ArrayList<String>();
		statusList.add(PayDict.PAY_TRADE_STATUS_INIT);
		PaymentTradeExampleExtended paymentTradeExampleExtended = new PaymentTradeExampleExtended();
		paymentTradeExampleExtended.createCriteria().andTradeStatusIn(statusList)
		.andCreateTimeGreaterThanOrEqualTo(startTime).andCreateTimeLessThanOrEqualTo(endTime)
		.andServiceProviderCodeEqualTo(PayDict.PLATFORM_CODE_YEE_PAY);
		
		List<PaymentTrade> paymentTradeList = paymentBaseManagerService.queryPaymentTrans(paymentTradeExampleExtended);
		Integer count = 0;
		for(PaymentTrade pay:paymentTradeList){
			RechargeSuccessMess rechargeSuccessMess = this.queryRechargeResult(pay);
			if(rechargeSuccessMess!=null){
				
				iRechargeManager.rechargeCallback(CheckAccountHelper.createRechargeCallback(rechargeSuccessMess));
				count=count+1;
			}
			
		}
		return count;
	}
	@Override
	public List<YbPayTradeHistoryRecord> queryThirdPartPayAccountHistory(String accountNo,String startPoint,String endPoint)throws UPPException{
		try{
			QueryAccountHistoryRequestYB queryAccountHistoryRequestYB= new QueryAccountHistoryRequestYB();
			queryAccountHistoryRequestYB.setReq02_userIdentityNumber(accountNo);
			queryAccountHistoryRequestYB.setReq51_startPoint(startPoint);
			queryAccountHistoryRequestYB.setReq52_endPoint(endPoint);
			
			//TODO 调用网关接口		
			ThirdPartPayQueryFacade	accountServiceFacade = (ThirdPartPayQueryFacade) ServiceFactory.getFactory().getService(ThirdPartPayQueryFacade.class);
	
			
			QueryAccountHistoryResponseYB res=  (QueryAccountHistoryResponseYB) accountServiceFacade.queryAccountInfo(queryAccountHistoryRequestYB);
			
			return this.parseToYbPayTradeHistoryRecord(res.getRecordList());
			 
		}catch(Exception e){
			throw new UPPException(e.getLocalizedMessage(),e);
		}
	}
	private List<YbPayTradeHistoryRecord> parseToYbPayTradeHistoryRecord(List<String> list) throws ParseException{
		
		List<YbPayTradeHistoryRecord> recordList =  new ArrayList<YbPayTradeHistoryRecord>();
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=1;i<list.size();i++){
			String[] strs=list.get(i).split(",");
			YbPayTradeHistoryRecord record =new YbPayTradeHistoryRecord();
			record.setAccountType(strs[0]);
			record.setTradeDesc(strs[1]);
			record.setTradeTime(sf.parse(strs[2]).getTime());
			record.setAmount(multiplyBy(strs[3]));
			record.setPayPoundScale(multiplyBy(strs[4]));
			record.setRealityTradeAmount(multiplyBy(strs[5]));
			record.setTradeFinishBalance(multiplyBy(strs[6]));
			record.setTimeString(strs[2]);
			recordList.add(record);
		}
		 Collections.sort(recordList);
		 
		 return recordList;
	}
	private Long multiplyBy(String amount){
		Double result = (Double.valueOf(amount)*100.0);
		return result.longValue();
	}

}
