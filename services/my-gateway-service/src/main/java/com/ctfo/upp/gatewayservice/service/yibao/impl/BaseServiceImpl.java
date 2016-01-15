package com.ctfo.upp.gatewayservice.service.yibao.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.gatewayservice.bean.yibao.remote.NotHmac;
import com.ctfo.upp.gatewayservice.util.base.ConfigUtil;
import com.ctfo.upp.gatewayservice.util.yibao.Constants;
import com.ctfo.upp.gatewayservice.util.yibao.EncodeHMAC;
import com.ctfo.upp.gatewayservice.util.yibao.PareseObjectParameter;
import com.ctfo.upp.root.utils.EnvironmentUtil;


public class BaseServiceImpl {
	
	protected static final Log log = LogFactory.getLog(BaseServiceImpl.class);
	
	/**
	 * 验证回值内容是否正确，三步：1，验证HAMC密钥是否正确；2，验证基本字段与请求的是否一致；3，验证返回码内容
	 * @param requestObject
	 * @param responseObject
	 * @return
	 * @throws Exception
	 */
	protected boolean validateResponse(Object requestObject, Object responseObject) throws Exception{
		boolean flag = true;
		//步骤1
		if(!validateHMAC(responseObject, 1)){
			flag = false;
		}
		//步骤2
		Map<String, String> requestMap = getCompareParameter(requestObject);
		Map<String, String> responseMap = getCompareParameter(responseObject);
		if(requestMap.size() != responseMap.size()){
			log.error(">>>>>>>>>> 请求与返回的基本属性不一致  <<<<<<<<<<");
			flag = false;
		}else{
			if(!requestMap.get("command").equals(responseMap.get("command"))){
				log.error(">>>>>>>>>> 请求与返回的command不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!requestMap.get("req01_merchantIdentityNumber").equals(responseMap.get("resp02_merchantIdentityNumber"))){
				log.error(">>>>>>>>>> 请求与返回的merchantIdentityNumber不一致  <<<<<<<<<<");
				flag = false;
			}
		}
		//步骤3
		PropertyDescriptor pd = new PropertyDescriptor("resp01_code", responseObject.getClass());
		Method getMethod = pd.getReadMethod();
		if(!((String)getMethod.invoke(responseObject)).equals("1") && !((String)getMethod.invoke(responseObject)).equals("501")){//1：成功
			log.error(">>>>>>>>>> 返回失败码：" + Constants.ErrorCodes.get((String)getMethod.invoke(responseObject)) + " <<<<<<<<<<");
			flag = false;
		}
		
		return flag;
	}
	
	public boolean validateHMAC(Object responseObject, int type) throws Exception{
		boolean flag = true;
		PropertyDescriptor pd = new PropertyDescriptor("hmac", responseObject.getClass());
        Method getMethod = pd.getReadMethod();
		String returnHMAC = (String)getMethod.invoke(responseObject);
		Method setMethod = pd.getWriteMethod();
		setMethod.invoke(responseObject, "");
		String localHMAC = createHMAC(responseObject, type);
		if(!returnHMAC.equals(localHMAC)){
			log.error(">>>>>>>>>> 签名不一致：【返回：" + returnHMAC + "】,【本地计算：" + localHMAC + "】 <<<<<<<<<<");
			flag = false;
		}
		return flag;
	}
	
	private Map getCompareParameter(Object object) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		for(Field f : fields){
			if(f.getName().contains("command") || f.getName().contains("merchantIdentityNumber")){
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), object.getClass());
	            Method getMethod = pd.getReadMethod();
	            map.put(f.getName(), (String)getMethod.invoke(object));
			}
		}
		return map;
	}
	
	//type=1：发往账户通
	//type=2：发往委托结算
	public static String createHMAC(Object object, int type) throws Exception{
		//将具有NotHmac标签的字段的值设为空，生成验证码的过程中不使用
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		for(Field f : fields){
			if(f.getAnnotation(NotHmac.class) != null){
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), object.getClass());  
	            Method getMethod = pd.getWriteMethod();
	            getMethod.invoke(object, "");
			}
		}
		String encryptData = PareseObjectParameter.getObjectParameterContentString(object);
		String key = "";
		switch (type) {
		case 1:
			key = ConfigUtil.getValue("YB_ACCOUNT_ACCESS_MER_ID");
			break;
		case 2:
			key = ConfigUtil.getValue("YB_ENTRUST_SETTLE_MER_ID");
			break;
		default:
			break;
		}
		return EncodeHMAC.hmacSign(encryptData, ConfigUtil.getPublicKey(key));
	}
	
}
