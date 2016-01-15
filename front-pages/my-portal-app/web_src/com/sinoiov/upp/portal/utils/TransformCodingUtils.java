package com.sinoiov.upp.portal.utils;

import java.io.UnsupportedEncodingException;

public class TransformCodingUtils {
	public static String transformCoding(String param){


		String temp="";
		try {
			if(param!=null&&!"".equals(param)){
				 temp=new String(param.getBytes("ISO8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
}
