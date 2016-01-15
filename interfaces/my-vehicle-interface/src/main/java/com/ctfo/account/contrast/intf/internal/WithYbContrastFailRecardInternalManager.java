package com.ctfo.account.contrast.intf.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.ctfo.payment.dto.beans.SmartTradeAccountRecordDto;
import com.ctfo.upp.exception.UPPException;

public interface WithYbContrastFailRecardInternalManager {
	/**
	 * 处理今日之前，所有未同步成功的交易记录
	 * @throws UPPException
	 */
	public void dealAllUnsycRecord() throws UPPException;

	public void dealAccountStatus() throws UPPException;

	public Map<String,SmartTradeAccountRecordDto> getSmartTradeAccountRecords(String insideAccountNo,
			String accountDate) throws UPPException;

	public HashSet<String> unSycSuccessAccountNo(Long startTime, Long endTime)
			throws Exception;
}
