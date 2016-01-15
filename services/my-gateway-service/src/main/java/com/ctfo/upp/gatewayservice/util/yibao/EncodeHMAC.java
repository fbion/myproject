package com.ctfo.upp.gatewayservice.util.yibao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncodeHMAC {
	public static final String ENCODE = "UTF-8"; // UTF-8

	/**
	 * 对报文进行hmac签名，字符串按照UTF-8编码 (商户签名时直接调用这个即可，易宝只认以UTF-8编码的签名串)
	 * 
	 * @param aValue
	 *            - 字符串
	 * @param aKey
	 *            - 密钥
	 * @return - 签名结果，hex字符串
	 */
	public static String hmacSign(String aValue, String aKey) {
		System.out.println("加密内容：" + aValue);
		String hmac = hmacSign(aValue, aKey, ENCODE);
		System.out.println("HMAC：" + hmac);
		return hmac;
	}

	/**
	 * 对报文进行采用MD5进行hmac签名
	 * 
	 * @param aValue
	 *            - 字符串
	 * @param aKey
	 *            - 密钥
	 * @param encoding
	 *            - 字符串编码方式
	 * @return - 签名结果，hex字符串
	 */
	public static String hmacSign(String aValue, String aKey, String encoding) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encoding);
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	public static void main(String[] args) {
		try {
			// 测试用的签名key
			String testHmacKey = "Ce37zjy0N23nye28Mgc97Ia60k1a4R9L61918y696wb2YFn84u8H16A36o4L";
			// 签名前的串，实际上是将接口文档里的参数的值按顺序连接起来，如这里其实是将"CheckAccountCallBack","123","2013-07-03","okok"四个值连接起来的
			String source = "AccountDataSync110040011011P201404231748102963471398246508467";
			// 签名后的串，应该为02517e9bb951fb315bff3009f2eb578f
			System.out.println(EncodeHMAC.hmacSign(source, testHmacKey));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
