package com.sinoiov.upp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.security.ConvertUtils;
import com.sinoiov.pay.utils.UPPConfigUtil;
import com.sinoiov.pay.utils.UPPJsonUtil;
import com.sinoiov.upp.service.IAccountService;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

public class BaseController {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private IAccountService accountService;
	/**
	 * 解密加密参数
	 * 
	 * @param data
	 * @param encryptkey
	 * @param merchantcode
	 * @return
	 * @throws UPPException
	 */
	protected String getParamJson(String data, String encryptkey, String merchantcode) throws UPPException {
		try {
			logger.debug("----->>>>>data:" + data);
			logger.debug("----->>>>>encryptkey:" + encryptkey);
			logger.info("----->>>>>merchantcode:" + merchantcode);
			
			String json = "";
			if(Long.parseLong(merchantcode.substring(0, 8)) < Long.parseLong("201503120000000000000".substring(0, 8))){
				String publicKey_1_0_0 = UPPConfigUtil.getPublicKey(merchantcode);
				if (StringUtils.isBlank(publicKey_1_0_0))
					throw new UPPException("商户编码[" + merchantcode + "]对应的公钥不存在！");
				String privateKey_1_0_0 = UPPConfigUtil.getPrivateKey(UPPConfigUtil.getValue("UPP_CODE"));
				json = ConvertUtils.decodeParamJson_1_0_0(data, encryptkey, privateKey_1_0_0, publicKey_1_0_0);		
			}else{
				String publicKey = UPPConfigUtil.getPublicKey(merchantcode);
				if (StringUtils.isBlank(publicKey))
					throw new UPPException("商户编码[" + merchantcode + "]对应的公钥不存在！");
				String privateKey = UPPConfigUtil.getPrivateKey(UPPConfigUtil.getValue("UPP_CODE_NEW"));
				json = ConvertUtils.decodeParamJson(data, encryptkey, privateKey, publicKey);
			}
			
			logger.info("----->>>>>json:" + json);
			
			return json;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException(ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("解密或解析参数错误", e);
			throw new UPPException("解密或解析参数错误!");
		}
	}

	/**
	 * 加密返回参数
	 * 
	 * @param json
	 * @param merchantcode
	 */
	protected String getReturnJson(String json, String merchantcode) throws UPPException {
		try {
			logger.info("----->>>>>json:" + json);
			String result = "";
			if(Long.parseLong(merchantcode.substring(0, 8)) < Long.parseLong("201503120000000000000".substring(0, 8))){
				String privateKey_1 = UPPConfigUtil.getPrivateKey(UPPConfigUtil.getValue("UPP_CODE"));
				String publicKey_1 = UPPConfigUtil.getPublicKey(merchantcode);
				result = ConvertUtils.encodeReturnJson_1_0_0(json, privateKey_1, publicKey_1, merchantcode);
			}else{
				String privatekey = UPPConfigUtil.getPrivateKey(UPPConfigUtil.getValue("UPP_CODE_NEW"));
				String publickey = UPPConfigUtil.getPublicKey(merchantcode);
				result = ConvertUtils.encodeReturnJson(json, privatekey, publickey);
				result = result.substring(0,result.length()-1) + ", \"merchantcode\":\""+merchantcode+"\"}";
			}
			return result;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("加密参数错误", e);
			throw new UPPException("加密参数错误!");
		}
	}
	
	/**
	 * 再次加密（按照业务系统的加密方式）
	 * 
	 * @param json
	 * @param merchantcode
	 */
	protected String encodeAgain(String json, String merchantcode) throws UPPException {
		try {
			logger.info("----->>>>>json:" + json);

			String uppcode = Long.parseLong(merchantcode.substring(0, 8)) < Long.parseLong("201503120000000000000"
					.substring(0, 8)) ? UPPConfigUtil.getValue("UPP_CODE") : UPPConfigUtil.getValue("UPP_CODE_NEW");

			String privateKey = UPPConfigUtil.getPrivateKey(merchantcode);

			String publicKey = UPPConfigUtil.getPublicKey(uppcode);

			String result = ConvertUtils.encodeReturnJson(json, privateKey, publicKey);

			return result;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("加密参数错误", e);
			throw new UPPException("加密参数错误!");
		}
	}

	/**
	 * 从JSONObject中获取数值
	 * 
	 * @param jsonMap
	 *            JSONObject对象
	 * @param key
	 * @return 如果没有获取到数值则返回空串(这里主要是想规避抛出JSONException异常)
	 */
	protected String getStringFromJsonMap(JSONObject jsonMap, String key) {
		return jsonMap.get(key) == null ? "" : jsonMap.getString(key);
	}

	/**
	 * controller通用返回处理
	 * 
	 * @param merchantcode
	 * @param paramJson
	 * @param obj
	 * @return
	 * @throws UPPException
	 */
	protected List<String> commonControllerReturn(String merchantcode, String paramJson, Object obj) throws UPPException {
		String resultJson = "{}";

		// TODO 如果返回的是string，看能不能统一返回一个map
		
		if (obj != null) {
			if (obj instanceof List) {
				Map<String, Object> mp = new HashMap<String, Object>();
				mp.put("data", obj);
				resultJson = JSONObject.fromObject(mp).toString();
			} else {
				resultJson = JSONObject.fromObject(obj).toString();
			}
		}

		// 加密返回参数
		String returnJson = this.getReturnJson(resultJson, merchantcode);
		// 返回成功结果
		returnJson = UPPJsonUtil.getJson(HttpUtils.SUCCESS, returnJson);

		List<String> lstReturn = new ArrayList<String>();
		lstReturn.add(returnJson);// 返回给接口发布系统调用方的JSON结果(最终被返回给接口调用的用户)
		lstReturn.add(resultJson);// 接口发布系统调用后台接口返回JSON结果
		lstReturn.add(paramJson);// 请求JSON参数(最终被记录进日志)
		lstReturn.add(merchantcode);// 商户编码
		return lstReturn;
	}
	protected AccountDto getAccountByAccountNo(String accountNo) throws UPPException{
		AccountDto account = new AccountDto();
		try {
			if (StringUtils.isBlank(accountNo))
				throw new UPPServiceException(ReturnCodeDict.IS_NULL, "账号不能为空");
			account = accountService.getAccountByAccountNo(accountNo);
		} catch (UPPServiceException e) {
			logger.error("账号不能为空",e);
		} catch (Exception e) {
			logger.error("根据账户号查询账户信息异常",e);
		}
		return account;
	}

}
