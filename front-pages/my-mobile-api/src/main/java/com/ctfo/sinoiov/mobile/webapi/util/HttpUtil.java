package com.ctfo.sinoiov.mobile.webapi.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * HTTP请求工具类
 * @author XUBAO
 *
 */
public class HttpUtil {
	private static final Logger logger = Logger.getLogger(HttpUtil.class);
	public static String HTTP_ENCODE = "UTF-8";
	public static String HTTP_METHOD_POST = "POST";
	private static String check = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";

	public static String sendHttpPostResquest(String httpUrlStr, String context) {
		return sendHttpPostResquest(httpUrlStr, context, null);
		
	}
	
	public static String sendHttpPostResquest(String httpUrlStr, Map<String,String> paramMap) {
		//设置传送参数
		StringBuilder paramSB = new StringBuilder();
		if(paramMap != null){
			for (Entry<String,String> entry : paramMap.entrySet()) {
				paramSB.append(entry.getKey()).append("=").append(entry.getValue()==null?"":entry.getValue()).append("&");
			}
		}
		return sendHttpPostResquest(httpUrlStr, paramSB.toString(), null);
	}
	
	public static String sendHttpPostResquest(String httpUrlStr, String context, Map<String,String> headerMap) {
		logger.info("远程调用方法开始!URL:" + httpUrlStr);
		StringBuffer sbTotal = new StringBuffer();
		HttpURLConnection httpCon = null;
		PrintWriter out = null;
		BufferedReader reader =null;
		try {
		    if(httpUrlStr ==null || "".equals(httpUrlStr)){
		        throw new Exception("url 为空!");
		    }
			httpCon = (HttpURLConnection) new URL(httpUrlStr).openConnection();
			//设置超时时间 20秒获取不到,即视为调用失败
			httpCon.setConnectTimeout(5*1000);
			httpCon.setRequestMethod(HTTP_METHOD_POST);// 提交模式
			httpCon.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是false;
			httpCon.setDoInput(true);
			// Post 请求不能使用缓存
			httpCon.setUseCaches(false);
			httpCon.addRequestProperty("Accept-Charset", HTTP_ENCODE);
			httpCon.addRequestProperty("contentType", HTTP_ENCODE);
			if(headerMap != null){
				for (Entry<String,String> entry : headerMap.entrySet()) {
					httpCon.addRequestProperty(entry.getKey(),entry.getValue());
				}
			}
			
//			if(CookieStr != null){
//				httpCon.setRequestProperty("cookie", CookieStr);
//			}
			
			httpCon.connect();
			
			out = new PrintWriter(new OutputStreamWriter(
					httpCon.getOutputStream(), HTTP_ENCODE));
			out.write(context);
			out.flush();
			
			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String currentLine;
			reader = new BufferedReader(new InputStreamReader(
					httpCon.getInputStream(), HTTP_ENCODE));
			Map<String, List<String>> hearders =  httpCon.getHeaderFields();
			if(logger.isInfoEnabled()){
				StringBuffer sb = new StringBuffer();
				sb.append("\nhttp请求参数为:" + context);
				sb.append("\nhttp响应状态码为:" + httpCon.getResponseCode());
				sb.append("\nhttp响应消息为:" + httpCon.getResponseMessage());
				sb.append("\nhttp响应消息头为:");
				for( Entry<String, List<String>> entry :  hearders.entrySet()){
					sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
				}
				logger.info(sb.toString());
			}
			while ((currentLine = reader.readLine()) != null) {
				sbTotal.append(currentLine);
			}
		} catch (Exception e) {
			logger.warn( "远程调用方法异常!URL:"+ httpUrlStr, e);
		}finally{
			//关闭流
			closeStreamObject(out, reader);
			//关闭HTTP请求
			try {
				logger.info("关闭HTTP请求!");
				if(httpCon != null) httpCon.disconnect();
			} catch (Exception e) {
				logger.warn( "SYS_ERE", e);
			}
		}
		String result = sbTotal.toString();
		logger.info("远程调用方法结束!URL:" + httpUrlStr + "\n 响应结果为: " + result);
		return result;
	}
	
