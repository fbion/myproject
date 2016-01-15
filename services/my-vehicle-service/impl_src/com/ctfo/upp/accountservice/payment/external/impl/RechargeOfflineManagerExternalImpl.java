package com.ctfo.upp.accountservice.payment.external.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApprovalExampleExtended;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExampleExtended;
import com.ctfo.payment.dao.beans.RechargeApplyVoucher;
import com.ctfo.payment.intf.internal.IRechargeOfflineManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;

//@Service("RechargeOfflineManagerExternalImpl")
public class RechargeOfflineManagerExternalImpl implements com.ctfo.payment.intf.external.IRechargeOfflineManager {

	private static Log logger = LogFactory.getLog(RechargeOfflineManagerExternalImpl.class);
	
	@Autowired
	@Qualifier("RechargeOfflineManagerImpl")
	private IRechargeOfflineManager rechargeOfflineManager;

	@Override
	public OfflineRechargeApply addRechargeApply(OfflineRechargeApply rechargeApply) throws UPPException {
		try {
			
			rechargeApply = rechargeOfflineManager.addRechargeApply(rechargeApply);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("增加线下充值申请表信息异常！",e);
			throw new UPPException("增加线下充值申请表信息异常");
		}
		
		return rechargeApply;
	}

	/***
	 * 1.调用对应的转账和扣款接口逻辑处理方法。 2.成功后更新充值申请表的订单相关字段信息。
	 */
	@Override
	public void modifyRechargeApply(OfflineRechargeApply rechargeApply) throws UPPException {
		try {
			
			rechargeOfflineManager.modifyRechargeApply(rechargeApply);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("修改线下充值申请表信息异常", e);
			throw new UPPException("修改线下充值申请表信息异常", e);
		}
	}
	
	/***
	 * 新加接口方法，修改问题：事务同步
	 * 离线审批申请
	 * 1.修改申请表信息。
	 * 2.增加审批信息。
	 * 3.充值/扣款调用相应转账接口。
	 * @param rechargeApply 离线充值申请模型
	 * @param rechargeApplyApproval 审批模型
	 * @throws UPPException 失败的异常信息
	 */
	@Override
	public void modifyOfflineApplyApprove(OfflineRechargeApply rechargeApply, OfflineRechargeApplyApproval rechargeApplyApproval) throws UPPException {
		try {
			
			rechargeOfflineManager.modifyOfflineApplyApprove(rechargeApply, rechargeApplyApproval);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("修改线下充值申请表信息异常", e);
			throw new RuntimeException("修改线下充值申请表信息异常", e);
		}
	}
	
	/***
	 * 根据tradeType和申请状态判断调用转账接口（充值/扣款） 取得交易类型（4001:充值，4002：扣款）和申请状态（已完成4102）
	 * 1.查询申请状态和交易类型 
	 * 2.调用转账接口
	 * 3.更新申请表：订单号和内部交易流水号
	 * @param applyId
	 * @param tradeType
	 * @throws UPPException
	 */
	public void callOfflineTransferAccounts(String applyId) throws UPPException {
		try {
			
			rechargeOfflineManager.callOfflineTransferAccounts(applyId);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("线下充值/扣款调用转账接口异常", e);
			throw new UPPException("线下充值/扣款调用转账接口异常", e);
		}

	}
	
	
	@Override
	public void cancelRechargeApply(String applyId, String state) throws UPPException {
		try {
			
			rechargeOfflineManager.cancelRechargeApply(applyId, state);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("取消线下充值申请表信息异常", e);
			throw new UPPException("取消线下充值申请表信息异常", e);
		}
	}

	@Override
	public void removeRechargeApply(String applyUUID) throws UPPException {
		try {
			
			rechargeOfflineManager.removeRechargeApply(applyUUID);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("删除线下充值申请表信息异常", e);
			throw new UPPException("删除线下充值申请表信息异常", e);
		}
	}

	@Override
	public List<OfflineRechargeApply> queryRechargeApply(OfflineRechargeApplyExampleExtended exampleExtended) throws UPPException {

		try {
			
			return rechargeOfflineManager.queryRechargeApply(exampleExtended);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("查询线下充值申请表集合异常", e);
			throw new UPPException("查询线下充值申请表集合异常", e);
		}
		
	}

	@Override
	public PaginationResult<OfflineRechargeApply> queryRechargeApplyByPage(OfflineRechargeApplyExampleExtended exampleExtended) throws UPPException {
	
		try {
			
			return rechargeOfflineManager.queryRechargeApplyByPage(exampleExtended);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("查询线下充值申请表页面对象异常", e);
			throw new UPPException("查询线下充值申请表页面对象异常", e);
		}
	
	}

