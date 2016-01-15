package com.ctfo.upp.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ctfo.upp.http.HttpUtils;

public class MobileTest {

	public static void main(String[] args) {
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("tokenId", "8CFF9130A9B7507D6E7B01E7AE38628D");
			JSONObject jsonMap = JSONObject.fromObject(map);

			String json = jsonMap.toString();
			String url = "http://crmmobileauth.sinoiov.net/mobile/provingTokenId";

			json = HttpUtils.sendRequest(json, url);
			
			System.out.println("=="+json);
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}

}
