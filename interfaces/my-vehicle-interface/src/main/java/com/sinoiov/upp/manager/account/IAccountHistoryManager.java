package com.sinoiov.upp.manager.account;

import java.util.List;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.HAccount;
import com.ctfo.account.dao.beans.HAccountExampleExtended;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;

public interface IAccountHistoryManager {
	
	/***
	 * 记录主账户历史, 此方法要配合消息处理方法(receiveHandleMQ)来完成
	 * 发消息到队列
	 * @param Account
	 *            主账户历史数据模型
	 */
	public void recordHistory(Account account);
	/**
	 * 处理监听到的消息
	 * @param account
	 * @throws UPPException
	 */
	public void receiveHandleMQ(Account account)throws UPPException;

	/**
	 * 根据条件查询账户历史集合信息
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public List<HAccount> queryAccountHistory(HAccountExampleExtended exampleExtended) throws UPPException;

	/**
	 * 根据条件查询账户历史分页信息
	 * @param exampleExtended
	 * @return
	 * @throws UPPException
	 */
	public PaginationResult<HAccount> queryAccountHistoryByPage(HAccountExampleExtended exampleExtended) throws UPPException;

	/**
	 * 修改账户历史，此方法仅用于旧数据的改造
	 * @param accountId
	 * @param ownerUserNo
	 * @return
	 * @throws UPPException
	 */
	public void updateAccountHistoryById(String accountId, String ownerUserNo)throws UPPException;
	
	
}
