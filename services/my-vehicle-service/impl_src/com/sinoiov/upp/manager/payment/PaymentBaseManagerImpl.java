package com.sinoiov.upp.manager.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.HPaymentTrade;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.PayOrderSubjectDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.root.util.TradeNumberHelper;

@Service("paymentBaseManager")
public class PaymentBaseManagerImpl  extends AbstractPaymentManager implements IPaymentBaseManager{
	private static Log logger = LogFactory.getLog(PaymentBaseManagerImpl.class);
	
	@Override
	public List<PaymentTrade> queryPaymentTrans(PaymentTradeExampleExtended exampleExtended) throws UPPException {
		List<PaymentTrade> list = new ArrayList<PaymentTrade>();
		try {
			list = super.getModels(exampleExtended);
		} catch (Exception e) {
			logger.debug("查询业务订单数据时出错！", e);
			throw new UPPException("查询业务订单数据时出错！", e);
		}
		return list;
	}
	@Override
	public PaymentTrade savePaymentTradeByOrderInfo(OrderInfo orderInfo)
			throws UPPException {
		PaymentTrade paymentTrade = new PaymentTrade();

		super.copyOrderToTransaction(orderInfo, paymentTrade);
		paymentTrade.setPayNo(TradeNumberHelper.getPayNo(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY));
		paymentTrade = this.createPaymentTrans(paymentTrade);		

		return paymentTrade;
	}
	
	@Override
	public PaymentTrade createPaymentTrans(PaymentTrade paymentTrans)
			throws UPPException {
		try{
			
			paymentTrans.setCreateTime(new Date().getTime());//交易创建时间
			paymentTrans.setCreateSubareaTime(new Date());//当前时间
			paymentTrans.setPayConfirmDate(new Long(0));//支付确认时间
			paymentTrans.setPayPoundScale(new Long(0));//手续费
			paymentTrans.setVersion(0);
			paymentTrans.setTradeStatus(PayDict.PAY_TRADE_STATUS_INIT);//初始状态
			//必填项
			if(StringUtils.isBlank(paymentTrans.getOrderId()))
				throw new UPPException("订单号不能为空");	
			if(StringUtils.isBlank(paymentTrans.getTradeExternalNo()))
				throw new UPPException("内部交易流水号不能为空");
			if(paymentTrans.getOrderAmount()<0)
				throw new UPPException("交易金额必须大于0");
			
			String uuid = super.addModel(paymentTrans);
			paymentTrans.setId(uuid);
			
			return paymentTrans;
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建交易时异常", e);
			throw new UPPException("创建交易时异常");
		}
	}
	
	@Override
	public String savePaymentTradeHistory(String id) throws UPPException{
		try{
			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade = (PaymentTrade) super.getModelById(paymentTrade);
			HPaymentTrade hPaymentTrade = new HPaymentTrade();
			PropertyUtils.copyProperties(hPaymentTrade, paymentTrade);
			hPaymentTrade.setRecordCreateTime(new Date().getTime());//创建时间
			hPaymentTrade.setPayTradeId(paymentTrade.getId());
			return super.addModel(hPaymentTrade);
		}catch(Exception e){
			logger.error("保存交易历史异常", e);
			throw new UPPException("保存交易历史异常",e);
		}
	}

