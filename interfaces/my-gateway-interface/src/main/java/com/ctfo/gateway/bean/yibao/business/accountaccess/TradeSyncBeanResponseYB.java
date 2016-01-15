package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class TradeSyncBeanResponseYB extends ResponseBean implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1687610306362635847L;

	// true：成功，false：失败
	private boolean flag;
	
	private String lastSuccessRecordId;
	
	private String ybReturnCode;
	

	public String getYbReturnCode() {
		return ybReturnCode;
	}

	public void setYbReturnCode(String ybReturnCode) {
		this.ybReturnCode = ybReturnCode;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getLastSuccessRecordId() {
		return lastSuccessRecordId;
	}

	public void setLastSuccessRecordId(String lastSuccessRecordId) {
		this.lastSuccessRecordId = lastSuccessRecordId;
	}
}
