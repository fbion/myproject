package com.ctfo.upp.gatewayservice.service.yibao.intf;

import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountHistoryRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountHistoryResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryResponseYB;

public interface QueryTradeServiceYB {
	
	/**
	 * 商户通过此接口查询商户监管账户信息或者用户监管账户信息
	 * @param queryAccountInfoRequestYB
	 * @return QueryAccountInfoResponseYB
	 * @throws Exception
	 */
	public QueryAccountInfoResponseYB queryAccount(QueryAccountInfoRequestYB queryAccountInfoRequestYB)throws Exception;
	/**
	 * 查询充值结果
	 * @param rechargeResultQueryRequestYB
	 * @return
	 * @throws Exception
	 */
	public RechargeResultQueryResponseYB rechargeResultQuery(RechargeResultQueryRequestYB rechargeResultQueryRequestYB)throws Exception;
	/**
	 * 查询账务历史
	 * @return
	 */
	public QueryAccountHistoryResponseYB queryAccountHistory(QueryAccountHistoryRequestYB queryAccountHistoryRequestYB)throws Exception;
}
