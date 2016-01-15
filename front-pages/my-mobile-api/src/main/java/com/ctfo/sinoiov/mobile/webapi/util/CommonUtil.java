package com.ctfo.sinoiov.mobile.webapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;


import com.ctfo.util.SnoGerUtil;
/**
 * 
 * 
 * <p>
 * -----------------------------------------------------------------------------
 * <br>
 * 工程名 ： pltp-webapi
 * <br>
 * 功能：
 * <br>
 * 描述：
 * <br>
 * 授权 : (C) Copyright (c) 2011
 * <br>
 * 公司 : 北京中交兴路信息科技有限公司
 * <br>
 * -----------------------------------------------------------------------------
 * <br>
 * 修改历史
 * <br>
 * <table width="432" border="1">
 * <tr><td>版本</td><td>时间</td><td>作者</td><td>改变</td></tr>
 * <tr><td>1.0</td><td>2014-3-10</td><td>JiangXF</td><td>创建</td></tr>
 * </table>
 * <br>
 * <font color="#FF0000">注意: 本内容仅限于[北京中交车联网科技有限公司]内部使用，禁止转发</font>
 * <br>
 * 
 * @version 1.0
 * 
 * @author JiangXF
 * @since JDK1.6
 */
public class CommonUtil {
	
	/** 数字英文 */
	public static final String NUMBER_LETTLE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** 英文 */
	public static final String LETTER_ONLY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** 数字 */
	public static final String NUMBER_ONLY = "0123456789";

	/**
	 * 生成随机密码、手机短信验证码
	 * 
	 * @param type
	 *            (1：纯数字，2：纯英文，3：数字英文，默认：3)
	 * @param length
	 *            (默认：6位)
	 * @return length位数的type类型的任意组合
	 */
	public static String getRandomString(Integer type, Integer length) {
		type = type == null ? 3 : type;
		length = length == null ? 6 : length;
		StringBuffer buffer = null;
		if (type == 1)
			buffer = new StringBuffer(NUMBER_ONLY);
		else if (type == 2)
			buffer = new StringBuffer(LETTER_ONLY);
		else if (type == 3)
			buffer = new StringBuffer(NUMBER_LETTLE);
		else
			buffer = new StringBuffer(NUMBER_LETTLE);
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}
	
	

	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return filename.substring(dot + 1);   
            }   
        }   
        return filename;   
    }
	
	/**
	 * 生成新文件名称
	 * @param fileExt
	 * @return
	 */
	public static String getNewFileName(String fileExt){
		String name = "";
		name = String.valueOf(System.currentTimeMillis());
		name += SnoGerUtil.getRandomNumber(4) + "." + fileExt;// [15 numaber].xxx
		return name;
	}
	
	/**
	 * 折分保存图片值
	 * @param str	如：abc:2,edf:3,eee:1
	 * @param obj	数组
	 * @return map
	 */
	public static Map<String,String[]> getFiledValue(String str,String[] obj){
		Map<String,String[]> map = new HashMap<String,String[]>();
		if(StringUtils.isNotBlank(str) && obj != null && obj.length>0){
			String[] s = str.split(",");
			
			int offset = 0;
			for(int i = 0 ; i < s.length ; i++){
				String _name = s[i].split(":")[0];
				String _value = s[i].split(":")[1];
				int des = offset + Integer.valueOf(_value).intValue();
				String[] value = new String[Integer.valueOf(_value).intValue()];
				int k = 0;
				for(int j = offset ; j < des ; j++){
					value[k] = obj[j];
					k++;
				}
				offset = des;
				map.put(_name, value);
			}
			
		
		}
		
		return map;
	}
	/**
	 * 将数组图片名称拼成一个字符串，以逗号隔开
	 * @param map 图片集合
	 * @param keyId	
	 * @return
	 */
	public static String getPicValueToString(Map<String,String[]> map,String keyId){
		String value = "";
		if(map == null || StringUtils.isBlank(keyId)){
			return "";
		}
		String s[] = map.get(keyId);
		if(s != null && s.length>0){
			for(int i = 0;i<s.length;i++){
				if(i>0 && i<s.length){
					value +=",";
				}
				value += s[i]; 
				
			}
		}
		return value;
	}
	/**
	 * 根据时间字符串转成long值
	 * @param date
	 * @return 字符串；毫秒
	 */
	public static String dateToString(String date,String formatStr){
		if(date == null || date.length()<1){
			return null;
		}
		if(formatStr == null || formatStr.length()<1){
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (ParseException e) {
			return null;
			
		}
		return c.getTimeInMillis()+"";
	}
	/**
	 * 根据时间类型串转成long值
	 * @param date
	 * @return 字符串；毫秒
	 */
	public static String dateToString(Date date){
		if(date == null){
			return null;
		}
		GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis()+"";
	}
	/**
	 * 将long型转成 yyyy-MM-dd HH:mm:ss格的时间
	 * @param l
	 * @return 字符串：如 2014-01-10 01:01:00
	 */
	public static String longToDate(long l){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return format.format(new Date(l));
	}
	public static void main(String[] args) {
	}
	
}
