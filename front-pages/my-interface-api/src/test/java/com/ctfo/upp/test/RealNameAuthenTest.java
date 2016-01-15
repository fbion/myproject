package com.ctfo.upp.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;

public class RealNameAuthenTest extends TestBase {

	@Test
	public void submitAuthenApplyInfo() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("applyTime", new Date().getTime());
			map.put("userName", "韦子宇");
			map.put("idcardType", "ID");
			map.put("idcardNo", "341221198510064636");
			map.put("bankcardNo", "6217971000002544293");
			map.put("bankCode", "POST");
			map.put("accontId", "91aa63c4-33d7-43fb-8b54-a14e7ae567dc");
			map.put("applyType", "2");
			map.put("applyStatus", "1");

			JSONObject jsonMap = JSONObject.fromObject(map);
			String json = jsonMap.toString();

			String url = UPP_URL + "/realNameAuthen/submitAuthenApplyInfo";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
