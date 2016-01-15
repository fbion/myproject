package com.sinoiov.upp.business.payment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.payment.dao.beans.AccountFrozenDetail;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.dto.beans.FreezeAccountResultBean;
import com.ctfo.upp.dict.OrderAndTradeAttributeKey;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.PayOrderSubjectDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.root.utils.SmsSender;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.business.AbstractBusiness;
import com.sinoiov.upp.callback.IBusinessCallBack;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.manager.payment.IFrozenDetailManager;
import com.sinoiov.upp.manager.payment.IOrderManager;
import com.sinoiov.upp.manager.payment.IPaymentBaseManager;
import com.sinoiov.upp.manager.payment.IPaymentCheckManager;
import com.sinoiov.upp.manager.payment.IPaymentTransManager;
import com.sinoiov.upp.service.dto.TradeDto;
import com.sinoiov.upp.util.DefaultConfig;
import com.sinoiov.yyzc.commons.kafka.YyzcKafkaBasicProducer;



@Service("paymentTradeBusiness")
public class PaymentTradeBusinessImpl extends AbstractBusiness implements IPaymentTradeBusiness {
	private static Log logger = LogFactory.getLog(PaymentTradeBusinessImpl.class);
	static final private ReadWriteLock lock = new ReentrantReadWriteLock();
	static final private ReadWriteLock handleLock = new ReentrantReadWriteLock();
	@Autowired
	@Qualifier("orderManager")
	private IOrderManager orderManager;
	@Autowired
	@Qualifier("paymentBaseManager")
	private IPaymentBaseManager paymentBaseManager;
	
	@Autowired
	@Qualifier("paymentTransManager")
	private  IPaymentTransManager  paymentTransManager;
	
	@Autowired
	@Qualifier("paymentCheckManager")
	private  IPaymentCheckManager  paymentCheckManager;
	
	@Autowired
	@Qualifier("frozenDetailManager")
	private  IFrozenDetailManager  frozenDetailManager;
	
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;	
	
	private IBusinessCallBack businessCallBack;
	
	private IBusinessCallBack getCallBack(){
		return businessCallBack!=null?businessCallBack:(IBusinessCallBack) ServiceFactory.getFactory().getService(IBusinessCallBack.class);
	}
	
