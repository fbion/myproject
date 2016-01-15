package com.ctfo.upp.service.recharge;

import com.ctfo.file.bean.AttachmentBean;
import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.recharge.bean.TradeApplyDto;
import com.sinoiov.upp.service.dto.ApplyDto;
import com.sinoiov.upp.service.dto.ApprovalDto;
import com.sinoiov.upp.service.dto.VoucherDto;

/**
 * 线下流程管理接口
 * 
 * @author jichao
 */
public interface RechargeService {

	/**
	 * 增加
	 */
	public PaginationResult<?> add(TradeApplyDto model ,String loginUser) throws UPPException;
	/***
	 * 上传凭证信息
	 * @param attachmentBean
	 * @return
	 * @throws UPPException
	 */
	public String uploadFile(AttachmentBean attachmentBean) throws UPPException;
	/***
	 * 根据输入的金额小写数字转为中文大写金额字符串
	 * @param str
	 * @return
	 * @throws UPPException
	 */
	public String getNum2CNString(String str)throws UPPException;
	/**
	 * 修改线下流程-充值信息
	 * @param model TradeApplyDto  loginUser
	 * @return  PaginationResult<TradeApplyDto>
	 * @throws UPPException
	 */
	public PaginationResult<TradeApplyDto> update(TradeApplyDto model,String user) throws UPPException;

	/**
	 * 更新申请信息
	 */
	public void modifyRechargeApply(OfflineRechargeApply rechargeApply,OfflineRechargeApplyApproval approval) throws UPPException;

	/**
	 * 按ID查询
	 */
	public TradeApplyDto queryById(String templateCode) throws UPPException;
	/**
	 * 根据申请ID查询申请信息
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	public OfflineRechargeApply getRechargeApplyById(String id) throws UPPException;
	/**
	 * 根据申请ID，查询凭证信息
	 */
//	public RechargeApplyVoucher queryVoucherById(String applyId) throws UPPException;
	public VoucherDto queryVoucherById(String applyId) throws UPPException;
	
	/**
	 * 根据申请ID，删除凭证信息
	 */
	public void removeVoucherById(String applyId) throws UPPException;
	/** 
	 * 条件查询
	 */
	public PaginationResult<?> queryByPage(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 修改操作步骤，当前操作人，申请状态，操作时间
	 */
	public PaginationResult<TradeApplyDto> editStepNo(TradeApplyDto model ,String user) throws UPPException;
	
	/**
	 * 验证凭证信息是否唯一
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public String isVoucherUnique(DynamicSqlParameter requestParam) throws UPPException;
	
	/**
	 * 线下充值申请审批
	 * @param model
	 * @param loginUser
	 * @throws UPPException
	 */
	public void addApproval(ApprovalDto approvalDto) throws UPPException;
	
	/**
	 * 添加凭证信息
	 * @param voucherDto
	 * @throws UPPException
	 */
	public void addVoucher(VoucherDto voucherDto) throws UPPException;
	/**
	 * 导出
	 * @param requestParam
	 * @throws UPPException
	 */
	public void exportExcel(DynamicSqlParameter requestParam) throws UPPException;


}
