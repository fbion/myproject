package com.ctfo.upp.accountservice.payment.external.impl;

import java.util.Map;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.exception.UPPException;

/**
 * 此方法全部未实现，下版(5月份的版本)实现，目的是把充值和扣款分开，避免各种判断参数错误
 * @author malongqing
 *
 */
public class DeductMoneyManagerExternalImpl implements com.ctfo.payment.intf.external.IDeductMoneyManager {

	@Override
	public Map<String, Object> deductMoney(OrderInfo orderInfo)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> pcNetDeductMoney(OrderInfo orderInfo)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> pcFastpayDeductMoney(OrderInfo orderInfo)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> mobileNetDeductMoney(OrderInfo orderInfo)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> mobileFastpayDeductMoney(OrderInfo orderInfo)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

}
