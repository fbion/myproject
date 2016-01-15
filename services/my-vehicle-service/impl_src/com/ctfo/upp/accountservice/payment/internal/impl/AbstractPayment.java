package com.ctfo.upp.accountservice.payment.internal.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.base.dao.beans.BankCardInfo;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.yibao.business.accountaccess.BankCardRechargeBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.entrustsettle.WithDrawQuickBeanRequestYB;
import com.ctfo.gateway.intf.TradeServiceFacade;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.intf.internal.IOrderManager;
import com.ctfo.payment.intf.internal.IPaymentBaseManager;
import com.ctfo.payment.intf.internal.IPaymentTransManager;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.manager.support.UppGenericManagerImpl;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.manager.payment.IFrozenDetailManager;
import com.sinoiov.upp.util.DefaultConfig;
public abstract class AbstractPayment extends UppGenericManagerImpl<Object, Object>{

	private static final Log logger = LogFactory.getLog(AbstractPayment.class);

//	@Autowired
//	@Qualifier("accountManagerService")  
//	protected IAccountManager internalAccountManager;
//	@Autowired
//	@Qualifier("frozenDetailManagerService")  
//	protected IFrozenDetailManager frozenDetailManager;	
//	@Autowired
//	@Qualifier("iOrderManager")  
//	protected IOrderManager iOrderManager;
//	@Autowired
//	@Qualifier("paymentTransManagerService")  
//	protected IPaymentTransManager paymentTransManager;
//	@Autowired
//	@Qualifier("mongoService") 
//	protected IMongoService mongoService;
//
//	@Autowired
//	@Qualifier("paymentBaseManagerService")  
//	protected IPaymentBaseManager paymentBaseManagerService;

	//网关对象
	private TradeServiceFacade tradeServiceFacade;

	protected TradeServiceFacade getGatewayTradeServiceFacade(){

		if(tradeServiceFacade==null){
			tradeServiceFacade = (TradeServiceFacade) ServiceFactory.getFactory().getService(TradeServiceFacade.class);		
			logger.info("------>>>>>初始化网关对象:"+tradeServiceFacade);			
		}

		return tradeServiceFacade;

	}



	/**
	 * 获取账户号
	 * @param userId
	 * @return
	 * @throws UPPBusinessException
	 */
	public String getUPPAccountNo(String userId) throws UPPException{
		try{
			if (StringUtils.isBlank(userId))
				throw new UPPException("用户ID不能为空");		
			AccountExampleExtended exampleExtended = new AccountExampleExtended();
			exampleExtended.createCriteria().andOwnerUserIdEqualTo(userId);
			List<Account> list = super.getModels(exampleExtended);
			if(list!=null && list.size()==1){
				return list.get(0).getInsideAccountNo();
			}	
			
		}catch(Exception e){
			logger.error("根据用户ID查询帐号异常", e);
			new UPPException("根据用户ID查询帐号异常",e);
		}
		
		return null;
	}


	/**
	 * 根据内部交易流水号获取该笔交易对应的内部账号信息
	 * @param id
	 * @return
	 */
	protected Account getAccountNoByTradeInternalNo(String tradeInternalNo)throws UPPException{
		try{
			AccountExampleExtended accEE = new AccountExampleExtended();
			OrderInfoExampleExtended orderEE = new OrderInfoExampleExtended();
			orderEE.setSelectedField(OrderInfo.fieldCollectMoneyAccountNo());
			orderEE.createCriteria().andTradeExternalNoEqualTo(tradeInternalNo);
			accEE.createCriteria().andInsideAccountNoIn(Arrays.asList(orderEE));			
			List<Account> accList = null;//internalAccountManager.queryAccount(accEE);
			if(accList !=null && accList.size()==1)
				return accList.get(0);	

		}catch(Exception e){
			logger.error("根据内部交易流水号获取该笔交易对应的内部账号信息对象", e);
			new UPPException("根据内部交易流水号获取该笔交易对应的内部账号信息对象",e);
		}

		return null;
	}

