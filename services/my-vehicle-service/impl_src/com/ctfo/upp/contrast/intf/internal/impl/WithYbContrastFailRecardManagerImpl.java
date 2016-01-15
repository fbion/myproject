package com.ctfo.upp.contrast.intf.internal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.contrast.intf.internal.WithYBInternalContrast;
import com.ctfo.account.contrast.intf.internal.WithYbContrastFailRecardInternalManager;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.dto.beans.SmartTradeAccountRecordDto;
import com.ctfo.upp.accountservice.payment.internal.impl.AbstractPayment;
import com.ctfo.upp.contrast.intf.external.impl.WithYBContrastManagerImpl;
import com.ctfo.upp.dict.CheckAccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.payment.intf.thirdpart.oper.internal.ThirdPartyPaymentDealWithManager;
import com.ctfo.upp.payment.intf.thirdpart.query.internal.ThirdPartAccountInspectManager;
import com.ctfo.upp.payment.intf.thirdpart.query.internal.ThirdPartyPaymentQueryManager;
import com.ctfo.upp.root.utils.TimeHandleUtil;
import com.sinoiov.upp.manager.account.IAccountManager;

//@Service("withYbContrastFailRecardManager")
public class WithYbContrastFailRecardManagerImpl extends AbstractPayment implements WithYbContrastFailRecardInternalManager{
	private static Log logger = LogFactory.getLog(WithYBContrastManagerImpl.class);
	
	@Autowired
	@Qualifier("oracleDataSource") 
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("withYBContrastInternalManager")
	private WithYBInternalContrast withYBInternalContrast;
	
	@Autowired
	@Qualifier("thirdPartyPaymentQueryManager")
	private ThirdPartyPaymentQueryManager thirdPartyPaymentQueryManager;
	
	@Autowired
	@Qualifier("thirdPartyPaymentDealWithManager")
	private  ThirdPartyPaymentDealWithManager thirdPartyPaymentDealWithManager;
	
	@Autowired
	@Qualifier("thirdPartAccountInspectManager")
	private ThirdPartAccountInspectManager thirdPartAccountInspectManager;
	
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;
	
