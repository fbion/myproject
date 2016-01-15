package com.ctfo.upp.gateway.fastpay.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.utils.SpringBUtils;

public final class BeanUtil {
	static final private String FAST_PAYMENT_CONFIG_BEAN_NAME = "fastPaymentConfig";
	static private Log logger = LogFactory.getLog(BeanUtil.class);

	static public FastPaymentConfig getFastPaymentConfig() {
		return ((FastPaymentConfig) SpringBUtils.getBean(FAST_PAYMENT_CONFIG_BEAN_NAME));
	}

	static public String convertBeanValues2Str(Object bean) throws IntrospectionException {
		if (bean == null)
			throw new NullPointerException("The parameter is null.");
		final StringBuffer strBuffer = new StringBuffer("");
		final BeanInfo bi = Introspector.getBeanInfo(bean.getClass());
		final PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			if (pd == null)
				continue;
			try {
				strBuffer.append(pd.getReadMethod().invoke(bean));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return strBuffer.toString();
	}

	static public String convertMapValues2Str(Map<?, ?> map) {
		if (map == null || map.isEmpty())
			throw new NullPointerException("The map parameter is null.");
		final StringBuffer strBuffer = new StringBuffer("");
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			if (entry == null)
				continue;
			strBuffer.append(entry.getValue() == null ? "" : entry.getValue());
		}
		if (logger.isDebugEnabled())
			logger.debug("The string representation of a value object:" + strBuffer.toString());
		return strBuffer.toString();
	}
}
