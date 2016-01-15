package com.ctfo.upp.gateway.fastpay.pc.yibao.response.domain;

import com.ctfo.upp.gateway.fastpay.pc.base.domain.PCDomain;

public class YBFastPayCallbackResultDomain extends PCDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户账户
	private String merchantaccount;
	// 易宝交易流水号
	private String yborderid;
	// 交易订单
	private String orderid;
	// 支付金额 int 以“分”为单位的整型
	private Integer amount;
	// 银行信息 string 支付卡所属银行的名称
	private String bank;
	// 卡号后4位 string 支付卡卡号后4位
	private String lastno;
	//支付身份标识 string
	private String identityid;
	/*
	0 	IMEI 	国际移动设备身份码的缩写，国际移动装备辨识码，是由15位数字组成的"电子串号"，它与每台手机一一对应
	1 	MAC地址 	MAC(Media Access Control)地址，或称为 MAC位址、硬件位址，用来定义网络设备的位置。在OSI模型中，第三层网络层负责 IP地址，第二层数据链路层则负责 MAC位址。因此一个主机会有一个IP地址，而每个网络位置会有一个专属于它的MAC位址。
	2 	用户ID 	用户编号
	3 	用户Email 	
	4 	用户手机号 	
	5 	用户身份证号 	
	6 	用户纸质订单协议号
	*/
	private Integer identitytype;
	// 订单状态 int 1：成功
	private Integer status;

	private String className = getClass().getCanonicalName();

	public String getMerchantaccount() {
		return merchantaccount;
	}

	public void setMerchantaccount(String merchantaccount) {
		this.merchantaccount = merchantaccount;
	}

	public String getYborderid() {
		return yborderid;
	}

	public void setYborderid(String yborderid) {
		this.yborderid = yborderid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getLastno() {
		return lastno;
	}

	public void setLastno(String lastno) {
		this.lastno = lastno;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getIdentityid() {
		return identityid;
	}

	public void setIdentityid(String identityid) {
		this.identityid = identityid;
	}

	public Integer getIdentitytype() {
		return identitytype;
	}

	public void setIdentitytype(Integer identitytype) {
		this.identitytype = identitytype;
	}
}
