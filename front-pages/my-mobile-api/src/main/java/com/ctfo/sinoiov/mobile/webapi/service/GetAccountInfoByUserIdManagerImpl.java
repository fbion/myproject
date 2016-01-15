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
import com.ctfo.sinoiov.mobile.webapi.bean.request.GetAccountInfoByUserIdReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.GetAccountInfoByUserIdRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.PayErrorCodesZFPT;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 根据用户Id查询账户
 * 
 * @author sunchuanfu
 */
@Service("getAccountInfoByUserIdManagerImpl")
@SuppressWarnings("unchecked")
public class GetAccountInfoByUserIdManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(GetAccountInfoByUserIdManagerImpl.class);
	
	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		GetAccountInfoByUserIdRsp rsp = new GetAccountInfoByUserIdRsp();
		GetAccountInfoByUserIdReq req = null;

		try {
			req = (GetAccountInfoByUserIdReq) request.getBody();
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("userId", req.getUserId());
			UppResult uppResult = PayManagerUtil.invokeUPP(mpParas, "UPP_QUERY_BY_USERID");
			if (uppResult == null || StringUtils.isBlank(uppResult.getResult())) {
				return rsp;
			}
			if (UppResult.FAILURE.equals(uppResult.getResult())) {
				if (PayErrorCodesZFPT.ACCOUNT_NOT_EXIST.equals(uppResult.getErrorcode())) {
					// 如果账户不存在则返回空对象
					return rsp;
				} else {
					throw new ClientException(PayError.E240001, uppResult.getError());
				}
			}
			rsp = (GetAccountInfoByUserIdRsp) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()),
					GetAccountInfoByUserIdRsp.class);

			// 查询账户的累计收入和支出
			mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", rsp.getAccountNo());
			// 这里时间不传，支付会默认查询所有时间
			uppResult = PayManagerUtil.invokeUPP(mpParas, "UPP_GET_SUM_ACCOUNT_BALANCE");
			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}
			Map<String, String> mp = (Map<String, String>) JSONObject.toBean(
					JSONObject.fromObject(uppResult.getData()), Map.class);
			rsp.setPaySumBalance(mp.get("paySumBalance"));
			rsp.setIncomeSumBalance(mp.get("incomeSumBalance"));

			// 查询账户是否设置支付密码(这里传入参数同上)
			uppResult = PayManagerUtil.invokeUPP(mpParas, "UPP_IS_SET_PAYPASSWORD");
			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}
			mp = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()), Map.class);
			boolean isSetPayPassword = mp.get("data").equals(UppResult.SUCCESS);
			rsp.setIsSetPayPassword(isSetPayPassword ? PublicStaticParam.RESULT_SUCCESS : PublicStaticParam.RESULT_FAIL);
			
			// 判断是否设置安全问题
			boolean isSetSecurityQuestion = false;
			String accountSafetyLevel = rsp.getAccountSafetyLevel();
			if (accountSafetyLevel.equals("0")) {
				isSetSecurityQuestion = false;
			} else if (accountSafetyLevel.equals("1")) {
				if (isSetPayPassword) {
					isSetSecurityQuestion = false;
				} else {
					isSetSecurityQuestion = true;
				}
			} else if (accountSafetyLevel.equals("2")) {
				isSetSecurityQuestion = true;
			}
			rsp.setIsSetSecurityQuestion(isSetSecurityQuestion ? PublicStaticParam.RESULT_SUCCESS : PublicStaticParam.RESULT_FAIL);			
		} catch (ClientException e) {
			logger.error("根据用户Id查询账户信息时报错！", e);
			throw e;
		} catch (Exception e) {
			logger.error("根据用户Id查询账户信息时报错！", e);
			throw new ClientException(PayError.E240001, "根据用户Id查询账户信息时报错！");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
		GetAccountInfoByUserIdReq req = (GetAccountInfoByUserIdReq) request.getBody();
		// 验证用户是否存在
		if (StringUtils.isBlank(req.getUserId())) {
			throw new ClientException("E240009", PayError.E240009);
		}
//		else {
//			UAAUser user = null;
//			try {
//				user = userService.queryUaaUserById(req.getUserId(), null);
//			} catch (Exception e) {
//			}
//			if (user == null) {
//				throw new ClientException("E120011", UserError.E120011);
//			}
//		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", GetAccountInfoByUserIdReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
