package com.ctfo.upp.accountservice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ctfo.util.SnoGerUtil;
/**
 * 流水号、订单号生成
 * @author majingyuan
 *
 */
public class TradeNumberHelper {
	
	
	public static String getPayOrderNo(){
		//--支付订单号，稍后细化
		StringBuilder sf=new StringBuilder ();
		sf.append("ON").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append(String.valueOf(SnoGerUtil.getRandomNumber(3)));
		//--支付订单号，稍后细化
		return sf.toString();
	}
	//交易流水号
	public static String getTradeExternalNo(){
		StringBuilder sf=new StringBuilder ();
		sf.append("BN").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append(String.valueOf(SnoGerUtil.getRandomNumber(3)));
		
		return sf.toString();
	}
	//账务流水
	public static String getBookAccExternalNo(){
			StringBuilder sf=new StringBuilder ();
			sf.append("AN").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append(String.valueOf(SnoGerUtil.getRandomNumber(3)));
			
			return sf.toString();
	}
	//生成任务号码
	public static String getTaskNo(){
				StringBuilder sf=new StringBuilder ();
				sf.append("TASK").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append(String.valueOf(SnoGerUtil.getRandomNumber(3)));
				
				return sf.toString();
	}
	//生成清结算申请号
	public static String getClearingAndSettlementNo(){
		StringBuilder sf=new StringBuilder ();
		sf.append("CS").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append(String.valueOf(SnoGerUtil.getRandomNumber(2)));
		
		return sf.toString();
	}
	//批次内记录号
	public static Long getBatchInterNo(){
		return Long.valueOf(new SimpleDateFormat("yyMMddHHmmssSSS").format(System.currentTimeMillis()))*100;
	}
}
