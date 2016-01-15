package com.sinoiov.upp.controller;

import java.util.ArrayList;
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
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.root.utils.SmsSender;
import com.sinoiov.pay.utils.UPPConfigUtil;
import com.sinoiov.pay.utils.UPPJsonUtil;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.service.IAccountService;
import com.sinoiov.upp.service.IOrderService;
import com.sinoiov.upp.service.ISimpleCodeService;
import com.sinoiov.upp.service.ISmsCodeService;
import com.sinoiov.upp.service.IStatisticsService;
import com.sinoiov.upp.service.dto.AccountDetailDto;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.service.dto.OrderDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Scope("prototype")
@Controller
@RequestMapping(value = "/account")
public class Account extends BaseController {

	@Autowired
	private IAccountService accountService;
	@Autowired
	private ISmsCodeService smsCodeService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IStatisticsService statisticsService;
	@Autowired
	private ISimpleCodeService simpleCodeService;
	
	private static Map<String,String> temMap = new HashMap<String,String>();

	// 支付后台的商户编号
	private static String WEB_APP_CODE = null;

	public Account() {
		try {
			WEB_APP_CODE = UPPConfigUtil.getValue("WEB_APP_CODE");
		} catch (Exception e) {
			logger.error("从配置文件中读取支付商户号报错!", e);
		}
	}

	/**
	 * jsonMap取数据转为AccountDto（注：这里不取支付密码参数）
	 */
	private AccountDto getAccountDto(JSONObject jsonMap) throws UPPServiceException {
		String mobileNo = super.getStringFromJsonMap(jsonMap, "mobileNo");
		String ownerUserNo = super.getStringFromJsonMap(jsonMap, "ownerUserNo");
		String applyPersonPost = super.getStringFromJsonMap(jsonMap, "applyPersonPost");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");
		if (!SmsSender.isMobileNO(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码格式不正确");

		String ownerUserId = super.getStringFromJsonMap(jsonMap, "ownerUserId");
		String ownerLoginName = super.getStringFromJsonMap(jsonMap, "ownerLoginName");
		String payPassword = super.getStringFromJsonMap(jsonMap, "payPassword");
		if (StringUtils.isBlank(ownerUserId))
			throw new UPPServiceException(ReturnCodeDict.USER_ID_N, "用户ID不能为空");
		if (StringUtils.isBlank(ownerLoginName))
			throw new UPPServiceException(ReturnCodeDict.USER_LOGIN_NAME_N, "登录帐号不能为空");

		AccountDto accountDto = new AccountDto();
		accountDto.setMobileNo(mobileNo);
		accountDto.setOwnerLoginName(ownerLoginName);
		accountDto.setOwnerUserId(ownerUserId);
		if(StringUtils.isNotBlank(payPassword))
			accountDto.setPayPassword(payPassword);
		if(StringUtils.isNotBlank(ownerUserNo))
			accountDto.setOwnerUserNo(ownerUserNo);
		return accountDto;
	}

	/**
	 * 开户（需要短信验证码）
	 * <p>
	 * 描述：用于为用户开户
	 */
	@RequestMapping(value = "/createAccountSmsCode", produces = "text/plain;charset=UTF-8")
	public List<String> createAccountSmsCode(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String smsCode = super.getStringFromJsonMap(jsonMap, "smsCode");
		String MD5 = super.getStringFromJsonMap(jsonMap, "MD5");
		if (StringUtils.isBlank(smsCode))
			throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "短信验证码不能为空");
		if (StringUtils.isBlank(MD5))
			throw new UPPServiceException(ReturnCodeDict.OLD_MD5, "支付密码不能为空");
		if (StringUtils.isBlank(MD5))
			throw new UPPServiceException(ReturnCodeDict.OLD_MD5, "支付密码不能为空");
		AccountDto accountDto = this.getAccountDto(jsonMap);
		accountDto.setPayPassword(MD5);

		String accountNo = accountService.createAccount(smsCode, accountDto);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("accountNo", accountNo);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 开户(创建的账户支付密码为默认密码)
	 * <p>
	 * 描述：用于为用户开户，后台服务器调用开户
	 */
	@RequestMapping(value = "/createAccount", produces = "text/plain;charset=UTF-8")
	public List<String> createAccount(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		AccountDto accountDto = this.getAccountDto(jsonMap);

		String accountNo = accountService.createAccount(accountDto);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("accountNo", accountNo);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 根据账号查询账户信息接口
	 * <p>
	 * 描述：根据账号查询账户信息
	 */
	@RequestMapping(value = "/queryAccountInfo", produces = "text/plain;charset=UTF-8")
	public List<String> getAccountByAccountNo(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "账号不能为空");

		AccountDto accountDto = accountService.getAccountByAccountNo(accountNo);

		if (accountDto == null)
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "账户不存在");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, accountDto);
	}

