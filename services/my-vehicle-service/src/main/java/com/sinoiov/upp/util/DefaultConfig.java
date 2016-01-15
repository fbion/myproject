package com.sinoiov.upp.util;


public class DefaultConfig{

	//private static Log logger = LogFactory.getLog(DefaultConfig.class);

	private static String myURI = "accountService";

	
	//取系统参数：缓存　－－　数据库　－－　配置文件
	public static String getValue(String key){
		
		return com.sinoiov.upp.config.DefaultConfig.getValue(key, myURI);
		
	}

}
