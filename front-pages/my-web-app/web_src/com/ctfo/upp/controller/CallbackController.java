package com.ctfo.upp.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.base.dao.beans.UPPlatformCallbackUrl;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.callback.CallbackService;
import com.ctfo.upp.util.Converter;

/**
 * 回调Controller
 * @author lipeng01
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/callback")
public class CallbackController extends BaseController {

	private static Log logger = LogFactory.getLog(CallbackController.class);
	private PaginationResult<UPPlatformCallbackUrl> result = new PaginationResult<UPPlatformCallbackUrl>();
	@Resource
	private CallbackService callbackService ;
	public CallbackController() {
		model = new UPPlatformCallbackUrl();
	}
	/**
	 * 添加回调信息
	 * @return
	 */
	@RequestMapping(value = "/callbackAdd.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<UPPlatformCallbackUrl> callbackAdd(){
		try {
			result = callbackService.callbackAdd((UPPlatformCallbackUrl) model);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (UPPException e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("添加回调异常",e);
		}
		return result;
	}
	@RequestMapping(value = "/callbackList.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<UPPlatformCallbackUrl> callbackList(DynamicSqlParameter requestParam){
		try {
			result = callbackService.callbackList(requestParam);
		} catch (UPPException e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("分页查询回调信息异常",e);
		}
		return result;
	}
	@RequestMapping(value = "/getCallbackById.action")
	@ResponseBody
	public UPPlatformCallbackUrl getCallbackById(@RequestParam String id){
		UPPlatformCallbackUrl url = new UPPlatformCallbackUrl();
		try {
			url = callbackService.findCallbackById(id);
		} catch (UPPException e) {
			logger.error("查找回调信息失败",e);
		}
		return url;
	}
	@RequestMapping(value = "/callbackEdit", produces = "application/json")
	@ResponseBody
	public PaginationResult<UPPlatformCallbackUrl> callbackEdit(){
		try {
			result = callbackService.callbackEdit((UPPlatformCallbackUrl) model);
		} catch (UPPException e) {
			logger.error("修改回调对象失败",e);
		}
		return result;
	}
	@RequestMapping(value = "/delCallback.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<UPPlatformCallbackUrl> delCallback(@RequestParam String id){
		try {
			result = callbackService.delCallback(id);
		} catch (UPPException e) {
			logger.error("删除回调对象异常",e);
		}
		return result;
	}
}
