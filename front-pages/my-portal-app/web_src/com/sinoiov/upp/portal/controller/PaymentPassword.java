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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.portal.service.IPaymentPassword;
import com.sinoiov.upp.portal.utils.RegUtil;

@Controller
@RequestMapping(value="paymentPassword")
public class PaymentPassword {
	@Autowired
	private IPaymentPassword paymentPassword;
	
	private static Log logger = LogFactory.getLog(PaymentPassword.class);
	
	/**
	 * wjg
	 * 修改支付密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="changePayPwd.action")
	@ResponseBody
	public String changePayPwd(HttpServletRequest request){
		String result="-1";
		String oldMD5 = request.getParameter("oldPayPwd");
		String newMD5 = request.getParameter("newPayPwd");
		if(newMD5==null||newMD5==""){
			return "-2";
		}
		HttpSession session = request.getSession();
		Map<String, String>  map= new HashMap<String, String>();
		map.put("securityTicket", (String)session.getAttribute("tokenId"));
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("oldMD5", oldMD5);
		map.put("newMD5", newMD5);
		try {
			result = paymentPassword.changePayPwd(map);
		} catch (UPPException e) {
			logger.error("修改账户支付密码异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 设置交易密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="setPayPwd.action")
	@ResponseBody
	public String setPayPwd(HttpServletRequest request){
		String result = "-1";
		String newMD5 = request.getParameter("newPayPwd");
		HttpSession session = request.getSession();
		Map<String, String>  map= new HashMap<String, String>();
		map.put("securityTicket", (String)session.getAttribute("tokenId"));
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("MD5", newMD5);
		try {
			result = paymentPassword.setPayPwd(map);
		} catch (UPPException e) {
			logger.error("设置账户支付密码异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 添加密码保护
	 * @return
	 */
	@RequestMapping(value="addTryptoguard.action")
	@ResponseBody
	public String changeTryptoguard(HttpServletRequest request){
		String result = "-1";
		String queryOne = request.getParameter("queryOne");
		String queryTwo = request.getParameter("queryTwo");
		String queryThree = request.getParameter("queryThree");
		String answerOne = request.getParameter("answerOne");
		String answerTwo = request.getParameter("answerTwo");
		String answerThree = request.getParameter("answerThree");
		if(StringUtils.isBlank(answerOne)||!RegUtil.iscryptoguard(answerOne)){
			return "-2";
		}
		if(StringUtils.isBlank(queryOne)){
			return "-2";
		}
		if(StringUtils.isBlank(answerTwo)||!RegUtil.iscryptoguard(answerTwo)){
			return "-2";
		}
		if(StringUtils.isBlank(queryTwo)){
			return "-2";
		}
		if(StringUtils.isBlank(answerThree)||!RegUtil.iscryptoguard(answerThree)){
			return "-2";
		}
		if(StringUtils.isBlank(queryThree)){
			return "-2";
		}
		
		HttpSession session = request.getSession();
		Map<String, String>  map= new HashMap<String, String>();
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("securityTicket", (String)session.getAttribute("tokenId"));
		map.put("securityQuestion1",queryOne);
		map.put("securityQuestion2",queryTwo);
		map.put("securityQuestion3",queryThree);
		map.put("securityAnswer1",answerOne);
		map.put("securityAnswer2",answerTwo);
		map.put("securityAnswer3",answerThree);
		try {
			result = paymentPassword.addTryptoguard(map);
		} catch (UPPException e) {
			logger.error("添加交易密码异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 修改密码保护
	 * @return
	 */
	@RequestMapping(value="changeTryptoguard.action")
	@ResponseBody
	public String addTryptoguard(HttpServletRequest request){
		String result = "-1";
		String queryOne = request.getParameter("queryOne");
		String queryTwo = request.getParameter("queryTwo");
		String queryThree = request.getParameter("queryThree");
		String answerOne = request.getParameter("answerOne");
		String answerTwo = request.getParameter("answerTwo");
		String answerThree = request.getParameter("answerThree");
		if(StringUtils.isBlank(queryOne)&&StringUtils.isBlank(answerOne)&&
				StringUtils.isBlank(queryTwo)&&StringUtils.isBlank(answerTwo)&&
				StringUtils.isBlank(queryThree)&&StringUtils.isBlank(answerThree)){
			return "-2";
		}
		Map<String, String>  map= new HashMap<String, String>();
		if(StringUtils.isNotBlank(queryOne)&&StringUtils.isNotBlank(answerOne)&&RegUtil.iscryptoguard(answerOne)){
			map.put("securityQuestion1",queryOne);
			map.put("securityAnswer1",answerOne);
		}else{
			return "-2";
		}
		if(StringUtils.isNotBlank(queryTwo)&&StringUtils.isNotBlank(answerTwo)&&RegUtil.iscryptoguard(answerTwo)){
			map.put("securityQuestion2",queryTwo);
			map.put("securityAnswer2",answerTwo);
		}else{
			return "-2";
		}
		if(StringUtils.isNotBlank(queryThree)&&StringUtils.isNotBlank(answerThree)&&RegUtil.iscryptoguard(answerThree)){
			map.put("securityQuestion3",queryThree);
			map.put("securityAnswer3",answerThree);
		}else{
			return "-2";
		}
		HttpSession session = request.getSession();
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("securityTicket", (String)session.getAttribute("tokenId"));
		try {
			result = paymentPassword.changeTryptoguard(map);
		} catch (UPPException e) {
			logger.error("修改交易密码异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 跳转至修改绑定手机首页
	 * @return
	 */
	@RequestMapping(value="chengeMobileNoHomePage.action")
	public String chengeMobileNoHomePage(){
		return "pages/paymentPassword/changeMobileNoHome";
	}
	/**
	 * wjg
	 * 跳转至修改绑定手机页面
	 * @return
	 */
	@RequestMapping(value="chengeMobileNoPage.action")
	public String chengeMobileNoPage(){
		return "pages/paymentPassword/changeMobileNo";
	}
	
	/**
	 * wjg
	 * 跳转至资金账户密码首页
	 * @return
	 */
	@RequestMapping(value="resetPayPwdHomePage.action")
	public String resetPayPwdHomePage(){
		return "pages/paymentPassword/payPwdHome";
	}
	/**
	 * wjg
	 * 跳转至资金账户密码修改页
	 * @return
	 */
	@RequestMapping(value="changePayPwdPage.action")
	public String changePayPwdPage(){
		return "pages/paymentPassword/changePayPwd";
	}
	/**
	 * wjg
	 * 跳转至资金账户密码重置页
	 * @return
	 */
	@RequestMapping(value="resetPayPwdPage.action")
	public String resetPayPwdPage(){
		return "pages/paymentPassword/resetPayPwd";
	}
	/**
	 * wjg
	 * 跳转至资金账户密码设置页
	 * @return
	 */
	@RequestMapping(value="setPayPwdPage.action")
	public String setPayPwdPage(){
		return "pages/paymentPassword/setPayPwd";
	}
	/**
	 * wjg
	 * 跳转至修改密码保护问题首页
	 * @return
	 */
	@RequestMapping(value="changeCryptoguardHomePage.action")
	public String changeCryptoguardHomePage(){
		return "pages/paymentPassword/changeCryptoguardHome";
	}
	/**
	 * wjg
	 * 跳转至密码保护问题修改页
	 * @return
	 */
	@RequestMapping(value="changeCryptoguardPage.action")
	public String changeCryptoguardPage(){
		return "pages/paymentPassword/changeCryptoguard";
	}
	/**
	 * wjg
	 * 跳转至设置密码保护问题首页
	 * @return
	 */
	@RequestMapping(value="setCryptoguardHomePage.action")
	public String setCryptoguardHomePage(){
		return "pages/paymentPassword/setCryptoguardHome";
	}
	/**
	 * wjg
	 * 跳转至密码保护问题设置页
	 * @return
	 */
	@RequestMapping(value="setCryptoguardPage.action")
	public String setCryptoguardPage(){
		return "pages/paymentPassword/setCryptoguard";
	}
	/**
	 * wjg
	 * 验证密保问题是否正确
	 * @return
	 */
	@RequestMapping(value="cryptoguardProving.action")
	@ResponseBody
	public String cryptoguardProving(HttpServletRequest request){
		String result = "-1";
		String cryptoguardQuery = request.getParameter("cryptoguardQuery");
		String cryptoguardAnswer = request.getParameter("cryptoguardAnswer");
		if(cryptoguardAnswer==null||cryptoguardAnswer==""||!RegUtil.iscryptoguard(cryptoguardAnswer)){
			return "-5";
		}
		if(cryptoguardQuery==null||cryptoguardQuery==""){
			return "-6";
		}
		HttpSession session = request.getSession();
		Map<String, String>  map= new HashMap<String, String>();
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("securityQuestion", cryptoguardQuery);
		map.put("securityAnswer", cryptoguardAnswer);
		try {
			result = paymentPassword.validate(map,request);
		} catch (UPPException e) {
			logger.error("验证密保问题答案异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 验证支付密码和密保问题是否正确（没有短信验证码）
	 * @return
	 */
	@RequestMapping(value="provingPayPwdAndCryptoguard.action")
	@ResponseBody
	public String provingPayPwdAndCryptoguard(HttpServletRequest request){
		String result= "-1";
		String cryptoguardQuery = request.getParameter("queryOne");
		String cryptoguardAnswer = request.getParameter("answerOne");
		String payPwd = request.getParameter("payPwd");
		if(payPwd==null||payPwd==""){
			return "-3";
		}
		if(cryptoguardAnswer==null||cryptoguardAnswer==""||!RegUtil.iscryptoguard(cryptoguardAnswer)){
			return "-5";
		}
		if(cryptoguardQuery==null||cryptoguardQuery==""){
			return "-6";
		}
		HttpSession session = request.getSession();
		Map<String, String>  map= new HashMap<String, String>();
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("securityQuestion", cryptoguardQuery);
		map.put("securityAnswer", cryptoguardAnswer);
		map.put("payPassword", payPwd);
		try {
			result = paymentPassword.validate(map,request);
		} catch (UPPException e) {
			logger.error("验证密保问题与支付密码异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 验证支付密码是否正确
	 * @return
	 */
	@RequestMapping(value="provingPayPwd.action")
	@ResponseBody
	public String provingPayPwd(HttpServletRequest request){
		String result = "-1";
		String noteProvingText = request.getParameter("mobileCode");
		String MD5 = request.getParameter("payPwd");
		String imgProvingText=request.getParameter("imgProvingText");
		if(noteProvingText==null||noteProvingText==""||!RegUtil.isMobileCode(noteProvingText)){
			return "-2";
		}
		if(MD5==null||MD5==""){
			return "-3";
		}
		HttpSession session = request.getSession();
		String imgCheckCode = (String)session.getAttribute("imgCheckCode");
		if(imgProvingText==null||!imgProvingText.toUpperCase().equals(imgCheckCode)){
			return "-7";
		}
		Map<String, String>  map= new HashMap<String, String>();
		map.put("mobileNo", (String)session.getAttribute("mobileNo"));
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("smsCode",noteProvingText);
		map.put("payPassword", MD5);
		try {
			result = paymentPassword.validate(map,request);
		} catch (UPPException e) {
			logger.error("设置账户支付密码异常！", e);
		}
		return result;
	}
	/**
	 * 修改绑定手机号码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="updateMobileNo.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String updateMobileNoByAccountNo(HttpServletRequest request){
		String result = null;
		String newMobileNo = request.getParameter("newMobileNo");
		String mobileCode = request.getParameter("mobileCode");
		String imgProvingText=request.getParameter("imgProvingText");
		if(newMobileNo==null||newMobileNo==""||!RegUtil.isMobileNO(newMobileNo)){
			return "-3";
		}
		if(mobileCode==null||mobileCode==""||!RegUtil.isMobileCode(mobileCode)){
			return "-2";
		}
		HttpSession session = request.getSession();
		String imgCheckCode = (String)session.getAttribute("imgCheckCode");
		if(imgProvingText==null||!imgProvingText.toUpperCase().equals(imgCheckCode)){
			return "-7";
		}
		Map<String, String>  map= new HashMap<String, String>();
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("securityTicket", (String)session.getAttribute("tokenId"));
		map.put("mobileNo", newMobileNo);
		map.put("smsCode",mobileCode);
		try {
			result = paymentPassword.updateMobileNoByAccountNo(map);
		} catch (UPPException e) {
			logger.error("修改绑定手机号码异常！", e);
		}
		return result;
	}
	/**
	 * 验证短信验证码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="provingMobileCode.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String provingMobileCode(HttpServletRequest request){
		String result= "-1";
		String mobileCode = request.getParameter("mobileCode");
		String imgProvingText=request.getParameter("imgProvingText");
		if(mobileCode==null||mobileCode==""||!RegUtil.isMobileCode(mobileCode)){
			return "-2";
		}
		HttpSession session = request.getSession();
		String imgCheckCode = (String)session.getAttribute("imgCheckCode");
		if(imgProvingText==null||!imgProvingText.toUpperCase().equals(imgCheckCode)){
			return "-7";
		}
		Map<String, String>  map= new HashMap<String, String>();
		map.put("mobileNo", (String)session.getAttribute("mobileNo"));
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		map.put("smsCode",mobileCode);
		try {
			result = paymentPassword.validate(map,request);
		} catch (UPPException e) {
			logger.error("验证短信验证码异常！", e);
		}
		return result;
	}
	/**
	 * 获取密保信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="getCryptoguard.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> getCryptoguard(HttpServletRequest request){
		Map<String, String> resultMap=new HashMap<String, String>();
		HttpSession session = request.getSession();
		Map<String, String>  map= new HashMap<String, String>();
		map.put("accountNo", (String)session.getAttribute("accountNo"));
		String cryptoguard = "-1";
		try {
			cryptoguard = paymentPassword.getCryptoguard(map);
		} catch (UPPException e) {
			logger.error("获取密保信息异常！", e);
		}
		if(cryptoguard!=null&&cryptoguard!="-1"){
			String[] split = cryptoguard.split(",");
			Map<String, String> cryptoguardQueryCode = this.getCryptoguardQueryCode();
			resultMap.put(split[0], cryptoguardQueryCode.get(split[0]));
			resultMap.put(split[1], cryptoguardQueryCode.get(split[1]));
			resultMap.put(split[2], cryptoguardQueryCode.get(split[2]));
		}
		return resultMap;
	}
	/**
	 * 获取密保问题码表信息
	 * @param
	 * @return
	 */
	@RequestMapping(value="getCryptoguardQueryCode.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> getCryptoguardQueryCode(){
		Map<String, String> resultMap=null;
		try {
			resultMap = paymentPassword.getCryptoguardQueryCode();
		} catch (UPPException e) {
			logger.error("获取密保问题码表信息异常！", e);
		}
		return resultMap;
	}
	
}
