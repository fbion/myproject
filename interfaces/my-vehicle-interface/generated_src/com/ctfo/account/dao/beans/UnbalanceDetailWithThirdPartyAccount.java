package com.ctfo.account.dao.beans;

import com.ctfo.upp.models.BaseSerializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;

public class UnbalanceDetailWithThirdPartyAccount extends BaseSerializable {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ID
     * DB Comment: 唯一标识
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="唯一标识", required=false )
    private String id;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_ID
     * DB Comment: 支付交易ID
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="支付交易ID", required=false )
    private String accountId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_NO
     * DB Comment: 内部账户号
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="内部账户号", required=false )
    private String accountNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.CHECK_ACCOUNT_TIME
     * DB Comment: 对账时间
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="对账时间", required=false )
    private Long checkAccountTime;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_DATE
     * DB Comment: 对账日期
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="对账日期", required=false )
    private String accountDate;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.FAIL_REASON
     * DB Comment: 失败原因
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="失败原因", required=false )
    private String failReason;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ERROR_DESC
     * DB Comment: 错误描述
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="错误描述", required=false )
    private String errorDesc;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IS_DISPOSE
     * DB Comment: 对账差异是否已处理
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="对账差异是否已处理", required=false )
    private String isDispose;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TOTAL_BALANCE_DIVERGENCE
     * DB Comment: 
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="", required=false )
    private BigDecimal totalBalanceDivergence;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_AMOUNT_DIVERGENCE
     * DB Comment: 
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="", required=false )
    private BigDecimal rechargeAmountDivergence;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.Out_AMOUNT_DIVERGENCE
     * DB Comment: 
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="", required=false )
    private BigDecimal outAmountDivergence;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IN_AMOUNT_DIVERGENCE
     * DB Comment: 
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="", required=false )
    private BigDecimal inAmountDivergence;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_REFUNT_AMOUNT_DIVERGE
     * DB Comment: 
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="", required=false )
    private BigDecimal rechargeRefuntAmountDiverge;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TAKE_CASH_AMOUNT_DIVERGENCE
     * DB Comment: 
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="", required=false )
    private BigDecimal takeCashAmountDivergence;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.REMARK
     * DB Comment: 
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    @XmlElement(name="", required=false )
    private String remark;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ID
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ID
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ID
     *
     * @param id the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ID
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldId() {
        return "ID";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_ID
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_ID
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_ID
     *
     * @param accountId the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_ID
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldAccountId() {
        return "ACCOUNT_ID";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_NO
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_NO
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_NO
     *
     * @param accountNo the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_NO
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldAccountNo() {
        return "ACCOUNT_NO";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.CHECK_ACCOUNT_TIME
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.CHECK_ACCOUNT_TIME
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public Long getCheckAccountTime() {
        return checkAccountTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.CHECK_ACCOUNT_TIME
     *
     * @param checkAccountTime the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.CHECK_ACCOUNT_TIME
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setCheckAccountTime(Long checkAccountTime) {
        this.checkAccountTime = checkAccountTime;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldCheckAccountTime() {
        return "CHECK_ACCOUNT_TIME";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_DATE
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_DATE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getAccountDate() {
        return accountDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_DATE
     *
     * @param accountDate the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ACCOUNT_DATE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldAccountDate() {
        return "ACCOUNT_DATE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.FAIL_REASON
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.FAIL_REASON
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getFailReason() {
        return failReason;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.FAIL_REASON
     *
     * @param failReason the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.FAIL_REASON
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldFailReason() {
        return "FAIL_REASON";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ERROR_DESC
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ERROR_DESC
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ERROR_DESC
     *
     * @param errorDesc the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.ERROR_DESC
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldErrorDesc() {
        return "ERROR_DESC";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IS_DISPOSE
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IS_DISPOSE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getIsDispose() {
        return isDispose;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IS_DISPOSE
     *
     * @param isDispose the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IS_DISPOSE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setIsDispose(String isDispose) {
        this.isDispose = isDispose;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldIsDispose() {
        return "IS_DISPOSE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TOTAL_BALANCE_DIVERGENCE
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TOTAL_BALANCE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public BigDecimal getTotalBalanceDivergence() {
        return totalBalanceDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TOTAL_BALANCE_DIVERGENCE
     *
     * @param totalBalanceDivergence the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TOTAL_BALANCE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setTotalBalanceDivergence(BigDecimal totalBalanceDivergence) {
        this.totalBalanceDivergence = totalBalanceDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldTotalBalanceDivergence() {
        return "TOTAL_BALANCE_DIVERGENCE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_AMOUNT_DIVERGENCE
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_AMOUNT_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public BigDecimal getRechargeAmountDivergence() {
        return rechargeAmountDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_AMOUNT_DIVERGENCE
     *
     * @param rechargeAmountDivergence the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_AMOUNT_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setRechargeAmountDivergence(BigDecimal rechargeAmountDivergence) {
        this.rechargeAmountDivergence = rechargeAmountDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldRechargeAmountDivergence() {
        return "RECHARGE_AMOUNT_DIVERGENCE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.Out_AMOUNT_DIVERGENCE
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.Out_AMOUNT_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public BigDecimal getOutAmountDivergence() {
        return outAmountDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.Out_AMOUNT_DIVERGENCE
     *
     * @param outAmountDivergence the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.Out_AMOUNT_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setOutAmountDivergence(BigDecimal outAmountDivergence) {
        this.outAmountDivergence = outAmountDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldOutAmountDivergence() {
        return "Out_AMOUNT_DIVERGENCE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IN_AMOUNT_DIVERGENCE
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IN_AMOUNT_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public BigDecimal getInAmountDivergence() {
        return inAmountDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IN_AMOUNT_DIVERGENCE
     *
     * @param inAmountDivergence the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.IN_AMOUNT_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setInAmountDivergence(BigDecimal inAmountDivergence) {
        this.inAmountDivergence = inAmountDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldInAmountDivergence() {
        return "IN_AMOUNT_DIVERGENCE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_REFUNT_AMOUNT_DIVERGE
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_REFUNT_AMOUNT_DIVERGE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public BigDecimal getRechargeRefuntAmountDiverge() {
        return rechargeRefuntAmountDiverge;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_REFUNT_AMOUNT_DIVERGE
     *
     * @param rechargeRefuntAmountDiverge the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.RECHARGE_REFUNT_AMOUNT_DIVERGE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setRechargeRefuntAmountDiverge(BigDecimal rechargeRefuntAmountDiverge) {
        this.rechargeRefuntAmountDiverge = rechargeRefuntAmountDiverge;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldRechargeRefuntAmountDiverge() {
        return "RECHARGE_REFUNT_AMOUNT_DIVERGE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TAKE_CASH_AMOUNT_DIVERGENCE
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TAKE_CASH_AMOUNT_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public BigDecimal getTakeCashAmountDivergence() {
        return takeCashAmountDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TAKE_CASH_AMOUNT_DIVERGENCE
     *
     * @param takeCashAmountDivergence the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.TAKE_CASH_AMOUNT_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setTakeCashAmountDivergence(BigDecimal takeCashAmountDivergence) {
        this.takeCashAmountDivergence = takeCashAmountDivergence;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldTakeCashAmountDivergence() {
        return "TAKE_CASH_AMOUNT_DIVERGENCE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.REMARK
     *
     * @return the value of UPP.TB_UPP_CHECK_ACC_DIVERGENCE.REMARK
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column UPP.TB_UPP_CHECK_ACC_DIVERGENCE.REMARK
     *
     * @param remark the value for UPP.TB_UPP_CHECK_ACC_DIVERGENCE.REMARK
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String fieldRemark() {
        return "REMARK";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public UnbalanceDetailWithThirdPartyAccount() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String tableName() {
        return "TB_UPP_CHECK_ACC_DIVERGENCE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public static String daoInterface() {
        return "com.ctfo.upp.accountservice.account.dao.UnbalanceDetailWithThirdPartyAccountDAO";
    }
}