package com.sinoiov.upp;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ctfo.upp.http.HttpUtils;

public class CallbackTest {

	public static void main(String[] args){
		
			try {
				
				CallbackTest tt = new CallbackTest();
				tt.transferAccounts();
				
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	}
	
	
	private String chptPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANIjCEAy/jOSJYyCGXwa5af+Co8/cr9K3W8QmjpSKKffjiumc8X09A24GTwRnVrhQALVYQcVvgkZEk7ON3ktkZ3yqwJvlvsHky1VXh+54vqYiik0QpDrQv/IKh+20+pyg7+X9UgHhezNskflemUfEKXv1XQLr/kW6qoh2It6cIBhAgMBAAECgYEAiNrb8sPDHl8eKUZJxrPVwfUHd4fA+aiOUlkjuLvtUnecJf2ABPqlP5IYjTCDpL5ya+YADRcx7sbvT3eqVTdCT8ZKZxswt1sq7wBieEvxZW9YfnPSVAGiVBeyrjHm71OVTj/i7ddggbP1CMs3OqCV8qgBeTF9Mi5l5qTPjLhcJuECQQD/YMsaYVV/AA8DMFH6ek/ZdZrBsS2uezqB1DksV4d6aRqs5NVpSgNLJ/xQc4J/KF7PLz1whGvIhK7AWW0mm3QVAkEA0qYI5mT6c6Djc0QlyBVlifCYNcsHKKM4rFF5i21Arq/JoT9pmHM0ryLBhbI+nuVwfSvWjQZeCJ5zj7b8NldyHQJAEmhzu28QrpreeihdgGSYpOApS5Tt6gnP6dPWWy/kABrbZWMJTxGasywqr9Hjsi7CxsRs9VNWQZnuzsaja4kBEQJBAJn9IIJ0jRYcJknsJoZ3Bcxp4otoiSou784gnW7Mhj1takODXMlCZuAk0z3OSLLV3X7TnjtnzTQVyTrlNMjw8g0CQQCvJzD8MUuYMv31BXQ2+O7YZNSjuyefhWX4GfnP5LhRtdksPNWa96r/sT2vc6VPQ5nofLHo7d8NnxwqxBmbS0PZ";
	private String chptPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSIwhAMv4zkiWMghl8GuWn/gqPP3K/St1vEJo6Uiin344rpnPF9PQNuBk8EZ1a4UAC1WEHFb4JGRJOzjd5LZGd8qsCb5b7B5MtVV4fueL6mIopNEKQ60L/yCofttPqcoO/l/VIB4XszbJH5XplHxCl79V0C6/5FuqqIdiLenCAYQIDAQAB";
	private String chptCode = "201501201602491637563";

	private String uppPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAPGE6DHyrUUAgqep/oGqMIsrJddJNFI1J/BL01zoTZ9+YiluJ7I3uYHtepApj+Jyc4Hfi+08CMSZBTHi5zWHlHQCl0WbdEkSxaiRX9t4aMS13WLYShKBjAZJdoLfYTGlyaw+tm7WG/MR+VWakkPX0pxfG+duZAQeIDoBLVfL++ihAgMBAAECgYAw2urBV862+5BybA/AmPWy4SqJbxR3YKtQj3YVACTbk4w1x0OeaGlNIAW/7bheXTqCVf8PISrA4hdL7RNKH7/mhxoX3sDuCO5nsI4Dj5xF24CymFaSRmvbiKU0Ylso2xAWDZqEs4Le/eDZKSy4LfXA17mxHpMBkzQffDMtiAGBpQJBAPn3mcAwZwzS4wjXldJ+Zoa5pwu1ZRH9fGNYkvhMTp9I9cf3wqJUN+fVPC6TIgLWyDf88XgFfjilNKNz0c/aGGcCQQD3WRxwots1lDcUhS4dpOYYnN3moKNgB07Hkpxkm+bw7xvjjHqI8q/4Jiou16eQURG+hlBZlZz37Y7P+PHF2XG3AkAyng/1WhfUAfRVewpsuIncaEXKWi4gSXthxrLkMteM68JRfvtb0cAMYyKvr72oY4Phyoe/LSWVJOcW3kIzW8+rAkBWekhQNRARBnXPbdS2to1f85A9btJP454udlrJbhxrBh4pC1dYBAlz59v9rpY+Ban/g7QZ7g4IPH0exzm4Y5K3AkBjEVxIKzb2sPDe34Aa6Qd/p6YpG9G6ND0afY+m5phBhH+rNkfYFkr98cBqjDm6NFhT7+CmRrF903gDQZmxCspY";
	private String uppPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDxhOgx8q1FAIKnqf6BqjCLKyXXSTRSNSfwS9Nc6E2ffmIpbieyN7mB7XqQKY/icnOB34vtPAjEmQUx4uc1h5R0ApdFm3RJEsWokV/beGjEtd1i2EoSgYwGSXaC32ExpcmsPrZu1hvzEflVmpJD19KcXxvnbmQEHiA6AS1Xy/vooQIDAQAB";
	private String uppCode = "201501211146539567052";

	public void transferAccounts() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", "MA1423143523675161");// 账户
			// map.put("accountNo", "MA1423386493521886");//账户
			map.put("userId", "f1e6a22s-950c-4135-a298-d0a2c25b387a");
			map.put("workOrderNo", "20152532156655");// 业务订单号
			map.put("orderAmount", "1270.00");// 金额
			map.put("storeCode", chptCode);
			map.put("collectMoneyUserId", "f2e6a22p-950c-4135-a298-d0a2c25b387b");
			map.put("collectMoneyAccountNo", "MA1423143530404198");

			JSONObject jsonMap = JSONObject.fromObject(map);

			String json = jsonMap.toString();
			String url = "http://pay.sinoiov.net/interface/upp/paymentTrade/transferAccounts";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);
			System.out.println(json);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
