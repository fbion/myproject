package com.ctfo.upp.payment.intf.thirdpart.oper.internal.impl;

import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.UnlockAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.UnlockAccountBeanResponseYB;
import com.ctfo.gateway.intf.AccountServiceFacade;
import com.ctfo.upp.accountservice.utils.TradeNumberHelper;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.payment.intf.thirdpart.oper.internal.ThirdPartyPaymentDealWithManager;
import com.ctfo.upp.soa.ServiceFactory;

//@Service("thirdPartyPaymentDealWithManager")
public class ThirdPartyPaymentDealWithManagerImpl implements ThirdPartyPaymentDealWithManager{
	public boolean unlockThirdPartyAccount(String accountNo) throws UPPException{
		UnlockAccountBeanRequestYB request = new UnlockAccountBeanRequestYB();
		AccountServiceFacade 			accountServiceFacade = (AccountServiceFacade) ServiceFactory.getFactory().getService(AccountServiceFacade.class);
		request.setAccountId(accountNo);
		request.setAccountUnFreezeReason("同步交易");
		request.setRequestId(TradeNumberHelper.getTradeExternalNo());
		UnlockAccountBeanResponseYB response = (UnlockAccountBeanResponseYB)accountServiceFacade.unlockAccount(request);
		
		return response.isFlag();
	}
}
