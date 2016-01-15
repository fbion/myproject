package com.zyzfpt.portal.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AuthenApplyInfo;
import com.ctfo.account.dto.beans.AccountDTO;
import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.base.dao.beans.SimpleCodeExampleExtended;
import com.ctfo.base.intf.internal.ISimpleCodeManager;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.intf.external.IPaymentTradeManager;
import com.ctfo.payment.intf.external.IRechargeManager;
import com.ctfo.payment.intf.external.IWithdrawCashManager;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.root.utils.MD5Util;
import com.ctfo.upp.soa.IServiceFactory;
import com.ctfo.upp.soa.ServiceFactory;

/**
 * 账户测试类
 * 
 * @author jichao
 */
@ContextConfiguration("classpath*:spring-all.xml")
public class AccountTest extends BaseTest {

	/*private static Log logger = LogFactory.getLog(AccountTest.class);

	*//**
	 * 账号管理
	 *//*
	private AccountManager accountManager;
	*//**
	 * 实名认证
	 *//*
	private RealNameAuthen realNameAuthen;
	
	*//**
	 * 充值
	 *//*
	private IRechargeManager rechargeManager;

	*//**
	 * 交易
	 *//*
	private IPaymentTradeManager paymentTradeManager;
	
	*//**
	 * 码表
	 *//*
	private ISimpleCodeManager simpleCodeManager;
	
	*//**
	 * 提现
	 *//*
	private IWithdrawCashManager withdrawCashManager;

	public AccountTest() {
		IServiceFactory factory = ServiceFactory.getFactory();
		accountManager = (AccountManager) factory.getService(AccountManager.class);
		realNameAuthen = (RealNameAuthen) factory.getService(RealNameAuthen.class);
		rechargeManager = (IRechargeManager)factory.getService(IRechargeManager.class);
		paymentTradeManager = (IPaymentTradeManager) factory.getService(IPaymentTradeManager.class);
		simpleCodeManager = (ISimpleCodeManager) factory.getService(ISimpleCodeManager.class);
		withdrawCashManager = (IWithdrawCashManager)factory.getService(IWithdrawCashManager.class);
	}

	*//**
	 * 查询账户信息接口测试 
	 *//*
	@Test
	public void testQqueryAccountInfo() {
		System.out.println(accountManager);
		// logger.info("调用服务地址：" + accountManager);
		// 内部账号
		String accountNo = "MA1418622298531566";
		try {
			Account account = accountManager.queryAccountInfo(accountNo);
			logger.info("ID:" + account.getId());
			// ID:4858e19f-8e30-4625-bd59-b298a5657d82
		} catch (UPPException e) {
			logger.info(e.getMessage(), e);
		}
	}

	*//**
	 * 业务平台开户接口测试 
	 *//*
	@Test
	public void testCreateAccount() {
		logger.info("调用服务地址：" + accountManager);
		// HessianProxy[http://127.0.0.1:8888/accountService/hessian-remote/com.ctfo.account.intf.external.AccountManager]
		AccountDTO accountDTO = new AccountDTO();
		Account _account = new Account();
		_account.setId(UUID.randomUUID().toString());
		_account.setOwnerUserId("0001"); // 必填
		_account.setOwnerLoginName("zhangsan");
		_account.setAccountType(PayDict.ACCOUNT_TYPE_CAR_OWNER);// 车主、中交、货主
		_account.setAccountStatus("1");// 0：表示不可以，1：表示可用
		_account.setTotalBalance(0l);// 总余额

		_account.setUnableTakecashBalance(0l);// 可提现金额=可用金额-不可提现金额
		_account.setFrozenBalance(0l);// 用户被冻结的金额，改金额不可提现，不可做交易
		_account.setUsableBalance(0l);// 可用金额=总余额-冻结金额

		_account.setCreateTime(System.currentTimeMillis());
		_account.setPayPassword("123456");
		_account.setMobileNo("13720055520");
		_account.setIsOperMess("1");// 短信是否可用
		accountDTO.setAccount(_account);
		accountDTO.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_PAY);// 支付平台的编号:易宝支付
		try {
			Account account = accountManager.createAccount(accountDTO);
			logger.info("ADD Account RETURN ID:" + account.getId());
		} catch (UPPException e) {
			logger.info(e.getMessage(), e);
		}
	}

	*//**
	 * 修改交易密码接口测试 
	 *//*
	@Test
	public void testModifyPayPassword() {
		String insideAccountNo = "MA1417516959688410";// 内部账号
		try {
			Account account = accountManager.queryAccountInfo(insideAccountNo);
			logger.info("原始密码为：" + account.getPayPassword());

			accountManager.modifyPayPassword(insideAccountNo, "abcdef");

			String modifyPWD = MD5Util.getEncryptedPwd("abcdef");

			Account account2 = accountManager.queryAccountInfo(insideAccountNo);
			logger.info("修改后密码为：" + account2.getPayPassword() + "  modifyPWD:" + modifyPWD);

		} catch (UPPException e) {
			logger.info(e.getMessage(), e);
		}
	}

	*//**
	 * 账户锁定（3次输入错误密码后锁定） 
	 *//*
	@Test
	public void testLockAccount() {

		try {
			String insideAccountNo = "MA1418622298531566";// 内部账号
			String lockReasons = "输入密码3次错误锁定账号";
			accountManager.lockAccount(insideAccountNo, lockReasons);

		} catch (UPPException e) {
			logger.info(e.getMessage(), e);
		}

	}

	*//**
	 * 显示订单列表查询接口测试 
	 *//*
	@Test
	public void testQueryPaymentTrade() {
		List<PaymentTrade> list = null;
		PaymentTradeExampleExtended exampleExtended = new PaymentTradeExampleExtended();
		exampleExtended.createCriteria();
		try {
			list = paymentTradeManager.queryPaymentTrade(exampleExtended);
			for (PaymentTrade paymentTrade : list) {
				String id = paymentTrade.getId();
				logger.info("ID:" + id + "\n");
			}
			logger.info("一共：" + list.size() + "条记录。");

		} catch (UPPException e) {
			logger.info(e.getMessage(), e);
		}

	}
	
	
	*//**
	 * 获取银行代码 
	 *//*
	@Test
	public void testGetBankCode() throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, UPPException{
		DynamicSqlParameter requestParam = new DynamicSqlParameter();
		Map<String,String> equal = new HashMap<String, String>();
		equal.put("typeCode", "BANK_TYPE");
		requestParam.setEqual(equal);
		List<SimpleCode> list = new ArrayList<SimpleCode>();
		
		SimpleCodeExampleExtended example = new SimpleCodeExampleExtended();
		Converter.paramToExampleExtended(requestParam, example);
		list = simpleCodeManager.getSimpleCodeByExampleExtended(example);
		logger.info(list.get(0).getName());
	}

	
	*//**
	 * 充值接口测试 
	 *//*
	@Test
	public void testRecharge() {
 
		
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setUserId("b72f96ae-f6a6-471d-a89e-0643286f9f1f");//付款用户ID
		orderInfo.setAccountNo("MA1419400019885725");//内部账号--对应着insideAccountNo(付款内部账户号，如果是充值交易付款和收款账户是同一个人)
		orderInfo.setOrderAmount(Long.parseLong("100"));// 订单金额,保留2位小数
		orderInfo.setClentType(PayDict.CLIENT_TYPE_PC);//终端类型，固定值(PC, MOBILE)
		orderInfo.setPayChannel(PayDict.CHANNEL_NET);// wap支付、网页支付
		orderInfo.setStoreCode("22001111203");//来自哪个商户(商户编号)
		orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);//设置订单类型为在线充值
		orderInfo.setBankCardCode("ICBC-NET-B2C");
		orderInfo.setBankCardName("中国工商银行");
		orderInfo.setBankCardType("DEBIT");//银行卡类型:借记卡、信用卡  (必须为:DEBIT)

		try {
			Map result = rechargeManager.recharge(orderInfo);
			logger.info(result);
		} catch (UPPException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	*//**
	 * 实名认证接口测试 
	 * @exception 提交实名认证申请信息时产生错误, 原因:
	 * @exception 网关返回实名认证结果失败.
	 *//*
	@Test
	public void testAuth() {

		AuthenApplyInfo authenApplyInfo = new AuthenApplyInfo();

		// 内部账号
		authenApplyInfo.setInsideAccountNo("MA1418622298531566"); 
		// 对应账户的ID
		authenApplyInfo.setAccontId("4858e19f-8e30-4625-bd59-b298a5657d82");
		// 申请类型：1.开户申请，2.实名认证申请
		authenApplyInfo.setApplyType(RealNameAuthen.RealNameAuthenApplyType.realNameAuth.getState() + "");
		// 该申请的状态
		authenApplyInfo.setApplyStatus(RealNameAuthen.RealNameAuthenApplyStatus._final.getState() + "");
		// 实名认证请求时必填
		authenApplyInfo.setUserName("冀超"); 
		// 用户身份证件类型
		authenApplyInfo.setIdcardType("1");
		
		//设置主副卡 0不是主卡，1是主卡
		authenApplyInfo.setIsMainCard("1");
		//0 是普通账号，1是对公账号
		authenApplyInfo.setAccountNoType("0");
		
		// 证件号码
		authenApplyInfo.setIdcardNo("130603198108150914");
		// 银行卡号，实名认证请求时必填，用户作为提现卡号
		authenApplyInfo.setBankcardNo("4367420014240338865");
		//银行卡类型：0:普通账号  1：对公账号
		authenApplyInfo.setBankCardType("0"); 
		// 银行代码
		authenApplyInfo.setBankCode("CCB");
		// 开户行名称
		authenApplyInfo.setBranchBankName("建设银行");
		// 请求时间
		authenApplyInfo.setApplyTime(System.currentTimeMillis());
		// 标识第三方支付平台，如：易宝、支付宝或中交支付
		authenApplyInfo.setProviderType(PayDict.PLATFORM_CODE_YEE_PAY);
		
		
		// 加密后的用户交易密码
		// authenApplyInfo.setPayPassword(payPassword);
		// 开户行省份编码
		//authenApplyInfo.setBranchBankProvince("");
		// 开户行城市编码
		//authenApplyInfo.setBranchBankCity("");
		// 身份证有效期，非必填
		// authenApplyInfo.setIdcardEnddate(idcardEnddate);
		// 调用支付平台后，支付平台回传确认的时间
		// authenApplyInfo.setPayConfirmDate(payConfirmDate);
		// 第三方支付给定的交易流水号
		// authenApplyInfo.setExternalNo(externalNo);
		// 存放支付机构返回的响应状态
		// authenApplyInfo.setPayStatus(payStatus);
		//authenApplyInfo.setRemark("yeepayAuth");

		try {
			AuthenApplyInfo result = realNameAuthen.submitAuthenApplyInfo(authenApplyInfo);
			logger.info("调用结果:" + result.getAccontId()); 
		} catch (UPPException e) {
			logger.info(e.getMessage(), e);
		}

		
		*//**
		 * 提交参数：
			insideAccountNo内部账号
			accontId 对应账户的ID
			applyType申请类型：1.开户申请，2.实名认证申请
			applyStatus该申请的状态：1。最终状态(发起认证请求，通过后保存申请信息) 2.草稿状态(不发起认证，只保存申请信息)
			userName实名认证请求时必填
			idcardNo实名认证时必填
			bankCode银行代码	
			bankcardNo银行卡号 
			branchBankName开户行名称(银行名称)
			applyTime请求时间，必填
		            未用参数：
			------------------------
			mobileNo绑定手机号
			idcardType 用户身份证件类型
			payPassword加密后的用户交易密码
			branchBankProvince开户行省份编码
			branchBankCity开户行城市编码
			idcardEnddate身份证有效期，非必填
			payConfirmDate调用支付平台后，支付平台回传确认的时间
			providerType 标识第三方支付平台，如：易宝、支付宝或中交支付
			externalNo第三方支付给定的交易流水号
			payStatus存放支付机构返回的响应状态
		 *//*
		
	}

	

	*//**
	 * 转账操作接口测试 
	 * 收银台与提现共用一个接口
	 * 收银台-->UserId
	 * 提现-->UserId & CollectMoneyUserId
	 *//*
	@Test
	public void testTransferAccounts() {

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setStoreCode("MA1418622298531566");//商家编号：谁调用谁带此参数，表示哪个系统的编号
		orderInfo.setAccountNo("MA1417079837612897");
		orderInfo.setCollectMoneyAccountNo("MA1418622298531566");//收款内部账户号，如果是充值交易付款和收款账户是同一个人
		orderInfo.setOrderAmount(1000L);
		orderInfo.setWorkOrderNo("10010");
		orderInfo.setOrderType(PayDict.ORDER_SUBJECT_TRANSFER_ACCOUNT);
		orderInfo.setUserId("1111111111111");//付款人账号ID--(收银台)
		orderInfo.setCollectMoneyUserId("222222");//收款人账号ID---(提现)
		orderInfo.setServiceProviderCode("1002");
		
		try {
			AbstractTransferAccountsResult result = paymentTradeManager.transferAccounts(orderInfo);
			logger.info(result.getAccountNo());
		} catch (UPPException e) {
			logger.error("账户转账异常！", e);
		}
	}
	
	*//**
	 * 提现测试
	 *//*
	@Test
	public void testIWithdrawCashManager(){
		OrderInfo orderInfo=new OrderInfo();
		//注意：账号ID需要在TB_UPP_BANK_CARD_INFO表中有绑定数据才可以
		orderInfo.setUserId("33c5e678-de50-40c1-9ff7-9a8599072e12");
		orderInfo.setAccountNo("MA1418893800052766");
		orderInfo.setOrderAmount(5l);
		orderInfo.setStoreCode("111");
		orderInfo.setWorkOrderNo("111");
		orderInfo.setOrderType(PayDict.ORDER_SUBJECT_WITHDRAW_CASH);
		orderInfo.setRemarks("");
		
		try {
			String result = withdrawCashManager.withdrawCash(orderInfo);
			logger.info(result);
		} catch (UPPException e) {
			logger.error("提现异常！", e);
		}
	}
	
	*/
}
