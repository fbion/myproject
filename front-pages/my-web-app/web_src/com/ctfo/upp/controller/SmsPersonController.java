package com.ctfo.upp.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.ctfo.base.dao.beans.UPPlatform;
import com.ctfo.base.dao.beans.UPPlatformCallbackUrl;
import com.ctfo.file.bean.DynamicMongoParameter;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.UUIDUtil;
import com.ctfo.upp.service.sms.SmsPersonService;
import com.ctfo.upp.service.sms.bean.Sms;
import com.ctfo.upp.service.sms.bean.SmsPerson;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.PasswordUtils;

/**
 * 短信模板页面
 * 
 * @author lipeng
 */
@Controller
@Scope("prototype")
@SuppressWarnings("all")
@RequestMapping(value = "/smsPerson")
public class SmsPersonController extends BaseController{

	private static Log logger = LogFactory.getLog(SmsPersonController.class);
	
	@Autowired
	private SmsPersonService smsPersonService;
	public SmsPersonController() {
		model = new SmsPerson();
	}
	/**
	 * 进入列表页面
	 */
	@RequestMapping(value = "/personList", method = RequestMethod.GET)
	public ModelAndView personList(HttpServletRequest request, ModelAndView mv)throws UnsupportedEncodingException {
		mv.setViewName("pages/sms/personList");
		return mv;
	}
	@RequestMapping(value = "/ajaxPersonList", method = RequestMethod.POST)
	@ResponseBody
	public PaginationResult<SmsPerson> list(DynamicMongoParameter requestMongoParam){
		PaginationResult<SmsPerson> result=null;
		JSONArray jsonArray = null;
		try {
			result = this.smsPersonService.query(requestMongoParam);
			
		} catch (Exception e) {
			logger.error("查询短信息异常!", e);
		}
		return result;
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public PaginationResult<SmsPerson> add(){
		
		PaginationResult<SmsPerson> result = new PaginationResult<SmsPerson>();
		String uuid = UUIDUtil.newUUID();
		SmsPerson sms = (SmsPerson) model;
		try {
			sms.setUuid(uuid);
			smsPersonService.add(sms);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (UPPException e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("添加短信联系人信息异常",e);
		}
		return result;
	}
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public PaginationResult<SmsPerson> del(@RequestParam("id") String id){
		PaginationResult<SmsPerson> result = new PaginationResult<SmsPerson>();
		try {
			if(!"".equals(id)){
				smsPersonService.delete(id);
			}
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("删除联系人信息异常!", e);
			result.setMessage(Converter.OP_FAILED);
		}
		return result ;
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public PaginationResult<SmsPerson> modify(){
		PaginationResult<SmsPerson> result = new PaginationResult<SmsPerson>();
		SmsPerson sms = (SmsPerson) model;
		try {
			SmsPerson smsPerson = smsPersonService.queryById(sms.getUuid());
			smsPerson.setJob(sms.getJob());
			smsPerson.setMobileNo(sms.getMobileNo());
			smsPerson.setName(sms.getName());
			smsPersonService.update(smsPerson);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("修改短信联系人信息异常!", e);
			result.setMessage(Converter.OP_FAILED);
		}
		return result ;
	}
	/**
	 * 添加日期和时间
	 */
	@ResponseBody
	@RequestMapping(value = "/addTime", method = RequestMethod.POST)
	public PaginationResult<SmsPerson> addTime(){
		PaginationResult<SmsPerson> result = new PaginationResult<SmsPerson>();
		SmsPerson sms = (SmsPerson) model;
		try {
			SmsPerson smsPerson = smsPersonService.queryById(sms.getUuid());
			smsPerson.setDay(sms.getDay());
			smsPerson.setStartTime(sms.getStartTime());
			smsPerson.setEndTime(sms.getEndTime());
			smsPersonService.update(smsPerson);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("修改短信联系人信息异常!", e);
			result.setMessage(Converter.OP_FAILED);
		}
		return result ;
	}
	/**
	 * 通过ID查询
	 */
	@ResponseBody
	@RequestMapping(value = "/getSmsPerson", method = RequestMethod.POST)
	public SmsPerson queryById(@RequestParam String id){
		SmsPerson smsPerson = new SmsPerson();
		try {
			smsPerson = smsPersonService.queryById(id);
		} catch (Exception e) {
			logger.error("查找短信联系人信息异常!", e);
			result.setMessage(Converter.OP_FAILED);
		}
		return smsPerson ;
	}
	/**
	 * 进入页面
	 *//*
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
	
	*//**
	 * 添加页面
	 *//*
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, ModelAndView mv)throws UnsupportedEncodingException {
		mv.setViewName("pages/sms/add");
		return mv;
	}
	
	*//**
	 * 添加操作
	 * flag:1存在，2成功
	 *//*
	@ResponseBody
	@RequestMapping(value = "/ajaxAdd", method = RequestMethod.POST)
	public String ajaxAdd(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap,
			@RequestParam("templateName") String templateName,
			@RequestParam("templateContent") String templateContent,
			@RequestParam("status") Integer status
			) throws Exception {
		String flag = "";
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
	 
	*//**
	 * 修改页面
	 *//*
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
	
	*//**
	 * 修改操作
	 *//*
	@ResponseBody
	@RequestMapping(value = "/ajaxUpdate", method = RequestMethod.POST)
	public String ajaxUpdate(
			@RequestParam("templateCode") String templateCode,
			@RequestParam("templateName") String templateName,
			@RequestParam("templateContent") String templateContent,
			@RequestParam("status") Integer status
			) throws UnsupportedEncodingException {
		String flag = "0";
		try {
			Sms sms = (Sms)smsService.queryById(templateCode);
			if(sms!=null){
				sms.setTemplateName(templateName);
				sms.setTemplateContent(templateContent);
				sms.setStatus(status);
				smsService.update(sms);
				flag = "1";
			}
		} catch (Exception e) {
			logger.error("查询短信息模板异常! templateCode="+templateCode, e);
		}
		return flag;
	}
	
	*//**
	 * 删除
	 * flag :1成功，0失败
	 *//*
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
	
	*//**
	 * 启用/禁用
	 * flag :1成功，0失败
	 *//*
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
	
	
	*//**
	 * 查询页面
	 *//*
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
		
	}*/
	
}
