/**
 * 北京中交兴路车联网科技有限公司 2011 版权所有.
 */
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
import com.ctfo.sinoiov.mobile.webapi.bean.request.QueryPayCenterTradeListReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.QueryPayCenterTradeListRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.PayCenterTradeVO;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.BillRrror;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayCenterTradeInfo;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.UserError;
import com.ctfo.sinoiov.mobile.webapi.util.PayConstants;
import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.models.DynamicSqlParameter;

/**
 * 分页查询账户流水
 * 
 * @author sunchuanfu
 */
@Service("queryAccountDetailByPageManagerImpl")
public class QueryAccountDetailByPageManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(QueryAccountDetailByPageManagerImpl.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		QueryPayCenterTradeListReq req = null;
		QueryPayCenterTradeListRsp rsp = new QueryPayCenterTradeListRsp();
		try {
			req = (QueryPayCenterTradeListReq) request.getBody();
			
			logger.info(String.format("分页查询账户流水,查询条件%s", JSONObject.fromObject(req)));
			DynamicSqlParameter dsp = new DynamicSqlParameter();
			dsp.setOrder("accountTime");// 默认按照时间倒序排序
			dsp.setSort("desc");
			dsp.setPage(Integer.valueOf(req.getPage()));
			dsp.setRows(Integer.valueOf(req.getRows()));

			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("insideAccountNo", req.getAccountNo());

			dsp.setEqual(mpParams);

			if (StringUtils.isNotBlank(req.getBookAccountType())) {
				Map<String, Object> inMap = new HashMap<String, Object>();
				inMap.put("bookaccountType", req.getBookAccountType());
				dsp.setInMap(inMap);
			} else {
				Map<String, Object> inMap = new HashMap<String, Object>();
				inMap.put("bookaccountType", "deduction,recorded");
				dsp.setInMap(inMap);
			}

			UppResult uppResult = this.callUpp(dsp, "UPP_QUERY_ACCOUNT_HISTORY_LIST_BY_ACCOUNT");
			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}

			Map mp = (Map) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()), Map.class);
			
			Object data = mp.get("data");
			if (data != null) {
				List<PayCenterTradeVO> list = (List<PayCenterTradeVO>) JSONArray.toCollection(
						JSONArray.fromObject(data), PayCenterTradeVO.class);
				// 对象转换
				this.toObject(list, rsp);
			}
			rsp.setTotal(String.valueOf(mp.get("total")));
			logger.info(String.format("分页查询账户流水,结果为%s", JSONObject.fromObject(rsp)));
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("查询支付中心交易列表信息异常", e);
			throw new ClientException("E200005", BillRrror.E200005);
		}
		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E120001", UserError.E120001);
		}
		QueryPayCenterTradeListReq req = (QueryPayCenterTradeListReq) request.getBody();
		if (StringUtils.isBlank(req.getUserId())) {
			throw new ClientException("E250001", PayCenterTradeInfo.E250001);
		}
		if (StringUtils.isBlank(req.getAccountNo())) {
			throw new ClientException("E250002", PayCenterTradeInfo.E250002);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", QueryPayCenterTradeListReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

	/**
	 * 对象转换
	 */
	private void toObject(List<PayCenterTradeVO> list, QueryPayCenterTradeListRsp response) {
		if (list != null) {
			// 将类的list属性冒泡排序
			PayCenterTradeVO a;
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = 1; j < list.size() - i; j++) {
					Long time1 = Long.valueOf(list.get(j - 1).getBookAccountTime().toString());
					Long time2 = Long.valueOf(list.get(j).getBookAccountTime().toString());
					if (time1 - time2 < 0) {
						a = list.get(j - 1);
						list.set((j - 1), list.get(j));
						list.set(j, a);
					}
				}
			}

			List<PayCenterTradeVO> payList = new ArrayList<PayCenterTradeVO>();
			for (PayCenterTradeVO bean : list) {
				PayCenterTradeVO payBean = new PayCenterTradeVO();
				payBean.setId(bean.getId());
				payBean.setBookAccountMoney(bean.getBookAccountMoney());
				payBean.setBookAccountTime(bean.getBookAccountTime());
				payBean.setBookAccountType(bean.getBookAccountType());
				payBean.setBookCurrentMoney(bean.getBookCurrentMoney());
				payBean.setOrderNo(bean.getOrderNo());
				payBean.setOrderRemarks(bean.getOrderRemarks());
				payBean.setOrderType(bean.getOrderNo());
				payBean.setOrderTypeLuc(bean.getOrderTypeLuc());
				// 如果ProductName为空的话则将OrderRemarks的指付给ProductName
				payBean.setProductName("".equals(bean.getProductName().trim()) ? bean.getOrderRemarks() : bean
						.getProductName());
				payList.add(payBean);
			}
			response.setData(payList);
			response.setTotal(list.size() + "");
		}
	}

	/**
	 * 注：这里不要用invokeUpp接口
	 */
	private UppResult callUpp(Object param, String methodUrlKey) throws Exception {
		UppResult uppResult = null;
		try {
			String methodUrlValue = PayConstants.getConfigValue(methodUrlKey);
			String requestJson = JSONObject.fromObject(param).toString();
			// 发送POST消息(明文的数据在下面的方法中做了加密，实际POST发送给支付平台是加密后的数据)
			String returnJson = HttpUtils.sendRequest(requestJson, 
					PayConstants.getConfigValue("UPP_INTERFACE_URL") + methodUrlValue,
					PayConstants.getPrivateKey(PayConstants.getConfigValue("MOBILE_API_MERCHANT_CODE")), 
					PayConstants.getPublicKey(PayConstants.getConfigValue("INTERFACE_MERCHANT_CODE")), 
					PayConstants.getConfigValue("MOBILE_API_MERCHANT_CODE"));
			uppResult = (UppResult) JSONObject.toBean(JSONObject.fromObject(returnJson), UppResult.class);

		} catch (Exception e) {
			logger.error("调用支付中接口出错：", e);
			throw e;
		}
		return uppResult;
	}

}
