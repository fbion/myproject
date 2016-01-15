package com.ctfo.sinoiov.mobile.webapi.util;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @Title:md5加密类
 * @Description:
 * @Author:fx
 * @Since:2015-1-20
 * @Version:1.1.0
 */
public class MD5Utils {

	public static String getDefaultMd5String(String source) {
		return md5String(source, "UTF-8", "");
	}

	public static String getDefaultMd5String(String source, String md5Key) {
		return md5String(source, "UTF-8", md5Key);
	}

	public static String md5String(String source, String encoder, String mid5key) {
		StringBuilder hexString = new StringBuilder();
		if (!isEmpty(source)) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update((source + mid5key).getBytes(encoder));
				byte[] hash = md.digest();
				for (int i = 0; i < hash.length; i++) {
					if ((0xff & hash[i]) < 0x10) {
						hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
					} else {
						hexString.append(Integer.toHexString(0xFF & hash[i]));
					}
				}
			} catch (NoSuchAlgorithmException e) {
			} catch (UnsupportedEncodingException e) {
			}
		}
		return hexString.toString();
	}

	public static void main(String[] args) {
		String md5Str = MD5Utils.getDefaultMd5String("000000");
		System.out.println(md5Str);
	}
	
}
