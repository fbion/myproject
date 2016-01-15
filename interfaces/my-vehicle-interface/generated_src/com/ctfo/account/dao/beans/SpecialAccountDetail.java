package com.ctfo.account.dao.beans;

import com.ctfo.upp.models.BaseSerializable;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class SpecialAccountDetail extends BaseSerializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ID DB Comment: 
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "", required = false)
	private String id;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_EXTERNAL_NO DB Comment: 内部交易流水，唯一标识
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "内部交易流水，唯一标识", required = false)
	private String insideExternalNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_TYPE DB Comment: RECORDED:入账，DEDUCTION : 出账，FROZEN：冻结，UNFROZEN：解冻
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "RECORDED:入账，DEDUCTION : 出账，FROZEN：冻结，UNFROZEN：解冻", required = false)
	private String bookaccountType;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_ACCOUNT_NO DB Comment: 对应的内部账号
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "对应的内部账号", required = false)
	private String insideAccountNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBJECT DB Comment: 包括：充值、付款、提现、手工调账等
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "包括：充值、付款、提现、手工调账等", required = false)
	private String accountSubject;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_TIME DB Comment: 记账时间
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "记账时间", required = false)
	private Long accountTime;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_MONEY DB Comment: 记账交易金额
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "记账交易金额", required = false)
	private Long accountMoney;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_DATE DB Comment: 账期，该记账对应的日期
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "账期，该记账对应的日期", required = false)
	private String accountDate;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_STATUS DB Comment: 记账状态，成功、失败
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "记账状态，成功、失败", required = false)
	private String bookaccountStatus;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.TRADE_EXTERNAL_NO DB Comment: 对应内部交易流水号
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "对应内部交易流水号", required = false)
	private String tradeExternalNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_CHANNEL DB Comment: 如果该账户为外部账户，该字段不能为空，渠道表示该外部账户是在哪个第三方支付平台开户的
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "如果该账户为外部账户，该字段不能为空，渠道表示该外部账户是在哪个第三方支付平台开户的", required = false)
	private String externalAccountChannel;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_NO DB Comment: 如果该账户为外部账户，该字段不能为空，表示在第三方支付平台的账号
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "如果该账户为外部账户，该字段不能为空，表示在第三方支付平台的账号", required = false)
	private String externalAccountNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.SUB_ACCOUNT_ID DB Comment: 对应的子账户ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "对应的子账户ID", required = false)
	private String subAccountId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_ID DB Comment: 对应的账户ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "对应的账户ID", required = false)
	private String accountId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.OPERATOR_ID DB Comment: 操作人ID，只有在手工记账时才有
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "操作人ID，只有在手工记账时才有", required = false)
	private String operatorId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOK_ACCOUNT_INFO_ID DB Comment: 流水记录id,对应总的记账流水
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "流水记录id,对应总的记账流水", required = false)
	private String bookAccountInfoId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBAREA_TIME DB Comment: 记账分区时间
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "记账分区时间", required = false)
	private Date accountSubareaTime;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.STORE_CODE DB Comment: 商户编号
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "商户编号", required = false)
	private String storeCode;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.CURRENT_MONEY DB Comment: 交易后账户余额
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	@XmlElement(name = "交易后账户余额", required = false)
	private Long currentMoney;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ID
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ID
	 * @param id  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldId() {
		return "ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_EXTERNAL_NO
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_EXTERNAL_NO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getInsideExternalNo() {
		return insideExternalNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_EXTERNAL_NO
	 * @param insideExternalNo  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_EXTERNAL_NO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setInsideExternalNo(String insideExternalNo) {
		this.insideExternalNo = insideExternalNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldInsideExternalNo() {
		return "INSIDE_EXTERNAL_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_TYPE
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_TYPE
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getBookaccountType() {
		return bookaccountType;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_TYPE
	 * @param bookaccountType  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_TYPE
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setBookaccountType(String bookaccountType) {
		this.bookaccountType = bookaccountType;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldBookaccountType() {
		return "BOOKACCOUNT_TYPE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_ACCOUNT_NO
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_ACCOUNT_NO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_ACCOUNT_NO
	 * @param insideAccountNo  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.INSIDE_ACCOUNT_NO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldInsideAccountNo() {
		return "INSIDE_ACCOUNT_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBJECT
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBJECT
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getAccountSubject() {
		return accountSubject;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBJECT
	 * @param accountSubject  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBJECT
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setAccountSubject(String accountSubject) {
		this.accountSubject = accountSubject;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldAccountSubject() {
		return "ACCOUNT_SUBJECT";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_TIME
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_TIME
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public Long getAccountTime() {
		return accountTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_TIME
	 * @param accountTime  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_TIME
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setAccountTime(Long accountTime) {
		this.accountTime = accountTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldAccountTime() {
		return "ACCOUNT_TIME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_MONEY
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_MONEY
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public Long getAccountMoney() {
		return accountMoney;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_MONEY
	 * @param accountMoney  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_MONEY
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setAccountMoney(Long accountMoney) {
		this.accountMoney = accountMoney;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldAccountMoney() {
		return "ACCOUNT_MONEY";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_DATE
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_DATE
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getAccountDate() {
		return accountDate;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_DATE
	 * @param accountDate  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_DATE
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldAccountDate() {
		return "ACCOUNT_DATE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_STATUS
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_STATUS
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getBookaccountStatus() {
		return bookaccountStatus;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_STATUS
	 * @param bookaccountStatus  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOKACCOUNT_STATUS
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setBookaccountStatus(String bookaccountStatus) {
		this.bookaccountStatus = bookaccountStatus;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldBookaccountStatus() {
		return "BOOKACCOUNT_STATUS";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.TRADE_EXTERNAL_NO
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.TRADE_EXTERNAL_NO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getTradeExternalNo() {
		return tradeExternalNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.TRADE_EXTERNAL_NO
	 * @param tradeExternalNo  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.TRADE_EXTERNAL_NO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setTradeExternalNo(String tradeExternalNo) {
		this.tradeExternalNo = tradeExternalNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldTradeExternalNo() {
		return "TRADE_EXTERNAL_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_CHANNEL
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_CHANNEL
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getExternalAccountChannel() {
		return externalAccountChannel;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_CHANNEL
	 * @param externalAccountChannel  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_CHANNEL
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setExternalAccountChannel(String externalAccountChannel) {
		this.externalAccountChannel = externalAccountChannel;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldExternalAccountChannel() {
		return "EXTERNAL_ACCOUNT_CHANNEL";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_NO
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_NO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getExternalAccountNo() {
		return externalAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_NO
	 * @param externalAccountNo  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.EXTERNAL_ACCOUNT_NO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setExternalAccountNo(String externalAccountNo) {
		this.externalAccountNo = externalAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldExternalAccountNo() {
		return "EXTERNAL_ACCOUNT_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.SUB_ACCOUNT_ID
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.SUB_ACCOUNT_ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getSubAccountId() {
		return subAccountId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.SUB_ACCOUNT_ID
	 * @param subAccountId  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.SUB_ACCOUNT_ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setSubAccountId(String subAccountId) {
		this.subAccountId = subAccountId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldSubAccountId() {
		return "SUB_ACCOUNT_ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_ID
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_ID
	 * @param accountId  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldAccountId() {
		return "ACCOUNT_ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.OPERATOR_ID
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.OPERATOR_ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getOperatorId() {
		return operatorId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.OPERATOR_ID
	 * @param operatorId  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.OPERATOR_ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldOperatorId() {
		return "OPERATOR_ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOK_ACCOUNT_INFO_ID
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOK_ACCOUNT_INFO_ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getBookAccountInfoId() {
		return bookAccountInfoId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOK_ACCOUNT_INFO_ID
	 * @param bookAccountInfoId  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.BOOK_ACCOUNT_INFO_ID
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setBookAccountInfoId(String bookAccountInfoId) {
		this.bookAccountInfoId = bookAccountInfoId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldBookAccountInfoId() {
		return "BOOK_ACCOUNT_INFO_ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBAREA_TIME
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBAREA_TIME
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public Date getAccountSubareaTime() {
		return accountSubareaTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBAREA_TIME
	 * @param accountSubareaTime  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.ACCOUNT_SUBAREA_TIME
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setAccountSubareaTime(Date accountSubareaTime) {
		this.accountSubareaTime = accountSubareaTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldAccountSubareaTime() {
		return "ACCOUNT_SUBAREA_TIME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.STORE_CODE
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.STORE_CODE
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public String getStoreCode() {
		return storeCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.STORE_CODE
	 * @param storeCode  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.STORE_CODE
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldStoreCode() {
		return "STORE_CODE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.CURRENT_MONEY
	 * @return  the value of UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.CURRENT_MONEY
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public Long getCurrentMoney() {
		return currentMoney;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.CURRENT_MONEY
	 * @param currentMoney  the value for UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO.CURRENT_MONEY
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public void setCurrentMoney(Long currentMoney) {
		this.currentMoney = currentMoney;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String fieldCurrentMoney() {
		return "CURRENT_MONEY";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public SpecialAccountDetail() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String tableName() {
		return "TB_UPP_SUB_BOOK_ACCOUNT_INFO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_BOOK_ACCOUNT_INFO
	 * @abatorgenerated  Fri Feb 06 20:03:18 CST 2015
	 */
	public static String daoInterface() {
		return "com.ctfo.upp.accountservice.account.dao.SpecialAccountDetailDAO";
	}
}