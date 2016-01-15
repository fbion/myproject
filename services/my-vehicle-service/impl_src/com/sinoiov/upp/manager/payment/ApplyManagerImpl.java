package com.sinoiov.upp.manager.payment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExampleExtended;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.models.PaginationResult;

@Service("applyManager")
public class ApplyManagerImpl extends AbstractPaymentManager implements IApplyManager {

	@Override
	public OfflineRechargeApply createApply(OfflineRechargeApply bean)throws Exception {
		
		super.notNull(bean, bean.getApplyNo(), bean.getApplyName(),
				bean.getTradeType(), bean.getTradeAmount(), bean.getInsideAccountNo());
		
		bean.setApplyTime(System.currentTimeMillis());
		bean.setApplyStatus(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_Y);
		
		String uuid = super.addModel(bean);
		
		bean.setId(uuid);
		
		return bean;
	}

	@Override
	public void modifyApply(OfflineRechargeApply bean)throws Exception {

		super.notNull(bean, bean.getId());
		
		super.updateModelPart(bean);
		
	}

	@Override
	public void removeApply(String id) throws Exception {
		super.notNull(id);
		OfflineRechargeApply bean = new OfflineRechargeApply();
		bean.setId(id);
		super.removeModel(bean);
	}

	@Override
	public List<OfflineRechargeApply> queryApply(
			OfflineRechargeApplyExampleExtended exampleExtended)throws Exception {
		super.notNull(exampleExtended);
		return super.getModels(exampleExtended);
	}

	@Override
	public PaginationResult<OfflineRechargeApply> queryApplyPage(
			OfflineRechargeApplyExampleExtended exampleExtended)throws Exception {
		super.notNull(exampleExtended);
		return super.paginateModels(exampleExtended);
	}

	@Override
	public OfflineRechargeApply getApplyById(String id) throws Exception {
		super.notNull(id);
		OfflineRechargeApply bean = new OfflineRechargeApply();
		bean.setId(id);
		return super.getModelById(bean);
	}

	@Override
	public int countApply(OfflineRechargeApplyExampleExtended exampleExtended)
			throws Exception {
		super.notNull(exampleExtended);
		return super.countModels(exampleExtended);
	}

	
}
