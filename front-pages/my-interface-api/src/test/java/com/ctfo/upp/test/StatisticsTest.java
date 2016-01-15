package com.ctfo.upp.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.root.utils.MD5Util;

public class StatisticsTest extends TestBase {

	
	@Test
	public void sumAccount() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			//mpParams.put("insideAccountNo", "MA1423460374157329");
			mpParams.put("accountType", "0");

			DynamicSqlParameter parameter = new DynamicSqlParameter();
			parameter.setEqual(mpParams);
			
			JSONObject jsonMap = JSONObject.fromObject(parameter);
			String json = jsonMap.toString();

			String url = UPP_URL + "/statistics/sumAccount";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sumAccountBalance() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("insideAccountNo", "MA1433830498076521");
			//mpParams.put("accountType", "0");

			DynamicSqlParameter parameter = new DynamicSqlParameter();
			parameter.setEqual(mpParams);
			
			JSONObject jsonMap = JSONObject.fromObject(parameter);
			String json = jsonMap.toString();

			String url = UPP_URL + "/statistics/sumAccountDetail";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
