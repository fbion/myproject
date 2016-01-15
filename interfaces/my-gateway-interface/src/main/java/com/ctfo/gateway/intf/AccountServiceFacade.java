package com.ctfo.gateway.intf;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.upp.exception.UPPException;


public interface AccountServiceFacade {
	/**
	 * 创建账户
	 * @param CreateAccountBeanRequestYB
	 * @return CreateAccountBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean createAccount(RequestBean requestBean) throws UPPException;
	/**
	 * 锁定账户
	 * @param LockAccountBeanRequestYB
	 * @return LockAccountBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean lockAccount(RequestBean requestBean) throws UPPException;
	/**
	 * 解锁账户
	 * @param UnlockAccountBeanRequestYB
	 * @return UnlockAccountBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean unlockAccount(RequestBean requestBean) throws UPPException;
	/**
	 * 实名认证账户
	 * @param CertifyAccountBeanRequestYB
	 * @return CertifyAccountBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean certifyAccount(RequestBean requestBean) throws UPPException;
	/**
	 * 手工设置账户的认证状态
	 * @param SetAccountCertifyBeanRequestYB
	 * @return SetAccountCertifyBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean setAccountCertify(RequestBean requestBean) throws UPPException;
}
