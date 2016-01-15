package com.sinoiov.upp.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountDetailExampleExtended;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.account.dao.beans.AccountDetailExampleExtended.Criteria;
import com.ctfo.upp.dict.AccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.root.utils.AmountUtil;
import com.sinoiov.pay.utils.UPPConfigUtil;
import com.sinoiov.pay.utils.UPPJsonUtil;
import com.sinoiov.root.util.TradeNumberHelper;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.service.IAccountService;
import com.sinoiov.upp.service.IFastPay;
import com.sinoiov.upp.service.IOrderService;
import com.sinoiov.upp.service.IPayService;
import com.sinoiov.upp.service.IPaymentTradeService;
import com.sinoiov.upp.service.dto.AccountDetailDto;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.service.dto.PayDto;
import com.sinoiov.upp.service.dto.TradeDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Scope("prototype")
@Controller
@RequestMapping(value = "/deductMoney")
public class DeductMoney extends BaseController {
	@Autowired
	private IPayService payService;
	@Autowired
	private IFastPay fastPayImpl;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IOrderService orderService;
	
	

	/**
	 * 收银台：网银扣款申请
	 * <p>
	 * 描述：当业务系统提交订单到收银台后，收银台选择某种支付方式后调用此接口。当用户在银行界面支付成功后，用户账户中增加相应的金额，接着冻结该笔金额。
	 */
	@RequestMapping(value = "/deductMoneyApply", produces = "text/plain;charset=utf-8")
	public List<String> payApplyForCashierNet(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		if (paramJson.indexOf("merchantOrderRemark") > -1) {
			paramJson = paramJson.replaceAll("merchantOrderRemark", "remarks");
		}

		PayDto payDto = new PayDto();
		payDto = (PayDto) UPPJsonUtil.jsonToObject(paramJson, payDto);
		payDto.setStoreCode(params.getMerchantcode());
		payDto.setClentType(PayDict.CLIENT_TYPE_PC);
		payDto.setPayChannel(PayDict.CHANNEL_NET);
		// payDto.setBankCardCode(bankCardCode);
		// payDto.setBankCardName(bankCardName);
		// payDto.setBankCardType(bankCardType);
		payDto.setRemarks(StringUtils.isBlank(payDto.getRemarks()) ? "油卡充值" : payDto.getRemarks());
		payDto.setProductName(StringUtils.isBlank(payDto.getProductName()) ? "油卡充值" : payDto.getProductName());
		payDto.setProductCatalog(StringUtils.isBlank(payDto.getProductCatalog()) ? PayDict.PAY_ORDER_PRODUCT＿CATALOG_YK
				: payDto.getProductCatalog());

		Map<String, Object> resultMap = payService.payApplyForCashierNet(payDto);

		if (resultMap.get("payConfirmDate") == null)
			resultMap.put("payConfirmDate", new Date().getTime() + "");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 收银台：快捷支付扣款申请
	 * <p>
	 * 描述：当业务系统提交订单到收银台后，收银台选择某种支付方式后调用此接口。 当用户使用快捷支付成功后，在用户账户中增加相应的金额，接着冻结该笔金额。
	 */
	@RequestMapping(value = "/deductMoneyApplyFast", produces = "text/plain;charset=utf-8")
	public List<String> payApplyForCashierFast(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		if (paramJson.indexOf("merchantOrderRemark") > -1) {
			paramJson = paramJson.replaceAll("merchantOrderRemark", "remarks");
		}

		//  这个方法没有setBankCardCode等
		PayDto payDto = new PayDto();
		payDto = (PayDto) UPPJsonUtil.jsonToObject(paramJson, payDto);
		payDto.setStoreCode(params.getMerchantcode());
		payDto.setClentType(PayDict.CLIENT_TYPE_PC);
		payDto.setPayChannel(PayDict.CHANNEL_FASTPAY);
		payDto.setRemarks(StringUtils.isBlank(payDto.getRemarks()) ? "油卡充值" : payDto.getRemarks());
		payDto.setProductName(StringUtils.isBlank(payDto.getProductName()) ? "油卡充值" : payDto.getProductName());
		payDto.setProductCatalog(StringUtils.isBlank(payDto.getProductCatalog()) ? PayDict.PAY_ORDER_PRODUCT＿CATALOG_YK
				: payDto.getProductCatalog());
		AccountDto account = super.getAccountByAccountNo(payDto.getAccountNo());
		if(account.getAccountStatus().equals(AccountDict.ACCOUNT_STATUS_LOCKED))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED,"您的账户已锁定");
		Map<String, Object> resultMap = payService.payApplyForCashierFast(payDto);
		if (resultMap.get("payConfirmDate") == null)
			resultMap.put("payConfirmDate", new Date().getTime() + "");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 收银台：账户支付扣款申请
	 * <p>
	 * 描述：当业务系统提交订单到收银台后，收银台选择账户支付方式后调用此接口，如果支付密码，短信验证码等正确时，冻结该账户相应的金额
	 */
	@RequestMapping(value = "/deductMoneyCashier", produces = "text/plain;charset=utf-8")
	public List<String> payApplyForCashierAccount(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		//  这里get要使用统一
		String MD5 = (jsonMap.get("MD5") == null) ? "" : jsonMap.getString("MD5");
		String smsCode = (jsonMap.get("smsCode") == null) ? "" : jsonMap.getString("smsCode");
		String mobileNo = (jsonMap == null) ? "" : jsonMap.getString("mobileNo");
	
		if (StringUtils.isBlank(MD5))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "支付密码不能为空");
		if (StringUtils.isBlank(smsCode))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "短信验证码不能为空");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");

