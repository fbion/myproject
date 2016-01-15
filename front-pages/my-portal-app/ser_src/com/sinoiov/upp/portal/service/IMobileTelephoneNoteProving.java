package com.sinoiov.upp.portal.service;

import com.ctfo.upp.exception.UPPException;

public interface IMobileTelephoneNoteProving {
	
	/**
	 * 获取手机短信验证码
	 * @param mobileNo
	 * @return
	 */
	public String getMobileTelephoneNoteProving(String mobileNo,String accountNo) throws UPPException;
	/**
	 * 获取手机短信验证码(不验证用户绑定手机)
	 * @param mobileNo
	 * @return
	 */
	public String getMobileTelephoneNoteProvingAgain(String mobileNo,String accountNo) throws UPPException;
}
