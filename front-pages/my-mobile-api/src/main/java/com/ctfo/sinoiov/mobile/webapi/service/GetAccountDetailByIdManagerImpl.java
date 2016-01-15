/**
 * 北京中交兴路车联网科技有限公司 2011 版权所有.
 */
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
import com.ctfo.sinoiov.mobile.webapi.bean.request.QueryPayCenterTradeInfoReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.QueryPayCenterTradeInfoRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.BillRrror;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayCenterTradeInfo;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.UserError;

/**
 * 根据流水ID查询对应的账户流水信息
 * 
 * @author dxs
 */
@Service("getAccountDetailByIdManagerImpl")
public class GetAccountDetailByIdManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(GetAccountDetailByIdManagerImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		QueryPayCenterTradeInfoRsp rsp = new QueryPayCenterTradeInfoRsp();
		try {
			QueryPayCenterTradeInfoReq req = (QueryPayCenterTradeInfoReq) request.getBody();
			logger.info(String.format("根据流水ID查询对应的账户流水信息,查询条件%s", JSONObject.fromObject(req)));
			UppResult uppResult = PayManagerUtil.invokeUPP(req, "UPP_QUERY_ACCOUNT_HISTORY_INFO_BY_ID");
			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}
			rsp = (QueryPayCenterTradeInfoRsp) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()),
					QueryPayCenterTradeInfoRsp.class);

			Map<String, String> mpReturn = (Map<String, String>) JSONObject.toBean(
					JSONObject.fromObject(uppResult.getData()), Map.class);
			// 注：这里“充值类型”后台传入code转成name
			String orderTypeLuc = null;
			String payChannel = mpReturn.get("payChannel");
			if (StringUtils.isNotBlank(payChannel)) {
				if (payChannel.equalsIgnoreCase("NET")) {
					orderTypeLuc = "网银支付";
				} else if (payChannel.equalsIgnoreCase("ACCOUNT")) {
					orderTypeLuc = "账户支付";
				} else if (payChannel.equalsIgnoreCase("FASTPAY")) {
					orderTypeLuc = "快捷支付";
				}
			}
			rsp.setOrderTypeLuc(orderTypeLuc);

			logger.info(String.format("根据流水ID查询对应的账户流水信息成功,结果为%s", JSONObject.fromObject(rsp)));
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("根据流水ID查询对应的账户流水信息异常", e);
			throw new ClientException("E200005", BillRrror.E200005);
		}
		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E120001", UserError.E120001);
		}

		QueryPayCenterTradeInfoReq req = (QueryPayCenterTradeInfoReq) request.getBody();
		if (StringUtils.isBlank(req.getId())) {
			throw new ClientException("E250003", PayCenterTradeInfo.E250003);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", QueryPayCenterTradeInfoReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}
}
