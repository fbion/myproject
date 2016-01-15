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
import com.ctfo.sinoiov.mobile.webapi.bean.request.GetFastPayUrlForAccountRechargeReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.GetFastPayUrlRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.MoneyError;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.PayConstants;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 获取"账户充值"易宝快捷支付URL地址
 * 
 * @author sunchuanfu
 */
@Service("getFastPayUrlForAccountRechargeManagerImpl")
public class GetFastPayUrlForAccountRechargeManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(GetFastPayUrlForAccountRechargeManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		GetFastPayUrlRsp rsp = new GetFastPayUrlRsp();

		try {
			GetFastPayUrlForAccountRechargeReq req = (GetFastPayUrlForAccountRechargeReq) request.getBody();
			req.setStoreCode(PayConstants.getConfigValue("MOBILE_API_MERCHANT_CODE"));

			UppResult uppResult = PayManagerUtil.invokeUPP(req, "UPP_GET_FASTPAY_URL_FOR_ACCOUNT_RECHARGE");

			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}

			rsp = (GetFastPayUrlRsp) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()),
					GetFastPayUrlRsp.class);
		} catch (ClientException e) {
			logger.error("获取账户充值易宝快捷支付URL地址时报错！", e);
			throw e;
		} catch (Exception e) {
			logger.error("获取账户充值易宝快捷支付URL地址时报错！", e);
			throw new ClientException(PayError.E240001, "获取账户充值易宝快捷支付URL地址时报错！");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}

		GetFastPayUrlForAccountRechargeReq req = (GetFastPayUrlForAccountRechargeReq) request.getBody();
		// 用户id
		if (StringUtils.isBlank(req.getUserId())) {
			throw new ClientException("E240009", PayError.E240009);
		}
		// 账户
		if (StringUtils.isBlank(req.getAccountNo())) {
			throw new ClientException("E240005", PayError.E240005);
		}
		// 金额,保留2位小数
		if (StringUtils.isBlank(req.getAmount())) {
			throw new ClientException("E160001", MoneyError.E160001);
		}
		// 终端类型，固定值(PC,MOBILE)
		if (StringUtils.isBlank(req.getClentType())) {
			throw new ClientException("E240016", PayError.E240016);
		}
		// 终端Id
		// if (StringUtils.isBlank(req.getClentId())) {
		// throw new ClientException("E240017", PayError.E240017);
		// }
		// 支付渠道，填英文固定值(在线-NET,快捷-FASTPAY,手机-WAP)
		if (StringUtils.isBlank(req.getPayChannel())) {
			throw new ClientException("E240023", PayError.E240023);
		}
		// 商品类型(账户充值值为2)
		if (StringUtils.isBlank(req.getProductCatalog())) {
			throw new ClientException("E240012", PayError.E240012);
		}
		// 商品名称
		if (StringUtils.isBlank(req.getProductName())) {
			throw new ClientException("E240013", PayError.E240013);
		}
		// 客户端IP
		String userIp = req.getUserIp();
		if (StringUtils.isBlank(userIp) || userIp.startsWith("127.") || userIp.startsWith("0.")) {
			// 如果客户端IP传值为空
			// 或者数值为127.*.*.*，或者为0.0.0.0，则设置一个随意的默认值
			req.setUserIp("192.168.1.1");
			// throw new ClientException("E240018", PayError.E240018);
		}
		// 标识类型:0=IMEI,1=MAC地址,2=用户ID,3=用户Email,4=用户手机号
		if (StringUtils.isBlank(req.getIdentityType())) {
			throw new ClientException("E240014", PayError.E240014);
		}
		// 标识ID
		if (StringUtils.isBlank(req.getIdentityId())) {
			throw new ClientException("E240015", PayError.E240015);
		}
		// 前台回调URL
		if (StringUtils.isBlank(req.getFcallbackUrl())) {
			throw new ClientException("E240011", PayError.E240011);
		}
		// 业务订单号
		if (StringUtils.isBlank(req.getWorkOrderNo())) {
			throw new ClientException("E240010", PayError.E240010);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", GetFastPayUrlForAccountRechargeReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
