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

import com.ctfo.upp.dict.PayDict;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.controller.BaseController;
import com.sinoiov.upp.service.IWebPay;

@Scope("prototype")
@Controller
@RequestMapping(value = "/webPayController")
public class WebPayController extends BaseController {
	
	@Autowired
	private IWebPay webPayImpl;
	
	/**
	 * 网银支付（PC端）
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/rechargeByWebInPC", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByWebInPC(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		return this.rechargeByWeb(response, params, PayDict.CLIENT_TYPE_PC);
	}
	
	/**
	 * 网银支付（Mobile端）
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 跳转地址
	 */
	@RequestMapping(value = "/rechargeByWebInMobile", produces = "text/plain;charset=utf-8")
	public List<String> rechargeByWebInMobile(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		return this.rechargeByWeb(response, params, PayDict.CLIENT_TYPE_MOBILE);
	}
	
//	/**
//	 * 网银支付（PC端）（油品平台油卡充值）
//	 * @param data
//	 * @param encryptkey
//	 * @param merchantcode
//	 * @param cashierCode
//	 * @param type
//	 * @param bankCode
//	 * @return 跳转地址
//	 */
//	@RequestMapping(value = "/rechargeByWebInPCForOil", produces = "text/plain;charset=utf-8")
//	public List<String> rechargeByWebInPCForOil(HttpServletResponse response, PayCommonRequestParams params, String type, String cashierCode, String bankCode) throws Exception {
//		/**
//		 * 此处先解密油品传过来的参数，之后与收银台的参数一起采用收银台的code进行加密
//		 */
//		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
//		JSONObject map = JSONObject.fromObject(paramJson);
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("userId", map.getString("identityId"));
//		paramMap.put("money", map.getString("merchantOrderAmount"));
//		paramMap.put("productName", (map.containsKey("productName") && StringUtils.isNotBlank(map.getString("productName"))) ? map.getString("productName") : "油卡充值");
//		paramMap.put("productCatalog", (map.containsKey("productCatalog") && StringUtils.isNotBlank(map.getString("productName"))) ? map.getString("productName") : PayDict.PAY_ORDER_PRODUCT＿CATALOG_YK);
//		paramMap.put("type", type);
//		paramMap.put("workOrderNo", map.getString("merchantOrderNo"));
//		paramMap.put("storeCode", params.getMerchantcode());
//		paramMap.put("fCallbackUrl", map.getString("fcallbackURL"));
//		paramMap.put("callbackUrl", map.getString("callbackURL"));
//		paramMap.put("bankCode", bankCode);
//		paramMap.put("accountNo", map.getString("accountNo"));
//		paramMap.put("remarks", (map.containsKey("remarks") && StringUtils.isNotBlank(map.getString("remarks"))) ? map.getString("remarks") : "油卡充值");
//	
//		JSONObject js = JSONObject.fromObject(super.encodeAgain(JSONObject.fromObject(paramMap).toString(), cashierCode));
//		PayCommonRequestParams bean = new PayCommonRequestParams();
//		bean.setData(js.getString("data"));
//		bean.setEncryptkey(js.getString("encryptkey"));
//		bean.setMerchantcode(cashierCode);
//		/**
//		 * 此处先解密油品传过来的参数，之后与收银台的参数一起采用收银台的code进行加密
//		 */
//		
//		return this.rechargeByWeb(response, bean, PayDict.CLIENT_TYPE_PC);
//	}
	
	private List<String> rechargeByWeb(HttpServletResponse response, PayCommonRequestParams params, String clientType) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String identityId = map.getString("userId");
		BigDecimal money = new BigDecimal(map.getString("money"));
		String productName = map.getString("productName");
		String productCatalog = map.getString("productCatalog");
		String type = map.getString("type");//1：充值，2：支付
		String workOrderNo = map.getString("workOrderNo");
		String storeCode = map.getString("storeCode");
		String fCallbackUrl = map.containsKey("fCallbackUrl") ? map.getString("fCallbackUrl") : "";
		String callbackUrl = map.containsKey("callbackUrl") ? map.getString("callbackUrl") : "-1";
		String bankCode = map.getString("bankCode");
		String accountNo = map.getString("accountNo");
		String remark = (map.containsKey("remarks") && StringUtils.isNotBlank(map.getString("remarks"))) ? map.getString("remarks") : "";
		String tradeExternalNo=map.getString("tradeExternalNo");
		String ownerUserNo = map.getString("ownerUserNo");
		Map<String, String> returnMap = webPayImpl.recharge(type, workOrderNo, storeCode, fCallbackUrl, callbackUrl, identityId, bankCode, money, productName, productCatalog, accountNo, clientType, remark,tradeExternalNo,ownerUserNo);
		returnMap.put("payConfirmDate", String.valueOf(System.currentTimeMillis()));
		
		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}
}
