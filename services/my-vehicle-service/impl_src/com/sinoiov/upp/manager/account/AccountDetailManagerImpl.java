package com.sinoiov.upp.manager.account;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.AccountDetail;
import com.ctfo.account.dao.beans.AccountDetailExampleExtended;
import com.ctfo.upp.accountservice.utils.TradeNumberHelper;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.manager.AbstractManager;

@Service("accountDetailManager")
public class AccountDetailManagerImpl extends AbstractManager implements IAccountDetailManager {

	private static final Log logger = LogFactory.getLog(AccountDetailManagerImpl.class);
	@Override
	public AccountDetail getAccountDetailById(String id) throws UPPException {
		try{
			
			AccountDetail bean = new AccountDetail();
			bean.setId(id);			
			return super.getModelById(bean);
			
		}catch(Exception e){
			logger.error("根据ID查询账户流水异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据ID查询账户流水异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public List<AccountDetail> queryBookAccount(
			AccountDetailExampleExtended exampleExtended) throws UPPException {
		try{
			
			return super.getModels(exampleExtended);
			
		}catch(Exception e){
			logger.error("根据查询条件查询账户流水信息异常异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据查询条件查询账户流水信息异常异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public PaginationResult<AccountDetail> queryAccountDetailPage(
			AccountDetailExampleExtended exampleExtended) throws UPPException {
		try{
			
			return super.paginateModels(exampleExtended);
			
		}catch(Exception e){
			logger.error("根据查询条件查询账户流水分页信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据查询条件查询账户流水分页信息异常:"+e.getLocalizedMessage());
		}
	}


	@Override
	public Long sumAccountBookBalance(Map<String, Object> parameter) throws UPPException {
		try{
			super.notNull(parameter);
			List<Map<? extends Object, ? extends Object>> list = super.queryListBySQL("STAT_TB_UPP_ACCOUNT_INFO.sumAccountDetailRecordedDeduction", parameter);
			if(list!=null && list.size()>0){
				return list.get(0).get("ACCOUNTMONEY")==null?0:((BigDecimal) list.get(0).get("ACCOUNTMONEY")).longValue();
			 }else{
				 logger.warn("未统计到账户余额信息");
			 }			
		}catch(Exception e){
			logger.error("统计账户出账流水异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "统计账户出账流水异常:"+e.getLocalizedMessage());
		}
		return null;
	}
	
	@Override
	public void recordAccountDetail(AccountDetail accountDetail)
			throws UPPException {
		try{
			
			super.addModel(accountDetail);
			
		}catch(Exception e){
			logger.error("记录账户流水异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "记录账户流水异常:"+e.getLocalizedMessage());
		}
		
	}

	@Override
	public void recordAccountDetail(String accountId, String insideAccountNo,
			String accountingType, String subject, Long money,
			Long currentMoney, String accountDate, String tradeInternalNo,
			String storeCode, String orderId) throws UPPException {
		
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setInsideExternalNo(TradeNumberHelper.getBookAccExternalNo());
		accountDetail.setBookaccountType(accountingType);
		accountDetail.setInsideAccountNo(insideAccountNo);
		accountDetail.setAccountSubject(subject);
		accountDetail.setAccountTime(System.currentTimeMillis());
		accountDetail.setAccountMoney(money);
		accountDetail.setAccountDate(StringUtils.isBlank(accountDate) ? new SimpleDateFormat("yyyy-MM-dd").format(new Date()):accountDate);
		accountDetail.setTradeExternalNo(tradeInternalNo);
		accountDetail.setAccountId(accountId);
		accountDetail.setStoreCode(storeCode);
		accountDetail.setCurrentMoney(currentMoney);
		accountDetail.setOrderId(orderId);
		
		this.recordAccountDetail(accountDetail);
		
	}

	@Override
	public int countBookAccount(AccountDetailExampleExtended exampleExtended)
			throws UPPException {
		super.notNull(exampleExtended);
		try{
			return super.countModels(exampleExtended);	
		}catch(Exception e){
			logger.error("统计账户流水数量异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "统计账户流水数量异常:"+e.getLocalizedMessage());
		}
		
		
	}

}
