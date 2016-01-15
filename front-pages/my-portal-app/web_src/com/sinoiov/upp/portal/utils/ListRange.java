package com.sinoiov.upp.portal.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据组装
 * @author jichao
 */


public class ListRange<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<T> data;
	private Page page;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
