package com.sinoiov.upp.portal.service;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.http.HttpUtils;
import com.sinoiov.upp.portal.util.ProtalConvertUtils;

public abstract class AbstractService {
	
	private static Log logger = LogFactory.getLog(AbstractService.class);
	
	protected static Map<String,String> mymap = null;
	
	
	
	protected String sendRequest(Object bean, String url) throws Exception{

		try{
			
			logger.info("----->>>>>url:"+url);
			
			url = ProtalConvertUtils.getValue("UPP_INTERFACE_URL") + url;
			
			String interface_code = ProtalConvertUtils.getValue("INTERFACE_CODE");
			
			String publickey = ProtalConvertUtils.getPublicKey(interface_code);
			
			String privatekey = ProtalConvertUtils.getPrivateKey();
			
			String merchantcode = ProtalConvertUtils.getValue("MERCHANT_CODE");
			
			JSONObject jsonMap = JSONObject.fromObject(bean);
			
			String json = jsonMap.toString();
			
			json = HttpUtils.sendRequest(json, url, privatekey, publickey, merchantcode);
			
			
			//保存参数（商户编码， 明文，单次随机数，签名，数据密文，密钥密文）
			logger.info("----->>>>>json:"+json);
			
			return json;

		}catch(Exception e){
			logger.error("解密或解析参数错误",e);
			throw new Exception("解密或解析参数错误!");
		}	
	}
	protected String sendRequestMap(Map<String,String> map, String url) throws Exception{

		try{
			
			logger.info("----->>>>>url:"+url);
			
			url = ProtalConvertUtils.getValue("UPP_INTERFACE_URL") + url;
			
			String interface_code = ProtalConvertUtils.getValue("INTERFACE_CODE");
			
			String publickey = ProtalConvertUtils.getPublicKey(interface_code);
			
			String privatekey = ProtalConvertUtils.getPrivateKey();
			
			String merchantcode = ProtalConvertUtils.getValue("MERCHANT_CODE");
			
			JSONObject jsonObject = new JSONObject();
			map.put("cashierCode", merchantcode);
			String json = jsonObject.fromObject(map).toString();
			json = HttpUtils.sendRequest(json, url,privatekey,publickey);
			
			
			//保存参数（商户编码， 明文，单次随机数，签名，数据密文，密钥密文）
			logger.info("----->>>>>json:"+json);
			
			return json;

		}catch(Exception e){
			logger.error("解密或解析参数错误",e);
			throw new Exception("解密或解析参数错误!");
		}	
	}
	
	

}
