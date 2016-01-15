package com.sinoiov.upp.callback;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.exception.UPPException;

public interface IBusinessCallBack {
	
	public void callBack(final OrderInfo order) throws UPPException;

}
