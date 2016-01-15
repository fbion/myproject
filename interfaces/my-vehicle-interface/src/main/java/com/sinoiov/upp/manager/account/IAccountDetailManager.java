package com.sinoiov.upp.manager.account;

import java.util.List;
import java.util.Map;

import com.ctfo.account.dao.beans.AccountDetail;
import com.ctfo.account.dao.beans.AccountDetailExampleExtended;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;

public interface IAccountDetailManager {
	
	/***
	 * 记录主账户流水
	 * 
	 * @param accountDetail
	 *            主账户流水模型
	 * @throws UPPException
	 */
	public void recordAccountDetail(AccountDetail accountDetail) throws UPPException;
	
	/**
	 * 记录主账户流水
	 * @param accountId 账户ID
	 * @param insideAccountNo 账号
	 * @param accountingType 账户操作类型
	 * @param subject 科目
	 * @param money 交易金额 
	 * @param currentMoney 交易后余额(当前余额)
	 * @param accountDate 进账时间
	 * @param tradeInternalNo 内部交易流水号
	 * @param storeCode 商户编号
	 * 
	 * @throws UPPException
	 * 
	 */
	public void recordAccountDetail(String accountId, String insideAccountNo, String accountingType, String subject, Long money, Long currentMoney, String accountDate, String tradeInternalNo, String storeCode, String orderId)throws UPPException;
	
	/**
	 * 根据主键查询流水详细信息
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	public AccountDetail getAccountDetailById(String id) throws UPPException;
	
	/**
	 * 根据条件查询账户流水集合
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public List<AccountDetail> queryBookAccount(AccountDetailExampleExtended exampleExtended) throws UPPException;
	/**
	 * 根据条件统计账户流水数量
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public int countBookAccount(AccountDetailExampleExtended exampleExtended) throws UPPException;
	/***
	 * 根据账号查询账户流水信息
	 * @param accountNo 账号
	 * @return
	 * @throws UPPException
	 */
	public PaginationResult<AccountDetail> queryAccountDetailPage(AccountDetailExampleExtended exampleExtended) throws UPPException;
	
	/**
	 * 根据条件统计账户流水
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public Long sumAccountBookBalance(Map<String, Object> parameter) throws UPPException;
	
	
	
}