	/**
	 * 订单转交易
	 * @param orderInfo
	 * @param paymentTrade
	 * @throws UPPException
	 */
	protected void copyOrderToTransaction(OrderInfo orderInfo, PaymentTrade paymentTrade)throws UPPException{
		try{

			if(orderInfo==null || paymentTrade==null)
				throw new UPPException("源对象和目标对象不能为null");

			String orderType = orderInfo.getOrderType();
			String accountNo = "", collectMoneyAccountNo="";
			if(PayDict.ORDER_SUBJECT_RECHARGE.equals(orderType) ){
				//充值
				accountNo = orderInfo.getAccountNo();
				collectMoneyAccountNo = orderInfo.getCollectMoneyAccountNo();
			}else if(PayDict.ORDER_SUBJECT_WITHDRAW_CASH.equals(orderType) ){
				//提现
				accountNo = orderInfo.getAccountNo();
				collectMoneyAccountNo = orderInfo.getCollectMoneyAccountNo();
			}else if(PayDict.ORDER_SUBJECT_TRANSFER_ACCOUNT.equals(orderType)){
				//其它
				//accountId = orderInfo.getCollectMoneyAccountId();
			}

			if(StringUtils.isNotBlank(accountNo)){
				paymentTrade.setAccountNo(accountNo);
			}
			if(StringUtils.isNotBlank(collectMoneyAccountNo)){
				paymentTrade.setCollectMoneyAccountNo(collectMoneyAccountNo);
			}

			if(StringUtils.isNotBlank(orderInfo.getBankCardCode())){
				paymentTrade.setBankCardCode(orderInfo.getBankCardCode());//银行代码
				paymentTrade.setBankCode(orderInfo.getBankCardCode());
			}				
			if(StringUtils.isNotBlank(orderInfo.getBankCardType())){
				paymentTrade.setBankCardType(orderInfo.getBankCardType());//银行卡类型
				paymentTrade.setCardType(orderInfo.getBankCardType());
			}				
			if(StringUtils.isNotBlank(orderInfo.getBankCardName())){
				paymentTrade.setBankCardName(orderInfo.getBankCardName());//银行名称
				paymentTrade.setBankName(orderInfo.getBankCardName());
			}


			if(orderInfo.getOrderAmount()>0)
				paymentTrade.setOrderAmount(orderInfo.getOrderAmount());//交易金额
			if(StringUtils.isNotBlank(orderInfo.getId()))
				paymentTrade.setOrderId(orderInfo.getId());//订单
			if(StringUtils.isNotBlank(orderInfo.getClentType()))
				paymentTrade.setClentType(orderInfo.getClentType());//终端	
			if(StringUtils.isNotBlank(orderInfo.getPayChannel()))
				paymentTrade.setPayChannel(orderInfo.getPayChannel());//支付通道	
			if(StringUtils.isNotBlank(orderInfo.getOrderType()))
				paymentTrade.setTradeType(orderInfo.getOrderType());//交易类型					
			if(StringUtils.isNotBlank(orderInfo.getTradeExternalNo()))
				paymentTrade.setTradeExternalNo(orderInfo.getTradeExternalNo());//交易流水号，与订单流水一致

			paymentTrade.setCreateSubareaTime(new Date());//交易时间
			paymentTrade.setCreateTime(new Date().getTime());//交易时间
			paymentTrade.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_PAY);//易宝支付			
			paymentTrade.setTradeStatus(PayDict.PAY_TRADE_STATUS_INIT);//支付交易状态			
			paymentTrade.setPayCheckStatus(PayDict.PAY_TRADE_STATUS_BALANCE_ACCOUNT_N);//交易对账状态			

			//paymentTrade.setPayPoundScale();//支付手续费		
			//paymentTrade.setId(id);//
			//paymentTrade.setPayId(payId);//交易批次	
			//paymentTrade.setBookAccountStatus(bookAccountStatus);//交易是否记账成功
			//paymentTrade.setPayConfirmDate(payConfirmDate);//第三方支付回传确认时间
			//paymentTrade.setExternalNo(externalNo);//第三方支付给定的交易流水号
			//paymentTrade.setPaymentType(paymentType);//直链网银或第三方支付
			//paymentTrade.setPayOrderId(orderInfo.getId());//支付平台订单号
			//paymentTrade.setPayPoundScale(payPoundScale);//支付手续费
			//paymentTrade.setPaySettleStatus(paySettleStatus);//是否已经结算
			//paymentTrade.setVersion(version);//锁标示

		}catch(Exception e){
			logger.error("订单对象转交易对象时异常", e);
			throw new UPPException("订单对象转交易对象时异常", e);
		}

	}

	/**
	 * 交易转网关请求
	 * @param orderInfo
	 * @param paymentTrade
	 * @throws UPPException
	 */
	protected void copyTransactionToRequestBean(PaymentTrade paymentTrade,  RequestBean requestBean)throws UPPException{

		try{

			if(requestBean==null || paymentTrade==null)
				throw new UPPException("源对象和目标对象不能为null");

			if(requestBean instanceof BankCardRechargeBeanRequestYB){

				BankCardRechargeBeanRequestYB requestBeanYB = (BankCardRechargeBeanRequestYB) requestBean;

				requestBeanYB.setAccountId(paymentTrade.getCollectMoneyAccountNo());//账户ID			
				requestBeanYB.setCardType(paymentTrade.getBankCardType());//银行卡类型，信用卡-CREDIT,借记卡-DEBIT			
				requestBeanYB.setFrpChannel(paymentTrade.getBankCardCode());//银行编码			
				requestBeanYB.setRequestId(paymentTrade.getTradeExternalNo());//流水号
				if(paymentTrade.getOrderAmount()>0){
					requestBeanYB.setAmount(AmountUtil.getAmount(paymentTrade.getOrderAmount()));//金额，保留小数点后2位
				}	
				//				if(paymentTrade.getPayPoundScale()>0){
				//					String tem = paymentTrade.getPayPoundScale().toString();				
				//					requestBean.setPlatFee(tem.substring(0, tem.length()-2)+"."+tem.substring(tem.length()-2));//平台手续费，保留小数点后2位
				//				}
				requestBeanYB.setPlatFee(DefaultConfig.getValue("PAY_POUND_SCALE"));//平台手续费，保留小数点后2位
				requestBeanYB.setClientType(paymentTrade.getClentType());//终端类型，固定值(PC, MOBILE)
				requestBeanYB.setChannel(PayDict.CLIENT_TYPE_MOBILE.equals(paymentTrade.getPayChannel())?PayDict.CHANNEL_FASTPAY:PayDict.CHANNEL_NET);//支付渠道，填英文固定值(在线-NET，快捷-FASTPAY，手机-WAP)		
				requestBeanYB.setPlatTrxDate(new Date().getTime()+"");//平台商交易日期，毫秒值

			}else if(requestBean instanceof WithDrawQuickBeanRequestYB){
				WithDrawQuickBeanRequestYB requestBeanYB = (WithDrawQuickBeanRequestYB) requestBean;
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String data = df.format(new Date());
				requestBeanYB.setBatchNo("0"+data);
				if(paymentTrade.getOrderAmount()>0){
					requestBeanYB.setAmount(AmountUtil.getAmount(paymentTrade.getOrderAmount()));//金额，保留小数点后2位
				}	

				if(StringUtils.isNotBlank(paymentTrade.getAccountNo())){
					BankCardInfo bankCard = this.getBankCardByAccountNo(paymentTrade.getAccountNo());
					if(bankCard!=null){
						requestBeanYB.setBankCode(bankCard.getBankCode());
						requestBeanYB.setBranchBankName(bankCard.getBranchBankName());
						requestBeanYB.setProvince(bankCard.getBranchBankProvince());
						requestBeanYB.setCity(bankCard.getBranchBankCity());
					}else{
						throw new UPPException("--->>>找不到账号["+paymentTrade.getAccountNo()+"]对应的银行卡信息！");
					}
				}
				requestBeanYB.setOrderId(paymentTrade.getTradeExternalNo());
				requestBeanYB.setAccountNumber(paymentTrade.getAccountNo());
				Account queryAccountInfo = null;//internalAccountManager.getAccountByAccountNo(paymentTrade.getAccountNo());
				requestBeanYB.setAccountName(queryAccountInfo.getOwnerLoginName());
			}			

			//			paymentTrade.getBankCode();
			//			paymentTrade.getBankName();
			//			paymentTrade.getBookAccountStatus();
			//			paymentTrade.getCardType();
			//			paymentTrade.getCreateSubareaTime();
			//			paymentTrade.getCreateTime();
			//			paymentTrade.getExternalNo();
			//			paymentTrade.getId();
			//			paymentTrade.getOrderAmount();
			//			paymentTrade.getOrderId();
			//			paymentTrade.getPayChannel();
			//			paymentTrade.getPayConfirmDate();
			//			paymentTrade.getPayCheckStatus();
			//			paymentTrade.getPayId();
			//			paymentTrade.getPaymentType();
			//			paymentTrade.getPayOrderId();
			//			paymentTrade.getPayPoundScale();
			//			paymentTrade.getPaySettleStatus();
			//			paymentTrade.getServiceProviderCode();
			//			paymentTrade.getTradeExternalNo();
			//			paymentTrade.getTradeStatus();
			//			paymentTrade.getTradeType();
			//			paymentTrade.getVersion();

		}catch(Exception e){
			logger.error("交易对象转网关对象时异常", e);
			throw new UPPException("交易对象转网关对象时异常", e);
		}
	}

	private void setValue(Object obj, String key,  Class[] ptypes, Object[] params)throws Exception{
		String method = "set";
		key = key.toLowerCase();
		String args [] = key.split("_");
		for(String s : args){
			method += s.substring(0,1).toUpperCase()+s.substring(1);
		}
		Converter.publicCall(obj, method, ptypes, params);
	}

	private BankCardInfo getBankCardByAccountNo(String accountNo)throws UPPException{

		BankCardInfo bankCardInfo = null;

		try {

			String sql = DefaultConfig.getValue("BANK_CARD_INFO_BY_INSIDE_ACCOUNT_NO");
			sql = sql.replace("?", "'"+accountNo+"'");
			logger.info("----->>>SQL:"+sql);
			List<Map<?, ?>> list = super.queryBySQL(sql);
			if(list!=null && list.size()>0){
				bankCardInfo = new BankCardInfo();
				Map map = list.get(0);
				Set<String> set = map.keySet();
				for(String key : set){	
					this.setValue(bankCardInfo, key, new Class[]{"CREATE_TIME".equalsIgnoreCase(key)?Long.class:String.class}, new Object[]{"CREATE_TIME".equalsIgnoreCase(key)?((BigDecimal) map.get(key)).longValue():map.get(key)});
				}	
			}

		} catch (Exception e) {
			logger.error("set value error",e);
			throw new UPPException("set value error");
		}	
		return bankCardInfo;	
	}
	/**
	 * 业务订单orderInfo 转换为 uppOrderDto 对象
	 * @param orderInfo
	 * @param uppOrderDto
	 * @throws UPPException
	 */
