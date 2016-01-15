package com.ctfo.upp.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.paymenttrade.PaymentTradeService;

@Component
@Controller
@Scope("prototype")
@RequestMapping("/paymentTrade")
public class PaymentTradeController extends BaseController{
	private static Log logger = LogFactory.getLog(PaymentTradeController.class);
	
	@Resource(name = "paymentTradeService", description = "service参数接口")
	private PaymentTradeService paymentTradeService;
	/**
	 * 分页交易管理-交易订单查询
	 * @return
	 */
	@RequestMapping(value="/queryTradeList.action" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryParamByPage(DynamicSqlParameter requestParam){
		try {
			result = paymentTradeService.queryPaymentTradeByPage(requestParam);
		} catch (Exception e) {
			result.setMessage("交易管理-支付交易查询信息异常!");
			logger.error("交易管理-支付交易查询信息异常!"+e.getMessage(),e);
		}		
		return result;	
	}
}
