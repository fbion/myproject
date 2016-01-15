package com.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Random;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.ctfo.sinoiov.mobile.webapi.bean.common.Head;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Parameter;
import com.ctfo.sinoiov.mobile.webapi.util.MD5Utils;
import com.ctfo.sinoiov.mobile.webapi.util.PropertyUtils;

public class BaseTest {
	public static String url = "http://127.0.0.1:5280/MobileApi/business/call.html?param=";
//	public static String url = "http://192.168.100.58:7580/business/call.html?param=";
//	public static String url = "http://pay2.sinoiov.net/mobileApi/business/call.html?param=";

	/**
	 * 封装请求参数为JSON格式
	 * 
	 * @param servCode
	 * @param body
	 * @return
	 */
	public static String getParam(String servCode, Object body) {
		Parameter param = new Parameter();
		Head head = new Head();

		head.setServCode(servCode);
		head.setCallTime(String.valueOf(System.currentTimeMillis()));
		head.setSequenceNum(String.valueOf(new Random().nextLong()));

		param.setBody(body);
		param.setHead(head);
		param.setSign(PropertyUtils.getValueByKey("SIGN_KEY"));

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "result", "errorMessage" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject jsonObj = JSONObject.fromObject(param, jsonConfig);
		param.setSign(MD5Utils.getDefaultMd5String(jsonObj.toString()));
		jsonObj = JSONObject.fromObject(param, jsonConfig);
		System.out.println("http请求参数：" + jsonObj.toString());

		return new String(jsonObj.toString().getBytes(Charset.forName("UTF-8")));
	}

	/**
	 * 发送URL请求并返回数据
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getReturnFromUrl(String url) throws Exception {
		System.out.println("请求URL:" + url);
		String result = "";
		URL u = new URL(url);

		URLConnection connection = u.openConnection();
		connection.setRequestProperty("Accept-Charset", "UTF-8");

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		if (in != null) {
			while ((line = in.readLine()) != null) {
				result += line;
			}
		}
		connection.connect();
		result = new String(result.toString().getBytes(Charset.forName("UTF-8")));
		in.close();
		return result;
	}

}
