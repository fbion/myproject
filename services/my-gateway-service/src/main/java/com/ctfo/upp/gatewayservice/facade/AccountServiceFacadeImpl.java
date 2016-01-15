package com.ctfo.upp.gatewayservice.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CertifyAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CreateAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.LockAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.SetAccountCertifyBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.UnlockAccountBeanRequestYB;
import com.ctfo.gateway.intf.AccountServiceFacade;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gatewayservice.service.yibao.intf.AccountServiceYB;

@Service("accountServiceFacade")
public class AccountServiceFacadeImpl extends FacadeSupport implements AccountServiceFacade {
	
	@Autowired(required=false)
	private AccountServiceYB accountServiceYB;
	
	@Override
	public ResponseBean createAccount(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof CreateAccountBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return accountServiceYB.createAccount((CreateAccountBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 创建账户发生错误  <<<<<<<<<<", e);
		}
	}

	@Override
	public ResponseBean lockAccount(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof LockAccountBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return accountServiceYB.lockAccount((LockAccountBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 锁定账户发生错误  <<<<<<<<<<", e);
		}
	}

	@Override
	public ResponseBean unlockAccount(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof UnlockAccountBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return accountServiceYB.unlockAccount((UnlockAccountBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 解锁账户发生错误  <<<<<<<<<<", e);
		}
	}

	@Override
	public ResponseBean certifyAccount(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof CertifyAccountBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return accountServiceYB.certifyAccount((CertifyAccountBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 认证账户发生错误  <<<<<<<<<<", e);
		}
	}

	@Override
	public ResponseBean setAccountCertify(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof SetAccountCertifyBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return accountServiceYB.setAccountCertify((SetAccountCertifyBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象发生错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 设置账户认证状态发生错误 <<<<<<<<<<", e);
		}
	}

	public AccountServiceYB getAccountServiceYB() {
		return accountServiceYB;
	}

	public void setAccountServiceYB(AccountServiceYB accountServiceYB) {
		this.accountServiceYB = accountServiceYB;
	}

}
