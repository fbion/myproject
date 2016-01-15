package com.ctfo.upp.service.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.base.dao.beans.UPParam;
import com.ctfo.base.dao.beans.UPParamExampleExtended;
import com.ctfo.base.intf.internal.ParamManager;
import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.Converter;

/***
 * 类描述：参数设置serviceImpl
 * 
 * @author：liuguozhong
 * @date：2014-11-20 下午1:29:00
 * @version 1.0
 */
@Service("paramService")
public class ParamServiceImpl implements ParamService {

	private static Log logger = LogFactory.getLog(ParamServiceImpl.class);

	private ParamManager paramManager = null;

	private ParamManager getParamManager() {
		if (this.paramManager == null) {
			paramManager = (ParamManager) ServiceFactory.getFactory().getService(ParamManager.class);
		}
		return this.paramManager;
	}
	

	@Override
	@AnnotationName(name = "增加参数设置")
	public PaginationResult<?> addParam(Object model) throws UPPException {
		PaginationResult<Object> result = new PaginationResult<Object>();
		UPParam param = null;
		try {
			
			
			
			param = getParamManager().createParam((UPParam) model);
			result.setDataObject(param);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("添加的参数UPParam表异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("添加参数异常", e);
		}
		return result;
	}

	@Override
	@AnnotationName(name = "修改参数设置")
	public PaginationResult<?> updateParam(UPParam model) throws UPPException {
		PaginationResult<Object> result = new PaginationResult<Object>();
		try {
			getParamManager().modifyParam(model);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("修改参数异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("修改参数异常", e);
		}
		return result;
	}

	@Override
	@AnnotationName(name = "删除参数设置")
	public PaginationResult<?> deleteParam(String ids) throws UPPException {
		PaginationResult<Object> result = new PaginationResult<Object>();
		try {
			getParamManager().removeParam(ids);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("删除参数异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("删除参数异常", e);
		}
		return result;
	}

	@Override
	@AnnotationName(name = "根据ID查询参数配置")
	public PaginationResult<?> queryParamById(String id) throws UPPException {
		PaginationResult<Object> result = new PaginationResult<Object>();
		try {
			UPParam param = getParamManager().getParamById(id);
			result.setDataObject(param);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("根据ID查询参数异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("根据ID查询参数异常", e);
		}
		return result;
	}

	@Override
	@AnnotationName(name = "条件查询参数配置集合")
	public List<UPParam> queryParam(DynamicSqlParameter requestParam) throws UPPException {
		UPParamExampleExtended exampleExtended = new UPParamExampleExtended();
		List<UPParam> list = new ArrayList<UPParam>();
		try {
			// 封装查询条件
			Converter.paramToExampleExtended(requestParam, exampleExtended);
			list = getParamManager().queryParam(exampleExtended);
		} catch (Exception e) {
			logger.error("条件查询参数配置集合异常。");
			throw new UPPException("条件查询参数配置集合异常", e);
		}
		return list;
	}

	@Override
	@AnnotationName(name = "条件分页查询参数配置集合")
	public PaginationResult<?> queryParamByPage(DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<UPParam> result = new PaginationResult<UPParam>();
		UPParamExampleExtended exampleExtended = new UPParamExampleExtended();

		try {
			// 封装查询条件
			Converter.paramToExampleExtended(requestParam, exampleExtended);
			result = getParamManager().queryParamByPage(exampleExtended);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("条件分页查询参数配置集合异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("条件分页查询参数配置集合异常", e);
		}
		return result;
	}

	public String checkExist(UPParam model) throws UPPException {
		// 如果model的ID存在值，则为修改，不用校验。
		if (model.getId() != null && !"".equals(model.getId())) {//修改
			UPParam param = getParamManager().getParamById(model.getId());
			boolean existFlag=model.getParamName().equalsIgnoreCase(param.getParamName());
			if(!existFlag){
			 return	existParamName(model);
			}
		} else {//增加
			return existParamName(model);
		}
		return "false";
	}

	private String existParamName(UPParam model) {
		try {
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String, String> equal = new HashMap<String, String>();
			equal.put("paramName", model.getParamName());
			requestParam.setEqual(equal);
			List<UPParam> result = queryParam(requestParam);
			if (result.size() > 0) {
				return "true";
			}
		} catch (Exception e) {
			logger.error("参数查找数据异常", e);
		}
		return "false";
	}
}
