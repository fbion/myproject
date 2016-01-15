package com.ctfo.upp.accountservice.payment.external.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctfo.payment.dao.beans.AccountFrozenDetail;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.dto.beans.AbstractTransferResult;
import com.ctfo.payment.dto.beans.FreezeAccountResultBean;
import com.ctfo.payment.intf.internal.IOrderManager;
import com.ctfo.payment.intf.internal.IPaymentBaseManager;
import com.ctfo.payment.intf.internal.IPaymentTransManager;
import com.ctfo.upp.accountservice.utils.CheckOrderHelper;
import com.ctfo.upp.accountservice.utils.TradeNumberHelper;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.smscode.CommonBaseManager;
import com.sinoiov.upp.manager.payment.IFrozenDetailManager;
import com.sinoiov.upp.util.DefaultConfig;

//@Transactional
//@Service("PaymentTradeManagerExternalImpl")
public class PaymentTradeManagerExternalImpl implements com.ctfo.payment.intf.external.IPaymentTradeManager {

	private static final Log logger = LogFactory.getLog(PaymentTradeManagerExternalImpl.class);

	@Autowired
	@Qualifier("paymentTransManagerService")  
	private IPaymentTransManager iPaymentTransManager;

	@Autowired
	@Qualifier("frozenDetailManagerService")  
	private IFrozenDetailManager frozenDetailManager;

	@Autowired
	@Qualifier("iOrderManager")  
	private IOrderManager iOrderManager;
	
	@Autowired
	@Qualifier("paymentBaseManagerService")
	private IPaymentBaseManager paymentBaseManagerService;
	
	@Autowired
	@Qualifier("commonBaseManager")
	private CommonBaseManager commonBaseManager;

	@Override
	public List<PaymentTrade> queryPaymentTrade(
			PaymentTradeExampleExtended exampleExtended) throws UPPException {
		try{

			return paymentBaseManagerService.queryPaymentTrans(exampleExtended);

		}catch(Exception e){
			logger.error("根据条件查询交易异常！",e);
			throw new UPPException("根据条件查询交易异常！");
		}
	}

	@Override
	public PaginationResult<PaymentTrade> queryPaymentTradeByPage(
			PaymentTradeExampleExtended exampleExtended) throws UPPException {
		try{
			return paymentBaseManagerService.queryPaymentTransByPage(exampleExtended);

		}catch(Exception e){
			logger.error("根据条件查询交易页面对象异常！",e);
			throw new UPPException("根据条件查询交易页面对象异常！");
		}
	}
	
	@Override
	public PaymentTrade getPaymentTradeById(String tradeUUID)
			throws UPPException {
		try{

			return paymentBaseManagerService.queryPaymentTransById(tradeUUID);

		}catch(Exception e){
			logger.error("根据ID查询交易页面对象异常！",e);
			throw new UPPException("根据ID查询交易页面对象异常！");
		}
	}


	@Override
	public FreezeAccountResultBean freezeAccountMoney(OrderInfo orderInfo)
			throws UPPException {
		try{
			CheckOrderHelper.checkFreezeAccounts(orderInfo);

			return iPaymentTransManager.freezeAccountMoney(orderInfo);

		}catch(Exception e){
			logger.error("冻结账户金额异常！",e);
			if(e instanceof UPPException)
				throw new UPPException("冻结账户金额异常！异常码："+((UPPException)e.getCause()).getErrorCode());
			else
				throw new UPPException("冻结账户金额异常！");			
		}
	}

	@Override
	public FreezeAccountResultBean unFreezeAccountMoney(OrderInfo orderInfo)
			throws UPPException {
		try{

			CheckOrderHelper.checkFreezeAccounts(orderInfo);

			return iPaymentTransManager.unFreezeAccountMoney(orderInfo);

		}catch(Exception e){
			logger.error("解冻账户金额异常！",e);
			throw new UPPException("解冻账户金额异常！");
		}
	}
	/**
	 * 事物需要配置在iPaymentTransManager这一层
	 */
	@Override
	public AbstractTransferResult transferAccounts(OrderInfo orderInfo)
			throws UPPException {
		try{

			CheckOrderHelper.checkTranserAccounts(orderInfo);
			if(orderInfo.getTradeExternalNo()==null&&!"".equals(orderInfo.getTradeExternalNo())){
				orderInfo.setTradeExternalNo(TradeNumberHelper.getTradeExternalNo());//生成内部交易流水
			}
			AccountFrozenDetail accountFrozenDetail=iPaymentTransManager.queryAccountFrozenDetailMess(orderInfo.getStoreCode(), 
					orderInfo.getWorkOrderNo());
			if(accountFrozenDetail==null){
				AbstractTransferResult result =	iPaymentTransManager.transferAccounts(orderInfo,PayDict.TRANSFER_ACCOUNTS_TYPE_DIRECT);

//				iPaymentTransManager.handleTradeRecord(orderInfo.getTradeExternalNo());
				return result;
			}else{
				if((accountFrozenDetail.getFrozenAmount()-accountFrozenDetail.getUnfrozenAmount()) < orderInfo.getOrderAmount()){
					logger.error(orderInfo.getWorkOrderNo()+",该业务订单冻结的金额不足");
					UPPException uppe=new UPPException(new StringBuilder().append("该业务订单冻结的金额不足,冻结额度为：")
							.append(accountFrozenDetail.getFrozenAmount()).append(",已解冻金额为：").append(accountFrozenDetail.getUnfrozenAmount())
							.append(accountFrozenDetail.getFrozenAmount()).append(",订单金额为：").append(orderInfo.getOrderAmount())
							.append(accountFrozenDetail.getFrozenAmount()).append(",业务订单号为：").append(orderInfo.getWorkOrderNo())
							.toString());
					uppe.setErrorCode(ReturnCodeDict.FREEZE_RECORD_NOT_EXIST);
					throw uppe;
				}
				
				AbstractTransferResult result = iPaymentTransManager.transferAccounts(orderInfo,PayDict.TRANSFER_ACCOUNTS_TYPE_NEED_FROZEN);
//				iPaymentTransManager.handleTradeRecord(orderInfo.getTradeExternalNo());

 
				return result;
			}

		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new UPPException(e.getMessage());
		}

	}
	@Override
	public void unlockAccount(String accountNo)throws UPPException{
		try{
			
			paymentBaseManagerService.unLockAccount(accountNo);
			try{
				logger.info(DefaultConfig.getValue("lockAccountShortMessageSwitch"));
				commonBaseManager.sendShortMessByAccountNo(accountNo, "tyzfpt1005", new ArrayList<String>());
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new UPPException(e.getMessage());
		}
	}

	
	

}
