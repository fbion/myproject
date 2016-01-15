package com.ctfo.upp.service.approve;

import java.util.List;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.service.dto.ApprovalDto;


/**
* 类描述：  线下流程管理-扣款审批 service
* @author：lipeng01  
* @date：2014-12-4 下午3:21:00    
* @version 1.0
* @since JDK1.6
*/ 
@AnnotationName(name = "线下流程管理-扣款审批")
public interface ApproveService{
	/**
	 * 添加扣款审批
	 * @param model loginUser
	 * 		OfflineRechargeApplyApproval 扣款审批实体
	 * 		loginUser 当前登录用户
	 * @return PaginationResult
	 * 		返回对象，页面取其中message属性
	 * @throws UPPException
	 * 		添加扣款审批失败异常
	 */
	@AnnotationName(name = "添加扣款审批")
	public PaginationResult<OfflineRechargeApplyApproval> addPayment(OfflineRechargeApplyApproval model,String loginUser) throws UPPException;
	/**
	 * 通过条件查询审批详情
	 * @param requestParam
	 * 			DynamicSqlParameter 条件对象
	 * @return List<OfflineRechargeApplyApproval>
	 * 			审批详情集合
	 * @throws UPPException
	 */
	@AnnotationName(name = "通过条件查询审批详情")
	public List<OfflineRechargeApplyApproval> queryByRechargeApplyId(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 通过ID查询审批详情
	 * @param id
	 * 		OfflineRechargeApplyApproval 审批表主键ID
	 * @return	OfflineRechargeApplyApproval
	 * @throws UPPException
	 */
	@AnnotationName(name = "通过ID查询审批详情")
	public OfflineRechargeApplyApproval findById(String id)throws UPPException;
	/**
	 * 通过扣款ID和步骤号查询对应角色的审批详情
	 * @param id
	 * @param approvalUserId
	 * @return List<OfflineRechargeApplyApproval>
	 * @throws UPPException
	 */
	@AnnotationName(name = "通过扣款ID查询审批详情")
//	public List<OfflineRechargeApplyApproval> findByRechargeApplyId(String id,String approvalUserId)throws UPPException;
	public List<ApprovalDto> findByRechargeApplyId(String id,String approvalUserId)throws UPPException;
	
}
