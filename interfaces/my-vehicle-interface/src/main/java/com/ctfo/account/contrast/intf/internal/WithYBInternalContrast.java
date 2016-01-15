package com.ctfo.account.contrast.intf.internal;

import java.util.Collection;
import java.util.List;

import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.upp.exception.UPPException;



public interface WithYBInternalContrast {
	/**
	 * 发送交易结束标识
	 * @return
	 * @throws UPPException
	 */
	public boolean sendLastTradeSign() throws UPPException;
	/**
	 * 发送交易结束标识
	 * @return
	 * @throws UPPException
	 */
	public boolean sendDateLastTradeSign(String dateString)throws UPPException;

	/**
	 * 对部分交易进行同步
	 * @param tradeList
	 * @param batchInsideNo
	 * @return
	 * @throws UPPException
	 */
	public List<PaymentTrade> transferBatchSynchronizingByPage(Collection<PaymentTrade> tradeList,Integer batchInsideNo) throws UPPException;
	/**
	 * 根据起止时间查询易宝平台成功且未同步的交易
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws UPPException
	 */
	public List<PaymentTrade> querySuccessUnsycTrades(Long startTime,Long endTime) throws UPPException;
}
