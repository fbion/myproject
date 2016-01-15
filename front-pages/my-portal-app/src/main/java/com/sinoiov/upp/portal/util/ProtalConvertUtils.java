package com.sinoiov.upp.portal.util;

import com.sinoiov.upp.config.DefaultConfig;

public class ProtalConvertUtils{

	//private static Log logger = LogFactory.getLog(CashierConvertUtils.class);
	
	private static String myURI = "portalApp";

	// 取系统参数：缓存　－－　数据库　－－　配置文件
		public static String getValue(String key) {
			
			return DefaultConfig.getValue(key, myURI);
			
		}
		
	/**
	 * 根据编号获取公钥
	 * 
	 * @param merchantcode
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(String merchantcode) {
		
		return DefaultConfig.getPublicKey(merchantcode, myURI);
		
	}

	/**
	 * 获取私钥
	 */
	public static String getPrivateKey() {
		
		String merchantcode = getValue("PORTAL_MERCHANT_CODE");
		return DefaultConfig.getPrivateKey(merchantcode, myURI);
		
	}

}
