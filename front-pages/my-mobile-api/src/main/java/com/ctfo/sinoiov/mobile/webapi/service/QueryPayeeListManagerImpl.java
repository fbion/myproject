package com.ctfo.sinoiov.mobile.webapi.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.ctfo.sinoiov.mobile.webapi.bean.request.QueryPayeeListReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.QueryPayeeListRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.PayeeVo;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.UserError;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 查询公司对公账号信息
 * 
 * @author dxs
 */
@Service("queryPayeeListManagerImpl")
public class QueryPayeeListManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(QueryPayeeListManagerImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		QueryPayeeListRsp queryPayeeListRsp = new QueryPayeeListRsp();

		try {
			List<PayeeVo> lstPayeeVos = null;

			logger.info(String.format("查询支付中心对公账号列表（收件人列表）,查询条件%s", "ZJXL_BACK_ACCOUNT"));
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", "ZJXL_BACK_ACCOUNT");
			logger.info(">>>>>>>>>>>>条件type为[ZJXL_BACK_ACCOUNT]>>>>>>>>>>>>>>>>");
			logger.info(">>>>>>>>>>>>根据条件进行查询，开始时间[" + new Date() + "]>>>>>>>>>>>>>>>>");
			UppResult uppResult = PayManagerUtil.invokeUPP(params, "UPP_QUERY_SIMPLE_CODE");
			logger.info(">>>>>>>>>>>>根据条件进行查询，结束时间[" + new Date() + "]>>>>>>>>>>>>>>>>");
			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				logger.info(">>>>>>>>>>>>查询结果错误，原因[" + uppResult.getError() + "]>>>>>>>>>>>>>>>>");
				throw new ClientException(PayError.E240001, uppResult.getError());
			}

			// TODO 这里如果使用com.ctfo.upp.models.PaginationResult会报错
			Map mp = (Map) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()), Map.class);

			if (uppResult.getData() != null) {
				lstPayeeVos = new ArrayList<PayeeVo>();
				@SuppressWarnings("unchecked")
				List<Map> lstSimpleCodeMap = (List<Map>) JSONArray.toCollection(
						JSONArray.fromObject(mp.get("data")), Map.class);
				logger.info(">>>>>>>>>>>>结果集大小[" + lstSimpleCodeMap != null ? "0" : lstSimpleCodeMap.size()
						+ "],转换数据开始>>>>>>>>>>>>>>>>");

				if (lstSimpleCodeMap != null && lstSimpleCodeMap.size() > 0) {
					for (Map mpSimpleCode : lstSimpleCodeMap) {
						PayeeVo payeeVo = new PayeeVo();
						Object desc = mpSimpleCode.get("description");
						if (desc != null) {
							String strDesc = (String)desc;
							String[] str = strDesc.split(":");
							payeeVo.setBankName(str[0]);
							payeeVo.setCardNum(str[1]);
							payeeVo.setPayeeId((String) mpSimpleCode.get("code"));
							payeeVo.setCompanyName((String) mpSimpleCode.get("name"));
						}
						lstPayeeVos.add(payeeVo);
					}
				}
				logger.info(">>>>>>>>>>>>转换后结果集大小[" + lstPayeeVos.size() + "],转换数据结束>>>>>>>>>>>>>>>>");
			}
			queryPayeeListRsp.setList(lstPayeeVos);
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.error("查询收款人列表失败：", e);
			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			throw new ClientException("查询收款人列表失败：", e);
		}

		return queryPayeeListRsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E120001", UserError.E120001);
		}
		QueryPayeeListReq req = (QueryPayeeListReq) request.getBody();
		if (StringUtils.isBlank(req.getUserId())) {
			throw new ClientException("E120002", UserError.E120002);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", QueryPayeeListReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
