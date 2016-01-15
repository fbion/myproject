package com.sinoiov.upp.manager.payment;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.base.dao.beans.BankCardInfo;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.manager.AbstractManager;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.util.DefaultConfig;
import com.sinoiov.upp.yeepayfastgateway.intf.IYeepayFastRechargeFacade;
import com.sinoiov.upp.yeepaywebgateway.intf.IYeepayWebRechargeFacade;
public abstract class AbstractPaymentManager extends AbstractManager{

	private static final Log logger = LogFactory.getLog(AbstractPaymentManager.class);

	@Autowired
	@Qualifier("accountManager")  
	protected IAccountManager accountManager;
//	@Autowired
//	@Qualifier("frozenDetailManagerService")  
//	protected IFrozenDetailManager frozenDetailManager;	
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
	
	//快捷网关对象
	private IYeepayFastRechargeFacade iYeepayFastRechargeFacade;
	
	//网银网关对象
	private IYeepayWebRechargeFacade iYeepayWebRechargeFacade;

	protected IYeepayFastRechargeFacade getYeepayFastRechargeFacade(){

		if(iYeepayFastRechargeFacade==null){
			iYeepayFastRechargeFacade = (IYeepayFastRechargeFacade) ServiceFactory.getFactory().getService(IYeepayFastRechargeFacade.class);		
			logger.info("------>>>>>初始化快捷网关对象:"+iYeepayFastRechargeFacade);			
		}

		return iYeepayFastRechargeFacade;

	}
	
	protected IYeepayWebRechargeFacade getYeepayWebRechargeFacade(){

		if(iYeepayWebRechargeFacade==null){
			iYeepayWebRechargeFacade = (IYeepayWebRechargeFacade) ServiceFactory.getFactory().getService(IYeepayWebRechargeFacade.class);		
			logger.info("------>>>>>初始化网银网关对象:"+iYeepayWebRechargeFacade);			
		}

		return iYeepayWebRechargeFacade;

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
			List<Account> accList = accountManager.queryAccount(accEE);
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
			}else if(PayDict.ORDER_SUBJECT_WITHDRAW_CASH.equals(orderType)){
				//提现
				accountNo = orderInfo.getAccountNo();
				collectMoneyAccountNo = orderInfo.getCollectMoneyAccountNo();
			}else if(PayDict.ORDER_SUBJECT_FREEZE_MONEY.equals(orderType)){
				//提现
				accountNo = orderInfo.getAccountNo();
				collectMoneyAccountNo = orderInfo.getCollectMoneyAccountNo();
			}else if(PayDict.ORDER_SUBJECT_TRANSFER_ACCOUNT.equals(orderType)){
				//其它
				accountNo = orderInfo.getAccountNo();
				collectMoneyAccountNo = orderInfo.getCollectMoneyAccountNo();
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
			if(StringUtils.isNotBlank(orderInfo.getBankCode()))
				paymentTrade.setBankCode(orderInfo.getBankCode());//交易流水号，与订单流水一致
			if(StringUtils.isNotBlank(orderInfo.getOrderSubject()))
				paymentTrade.setTradeSubject(orderInfo.getOrderSubject());//科目
			
			//支付确认时间
			paymentTrade.setPayConfirmDate(orderInfo.getPayConfirmDate()!=null?orderInfo.getPayConfirmDate():System.currentTimeMillis());
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
	
}
