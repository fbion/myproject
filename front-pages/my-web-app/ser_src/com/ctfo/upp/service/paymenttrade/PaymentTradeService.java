package com.ctfo.upp.service.paymenttrade;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;

public interface PaymentTradeService {
	/**
	 * 条件分页交易管理-交易订单查询
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件分页交易管理-交易订单查询")
	public PaginationResult<?> queryPaymentTradeByPage(DynamicSqlParameter requestParam)throws UPPException;
}
