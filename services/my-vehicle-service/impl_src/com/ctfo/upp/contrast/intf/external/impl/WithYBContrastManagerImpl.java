package com.ctfo.upp.contrast.intf.external.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.contrast.intf.external.WithYBContrast;
import com.ctfo.account.contrast.intf.internal.WithYBInternalContrast;
import com.ctfo.account.contrast.intf.internal.WithYbContrastFailRecardInternalManager;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.dto.beans.YBSycHaveSuccessTrade;
import com.ctfo.payment.intf.internal.IPaymentBaseManager;
import com.ctfo.upp.dict.CheckAccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.mgbeans.SyncTradeFailRecord;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.TimeHandleUtil;
import com.google.code.morphia.query.Query;

/**
 * 交易同步
 * @author majingyuan
 *
 */
//@Service("withYBContrastManagerService")
public class WithYBContrastManagerImpl extends AbstractContrast implements WithYBContrast{
	private static Log logger = LogFactory.getLog(WithYBContrastManagerImpl.class);
	
	@Autowired
	@Qualifier("paymentBaseManagerService")  
	private IPaymentBaseManager internalPaymentTradeBaseManager;
	
	@Autowired
	@Qualifier("withYBContrastInternalManager")  
	private WithYBInternalContrast withYBContrastInternalManager;
	
	@Autowired
	@Qualifier("withYbContrastFailRecardManager")  
	private WithYbContrastFailRecardInternalManager withYbContrastFailRecardManager;
		
	
	@Override
	public void contrast() throws UPPException {
		// TODO Auto-generated method stub
//		withYbContrastFailRecardManager.dealAccountStatus();	
		withYbContrastFailRecardManager.dealAllUnsycRecord();
		
	}

	@Override
	public String withYBContrastCallback() throws UPPException {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public String transferSynchronizing() throws UPPException {
		SyncTradeFailRecord syncTradeFailRecord=this.transferBatchSynchronizingByTime(TimeHandleUtil.getTimesmorning(), TimeHandleUtil.getTimesnight());
//		if(syncTradeFailRecord==null){
//			withYBContrastInternalManager.sendLastTradeSign();
//		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					dealWithFailSycTradeByTime(TimeHandleUtil.getTimesmorning(), TimeHandleUtil.getTimesnight());
					
					logger.warn("<<<<<M>>>>>交易同步完成！");
				} catch (UPPException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}).start();
		
