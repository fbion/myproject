package com.ctfo.upp.dto;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.upp.models.BaseSerializable;
/**
* 类描述：账户模型
* @author：liuguozhong  
* @date：2015年1月31日下午7:01:54 
* @version 1.0
* @since JDK1.6
 */
public class AccountDTO extends BaseSerializable {
	private static final long serialVersionUID = 1L;
	
	private Account account;
	private String serviceProviderCode;

	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
