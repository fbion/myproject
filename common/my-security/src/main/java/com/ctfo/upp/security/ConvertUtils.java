package com.ctfo.upp.security;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ctfo.upp.security.encrypt.AES;
import com.ctfo.upp.security.encrypt.RSA;

public abstract class ConvertUtils {

	private static final DecimalFormat simpleFormat = new DecimalFormat("####");

	private static Log logger = LogFactory.getLog(ConvertUtils.class);

	public static final boolean objectToBoolean(Object o){
		return o != null ? Boolean.valueOf(o.toString()).booleanValue() : false;
	}

	public static final int objectToInt(Object o){
		if(o instanceof Number)
			return ((Number)o).intValue();
		try{
			if(o == null)
				return -1;
			else
				return Integer.parseInt(o.toString());
		}catch(NumberFormatException e){
			return -1;
		}
	}

	public static final short objectToShort(Object o){
		if(o instanceof Number)
			return ((Number)o).shortValue();
		try{
			if(o == null)
				return -1;
			else
				return Short.parseShort(o.toString());
		}catch(NumberFormatException e){
			return -1;
		}
	}

	public static final double objectToDouble(Object o){
		if(o instanceof Number)
			return ((Number)o).doubleValue();
		try{
			if(o == null)
				return -1D;
			else
				return Double.parseDouble(o.toString());
		}catch(NumberFormatException e){
			return -1D;
		}
	}

	public static final long objectToLong(Object o)
	{
		if(o instanceof Number)
			return ((Number)o).longValue();
		try{
			if(o == null)
				return -1L;
			else
				return Long.parseLong(o.toString());
		}catch(NumberFormatException e){
			return -1L;
		}
	}

	public static final String objectToString(Object obj, DecimalFormat fmt)
	{
		fmt.setDecimalSeparatorAlwaysShown(false);
		if(obj instanceof Double)
			return fmt.format(((Double)obj).doubleValue());
		if(obj instanceof Long)
			return fmt.format(((Long)obj).longValue());
		else
			return obj.toString();
	}

	public static final Object getObjectValue(String value)
	{
		try{
			return Long.valueOf(value);
		}catch(NumberFormatException e) {}

		try{
			return Double.valueOf(value);
		}catch(NumberFormatException e){
			return value;
		}
	}

	public static String longToSimpleString(long value){
		return simpleFormat.format(value);
	}

	public static String asHex(byte hash[]){
		return toHex(hash);
	}

