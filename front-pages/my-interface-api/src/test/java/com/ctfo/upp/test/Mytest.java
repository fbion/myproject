package com.ctfo.upp.test;

import com.ctfo.upp.security.ConvertUtils;

public class Mytest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String data="xxtIR5P0t7afYs996PTXOsQLLb9J5PMP/YZUn3aZTV6smfOlrR728LInxv9q29q0U2vGuoWrnIm6RlnPRhsIzV+9Xg7586szQJdbErYJjkpPXdOcwEJ+mkTqbnVtTHRxys8CL4q6/o53Zo2unuLTIowz8DAvRhlWJa6r+zsVHPAHiI/hap6ZGoe9Qaglm9VfkObo3mhJtxmK1t/su6TmCVftEf4WROos4Jj7vFtUg5hF87CEs4bNQqoL6yaXa+xR8AU61aJh4XaYa72VyPjooN5q/fHvB1vJ+XBE+fN2oE1pp3voH/EZp0vTeu/g3SxnKMSDyYVEYt60mQPbAMWpjzMkPmC2EBQ3nq+E/jcEtfgnd9UNk5U2n0HaZvfEdpSzZu3l27tjIKjWCb6G135uk38rdCf0R3XJL0vCEefTeECk93hYQkrwV4Is09WJ2c627RgxqPhSH0HficddVjgu12TmqNzFyGgQz2lUoqrDu6Vab4j6uYejcF57SE1P9wyX";
		
		String encryptkey="mMOTldt+6Y7aDhYEZCOXtGU8mmGMxwijL2k+xlSKsPGgPJOQlHCfcywoTdIiN+6NETez7XnEe5yQnx04GTwOc3UysdLwhxInZAlPkEks/eRAqVH+X8wD9j5ZB/KwnAseH18Ss97dzss6XYPRD6etf85aA83ORNGeFbhm0PVIGBo=";

		String yb_publickey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdaAde+egFkLwV7THPum4nPSBAJ2MGOaYBBldbKdbnCX8emCqXtp8OB9WIWbDVQfpNAH/s53Z/NW1pmjhLbbgOGcsEGd/feh/QIL80Wv26afqlLG/lTvUavnSdQs732/5viT+G/C9YWWp4MxqKTd8Va1b9BkzfpuvqcmAtiHkPBwIDAQAB";
		
		String zj_gatewaykey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCWmMZI3tzaJUNgDqWIW8B2CE8NCdZMS++aUFlBvVmepMtmhGFwr98KOz0i+6p42c/RUXSxyoE/L3aMF2zZpUiJcnRmSdiLpPYI2VD3eoNUe2reIgKF82uvsoIh4EKsbsoPZbcQxvYjFQe3qgjVPNr8V91mz+vbLIkm5t3SPsMVAgMBAAECgYAdF6JxwF2fCv1qnS+si8t56LgztdUyDf3Qqp6kJdV5J07FB821c+g1mazqxJGrox9XQofl7siLBIrgP/I4B59YDtoX7zr9l2YRrHUpBJOQYg7PIOWzo6CcFluK83Hne+PGnJpWrJiJd53N/8MNOAK8RrErRn8GO+UZNDSPIhkwXQJBANyR7uNI5zYJVk8djR9O/++B8UZMQ51V5NZTY0jHwtgJ9/qOi9GZInim+ivR8EOCuZCXqq+zuWNdH37or29yxpcCQQDM9BZr3T0+2qTn1IfvTCO7fSJAWRaIuZtDsoCVBuJf4BQbB7KD+zQvyzhCPwXWLgUHkHJETD2XDW++GLR5ycUzAj83JESUjaU/3RW2sayWJynUtqea63X7331WF4K6rzYGzHcyLHDH9YCoqRXh3poyRnwdqc0CH+w46w70qzcwpYECQQCPlJAAkMVPOy07nBB+/AAsYMWV/tNihWTYUDz0KhZ8xCZRqVrOSzWMJfoLrssP+L1dRzxFzIN5Rth5fCUzDL8xAkEAt9mmIOOEeZppG7iw/RqtjCB1vngYJnIm8dVWRQGVg4t4qMXaxazKewT6rfd9xU3494SdM9x4AXq5ukj4it/ajA==";
		
		//解密
		try {
			
			String json = ConvertUtils.decodeParamJson(data, encryptkey, zj_gatewaykey, yb_publickey);
		
			System.out.println("---->>:"+json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
