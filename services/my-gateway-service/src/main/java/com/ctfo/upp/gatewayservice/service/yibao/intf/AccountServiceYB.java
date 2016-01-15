package com.ctfo.upp.gatewayservice.service.yibao.intf;

import com.ctfo.gateway.bean.yibao.business.accountaccess.CertifyAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CertifyAccountBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CreateAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CreateAccountBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.LockAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.LockAccountBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.SetAccountCertifyBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.SetAccountCertifyBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.UnlockAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.UnlockAccountBeanResponseYB;

public interface AccountServiceYB {
	
	public CreateAccountBeanResponseYB createAccount(CreateAccountBeanRequestYB createAccountBeanRequest) throws Exception;
	
	public LockAccountBeanResponseYB lockAccount(LockAccountBeanRequestYB lockAccountBeanRequest) throws Exception;
	
	public UnlockAccountBeanResponseYB unlockAccount(UnlockAccountBeanRequestYB unlockAccountBeanRequest) throws Exception;
	
	public CertifyAccountBeanResponseYB certifyAccount(CertifyAccountBeanRequestYB certifyAccountBeanRequest) throws Exception;
	
	public SetAccountCertifyBeanResponseYB setAccountCertify(SetAccountCertifyBeanRequestYB setAccountCertifyBeanRequest) throws Exception;
}
