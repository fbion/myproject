package com.ctfo.account.dao.beans;

import com.ctfo.upp.models.BaseSerializable;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class HAccount extends BaseSerializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ID DB Comment: 
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "", required = false)
	private String id;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.INSIDE_ACCOUNT_NO DB Comment: 
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "", required = false)
	private String insideAccountNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_TYPE DB Comment: 1：代表内部账户，只再第三方支付平台没有对应账户 2：外部账户，在第三方支付平台有对应的账户
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "1：代表内部账户，只再第三方支付平台没有对应账户2：外部账户，在第三方支付平台有对应的账户", required = false)
	private String accountType;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_ID DB Comment: 所属用户标识
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "所属用户标识", required = false)
	private String ownerUserId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_LOGIN_NAME DB Comment: 所属用户的中文名
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "所属用户的中文名", required = false)
	private String ownerLoginName;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_STATUS DB Comment: 0：表示不可以，1：表示可用
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "0：表示不可以，1：表示可用", required = false)
	private String accountStatus;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TOTAL_BALANCE DB Comment: 账户的总余额
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "账户的总余额", required = false)
	private Long totalBalance;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.UNABLE_TAKECASH_BALANCE DB Comment: 不可提现金额
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "不可提现金额", required = false)
	private Long unableTakecashBalance;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.FROZEN_BALANCE DB Comment: 用户被冻结的金额，改金额不可提现，不可做交易
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "用户被冻结的金额，改金额不可提现，不可做交易", required = false)
	private Long frozenBalance;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.USABLE_BALANCE DB Comment: 可用金额=总余额-冻结金额
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "可用金额=总余额-冻结金额", required = false)
	private Long usableBalance;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.CREATE_TIME DB Comment: 创建时间
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "创建时间", required = false)
	private Long createTime;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.VERSION DB Comment: 用于乐观锁的相关操作的支持
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "用于乐观锁的相关操作的支持", required = false)
	private Integer version;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.IS_OPER_MESS DB Comment: 是否开通短信通知
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "是否开通短信通知", required = false)
	private String isOperMess;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TRADE_EXTERNAL_NO DB Comment: 对应内部交易流水号
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "对应内部交易流水号", required = false)
	private String tradeExternalNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.MOBILE_NO DB Comment: 绑定手机号，用于下发支付验证码
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "绑定手机号，用于下发支付验证码", required = false)
	private String mobileNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PART_SHOW_IDCARD_NO DB Comment: 部分显示的身份证号
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "部分显示的身份证号", required = false)
	private String partShowIdcardNo;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_ID DB Comment: 对应账户的ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "对应账户的ID", required = false)
	private String accountId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PAY_PASSWORD DB Comment: 加密存放用户的支付密码，用于第三方支付使用
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "加密存放用户的支付密码，用于第三方支付使用", required = false)
	private String payPassword;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_TIME DB Comment: 历史生成时间
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "历史生成时间", required = false)
	private Long recordCreateTime;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_SUBAREA_TIME DB Comment: 历史生成分区时间
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "历史生成分区时间", required = false)
	private Date recordCreateSubareaTime;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OPER_ID DB Comment: 操作人ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "操作人ID", required = false)
	private String operId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_NO DB Comment: 
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	@XmlElement(name = "", required = false)
	private String ownerUserNo;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ID
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ID
	 * @param id  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldId() {
		return "ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.INSIDE_ACCOUNT_NO
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.INSIDE_ACCOUNT_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.INSIDE_ACCOUNT_NO
	 * @param insideAccountNo  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.INSIDE_ACCOUNT_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldInsideAccountNo() {
		return "INSIDE_ACCOUNT_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_TYPE
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_TYPE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_TYPE
	 * @param accountType  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_TYPE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldAccountType() {
		return "ACCOUNT_TYPE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_ID
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getOwnerUserId() {
		return ownerUserId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_ID
	 * @param ownerUserId  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldOwnerUserId() {
		return "OWNER_USER_ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_LOGIN_NAME
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_LOGIN_NAME
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getOwnerLoginName() {
		return ownerLoginName;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_LOGIN_NAME
	 * @param ownerLoginName  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_LOGIN_NAME
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setOwnerLoginName(String ownerLoginName) {
		this.ownerLoginName = ownerLoginName;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldOwnerLoginName() {
		return "OWNER_LOGIN_NAME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_STATUS
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_STATUS
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_STATUS
	 * @param accountStatus  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_STATUS
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldAccountStatus() {
		return "ACCOUNT_STATUS";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TOTAL_BALANCE
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TOTAL_BALANCE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public Long getTotalBalance() {
		return totalBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TOTAL_BALANCE
	 * @param totalBalance  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TOTAL_BALANCE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setTotalBalance(Long totalBalance) {
		this.totalBalance = totalBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldTotalBalance() {
		return "TOTAL_BALANCE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.UNABLE_TAKECASH_BALANCE
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.UNABLE_TAKECASH_BALANCE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public Long getUnableTakecashBalance() {
		return unableTakecashBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.UNABLE_TAKECASH_BALANCE
	 * @param unableTakecashBalance  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.UNABLE_TAKECASH_BALANCE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setUnableTakecashBalance(Long unableTakecashBalance) {
		this.unableTakecashBalance = unableTakecashBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldUnableTakecashBalance() {
		return "UNABLE_TAKECASH_BALANCE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.FROZEN_BALANCE
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.FROZEN_BALANCE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public Long getFrozenBalance() {
		return frozenBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.FROZEN_BALANCE
	 * @param frozenBalance  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.FROZEN_BALANCE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setFrozenBalance(Long frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldFrozenBalance() {
		return "FROZEN_BALANCE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.USABLE_BALANCE
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.USABLE_BALANCE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public Long getUsableBalance() {
		return usableBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.USABLE_BALANCE
	 * @param usableBalance  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.USABLE_BALANCE
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setUsableBalance(Long usableBalance) {
		this.usableBalance = usableBalance;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldUsableBalance() {
		return "USABLE_BALANCE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.CREATE_TIME
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.CREATE_TIME
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.CREATE_TIME
	 * @param createTime  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.CREATE_TIME
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldCreateTime() {
		return "CREATE_TIME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.VERSION
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.VERSION
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.VERSION
	 * @param version  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.VERSION
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldVersion() {
		return "VERSION";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.IS_OPER_MESS
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.IS_OPER_MESS
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getIsOperMess() {
		return isOperMess;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.IS_OPER_MESS
	 * @param isOperMess  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.IS_OPER_MESS
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setIsOperMess(String isOperMess) {
		this.isOperMess = isOperMess;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldIsOperMess() {
		return "IS_OPER_MESS";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TRADE_EXTERNAL_NO
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TRADE_EXTERNAL_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getTradeExternalNo() {
		return tradeExternalNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TRADE_EXTERNAL_NO
	 * @param tradeExternalNo  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.TRADE_EXTERNAL_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setTradeExternalNo(String tradeExternalNo) {
		this.tradeExternalNo = tradeExternalNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldTradeExternalNo() {
		return "TRADE_EXTERNAL_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.MOBILE_NO
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.MOBILE_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.MOBILE_NO
	 * @param mobileNo  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.MOBILE_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldMobileNo() {
		return "MOBILE_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PART_SHOW_IDCARD_NO
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PART_SHOW_IDCARD_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getPartShowIdcardNo() {
		return partShowIdcardNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PART_SHOW_IDCARD_NO
	 * @param partShowIdcardNo  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PART_SHOW_IDCARD_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setPartShowIdcardNo(String partShowIdcardNo) {
		this.partShowIdcardNo = partShowIdcardNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldPartShowIdcardNo() {
		return "PART_SHOW_IDCARD_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_ID
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_ID
	 * @param accountId  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.ACCOUNT_ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldAccountId() {
		return "ACCOUNT_ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PAY_PASSWORD
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PAY_PASSWORD
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getPayPassword() {
		return payPassword;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PAY_PASSWORD
	 * @param payPassword  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.PAY_PASSWORD
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldPayPassword() {
		return "PAY_PASSWORD";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_TIME
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_TIME
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public Long getRecordCreateTime() {
		return recordCreateTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_TIME
	 * @param recordCreateTime  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_TIME
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setRecordCreateTime(Long recordCreateTime) {
		this.recordCreateTime = recordCreateTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldRecordCreateTime() {
		return "RECORD_CREATE_TIME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_SUBAREA_TIME
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_SUBAREA_TIME
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public Date getRecordCreateSubareaTime() {
		return recordCreateSubareaTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_SUBAREA_TIME
	 * @param recordCreateSubareaTime  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.RECORD_CREATE_SUBAREA_TIME
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setRecordCreateSubareaTime(Date recordCreateSubareaTime) {
		this.recordCreateSubareaTime = recordCreateSubareaTime;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldRecordCreateSubareaTime() {
		return "RECORD_CREATE_SUBAREA_TIME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OPER_ID
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OPER_ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getOperId() {
		return operId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OPER_ID
	 * @param operId  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OPER_ID
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setOperId(String operId) {
		this.operId = operId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldOperId() {
		return "OPER_ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_NO
	 * @return  the value of UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public String getOwnerUserNo() {
		return ownerUserNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_NO
	 * @param ownerUserNo  the value for UPP.TB_UPP_CASH_ACCOUNT_HISTORY.OWNER_USER_NO
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public void setOwnerUserNo(String ownerUserNo) {
		this.ownerUserNo = ownerUserNo;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String fieldOwnerUserNo() {
		return "OWNER_USER_NO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public HAccount() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String tableName() {
		return "TB_UPP_CASH_ACCOUNT_HISTORY";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CASH_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Jul 20 16:52:08 CST 2015
	 */
	public static String daoInterface() {
		return "com.ctfo.upp.accountservice.account.dao.HAccountDAO";
	}
}