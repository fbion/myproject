package com.ctfo.upp.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;


public class RequestParamsUtil {

	public static void bindParams(Object obj, HttpServletRequest request,String reqParamerPrefix){
		Class<? extends Object> controlerClass=obj.getClass();
		Method[] controlerMethods = controlerClass.getMethods();
		for (int i = 0; i < controlerMethods.length; i++) {
			if(controlerMethods[i].getName().startsWith("set")){
				Method method = controlerMethods[i];
				try {
					invokSetMethod(method,obj,request,reqParamerPrefix);
				} catch (Exception e) {
				}
				
			}
		}
	}
	@SuppressWarnings("all")
	private static void invokSetMethod(Method method,Object obj,HttpServletRequest request,String reqParamerPrefix) 
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException, SecurityException, NoSuchMethodException, InstantiationException, ClassNotFoundException{
		String paramType = method.getParameterTypes()[0].getName();
		String fieldName = getFieldNameBySetAndGetMethod(method.getName());
		if(StringUtils.isBlank(reqParamerPrefix)){
			reqParamerPrefix=fieldName;
		}else{
			reqParamerPrefix=reqParamerPrefix+"."+fieldName;
		}
		String value=request.getParameter(reqParamerPrefix);
		//以下可以确认value类型 
		if("int".equals(paramType) && NumberUtils.isDigits(value)){ 
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Integer.valueOf(value).intValue()); 
        }else if("long".equals(paramType) && NumberUtils.isDigits(value)){
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Long.valueOf(value).longValue()); 
        }else if("short".equals(paramType) && NumberUtils.isDigits(value)){
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Short.valueOf(value).shortValue()); 
        }else if("float".equals(paramType) && NumberUtils.isNumber(value)){ 
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Float.valueOf(value).floatValue()); 
        }else if("double".equals(paramType) && NumberUtils.isNumber(value)){ 
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Double.valueOf(value).doubleValue()); 
        }else if(String.class.getName().equals(paramType)){ 
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj,value);
        }else if(Integer.class.getName().equals(paramType) && NumberUtils.isDigits(value)){ 
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Integer.valueOf(value)); 
        }else if(Long.class.getName().equals(paramType) && NumberUtils.isDigits(value)){
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Long.valueOf(value)); 
        }else if(Short.class.getName().equals(paramType) && NumberUtils.isDigits(value)){
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Short.valueOf(value)); 
        }else if(Float.class.getName().equals(paramType) && NumberUtils.isNumber(value)){ 
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Float.valueOf(value)); 
        }else if(Double.class.getName().equals(paramType) && NumberUtils.isNumber(value)){ 
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, Double.valueOf(value)); 
        }else if(BigDecimal.class.getName().equals(paramType)){
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj,new BigDecimal(value)); 
        }else if(Date.class.getName().equals(paramType)){
        	if(StringUtils.isNotBlank(value))
        		method.invoke(obj, DateUtils.parseDate(value,new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"})); 
        }else if(Map.class.getName().equals(paramType) || HashMap.class.getName().equals(paramType)){
        	String getMehtodName=findGetMethodByFieldName(fieldName);
        	Method m=obj.getClass().getMethod(getMehtodName, null);
        	Object o=m.invoke(obj, null);
        	Enumeration<String> parameterNames = request.getParameterNames();
        	while (parameterNames.hasMoreElements()) {
				String string = (String) parameterNames.nextElement();
				if(string.startsWith(reqParamerPrefix+".") && string.length()>reqParamerPrefix.length()+1){
					if(o==null){
		        		o=new HashMap();
		        	}
					((Map)o).put(string.substring(reqParamerPrefix.length()+1), request.getParameter(string));
				}
			}
        	method.invoke(obj, o);
        }else if(List.class.getName().equals(paramType) || ArrayList.class.getName().equals(paramType)){//目前支持自定已List  com.ctfo.vcms.util.AutoArrayList 收集请求参数...
        	String getMehtodName=findGetMethodByFieldName(fieldName);
        	Method m=obj.getClass().getMethod(getMehtodName, null);
        	Object o=m.invoke(obj, null);
        	Enumeration<String> parameterNames = request.getParameterNames();
        	while (parameterNames.hasMoreElements()) {
				String string = (String) parameterNames.nextElement();
				if(string.startsWith(reqParamerPrefix+"[") && string.length()>reqParamerPrefix.length()+1){
					int index = Integer.valueOf(string.substring(string.indexOf("[")+1,string.indexOf("]")));
					if(string.length()-1==string.indexOf("]")){
							((List)o).get(index);
							((List)o).add(index,request.getParameter(string));
							((List)o).remove(index+1);
					}else{
						Object entity = ((List)o).get(index);
						bindParams(entity,request,reqParamerPrefix+"["+index+"]");
					}
				}
			}
        	method.invoke(obj, o);
        }else{
        	String getMehtodName=findGetMethodByFieldName(fieldName);
        	Method m=obj.getClass().getMethod(getMehtodName, null);
        	Object o=m.invoke(obj, null);
        	bindParams(o,request,reqParamerPrefix);
        } 
	}
	
	private static String getFieldNameBySetAndGetMethod(String methodName){
		return  methodName.substring(3,4).toLowerCase()+methodName.substring(4);
	}
	
	private static String findGetMethodByFieldName(String feildName){
		return  "get"+feildName.substring(0,1).toUpperCase()+feildName.substring(1);
	}
}
