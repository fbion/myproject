package com.sinoiov.upp.business.account;

import java.util.List;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.service.dto.AccountDetailDto;
import com.sinoiov.upp.service.dto.AccountDto;

/***
 * 账户管理业务接口
 */
public interface IAccountBusiness {

	/***
	 * 根据账号查询账户信息
	 * 
	 * @param accountNo
	 *            账号
	 * @return
	 * @throws UPPException
	 */
	public AccountDto getAccountByAccountNo(String accountNo) throws UPPException;

	/***
	 * 根据用户ID查询账户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @throws UPPException
	 */
	public AccountDto getAccountByUserId(String userId) throws UPPException;

	/***
	 * 根据账号查询账户分页信息
	 * 
	 * @param accountNo
	 *            账号
	 * @return
	 * @throws UPPException
	 */
	public PaginationResult<AccountDto> queryAccountByPage(
			DynamicSqlParameter requestParam) throws UPPException;

	/***
	 * 根据账号查询账户信息
	 * 
	 * @param accountNo
	 *            账号
	 * @return
	 * @throws UPPException
	 */
	public List<AccountDto> queryAccountList(DynamicSqlParameter requestParam)
			throws UPPException;

	/***
	 * 业务平台开户
	 * 
	 * @param account
	 *            开户实体
	 * @return 开户成功后返回实体
	 * @throws UPPException
	 */
	public AccountDto createAccount(AccountDto accountDto) throws UPPException;

	/**
	 * 修改交易密码
	 * 
	 * @param accountNo
	 * @param oldMD5
	 * @param newMD5
	 * @return
	 * @throws UPPException
	 */
	public String modifyPayPassword(String accountNo, String oldMD5,
			String newMD5) throws UPPException;
	/**
	 * 修改支付手机号
	 * 
	 * @param accountNo
	 * @param mobileNo
	 * @return
	 * @throws UPPException
	 */
	public String modifyPayMobileNo(String accountNo, String mobileNo) throws UPPException;

	/**
	 * 修改交易密码, 此方法为第一次设置支付密码用
	 * 
	 * @param accountNo
	 * @param newMD5
	 * @return
	 * @throws UPPException
	 */
	public String modifyPayPassword(String accountNo, String newMD5)
			throws UPPException;

	/**
	 * 支付密码是否正确, 如果当天连续输入三次，则账户会被锁定
	 * 
	 * @param accountNo
	 * @param newMD5
	 * @return
	 * @throws UPPException
	 */
	public boolean isPayPassword(String accountNo, String MD5)
			throws UPPException;

	/**
	 * 是否设置过支付密码
	 * 
	 * @param accountNo
	 * @return
	 * @throws UPPException
	 */
	public boolean isSetPayPassword(String accountNo) throws UPPException;

	/**
	 * 锁定账户
	 * 
	 * @param accountNo
	 * @param lockReasons
	 * @return
	 * @throws UPPException
	 */
	public void lockAccount(String accountNo, String lockReasons)
			throws UPPException;

	/**
	 * 解锁账户
	 * 
	 * @param accountNo
	 * @return
	 * @throws UPPException
	 */
	public void unLockAccount(String accountNo) throws UPPException;

	/**
	 * 注销账户
	 * 
	 * @param accountNo
	 * @return
	 * @throws UPPException
	 */
	public void revokeAccount(String accountNo) throws UPPException;
	/**
	 * 回恢账户状态
	 * 
	 * @param accountNo
	 * @return
	 * @throws UPPException
	 */
	public void recoveryAccount(String accountNo) throws UPPException;
	
	/**
	 * 处理账户数据，主要是旧数据
	 * @param list
	 * @return
	 * @throws UPPException
	 */
	public List<AccountDto> handleAccountData(List<AccountDto> list)throws UPPException;

	// /////////////////账户流水/////////////

	/***
	 * 根据账号查询账户流水信息
	 * 
	 * @param 根据条件统计账户流水数量
	 *      
	 * @return 账户流水数量
	 * @throws UPPException
	 */
	public int countAccountDetail(
			DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 根据条件统计账户条数
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public int countOfAccount(DynamicSqlParameter requestParam) throws UPPException;
	/***
	 * 根据账号查询账户流水信息
	 * 
	 * @param accountNo
	 *            账号
	 * @return
	 * @throws UPPException
	 */
	public PaginationResult<AccountDetailDto> queryAccountDetailByPage(
			DynamicSqlParameter requestParam) throws UPPException;

	/***
	 * 根据账号查询账户流水分页信息
	 * 
	 * @param accountNo
	 *            账号
	 * @return
	 * @throws UPPException
	 */
	public List<AccountDetailDto> queryAccountDetailList(
			DynamicSqlParameter requestParam) throws UPPException;

	/**
	 * 根据主键查询流水详细信息
	 * 
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	public AccountDetailDto getAccountDetailById(String id) throws UPPException;

}
