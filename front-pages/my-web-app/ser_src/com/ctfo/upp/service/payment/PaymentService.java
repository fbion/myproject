package com.ctfo.upp.service.payment;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.recharge.bean.TradeApplyDto;


/**
* 类描述：  线下流程管理-扣款 service
* @author：lipeng01  
* @date：2014-12-4 下午3:21:00    
* @version 1.0
* @since JDK1.6
*/ 
@AnnotationName(name = "线下流程管理-扣款")
public interface PaymentService{
	/**
	 * 添加扣款
	 * @param model loginUser
	 * 		OfflineRechargeApply 扣款实体
	 * 		loginUser 当前登录用户
	 * @return PaginationResult
	 * 		返回对象，页面取其中message属性
	 * @throws UPPException
	 * 		添加扣款失败异常
	 */
	@AnnotationName(name = "添加扣款")
	public PaginationResult<?> addPayment(TradeApplyDto model,String loginUser) throws UPPException;
	/**
	 * 修改扣款
	 * @param model
	 * 			OfflineRechargeApply  扣款对象实体
	 * @param loginUser
	 * 			当前登录用户
	 * @return PaginationResult
	 * 			返回对象，页面只取其中message值
	 * @throws UPPException
	 * 			修改失败异常
	 */
	@AnnotationName(name = "修改扣款操作")
	public PaginationResult<?> modifyPayment(TradeApplyDto model,String loginUser) throws UPPException;
	/**
	 * 条件查询扣款数据
	 * @param requestParam
	 * 		查询条件
	 * @return	PaginationResult
	 * 		分页对象
	 * @throws UPPException
	 */
	@AnnotationName(name = "条件分页查询扣款")
	public PaginationResult<?> queryPayment(DynamicSqlParameter requestParam)throws UPPException;
	/**
	 * 通过ID查询扣款数据
	 * @param id
	 * 		扣款对象ID
	 * @return	OfflineRechargeApply
	 * 		扣款对象实体
	 * @throws UPPException
	 */
	@AnnotationName(name = "通过ID查询扣款对象")
	public OfflineRechargeApply queryPaymentById(String id)throws UPPException;
	/**
	 * 修改扣款操作步骤
	 * @param model user
	 * 		扣款对象OfflineRechargeApply,user为操作人
	 * @return PaginationResult
	 * @throws UPPException
	 */
	@AnnotationName(name = "修改操作步骤")
	public PaginationResult<?> updateStepNp(OfflineRechargeApply model,String user) throws UPPException;
	
}
