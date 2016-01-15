package com.ctfo.sinoiov.mobile.webapi.bean.request;

/**
 * 通过银行转账线下对充值进行充值申请
 * 
 * @author dxs
 */
public class AccountRechargeByTransferReq extends BaseBeanReq {
	private static final long serialVersionUID = 1L;

	/**
	 * 付款人标识
	 */
	private String payer;

	/**
	 * 付款人名字
	 */
	private String payerName;

	/**
	 * 付款账号
	 */
	private String payAccount;

	/**
	 * 收款人账号
	 */
	private String payee;

	/**
	 * 收款人名字
	 */
	private String payeeName;

	/**
	 * 汇款金额
	 */
	private String remittance;

	/**
	 * 付款日期
	 */
	private String payTime;

//	/**
//	 * 付款凭证
//	 */
//	private File payProof;

	/**
	 * 资金账户内部账号
	 */
	private String insideAccountNo;

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getRemittance() {
		return remittance;
	}

	public void setRemittance(String remittance) {
		this.remittance = remittance;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

}
