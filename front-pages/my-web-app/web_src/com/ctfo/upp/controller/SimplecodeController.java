package com.ctfo.upp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.simplecode.ISimplecodeService;

/**
 * 码表控制层
 * 
 * @author lipeng01
 * 
 */

@Scope("prototype")
@Controller
@RequestMapping(value = "/simpleCode")
public class SimplecodeController extends BaseController {

	private static Log logger = LogFactory.getLog(SimplecodeController.class);
	@Resource
	private ISimplecodeService simpleCodeService;

	public SimplecodeController() {
		model = new SimpleCode();
	}

	/**
	 * 输入 和输出为JSON格式的数据的方式
	 * 
	 */
	@RequestMapping(value = "/getCodeById.action", produces = "application/json")
	@ResponseBody
	public SimpleCode getCodeById(@RequestParam("id") String id) {
		SimpleCode simpleCode = null;

		try {
			simpleCode = simpleCodeService.queryCodeById(id);
		} catch (Exception e) {
			logger.error("查询码表信息异常!", e);
		}

		return simpleCode;

	}

	/**
	 * 进入码表数据页面
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@ResponseBody
	@RequestMapping(value = "/getCodeList.action", produces = "application/json")
	public PaginationResult<?> getCodeList(DynamicSqlParameter requestParam) {
		PaginationResult result = null;
		try {
			result = simpleCodeService.queryCodeByPage(requestParam);
		} catch (Exception e) {
			result.setMessage(e.getLocalizedMessage());
			logger.error("分页查询码表信息异常", e);
		}
		return result;
	}

	/**
	 * 添加码表数据
	 * 
	 * @param simpleCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addCode.action", produces = "application/json")
	public PaginationResult<SimpleCode> addCode() {
		PaginationResult<SimpleCode> result = new PaginationResult<SimpleCode>();
		try {
			result = (PaginationResult<SimpleCode>) simpleCodeService.addCode((SimpleCode) model);
		} catch (Exception e) {
			logger.error("添加码表数据异常", e);
		}
		return result;
	}

	/**
	 * 树结构查询
	 * 
	 * @param requestParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findAllCode.action", produces = "application/json")
	@ResponseBody
	public List<SimpleCode> findAllCode(DynamicSqlParameter requestParam) {
		List<SimpleCode> result = new ArrayList<SimpleCode>();
		try {
			result = (List<SimpleCode>) simpleCodeService.queryCodeByParam(requestParam);
		} catch (Exception e) {
			logger.error("参数查找码表数据异常", e);
		}
		return result;
	}
	@RequestMapping(value = "/findMerchant.action",produces = "application/json")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<SimpleCode> findMerchant(DynamicSqlParameter requestParam){
		List<SimpleCode> result = new ArrayList<SimpleCode>();
		try {
			result = (List<SimpleCode>) simpleCodeService.queryCodeByParam(requestParam);
		} catch (Exception e) {
			logger.error("参数查找码表数据异常", e);
		}
		return result;
	}

	/**
	 * 判断code字段是否唯一
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkExist.action")
	@ResponseBody
	public String checkExist(SimpleCode model) {
		boolean checkResult = false;
		//如果model的ID存在值，则为修改，不用校验。
		if(model.getId()!=null&&!"".equals(model.getId())){
			return "true";
		}else{
			try {
				checkResult = simpleCodeService.checkExist(model.getTypeCode(),model.getCode());
				if (checkResult) {
					return "true";
				}
			} catch (Exception e) {
				logger.error("参数查找码表数据异常", e);
			}
		}
		return "false";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyStatus.action")
	@ResponseBody
	public PaginationResult<SimpleCode> modifyStatus() {
		PaginationResult<SimpleCode> result = new PaginationResult<SimpleCode>();
		try {
			result = (PaginationResult<SimpleCode>) simpleCodeService.modifyStatus((SimpleCode) model);
		} catch (Exception e) {
			logger.error("码表状态修改异常", e);
		}
		return result;
	}

	@RequestMapping(value = "/deleteCode.action", produces = "application/json")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PaginationResult<SimpleCode> deleteCode(@RequestParam("id") String ids) {
		PaginationResult<SimpleCode> result = new PaginationResult<SimpleCode>();
		try {
			result = (PaginationResult<SimpleCode>) simpleCodeService.deleteCode(ids);
		} catch (Exception e) {
			logger.error("删除码表信息失败", e);
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editCode.action",produces = "application/json")
	@ResponseBody
	public PaginationResult<SimpleCode> editCode(){
		PaginationResult<SimpleCode> result = new PaginationResult<SimpleCode>();
		try {
			result = (PaginationResult<SimpleCode>) simpleCodeService.updateCode((SimpleCode) model);
		} catch (UPPException e) {
			logger.error("更新码表信息失败",e);
		}
		return result;
	}

}