	private OrderInfo queryWorkBusinessResouceOrder(String storeCode,String workOrderNo) throws UPPException{
		
		try {
			OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
			exampleExtended.createCriteria().andStoreCodeEqualTo(storeCode).andWorkOrderNoEqualTo(workOrderNo).andOrderTypeEqualTo(PayDict.ORDER_SUBJECT_FREEZE_MONEY);
			List<OrderInfo> list=orderManager.queryOrderInfo(exampleExtended);
			return list.get(0);
		} catch (UPPException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}
	}
	private void sendSms(OrderInfo orderInfo,String smsCode) throws Exception{
		//TODO
		try {
			//当成功时向相关用户发送短信collectMoneyAccountNo
			Date date = new Date();
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			String confirmTime = format.format(date);  
			Account account = accountManager.getAccountByAccountNo(orderInfo.getCollectMoneyAccountNo());
			String mobileNo = account.getMobileNo();//获得用户绑定的手机号
			String amount = AmountUtil.getAmount(orderInfo.getOrderAmount());//金额
			String usableBalance = AmountUtil.getAmount(account.getUsableBalance());
			List<String> contents =new ArrayList<String>();
			contents.add(confirmTime);
			contents.add(amount);
			contents.add(usableBalance);
			SmsSender.getInstance().sendSmsByTemplate(mobileNo, smsCode, contents);
		} catch (Exception e) {
			logger.error("充值后向用户发送短信异常",e);
		}
	}
	private List<TradeDto> copyPaymentTrade(Collection<PaymentTrade> collention)throws Exception{		
		
		List<TradeDto> list = new ArrayList<TradeDto>();
		TradeDto dto = null;
		for(PaymentTrade trade : collention){
			dto = new TradeDto();
			BeanUtils.copyProperties(trade, dto);
			if(StringUtils.isNotBlank(trade.getOrderId())){
				OrderInfo order = orderManager.getOrderInfoById(trade.getOrderId());
				dto.setWorkOrderNo(order==null?"":order.getWorkOrderNo());
			}				
			list.add(dto);
		}
				
//		List<Runnable> tasks = new ArrayList<Runnable>();
//		final List<TradeDto> list = new ArrayList<TradeDto>();
//		for(final PaymentTrade trade : collention){
//			final TradeDto dto = new TradeDto();
//			Runnable task = new Runnable() {
//				@Override
//				public void run() {
//					try {
//						BeanUtils.copyProperties(trade, dto);
//						if(StringUtils.isNotBlank(trade.getOrderId())){
//							OrderInfo order = orderManager.getOrderInfoById(trade.getOrderId());
//							dto.setWorkOrderNo(order==null?"":order.getWorkOrderNo());
//						}				
//						list.add(dto);
//					} catch (Exception e) {
//						logger.error("" + e);
//					}
//				}
//			};
//			tasks.add(task);
//		}
//		TaskPool.completeTask(tasks);
		
		return list;
	}
	/**
	 * 判断订单是否需要重新生成交易
	 * @param orderInfo
	 * @param sourceOrderInfo
	 * @return
	 * @throws UPPException
	 */
	private boolean checkTradeCreateOrModify(OrderInfo orderInfo,OrderInfo sourceOrderInfo) throws UPPException{
		boolean flag = false;
		orderInfo.setId(sourceOrderInfo.getId());
		if(orderInfo.getPayChannel().equals(sourceOrderInfo.getPayChannel())){
			if(orderInfo.getPayChannel().equals(PayDict.CHANNEL_NET)){
				if(orderInfo.getBankCode().equals(sourceOrderInfo.getBankCode())){//支付方式为网银，且选择的银行不变
					flag=false;
				}else{
					flag=true;
				}
			}else{//支付方式为快捷将不产生新交易，防重复处理将交给第三方支付去处理
				flag=false;
			}	
		}else{ //渠道状态变更,需要重新生成交易
			
			flag=true;
		}
		return flag;
	}
	/**
	 * 1.判断是否生成新的订单
	 * 2.生成并保存新的交易
	 * 
	 */
	private OrderInfo handleOrderInfo(OrderInfo orderInfo, OrderInfo sourceOrderInfo,String orderSubject, String channel)throws UPPException{
		
		if(orderInfo==null || sourceOrderInfo==null 
				|| StringUtils.isBlank(orderSubject)
				|| StringUtils.isBlank(channel)
				)
			throw new UPPException(ReturnCodeDict.IS_NULL,"参数不合法!");		
		//是否生成新的订单
		if(this.checkTradeCreateOrModify(orderInfo, sourceOrderInfo)){
			//以下属性不能修改
			orderInfo.setId(sourceOrderInfo.getId());
			orderInfo.setOrderAmount(sourceOrderInfo.getOrderAmount());//金额
			orderInfo.setOrderStatus(sourceOrderInfo.getOrderStatus());
			orderInfo.setOrderType(sourceOrderInfo.getOrderType());
			orderInfo.setCreateTime(sourceOrderInfo.getCreateTime());
			orderManager.editOrderInfo(orderInfo);	
		}else{
			//以下属性不能修改
			sourceOrderInfo.setCallbackUrl(orderInfo.getCallbackUrl());
			sourceOrderInfo.setFcallbackUrl(orderInfo.getFcallbackUrl());
			sourceOrderInfo.setClentType(orderInfo.getClentType());
			orderInfo=sourceOrderInfo;
		}
		orderInfo.setOrderSubject(orderSubject);
		orderInfo.setPayChannel(channel);
		return orderInfo;
	}
	//补全交易订单数据
	private void setPaymentTradeOrderInfo(OrderInfo orderInfo){
		try{
			Account account = null;
			String accountNo = StringUtils.isNotBlank(orderInfo.getAccountNo())?orderInfo.getAccountNo():"";
			String collectMoneyAccountNo = StringUtils.isNotBlank(orderInfo.getCollectMoneyAccountNo())?orderInfo.getCollectMoneyAccountNo():"";
			//支付大部分付款账户和收款账户是同一账户
			if(accountNo.equals(collectMoneyAccountNo) && StringUtils.isNotBlank(accountNo)){
				if(StringUtils.isBlank(orderInfo.getOwnerUserNo())){
					account = accountManager.getAccountByAccountNo(accountNo);
					orderInfo.setOwnerUserNo(account != null?account.getOwnerUserNo():"");
				}
				if(StringUtils.isBlank(orderInfo.getUserId())){
					orderInfo.setUserId(account != null?account.getOwnerUserId():"");
				}
				if(StringUtils.isBlank(orderInfo.getCollectMoneyUserId())){
					orderInfo.setCollectMoneyUserId(account != null?account.getOwnerUserId():"");
				}
			}else{
				if(StringUtils.isBlank(orderInfo.getOwnerUserNo())){
					account = accountManager.getAccountByAccountNo(StringUtils.isNotBlank(accountNo)?accountNo:collectMoneyAccountNo);
					orderInfo.setOwnerUserNo(account != null?account.getOwnerUserNo():"");
				}
				if(StringUtils.isBlank(orderInfo.getUserId()) && StringUtils.isNotBlank(orderInfo.getAccountNo())){
					account = accountManager.getAccountByAccountNo(orderInfo.getAccountNo());
					orderInfo.setUserId(account != null?account.getOwnerUserId():"");
				}
				if(StringUtils.isBlank(orderInfo.getCollectMoneyUserId()) && StringUtils.isNotBlank(orderInfo.getCollectMoneyAccountNo())){
					account = accountManager.getAccountByAccountNo(orderInfo.getCollectMoneyAccountNo());
					orderInfo.setCollectMoneyUserId(account != null?account.getOwnerUserId():"");
				}
			}
			
		}catch(Exception e){
			logger.warn("补全交易订单数据异常", e);
		}
	}
	
	@Override
	public String netRecharge(OrderInfo orderInfo)
			throws UPPException {
		try {
			logger.info("网银充值交易，WorkOrderNo："+orderInfo.getWorkOrderNo()+",tradeNo:"+orderInfo.getTradeExternalNo());
			
			
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_RECHARGE);
			
			OrderInfo sourceOrderInfo = paymentCheckManager.orderIsExits(orderInfo.getWorkOrderNo(), orderInfo.getStoreCode());
			
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_RECHARGE);
			orderInfo.setPayChannel(PayDict.CHANNEL_NET);
			PaymentTrade paymentTrade =null;
			if(sourceOrderInfo==null){
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);
				
				orderInfo.setBusinessStepNo((short) 1);
				orderInfo.setCollectMoneyAccountNo(orderInfo.getAccountNo());
				this.setPaymentTradeOrderInfo(orderInfo);
				orderManager.createOrderInfo(orderInfo);
				paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			}else{	
				if(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(sourceOrderInfo.getOrderStatus()))
					throw new UPPException(ReturnCodeDict.ORDER_IS_PAYED, "订单已经支付完成，请勿重复支付");	
				orderInfo = this.handleOrderInfo(orderInfo, sourceOrderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_RECHARGE, PayDict.CHANNEL_NET);
				paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);				
