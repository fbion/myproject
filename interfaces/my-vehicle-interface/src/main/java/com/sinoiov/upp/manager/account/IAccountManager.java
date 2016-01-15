package com.sinoiov.upp.manager.account;

import java.util.List;
import java.util.Map;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountChange;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.account.dto.beans.BalanceDto;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;

public interface IAccountManager{
		
	/***
	 * 根据账号查询账户信息
	 * @param accountNo 账号
	 * @return
	 * @throws UPPException
	 */
	public Account getAccountByAccountNo(String accountNo) throws UPPException;
	/***
	 * 根据用户ID查询账户信息
	 * @param userId 用户ID
	 * @return
	 * @throws UPPException
	 */
	public Account getAccountByUserId(String userId) throws UPPException;
	/**
	 * 根据账号查询账户所属的用户ID
	 * @param accountNo
	 * @return
	 * @throws UPPException
	 */
	public String getUserIdByAccountNo(String accountNo) throws UPPException;
	/**
	 * 根据账户ID查询账户信息
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	public Account getAccountById(String id) throws UPPException;
	
	/**
	 * 根据条件查询账户集合集合
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public List<Account> queryAccount(AccountExampleExtended exampleExtended) throws UPPException;
	/**
	 * 根据条件统计账户数据
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public int countAccount(AccountExampleExtended exampleExtended) throws UPPException;
	/**
	 * 根据条件查询账户分页信息
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public PaginationResult<Account> queryAccountByPage(AccountExampleExtended exampleExtended) throws UPPException;
	
	/**
	 * 根据条件统计账户余额
	 * @param parameter
	 * @return
	 * @throws UPPException
	 */
	public BalanceDto sumAccountBalance(Map<String, Object> parameter)throws UPPException;
	
	
	
	
	
	
	
	
	
	
	/***
	 * 业务平台开户
	 * @param account
	 *            开户实体
	 * @return 开户成功后返回实体
	 * @throws UPPException
	 */
	public Account createAccount(Account account) throws UPPException;
	
	/**
	 * 修改交易密码
	 * @param accountNo
	 * @param payPassword
	 * @return
	 * @throws UPPException
	 */
	public void modifyPayPassword(String accountNo, String payPassword) throws UPPException;
	
	/**
	 * 修改支付密码输入错误次数
	 * @param accountNo
	 * @param errorCount
	 * @throws UPPException
	 */
	public void modifyPayPasswordErrorCount(String accountNo, int errorCount) throws UPPException;
	
	/**
	 * 修改支付手机号码
	 * @param accountNo
	 * @param mobileNo
	 * @throws UPPException
	 */
	public void modifyPayMobileNo(String accountNo, String mobileNo)throws UPPException;
	
	/**
	 * 锁定账户
	 * @param accountNo
	 * @param lockReasons
	 * @return
	 * @throws UPPException
	 */
	public void lockAccount(String accountNo, String lockReasons) throws UPPException;	
	
	/***
	 * 解锁账户
	 * 
	 * @param accountNo
	 *            账户号
	 * @throws UPPException
	 */
	public void unlockAccount(String accountNo) throws UPPException;
	
	/***
	 * 更新主账户,注意不需要更新的字段为null
	 * 
	 * @param account
	 *            账户实体
	 * @throws UPPException
	 */
	public void updateAccountById(Account account) throws UPPException;
	
	/**
	 * 修改会员编号
	 * @param id
	 * @param ownerUserNo
	 * @throws UPPException
	 */
	public int updateOwnerUserNoById(String id, String ownerUserNo) throws UPPException;

	/***
	 * 更新主账户状态
	 * 
	 * @param accountNo
	 *            主账户
	 * @param state
	 *            状态
	 * @throws UPPException
	 */
	public void updateAccountState(String accountNo, String state) throws UPPException;
	/***
	 * 吊销账户
	 * 
	 * @param accountNo
	 *            账户号
	 * @throws UPPException
	 */
	public void revokeAccount(String accountNo) throws UPPException;

	/***
	 * 恢复账户为正常状态
	 * 
	 * @param accountNo
	 *            账户号
	 * @throws UPPException
	 */
	public void recoveryAccount(String accountNo) throws UPPException;
	
	/***
	 * 检查备付金账户余额是否接近阀值
	 * @param accountNo 备付金账户号
	 * @return
	 * @throws UPPException
	 */
	public boolean isWarnValue(String accountNo) throws UPPException;
	/***
	 * 获取账户可用余额
	 * @param accountNo
	 * @return
	 * @throws UPPException
	 */
	public Long getBalance(String accountNo) throws UPPException;
	
	
	/***
	 * 账户进账
	 * 
	 * @param AccountChange
	 *            进账模型
	 * @throws UPPException
	 */
	public void income(AccountChange income) throws UPPException;

	/**
	 * 账户出账
	 * @param payout
	 * @param payoutFrozenBalance 是否从冻结金额中出账
	 * @throws UPPException
	 */
	public void payout(AccountChange payout, boolean payoutFrozenBalance) throws UPPException;
	
	/**
	 * 进账出账组合方法, 此方法为payout 和 income 的组合，不生产交易相关的信息
	 * @param payoutAccountNo
	 * @param incomeAccountNo
	 * @param change
	 * @param payoutFrozenBalance 是否从冻结金额中出账
	 * @throws UPPException
	 */
	public void payoutAndIncome(String payoutAccountNo, String incomeAccountNo, AccountChange change, boolean payoutFrozenBalance) throws UPPException;
	
	/**
	 * 账户提现专用出账接口,此接口只适用于提现出账进使用
	 * @param payoutWithdrawCash
	 * @throws UPPException
	 */
	public void payoutForWithdrawCash(AccountChange payoutWithdrawCash) throws UPPException;

	/***
	 * 冻结子账户金额
	 * 
	 * @param tradeInternalNo
	 *            内部交易流水号
	 * @param subject
	 *            科目
	 * @param accountNo
	 *            被冻结的账户号
	 * @param storeCode
	 *            发起冻结请求的商户
	 * @param money
	 *            被冻结金额
	 * @throws UPPException
	 */
	public void frozenAccount(String tradeInternalNo, String accountDate, String subject, String accountNo, String storeCode, String orderId, Long money) throws UPPException;

	/***
	 * 解冻子账户金额
	 * 
	 * @param tradeInternalNo
	 *            内部交易流水号
	 * @param subject
	 *            科目
	 * @param accountNo
	 *            被解冻的账户号
	 * @param storeCode
	 *            发起冻结请求的商户
	 * @param money
	 *            被解冻金额
	 * @throws UPPException
	 */
	public void unfrozenAccount(String tradeInternalNo, String accountDate, String subject, String accountNo, String storeCode, String orderId, Long money) throws UPPException;

	
}
