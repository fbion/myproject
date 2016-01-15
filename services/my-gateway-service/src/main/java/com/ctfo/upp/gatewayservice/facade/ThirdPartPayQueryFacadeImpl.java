package com.ctfo.upp.gatewayservice.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountHistoryRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryRequestYB;
import com.ctfo.gateway.intf.ThirdPartPayQueryFacade;
import com.ctfo.upp.gatewayservice.service.yibao.intf.QueryTradeServiceYB;
@Service("thirdPartPayQueryFacade")
public class ThirdPartPayQueryFacadeImpl implements ThirdPartPayQueryFacade {
	@Autowired(required=false)
	private QueryTradeServiceYB queryTradeServiceYB;
	@Override
	public ResponseBean queryAccountInfo(RequestBean requestBean)
			throws Exception {
		if(requestBean instanceof QueryAccountInfoRequestYB){
			return queryTradeServiceYB.queryAccount((QueryAccountInfoRequestYB) requestBean);
		}else if(requestBean instanceof RechargeResultQueryRequestYB){
			return queryTradeServiceYB.rechargeResultQuery((RechargeResultQueryRequestYB)requestBean);
		}else if(requestBean instanceof QueryAccountHistoryRequestYB){
			return queryTradeServiceYB.queryAccountHistory((QueryAccountHistoryRequestYB)requestBean);
		}else{
			throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
		}
	}
}
