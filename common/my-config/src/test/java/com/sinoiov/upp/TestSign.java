package com.sinoiov.upp;

import com.ctfo.upp.security.encrypt.RSA;

public class TestSign {

	public static void main(String[] args) {

		
		String zj_privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCWmMZI3tzaJUNgDqWIW8B2CE8NCdZMS++aUFlBvVmepMtmhGFwr98KOz0i+6p42c/RUXSxyoE/L3aMF2zZpUiJcnRmSdiLpPYI2VD3eoNUe2reIgKF82uvsoIh4EKsbsoPZbcQxvYjFQe3qgjVPNr8V91mz+vbLIkm5t3SPsMVAgMBAAECgYAdF6JxwF2fCv1qnS+si8t56LgztdUyDf3Qqp6kJdV5J07FB821c+g1mazqxJGrox9XQofl7siLBIrgP/I4B59YDtoX7zr9l2YRrHUpBJOQYg7PIOWzo6CcFluK83Hne+PGnJpWrJiJd53N/8MNOAK8RrErRn8GO+UZNDSPIhkwXQJBANyR7uNI5zYJVk8djR9O/++B8UZMQ51V5NZTY0jHwtgJ9/qOi9GZInim+ivR8EOCuZCXqq+zuWNdH37or29yxpcCQQDM9BZr3T0+2qTn1IfvTCO7fSJAWRaIuZtDsoCVBuJf4BQbB7KD+zQvyzhCPwXWLgUHkHJETD2XDW++GLR5ycUzAj83JESUjaU/3RW2sayWJynUtqea63X7331WF4K6rzYGzHcyLHDH9YCoqRXh3poyRnwdqc0CH+w46w70qzcwpYECQQCPlJAAkMVPOy07nBB+/AAsYMWV/tNihWTYUDz0KhZ8xCZRqVrOSzWMJfoLrssP+L1dRzxFzIN5Rth5fCUzDL8xAkEAt9mmIOOEeZppG7iw/RqtjCB1vngYJnIm8dVWRQGVg4t4qMXaxazKewT6rfd9xU3494SdM9x4AXq5ukj4it/ajA==";
		String yb_publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdaAde+egFkLwV7THPum4nPSBAJ2MGOaYBBldbKdbnCX8emCqXtp8OB9WIWbDVQfpNAH/s53Z/NW1pmjhLbbgOGcsEGd/feh/QIL80Wv26afqlLG/lTvUavnSdQs732/5viT+G/C9YWWp4MxqKTd8Va1b9BkzfpuvqcmAtiHkPBwIDAQAB";
		String zj_publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwlpjGSN7c2iVDYA6liFvAdghPDQnWTEvvmlBZQb1ZnqTLZoRhcK/fCjs9IvuqeNnP0VF0scqBPy92jBds2aVIiXJ0ZknYi6T2CNlQ93qDVHtq3iIChfNrr7KCIeBCrG7KD2W3EMb2IxUHt6oI1Tza/FfdZs/r2yyJJubd0j7DFQIDAQAB";
		String merchantcode = "10000418926";
		

		String signData="招商银行62258801158883731110000418926";
		
		String sign = "IJDYvL3NIHSxZEDKML45ODDd4OaVoMhxdyyAYQyKTIjU+3v5A3Cav+8L3qxfMncavwG05dJWYEVkJI/Q7QqTY4g6TbCFmqIiIOzfaOA8G6phPzHho9ylDZwl0lAHwyiJ0ivTOFkFd2M+DAqIh2GZ1Ot4yM3IguE2N91ScJJPlwk=";
		
		boolean dd = RSA.checkSign(signData, sign, yb_publickey);
		
		
		System.out.println("收到:"+dd);

	}

}
