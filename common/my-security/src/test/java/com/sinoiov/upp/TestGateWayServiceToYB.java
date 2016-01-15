package com.sinoiov.upp;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ctfo.upp.http.HttpUtils;

public class TestGateWayServiceToYB {

	public static void main(String[] args){
		testYB();
		//testZJ();
	}
	
	
	
	public static void testZJ(){
		
		String merchantcode = "201502111739308719358";
		String upp_publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCc4kXWYgLlZYAV1YK+FjG2yw3aDobYP9rs2W1T2AkRe3pY5TF7VCCr+uAB8ZKyq/mWrLpnsIOs9G8kIgalkF1qzESLQPjDTJ32zbJoDak6yWU6olQXjsw73br5gXS90NQEP3E3w8dhuYb1mdvVQeLcKQHn19oZt28eq7MiPZPysQIDAQAB";
		String upp_privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJziRdZiAuVlgBXVgr4WMbbLDdoOhtg/2uzZbVPYCRF7eljlMXtUIKv64AHxkrKr+Zasumewg6z0byQiBqWQXWrMRItA+MNMnfbNsmgNqTrJZTqiVBeOzDvduvmBdL3Q1AQ/cTfDx2G5hvWZ29VB4twpAefX2hm3bx6rsyI9k/KxAgMBAAECgYBAMBwe0M8aiGLVjBwIJhCFF6M9OoBVbhrmmTcv/M4JBxpR0MRkD1Gmy+hnIy3ASLi/u0AxOHlHhGMMx5csmlp9uFHqCzsrr5newdxDKXIj54P3CNniPYQIBGdbcJzQREvguBxQ2sqt6KQ9owbUIPSqUubbn+dvdnQvDV6K70MgGQJBAOTWunwYsdd3lPlnlkxuWWPo/DR7RbLsFfY8rQ/lHP6LpBv62ie00S5x10rl7EsYxMULQx/2/zbMy1IUVFY0xFsCQQCvgTFlOCRzA/1RgRhjXjsHgODqX15JHNW46KMrznDTiqSSxf/nAPjORk2cze8nF0c58bsqihwvENSHViCzDWLjAkEA4e8DhbxX8dU195wHb4TPqu3/+8ztvr6K3GeBspzRqJu0No/QTb2ffcyV9HPXfTmCJM1+w5N43OELqQk7TzWf2wJANoJ22AxG7d4oWJ8y9vH2fY8ezEyQmT62drbyrxFwY5OlJ90NbS8VF+QoCPLBO6/0NGALFNxOgqPEjsBGabZXpwJAVV0dTYerVLq6zp82KEv0IQzPU9uo6IoNI28mQ9UnlhlvzJ9nJNRqeqGd+5RRdWQEsgpKt3qmYJ7unhCWyngAuQ==";
		
		String chpt_publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1763JHqaQl5PLszSQokuW+Ck79KB+PC6U4T/utvXiXLYbS16D7hvX5Do71mNxLaWXd2H4L4Z19BZEWVLi1w9GCQMWZeJpYAhIt8ZZIWZHThqL8MJD3zby9MuKE0scutNn/huD7symGNQ4G1xF4xpaxMqg1f1aqA3cmnMGuHAv/wIDAQAB";
		String chpt_privatekey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALXvrckeppCXk8uzNJCiS5b4KTv0oH48LpThP+629eJcthtLXoPuG9fkOjvWY3EtpZd3YfgvhnX0FkRZUuLXD0YJAxZl4mlgCEi3xlkhZkdOGovwwkPfNvL0y4oTSxy602f+G4PuzKYY1DgbXEXjGlrEyqDV/VqoDdyacwa4cC//AgMBAAECgYAgbti5KLiiwBK91nWBbx0wjY60PHi76DiZm8oM4RrWPpbMKNRximn2Q55J+n8BtxR0+vhBCR/cOSYbCx1miNYU5fx7sTVo74J9PIYfX596H8oBdVxkzHY0XCce15jcmHaB0sn5z3CrlL4mjzl2tw5s26Rwz7GtQUR2cHV+a8awuQJBAOZXbtyN0yLi79Hm6inUmp6leIOoHxaAJsQuT7AjDidOhqU8xvQIZ2KUvYklkNfNqhlRi+jSH93hJhvh/Yb5tuMCQQDKM+O2y8OZTJj4B7XWLZtvRhGfEKyTtVr2OsbZwG4VlmdbBZjnKqPfE4QusCVVKZaz0KIEGag+B9G2BxnzOtE1AkEAn1EgI7qQq5ONToBJvC8J1usaZO2WuwheR9jEU06bzoIYzRcxgL6DS4MXaVGrUig1G5f0jk8vrVa59hUkNwRnAwJBAIzbdOgkLlAecflmtZ9MChmMAD6EyRPkpEcfTuLmEEntinZ4AboXlCyUVeKIRpl67Ua6MgMVNIRxwf9CFyu/b3kCQQCYpBj1XjPMYrNAvruRelnMdaDPRhTyCIJX7xkcYfcUkxAGRQqu6S0Q57DnQZ5WeHYydEZHiVmwkC1SIp6r2drr";
		
		
		try {
				
			Map<String,String> map = new HashMap<String,String>();
			map.put("userId", "ea32affa-3ef1-41c7-962d-9addd9d3e7fe");
			JSONObject jsonMap = JSONObject.fromObject(map);
			String json = jsonMap.toString();
			
			String url = "http://pay.sinoiov.net/interface/upp/account/queryAccountInfoByUserId";
				
			json = HttpUtils.sendRequest(json, url, chpt_privatekey, upp_publickey, merchantcode);
			//json = HttpUtils.sendRequest(json, url, zj_privatekey, yb_publickey, merchantaccount, false);
						
			System.out.println("解密后:"+json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void testYB(){
		
		String zj_privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCWmMZI3tzaJUNgDqWIW8B2CE8NCdZMS++aUFlBvVmepMtmhGFwr98KOz0i+6p42c/RUXSxyoE/L3aMF2zZpUiJcnRmSdiLpPYI2VD3eoNUe2reIgKF82uvsoIh4EKsbsoPZbcQxvYjFQe3qgjVPNr8V91mz+vbLIkm5t3SPsMVAgMBAAECgYAdF6JxwF2fCv1qnS+si8t56LgztdUyDf3Qqp6kJdV5J07FB821c+g1mazqxJGrox9XQofl7siLBIrgP/I4B59YDtoX7zr9l2YRrHUpBJOQYg7PIOWzo6CcFluK83Hne+PGnJpWrJiJd53N/8MNOAK8RrErRn8GO+UZNDSPIhkwXQJBANyR7uNI5zYJVk8djR9O/++B8UZMQ51V5NZTY0jHwtgJ9/qOi9GZInim+ivR8EOCuZCXqq+zuWNdH37or29yxpcCQQDM9BZr3T0+2qTn1IfvTCO7fSJAWRaIuZtDsoCVBuJf4BQbB7KD+zQvyzhCPwXWLgUHkHJETD2XDW++GLR5ycUzAj83JESUjaU/3RW2sayWJynUtqea63X7331WF4K6rzYGzHcyLHDH9YCoqRXh3poyRnwdqc0CH+w46w70qzcwpYECQQCPlJAAkMVPOy07nBB+/AAsYMWV/tNihWTYUDz0KhZ8xCZRqVrOSzWMJfoLrssP+L1dRzxFzIN5Rth5fCUzDL8xAkEAt9mmIOOEeZppG7iw/RqtjCB1vngYJnIm8dVWRQGVg4t4qMXaxazKewT6rfd9xU3494SdM9x4AXq5ukj4it/ajA==";
		String yb_publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdaAde+egFkLwV7THPum4nPSBAJ2MGOaYBBldbKdbnCX8emCqXtp8OB9WIWbDVQfpNAH/s53Z/NW1pmjhLbbgOGcsEGd/feh/QIL80Wv26afqlLG/lTvUavnSdQs732/5viT+G/C9YWWp4MxqKTd8Va1b9BkzfpuvqcmAtiHkPBwIDAQAB";
		String merchantaccount = "10000418926";
		
		try {
			
			//String url = "https://ok.yeepay.com/payapi/api/bankcard/check";
			//String json = "{\"cardno\":\"6225880115888373\", \"merchantaccount\":\"10000418926\"}";
			//解密后:{"cardlist":"[{\"bankcode\":\"CMBCHINA\",\"bindid\":\"4022137\",\"bindvalidthru\":1439808796,\"card_last\":\"3437\",\"card_name\":\"招商银行信用卡\",\"card_top\":\"622576\",\"merchantaccount\":\"10000418926\",\"phone\":\"13811837912\"}]","identityid":"a9b0e150-7887-4ade-b0b9-5ac038eaebd6","identitytype":"6","merchantaccount":"10000418926"}

			
			
			String url = "https://ok.yeepay.com/payapi/api/bankcard/bind/list";
			String json = "{\"identityid\":\"a9b0e150-7887-4ade-b0b9-5ac038eaebd6\",\"identitytype\":6, \"merchantaccount\":\"10000418926\"}";
					
			System.out.println("发送:"+json);					
			//发送
			json = HttpUtils.sendRequest(json, url, zj_privatekey, yb_publickey, merchantaccount, false);
			
			System.out.println("解密后:"+json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
