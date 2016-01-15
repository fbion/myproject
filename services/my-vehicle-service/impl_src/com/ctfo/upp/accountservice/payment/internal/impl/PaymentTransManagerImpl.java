package com.ctfo.upp.accountservice.payment.internal.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountChange;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.payment.dao.beans.AccountFrozenDetail;
import com.ctfo.payment.dao.beans.AccountFrozenDetailExampleExtended;
import com.ctfo.payment.dao.beans.HPaymentTrade;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.TransferAccountsToDealWithResult;
import com.ctfo.payment.dto.beans.AbstractTransferResult;
import com.ctfo.payment.dto.beans.FreezeAccountResultBean;
import com.ctfo.payment.dto.beans.TransferAccountsResult;
import com.ctfo.payment.intf.internal.IOrderManager;
import com.ctfo.payment.intf.internal.IPaymentBaseManager;
import com.ctfo.payment.intf.internal.IPaymentTransManager;
import com.ctfo.upp.accountservice.utils.AccountMessUtil;
import com.ctfo.upp.accountservice.utils.GetObjectSummary;
import com.ctfo.upp.accountservice.utils.PayRecordCreateUtil;
import com.ctfo.upp.accountservice.utils.RequestBeanUtil;
import com.ctfo.upp.accountservice.utils.TradeNumberHelper;
import com.ctfo.upp.dict.CheckAccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.manager.support.UppGenericManagerImpl;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.manager.payment.IFrozenDetailManager;

//@Service("paymentTransManagerService")
public class PaymentTransManagerImpl extends UppGenericManagerImpl<Object, Object> implements IPaymentTransManager {
	private static final Log logger = LogFactory.getLog(PaymentTransManagerImpl.class);
	static final private ReadWriteLock lock = new ReentrantReadWriteLock();

	@Autowired
	@Qualifier("frozenDetailManagerService")
	private IFrozenDetailManager frozenDetailManager;

	@Autowired
	@Qualifier("iOrderManager")
	private IOrderManager iOrderManager;

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("mongoService")
	private IMongoService mongoService;

	@Autowired
	@Qualifier("accountManagerService")
	private IAccountManager internalAccountManager;

	@Autowired
	@Qualifier("paymentBaseManagerService")
	private IPaymentBaseManager paymentBaseManagerService;

