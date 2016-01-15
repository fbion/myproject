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
import com.ctfo.sinoiov.mobile.webapi.bean.request.OilRechargeByAccountReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.OilRechargeByAccountRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.CheckValue;
import com.ctfo.sinoiov.mobile.webapi.util.PayConstants;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 使用账户余额对油卡进行充值
 * 
 * @author sunchuanfu
 */
@Service("oilRechargeByAccountManagerImpl")
public class OilRechargeByAccountManagerImpl implements IJsonService {
	private static final Log logger = LogFactory.getLog(OilRechargeByAccountManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		OilRechargeByAccountRsp rsp = new OilRechargeByAccountRsp();

		try {
			OilRechargeByAccountReq req = (OilRechargeByAccountReq) request.getBody();
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("MD5", req.getMd5());// 注:这里因为了手机APP好处理，req中属性名为"md5"
			mpParas.put("smsCode", req.getSmsCode());
			mpParas.put("mobileNo", req.getMobileNo());
			mpParas.put("accountNo", req.getAccountNo());
			mpParas.put("merchantOrderAmount", req.getMerchantOrderAmount());
			mpParas.put("merchantOrderNo", req.getMerchantOrderNo());// 业务订单号
			mpParas.put("userId", req.getUserId());
			mpParas.put("remarks", req.getRemarks());
			mpParas.put("productName", req.getProductName());
			mpParas.put("productCatalog", "11");// 使用默认值11
			// 服务端回调地址
			String callbackURL = req.getCallbackURL();
			if (StringUtils.isBlank(callbackURL)) {
				// 如果地址为空则使用支付APP配置的地址
				mpParas.put("callbackURL", PayConstants.getConfigValue("UPP_OIL_RECHARGE_CALLBACK_URL"));
			} else {
				mpParas.put("callbackURL", callbackURL);
			}
			// 客户端回调地址
			String fcallbackURL = req.getFcallbackURL();
			if (StringUtils.isBlank(fcallbackURL)) {
				// 如果地址为空则使用支付APP配置的地址
				mpParas.put("fcallbackURL", "fcallbackURL");
			} else {
				mpParas.put("fcallbackURL", fcallbackURL);
			}
			UppResult uppResult = PayManagerUtil.invokeUPP(mpParas, "UPP_OIL_RECHARGE_BY_ACCOUNT");

			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}

			rsp = (OilRechargeByAccountRsp) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()),
					OilRechargeByAccountRsp.class);
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("使用账户余额对油卡进行充值时报错！", e);
			throw new ClientException(PayError.E240001, "使用账户余额对油卡进行充值时报错！");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
		OilRechargeByAccountReq req = (OilRechargeByAccountReq) request.getBody();
		if (StringUtils.isBlank(req.getAccountNo())) {
			throw new ClientException("E240005", PayError.E240005);
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
		// 支付密码
		if (StringUtils.isBlank(req.getMd5())) {
			throw new ClientException("E240006", PayError.E240006);
		}
		if (StringUtils.isBlank(req.getMerchantOrderAmount())) {
			throw new ClientException("E240004", PayError.E240004);
		}
		if (StringUtils.isBlank(req.getMerchantOrderNo())) {
			throw new ClientException("E240004", PayError.E240004);
		}
		if (StringUtils.isBlank(req.getUserId())) {
			throw new ClientException("E240004", PayError.E240004);
		}
		// 这个地址不验证
		// if (StringUtils.isBlank(req.getCallbackURL())) {
		// throw new ClientException("E240004", PayError.E240004);
		// }
		// if (StringUtils.isBlank(req.getFcallbackURL())) {
		// throw new ClientException("E240004", PayError.E240004);
		// }
		if (StringUtils.isBlank(req.getProductName())) {
			throw new ClientException("E240004", PayError.E240004);
		}
		// if (StringUtils.isBlank(req.getProductCatalog())) {
		// throw new ClientException("E240004", PayError.E240004);
		// }
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", OilRechargeByAccountReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}