package com.ctfo.upp.service.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.account.dto.beans.AccountDTO;
import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.bean.UnfreezeAmountDto;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dto.UPPAccountDto;
import com.ctfo.upp.dto.UPPDto;
import com.ctfo.upp.dto.UPPOrderRechargeDto;
import com.ctfo.upp.excelbeans.ManageAccountExcel;
import com.ctfo.upp.excelbeans.ZJAccountExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.service.TaskServiceImpl;
import com.ctfo.upp.service.simplecode.ISimplecodeService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DateUtil;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.AccountDto;

/***
 * 类描述：普通账户和中交账户列表Service
 * 
 * @author：liuguozhong
 * @date：2015年1月16日下午2:34:38
 * @version 1.0
 * @since JDK1.6
 */
@Service("accountService")
public class AccountServiceImpl extends TaskServiceImpl<AccountDto, ManageAccountExcel> implements IAccountService {

	private static Log logger = LogFactory.getLog(AccountServiceImpl.class);
	private Map<String, String> codeMap = new HashMap<String, String>();
	@Resource
	private ISimplecodeService simpleCodeService;
	//开户manager
//	private InternalAccountManager manager = null;
	//充值manager
//	private IRechargeManager rechargeManager=null; 
	
//	private IPaymentTradeManager paymentTradeManager=null;
	
//	private ICheckPwErrorTimes checkPwErrorTimesService = null;
	
	private final String CHECK_TRUE = "1";
	
//	private ICheckPwErrorTimes getICheckPwErrorTimes(){
//		if (this.checkPwErrorTimesService == null) {
//			checkPwErrorTimesService = (ICheckPwErrorTimes) ServiceFactory.getFactory().getService(ICheckPwErrorTimes.class);
//		}
//		logger.info("webAPP普通账户-调用账户服务manager"+checkPwErrorTimesService);
//		return this.checkPwErrorTimesService;
//	}
	
//	private InternalAccountManager getManager() {
//		if (this.manager == null) {
//			manager = (InternalAccountManager) ServiceFactory.getFactory().getService(InternalAccountManager.class);
//		}
//		logger.info("webAPP中交账户-调用账户服务manager"+manager);
//		return this.manager;
//	}
	
//	private IRechargeManager getRechargeManager() {
//		if (this.rechargeManager == null) {
//			rechargeManager = (IRechargeManager) ServiceFactory.getFactory().getService(IRechargeManager.class);
//		}
//		logger.info("webAPP中交账户-调用充值服务manager"+rechargeManager);
//		return this.rechargeManager;
//	}
	
//	private IPaymentTradeManager getPaymentTradeManager(){
//		if (this.paymentTradeManager == null) {
//			paymentTradeManager = (IPaymentTradeManager) ServiceFactory.getFactory().getService(IPaymentTradeManager.class);
//		}
//		logger.info("webAPP中交账户-账户解锁服务manager"+paymentTradeManager);
//		return this.paymentTradeManager;
//	}
	
