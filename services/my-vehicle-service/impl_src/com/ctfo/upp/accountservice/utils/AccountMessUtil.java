package com.ctfo.upp.accountservice.utils;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.payment.intf.internal.IPaymentTransManager;
import com.ctfo.upp.dict.AccountDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;

/**
 * 计划用缓存实现内部账号和账户标识的转换
 * @author majingyuan
 *
 */
public class AccountMessUtil {

	
	/**
	 * 查询账户是否存在，状态是否正常
	 * @param account
	 * @throws UPPException
	 */
	public static void accountCheck(Account account)throws UPPException{
		if(account==null){
			UPPException e= new UPPException("账户未开户，未查到对应账户");
			e.setErrorCode(ReturnCodeDict.ACCOUNT_NOT_EXIST);
			throw e;
		}
		if(AccountDict.ACCOUNT_STATUS_LOCKED.equals(account.getAccountStatus())){
			UPPException e= new UPPException("账户已经被锁定");
			e.setErrorCode(ReturnCodeDict.ACCOUNT_STATUS_LOCKED);
			throw e;
		}
	}
}
