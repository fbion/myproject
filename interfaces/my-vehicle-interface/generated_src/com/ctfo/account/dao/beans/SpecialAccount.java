package com.ctfo.account.dao.beans;

import com.ctfo.upp.models.BaseSerializable;
import javax.xml.bind.annotation.XmlElement;

public class SpecialAccount extends BaseSerializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.ID DB Comment: 
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "", required = false)
	private String id;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.PARENT_ACCOUNT_ID DB Comment: 父账户ID,对应父账户
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "父账户ID,对应父账户", required = false)
	private String parentAccountId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.INSIDE_ACCOUNT_NO DB Comment: 用户在本系统内的账号
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "用户在本系统内的账号", required = false)
	private String insideAccountNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_TYPE DB Comment: 中交或车主货主
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "中交或车主货主", required = false)
	private String accountType;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.SERVICE_PROVIDER_CODE DB Comment: 
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "", required = false)
	private String serviceProviderCode;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.EXTERNAL_ACCOUNT_NO DB Comment: 如果该账户为外部账户，该字段不能为空，表示在第三方支付平台的账号
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "如果该账户为外部账户，该字段不能为空，表示在第三方支付平台的账号", required = false)
	private String externalAccountNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_STATUS DB Comment: 0：表示不可以，1：表示可用
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "0：表示不可以，1：表示可用", required = false)
	private String accountStatus;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.TOTAL_BALANCE DB Comment: 总余额
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "总余额", required = false)
	private Long totalBalance;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.UNABLE_TAKECASH_BALANCE DB Comment: 可提现金额=可用金额-不可提现金额
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "可提现金额=可用金额-不可提现金额", required = false)
	private Long unableTakecashBalance;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.FROZEN_BALANCE DB Comment: 用户被冻结的金额，改金额不可提现，不可做交易
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "用户被冻结的金额，改金额不可提现，不可做交易", required = false)
	private Long frozenBalance;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.USABLE_BALANCE DB Comment: 可用金额=总余额-冻结金额
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "可用金额=总余额-冻结金额", required = false)
	private Long usableBalance;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.CREATE_TIME DB Comment: 
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "", required = false)
	private Long createTime;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_ACCOUNT.VERSION DB Comment: 用于乐观锁的相关操作
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	@XmlElement(name = "用于乐观锁的相关操作", required = false)
	private Integer version;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.ID
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.ID
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.ID
	 * @param id  the value for UPP.TB_UPP_SUB_ACCOUNT.ID
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldId() {
		return "ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.PARENT_ACCOUNT_ID
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.PARENT_ACCOUNT_ID
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getParentAccountId() {
		return parentAccountId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.PARENT_ACCOUNT_ID
	 * @param parentAccountId  the value for UPP.TB_UPP_SUB_ACCOUNT.PARENT_ACCOUNT_ID
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setParentAccountId(String parentAccountId) {
		this.parentAccountId = parentAccountId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldParentAccountId() {
		return "PARENT_ACCOUNT_ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.INSIDE_ACCOUNT_NO
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.INSIDE_ACCOUNT_NO
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.INSIDE_ACCOUNT_NO
	 * @param insideAccountNo  the value for UPP.TB_UPP_SUB_ACCOUNT.INSIDE_ACCOUNT_NO
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldInsideAccountNo() {
		return "INSIDE_ACCOUNT_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_TYPE
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_TYPE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_TYPE
	 * @param accountType  the value for UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_TYPE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldAccountType() {
		return "ACCOUNT_TYPE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.SERVICE_PROVIDER_CODE
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.SERVICE_PROVIDER_CODE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.SERVICE_PROVIDER_CODE
	 * @param serviceProviderCode  the value for UPP.TB_UPP_SUB_ACCOUNT.SERVICE_PROVIDER_CODE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldServiceProviderCode() {
		return "SERVICE_PROVIDER_CODE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.EXTERNAL_ACCOUNT_NO
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.EXTERNAL_ACCOUNT_NO
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getExternalAccountNo() {
		return externalAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.EXTERNAL_ACCOUNT_NO
	 * @param externalAccountNo  the value for UPP.TB_UPP_SUB_ACCOUNT.EXTERNAL_ACCOUNT_NO
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setExternalAccountNo(String externalAccountNo) {
		this.externalAccountNo = externalAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldExternalAccountNo() {
		return "EXTERNAL_ACCOUNT_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_STATUS
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_STATUS
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_STATUS
	 * @param accountStatus  the value for UPP.TB_UPP_SUB_ACCOUNT.ACCOUNT_STATUS
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldAccountStatus() {
		return "ACCOUNT_STATUS";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.TOTAL_BALANCE
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.TOTAL_BALANCE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public Long getTotalBalance() {
		return totalBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.TOTAL_BALANCE
	 * @param totalBalance  the value for UPP.TB_UPP_SUB_ACCOUNT.TOTAL_BALANCE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setTotalBalance(Long totalBalance) {
		this.totalBalance = totalBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldTotalBalance() {
		return "TOTAL_BALANCE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.UNABLE_TAKECASH_BALANCE
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.UNABLE_TAKECASH_BALANCE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public Long getUnableTakecashBalance() {
		return unableTakecashBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.UNABLE_TAKECASH_BALANCE
	 * @param unableTakecashBalance  the value for UPP.TB_UPP_SUB_ACCOUNT.UNABLE_TAKECASH_BALANCE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setUnableTakecashBalance(Long unableTakecashBalance) {
		this.unableTakecashBalance = unableTakecashBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldUnableTakecashBalance() {
		return "UNABLE_TAKECASH_BALANCE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.FROZEN_BALANCE
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.FROZEN_BALANCE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public Long getFrozenBalance() {
		return frozenBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.FROZEN_BALANCE
	 * @param frozenBalance  the value for UPP.TB_UPP_SUB_ACCOUNT.FROZEN_BALANCE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setFrozenBalance(Long frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldFrozenBalance() {
		return "FROZEN_BALANCE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.USABLE_BALANCE
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.USABLE_BALANCE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public Long getUsableBalance() {
		return usableBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.USABLE_BALANCE
	 * @param usableBalance  the value for UPP.TB_UPP_SUB_ACCOUNT.USABLE_BALANCE
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setUsableBalance(Long usableBalance) {
		this.usableBalance = usableBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldUsableBalance() {
		return "USABLE_BALANCE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.CREATE_TIME
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.CREATE_TIME
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.CREATE_TIME
	 * @param createTime  the value for UPP.TB_UPP_SUB_ACCOUNT.CREATE_TIME
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldCreateTime() {
		return "CREATE_TIME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_ACCOUNT.VERSION
	 * @return  the value of UPP.TB_UPP_SUB_ACCOUNT.VERSION
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_ACCOUNT.VERSION
	 * @param version  the value for UPP.TB_UPP_SUB_ACCOUNT.VERSION
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String fieldVersion() {
		return "VERSION";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public SpecialAccount() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String tableName() {
		return "TB_UPP_SUB_ACCOUNT";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String daoInterface() {
		return "com.ctfo.upp.accountservice.account.dao.SpecialAccountDAO";
	}
}