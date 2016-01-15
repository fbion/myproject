package com.sinoiov.upp.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.base.dao.beans.UPParam;
import com.ctfo.base.dao.beans.UPParamExampleExtended;
import com.ctfo.base.dao.beans.UPPlatform;
import com.ctfo.base.intf.internal.ParamManager;
import com.ctfo.base.intf.internal.PlatformRegisterManager;
import com.ctfo.upp.encryptkey.EncryptKeyUtils;
import com.ctfo.upp.encryptkey.KeyBean;
import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.util.EnvironmentUtil;


public class DefaultConfig extends EncryptKeyUtils{

	private static Log logger = LogFactory.getLog(DefaultConfig.class);

	protected static String UPP_KEY = "UPP_KEY";

	protected static Map<String, KeyBean> keyCacheMap = new HashMap<String, KeyBean>();

	protected static Map<String, String> parameterCacheMap = new HashMap<String, String>();

	protected static String myIP = HttpUtils.getIP_Inet4Address();


	/**
	 * 根据编号获取公钥
	 * 缓存　－－　数据库
	 * @param merchantcode
	 * @param systemSign
	 * @return
	 */
	public static String getPublicKey(String merchantcode, String systemSign) {
		String publicKey = "";
		try {
			if (StringUtils.isBlank(merchantcode))
				return "";

			if(isKeyCacheChange(systemSign)){
				keyCacheMap.clear();
			}else{
				publicKey = keyCacheMap.get(merchantcode)==null?"":keyCacheMap.get(merchantcode).getPublicKey();
			}

			if (StringUtils.isBlank(publicKey)){				
				KeyBean keyBean = getKeyBeanFromDB(merchantcode);
				String decryptkey = getValue(UPP_KEY, systemSign);
				if(keyBean!=null && StringUtils.isNotBlank(decryptkey)){
					keyBean = decryptKeys(keyBean, decryptkey);
					publicKey = keyBean.getPublicKey();
					keyCacheMap.put(merchantcode, keyBean);
				}
			}			

		} catch (Exception e) {
			logger.error("根据编号[" + merchantcode + "]获取公钥时异常", e);
		}
		return publicKey;
	}

	/**
	 * 根据编号获取私钥
	 * 缓存　－－　数据库
	 * @param merchantcode
	 * @param systemSign
	 * @return
	 */
	public static String getPrivateKey(String merchantcode, String systemSign) {
		String privateKey = "";
		try {

			if (StringUtils.isBlank(merchantcode))
				return "";

			if(isKeyCacheChange(systemSign)){
				keyCacheMap.clear();
			}else{
				privateKey = keyCacheMap.get(merchantcode)==null?"":keyCacheMap.get(merchantcode).getPrivateKey();
			}

			if (StringUtils.isBlank(privateKey)){				
				KeyBean keyBean = getKeyBeanFromDB(merchantcode);
				String decryptkey = getValue(UPP_KEY, systemSign);
				if(keyBean!=null && StringUtils.isNotBlank(decryptkey)){
					keyBean = decryptKeys(keyBean, decryptkey);
					privateKey = keyBean.getPrivateKey();
					keyCacheMap.put(merchantcode, keyBean);
				}
			}

		} catch (Exception e) {
			logger.error("根据编号[" + merchantcode + "]获取私钥时异常", e);
		}
		return privateKey;
	}

	/**
	 * 取系统参数
	 * 缓存　－－　数据库　－－　配置文件
	 * @param key
	 * @return
	 */
	public static String getValue(String key, String systemSign){
		String value = "";
		try{
			if(StringUtils.isBlank(key))return null;
			//缓存
			if(isParamCacheChange(systemSign)){
				parameterCacheMap.clear();
			}else{
				value = parameterCacheMap.get(key);
			}

			if (StringUtils.isBlank(value)){
				//数据库
				value = getParamFromDB(key);
				logger.debug("---------从数据库中获得："+value);
				if(StringUtils.isBlank(value)){
					//配置文件
					value = EnvironmentUtil.getInstance("system.properties").getPropertyValue(key);
					logger.debug("---------从配置文件system.properties中获得："+value);
				}
				if(StringUtils.isNotBlank(value)){
					if(StringUtils.isNotBlank(parameterCacheMap.get(key))){
						parameterCacheMap.remove(key);
						logger.debug("---------从缓存中清除了["+key+"]对应的值："+value);
					}
					parameterCacheMap.put(key, value);
				}
			}
		}catch(Exception e){
			logger.warn("获取["+key+"]缓存时异常",e);
		}
		return value;
	}



	///////////////私有方法////////////////

	private static String getParamFromDB(String key)throws Exception{
		String value="";
		try{

			UPParamExampleExtended exampleExtended = new UPParamExampleExtended();	
			ParamManager manager = (ParamManager) ServiceFactory.getFactory().getService(ParamManager.class);
			exampleExtended.createCriteria().andParamNameEqualTo(key);
			List<UPParam> list = manager.queryParam(exampleExtended);
			value = list!=null && list.size()>0?list.get(0).getParamValue():"";

		}catch(Exception e){
			logger.warn("根据["+key+"]查询系统参数时异常",e);
		}
		return value;
	}

	private static KeyBean getKeyBeanFromDB(String merchantCode) throws Exception {

		PlatformRegisterManager paramManager = (PlatformRegisterManager) ServiceFactory.getFactory().getService(
				PlatformRegisterManager.class);
		UPPlatform platform = paramManager.getRegPlatformByStoreCode(merchantCode);

		KeyBean keybean = new KeyBean();
		keybean.setMerchantCode(merchantCode);
		keybean.setMerchantName(platform == null || StringUtils.isBlank(platform.getStoreName()) ? "" : platform
				.getStoreName().trim());
		keybean.setPrivateKey(platform == null || StringUtils.isBlank(platform.getPrivateKey()) ? "" : platform
				.getPrivateKey().trim());
		keybean.setPublicKey(platform == null || StringUtils.isBlank(platform.getPublicKey()) ? "" : platform
				.getPublicKey().trim());
		keybean.setStatus(platform == null || StringUtils.isBlank(platform.getStoreStatus()) ? "" : platform
				.getStoreStatus().trim());

		return keybean;
	}

	private static boolean isParamCacheChange(String systemSing){
		try{
			
			ParamManager manager = (ParamManager) ServiceFactory.getFactory().getService(ParamManager.class);
			return manager.isParamChange(systemSing, myIP);
			
		}catch(Exception e){
			logger.warn("获取系统配置参数缓存标识位异常！");
		}		
		return false;
	}

	private static boolean isKeyCacheChange(String systemSing){
		try{
			PlatformRegisterManager paramManager = (PlatformRegisterManager) ServiceFactory.getFactory().getService(PlatformRegisterManager.class);
			return paramManager.isKeyChange(systemSing, myIP);
		}catch(Exception e){
			logger.warn("获取密钥缓存标识位异常！");
		}		
		return false;
	}

}
