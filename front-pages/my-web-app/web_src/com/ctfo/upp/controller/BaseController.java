package com.ctfo.upp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.IService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.RequestParamsUtil;
import com.ctfo.upp.view.beans.Index;


/***
* 类描述：Controller 基类
* @author：liuguozhong  
* @date：2015年1月6日下午4:38:25 
* @version 1.0
* @since JDK1.6
*/ 
public abstract class BaseController {

	protected HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() ;
	protected HttpServletResponse response;
	protected HttpSession session=request.getSession() ;
	protected Index index = (Index)session.getAttribute(Converter.SESSION_INDEX);
	protected Operator op = Converter.getOperator(request);
	private final int INTERVAL_SERVER_ERROR_CODE=500;
	protected final String EMPTY_STRING="";
	private static final String REDIRECT ="redirect:";
	private static final String SUFFIX =".jsp";
	
	
	protected Object model;
	
	//@Resource
	protected IService service;
	protected String ids;
	
	protected String message;
	
	protected PaginationResult<?> result = new PaginationResult<Object>();
	
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		RequestParamsUtil.bindParams(this,this.request,null);
	}
	protected String forward(String uri){
		return uri;
	}
	
	protected String forwardToJsp(String uri){
		return forward(uri);
	}
	
	public String redirect(String uri){
		return REDIRECT+uri;
	}
	
	public String redirectJsp(String uri){
		return redirect(uri)+SUFFIX;
	}
	
	final protected void setMessage(String errorMessage){
		response.setStatus(INTERVAL_SERVER_ERROR_CODE);
    	request.setAttribute("message", (errorMessage==null?"":errorMessage));
	}
	final protected String forwardError()
    {
    	return forwardToJsp("/pages/error");
    }

	final protected String forwardError(String errorMessage)
    {
		setMessage(errorMessage);
    	return forwardToJsp("/pages/error");
    }


	public PaginationResult<?> getResult() {
		return result;
	}

	public void setResult(PaginationResult<?> result) {
		this.result = result;
	}
	
	public Object getModel() {
		return model;
	}
	public void setModel(Object model) {
		this.model = model;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getMessage() {
		return message;
	}

}
