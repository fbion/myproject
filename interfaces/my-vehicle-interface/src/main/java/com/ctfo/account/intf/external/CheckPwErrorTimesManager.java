package com.ctfo.account.intf.external;

import com.ctfo.upp.exception.UPPException;


public interface CheckPwErrorTimesManager {

	/**
	 * 保存用户密码输入错误次数
	 * @param errorTimesBean
	 * @return uuid
	 * @throws UPPBusinessException
	 */
	public String saveErrorTimes(CheckPwErrorTimes errorTimesBean) throws UPPException;
	
	/**
	 * 根据当前登录用户ID 移除对应密码输入错误次数
	 * @param userId
	 * @return 
	 * @throws UPPBusinessException
	 */
	public String removeErrorTimesByUserId(String userId) throws UPPException;
	
	/**
	 * 根据当前登录用户ID  查找对应的密码输入错误次数对象
	 * @param userId
	 * @return
	 * @throws UPPBusinessException
	 */
	public CheckPwErrorTimes getErrorTimesByUserId(String userId) throws UPPException;
	/**
	 * 更新操作
	 * @param errorTimesBean
	 * @throws UPPBusinessException
	 */
	public void updateThis(CheckPwErrorTimes errorTimesBean) throws UPPException;
	/**
	 * 删除所有超时记录
	 * @throws UPPBusinessException
	 */
	public void removeAll() throws UPPException;
}
