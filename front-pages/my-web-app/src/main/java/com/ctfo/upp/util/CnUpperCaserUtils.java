package com.ctfo.upp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.ctfo.upp.exception.UPPException;

/***
 * 类描述：将16位以内的阿拉伯数字转成汉字大写形式
 * 
 * @author：liuguozhong
 * @date：2014年12月11日上午11:32:07
 * @version 1.0
 * @since JDK1.6
 */
public class CnUpperCaserUtils {

	private static Log logger = LogFactory.getLog(CnUpperCaserUtils.class);
	/** 大写数字 */
	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	/** 整数部分的单位 */
	private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
	/** 小数部分的单位 */
	private static final String[] DUNIT = { "角", "分", "厘" };

	/**
	 * 得到大写金额。
	 */
	public static String getNum2CnString(String str) throws UPPException{
		if(!StringUtils.isEmpty(str)&&!isNumeric(str)){
			logger.debug(str + "不是数字");
			throw new UPPException(str + "不是数字，CnUpperCaserUtils类不能处理");
		}
		str = str.replaceAll(",", "");// 去掉","
		String integerStr;// 整数部分数字
		String decimalStr;// 小数部分数字
		
		// 初始化：分离整数部分和小数部分
		if (str.indexOf(".") > 0) {
			integerStr = str.substring(0, str.indexOf("."));
			decimalStr = str.substring(str.indexOf(".") + 1);
		} else if (str.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = str.substring(1);
		} else {
			integerStr = str;
			decimalStr = "";
		}
		// integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
		if (!integerStr.equals("")) {
			integerStr = Long.toString(Long.parseLong(integerStr));
			if (integerStr.equals("0")) {
				integerStr = "";
			}
		}
		// overflow超出处理能力，直接返回
		if (integerStr.length() > IUNIT.length) {
			logger.debug(str + ":超出处理能力");
			return str;
		}
		int[] integers = toArray(integerStr);// 整数部分数字
		boolean isMust5 = isMust5(integerStr);// 设置万单位
		int[] decimals = toArray(decimalStr);// 小数部分数字
		return getChineseInteger(integers, isMust5) + getChineseDecimal(decimals);
	}

	/**
	 * 整数部分和小数部分转换为数组，从高位至低位
	 */
	private static int[] toArray(String number) {
		int[] array = new int[number.length()];
		for (int i = 0; i < number.length(); i++) {
			array[i] = Integer.parseInt(number.substring(i, i + 1));
		}
		return array;
	}

	/**
	 * 得到中文金额的整数部分。
	 */
	private static String getChineseInteger(int[] integers, boolean isMust5) {
		StringBuffer chineseInteger = new StringBuffer("");
		int length = integers.length;
		for (int i = 0; i < length; i++) {
			// 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
			// 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
			String key = "";
			if (integers[i] == 0) {
				if ((length - i) == 13)// 万(亿)(必填)
					key = IUNIT[4];
				else if ((length - i) == 9)// 亿(必填)
					key = IUNIT[8];
				else if ((length - i) == 5 && isMust5)// 万(不必填)
					key = IUNIT[4];
				else if ((length - i) == 1)// 元(必填)
					key = IUNIT[0];
				// 0遇非0时补零，不包含最后一位
				if ((length - i) > 1 && integers[i + 1] != 0)
					key += NUMBERS[0];
			}
			chineseInteger.append(integers[i] == 0 ? key : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
		}
		return chineseInteger.toString();
	}

	/**
	 * 得到中文金额的小数部分。
	 */
	private static String getChineseDecimal(int[] decimals) {
		StringBuffer chineseDecimal = new StringBuffer("");
		for (int i = 0; i < decimals.length; i++) {
			// 舍去3位小数之后的
			if (i == 3)
				break;
			chineseDecimal.append(decimals[i] == 0 ? "" : (NUMBERS[decimals[i]] + DUNIT[i]));
		}
		return chineseDecimal.toString();
	}

	/**
	 * 判断第5位数字的单位"万"是否应加。
	 */
	private static boolean isMust5(String integerStr) {
		int length = integerStr.length();
		if (length > 4) {
			String subInteger = "";
			if (length > 8) {
				// 取得从低位数，第5到第8位的字串
				subInteger = integerStr.substring(length - 8, length - 4);
			} else {
				subInteger = integerStr.substring(0, length - 4);
			}
			return Integer.parseInt(subInteger) > 0;
		} else {
			return false;
		}
	}
	/***
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		boolean flag=true;
		String integerStr = "";
		String decimalStr = "";
		if (str.indexOf(".") > 0) {
			integerStr = str.substring(0, str.indexOf("."));
			if(!org.apache.commons.lang.StringUtils.isEmpty(integerStr)){
				flag=isNum(integerStr);
			}
			decimalStr = str.substring(str.indexOf(".") + 1);
			if(flag&&!org.apache.commons.lang.StringUtils.isEmpty(decimalStr)){
				flag=isNum(decimalStr);
			}
		} else if (str.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = str.substring(1);
			flag=isNum(decimalStr);
		} else {
			integerStr = str;
			decimalStr = "";
			flag=isNum(integerStr);
		}
		return flag;
	}

	private static boolean isNum(String str){
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//System.out.println(CnUpperCaserUtils.getNum2CnString("a.0"));
			System.out.println(CnUpperCaserUtils.getNum2CnString("5000"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
