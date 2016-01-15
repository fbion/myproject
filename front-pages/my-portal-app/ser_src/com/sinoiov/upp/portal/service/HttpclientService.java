package com.sinoiov.upp.portal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinoiov.upp.portal.util.ProtalConvertUtils;

public abstract class HttpclientService {
	
	private static Log logger = LogFactory.getLog(HttpclientService.class);
	
	protected static Map<String,String> mymap = null;
	
	
	
	protected String sendRequestBank(Map<String,String> bean, String url) throws Exception{

		try{
			
			logger.info("----->>>>>url:"+url);
			
			url = ProtalConvertUtils.getValue("getBackJson") + url;
			
			String cardNum = bean.get("cardNum");
			
			String cha_id = bean.get("cha_id");
			
			String json = sendRequest(url, cardNum, cha_id);
			
			
			logger.info("----->>>>>json:"+json);
			
			return json;

		}catch(Exception e){
			logger.error("解密或解析参数错误",e);
			throw new Exception("解密或解析参数错误!");
		}	
	}
	private String sendRequest(String url,String cardNum,String cha_id) throws Exception{
		try{
			
			logger.info("-----－－－－－－－－－－－sendRequest－－－－－－－－－－－－－－－－－－－－");
			logger.info("----->>>>>发送url:"+url);
			
			String params = "cardNum=" + URLEncoder.encode(cardNum, "UTF-8");
			params += "&2cha_id="+ URLEncoder.encode(cha_id, "UTF-8");
			
			//发送
			String json = sendPost(url, params);
			
		
			
			logger.info("----->>>>>返回内容:"+json);
			logger.info("-----－－－－－－－－－－－sendRequest－－－－－－－－－－－－－－－－－－－－");
			
			return json;
			
			}catch(Exception e){
				logger.error("调用接口错误!",e);
				throw e;
			}
	}
	public static String sendPost(String url, String param) throws Exception{
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());

			// 发送请求参数
			out.print(param);

			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 发送POST请求出现异常！ <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", e);
			throw e;
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 关闭输入流出现异常！ <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", ex);
			}
		}
		return result.toString();
	}

	

}
