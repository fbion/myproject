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
import com.ctfo.sinoiov.mobile.webapi.bean.request.ModifyPayPasswordReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.PayCommonRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.CheckValue;
import com.ctfo.sinoiov.mobile.webapi.util.PayErrorCodesZFPT;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 修改支付密码
 * 
 * @author sunchuanfu
 */
@Service("modifyPayPasswordManagerImpl")
public class ModifyPayPasswordManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(ModifyPayPasswordManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		PayCommonRsp rsp = new PayCommonRsp();
		ModifyPayPasswordReq req = null;

		try {
			req = (ModifyPayPasswordReq) request.getBody();
			UppResult uppResult = PayManagerUtil.invokeUPP(req, "UPP_MODIFY_PAYPASSWORD");

			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				String errorCode = uppResult.getErrorcode();
				if (errorCode.equals(PayErrorCodesZFPT.MOBILE_NO_SMSCODE)) {
					throw new ClientException(PayError.E240002, uppResult.getError());
				} else if (errorCode.equals(PayErrorCodesZFPT.OLD_MD5)) {
					throw new ClientException(PayError.E240003, uppResult.getError());
				} else {
					throw new ClientException(PayError.E240001, uppResult.getError());
				}
			}

			@SuppressWarnings("unchecked")
			Map<String, String> mp = (Map<String, String>) JSONObject.toBean(
					JSONObject.fromObject(uppResult.getData()), Map.class);

			rsp.setResult(mp.get("data").equals(UppResult.SUCCESS) ? PublicStaticParam.RESULT_SUCCESS
					: PublicStaticParam.RESULT_FAIL);
		} catch (ClientException e) {
			logger.error("修改账户" + req.getAccountNo() + "支付密码时报错！", e);
			throw e;
		} catch (Exception e) {
			logger.error("修改账户" + req.getAccountNo() + "支付密码时报错！", e);
			throw new ClientException(PayError.E240001, "修改账户" + req.getAccountNo() + "支付密码时报错！");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
		ModifyPayPasswordReq req = (ModifyPayPasswordReq) request.getBody();
		// 账号
		if (StringUtils.isBlank(req.getAccountNo())) {
			throw new ClientException("E240005", PayError.E240005);
		}
		// 旧支付密码
		if (StringUtils.isBlank(req.getOldMD5())) {
			throw new ClientException("E240006", PayError.E240006);
		}
		// 新支付密码
		if (StringUtils.isBlank(req.getNewMD5())) {
			throw new ClientException("E240007", PayError.E240007);
		}
		// 短信验证码
		if (StringUtils.isBlank(req.getSmsCode())) {
			throw new ClientException("E000018", Common.E000018);
		}
		// 手机号
		if (StringUtils.isBlank(req.getMobileNo())) {
			throw new ClientException("E000017", Common.E000017);
		} else if (!CheckValue.isPhone(req.getMobileNo())) {
			throw new ClientException("E000016", Common.E000016);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", ModifyPayPasswordReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
