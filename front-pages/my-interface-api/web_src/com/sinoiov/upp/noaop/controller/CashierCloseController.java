package com.sinoiov.upp.noaop.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Scope("prototype")
@Controller
@RequestMapping(value = "/cashierCloseController")
public class CashierCloseController {
	/**
	 * 充值成功，易宝跳转此页面,并且关闭
	 * @return
	 */
	@RequestMapping(value="successClose.action")
	public String successClose(){
		return "successClose";
	}
}
