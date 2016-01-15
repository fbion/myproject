package com.ctfo.upp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	private static Log logger = LogFactory.getLog(DateUtil.class);
	/**
	 * 
	 * @param time  dateFormat
	 * 		Long     
	 * @param dateFormat
	 * @return
	 */
	public static String longToDate(Long time,String dateFormat){
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		Date date = new Date(time);
		return df.format(date);
	}
	/**
	 * 比较一个时间是否在另一个之前
	 * @param timeStart
	 * @param timeEnd
	 * @param dateFormat
	 * @return
	 */
	 
	public static boolean checkTime(String timeStart,String timeEnd,String dateFormat) {
		SimpleDateFormat formatDate = new SimpleDateFormat(dateFormat);
			try {
				String start = formatDate.format(formatDate.parse(timeStart));
				String end = formatDate.format(formatDate.parse(timeEnd));
				Date dataStart = formatDate.parse(start);
				Date dataEnd = formatDate.parse(end);
				boolean flag = dataStart.before(dataEnd);
				return flag;
			} catch (ParseException e) {
				logger.error("时间转换失败");
			}
		

		return false;
	}
}