//				if(this.checkTradeCreateOrModify(orderInfo, sourceOrderInfo)){
//					orderInfo.setId(sourceOrderInfo.getId());
//					orderManager.editOrderInfo(orderInfo);
//					paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
//				}else{
//					orderInfo=sourceOrderInfo;
//					//paymentTrade=paymentBaseManager.getPaymentTradeByOrderId(sourceOrderInfo.getId());
//					paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
//				}
			}
			String url = paymentTransManager.callGateWayForNetRecharge(paymentTrade, orderInfo);
			return url;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}
	
	@Override
	public void netRechargeCallBack(PaymentTrade paymentTrade)throws UPPException {
		try{
			logger.debug("<<<<<网银充值回调，PayNo:"+paymentTrade.getPayNo());
			Integer version= paymentCheckManager.tradeIsHandled(paymentTrade.getId());
			OrderInfo orderInfo = orderManager.getOrderInfoById(paymentTrade.getOrderId());
			boolean orderStatusFlag  = paymentCheckManager.orderIsHandled(orderInfo);
			if( PayDict.PAY_TRADE_STATUS_PAY_SUCCESS.equals(paymentTrade.getTradeStatus())){
				if(orderStatusFlag)
					orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_ORDER_STATUS_PAY_SUCCESS, orderInfo.getPayConfirmDate(),(short) 2,PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_RECHARGE,orderInfo.getVersion());
				
				
				if(paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_SUCCESS, orderInfo.getPayConfirmDate(),version))
					paymentTransManager.accountRecordedMoneyHandle(orderInfo);//修改完成，进行入账操作
				//当成功时向相关用户发送短信collectMoneyAccountNo
				//当成功时向相关用户发送短信collectMoneyAccountNo
				try {
					this.sendSms(orderInfo, "tyzfpt1006");
				} catch (Exception e1) {
					logger.error("网银充值发送短信失败",e1);
				}
				logger.info("<<<<<网银充值回调返回订单状态为成功，入账完成，OrderId:"+orderInfo.getId()+",订单号："+orderInfo.getOrderNo());
			}else{
				logger.info("<<<<<网银充值回调返回订单状态为失败，OrderId:"+orderInfo.getId());
				if(orderStatusFlag)
					orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_TRADE_STATUS_PAY_FAIL, orderInfo.getPayConfirmDate(),(short) 2,PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_RECHARGE,orderInfo.getVersion());
				
				paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_FAIL, orderInfo.getPayConfirmDate(),version);
			}
			
			logger.info(orderInfo.getTradeExternalNo()+":入账操作完成.");
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public String fastPayRecharge(OrderInfo orderInfo)
				throws UPPException {
		try{
			logger.info("快捷充值交易，WorkOrderNo："+orderInfo.getOrderNo()+",tradeNo:"+orderInfo.getTradeExternalNo());
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE);
			
			OrderInfo sourceOrderInfo = paymentCheckManager.orderIsExits(orderInfo.getWorkOrderNo(), orderInfo.getStoreCode());
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE);
			orderInfo.setPayChannel(PayDict.CHANNEL_FASTPAY);
			PaymentTrade paymentTrade =null;
			if( sourceOrderInfo==null){	
				orderInfo.setBusinessStepNo((short) 1);
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);				
				orderInfo.setCollectMoneyAccountNo(orderInfo.getAccountNo());
				this.setPaymentTradeOrderInfo(orderInfo);
				orderManager.createOrderInfo(orderInfo);			
				paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			}else{		
				if(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(sourceOrderInfo.getOrderStatus()))
					throw new UPPException(ReturnCodeDict.ORDER_IS_PAYED, "订单已经支付完成，请勿重复支付");
				orderInfo = this.handleOrderInfo(orderInfo, sourceOrderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE, PayDict.CHANNEL_FASTPAY);
				paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
//				if(this.checkTradeCreateOrModify(orderInfo, sourceOrderInfo)){
//					orderInfo.setId(sourceOrderInfo.getId());
//					orderManager.editOrderInfo(orderInfo);
//					paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
//				}else{
//					orderInfo=sourceOrderInfo;
//					//paymentTrade=paymentBaseManager.getPaymentTradeByOrderId(sourceOrderInfo.getId());
//					paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
//				}
			}

			String url = paymentTransManager.callGateWayForFastRecharge(paymentTrade, orderInfo);
			return url;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}
	@Override
	public void fastPayRechargeCallBack(PaymentTrade paymentTrade)
			throws UPPException{
		try{
			logger.debug("<<<<<快捷充值回调>>>>>，PayNo:"+paymentTrade.getPayNo());
			
			Integer version= paymentCheckManager.tradeIsHandled(paymentTrade.getId());
			OrderInfo orderInfo = orderManager.getOrderInfoById(paymentTrade.getOrderId());
			boolean orderStatusFlag  = paymentCheckManager.orderIsHandled(orderInfo);
	
			if( PayDict.PAY_TRADE_STATUS_PAY_SUCCESS.equals(paymentTrade.getTradeStatus())){
				if(orderStatusFlag)
					orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_ORDER_STATUS_PAY_SUCCESS, orderInfo.getPayConfirmDate(),(short) 2,PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE,orderInfo.getVersion());
				
				
				if(paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_SUCCESS, orderInfo.getPayConfirmDate(),version))
					paymentTransManager.accountRecordedMoneyHandle(orderInfo);//修改完成，进行入账操作
				//当成功时向相关用户发送短信collectMoneyAccountNo
				try {
					this.sendSms(orderInfo, "tyzfpt1006");
				} catch (Exception e1) {
					logger.error("快捷充值发送短信失败",e1);
				}
				
				logger.info("<<<<<快捷充值回调返回订单状态为成功，入账完成，OrderId:"+orderInfo.getId()+",订单号："+orderInfo.getOrderNo());
			}else{
				logger.info("<<<<<快捷充值回调返回订单状态为失败，OrderId:"+orderInfo.getId());
				if(orderStatusFlag)
					orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_TRADE_STATUS_PAY_FAIL, orderInfo.getPayConfirmDate(),(short) 2,PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE,orderInfo.getVersion());
				
				paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_FAIL, orderInfo.getPayConfirmDate(),version);
			}
			
			logger.info(orderInfo.getTradeExternalNo()+":入账操作完成.");
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	/**
	 * 1.保存订单
	 * 2.发送订单到消息队列
	 */
	@Override
	public List<OrderInfo> batchAccountRecharge(List<OrderInfo> list)
			throws UPPException {
		List<OrderInfo> result = new ArrayList<OrderInfo>();
		try{
			
			YyzcKafkaBasicProducer client = new YyzcKafkaBasicProducer();
			for(OrderInfo orderInfo : list){
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);//充值
				result.add(saveOrderInfo(orderInfo));
				//发送消息到队列
				client.send(DefaultConfig.getValue("PAY_ACCOUNT_BATCH_RECHARGE"), orderInfo);
			}
					
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("批量给用户充值时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "批量给用户充值时异常:"+e.getLocalizedMessage());
		}
		return result;
	}
	//防止多线程保存订单时造成数据死锁
	private  OrderInfo saveOrderInfo(OrderInfo orderInfo)throws Exception{	
		try{
			//锁
			lock.writeLock().lock();
			this.setPaymentTradeOrderInfo(orderInfo);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_BATCH_RECHARGE);
			orderInfo = orderManager.createOrderInfo(orderInfo);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("保存订单时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "保存订单时异常:"+e.getLocalizedMessage());
		}finally {
			lock.writeLock().unlock();
		}
		return orderInfo;	
	}
	//处理批量充值带锁
	private void handleAccountRecharge(OrderInfo orderInfo)throws Exception{		
		try{
			//锁
			handleLock.writeLock().lock();
			
			String id = orderInfo.getId();//已经保存过了,直接取ID
			if(StringUtils.isBlank(id))
				throw new UPPException(ReturnCodeDict.IS_NULL,"订单ID不能为空");					
			Long payConfirmDate = System.currentTimeMillis();
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);//直接设置为成功
			orderInfo.setPayConfirmDate(payConfirmDate);			
			paymentTransManager.accountRecordedMoneyHandle(orderInfo);		
			orderManager.updateOrderInfoStatus(id, orderInfo.getOrderStatus(), orderInfo.getPayConfirmDate());
			//回调
			try{this.getCallBack().callBack(orderInfo);}catch(Exception e){logger.error("批量充值回调异常"+e.getMessage(),e);}
		
		}catch(Exception e){
			throw e;
		}finally{
			handleLock.writeLock().unlock();
		}
	}
	/**
	 * 处理消息队列中用户充值订单
	 * 1.处理订单
	 * ２.回调业务系统
	 */
	@Override
	public void handleBatchAccountRecharge(OrderInfo orderInfo)throws UPPException {
		try{
			
			this.handleAccountRecharge(orderInfo);
			
		}catch(Exception e){
			logger.error("批量给用户充值时异常:"+e.getMessage(), e);
			try{
				Thread.sleep(1000);
				this.handleAccountRecharge(orderInfo);
			}catch(Exception e1){
				logger.error("2批量给用户充值时异常！", e1);
				throw new UPPException(ReturnCodeDict.ERROR, "批量给用户充值时异常:"+e.getLocalizedMessage());
			}	
		}
	}
	
	/**
	 * 1.给用户充值
	 * 2.改订单状态
	 */
	@Override
	public OrderInfo accountRecharge(OrderInfo orderInfo)
			throws UPPException {
		
		try{
			Long payConfirmDate = System.currentTimeMillis();
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_RECHARGE);
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);//直接设置为成功
			orderInfo.setPayConfirmDate(payConfirmDate);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_RECHARGE);
			orderInfo.setBankCardType(PayDict.CARD_TYPE_DEBIT);
			this.setPaymentTradeOrderInfo(orderInfo);
			orderManager.createOrderInfo(orderInfo);
					
			paymentTransManager.accountRecordedMoneyHandle( orderInfo);
			
			return orderInfo;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("批量给用户充值时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "批量给用户充值时异常:"+e.getLocalizedMessage());
		}

	}

	@Override
	public OrderInfo transferAccounts(OrderInfo orderInfo)
			throws UPPException {
		try{			
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_TRANSFER_ACCOUNT);
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_TRANSFER_ACCOUNT);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_TRANSFER_ACCOUNT);
			this.setPaymentTradeOrderInfo(orderInfo);
			orderManager.createOrderInfo(orderInfo);
			PaymentTrade paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			
			//如果有相同业务订单号的冻结金额，从冻结金额中扣除
			boolean isforzen = false;
			if(StringUtils.isNotBlank(orderInfo.getWorkOrderNo()) && StringUtils.isNotBlank(orderInfo.getStoreCode())){
				OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
				exampleExtended.createCriteria().andWorkOrderNoEqualTo(orderInfo.getWorkOrderNo())
				.andOrderTypeEqualTo(PayDict.ORDER_SUBJECT_FREEZE_MONEY)
				.andStoreCodeEqualTo(orderInfo.getStoreCode());
				int count = orderManager.getOrderCount(exampleExtended);
				if(count>0){
					isforzen = true;
					frozenDetailManager.increaseUnFrozenAmount(orderInfo.getOrderAmount(), orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
				}
			}			
			//转账操作
			paymentTransManager.transferAccounts(orderInfo.getStoreCode(), paymentTrade, isforzen);
			orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_ORDER_STATUS_PAY_SUCCESS, paymentTrade.getPayConfirmDate());
			paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_SUCCESS, paymentTrade.getPayConfirmDate());
			return orderInfo;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public FreezeAccountResultBean freezeAccountMoney(OrderInfo orderInfo)
			throws UPPException {
		try{
			FreezeAccountResultBean resultBean = new FreezeAccountResultBean();
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FREEZE);
			paymentCheckManager.addOrUpdateAccountFrozenDetail(orderInfo);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FREEZE);
			orderInfo.setPayConfirmDate(System.currentTimeMillis());
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
			this.setPaymentTradeOrderInfo(orderInfo);
			//保存订单
			orderInfo = orderManager.createFreezeOrderInfo(orderInfo);
			
			Map<String,Object> result =paymentTransManager.freezeAccountHandle(orderInfo);
			
			resultBean.setReturnCode(ReturnCodeDict.SUCCESS);
			resultBean.setTradeExternalNo((String) result.get(OrderAndTradeAttributeKey.TRADE_EXTERNAL_NO	));
			resultBean.setPayConfirmDate(	(Long) result.get(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE	));
			resultBean.setOrderNo(orderInfo.getOrderNo());
			return resultBean;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public FreezeAccountResultBean unFreezeAccountMoney(OrderInfo orderInfo)
			throws UPPException {
		try{
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_UNFREEZE);
			FreezeAccountResultBean resultBean = new FreezeAccountResultBean();
			orderInfo.setPayConfirmDate(System.currentTimeMillis());
			orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_UNFREEZE);
		
			paymentCheckManager.accountFrozenDetailCheck(orderInfo.getStoreCode(), orderInfo.getWorkOrderNo(), orderInfo.getOrderAmount());		
			
			this.setPaymentTradeOrderInfo(orderInfo);
			orderInfo = orderManager.createOrderInfo(orderInfo);		
			
			Map<String,Object> result =paymentTransManager.unfreezeAccountHandle(orderInfo);
	
			frozenDetailManager.increaseUnFrozenAmount(orderInfo.getOrderAmount(), orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
			resultBean.setReturnCode(ReturnCodeDict.SUCCESS);
			resultBean.setTradeExternalNo((String) result.get(OrderAndTradeAttributeKey.TRADE_EXTERNAL_NO	));
			resultBean.setPayConfirmDate(	(Long) result.get(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE	));
			return resultBean;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public  OrderInfo accountPay(OrderInfo orderInfo)throws UPPException {
		try{
			OrderInfo sourceOrderInfo = paymentCheckManager.orderIsExits(orderInfo.getWorkOrderNo(), orderInfo.getStoreCode());
			if(sourceOrderInfo==null){
				paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_PAY);
				paymentCheckManager.addOrUpdateAccountFrozenDetail(orderInfo);
				orderInfo.setBusinessStepNo((short) 1);
				orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_PAY);
				orderInfo.setPayChannel(PayDict.CHANNEL_ACCOUNT);
				orderInfo = orderManager.createFreezeOrderInfo(orderInfo);		
			}else{
				if(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(sourceOrderInfo.getOrderStatus()))
					throw new UPPException(ReturnCodeDict.ORDER_IS_PAYED, "订单已经支付完成，请勿重复支付");	
				//以下属性不能修改
				orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_PAY);
				orderInfo.setPayChannel(PayDict.CHANNEL_ACCOUNT);
				orderInfo.setId(sourceOrderInfo.getId());
				orderInfo.setOrderAmount(sourceOrderInfo.getOrderAmount());//金额
				orderInfo.setCreateTime(new Date().getTime());//订单生成时间
				orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);//订单直接设置为成功状态
				orderInfo.setVersion(0);//			
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_FREEZE_MONEY);
				orderInfo.setPayConfirmDate(System.currentTimeMillis());
				
				paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_PAY);
				paymentCheckManager.addOrUpdateAccountFrozenDetail(orderInfo);
				orderInfo.setBusinessStepNo((short) 1);
				
				orderManager.editOrderInfo(orderInfo);
			}
			
			Map<String, Object> result = paymentTransManager.freezeAccountHandle(orderInfo);
			//回调
			try{
				Long payConfirmDate = result.get(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE)==null?
						System.currentTimeMillis():(Long)result.get(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE);
				orderInfo.setPayConfirmDate(payConfirmDate);
				this.getCallBack().callBack(orderInfo);
			}catch(UPPException e){
				logger.error(String.format("%s%s%s%s%s","OrderId:",orderInfo.getId(),",WorkOrderNo:",orderInfo.getWorkOrderNo(),",回调通知业务系统异常:"+e.getMessage()),e);
			}
			
			return orderInfo;

		} catch (UPPException e) {
			logger.error("ErrorCode:"+((UPPException)e.getCause()).getErrorCode()+e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public String netPay(OrderInfo orderInfo) throws UPPException {
		try{
			logger.info("网银支付交易，WorkOrderNo："+orderInfo.getOrderNo()+",tradeNo:"+orderInfo.getTradeExternalNo());

			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_PAY);
			
			OrderInfo sourceOrderInfo = paymentCheckManager.orderIsExits(orderInfo.getWorkOrderNo(), orderInfo.getStoreCode());
			
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_PAY);
			orderInfo.setPayChannel(PayDict.CHANNEL_NET);
			PaymentTrade paymentTrade =null;
			if( sourceOrderInfo==null){	
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);
				orderInfo.setBusinessStepNo((short) 1);
				orderInfo.setCollectMoneyAccountNo(orderInfo.getAccountNo());
				this.setPaymentTradeOrderInfo(orderInfo);
				orderManager.createOrderInfo(orderInfo);	
				paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			}else{
				
				if(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(sourceOrderInfo.getOrderStatus()))
					throw new UPPException(ReturnCodeDict.ORDER_IS_PAYED, "订单已经支付完成，请勿重复支付");
				orderInfo = this.handleOrderInfo(orderInfo, sourceOrderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_PAY, PayDict.CHANNEL_NET);
				paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			}	
			
			String url = paymentTransManager.callGateWayForNetRecharge(paymentTrade,orderInfo);
			return url;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}
	@Override
	public void netPayCallBack(PaymentTrade paymentTrade)throws UPPException {
		try{
			logger.info("<<<<<网银充值回调，PayNo:"+paymentTrade.getPayNo());
			Integer version= paymentCheckManager.tradeIsHandled(paymentTrade.getId());
			OrderInfo orderInfo = orderManager.getOrderInfoById(paymentTrade.getOrderId());
			boolean orderStatusFlag  = paymentCheckManager.orderIsHandled(orderInfo);
			
			if( PayDict.PAY_TRADE_STATUS_PAY_SUCCESS.equals(paymentTrade.getTradeStatus())){
				if(paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_SUCCESS, orderInfo.getPayConfirmDate(),version)){
					paymentTransManager.accountRecordedMoneyHandle(orderInfo);//修改完成，进行入账操作	
				}
				
				if(orderStatusFlag){//只有订单有效时才进行冻结相关操作
					orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_ORDER_STATUS_PAY_SUCCESS, orderInfo.getPayConfirmDate(),(short) 2,PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_PAY,orderInfo.getVersion());
					
					logger.info(orderInfo.getTradeExternalNo()+":入账操作完成，开始冻结操作.");
					orderInfo.setBusinessStepNo((short)3);
					this.setPaymentTradeOrderInfo(orderInfo);
					orderManager.createFreezeOrderInfo(orderInfo);//充值冻结订单
					
					paymentTransManager.freezeAccountHandle(orderInfo);
					frozenDetailManager.frozenLimitHandle(orderInfo);
				}

			}else{

				logger.info("<<<<<网银支付回调返回订单状态为失败，OrderId:"+orderInfo.getId());
				if(orderStatusFlag)
					orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_TRADE_STATUS_PAY_FAIL, orderInfo.getPayConfirmDate(),(short) 2,PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_NET_PAY,orderInfo.getVersion());
				
				paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_FAIL, orderInfo.getPayConfirmDate(),version);
			}
			
			
			try{
				this.getCallBack().callBack(orderInfo);
			}catch(UPPException e){
				
				logger.error( String.format("%s%s%s%s%s","OrderId:",orderInfo.getId(),",WorkOrderNo:",orderInfo.getWorkOrderNo(),",回调通知业务系统异常"));
				logger.error(e.getMessage(),e);
			}
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}
	
	

	@Override
	public String fastPay(OrderInfo orderInfo) throws UPPException {
		try{
			logger.info("快捷扣款申请，WorkOrderNo："+orderInfo.getWorkOrderNo()+",tradeNo:"+orderInfo.getTradeExternalNo());
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY);		
			OrderInfo sourceOrderInfo = paymentCheckManager.orderIsExits(orderInfo.getWorkOrderNo(), orderInfo.getStoreCode());
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY);
			orderInfo.setPayChannel(PayDict.CHANNEL_FASTPAY);
			PaymentTrade paymentTrade =null;
			if( sourceOrderInfo==null){
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);
				orderInfo.setBusinessStepNo((short) 1);
				orderInfo.setCollectMoneyAccountNo(orderInfo.getAccountNo());
				this.setPaymentTradeOrderInfo(orderInfo);
				orderManager.createOrderInfo(orderInfo);	
				paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			}else{
				if(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(sourceOrderInfo.getOrderStatus()))
					throw new UPPException(ReturnCodeDict.ORDER_IS_PAYED, "订单已经支付完成，请勿重复支付");	
				orderInfo = this.handleOrderInfo(orderInfo, sourceOrderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY, PayDict.CHANNEL_FASTPAY);
				paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			}	
			String url = paymentTransManager.callGateWayForFastRecharge(paymentTrade, orderInfo);
			return url;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}
	@Override
	public void fastPayCallBack(PaymentTrade paymentTrade) throws UPPException {
		try{
			logger.info("<<<<<快捷支付回调>>>>>，PayNo:"+paymentTrade.getPayNo());
			Integer version= paymentCheckManager.tradeIsHandled(paymentTrade.getId());
			OrderInfo orderInfo = orderManager.getOrderInfoById(paymentTrade.getOrderId());
			boolean orderStatusFlag  = paymentCheckManager.orderIsHandled(orderInfo);
			
			if( PayDict.PAY_TRADE_STATUS_PAY_SUCCESS.equals(paymentTrade.getTradeStatus())){
				if(paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_SUCCESS, orderInfo.getPayConfirmDate(),version)){
					paymentTransManager.accountRecordedMoneyHandle(orderInfo);//修改完成，进行入账操作
					
				}
				if(orderStatusFlag){//只有订单有效时才进行冻结相关操作
					orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_ORDER_STATUS_PAY_SUCCESS, orderInfo.getPayConfirmDate(),(short) 2,PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY,orderInfo.getVersion());
					
					logger.info(orderInfo.getTradeExternalNo()+":入账操作完成，开始冻结操作.");
					orderInfo.setBusinessStepNo((short)3);
					this.setPaymentTradeOrderInfo(orderInfo);
					orderManager.createFreezeOrderInfo(orderInfo);//充值冻结订单
					
					paymentTransManager.freezeAccountHandle(orderInfo);
					frozenDetailManager.frozenLimitHandle(orderInfo);
				}
			}else{
				logger.info("<<<<<快捷充值回调返回订单状态为失败，OrderId:"+orderInfo.getId());
				if(orderStatusFlag)
					orderManager.updateOrderInfoStatus(orderInfo.getId(), PayDict.PAY_TRADE_STATUS_PAY_FAIL, orderInfo.getPayConfirmDate(),(short) 2,PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY,version);
				
				paymentBaseManager.updatePaymentTransStatus(paymentTrade.getId(), PayDict.PAY_TRADE_STATUS_PAY_FAIL, orderInfo.getPayConfirmDate(),version);

			}
			
			try{
				this.getCallBack().callBack(orderInfo);
			}catch(UPPException e){
				logger.error( String.format("%s%s%s%s%s","OrderId:",orderInfo.getId(),",WorkOrderNo:",orderInfo.getWorkOrderNo(),",回调通知业务系统异常"));
				logger.error(e.getMessage(),e);
			}
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public OrderInfo withdrawCash(OrderInfo orderInfo) throws UPPException {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public OrderInfo withdrawCashRapid(OrderInfo orderInfo) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentTrade getPaymentTradeById(String tradeUUID)
			throws UPPException {
		return paymentBaseManager.getPaymentTradeById(tradeUUID);
	}
	@Override
	public List<TradeDto> queryPaymentTrade(DynamicSqlParameter requestParam)
			throws UPPException {
		List<TradeDto> result = new ArrayList<TradeDto>();
		try {
			PaymentTradeExampleExtended exampleExtended = new PaymentTradeExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);
			List<PaymentTrade> list = paymentBaseManager.queryPaymentTrans(exampleExtended);
			result = this.copyPaymentTrade(list);
		} catch (Exception e) {
			logger.error("根据条件分页查询交易信息集合时异常",e);
			throw new UPPException("根据条件分页查询交易信息集合时异常");
		} 
		return result;
	}


	@Override
	public PaginationResult<TradeDto> queryPaymentTradeByPage(
			DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<TradeDto> result = new PaginationResult<TradeDto>();
		try{			
			PaginationResult<PaymentTrade> tem = paymentBaseManager.queryPaymentTradePage(requestParam);		
			if (tem != null) {
				result = new PaginationResult<TradeDto>();
				result.setTotal(tem.getTotal());
				result.setData(copyPaymentTrade(tem.getData()));
			}		
		} catch (Exception e) {
			logger.error("",e);
			throw new UPPException("");
		} 
		return result;
	}

	@Override
	public AccountFrozenDetail queryAccountFrozenDetailMess(String storeCode,
			String workOrderNo) throws UPPException {
		return frozenDetailManager.queryAccountFrozenDetailMess(storeCode,workOrderNo);
	}

	@Override
	public String fastPayApiRecharge(OrderInfo orderInfo, String type, Map<String, String> param)
			throws UPPException {
		try{
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE);
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);
			orderInfo.setBusinessStepNo((short) 1);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE);
			orderInfo.setCollectMoneyAccountNo(orderInfo.getAccountNo());
			orderInfo.setPayChannel(PayDict.CHANNEL_FASTPAY);
			this.setPaymentTradeOrderInfo(orderInfo);
			orderManager.createOrderInfo(orderInfo);
			PaymentTrade paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			String phone = paymentTransManager.callGateWayForFastApiRecharge(paymentTrade, orderInfo, type, param);
			return phone;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public String fastPayApi(OrderInfo orderInfo, String type, Map<String, String> param) throws UPPException {
		try{
			paymentCheckManager.checkOrder(orderInfo, PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY);
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE);
			orderInfo.setBusinessStepNo((short) 1);
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_FAST_PAY);
			orderInfo.setCollectMoneyAccountNo(orderInfo.getAccountNo());
			orderInfo.setPayChannel(PayDict.CHANNEL_FASTPAY);
			this.setPaymentTradeOrderInfo(orderInfo);
			orderManager.createOrderInfo(orderInfo);
			PaymentTrade paymentTrade = paymentBaseManager.savePaymentTradeByOrderInfo(orderInfo);
			String phone = paymentTransManager.callGateWayForFastApiRecharge(paymentTrade, orderInfo, type, param);
			return phone;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public String fastPayApiSure(String workOrderNo, String messageCode)
			throws UPPException {
		try{
			OrderInfo orderInfo = orderManager.getOrderByWorkOrderNo(workOrderNo);
			PaymentTrade paymentTrade = paymentBaseManager.getPaymentTradeByOrderId(orderInfo.getId());
			String result = paymentTransManager.callGateWayForFastApiRechargeSure(paymentTrade, orderInfo, messageCode);
			return result;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	@Override
	public OrderInfo queryOrderRechargeOverOrNot(String workOrderNo,String tradeExternalNo) throws UPPException {
		OrderInfo orderInfo = new OrderInfo();
		OrderInfoExampleExtended orderInfoExampleExtended = new OrderInfoExampleExtended();
		orderInfoExampleExtended.createCriteria().andWorkOrderNoEqualTo(workOrderNo).andTradeExternalNoEqualTo(tradeExternalNo);
		List<OrderInfo> orderInfoList = orderManager.queryOrderInfo(orderInfoExampleExtended);
		//没有交易订单
		if(orderInfoList == null || orderInfoList.size()<1){
			orderInfo.setOrderStatus("0");
			return orderInfo;
		}else if(orderInfoList != null && orderInfoList.size()>1){
			logger.warn("该业务订单号["+workOrderNo+"]存在多张支付订单!");
			//throw new UPPException("交易不合法，业务订单号["+workOrderNo+"]存在多张支付订单!");
			String flag = "0";
			for(OrderInfo oi : orderInfoList){
				flag = this.querypaymentTransOverOrNot(oi.getId(), oi.getPayChannel());
				if("1".equals(flag)) break;
			}
			orderInfo.setOrderStatus(flag);
		}else if(orderInfoList != null && orderInfoList.size()==1){
			orderInfo=orderInfoList.get(0);
			orderInfo.setOrderStatus("0");
			if(orderInfo.getOrderStatus().equals(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS)) {
				orderInfo.setOrderStatus("1");
			}else {
				String flag = this.querypaymentTransOverOrNot(orderInfo.getId(), orderInfo.getPayChannel());
				orderInfo.setOrderStatus(flag);
			}
		}
		return orderInfo;
	}
	//根据订单查询交易，查询该订单下是否有支付成功的交易
	private String querypaymentTransOverOrNot(String orderId, String payChannel) throws UPPException{
		String flag="0";
		//排序，按最新的交易记录查找
		List<PaymentTrade> paymentTradeList = paymentBaseManager.getPaymentTradesByOrderId(orderId);
		if(paymentTradeList == null) return flag;
		for(PaymentTrade trade:paymentTradeList){
			if(PayDict.CHANNEL_FASTPAY.equals(payChannel)) {
				flag=paymentTransManager.callGateWayForFastQueryRechargeOverOrNot(trade.getPayNo());
			}else if(PayDict.CHANNEL_NET.equals(payChannel)) {
				flag=paymentTransManager.callGateWayForNetQueryRechargeOverOrNot(trade.getPayNo());
			}
			if("1".equals(flag))break;
		}
		return flag;
	}
	
	
	
	@Override
	public OrderInfo payConfirm(OrderInfo orderInfo) throws UPPException{
		try{
			paymentCheckManager.accountFrozenDetailCheck(orderInfo.getStoreCode(), orderInfo.getWorkOrderNo(), orderInfo.getOrderAmount());
			OrderInfo resourceOrder = queryWorkBusinessResouceOrder(orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());	
			orderInfo.setAccountNo(resourceOrder.getAccountNo());
			orderInfo.setCollectMoneyAccountNo(resourceOrder.getCollectMoneyAccountNo());
			this.setPaymentTradeOrderInfo(orderInfo);
			orderManager.createPayConfirmOrderInfo(orderInfo);
			
			paymentTransManager.accountDeductMoneyFromFreeze(orderInfo);
			
			frozenDetailManager.increaseUnFrozenAmount(orderInfo.getOrderAmount(), orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
			try {
				resourceOrder.setCollectMoneyAccountNo(resourceOrder.getAccountNo());
				resourceOrder.setOrderAmount(orderInfo.getOrderAmount());
				//TODO
				//根据业务订单号查询所有订单,当是账户支付时才发送短信
				OrderInfoExampleExtended orderInfoExampleExtended = new OrderInfoExampleExtended();
				orderInfoExampleExtended.createCriteria().andWorkOrderNoEqualTo(orderInfo.getWorkOrderNo());
				List<OrderInfo> orderInfoList = orderManager.queryOrderInfo(orderInfoExampleExtended);
				for(OrderInfo order : orderInfoList){
					if(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_PAY.equals(order.getOrderSubject())){
 						this.sendSms(resourceOrder, "tyzfpt1010");
					}
				}
			} catch (Exception e1) {
				logger.error("账户支付发送扣款短信失败",e1);
			}
			return orderInfo; 
		} catch (Exception e) {
			logger.error("根据条件分页查询交易信息集合时异常",e);
			throw new UPPException("根据条件分页查询交易信息集合时异常");
		}	
	}

	@Override
	public OrderInfo payConfirmCancel(OrderInfo orderInfo) throws UPPException {
		try{
			paymentCheckManager.accountFrozenDetailCheck(orderInfo.getStoreCode(), orderInfo.getWorkOrderNo(), orderInfo.getOrderAmount());
			
			OrderInfo resourceOrder = queryWorkBusinessResouceOrder(orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
			orderInfo.setAccountNo(resourceOrder.getAccountNo());
			orderInfo.setCollectMoneyAccountNo(resourceOrder.getCollectMoneyAccountNo());
			this.setPaymentTradeOrderInfo(orderInfo);
			orderManager.createPayCancelOrderInfo(orderInfo);
			
			paymentTransManager.unfreezeAccountHandle(orderInfo);
			
			frozenDetailManager.increaseUnFrozenAmount(orderInfo.getOrderAmount(), orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
			
			return orderInfo; 
		} catch (Exception e) {
			logger.error("根据条件分页查询交易信息集合时异常",e);
			throw new UPPException("根据条件分页查询交易信息集合时异常");
		}
	}

}