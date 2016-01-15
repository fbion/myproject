package com.ctfo.upp.gatewayservice.service.yibao.intf;

import com.ctfo.gateway.bean.yibao.business.accountaccess.CheckAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CheckAccountBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.FireClientNoticeBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.FireClientNoticeBeanResponseYB;

public interface AccountCheckingServiceYB {
	
	public CheckAccountBeanResponseYB checkAccount(CheckAccountBeanRequestYB checkAccountBeanRequest) throws Exception;

	public FireClientNoticeBeanResponseYB fireClientNotice(FireClientNoticeBeanRequestYB fireClientNoticeBeanRequest) throws Exception;
}
