package com.sinoiov.upp.controller.cashier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.controller.BaseController;
import com.sinoiov.upp.service.ICashierServiceForFast;
import com.sinoiov.upp.yeepayfastgateway.bean.YeepayFastBankCard;
import com.sinoiov.upp.yeepayfastgateway.bean.YeepayFastBankCardList;

@Scope("prototype")
@Controller
@RequestMapping(value = "/cashierForFastController")
public class CashierForFastController extends BaseController {

	@Autowired
	private ICashierServiceForFast cashierServiceForFastImpl;

	/**
	 * 查询用户绑定的有效期内的银行卡列表
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return
	 */
	@RequestMapping(value = "/queryBindBankCard", produces = "text/plain;charset=utf-8")
	public List<String> queryBindBankCard(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String userId = map.getString("userId");
		List<YeepayFastBankCardList> list = cashierServiceForFastImpl.queryBindBankCard(userId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", list);

		return commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 查询银行卡
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户在银行预留的手机号
	 */
	@RequestMapping(value = "/queryBankCard", produces = "text/plain;charset=utf-8")
	public List<String> queryBankCard(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String bankCardNo = map.getString("bankCardNo");
		YeepayFastBankCard fastBankCard = cashierServiceForFastImpl.queryBankCard(bankCardNo);

		return commonControllerReturn(params.getMerchantcode(), paramJson, fastBankCard);
	}

	/**
	 * 请求发送短信验证码
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 用户在银行预留的手机号
	 */
	@RequestMapping(value = "/sendMessageCode", produces = "text/plain;charset=utf-8")
	public List<String> sendMessageCode(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String workOrderNo = map.getString("workOrderNo");
		Map<String, String> returnMap = cashierServiceForFastImpl.sendMessageCode(workOrderNo);

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 解绑银行卡
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return 1：成功
	 */
	@RequestMapping(value = "/unBindCard", produces = "text/plain;charset=utf-8")
	public List<String> unBindCard(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String userId = map.getString("userId");
		String bindCardId = map.getString("bindCardId");
		Map<String, String> returnMap = cashierServiceForFastImpl.unBindCard(userId, bindCardId);

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}

	/**
	 * 查询业务订单号是否已支付
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return
	 */
	@RequestMapping(value = "/queryOrderRechargeOverOrNot", produces = "text/plain;charset=utf-8")
	public List<String> queryOrderRechargeOverOrNot(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject map = JSONObject.fromObject(paramJson);
		String workOrderNo = map.getString("workOrderNo");
		String tradeExternalNo = map.getString("tradeExternalNo");
		Map<String, String> returnMap = cashierServiceForFastImpl.queryOrderRechargeOverOrNot(workOrderNo,tradeExternalNo);

		return commonControllerReturn(params.getMerchantcode(), paramJson, returnMap);
	}
}