//	protected void copyOrderInfoToUPPOrderDto(OrderInfo orderInfo,UPPOrderDto uppOrderDto) throws UPPException{
//		try {
//			if(orderInfo==null || uppOrderDto==null){
//				throw new UPPException("源对象和目标对象不能为null");
//			}
//			//忽略的字段 "orderAmount","createTime" ,identityType, 为Long  
//			//uppOrderDto 不存在的字段  version,createSubareaTime,
//			String[] ignoreProperties = {"orderAmount","createTime","identityType","createSubareaTime","version"};
//			BeanUtils.copyProperties(orderInfo, uppOrderDto, ignoreProperties);
//			//订单金额,保留2位小数  orderAmount
//			if(orderInfo.getOrderAmount()>0){
//				String amount = AmountUtil.getAmount(orderInfo.getOrderAmount());
//				uppOrderDto.setOrderAmount(amount);
//			}
//			//支付订单的生成时间  String createTime
//			Long temTime = orderInfo.getCreateTime()==null?new Date().getTime():orderInfo.getCreateTime();
//			if(temTime>0){
//				String createTime = Long.toString(temTime);
//				uppOrderDto.setCreateTime(createTime);
//			}
//			/***
//			 * 用户标识类型
//				0 	IMEI 	国际移动设备身份码的缩写，国际移动装备辨识码，是由15位数字组成的"电子串号"，它与每台手机一一对应
//				1 	MAC地址 	MAC(Media Access Control)地址，或称为 MAC位址、硬件位址，用来定义网络设备的位置。在OSI模型中，第三层网络层负责 IP地址，第二层数据链路层则负责 MAC位址。因此一个主机会有一个IP地址，而每个网络位置会有一个专属于它的MAC位址。
//				2 	用户ID 	用户编号
//				3 	用户Email 	
//				4 	用户手机号 	
//				5 	用户身份证号 	
//				6 	用户纸质订单协议号
//			 */
//			if(orderInfo.getIdentityType()>=0){
//				String identityType = Long.toString(orderInfo.getIdentityType());
//				uppOrderDto.setIdentityType(identityType);
//			}
//
//		} catch (Exception e) {
//			logger.error("订单对象转充值对象时异常", e);
//			throw new UPPException("订单对象转充值对象时异常", e);
//		}
//
//	}
}
