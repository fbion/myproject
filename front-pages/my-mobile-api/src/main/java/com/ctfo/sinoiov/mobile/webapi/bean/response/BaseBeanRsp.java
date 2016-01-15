package com.ctfo.sinoiov.mobile.webapi.bean.response;

import java.io.Serializable;

import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;

public class BaseBeanRsp implements Body, Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	// 乐观锁
	private String version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
