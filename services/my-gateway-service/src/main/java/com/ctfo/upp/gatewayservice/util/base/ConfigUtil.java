package com.ctfo.upp.gatewayservice.util.base;

import com.ctfo.upp.http.HttpUtils;
import com.sinoiov.upp.config.DefaultConfig;

public class ConfigUtil{
	
	private static String myURI = "GateWayService";

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
		
		return DefaultConfig.getPublicKey(merchantcode, myURI);

	}

	/**
	 * 根据编号获取私钥
	 * @param merchantcode
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey()throws Exception{
	
		String merchantcode = getValue("UPP_GATEWAY_MER_ID");
			
		return DefaultConfig.getPrivateKey(merchantcode, myURI);
			 
	}
	
	
	public static void main(String[] args){
		
		//String url = "http://192.168.15.221:5280/AccountService/gateway/paramsCallback/param";
		String url = "http://pay.sinoiov.net/accountService/gateway/paramsCallback/param";
		
		String json = "{\"type\":\"param\", \"key\":\"FACADE_ADVISOR_HANDLER_THREAD_NUM\"}";
		
		try {
			
			String value = HttpUtils.sendRequest(json, url);
		
			System.out.println("---"+value);
			
//			JSONObject jsonMap = JSONObject.fromObject(value);
//			
//			KeyBean keybean = new KeyBean();
//			keybean.setMerchantCode(jsonMap==null|| jsonMap.get("merchantCode")==null?"":jsonMap.getString("merchantCode"));
//			keybean.setMerchantName(jsonMap==null|| jsonMap.get("merchantName")==null?"":jsonMap.getString("merchantName"));
//			keybean.setPrivateKey(jsonMap==null|| jsonMap.get("privateKey")==null?"":jsonMap.getString("privateKey"));
//			keybean.setPublicKey(jsonMap==null|| jsonMap.get("publicKey")==null?"":jsonMap.getString("publicKey"));
//			keybean.setStatus(jsonMap==null|| jsonMap.get("status")==null?"":jsonMap.getString("status"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
