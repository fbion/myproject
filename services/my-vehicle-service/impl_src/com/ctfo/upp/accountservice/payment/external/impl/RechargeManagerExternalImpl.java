package com.ctfo.upp.accountservice.payment.external.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.intf.internal.IRechargeManager;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;


//@Service("RechargeManagerExternalImpl")
public class RechargeManagerExternalImpl implements com.ctfo.payment.intf.external.IRechargeManager{
	
	private static final Log logger = LogFactory.getLog(RechargeManagerExternalImpl.class);
	
	@Autowired
	@Qualifier("iRechargeManager")  
	private IRechargeManager iRechargeManager;
	
	@Override
	public Map<String, Object> recharge(OrderInfo orderInfo) throws UPPException {
		try{
			
			String clentType = orderInfo.getClentType();//终端类型，固定值(PC, MOBILE)
		
			String payChannel = orderInfo.getPayChannel();//支付渠道，填英文固定值(在线-NET，快捷-FASTPAY，手机-WAP)
			
			if("PC".equals(clentType) && "NET".equals(payChannel)){
				//PC网银充值
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);//订单类型
				return iRechargeManager.recharge(orderInfo);
				
			}else if("PC".equals(clentType) && "FASTPAY".equals(payChannel)){
				//PC快捷方式充值
				//TODO 注释掉
//				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_PC_FASTPAY_PAYOUT);//订单类型
				return iRechargeManager.pcFastpayRecharge(orderInfo);
				
			}else if("MOBILE".equals(clentType) && "NET".equals(payChannel)){
				//MOBILE网银充值
				//orderInfo.setOrderType(PayDict.ORDER_SUBJECT_MOBILE_FASTPAY_PAYOUT);//订单类型
				return iRechargeManager.mobileRecharge(orderInfo);
				
			}else if("MOBILE".equals(clentType) && "FASTPAY".equals(payChannel)){
				//MOBILE快捷方式充值
//				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_MOBILE_FASTPAY_PAYOUT);//订单类型
				return iRechargeManager.mobileFastpayRecharge(orderInfo);
				
			}			
			
			return null;
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("充值外部接口服务异常！",e);
			throw new UPPException(ReturnCodeDict.ERROR, "充值外部接口服务异常");
		}
		
	}

	@Override
	public Map<String, Object> pcFastpayRecharge(OrderInfo orderInfo)
			throws UPPException {
		try{
			//PC快捷方式充值
			//TODO
//			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_PC_FASTPAY_PAYOUT);//订单类型
			return iRechargeManager.pcFastpayRecharge(orderInfo);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("PC快捷方式充值接口服务异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "PC快捷方式充值接口服务异常");
		}
	}

	@Override
	public Map<String, Object> mobileRecharge(OrderInfo orderInfo)
			throws UPPException {
		try{
			//MOBILE网银充值
//			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_MOBILE_FASTPAY_PAYOUT);//订单类型
			return iRechargeManager.mobileRecharge(orderInfo);		
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("MOBILE网银充值接口服务异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "MOBILE网银充值接口服务异常");
		}
	}

	@Override
	public Map<String, Object> mobileFastpayRecharge(OrderInfo orderInfo)
			throws UPPException {
		try{
			//MOBILE快捷方式充值
//			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_MOBILE_FASTPAY_PAYOUT);//订单类型
			return iRechargeManager.mobileFastpayRecharge(orderInfo);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("MOBILE快捷方式充值接口服务异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "MOBILE快捷方式充值接口服务异常");
		}
	}

}
