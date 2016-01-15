package com.ctfo.upp.excelbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
/**
 * 银行卡查询EXCEL
 * @author lipeng01
 *
 */
public class BankCardExcel implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "exp(账户编码;NOMAL_ENUM)imp", required = false)
	String accountId;//ACCOUNT_ID
	
	@XmlElement(name = "exp(账户所属;NOMAL_ENUM)imp", required = false)
	String storeName;//STORE_NAME
	
	@XmlElement(name = "exp(银行名称;NOMAL_ENUM)imp", required = false)
	String branchBankName;//BRANCH_BANK_NAME
	
	@XmlElement(name = "exp(所属地区;NOMAL_ENUM)imp", required = false)
	String branchBankProvince;//BRANCH_BANK_PROVINCE / BRANCH_BANK_CITY
	
	@XmlElement(name = "exp(银行卡账号;NOMAL_ENUM)imp", required = false)
	String partBankAccountNo;//PART_BANK_ACCOUNT_NO
	
	@XmlElement(name = "exp(是否为主卡;NOMAL_ENUM)imp", required = false)
	String isMainCard;//IS_MAIN_CARD
	
	@XmlElement(name = "exp(卡类型;NOMAL_ENUM)imp", required = false)
	String accCardType;//ACC_CARD_TYPE
	
	@XmlElement(name = "exp(身份证号;NOMAL_ENUM)imp", required = false)
	String idcardNo;//IDCARD_NO
	
	@XmlElement(name = "exp(创建时间;NOMAL_ENUM)imp", required = false)
	String createTime;//CREATE_TIME

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getBranchBankProvince() {
		return branchBankProvince;
	}

	public void setBranchBankProvince(String branchBankProvince) {
		this.branchBankProvince = branchBankProvince;
	}

	public String getPartBankAccountNo() {
		return partBankAccountNo;
	}

	public void setPartBankAccountNo(String partBankAccountNo) {
		this.partBankAccountNo = partBankAccountNo;
	}

	public String getIsMainCard() {
		return isMainCard;
	}

	public void setIsMainCard(String isMainCard) {
		this.isMainCard = isMainCard;
	}

	public String getAccCardType() {
		return accCardType;
	}

	public void setAccCardType(String accCardType) {
		this.accCardType = accCardType;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
