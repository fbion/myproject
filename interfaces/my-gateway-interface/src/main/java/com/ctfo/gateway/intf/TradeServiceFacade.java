package com.ctfo.gateway.intf;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.gateway.bean.yibao.business.entrustsettle.WithDrawQuickBeanRequestYB;
import com.ctfo.upp.exception.UPPException;

public interface TradeServiceFacade {
	/**
	 * 转账
	 * @param TransactionBeanRequestYB
	 * @return TransactionBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean transaction(RequestBean requestBean) throws UPPException;
	/**
	 * 银行卡充值
	 * @param BankCardRechargeBeanRequestYB
	 * @return BankCardRechargeBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean bankCardRecharge(RequestBean requestBean) throws UPPException;
	/**
	 * 提现
	 * @param WithDrawBeanRequestYB
	 * @return WithDrawBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean withdraw(RequestBean requestBean) throws UPPException;
	/**
	 * 快速提现
	 * @param WithDrawQuickBeanRequestYB
	 * @return WithDrawQuickBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean withdrawQuick(RequestBean requestBean) throws UPPException;
	
	/**
	 * 充值交易同步
	 * @param RechargeSyncBeanRequestYB
	 * @return RechargeSyncBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean rechargeSync(RequestBean requestBean) throws UPPException;
	/**
	 * 查询提现进度
	 * @param WithDrawProcessBeanRequestYB
	 * @return WithDrawProcessBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean withDrawProcess(RequestBean requestBean) throws UPPException;
	/**
	 * 查询充值情况
	 * @param RechargeInfoBeanRequestYB
	 * @return RechargeInfoBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean rechargeInfo(RequestBean requestBean) throws UPPException;
	/**
	 * 交易批量同步
	 * @param TradeSyncBeanRequestYB
	 * @return TradeSyncBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean tradeSync(RequestBean requestBean) throws UPPException;
	
	/**
	 * 外部账户信息查询
	 * 由第三方支付维护的账户被称为外部账户
	 * @param TradeSyncBeanRequestYB
	 * @return TradeSyncBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean accountBalanceInquiry(RequestBean requestBean) throws UPPException;
}
