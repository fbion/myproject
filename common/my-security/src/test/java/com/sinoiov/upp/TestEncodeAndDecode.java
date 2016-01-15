package com.sinoiov.upp;

import net.sf.json.JSONObject;

import com.ctfo.upp.security.ConvertUtils;

public class TestEncodeAndDecode {
	
	protected static String chptPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL5aU15gKjyvZF3OE3y2YqakXruvGbEomgSzxH+t1ln3vXMvaVrj8LQ3vmXzg/sBF/DVl9EqTA2TUplyJlfiCVt0Oo095TLlkFE5s+XNcw8PJQYx9sPos1a+Zsn1+3bxu2Jy7bCc1HxAl1gxm2wUjbUIa1LA5om5pdHbzXk8O2NtAgMBAAECgYEAnyrIK1Id/0BpnEaE5PNc6hrnW+i+gvSAVQ48cMbbRb9yGq66WdXfn9I80uMcqPXxHuB7YdsMXQ2jM8vPZA0N5IgU5CRQsOPcLL/3lOi4NjaN50sr7/ro20BUiHjGJUD4kEbTOnwtyUdvq/lY0Ee0sPgEjq8aQh3Tc6LocYjrn8ECQQDzKu6lBco7UqVw/DRur0qiAJcM5RPR06sFXFuPZidAoYBK5Q5n76KnznR99wIJxb6C09MNti1Ye6DQFsRTy+XrAkEAyGXkcLZZi5v+6wFLHkYNTtn/qIeVJjrUkK4BgrGS7bqnL5EJSDbRuWgBLwXpLhyuyFAWJMJBqHHwgvMUWa/OBwJAfOU3L8Fajr+zQ+X2FEeqgKzm6HOci3A5bgZy7KNeU97foxglHxNBKwlqYeyFunk7kby0sV05yzGsdlTLP/MBQwJAUuPyjbyfZWPtGX+7dpY7QvZhDXCnfNywt/eBoHGa+MJQDRjM4hXqweoHM1Aal3w9v5WA00uWpPocJc4uCLrzWQJAZWsabRZcGGbOw7bTeVrLWhyon74NnnIgdCOBaknzYwSH+eBJE6tT7FLjHgtgPSPnB60nQ6o6BR68N24D3jIK/Q==";
	protected static String chptPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+WlNeYCo8r2RdzhN8tmKmpF67rxmxKJoEs8R/rdZZ971zL2la4/C0N75l84P7ARfw1ZfRKkwNk1KZciZX4glbdDqNPeUy5ZBRObPlzXMPDyUGMfbD6LNWvmbJ9ft28bticu2wnNR8QJdYMZtsFI21CGtSwOaJuaXR2815PDtjbQIDAQAB";
	protected static String chptCode = "201505210702567989876";

	protected static String uppPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAPGE6DHyrUUAgqep/oGqMIsrJddJNFI1J/BL01zoTZ9+YiluJ7I3uYHtepApj+Jyc4Hfi+08CMSZBTHi5zWHlHQCl0WbdEkSxaiRX9t4aMS13WLYShKBjAZJdoLfYTGlyaw+tm7WG/MR+VWakkPX0pxfG+duZAQeIDoBLVfL++ihAgMBAAECgYAw2urBV862+5BybA/AmPWy4SqJbxR3YKtQj3YVACTbk4w1x0OeaGlNIAW/7bheXTqCVf8PISrA4hdL7RNKH7/mhxoX3sDuCO5nsI4Dj5xF24CymFaSRmvbiKU0Ylso2xAWDZqEs4Le/eDZKSy4LfXA17mxHpMBkzQffDMtiAGBpQJBAPn3mcAwZwzS4wjXldJ+Zoa5pwu1ZRH9fGNYkvhMTp9I9cf3wqJUN+fVPC6TIgLWyDf88XgFfjilNKNz0c/aGGcCQQD3WRxwots1lDcUhS4dpOYYnN3moKNgB07Hkpxkm+bw7xvjjHqI8q/4Jiou16eQURG+hlBZlZz37Y7P+PHF2XG3AkAyng/1WhfUAfRVewpsuIncaEXKWi4gSXthxrLkMteM68JRfvtb0cAMYyKvr72oY4Phyoe/LSWVJOcW3kIzW8+rAkBWekhQNRARBnXPbdS2to1f85A9btJP454udlrJbhxrBh4pC1dYBAlz59v9rpY+Ban/g7QZ7g4IPH0exzm4Y5K3AkBjEVxIKzb2sPDe34Aa6Qd/p6YpG9G6ND0afY+m5phBhH+rNkfYFkr98cBqjDm6NFhT7+CmRrF903gDQZmxCspY";
	protected static String uppPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDxhOgx8q1FAIKnqf6BqjCLKyXXSTRSNSfwS9Nc6E2ffmIpbieyN7mB7XqQKY/icnOB34vtPAjEmQUx4uc1h5R0ApdFm3RJEsWokV/beGjEtd1i2EoSgYwGSXaC32ExpcmsPrZu1hvzEflVmpJD19KcXxvnbmQEHiA6AS1Xy/vooQIDAQAB";
	protected static String uppCode = "201505220058320392618";
	

	public static void main(String[] args) {
		
		
		try {
			
			String json = "{\"data\":[]}";
			
			json = ConvertUtils.encodeReturnJson(json, chptPrivateKey, uppPublicKey);
			
			System.out.println("json:"+json);
			
			JSONObject jsonMap = JSONObject.fromObject(json);
			
			String data = jsonMap.getString("data");
			String encryptkey = jsonMap.getString("encryptkey");
			
			json = ConvertUtils.decodeParamJson(data, encryptkey, uppPrivateKey, chptPublicKey);
			
			
			System.out.println("json:"+json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