		String merchantcode = (jsonMap.get("merchantcode") == null) ? "" : jsonMap.getString("merchantcode");
//		String yw_encryptkey = (jsonMap.get("yw_encryptkey") == null) ? "" : jsonMap.getString("yw_encryptkey");
//		String yw_data = (jsonMap.get("yw_data") == null) ? "" : jsonMap.getString("yw_data");
//		if (StringUtils.isBlank(yw_merchantcode))
//			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "业务商户编号不能为空");
//		if (StringUtils.isBlank(yw_encryptkey))
//			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "业务密钥密文不能为空");
//		if (StringUtils.isBlank(yw_data))
//			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "业务数据密文不能为空");
//
//		// 解密业务
//		String yw_paramJson = super.getParamJson(yw_data, yw_encryptkey, yw_merchantcode);
//		JSONObject yw_jsonMap = JSONObject.fromObject(yw_paramJson);

		String accountNo = jsonMap.get("accountNo") == null ? "" : jsonMap.getString("accountNo");
		String orderAmount = jsonMap.get("merchantOrderAmount") == null ? "" : jsonMap
				.getString("merchantOrderAmount");
		String workOrderNo = jsonMap.get("merchantOrderNo") == null ? "" : jsonMap.getString("merchantOrderNo");
		String userId = jsonMap.get("userId") == null ? "" : jsonMap.getString("userId");
		String callbackURL = jsonMap.get("callbackURL") == null ? "" : jsonMap.getString("callbackURL");
		String fcallbackURL = jsonMap.get("fcallbackURL") == null ? "" : jsonMap.getString("fcallbackURL");
		// 兼容remarks和merchantOrderAmount两种参数
		String remarks = jsonMap.get("merchantOrderRemark") == null ? jsonMap.get("remarks") == null ? "油卡充值"
				: jsonMap.getString("remarks") : jsonMap.getString("merchantOrderRemark");
		String productName = jsonMap.get("productName") == null ? "油卡充值" : jsonMap.getString("productName");
		String productCatalog = jsonMap.get("productCatalog") == null ? PayDict.PAY_ORDER_PRODUCT＿CATALOG_YK
				: jsonMap.getString("productCatalog");
		String ownerUserNo = jsonMap.get("ownerUserNo") == null ? "" : jsonMap.getString("ownerUserNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "账号不能为空");
		if (StringUtils.isBlank(orderAmount))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "订单金额不能为空");
		if (StringUtils.isBlank(workOrderNo))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "业务订单号不能为空");
		if(StringUtils.isBlank(ownerUserNo))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "用户会员编码不能为空");
		//查询账户信息,用来比较是否此账户足够支付金额
		AccountDto account = accountService.getAccountByAccountNo(accountNo);
		if(AmountUtil.getAmount(account.getUsableBalance())<AmountUtil.getAmount(orderAmount))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_USABLE_AMOUNT_INSUFFICIENT, "账户可用金额不足");
		PayDto payDto = new PayDto();
		payDto.setMerchantOrderAmount(orderAmount);
		// payDto.setOrderType(PayDict.ORDER_SUBJECT_FREEZE_MONEY);
		// payDto.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_PAY);
		payDto.setStoreCode(merchantcode);
		payDto.setMerchantOrderNo(workOrderNo);
		payDto.setAccountNo(accountNo);
		payDto.setUserId(userId);
		payDto.setRemarks(remarks);
		payDto.setProductName(productName);
		payDto.setProductCatalog(productCatalog);
		payDto.setCallbackURL(callbackURL);
		payDto.setFcallbackURL(fcallbackURL);

		payDto.setClentType(PayDict.CLIENT_TYPE_PC);
		payDto.setPayChannel(PayDict.CHANNEL_ACCOUNT);
		payDto.setTradeExternalNo(jsonMap.getString("tradeExternalNo"));
		payDto.setOwnerUserNo(ownerUserNo);
		// payDto.setOrderSubject(PayDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_PAYOUT);

		Map<String, Object> resultMap = payService.payApplyForCashierAccount(payDto, mobileNo, smsCode, MD5);
		resultMap.put("fcallbackURL", fcallbackURL);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 移动端：账户支付扣款申请
	 */
	@RequestMapping(value = "/mobileDeductMoneyApply", produces = "text/plain;charset=utf-8")
	public List<String> payApplyForMobileAccount(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		if (paramJson.indexOf("merchantOrderRemark") > -1) {
			paramJson = paramJson.replaceAll("merchantOrderRemark", "remarks");
		}
		
		JSONObject jsonMap = JSONObject.fromObject(paramJson);
		String MD5 = (jsonMap.get("MD5") == null) ? "" : (String)jsonMap.remove("MD5");
		String smsCode = (jsonMap.get("smsCode") == null) ? "" : (String)jsonMap.remove("smsCode");
		String mobileNo = (jsonMap == null) ? "" : (String)jsonMap.remove("mobileNo");

		if (StringUtils.isBlank(MD5))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "支付密码不能为空");
		if (StringUtils.isBlank(smsCode))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "短信验证码不能为空");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");


		PayDto payDto = new PayDto();
		payDto = (PayDto) UPPJsonUtil.jsonToObject(jsonMap.toString(), payDto);

		if (StringUtils.isBlank(payDto.getAccountNo()))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "账号不能为空");
		if (StringUtils.isBlank(payDto.getMerchantOrderAmount()))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "订单金额不能为空");
		if (StringUtils.isBlank(payDto.getMerchantOrderNo()))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "业务订单号不能为空");
		//查询账户信息,用来比较是否此账户足够支付金额
		AccountDto account = accountService.getAccountByAccountNo(payDto.getAccountNo());
		if(AmountUtil.getAmount(account.getUsableBalance())<AmountUtil.getAmount(payDto.getMerchantOrderAmount()))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_USABLE_AMOUNT_INSUFFICIENT, "账户可用金额不足");
		if(account.getAccountStatus().equals(AccountDict.ACCOUNT_STATUS_LOCKED))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED,"您的账户已锁定");
		payDto.setStoreCode(UPPConfigUtil.getValue("YP_MERCHANT_CODE"));
		payDto.setRemarks(StringUtils.isBlank(payDto.getRemarks()) ? "油卡充值" : payDto.getRemarks());
		payDto.setProductName(StringUtils.isBlank(payDto.getProductName()) ? "油卡充值" : payDto.getProductName());
		payDto.setProductCatalog(StringUtils.isBlank(payDto.getProductCatalog()) ? PayDict.PAY_ORDER_PRODUCT＿CATALOG_YK
				: payDto.getProductCatalog());
		payDto.setPayChannel(PayDict.CHANNEL_ACCOUNT);
		payDto.setClentType(PayDict.CLIENT_TYPE_MOBILE);
		if(StringUtils.isBlank(payDto.getTradeExternalNo())){
			payDto.setTradeExternalNo(TradeNumberHelper.getTradeExternalNo());
		}
		Map<String, Object> resultMap = payService.payApplyForMobileAccount(payDto, mobileNo, smsCode, MD5);

		if (resultMap.get("payConfirmDate") == null)
			resultMap.put("payConfirmDate", new Date().getTime() + "");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 
	 * 此接口目前是为了兼容旧版本的手机端，之后会废除
	 * 
	 * 移动端：快捷支付扣款申请
	 * <p>
	 * 描述：当业务系统提交订单到收银台后，收银台选择某种支付方式后调用此接口。 当用户使用快捷支付成功后，在用户账户中增加相应的金额，接着冻结该笔金额。
	 */
	@RequestMapping(value = "/deductMoneyApplyFastFroMoblie", produces = "text/plain;charset=utf-8")
	public List<String> payApplyForMobileFast(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		if (paramJson.indexOf("merchantOrderRemark") > -1) {
			paramJson = paramJson.replaceAll("merchantOrderRemark", "remarks");
		}
		JSONObject map = JSONObject.fromObject(paramJson);
		String identityId = map.getString("userId");
		BigDecimal money = new BigDecimal(map.getString("merchantOrderAmount"));
		String productName = StringUtils.isNotBlank(map.getString("productName")) ? map.getString("productName")
				: "油卡充值";
		String productCatalog = (map.containsKey("productCatalog") && StringUtils.isNotBlank(map
				.getString("productCatalog"))) ? map.getString("productCatalog") : PayDict.PAY_ORDER_PRODUCT＿CATALOG_YK;
		String type = "2";
		String workOrderNo = map.getString("merchantOrderNo");
		String storeCode = UPPConfigUtil.getValue("YP_MERCHANT_CODE");
		String fCallbackUrl = UPPConfigUtil.getValue("UPP_MOBILE_CALLBACK");
		String callbackUrl = map.containsKey("callbackURL") ? 
				map.getString("callbackURL"): map.containsKey("callbackUrl")?
						map.getString("callbackUrl") : "-1";
		String remark = (map.containsKey("remarks") && StringUtils.isNotBlank(map.getString("remarks"))) ? map
				.getString("remarks") : "油卡充值";
				
				
		String accountNo = map.getString("accountNo");
		AccountDto account = accountService.getAccountByAccountNo(accountNo);
		if(account.getAccountStatus().equals(AccountDict.ACCOUNT_STATUS_LOCKED))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED,"您的账户已锁定");
		String	tradeExternalNo	=null;	
		String ownerUserNo = "";
		if(map.get("tradeExternalNo")==null){
			tradeExternalNo=TradeNumberHelper.getTradeExternalNo();
		}else{
			tradeExternalNo=map.getString("tradeExternalNo");
		}
		if(map.get("ownerUserNo")!=null){
			ownerUserNo = map.getString("ownerUserNo");
		}
		Map<String, String> returnMap = fastPayImpl.rechargeByClient(type, workOrderNo, storeCode, fCallbackUrl,
				callbackUrl, identityId, money, productName, productCatalog, accountNo, PayDict.CLIENT_TYPE_MOBILE, remark,tradeExternalNo,ownerUserNo);
		returnMap.put("payConfirmDate", String.valueOf(new Date().getTime()));

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 扣款确认
	 * <p>
	 * 描述：和扣款申请组合，当业务系统处理完业务逻辑后，调用此接口扣除用户账户此前冻结的金额。这里不区分是什么支付渠道的申请。
	 */
	@RequestMapping(value = "/deductMoneySure", produces = "text/plain;charset=utf-8")
	public List<String> paySure(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		if (jsonMap.get("accountNo") == null)
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "账号为空");
		if (jsonMap.get("orderNo") == null)
			throw new UPPServiceException(ReturnCodeDict.ORDER_NO_N, "支付订单号为空");
		if (jsonMap.get("merchantOrderNo") == null)
			throw new UPPServiceException(ReturnCodeDict.ORDER_WORK_NO_N, "业务订单号错误");
		if (jsonMap.get("orderAmount") == null)
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "订单金额不能为空");
		if (jsonMap.get("result") == null)
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "通知结果不能为空");
		//查询交易流水，如果存在则不通过，避免业务系统重复提交。
		//TODO 
		DynamicSqlParameter requestParameter = new DynamicSqlParameter();
		Map<String,String> map = new HashMap<String, String>();
		map.put("tradeExternalNo", jsonMap.getString("tradeExternalNo"));
		map.put("workOrderNo", jsonMap.getString("merchantOrderNo"));
		map.put("orderStatus", "2");
		requestParameter.setEqual(map);
		int count = orderService.getOrderCount(requestParameter);
