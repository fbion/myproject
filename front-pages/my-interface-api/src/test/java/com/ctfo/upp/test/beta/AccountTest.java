package com.ctfo.upp.test.beta;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;

public class AccountTest extends TestBase {

	@Test
	public void getAccountByUserId() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();

			mpParas.put("userId", "2b273d9e-bb80-4c6c-8112-ff94015ad17a");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/queryAccountInfoByUserId";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
