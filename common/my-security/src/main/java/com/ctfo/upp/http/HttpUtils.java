package com.ctfo.upp.http;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.http.httpclient.HttpClient4Util;
import com.ctfo.upp.http.httpclient.HttpParameter;
import com.ctfo.upp.http.httpclient.HttpResp;
import com.ctfo.upp.security.ConvertUtils;

public abstract class HttpUtils {

	private static Log logger = LogFactory.getLog(HttpUtils.class);

	public static final String SUCCESS = "1";
	public static final String ERROR = "-1";
	public static final String CHARSET = "UTF-8";

	/**
	 * 非加密方法：发送不加密，接收不解密
	 * @throws Exception
	 */
	public static String sendRequest(String json, String url)throws Exception{
		try{			
			logger.debug("----->>>>>发送内容url:"+url);
			logger.debug("----->>>>>发送内容params:"+json);
			//发送
			String result = sendPost(url, setParameterValue(json));

			logger.debug("----->>>>>返回内容:"+result);

			return result;

		}catch(Exception e){
			logger.error("发送错误",e);
			throw new Exception("发送错误:"+e.getLocalizedMessage());
		}

	}
	/**
	 * 非加密方法：发送不加密，接收不解密
	 * @throws Exception
	 */
	public static String sendRequest(String json, String url, boolean post)throws Exception{
		try{			
			logger.debug("----->>>>>发送内容url:"+url);
			logger.debug("----->>>>>发送内容params:"+json);
			//发送
			String result = send(url, setParameterValue(json), post);

			logger.debug("----->>>>>返回内容:"+result);

			return result;

		}catch(Exception e){
			logger.error("发送错误",e);
			throw new Exception("发送错误:"+e.getLocalizedMessage());
		}

	}


	/**
	 * 接收解密方法：发送不加密，接收解密
	 * @param json
	 * @param url
	 * @param privatekey
	 * @param publickey
	 * @return
	 * @throws Exception
	 */
	public static String sendRequest(String json, String url, String privatekey, String publickey)throws Exception{

		try{

			logger.debug("-----－－－－－－－－－－－sendRequest－－－－－－－－－－－－－－－－－－－－");
			logger.debug("----->>>>>发送url:"+url);
			logger.debug("----->>>>>发送json:"+json);

			//发送
			json = sendPost(url, setParameterValue(json));

			JSONObject jsonMap = JSONObject.fromObject(json);
			
			String merchantaccount = jsonMap.get("merchantcode")!=null?jsonMap.getString("merchantcode"):jsonMap.get("merchantaccount")!=null?jsonMap.getString("merchantaccount"):"";
			
			//解密
			json = decodeParamJson(json, privatekey, publickey, merchantaccount);
			
			logger.debug("----->>>>>返回内容:"+json);
			logger.debug("-----－－－－－－－－－－－sendRequest－－－－－－－－－－－－－－－－－－－－");

			return json;

		}catch(Exception e){
			logger.error("调用接口错误!",e);
			throw new Exception(e.getLocalizedMessage());
		}

	}

	/**
	 * 发送加密，接收解密
	 * @param json
	 * @param url
	 * @param privatekey
	 * @param publickey
	 * @param merchantcode
	 * @return
	 * @throws Exception
	 */
	public static String sendRequest(String json, String url, String privatekey, String publickey, String merchantaccount)throws Exception{

		try{

			logger.debug("-----－－－－－－－－－－－sendRequest－－－－－－－－－－－－－－－－－－－－");
			logger.debug("----->>>>>发送url:"+url);
			logger.debug("----->>>>>发送内容:json:"+json);

			if(StringUtils.isBlank(merchantaccount))
				throw new Exception("商户编号不能为空!");

			//加密
			//json = ConvertUtils.encodeReturnJson(json, privatekey, publickey, merchantcode);
			json = encodeReturnJson(json, privatekey, publickey, merchantaccount);

			//发送
			json = sendPost(url, setParameterValue(json));

			//解密
			json = decodeParamJson(json, privatekey, publickey, merchantaccount);

			logger.debug("----->>>>>返回内容:"+json);
			logger.debug("-----－－－－－－－－－－－sendRequest－－－－－－－－－－－－－－－－－－－－");

			return json;

		}catch(Exception e){
			logger.error("调用接口错误!",e);
			throw new Exception(e.getLocalizedMessage());
		}

	}

