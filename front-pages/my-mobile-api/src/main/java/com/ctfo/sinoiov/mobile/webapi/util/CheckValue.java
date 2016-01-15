package com.ctfo.sinoiov.mobile.webapi.util;

import java.util.regex.Pattern;

/**
 * 数据格式有效性校验
 */
public class CheckValue {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		CheckValue cv = new CheckValue();
		System.out.print("输入正确如下：");
		System.out.print("\n\n\n去左边空格：=" + cv.ltrim("    abc") + "===");
		System.out.print("\n去右边空格：=" + cv.rtrim("edf   ") + "===");
		System.out.print("\n去两边空格：=" + cv.trim("  1234   ") + "===");
		System.out.print("\n判断字符串是否为空：" + cv.checkNull(" abc") + "===");
		System.out.print("\n判断字符串是否全是数字：" + cv.checkOnlyNumber("123456"));
		System.out.print("\n判断字符串是否全是字母：" + cv.checkOnlyChar("abcefe"));
		System.out.print("\n判断字符串是否是字母与数字组合："+cv.checkNumberAndChar("q2e34r5"));
		System.out.print("\n判断邮箱：" + cv.checkEmail("aa@aa.com"));
		System.out.print("\n判断URL：" + cv.checkUrl("http://baidu.com"));
		System.out.print("\n判断电话：" + cv.checkTelNumber("86-010-12345678"));
		System.out.print("\n判断邮编：" + cv.checkPostcode("100231"));
		System.out.print("\n判断长度：" + cv.checkCheckLength("abccq", 5));
		System.out.print("\n判断字符串第一个是否是字母：" + cv.checkFirstIsChar("a123"));
		System.out.print("\n判断日期：" + cv.checkDate("2008-12-12"));
		System.out.print("\n判断时间：" + cv.checkTime("15:50:01"));
		System.out.print("\n判断日期时间：" + cv.checkDateTime("2008/05/30 23:20:52"));
		System.out.print("\n判断身份证：" + cv.checkIDCard("12345619800325145X"));
		System.out.print("\n判断IP：" + cv.checkIP("249.249.249.249"));
		System.out.print("\n判断是否是中文："+cv.checkChinese("我是中国人"));
		
