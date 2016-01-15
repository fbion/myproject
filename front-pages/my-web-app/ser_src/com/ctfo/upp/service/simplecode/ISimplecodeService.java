package com.ctfo.upp.service.simplecode;

import java.util.List;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;


/**
* 类描述：  系统设置-编码设置 service
* @author：lipeng01  
* @date：2014-11-20 下午1:31:04    
* @version 1.0
* @since JDK1.6
*/ 
@AnnotationName(name = "系统设置-编码设置")
public interface ISimplecodeService{
	/**
	 * 添加编码数据
	 * @param simpleCode
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "添加编码数据")
	public PaginationResult<?> addCode(SimpleCode simpleCode) throws UPPException;
	/**
	 * 修改码表数据
	 * @param simpleCode
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "修改编码数据")
	public PaginationResult<?> updateCode(SimpleCode simpleCode) throws UPPException;
	/**
	 * 删除编码数据
	 * @param ids
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "删除编码数据")
	public PaginationResult<?> deleteCode(String ids) throws UPPException;
	/**
	 * 根据ID查找编码数据
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "根据ID查找")
	public SimpleCode queryCodeById(String id) throws UPPException;
	/**
	 * 条件查询编码数据
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件查询编码数据")
	public List<?> queryCodeByParam(DynamicSqlParameter requestParam)throws UPPException;
	/**
	 * 条件分页查询编码数据
	 * @param requestParam
	 * @param model
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件分页查询编码数据")
	public PaginationResult<?> queryCodeByPage(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 检查对象属性是否唯一
	 * @param code typeCode
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "检查对象属性是否唯一")
	public boolean checkExist(String typeCode,String code) throws UPPException;
	/**
	 * 修改码表状态
	 * @param simpleCode
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "修改码表状态")
	public PaginationResult<?> modifyStatus(SimpleCode simpleCode) throws UPPException;
	/**
	 * 通过编码查询对应名称
	 * @param code
	 * @return String name
	 * @throws UPPException
	 */
	public String getNameBycode(String code) throws UPPException;
	/**
	 * 通过父类编码，查找其所有子类
	 * @param code
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "通过父类编码，查找其所有子类")
	public List<SimpleCode> getByFCode(String code) throws UPPException;
	/**
	 * 通过父类编码及本身code查找
	 * @param typeCode
	 * @param code
	 * @return SimpleCode
	 * @throws UPPException
	 */
	
	@AnnotationName(name = "通过父类编码及本身code查找")
	public SimpleCode getByTypeCodeAndCode(String typeCode,String code) throws UPPException;
	
}
