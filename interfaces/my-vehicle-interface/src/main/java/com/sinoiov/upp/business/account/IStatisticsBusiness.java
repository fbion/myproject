package com.sinoiov.upp.business.account;

import com.ctfo.account.dto.beans.BalanceDto;
import com.ctfo.account.dto.beans.RecordedDeductionDto;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.sinoiov.upp.service.exception.UPPServiceException;

public interface IStatisticsBusiness {
	
	/**
	 * 统计账户余额
	 * @param parameter
	 * @return
	 * @throws UPPServiceException
	 */
	public BalanceDto sumAccountBalance(DynamicSqlParameter parameter) throws UPPException;
	
	/**
	 * 统计账户流水
	 * @param parameter
	 * @return
	 * @throws UPPServiceException
	 */
	public RecordedDeductionDto sumAccountDetailRecordedDeduction(DynamicSqlParameter parameter) throws UPPException;
	
	

}
