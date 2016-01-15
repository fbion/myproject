package com.sinoiov.pay.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.root.utils.AmountUtil;
import com.sinoiov.upp.service.dto.PayDto;

public class OrderInfoUtil {
	
	public static void copyPayDtoToOrderInfo(PayDto payDto, OrderInfo orderInfo) throws UPPException{
		
		if(payDto == null || orderInfo==null)
			throw new UPPException("源对象和目标对象不能为空");
		
		BeanUtils.copyProperties(payDto, orderInfo);
		
		orderInfo.setCallbackUrl(payDto.getCallbackURL());
		//orderInfo.setCollectMoneyUserId();
		//orderInfo.setCollectMoneyAccountNo();
		//orderInfo.setOrderType(payDto.get);
		//orderInfo.setTradeExternalNo();
		
		//orderInfo.setPayConfirmDate();	
		//orderInfo.setOrderSubject(payDto.get);	
		//orderInfo.setBusinessStepNo();
		//orderInfo.setUserIp();
		orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_ZJXL_PAY);
		
		if(StringUtils.isNotBlank(payDto.getCallbackURL()))
			orderInfo.setCallbackUrl(payDto.getCallbackURL());	
		if(StringUtils.isNotBlank(payDto.getFcallbackURL()))
			orderInfo.setFcallbackUrl(payDto.getFcallbackURL());	
		if(StringUtils.isNotBlank(payDto.getBankCardName()))
			orderInfo.setBankName(payDto.getBankCardName());	
		if(StringUtils.isNotBlank(payDto.getBankCardCode()))
			orderInfo.setBankCode(payDto.getBankCardCode());
		if(StringUtils.isNotBlank(payDto.getBankCardType()))
			orderInfo.setBankCardType(payDto.getBankCardType());
		if(StringUtils.isNotBlank(payDto.getMerchantOrderNo()))
			orderInfo.setWorkOrderNo(payDto.getMerchantOrderNo());
		//订单金额
		if(StringUtils.isNotBlank(payDto.getMerchantOrderAmount()))
			orderInfo.setOrderAmount(AmountUtil.getAmount(payDto.getMerchantOrderAmount()));
		
	}

}
