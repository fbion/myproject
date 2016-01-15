package com.ctfo.upp.service.param;

import java.util.List;

import com.ctfo.base.dao.beans.UPParam;
import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;


/***
 * 系统设置-参数设置管理
 * @version 1.0
 * @author liuguozhong
 * @date   2014-11-19
 * @since JDK1.6
 */
@AnnotationName(name = "系统设置-参数设置管理")
public interface ParamService {

	/**
	 *  增加参数设置
	 * @param model
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "增加参数设置")
	public PaginationResult<?> addParam(Object model)throws UPPException;
	/**
	 * 修改参数设置
	 * @param model
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "修改参数设置")
	public PaginationResult<?> updateParam(UPParam model)throws UPPException;
	/**
	 * 删除参数设置
	 * @param model
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "删除参数设置")
	public PaginationResult<?> deleteParam(String ids)throws UPPException;
	
	/**
	 * 根据查询参数配置
	 * @param paramUUID
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "根据ID查询参数配置")
	public PaginationResult<?> queryParamById(String  paramUUID)throws UPPException;
	
	/**
	 * 条件查询参数配置集合
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件查询参数配置集合")
	public List<UPParam> queryParam(DynamicSqlParameter requestParam)throws UPPException;
	
	/**
	 * 条件分页查询参数配置集合
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件分页查询参数配置集合")
	public PaginationResult<?> queryParamByPage(DynamicSqlParameter requestParam)throws UPPException;
	/**
	 * 校验参数名称是否重复
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "校验参数名称是否重复")
	public String checkExist(UPParam model)throws UPPException;
}
