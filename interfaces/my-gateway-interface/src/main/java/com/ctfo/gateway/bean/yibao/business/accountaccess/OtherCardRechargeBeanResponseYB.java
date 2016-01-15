package com.ctfo.gateway.bean.yibao.business.accountaccess;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class OtherCardRechargeBeanResponseYB extends ResponseBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8552438878927235597L;
	
	//true：成功，false：失败
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
