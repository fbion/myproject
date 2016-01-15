package com.ctfo.upp.util;

import java.util.Random;

import com.ctfo.upp.exception.UPPException;

/***
* 类描述：6位随机数-用于重置密码
* @author：liuguozhong  
* @date：2015年1月16日下午4:23:46 
* @version 1.0
* @since JDK1.6
*/ 
public class PasswordUtils {

	/**
	 * 获取6位随机数据密码
	 * @return
	 * @throws UPPException
	 */
	public static int resetRandomPassword()throws UPPException{
		Random rand=new Random();	
		int result=100000+rand.nextInt(900000);
		return result;
	}
}
