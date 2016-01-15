package com.ctfo.upp.encryptkey;

import org.apache.commons.lang.StringUtils;

import com.ctfo.upp.security.encrypt.AES;

public abstract class EncryptKeyUtils {
	
	
	/**
	 *　加密密钥
	 */
	public static KeyBean encryptKeys(KeyBean keybean, String key) throws Exception{

		if(StringUtils.isBlank(key))
			throw new Exception("加密密钥不能为空！");
		
		String publickey = keybean==null?"":keybean.getPublicKey();
		String privatekey = keybean==null?"":keybean.getPrivateKey();
		//加密
		publickey = StringUtils.isNotBlank(publickey)?AES.encryptToBase64(publickey, key):publickey;
		privatekey = StringUtils.isNotBlank(privatekey)?AES.encryptToBase64(privatekey, key):privatekey;

		keybean.setPublicKey(publickey);
		keybean.setPrivateKey(privatekey);

		return keybean;
	}
	
	/**
	 * 解密密钥
	 * 
	 */
	public static KeyBean decryptKeys(KeyBean keybean, String key) throws Exception{
		
		if(StringUtils.isBlank(key))
			throw new Exception("解密密钥不能为空！");
		
		String publickey=keybean==null?"":keybean.getPublicKey();
		String privatekey=keybean==null?"":keybean.getPrivateKey();
		
		//解密
		publickey = StringUtils.isNotBlank(publickey)?AES.decryptFromBase64(publickey, key):publickey;
		privatekey = StringUtils.isNotBlank(privatekey)?AES.decryptFromBase64(privatekey, key):privatekey;

		keybean.setPublicKey(publickey);
		keybean.setPrivateKey(privatekey);

		return keybean;
	}
	
	
}