	public static String getIpAddr(ServletRequest request) {
        //request.getRemoteAddr()这种方法在大部分情况下都是有效的。但是在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了
        //经过代理以后，由于在客户端和服务之间增加了中间层，因此服务器无法直接拿到客户端的IP，
        //服务器端应用也无法直接通过转发请求的地址返回给客户端。但是在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR 
        //信息用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址
		if (request == null)
			return "";
	    String ip = null;    
	    if(request instanceof HttpServletRequest){
	        HttpServletRequest httpRequest = (HttpServletRequest) request;
	        ip = httpRequest.getHeader("x-forwarded-for");
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
	        		|| ip.equals("127.0.0.1")) {
	        	ip = httpRequest.getHeader("Proxy-Client-IP");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
	        		|| ip.equals("127.0.0.1")) {
	        	ip = httpRequest.getHeader("WL-Proxy-Client-IP");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
	        		|| ip.equals("127.0.0.1")) {
	        	ip = httpRequest.getRemoteAddr();
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
	        		|| ip.equals("127.0.0.1")) {
	        	ip = httpRequest.getHeader("http_client_ip");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
	        		|| ip.equals("127.0.0.1")) {
	        	ip = httpRequest.getHeader("HTTP_X_FORWARDED_FOR");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        	ip = "127.0.0.1";
	        }
       }
		if (ip.contains(",")) {
			int i = ip.indexOf(",");
			ip = ip.substring(0, i);
		}
       return ip;    
    }
	
	/**
	 * 关闭输入流对象
	 * @param stream
	 */
	public static void closeStreamObject(Object...streams){
		//遍历流对象,调用关闭方法
		for (Object stream : streams) {
			if(stream == null) continue;
			try {
				logger.info("关闭流对象:" + stream);
				Method closeMethod = ReflectUtil.findMethod(stream.getClass(), "close", new Class[]{});
				closeMethod.invoke(stream, new Object[]{});
			} catch (Exception e) {
				logger.warn("关闭流对象异常", e);
			}
		}
	}
	/**
	 * 验证字符串是否为URL
	 * @param checkURL
	 * @return
	 */
	public static boolean checkURLAvailable(String checkURL){
		if(Pattern.matches(check,checkURL)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取请求参数
	 * @param request
	 * @return
	 */
	public static String getParmaFormResquest(HttpServletRequest request){
		if(request == null){
			return "this parma request is null!";
		}
		Enumeration<String> enumeration = request.getParameterNames();
		String parmaName = null;
		String result = " { parameter :{ ";
		
		boolean hasValue = false;
		while(enumeration.hasMoreElements()){
			parmaName = enumeration.nextElement();
			Object[] values = request.getParameterValues(parmaName);
			result += "\'" + parmaName + "\'" + ":" + "\'" + Arrays.toString(values) + "\'" + ",";
			hasValue = true;
		}
		if(hasValue){
			result = result.substring(0, result.length()-1);
		}
		result +=" }";
		enumeration =request.getAttributeNames();
		result += "}, requestURL: \'" + request.getRequestURL() + "\', ip:\'" + HttpUtil.getIpAddr(request) +"\'";
		result +="  }";
		return result;
	}
	
	/**
	 * 将请求转换为json串
	 * @param request
	 * @return
	 */
	public static String converterResquestToJsonString(HttpServletRequest request){
		if(request == null){
			return "this parma request is null!";
		}
		Enumeration<String> enumeration = request.getParameterNames();
		String parmaName = null;
		String result = " { parameter :{ ";
		
		boolean hasValue = false;
		while(enumeration.hasMoreElements()){
			parmaName = enumeration.nextElement();
			Object[] values = request.getParameterValues(parmaName);
			result += "\'" + parmaName + "\'" + ":" + "\'" + Arrays.toString(values) + "\'" + ",";
			hasValue = true;
		}
		if(hasValue){
			result = result.substring(0, result.length()-1);
		}
		result +=" }";
		enumeration =request.getAttributeNames();
		hasValue = false;
		result +=" ,attribute: {";
		while(enumeration.hasMoreElements()){
			parmaName = enumeration.nextElement();
			Object values = request.getAttribute(parmaName);
			if(values == null){
				values= "";
			}
			result += "\'" + parmaName + "\'" + ":" + "\'" + values.toString().replace("'", "\\'") + "\'" + ",";
			hasValue = true;
		}
		if(hasValue){
			result = result.substring(0, result.length()-1);
		}
		result +=" }";
		enumeration =request.getHeaderNames();
		hasValue = false;
		result += ", header: {";
		while(enumeration.hasMoreElements()){
			parmaName = enumeration.nextElement();
			String values = request.getHeader(parmaName);
			if(values == null){
				values= "";
			}
			result += "\'" + parmaName + "\'" + ":" + "\'" + values.toString().replace("'", "\\'") + "\'" + ",";
			hasValue = true;
		}
		if(hasValue){
			result = result.substring(0, result.length()-1);
		}
		result += "}, requestURL: \'" + request.getRequestURL() + "\', ip:\'" + HttpUtil.getIpAddr(request) +"\'";
		result +="  }";
		return result;
	}
	
	public static void sendJsonResponse(HttpServletResponse res, String context) {
		PrintWriter out = null;
		try {
			out = new PrintWriter( new OutputStreamWriter(res.getOutputStream(), "UTF-8"));
			res.setContentType("application/json;charset=UTF-8");
			out.write(context);
		} catch (Exception e) {
			//ignore this exception
			logger.warn( "发送json response 发送异常!", e);
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (Exception e2) {
					//ignore this exception
					logger.warn( "关闭发送json response 输出流异常!", e2);
				}
			}
		}
	}
}
