/**
 * 北京中交兴路车联网科技有限公司 2011 版权所有.
 */
package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 根据流水ID查询对应的账户流水信息request
 * 
 * @author dxs
 */
public class QueryPayCenterTradeInfoReq extends BaseBeanReq {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易ID
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
