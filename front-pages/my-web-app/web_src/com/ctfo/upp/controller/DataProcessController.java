package com.ctfo.upp.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.upp.service.data.IDataProcessService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/dataProcess")
public class DataProcessController extends BaseController{
	
	private static Log logger = LogFactory.getLog(DataProcessController.class);
	
	@Resource
	private IDataProcessService dataProcessService;
	
	@RequestMapping(value = "/handleMemberNoForAccount.action", method = RequestMethod.GET)
	@ResponseBody
	public void handleMemberNoForAccount(){
		try {
			dataProcessService.handleMemberNoForAccount();
		} catch (Exception e) {
			logger.error("处理账户旧数据信息异常", e);
		}
	}
	@RequestMapping(value = "/handleMemberNoForOrderInfo.action", method = RequestMethod.GET)
	@ResponseBody
	public void handleMemberNoForOrderInfo(){
		try {
			dataProcessService.handleMemberNoForOrderInfo();
		} catch (Exception e) {
			logger.error("处理订单旧数据信息异常", e);
		}
	}
	@RequestMapping(value = "/handleMemberNoForPaymentTradeOffline.action", method = RequestMethod.GET)
	@ResponseBody
	public void handleMemberNoForPaymentTradeOffline(){
		try {
			dataProcessService.handleMemberNoForPaymentTradeOffline();
		} catch (Exception e) {
			logger.error("处理线下申请旧数据信息异常", e);
		}
	}
	@RequestMapping(value = "/handleOrderInfoBatchRechargeToUserAccount.action", method = RequestMethod.GET)
	@ResponseBody
	public void handleOrderInfoBatchRechargeToUserAccount(){
		try {
			dataProcessService.handleOrderInfoBatchRechargeToUserAccount();
		} catch (Exception e) {
			logger.error("处理线下申请旧数据信息异常", e);
		}
	}
	@RequestMapping(value = "/countBatchRechargeToUserAccount.action", method = RequestMethod.GET)
	@ResponseBody
	public String countBatchRechargeToUserAccount(){
		String count = "";
		try {
			count = dataProcessService.countBatchRechargeToUserAccount();
		} catch (Exception e) {
			logger.error("处理线下申请旧数据信息异常", e);
		}
		return count;
	}

}
