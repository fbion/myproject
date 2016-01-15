package com.ctfo.account.intf.external;

import com.ctfo.upp.exception.UPPException;


public interface ICheckPwErrorTimes {
	
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
	 * 根据当前登录用户ID  查找对应的密码输入错误次数
	 * @param userId
	 * @return
	 * @throws UPPBusinessException
	 */
	public CheckPwErrorTimes getErrorTimesByUserId(String userId) throws UPPException;
	/**
	 * 删除超时的所有记录
	 * @throws UPPException
	 */
	public void removeAllTimeOut() throws UPPException;
	/**
	 * 更新操作
	 * @param checkPwErrorTimes
	 * @throws UPPException
	 */
	public void updateThis(CheckPwErrorTimes checkPwErrorTimes) throws UPPException;
	
}