	public static String toHex(byte input[]){
		if(input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for(int i = 0; i < input.length; i++){
			int current = input[i] & 0xff;
			if(current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	public static byte[] fromHex(String input){
		if(input == null)
			return null;
		byte output[] = new byte[input.length() / 2];
		for(int i = 0; i < output.length; i++)
			output[i] = (byte)Integer.parseInt(input.substring(i * 2, (i + 1) * 2), 16);

		return output;
	}

	public static String stringToHexString(String input, String encoding)
			throws UnsupportedEncodingException{
		return input != null ? toHex(input.getBytes(encoding)) : null;
	}

	public static String stringToHexString(String input){
		try{
			return stringToHexString(input, "UTF-8");
		}catch(UnsupportedEncodingException e){
			throw new IllegalStateException("UTF-8 encoding is not supported by JVM");
		}
	}

	public static String hexStringToString(String input, String encoding)
			throws UnsupportedEncodingException{
		return input != null ? new String(fromHex(input), encoding) : null;
	}

	public static String hexStringToString(String input){
		try{
			return hexStringToString(input, "UTF-8");
		}catch(UnsupportedEncodingException e){
			throw new IllegalStateException("UTF-8 encoding is not supported by JVM");
		}
	}

	public static String timeZoneToCode(TimeZone tz){

		return timeZoneToString(tz);
	}

	public static TimeZone codeToTimeZone(String tzString){

		return stringToTimeZone(tzString);
	}

	public static String timeZoneToString(TimeZone tz){

		return tz != null ? tz.getID() : "";
	}

	public static TimeZone stringToTimeZone(String tzString){

		return TimeZone.getTimeZone(tzString != null ? tzString : "");
	}

	public static String localeToCode(Locale aLocale){

		return localeToString(aLocale);
	}

	public static Locale codeToLocale(String locString){

		return stringToLocale(locString);
	}

	public static String localeToString(Locale loc){

		return loc != null ? loc.toString() : "";
	}

	public static Locale stringToLocale(String locString){

		locString = locString != null ? locString.trim() : "";
		if(locString.equals(""))
			return new Locale("", "", "");
		int pos = locString.indexOf(95);
		if(pos == -1)
			return new Locale(locString, "", "");
		String language = locString.substring(0, pos);
		locString = locString.substring(pos + 1);
		pos = locString.indexOf(95);
		if(pos == -1){
			return new Locale(language, locString, "");
		}else{
			String country = locString.substring(0, pos);
			locString = locString.substring(pos + 1);
			return new Locale(language, country, locString);
		}
	}

	public static Date dateToSQLDate(java.util.Date d){

		return d != null ? new Date(d.getTime()) : null;
	}

	public static Time dateToSQLTime(java.util.Date d){

		return d != null ? new Time(d.getTime()) : null;
	}

	public static Timestamp dateToSQLTimestamp(java.util.Date d){

		return d != null ? new Timestamp(d.getTime()) : null;
	}

	public static java.util.Date sqlTimestampToDate(Timestamp t){

		return t != null ? new java.util.Date(Math.round((double)t.getTime() + (double)t.getNanos() / 1000000D)) : null;
	}

	public static Timestamp getCurrentDate(){

		Calendar c = Calendar.getInstance();
		c.set(c.get(1), c.get(2), c.get(5), 0, 0, 0);
		Timestamp t = new Timestamp(c.getTime().getTime());
		t.setNanos(0);
		return t;
	}

	public static java.util.Date getDate(int y, int m, int d, boolean inclusive)
	{
		java.util.Date dt = null;
		Calendar c = Calendar.getInstance();
		c.clear();
		if(c.getActualMinimum(1) <= y && y <= c.getActualMaximum(1))
		{
			c.set(1, y);
			if(c.getActualMinimum(2) <= m && m <= c.getActualMaximum(2))
			{
				c.set(2, m);
				if(c.getActualMinimum(5) <= d && d <= c.getActualMaximum(5))
					c.set(5, d);
			}
			if(inclusive)
			{
				c.add(5, 1);
				c.add(14, -1);
			}
			dt = c.getTime();
		}
		return dt;
	}

	public static java.util.Date getDateStart(java.util.Date d)
	{

		Calendar c = new GregorianCalendar();
		c.clear();
		Calendar co = new GregorianCalendar();
		co.setTime(d);
		c.set(Calendar.DAY_OF_MONTH,co.get(Calendar.DAY_OF_MONTH));
		c.set(Calendar.MONTH,co.get(Calendar.MONTH));
		c.set(Calendar.YEAR,co.get(Calendar.YEAR));
		//c.add(Calendar.DAY_OF_MONTH,1);
		//c.add(Calendar.MILLISECOND,-1);
		return c.getTime();
	}

	public static java.util.Date getDateEnd(java.util.Date d)
	{
		Calendar c = Calendar.getInstance();
		c.clear();
		Calendar co = Calendar.getInstance();
		co.setTime(d);
		c.set(Calendar.DAY_OF_MONTH,co.get(Calendar.DAY_OF_MONTH));
		c.set(Calendar.MONTH,co.get(Calendar.MONTH));
		c.set(Calendar.YEAR,co.get(Calendar.YEAR));
		c.add(Calendar.DAY_OF_MONTH,1);
		c.add(Calendar.MILLISECOND,-1);
		return c.getTime();
	}

	public static double roundNumber(double rowNumber, int roundingPoint)
	{
		double base = Math.pow(10D, roundingPoint);
		return (double)Math.round(rowNumber * base) / base;
	}
	public static Object getObject(String type,String value) throws Exception{

		type=type.toLowerCase();
		if("boolean".equals(type))
			return Boolean.valueOf(value);
		if("byte".equals(type))
			return Byte.valueOf(value);
		if("short".equals(type))
			return Short.valueOf(value);
		if("char".equals(type))
			if(value.length() != 1)
				throw new NumberFormatException("Argument is not a character!");
			else
				return Character.valueOf(value.toCharArray()[0]);
		if("int".equals(type))
			return Integer.valueOf(value);
		if("long".equals(type))
			return Long.valueOf(value);
		if("float".equals(type))
			return Float.valueOf(value);
		if("double".equals(type))
			return Double.valueOf(value);
		if("string".equals(type))
			return value;
		else{
			Object objs[]=new String[]{value};
			return Class.forName(type).getConstructor(new Class[] {
					java.lang.String.class
			}).newInstance(objs);
		}
	}



	/**
	 * 获取数字加字母随机数
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public static String getRandom(int n) throws Exception{

		StringBuffer sb = new StringBuffer();
		Random random = new Random();  
		for (int i = 0; i < n; i++) {  
			boolean b = random.nextBoolean();  
			if (b) { // 字符串  
				// int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
				sb.append((char) (65 + random.nextInt(26)));// 取得大写字母  
			} else { // 数字  
				sb.append(String.valueOf(random.nextInt(10)));  
			}  
		}  
		return sb.toString();
	}












	////////////1.0.0版本加解密方法,所有新系统请不要用此功能///////////////////////////////////	

	private static TreeMap<String, Object> getSignData_1_0_0(Object obj, TreeMap<String, Object> treemap) throws Exception{

		if(obj instanceof List){
			for(Object tem : (List)obj){
				if(tem instanceof String){
					treemap.put("list", tem.toString());
				}else{
					getSignData_1_0_0(tem, treemap);
				}
			}
		}else if(obj instanceof Map){
			Map map = (Map)obj;
			//treemap.putAll(map);
			String key = "";
			for(Object tem : map.keySet()){
				if(tem instanceof String){
					key = (String) tem;
					treemap.put(key, map.get(key));
				}else{
					getSignData_1_0_0(tem, treemap);
				}
			}
		}else{
			Method[] mods = obj.getClass().getMethods();
			String key="";
			Object value=null;
			for(Method m : mods){
				if( Modifier.isPublic(m.getModifiers()) 
						&& !"getClass".equals(m.getName()) 
						&& !"getSign".equals(m.getName()) 
						&& "get".equals(m.getName().substring(0, 3))){
					key = (m.getName().substring(3,4)).toLowerCase()+m.getName().substring(4);
					value = m.invoke(obj, null);
					treemap.put(key, value);
				}
			}
		}	
		return treemap;
	}



	/**
	 * 根据对象属性排序并返回签名数据
	 * @param map
	 * @return 
	 * @throws Exception
	 */
	private static String getSignData_1_0_0(Object obj) throws Exception{

		if(obj instanceof String){
			return (String) obj;
		}else if(obj instanceof Map){
			Map map = (Map)obj;
			return getSignDataByMap_1_0_0(map);
		}else if(obj instanceof List){
			List list = (List)obj;
			return getSignDataByList_1_0_0(list);
		}else{
			Method[] mods = obj.getClass().getMethods();
			List<String> list = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			for(Method m : mods){
				if( Modifier.isPublic(m.getModifiers()) 
						&& !"getClass".equals(m.getName()) 
						&& !"getSign".equals(m.getName()) 
						&& "get".equals(m.getName().substring(0, 3))){
					list.add(m.getName());
				}
			}
			//排序
			sortList_1_0_0(list);
			for(String mName : list){
				Method m = obj.getClass().getMethod(mName);
				sb.append(m.invoke(obj, null).toString());
			}

			return sb.toString();
		}
	}


	private static String getSignDataByMap_1_0_0(Map<String,String> map) throws Exception{

		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();

		Set<String> set = map.keySet();
		list.addAll(set);

		sortList_1_0_0(list);
		JSONObject json = null;
		Map mymap = null;
		Set<String> myset = null;
		for(String key : list){	
			if(!"sign".equals(key)){
				if(map instanceof JSONObject){
					json = (JSONObject)map;
					Object val = json.get(key);
					//System.out.println(key+"=="+val+(val instanceof List));
					if(val==null || "".equals(val.toString()) || "null".equals(val.toString())) continue;
					if(val instanceof List){
						//对应高级查询
					}else if(val instanceof Map){
						mymap = (Map)val;
						myset = mymap.keySet();
						for(String mykey : myset){
							Object myval = mymap.get(mykey);
							if(myval==null || "".equals(myval.toString()) || "null".equals(val.toString())) continue;
							sb.append(myval.toString());
						}
					}else{
						sb.append(val.toString());
					}

				}else{
					logger.debug(key+"===="+map.get(key));
					sb.append(map.get(key)==null?"":map.get(key).toString());
				}
			}		
		}

		return sb.toString();

	}

	private static String getSignDataByList_1_0_0(List<?> list) throws Exception{

		if(list == null || list.size()<1)
			return "";

		Object obj = list.get(0);
		if(!(obj instanceof JSONObject)){
			list = toJSONObjectList_1_0_0(list);
		}

		StringBuffer sb = new StringBuffer();
		//排序
		sortList_1_0_0(list);

		JSONObject firstObj = (JSONObject) list.get(0);
		String key =(String) firstObj.keys().next();
		Object val=null;
		for(Object myobj : list){
			JSONObject jsonObj = (JSONObject)myobj;
			val = jsonObj.get(key);
			if(val !=null && !"".equals(val.toString()))
				sb.append(jsonObj.get(key));
			else
				sb.append(jsonObj.get("id"));
		}

		return sb.toString();
	}

	private static List<JSONObject> toJSONObjectList_1_0_0(List<?> list)throws Exception{
		List<JSONObject> result = new ArrayList<JSONObject>();
		for(Object obj : list){
			result.add(JSONObject.fromObject(obj));
		}
		return result;
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void sortList_1_0_0(List list)throws Exception{
		//排序
		if (list != null && list.size() > 1) {
			Collections.sort(list, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return o1.toString().compareTo(o2.toString());
				}
			});
		}
	}



	/**
	 * 1.解密密钥密文RSA：（密钥密文， 私钥）＝ 获得单次随机数
	 * 2.解密业务密文AES：（数据密文， 单次随机数）＝ 获得明文 ＋ 签名
	 * 3.返回解密后的对象bean
	 * @param 业务密文
	 * @param 密钥密文
	 * @param 解密私钥, 本系统的私钥
	 * @param 解密公钥, 来源系统的公钥
	 * 
	 * @return 解密后的明文json
	 * @throws Exception
	 */
	public static String decodeParamJson_1_0_0(String data, String encryptkey, String privateKey, String publickey) throws Exception{

		try{

			logger.info("decodeParamJson----------------------------------------------");
			logger.debug("----->>>>>data:"+data+"&encryptkey:"+encryptkey);

			String aesKey = RSA.decrypt(encryptkey, privateKey);

			if(StringUtils.isBlank(aesKey))
				throw new Exception("解密密钥密文失败，无法获得单次随机数！");

			String realData = AES.decryptFromBase64(data, aesKey);		
			if(StringUtils.isBlank(realData))
				throw new Exception("解密业务密文失败，无法获得业务数据和签名！");

			JSONObject jsonMap = JSONObject.fromObject(realData);
			Object list = jsonMap.get("data");
			String signData = list == null?getSignData_1_0_0(jsonMap):getSignData_1_0_0(list);
			if(StringUtils.isBlank(signData)){
				logger.warn("json数据为空，没有签名！");
			}else{
				String sign = (String) jsonMap.get("sign");
				if(!RSA.checkSign(signData, sign, publickey))
					throw new Exception("签名错误！");
			}
			//去除签名
			jsonMap.remove("sign");
			realData = jsonMap.toString();

			//保存参数（商户编码， 明文，单次随机数，签名，数据密文，密钥密文）
			logger.info("----->>>>>解密后参数:"+realData);
			logger.info("decodeParamJson----------------------------------------------");

			return realData;

		}catch(Exception e){
			logger.error("解密参数错误",e);
			throw new Exception("解密参数错误!");
		}

	}

	/**
	 * 1.生成签名RSA：（排序明文 + 私钥）＝ 签名
	 * 2.生成单次随机数 16位数字和字母
	 * 3.生成业务密文ASE：（明文 ＋ 签名， 单次随机数）＝ 业务密文
	 * 4.生成密钥密文RSA：（单次随机数， 公钥）＝ 密钥密文
	 * @param json 明文
	 * @param privateKey 私钥，本系统的私钥
	 * @param publicKey 公钥,目标系统的公钥
	 * 
	 * @return 加密后的json:{data:xxx,encryptkey:xxx,merchantcode:xxx}
	 * @throws Exception
	 */
	public static String encodeReturnJson_1_0_0(String json, String privateKey, String publicKey, String merchantCode)throws Exception{

		try{
			logger.info("encodeReturnJson----------------------------------------------");
			logger.debug("----->>>>>json:"+json);

			//设置签名
			json = setSign_1_0_0(json, privateKey);

			logger.info("----->>>>>签名后json:"+json);

			String aesKey = getRandom(16);
			if(StringUtils.isBlank(aesKey) || aesKey.length()!=16)
				throw new Exception("获取16位随机数错误");

			String data = AES.encryptToBase64(json, aesKey);
			if(StringUtils.isBlank(data))
				throw new Exception("加密业务密文错误");

			String encryptkey = RSA.encrypt(aesKey, publicKey);
			if(StringUtils.isBlank(encryptkey))
				throw new Exception("加密密钥密文错误");

			String result = "{\"data\":\""+data+"\",\"encryptkey\":\""+encryptkey+"\",\"merchantcode\":\""+merchantCode+"\"}";

			logger.debug("----->>>>>加密后json:"+result);
			logger.debug("encodeReturnJson----------------------------------------------");

			return result;

		}catch(Exception e){
			logger.error("加密参数错误",e);
			throw new Exception("加密参数错误!");
		}

	}

//	/**
//	 * 加密签名
//	 * @param json
//	 * @param privateKey
//	 * @param publicKey
//	 * @param merchantCode
//	 * @return
//	 * @throws Exception
//	 */
//	private static String encodeSign_1_0_0(String json, String privateKey, String publicKey, String merchantCode)throws Exception{
//
//		try{
//
//			//设置签名
//			json = setSign_1_0_0(json, privateKey);
//
//			//设置商户编号
//			json = json.substring(0,json.length()-1) + ",\"merchantcode\":\""+merchantCode+"\"}";
//
//			return json;
//
//		}catch(Exception e){
//			logger.error("加密签名错误",e);
//			throw new Exception("加密签名错误!");
//		}
//
//	}
//	/**
//	 * 解密签名
//	 * @param json
//	 * @param privateKey
//	 * @param publicKey
//	 * @param merchantCode
//	 * @return
//	 * @throws Exception
//	 */
//	private static String decodeSign_1_0_0(String json, String publicKey)throws Exception{
//
//		try{
//
//			JSONObject jsonMap = JSONObject.fromObject(json);		
//			List list = (List) jsonMap.get("data");		
//			String signData = getSignData_1_0_0(list);
//
//			String sign = (String) jsonMap.get("sign");
//			if(!RSA.checkSign(signData, sign, publicKey))
//				throw new Exception("签名错误！");
//
//			return json;
//
//		}catch(Exception e){
//			logger.error("解密签名错误",e);
//			throw new Exception("解密签名错误!");
//		}
//
//	}

	private static String setSign_1_0_0(String json, String privateKey)throws Exception{

		JSONObject jsonMap = JSONObject.fromObject(json);			
		Object list = jsonMap.get("data");
		String signData = list == null?getSignData_1_0_0(jsonMap):getSignData_1_0_0(list);

		String sign = "";
		if(StringUtils.isBlank(signData)){
			logger.warn("排序明文不正确,使用默认签名!");	
		}else{
			sign = RSA.sign(signData, privateKey);
			if(StringUtils.isBlank(sign))
				throw new Exception("签名失败");	
		}

		//设置签名
		json = json.substring(0,json.length()-1) + ", \"sign\":\""+sign + "\"}";

		return json;

	}
	////////////1.0.0版本加解密方法,所有新系统请不要用此功能///////////////////////////////////








	/**
	 * 1.解密密钥密文RSA：（密钥密文， 私钥）＝ 获得单次随机数
	 * 2.解密业务密文AES：（数据密文， 单次随机数）＝ 获得明文 ＋ 签名
	 * 3.返回解密后的对象bean
	 * @param 业务密文
	 * @param 密钥密文
	 * @param 解密私钥, 本系统的私钥
	 * @param 解密公钥, 来源系统的公钥
	 * 
	 * @return 解密后的明文json
	 * @throws Exception
	 */
	public static String decodeParamJson(String data, String encryptkey, String privateKey, String publickey) throws Exception{

		try{

			logger.info("decodeParamJson----------------------------------------------");
			logger.debug("----->>>>>data:"+data+"&encryptkey:"+encryptkey);

			String aesKey = RSA.decrypt(encryptkey, privateKey);

			if(StringUtils.isBlank(aesKey))
				throw new Exception("解密密钥密文失败，无法获得单次随机数！");

			String realData = AES.decryptFromBase64(data, aesKey);		
			if(StringUtils.isBlank(realData))
				throw new Exception("解密业务密文失败，无法获得业务数据和签名！");

			//验证签名
			TreeMap<String, String> map = JSON.parseObject(realData, new TypeReference<TreeMap<String, String>>(){});
			String sign = StringUtils.trimToEmpty(map.get("sign"));
			//签名数据
			String signTemp = getSignData(map);
			if(StringUtils.isBlank(signTemp)){
				logger.warn("数据为空，没有签名！");
			}else if(!RSA.checkSign(signTemp, sign, publickey)){
				throw new Exception("签名错误！");
			}	

			//去除签名
			map.remove("sign");
			realData = JSON.toJSONString(map);

			//保存参数（商户编码， 明文，单次随机数，签名，数据密文，密钥密文）
			logger.info("----->>>>>解密后参数:"+realData);
			logger.info("decodeParamJson----------------------------------------------");

			return realData;

		}catch(Exception e){
			logger.error("解密参数错误",e);
			throw new Exception("解密参数错误!");
		}

	}

	/**
	 * 1.生成签名RSA：（排序明文 + 私钥）＝ 签名
	 * 2.生成单次随机数 16位数字和字母
	 * 3.生成业务密文ASE：（明文 ＋ 签名， 单次随机数）＝ 业务密文
	 * 4.生成密钥密文RSA：（单次随机数， 公钥）＝ 密钥密文
	 * @param json 明文
	 * @param privateKey 私钥，本系统的私钥
	 * @param publicKey 公钥,目标系统的公钥
	 * 
	 * @return 加密后的json:{data:xxx,encryptkey:xxx,merchantcode:xxx}
	 * @throws Exception
	 */
	public static String encodeReturnJson(String json, String privateKey, String publicKey)throws Exception{

		try{
			logger.info("encodeReturnJson----------------------------------------------");
			logger.debug("----->>>>>json:"+json);
			if(StringUtils.isBlank(json) || !(json.substring(0, 1).equals("{")))
				throw new Exception("json参数不合法:"+json);
			//设置签名
			TreeMap<String, String> map = JSON.parseObject(json, new TypeReference<TreeMap<String, String>>(){});
			String signTemp = getSignData(map);
			String sign = "";
			if (StringUtils.isNotEmpty(privateKey)) {
				sign = RSA.sign(signTemp, privateKey);
			}
			json = json.substring(0,json.length()-1) + ",\"sign\":\""+sign+"\"}";

			logger.info("----->>>>>签名后json:"+json);

			String aesKey = getRandom(16);
			if(StringUtils.isBlank(aesKey) || aesKey.length()!=16)
				throw new Exception("获取16位随机数错误");

			String data = AES.encryptToBase64(json, aesKey);
			if(StringUtils.isBlank(data))
				throw new Exception("加密业务密文错误");

			String encryptkey = RSA.encrypt(aesKey, publicKey);
			if(StringUtils.isBlank(encryptkey))
				throw new Exception("加密密钥密文错误");

			String result = "{\"data\":\""+data+"\",\"encryptkey\":\""+encryptkey+"\"}";

			logger.debug("----->>>>>加密后json:"+result);
			logger.debug("encodeReturnJson----------------------------------------------");

			return result;

		}catch(Exception e){
			logger.error("加密参数错误",e);
			throw new Exception("加密参数错误!");
		}

	}

	/**
	 * 获取签名数据	
	 * @param realDataJson
	 * @return
	 * @throws Exception
	 */
	public static String getSignData(TreeMap<String, String> map)throws Exception{

		//对map中的值进行验证
		StringBuffer signData = new StringBuffer();
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			/** 把sign参数隔过去 */
			if (StringUtils.equals((String) entry.getKey(), "sign")) {
				continue;
			}
			signData.append(entry.getValue() == null ? "" : entry.getValue());
		}

		return signData.toString();
	}






	private ConvertUtils(){}


	public static void main(String[] args){
		
	}
	
	
}
