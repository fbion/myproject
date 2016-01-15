package com.ctfo.upp.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.base.dao.beans.UPParam;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.param.ParamService;

@Component
@Controller
@Scope("prototype")
@RequestMapping("/paramJson")
public class ParamController extends BaseController {

	private static Log logger = LogFactory.getLog(ParamController.class);
	@Resource(name = "paramService", description = "service参数接口")
	private ParamService paramService;
	private PaginationResult<?> result = new PaginationResult<Object>();
	
	public ParamController() {
		model=new UPParam();
	}

	/**
	 * 分页查询参数配置信息
	 * @return
	 */
	@RequestMapping(value="/queryParamList" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryParamByPage(DynamicSqlParameter requestParam){
		try {
			result = paramService.queryParamByPage(requestParam);
		} catch (Exception e) {
			result.setMessage("分页查询参数配置信息异常!");
			logger.error("分页查询参数配置信息异常!",e);
		}		
		return result;	
	}
	
	/**
	 * 输入 和输出为JSON格式的数据的方式
	 */
	@RequestMapping(value="/getParamById",produces = "application/json")
	@ResponseBody
	public PaginationResult<?> getParamById(@RequestParam("id") String id) {
		try {
			result= paramService.queryParamById(id);
		} catch (Exception e) {
			logger.error("查询参数表信息异常!", e);
		}
		return result;
	}
	/**
	 * 添加参数设置
	 * @return
	 */
	@RequestMapping(value="/addParam" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> addParam(@ModelAttribute UPParam model){
		try {	
			result = paramService.addParam(model);
		} catch (Exception e) {
			result.setMessage("添加参数设置异常!");
			logger.error("添加参数设置异常!",e);
		}		
		return result;	
	}
	/**
	 * 更新参数设置
	 * @return
	 */
	@RequestMapping(value="/updateParam" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> updateParam(@ModelAttribute UPParam model){
		try {	
			result = paramService.updateParam(model);
		} catch (Exception e) {
			result.setMessage("更新参数设置异常!");
			logger.error("更新参数设置异常!",e);
		}		
		return result;	
	}
	/**
	 * 删除参数设置
	 * @return
	 */
	@RequestMapping(value="/deleteParam" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> deleteParam(@RequestParam String ids){
		try {	
			result = paramService.deleteParam(ids);
		} catch (Exception e) {
			result.setMessage("删除参数设置异常!");
			logger.error("删除参数设置异常!",e);
		}		
		return result;	
	}
	/**
	 * 判断参数名是否唯一
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkExist.action")
	@ResponseBody
	public String checkExist(@ModelAttribute UPParam model) {
		try {
			return paramService.checkExist(model);
		} catch (UPPException e) {
			result.setMessage("参数名唯一判断异常!");
			logger.error("参数名唯一判断异常!",e);
		}
		return "false";
	}
}
