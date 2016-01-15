package com.sinoiov.upp.manager.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.file.boss.IMongoService;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryResponseYB;
import com.ctfo.gateway.intf.ThirdPartPayQueryFacade;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.dto.beans.RechargeSuccessMess;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.soa.ServiceFactory;

@Service("paymentResultQueryAndHandleManager")
public class PaymentResultQueryAndHandleManagerImpl implements
	IPaymentResultQueryAndHandleManager{

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("mongoService") 
	private IMongoService mongoService;
	
	@Autowired
	@Qualifier("paymentBaseManager")
	private IPaymentBaseManager paymentBaseManager;
	
	@Autowired
	@Qualifier("paymentTransManager")
	private  IPaymentTransManager  paymentTransManager;
	 
	private static final Log logger=LogFactory.getLog(PaymentResultQueryAndHandleManagerImpl.class);

	/**
	 * 1.查询易宝充值的状态
	 * 2.
	 */
	@Override
	public RechargeSuccessMess queryRechargeResult(PaymentTrade paymentTrade)throws UPPException{
		try{
			
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
		
		List<PaymentTrade> paymentTradeList = paymentBaseManager.queryPaymentTrans(paymentTradeExampleExtended);
		Integer count = 0;
//		for(PaymentTrade pay:paymentTradeList){
//			RechargeSuccessMess rechargeSuccessMess = this.queryRechargeResult(pay);
//			if(rechargeSuccessMess!=null){
//				
//				try {
//					//this.dealWithRechargeResult();
//				} catch (JsonGenerationException e) {
//					logger.error(e.getMessage(),e);
//				} catch (JsonMappingException e) {
//					logger.error(e.getMessage(),e);
//				} catch (IOException e) {
//					logger.error(e.getMessage(),e);
//				}
//				count=count+1;
//			}
//			
//		}
		return count;
	}
	
//	public Boolean dealWithRechargeResult() throws JsonGenerationException, JsonMappingException, IOException{
//		YyzcKafkaBasicProducer client = new YyzcKafkaBasicProducer();
//		
//		try {
//			
//			client.send(DefaultConfig.getValue("PAY_ACCOUNT_BATCH_RECHARGE"), null);
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return true;
//	}


}
