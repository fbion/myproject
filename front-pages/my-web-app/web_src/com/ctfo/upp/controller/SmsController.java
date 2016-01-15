package com.ctfo.upp.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.ctfo.file.bean.DynamicMongoParameter;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.sms.SmsService;
import com.ctfo.upp.service.sms.bean.Sms;
import com.ctfo.upp.util.PasswordUtils;

/**
 * 短信模板页面
 * 
 * @author jichao
 */
@Controller
@SuppressWarnings("all")
@RequestMapping(value = "/sms")
public class SmsController extends MultiActionController{

	private static Log logger = LogFactory.getLog(SmsController.class);
	
	@Autowired
	private SmsService smsService;
	
	/**
	 * 进入页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, ModelAndView mv
			) throws UnsupportedEncodingException {
		
		PaginationResult<Sms> result=null;
		JSONArray jsonArray = null;
		DynamicMongoParameter requestMongoParam = new DynamicMongoParameter();
		try {
			result = this.smsService.query(requestMongoParam);
			jsonArray = JSONArray.fromObject(result);
			
		} catch (Exception e) {
			logger.error("查询短信息异常!", e);
		}
		mv.addObject("list",jsonArray);
		
		mv.setViewName("pages/sms/index");
		return mv;
	}
	
	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, ModelAndView mv)throws UnsupportedEncodingException {
		mv.setViewName("pages/sms/add");
		return mv;
	}
	
	/**
	 * 添加操作
	 * flag:1存在，2成功
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxAdd", method = RequestMethod.POST)
	public String ajaxAdd(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap,
			@RequestParam("templateName") String templateName,
			@RequestParam("templateContent") String templateContent,
			@RequestParam("status") Integer status
			) throws Exception {
		String flag = "";
		//修改模板内容，将{}替换为<>
		templateContent = templateContent.replace("{_}", "<_>");
		if(smsService.isExists(templateName)){
			flag = "1";
		}else{
			flag = "false";
			Sms sms = new Sms();
			String templateCode="pay"+PasswordUtils.resetRandomPassword();
			sms.setTemplateCode(templateCode);
			sms.setTemplateName(templateName);
			sms.setTemplateContent(templateContent);
			sms.setStatus(status);
			try {
				smsService.add(sms);
				flag = "2";
			} catch (UPPException e) {
				logger.error("添加短信模板失败！",e);
			}
		}
		return flag;
		
	}
	 
	/**
	 * 修改页面
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request, ModelAndView mv,@RequestParam("code") String code) 
			throws UnsupportedEncodingException {
		mv.setViewName("pages/sms/update");
		try {
			mv.addObject(smsService.queryById(code));
		} catch (UPPException e) {
			logger.error("查询短信息模板异常! templateCode="+code, e);
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 修改操作
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxUpdate", method = RequestMethod.POST)
	public String ajaxUpdate(
			@RequestParam("code") String code,
			@RequestParam("templateCode") String templateCode,
			@RequestParam("templateName") String templateName,
			@RequestParam("templateContent") String templateContent,
			@RequestParam("status") Integer status
			) throws UnsupportedEncodingException {
		String flag = "0";
		//修改模板内容，将{}替换为<>
		templateContent = templateContent.replace("{_}", "<_>");
		try {
			Sms sms = smsService.queryById(code);
			smsService.delete(code);
			sms.setTemplateCode(templateCode);
			sms.setTemplateName(templateName);
			sms.setTemplateContent(templateContent);
			sms.setStatus(status);
			smsService.update(sms);
			flag = "1";
			
		} catch (Exception e) {
			logger.error("查询短信息模板异常! templateCode="+templateCode, e);
		}
		return flag;
	}
	
	/**
	 * 删除
	 * flag :1成功，0失败
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxDelete", method = RequestMethod.POST)
	public String ajaxDelete(
			HttpServletRequest request,
			@RequestParam("code") String code,
			@RequestParam("codes") String codes) 
			throws UnsupportedEncodingException {
		String flag = "0";
		try {
			if(!"".equals(code)){
				this.smsService.delete(code);
			}
			if(!"".equals(codes)){
				this.smsService.delete(codes);
			}
			flag = "1";
		} catch (Exception e) {
			logger.error("删除短信息模板异常! templateCode="+code, e);
		}
		return flag ;
	}
	
	/**
	 * 启用/禁用
	 * flag :1成功，0失败
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxSet", method = RequestMethod.POST)
	public String ajaxSet(@RequestParam("code") String code) throws UnsupportedEncodingException {
		String flag = "0";
		try {
			Sms sms = (Sms)smsService.queryById(code);
			if(sms.getStatus()==1){
				sms.setStatus(0);
			}else{
				sms.setStatus(1);
			}
			smsService.update(sms);
			flag = "1";
		} catch (Exception e) {
			logger.error("修改短信息模板状态异常!", e);
		}
		return flag;
	}
	
	
	/**
	 * 查询页面
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQuery", method = RequestMethod.POST,produces="text/plain;charset=utf-8")
	public String query(HttpServletRequest request, 
			ModelAndView mv,
			@RequestParam("templateCode")String templateCode,
			@RequestParam("templateName")String templateName)
			throws UnsupportedEncodingException {
		
		PaginationResult<Sms> result=null;
		JSONArray jsonArray = null;
		DynamicMongoParameter params = new DynamicMongoParameter();
		Map paramsMap = new HashMap<String, String>();
		paramsMap.put("templateCode", templateCode);
		paramsMap.put("templateName", templateName);
		params.setLike(paramsMap);
		try {
			result = this.smsService.query(params);
			jsonArray = JSONArray.fromObject(result);
			
		} catch (Exception e) {
			logger.error("查询短信息异常!", e);
		}
		
		return jsonArray.toString();
		
	}
	/**
	 * 查询页面
	 */
	@ResponseBody
	@RequestMapping(value = "/checkSmsCode", method = RequestMethod.POST,produces="text/plain;charset=utf-8")
	public boolean checkSmsCode(HttpServletRequest request, 
			ModelAndView mv,
			@RequestParam("templateCode")String templateCode
			)
			throws UnsupportedEncodingException {
		boolean flag = false;
		PaginationResult<Sms> result=null;
		JSONArray jsonArray = null;
		DynamicMongoParameter params = new DynamicMongoParameter();
		Map paramsMap = new HashMap<String, String>();
		paramsMap.put("templateCode", templateCode);
		params.setEqual(paramsMap);
		try {
			result = this.smsService.query(params);
			if(result!=null && !result.getData().isEmpty()){
				flag = false;
			}else{
				flag = true;
			}
		} catch (Exception e) {
			logger.error("查询短信息异常!", e);
		}
		
		return flag;
		
	}
	
}
