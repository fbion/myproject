package com.sinoiov.upp.portal.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;


/**
 * 账户服务
 * 
 * @author
 */
public interface AccountService {
	
	/**
	 * wjg
	 * 查找账户信息（加密处理）
	 * @param Map<String,String>
	 * @return Account
	 * @throws UPPException
	 */
	public Account findAccountInfo(Map<String,String> map) throws UPPException;
	/**
	 * wjg
	 * 查找账户信息（加密处理）
	 * @param Map<String,String>
	 * @return Account
	 * @throws UPPException
	 */
	public String quaryAccountCurrent(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * wjg
	 * 按用户id判断是否存在账户（加密处理）
	 * @param Map<String,String>
	 * @return Account
	 * @throws UPPException
	 */
	public Map<String,String> judgeAccount(Map<String,String> map) throws UPPException;
	/**
	 * wjg
	 * 按用户id开户（加密处理）
	 * @param Map<String,String>
	 * @return Account
	 * @throws UPPException
	 */
	public String openAnAccount(Map<String,String> map) throws UPPException;
	/**
	 * wjg
	 * 判断资金账户支付密码是否为初始状态
	 * @param accountNo  
	 * @return String smsCode
	 * @throws UPPException
	 */
	public String povringPayPassword(Map<String,String> map) throws UPPException;
	/**
	 * wjg
	 * 获取登录用户的用户信息
	 * @param accountNo  
	 * @return String smsCode
	 * @throws UPPException
	 */
	public String getUserMessage(HttpServletRequest request) throws UPPException;
	
	/**
	 * wjg
	 * 为查询条件赋值
	 * @param accountNo  
	 * @return String smsCode
	 * @throws UPPException
	 */
	public DynamicSqlParameter voluationParam(HttpServletRequest request) throws UPPException;
	/**
	 * wjg
	 * 通过ID修改备注
	 * @param accountNo  
	 * @return String smsCode
	 * @throws UPPException
	 */
	public String updateRemarksById(Map<String,String> map) throws UPPException;
	/**
	 * wjg
	 * 通过ID查找备注
	 * @param accountNo  
	 * @return String smsCode
	 * @throws UPPException
	 */
	public Map findRemarksById(String id) throws UPPException;
	/**
	 * wjg
	 * 验证是否设置密保问题
	 * @param accountNo  
	 * @return String smsCode
	 * @throws UPPException
	 */
	public Map<String,String> judgePaymentPwdQuery(Map<String,String> map) throws UPPException;
	
}
