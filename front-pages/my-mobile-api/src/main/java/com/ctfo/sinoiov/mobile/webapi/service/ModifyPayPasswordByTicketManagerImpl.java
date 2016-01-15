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
import com.ctfo.sinoiov.mobile.webapi.bean.request.ModifyPayPasswordByTicketReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.PayCommonRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;

/**
 * 修改支付密码(不需要输入旧密码，需要"安全票据")，使用这个接口前需要通过验证Z000017生成"安全票据"
 * 
 * @author sunchuanfu
 */
@Service("modifyPayPasswordByTicketManagerImpl")
public class ModifyPayPasswordByTicketManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(ModifyPayPasswordByTicketManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		PayCommonRsp rsp = new PayCommonRsp();
		ModifyPayPasswordByTicketReq req = null;

		try {
			req = (ModifyPayPasswordByTicketReq) request.getBody();

			UppResult uppResult = PayManagerUtil.invokeUPP(req, "UPP_MODIFY_PAYPASSWORD_BY_TICKET");

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
			logger.error("使用安全票据修改支付密码时报错！", e);
			throw new ClientException(PayError.E240001, "使用安全票据修改支付密码时报错!");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
		ModifyPayPasswordByTicketReq req = (ModifyPayPasswordByTicketReq) request.getBody();
		// 账号
		if (StringUtils.isBlank(req.getAccountNo())) {
			throw new ClientException("E240005", PayError.E240005);
		}
		// 新密码
		if (StringUtils.isBlank(req.getNewMD5())) {
			throw new ClientException("E240008", PayError.E240008);
		}
		// SecurityTicket
		if (StringUtils.isBlank(req.getSecurityTicket())) {
			throw new ClientException("E240025", PayError.E240025);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", ModifyPayPasswordByTicketReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
