package com.ctfo.account.balance.test;

import org.junit.Test;

import com.ctfo.account.balance.IAppleOfPayorderBalance;
import com.ctfo.account.balance.impl.AppleOfPayordBalance;
import com.ctfo.upp.exception.UPPException;

public class AppleOfPayordBalanceTest extends BaseTest {
	@Test
	public void testAppleOfPayordBanlance(){
		//测试线下交易申请和生产订单对账接口
		IAppleOfPayorderBalance apple = (AppleOfPayordBalance)ctx.getBean("appleOfPayordBalance");
		try {
			apple.appleBanlanceOf();
		} catch (UPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
