package com.ctfo.account.balance.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctfo.account.balance.IAppleOfPayorderBalance;
import com.ctfo.payment.dao.beans.CheckOrderDivergence;
import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExampleExtended;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExampleExtended.Criteria;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.payment.intf.external.IRechargeOfflineManager;
import com.ctfo.payment.intf.internal.IOrderManager;
import com.ctfo.upp.dict.BalanceOfAccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;

//@Service("appleOfPayordBalance")
public class AppleOfPayordBalance implements IAppleOfPayorderBalance {
	@Autowired
	private IRechargeOfflineManager rechargeOfflineManager;
	@Autowired
	private IOrderManager iOrderManager;
	@Autowired
//	private UnbalanceOrderWithBusPlatformManager unbalanceOrderWithBusPlatformManager;

	Log logger = LogFactory.getLog(AppleOfPayordBalance.class);
	
	//线下交易申请对账实现方法
	public void appleBanlanceOf() throws UPPException{
		
		List<OfflineRechargeApply> queryRechargeApply = quartyOffLineRechargeApply(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_SUCCESS,null);
		
		List<OrderInfo> queryOrderInfoList = quartyOrderInfoList(PayDict.PAY_TRADE_TYPE_OFFLINE,null);
		
		Map<String, OrderInfo> orderMap = new HashMap<String, OrderInfo>();
		//将订单列表放入MAP里
		if(queryOrderInfoList!=null&&queryOrderInfoList.size()>0){
			for(int i=0;i<queryOrderInfoList.size();i++){
				orderMap.put(queryOrderInfoList.get(i).getId(), queryOrderInfoList.get(i));
			}
		}
		
		if(queryRechargeApply!=null&&queryRechargeApply.size()>0){
			//循环对比交易金额
			for(int i=0;i<queryRechargeApply.size();i++){
				String orderId = queryRechargeApply.get(i).getOrderId();
				//取出线下申请金额
				Long tradeAmount = queryRechargeApply.get(i).getTradeAmount();
				if(orderMap.get(orderId) != null){
					//取出订单金额
					Long orderAmount = orderMap.get(orderId).getOrderAmount();
					if(tradeAmount.equals(orderAmount)){
						//修改对账状态为已对账
						changePayCheck(queryRechargeApply.get(i),PayDict.OFFLINERECHARGEAPPLY_PAYCHECK_BALANE);
					}else{
						//将对账的差错存入差错表
						Long D_value = tradeAmount-orderAmount;
						//正数说明线下交易金额多，负数说明订单交易金额多。
						boolean istPositiv = istPositiv(D_value);
						//将差异详情存入对账差错表
						saveBalanceErrorMessage(D_value,istPositiv,orderMap.get(orderId));
					}
					queryRechargeApply.remove(i);
					--i;
				}
			
			}
		
		}
		//再判断线下申请列表的数量是否大于0
		if(queryRechargeApply!=null&&queryRechargeApply.size()>0){
			for(int i=0;i<queryRechargeApply.size();i++){
				//大于0说明有线下申请交易没有生成订单。执行生成订单方法。
				try {
					rechargeOfflineManager.callOfflineTransferAccounts(queryRechargeApply.get(i).getId());
				} catch (UPPException e) {
					logger.error("生成订单发生错误：", e);
					continue;
				}
				//执行返回成功后再次进行对账。处理对接结果。订单生成不成功的，暂不做处理。
				List<OfflineRechargeApply> RechargeApply = quartyOffLineRechargeApply(null,queryRechargeApply.get(i).getId());
				List<OrderInfo> queryOrderInfo = quartyOrderInfoList(null,queryRechargeApply.get(i).getId());
				if(RechargeApply!=null&&queryOrderInfo!=null){
					Long tradeAmount = RechargeApply.get(0).getTradeAmount();
					Long orderAmount = queryOrderInfo.get(0).getOrderAmount();
					if(tradeAmount.equals(orderAmount)){
						//修改对账状态为已对账
						changePayCheck(RechargeApply.get(0),PayDict.OFFLINERECHARGEAPPLY_PAYCHECK_BALANE);
					}else{
						//将对账的差错存入差错表
						Long D_value = tradeAmount-orderAmount;
						//正数说明线下交易金额多，负数说明订单交易金额多。
						boolean istPositiv = istPositiv(D_value);
						//将差异详情存入对账差错表
						saveBalanceErrorMessage(D_value,istPositiv,queryOrderInfo.get(0));
					}
				}
			}
		}

	}
	
	//判断数字的正负
	private static boolean istPositiv(Long x){
		if(x<=0){
			return false;
		}
		return true;
	}
	
