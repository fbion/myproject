package com.sinoiov.upp.portal.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.portal.util.ProtalConvertUtils;
@Service
public class PaymentPasswordImpl extends AbstractService implements IPaymentPassword {
	
	private static Log logger = LogFactory.getLog(PaymentPasswordImpl.class);

	/**
	 * 修改支付密码
	 */
	public String changePayPwd(Map<String,String> map) throws UPPException {
		String result = "-1";
		try {
			//获取调用地址
			String uri = ProtalConvertUtils.getValue("UPP_PAYMENT_PASSWORD_CHANGE");
			//将参数转换为JSON格式
			String json = super.sendRequest(map, uri);
			//将返回的JSON解析
			JSONObject jsonObject = JSONObject.fromObject(json);
			result = jsonObject.get("result").toString();
		} catch (Exception e) {
			logger.error("修改交易密码异常！", e);
			throw new UPPException("修改交易密码异常！", e);
		}
		return result;
	}

	/**
	 * 设置支付密码
	 */
	public String setPayPwd(Map<String, String> map) throws UPPException {
		String result = "-1";
		try {
			//获取调用地址
			String uri = ProtalConvertUtils.getValue("UPP_PAYMENT_PASSWORD_SET");
			//将参数转换为JSON格式
			String json = super.sendRequest(map, uri);
			//将返回的JSON解析
			JSONObject jsonObject = JSONObject.fromObject(json);
			result = jsonObject.get("result").toString();
		} catch (Exception e) {
			logger.error("设置交易密码异常！", e);
			throw new UPPException("设置交易密码异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 修改绑定手机号码
	 */
	public String updateMobileNoByAccountNo(Map<String, String> map) throws UPPException {
		String result = "-1";
		try {
			//获取调用地址
			String uri = ProtalConvertUtils.getValue("MODIFY-MOBILE-NO");
			//将参数转换为JSON格式
			String json = super.sendRequest(map, uri);
			//将返回的JSON解析
			JSONObject jsonObject = JSONObject.fromObject(json);
			if("1".equals(jsonObject.get("result").toString())){
				result = jsonObject.get("result").toString();
			}
			if("-1".equals(jsonObject.get("result").toString())){
				if(ReturnCodeDict.MOBILE_NO_SMSCODE.equals(jsonObject.get("errorcode").toString())){
					result = "-2";//短信验证码错误
				}else{
					result = "-1";//修改手机号码异常
				}
			}
		} catch (Exception e) {
			logger.error("修改手机号码异常！", e);
			throw new UPPException("修改手机号码异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 获取密保信息
	 */
	public String getCryptoguard(Map<String, String> map) throws UPPException {
		String result = "-1";
		try {
			//获取调用地址
			String uri = ProtalConvertUtils.getValue("QUERY-SECURITY-QUESTION");
			//将参数转换为JSON格式
			String json = super.sendRequest(map, uri);
			//将返回的JSON解析
			JSONObject jsonObject = JSONObject.fromObject(json);
			if("1".equals(jsonObject.get("result").toString())){
				JSONObject jsonMap= jsonObject.getJSONObject("data");
				result = jsonMap.get("data").toString();
			}
		} catch (Exception e) {
			logger.error("验证交易密码是否正确异常！", e);
			throw new UPPException("验证交易密码是否正确异常！", e);
		}
		return result;
	}

	/**
	 * wjg
	 * 获取密保问题码表信息
	 */
	public Map<String, String> getCryptoguardQueryCode() throws UPPException {
		Map<String, String> resultMap = null;
		try {
			//获取调用地址
			String uri = ProtalConvertUtils.getValue("GET-CRYPTOGUARD-QUERY-CODE");
			//将参数转换为JSON格式
			String type = ProtalConvertUtils.getValue("CRYPTOGUARDCODE");
			Map<String,String> map = new HashMap<String, String>();
			map.put("type", type);
			String json = super.sendRequest(map, uri);
			//将返回的JSON解析
			JSONObject jsonObject = JSONObject.fromObject(json);
			if("1".equals(jsonObject.get("result").toString())){
				JSONObject jsonMap= jsonObject.getJSONObject("data");
				JSONArray jsonArray = jsonMap.getJSONArray("data");
				resultMap = new HashMap<String, String>();
				for(int i=0;i<jsonArray.size();i++){
					Map dataMap = (Map) jsonArray.get(i);
					resultMap.put(dataMap.get("code").toString(), dataMap.get("name").toString());
				}
				Map<String, Object> result = null;
			}
		} catch (Exception e) {
			logger.error("验证交易密码是否正确异常！", e);
			throw new UPPException("验证交易密码是否正确异常！", e);
		}
		return resultMap;
	}
	/**
	 * wjg
	 * 修改密码保护问题
	 */
	public String changeTryptoguard(Map<String, String> map)throws UPPException {
		String result = "-1";
		try {
			//获取调用地址
			String uri = ProtalConvertUtils.getValue("CHANGE-ACCOUNT-SAFETY");
			//将参数转换为JSON格式
			String json = super.sendRequest(map, uri);
			//将返回的JSON解析 
			JSONObject jsonObject = JSONObject.fromObject(json);
			result=jsonObject.get("result").toString();
		} catch (Exception e) {
			logger.error("修改密码保护问题异常！", e);
			throw new UPPException("修改密码保护问题异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 添加密码保护问题
	 */
	public String addTryptoguard(Map<String, String> map)throws UPPException {
		String result = "-1";
		try {
			//获取调用地址
			String uri = ProtalConvertUtils.getValue("ADD-ACCOUNT-SAFETY");
			//将参数转换为JSON格式
			String json = super.sendRequest(map, uri);
			//将返回的JSON解析
			JSONObject jsonObject = JSONObject.fromObject(json);
			result=jsonObject.get("result").toString();
		} catch (Exception e) {
			logger.error("添加密码保护问题异常！", e);
			throw new UPPException("添加密码保护问题异常！", e);
		}
		return result;
	}
	/**
	 * wjg
	 * 验证身份信息
	 */
	public String validate(Map<String, String> map,HttpServletRequest request) throws UPPException {
		String result = "-1";
		try {
			//获取调用地址
			String uri = ProtalConvertUtils.getValue("ACCOUNT-SAFETY-VALIDATE");
			//将参数转换为JSON格式
			String json = super.sendRequest(map, uri);
			//将返回的JSON解析
			JSONObject jsonObject = JSONObject.fromObject(json);
			if("1".equals(jsonObject.get("result").toString())){
				Map resultMap=jsonObject.getJSONObject("data");
				HttpSession session = request.getSession();
				session.setAttribute("tokenId", resultMap.get("data").toString());
				result = "1";
			}
			if(ReturnCodeDict.MOBILE_NO_SMSCODE.equals(jsonObject.get("errorcode").toString())){
				result = "-2";//短信验证码错误
			}
			if(ReturnCodeDict.ACCOUNT_STATUS_LOCKED.equals(jsonObject.get("errorcode").toString())){
				result = "-4";//账户被锁定
			}
			if(ReturnCodeDict.PAY_PASSWORD_ERROR.equals(jsonObject.get("errorcode").toString())){
				result = "-3";//支付密码错误
			}
			if(ReturnCodeDict.SAFET_QUERY_ANSWER_ERROR.equals(jsonObject.get("errorcode").toString())){
				result = "-5";//密保问题错误
			}
		} catch (Exception e) {
			logger.error("验证身份信息异常！", e);
			throw new UPPException("验证身份信息异常！", e);
		}
		return result;
	}
}
