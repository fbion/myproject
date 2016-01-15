package com.sinoiov.upp.manager.account;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.HAccount;
import com.ctfo.account.dao.beans.HAccountExampleExtended;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.manager.AbstractManager;
import com.sinoiov.upp.util.DefaultConfig;
import com.sinoiov.yyzc.commons.kafka.YyzcKafkaBasicProducer;

@Service("accountHistoryManager")
public class AccountHistoryManagerImpl extends AbstractManager implements IAccountHistoryManager {

	private static final Log logger = LogFactory.getLog(AccountHistoryManagerImpl.class);
	
	/**
	 * 此方法要配合消息处理方法(receiveHandleMQ)来完成
	 * 1.发消息到队列
	 * 2.处理监听到的消息
	 */
	@Override
	public void recordHistory(Account account){
		try{			
			//发送消息到队列
			YyzcKafkaBasicProducer client = new YyzcKafkaBasicProducer();
			
			client.send(DefaultConfig.getValue("PAY_ACCOUNT_HISTORY"), account);
			
		}catch(Exception e){
			logger.error("发送账户信息到消息队列异常！", e);
		}	
	}
	
	@Override
	public void receiveHandleMQ(Account account) throws UPPException {
		try{
			
			HAccount haccount = new HAccount();	
			String[] ignoreProperties = {"id"};
			BeanUtils.copyProperties(account, haccount, ignoreProperties);
			haccount.setAccountId(account.getId());
			haccount.setRecordCreateTime(System.currentTimeMillis());
			super.addModel(haccount);
			
		}catch(Exception e){
			logger.error("处理账户消息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "处理账户消息异常:"+e.getLocalizedMessage());
		}	
	}


	@Override
	public List<HAccount> queryAccountHistory(
			HAccountExampleExtended exampleExtended) throws UPPException {
		try{
			
			return super.getModels(exampleExtended);
			
		}catch(Exception e){
			logger.error("根据条件查询历史信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询历史信息异常:"+e.getLocalizedMessage());
		}	
	}

	@Override
	public PaginationResult<HAccount> queryAccountHistoryByPage(
			HAccountExampleExtended exampleExtended) throws UPPException {
		try{
			
			return super.paginateModels(exampleExtended);
			
		}catch(Exception e){
			logger.error("根据条件查询历史分页信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询历史分页信息异常:"+e.getLocalizedMessage());
		}	
	}

	@Override
	public void updateAccountHistoryById(String accountId, String ownerUserNo)
			throws UPPException {
		super.notNull(accountId, ownerUserNo);
		try{
			HAccountExampleExtended exampleExtended = new HAccountExampleExtended();
			exampleExtended.createCriteria().andAccountIdEqualTo(accountId);
			List<HAccount> list = super.getModels(exampleExtended);
			HAccount hAccount = null;
			for(HAccount h : list){
				hAccount = new HAccount();
				hAccount.setId(h.getId());
				hAccount.setOwnerUserNo(ownerUserNo);
				super.updateModelPart(hAccount);
			}
		}catch(Exception e){
			logger.error("修改账户历史接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改账户历史接口异常:"+e.getLocalizedMessage());
		}
	}
	
}
