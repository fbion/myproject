package com.ctfo.sinoiov.mobile.webapi.bean.common;

import java.math.BigDecimal;

/**
 * 定义公共的静态参数/方法
 */
public class PublicStaticParam {

	/**
	 * 返回客户端状态
	 */
	public static String RESULT_SUCCESS = "0"; // 成功

	public static String RESULT_FAIL = "1"; // 失败

	/**
	 * 用于计算
	 */
	public static int CALCULATE_VALUE = 100;

	/**
	 * 分换算成元
	 * 
	 * @param fen
	 * @return
	 */
	public static BigDecimal fen2Yuan(BigDecimal fen) {
		if (fen == null) {
			return new BigDecimal(0);
		}
		BigDecimal yuan = fen.divide(new BigDecimal(CALCULATE_VALUE), 2, BigDecimal.ROUND_HALF_UP);
		return yuan;
	}

	/**
	 * 元换算成分
	 * 
	 * @param yuan
	 * @return
	 */
	public static BigDecimal yuan2Fen(BigDecimal yuan) {
		if (yuan == null) {
			return new BigDecimal(0);
		}
		BigDecimal fen = yuan.multiply(new BigDecimal(CALCULATE_VALUE)).setScale(0, BigDecimal.ROUND_HALF_UP);
		return fen;
	}
}
