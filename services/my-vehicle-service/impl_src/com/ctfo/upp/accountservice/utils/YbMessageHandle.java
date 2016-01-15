package com.ctfo.upp.accountservice.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ctfo.payment.dto.beans.CheckCallbackMessage;

public class YbMessageHandle {
	/**
	 * 处理易宝报文
	 * @param detailString
	 */
	public static List<CheckCallbackMessage> dealWithYBMessage(String detailString){
		Map<String,String> map =new HashMap<String,String>();
		map.put("Balance", "balance");
		map.put("UnInAccountrecharge", "unInAccountRecharge");
		map.put("Trxin", "trxin");
		map.put("Trxout", "trxout");
		map.put("Recharge", "recharge");
		map.put("RechargeRefund", "rechargeRefund");
		map.put("Adjust", "adjust");
		List<CheckCallbackMessage> list = new ArrayList<CheckCallbackMessage>();
		
		
		for(String key:map.keySet()){
			detailString=detailString.replace(key, map.get(key));
		}
		String[] failAccountNoArr = detailString.split("\\|");
		
		for(String failAccountNoMess:failAccountNoArr){

			String insideAccountNo =	failAccountNoMess.substring(0,failAccountNoMess.indexOf("&")	);
			
			String content=failAccountNoMess.substring(failAccountNoMess.indexOf("&")+1);

			
			String jsonString =		new StringBuilder().append("{'userNumber:").append(insideAccountNo).append("','")
					.append(content.replace(";","','")).append("'}").toString()	;
			 jsonString =		jsonString.replace(":","':'");
			System.out.println(jsonString);
			CheckCallbackMessage mess=(CheckCallbackMessage) JSONObject.toBean(
					JSONObject.fromObject(jsonString), CheckCallbackMessage.class);
			list.add(mess);
		}
		return list;
	}
}
