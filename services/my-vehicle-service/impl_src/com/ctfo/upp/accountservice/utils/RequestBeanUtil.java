package com.ctfo.upp.accountservice.utils;

import java.math.BigDecimal;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeSyncBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TransactionBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawBeanRequestYB;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dto.beans.AbstractResultBean;
import com.ctfo.payment.dto.beans.FreezeAccountResultBean;
import com.ctfo.payment.dto.beans.TransferAccountsResult;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;

public class RequestBeanUtil {
	public static RequestBean createRequestBean(PaymentTrade paymentTrade,Long batchSeq) throws UPPException{
		if(PayDict.PAY_TRADE_TYPE_RECHARGE.equals(paymentTrade.getTradeType())){
			
			return createRechargeSyncBeanRequestYB(paymentTrade,batchSeq);
			
		}else if(PayDict.PAY_TRADE_TYPE_TRANSFER.equals(paymentTrade.getTradeType())){
			
			return createTransactionBeanRequestYB(paymentTrade,batchSeq);
			
		}else if(PayDict.PAY_TRADE_TYPE_WITHDRAW_CASH.equals(paymentTrade.getTradeType())){
			
			return createWithDrawBeanRequestYB(paymentTrade,batchSeq);
		}else{
			throw new UPPException("数据格式异常");
		}
	}
	
	public static WithDrawBeanRequestYB createWithDrawBeanRequestYB(PaymentTrade paymentTrade,Long batchSeq){
		WithDrawBeanRequestYB requestBean = new WithDrawBeanRequestYB();
		requestBean.setRecordId(batchSeq.toString());
		requestBean.setAccountId(paymentTrade.getAccountNo());
		requestBean.setAmount(BigDecimal.valueOf(paymentTrade.getOrderAmount()).divide(new BigDecimal(100)).toString());
		requestBean.setBankCardNo(paymentTrade.getBankCardNo());
		requestBean.setRequestId(paymentTrade.getTradeExternalNo());
		requestBean.setTime(String.valueOf(paymentTrade.getPayConfirmDate()));
		return requestBean;
	}
	public static TransactionBeanRequestYB createTransactionBeanRequestYB(PaymentTrade paymentTrade,Long batchSeq){
		TransactionBeanRequestYB requestBean=new TransactionBeanRequestYB();
		requestBean.setRecordId(batchSeq.toString());
		requestBean.setAmount(BigDecimal.valueOf(paymentTrade.getOrderAmount()).divide(new BigDecimal(100)).toString());
		requestBean.setRequestId(paymentTrade.getTradeExternalNo());
		requestBean.setOrigAccountId(paymentTrade.getAccountNo());
		requestBean.setTargetAccountId(paymentTrade.getCollectMoneyAccountNo());
		requestBean.setTime(String.valueOf(paymentTrade.getPayConfirmDate()));
		
		return requestBean;
	}
	public static RechargeSyncBeanRequestYB createRechargeSyncBeanRequestYB(PaymentTrade paymentTrade,Long batchSeq){
		RechargeSyncBeanRequestYB requestBean = new RechargeSyncBeanRequestYB();
		requestBean.setRecordId(batchSeq.toString());
//		requestBean.setRecordId(paymentTrade.getTradeExternalNo().replace("BN",""));
		requestBean.setRequestId(paymentTrade.getTradeExternalNo());
		requestBean.setAmount(BigDecimal.valueOf(paymentTrade.getOrderAmount()).divide(new BigDecimal(100)).toString());
		requestBean.setExternalId(paymentTrade.getExternalNo());
		requestBean.setAccountId(paymentTrade.getAccountNo());
		requestBean.setTime(String.valueOf(paymentTrade.getPayConfirmDate()));
		
		return requestBean;
	}
	
	public static AbstractResultBean createResult(OrderInfo orderInfo) throws UPPException {
		
		try {
			if(PayDict.ORDER_SUBJECT_RECHARGE.equals(orderInfo.getOrderType())){
				TransferAccountsResult	result= new TransferAccountsResult();
				result.setAccountNo(orderInfo.getAccountNo());
				result.setCollectMoneyAccountNo(orderInfo.getCollectMoneyAccountNo());
				result.setStoreCode(ReturnCodeDict.SUCCESS);
				result.setSubject(orderInfo.getOrderType());
				result.setOrderId(orderInfo.getId());
				result.setOrderNo(orderInfo.getOrderNo());
				result.setStoreCode(orderInfo.getStoreCode());
				result.setTradeExternalNo(orderInfo.getTradeExternalNo());
				result.setOrderAmount(orderInfo.getOrderAmount());
				return result;
			}else if(PayDict.ORDER_SUBJECT_TRANSFER_ACCOUNT.equals(orderInfo.getOrderType())){
				TransferAccountsResult	result= new TransferAccountsResult();
				result.setAccountNo(orderInfo.getAccountNo());
				result.setCollectMoneyAccountNo(orderInfo.getCollectMoneyAccountNo());
				result.setStoreCode(ReturnCodeDict.SUCCESS);
				result.setSubject(orderInfo.getOrderType());
				result.setOrderId(orderInfo.getId());
				result.setOrderNo(orderInfo.getOrderNo());
				result.setStoreCode(orderInfo.getStoreCode());
				result.setTradeExternalNo(orderInfo.getTradeExternalNo());
				result.setPayConfirmDate(orderInfo.getPayConfirmDate());
				result.setOrderAmount(orderInfo.getOrderAmount());
				return result;
			}else if(PayDict.ORDER_SUBJECT_FREEZE_MONEY.equals(orderInfo.getOrderType())||PayDict.ORDER_SUBJECT_UNFREEZE_MONEY.equals(orderInfo.getOrderType())){
				FreezeAccountResultBean result= new FreezeAccountResultBean();
				result.setAccountNo(orderInfo.getAccountNo());
				result.setReturnCode(ReturnCodeDict.SUCCESS);
				result.setSubject(orderInfo.getOrderType());
				result.setOrderNo(orderInfo.getOrderNo());
				result.setStoreCode(orderInfo.getStoreCode());
				result.setPayConfirmDate(orderInfo.getPayConfirmDate());
				result.setOrderAmount(orderInfo.getOrderAmount());
				return result;
			}	
			return null;
			
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}
}
