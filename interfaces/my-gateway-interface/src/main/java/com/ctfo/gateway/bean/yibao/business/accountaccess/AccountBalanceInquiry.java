package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.Necessary;
import com.ctfo.gateway.bean.base.RequestBean;
/**
 * 用于做外部账户的信息查询
 * @author majingyuan
 *
 */
public class AccountBalanceInquiry  extends RequestBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6077239548690654112L;
	@Necessary
	private String serviceProviderCode;  //支付渠道标识
	@Necessary
	private String queryType;	
	
	@Necessary
	private String insideAccountNo;		//内部账号
	
	@Necessary
	private String externalAccountNo;		//外部账号
	
	@Necessary
	private Long queryTime;				//查询时间

	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	public String getExternalAccountNo() {
		return externalAccountNo;
	}

	public void setExternalAccountNo(String externalAccountNo) {
		this.externalAccountNo = externalAccountNo;
	}

	public Long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Long queryTime) {
		this.queryTime = queryTime;
	}
}
