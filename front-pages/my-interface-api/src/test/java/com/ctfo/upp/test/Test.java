package com.ctfo.upp.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ctfo.upp.http.HttpUtils;

public class Test {
	
	private static String chptPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANIjCEAy/jOSJYyCGXwa5af+Co8/cr9K3W8QmjpSKKffjiumc8X09A24GTwRnVrhQALVYQcVvgkZEk7ON3ktkZ3yqwJvlvsHky1VXh+54vqYiik0QpDrQv/IKh+20+pyg7+X9UgHhezNskflemUfEKXv1XQLr/kW6qoh2It6cIBhAgMBAAECgYEAiNrb8sPDHl8eKUZJxrPVwfUHd4fA+aiOUlkjuLvtUnecJf2ABPqlP5IYjTCDpL5ya+YADRcx7sbvT3eqVTdCT8ZKZxswt1sq7wBieEvxZW9YfnPSVAGiVBeyrjHm71OVTj/i7ddggbP1CMs3OqCV8qgBeTF9Mi5l5qTPjLhcJuECQQD/YMsaYVV/AA8DMFH6ek/ZdZrBsS2uezqB1DksV4d6aRqs5NVpSgNLJ/xQc4J/KF7PLz1whGvIhK7AWW0mm3QVAkEA0qYI5mT6c6Djc0QlyBVlifCYNcsHKKM4rFF5i21Arq/JoT9pmHM0ryLBhbI+nuVwfSvWjQZeCJ5zj7b8NldyHQJAEmhzu28QrpreeihdgGSYpOApS5Tt6gnP6dPWWy/kABrbZWMJTxGasywqr9Hjsi7CxsRs9VNWQZnuzsaja4kBEQJBAJn9IIJ0jRYcJknsJoZ3Bcxp4otoiSou784gnW7Mhj1takODXMlCZuAk0z3OSLLV3X7TnjtnzTQVyTrlNMjw8g0CQQCvJzD8MUuYMv31BXQ2+O7YZNSjuyefhWX4GfnP5LhRtdksPNWa96r/sT2vc6VPQ5nofLHo7d8NnxwqxBmbS0PZ";
	
	private static String chptPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSIwhAMv4zkiWMghl8GuWn/gqPP3K/St1vEJo6Uiin344rpnPF9PQNuBk8EZ1a4UAC1WEHFb4JGRJOzjd5LZGd8qsCb5b7B5MtVV4fueL6mIopNEKQ60L/yCofttPqcoO/l/VIB4XszbJH5XplHxCl79V0C6/5FuqqIdiLenCAYQIDAQAB";
	
	private static String uppPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMZDZfeUhf+53j/YwWzYGvKNjl2O1b9C+pabmPFlE42X9ZJwTfpIDePlW5Qj227/yQZ7yhwbrnQA6sSbuDdYMlGdDlp0p7p5w2poDCwfpmWY4wjOvTbmFGo/daoM4Wd+3a+3EpYIjlSSF2SqgMuKrwS0hLTCt/9YZUNLNE7KGZgfAgMBAAECgYBrmDwIBjRKOaZhY9JkrUrCgwvVviRFraWsgjiYGFBqMSN2GzhWMTBIkzoFxQfazzlbKWbfpi4gijbeB9wLPPPg3XbJpaJCanRz48sfsYbnY1kl+EA+URcUIyresWs1RY08SVchmNbDgbc2WnaFP2zGnHV2OasZ5a82lstGBC7v0QJBAPKVN94kwKlgB9I0e1BZwNnuJMPYreriMpUp2firkPyjkk4Xf5j5VEMiCKDNMvLUX1kWqpae9kfq/nMGXw6Jf70CQQDROqZDVWJSef+VdgEw6a4wD3EGUDYcDTnSo+bLG/poAjee4f0zRoqxCBoxqG1R4/Ww6VH4YO5XFLRtD74g9bcLAkEAprThNepv9HYoKc8jR27G132PCoAn1RkpxdSDFR9ifhwy+TOw3oHJ5HDJGSircLF97Q1koioP+hCGWKnyPV1EyQJAJvcCMbX8CpoXxBDm5eIPA3pMtT1I5m58H0NQ3vCF8MrA6wDxM5RmjdORdUAFo+ONRPYewt/iYytC8rCk2uSHwwJAPSActQWSVXRTwVRvqoYbi/oKb3sGWUM4zyxLn4v8AmS7or9GEh93VYYeA8DIhVMnRMT7nEh7/sfB3u91vpfWQw==";
	
	private static String uppPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGQ2X3lIX/ud4/2MFs2BryjY5djtW/QvqWm5jxZRONl/WScE36SA3j5VuUI9tu/8kGe8ocG650AOrEm7g3WDJRnQ5adKe6ecNqaAwsH6ZlmOMIzr025hRqP3WqDOFnft2vtxKWCI5UkhdkqoDLiq8EtIS0wrf/WGVDSzROyhmYHwIDAQAB";
	
	private static String uppCode="201501211146539567052";
	
	private static String chptCode="201501201602491637563";
	

	public static void main(String[] args) {
		try{//{"userId":"ab076f93-6b52-4b1b-861e-83a1b529549f"}
			
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("id", "0d881075-8560-4fe0-8a1e-011a5b7d1f9a");
			
			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url ="http://192.168.104.68:7280/upp/account/queryAccountHistoryById";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