	/***
	 * 中交账户充值
	 * @param orderDto
	 * @return
	 * @throws UPPException
	 */
	@Override
	public Map<String, Object> recharge(UPPOrderRechargeDto orderDto) throws UPPException {
		logger.info("webAPP中交账户-开户调用充值服务manager----开始");
		Map<String, Object> map=new HashMap<String,Object>();
		try{		
			//检查参数合法
			String check = this.checkOrderDtoProperties(orderDto);
			if(!CHECK_TRUE.equals(check))
				throw new UPPException(check);
			//转换
			OrderInfo orderInfo = new OrderInfo();
			this.copyProperties(orderDto, orderInfo);
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);//订单类型
			orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_PAY);
			orderInfo.setWorkOrderNo(new Date().getTime()+"");
//			map = getRechargeManager().recharge(orderInfo);
			logger.info("UPP_HTTP_INTERFACE_UPP_RECHARGE_PARAM: " + JSONObject.fromObject(orderInfo).toString());
			String json = super.sendRequest(JSONObject.fromObject(orderInfo)
					.toString(), DefaultConfig
					.getValue("UPP_RECHARGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_RECHARGE: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				map = (Map<String, Object>)JSONObject.toBean(dataObj, Map.class);
			}
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("中交账户webAPP调接口服务异常",e);
			throw new UPPException("中交账户webAPP调接口服务异常");
		}
		logger.info("webAPP中交账户-开户调用充值服务manager----结束");
		return map;
	}
	/**
	 * 检查开户传参数非空校验
	 * @param uppAccountDto
	 * @return
	 */
	public PaginationResult<?> checkOpenAccount(UPPAccountDto uppAccountDto)throws UPPException{
		PaginationResult<?> result = new PaginationResult<Object>();
		if("".equals(uppAccountDto.getServiceProviderCode())||uppAccountDto.getServiceProviderCode()==null){//易宝平台编号
			result.setMessage("支付平台编号为空");
			return result;
		}
		if("".equals(uppAccountDto.getOwnerUserId())||uppAccountDto.getOwnerUserId()==null){//所属用户UUID
			result.setMessage("所属用户UUID为空");
			return result;
		}
		if("".equals(uppAccountDto.getOwnerLoginName())||uppAccountDto.getOwnerLoginName()==null){//所属用户统一认证登陆名
			result.setMessage("所属用户统一认证登陆名为空");
			return result;
		}
		if("".equals(uppAccountDto.getPayPassword())||uppAccountDto.getPayPassword()==null){//交易密码
			result.setMessage("交易密码为空");
			return result;
		}
		if("".equals(uppAccountDto.getMobileNo())||uppAccountDto.getMobileNo()==null){//手机号码
			result.setMessage("手机号码为空");
			return result;
		}
		return null;
	}
	/***
	 * 中交账户-开户
	 * @param UPPAccountDto 开户实体
	 * @return 成功返回 true,失败返回false
	 * @throws UPPException
	 */
	public String createAccount(UPPAccountDto uppAccountDto) throws UPPException {
		logger.info("webAPP中交账户-开户调用开户服务manager----开始");
		String result = "";
		final Account account = new Account();
		final AccountDTO _accountDTO = new AccountDTO();
		try {
			this.checkParams(uppAccountDto);
			// 帐户类型
			uppAccountDto.setAccountType((StringUtils.isBlank(uppAccountDto.getAccountType())) ? PayDict.ACCOUNT_TYPE_GOODS_OWNER : uppAccountDto.getAccountType());
			// 平台编码
			uppAccountDto.setServiceProviderCode((StringUtils.isBlank(uppAccountDto.getServiceProviderCode())) ? PayDict.PLATFORM_CODE_YEE_PAY : uppAccountDto.getServiceProviderCode());
			// 平台编码
			uppAccountDto.setPayPassword(uppAccountDto.getPayPassword());
			copyAccountProperties(account, uppAccountDto);
			_accountDTO.setServiceProviderCode(uppAccountDto.getServiceProviderCode());
			_accountDTO.setAccount(account);
			
			//开始调用开户manager
//			Account myAccount = getManager().createAccount(_accountDTO);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_BY_ACCDTO_PARAM: " + JSONObject.fromObject(_accountDTO).toString());
			String json = super.sendRequest(JSONObject.fromObject(_accountDTO)
					.toString(), DefaultConfig
					.getValue("UPP_QUERY_ACCOUNT_BY_ACCDTO"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_BY_ACCDTO: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			Account myAccount = null;
			if (dataObj != null) {
				myAccount = (Account)JSONObject.toBean(dataObj, Account.class);
			}
			
			result = myAccount == null ? "" : myAccount.getInsideAccountNo();
		} catch (UPPException ue) {
			logger.error(ue);
			throw ue;
		} catch (Exception e) {
			logger.error("开户时产生错误，原因：" + e.getLocalizedMessage(), e);
			throw new UPPException("开户时产生错误，原因：" + e.getLocalizedMessage(), e);
		}
		logger.info("webAPP中交账户-开户调用开户服务manager----结束");
		return result;
	}

	/***
	 * 查询普账户列表 0-中交;1-车主;2-货主 除去不等于0的账户数据，列为普通账户列表数据
	 * 
	 * @param requestParam
	 * @return PaginationResult
	 */
	@Override
	public PaginationResult<Account> queryAccountByPage(DynamicSqlParameter requestParam) throws UPPException {
//		PaginationResult<Account> result = new PaginationResult<Account>();
		PaginationResult<Account> result = new PaginationResult<Account>();
		try {
			AccountExampleExtended exampleExtended = new AccountExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);
//			result = getManager().queryAccountByPage(exampleExtended);
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountType", "0");
			requestParam.setNotequal(map);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_BY_PAGE_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(requestParam, DefaultConfig
					.getValue("UPP_QUERY_ACCOUNT_BY_PAGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_BY_PAGE: " + json);
			result = super.getPaginationResult(json, AccountDto.class);
			
//			JSONObject dataObj = super.processReturnResult(json);
//			if (dataObj != null) {
//				result = (PaginationResult)JSONObject.toBean(dataObj, PaginationResult.class);
//				
//				Map<String, Class> classMap = new HashMap<String, Class>(); 
//				classMap.put("data", Account.class); 
//				result = (PaginationResult)JSONObject.toBean(dataObj, PaginationResult.class, classMap);
//			}
			
//			JSONObject dataObj = super.processReturnResult(json);
//			if (dataObj != null) {
//				result = (PaginationResult<Account>)JSONObject.toBean(dataObj, PaginationResult.class);
//			}
			
		} catch (Exception e) {
			logger.error("查询充值页面异常", e);
			throw new UPPException("查询充值页面异常");
		}
		return result;
	}

	/**
	 * 根据ID查询详细
	 * 
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	public AccountDto getAccountById(String id) throws UPPException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", id);
			
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_INFO_PARAM: " + JSONObject.fromObject(map).toString());
			String json = super.sendRequest(map,  DefaultConfig.getValue("UPP_QUERY_ACCOUNT_INFO"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_INFO: " + json);
			
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				return (AccountDto)JSONObject.toBean(dataObj, AccountDto.class);
			}
			
//			AccountExampleExtended exampleExtended = new AccountExampleExtended();
//			exampleExtended.createCriteria().andIdEqualTo(id);
//			List<AccountDto> list = new ArrayList<AccountDto>();
//			
//			DynamicSqlParameter requestParam = new DynamicSqlParameter();
//			Map<String, String> equalMap = new HashMap<String, String>();
//			equalMap.put("id", id);
//			
//			requestParam.setEqual(equalMap);
////		list = getManager().queryAccount(exampleExtended);
//			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_PARAM: " + JSONObject.fromObject(requestParam).toString());
//			String json = super.sendRequest(requestParam,  DefaultConfig.getValue("UPP_QUERY_ACCOUNT"));
//			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT: " + json);
//			
//			JSONArray dataObj = super.getJSONArrayResult(json);
//			if (dataObj != null) {
//				list = (List<AccountDto>)JSONArray.toCollection(dataObj, AccountDto.class);
//			}
//			
//			AccountDto acc = new AccountDto();
//			if (list != null && list.size() > 0) {
//				acc = list.get(0);
//			}
//			return acc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void revokeAccount(String accountNo) throws UPPException {
		try {
//			getManager().revokeAccount(accountNo);
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", accountNo);
			super.sendRequest(map, DefaultConfig
					.getValue("UPP_REVOKE_ACCOUNT"));
			
		} catch (Exception e) {
			logger.error("注销账户异常", e);
			throw new UPPException("注销账户异常");
		}
	}

	@Override
	public void lockAccount(String accountNo) throws UPPException {
		try {
//			getManager().lockAccount(accountNo, "webAPP");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", accountNo);
			map.put("lockReasons", "webAPP");
			logger.info("UPP_HTTP_INTERFACE_UPP_LOCK_ACCOUNT_PARAM: " + map);
			String json = super.sendRequest(map, DefaultConfig.getValue("UPP_LOCK_ACCOUNT"));
			logger.info("UPP_HTTP_INTERFACE_UPP_LOCK_ACCOUNT: " + json);
			
			System.out.println("json: " + json);
			
		} catch (Exception e) {
			logger.error("锁定账户异常", e);
			throw new UPPException("锁定账户异常");
		}
	}

	@Override
	public boolean resetPassword(String insideAccountNo, String newPassword) throws UPPException {
		boolean flag = false;
		try {
//			getManager().modifyPayPassword(insideAccountNo, newPassword);
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", insideAccountNo);
			map.put("newMD5", newPassword);
			logger.info("UPP_HTTP_INTERFACE_UPP_ACCOUNT_RESET_PASSWORD_PARAM: " + map);
			String json = super.sendRequest(map, DefaultConfig.getValue("UPP_ACCOUNT_RESET_PASSWORD"));
			logger.info("UPP_HTTP_INTERFACE_UPP_ACCOUNT_RESET_PASSWORD: " + json);
			String result = super.getReturnString(json, "data");
			if (StringUtils.isNotBlank(result)) {
				if ("1".equals(result)) {
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.error("充值密码异常");
			throw new UPPException("充值密码异常", e);
		}
		return flag;
	}

	/***
	 * 查询中交账户列表 等于0的账户数据，列为中交账户列表数据
	 * 
	 * @param requestParam
	 * @return PaginationResult
	 */
	@Override
	public PaginationResult<AccountDto> queryZJAccount(DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<AccountDto> result = new PaginationResult<AccountDto>();
		try {
			AccountExampleExtended exampleExtended = new AccountExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);
//			result = getManager().queryAccountByPage(exampleExtended);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_ZJ_BY_PAGE_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(requestParam, DefaultConfig.getValue("UPP_QUERY_ACCOUNT_BY_PAGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_ZJ_BY_PAGE: " + json);
			result = super.getPaginationResult(json, AccountDto.class);
//			JSONObject dataObj = super.processReturnResult(json);
//			if (dataObj != null) {
//				result = (PaginationResult<Account>)JSONObject.toBean(dataObj, PaginationResult.class);
//			}
			
		} catch (Exception e) {
			logger.error("查询中交账户列表页面异常");
			throw new UPPException("查询中交账户列表页面异常", e);
		}
		return result;
	}

	/***
	 * 导出普通账户列表数据
	 * 
	 * @param requestParam
	 * @return List<ManageAccountExcel>
	 */
	@Override
	public List<ManageAccountExcel> exportExcel(DynamicSqlParameter requestParam) throws UPPException {
		List<ManageAccountExcel> result = new ArrayList<ManageAccountExcel>();
		//插入查询条件，按时间，排序
		requestParam.setOrder("createTime");
		requestParam.setSort("desc");
		try {
			/*List<Account> list = new ArrayList<Account>();
			AccountExampleExtended exampleExtended = new AccountExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);
			// 0-中交;1-车主;2-货主
			exampleExtended.createCriteria().andAccountTypeNotEqualTo(PayDict.ACCOUNT_TYPE_CTFO);// 普通账户:车主/货主
			
//			list = getManager().queryAccount(exampleExtended);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_PARAM: " + JSONObject.fromObject(exampleExtended).toString());
			String json = super.sendRequest(exampleExtended, DefaultConfig.getValue("UPP_QUERY_ACCOUNT"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				list = (List<Account>)JSONObject.toBean(dataObj, List.class);
			}
			
			List<ManageAccountExcel> result = new ArrayList<ManageAccountExcel>();
			for (int i = 0; i < list.size(); i++) {
				ManageAccountExcel excel = new ManageAccountExcel();
				Account account = list.get(i);
				excel.setInsideAccountNo(account.getInsideAccountNo());
				excel.setOwnerLoginName(account.getOwnerLoginName());
				excel.setAccountType(account.getAccountType());
				excel.setTotalBalance(AmountUtil.getAmount(account.getTotalBalance()));
				excel.setUsableBalance(AmountUtil.getAmount(account.getUsableBalance()));
				excel.setFrozenBalance(AmountUtil.getAmount(account.getFrozenBalance()));
				excel.setMobileNo(account.getMobileNo());
				if (account.getCreateTime() != null && !"".equals(account.getCreateTime())) {
					String time = DateUtil.longToDate(account.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
					excel.setCreateTime(time);
				} else {
					excel.setCreateTime("");
				}
				excel.setAccountStatus(account.getAccountStatus());
				result.add(excel);
			}
			return result;*/
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_BY_PAGE_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(requestParam, DefaultConfig
					.getValue("UPP_QUERY_ACCOUNT_BY_PAGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_BY_PAGE: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			JSONArray jArray = (JSONArray)(( dataObj ).get(RESULT_DATA_NAME));
			List<AccountDto> list = new ArrayList();
			list = (List)JSONArray.toCollection(jArray, AccountDto.class); 
			for (int i = 0; i < list.size(); i++) {
				ManageAccountExcel excel = new ManageAccountExcel();
				AccountDto account = list.get(i);
				excel.setInsideAccountNo(account.getInsideAccountNo());
//				excel.setOwnerLoginName(account.getOwnerLoginName());
				excel.setAccountType(account.getAccountType());
				excel.setTotalBalance(account.getTotalBalance());
				excel.setUsableBalance(account.getUsableBalance());
				excel.setFrozenBalance(account.getFrozenBalance());
				excel.setMobileNo(account.getMobileNo());
				if (account.getCreateTime() != null && !"".equals(account.getCreateTime())) {
					excel.setCreateTime(account.getCreateTime());
				} else {
					excel.setCreateTime("");
				}
				excel.setAccountStatus(account.getAccountStatus());
				result.add(excel);
			}
		} catch (Exception e) {
			logger.error("导出普通账户列表数据异常");
			throw new UPPException("导出普通账户列表数据异常", e);
		}
		return result;
	}

	/***
	 * 导出中交账户列表数据
	 * 
	 * @param requestParam
	 * @return List<ZJAccountExcel>
	 */
	@Override
	public List<ZJAccountExcel> exportZJExcel(DynamicSqlParameter requestParam) throws UPPException {
		//TODO
		try {
			List<Account> list = new ArrayList<Account>();
			Map<String,String> map = new HashMap<String, String>();
			map.put("accountType", "0");
			requestParam.setEqual(map);
		/*	AccountExampleExtended exampleExtended = new AccountExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);
			// 0-中交;1-车主;2-货主
			exampleExtended.createCriteria().andAccountTypeEqualTo(PayDict.ACCOUNT_TYPE_CTFO);// 中交账户
*/			
//			list = getManager().queryAccount(exampleExtended);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_ZJ_BY_PAGE_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(requestParam, DefaultConfig.getValue("UPP_QUERY_ACCOUNT_BY_PAGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_ZJ_BY_PAGE: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			JSONArray jArray = (JSONArray)(( dataObj ).get(RESULT_DATA_NAME));
			list = (List)JSONArray.toCollection(jArray, Account.class); 
			List<ZJAccountExcel> result = new ArrayList<ZJAccountExcel>();
			for (int i = 0; i < list.size(); i++) {
				ZJAccountExcel excel = new ZJAccountExcel();
				Account account = list.get(i);
				excel.setInsideAccountNo(account.getInsideAccountNo());
				excel.setOwnerLoginName(account.getOwnerLoginName());
				excel.setAccountStatus(account.getAccountStatus());
				String totalBalance = AmountUtil.getAmount(account.getTotalBalance());
				excel.setTotalBalance(totalBalance);
				if (account.getCreateTime() != null && !"".equals(account.getCreateTime())) {
					String time = DateUtil.longToDate(account.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
					excel.setCreateTime(time);
				} else {
					excel.setCreateTime("");
				}

				result.add(excel);
			}
			return result;
		} catch (Exception e) {
			logger.error("导出中交账户列表数据异常");
			throw new UPPException("导出中交账户列表数据异常", e);
		}
	}

	@Override
	public void DelockAccount(String accountNo) throws UPPException {
		try {
//			getPaymentTradeManager().unlockAccount(accountNo);
			Map<String, String> accMap = new HashMap<String, String>();
			accMap.put("accountNo", accountNo);
			logger.info("UPP_HTTP_INTERFACE_UPP_UNLOCK_ACCOUNT_PARAM: " + accMap);
			String jsonResult = super.sendRequest(JSONObject.fromObject(accMap).toString(), 
					DefaultConfig.getValue("UPP_UNLOCK_ACCOUNT"));
			logger.info("UPP_HTTP_INTERFACE_UPP_UNLOCK_ACCOUNT: " + jsonResult);
			String result = super.getReturnString(jsonResult, "data");
			if (StringUtils.isBlank(result)) {
				throw new UPPException("http请求解锁账户异常");
			}
			
			
			//删除mogoDB下  所有超时的 账户支付密码错误次数记录
//			getICheckPwErrorTimes().removeAllTimeOut();
//			super.sendRequest(JSONObject.fromObject(new HashMap())
//					.toString(), DefaultConfig
//					.getValue("UPP_REMOVE_ALL_TIMEOUT"));
		
			//删除本用户记录 
//			Account account = manager.queryAccountInfo(accountNo);
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("accountNo", accountNo);
//			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_INFO_PARAM: " + map);
//			String json = super.sendRequest(map, DefaultConfig.getValue("UPP_QUERY_ACCOUNT_INFO"));
//			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_INFO: " + json);
//			
//			JSONObject dataObj = super.processReturnResult(json);
//			AccountDto account = null;
//			if (dataObj != null) {
//				account = (AccountDto)JSONObject.toBean(dataObj, AccountDto.class);
//			}
//			
//			if (account != null) {
//				String userId = account.getOwnerUserId();
				
//			getICheckPwErrorTimes().removeErrorTimesByUserId(userId);
//				Map<String, String> userMap = new HashMap<String, String>();
//				userMap.put("userId", userId);
//				super.sendRequest(JSONObject.fromObject(userMap)
//						.toString(), DefaultConfig
//						.getValue("UPP_REMOVE_ERROR_TIMES_BY_USERID"));
//			}
		} catch (Exception e) {
			logger.error("解锁账户异常", e);
			throw new UPPException("解锁账户异常");
		}
	};
	
	/**
	 * 检查参数是否合法
	 * @param uppAccountDto
	 * @return
	 * @throws UPPException
	 */
	private boolean checkParams(UPPAccountDto uppAccountDto) throws UPPException{
		if (uppAccountDto == null)
			throw new UPPException("参数不合法[对象为空]");	
		if (StringUtils.isBlank(uppAccountDto.getOwnerUserId()))
			throw new UPPException("参数不合法[用户ID为空]");	
		if (StringUtils.isBlank(uppAccountDto.getOwnerLoginName()))
			throw new UPPException("参数不合法[用户登陆名为空]");	
		if (StringUtils.isBlank(uppAccountDto.getMobileNo()))
			throw new UPPException("参数不合法[手机号码为空]");	
		if (uppAccountDto.getMobileNo().length()>11)
			throw new UPPException("参数不合法[手机号码长度大于11]");	
		if (StringUtils.isBlank(uppAccountDto.getPayPassword()))
			throw new UPPException("开户时传递参数不合法[对象null]");	
		return true;
	}
	/**
	 * 开户-模型参数转换
	 * @param account
	 * @param accountDTO
	 */
	private static void copyAccountProperties(Account account, UPPAccountDto accountDTO) {

		account.setAccountType((StringUtils.isBlank(accountDTO.getAccountType()))?PayDict.ACCOUNT_TYPE_CTFO:accountDTO.getAccountType());
		account.setAccountType(accountDTO.getAccountType());
		account.setOwnerUserId(accountDTO.getOwnerUserId());
		account.setOwnerLoginName(accountDTO.getOwnerLoginName());
		account.setPayPassword(accountDTO.getPayPassword());
		account.setMobileNo(accountDTO.getMobileNo());
		account.setVersion(0);
	}
	/***
	 * 充值检查订单模型
	 * @param orderDto
	 * @return
	 * @throws UPPException
	 */
	private String checkOrderDtoProperties(UPPDto orderDto) throws UPPException {
		try {
			if (orderDto instanceof UPPOrderRechargeDto)
				return this.checkRechargeOrder((UPPOrderRechargeDto) orderDto);
		} catch (Exception e) {
			logger.error("参数错误！", e);
			throw new UPPException("参数错误");
		}

		return "参数错误，［" + orderDto + "］找不到对应的子类";
	}
	/***
	 * 检查各参数非空
	 * @param orderDto
	 * @return
	 * @throws UPPException
	 */
	private String checkRechargeOrder(UPPOrderRechargeDto orderDto) throws UPPException {
		if (StringUtils.isBlank(orderDto.getAccountNo())) {
			return "账号不能为空";
		}
		if (StringUtils.isBlank(orderDto.getAmount())) {
			if (AmountUtil.getAmount(orderDto.getAmount()) <= 0)
				return "金额必须大于0";
			return "金额不能为空";
		}
		if (StringUtils.isBlank(orderDto.getClentType())) {
			if ("PC".equals(orderDto.getClentType()) || "MOBILE".equals(orderDto.getClentType()))
				return "终端必须是PC或MOBILE";
			return "终端不能为空";
		}
		if (StringUtils.isBlank(orderDto.getPayChannel())) {
			if ("NET".equals(orderDto.getPayChannel()))
				return "支付渠道必须是NET";
			return "支付渠道不能为空";
		}
		if (StringUtils.isBlank(orderDto.getStoreCode())) {
			return "商户编号不能为空";
		}
		if (StringUtils.isBlank(orderDto.getUserId())) {
			return "用户ID不能为空";
		}
		if (StringUtils.isBlank(orderDto.getBankCardCode())) {
			return "银行代码不能为空";
		}
		if (StringUtils.isBlank(orderDto.getBankCardType())) {
			return "银行卡类型不能为空";
		}
		if (StringUtils.isBlank(orderDto.getBankCardName())) {
			return "银行名称不能为空";
		}
		return CHECK_TRUE;
	}
	/***
	 * 转换充值订单模型
	 * @param orderDto
	 * @param orderInfo
	 * @throws UPPException
	 */
	private void copyProperties(UPPDto orderDto, OrderInfo orderInfo) throws UPPException {
		try {
			String amount = "";
			String identityType = "";
			if (orderDto instanceof UPPOrderRechargeDto) {
				UPPOrderRechargeDto rechargeOrder = (UPPOrderRechargeDto) orderDto;
				//collectMoneyUserId收款用户的ID，因为是充值交易，所以收款与付款为同一人（后面需要单独赋值）
				//orderType订单类型，前台没有传值，后面需要单独赋值。（后面需要单独赋值）
				//collectMoneyAccountNo 收款内部账户号，如果是充值交易付款和收款账户是同一个人（后面需要单独赋值）
				//identityType 标识类型:0=IMEI,1=MAC地址,(后面需要单独赋值)
				String[] ignoreProperties = { "id","orderNo","orderStatus","collectMoneyUserId","orderType","tradeExternalNo",
						"createSubareaTime","productDesc","clentId","userUa","identityType",
						"serviceProviderCode","orderAmount", "createTime", "createSubareaTime", "version" };
				BeanUtils.copyProperties(rechargeOrder, orderInfo, ignoreProperties);
				orderInfo.setOrderType(PayDict.PAY_TRADE_TYPE_RECHARGE); 
				identityType = rechargeOrder.getIdentityType();
				orderInfo.setIdentityType(StringUtils.isNotBlank(identityType)?Integer.parseInt(identityType):0);
				orderInfo.setCollectMoneyAccountNo(rechargeOrder.getAccountNo());
				amount = rechargeOrder.getAmount();
			}
			orderInfo.setOrderAmount(AmountUtil.getAmount(amount));// 金额

		} catch (Exception e) {
			logger.error("参数转换错误！", e);
			throw new UPPException("参数转换错误");
		}

	}
	@Override
	public Long accountBalance(String type) throws UPPException {
		Long balance = 0L;
//		try {
//			DynamicSqlParameter requestParam = new DynamicSqlParameter();
//			if(type.equals(PayDict.ACCOUNT_TYPE_CTFO)){
//				List<Account> list = new ArrayList<Account>();
//				AccountExampleExtended exampleExtended = new AccountExampleExtended();
//				Map<String,String> map = new HashMap<String,String>();
//				map.put("accountType", PayDict.ACCOUNT_TYPE_CTFO);
//				requestParam.setEqual(map);
//				Converter.paramToExampleExtended(requestParam, exampleExtended);
//				
////				list = getManager().queryAccount(exampleExtended);
//				logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_PARAM: " + JSONObject.fromObject(exampleExtended).toString());
//				String json = super.sendRequest(exampleExtended, DefaultConfig.getValue("UPP_QUERY_ACCOUNT"));
//				logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT: " + json);
////				JSONObject dataObj = super.processReturnResult(json);
//				JSONArray dataObj = super.getJSONArrayResult(json);
//				if (dataObj != null) {
//					list = (List<Account>)JSONArray.toCollection(dataObj, Account.class);
//				}
//				
//				for(Account account : list){
//					balance += account.getTotalBalance();
//				}
//			}else{
//				List<Account> list = new ArrayList<Account>();
//				AccountExampleExtended exampleExtended = new AccountExampleExtended();
//				Map<String,String> map = new HashMap<String,String>();
//				map.put("accountType", PayDict.ACCOUNT_TYPE_CTFO);
//				requestParam.setNotequal(map);
//				Converter.paramToExampleExtended(requestParam, exampleExtended);
//				
////				list = getManager().queryAccount(exampleExtended);
//				
//				String json = super.sendRequest(exampleExtended, DefaultConfig.getValue("UPP_QUERY_ACCOUNT"));
//				JSONObject dataObj = super.processReturnResult(json);
//				if (dataObj != null) {
//					list = (List<Account>)JSONObject.toBean(dataObj, List.class);
//				}
//				
//				for(Account account : list){
//					balance += account.getTotalBalance();
//				}
//			}
//		} catch (Exception e) {
//			logger.error("查询账户总余额异常！", e);
//			throw new UPPException("查询账户总余额失败！");
//		}
//		return balance;
		return balance;
	}
	
	@Override
	public boolean hasSecurityQuestion(String accountNo) throws UPPException {
		boolean flag = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountNo", accountNo);
		try {
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_SECURITY_PROBLEM_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map).toString(), 
						DefaultConfig.getValue("UPP_QUERY_SECURITY_PROBLEM"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_SECURITY_PROBLEM: " + json);
			String result = super.getReturnString(json, "data");
			
			if (StringUtils.isNotBlank(result)) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("重置安全问题异常！", e);
			throw new UPPException("重置安全问题异常！");
		}
		return flag;
	}
	
	@Override
	public boolean resetSecurityProblem(String accountNo) throws UPPException {
		boolean flag = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountNo", accountNo);
		try {
			logger.info("UPP_HTTP_INTERFACE_UPP_RESET_SECURITY_PROBLEM_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map).toString(), 
						DefaultConfig.getValue("UPP_RESET_SECURITY_PROBLEM"));
			logger.info("UPP_HTTP_INTERFACE_UPP_RESET_SECURITY_PROBLEM" + json);
			String result = super.getReturnString(json, "data");
			if (StringUtils.isNotBlank(result)) {
				if ("1".equals(result)) {
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.error("重置安全问题异常！", e);
			throw new UPPException("重置安全问题异常！");
		}
		return flag;
	}
	
	@Override
	public String modifyMobile(String accountNo, String mobileNo)
			throws UPPException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountNo", accountNo);
		map.put("mobileNo", mobileNo);
		
		try {
			logger.info("UPP_HTTP_INTERFACE_UPP_MODIFY_MOBILE_PARAM: " + JSONObject.fromObject(map).toString());
			String json = super.sendRequest(JSONObject.fromObject(map).toString(), 
					DefaultConfig.getValue("UPP_MODIFY_MOBILE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_MODIFY_MOBILE: " + json);
			return super.getReturnString(json, "data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public double getTotalCommonCountBalance(DynamicSqlParameter requestParam)
			throws UPPException {
		try {
			logger.info("UPP_HTTP_INTERFACE_UPP_ACCOUNT_SUM_BALANCE_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(requestParam, 
					DefaultConfig.getValue("UPP_ACCOUNT_SUM_BALANCE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_ACCOUNT_SUM_BALANCE: " + json);
			String amount = super.getReturnString(json, "totalBalance");
			if (StringUtils.isNotBlank(amount)) {
				return Double.parseDouble(amount);
			}
		} catch (Exception e) {
			logger.error("查询账户总余额异常", e);
			throw new UPPException("查询账户总余额异常");
		}
		return 0L;
	}
	@Override
	public void exportExcelNew(DynamicSqlParameter requestParam)
			throws UPPException {
		try{
			long aa = System.currentTimeMillis();
			
			String taskName = "普通账户列表下载";
			String countUrl = DefaultConfig.getValue("UPP_COUNT_ACCOUNT");
			String queryUrl = DefaultConfig.getValue("UPP_QUERY_ACCOUNT_BY_PAGE");
			super.runExportExcelTask(taskName, countUrl, queryUrl, requestParam, AccountDto.class, ManageAccountExcel.class);
		
			logger.info("---->>>导出用时:"+(System.currentTimeMillis()-aa));
			
		}catch(Exception e){
			logger.error("导出账户流水异常："+e.getLocalizedMessage(),e);
		}
		
	}
	@Override
	public List<ManageAccountExcel> copyTask(List<?> list) throws Exception {
		List<ManageAccountExcel> result = new ArrayList<ManageAccountExcel>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				ManageAccountExcel excel = new ManageAccountExcel();
				AccountDto account = new AccountDto();
				account = (AccountDto) list.get(i);
				// 科目
				String accountType = account.getAccountType();
				String accountStatus = account.getAccountStatus();
				String ownerUserNo = account.getOwnerUserNo();
				if("0".equals(accountType)){
					excel.setAccountType("中交账户");
				}else{
					excel.setAccountType("普通账户");
				}
				//账户状态
			/*	if(StringUtils.isNotEmpty(accountStatus)){
					if(accountStatus.equals(AccountDict.ACCOUNT_STATUS_INIT)){
						excel.setAccountStatus("未实名");
					}else if(accountStatus.equals(AccountDict.ACCOUNT_STATUS_NORMAL)){
						excel.setAccountStatus("正常");
					}else if(accountStatus.equals(AccountDict.ACCOUNT_STATUS_REVOKE)){
						excel.setAccountStatus("已注销");
					}else if(accountStatus.equals(AccountDict.ACCOUNT_STATUS_LOCKED)){
						excel.setAccountStatus("已锁定");
					}
				}*/
		//		excel.setAccountType(StringUtils.isNotBlank(accountType)?this.getCodeName("ORDER_SUBJECT", accountSubject):"");
				excel.setAccountStatus(StringUtils.isNotBlank(accountStatus)?this.getCodeName("ACCOUNT_STATUS", accountStatus):"");
				excel.setInsideAccountNo(account.getInsideAccountNo());
//				excel.setOwnerLoginName(account.getOwnerLoginName());
		//		excel.setAccountType(account.getAccountType());
				excel.setTotalBalance(account.getTotalBalance());
				excel.setUsableBalance(account.getUsableBalance());
				excel.setFrozenBalance(account.getFrozenBalance());
				excel.setMobileNo(account.getMobileNo());
				excel.setOwnerUserNo(StringUtils.isNotBlank(ownerUserNo)?ownerUserNo:"");
				if (account.getCreateTime() != null && !"".equals(account.getCreateTime())) {
					excel.setCreateTime(account.getCreateTime());
				} else {
					excel.setCreateTime("");
				}
				
				/*// 科目
				String accountSubject = obj.getOrderTypeLuc();
				excel.setAccountSubject(StringUtils.isNotBlank(accountSubject)?this.getCodeName("ORDER_SUBJECT", accountSubject):"");
				// 记账类型
				String bookaccountType = obj.getBookAccountType();
				excel.setBookaccountType(StringUtils.isNotBlank(bookaccountType)?this.getCodeName("BOOK_ACCOUNT_TYPE", bookaccountType.toUpperCase()):"");
				
				//交易金额（元）
				if(obj.getBookAccountMoney()!=null&& !"".equals(obj.getBookAccountMoney())){
					excel.setAccountMoney(obj.getBookAccountMoney());
				}else{
					excel.setAccountMoney("0.00");
				}
				//交易时间
				excel.setAccountTime(DateUtil.longToDate(Long.parseLong(obj.getAccountTime()), "yyyy-MM-dd HH:mm:ss"));
				//交易后总余额（元）
				if(obj.getBookCurrentMoney()!=null && !"".equals(obj.getBookCurrentMoney())){
					excel.setTotalBalance(obj.getBookCurrentMoney());
				}else{
					excel.setTotalBalance("0.00");
				}*/
				result.add(excel);
			}
		}
		return result;
	}
	@Override
	public String unfreezeAmount(
			UnfreezeAmountDto dto) throws UPPException {
		String result = Converter.OP_FAILED;
		try {
			logger.info("UPP_HTTP_INTERFACE_UPP_UNFREEZE_ORDER_AMOUNT: " + JSONObject.fromObject(dto).toString());
			
			String url = DefaultConfig.getValue("UPP_INTERFACE_URL") + DefaultConfig.getValue("UPP_UNFREEZE_ORDER_AMOUNT");
		
			String publickey = DefaultConfig.getPublicKey(DefaultConfig.getValue("INTERFACE_CODE_OLD"));
			
			String merchantcode = DefaultConfig.getValue("YP_MERCHANT_CODE");
			
			String privatekey = com.sinoiov.upp.config.DefaultConfig.getPrivateKey(merchantcode, "webApp");
				
			JSONObject jsonMap = JSONObject.fromObject(dto);
					
			String json = jsonMap.toString();
					
			json = HttpUtils.sendRequest(json, url, privatekey, publickey, merchantcode);
			
			logger.info("----->>>>>json:"+json);
			
			jsonMap = JSONObject.fromObject(json);
			if("1".equals(jsonMap.getString("result"))){
				result = "1";
			}else{
				result = "-1";
			}
		} catch (Exception e) {
		   	result = "-1";
			logger.error("解冻账户金额异常");
			throw new UPPException("解冻账户金额异常", e);
		}
		return result;
	}
	
}
