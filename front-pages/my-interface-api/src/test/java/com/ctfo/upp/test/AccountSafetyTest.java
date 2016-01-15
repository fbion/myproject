package com.ctfo.upp.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.root.utils.MD5Util;

public class AccountSafetyTest extends TestBase {

	@Test
	public void validate() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423575567469620");
//			mpParas.put("mobileNo", "18500851753");
//			mpParas.put("smsCode", "");
			mpParas.put("payPassword", MD5Util.getEncryptedPwd("123456"));// TODO 加密后的参数？
//			mpParas.put("securityQuestion", "a");// 付值给q1?
//			mpParas.put("securityAnswer", "b");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/accountSafety/validate";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void querySecurityQuestion() {
		try {
			// TODO IAccountSafetyBusiness服务找不到
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423575567469620");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/accountSafety/querySecurityQuestion";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void resetSecurityQuestion() {
		try {
			// TODO IAccountSafetyBusiness服务找不到
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423575567469620");
			mpParas.put("securityTicket", "18bfefd3-e97f-4274-9d77-2ccf39a8a3f1");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/accountSafety/resetSecurityQuestion";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
