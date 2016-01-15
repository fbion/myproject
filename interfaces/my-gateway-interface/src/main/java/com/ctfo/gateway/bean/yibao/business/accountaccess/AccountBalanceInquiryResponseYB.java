package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.ResponseBean;

public class AccountBalanceInquiryResponseYB extends ResponseBean implements Serializable {

	private String serviceProviderCode;  //支付渠道标识

	private String queryType;	

	private String insideAccountNo;		//内部账号
	
	private String externalAccountNo;		//外部账号
	
	private Long queryTime;				//查询时间
	
	private boolean isFlag;				//查询结果
	
	private Long balanceAmount;			//账户余额
	
	private String accountStatus;		//账户状态
	
	private String errorMessage;

	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	public String getExternalAccountNo() {
		return externalAccountNo;
	}

	public void setExternalAccountNo(String externalAccountNo) {
		this.externalAccountNo = externalAccountNo;
	}

	public Long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Long queryTime) {
		this.queryTime = queryTime;
	}

	public boolean isFlag() {
		return isFlag;
	}

	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Long balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
}
