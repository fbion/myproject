package com.ctfo.upp.contrast.intf.internal.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.contrast.intf.internal.WithYBInternalContrast;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TradeSyncBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TradeSyncBeanResponseYB;
import com.ctfo.gateway.intf.TradeServiceFacade;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.dto.beans.PaymentTradeStatusFail;
import com.ctfo.payment.dto.beans.YBSycHaveSuccessTrade;
import com.ctfo.payment.intf.internal.IPaymentBaseManager;
import com.ctfo.upp.accountservice.utils.RequestBeanUtil;
import com.ctfo.upp.accountservice.utils.TradeNumberHelper;
import com.ctfo.upp.dict.CheckAccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.soa.ServiceFactory;


//@Service("withYBContrastInternalManager")
public class WithYBContrastInternalManagerImpl implements WithYBInternalContrast{
	private static Log logger = LogFactory.getLog(WithYBContrastInternalManagerImpl.class);
	
	@Autowired
	@Qualifier("paymentBaseManagerService")  
	private IPaymentBaseManager internalPaymentTradeBaseManager;
	
	@Autowired
	@Qualifier("mongoService") 
	private IMongoService mongoService;
	
	@Override
	public boolean sendLastTradeSign() throws UPPException{
		return this.sendDateLastTradeSign(null);
	}	
	public boolean sendDateLastTradeSign(String dateString)throws UPPException{
		TradeSyncBeanRequestYB tradeSyncBeanRequestYB = new TradeSyncBeanRequestYB();
		tradeSyncBeanRequestYB.setList(new ArrayList<Object>());
		tradeSyncBeanRequestYB.setLastOrNot(true);
		if(dateString!=null){
			tradeSyncBeanRequestYB.setDateString(dateString);
		}
		TradeServiceFacade	accountServiceFacade = (TradeServiceFacade) ServiceFactory.getFactory().getService(TradeServiceFacade.class);
		
		TradeSyncBeanResponseYB responseBean =		(TradeSyncBeanResponseYB) accountServiceFacade.tradeSync(tradeSyncBeanRequestYB);
		
		return responseBean.isFlag();
	}
	/**
	 * 同步集合内的交易
	 * @param tradeList  交易信息集合
	 * @return 未成功的交易集合
	 * @throws UPPException
	 */
	@Override
	public List<PaymentTrade> transferBatchSynchronizingByPage(Collection<PaymentTrade> tradeList,Integer batchStartInsideNo) throws UPPException{
		Long batchCode=TradeNumberHelper.getBatchInterNo();   //生成批次号
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String,PaymentTrade> resultMap=new HashMap<String,PaymentTrade>();
		
		List<PaymentTrade> resultList=new ArrayList<PaymentTrade>();
		
		TradeSyncBeanRequestYB tradeSyncBeanRequestYB = new TradeSyncBeanRequestYB();
		tradeSyncBeanRequestYB.setList(new ArrayList<Object>());
		Long batchInsideNo=batchCode+batchStartInsideNo;
		int i = 0;
		StringBuilder strb= new StringBuilder();
		for(PaymentTrade paymentTrade:tradeList){
			if(i == 0 ){//只取第一笔的日期
				tradeSyncBeanRequestYB.setDateString(sf.format(paymentTrade.getPayConfirmDate()));
			}
			
			resultMap.put(paymentTrade.getId(),paymentTrade);
			paymentTrade.setBatchInternalNo(batchInsideNo);
			tradeSyncBeanRequestYB.getList().add(
						RequestBeanUtil.createRequestBean(paymentTrade,batchInsideNo)
					);
			batchInsideNo=batchInsideNo+1;
			i=1;
			strb.append(paymentTrade.getId()).append(",");
			
		}
		logger.info("计数<<<<<M>>>>>>处理的交易Id:"+strb.toString());
		//TODO 调用网关接口
		
		TradeServiceFacade	accountServiceFacade = (TradeServiceFacade) ServiceFactory.getFactory().getService(TradeServiceFacade.class);
		
		TradeSyncBeanResponseYB responseBean =		(TradeSyncBeanResponseYB) accountServiceFacade.tradeSync(tradeSyncBeanRequestYB);
		
		String lastSuccessRecordId=responseBean.getLastSuccessRecordId();
		//TODO 调用网关接口结束
		
		
		for(PaymentTrade paymentTrade:tradeList){
			if(Long.valueOf(lastSuccessRecordId)>=paymentTrade.getBatchInternalNo()){
				this.dealWithSycSuccess(paymentTrade, resultMap, sf);
			}else if(ReturnCodeDict.YB_RETURN_CODE_ORDER_REDUNDANCES.equals(responseBean.getYbReturnCode())){ //4018说明该交易已经成功同步					
				if((Long.valueOf(lastSuccessRecordId)+1)==paymentTrade.getBatchInternalNo()
						||tradeList.size()==1){
					this.dealWithSycSuccess(paymentTrade, resultMap, sf);
				}
			}else{				
				logger.error("交易同步不成功,交易Id:"+paymentTrade.getId());
			}
		}
		for(String key:resultMap.keySet()){
			resultList.add(resultMap.get(key));
		}
		resultMap=null;
		return resultList;
	}
	public List<PaymentTrade> querySuccessUnsycTrades(Long startTime,Long endTime) throws UPPException{
		PaymentTradeExampleExtended ptee = new PaymentTradeExampleExtended();
		ptee.createCriteria().andPayConfirmDateGreaterThanOrEqualTo(startTime)
			.andPayConfirmDateLessThanOrEqualTo(endTime)
			.andIsClearingEqualTo(CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_STORE_CHECK)
			.andTradeStatusEqualTo(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS)
			.andServiceProviderCodeEqualTo(PayDict.PLATFORM_CODE_YEE_PAY);
		
		List<PaymentTrade> tradePageResult=internalPaymentTradeBaseManager.queryPaymentTrans(ptee);
		
		return tradePageResult;
	}
	private void saveSuccessTrade(String tradeId,Long payConfirmDate,String accountDate,Long batchInsideNo){	
		try{
			YBSycHaveSuccessTrade successTrade = new YBSycHaveSuccessTrade();
			successTrade.setPayTradeId(tradeId);
			successTrade.setOperTime(System.currentTimeMillis());
			successTrade.setId(java.util.UUID.randomUUID().toString());
			successTrade.setPayConfirmDate(payConfirmDate);
			successTrade.setAccountDate(accountDate);
			successTrade.setBatchInsideNo(batchInsideNo);
			mongoService.save(successTrade);
		}catch(Exception me){
			logger.error("交易同步成功，但交易状态更新失败，且未能写入失败记录!      "+me.getMessage(),me);
		}
	}
	private void dealWithSycSuccess(PaymentTrade paymentTrade,HashMap<String,PaymentTrade> resultMap,SimpleDateFormat sf){
		try{
			internalPaymentTradeBaseManager.updatePaymentTransClearingStatus(paymentTrade.getId(), CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_THIRDPART_SYC,paymentTrade.getBatchInternalNo());
		}catch(Exception e){  //出异常将状态记录记录下来
			this.saveSuccessTrade(paymentTrade.getId(),paymentTrade.getPayConfirmDate()
					,sf.format(paymentTrade.getPayConfirmDate()),paymentTrade.getBatchInternalNo());
		}
		resultMap.remove(paymentTrade.getId());
	}
	
}
