package com.sinoiov.upp.manager.account;

import com.ctfo.account.dao.beans.AccountSafetyMess;
import com.ctfo.upp.exception.UPPException;

public interface IAccountSafetyManager {
	/**
	 * 修改账户安全信息
	 * 
	 * @param safetyMess
	 * @return
	 * @throws UPPException
	 */
	String updateAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException;

	/**
	 * 验证账户安全信息
	 * 
	 * @param safetyMess
	 * @return
	 * @throws UPPException
	 */
	String checkAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException;

	/**
	 * 保存账户安全信息
	 * 
	 * @param safetyMess
	 * @return
	 * @throws UPPException
	 */
	String saveAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException;

	/**
	 * 重置
	 * 
	 * @param safetyMess
	 * @return
	 * @throws UPPException
	 */

	String resetAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException;

	/**
	 * 根据账户Id查安全问题
	 * 
	 * @param accountId
	 * @return
	 * @throws UPPException
	 */
	AccountSafetyMess getAccountSafetyMessByAccountId(String accountId) throws UPPException;


}
