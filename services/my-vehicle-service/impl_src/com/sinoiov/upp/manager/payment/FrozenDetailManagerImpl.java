package com.sinoiov.upp.manager.payment;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.AccountFrozenDetail;
import com.ctfo.payment.dao.beans.AccountFrozenDetailExampleExtended;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.manager.support.UppGenericManagerImpl;
@Service("frozenDetailManager")
public class FrozenDetailManagerImpl extends UppGenericManagerImpl<Object, Object> implements IFrozenDetailManager {
	static private final Log logger = LogFactory.getLog(FrozenDetailManagerImpl.class);

	@Override
	public void internalAddFrozenDetail(AccountFrozenDetail accountFrozenDetail) throws UPPException {
		// TODO Auto-generated method stub
		if (accountFrozenDetail == null)
			throw new NullPointerException("传递的参数对象 is null.");
		if (accountFrozenDetail.getStoreCode() == null || accountFrozenDetail.getStoreCode().isEmpty())
			throw new IllegalStateException("传递的[商户编号]值不合法.");
		else if (accountFrozenDetail.getWorkOrderNo() == null || accountFrozenDetail.getWorkOrderNo().isEmpty())
			throw new IllegalStateException("传递的[业务订单号]值不合法.");
		else if (accountFrozenDetail.getServiceProviderCode() == null || accountFrozenDetail.getServiceProviderCode().isEmpty())
			throw new IllegalStateException("传递的[支付平台编号]值不合法.");
		else if (accountFrozenDetail.getParentAccountNo() == null || accountFrozenDetail.getParentAccountNo().isEmpty())
			throw new IllegalStateException("传递的[父账号]值不合法.");
		else if (accountFrozenDetail.getFrozenAmount() == null || accountFrozenDetail.getFrozenAmount() == 0)
			throw new IllegalStateException("传递的[冻结金额]值不合法.");
		else if (accountFrozenDetail.getUnfrozenAmount() == null )
			throw new IllegalStateException("传递的[已解冻金额]值不合法.");
		try {
			super.addModel(accountFrozenDetail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("保存冻结明细时产生错误，原因：", e);
			throw new UPPException("保存冻结明细时产生错误，原因：", e);
		}

	}

	@Override
	public void increaseUnFrozenAmount(Long unfrozenAmount, String storeCode, String workOrderNo) throws UPPException {
		// TODO Auto-generated method stub
		if (unfrozenAmount == null || unfrozenAmount == 0)
			throw new IllegalArgumentException("更新冻结明细时所传递的[解冻金额]值不合法.");
		else if (storeCode == null || storeCode.isEmpty())
			throw new IllegalArgumentException("更新冻结明细时所传递的[商户编号]值不合法.");
		else if (workOrderNo == null || workOrderNo.isEmpty())
			throw new IllegalArgumentException("更新冻结明细时所传递的[业务订单号]值不合法.");
		final AccountFrozenDetailExampleExtended exampleExtended = new AccountFrozenDetailExampleExtended();
		exampleExtended.createCriteria().andStoreCodeEqualTo(storeCode).andWorkOrderNoEqualTo(workOrderNo);
		List<AccountFrozenDetail> accountFrozenDetailList = this.queryFrozenDetail(exampleExtended);
		if (accountFrozenDetailList == null || accountFrozenDetailList.isEmpty())
			throw new UPPException(String.format("根据参数[storeCode=%s][workOrderNo=%s]未查询到任何冻结明细记录.", storeCode, workOrderNo));
		AccountFrozenDetail accountFrozenDetail = accountFrozenDetailList.get(0);
		if (accountFrozenDetail == null)
			throw new UPPException(String.format("根据参数[storeCode=%s][workOrderNo=%s]查询到冻结明细对象 is null.", storeCode, workOrderNo));

		AccountFrozenDetail _accountFrozenDetail=this.queryAccountFrozenDetailMess(storeCode, workOrderNo);

		Long newUnfrozenAmount=_accountFrozenDetail.getUnfrozenAmount()+unfrozenAmount;

		_accountFrozenDetail.setId(accountFrozenDetail.getId());
		_accountFrozenDetail.setUnfrozenAmount(newUnfrozenAmount);
		_accountFrozenDetail.setVersion(accountFrozenDetail.getVersion() + 1);
		_accountFrozenDetail.setOperTime(System.currentTimeMillis());
		final AccountFrozenDetail where = new AccountFrozenDetail();
		where.setId(accountFrozenDetail.getId());
		where.setVersion(accountFrozenDetail.getVersion());
		try {
			super.updateModelByOtherModel(_accountFrozenDetail, where);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("更新解冻金额时产生错误，原因：", e);
			throw new UPPException("更新解冻金额时产生错误，原因：", e);
		} finally {
			if (accountFrozenDetailList != null)
				accountFrozenDetailList.clear();
			accountFrozenDetail = null;
		}
	}
	@Override
	public void increaseFrozenAmount(Long frozenAmount, String storeCode, String workOrderNo) throws UPPException {
		// TODO Auto-generated method stub
		if (frozenAmount == null || frozenAmount == 0)
			throw new IllegalArgumentException("更新冻结明细时所传递的[解冻金额]值不合法.");
		else if (storeCode == null || storeCode.isEmpty())
			throw new IllegalArgumentException("更新冻结明细时所传递的[商户编号]值不合法.");
		else if (workOrderNo == null || workOrderNo.isEmpty())
			throw new IllegalArgumentException("更新冻结明细时所传递的[业务订单号]值不合法.");
		final AccountFrozenDetailExampleExtended exampleExtended = new AccountFrozenDetailExampleExtended();
		exampleExtended.createCriteria().andStoreCodeEqualTo(storeCode).andWorkOrderNoEqualTo(workOrderNo);
		List<AccountFrozenDetail> accountFrozenDetailList = this.queryFrozenDetail(exampleExtended);
		if (accountFrozenDetailList == null || accountFrozenDetailList.isEmpty())
			throw new UPPException(String.format("根据参数[storeCode=%s][workOrderNo=%s]未查询到任何冻结明细记录.", storeCode, workOrderNo));
		AccountFrozenDetail accountFrozenDetail = accountFrozenDetailList.get(0);
		if (accountFrozenDetail == null)
			throw new UPPException(String.format("根据参数[storeCode=%s][workOrderNo=%s]查询到冻结明细对象 is null.", storeCode, workOrderNo));
		
		AccountFrozenDetail _accountFrozenDetail=this.queryAccountFrozenDetailMess(storeCode, workOrderNo);
		
		Long newFrozenAmount=_accountFrozenDetail.getFrozenAmount()+frozenAmount;
		_accountFrozenDetail.setId(accountFrozenDetail.getId());
		_accountFrozenDetail.setFrozenAmount(newFrozenAmount);
		_accountFrozenDetail.setVersion(accountFrozenDetail.getVersion() + 1);
		_accountFrozenDetail.setOperTime(System.currentTimeMillis());

		final AccountFrozenDetail where = new AccountFrozenDetail();
		where.setId(accountFrozenDetail.getId());
		where.setVersion(accountFrozenDetail.getVersion());
		try {
			super.updateModelByOtherModel(_accountFrozenDetail, where);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("更新解冻金额时产生错误，原因：", e);
			throw new UPPException("更新解冻金额时产生错误，原因：", e);
		} finally {
			if (accountFrozenDetailList != null)
				accountFrozenDetailList.clear();
			accountFrozenDetail = null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<AccountFrozenDetail> queryFrozenDetail(AccountFrozenDetailExampleExtended exampleExtended) throws UPPException {
		// TODO Auto-generated method stub
		if (exampleExtended == null)
			throw new NullPointerException("查询冻结明细时所传递的参数对象 is null.");
		List accountFrozenDetailList = null;
		try {
			accountFrozenDetailList = super.getModels(exampleExtended);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("查询冻结明细时产生错误，原因：", e);
			throw new UPPException("查询冻结明细时产生错误，原因：", e);
		}
		return accountFrozenDetailList;
	}
	@Override
	public AccountFrozenDetail queryAccountFrozenDetailMess(String storeCode,String workOrderNo) throws UPPException{
		
		
		AccountFrozenDetailExampleExtended accountFrozenDetailExampleExtended =new AccountFrozenDetailExampleExtended();
		accountFrozenDetailExampleExtended.createCriteria().andWorkOrderNoEqualTo(workOrderNo)
			.andStoreCodeEqualTo(storeCode);
		
		List<AccountFrozenDetail> list=this.queryFrozenDetail(accountFrozenDetailExampleExtended);
		if(list==null||list.isEmpty()){
			return null;
		}
		accountFrozenDetailExampleExtended=null;
		AccountFrozenDetail accountFrozenDetail= list.get(0);
		
		return accountFrozenDetail;
	}
	@Override
	public void delFrozenDetail(String id) throws UPPException {
		
	}
	@Override
	public boolean frozenLimitHandle(OrderInfo orderInfo) throws UPPException{
		AccountFrozenDetail accountFrozenDetail = this.queryAccountFrozenDetailMess(orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
		if (accountFrozenDetail == null) {
			accountFrozenDetail = new AccountFrozenDetail();

			accountFrozenDetail.setStoreCode(orderInfo.getStoreCode());
			accountFrozenDetail.setWorkOrderNo(orderInfo.getWorkOrderNo());
			accountFrozenDetail.setFrozenAmount(orderInfo.getOrderAmount());
			accountFrozenDetail.setUnfrozenAmount(0l);
			accountFrozenDetail.setParentAccountNo(orderInfo.getAccountNo());
			accountFrozenDetail.setServiceProviderCode(orderInfo.getServiceProviderCode());
			accountFrozenDetail.setVersion(0);
			this.internalAddFrozenDetail(accountFrozenDetail);
			
		} else {
			this.increaseFrozenAmount(orderInfo.getOrderAmount(), orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
		}
		return true;
	}

}
