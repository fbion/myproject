package com.ctfo.account.balance;

import com.ctfo.upp.exception.UPPException;

/**
 * 
 * @author wjg
 *线下交易申请对账
 */
public interface IAppleOfPayorderBalance {
	/**
	 * 线下交易申请对账方法
	 */
	public void appleBanlanceOf() throws UPPException;
}
