package com.ctfo.sinoiov.mobile.webapi.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.upp.http.HttpUtils;

/**
 * 支付工具类
 * 
 * @author sunchuanfu
 */
public class PayManagerUtil {
	protected static final Log logger = LogFactory.getLog(PayManagerUtil.class);

	/**
	 * POST请求支付中心
	 * 
	 * @param obj
	 *            封装参数对象
	 * @param methodUrlKey
	 *            方法URL(例如/account/createAccount)
	 * @return UppResult对象
	 * @exception Exception
	 */
	public static UppResult invokeUPP(Object obj, String methodUrlKey) throws Exception {
		try {
			// TODO 如果obj有继承关系，则现在不会转成map
			Map<String, String> mapParam = PayManagerUtil.putValueIntoMap(obj);
			return PayManagerUtil.invokeUPP(mapParam, methodUrlKey);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * POST请求支付中心
	 * 
	 * @param mapParam
	 *            参数放入Map结构中
	 * @param methodUrlKey
	 *            方法URL(例如/account/createAccount)
	 * @return UppResult对象
	 * @exception Exception
	 */
	public static UppResult invokeUPP(Map<String, String> mapParam, String methodUrlKey) throws Exception {
		UppResult uppResult = null;

		try {
			String methodUrlValue = PayConstants.getConfigValue(methodUrlKey);
			String requestJson = JSONObject.fromObject(mapParam).toString();

			// 发送POST消息(明文的数据在下面的方法中做了加密，实际POST发送给支付平台是加密后的数据)
			String returnJson = HttpUtils.sendRequest(requestJson, 
					PayConstants.getConfigValue("UPP_INTERFACE_URL") + methodUrlValue,
					PayConstants.getPrivateKey(PayConstants.getConfigValue("MOBILE_API_MERCHANT_CODE")), 
					PayConstants.getPublicKey(PayConstants.getConfigValue("INTERFACE_MERCHANT_CODE")), 
					PayConstants.getConfigValue("MOBILE_API_MERCHANT_CODE"));
			uppResult = (UppResult) JSONObject.toBean(JSONObject.fromObject(returnJson), UppResult.class);
		} catch (Exception e) {
			logger.error("调用支付中接口出错：", e);
			throw e;
		}

		return uppResult;
	}
	
	/**
	 * 对象转为map结构
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("all")
	public static Map<String, String> putValueIntoMap(Object obj) {
		Map<String, String> mp = new HashMap<String, String>();
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String fieldName = null;
		String getterMethodName = null;
		String fieldValue = null;
		for (Field f : fields) {
			fieldName = f.getName();
			if (fieldName.equals("serialVersionUID"))
				continue;
			getterMethodName = "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
			try {
				fieldValue = (String) clazz.getMethod(getterMethodName, null).invoke(obj, null);
			} catch (Exception e) {
				//
			}
			if (fieldValue != null) {
				mp.put(fieldName, fieldValue);
			}
		}
		return mp;
	}

}
