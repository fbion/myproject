package com.sinoiov.upp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctfo.upp.models.DynamicSqlParameter;
import com.sinoiov.pay.utils.UPPJsonUtil;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.service.IStatisticsService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/statistics")
public class Statistics extends BaseController {
	
	@Autowired
	private IStatisticsService statisticsService;
	
	
	/**
	 * 统计账户余额接口
	 * <p>
	 * 描述：根据条件统计账户余额
	 */
	@RequestMapping(value = "/sumAccount", produces = "text/plain;charset=UTF-8")
	public List<String> sumAccount(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		Map<String, String> map = statisticsService.sumAccount(parameter);
		
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, map);
		
	}
	/**
	 * 统计账户流水接口
	 * <p>
	 * 描述：根据条件统计账户流水
	 */
	@RequestMapping(value = "/sumAccountDetail", produces = "text/plain;charset=UTF-8")
	public List<String> sumAccountDetail(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		
		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);
		
		Map<String, String> map = statisticsService.sumAccountDetail(parameter);
		
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, map);
		
	}

}
