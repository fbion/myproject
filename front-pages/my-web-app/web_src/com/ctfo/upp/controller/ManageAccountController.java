package com.ctfo.upp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.base.dao.beans.UPPlatform;
import com.ctfo.upp.bean.AccountRiskRule;
import com.ctfo.upp.bean.UnfreezeAmountDto;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.MD5Util;
import com.ctfo.upp.root.utils.SmsSender;
import com.ctfo.upp.service.account.IAccountRiskRuleService;
import com.ctfo.upp.service.account.IAccountService;
import com.ctfo.upp.service.sms.SmsService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.PasswordUtils;
import com.sinoiov.upp.service.dto.AccountDto;

/***
* 类描述：普通账户列表Controller
* @author：liuguozhong  
* @date：2015年1月16日下午2:38:58 
* @version 1.0
* @since JDK1.6
*/ 
@Scope("prototype")
@Controller
@RequestMapping(value = "/account")
public class ManageAccountController extends BaseController{
	
	private static Log logger = LogFactory.getLog(ManageAccountController.class);
	
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IAccountRiskRuleService accountRiskRuleService;
	
	@Autowired
	private SmsService smsService;
	
	private PaginationResult<Account> result = new PaginationResult<Account>();
	
	private ManageAccountController(){
		model = new AccountRiskRule();
	}
	