	/**
	 * 根据用户ID查询账户信息
	 * <p>
	 * 描述：根据账户所属的用户ID查询账户信息
	 */
	@RequestMapping(value = "/queryAccountInfoByUserId", produces = "text/plain;charset=UTF-8")
	public List<String> getAccountByUserId(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String userId = super.getStringFromJsonMap(jsonMap, "userId");
		if (StringUtils.isBlank(userId))
			throw new UPPServiceException(ReturnCodeDict.USER_ID_N, "用户ID不能为空");

		AccountDto accountDto = accountService.getAccountByUserId(userId);

		if (accountDto == null)
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "账户不存在");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, accountDto);
	}

	//取订单科目对应的名称
	private String getNameByCode(String code) throws Exception{
		String val = temMap.get(code);
		if(StringUtils.isBlank(val)){
			List<com.ctfo.base.dao.beans.SimpleCode> codes = (List<com.ctfo.base.dao.beans.SimpleCode>)simpleCodeService.queryList("ORDER_SUBJECT");
			for(com.ctfo.base.dao.beans.SimpleCode sc : codes){
				if(temMap.get(sc.getCode())==null){
					temMap.put(sc.getCode(), sc.getName());
				}
			}
			val = temMap.get(code)==null?"":temMap.get(code);
		}
		return val;
	}
	
	
	/**
	 * 分页查询账户流水
	 */
	@RequestMapping(value = "/queryAccountHistory", produces = "text/plain;charset=UTF-8")
	public List<String> queryAccountDetailByPage(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		PaginationResult<AccountDetailDto> result = accountService.queryAccountDetailByPage(parameter);
		//兼容旧版本
		if((UPPConfigUtil.getValue("YP_MERCHANT_CODE")).equals(params.getMerchantcode())){
			List<AccountDetailDto> temp = new ArrayList<AccountDetailDto>();
			for(AccountDetailDto dto : result.getData()){
				dto.setBookAccountMoney(StringUtils.isNotBlank(dto.getBookAccountMoney())?AmountUtil.getAmount(dto.getBookAccountMoney())+"":"0");
				dto.setBookCurrentMoney(StringUtils.isNotBlank(dto.getBookCurrentMoney())?AmountUtil.getAmount(dto.getBookCurrentMoney())+"":"0");
				dto.setOrderTypeLuc(this.getNameByCode(dto.getOrderTypeLuc()));
				temp.add(dto);
			}
			result.setData(temp);
		}
		
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, result);
	}
	/**
	 * 统计账户流水数量
	 */
	@RequestMapping(value = "/countAccountDetail", produces = "text/plain;charset=UTF-8")
	public List<String> countAccountDetail(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		
		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);
		
		int count = accountService.countAccountDetail(parameter);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("count", count+"");
		
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, map);
	}
	/**
	 * 统计账户数量
	 */
	@RequestMapping(value = "/countOfAccount", produces = "text/plain;charset=UTF-8")
	public List<String> countOfAccount(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		
		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);
		
		int count = accountService.countOfAccount(parameter);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("count", count+"");
		
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, map);
	}
	/**
	 * 根据ID修改账户流水备注
	 * <p>
	 * 描述：根据ID修改账户流水备注
	 */
	@RequestMapping(value = "/modifyOrderRemarkByOrderId", produces = "text/plain;charset=UTF-8")
	public List<String> modifyOrderRemark(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String id = super.getStringFromJsonMap(jsonMap, "id");
		String remarks = super.getStringFromJsonMap(jsonMap, "remarks");
		if (StringUtils.isBlank(id))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "ID不能为空");
		if (StringUtils.isBlank(remarks))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "备注不能为空");

		orderService.modifyOrderRemark(id, remarks);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");// 1代表成功;如果失败则会抛出异常

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 根据流水ID查询对应的账户流水信息
	 */
	@RequestMapping(value = "/queryAccountHistoryById", produces = "text/plain;charset=UTF-8")
	public List<String> getAccountDetailById(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String id = super.getStringFromJsonMap(jsonMap, "id");
		if (StringUtils.isBlank(id))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "ID不能为空");

		Map<String, String> resultMap = accountService.getAccountDetailById(id);

		//兼容旧版本
		if((UPPConfigUtil.getValue("YP_MERCHANT_CODE")).equals(params.getMerchantcode())){
			String bookAccountMoney = StringUtils.isNotBlank(resultMap.get("bookAccountMoney"))?AmountUtil.getAmount(resultMap.get("bookAccountMoney"))+"":"0";
			String bookCurrentMoney = StringUtils.isNotBlank(resultMap.get("bookCurrentMoney"))?AmountUtil.getAmount(resultMap.get("bookCurrentMoney"))+"":"0";
			String orderTypeLuc = StringUtils.isNotBlank(resultMap.get("orderTypeLuc"))?this.getNameByCode(resultMap.get("orderTypeLuc")):"";	
			resultMap.put("bookAccountMoney", bookAccountMoney);		
			resultMap.put("bookCurrentMoney", bookCurrentMoney);		
			resultMap.put("orderTypeLuc", orderTypeLuc);		
		}
		
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 锁定帐户
	 */
	@RequestMapping(value = "/lockAccount", produces = "text/plain;charset=UTF-8")
	public List<String> lockAccount(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String lockReasons = super.getStringFromJsonMap(jsonMap, "lockReasons");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "账号不能为空");
		if (StringUtils.isBlank(lockReasons))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "锁定原因不能为空");

		accountService.lockAccount(accountNo, lockReasons);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 解锁账户
	 */
	@RequestMapping(value = "/unlockAccount", produces = "text/plain;charset=UTF-8")
	public List<String> unlockAccount(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "账号不能为空");

		accountService.unLockAccount(accountNo);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 修改支付密码(需要输入旧密码和短信验证码等)
	 */
	@RequestMapping(value = "/modifyPayPassword", produces = "text/plain;charset=UTF-8")
	public List<String> modifyPayPassword(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String originalPassword = super.getStringFromJsonMap(jsonMap, "oldMD5");
		String newPassword = super.getStringFromJsonMap(jsonMap, "newMD5");
		String mobileNo = super.getStringFromJsonMap(jsonMap, "mobileNo");
		String smsCode = super.getStringFromJsonMap(jsonMap, "smsCode");

		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		if (StringUtils.isBlank(originalPassword))
			throw new UPPServiceException(ReturnCodeDict.OLD_MD5, "旧密码不能为空");
		if (StringUtils.isBlank(newPassword))
			throw new UPPServiceException(ReturnCodeDict.PAY_PASSWORD_ERROR, "新密码不能为空");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");
		if (StringUtils.isBlank(smsCode))
			throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "短信验证码不能为空");

		accountService.modifyPayPassword(accountNo, originalPassword, newPassword, mobileNo, smsCode);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 修改支付密码(不需要输入旧密码，需要"安全票据")
	 * <p>
	 * 使用这个接口前需要通过验证(调用/accountSafety/validate接口)并生成"安全票据"
	 */
	@RequestMapping(value = "/modifyPayPasswordByTicket", produces = "text/plain;charset=UTF-8")
	public List<String> modifyPayPasswordByTicket(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String newPassword = super.getStringFromJsonMap(jsonMap, "newMD5");
		String securityTicket = super.getStringFromJsonMap(jsonMap, "securityTicket");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		if (StringUtils.isBlank(newPassword))
			throw new UPPServiceException(ReturnCodeDict.PAY_PASSWORD_ERROR, "新密码不能为空");

		if (!WEB_APP_CODE.equals(params.getMerchantcode())) {
			if (StringUtils.isBlank(securityTicket))
				throw new UPPServiceException(ReturnCodeDict.IS_NULL, "安全票据不能为空");
		}

		accountService.modifyPayPasswordByTicket(accountNo, newPassword, securityTicket);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 设置支付密码
	 */
	@RequestMapping(value = "/setPayPasswordByTicket", produces = "text/plain;charset=UTF-8")
	public List<String> setPayPasswordByTicket(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String originalPassword = super.getStringFromJsonMap(jsonMap, "MD5");
		String securityTicket = super.getStringFromJsonMap(jsonMap, "securityTicket");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		if (StringUtils.isBlank(originalPassword))
			throw new UPPServiceException(ReturnCodeDict.PAY_PASSWORD_ERROR, "密码不能为空");
		if (StringUtils.isBlank(securityTicket))
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "安全票据不能为空");

		accountService.setPayPassword(accountNo, originalPassword, securityTicket);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 设置支付密码
	 */
	@RequestMapping(value = "/setPayPassword", produces = "text/plain;charset=UTF-8")
	public List<String> setPayPassword(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String originalPassword = super.getStringFromJsonMap(jsonMap, "MD5");
		String mobileNo = super.getStringFromJsonMap(jsonMap, "mobileNo");
		String smsCode = super.getStringFromJsonMap(jsonMap, "smsCode");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		if (StringUtils.isBlank(originalPassword))
			throw new UPPServiceException(ReturnCodeDict.PAY_PASSWORD_ERROR, "密码不能为空");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");
		if (!SmsSender.isMobileNO(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码格式不正确");
		if (StringUtils.isBlank(smsCode))
			throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "短信验证码不能为空");

		accountService.setPayPassword(accountNo, originalPassword, mobileNo, smsCode);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 检查账户支付密码是否正确
	 */
	@RequestMapping(value = "/isPayPassword", produces = "text/plain;charset=UTF-8")
	public List<String> isPayPassword(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String smsCode = super.getStringFromJsonMap(jsonMap, "smsCode");
		String mobileNo = super.getStringFromJsonMap(jsonMap, "mobileNo");
		String originalPassword = super.getStringFromJsonMap(jsonMap, "MD5");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		if (StringUtils.isBlank(smsCode))
			throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "短信验证码不能为空");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");
		if (!SmsSender.isMobileNO(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码格式不正确");
		if (StringUtils.isBlank(originalPassword))
			throw new UPPServiceException(ReturnCodeDict.PAY_PASSWORD_ERROR, "密码不能为空");

		boolean result = accountService.isPayPassword(accountNo, smsCode, mobileNo, originalPassword);
		String pwdErrorCount = "";
		if (!result) {
			AccountDto dto = accountService.getAccountByAccountNo(accountNo);
			int errorLimit = Integer.parseInt(UPPConfigUtil.getValue("PAY_PASSWORD_ERROR_NUMBER"));
			int error = StringUtils.isBlank(dto.getPwdErrorCount()) ? 0 : Integer.parseInt(dto.getPwdErrorCount()
					.trim());
			pwdErrorCount = (errorLimit - error) + "";
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", result ? "1" : "-1");
		resultMap.put("pwdErrorCount", pwdErrorCount);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 检查账户是否设置过支付密码
	 */
	@RequestMapping(value = "/isSetPayPassword", produces = "text/plain;charset=UTF-8")
	public List<String> isSetPayPassword(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");

		boolean result = accountService.isSetPayPassword(accountNo);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", result ? "1" : "-1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 获取短信验证码
	 */
	@RequestMapping(value = "/getSmsCode", produces = "text/plain;charset=UTF-8")
	public List<String> getSmsCode(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String mobileNo = super.getStringFromJsonMap(jsonMap, "mobileNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");
		if (!SmsSender.isMobileNO(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码格式不正确");

		if (StringUtils.isNotBlank(accountNo) && !accountNo.equals(params.getMerchantcode())) {
			// 注：上面比对“账户号”和“商户号”是否相等，用在手机端调用此接口时，当时约定：如果不想验证账户是否与手机号是绑定关系，
			// 则“账户号”和“商户号”数据传相同的值
			AccountDto account = accountService.getAccountByAccountNo(accountNo);
			String dbMobileNo = account.getMobileNo();
			if (StringUtils.isBlank(dbMobileNo))
				throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "帐户不合法，未绑定手机号码");
			if (!mobileNo.equals(dbMobileNo))
				throw new UPPServiceException(ReturnCodeDict.ACCOUNT_MOBILENO_N, "不是帐户所绑定的手机号码");
		}

		smsCodeService.getSmsCode(mobileNo);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 获取短信验证码(修改手机号码时使用)
	 * <p>
	 * 注：这里没有验证接收短信的手机号是否是账户绑定的手机号。PLUS:这个接口只是在修改手机号码时使用，可能存在安全漏洞。
	 */
	@RequestMapping(value = "/getSmsCodeForModifyMobileNo", produces = "text/plain;charset=UTF-8")
	public List<String> getSmsCodeForModifyMobileNo(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String mobileNo = super.getStringFromJsonMap(jsonMap, "mobileNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");
		if (!SmsSender.isMobileNO(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码格式不正确");

		smsCodeService.getSmsCode(mobileNo);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 统计账户流水
	 * <p>
	 * 描述：根据账号和时间统计该账号在这段时间内出项和进项的总额
	 */
	@RequestMapping(value = "/sumAccountBalance", produces = "text/plain;charset=UTF-8")
	public List<String> sumAccountBalance(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		
		String startTime = super.getStringFromJsonMap(jsonMap, "startTime");
		String endTime = super.getStringFromJsonMap(jsonMap, "endTime");
		
		DynamicSqlParameter parameter = new DynamicSqlParameter();
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountNo", accountNo);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		parameter.setEqual(map);
		
		map = statisticsService.sumAccountDetail(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, map);
	}

	/**
	 * 根据业务订单号查询订单信息
	 */
	@RequestMapping(value = "/queryPayOrderInfo", produces = "text/plain;charset=UTF-8")
	public List<String> queryPayOrderInfoByWorkOrderNo(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String workOrderNo = super.getStringFromJsonMap(jsonMap, "workOrderNo");
		if (StringUtils.isBlank(workOrderNo))
			throw new UPPServiceException(ReturnCodeDict.WORK_NO_IS_NULL, "业务订单号不能为空");

		List<OrderDto> lstOrders = orderService.queryPayOrderInfoByWorkOrderNo(params.getMerchantcode(), workOrderNo);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, lstOrders);
	}

	/**
	 * 查询账户流水列表
	 */
	@RequestMapping(value = "/queryAccountDetailList", produces = "text/plain;charset=UTF-8")
	public List<String> queryAccountDetailList(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		List<AccountDetailDto> lstReturn = accountService.queryAccountDetailList(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, lstReturn);
	}

	/**
	 * 注销账户
	 */
	@RequestMapping(value = "/revokeAccount", produces = "text/plain;charset=UTF-8")
	public List<String> revokeAccount(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");

		accountService.revokeAccount(accountNo);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 分页查询账户
	 */
	@RequestMapping(value = "/queryAccountByPage", produces = "text/plain;charset=UTF-8")
	public List<String> queryAccountByPage(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		PaginationResult<AccountDto> lstReturn = accountService.queryAccountByPage(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, lstReturn);
	}

	/**
	 * 查询账户列表
	 */
	@RequestMapping(value = "/queryAccountList", produces = "text/plain;charset=UTF-8")
	public List<String> queryAccountList(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		List<AccountDto> lstReturn = accountService.queryAccountList(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, lstReturn);
	}

	/**
	 * 修改账户绑定手机号码
	 */
	@RequestMapping(value = "/modifyMobileNo", produces = "text/plain;charset=UTF-8")
	public List<String> modifyMobileNo(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String accountNo = super.getStringFromJsonMap(jsonMap, "accountNo");
		String mobileNo = super.getStringFromJsonMap(jsonMap, "mobileNo");// 需要修改成的新手机号

		if (StringUtils.isBlank(accountNo))
			throw new UPPServiceException(ReturnCodeDict.ACCOUNT_N, "帐号不能为空");
		if (StringUtils.isBlank(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码不能为空");
		if (!SmsSender.isMobileNO(mobileNo))
			throw new UPPServiceException(ReturnCodeDict.MOBILENO_N, "手机号码格式不正确");

		String securityTicket = null;
		String smsCode = null;
		if (!WEB_APP_CODE.equals(params.getMerchantcode())) {
			// 如果不是支付后台调用此接口，则需要传securityTicket
			securityTicket = super.getStringFromJsonMap(jsonMap, "securityTicket");
			smsCode = super.getStringFromJsonMap(jsonMap, "smsCode");
			if (StringUtils.isBlank(securityTicket))
				throw new UPPServiceException(ReturnCodeDict.IS_NULL, "安全票据不能为空");
			if (StringUtils.isBlank(smsCode))
				throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "短信验证码不能为空");
		}

		accountService.modifyMobileNo(accountNo, mobileNo, securityTicket, smsCode);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", "1");// TODO 这里的返回key统一用result比较好,不过之前有data

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

}
