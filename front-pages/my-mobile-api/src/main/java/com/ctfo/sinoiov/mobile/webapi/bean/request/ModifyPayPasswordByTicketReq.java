package com.ctfo.sinoiov.mobile.webapi.bean.request;

/**
 * 修改支付密码(不需要输入旧密码，需要"安全票据")
 * 
 * @author sunchuanfu
 */
public class ModifyPayPasswordByTicketReq extends BaseBeanReq {
	private static final long serialVersionUID = -6050330882559142871L;

	private String accountNo;
	private String newMD5;// 新的支付密码
	private String securityTicket;// 安全票据

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getNewMD5() {
		return newMD5;
	}

	public void setNewMD5(String newMD5) {
		this.newMD5 = newMD5;
	}

	public String getSecurityTicket() {
		return securityTicket;
	}

	public void setSecurityTicket(String securityTicket) {
		this.securityTicket = securityTicket;
	}

}
