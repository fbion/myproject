package com.ctfo.upp.gatewayservice.util.base;

import java.util.Date;
import java.util.Random;

public class RandomUtil {

	/** 特殊字符 */
	public static final String PERCENT = "%";
	/** 手机号码中间四位屏蔽用字符 */
	public static final String FOUR_STAR = "****";

	/** 英文 */
	public static final String LETTER_ONLY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** 数字 */
	public static final String NUMBER_ONLY = "0123456789";

	/**
	 * 得到n位长度的随机数
	 * @param n 随机数的长度
	 * @return 返回 n位的随机整数
	 */
	public static String getRandomNumber(int n) {
		int temp = 0;
		int min = (int) Math.pow(10, n - 1);
		int max = (int) Math.pow(10, n);
		Random rand = new Random();

		while (true) {
			temp = rand.nextInt(max);
			if (temp >= min)
				break;
		}

		return String.valueOf(temp);
	}

	public static String getRandomNumberWithDate(int n) {
		return String.valueOf(new Date().getTime()) + getRandomNumber(n);
	}

	/**
	 * 生成随机字符串
	 * @param type (1：纯数字，2：纯英文，3：数字英文，4：数字英文特殊符号，默认：1)
	 * @param length (默认：6位)
	 * @param contents 可选择输入参数，自定义取值范围；如果自定义取值范围，那么type可以为null
	 * @return length位数的type类型的任意组合
	 */
	public static String getRandomString(Integer type, Integer length, String... contents) {
		type = type == null ? 1 : type;
		length = length == null ? 6 : length;
		StringBuffer buffer = null;
		if (contents != null && contents.length > 0) {
			buffer = new StringBuffer(contents[0]);
		} else {
			if (type == 1)
				buffer = new StringBuffer(NUMBER_ONLY);
			else if (type == 2)
				buffer = new StringBuffer(LETTER_ONLY);
			else if (type == 3)
				buffer = new StringBuffer(NUMBER_ONLY + LETTER_ONLY);
			else if (type == 4)
				buffer = new StringBuffer(NUMBER_ONLY + LETTER_ONLY + PERCENT);
			else
				buffer = new StringBuffer(NUMBER_ONLY);
		}
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

}
