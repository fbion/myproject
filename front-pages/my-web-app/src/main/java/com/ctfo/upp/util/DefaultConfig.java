package com.ctfo.upp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultConfig{

	private static Log logger = LogFactory.getLog(DefaultConfig.class);

	private static String myURI = "webApp";

	//取系统参数：缓存　－－　数据库　－－　配置文件
	public static String getValue(String key){
		
		return com.sinoiov.upp.config.DefaultConfig.getValue(key, myURI);
		
	}

	/**
	 * 根据编号获取公钥
	 * @param merchantcode
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(String merchantcode)throws Exception{
		
		return com.sinoiov.upp.config.DefaultConfig.getPublicKey(merchantcode, myURI);
		
	}

	/**
	 * 根据编号获取私钥
	 * @param merchantcode
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(){
		
			String merchantcode = getValue("MERCHANT_CODE");
			
			return com.sinoiov.upp.config.DefaultConfig.getPrivateKey(merchantcode, myURI);
	}
	
}
