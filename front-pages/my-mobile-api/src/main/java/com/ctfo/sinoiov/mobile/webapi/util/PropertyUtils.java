package com.ctfo.sinoiov.mobile.webapi.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class PropertyUtils {
	static Properties properties = null;

	static {

		try {
			InputStream in = null;
			try {
				in = PropertyUtils.class.getClassLoader().getResourceAsStream("system.properties");
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

	public static String getValueByKey(String key) {
		return getValueByKey(key, null);
	}

	public static boolean getValueByKeyWithDefualt(String key, boolean defaultValue) {
		return getValueByKey(key) == null ? false : Boolean.valueOf(getValueByKey(key).trim());
	}

}
