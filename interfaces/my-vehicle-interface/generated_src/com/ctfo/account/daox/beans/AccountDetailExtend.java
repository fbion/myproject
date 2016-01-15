package com.ctfo.account.daox.beans;

import java.io.Serializable;

import com.ctfo.account.dao.beans.AccountDetail;

/***
 * 类描述：普通账户和中交账户交易流水SQL扩展Bean类
 * @author：liuguozhong
 * @date：2015年2月5日上午1:24:03
 * @version 1.0
 * @since JDK1.6
 */
public class AccountDetailExtend extends AccountDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 账户状态 */
	private String accountStatus;
	/** 账户总金额 */
	private Long totalBalance;
	/** 账户类型：0为中交；非0为普通 */
	private String accountType;
	/** 所属用户标识,UAA中的用户标识 */
	private String ownerUserId;
	/** 所属用户的中文名 */
	private String ownerLoginName;

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Long getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Long totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getOwnerLoginName() {
		return ownerLoginName;
	}

	public void setOwnerLoginName(String ownerLoginName) {
		this.ownerLoginName = ownerLoginName;
	}
}