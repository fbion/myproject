package com.ctfo.upp.service.merchant;

import java.util.List;

import com.ctfo.base.dao.beans.UPPlatform;
import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.excelbeans.MerchantExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;

/**
* 类描述：  商户管理-商户管理 service
* @author：lipeng01  
* @date：2014-11-28 上午9:40:04    
* @version 1.0
* @since JDK1.6
*/ 
@AnnotationName(name = "系统设置-编码设置")
public interface MerchantService {
	/**
	 * 条件查询商户数据集合
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件查询编码数据")
	public List<UPPlatform> queryMerchantByParam(DynamicSqlParameter requestParam)throws UPPException;
	/**
	 * 根据商户名称查看此名是否存在
	 * @param model
	 * 			商户实体 UPPlatform
	 * @return true or false
	 * @throws UPPException
	 */
	@AnnotationName(name = "查看商户名称是否存在")
	public boolean checkExist(UPPlatform model) throws UPPException;
	/**
	 * 添加商户到数据库
	 * @param model userName
	 * 			商户实体   操作人
	 * @return PaginationResult
	 * 			返回此商户秘钥和商户编码，用于页面显示商户中的秘钥和商户编码
	 * @throws UPPException
	 * 			添加商户信息时异常
	 */
	@SuppressWarnings("rawtypes")
	@AnnotationName(name = "添加商户信息")
	public PaginationResult addMerchant(UPPlatform model,String userName)throws UPPException;
	/**
	 * 分页查询所有商户信息
	 * @param requestParam
	 * 			查询条件对象
	 * @return PaginationResult
	 * 			分页对象
	 * @throws UPPException
	 * 			查询所有商户信息时异常
	 */
	@AnnotationName(name = "分页查询所有商户信息")
	public PaginationResult<UPPlatform> queryMerchantAll(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 按ID删除商户信息
	 * @param ids
	 * 		商户ID
	 * @return PaginationResult
	 * 		返回对象
	 * @throws UPPException
	 */
	@AnnotationName(name = "按ID删除商户信息")
	public PaginationResult<UPPlatform> delMerchant(String ids) throws UPPException;
	/**
	 * 通过ID查找商户信息
	 * @param id
	 * 		商户ID
	 * @return UPPlatform
	 * 			商户实体
	 * @throws UPPException
	 */
	@AnnotationName(name = "通过ID查找商户信息")
	public UPPlatform getMerchantById(String id) throws UPPException;
	/**
	 * 修改商户信息
	 * @param model
	 * 		商户实体
	 * @throws UPPException
	 */
	@AnnotationName(name = "修改商户信息")
	public void editMerchant(UPPlatform model) throws UPPException;
	/**
	 * 修改商户状态
	 * @param model
	 * 		商户信息实体UPPlatform 
	 * @return paginationResult
	 * 		
	 * @throws UPPException
	 */
	@AnnotationName(name = "修改商户状态")
	public PaginationResult<UPPlatform> modifyStatusMerchant(UPPlatform model) throws UPPException;
	/**
	 * 通过ID导出单条商户数据
	 * @param id
	 * @return List<MerchantExcel>
	 * @throws UPPException
	 */
	public List<MerchantExcel> exportById(String id) throws UPPException;
	/**
	 * 通过条件导出所有数据
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public List<MerchantExcel> exportAll(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 初始化密钥
	 * @throws UPPException
	 */
	public void initKeys() throws UPPException;
}
