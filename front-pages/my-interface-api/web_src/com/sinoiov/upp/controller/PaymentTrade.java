package com.sinoiov.upp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.pay.utils.UPPJsonUtil;
import com.sinoiov.root.util.TradeNumberHelper;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.service.IFastPay;
import com.sinoiov.upp.service.IOrderService;
import com.sinoiov.upp.service.IPaymentTradeService;
import com.sinoiov.upp.service.IRechargeService;
import com.sinoiov.upp.service.dto.OrderDto;
import com.sinoiov.upp.service.dto.OrderFreezeDto;
import com.sinoiov.upp.service.dto.TradeDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

/**
 * 线上交易接口
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/paymentTrade")
public class PaymentTrade extends BaseController {
	@Autowired
	private IPaymentTradeService paymentTradeService;
	@Autowired
	private IRechargeService rechargeService;
	@Autowired
	private IFastPay fastPay;
	@Autowired
	private IOrderService orderService;

	/**
	 * 此接口目前是为了兼容旧版本的手机端，之后会废除
	 * 
	 * 线上账户充值
	 * <p>
	 * 描述：用于给用户账户充值。 方式有两种:NET(网银)、PASTPAY(快捷);渠道有:PC、MOBILE
	 * 用户在门户提交充值订单，验证成功后，此方法返回跳转第三方支付银行的URL；用户在网银页面支付成功，第三方回调支付，支付为相应的账户增加金额
	 */
	@RequestMapping(value = "/recharge", produces = "text/plain;charset=utf-8")
	public List<String> recharge(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		// TODO 这里要修改get方式
		String identityId = jsonMap.getString("userId");
		BigDecimal money = new BigDecimal(jsonMap.getString("amount"));
		String productName = jsonMap.getString("productName");
		String productCatalog = (jsonMap.containsKey("productCatalog") && StringUtils.isNotBlank(jsonMap
				.getString("productCatalog"))) ? jsonMap.getString("productCatalog")
				: PayDict.PAY_ORDER_PRODUCT＿CATALOG_ACC;
		String type = "1";
		String workOrderNo = jsonMap.getString("workOrderNo");
		String storeCode = jsonMap.getString("storeCode");
		String fCallbackUrl = jsonMap.getString("fcallbackUrl");
		String callbackUrl = jsonMap.containsKey("callbackURL") ? 
				jsonMap.getString("callbackURL"): jsonMap.containsKey("callbackUrl")?
						jsonMap.getString("callbackUrl") : "-1";
		String remark = (jsonMap.containsKey("remarks") && StringUtils.isNotBlank(jsonMap.getString("remarks"))) ? jsonMap
				.getString("remarks") : "账户充值";
		String accountNo = jsonMap.getString("accountNo");
		String clentType=jsonMap.getString("clentType");
		String tradeExternalNo=null;
		String ownerUserNo = "";
		if(jsonMap.get("tradeExternalNo")==null){
			tradeExternalNo=TradeNumberHelper.getTradeExternalNo();
		}else{
			tradeExternalNo=jsonMap.getString("tradeExternalNo");
		}
		if(jsonMap.get("ownerUserNo")!=null){
			ownerUserNo = jsonMap.getString("ownerUserNo");
		}
		Map<String, String> returnMap = fastPay.rechargeByClient(type, workOrderNo, storeCode, fCallbackUrl,
				callbackUrl, identityId, money, productName, productCatalog, accountNo, clentType, remark,tradeExternalNo,ownerUserNo);
		returnMap.put("payConfirmDate", String.valueOf(new Date().getTime()));

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 服务端为用户充值
	 * <p>
	 * 描述：特定接口，从中交账户向用户的账户转一笔金额，用于如油卡返利等业务需求(现在这个接口已被批量充值接口替换,为了兼容性保留)
	 */
	@RequestMapping(value = "/rechargeToUserAccount", produces = "text/plain;charset=utf-8")
	public List<String> rechargeToUserAccount(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		OrderDto orderDto = new OrderDto();
		orderDto = (OrderDto) UPPJsonUtil.jsonToObject(paramJson, orderDto);
		orderDto.setStoreCode(params.getMerchantcode());
		orderDto.setRemarks(StringUtils.isBlank(orderDto.getRemarks()) ? "油卡返利" : orderDto.getRemarks());
		orderDto.setProductName(StringUtils.isBlank(orderDto.getProductName()) ? "油卡返利" : orderDto.getProductName());
		orderDto.setProductCatalog(StringUtils.isBlank(orderDto.getProductCatalog()) ? PayDict.PAY_ORDER_PRODUCT＿CATALOG_FL
				: orderDto.getProductCatalog());

		rechargeService.rechargeToUserAccount(orderDto);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("orderNo", orderDto.getOrderNo());
		returnMap.put("payConfirmDate",
				(orderDto.getPayConfirmDate() == null) ? new Date().getTime() + "" : orderDto.getPayConfirmDate());

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 批量为用户充值(非实时接口)
	 * <p>
	 * 描述：特定接口，从中交账户向用户的账户转一笔金额，用于如油卡返利等等业务需求
	 */
	@RequestMapping(value = "/batchRechargeToUserAccount", produces = "text/plain;charset=utf-8")
	public List<String> batchRechargeToUserAccount(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		List<OrderDto> orderDtos = new ArrayList<OrderDto>();

		JSONArray jsonArray = (JSONArray) UPPJsonUtil.jsonToObject(paramJson, orderDtos);

		OrderDto orderDto = null;
		for (Object str : jsonArray) {
			orderDto = new OrderDto();
			UPPJsonUtil.jsonToObject(str.toString(), orderDto);
			if (StringUtils.isBlank(orderDto.getCollectMoneyAccountNo())) {
				throw new UPPServiceException("内部账户号不能为空");
			}
			if (StringUtils.isBlank(orderDto.getOrderAmount())) {
				throw new UPPServiceException("内部账户号 [" + orderDto.getCollectMoneyAccountNo() + "] 对应的金额不能为空");
			}
			if (StringUtils.isBlank(orderDto.getWorkOrderNo())) {
				throw new UPPServiceException("内部账户号 [" + orderDto.getCollectMoneyAccountNo() + "] 对应的业务订单号不能为空");
			}
			orderDto.setStoreCode(params.getMerchantcode());
			orderDtos.add(orderDto);
		}

		// 返回的是每笔的属性放到一个Map中了
		List<Map<String, Object>> lstReturn = rechargeService.batchRechargeToUserAccount(orderDtos);

		Map<String, List<Map<String, Object>>> mpReturn = new HashMap<String, List<Map<String, Object>>>();
		mpReturn.put("data", lstReturn);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, mpReturn);
	}

	/**
	 * 转账
	 * <p>
	 * 描述：从一个账户转一笔金额到另一个账户。如果之前被转账户有冻结金额，且商户号+业务订单后一致，则从冻结金额中转出
	 */
	@RequestMapping(value = "/transferAccounts", produces = "text/plain;charset=utf-8")
	public List<String> transferAccounts(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		OrderDto orderDto = new OrderDto();
		orderDto = (OrderDto) UPPJsonUtil.jsonToObject(paramJson, orderDto);
		orderDto.setRemarks(StringUtils.isBlank(orderDto.getRemarks()) ? "账户转账" : orderDto.getRemarks());
		orderDto.setProductName(StringUtils.isBlank(orderDto.getProductName()) ? "账户转账" : orderDto.getProductName());
		orderDto.setProductCatalog(StringUtils.isBlank(orderDto.getProductCatalog()) ? PayDict.PAY_ORDER_PRODUCT＿CATALOG_TRANS
				: orderDto.getProductCatalog());

		paymentTradeService.transferAccounts(orderDto);

		Map<String, Object> mpReturn = new HashMap<String, Object>();
		mpReturn.put("orderNo", orderDto.getOrderNo());
		mpReturn.put("payConfirmDate",
				(orderDto.getPayConfirmDate() == null) ? new Date().getTime() + "" : orderDto.getPayConfirmDate());

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, mpReturn);
	}

	/**
	 * 冻结账户金额
	 * <p>
	 * 描述：从某一账户冻结一笔金额
	 */
	@RequestMapping(value = "/freezeBalance", produces = "text/plain;charset=utf-8")
	public List<String> freezeBalance(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		OrderFreezeDto freeDto = new OrderFreezeDto();
		freeDto.setStoreCode(params.getMerchantcode());
		freeDto.setOrderType(PayDict.ORDER_SUBJECT_FREEZE_MONEY);
		freeDto.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_PAY);

		freeDto = (OrderFreezeDto) UPPJsonUtil.jsonToObject(paramJson, freeDto);

		Map<String, Object> mpReturn = paymentTradeService.freezeAccountMoney(freeDto);
		if (mpReturn.get("payConfirmDate") == null)
			mpReturn.put("payConfirmDate", new Date().getTime() + "");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, mpReturn);
	}

	/**
	 * 解冻账户金额
	 * <p>
	 * 描述：解除账户被冻结的金额，前提是商户编号和业务订单号一致，且解除金额小于等于冻结金额
	 */
	@RequestMapping(value = "/unFreezeBalance", produces = "text/plain;charset=utf-8")
	public List<String> unFreezeBalance(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		OrderFreezeDto orderFreezeDto = new OrderFreezeDto();
		orderFreezeDto.setStoreCode(params.getMerchantcode());
		orderFreezeDto.setOrderType(PayDict.ORDER_SUBJECT_UNFREEZE_MONEY);
		orderFreezeDto.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_PAY);

		orderFreezeDto = (OrderFreezeDto) UPPJsonUtil.jsonToObject(paramJson, orderFreezeDto);

		Map<String, Object> mpReturn = paymentTradeService.unFreezeAccountMoney(orderFreezeDto);
		if (mpReturn.get("payConfirmDate") == null)
			mpReturn.put("payConfirmDate", new Date().getTime() + "");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, mpReturn);
	}

	/**
	 * 提现接口(TODO 未实现)
	 * <p>
	 * 描述：用于将用户账户可提现金额以T+1的方式结算到用户绑定的银行卡中
	 */
	@RequestMapping(value = "/withdrawcash", produces = "text/plain;charset=utf-8")
	public List<String> withdrawcash(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, null);
	}

	/**
	 * 分页查询交易记录
	 */
	@RequestMapping(value = "/queryPaymentTradeByPage", produces = "text/plain;charset=UTF-8")
	public List<String> queryPaymentTradeByPage(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		PaginationResult<TradeDto> prReturn = paymentTradeService.queryPaymentTradeByPage(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, prReturn);
	}

	/**
	 * 查询交易记录集合
	 */
	@RequestMapping(value = "/queryPaymentTradeList", produces = "text/plain;charset=UTF-8")
	public List<String> queryPaymentTradeList(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		List<TradeDto> lstReturn = paymentTradeService.queryPaymentTradeList(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, lstReturn);
	}

	/**
	 * 分页查询交易订单
	 */
	@RequestMapping(value = "/queryOrderByPage", produces = "text/plain;charset=UTF-8")
	public List<String> queryOrderByPage(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		PaginationResult<OrderDto> prOrderDto = orderService.queryOrderByPage(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, prOrderDto);
	}
	@RequestMapping(value = "/getOrderCount", produces = "text/plain;charset=UTF-8")
	public List<String> getOrderCount(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		Integer count = orderService.getOrderCount(parameter);
		Map<String, Object> mpReturn=new HashMap<String,Object>();
		mpReturn.put("count", count+"");
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, mpReturn);
	}

	/**
	 * 分页查询交易订单
	 */
	@RequestMapping(value = "/getOrderById", produces = "text/plain;charset=UTF-8")
	public List<String> getOrderById(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String orderId = super.getStringFromJsonMap(jsonMap, "orderId");
		if (StringUtils.isBlank(orderId)) 
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "orderId不能为空");
		
		OrderDto orderDto = orderService.getOrderById(orderId);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, orderDto);
	}
	/**
	 * 
	 */
	@RequestMapping(value = "/getOrderByOrderNo", produces = "text/plain;charset=UTF-8")
	public List<String> getOrderByOrderNo(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String orderNo = super.getStringFromJsonMap(jsonMap, "orderNo");
		if (StringUtils.isBlank(orderNo)) 
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "orderId不能为空");
		
		OrderDto orderDto = orderService.getOrderByOrderNo(orderNo);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, orderDto);
	}
	@RequestMapping(value = "/getCallbackOrderResult", produces = "text/plain;charset=UTF-8")
	public List<String> getCallbackOrderResult(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);
		String workOrderNo = super.getStringFromJsonMap(jsonMap, "merchantOrderNo");
		String tradeExternalNo=super.getStringFromJsonMap(jsonMap, "tradeExternalNo");
		boolean result = orderService.getCallbackOrderInfo(workOrderNo, tradeExternalNo);
		Map<String, Object> mpReturn = new HashMap<String,Object>();
		
		if(result){
			mpReturn.put("data", "1");
		}else{
			mpReturn.put("data", "-1");
		}
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, mpReturn);
	}
	
}
