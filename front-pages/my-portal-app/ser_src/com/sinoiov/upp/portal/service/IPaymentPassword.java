package com.sinoiov.upp.portal.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctfo.upp.exception.UPPException;

public interface IPaymentPassword {
	/**
	 * 修改账号支付密码
	 * @param map
	 * @return
	 * @throws UPPException
	 */
	public String changePayPwd(Map<String,String> map) throws UPPException;
	/**
	 * 设置账号支付密码
	 * @param map
	 * @return
	 * @throws UPPException
	 */
	public String setPayPwd(Map<String,String> map) throws UPPException;
	/**
	 * 验证身份信息
	 * @param map
	 * @return
	 * @throws UPPException
	 */
	public String validate(Map<String,String> map,HttpServletRequest request) throws UPPException;
	/**
	 * 修改绑定手机号
	 * @param map
	 * @return
	 * @throws UPPException
	 */
	public String updateMobileNoByAccountNo(Map<String,String> map) throws UPPException;
	/**
	 * 获取密保信息
	 * @param map
	 * @return
	 * @throws UPPException
	 */
	public String getCryptoguard(Map<String,String> map) throws UPPException;
	/**
	 * 获取密保问题码表
	 * @param map
	 * @return
	 * @throws UPPException
	 */
	public Map<String, String> getCryptoguardQueryCode() throws UPPException;
	/**
	 * 修改密码保护问题
	 * @param map
	 * @return
	 * @throws UPPException
	 */
	public String changeTryptoguard(Map<String,String> map) throws UPPException;
	/**
	 * 添加密码保护问题
	 * @param map
	 * @return
	 * @throws UPPException
	 */
	public String addTryptoguard(Map<String,String> map) throws UPPException;
	
}
