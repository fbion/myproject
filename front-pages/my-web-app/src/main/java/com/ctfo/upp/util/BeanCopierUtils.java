package com.ctfo.upp.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanCopier;

@SuppressWarnings("all")
public class BeanCopierUtils {
	private static final Map beanCopierMap = new HashMap();

	public BeanCopierUtils() {
	}
	public static Object copyProperties(Object source, Object target) {
		String beanKey = generateKey(source.getClass(), target.getClass());
		BeanCopier copier = null;
		if (!beanCopierMap.containsKey(beanKey)) {
			copier = BeanCopier.create(source.getClass(), target.getClass(), false);
			beanCopierMap.put(beanKey, copier);
		} else {
			copier = (BeanCopier) beanCopierMap.get(beanKey);
		}
		copier.copy(source, target, null);
		return target;
	}

	private static String generateKey(Class class1, Class class2) {
		return (new StringBuilder()).append(class1.toString()).append("-").append(class2.toString()).toString();
	}
}
