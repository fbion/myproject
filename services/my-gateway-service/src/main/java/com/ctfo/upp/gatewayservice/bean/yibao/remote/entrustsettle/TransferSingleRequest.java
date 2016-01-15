package com.ctfo.upp.gatewayservice.bean.yibao.remote.entrustsettle;

import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;

public class TransferSingleRequest {
	
	private String cmd;
	private String version;
	private String group_Id;
	private String mer_Id;
	private String product;
	private String batch_No;
	private String bank_Code;
	private String order_Id;
	private String cnaps;
	private String bank_Name;
	private String branch_Bank_Name;
	private String amount;
	private String account_Name;
	private String account_Number;
	private String account_Type;
	private String province;
	private String city;
	private String fee_Type;
	private String payee_Email;
	private String payee_Mobile;
	private String urgency;
	private String leave_Word;
	private String abstractInfo;
	private String remarksInfo;
	private String hmac;
	public TransferSingleRequest() {
		this.cmd = "TransferSingle";
		this.group_Id = ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID");
		this.mer_Id = ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID");
		this.fee_Type = ConfigUtil.getValue("YB_ENTRUST_SETTLE_FEE_FROM");
		this.urgency = "1";
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMer_Id() {
		return mer_Id;
	}
	public void setMer_Id(String mer_Id) {
		this.mer_Id = mer_Id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBatch_No() {
		return batch_No;
	}
	public void setBatch_No(String batch_No) {
		this.batch_No = batch_No;
	}
	public String getBank_Code() {
		return bank_Code;
	}
	public void setBank_Code(String bank_Code) {
		this.bank_Code = bank_Code;
	}
	public String getOrder_Id() {
		return order_Id;
	}
	public void setOrder_Id(String order_Id) {
		this.order_Id = order_Id;
	}
	public String getCnaps() {
		return cnaps;
	}
	public void setCnaps(String cnaps) {
		this.cnaps = cnaps;
	}
	public String getBank_Name() {
		return bank_Name;
	}
	public void setBank_Name(String bank_Name) {
		this.bank_Name = bank_Name;
	}
	public String getBranch_Bank_Name() {
		return branch_Bank_Name;
	}
	public void setBranch_Bank_Name(String branch_Bank_Name) {
		this.branch_Bank_Name = branch_Bank_Name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAccount_Name() {
		return account_Name;
	}
	public void setAccount_Name(String account_Name) {
		this.account_Name = account_Name;
	}
	public String getAccount_Number() {
		return account_Number;
	}
	public void setAccount_Number(String account_Number) {
		this.account_Number = account_Number;
	}
	public String getAccount_Type() {
		return account_Type;
	}
	public void setAccount_Type(String account_Type) {
		this.account_Type = account_Type;
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
	public String getFee_Type() {
		return fee_Type;
	}
	public void setFee_Type(String fee_Type) {
		this.fee_Type = fee_Type;
	}
	public String getPayee_Email() {
		return payee_Email;
	}
	public void setPayee_Email(String payee_Email) {
		this.payee_Email = payee_Email;
	}
	public String getPayee_Mobile() {
		return payee_Mobile;
	}
	public void setPayee_Mobile(String payee_Mobile) {
		this.payee_Mobile = payee_Mobile;
	}
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	public String getLeave_Word() {
		return leave_Word;
	}
	public void setLeave_Word(String leave_Word) {
		this.leave_Word = leave_Word;
	}
	public String getAbstractInfo() {
		return abstractInfo;
	}
	public void setAbstractInfo(String abstractInfo) {
		this.abstractInfo = abstractInfo;
	}
	public String getRemarksInfo() {
		return remarksInfo;
	}
	public void setRemarksInfo(String remarksInfo) {
		this.remarksInfo = remarksInfo;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public String getGroup_Id() {
		return group_Id;
	}
	public void setGroup_Id(String group_Id) {
		this.group_Id = group_Id;
	}
}
