package com.ctfo.upp.excelbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
/**
 * 中交账户交易流水EXCEL
 * @author lipeng01
 *
 */
public class ZJAccountTradExcel implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "exp(流水号;NOMAL_ENUM)imp", required = false)
	String tradeExternalNo;//TRADEEXTERNALNO
	
	@XmlElement(name = "exp(账户编码;NOMAL_ENUM)imp", required = false)
	String insideAccountNo;//INSIDEACCOUNTNO
	
	@XmlElement(name = "exp(账户所属;NOMAL_ENUM)imp", required = false)
	String ownerLoginName;//OWNERLOGINNAME
	
	@XmlElement(name = "exp(记账类型;NOMAL_ENUM)imp", required = false)
	String bookaccountType;//BOOKACCOUNTTYPE
	
	@XmlElement(name = "exp(科目;NOMAL_ENUM)imp", required = false)
	String accountSubject;//ACCOUNTSUBJECT
	
	@XmlElement(name = "exp(交易金额（元）;NOMAL_ENUM)imp", required = false)
	String accountMoney;//ACCOUNTMONEY
	
	@XmlElement(name = "exp(交易时间;NOMAL_ENUM)imp", required = false)
	String accountTime;//ACCOUNTTIME
	
	@XmlElement(name = "exp(交易后总余额（元）;NOMAL_ENUM)imp", required = false)
	String totalBalance;//TOTALBALANCE
	
	public String getTradeExternalNo() {
		return tradeExternalNo;
	}

	public void setTradeExternalNo(String tradeExternalNo) {
		this.tradeExternalNo = tradeExternalNo;
	}

	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	public String getOwnerLoginName() {
		return ownerLoginName;
	}

	public void setOwnerLoginName(String ownerLoginName) {
		this.ownerLoginName = ownerLoginName;
	}

	public String getBookaccountType() {
		return bookaccountType;
	}

	public void setBookaccountType(String bookaccountType) {
		this.bookaccountType = bookaccountType;
	}

	public String getAccountSubject() {
		return accountSubject;
	}

	public void setAccountSubject(String accountSubject) {
		this.accountSubject = accountSubject;
	}

	public String getAccountMoney() {
		return accountMoney;
	}

	public void setAccountMoney(String accountMoney) {
		this.accountMoney = accountMoney;
	}

	public String getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(String accountTime) {
		this.accountTime = accountTime;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}
	

}
