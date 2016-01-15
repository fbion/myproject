package com.ctfo.account.contrast.intf.external;

import java.util.Collection;
import java.util.List;

import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.mgbeans.SyncTradeFailRecord;


/**
 * 交易同步
 * @author majingyuan
 *
 */
public interface WithYBContrast extends WithThirdPartyPaymentContrast {
	public /*return model*/ String withYBContrastCallback() throws UPPException;
	
	/**
	 * 按天自动同步
	 * @return
	 * @throws UPPException
	 */
	public String transferSynchronizing()throws  UPPException;
	/**
	 * 传入起止时间进行交易同步
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws UPPException
	 */
	public SyncTradeFailRecord transferBatchSynchronizingByTime(Long startTime,Long endTime) throws UPPException;
		/**
	 * 对个别交易进行同步，根据交易号
	 * @param tradeNos
	 * @param serviceProviderCode
	 * @throws UPPException
	 */
	public List<PaymentTrade> transferBatchSynchronizingByPTradeNos(List<String> tradeNoList) throws UPPException;
	/**
	 * 发送最后一笔交易标识
	 * @param dateString
	 * @return
	 * @throws UPPException
	 */
	public boolean sendLastTradeSign(String dateString) throws UPPException;
	/**
	 * 处理失败的同步交易
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws UPPException
	 */
	public boolean dealWithFailSycTradeByTime(Long startTime, Long endTime)
			throws UPPException;
	/**
	 * 按交易流水号同步交易
	 * @param tradeNoList
	 * @return
	 * @throws UPPException
	 */
	public List<PaymentTrade> transferSynchronizingByPTradeNos(List<String> tradeNoList)
			throws UPPException;
}
