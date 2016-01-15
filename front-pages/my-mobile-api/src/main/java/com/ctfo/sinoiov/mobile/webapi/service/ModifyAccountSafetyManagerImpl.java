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
import com.ctfo.sinoiov.mobile.webapi.bean.common.PublicStaticParam;
import com.ctfo.sinoiov.mobile.webapi.bean.request.AccountSafetyReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.PayCommonRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;

/**
 * 修改安全问题
 * 
 * @author sunchuanfu
 */
@Service("modifyAccountSafetyManagerImpl")
public class ModifyAccountSafetyManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(ModifyAccountSafetyManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		PayCommonRsp rsp = new PayCommonRsp();
		AccountSafetyReq req = null;

		try {
			req = (AccountSafetyReq) request.getBody();

			UppResult uppResult = PayManagerUtil.invokeUPP(req, "UPP_MODIFY_ACCOUNT_SAFETY");

			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}

			@SuppressWarnings("unchecked")
			Map<String, String> mp = (Map<String, String>) JSONObject.toBean(
					JSONObject.fromObject(uppResult.getData()), Map.class);

			rsp.setResult(mp.get("data").equals(UppResult.SUCCESS) ? PublicStaticParam.RESULT_SUCCESS
					: PublicStaticParam.RESULT_FAIL);
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("修改安全问题时报错！", e);
			throw new ClientException(PayError.E240001, "修改安全问题时报错!");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
		AccountSafetyReq req = (AccountSafetyReq) request.getBody();
		// 账号
		if (StringUtils.isBlank(req.getAccountNo())) {
			throw new ClientException("E240005", PayError.E240005);
		}
		// 安全票据
		if (StringUtils.isBlank(req.getSecurityTicket())) {
			throw new ClientException("E000017", Common.E000017);
		}
		// 验证3个安全问题和对应答案是否不全
		if (StringUtils.isBlank(req.getSecurityQuestion1()) || StringUtils.isBlank(req.getSecurityAnswer1())
				|| StringUtils.isBlank(req.getSecurityQuestion2()) || StringUtils.isBlank(req.getSecurityAnswer2())
				|| StringUtils.isBlank(req.getSecurityQuestion3()) || StringUtils.isBlank(req.getSecurityAnswer3())) {
			throw new ClientException("E000001", Common.E000001);
		}
		// TODO 可能需要验证...
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", AccountSafetyReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
