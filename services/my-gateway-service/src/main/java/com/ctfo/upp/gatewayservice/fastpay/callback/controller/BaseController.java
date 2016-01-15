package com.ctfo.upp.gatewayservice.fastpay.callback.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.ctfo.upp.gateway.fastpay.intf.AbstractFastPaymentFacade;

public class BaseController extends MultiActionController {
	static protected final String GATEWAY_CALLBACK_HANDLER_POOL_NAME = "__callback_handler_task_pool__";
	static protected final String YEEPAY_RETURN_STR = "success";
	static protected ResponseEntity<String> newResponseEntity(String responseText, HttpStatus status) {
		return new ResponseEntity<String>(responseText, (status == null ? HttpStatus.OK : status));
	}

	static protected ResponseEntity<String> newResponseEntity(String responseText) {
		return newResponseEntity(responseText, null);
	}

	@Resource
	private AbstractFastPaymentFacade fastPaymentServiceFacade;

	public void setFastPaymentServiceFacade(AbstractFastPaymentFacade fastPaymentServiceFacade) {
		this.fastPaymentServiceFacade = fastPaymentServiceFacade;
	}

	public AbstractFastPaymentFacade getFastPaymentServiceFacade() {
		return fastPaymentServiceFacade;
	}

}