//		List<AccountDetailDto> list = accountService.queryAccountDetailList(requestParameter);
		if(count>0)
			throw new UPPServiceException(ReturnCodeDict.ORDER_IS_PAYED, "此订单已处理");
		
		PayDto payDto = new PayDto();
		payDto.setAccountNo(jsonMap.getString("accountNo"));
		payDto.setOrderNo(jsonMap.getString("orderNo"));
		payDto.setMerchantOrderNo(jsonMap.getString("merchantOrderNo"));
		payDto.setMerchantOrderAmount(jsonMap.getString("orderAmount"));
		payDto.setStoreCode(params.getMerchantcode());
		payDto.setProductCatalog(jsonMap.getString("productCatalog"));
		payDto.setProductName(jsonMap.getString("productName"));
		payDto.setRemarks(jsonMap.getString("remarks"));
		payDto.setResult(jsonMap.getString("result"));
		payDto.setTradeExternalNo(jsonMap.getString("tradeExternalNo"));
		// 支付确认
		Map<String, Object> resultMap = payService.paySure(payDto);

		if (resultMap.get("payConfirmDate") == null)
			resultMap.put("payConfirmDate", new Date().getTime() + "");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 扣款审请并确认接口
	 * <p>
	 * 描述：将用户账户的金额转到中交收益账户中，如果此前有冻结金额，且商户号+业务订单号一致，则会从冻结金额中转出。
	 */
	@RequestMapping(value = "/deductMoney", produces = "text/plain;charset=utf-8")
	public List<String> payApplyAndSure(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		PayDto payDto = new PayDto();
		payDto = (PayDto) UPPJsonUtil.jsonToObject(paramJson, payDto);

		// 支付审请并默认转账
		Map<String, Object> resultMap = payService.payApplyAndSure(payDto);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

}