	//按条件查询线下申请列表
	private List<OfflineRechargeApply> quartyOffLineRechargeApply(String applyStatus,String orderId) throws UPPException{
		List<OfflineRechargeApply> queryRechargeApply = new ArrayList<OfflineRechargeApply>();
		OfflineRechargeApplyExampleExtended offLineExample = new OfflineRechargeApplyExampleExtended();
		Criteria createCriteria = offLineExample.createCriteria();
		createCriteria.andPayCheckEqualTo("0");
		if(applyStatus!=null){
			createCriteria.andApplyStatusEqualTo(applyStatus);
			try {
				queryRechargeApply = rechargeOfflineManager.queryRechargeApply(offLineExample);
			} catch (UPPException e) {
				logger.error("按申请状态查询线下交易申请列表错误：", e);
				throw e;
			}
			return queryRechargeApply;
		}else if(orderId!=null){
			createCriteria.andOrderIdEqualTo(orderId);
			try {
				queryRechargeApply = rechargeOfflineManager.queryRechargeApply(offLineExample);
			} catch (UPPException e) {
				logger.error("按申请状态查询线下交易申请列表错误：", e);
				throw e;
			}
			return queryRechargeApply;
		}
		return null;
	}
	
	//按条件查询订单交易列表
	private List<OrderInfo> quartyOrderInfoList(String orderType ,String id) throws UPPException{
		List<OrderInfo> queryOrderInfoList = new ArrayList<OrderInfo>();
		OrderInfoExampleExtended orderInfoExample = new OrderInfoExampleExtended();
		if(orderType!=null){
			orderInfoExample.createCriteria().andOrderTypeEqualTo(orderType);
			try {
				queryOrderInfoList = iOrderManager.queryOrderInfoList(orderInfoExample);
			} catch (UPPException e) {
				logger.error("按订单类型查询订单交易申请列表错误：", e);
			}
			return queryOrderInfoList;
		}else if(id!=null){
			orderInfoExample.createCriteria().andIdEqualTo(id);
			try {
				queryOrderInfoList = iOrderManager.queryOrderInfoList(orderInfoExample);
			} catch (UPPException e) {
				logger.error("按订单类型查询订单交易申请列表错误：", e);
				throw e;
			}
			return queryOrderInfoList;
		}
		return null;		
	}
	
	//修改线下交易申请对账状态
	private void changePayCheck(OfflineRechargeApply apply , String payCheck) throws UPPException{
		apply.setPayCheck(payCheck);
		try {
			rechargeOfflineManager.modifyRechargeApply(apply);
		} catch (UPPException e) {
			logger.error("修改线下交易申请对账状态错误：", e);
			throw e;
		}
	}
	
	private void saveBalanceErrorMessage(long D_value,boolean istPositiv,OrderInfo orderInfo) throws UPPException{
		// 添加商户差错表信息
		CheckOrderDivergence unbalanceDetailWithBusPlatform = new CheckOrderDivergence();
		//订单ID
		unbalanceDetailWithBusPlatform.setPayOrderId(orderInfo.getId());
		//对账日期
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		unbalanceDetailWithBusPlatform.setAccountDate(format.format(date));
		//对账时间
		unbalanceDetailWithBusPlatform.setCheckAccountTime(date.getTime());
		//失败原因
		unbalanceDetailWithBusPlatform.setFailReason("线下申请交易金额与生产的订单金额有差异！");
		//错误描述
		if(istPositiv){
			unbalanceDetailWithBusPlatform.setErrorDesc("订单金额比申请交易金额少"+Math.abs(D_value)+"。(单位：分)");
		}else{
			unbalanceDetailWithBusPlatform.setErrorDesc("订单金额比申请交易金额多"+Math.abs(D_value)+"。(单位：分)");
		}
		
		//对账差异是否已处理
		unbalanceDetailWithBusPlatform.setIsDispose(BalanceOfAccountDict.BALANCE_OF_ACCOUNT_STATUS_FALSE);
		//差异金额
		unbalanceDetailWithBusPlatform.setDivergenceAmount(D_value);
		//交易金额
		unbalanceDetailWithBusPlatform.setTradeAmount(orderInfo.getOrderAmount());
		//内部账户号
		unbalanceDetailWithBusPlatform.setAccountId(orderInfo.getCollectMoneyAccountNo());
		//业务订单号
		unbalanceDetailWithBusPlatform.setWorkOrderNo(orderInfo.getWorkOrderNo());
		//支付订单的订单号
		unbalanceDetailWithBusPlatform.setOrderNo(orderInfo.getOrderNo());
	}
}
