package com.sinoiov.upp.controller.pay;

import java.math.BigDecimal;
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

import com.ctfo.upp.dict.AccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.controller.BaseController;
import com.sinoiov.upp.service.IFastPay;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Scope("prototype")
@Controller
@RequestMapping(value = "/fastPayController")
public class FastPayController extends BaseController {

	@Autowired
	private IFastPay fastPayImpl;

	/**
	 * 储蓄卡支付请求（PC）
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户银行预留手机号
	 */
	@RequestMapping(value = "/rechargeByApiSaveRequestInPC", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByApiSaveRequestInPC(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		return this.rechargeByApiSaveRequest(response, params, PayDict.CLIENT_TYPE_PC);
	}

	/**
	 * 储蓄卡支付请求（Mobile）
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户银行预留手机号
	 */
	@RequestMapping(value = "/rechargeByApiSaveRequestInMobile", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByApiSaveRequestInMobile(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		return this.rechargeByApiSaveRequest(response, params, PayDict.CLIENT_TYPE_MOBILE);
	}

	private List<String> rechargeByApiSaveRequest(HttpServletResponse response, PayCommonRequestParams params,
			String clientType) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		AccountDto account = super.getAccountByAccountNo(map.getString("accountNo"));
		if(account.getAccountStatus().equals(AccountDict.ACCOUNT_STATUS_LOCKED))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED,"您的账户已锁定");
		String identityId = map.getString("userId");
		String phone = map.getString("phone");
		String cardNo = map.getString("cardNo");
		String idCard = map.getString("idCard");
		BigDecimal money = new BigDecimal(map.getString("money"));
		String productName = map.getString("productName");
		String productCatalog = map.getString("productCatalog");
		String type = map.getString("type");// 1：充值，2：支付
		String workOrderNo = map.getString("workOrderNo");
		String storeCode = map.getString("storeCode");
		String fCallbackUrl = map.containsKey("fCallbackUrl") ? map.getString("fCallbackUrl") : "";
		String callbackUrl = map.containsKey("callbackUrl") ? map.getString("callbackUrl") : "-1";
		String remark = (map.containsKey("remarks") && StringUtils.isNotBlank(map.getString("remarks"))) ? map
				.getString("remarks") : "";
		String accountNo = map.getString("accountNo");

		Map<String, String> returnMap = fastPayImpl.rechargeByApiSaveRequest(type, workOrderNo, storeCode,
				fCallbackUrl, callbackUrl, identityId, phone, cardNo, idCard, money, productName, productCatalog,
				accountNo, clientType, remark);
		returnMap.put("payConfirmDate", String.valueOf(System.currentTimeMillis()));

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 信用卡支付请求（PC）
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户银行预留手机号
	 */
	@RequestMapping(value = "/rechargeByApiCreditRequestInPC", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByApiCreditRequestInPC(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		return this.rechargeByApiCreditRequest(response, params, PayDict.CLIENT_TYPE_PC);
	}

	/**
	 * 信用卡支付请求（Mobile）
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户银行预留手机号
	 */
	@RequestMapping(value = "/rechargeByApiCreditRequestInMobile", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByApiCreditRequestInMobile(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		return this.rechargeByApiCreditRequest(response, params, PayDict.CLIENT_TYPE_MOBILE);
	}

	private List<String> rechargeByApiCreditRequest(HttpServletResponse response, PayCommonRequestParams params,
			String clientType) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String identityId = map.getString("userId");
		String phone = map.getString("phone");
		String cardNo = map.getString("cardNo");
		String validThru = map.getString("validThru");
		String cvv2 = map.getString("cvv2");
		BigDecimal money = new BigDecimal(map.getString("money"));
		String productName = map.getString("productName");
		String productCatalog = map.getString("productCatalog");
		String type = map.getString("type");// 1：充值，2：支付
		String workOrderNo = map.getString("workOrderNo");
		String storeCode = map.getString("storeCode");
		String fCallbackUrl = map.containsKey("fCallbackUrl") ? map.getString("fCallbackUrl") : "";
		String callbackUrl = map.containsKey("callbackUrl") ? map.getString("callbackUrl") : "-1";
		String remark = (map.containsKey("remarks") && StringUtils.isNotBlank(map.getString("remarks"))) ? map
				.getString("remarks") : "";
		String accountNo = map.getString("accountNo");

		Map<String, String> returnMap = fastPayImpl.rechargeByApiCreditRequest(type, workOrderNo, storeCode,
				fCallbackUrl, callbackUrl, identityId, phone, cardNo, validThru, cvv2, money, productName,
				productCatalog, accountNo, clientType, remark);
		returnMap.put("payConfirmDate", String.valueOf(System.currentTimeMillis()));

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 绑卡支付请求（PC）
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户银行预留手机号
	 */
	@RequestMapping(value = "/rechargeByApiRequestInPC", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByApiRequestInPC(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		return this.rechargeByApiRequest(response, params, PayDict.CLIENT_TYPE_PC);
	}

	/**
	 * 绑卡支付请求（Mobile）
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户银行预留手机号
	 */
	@RequestMapping(value = "/rechargeByApiRequestInMobile", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByApiRequestInMobile(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		return this.rechargeByApiRequest(response, params, PayDict.CLIENT_TYPE_MOBILE);
	}

	private List<String> rechargeByApiRequest(HttpServletResponse response, PayCommonRequestParams params,
			String clientType) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String identityId = map.getString("userId");
		String bingCardId = map.getString("bingCardId");
		BigDecimal money = new BigDecimal(map.getString("money"));
		String productName = map.getString("productName");
		String productCatalog = map.getString("productCatalog");
		String type = map.getString("type");// 1：充值，2：支付
		String workOrderNo = map.getString("workOrderNo");
		String storeCode = map.getString("storeCode");
		String fCallbackUrl = map.containsKey("fCallbackUrl") ? map.getString("fCallbackUrl") : "";
		String callbackUrl = map.containsKey("callbackUrl") ? map.getString("callbackUrl") : "-1";
		String remark = (map.containsKey("remarks") && StringUtils.isNotBlank(map.getString("remarks"))) ? map
				.getString("remarks") : "";
		String accountNo = map.getString("accountNo");

		Map<String, String> returnMap = fastPayImpl.rechargeByApiRequest(type, workOrderNo, storeCode, fCallbackUrl,
				callbackUrl, identityId, bingCardId, money, productName, productCatalog, accountNo, clientType, remark);
		returnMap.put("payConfirmDate", String.valueOf(System.currentTimeMillis()));

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 支付请求确认
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户银行预留手机号
	 */
	@RequestMapping(value = "/rechargeByApiSure", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByApiSure(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String workOrderNo = map.getString("workOrderNo");
		String messageCode = map.getString("messageCode");
		Map<String, String> returnMap = fastPayImpl.rechargeByApiSure(workOrderNo, messageCode);

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 网页支付请求（PC）
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/rechargeByPageRequestInPC", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByPageRequestInPC(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		return this.rechargeByPageRequest(response, params, PayDict.CLIENT_TYPE_PC);
	}

//	/**
//	 * 网页支付请求（PC）（油品平台油卡充值）
//	 * 
//	 * @param data
//	 * @param encryptkey
//	 * @param merchantcode
//	 * @param cashierCode
//	 * @param type
//	 * @return 跳转地址
//	 */
//	@RequestMapping(value = "/rechargeByPageRequestInPCForOil", produces = "text/plain;charset=utf-8")
//	public List<String> rechargeByPageRequestInPCForOil(HttpServletResponse response, PayCommonRequestParams params,
//			String cashierCode, String type) throws Exception {
//		/**
//		 * 此处先解密油品传过来的参数，之后与收银台的参数一起采用收银台的code进行加密
//		 */
//		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
//		JSONObject map = JSONObject.fromObject(paramJson);
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("userId", map.getString("identityId"));
//		paramMap.put("money", map.getString("merchantOrderAmount"));
//		paramMap.put("productName", (map.containsKey("productName") && StringUtils.isNotBlank(map
//				.getString("productName"))) ? map.getString("productName") : "油卡充值");
//		paramMap.put("productCatalog", (map.containsKey("productCatalog") && StringUtils.isNotBlank(map
//				.getString("productName"))) ? map.getString("productName") : PayDict.PAY_ORDER_PRODUCT＿CATALOG_YK);
//		paramMap.put("type", type);
//		paramMap.put("workOrderNo", map.getString("merchantOrderNo"));
//		paramMap.put("storeCode", params.getMerchantcode());
//		paramMap.put("fCallbackUrl", map.getString("fcallbackURL"));
//		paramMap.put("callbackUrl", map.getString("callbackURL"));
//		paramMap.put("accountNo", map.getString("accountNo"));
//		paramMap.put(
//				"remarks",
//				(map.containsKey("remarks") && StringUtils.isNotBlank(map.getString("remarks"))) ? map
//						.getString("remarks") : "油卡充值");
//
//		JSONObject js = JSONObject.fromObject(super.encodeAgain(JSONObject.fromObject(paramMap).toString(),
//				cashierCode));
//		PayCommonRequestParams bean = new PayCommonRequestParams();
//		bean.setData(js.getString("data"));
//		bean.setEncryptkey(js.getString("encryptkey"));
//		bean.setMerchantcode(cashierCode);
//		/**
//		 * 此处先解密油品传过来的参数，之后与收银台的参数一起采用收银台的code进行加密
//		 */
//
//		return this.rechargeByPageRequest(response, bean, PayDict.CLIENT_TYPE_PC);
//	}

	/**
	 * 网页支付请求（Mobile）
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/rechargeByPageRequestInMobile", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByPageRequestInMobile(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		return this.rechargeByPageRequest(response, params, PayDict.CLIENT_TYPE_MOBILE);
	}

	private List<String> rechargeByPageRequest(HttpServletResponse response, PayCommonRequestParams params,
			String clientType) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		AccountDto account = super.getAccountByAccountNo(map.getString("accountNo"));
		if(account.getAccountStatus().equals(AccountDict.ACCOUNT_STATUS_LOCKED))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED,"您的账户已锁定");
		String identityId = map.getString("userId");
		BigDecimal money = new BigDecimal(map.getString("money"));
		String productName = map.getString("productName");
		String productCatalog = map.getString("productCatalog");
		String type = map.getString("type");// 1：充值，2：支付
		String workOrderNo = map.getString("workOrderNo");
		String storeCode = map.getString("storeCode");
		String fCallbackUrl = map.containsKey("fCallbackUrl") ? map.getString("fCallbackUrl") : "";
		String callbackUrl = map.containsKey("callbackURL") ? 
				map.getString("callbackURL"): map.containsKey("callbackUrl")?
						map.getString("callbackUrl") : "-1";
		String remark = (map.containsKey("remarks") && StringUtils.isNotBlank(map.getString("remarks"))) ? map
				.getString("remarks") : "";
		String accountNo = map.getString("accountNo");
		String tradeExternalNo=map.getString("tradeExternalNo");
		String ownerUserNo = map.getString("ownerUserNo");
		Map<String, String> returnMap = fastPayImpl.rechargeByClient(type, workOrderNo, storeCode, fCallbackUrl,
				callbackUrl, identityId, money, productName, productCatalog, accountNo, clientType, remark, tradeExternalNo,ownerUserNo);
		returnMap.put("payConfirmDate", String.valueOf(System.currentTimeMillis()));

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}
}