	/**
	 * 根据账户绑定额度风控规则
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value="/saveAccountRiskRule.action" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> saveAccountRiskRule(HttpServletRequest request){
		try {
			String type = request.getParameter("type");
			if (StringUtils.isNotBlank(type) && "modify".equals(type)) {
				AccountRiskRule rule = (AccountRiskRule)model;
				if (StringUtils.isBlank(rule.getId())) {
					result = null;
					throw new IllegalArgumentException("非法请求");
				} else {
					this.accountRiskRuleService.delRiskRule(rule.getId());
				}
			}
			this.accountRiskRuleService.saveRiskRule((AccountRiskRule)model);
			return result;
		} catch (Exception e) {
			logger.error("根据账户绑定额度风控规则异常", e);
			result = null;
		}		
		return result;	
	}
	
	@RequestMapping(value="/queryRiskRuleByAccNo.action" ,produces = "application/json")
	@ResponseBody
	public AccountRiskRule queryRiskRuleByAccNo(DynamicSqlParameter requestParam){
		try {
			return this.accountRiskRuleService.queryRiskRuleByAccNo(((AccountRiskRule)model).getAcc_no());
		} catch (Exception e) {
			logger.error("根据账户绑定额度风控规则异常", e);
		}		
		return null;	
	}
	
	
	/**
	 * 查询列表
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value="/queryAccount" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<Account> queryList(DynamicSqlParameter requestParam){
		try {
			result = accountService.queryAccountByPage(requestParam);
		} catch (UPPException e) {
			result.setMessage("分页查询线下充值信息异常!");
			logger.error("分页查询线下充值信息异常!",e);
		}		
		return result;	
	}
	
	@RequestMapping(value="/modifyMobile.action" ,produces = "application/json")
	@ResponseBody
	public String modifyMobile(@RequestParam("mobileNo") String mobileNo, 
				@RequestParam("accountNo") String accountNo){
		try {
			return this.accountService.modifyMobile(accountNo, mobileNo);
		} catch (Exception e) {
			logger.error("根据账户绑定额度风控规则异常", e);
		}		
		return null;	
	}
	/**
	 * 查询普通账户总余额
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value="/queryAccountBalance.action" ,produces = "application/json")
	@ResponseBody
	public double queryAccountBalance(DynamicSqlParameter requestParam){
		Map<String,String> equalMap = null;
		if (requestParam.getEqual() == null) {
			equalMap = new HashMap<String,String>();
		} else {
			equalMap = requestParam.getEqual();
		}
		equalMap.put("accountType", PayDict.ACCOUNT_TYPE_GOODS_OWNER);
//		if(requestParam.getNotequal()==null){
//			map.put("accountType", PayDict.ACCOUNT_TYPE_GOODS_OWNER);
////			map.put("accountType", PayDict.ACCOUNT_TYPE_CTFO);
////			requestParam.setNotequal(map);
//			requestParam.setEqual(map);
//		}
		
		double balance = 0;
//		latest code by lipeng
		try {
//			balance = accountService.accountBalance(requestParam);
			balance = accountService.getTotalCommonCountBalance(requestParam);
		} catch (UPPException e) {
			balance = -1L;
			logger.error("查询普通账户总余额异常!",e);
		}		
		return balance;	
	}
	@RequestMapping(value = "/getDetailById.action", produces = "application/json")
	@ResponseBody
	public AccountDto getMerchantById(@RequestParam("id") String id) {
		AccountDto acc = null;
		try {
			acc = accountService.getAccountById(id);
		} catch (UPPException e) {
			logger.error("通过ID查询商户信息异常", e);
		}
		return acc;
	}
	
	/**
	 * 注销
	 * @param accountNo
	 * @return
	 */
	@RequestMapping(value = "/revokeAccount", produces = "application/json")
	@ResponseBody
	public PaginationResult<Account> revokeAccount(@RequestParam("accountNo") String accountNo){
		PaginationResult<Account> result = new PaginationResult<Account>();
		try {
			accountService.revokeAccount(accountNo);
			result.setMessage("账户注销成功");
		} catch (Exception e) {
			logger.error("账户注销异常",e);
		}
		return result;
	}
	/***
	 * 锁定
	 * @param accountNo
	 * @return
	 */
	@RequestMapping(value = "/lockAccount", produces = "application/json")
	@ResponseBody
	public PaginationResult<Account> lockAccount(@RequestParam("accountNo") String accountNo){
		PaginationResult<Account> result = new PaginationResult<Account>();
		try {
			accountService.lockAccount(accountNo);
			result.setMessage("账户锁定成功");
		} catch (Exception e) {
			logger.error("账户锁定异常",e);
		}
		return result;
	}
	/***
	 * 解锁
	 * @param accountNo
	 * @return
	 */
	@RequestMapping(value = "/DelockAccount", produces = "application/json")
	@ResponseBody
	public PaginationResult<Account> DelockAccount(@RequestParam("accountNo") String accountNo){
		PaginationResult<Account> result = new PaginationResult<Account>();
		try {
			accountService.DelockAccount(accountNo);
			result.setMessage("账户解锁成功");
		} catch (Exception e) {
			logger.error("账户解锁异常",e);
		}
		return result;
	}
	/***
	 * 解冻
	 * @param accountNo
	 * @return
	 */
	@RequestMapping(value = "/unfreezeAmount", produces = "application/json")
	@ResponseBody
	public String unfreezeAmount(UnfreezeAmountDto dto){
		String result = Converter.OP_FAILED;
		try {
			result = accountService.unfreezeAmount(dto);
		} catch (UPPException e) {
			logger.error("解冻账户金额异常",e);
		}
		return result;
	}
	/***
	 * 重置密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changePWD",produces = "application/json")
	@ResponseBody
	public String changePWD(@RequestParam("accountNo") String insideAccountNo,
			@RequestParam("mobileNo") String mobileNo){
		String newPassword="";
		try {
			newPassword=String.valueOf(PasswordUtils.resetRandomPassword());
			List<String> contents =new ArrayList<String>();
			contents.add(newPassword);
//			smsService.sendShortMessByAccountNo(insideAccountNo, "tyzfpt1003", contents);
			String md5 = MD5Util.getEncryptedPwd(newPassword);
			boolean flag = accountService.resetPassword(insideAccountNo, md5);
			if (flag) {
				logger.info("WEB_changePWD_SEND_MSG: " + insideAccountNo);
				try {
//					smsService.sendShortMessByAccountNo(insideAccountNo, "tyzfpt1003", contents);
					SmsSender.getInstance().sendSmsByTemplate(mobileNo, "tyzfpt1003", contents);
				} catch (Exception e) {
					logger.error("重置密码成功后发送短信异常",e);
				}
			}
		} catch (Exception e) {
			logger.error("重置密码异常",e);
		}
		return newPassword;
	}
	
	/***
	 * 查询是否设置安全问题
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querySecurityQuestion",produces = "application/json")
	@ResponseBody
	public String querySecurityQuestion(@RequestParam("accountNo") String accountNo){
		
		try {
			String result = this.accountService.hasSecurityQuestion(accountNo) ? "1" : "0";
			return result;
		} catch (UPPException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/***
	 * 重置安全问题
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resetSecurityProblem",produces = "application/json")
	@ResponseBody
	public String resetSecurityProblem(@RequestParam("accountNo") String accountNo){
		
		try {
			return this.accountService.resetSecurityProblem(accountNo) ? "1" : "0";
		} catch (UPPException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/***
	 * 账户冻结时，检查冻结的金额是否正确
	 */
	@RequestMapping(value = "/check",produces = "application/json")
	@ResponseBody
	public String check(@RequestParam("accountNo") String insideAccountNo,@RequestParam("frozen") String frozen, @RequestParam("available") String available ){
		String newPassword="false";
		/*try {
			newPassword=String.valueOf(PasswordUtils.resetRandomPassword());
			List<String> contents =new ArrayList<String>();
			contents.add(newPassword);
			smsService.sendShortMessByAccountNo(insideAccountNo, "tyzfpt1003", contents);
			String md5 = MD5Util.getEncryptedPwd(MD5Util.getEncryptedPwd(newPassword));
			accountService.resetPassword(insideAccountNo,md5);
		} catch (Exception e) {
			logger.error("重置密码异常",e);
		}*/
		return newPassword;
	}
	/**
	 * 通过条件导出EXCEL
	 * @param requestParam
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/exportExcelAll", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(DynamicSqlParameter requestParam){
		try {
			//添加排序
			requestParam.setOrder("createTime");
			requestParam.setSort("desc");
			accountService.exportExcelNew(requestParam);
		//	generalAccountTradeService.exportExcel(requestParam);
		} catch (Exception e) {
			logger.error("导出普通账户列表EXCEL异常",e);
		}
	}
	/*public void exportExcel(DynamicSqlParameter requestParam){
		try {
			response.setDateHeader("Expires", 0);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String encodedfileName = new String("普通账户数据".getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");
			OutputStream out = response.getOutputStream();
			ExportExcel<ManageAccountExcel> exp = new ExportExcel<ManageAccountExcel>();
			List<ExpObj> expObjs = ExcelUtil.getExpObjs(ManageAccountExcel.class, 0, "EXP");
			// 定义的方法
			List<Method> methods = new ArrayList<Method>(expObjs.size());
			// 定义执行方法的对象
			List<Object> objs = new ArrayList<Object>(expObjs.size());
			List<ManageAccountExcel> result = new ArrayList<ManageAccountExcel>();
			result = accountService.exportExcel(requestParam);
			for(ManageAccountExcel excel : result){
				//账户类型
				excel.setAccountType("普通");
					
				//账户状态
				if(StringUtils.isNotEmpty(excel.getAccountStatus())){
					String status = excel.getAccountStatus();
					if(status.equals(AccountDict.ACCOUNT_STATUS_INIT)){
						excel.setAccountStatus("未实名");
					}else if(status.equals(AccountDict.ACCOUNT_STATUS_NORMAL)){
						excel.setAccountStatus("正常");
					}else if(status.equals(AccountDict.ACCOUNT_STATUS_REVOKE)){
						excel.setAccountStatus("已注销");
					}else if(status.equals(AccountDict.ACCOUNT_STATUS_LOCKED)){
						excel.setAccountStatus("已锁定");
					}
				}
			}
			for (int i = 0; i < expObjs.size(); i++) {
				objs.add(null);
				methods.add(null);
			}
			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
			exp.singleExportExcel(result, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("导出账户EXCEL异常",e);
		}
	}*/
}