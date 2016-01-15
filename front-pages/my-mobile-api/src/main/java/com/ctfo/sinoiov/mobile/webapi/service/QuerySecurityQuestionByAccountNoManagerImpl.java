package com.ctfo.sinoiov.mobile.webapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.sinoiov.mobile.webapi.base.intf.IJsonService;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Head;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Parameter;
import com.ctfo.sinoiov.mobile.webapi.bean.request.AccountSafetyReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.QuerySecurityQuestionListRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.SecurityQuestionVo;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 根据账号查询账户设置的安全问题列表
 * 
 * @author sunchuanfu
 */
@Service("querySecurityQuestionByAccountNoManagerImpl")
public class QuerySecurityQuestionByAccountNoManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(QuerySecurityQuestionByAccountNoManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		QuerySecurityQuestionListRsp rsp = new QuerySecurityQuestionListRsp();
		AccountSafetyReq req = null;

		try {
			req = (AccountSafetyReq) request.getBody();

			Map<String, String> mapParam = new HashMap<String, String>();
			mapParam.put("accountNo", req.getAccountNo());

			UppResult uppResult = PayManagerUtil.invokeUPP(mapParam, "UPP_QUERY_SECURITY_QUESTION");

			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}

			@SuppressWarnings("unchecked")
			Map<String, String> mp = (Map<String, String>) JSONObject.toBean(
					JSONObject.fromObject(uppResult.getData()), Map.class);

			// 查询所有安全设置问题
			Map<String, String> mpAllSecurityQuestions = this.queryAllQuestions();
			List<SecurityQuestionVo> questions = null;
			String securityQuestionsOfAccount = mp.get("data");// 返回安全问题名称列表,格式如"code1,code2,code3"
			if (StringUtils.isNotBlank(securityQuestionsOfAccount) && mpAllSecurityQuestions != null) {
				questions = new ArrayList<SecurityQuestionVo>();
				String[] sqs = securityQuestionsOfAccount.split(",");
				SecurityQuestionVo vo = null;
				for (String questionCode : sqs) {
					vo = new SecurityQuestionVo();
					vo.setCode(questionCode);
					vo.setName(mpAllSecurityQuestions.get(questionCode));
					questions.add(vo);
				}
			}
			rsp.setQuestions(questions);
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("根据账号查询账户设置的安全问题列表时报错！", e);
			throw new ClientException(PayError.E240001, "根据账号查询账户设置的安全问题列表时报错！");
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

	/**
	 * 查询所有安全设置问题
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> queryAllQuestions() throws Exception {
		Map<String, String> mpSecurityQuestions = null;

		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("type", "CRYPTOGUARD-QUERY");
		UppResult uppResult = PayManagerUtil.invokeUPP(mapParam, "UPP_QUERY_SIMPLE_CODE");

		if (uppResult.getResult().equals(UppResult.FAILURE)) {
			throw new ClientException(PayError.E240001, uppResult.getError());
		}

		Map mp = (Map) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()), Map.class);

		if (uppResult.getData() != null) {
			mpSecurityQuestions = new HashMap<String, String>();

			@SuppressWarnings("unchecked")
			List<Map> lstSimpleCodeMap = (List<Map>) JSONArray.toCollection(JSONArray.fromObject(mp.get("data")),
					Map.class);

			if (lstSimpleCodeMap != null && lstSimpleCodeMap.size() > 0) {
				for (Map mpSimpleCode : lstSimpleCodeMap) {
					mpSecurityQuestions.put(mpSimpleCode.get("code").toString(), mpSimpleCode.get("name").toString());
				}
			}
		}

		return mpSecurityQuestions;
	}

}
