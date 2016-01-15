package com.ctfo.sinoiov.mobile.webapi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ReflectUtil {
	// 弱引用缓存,当垃圾回收时,不影响无用的数据回收
	private static Map<String, Map<String, Method>> setMethodCatchMap = new WeakHashMap<String, Map<String, Method>>();

	private static Logger logger = Logger.getLogger(ReflectUtil.class);

	/**
	 * 获取class 的 set Method Map集合
	 * 
	 * @param destClass
	 * @return
	 */
	public static Map<String, Method> getClassSetMethodMap(Class<?> destClass) {
		Map<String, Method> destSetMethodMap = setMethodCatchMap.get(destClass.getName());
		if (destSetMethodMap == null) {
			loadClassSetMethod(destClass);
			destSetMethodMap = setMethodCatchMap.get(destClass.getName());
		}
		return destSetMethodMap;
	}

	/**
	 * load set method of cl class into catchMap
	 * 
	 * @param destClass
	 * @return
	 */
	public synchronized static void loadClassSetMethod(Class<?> cl) {
		Map<String, Method> destSetMethodMap = setMethodCatchMap.get(cl.getName());
		if (destSetMethodMap == null) {
			destSetMethodMap = new HashMap<String, Method>();
			for (Method m : cl.getMethods()) {
				// set方法
				if (isPublicSetMethod(m)) {
					destSetMethodMap.put(m.getName().substring(3, 4).toLowerCase() + m.getName().substring(4), m);
				}
			}
			setMethodCatchMap.put(cl.getName(), destSetMethodMap);
		}
	}

	public static Method findMethod(Class cls, String methodName, Class[] parmaType) {
		Method m = null;
		try {
			m = cls.getMethod(methodName, parmaType);
		} catch (NoSuchMethodException e) {
			// ignore this exception
		}
		if (m == null) {
			m = findNotPublicMethod(cls, methodName, parmaType);
		}
		return m;
	}

	public static Method findNoParmaMethod(Class cls, String methodName) {
		return findMethod(cls, methodName, new Class[] {});
	}

	public static Method findSetMethodByFieldName(Class cls, String fieldName, Class[] classes) {
		return findMethod(cls, findSetMethodNamebyFieldName(fieldName), classes);
	}

	public static String findSetMethodNamebyFieldName(String fieldName) {
		if (StringUtils.isBlank(fieldName)) {
			return null;
		}
		String getMethodName = null;
		if (fieldName.length() == 1) {
			getMethodName = "set" + fieldName.toUpperCase();
		} else {
			getMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		}
		return getMethodName;
	}

	public static String findGetMethodNamebyFieldName(String fieldName) {
		if (StringUtils.isBlank(fieldName)) {
			return null;
		}
		String getMethodName = null;
		if (fieldName.length() == 1) {
			getMethodName = "get" + fieldName.toUpperCase();
		} else {
			getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		}
		return getMethodName;
	}

	/**
	 * 获取非public的方法
	 * 
	 * @param cls
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param parmaType
	 *            方法类型
	 * @return 方法对象
	 */
	public static Method findNotPublicMethod(Class cls, String methodName, Class[] parmaType) {
		Method m = null;
		try {
			m = cls.getDeclaredMethod(methodName, parmaType);
			if (m != null) {
				m.setAccessible(true);
			}
		} catch (NoSuchMethodException e) {
			logger.warn(" class :" + cls + " does not have method which named " + methodName);
		}
		return m;
	}

	public static boolean isPublicSetMethod(Method m) {
		if (m != null && m.getName().startsWith("set") && m.getName().length() > 3 && m.getParameterTypes().length == 1)
			return true;
		return false;
	}

	public static boolean isPublicGetMethod(Method m) {
		if (m != null) {
			String methdoName = m.getName();
			if (methdoName.startsWith("get") && !"getClass".equals(methdoName) && methdoName.length() > 3
					&& m.getParameterTypes().length == 0) {
				return true;
			}
		}
		return false;
	}

	public static Field findFiledByGetMethod(Method m) {
		String filedName = m.getName().substring(3, 4).toLowerCase() + m.getName().substring(4);
		Field origField = null;
		try {
			origField = m.getDeclaringClass().getDeclaredField(filedName);
		} catch (NoSuchFieldException e) {
			// ignore exception
		}
		return origField;
	}

	public static Method findGetMethodByFieldName(Class cls, String fieldName) {
		return findMethod(cls, findGetMethodNamebyFieldName(fieldName), new Class[] {});
	}
	
	public static void main(String[] args) {
		System.out.println(findSetMethodNamebyFieldName("t"));
		System.out.println(findSetMethodNamebyFieldName("testFiled"));
		System.out.println(findSetMethodNamebyFieldName(null));
	}
}
