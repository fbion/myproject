package com.ctfo.gateway.bean.yibao.business.entrustsettle;

import java.io.Serializable;

import com.ctfo.gateway.bean.base.ResponseBean;

public class WithDrawQuickBeanResponseYB extends ResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1900693577080609401L;
	
	//true：成功，false：失败
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
