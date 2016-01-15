/**
 * 北京中交兴路车联网科技有限公司 2011 版权所有.
 */
package com.ctfo.sinoiov.mobile.webapi.bean.response;

import java.io.Serializable;
import java.util.List;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.PayCenterTradeVO;

/**
 * 账户流水返回
 * 
 * @author dxs
 */
public class QueryPayCenterTradeListRsp implements Body, Serializable {
	private static final long serialVersionUID = 3836430739036535946L;

	/**
	 * 总条数
	 */
	private String total;

	/**
	 * 数据
	 */
	private List<PayCenterTradeVO> data;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<PayCenterTradeVO> getData() {
		return data;
	}

	public void setData(List<PayCenterTradeVO> data) {
		this.data = data;
	}

}
