package com.ctfo.gateway.bean.yibao.business.entrustsettle;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;

public class WithDrawQuickBeanRequestYB extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 468968273851637085L;
	
	//批次号
	@Necessary
	private String batchNo;
	//订单号
	@Necessary
	private String orderId;
	//银行编码
	@Necessary
	private String bankCode;
	//支行名称
	private String branchBankName;
	//金额
	@Necessary
	private String amount;
	//打款账户名称
	@Necessary
	private String accountName;
	//打款账户号码
	@Necessary
	private String accountNumber;
	//打款账户所在省
	private String province;
	//打款账户所在市
	private String city;
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
