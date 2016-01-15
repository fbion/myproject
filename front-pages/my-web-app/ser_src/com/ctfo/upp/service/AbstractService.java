package com.ctfo.upp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DefaultConfig;

public abstract class AbstractService{
	
	private static Log logger = LogFactory.getLog(AbstractService.class);
	
	protected static final String RESULT_NAME = "result";
	
	protected static final String RESULT_SUCCESS_CODE = "1";
	
	protected static final String RESULT_DATA_NAME = "data";
	
	protected static final String RESULT_DATA_SINGLE_STR_NAME = "returnStr";
	
	/**
	 * 获取远程对象方法
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> X getRemoManager(Class<?> cls){
		return (X)ServiceFactory.getFactory().getService(cls);
	}
	
	public com.ctfo.csm.uaa.intf.support.Operator getOnlieOperator(){
		HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return Converter.getOperator(request);
	}
	
	/**
	 * 处理http请求返回json
	 * @param json
	 * @return
	 */
	protected JSONObject processReturnResult(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jsonMap = JSONObject.fromObject(json);
			if(RESULT_SUCCESS_CODE.equals(jsonMap.get(RESULT_NAME).toString())){
				return (JSONObject)jsonMap.get(RESULT_DATA_NAME);
			}
		}
		return null;
	}
	
	/***
	 * 获取List类型数据
	 * @param json
	 * @return
	 */
	protected JSONArray getJSONArrayResult(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jsonMap = JSONObject.fromObject(json);
			if(RESULT_SUCCESS_CODE.equals(jsonMap.get(RESULT_NAME).toString())){
				return (JSONArray)(((JSONObject)jsonMap.get(RESULT_DATA_NAME)).get(RESULT_DATA_NAME));
			}
		}
		return null;
	}
	
	/**
	 * List<Account> list = (List<Account>)JSONArray.toCollection(dataObj, Account.class);
	 * 
	private final int pageSize = 10;
	private Collection<T> data;
	private int start;
	private int total;
	private String message;
	private Object dataObject;
	
	    "message": "",
        "pageSize": "10",
        "start": "0",
        "total": "216"
        
	 * @param json
	 * @return
	 */
	protected PaginationResult getPaginationResult(String json, Class clazz) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jsonMap = JSONObject.fromObject(json);
			if(RESULT_SUCCESS_CODE.equals(jsonMap.get(RESULT_NAME).toString())){
				JSONObject jsonData = (JSONObject)jsonMap.get(RESULT_DATA_NAME);
				JSONArray jArray = (JSONArray)(( jsonData ).get(RESULT_DATA_NAME));
				if (jArray != null) {
					PaginationResult pr = new PaginationResult();
					List<?> list = (List<?>)JSONArray.toCollection(jArray, clazz);
					pr.setData(list);
					pr.setMessage((String)((jsonData).get("message")));
					pr.setTotal(Integer.valueOf((String)(jsonData).get("total")));
//					pr.s(Integer.valueOf((String)(jsonData).get("pageSize")));
					pr.setStart(Integer.valueOf((String)(jsonData).get("start")));
					return pr;
				}
			}
		}
		return null;
	}
	
	/**
	 * http请求， 处理返回字符串的情况
	 * @param json
	 * @param key
	 * @return
	 */
	protected String getReturnString(String json, String key) {
		JSONObject dataObj = processReturnResult(json);
		if (dataObj != null) {
			Map<String, String> returnMap = (Map<String, String>)JSONObject.toBean(dataObj, Map.class);
			return (String)returnMap.get(key);
		}
		return "";
	}
	
	protected String sendRequest(Object bean, String url) throws Exception{

		try{
			
			logger.info("----->>>>>url:"+url);
			
			url = DefaultConfig.getValue("UPP_INTERFACE_URL") + url;
			
			String interface_code = DefaultConfig.getValue("INTERFACE_CODE");
			
			String publickey = DefaultConfig.getPublicKey(interface_code);
			
			String privatekey = DefaultConfig.getPrivateKey();
			
			String merchantcode = DefaultConfig.getValue("MERCHANT_CODE");
			
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
			
			url = DefaultConfig.getValue("UPP_INTERFACE_URL") + url;
			
			String interface_code = DefaultConfig.getValue("INTERFACE_CODE");
			
			String publickey = DefaultConfig.getPublicKey(interface_code);
			
			String privatekey = DefaultConfig.getPrivateKey();
			
			String merchantcode = DefaultConfig.getValue("MERCHANT_CODE");
			
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
