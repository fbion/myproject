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

import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.approve.ApproveService;
import com.sinoiov.upp.service.dto.ApprovalDto;
/**
 * 审批流程控制层
 * @author lipeng01
 *
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/approveInfo")
public class ApproveInfoController extends BaseController{
	private static Log logger = LogFactory.getLog(ApproveInfoController.class);
	PaginationResult<OfflineRechargeApplyApproval> result = new PaginationResult<OfflineRechargeApplyApproval>();
	public ApproveInfoController() {
		model = new OfflineRechargeApplyApproval();
	}
	@Resource(name = "approveService", description = "service充值接口")
	private ApproveService approveService;
	
	@RequestMapping(value = "/findById.action",produces = "application/json")
	@ResponseBody
	public OfflineRechargeApplyApproval findById(@RequestParam String id){
		OfflineRechargeApplyApproval approval = new OfflineRechargeApplyApproval();
		try {
			approval = approveService.findById(id);
		} catch (UPPException e) {
			logger.error("通过ID查询审批详情异常",e);
		}
		return approval;
	}
	/**
	 * 通过扣款ID查询审批结果
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findByRechargeApplyId.action",produces = "application/json")
	@ResponseBody
	public List<ApprovalDto> findByRechargeApplyId(@RequestParam String id,@RequestParam String approvalUserId){
		List<ApprovalDto> approval = new ArrayList<ApprovalDto>();
		try {
			approval = approveService.findByRechargeApplyId(id,approvalUserId);
		} catch (UPPException e) {
			logger.error("通过扣款ID查询审批详情异常",e);
		}
		return approval;
	}
	
}
