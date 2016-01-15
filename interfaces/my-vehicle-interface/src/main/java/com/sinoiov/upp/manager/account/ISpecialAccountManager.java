package com.sinoiov.upp.manager.account;

import java.util.List;

import com.ctfo.account.dao.beans.AccountChange;
import com.ctfo.account.dao.beans.HSpecialAccount;
import com.ctfo.account.dao.beans.HSpecialAccountExampleExtended;
import com.ctfo.account.dao.beans.SpecialAccount;
import com.ctfo.account.dao.beans.SpecialAccountDetail;
import com.ctfo.account.dao.beans.SpecialAccountDetailExampleExtended;
import com.ctfo.account.dao.beans.SpecialAccountExampleExtended;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;

public interface ISpecialAccountManager {
	
	/***
	 * 业务平台开户
	 * 
	 * @param specialAccount
	 *            子账户实体
	 * @return
	 * @throws UPPException
	 */
	public SpecialAccount createSpecialAccount(SpecialAccount specialAccount) throws UPPException;

	/***
	 * 冻结子账户，冻结成功后返回受影响的行数
	 * 
	 * @param bookAccInternalNo
	 *            内部账务流水号
	 * @param subject
	 *            科目
	 * @param tradeInternalNo
	 *            内部交易流水号
	 * @param accountNo
	 *            被冻结的账户号
	 * @param storeCode
	 *            发起冻结请求的商户
	 * @param money
	 *            金额
	 * @return
	 * @throws UPPException
	 */
	public int frozenSpecialAccount(String bookAccInternalNo, String accountDate, String subject, String tradeInternalNo, String storeCode, String mainAccountId, String accountNo, Long money) throws UPPException;

	/**
	 * 解冻子账户
	 * @param bookAccInternalNo
	 * @param accountDate
	 * @param subject
	 * @param tradeInternalNo
	 * @param storeCode
	 * @param mainAccountId
	 * @param accountNo
	 * @param money
	 * @return
	 * @throws UPPException
	 */
	public int unfrozenSpecialAccount(String bookAccInternalNo, String accountDate, String subject, String tradeInternalNo, String storeCode, String mainAccountId, String accountNo, Long money) throws UPPException;

	/**
	 * 子账户进账
	 * @param bookAccInternalNo
	 * @param accountDate
	 * @param subject
	 * @param tradeInternalNo
	 * @param insideAccountNo
	 * @param storeCode
	 * @param mainAccountId
	 * @param income
	 * @return
	 * @throws UPPException
	 */
	public int incomeSpecialAccount(String bookAccInternalNo, String accountDate, String subject, String tradeInternalNo, String insideAccountNo, String storeCode, String mainAccountId, AccountChange income) throws UPPException;

	/**
	 * 子账户出账
	 * @param bookAccInternalNo
	 * @param accountDate
	 * @param subject
	 * @param tradeInternalNo
	 * @param insideAccountNo
	 * @param storeCode
	 * @param mainAccountId
	 * @param payout
	 * @return
	 * @throws UPPException
	 */
	public int payoutSpecialAccount(String bookAccInternalNo, String accountDate, String subject, String tradeInternalNo, String insideAccountNo, String storeCode, String mainAccountId, AccountChange payout) throws UPPException;
	
	/**
	 * 根据条件查询子账户集合信息
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public List<SpecialAccount> querySpecialAccount(SpecialAccountExampleExtended exampleExtended) throws UPPException;

	/**
	 * 根据条件查询子账户分页信息
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public PaginationResult<SpecialAccount> querySpecialAccountByPage(SpecialAccountExampleExtended exampleExtended) throws UPPException;

	
	
	
	/***
	 * 记录子账户流水
	 * 
	 * @param specialAccountDetail
	 *            子账户流水模型
	 * @throws UPPException
	 */
	public SpecialAccountDetail addSpecialAccountDetail(SpecialAccountDetail specialAccountDetail) throws UPPException;

	/**
	 * 根据条件查询子账户流水集合
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public List<SpecialAccountDetail> queryBookSpecialAccount(SpecialAccountDetailExampleExtended exampleExtended) throws UPPException;

	/**
	 * 根据条件查询子账户流水分页信息
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public PaginationResult<SpecialAccountDetail> queryBookSpecialAccountByPage(SpecialAccountDetailExampleExtended exampleExtended) throws UPPException;

	
	
	
	
	/***
	 * 记录子账户历史
	 * 
	 * @param hSpecialAccount
	 *            子账户历史数据模型
	 * @throws UPPException
	 */
	public void addSpecialAccountHistory(HSpecialAccount hSpecialAccount) throws UPPException;
	
	/**
	 * 根据条件查询子账户历史集合
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public List<HSpecialAccount> queryHisSpecialAccount(HSpecialAccountExampleExtended exampleExtended) throws UPPException;

	/**
	 * 根据条件查询子账户历史分页
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public PaginationResult<HSpecialAccount> queryHisSpecialAccountByPage(HSpecialAccountExampleExtended exampleExtended) throws UPPException;

	
	
}
