package com.sinoiov.upp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctfo.upp.clearingandsettlement.IClearingAndSettlement;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Scope("prototype")
@Controller
@RequestMapping(value = "/clearingAndSettlement")
public class ClearingAndSettlement extends BaseController {

	@Autowired
	private IClearingAndSettlement clearingAndSettlementService;

	/**
	 * 与商户对帐(TODO 未实现)
	 */
	@RequestMapping(value = "/contrastOrder", produces = "text/plain;charset=UTF-8")
	public List<String> contrastOrder(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		// String paramJson = super.getParamJson(params.getData(),
		// params.getEncryptkey(), params.getMerchantcode());
		// JSONObject jsonMap = JSONObject.fromObject(paramJson);

		throw new UPPServiceException("未实现的方法");
		// return super.commonControllerReturn(params.getMerchantcode(),
		// paramJson, null);
	}

}
