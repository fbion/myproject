package com.ctfo.upp.gatewayservice.servlet.yibao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctfo.gateway.bean.yibao.business.accountaccess.CreateAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CreateAccountBeanResponseYB;
import com.ctfo.gateway.intf.AccountServiceFacade;
import com.ctfo.upp.utils.SpringBUtils;

public class TestServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4240481862704545133L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		AccountServiceFacade accountServiceFacade = (AccountServiceFacade) SpringBUtils.getBean("accountServiceFacade");
		CreateAccountBeanRequestYB requestBean = new CreateAccountBeanRequestYB();
		requestBean.setAccountId("0987654321qwertyuiop000");
		requestBean.setUserName("黄宏旺");
		requestBean.setMobile("13811837912");
		requestBean.setIdCardType("2");
		requestBean.setIdCardNo("460102198704040319");
		requestBean.setRequestId("1qazxsw23edcvfr4");
		
//		LockAccountBeanRequestYB requestBean = new LockAccountBeanRequestYB();
//		requestBean.setAccountId("0987654321qwertyuiop");
//		requestBean.setRequestId("1qazxsw23edcvfr4");
//		requestBean.setAccountFreezeReason("使用不当，进行锁定");
		
//		UnlockAccountBeanRequestYB requestBean = new UnlockAccountBeanRequestYB();
//		requestBean.setAccountId("0987654321qwertyuiop");
//		requestBean.setRequestId("1qazxsw23edcvfr4");
//		requestBean.setAccountUnFreezeReason("可以使用");
		
//		CertifyAccountBeanRequestYB requestBean = new CertifyAccountBeanRequestYB();
//		requestBean.setAccountId("1110987654321qwertyuiop");
//		requestBean.setUserName("黄宏旺");
//		requestBean.setIdCardNo("460102198704040319");
//		requestBean.setBankCardNumber("6216610100011400765");
//		requestBean.setBankCode("BOC");
//		requestBean.setRequestId("1qazxsw23edcvfr4");
		
//		SetAccountCertifyBeanRequestYB requestBean = new SetAccountCertifyBeanRequestYB();
//		requestBean.setAccountId("1110987654321qwertyuiop");
//		requestBean.setRequestId("1qazxsw23edcvfr4");
//		requestBean.setUserName("黄宏旺");
//		requestBean.setIdCardType("2");
//		requestBean.setIdCardNo("460102198704040319");
		
		try {
			CreateAccountBeanResponseYB response = (CreateAccountBeanResponseYB) accountServiceFacade.createAccount(requestBean);
			System.out.println(response.isFlag());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
