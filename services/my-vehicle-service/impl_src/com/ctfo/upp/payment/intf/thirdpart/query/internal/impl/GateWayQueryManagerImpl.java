package com.ctfo.upp.payment.intf.thirdpart.query.internal.impl;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.gateway.intf.ThirdPartPayQueryFacade;
import com.ctfo.gateway.intf.TradeServiceFacade;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.payment.intf.thirdpart.query.internal.GateWayQueryManager;
import com.ctfo.upp.soa.ServiceFactory;

//@Service("gateWayQueryManager")
public class GateWayQueryManagerImpl implements GateWayQueryManager{
	private static Log logger = LogFactory.getLog(GateWayQueryManagerImpl.class);
	@Override
	public String queryAccountInfo(String accountNo) throws UPPException {
		
		try {
			
			ThirdPartPayQueryFacade thirdPartPayQueryFacade= (ThirdPartPayQueryFacade) ServiceFactory.getFactory().getService(ThirdPartPayQueryFacade.class);
					
			QueryAccountInfoRequestYB requestBean= new QueryAccountInfoRequestYB();
			requestBean.setAccountNo(accountNo);
		
			QueryAccountInfoResponseYB res =(QueryAccountInfoResponseYB) thirdPartPayQueryFacade.queryAccountInfo(requestBean);
			
			return JSONArray.fromObject(res).toString();
		} catch (Exception e) {
			throw new UPPException(e.getLocalizedMessage());
		}
	}

}
