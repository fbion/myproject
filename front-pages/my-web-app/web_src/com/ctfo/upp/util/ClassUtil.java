package com.ctfo.upp.util;

import java.lang.reflect.Field;
import java.util.List;

public class ClassUtil {
	
	private static boolean notEndWith(String string, List<String> notEndWithStrings) {
		int count = 0;
		if (notEndWithStrings != null && notEndWithStrings.size() > 0) {
			for (String each : notEndWithStrings) {
				if (string.endsWith(each)) {
					count++;
				}
			}
		}
		return count > 0 ? false : true;
	}
	
	public static boolean startWithSpecMethodHasValue(Object obj, String name, List<String> notEndWithStrings) 
			throws IllegalArgumentException, IllegalAccessException {
		int count = 0;
		Class<?> _class = obj.getClass();
		Field[] fields = _class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().startsWith(name) 
					&& notEndWith(field.getName(), notEndWithStrings)) {
				if (field.get(obj) != null) {
					count++;
				}
			}
		}
		return count > 0 ? true : false;
	}
	
	public static void copySpecNewValue2OldObj(Object oldObj, 
			Object newValueObj, String startWithName, String passEndString) 
			throws Exception {
		
		Class<?> _class = newValueObj.getClass();
		Field[] fields = _class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().startsWith(startWithName)) {
				Field oldField = oldObj.getClass().getDeclaredField(field.getName());
				oldField.setAccessible(true);
				if (!field.getName().endsWith(passEndString)) {
					oldField.set(oldObj, field.get(newValueObj));
				}
			}
		}
	}
}
