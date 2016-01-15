package com.sinoiov.upp.util.payment;

import java.util.HashMap;

import com.ctfo.upp.dict.PayOrderSubjectDict;

public class CheckOrderMethodCategories {
	public static final HashMap<String,String> methodMap= new HashMap<String,String>();
	
	static{
		methodMap.put("netPayCheckOrder",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_PAY);
		methodMap.put("netRechargeCheckOrder",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_RECHARGE);
		methodMap.put("fastpayRecharge",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE);
		methodMap.put("fastPay",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY);
		methodMap.put("transferAccounts",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_TRANSFER_ACCOUNT);
		methodMap.put("freezeAccountMoney",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FREEZE);
		methodMap.put("unFreezeAccountMoney",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_UNFREEZE);
		methodMap.put("accountPay",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_PAY);
		methodMap.put("accountRecharge",PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_RECHARGE);
		
	}
}
