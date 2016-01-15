package com.ctfo.upp.gatewayservice.util.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.annotation.FieldLength;
import com.ctfo.upp.gateway.fastpay.annotation.GreaterThan;
import com.ctfo.upp.gateway.fastpay.annotation.NotNull;
import com.ctfo.upp.gateway.fastpay.annotation.NotNullOrEmpty;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.utils.BeanUtil;
import com.ctfo.upp.gateway.fastpay.utils.FastPaymentConfig;
import com.ctfo.upp.gateway.fastpay.utils.JSONUtil;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpClient4Util;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpParameter;
import com.ctfo.upp.gateway.fastpay.utils.httpclient.HttpResp;

public abstract class AbstractFastPayment {
	static private Log logger = LogFactory.getLog(AbstractFastPayment.class);
	static public final String CALLBACK_UNIFIED_RETURN_STR = "success";// 商户(业务系统)必须返回success.

	static public interface HttpParameterFactory {
		HttpParameter createHttpParameter();
	}

	static public HttpParameterFactory httpParameterFactory = new HttpParameterFactory() {

		@Override
		public HttpParameter createHttpParameter() {
			// TODO Auto-generated method stub
			return new HttpParameter();
		}

	};

	static public FastPaymentConfig getFastPaymentConfig() {
		return BeanUtil.getFastPaymentConfig();
	}

	static public String object2JSON(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		if (object == null)
			throw new NullPointerException("The object parameter is null.");
		return JSONUtil.object2JSON(object);
	}
	
	
	public static String sendRemoteReauestNew(String url, HttpParameter httpParameter)throws Exception{
		
		if (StringUtils.isBlank(url))
			throw new IllegalStateException("The url parameter is null or empty.");
		else if (httpParameter == null)
			throw new NullPointerException("The httpParameter is null.");

		String charset = "utf-8";

		final int timeout = Integer.parseInt(ConfigUtil.getValue("HTTP_TIMEOUT"));
		HttpClient4Util httpClient = null;
		if (url.indexOf("https") >= 0)
			httpClient = new HttpClient4Util(timeout, true);
		else
			httpClient = new HttpClient4Util(timeout, false);

		HttpResp httpResp = new HttpResp();
		try {
			//if (post)
				httpResp = httpClient.doPost(url, httpParameter, charset);
			//else
			//	httpResp = httpClient.doGet(url, httpParameter, charset);
		} catch (IOException e) {
			throw e;
		} finally {
			if (httpClient != null)
				httpClient.shutdown();
		}
		
		return httpResp.getText(charset);	
	}
	
	
	

	static public String sendRemoteRequest(String url, HttpParameter httpParameter, String charset, boolean post) throws IOException {
		
		if (StringUtils.isBlank(url))
			throw new IllegalStateException("The url parameter is null or empty.");
		else if (httpParameter == null)
			throw new NullPointerException("The httpParameter is null.");

		if (StringUtils.isBlank(charset))
			charset = "UTF-8";

		final int timeout = Integer.parseInt(ConfigUtil.getValue("HTTP_TIMEOUT"));
		HttpClient4Util httpClient = null;
		if (url.indexOf("https") >= 0)
			httpClient = new HttpClient4Util(timeout, true);
		else
			httpClient = new HttpClient4Util(timeout, false);

		HttpResp httpResp = new HttpResp();
		try {
			if (post)
				httpResp = httpClient.doPost(url, httpParameter, charset);
			else
				httpResp = httpClient.doGet(url, httpParameter, charset);
		} catch (IOException e) {
			throw e;
		} finally {
			if (httpClient != null)
				httpClient.shutdown();
		}
		
		return httpResp.getText(charset);
	}

	static protected Map<?, ?> mapClean(Map<?, ?> map) {
		if (map == null || map.isEmpty())
			throw new NullPointerException("The map parameter is null.");
		if (map.containsKey("class"))
			map.remove("class");
		return map;
	}

	static protected void checkObjectField(FastPayDomain fastPayDomain) throws UPPException {
		Field[] fields = fastPayDomain.getClass().getDeclaredFields();
		if (fields == null || fields.length == 0)
			return;
		for (Field f : fields) {
			if (f == null)
				continue;

			Annotation[] annotations = f.getAnnotations();
			if (annotations == null || annotations.length == 0)
				continue;
			PropertyDescriptor pd = null;
			try {
				pd = new PropertyDescriptor(f.getName(), fastPayDomain.getClass());
			} catch (IntrospectionException ignore) {
				;
			}
			Object value = null;
			try {
				value = pd.getReadMethod().invoke(fastPayDomain);
			} catch (Exception ignore) {
			}

			for (Annotation annotation : annotations) {
				if (annotation == null)
					continue;
				if (annotation.annotationType() == null)
					continue;

				if (NotNull.class.isInstance(annotation)) {
					if (value == null) // Not null has checked
						throw new UPPException(String.format("[%s]的属性[%s]值不能为null.", fastPayDomain.getClass(), f.getName()));
				} else if (NotNullOrEmpty.class.isInstance(annotation)) {
					checkFieldValue(fastPayDomain.getClass(), f.getName(), value);
				} else if (FieldLength.class.isInstance(annotation)) {
					final FieldLength fieldLength = (FieldLength) annotation;
					if (value == null)
						continue;
					final String _value = (String) value;
					if (_value.length() > fieldLength.value())
						throw new UPPException(String.format("[%s]的属性[%s]值长度不能大于[%s].", fastPayDomain.getClass().getCanonicalName(), f.getName(), fieldLength.value()));
				} else if (GreaterThan.class.isInstance(annotation)) {
					final GreaterThan greaterThan = (GreaterThan) annotation;
					final Integer _value = Integer.parseInt(value.toString());
					if (_value.longValue() <= greaterThan.value())
						throw new UPPException(String.format("[%s]的属性[%s]值必须大于[%s].", fastPayDomain.getClass().getCanonicalName(), f.getName(), greaterThan.value()));
				}

			}
			annotations = null;
		}
		fields = null;
		System.gc();
		System.runFinalization();
	}

	static protected void checkFieldValue(Class<?> clazz, String propertyName, Object fieldValue) throws UPPException {
		if (String.class.isInstance(fieldValue)) {
			final String _value = (String) fieldValue;
			if (_value.isEmpty())
				throw new UPPException(String.format("[%s]的属性[%s]值不能为空.", clazz.getCanonicalName(), propertyName));
		}
	}

	static protected Map<String, Object> describe(Object object, boolean lowercaseKey) throws UPPException {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(object.getClass());
		} catch (IntrospectionException ignore) {
			// TODO Auto-generated catch block
			ignore.printStackTrace();
		}
		Map<String, Object> resultMap = new TreeMap<String, Object>();
		final PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		if (pds == null || pds.length == 0)
			throw new UPPException("获取到的对象属性集合is null.");
		for (PropertyDescriptor pd : pds) {
			if (!resultMap.containsKey((lowercaseKey ? pd.getName().toLowerCase() : pd.getName()))) {
				try {
					resultMap.put((lowercaseKey ? pd.getName().toLowerCase() : pd.getName()), pd.getReadMethod().invoke(object));
				} catch (Exception ignore) {
					// TODO Auto-generated catch block
					ignore.printStackTrace();
				}
			}
		}
		return resultMap;
	}
}
