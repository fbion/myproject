package com.ctfo.gateway.intf;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;

public interface ThirdPartPayQueryFacade {
	/**
	 * 查询在第三方支付中的账户信息
	 * @param requestBean
	 * @return
	 * @throws Exception
	 */
	public ResponseBean queryAccountInfo(
			RequestBean requestBean)
			throws Exception;
}
