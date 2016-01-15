package com.ctfo.upp.service.callback;

import com.ctfo.base.dao.beans.UPPlatformCallbackUrl;
import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;


/**
* 类描述：  商户管理-回调 service
* @author：lipeng01  
* @date：2014-12-3 下午4:31:00    
* @version 1.0
* @since JDK1.6
*/ 
@AnnotationName(name = "商户管理-回调")
public interface CallbackService{
	/**
	 * 修改码表状态
	 * @param simpleCode
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "添加回调")
	public PaginationResult<UPPlatformCallbackUrl> callbackAdd(UPPlatformCallbackUrl model) throws UPPException;
	/**
	 * 通过商户ID查找回调分页信息
	 * @param storeId
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "通过商户ID查找回调分页信息")
	public PaginationResult<UPPlatformCallbackUrl> callbackList(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 通过ID查找回调对象
	 * @param id
	 * 		回调对象ID
	 * @return
	 * @throws UPPException
	 */
	@AnnotationName(name = "通过ID查找回调信息")
	public UPPlatformCallbackUrl findCallbackById(String id) throws UPPException;
	/**
	 * 修改回调对象
	 * @param model
	 * 		回调对象实体
	 * @return
	 * 	PaginationResult
	 * @throws UPPException
	 */
	@AnnotationName(name = "修改回调对象")
	public PaginationResult<UPPlatformCallbackUrl> callbackEdit(UPPlatformCallbackUrl model)throws UPPException;
	/**
	 * 删除回调对象
	 * @param id
	 * 		回调对象ID
	 * @return PaginationResult
	 * 		返回对象，前台页面要取其中的message字段
	 * @throws UPPException
	 */
	@AnnotationName(name = "删除回调对象")
	public PaginationResult<UPPlatformCallbackUrl> delCallback(String id)throws UPPException;
	
	
}