	/**
	 * 发送加密，接收解密
	 */
	public static String sendRequest(String json, String url, String privatekey, String publickey, String merchantaccount, boolean post)throws Exception{

		try{

			logger.debug("-----－－－－－－－－－－－sendRequest－－－－－－－－－－－－－－－－－－－－");
			logger.debug("----->>>>>发送url:"+url);
			logger.debug("----->>>>>发送内容:json:"+json);

			if(StringUtils.isBlank(merchantaccount))
				throw new Exception("商户编号不能为空!");

			//加密
			json = encodeReturnJson(json, privatekey, publickey, merchantaccount);

			//发送
			json = send(url, setParameterValue(json), post);
						
			//解密
			json = decodeParamJson(json, privatekey, publickey, merchantaccount);

			logger.debug("----->>>>>返回内容:"+json);
			logger.debug("-----－－－－－－－－－－－sendRequest－－－－－－－－－－－－－－－－－－－－");

			return json;

		}catch(Exception e){
			logger.error("调用接口错误!",e);
			throw new Exception(e.getLocalizedMessage());
		}

	}

	
	
	
	
	


	/**
	 * 加密方法
	 * @param json
	 * @param privatekey
	 * @param publickey
	 * @param merchantaccount
	 * @return
	 * @throws Exception
	 */
	public static String encodeReturnJson(String json, String privatekey, String publickey, String merchantaccount)throws Exception{

		//设置商户编号
		if(merchantaccount.length()==21){
			if(Long.parseLong(merchantaccount.substring(0, 8)) < Long.parseLong("201503120000000000000".substring(0, 8))){
				json = ConvertUtils.encodeReturnJson_1_0_0(json, privatekey, publickey, merchantaccount);
			}else{
				json = ConvertUtils.encodeReturnJson(json, privatekey, publickey);
				json = json.substring(0,json.length()-1) + ",\"merchantcode\":\""+merchantaccount+"\"}";
			}
		}else if("YB".equals(merchantaccount.substring(0, 2)) || merchantaccount.length()==11){
			json = ConvertUtils.encodeReturnJson(json, privatekey, publickey);
			json = json.substring(0,json.length()-1) + ",\"merchantaccount\":\""+merchantaccount+"\"}";
		}
		return json;
	}
	
	/**
	 * 解密json参数,如果result不等1,则不解密
	 * @param json
	 * @param privatekey
	 * @param publickey
	 * @return
	 * @throws Exception
	 */
	public static String decodeParamJson(String json, String privatekey, String publickey, String merchantaccount)throws Exception{
				
		JSONObject jsonMap = JSONObject.fromObject(json);

		if(jsonMap.get("data")==null || jsonMap.get("encryptkey")==null){
			logger.warn("响应参数不正确:"+json);
		}else{
			String data = jsonMap.getString("data");				
			String encryptkey = jsonMap.getString("encryptkey");
			
			if(merchantaccount.length()==21){			
				String result = jsonMap.getString("result");
				if(SUCCESS.equals(result)){
					//解密
					if(Long.parseLong(merchantaccount.substring(0, 8)) < Long.parseLong("201503120000000000000".substring(0, 8))){
						json = ConvertUtils.decodeParamJson_1_0_0(data, encryptkey, privatekey, publickey);
					}else{
						json = ConvertUtils.decodeParamJson(data, encryptkey, privatekey, publickey);	
					}
					json = "{\"result\":\""+SUCCESS+"\",\"data\":"+json+",\"merchantcode\":\""+merchantaccount+"\",\"errorcode\":\"\",\"error\":\"\"}";				
				}
				
			}else if("YB".equals(merchantaccount.substring(0, 2)) || merchantaccount.length()==11){
				
				json = ConvertUtils.decodeParamJson(data, encryptkey, privatekey, publickey);
			}
		}
		
		return json;
	}

	
	
	
	

