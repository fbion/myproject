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
import com.ctfo.sinoiov.mobile.webapi.bean.request.SetPayPasswordReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.SetPayPasswordRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.CheckValue;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Login;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.PayErrorCodesZFPT;
import com.ctfo.sinoiov.mobile.webapi.util.PayManagerUtil;

/**
 * 设置支付密码
 * 
 * @author sunchuanfu
 */
@Service("setPayPasswordManagerImpl")
public class SetPayPasswordManagerImpl implements IJsonService {
	protected static final Log logger = LogFactory.getLog(SetPayPasswordManagerImpl.class);
	
//	@Autowired
//	private IUserService userService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		SetPayPasswordRsp rsp = new SetPayPasswordRsp();

		SetPayPasswordReq req = null;
		try {
			req = (SetPayPasswordReq) request.getBody();

			// 根据用户id查询是否存在用户下的账户
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("userId", req.getUserId());
			UppResult uppResult = PayManagerUtil.invokeUPP(mpParas, "UPP_QUERY_BY_USERID");

			String accountNo = null;
			Map<String, String> mp = null;
			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				if (uppResult.getErrorcode().equals(PayErrorCodesZFPT.ACCOUNT_NOT_EXIST)) {
					// 如果账户不存在，则创建账户同时也设置密码
					mpParas = new HashMap<String, String>();
					mpParas.put("ownerUserId", req.getOwnerUserId());
					mpParas.put("ownerLoginName", req.getOwnerLoginName());
					mpParas.put("mobileNo", req.getMobileNo());
					mpParas.put("smsCode", req.getSmsCode());
					mpParas.put("MD5", req.getMd5());
					uppResult = PayManagerUtil.invokeUPP(mpParas, "UPP_CREATE_ACCOUNT_SMSCODE");
					if (uppResult.getResult().equals(UppResult.FAILURE)) {
						throw new ClientException(PayError.E240001, uppResult.getError());
					}
					mp = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()), Map.class);
					accountNo = mp.get("accountNo");
					rsp.setAccountNo(accountNo);
					rsp.setResult(PublicStaticParam.RESULT_SUCCESS);
				} else {
					throw new ClientException(PayError.E240001, uppResult.getError());
				}
			} else {
				// 如果账户存在，则设置支付密码
				mp = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(uppResult.getData()), Map.class);
				accountNo = mp.get("accountNo");

				mpParas = new HashMap<String, String>();
				mpParas.put("accountNo", accountNo);
				mpParas.put("mobileNo", req.getMobileNo());
				mpParas.put("smsCode", req.getSmsCode());
				mpParas.put("MD5", req.getMd5());
				uppResult = PayManagerUtil.invokeUPP(mpParas, "UPP_SET_PAYPASSWORD");
				if (uppResult.getResult().equals(UppResult.SUCCESS)) {
					rsp.setAccountNo(accountNo);
					rsp.setResult(PublicStaticParam.RESULT_SUCCESS);
				} else {
					throw new ClientException(PayError.E240001, uppResult.getError());
				}
			}
		} catch (ClientException e) {
			logger.error("设置用户" + req.getUserId() + "支付密码时报错！", e);
			throw e;
		} catch (Exception e) {
			logger.error("设置用户" + req.getUserId() + "支付密码时报错！", e);
			throw new ClientException(PayError.E240001, "设置用户" + req.getUserId() + "支付密码时报错！");
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E240004", PayError.E240004);
		}
		// 用户登录ID
		SetPayPasswordReq req = (SetPayPasswordReq) request.getBody();
		if (StringUtils.isBlank(req.getOwnerUserId())) {
			throw new ClientException("E240009", PayError.E240009);
		} 
//		else {
//			// 验证用户是否存在
//			UAAUser user = null;
//			try {
//				user = userService.queryUaaUserById(req.getOwnerUserId(), null);
//			} catch (Exception e) {
//
//			}
//			if (user == null) {
//				throw new ClientException("E120011", UserError.E120011);
//			}
//		}
		// 登录名
		if (StringUtils.isBlank(req.getOwnerLoginName())) {
			throw new ClientException("E110002", Login.E110002);
		}
		// 电话
		if (StringUtils.isBlank(req.getMobileNo())) {
			throw new ClientException("E000017", Common.E000017);
		} else if (!CheckValue.isPhone(req.getMobileNo())) {
			throw new ClientException("E000016", Common.E000016);
		}
		// 支付密码
		if (StringUtils.isBlank(req.getMd5())) {
			throw new ClientException("E240006", PayError.E240006);
		}
		// 短信验证码
		if (StringUtils.isBlank(req.getSmsCode())) {
			throw new ClientException("E000018", Common.E000018);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", SetPayPasswordReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

}
