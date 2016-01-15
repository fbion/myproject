package com.ctfo.upp.accountservice.payment.internal.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dto.beans.AbstractTransferResult;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.util.DefaultConfig;
public abstract class AbstractRecharge extends AbstractPayment{
	
	private static final Log logger = LogFactory.getLog(AbstractRecharge.class);
	
	public String rechargeOffline(OfflineRechargeApply rechargeApply)
			throws UPPException {
		try{
			
			logger.info("---->>>线下充值,账号["+rechargeApply.getInsideAccountNo()+"],金额["+rechargeApply.getTradeAmount().longValue()+"]");
			
			//rechargeApply.
					
			OrderInfo orderInfo = new OrderInfo();			
			orderInfo.setOrderAmount(rechargeApply.getTradeAmount().longValue());//充值金额
			//orderInfo.setWorkOrderNo(workOrderNo);//业务订单号
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);//线下充值
			
			orderInfo.setStoreCode(DefaultConfig.getValue("ZJZF_CODE"));//来自哪个商户,中交代码
			
			//中交支出帐户
			String zj_payout = super.getUPPAccountNo(DefaultConfig.getValue("ZJZF_PAYMENT_PAYOUT_ACCOUT_USERID"));
			//orderInfo.setAccountNo(DefaultConfig.getZJZF_PAYMENT_PAYOUT_ACCOUT());//付款人账号，中交备付金账号
			orderInfo.setAccountNo(zj_payout);//付款人账号，中交备付金账号
			
			orderInfo.setCollectMoneyAccountNo(rechargeApply.getInsideAccountNo());//收款人账号							
				
			//AbstractTransferAccountsResult result = paymentTransManager.transferAccounts(orderInfoDto);//转账
			AbstractTransferResult result = null;//paymentTransManager.transferAccountsDirect(orderInfo);//转账
			
			OrderInfo resultOrder = null;//iOrderManager.getOrderInfoByOrderNo(result.getOrderNo());			
			
			//更新申请状态
			OfflineRechargeApply rechargeApplyTem = new OfflineRechargeApply();
			rechargeApplyTem.setId(rechargeApply.getId());
			rechargeApplyTem.setOrderId(resultOrder.getId());
			rechargeApplyTem.setTradeExternalNo(result.getTradeExternalNo());
			this.modifyRechargeApply(rechargeApplyTem);//子类必须实现此方法
			
			logger.info("---->>线下充值成功,账号:［"+rechargeApply.getInsideAccountNo()+"],充值金额:["+rechargeApply.getTradeAmount().longValue()+"],内部交易流水号:["+result.getTradeExternalNo()+"],订单:["+resultOrder.getId()+"]");
						

		}catch(Exception e){
			logger.error("线下充值异常", e);
			throw new UPPException("线下充值异常",e);
		}
		
		return "SUCCESS";
	}

	public abstract void modifyRechargeApply(OfflineRechargeApply rechargeApply)throws UPPException;


}