	/**
	 * 保存交易历史
	 * 
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	private String savePaymentTradeHistory(String id) throws UPPException {
		try {

			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade = (PaymentTrade) super.getModelById(paymentTrade);
			HPaymentTrade hPaymentTrade = new HPaymentTrade();
			PropertyUtils.copyProperties(hPaymentTrade, paymentTrade);
			hPaymentTrade.setRecordCreateTime(new Date().getTime());// 创建时间
			hPaymentTrade.setPayTradeId(paymentTrade.getId());

			return super.addModel(hPaymentTrade);

		} catch (Exception e) {
			logger.error("保存交易历史异常", e);
			throw new UPPException("保存交易历史异常", e);
		}
	}

	@Override
	public void doUnfinishedTask() throws UPPException {

	}


	@Override
	public FreezeAccountResultBean freezeAccountMoney(OrderInfo orderInfo) throws UPPException {
		FreezeAccountResultBean  result =new FreezeAccountResultBean();
		try {
			orderInfo.setTradeExternalNo(TradeNumberHelper.getTradeExternalNo());
			this.orderAccountNoHandle(orderInfo);
			this.addModel(orderInfo);
			try{mongoService.save(GetObjectSummary.getSummary(orderInfo));}catch(Exception e){logger.error("使用MongoDb存放流水失败"+e.getMessage(),e);}
			this.freezeAmountAtomHandle(orderInfo);
			Long payConfirmDate = System.currentTimeMillis();
			iOrderManager.updateOrderState(orderInfo.getOrderNo(), PayDict.PAY_ORDER_STATUS_PAY_SUCCESS,payConfirmDate );
			orderInfo.setPayConfirmDate(payConfirmDate);
			return (FreezeAccountResultBean)RequestBeanUtil.createResult(orderInfo);	 
		} catch (UPPException e) {
			throw e;
		} catch (Exception e) {
			logger.error("冻结服务异常,errorCode:"+((UPPException)e.getCause()).getErrorCode(), e);
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public void freezeAmountAtomHandle(OrderInfo orderInfo) throws UPPException {

		internalAccountManager.frozenAccount(orderInfo.getTradeExternalNo(),this.haveConfirmDate(orderInfo), orderInfo.getOrderType(), orderInfo.getAccountNo(), orderInfo.getStoreCode(),orderInfo.getId(), orderInfo.getOrderAmount());

		
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
			frozenDetailManager.internalAddFrozenDetail(accountFrozenDetail);
		} else {
			frozenDetailManager.increaseFrozenAmount(orderInfo.getOrderAmount(), orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
		}
	}

	@Override
	public void unFreezeAmountAtomHandle(String tradeInternalNo,String accountDate, String subject, String accountNo, String storeCode, Long money, String workOrderNo) throws UPPException {
		AccountFrozenDetail accountFrozenDetail = this.queryAccountFrozenDetailMess(storeCode, workOrderNo);
		if (accountFrozenDetail == null) {
			logger.error(workOrderNo + ",未查到业务订单对应的冻结记录");
			UPPException uppe = new UPPException("未查到业务订单对应的冻结记录");
			uppe.setErrorCode(ReturnCodeDict.FREEZE_RECORD_NOT_EXIST);
			throw uppe;
		}
		if ((accountFrozenDetail.getFrozenAmount() - accountFrozenDetail.getUnfrozenAmount()) < money) {
			logger.error(workOrderNo + ",该业务订单冻结的金额不足");
			UPPException uppe = new UPPException("该业务订单冻结的金额不足");
			uppe.setErrorCode(ReturnCodeDict.FREEZE_RECORD_NOT_EXIST);
			throw uppe;
		}
		OrderInfo  orderInfo = iOrderManager.getOrderInfoByTradeExternalNo(tradeInternalNo);
		
		internalAccountManager.unfrozenAccount(tradeInternalNo,accountDate, subject, accountNo, storeCode,"", money);
		
		if(orderInfo!=null)
			iOrderManager.updateOrderState(orderInfo.getOrderNo(), PayDict.PAY_ORDER_STATUS_PAY_SUCCESS, new Date().getTime());
		else
			throw new UPPException("根据交易流水号："+tradeInternalNo+",未查到对应订单");
		
		
		frozenDetailManager.increaseUnFrozenAmount(money, storeCode, workOrderNo);
	}

	@Override
	public  FreezeAccountResultBean unFreezeAccountMoney(OrderInfo orderInfo) throws UPPException {
		try {
			String accountDate = this.haveConfirmDate(orderInfo);
			orderInfo.setTradeExternalNo(TradeNumberHelper.getTradeExternalNo());
			this.orderAccountNoHandle(orderInfo);
			this.addModel(orderInfo);
			try{mongoService.save(GetObjectSummary.getSummary(orderInfo));}catch(Exception e){logger.error("使用MongoDb存放流水失败"+e.getMessage(),e);}
			this.unFreezeAmountAtomHandle(orderInfo.getTradeExternalNo(),accountDate, orderInfo.getOrderType(), orderInfo.getAccountNo(), orderInfo.getStoreCode(), orderInfo.getOrderAmount(), orderInfo.getWorkOrderNo());

			return (FreezeAccountResultBean) RequestBeanUtil.createResult(orderInfo);
		} catch (UPPException e) {
			logger.info("解冻失败.", e);
			throw e;
		} catch (Exception e) {
			logger.error("解冻服务异常", e);
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public AbstractTransferResult transferAccountsDirect(OrderInfo orderInfo) throws UPPException {
//		logger.info("转账操作需要先进行冻结，订单号：" + orderInfo.getOrderNo() + ",内部交易流水号:" + orderInfo.getTradeExternalNo());
//		this.freezeAmountAtomHandle(orderInfo);
//		return this.transferAccounts(orderInfo);
		return null;
	}

	@Override
	public AbstractTransferResult transferAccounts(OrderInfo orderInfo,String transferAccountType) throws UPPException {
		PaymentTrade paymentTrade =null; 
		if(PayDict.TRANSFER_ACCOUNTS_TYPE_NEED_FROZEN.equals(transferAccountType))
			paymentTrade = this.transferAccountsPrepose(orderInfo);
		if(PayDict.TRANSFER_ACCOUNTS_TYPE_DIRECT.equals(transferAccountType))
			paymentTrade = this.transferAccountsDirectPrepose(orderInfo);
		
		logger.info("转账账户操作完成，进行订单和交易记录的更新");
		this.transferAccountsPostposition(orderInfo, paymentTrade);
		
		TransferAccountsResult result = (TransferAccountsResult) RequestBeanUtil.createResult(orderInfo);
		return result;
	}

	/**
	 * 转账前置操作
	 * 
	 * @param orderInfo
	 * @return
	 * @throws UPPException
	 */
	@Override
	public PaymentTrade transferAccountsPrepose(OrderInfo orderInfo) throws UPPException {
		try {
			
			this.accountStatusCheck(orderInfo);
			
			this.orderAccountNoHandle(orderInfo);

			if(!(StringUtils.isNotBlank(orderInfo.getId()) && StringUtils.isNotBlank(orderInfo.getOrderNo())))
				iOrderManager.addOrderInfo(orderInfo);
			
			try{mongoService.save(GetObjectSummary.getSummary(orderInfo));}catch(Exception e){logger.error("使用MongoDb存放流水失败"+e.getMessage(),e);}

			PaymentTrade paymentTrade = PayRecordCreateUtil.createTrade(orderInfo);

			this.addModel(paymentTrade);

			this.accountAtomHandle(orderInfo);

			return paymentTrade;
		} catch (UPPException uppe) {
			throw uppe;
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}
	/**
	 * 转账前置操作
	 * 
	 * @param orderInfo
	 * @return
	 * @throws UPPException
	 */
	@Override
	public PaymentTrade transferAccountsDirectPrepose(OrderInfo orderInfo) throws UPPException {
		try {
			this.accountStatusCheck(orderInfo);
			this.orderAccountNoHandle(orderInfo);

			if(!(StringUtils.isNotBlank(orderInfo.getId()) && StringUtils.isNotBlank(orderInfo.getOrderNo())))
				iOrderManager.addOrderInfo(orderInfo);
			
			try{mongoService.save(GetObjectSummary.getSummary(orderInfo));}catch(Exception e){logger.error("使用MongoDb存放流水失败"+e.getMessage(),e);}

			PaymentTrade paymentTrade = PayRecordCreateUtil.createTrade(orderInfo);

			this.addModel(paymentTrade);

			this.directAccountAtomHandle(orderInfo);

			return paymentTrade;
		} catch (UPPException uppe) {
			throw uppe;
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractTransferResult transferAccountGateWayHandle(OrderInfo orderInfo, PaymentTrade paymentTrade) throws UPPException {
		try {
			// TransactionBeanRequestYB transactionBeanRequestYB =new
			// TransactionBeanRequestYB();
			// TransactionBeanResponseYB res=(TransactionBeanResponseYB)
			// tradeServiceFacade.transaction(transactionBeanRequestYB);
			// if(res.isFlag()){
			TransferAccountsResult result = (TransferAccountsResult) RequestBeanUtil.createResult(orderInfo);
			try {
				TransferAccountsToDealWithResult transferAccountsToDealWithResult = new TransferAccountsToDealWithResult();
				transferAccountsToDealWithResult.setId(orderInfo.getTradeExternalNo());
				transferAccountsToDealWithResult.setTransferAccountsResult(result);
				transferAccountsToDealWithResult.setOrderInfo(orderInfo);
				transferAccountsToDealWithResult.setPaymentTrade(paymentTrade);

				transferAccountsToDealWithResult.setAccountNo(orderInfo.getAccountNo());
				transferAccountsToDealWithResult.setCollectMoneyAccountNo(orderInfo.getCollectMoneyAccountNo());

				mongoService.save(transferAccountsToDealWithResult);

			} catch (Exception e) {
				logger.error("交易结果存入MongoDB失败,内部交易流水号：" + orderInfo.getTradeExternalNo());
			}

			return result;
			// }else{
			// UPPException uppe=new UPPException("转账交易网关返回结果失败");
			// uppe.setErrorCode(ReturnCodeDict.GATEWAY_FAIL);
			// throw uppe;
			// }
		} catch (UPPException e) {
			throw e;
		}
	}

	@Override
	public void transferAccountsPostposition(OrderInfo orderInfo, PaymentTrade paymentTrade) throws UPPException {
		Long payConfirmDate =System.currentTimeMillis();
		orderInfo.setPayConfirmDate(payConfirmDate);
		paymentTrade.setTradeStatus(PayDict.PAY_TRADE_STATUS_PAY_SUCCESS);
		paymentTrade.setPayConfirmDate(payConfirmDate);
		paymentTrade.setIsClearing(CheckAccountDict.PAYMENT_CLEARING_STATUS_HAVE_STORE_CHECK);
		paymentBaseManagerService.modifyPaymentTrans(paymentTrade);
		iOrderManager.updateOrderState(orderInfo.getOrderNo(), PayDict.PAY_ORDER_STATUS_PAY_SUCCESS,payConfirmDate);
	}

	@Override
	public AccountFrozenDetail queryAccountFrozenDetailMess(String storeCode, String workOrderNo) throws UPPException {
		AccountFrozenDetailExampleExtended accountFrozenDetailExampleExtended = new AccountFrozenDetailExampleExtended();
		accountFrozenDetailExampleExtended.createCriteria().andWorkOrderNoEqualTo(workOrderNo).andStoreCodeEqualTo(storeCode);

		List<AccountFrozenDetail> list = frozenDetailManager.queryFrozenDetail(accountFrozenDetailExampleExtended);
		if (list == null || list.isEmpty()) {
			return null;
		}
		accountFrozenDetailExampleExtended = null;
		AccountFrozenDetail accountFrozenDetail = list.get(0);

		return accountFrozenDetail;
	}
	public String inAndFreezeAccountAtomHandle(String tradeInternalNo, String accountDate, String subject, String accountNo, String storeCode,String serviceProviderCode, Long money) throws UPPException {

		AccountChange inCome = new AccountChange();
		inCome.setAccountNo(tradeInternalNo);
		inCome.setMoney(money);
		inCome.setServiceProviderCode(serviceProviderCode);
		inCome.setSubject(subject);
		inCome.setTradeInternalNo(tradeInternalNo);
		inCome.setStoreCode(storeCode);
		inCome.setAccountDate(accountDate);
		internalAccountManager.income(inCome);
		
	
		internalAccountManager.frozenAccount(tradeInternalNo, accountDate, subject, accountNo, storeCode,"", money);

		return ReturnCodeDict.SUCCESS;
	}
	/**
	 * 入账，出账的原子操作
	 * 
	 * @param orderInfo
	 * @return
	 * @throws UPPException
	 */
	@Override
	public String directAccountAtomHandle(OrderInfo orderInfo) throws UPPException {
		String accountDate = this.haveConfirmDate(orderInfo);
		
	

		AccountChange inCome = new AccountChange();
		inCome.setAccountNo(orderInfo.getCollectMoneyAccountNo());
		inCome.setMoney(orderInfo.getOrderAmount());
		inCome.setServiceProviderCode(orderInfo.getServiceProviderCode());
		inCome.setSubject(orderInfo.getOrderType());
		inCome.setTradeInternalNo(orderInfo.getTradeExternalNo());
		inCome.setStoreCode(orderInfo.getStoreCode());
		inCome.setAccountDate(accountDate);
		internalAccountManager.income(inCome);
		
		AccountChange payOut = new AccountChange();
		payOut.setAccountNo(orderInfo.getAccountNo());
		payOut.setMoney(orderInfo.getOrderAmount());
		payOut.setServiceProviderCode(orderInfo.getServiceProviderCode());
		payOut.setSubject(orderInfo.getOrderType());
		payOut.setTradeInternalNo(orderInfo.getTradeExternalNo());
		payOut.setStoreCode(orderInfo.getStoreCode());
		payOut.setAccountDate(accountDate);
//		internalAccountManager.directPayout(payOut);

		return ReturnCodeDict.SUCCESS;
	}

	/**
	 * 入账，出账的原子操作
	 * 
	 * @param orderInfo
	 * @return
	 * @throws UPPException
	 */
	@Override
	public String accountAtomHandle(OrderInfo orderInfo) throws UPPException {
		String accountDate = this.haveConfirmDate(orderInfo);
		
		AccountChange payOut = new AccountChange();
		payOut.setAccountNo(orderInfo.getAccountNo());
		payOut.setMoney(orderInfo.getOrderAmount());
		payOut.setServiceProviderCode(orderInfo.getServiceProviderCode());
		payOut.setSubject(orderInfo.getOrderType());
		payOut.setTradeInternalNo(orderInfo.getTradeExternalNo());
		payOut.setStoreCode(orderInfo.getStoreCode());
		payOut.setAccountDate(accountDate);
//		internalAccountManager.payout(payOut);

		AccountChange inCome = new AccountChange();
		inCome.setAccountNo(orderInfo.getCollectMoneyAccountNo());
		inCome.setMoney(orderInfo.getOrderAmount());
		inCome.setServiceProviderCode(orderInfo.getServiceProviderCode());
		inCome.setSubject(orderInfo.getOrderType());
		inCome.setTradeInternalNo(orderInfo.getTradeExternalNo());
		inCome.setStoreCode(orderInfo.getStoreCode());
		inCome.setAccountDate(accountDate);
		internalAccountManager.income(inCome);

		frozenDetailManager.increaseUnFrozenAmount(orderInfo.getOrderAmount(), orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
		return ReturnCodeDict.SUCCESS;
	}

	/**
	 * 转账时检查付款账户和收款账户状态是否正常
	 * 
	 * @param orderInfo
	 * @throws UPPException
	 */
	@Override
	public void accountStatusCheck(OrderInfo orderInfo) throws UPPException {

//		Account cashAccount = internalAccountManager.queryAccountInfo(orderInfo.getAccountNo());

//		AccountMessUtil.accountCheck(cashAccount);

//		Account collectMoneyAccount = internalAccountManager.queryAccountInfo(orderInfo.getCollectMoneyAccountNo());

//		AccountMessUtil.accountCheck(collectMoneyAccount);

	}

	
	@Override
	public void orderAccountNoHandle(OrderInfo orderInfo) throws UPPException {
		if (orderInfo.getAccountNo() != null && !"".equals(orderInfo.getAccountNo())) {
			orderInfo.setUserId(paymentBaseManagerService.getUserIdByAccountNo(orderInfo.getAccountNo()));
		}
		if (orderInfo.getCollectMoneyAccountNo() != null && !"".equals(orderInfo.getCollectMoneyAccountNo())) {
			orderInfo.setCollectMoneyUserId(paymentBaseManagerService.getUserIdByAccountNo(orderInfo.getCollectMoneyAccountNo()));
		}
		PayRecordCreateUtil.createOrder(orderInfo);
	}
	private   String haveConfirmDate( OrderInfo orderInfo){
		if(orderInfo.getPayConfirmDate()!=null&&orderInfo.getPayConfirmDate()!=0){
			return new SimpleDateFormat("yyyy-MM-dd").format(orderInfo.getPayConfirmDate());
		}else{
			return null;
		}
			
	}
	

	// /**
	// * 交易订单SQL查询（用于后台展示）
	// * @param sql
	// * @return
	// * @throws UPPException
	// */
	// @Override
	// public List<Map<?, ?>> querySqlByPage(String sql) throws UPPException{
	// if (sql == null || sql.isEmpty())
	// throw new IllegalArgumentException("传递的sql语句无效.");
	// List<Map<?, ?>> resultList = Collections.emptyList();
	// try {
	// resultList = super.queryBySQL(sql);
	// } catch (Exception e) {
	// logger.debug(String.format("根据sql=[%s]查询数据时出错，原因：", sql), e);
	// throw new UPPException(String.format("根据sql=[%s]查询数据时出错，原因：", sql), e);
	// }
	// return resultList;
	// }
}
