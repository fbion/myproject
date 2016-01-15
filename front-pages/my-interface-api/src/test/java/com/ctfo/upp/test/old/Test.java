package com.ctfo.upp.test.old;

import java.net.URLEncoder;

import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.security.ConvertUtils;

public class Test {

	public static void main(String[] args) {
		
		try{
			
		String url = "";	
	
			
		// TODO Auto-generated method stub
		String data = "DgZSNNFZwvo3N2wBv1/5FTTy7hnONbdLE9ghe9SV/oAijISaQxnoyHLFRCGjLUtcpBU+1uWQn1EJDyMfXrcwz0OhQQOwzWuj9znfQc0NWyqpWa12LjMsdxAPiHMB+vHfCIGQoFZdDiTrlVxP02O6tUA4pi+IAiJIhRT57SpyOSzegX5auE2eHBTg11EFYaG/TJwAomukAQMs9WD5buRXjiGkrad7Z0/fifSa1QKS6pDsyeyb32TyvwANhplxUo8KopIrwqgmVU2HqMsHnknAJmCm42rzOB0uJzEWXG+PuuBkI7xzYdUFpfyyWfaVMOo+jxp0JVj97o6W9A0YhItoL86IgsE9aj/qi7jARbLObajubhT4G8xaCZFR2pCp0KpsyKa/L622qEHSXxyTmJqG6hhTdXF8Epg2LwMQp056YJTOFr/TlPsSdsVJFCHIzWc1fNtlwxNzYMOUxLX1cMp40/cgVJY82QtwSmJBjG9AUhO1MxizdILTYpa72DfDfGpT";
		String encryptkey ="P3AU50RAkg/45b3M/bvJaHEqZMMQHi7YH0BrjIYv+fuM0j1zSQa87BehtSRQMZEhKTTeEGSHbVluuxq/LTiLurJXDzUKrnjVPYkP8vAx82Al6gZs5pL6pVf38NvgBBlaHfjeqIdBmUXU3coaLnGiqyDEBjnqBJchVmGroGEUOyo=";
		url = "http://pay.sinoiov.net/interface/upp/callback/mboileCallback?";
		url += "data="+ URLEncoder.encode(data, "UTF-8");
		url += "&encryptkey="+ URLEncoder.encode(encryptkey, "UTF-8");
		
		System.out.println("url:"+url);
		
		
		String ZJ_GATEWAYSERVICE_KEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCWmMZI3tzaJUNgDqWIW8B2CE8NCdZMS++aUFlBvVmepMtmhGFwr98KOz0i+6p42c/RUXSxyoE/L3aMF2zZpUiJcnRmSdiLpPYI2VD3eoNUe2reIgKF82uvsoIh4EKsbsoPZbcQxvYjFQe3qgjVPNr8V91mz+vbLIkm5t3SPsMVAgMBAAECgYAdF6JxwF2fCv1qnS+si8t56LgztdUyDf3Qqp6kJdV5J07FB821c+g1mazqxJGrox9XQofl7siLBIrgP/I4B59YDtoX7zr9l2YRrHUpBJOQYg7PIOWzo6CcFluK83Hne+PGnJpWrJiJd53N/8MNOAK8RrErRn8GO+UZNDSPIhkwXQJBANyR7uNI5zYJVk8djR9O/++B8UZMQ51V5NZTY0jHwtgJ9/qOi9GZInim+ivR8EOCuZCXqq+zuWNdH37or29yxpcCQQDM9BZr3T0+2qTn1IfvTCO7fSJAWRaIuZtDsoCVBuJf4BQbB7KD+zQvyzhCPwXWLgUHkHJETD2XDW++GLR5ycUzAj83JESUjaU/3RW2sayWJynUtqea63X7331WF4K6rzYGzHcyLHDH9YCoqRXh3poyRnwdqc0CH+w46w70qzcwpYECQQCPlJAAkMVPOy07nBB+/AAsYMWV/tNihWTYUDz0KhZ8xCZRqVrOSzWMJfoLrssP+L1dRzxFzIN5Rth5fCUzDL8xAkEAt9mmIOOEeZppG7iw/RqtjCB1vngYJnIm8dVWRQGVg4t4qMXaxazKewT6rfd9xU3494SdM9x4AXq5ukj4it/ajA==";
		String yb_public="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdaAde+egFkLwV7THPum4nPSBAJ2MGOaYBBldbKdbnCX8emCqXtp8OB9WIWbDVQfpNAH/s53Z/NW1pmjhLbbgOGcsEGd/feh/QIL80Wv26afqlLG/lTvUavnSdQs732/5viT+G/C9YWWp4MxqKTd8Va1b9BkzfpuvqcmAtiHkPBwIDAQAB";
		//解密
		String json = ConvertUtils.decodeParamJson_1_0_0(data, encryptkey, ZJ_GATEWAYSERVICE_KEY, yb_public);
	
		System.out.println("json:"+json);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

}