	@Override
	public OfflineRechargeApply getRechargeApplyById(String applyUUID) throws UPPException {
		
		try {
			
			return rechargeOfflineManager.getRechargeApplyById(applyUUID);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("根据ID查询线下充值申请表异常", e);
			throw new UPPException("根据ID查询线下充值申请表异常", e);
		}

	}

	
	/***
	 * 线下流程管理-审批流程
	 * 
	 * @param rechargeApplyApproval
	 */
	@Override
	public OfflineRechargeApplyApproval approvalRecharge(OfflineRechargeApplyApproval rechargeApplyApproval) throws UPPException {
		try {
			return rechargeOfflineManager.approvalRecharge(rechargeApplyApproval);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("增加线下充值审批表信息异常", e);
			throw new UPPException("增加线下充值审批表信息异常", e);
		}

	}

	@Override
	public void modifyApprovalRecharge(OfflineRechargeApplyApproval rechargeApplyApproval) throws UPPException {
		try {
			
			rechargeOfflineManager.modifyApprovalRecharge(rechargeApplyApproval);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("修改线下充值审批表信息异常", e);
			throw new UPPException("修改线下充值审批表信息异常", e);
		}
	}

	@Override
	public void removeApprovalRecharge(String approvalUUID) throws UPPException {
		try {
			rechargeOfflineManager.removeApprovalRecharge(approvalUUID);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("删除线下充值审批表信息异常", e);
			throw new UPPException("删除线下充值审批表信息异常", e);
		}
	}

	@Override
	public List<OfflineRechargeApplyApproval> queryRechargeApproval(OfflineRechargeApplyApprovalExampleExtended exampleExtended) throws UPPException {
		
		try {
			
			return rechargeOfflineManager.queryRechargeApproval(exampleExtended);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("查询线下充值审批表集合异常", e);
			throw new UPPException("查询线下充值审批表集合异常", e);
		}
	}

	@Override
	public OfflineRechargeApplyApproval getRechargeApprovalById(String approvalUUID) throws UPPException {
		
		try {
			return rechargeOfflineManager.getRechargeApprovalById(approvalUUID);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("根据ID查询线下充值审批表异常", e);
			throw new UPPException("根据ID查询线下充值审批表异常", e);
		}
	}

	@Override
	public PaginationResult<OfflineRechargeApplyApproval> queryRechargeApprovalByPage(OfflineRechargeApplyApprovalExampleExtended exampleExtended) throws UPPException {
		
		try {
			return rechargeOfflineManager.queryRechargeApprovalByPage(exampleExtended);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("查询线下充值审批表页面对象异常", e);
			throw new UPPException("查询线下充值审批表页面对象异常", e);
		}
		
	}

	@Override
	public RechargeApplyVoucher submitTradeVoucher(RechargeApplyVoucher rechargeApplyVoucher) throws UPPException {
		try {
			return rechargeOfflineManager.submitTradeVoucher(rechargeApplyVoucher);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("增加交易凭证信息异常", e);
			throw new UPPException("增加交易凭证信息异常", e);
		}
		
	}

	@Override
	public void modifyTradeVoucher(RechargeApplyVoucher rechargeApplyVoucher) throws UPPException {
		try {
			rechargeOfflineManager.modifyTradeVoucher(rechargeApplyVoucher);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("修改交易凭证信息异常", e);
			throw new UPPException("修改交易凭证信息异常", e);
		}
	}

	@Override
	public void removeTradeVoucher(String tradeVoucherUUID) throws UPPException {
		try {
			rechargeOfflineManager.removeTradeVoucher(tradeVoucherUUID);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("删除交易凭证信息异常", e);
			throw new UPPException("删除交易凭证信息异常", e);
		}
	}

	/***
	 * 根据申请ID查询凭证信息,用于webAPP查看凭证图片
	 * 
	 * @param applyId
	 * @return
	 * @throws UPPException
	 */
	@Override
	public List<RechargeApplyVoucher> getRechargeApplyVoucherByApplyId(String applyId) throws UPPException {
		try {
			return rechargeOfflineManager.getRechargeApplyVoucherByApplyId(applyId);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("根据申请ID查询凭证信息异常", e);
			throw new UPPException("根据申请ID查询凭证信息异常", e);
		}
	}

	/***
	 * 删除凭证信息,用于webAPP修改凭证图片
	 * 
	 * @param applyId
	 * @return
	 * @throws UPPException
	 */
	@Override
	public void removeTradeVoucherByApply(String applyId) throws UPPException {
		try {
			rechargeOfflineManager.removeTradeVoucherByApply(applyId);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		} catch (Exception e) {
			logger.error("根据申请ID删除凭证信息异常", e);
			throw new UPPException("根据申请ID删除凭证信息异常", e);
		}
	}
	
}