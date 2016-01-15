package com.sinoiov.upp.business.account;

import com.ctfo.account.dao.beans.AccountSafetyMess;
import com.ctfo.upp.exception.UPPException;

public interface IAccountSafetyBusiness {

	/**
	 * 保存账户安全信息
	 * 
	 * @param safetyMess
	 * @return
	 * @throws UPPException
	 */
	String createAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException;

	/**
	 * 修改账户安全信息
	 * 
	 * @param safetyMess
	 * @return
	 * @throws UPPException
	 */
	String modifyAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException;

	/**
	 * 重置
	 * 
	 * @param safetyMess
	 * @return
	 * @throws UPPException
	 */

	boolean resetAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException;

	/**
	 * 验证账户安全信息
	 * 
	 * @param safetyMess
	 * @return
	 * @throws UPPException
	 */
	boolean checkAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException;
	
	/**
	 * 根据账号查询账户安全问题
	 * @param accountNo
	 * @return
	 * @throws UPPException
	 */
	AccountSafetyMess getSecurityQuestionByAccountId(String accountId)throws UPPException;

}