	@Override
	public Map<String,SmartTradeAccountRecordDto>  getSmartTradeAccountRecords(String insideAccountNo,String accountDate)throws UPPException{
		Connection conn=null;
		String accountSql="select bi.trade_external_no,bi.account_money,bi.bookaccount_type,pt.pay_confirm_date,pt.trade_type,pt.trade_status,pt.is_clearing from tb_upp_book_account_info bi join tb_upp_pay_trade pt on bi.trade_external_no=pt.trade_external_no  where bi.account_date=?  and bi.inside_account_no=?  ";
		SimpleDateFormat daysf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Map<String,SmartTradeAccountRecordDto> dtoMap= new HashMap<String,SmartTradeAccountRecordDto>();
			conn=dataSource.getConnection();
			PreparedStatement accountPre= conn.prepareStatement(accountSql);
			accountPre.setString(1, accountDate);
			accountPre.setString(2, insideAccountNo);


			
			ResultSet rs = accountPre.executeQuery();	
			while(rs.next()){
				SmartTradeAccountRecordDto dto = new SmartTradeAccountRecordDto();
				dto.setTradeExternalNo(rs.getString(1));
				dto.setTradeAmount(rs.getLong(2));
				dto.setBookAccountType(rs.getString(3));
				dto.setPayConfirmDate(rs.getLong(4));
				dto.setTradeType(rs.getString(5));
				dto.setTradeStatus(rs.getString(6));
				dto.setIsClearing(rs.getString(7));
				
				dto.setPayConfirmDateString(daysf.format(dto.getPayConfirmDate()));
				
				dtoMap.put(dto.getPayConfirmDateString()+","+dto.getTradeAmount(), dto);
			}
			return dtoMap;
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new UPPException(e.getMessage());
		}finally{
			if(conn!=null){try {conn.close();} catch (SQLException e) {logger.error(e.getMessage(),e);}}
		}
	}
	
	@Override
	public void dealAccountStatus()throws UPPException{
		Connection conn=null;
		String accountSql="select t.account_no from tb_upp_pay_trade t where t.is_clearing!='2' and t.pay_confirm_date<? and t.trade_status=? and t.service_provider_code=? group by t.account_no";

		String collAccountSql="select t.collect_money_account_no from tb_upp_pay_trade t where   t.is_clearing!='2'  and t.pay_confirm_date<? and t.trade_status=? and t.service_provider_code=? group by t.collect_money_account_no";

		try {
			HashSet<String> accountSet=new HashSet<String>();
			Long todayMonringTime = TimeHandleUtil.getTimesmorning();
			
			conn=dataSource.getConnection();
			PreparedStatement accountPre= conn.prepareStatement(accountSql);
			accountPre.setLong(1, todayMonringTime);
			accountPre.setString(2, PayDict.PAY_TRADE_STATUS_PAY_SUCCESS);
			accountPre.setString(3, PayDict.PLATFORM_CODE_YEE_PAY);

			
			ResultSet rs = accountPre.executeQuery();	
			while(rs.next()){
				accountSet.add(rs.getString(1));
			}
			accountPre= conn.prepareStatement(collAccountSql);
			accountPre.setLong(1, todayMonringTime);
			accountPre.setString(2, PayDict.PAY_TRADE_STATUS_PAY_SUCCESS);
			accountPre.setString(3, PayDict.PLATFORM_CODE_YEE_PAY);
			rs = accountPre.executeQuery();	
			while(rs.next()){
				accountSet.add(rs.getString(1));
			}
			logger.debug(accountSet);
			for(String insideAccountNo:accountSet){
				this.accountUnlockOper(insideAccountNo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new UPPException(e.getMessage());
		}finally{
			if(conn!=null){try {conn.close();} catch (SQLException e) {logger.error(e.getMessage(),e);}}
		}
	}
	/**
	 * 1.查出未同步交易的最大时间和最小时间
	 * 2.按天成多个时间段
	 * 3.按时间段进行交易同步
	 */
	@Override
	public void dealAllUnsycRecord() throws UPPException {
		Connection conn=null;
		String maxTimeSql="select max(t.PAY_CONFIRM_DATE)  from TB_UPP_PAY_TRADE t where t.pay_confirm_date<? and t.trade_status=? and t.service_provider_code=? and t.Is_Clearing!=? ";
		String minTimeSql="select min(t.PAY_CONFIRM_DATE)  from TB_UPP_PAY_TRADE t where t.pay_confirm_date<? and t.trade_status=? and t.service_provider_code=? and t.Is_Clearing!=? ";
		try {
			conn=dataSource.getConnection();
			
			Long todayMonringTime = TimeHandleUtil.getTimesnight();
			Long minTime=null;
			Long maxTime= null;
			
			todayMonringTime=todayMonringTime+1;
			
			PreparedStatement psMin= conn.prepareStatement(minTimeSql);
			psMin.setLong(1, todayMonringTime);
			psMin.setString(2, PayDict.PAY_TRADE_STATUS_PAY_SUCCESS);
			psMin.setString(3, PayDict.PLATFORM_CODE_YEE_PAY);
			psMin.setString(4, CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_THIRDPART_SYC);
			ResultSet rs = psMin.executeQuery();			
			if(rs.next()){
				minTime = rs.getLong(1);
			}
			
			
			PreparedStatement psMax= conn.prepareStatement(maxTimeSql);
			psMax.setLong(1, todayMonringTime);
			psMax.setString(2, PayDict.PAY_TRADE_STATUS_PAY_SUCCESS);
			psMax.setString(3, PayDict.PLATFORM_CODE_YEE_PAY);
			psMax.setString(4, CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_THIRDPART_SYC);
			rs = psMax.executeQuery();
			if(rs.next()){
				maxTime = rs.getLong(1);
			}
			if(maxTime!=null&&minTime!=null){
				List<Long[]> startEndTimesList 
					= this.partitionTimeQuantum(minTime, maxTime);
				
				this.dealWithSycTrade(startEndTimesList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new UPPException(e.getMessage());
		}finally{
			if(conn!=null){try {conn.close();} catch (SQLException e) {logger.error(e.getMessage(),e);}}
		}
	}
	/**
	 * 按照分好的时间段，进行交易同步
	 * @param startEndTimesList
	 */
	private void dealWithSycTrade(List<Long[]> startEndTimesList){
		
		for(Long[] startEndTime:startEndTimesList){
			try {
				List<String> accountNoList=new ArrayList<String>();
				accountNoList.addAll(this.unSycSuccessAccountNo(startEndTime[0],startEndTime[1]));
				
			
				
				accountNoList = thirdPartAccountInspectManager.queryAccountStatusFromYb(accountNoList, new ArrayList(), false);
				
				
				for(String insideAccountNo:accountNoList){
//					internalAccountManager.unlockAccount(insideAccountNo);
				}
				
				
				List<PaymentTrade> paymentTradeList = withYBInternalContrast.querySuccessUnsycTrades(startEndTime[0],startEndTime[1]);
				
				if(paymentTradeList.size()>0){
					logger.info(new StringBuilder().append("<<<<<M>>>>>,没有全部完成交易同步的时间段:").append(startEndTime[0]).append(" -- ").append(startEndTime[1]).toString());
					if(paymentTradeList.size()<30){
						withYBInternalContrast.transferBatchSynchronizingByPage(paymentTradeList, 1);
					}else{
						int batchNo=1;
						List<PaymentTrade> batchList = new ArrayList<PaymentTrade>();
						for(PaymentTrade trade:paymentTradeList){
							batchList.add(trade);
							if(batchList.size()>30){
								withYBInternalContrast.transferBatchSynchronizingByPage(batchList, batchNo);
								batchNo+=1;
								batchList.clear();
							}
						}
						if(batchList.size()>0){
							withYBInternalContrast.transferBatchSynchronizingByPage(batchList, batchNo);
						}
					}	
				}
			} catch (UPPException e) {
				logger.error(e.getMessage(),e);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * 按天划分时间段分段
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private List<Long[]> partitionTimeQuantum(Long startTime,Long endTime){		

		Calendar starCal = Calendar.getInstance();
		starCal.setTime(new Date(startTime));
		starCal.set(Calendar.HOUR_OF_DAY, 0);		
		starCal.set(Calendar.SECOND, 0);		
		starCal.set(Calendar.MINUTE, 0);		
		starCal.set(Calendar.MILLISECOND, 0);	
				
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(new Date(endTime));
		endCal.set(Calendar.HOUR_OF_DAY, 24);		
		endCal.set(Calendar.SECOND, 0);		
		endCal.set(Calendar.MINUTE, 0);		
		endCal.set(Calendar.MILLISECOND, 0);
		
		Long day= (endCal.getTime().getTime()-starCal.getTime().getTime())/(3600000*24);
		
		List<Long[]> timeList=new ArrayList<Long[]>();
		for(int i=0;i<day;i++){
			Long s=starCal.getTime().getTime()+i*(3600000*24);
			Long e=starCal.getTime().getTime()+((i+1)*(3600000*24))-1;
			timeList.add(new Long[]{s,e});
		}
		
		return timeList;	
	}
	private boolean accountUnlockOper(String insideAccountNo)throws UPPException{
		
		try {
			QueryAccountInfoResponseYB res = thirdPartyPaymentQueryManager.queryThirdPartyAccountInfo(PayDict.PLATFORM_CODE_YEE_PAY, insideAccountNo);

			String balance = res.getTotalBalance();
			String unTakeCashBalance=res.getNoncashableBalance();
			String status = res.getAccountStatus();
			if("FROZEN".equals(status)){
				return thirdPartyPaymentDealWithManager.unlockThirdPartyAccount(insideAccountNo);
				
			}else{
				return true;
			}
		} catch (UPPException e) {
			logger.error(e.getMessage(),e);
			return false;
		}
	}
	
	@Override
	public HashSet<String> unSycSuccessAccountNo(Long startTime,Long endTime) throws Exception{
		PaymentTradeExampleExtended ptee = new PaymentTradeExampleExtended();
		ptee.createCriteria().andPayConfirmDateGreaterThanOrEqualTo(startTime)
			.andPayConfirmDateLessThanOrEqualTo(endTime)
			.andIsClearingEqualTo(CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_STORE_CHECK)
			.andTradeStatusEqualTo(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS)
			.andServiceProviderCodeEqualTo(PayDict.PLATFORM_CODE_YEE_PAY);
		List<PaymentTrade> notSycRecords =  this.getModels(ptee);
		HashSet<String> accountNoSet = new HashSet<String>();
		for(PaymentTrade notSycRecord:notSycRecords){
			accountNoSet.add(notSycRecord.getAccountNo());
			accountNoSet.add(notSycRecord.getCollectMoneyAccountNo());		
		}
		return accountNoSet;
	}
	
}
