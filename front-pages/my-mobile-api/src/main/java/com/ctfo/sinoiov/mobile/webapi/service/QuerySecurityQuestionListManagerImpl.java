package com.ctfo.sinoiov.mobile.webapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.sinoiov.mobile.webapi.base.intf.IJsonService;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Head;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Parameter;
import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.QuerySecurityQuestionListRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.SecurityQuestionVo;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 查询可以选择的密保问题列表
 * 
 * @author sunchuanfu
 */
@Service("querySecurityQuestionListManagerImpl")
public class QuerySecurityQuestionListManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory
			.getLog(QuerySecurityQuestionListManagerImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public Body execute(Parameter request, Object... obj)
			throws ClientException {
		QuerySecurityQuestionListRsp rsp = new QuerySecurityQuestionListRsp();
		try {
			Map<String, String> mapParam = new HashMap<String, String>();
			mapParam.put("type", "CRYPTOGUARD-QUERY");
			UppResult uppResult = PayManagerUtil.invokeUPP(mapParam,
					"UPP_QUERY_SIMPLE_CODE");

			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001,
						uppResult.getError());
			}

			Map mp = (Map) JSONObject.toBean(
					JSONObject.fromObject(uppResult.getData()), Map.class);

			List<SecurityQuestionVo> lstSecurityQuestionVos = null;
			if (uppResult.getData() != null) {
				lstSecurityQuestionVos = new ArrayList<SecurityQuestionVo>();

				@SuppressWarnings("unchecked")
				List<Map> lstSimpleCodeMap = (List<Map>) JSONArray
						.toCollection(JSONArray.fromObject(mp.get("data")),
								Map.class);

				if (lstSimpleCodeMap != null && lstSimpleCodeMap.size() > 0) {
					SecurityQuestionVo vo = null;
					for (Map mpSimpleCode : lstSimpleCodeMap) {
						vo = new SecurityQuestionVo();
						vo.setCode(mpSimpleCode.get("code").toString());
						vo.setName(mpSimpleCode.get("name").toString());
						lstSecurityQuestionVos.add(vo);
					}
				}
			}

			rsp.setQuestions(lstSecurityQuestionVos);
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("查询可以选择的密保问题列表时报错！", e);
			throw new ClientException(PayError.E240001, "查询可以选择的密保问题列表时报错！");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", BaseBeanReq.class);
			Parameter param = (Parameter) JSONObject.toBean(
					JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
