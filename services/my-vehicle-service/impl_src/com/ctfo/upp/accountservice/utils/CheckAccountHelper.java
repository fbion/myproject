package com.ctfo.upp.accountservice.utils;

import com.ctfo.gateway.bean.yibao.business.accountaccess.CheckAccountBeanDetailRequestYB;
import com.ctfo.payment.dao.beans.RechargeCallback;
import com.ctfo.payment.dto.beans.CheckAccountMess;
import com.ctfo.payment.dto.beans.RechargeSuccessMess;
import com.ctfo.upp.dict.PayDict;

public class CheckAccountHelper {
	public static  CheckAccountBeanDetailRequestYB createCheckAccountBeanRequest(CheckAccountMess checkAccountMess){
			Long time=System.currentTimeMillis();
			CheckAccountBeanDetailRequestYB checkAccountBeanDetailRequest=new CheckAccountBeanDetailRequestYB();
			checkAccountBeanDetailRequest.setRequestId(checkAccountMess.getRecordId());
//			checkAccountBeanDetailRequest.setRequestId(requestId++ + "");
	//		checkAccountBeanDetailRequest.setAccountId(checkAccountMess.getAccountId());
			checkAccountBeanDetailRequest.setAccountId(checkAccountMess.getInsideAccountNo());
			checkAccountBeanDetailRequest.setBalance(checkAccountMess.getTotalBalance().toString());
			checkAccountBeanDetailRequest.setTrxout(checkAccountMess.getOutAmount().toString());
			checkAccountBeanDetailRequest.setTrxin(checkAccountMess.getInAmount().toString());
			checkAccountBeanDetailRequest.setRecharge(checkAccountMess.getRechargeAmount().toString());
			checkAccountBeanDetailRequest.setCash(checkAccountMess.getTakeCashAmount().toString());
			checkAccountBeanDetailRequest.setRechargeRefund("0");
			checkAccountBeanDetailRequest.setAdjuset("0");
			checkAccountBeanDetailRequest.setTime(time.toString());
			
			return checkAccountBeanDetailRequest;
		}
	public static RechargeCallback createRechargeCallback(RechargeSuccessMess log){
		RechargeCallback rechargeCallback= new RechargeCallback();
		rechargeCallback.setTrxAmount(log.getOrderAmount());
		rechargeCallback.setRequestId(log.getTradeNo());
		rechargeCallback.setExternalId(log.getExternalId());
		rechargeCallback.setCompleteTime(log.getPayConfirmDate());
		rechargeCallback.setPlatformCode(PayDict.PLATFORM_CODE_YEE_PAY);
		rechargeCallback.setFlag(log.getFlag());
		return rechargeCallback;
	}
}

