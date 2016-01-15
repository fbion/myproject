package com.sinoiov.upp.portal.service;

import com.sinoiov.upp.portal.controller.vo.CashierAccountDTO;

public interface IQueryUaaUserInfo {
	/**
	 * 获取统一认证用户信息
	 * @param userName
	 * @return
	 */
	public CashierAccountDTO getUaaUserInfo(String loginName);
}
