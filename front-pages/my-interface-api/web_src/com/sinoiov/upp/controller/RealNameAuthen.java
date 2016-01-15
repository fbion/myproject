package com.sinoiov.upp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctfo.upp.account.IRealNameAuthenManager;
import com.sinoiov.upp.bean.PayCommonRequestParams;

@Scope("prototype")
@Controller
@RequestMapping(value = "/realNameAuthen")
public class RealNameAuthen extends BaseController {

	@Resource
	private IRealNameAuthenManager realNameAuthenService;

	/**
	 * 实名认证申请(TODO 未实现)
	 * <p>
	 * 描述：将用户信息和提交的银行卡信息通过第三方提供的接口验证，如果正确则绑定提交的银行卡，用于后续的结算提现
	 */
	@RequestMapping(value = "/submitAuthenApplyInfo", produces = "text/plain;charset=UTF-8")
	public List<String> submitAuthenApplyInfo(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		// JSONObject jsonMap = JSONObject.fromObject(paramJson);

		// UPPIdentityDto uppIdentityDto = new UPPIdentityDto();

		// BeanUtils.copyProperties(uppIdentityDto, jsonMap);

		// boolean result =
		// realNameAuthenService.submitAuthenApplyInfo(uppIdentityDto);

		// Map<String, String> map = new HashMap<String, String>();
		// map.put("data", result?"SUCCESS":"FAIL");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, null);
	}

	/**
	 * 实名认证状态查询(TODO 未实现)
	 * <p>
	 * 描述：查询用户是否已经实名认证过
	 */
	@RequestMapping(value = "/queryAuthenState", produces = "text/plain;charset=UTF-8")
	public List<String> queryAuthenState(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		// JSONObject jsonMap = JSONObject.fromObject(paramJson);

		// if (jsonMap.get("idCard") == null)
		// throw new UPPServiceException(ReturnCodeDict.IS_NULL, "身份证号不能为空");
		//
		// String idCard = jsonMap.getString("idCard");
		//
		// String strAuthenState =
		// realNameAuthenService.queryAuthenState(idCard);
		//
		// if (strAuthenState == null || strAuthenState.isEmpty())
		// throw new UPPServiceException("查询实名认证状态返回结果 is null");
		//
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("state", strAuthenState);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, null);
	}

	/**
	 * 实名认证申请修改(TODO 未实现)
	 * <p>
	 * 描述：修改已提交，未完成实名认证的申请信息
	 */
	@RequestMapping(value = "/modifyAuthenApplyInfo", produces = "text/plain;charset=UTF-8")
	public List<String> modifyAuthenApplyInfo(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		// JSONObject jsonMap = JSONObject.fromObject(paramJson);
		//
		// UPPIdentityDto uppIdentityDto = new UPPIdentityDto();
		//
		// uppIdentityDto = (UPPIdentityDto) UPPJsonUtil.jsonToObject(paramJson,
		// uppIdentityDto);
		// boolean result =
		// realNameAuthenService.modifyAuthenApplyInfo(uppIdentityDto);
		//
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("data", result ? "SUCCESS" : "FAIL");

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, null);
	}

}
