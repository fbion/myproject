package com.ctfo.sinoiov.mobile.webapi.util;


/**
 * 支付参数常量类
 * 
 * @author sunchuanfu
 */
public class PayConstants {
	private static String myURI = "mobileApi";

	/**
	 * 取系统参数：先从缓存取　－－　后从数据库取　－－　最后从配置文件取
	 * 
	 * @param key
	 *            配置文件中key
	 * @return
	 */
	public static String getConfigValue(String key) {
		return com.sinoiov.upp.config.DefaultConfig.getValue(key, myURI);
	}

	/**
	 * 根据编号获取公钥
	 */
	public static String getPublicKey(String merchantcode) {
		return com.sinoiov.upp.config.DefaultConfig.getPublicKey(merchantcode, myURI);
	}

	/**
	 * 根据编号获取私钥
	 */
	public static String getPrivateKey(String merchantcode) {
		return com.sinoiov.upp.config.DefaultConfig.getPrivateKey(merchantcode, myURI);
	}

}
