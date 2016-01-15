package com.sinoiov.upp.manager.payment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.RechargeApplyVoucher;
import com.ctfo.payment.dao.beans.RechargeApplyVoucherExampleExtended;
import com.ctfo.upp.models.PaginationResult;

@Service("applyVoucherMagager")
public class ApplyVoucherMagagerImpl extends AbstractPaymentManager implements IApplyVoucherMagager {

	@Override
	public RechargeApplyVoucher addTradeVoucher(
			RechargeApplyVoucher bean) throws Exception {
		super.notNull(bean, bean.getApplyId(), bean.getVoucherFileName());
		String uuid = super.addModel(bean);
		bean.setId(uuid);
		return bean;
	}

	@Override
	public void modifyTradeVoucher(RechargeApplyVoucher bean)throws Exception {
		super.notNull(bean, bean.getId());
		
		super.updateModelPart(bean);
	}

	@Override
	public List<RechargeApplyVoucher> getApplyVoucherByApplyId(String applyId)
			throws Exception {
		super.notNull(applyId);
		RechargeApplyVoucherExampleExtended exampleExtended = new RechargeApplyVoucherExampleExtended();
		exampleExtended.createCriteria().andApplyIdEqualTo(applyId);
		return this.queryTradeVoucher(exampleExtended);
	}

	@Override
	public void removeTradeVoucher(String id) throws Exception {
		super.notNull(id);
		RechargeApplyVoucher bean = new RechargeApplyVoucher();
		bean.setId(id);
		super.removeModel(bean);
	}

	@Override
	public List<RechargeApplyVoucher> queryTradeVoucher(
			RechargeApplyVoucherExampleExtended exampleExtended)throws Exception {
		super.notNull(exampleExtended);
		return super.getModels(exampleExtended);
	}

	@Override
	public PaginationResult<RechargeApplyVoucher> queryTradeVoucherPage(
			RechargeApplyVoucherExampleExtended exampleExtended)throws Exception {
		super.notNull(exampleExtended);
		return super.paginateModels(exampleExtended);
	}

	@Override
	public int countTradeVoucher(
			RechargeApplyVoucherExampleExtended exampleExtended)
			throws Exception {
		super.notNull(exampleExtended);
		return super.countModels(exampleExtended);
	}

	
}
