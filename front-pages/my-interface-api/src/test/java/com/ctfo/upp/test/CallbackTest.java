package com.ctfo.upp.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.ctfo.upp.security.ConvertUtils;

public class CallbackTest {

	public static void main(String[] args) {

		try {

			Map<String, String> map = new HashMap<String, String>();

			map.put("workOrderNo", "A_rc20151112180057447");
			map.put("orderNo", "45a8762479574dfb897d9a0f8a5b4ce3");
			map.put("payType", "ACCOUNT");
			map.put("result", "1");
			map.put("merchantOrderAmount", "14372000.00");
			// 支付确认时间
			map.put("payConfirmDate", new Date().getTime() + "");

			String merchantcode = "201502111739308719358";
			String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1763JHqaQl5PLszSQokuW+Ck79KB+PC6U4T/utvXiXLYbS16D7hvX5Do71mNxLaWXd2H4L4Z19BZEWVLi1w9GCQMWZeJpYAhIt8ZZIWZHThqL8MJD3zby9MuKE0scutNn/huD7symGNQ4G1xF4xpaxMqg1f1aqA3cmnMGuHAv/wIDAQAB";
			String privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJziRdZiAuVlgBXVgr4WMbbLDdoOhtg/2uzZbVPYCRF7eljlMXtUIKv64AHxkrKr+Zasumewg6z0byQiBqWQXWrMRItA+MNMnfbNsmgNqTrJZTqiVBeOzDvduvmBdL3Q1AQ/cTfDx2G5hvWZ29VB4twpAefX2hm3bx6rsyI9k/KxAgMBAAECgYBAMBwe0M8aiGLVjBwIJhCFF6M9OoBVbhrmmTcv/M4JBxpR0MRkD1Gmy+hnIy3ASLi/u0AxOHlHhGMMx5csmlp9uFHqCzsrr5newdxDKXIj54P3CNniPYQIBGdbcJzQREvguBxQ2sqt6KQ9owbUIPSqUubbn+dvdnQvDV6K70MgGQJBAOTWunwYsdd3lPlnlkxuWWPo/DR7RbLsFfY8rQ/lHP6LpBv62ie00S5x10rl7EsYxMULQx/2/zbMy1IUVFY0xFsCQQCvgTFlOCRzA/1RgRhjXjsHgODqX15JHNW46KMrznDTiqSSxf/nAPjORk2cze8nF0c58bsqihwvENSHViCzDWLjAkEA4e8DhbxX8dU195wHb4TPqu3/+8ztvr6K3GeBspzRqJu0No/QTb2ffcyV9HPXfTmCJM1+w5N43OELqQk7TzWf2wJANoJ22AxG7d4oWJ8y9vH2fY8ezEyQmT62drbyrxFwY5OlJ90NbS8VF+QoCPLBO6/0NGALFNxOgqPEjsBGabZXpwJAVV0dTYerVLq6zp82KEv0IQzPU9uo6IoNI28mQ9UnlhlvzJ9nJNRqeqGd+5RRdWQEsgpKt3qmYJ7unhCWyngAuQ==";
			String uppCode = "201503111702337058419";
			String url = "";

			// 加密
			JSONObject jsonMap = JSONObject.fromObject(map);
			String json = ConvertUtils.encodeReturnJson(jsonMap.toString(), privatekey, publickey);

			jsonMap = JSONObject.fromObject(json);
			String data = (String) jsonMap.get("data");
			String encryptkey = (String) jsonMap.get("encryptkey");
			if (StringUtils.isBlank(data) || StringUtils.isBlank(encryptkey) || StringUtils.isBlank(uppCode))
				throw new Exception("加密后参数不合法或错误!");//
			String params = "data=" + URLEncoder.encode(data, "UTF-8");
			params += "&encryptkey=" + URLEncoder.encode(encryptkey, "UTF-8");
			params += "&merchantcode=" + URLEncoder.encode(uppCode, "UTF-8");

			System.out.println("url:" + url);
			System.out.println("params:" + params);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
