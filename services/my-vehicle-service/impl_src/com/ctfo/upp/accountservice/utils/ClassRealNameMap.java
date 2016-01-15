package com.ctfo.upp.accountservice.utils;

import java.util.HashMap;
import java.util.Map;

public class ClassRealNameMap {
	public static Map<String,String> realNameMap =new HashMap<String,String>();
	public static Map<String,String> tableHeaderMap =new HashMap<String,String>();
	static{
		realNameMap.put("CheckAccountFailRecord", "com.ctfo.payment.flowrecord.beans.CheckAccountFailRecord");
		realNameMap.put("YBLog", "com.ctfo.upp.gatewayservice.bean.mongoDB.log.YBLog");
	
		tableHeaderMap.put("CheckAccountFailRecord", "id|accountId|balance|Balance|Trxout|Trxin|RechargeRefundWithdraw|Adjust|UnInAccountRecharge|date");
		tableHeaderMap.put("YBLog", "id|name|type|requestTime|responseTime|request|response");
	}
}