		return null;
	}

	
	@Override
	public SyncTradeFailRecord transferBatchSynchronizingByTime(Long startTime,Long endTime) throws UPPException{
		int pageSize=5;
		try {
			PaymentTradeExampleExtended ptee = new PaymentTradeExampleExtended();
			
			ptee.createCriteria().andPayConfirmDateGreaterThanOrEqualTo(startTime).andPayConfirmDateLessThanOrEqualTo(endTime)
			.andTradeStatusEqualTo(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS).andServiceProviderCodeEqualTo(PayDict.PLATFORM_CODE_YEE_PAY)
			.andIsClearingNotEqualTo(CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_THIRDPART_SYC);
			ptee.setSelectedField(PaymentTrade.fieldId());
			
			List<PaymentTrade> tradeList= this.getModels(ptee);
			
			List<PaymentTrade> paramList =new ArrayList<PaymentTrade>();
			
			List<PaymentTrade> sycFailTradeRecords =new ArrayList<PaymentTrade>();
			int i=1;
			
			for(PaymentTrade trade:tradeList){
				paramList.add(trade);
				if(i%pageSize==0){
					int startInsideNo =( i/pageSize-1)*pageSize+1;
					
					sycFailTradeRecords.addAll(
							withYBContrastInternalManager.transferBatchSynchronizingByPage(paramList,startInsideNo)
							);
					paramList.clear();
				}			
				i=i+1;
			}
			if(paramList.size()>0){
				sycFailTradeRecords.addAll(
						withYBContrastInternalManager.transferBatchSynchronizingByPage(paramList,tradeList.size()/pageSize+1)
				);		
			}	
			if(sycFailTradeRecords.size()>0){
				SyncTradeFailRecord record =  new SyncTradeFailRecord();
				record.setSycFailTradeRecords(sycFailTradeRecords);
				
				return record;
			}else{
				return null; //返回空说明没有失败记录
			}
		} catch (Exception e) {
			throw new UPPException("",e.getMessage());
		}
	}
	@Override
	public List<PaymentTrade> transferSynchronizingByPTradeNos(
			List<String> tradeNoList) throws UPPException{
		if(tradeNoList!=null&&tradeNoList.size()>0){
			PaymentTradeExampleExtended ptee = new PaymentTradeExampleExtended();
			ptee.createCriteria().andTradeExternalNoIn(tradeNoList)
				.andTradeStatusEqualTo(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS)
				.andServiceProviderCodeEqualTo(PayDict.PLATFORM_CODE_YEE_PAY);
			ptee.setOrderByClause(PaymentTrade.fieldPayConfirmDate() + " desc");
			//PaginationResult<PaymentTrade> tradePageResult=paymentBaseManagerService.queryPaymentTransByPage(ptee);
			
			//return withYBContrastInternalManager.transferBatchSynchronizingByPage(tradePageResult.getData(),1 );
		}
		return null;
	}

	@Override
	public List<PaymentTrade> transferBatchSynchronizingByPTradeNos(
			List<String> tradeNoList) throws UPPException {
		
		if(tradeNoList!=null&&tradeNoList.size()>0){
			PaymentTradeExampleExtended ptee = new PaymentTradeExampleExtended();
			ptee.createCriteria().andTradeExternalNoIn(tradeNoList)
				.andIsClearingEqualTo(CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_STORE_CHECK)
				.andTradeStatusEqualTo(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS)
				.andServiceProviderCodeEqualTo(PayDict.PLATFORM_CODE_YEE_PAY);
			ptee.setOrderByClause(PaymentTrade.fieldPayConfirmDate() + " desc");
			//PaginationResult<PaymentTrade> tradePageResult=paymentBaseManagerService.queryPaymentTransByPage(ptee);
			
			//return withYBContrastInternalManager.transferBatchSynchronizingByPage(tradePageResult.getData(),1 );
		}
		return null;
	}

	@Override
	public boolean sendLastTradeSign(String dateString) throws UPPException {
		
		return withYBContrastInternalManager.sendDateLastTradeSign(dateString);
	}
	@Override
	public boolean dealWithFailSycTradeByTime(Long startTime,Long endTime) throws UPPException{
		List<PaymentTrade>  tradePageResult = withYBContrastInternalManager.querySuccessUnsycTrades(startTime, endTime);
		
		for(PaymentTrade trade:tradePageResult){
			List<PaymentTrade> list = new ArrayList<PaymentTrade>();
			list.add(trade);
			withYBContrastInternalManager.transferBatchSynchronizingByPage(list,1 );
		}
		this.YBSycHaveSuccessTradeHandle(startTime);
		
		SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
		final String accountDateStr=sf.format(startTime);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					withYBContrastInternalManager.sendDateLastTradeSign(accountDateStr);
					
					logger.warn("<<<<<M>>>>>发送最后一笔成功同步的交易记录！");
				} catch (UPPException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}).start();
		
		return true;
	}
	/**
	 * 根据分页信息处理对应的交易同步操作
	 * @param ptee 交易的分页查询，根据该信息查询出相应的支付交易记录
	 * @return  List<PaymentTrade> 未成功同步的交易记录
	 * @throws UPPException
	 */
	private List<PaymentTrade> dealWithSyncTradeBySinglePage(PaymentTradeExampleExtended ptee) throws UPPException{
		logger.debug("<<<<<M>>>>>skipNum="+ptee.getSkipNum()+",LimitNum="+ptee.getLimitNum()+",EndNum="+ptee.getEndNum());
		PaginationResult<PaymentTrade> tradePageResult=null;//paymentBaseManagerService.queryPaymentTransByPage(ptee);		
		
		logger.debug("<<<<<M>>>>>查出交易结果的数量:"+tradePageResult.getData().size()+",");
		if(tradePageResult.getData()!=null&&tradePageResult.getData().size()>0){
			ArrayList<PaymentTrade> tradeList=new ArrayList<PaymentTrade>();
			tradeList.addAll(tradePageResult.getData());
			
			logger.info("根据分页信息处理对应的交易同步操作,交易记录数量："+tradeList.size());
			return withYBContrastInternalManager.transferBatchSynchronizingByPage(tradeList,ptee.getSkipNum()+1 );
		}else{
			logger.warn("根据分页信息处理对应的交易同步操作，未查到对应的交易记录！");
			return null;
		}
	}
	
	private Integer YBSycHaveSuccessTradeHandle(Long time){
		Integer count = 0;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Query query =null;//mongoService.getQuery(YBSycHaveSuccessTrade.class);
			
			query.field("accountDate").equal(sf.format(time));
			
			List<YBSycHaveSuccessTrade> list =null;// mongoService.query(YBSycHaveSuccessTrade.class, query);
			
			for(YBSycHaveSuccessTrade trade: list){
				try {
					internalPaymentTradeBaseManager
						.updatePaymentTransClearingStatus(trade.getPayTradeId(), CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_THIRDPART_SYC,trade.getBatchInsideNo());
					count=count+1;
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return count;
	}
}
