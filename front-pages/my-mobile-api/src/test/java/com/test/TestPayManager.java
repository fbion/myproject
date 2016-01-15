package com.test;

import java.util.Date;

import org.junit.Test;

import com.ctfo.sinoiov.mobile.webapi.bean.request.AccountRechargeByTransferReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.AccountSafetyReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.GetFastPayUrlForAccountRechargeReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.GetFastPayUrlReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.GetSmsCodeReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.IsPayPasswordReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.IsSetPayPasswordReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.ModifyMobileNoReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.ModifyPayPasswordByTicketReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.ModifyPayPasswordReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.OilRechargeByAccountReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.GetAccountInfoByUserIdReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.QueryPayCenterTradeInfoReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.QueryPayCenterTradeListReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.QueryPayeeListReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.SetPayPasswordReq;
import com.ctfo.sinoiov.mobile.webapi.bean.request.ValidateAccountInfosReq;
import com.ctfo.sinoiov.mobile.webapi.util.MD5Utils;

/**
 * 支付接口测试类
 * 
 * @author sunchuanfu
 */
public class TestPayManager {

	@Test
	public void modifyPayPasswordManagerImpl() {
		try {
			// OK
			ModifyPayPasswordReq req = new ModifyPayPasswordReq();
			req.setAccountNo("MA1423575567469620");
			req.setOldMD5(MD5Utils.getDefaultMd5String("123456"));
			req.setNewMD5(MD5Utils.getDefaultMd5String("654321"));
			req.setSmsCode("252555");
			req.setMobileNo("18500851753");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000001", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void setPayPasswordManagerImpl() {
		try {
			// OK
			SetPayPasswordReq req = new SetPayPasswordReq();
			req.setUserId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setOwnerUserId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setOwnerLoginName("18500851753");
			req.setMobileNo("18500851753");
			req.setMd5(MD5Utils.getDefaultMd5String("6543210"));
			req.setSmsCode("4545454");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000002", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);// 该账户［MA1423485164023705］已经设置过密码！
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void isPayPasswordManagerImpl() {
		try {
			// OK
			IsPayPasswordReq req = new IsPayPasswordReq();
			req.setAccountNo("MA1423575567469620");
			req.setMobileNo("18500851753");
			req.setMd5(MD5Utils.getDefaultMd5String("123456scf"));
			req.setSmsCode("045541");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000003", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);// 该账户［MA1423485164023705］已经设置过密码！
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void isSetPayPasswordManagerImpl() {
		try {
			// OK
			IsSetPayPasswordReq req = new IsSetPayPasswordReq();
			req.setAccountNo("MA1423575567469620");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000004", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取易宝快捷支付URL(可以使用此接口获取快捷支付URL对油卡进行充值)
	 */
	@Test
	public void getFastPayUrlManagerImpl() {
		try {
			// OK
			// 注：直接运行报错，可以粘出URL FF运行
			GetFastPayUrlReq req = new GetFastPayUrlReq();
			req.setAccountNo("MA1423575567469620");
			req.setMerchantOrderNo("11111");// 业务订单号
			req.setMerchantOrderRemark("remark");
			req.setMerchantOrderAmount("0.01");
			req.setCallbackURL("ff");
			req.setFcallbackURL("fff");
			req.setUserId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setProductCatalog("1");
			req.setProductName("oid recharge");
			req.setIdentityType("2");
			req.setIdentityId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setClentType("MOBILE");
			req.setClentId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setUserUa("FFF");
			req.setUserLoginName("FFF");
			req.setUserIP("192.168.15.224");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000005", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getAccountInfoByUserIdManagerImpl() {
		try {
			// OK
			GetAccountInfoByUserIdReq req = new GetAccountInfoByUserIdReq();
			req.setUserId("30a76b32-806f-44d7-b7eb-f72df206c07f");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000006", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 线下账户充值
	@Test
	public void accountRechargeByTransferManagerImpl() {
		try {
			// TODO 上传文件如何测试？
			AccountRechargeByTransferReq req = new AccountRechargeByTransferReq();
			req.setUserId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setPayer("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setPayerName("MA1423575567469620");
			req.setPayAccount("MA1423575567469620");
			req.setPayee("MA1432628700496379");
			req.setPayeeName("MA1432628700496379");
			req.setRemittance("0.01");
			req.setPayTime(String.valueOf(new Date().getTime()));
			req.setInsideAccountNo("MA1423575567469620");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");

			String url = BaseTest.url + BaseTest.getParam("Z000007", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getSmsCodeManagerImpl() {
		try {
			// OK
			GetSmsCodeReq req = new GetSmsCodeReq();
			req.setAccountNo("MA1423575567469620");
			req.setMobileNo("18500851753");
			// req.setIsCreateAccount("0");//
			// 0代表开户时获取验证码(此时账户号属性为空，手机APP服务端置其为商户号)；1代表其它情况
			// req.setIsModifyMobileNo("0");// 0代表是修改手机号码时需要发送给新手机号验证码；1代表其它情况

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000008", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询支付中心指定账户的收入支出交易列表
	@Test
	public void QueryAccountDetailByPageManagerImpl() {
		try {
			// OK
			QueryPayCenterTradeListReq req = new QueryPayCenterTradeListReq();
			req.setAccountNo("MA1423575567469620");
			req.setUserId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setBookAccountType("deduction");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000009", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询支付中心指定账户的单个交易明细信息
	@Test
	public void getAccountDetailByIdManagerImpl() {
		try {
			// OK
			QueryPayCenterTradeInfoReq req = new QueryPayCenterTradeInfoReq();
			// req.setId("04faed24-30a5-453c-bd0e-bb8055a9ae65");// 交易Id
			req.setId("0d881075-8560-4fe0-8a1e-011a5b7d1f9a");// 交易Id

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000010", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取"账户充值"易宝快捷支付URL地址
	@Test
	public void getFastPayUrlForAccountRechargeManagerImpl() {
		try {
			// OK
			GetFastPayUrlForAccountRechargeReq req = new GetFastPayUrlForAccountRechargeReq();
			req.setUserId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setAccountNo("MA1423575567469620");
			req.setAmount("1.2");
			req.setClentType("MOBILE");
			req.setPayChannel("FASTPAY");
			req.setBankCardCode("a");
			req.setBankCardType("a");
			req.setBankCardName("a");
			req.setRemarks("a");
			req.setProductCatalog("2");
			req.setProductName("account recharge");
			req.setUserIp("192.168.15.224");
			req.setIdentityType("2");
			req.setIdentityId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setFcallbackUrl("a");
			req.setWorkOrderNo("787878787878");// 业务订单号，注意修改

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000011", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modifyMobileNoManagerImpl() {
		try {
			// TODO
			ModifyMobileNoReq req = new ModifyMobileNoReq();
			req.setAccountNo("MA1423575567469620");
			req.setMobileNo("18500851753");
			req.setSecurityTicket("35e48ec7-6dea-40a5-b256-d8f32a453bbe");
			req.setSmsCode("214400");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000012", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createAccountSafetyManagerImpl() {
		try {
			// OK
			AccountSafetyReq req = new AccountSafetyReq();
			req.setAccountNo("MA1423575567469620");
			req.setSecurityTicket("96892906-7877-4532-ad43-e28b63faa64b");
			req.setSecurityQuestion1("1");
			req.setSecurityQuestion2("2");
			req.setSecurityQuestion3("3");
			req.setSecurityAnswer1("a1");
			req.setSecurityAnswer2("a2");
			req.setSecurityAnswer3("a3");
			req.setRemark("testRemark");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000013", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modifyAccountSafetyManagerImpl() {
		try {
			// OK
			AccountSafetyReq req = new AccountSafetyReq();
			req.setAccountNo("MA1423575567469620");
			req.setSecurityTicket("896588a1-eccb-43fa-ab75-f726bfd6a54e");
			req.setSecurityQuestion1("3");
			req.setSecurityQuestion2("2");
			req.setSecurityQuestion3("1");
			req.setSecurityAnswer1("a3");
			req.setSecurityAnswer2("a2");
			req.setSecurityAnswer3("a1");
			req.setRemark("testRemark");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000014", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkAccountSafetyManagerImpl() {
		try {
			// TODO 这里的ticket传入没有验证
			AccountSafetyReq req = new AccountSafetyReq();
			req.setAccountNo("MA1423575567469620");
			req.setSecurityTicket("aaa91362-36be-4f97-8876-7791e1a77adc");
			// req.setSecurityQuestion1("3");
			req.setSecurityQuestion2("2");
			// req.setSecurityQuestion3("1");
			// req.setSecurityAnswer1("a3");
			req.setSecurityAnswer2("a2");
			// req.setSecurityAnswer3("a1");
			// req.setRemark("testRemark");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000015", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void querySecurityQuestionByAccountNoManagerImpl() {
		try {
			// OK
			AccountSafetyReq req = new AccountSafetyReq();
			req.setAccountNo("MA1423575567469620");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000016", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void validateManagerImpl() {
		try {
			// OK
			ValidateAccountInfosReq req = new ValidateAccountInfosReq();
			req.setAccountNo("MA1423575567469620");
			req.setMobileNo("18500851753");
			req.setSmsCode("411321");
			req.setPayPassword(MD5Utils.getDefaultMd5String("116716"));
			// req.setSecurityQuestion("3");
			// req.setSecurityAnswer("a2");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000017", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modifyPayPasswordByTicketManagerImpl() {
		try {
			// OK
			ModifyPayPasswordByTicketReq req = new ModifyPayPasswordByTicketReq();
			req.setAccountNo("MA1423575567469620");
			req.setSecurityTicket("c3314aed-e41e-42e4-afc2-6804f2606f95");
			req.setNewMD5(MD5Utils.getDefaultMd5String("123456scf"));

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000018", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void querySecurityQuestionListManagerImpl() {
		try {
			// OK
			BaseBeanReq req = new BaseBeanReq();
			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000019", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询收款人列表
	@Test
	public void queryPayeeListManagerImpl() {
		try {
			// OK
			QueryPayeeListReq req = new QueryPayeeListReq();
			req.setUserId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setPage(1);
			req.setPageSize(2);

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000020", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询收款人列表
	@Test
	public void oilRechargeByAccountManagerImpl() {
		try {
			//
			OilRechargeByAccountReq req = new OilRechargeByAccountReq();
			req.setUserId("022d1da3-6bd6-4ce2-9921-74eaf53aec65");
			req.setAccountNo("MA1423575567469620");
			req.setCallbackURL("D");
			req.setFcallbackURL("D");
			req.setMd5("123456");
			req.setMerchantOrderAmount("0.01");
			req.setMerchantOrderNo("FASFD");
			req.setMobileNo("18500851753");
			req.setProductCatalog("1");
			req.setProductName("fdd");
			req.setRemarks("dd");
			req.setSmsCode("dd");

			System.out.println("http请求开始>>>>>>>>>>>>>>>");
			String url = BaseTest.url + BaseTest.getParam("Z000021", req);
			String result = BaseTest.getReturnFromUrl(url);
			System.out.println("http返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
