package com.ctfo.sinoiov.mobile.webapi.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class PropertyImageUtils {
	static Properties properties = null;

	static {

		try {
			InputStream in = null;
			try {
				in = PropertyImageUtils.class.getClassLoader().getResourceAsStream("image.properties");
				properties = new Properties();
				properties.load(in);
			} finally {
				if (in != null) {
					in.close();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置文件值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValueByKey(String key, String defaultValue) {
		try {
			if (StringUtils.isEmpty((String) properties.get(key))) {
				return defaultValue;
			}
		} catch (Throwable e) {
			return defaultValue;
		}

		return ((String) properties.get(key)).trim();
	}

	/**
	 * 获取配置文件值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueByKey(String key) {
		return getValueByKey(key, null);
	}

	/**
	 * 获取配置文件URL值
	 * 
	 * @param key
	 * @return
	 */
	public static String getUrlByKey(String key) {
		return getUrlByKey(key, null);
	}

	/**
	 * 获取配置文件URL值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getUrlByKey(String key, String defaultValue) {
		try {
			if (StringUtils.isEmpty((String) properties.get(key))) {
				return defaultValue;
			}
		} catch (Throwable e) {
			return defaultValue;
		}
		String url = properties.get("WEBAPI_URL") + "";
		String value = ((String) properties.get(key)).trim();
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return url + value;
	}

	public static boolean getValueByKeyWithDefualt(String key, boolean defaultValue) {
		return getValueByKey(key) == null ? false : Boolean.valueOf(getValueByKey(key).trim());
	}

}
