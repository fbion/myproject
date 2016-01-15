package com.sinoiov.upp.portal.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ctfo.upp.root.utils.MD5Util;

public class RegUtil {
	public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^1\\d{10}$");  		  
		Matcher m = p.matcher(mobiles);  	  
		return m.matches();  
	}
	public static boolean isImgText(String imgText){  
		Pattern p = Pattern.compile("^[a-zA-Z0-9]{4}$");  		  
		Matcher m = p.matcher(imgText);  	  
		return m.matches();  
	}
	public static boolean isMobileCode(String mobileCode){  
		Pattern p = Pattern.compile("^\\d{6}$");  		  
		Matcher m = p.matcher(mobileCode);  	  
		return m.matches();  
	}
	public static String isToken(){
		String result = "";
		 int max=9;
	     int min=0;
	     Random random = new Random();
	     for(int i=0;i<10;i++){
	    	 int s = random.nextInt(max)%(max-min+1) + min;
	    	 result += s;
	     }
		return MD5Util.getEncryptedPwd(result);
	}
	public static boolean iscryptoguard(String cryptoguard){  
		Pattern p = Pattern.compile("^[a-zA-Z0-9\\u4e00-\\u9fa5\\?]{1,15}$");  		  
		Matcher m = p.matcher(cryptoguard);  	  
		return m.matches();  
	}
}
