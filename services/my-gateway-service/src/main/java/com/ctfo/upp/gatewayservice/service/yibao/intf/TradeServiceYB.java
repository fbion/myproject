package com.ctfo.upp.gatewayservice.service.yibao.intf;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.gateway.bean.yibao.business.accountaccess.BankCardRechargeBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.BankCardRechargeBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeInfoBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeInfoBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeSyncBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeSyncBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TradeSyncBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TradeSyncBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TransactionBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TransactionBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawProcessBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawProcessBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.entrustsettle.WithDrawQuickBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.entrustsettle.WithDrawQuickBeanResponseYB;

public interface TradeServiceYB {
	
	public BankCardRechargeBeanResponseYB bankCardRecharge(BankCardRechargeBeanRequestYB bankCardRechargeBeanRequest) throws Exception;
	
	public WithDrawBeanResponseYB withDraw(WithDrawBeanRequestYB withDrawBeanRequest) throws Exception;
	
	public TransactionBeanResponseYB transacte(TransactionBeanRequestYB transactionBeanRequest) throws Exception;

	public RechargeSyncBeanResponseYB rechargeSync(RechargeSyncBeanRequestYB rechargeSyncBeanRequest) throws Exception;
	
	public RechargeInfoBeanResponseYB rechargeInfo(RechargeInfoBeanRequestYB rechargeInfoBeanRequest) throws Exception;
	
	public WithDrawProcessBeanResponseYB withDrawProcess(WithDrawProcessBeanRequestYB withDrawProcessBeanRequest) throws Exception;
	/**
	 * 委托结算提现
	 * @param withDrawQuickBeanRequestYB
	 * @return
	 * @throws Exception
	 */
	public WithDrawQuickBeanResponseYB withdrawQuick(WithDrawQuickBeanRequestYB withDrawQuickBeanRequestYB) throws Exception;
	
	public TradeSyncBeanResponseYB tradeSync(TradeSyncBeanRequestYB tradeSyncBeanRequestYB) throws Exception;
	/**
	 * 查询账户信息
	 * @param requestBean
	 * @return
	 * @throws Exception
	 */
	ResponseBean accountBalanceInquiryHandle(RequestBean requestBean)
			throws Exception;
}
