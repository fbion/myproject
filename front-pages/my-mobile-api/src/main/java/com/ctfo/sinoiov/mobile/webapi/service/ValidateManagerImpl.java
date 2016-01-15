package com.ctfo.sinoiov.mobile.webapi.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.sinoiov.mobile.webapi.base.intf.IJsonService;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Head;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Parameter;
import com.ctfo.sinoiov.mobile.webapi.bean.request.ValidateAccountInfosReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.PayCommonRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.CheckValue;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 验证账户相关各种信息(短信\支付密码\单个安全问题)，返回"安全票据"securityTicket
 * 
 * @author sunchuanfu
 */
@Service("validateManagerImpl")
public class ValidateManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(ValidateManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		PayCommonRsp rsp = new PayCommonRsp();
		ValidateAccountInfosReq req = null;

		try {
			req = (ValidateAccountInfosReq) request.getBody();

			UppResult uppResult = PayManagerUtil.invokeUPP(req, "UPP_ACCOUNTSAFETY_VALIDATE");

			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}

			@SuppressWarnings("unchecked")
			Map<String, String> mp = (Map<String, String>) JSONObject.toBean(
					JSONObject.fromObject(uppResult.getData()), Map.class);

			rsp.setResult(mp.get("data"));// 返回安全票据SecurityTicket
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("验证账户相关各种信息时报错！", e);
			throw new ClientException(PayError.E240001, "验证账户相关各种信息时报错!");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
		ValidateAccountInfosReq req = (ValidateAccountInfosReq) request.getBody();
		// 账号
		if (StringUtils.isBlank(req.getAccountNo())) {
			throw new ClientException("E240005", PayError.E240005);
		}
		// 手机号和验证码不能只输入一个
		if ((StringUtils.isBlank(req.getMobileNo()) && StringUtils.isNotBlank(req.getSmsCode()))
				|| StringUtils.isNotBlank(req.getMobileNo()) && StringUtils.isBlank(req.getSmsCode())) {
			throw new ClientException("E000001", Common.E000001);
		}

		if (StringUtils.isNotBlank(req.getMobileNo()) && !CheckValue.isPhone(req.getMobileNo())) {
			throw new ClientException("E000016", Common.E000016);
		}

		// 安全问题和答案不能只输入一个
		if ((StringUtils.isBlank(req.getSecurityQuestion()) && StringUtils.isNotBlank(req.getSecurityAnswer()))
				|| StringUtils.isNotBlank(req.getSecurityQuestion()) && StringUtils.isBlank(req.getSecurityAnswer())) {
			throw new ClientException("E000001", Common.E000001);
		}
		// 需要验证的参数不能同时为空
		if (StringUtils.isBlank(req.getMobileNo()) && StringUtils.isBlank(req.getSmsCode())
				&& StringUtils.isBlank(req.getPayPassword()) && StringUtils.isBlank(req.getSecurityQuestion())
				&& StringUtils.isBlank(req.getSecurityAnswer())) {
			throw new ClientException("E000001", Common.E000001);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", ValidateAccountInfosReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
