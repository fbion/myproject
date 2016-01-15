package com.sinoiov.upp.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.root.utils.EnvironmentUtil;
import com.sinoiov.upp.portal.controller.vo.AccountParam;
import com.sinoiov.upp.portal.controller.vo.CashierAccountDTO;
import com.sinoiov.upp.portal.service.AccountService;
import com.sinoiov.upp.portal.util.ProtalConvertUtils;

@Controller
@RequestMapping(value="account")
public class AccountController{
	private static final String List = null;

	@Autowired
	private AccountService accountService;
	
	private static Log logger = LogFactory.getLog(AccountController.class);
	/**
	 * 登录首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="pay.action")
	public String pay(HttpServletRequest request){
		String ownerLoginName = null;
		try {
			ownerLoginName = accountService.getUserMessage(request);
		} catch (Exception e) {
			logger.error("获取用户信息错误！", e);
		}
		return StringUtils.isNotBlank(ownerLoginName)?"pages/pay":"logout";
	}
	/**
	 * 再次跳转首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="payAgain.action")
	public String payAgain(HttpServletRequest request){
		String ownerLoginName = null;
		try {
			ownerLoginName = accountService.getUserMessage(request);
		} catch (Exception e) {
			logger.error("获取用户信息错误！", e);
		}
		return StringUtils.isNotBlank(ownerLoginName)?"redirect:/provingAccount.action":"logout";
	}
	/**
	 * 验证账户是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping(value="provingAccount.action")
	public String provingAccount(HttpServletRequest request){
		HttpSession session = request.getSession();
		String ownerUserId = null;
		String mobileNo = null;
		if(session.getAttribute("ownerUserId")!=null){
			ownerUserId = (String)session.getAttribute("ownerUserId");
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			session.setAttribute("merchantcode",ProtalConvertUtils.getValue("PORTAL_MERCHANT_CODE"));
		} catch (Exception e) {
			logger.error("获取商户编码错误！", e);
		}
		
		String judgeAccount = "";
		Map<String ,String> map = new HashMap<String,String>();
		map.put("userId", ownerUserId);
		
		try {
			resultMap = accountService.judgeAccount(map);
			judgeAccount = resultMap.get("accountNo");
		} catch (UPPException e) {
			logger.error("判断资金账户是否存在发生错误", e);
		}
		
		if(judgeAccount!=null&&judgeAccount!=""&&!ReturnCodeDict.ACCOUNT_NOT_EXIST.equals(judgeAccount)){
			mobileNo = resultMap.get("mobileNo");
			request.setAttribute("accountNo",judgeAccount);
			session.setAttribute("mobileNo",mobileNo);
		}else if(ReturnCodeDict.ACCOUNT_NOT_EXIST.equals(judgeAccount)){
			return "pages/account/open";
		}else{
			return "pages/bankrollAccount/accountError";
		}
		return "pages/bankrollAccount/accountHome";
	}
	/**
	 * 获取账户数据
	 * @param accountNo
	 * @return
	 */
	@RequestMapping(value="queryAccount.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Account queryAccount(@RequestParam(value="accountNo", required=true) String accountNo,
								  @RequestParam (value="random", required=true)String random){
		Account queryAccountInfo = null;
		try {
			if(accountNo==null||"null".equals(accountNo)){
				throw new UPPException("accountNo为空");
			}
			Map<String ,String> map = new HashMap<String,String>();
			map.put("accountNo", accountNo);
			queryAccountInfo = accountService.findAccountInfo(map);
		} catch (Exception e) {
			logger.error("获取资金账户信息发生错误", e);
		}
		return queryAccountInfo;
	}
	/**
	 * 验证账户密码是否是初始密码
	 * @param accountNo
	 * @return
	 */
	@RequestMapping(value="accountPasswordType.action")
	@ResponseBody
	public Map<String ,String> accountPasswordType(@RequestParam(value="accountNo", required=true) String accountNo){
		Map<String ,String> resultMap = new HashMap<String,String>();
		try {
			if(accountNo==null||"null".equals(accountNo)){
				throw new UPPException("accountNo为空");
			}
			Map<String ,String> map = new HashMap<String,String>();
			map.put("accountNo", accountNo);
			String povringPayPassword = accountService.povringPayPassword(map);
			resultMap.put("accountPasswordType", povringPayPassword);
		} catch (Exception e) {
			logger.error("判断资金账户密码状态发生异常", e);
		}
		return resultMap;
	}
	/**
	 * 获取账户流水
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryAccountCurrent.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String queryAccountCurrent(HttpServletRequest request){
		DynamicSqlParameter requestParam = null;
		String json = "";
		try {
			requestParam = accountService.voluationParam(request);
			json = accountService.quaryAccountCurrent(requestParam);
		} catch (Exception e) {
			logger.error("获取资金账户流水信息发生错误", e);
		}
		return json;
	}
	/**
	 * 通过Id获取账户流水
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getAccountCurrent.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getAccountCurrent(@RequestParam(value="id", required=true) String id){
		
		String json = "";
		DynamicSqlParameter requestParam = new DynamicSqlParameter();
		requestParam.setPage(0);
		requestParam.setRows(10);
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		requestParam.setEqual(map);
		try {
			json = accountService.quaryAccountCurrent(requestParam);
		} catch (Exception e) {
			logger.error("获取资金账户流水信息发生错误", e);
		}
		return json;
	}
	/**
	 * 跳转到跟多页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="AccountCurrentList.action")
	public String AccountCurrentList(HttpServletRequest request){
		return "pages/bankrollAccount/accountCurrentList";
	}
	/**
	 * 跳转到详情页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="accountCurrentDetail.action")
	public String accountCurrentDetailPage(HttpServletRequest request){
		request.setAttribute("id",(String)request.getParameter("id"));
		return "pages/bankrollAccount/accountCurrentDetailPage";
	}
	/**
	 * 跳转到开户页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="createAccountPage.action")
	public String createAccountPage(HttpServletRequest request){
		String userId = request.getParameter("userId");
		String ownerLoginName = request.getParameter("ownerLoginName");
		request.setAttribute("userId",userId);
		request.setAttribute("ownerLoginName",ownerLoginName);
		try {
			request.setAttribute("merchantcode",ProtalConvertUtils.getValue("PORTAL_MERCHANT_CODE"));
		} catch (Exception e) {
			logger.error("获取商户编码错误！", e);
		}
		return "pages/account/open";
	}
	/**
	 * 创建一个账户
	 * @param request
	 * @param accountParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createAccount.action", method = RequestMethod.POST)
	public String createAccount(HttpServletRequest request, AccountParam accountParam){
		HttpSession session = request.getSession();
		String imgCheckCode = (String)request.getSession().getAttribute("imgCheckCode");
		CashierAccountDTO dto = (CashierAccountDTO) session.getAttribute("accountDto");
		if(imgCheckCode==null||!imgCheckCode.equals(accountParam.getImgCheckCode().toUpperCase())){
			return "-2";
		}
		String judgeAccount="";
		//判断资金账户是否存在
		try {
			Map<String ,String> map = new HashMap<String,String>();
			map.put("userId", (String)session.getAttribute("ownerUserId"));
			Map<String, String> resultMap = accountService.judgeAccount(map);
			judgeAccount = resultMap.get("accountNo");
		} catch (UPPException e) {
			logger.error("判断资金账户是否存在发生错误", e);
		}
		if(judgeAccount!=null&&judgeAccount!=""&&judgeAccount!="null"&&!ReturnCodeDict.ACCOUNT_NOT_EXIST.equals(judgeAccount)){
			return "-4";
		}else if(ReturnCodeDict.ACCOUNT_CREATE_R.equals(judgeAccount)){
			return "-5";
		}
		try {
			
			Map<String ,String> map = new HashMap<String,String>();
			map.put("ownerUserId", (String)session.getAttribute("ownerUserId"));
			map.put("ownerLoginName", (String)session.getAttribute("ownerLoginName"));
			map.put("MD5", accountParam.getPayPassword());
			map.put("mobileNo", accountParam.getMobileNo());
			map.put("smsCode", accountParam.getSmsCheckCode());
			map.put("ownerUserNo", dto.getOwnerUserNo());
			map.put("applyPersonPost", dto.getApplyPersonPost());
			judgeAccount = accountService.openAnAccount(map);
		} catch (UPPException e) {
			logger.error("创建资金账户发生错误", e);
		}
		
		if(!"".equals(judgeAccount)&&!ReturnCodeDict.MOBILE_NO_SMSCODE.equals(judgeAccount)&&!ReturnCodeDict.ACCOUNT_CREATE_R.equals(judgeAccount)){
			return "1";
		}else if(ReturnCodeDict.MOBILE_NO_SMSCODE.equals(judgeAccount)){
			return "-3";
		}else if(ReturnCodeDict.ACCOUNT_CREATE_R.equals(judgeAccount)){
			return "-5";
		}else{
			return "-1";
		}
	}
	/**
	 * 修改备注方法
	 * @param id
	 * @return
	 */
	@RequestMapping(value="updateRemarks.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String updateRemarksById(@RequestParam(value="id", required=true) String id,@RequestParam(value="remarks", required=true) String remarks){
		String result = "";
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("remarks", remarks);
		try {
			result = accountService.updateRemarksById(map);
		} catch (UPPException e) {
			logger.error("修改订单备注异常！", e);
		}
		return result;
	}
	/**
	 * 获取备注方法
	 * @param id
	 * @return
	 */
	@RequestMapping(value="findRemarks.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String,String> findRemarksById(@RequestParam(value="id", required=true) String id){
		Map<String,String> result=new HashMap<String, String>();
		try {
			Map findRemarksById = accountService.findRemarksById(id);
			result.put("remarks", (String)findRemarksById.get("remarks"));
		} catch (UPPException e) {
			logger.error("获取订单备注异常！", e);
		}
		return result;
	}
	/**
	 * 跳转至安全设置页面
	 * @param
	 * @return
	 */
	@RequestMapping(value="safetySet.action")
	public String safetySet(){
		return "pages/paymentPassword/safetySet";
	}
	/**
	 * 验证密保问题是否设置和密保问题数量
	 * @param accountNo
	 * @return
	 */
	@RequestMapping(value="judgePaymentPwdQuery.action")
	@ResponseBody
	public Map<String ,String> judgePaymentPwdQuery(HttpServletRequest request){
		Map<String ,String> resultMap = new HashMap<String,String>();
		HttpSession session = request.getSession();
		Map<String ,String> map = new HashMap<String,String>();
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		try {
			resultMap = accountService.judgePaymentPwdQuery(map);
		} catch (Exception e) {
			logger.error("判断资金账户密保状态发生异常", e);
		}
		return resultMap;
	}
	
	
}
