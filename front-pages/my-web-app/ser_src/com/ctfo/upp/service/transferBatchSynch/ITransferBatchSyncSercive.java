package com.ctfo.upp.service.transferBatchSynch;

import java.util.List;

import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dto.beans.YBTradeRecordContrastDto;
import com.ctfo.upp.exception.UPPException;



public interface ITransferBatchSyncSercive {
	/**
	 * 以交易流水同步
	 * @param list
	 */
	public List<PaymentTrade>  transferBatchSynchronizingByPTradeNos(String param) throws UPPException;
	/**
	 * 以交易时间同步
	 * @param list
	 */
	public void transferBatchSynchronizingByTime(String startTime,String endTime) throws UPPException;
	/**
	 * 发送最后一次交易标识
	 * @param list
	 */
	public void sendLastTradeSign(String sendLastTradeSign) throws UPPException;
	/**
	 * 账户同步
	 * @param list
	 */
	public void accountReconciliationByTime(String startTime,String endTime,String numberSign) throws UPPException;
	
	public boolean unlockThirdPartPayAccount(String insideAccountNo)
			throws UPPException;
	public QueryAccountInfoResponseYB queryThirdPartPayAccount(String insideAccountNo)
			throws UPPException;
	public List<YBTradeRecordContrastDto> queryAccountHistory(String accountNo,
			Long startTime, Long endTime) throws UPPException;
}
