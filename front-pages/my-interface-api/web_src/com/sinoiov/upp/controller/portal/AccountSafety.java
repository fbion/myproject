package com.sinoiov.upp.controller.portal;

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

import com.ctfo.upp.dict.ReturnCodeDict;
import com.sinoiov.pay.utils.UPPJsonUtil;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.controller.BaseController;
import com.sinoiov.upp.service.IAccountSafetyService;
import com.sinoiov.upp.service.dto.AccountSafetyDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Scope("prototype")
@Controller
@RequestMapping(value = "/accountSafety")
public class AccountSafety extends BaseController {

	@Autowired
	private IAccountSafetyService accountSafetyService;

	/**
	 * 增加账户安全信息
	 * <p>
	 * 描述：用于增强账户安全
	 */
	@RequestMapping(value = "/createAccountSafety", produces = "text/plain;charset=UTF-8")
	public List<String> createAccountSafety(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		AccountSafetyDto safetyDto = new AccountSafetyDto();

		// TODO 这里转化成bean后没有做属性判空
		safetyDto = (AccountSafetyDto) UPPJsonUtil.jsonToObject(paramJson, safetyDto);

		String recordId = accountSafetyService.addAccountSafetyMess(safetyDto);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", StringUtils.isNotBlank(recordId) ? "1" : "-1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 修改账户安全信息
	 * <p>
	 * 描述：用于增强账户安全
	 */
	@RequestMapping(value = "/modifyAccountSafety", produces = "text/plain;charset=UTF-8")
	public List<String> modifyAccountSafety(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		AccountSafetyDto safetyDto = new AccountSafetyDto();
		safetyDto = (AccountSafetyDto) UPPJsonUtil.jsonToObject(paramJson, safetyDto);

		boolean result = accountSafetyService.modifyAccountSafetyMess(safetyDto);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", result ? "1" : "-1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 重置账户安全信息
	 * <p>
	 * 描述：用于用户忘记安全问题时后台管理员为其重置
	 */
	@RequestMapping(value = "/resetSecurityQuestion", produces = "text/plain;charset=UTF-8")
	public List<String> resetSecurityQuestion(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");

		boolean result = accountSafetyService.resetAccountSafetyByAccountNo(accountNo);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", result ? "1" : "-1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 根据账号查询账户设置的安全问题名称编码列表
	 * <p>
	 * 返回格式：如"001,002,003"
	 */
	@RequestMapping(value = "/querySecurityQuestion", produces = "text/plain;charset=UTF-8")
	public List<String> querySecurityQuestion(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");

		String questions = accountSafetyService.querySecurityQuestion(accountNo);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", questions);// 返回安全问题名称列表,格式如"q1,q2,q3"

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 验证账户相关各种信息(短信\支付密码\单个安全问题)，返回"安全票据"securityTicket
	 */
	@RequestMapping(value = "/validate", produces = "text/plain;charset=UTF-8")
	public List<String> validate(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");

		String mobileNo = super.getStringFromJsonMap(jsonMap, "mobileNo");
		String smsCode = super.getStringFromJsonMap(jsonMap, "smsCode");

		if ((StringUtils.isNotBlank(mobileNo) && StringUtils.isBlank(smsCode)) || StringUtils.isBlank(mobileNo)
				&& StringUtils.isNotBlank(smsCode)) {
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "手机号和短信验证码不能同时为空");// TODO
		}

		String payPassword = super.getStringFromJsonMap(jsonMap, "payPassword");

		String securityQuestion = super.getStringFromJsonMap(jsonMap, "securityQuestion");
		String securityAnswer = super.getStringFromJsonMap(jsonMap, "securityAnswer");
		if ((StringUtils.isNotBlank(securityQuestion) && StringUtils.isBlank(securityAnswer))
				|| StringUtils.isBlank(securityQuestion) && StringUtils.isNotBlank(securityAnswer)) {
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "安全问题和答案不能同时为空");// TODO
		}

		// 需要验证的参数不能同时为空
		if (StringUtils.isBlank(mobileNo) && StringUtils.isBlank(smsCode) && StringUtils.isBlank(payPassword)
				&& StringUtils.isBlank(securityQuestion) && StringUtils.isBlank(securityAnswer)) {
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "需要验证的参数不能同时为空");// TODO
		}

		String securityTicket = accountSafetyService.validate(accountNo, mobileNo, smsCode, payPassword,
				securityQuestion, securityAnswer);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", securityTicket);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

}
