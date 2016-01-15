package com.ctfo.upp.accountservice.utils;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dto.beans.OrderInfoSummaryDTO;

public class GetObjectSummary {
	public static Object getSummary(Object obj){
		if(obj instanceof OrderInfo){
			OrderInfoSummaryDTO dto = new OrderInfoSummaryDTO();
			OrderInfo orderInfo = (OrderInfo) obj;
			dto.setId(orderInfo.getId());
			dto.setAccountNo(orderInfo.getAccountNo());
			dto.setCollectMoneyAccountNo(orderInfo.getCollectMoneyAccountNo());
			dto.setOrderNo(orderInfo.getOrderNo());
			dto.setCreateTime(orderInfo.getCreateTime());
			dto.setOrderAmount(orderInfo.getOrderAmount());
			dto.setOrderType(orderInfo.getOrderType());
			dto.setStoreCode(orderInfo.getStoreCode());
			dto.setTradeExternalNo(dto.getTradeExternalNo());
			dto.setWorkOrderNo(dto.getWorkOrderNo());
			return dto;
		}
		return null;
	}
}
