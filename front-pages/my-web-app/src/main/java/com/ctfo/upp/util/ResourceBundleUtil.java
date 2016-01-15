/**
 * <h3>Class name</h3>
 * <h4>Description</h4>
 * 
 * <h4>Special Notes</h4>
 * 
 * 
 * @ver 0.1
 * @author HollyLee 2010-10-20
 * 
 */
package com.ctfo.upp.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

public class ResourceBundleUtil {
	protected static ResourceBundle systempropertys =ResourceBundle.getBundle("system");
	
	/**
	 * 模板文字参数填充
	 * 
	 * @param template模板文字
	 * @param values参数数组
	 * @return
	 */
	public static String parseMsg(String template, String[] values) {
		if (!StringUtils.isBlank(template) && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (null == values[i])
					values[i] = " ";
				template = template.replaceFirst("\\{" + i + "\\}", values[i]);
			}
		}else{
			for (int i = 0; i < values.length; i++) {
				if (null == values[i])
					values[i] = " ";
				template = template.replaceFirst("\\{" + i + "\\}", "");
			}
		}
		return template;
	}
	
	public static String getSystemString(String key) {
		try {
			return systempropertys.getString(key);
		} catch (Exception e) {
		}

		return key;
	}
	@SuppressWarnings("static-access")
	public static String getSystemString(String key,Object... params)
	{
		try
		{
		String value = systempropertys.getString(key);
		MessageFormat form = new MessageFormat(value);
		return form.format(value,params);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		return "error";
		}
		
	}
	/**
	 * 
	 * @param key 需要获得的key值
	 * @param resource 资源文件名称
	 * @return 返回value值
	 */
	@SuppressWarnings("static-access")
	public static String getString(String key,String resource,Object... params)
	{
   ResourceBundle bundles =ResourceBundle.getBundle(resource);
		try
		{
		String value = bundles.getString(key);
		MessageFormat form = new MessageFormat(value);
		return form.format(value,params);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		return "error";
		}
		
	}
}

