package com.ctfo.upp.accountservice.utils;

import org.springframework.beans.BeanUtils;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.exception.UPPException;

public class CheckOrderHelper {
	public static OrderInfo checkTranserAccounts (OrderInfo orderInfo)throws UPPException{
		CheckOrderHelper.checkFreezeAccounts(orderInfo);
		
		OrderInfo paramOrder = new OrderInfo();
		if (orderInfo.getCollectMoneyAccountNo() == null || orderInfo.getCollectMoneyAccountNo() .isEmpty())
			throw new IllegalArgumentException("支付平台参数值不合法.");
		if (orderInfo.getAccountNo() == null || orderInfo.getAccountNo() .isEmpty())
			throw new IllegalArgumentException("支付平台参数值不合法.");
		
		
		String[] ignoreProperties = { "id","orderNo","orderStatus","tradeExternalNo",
				"createSubareaTime","productDesc","clentId","userUa","identityType","payConfirmDate",
				 "createTime", "version" };
		BeanUtils.copyProperties(orderInfo, paramOrder, ignoreProperties);
		
		return paramOrder;
	}


	public static void checkFreezeAccounts (OrderInfo orderInfo)throws UPPException{
		if (orderInfo == null )
			throw new NullPointerException("Create an account, account object parameter is null.");

		if (orderInfo.getStoreCode() == null || orderInfo.getStoreCode().isEmpty())
			throw new IllegalArgumentException("商户编号不能为空");
		if (orderInfo.getAccountNo() == null || orderInfo.getAccountNo().isEmpty())
			throw new IllegalArgumentException("帐号不能为空");		
		if (orderInfo.getOrderAmount() == null || orderInfo.getOrderAmount()<=0)
			throw new IllegalArgumentException("金额不能为空");
		if (orderInfo.getWorkOrderNo() == null || orderInfo.getWorkOrderNo().isEmpty())
			throw new IllegalArgumentException("业务订单不能为空");
		if (orderInfo.getOrderType() == null || orderInfo.getOrderType().isEmpty())
			throw new IllegalArgumentException("订单类型不能为空");
		else if (orderInfo.getServiceProviderCode() == null || orderInfo.getServiceProviderCode().isEmpty())
			throw new IllegalStateException("传递的[支付平台编号]值不合法.");

	}
}
