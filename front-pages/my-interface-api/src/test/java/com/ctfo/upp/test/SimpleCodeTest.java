package com.ctfo.upp.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;

public class SimpleCodeTest extends TestBase {

	@Test
	public void queryList() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("type", "CRYPTOGUARD-QUERY");

			JSONObject jsonMap = JSONObject.fromObject(mpParams);
			String json = jsonMap.toString();

			String url = UPP_URL + "/simpleCode/queryList";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