	/**
	 * 获取收银台URL（加密）
	 * @param json
	 * @param url
	 * @param privatekey
	 * @param publickey
	 * @param merchantcode
	 * @return
	 * @throws Exception
	 */
	public static String getCashierDeductMoneyUrl(String json, String url, String privatekey, String publickey, String merchantaccount) throws Exception{

		try{

			logger.debug("-----－－－－－－－－－－－getCashierDeductMoneyUrl－－－－－－－－－－－－－－－－－－－－");
			logger.debug("----->>>>>发送url:"+url);
			logger.debug("----->>>>>发送json:"+json);

			//String params = getMyEncodeReturnJson(json, privatekey, publickey, merchantcode);
			JSONObject temMap = JSONObject.fromObject(json);
			String userId = temMap.get("userId")==null?"": temMap.getString("userId");
			String accountNo = temMap.get("accountNo")==null?"": temMap.getString("accountNo");
			String callbackURL = temMap.get("callbackURL")==null?"": temMap.getString("callbackURL");
			//String userLoginName = temMap.get("userLoginName")==null?"": temMap.getString("userLoginName");

			String merchantOrderNo = temMap.get("merchantOrderNo")==null?"":temMap.getString("merchantOrderNo");
			String tradeExternalNo = temMap.get("tradeExternalNo")==null?"":temMap.getString("tradeExternalNo");
			String merchantOrderRemark = temMap.get("merchantOrderRemark")==null?"":temMap.getString("merchantOrderRemark");
			String merchantOrderAmount = temMap.get("merchantOrderAmount")==null?"":temMap.getString("merchantOrderAmount");
			

			if(StringUtils.isBlank(merchantaccount)) throw new Exception("商户编号不能为空");
			if(StringUtils.isBlank(userId)) throw new Exception("用户ID不能为空");
			if(StringUtils.isBlank(accountNo)) throw new Exception("资金账号不能为空");
			if(StringUtils.isBlank(callbackURL)) throw new Exception("回调地址不能为空");
			//if(StringUtils.isBlank(userLoginName)) throw new Exception("用户登录名不能为空");
			if(StringUtils.isBlank(merchantOrderNo)) throw new Exception("业务订单号不能为空");
			if(StringUtils.isBlank(tradeExternalNo)) throw new Exception("交易流水号不能为空");
			if(StringUtils.isBlank(merchantOrderAmount)) throw new Exception("订单金额不能为空");

			//加密
			json = encodeReturnJson(json, privatekey, publickey, merchantaccount);

			JSONObject jsonMap = JSONObject.fromObject(json);
			String data = jsonMap.getString("data");
			String encryptkey = jsonMap.getString("encryptkey");

			if(StringUtils.isBlank(data) || StringUtils.isBlank(encryptkey) || StringUtils.isBlank(merchantaccount))
				throw new Exception("加密后参数不合法或错误!");

			String params = "data=" + URLEncoder.encode(data, CHARSET);
			params += "&encryptkey="+ URLEncoder.encode(encryptkey, CHARSET);
			params += "&merchantcode="+ URLEncoder.encode(merchantaccount, CHARSET);			

			if(StringUtils.isNotBlank(merchantOrderNo))
				params += "&merchantOrderNo="+ URLEncoder.encode(merchantOrderNo, CHARSET);					
			if(StringUtils.isNotBlank(merchantOrderRemark))
				params += "&merchantOrderRemark="+ URLEncoder.encode(merchantOrderRemark, CHARSET);					
			if(StringUtils.isNotBlank(merchantOrderAmount))
				params += "&merchantOrderAmount="+ URLEncoder.encode(merchantOrderAmount, CHARSET);	
			if(StringUtils.isNotBlank(userId))
				params += "&userId="+ URLEncoder.encode(userId, CHARSET);

			url += "?" + params;			

			logger.debug("----->>>>>返回内容:"+url);
			logger.debug("-----－－－－－－－－－－－getCashierDeductMoneyUrl－－－－－－－－－－－－－－－－－－－－");

		}catch(Exception e){
			logger.error("调用接口错误!",e);
			throw new Exception("调用接口错误!");
		}

		return url;
	}

	public static String sendGet(String url, HttpParameter httpParameter) throws Exception {
		return send(url, httpParameter, false);
	}

	public static String sendPost(String url, HttpParameter httpParameter) throws Exception{
		return send(url, httpParameter, true); 
	}

	/**
	 * 向指定 URL 发送请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param httpParameter
	 *            请求参数
	 * @return 远程资源的响应结果
	 */
	public static String send(String url, HttpParameter httpParameter, boolean post) throws Exception{

		try {

			int timeout = 1800000;//超时时间

			HttpClient4Util util = null;
			if (url.indexOf("https") >= 0) {
				util = new HttpClient4Util(timeout, true);
			} else {
				util = new HttpClient4Util(timeout, false);
			}

			HttpResp httpResp = new HttpResp();
			if (post) {
				httpResp = util.doPost(url, httpParameter, CHARSET);
			} else {
				httpResp = util.doGet(url, httpParameter, CHARSET);
			}

			return httpResp.getText(CHARSET);

		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 发送请求出现异常！ <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", e);
			throw e;
		}
	}

	public static HttpParameter setParameterValue(String json) throws Exception{
		JSONObject jsonMap = JSONObject.fromObject(json);
		@SuppressWarnings("unchecked")
		Set<String> set = jsonMap.keySet();
		HttpParameter httpParameter = new HttpParameter();
		String value ="";
		for(String key : set){
			value = jsonMap.get(key)==null?"":jsonMap.getString(key);
			if(StringUtils.isNotBlank(value)){
				httpParameter.add(key, value);
			}
		}
		return httpParameter;
	}


	public static String getIP_Inet4Address(){
		String ip = "127.0.0.1";
		try {
			Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress inetAddress = null;
			while (allNetInterfaces.hasMoreElements()){
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration<?> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()){
					inetAddress = (InetAddress) addresses.nextElement();
					if (inetAddress != null && inetAddress instanceof Inet4Address && !inetAddress.getHostAddress().equals("127.0.0.1")){
						ip = inetAddress.getHostAddress();
					} 
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return ip;
	}

	private HttpUtils(){}

	public static void main(String[] arg){		

		//testSendRequest();
		//testReceiveRequest();
		//testReceiveRequest();
		//testResponse();

	}


}