		System.out.print("\n\n\n输入错误如下：");
		System.out.print("\n\n\n判断字符串是否为空：" + cv.checkNull("") + "===");
		System.out.print("\n判断字符串是否全是数字：" + cv.checkOnlyNumber("1234afa56"));
		System.out.print("\n判断字符串是否全是字母：" + cv.checkOnlyChar("12bcefe"));
		System.out.print("\n判断字符串是否是字母与数字组合："+cv.checkNumberAndChar("qqqq"));
		System.out.print("\n判断邮箱：" + cv.checkEmail("aa.jiang@aa.com"));
		System.out.print("\n判断URL：" + cv.checkUrl("//baidu.com"));
		System.out.print("\n判断电话：" + cv.checkTelNumber("860-010-123456789"));
		System.out.print("\n判断邮编：" + cv.checkPostcode("10023"));
		System.out.print("\n判断长度：" + cv.checkCheckLength("abcc", 3));
		System.out.print("\n判断字符串第一个是否是字母：" + cv.checkFirstIsChar("1fafaf23"));
		System.out.print("\n判断日期：" + cv.checkDate("2008-12-32"));
		System.out.print("\n判断时间：" + cv.checkTime("00:00:70"));
		System.out.print("\n判断日期时间：" + cv.checkDateTime("2008/05/30 24:59:52"));
		System.out.print("\n判断身份证：" + cv.checkIDCard("12345619800325145"));
		System.out.print("\n判断IP：" + cv.checkIP("1.0.0.1"));
		System.out.print("\n判断是否是中文："+cv.checkChinese("abce"));
	}

	/*****去掉左边空格*****/
	public static String ltrim(String str) {
		String whitespace = new String(" \t\n\r");
		String s = new String(str);
		if (whitespace.indexOf(s.charAt(0)) != -1) {
			int j = 0, i = s.length();
			while (j < i && whitespace.indexOf(s.charAt(j)) != -1) {
				j++;
			}
			s = s.substring(j, i);
		}
		return s;
	}

	/*****去掉右边空格*****/
	public static String rtrim(String str) {
		String whitespace = new String(" \t\n\r");
		String s = new String(str);
		if (whitespace.indexOf(s.charAt(s.length() - 1)) != -1) {
			int i = s.length() - 1;
			while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1) {
				i--;
			}
			s = s.substring(0, i + 1);
		}
		return s;
	}

	/*****去掉两边空格*****/
	public static String trim(String str) {
		return rtrim(ltrim(str));
	}

	/*****判断字符串是否为空*****/
	public boolean checkNull(String str) {
		String s = str;
		if (s == null || s.isEmpty() || s == "") {
			return false;
		}
		return true;
	}

	/*****判断字符串是否全是数字*****/
	public static boolean checkOnlyNumber(String str) {
		String reg = "(^-?|^\\+?)\\d+$";
		return Pattern.matches(reg, str);
	}

	/***** 判断字符串是否全是字母*****/
	public static boolean checkOnlyChar(String str) {
		String reg = "^[a-zA-z]*$";
		return Pattern.matches(reg, str);
	}

	/*****判断是否是数字和字母组合*****/
	public static boolean checkNumberAndChar(String str) {
		String reg = "^([a-zA-Z][a-zA-Z0-9]{0,}[0-9][a-zA-Z0-9]{0,})|([0-9][a-zA-Z0-9]{0,}[a-zA-Z][a-zA-Z0-9]{0,})$";
		return Pattern.matches(reg,str);
	}

	/*****判断邮箱*****/
	public static boolean checkEmail(String str) {
		String reg = "\\w+([-+.]\\.\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return Pattern.matches(reg, str);
	}

	/*****判断URL*****/
	public static boolean checkUrl(String str) {
		String reg = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";
		return Pattern.matches(reg, str);
	}

	/*****判断电话*****/
	public static boolean checkTelNumber(String str) {
		String reg = "^(\\d+-)?(\\d{3}-\\d{8}|\\d{4}-\\d{7})?(-\\d+)?$";
		return Pattern.matches(reg, str);
	}
	
	/**
	 * 判断移动电话的规则,验证
	 */
	public static boolean isPhone(String phone) {
		String reg = "^1\\d{10}$";
		return Pattern.matches(reg, phone);
	}

	/*****判断邮编*****/
	public static boolean checkPostcode(String str) {
		String reg = "^\\d{6}$";
		return Pattern.matches(reg, str);
	}

	/*****判断字符串是否超出长度*****/
	public static boolean checkCheckLength(String str, int i) {
		if (str.length() == 0 || i < 0) {
			return false;
		}
		if (str.length() > i) {
			return false;
		}
		return true;
	}

	/*****判断第一个是否是字母*****/
	public static boolean checkFirstIsChar(String str) {
		if (str.length() == 0) {
			return false;
		}
		if ((str.charAt(0) >= 'a' && str.charAt(0) <= 'z')
				|| (str.charAt(0) >= 'A' && str.charAt(0) <= 'Z')) {
			return true;
		} else {
			return false;
		}
	}

	/*****判断日期*****/
	public static boolean checkDate(String str) {
		String reg;

		/*****
		 * 实现了润年的判断
		 * 
		 * *****/
		reg = "^((\\d{2}(([02468][048])|([13579][26]))[\\-|\\/|\\s]?"
				+ "((((0?[13578])|(1[02]))[\\-|\\/|\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"
				+ "(((0?[469])|(11))[\\-|\\/|\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
				+ "(0?2[\\-|\\/|\\s]?((0?[1-9])|([1-2][0-9])))))|"
				+ "(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-|\\/|\\s]?"
				+ "((((0?[13578])|(1[02]))[\\-|\\/|\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"
				+ "(((0?[469])|(11))[\\-|\\/|\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
				+ "(0?2[\\-|\\/|\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))$";
		// 年-(/)月-(/)日

		return Pattern.matches(reg, str);
	}

	/*****判断时间*****/
	public static boolean checkTime(String str) {
		String reg = "^((0([0-9]{1}))|(((1([0-2]{1})))|((1([0-9]{1}))|(2([0-3]{1}))))):[0-5][0-9]{1}:[0-5][0-9]{1}$";
		return Pattern.matches(reg, str);
	}

	/*****判断日期时间*****/
	public static boolean checkDateTime(String str) {
		// 格式年/(-)月/(-)日
		String reg = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?"
				+ "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"
				+ "(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?"
				+ "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"
				+ "(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"
				+ "(\\s((((0?[0-9])|([1-2][0-3]))|((0?[0-9])|(1[0-9])|(2[0-3])))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";

		return Pattern.matches(reg, str);
	}

	/*****判断身份证*****/
	public static boolean checkIDCard(String str) {		
		String reg;
		/*****判断18位身份证******/
		if (str.length() == 18) {
			reg = "^[\\d]{6}((\\d{2}(([02468][048])|([13579][26]))"
					+ "((((0[13578])|(1[02]))((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))"
					+ "((0[1-9])|([1-2][0-9])|(30)))|(02((0[1-9])|([1-2][0-9])))))|"
					+ "(\\d{2}(([02468][1235679])|([13579][01345789]))((((0[13578])|(1[02]))"
					+ "((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))((0[1-9])|([1-2][0-9])|(30)))|"
					+ "(02((0?[1-9])|(1[0-9])|(2[0-8]))))))[\\d]{3}[x|X|\\d]$";
			return Pattern.matches(reg, str);
		} else if (str.length() == 15) {
			/*****判断15位身份证*****/
			reg = "^[\\d]{6}(((([02468][048])|([13579][26]))"
					+ "((((0[13578])|(1[02]))((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))"
					+ "((0[1-9])|([1-2][0-9])|(30)))|(02((0[1-9])|([1-2][0-9])))))|"
					+ "((([02468][1235679])|([13579][01345789]))((((0[13578])|(1[02]))"
					+ "((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))((0[1-9])|([1-2][0-9])|(30)))|"
					+ "(02((0?[1-9])|(1[0-9])|(2[0-8]))))))[\\d]{3}$";
			return Pattern.matches(reg, str);
		} else {
			return false;
		}
	}

	/*****判断IP*****/
	public static boolean checkIP(String str) {
		if (str.length() < 7 || str.length() > 15) {
			return false;
		}
		String reg = "";
		reg = "((25[0-5]{1})|(2[0-4]{1}[0-9]{1})|(1[0-9]{2})|([1-9]{1}[0-9]{1})|([1-9]{1}))\\."+
          "(((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9][0-9])|([0-9]))\\.){2}" +
          "((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9][0-9])|([0-9]))";
		return Pattern.matches(reg, str);
	}
	
	/*****只能输入中文*****/
	public static boolean checkChinese(String str) {
		if(str.length() == 0) 
			return false;
		
		String reg = "^[\u4e00-\u9fa5]*$";
		return Pattern.matches(reg,str);
	}
	
}