	@Override
	public boolean updatePaymentTransStatus(String id, String status, Long confirmDate,Integer version)
			throws UPPException {
		try{
			OrderInfo condition=new OrderInfo();
			condition.setId(id);
			condition.setVersion(version);
			
			this.savePaymentTradeHistory(id);//保存历史
			
			PaymentTrade paymentTrade = new PaymentTrade();

			paymentTrade.setTradeStatus(status);
	
			if(confirmDate!=null){
				paymentTrade.setPayConfirmDate(confirmDate>0?confirmDate:new Date().getTime());//确认时间
			}
			this.savePaymentTradeHistory(id);//订单快照
			int count = super.updateModelByOtherModel(paymentTrade, condition);//修改状态
			if(count>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error("更新交易状态异常", e);
			throw new UPPException("更新交易状态异常",e);
		}
	}
	@Override
	public boolean updatePaymentTransStatus(String id, String status, Long confirmDate)
			throws UPPException {
		try{
			
			this.savePaymentTradeHistory(id);//保存历史
			
			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade.setTradeStatus(status);

			if(confirmDate!=null){
				paymentTrade.setPayConfirmDate(confirmDate>0?confirmDate:new Date().getTime());//确认时间
			}
			this.savePaymentTradeHistory(id);//订单快照
			int count = super.updateModelPart(paymentTrade);//修改状态
			if(count>0){
				return true;
			}else{
				return false;
			}	
		}catch(Exception e){
			logger.error("更新交易状态异常", e);
			throw new UPPException("更新交易状态异常",e);
		}
	}
	@Override
	public PaginationResult<PaymentTrade> queryPaymentTradePage(DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<PaymentTrade> result = new PaginationResult<PaymentTrade>();
		try {
			PaymentTradeExampleExtended exampleExtended = new PaymentTradeExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);
			result = super.paginateModels(exampleExtended);
		} catch (Exception e) {
			logger.error("根据条件分页查询交易信息时异常",e);
			throw new UPPException("根据条件分页查询交易信息时异常");
		}
		return result;
	}
	@Override
	public PaymentTrade getPaymentTradeById(String tradeUUID) throws UPPException {
		PaymentTrade trade = new PaymentTrade();
		try {
			trade.setId(tradeUUID);
			trade = super.getModelById(trade);
		} catch (Exception e) {
			logger.error("根据ID查询交易信息异常",e);
			throw new UPPException("根据ID查询交易信息异常");
		}
		return trade;
	}
	@Override
	public List<PaymentTrade> getPaymentTradesByOrderId(String orderId) throws UPPException {
		try {
			PaymentTradeExampleExtended exampleExtended=new PaymentTradeExampleExtended();
			exampleExtended.createCriteria().andOrderIdEqualTo(orderId);
			exampleExtended.setOrderByClause("  CREATE_TIME DESC ");
			List<PaymentTrade> list = super.getModels(exampleExtended);
			if(!list.isEmpty()&&list.size()>0){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("根据ID查询交易信息异常",e);
			throw new UPPException("根据ID查询交易信息异常");
		}
	}
	@Override
	public PaymentTrade getPaymentTradeByOrderId(String orderId) throws UPPException {
		try {
			
			PaymentTradeExampleExtended exampleExtended=new PaymentTradeExampleExtended();
			exampleExtended.createCriteria().andOrderIdEqualTo(orderId);
			List<PaymentTrade> list = super.getModels(exampleExtended);
			if(!list.isEmpty()&&list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("根据ID查询交易信息异常",e);
			throw new UPPException("根据ID查询交易信息异常");
		}
	}
	@Override
	public PaymentTrade getPaymentTradeByPayNo(String pay_no)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			PaymentTradeExampleExtended example = new PaymentTradeExampleExtended();
			example.createCriteria().andPayNoEqualTo(pay_no);
			List<PaymentTrade> list = super.getModels(example);
			if(list != null && list.size() > 0) {
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("根据PayNo查询交易信息异常",e);
			throw new UPPException("根据PayNo查询交易信息异常");
		}
	}
	@Override
	public void updateOrderAndTrade(OrderInfo orderInfo,PaymentTrade paymentTrade) throws UPPException {
		try{
			
			
			this.updateModelPart(orderInfo);
			
			
			paymentTrade.setBankCode(orderInfo.getBankCode());
			paymentTrade.setBankName(orderInfo.getBankName());
			paymentTrade.setServiceProviderCode(orderInfo.getServiceProviderCode());
			
			this.updateModelPart(paymentTrade);
		} catch (Exception e) {
			logger.error("根据ID查询交易信息异常",e);
			throw new UPPException("根据ID查询交易信息异常");
		}
	}
}
