package com.sinoiov.upp.portal.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * 
 * @author jichao
 * 
 */
public class DateUtils {

	public static final String FMT_DATETIME = "yyyy-MM-dd hh:mm:ss";
	public static final String FMT_DATE = "yyyy-MM-dd";
	
	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(gc.getTime());
	}

	/**
	 * 日期字符串转为时间戳
	 * @param dateStr 
	 * @param fmt "yyyy-MM-dd hh:mm:ss"
	 * @return
	 */
	public static long date2TimeStamp(String datetimeStr,String fmt) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		Date date;
		try {
			date = simpleDateFormat.parse(datetimeStr);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String timeStamp2Date(long timestamp){
		//long sd=1345185923140L;  
	    Date dat=new Date(timestamp);  
	    GregorianCalendar gc = new GregorianCalendar();   
	    gc.setTime(dat);  
	    java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	    String sb=format.format(gc.getTime());  
	   return sb;
	}
    
	

	public static void main(String[] args) {
		System.out.println(dateFormat(new Date()));
		System.out.println(date2TimeStamp("2014-9-5 13:05:30","yyyy-MM-dd hh:mm:ss"));
		System.out.println(date2TimeStamp("2014-9-5","yyyy-MM-dd"));
		System.out.println(timeStamp2Date(1345185923140L));
	}
}
