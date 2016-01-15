package com.ctfo.sinoiov.mobile.webapi.bean.response;

import java.io.Serializable;

import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.common.PublicStaticParam;

/**
 * 根据用户Id查询账户返回的账户对象
 * 
 * @author sunchuanfu
 */
public class GetAccountInfoByUserIdRsp implements Body, Serializable {
	private static final long serialVersionUID = 5291421130011262336L;

	private String accountNo;// 账户号码
	private String accountStatus;// 0:不可用 1:可用
	private String accountType;// 类型code
	private String createTime;// 创建时间
	private String frozenBalance = "0";// 用户被冻结的金额
	private String mobileNo;// 电话号码
	private String ownerLoginName;// 登录用户名
	private String ownerUserId;// 登录用户Id
	private String partShowIdcardNo;// 部分显示的用户身份证号
	private String totalBalance = "0";// 总余额
	private String unableTakecashBalance = "0";// 不可提现金额
	private String usableBalance = "0";// 可用金额
	private String paySumBalance = "0";// 累计支出
	private String incomeSumBalance = "0";// 累计收入

	// 是否有支付密码
	private String isSetPayPassword = PublicStaticParam.RESULT_FAIL;// 1(默认值)代表没有设置支付密码；0代表已设置

	// 是否设置安全问题
	private String isSetSecurityQuestion = PublicStaticParam.RESULT_FAIL;// 1(默认值)代表没有设置安全问题；0代表已设置

	// 账户安全等级
	// 默认值为0代表低级(支付密码和安全问题都没有设置)；如果值1代表中级(支付密码和安全问题其中一项没有设置)；
	// 值为2代表高级(支付密码和安全问题都已设置)
	private String accountSafetyLevel = "0";

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(String frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getOwnerLoginName() {
		return ownerLoginName;
	}

	public void setOwnerLoginName(String ownerLoginName) {
		this.ownerLoginName = ownerLoginName;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getPartShowIdcardNo() {
		return partShowIdcardNo;
	}

	public void setPartShowIdcardNo(String partShowIdcardNo) {
		this.partShowIdcardNo = partShowIdcardNo;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getUnableTakecashBalance() {
		return unableTakecashBalance;
	}

	public void setUnableTakecashBalance(String unableTakecashBalance) {
		this.unableTakecashBalance = unableTakecashBalance;
	}

	public String getUsableBalance() {
		return usableBalance;
	}

	public void setUsableBalance(String usableBalance) {
		this.usableBalance = usableBalance;
	}

	public String getIsSetPayPassword() {
		return isSetPayPassword;
	}

	public void setIsSetPayPassword(String isSetPayPassword) {
		this.isSetPayPassword = isSetPayPassword;
	}

	public String getPaySumBalance() {
		return paySumBalance;
	}

	public void setPaySumBalance(String paySumBalance) {
		this.paySumBalance = paySumBalance;
	}

	public String getIncomeSumBalance() {
		return incomeSumBalance;
	}

	public void setIncomeSumBalance(String incomeSumBalance) {
		this.incomeSumBalance = incomeSumBalance;
	}

	public String getAccountSafetyLevel() {
		return accountSafetyLevel;
	}

	public void setAccountSafetyLevel(String accountSafetyLevel) {
		this.accountSafetyLevel = accountSafetyLevel;
	}

	public String getIsSetSecurityQuestion() {
		return isSetSecurityQuestion;
	}

	public void setIsSetSecurityQuestion(String isSetSecurityQuestion) {
		this.isSetSecurityQuestion = isSetSecurityQuestion;
	}

}
