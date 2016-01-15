package com.sinoiov.upp.manager.payment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApprovalExampleExtended;
import com.ctfo.upp.models.PaginationResult;

@Service("applyApproveManager")
public class ApplyApproveManagerImpl extends AbstractPaymentManager implements IApplyApproveManager {

	@Override
	public OfflineRechargeApplyApproval addApplyApprove(
			OfflineRechargeApplyApproval bean)throws Exception {
		super.notNull(bean, bean.getApprovalResult(), bean.getRechargeApplyId(), bean.getApprovalUserId());
		String uuid = super.addModel(bean);
		bean.setId(uuid);
		return bean;
	}

	@Override
	public void modifyApplyApprove(
			OfflineRechargeApplyApproval bean)throws Exception {
		super.notNull(bean, bean.getId());
		super.updateModelPart(bean);
	}

	@Override
	public void removeApplyApprove(String id) throws Exception {
		super.notNull(id);
		OfflineRechargeApplyApproval bean = new OfflineRechargeApplyApproval();
		bean.setId(id);
		super.removeModel(bean);
	}

	@Override
	public List<OfflineRechargeApplyApproval> queryApplyApprove(
			OfflineRechargeApplyApprovalExampleExtended exampleExtended)throws Exception {
		super.notNull(exampleExtended);
		
		return super.getModels(exampleExtended);
	}

	@Override
	public OfflineRechargeApplyApproval getApplyApprovalById(String id)
			throws Exception {
		super.notNull(id);
		OfflineRechargeApplyApproval bean = new OfflineRechargeApplyApproval();
		bean.setId(id);
		return super.getModelById(bean);
	}

	@Override
	public PaginationResult<OfflineRechargeApplyApproval> queryApplyApprovalPage(
			OfflineRechargeApplyApprovalExampleExtended exampleExtended)
			throws Exception {
		super.notNull(exampleExtended);
		return super.paginateModels(exampleExtended);
	}

	
}
