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
import com.ctfo.sinoiov.mobile.webapi.bean.request.IsPayPasswordReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.PayCommonRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.CheckValue;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;

/**
 * 验证支付密码是否正确
 * 
 * @author sunchuanfu
 */
@Service("isPayPasswordManagerImpl")
public class IsPayPasswordManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(IsPayPasswordManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		PayCommonRsp rsp = new PayCommonRsp();
		IsPayPasswordReq req = null;

		try {
			req = (IsPayPasswordReq) request.getBody();
			Map<String, String> mapParam = new HashMap<String, String>();
			mapParam.put("accountNo", req.getAccountNo());
			mapParam.put("MD5", req.getMd5());// 注：这里的"MD5"要大写
			mapParam.put("smsCode", req.getSmsCode());
			mapParam.put("mobileNo", req.getMobileNo());
			UppResult uppResult = PayManagerUtil.invokeUPP(mapParam, "UPP_IS_PAYPASSWORD");

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
			logger.error("验证账户" + req.getAccountNo() + "支付密码是否正确时报错！", e);
			throw new ClientException(PayError.E240001, "验证账户" + req.getAccountNo() + "支付密码是否正确时报错!");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
		IsPayPasswordReq req = (IsPayPasswordReq) request.getBody();
		// 账号
		if (StringUtils.isBlank(req.getAccountNo())) {
			throw new ClientException("E240005", PayError.E240005);
		}
		// 电话
		if (StringUtils.isBlank(req.getMobileNo())) {
			throw new ClientException("E000017", Common.E000017);
		} else if (!CheckValue.isPhone(req.getMobileNo())) {
			throw new ClientException("E000016", Common.E000016);
		}
		// 短信验证码
		if (StringUtils.isBlank(req.getSmsCode())) {
			throw new ClientException("E000018", Common.E000018);
		}
		// 支付密码
		if (StringUtils.isBlank(req.getMd5())) {
			throw new ClientException("E240006", PayError.E240006);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", IsPayPasswordReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
