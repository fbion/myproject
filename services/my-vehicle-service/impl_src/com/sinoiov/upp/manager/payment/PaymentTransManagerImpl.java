package com.sinoiov.upp.manager.payment;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.AccountChange;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.upp.dict.OrderAndTradeAttributeKey;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.root.util.TradeNumberHelper;
import com.sinoiov.upp.manager.account.IAccountManager;

@Service("paymentTransManager")
public class PaymentTransManagerImpl extends AbstractPaymentManager implements IPaymentTransManager{
	private static Log logger = LogFactory.getLog(PaymentTransManagerImpl.class);
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;
	
	/**
	 * 获取确认日期
	 * @param orderInfo
	 * @return
	 */
	private   String haveConfirmDate( OrderInfo orderInfo){
		if(orderInfo.getPayConfirmDate()!=null&&orderInfo.getPayConfirmDate()!=0){
			return new SimpleDateFormat("yyyy-MM-dd").format(orderInfo.getPayConfirmDate());
		}else{
			return null;
		}
			
	}
	/**
	 * 分转换成元
	 */
	private Double chargeFenToYuan(Long amount){
		return Double.valueOf(amount)/100.0;
	}

	@Override
	public Map<String, Object> freezeAccountHandle(
			OrderInfo orderInfo) throws UPPException {
		Map<String,Object> result = new HashMap<String,Object>();
		Long payConfirmDate = System.currentTimeMillis();
		orderInfo.setPayConfirmDate(payConfirmDate);
		
		//TODO 账户的解冻操作
		accountManager.frozenAccount(orderInfo.getTradeExternalNo(), this.haveConfirmDate(orderInfo), orderInfo.getOrderSubject(), orderInfo.getAccountNo(), orderInfo.getStoreCode(),orderInfo.getId(), orderInfo.getOrderAmount());
		result.put(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE, payConfirmDate);
		result.put(OrderAndTradeAttributeKey.TRADE_EXTERNAL_NO, orderInfo.getTradeExternalNo());
		return result;
	}
	@Override
	public Map<String,Object> unfreezeAccountHandle(OrderInfo orderInfo)throws UPPException{
		Map<String,Object> result = new HashMap<String,Object>();
		Long payConfirmDate = System.currentTimeMillis();
		orderInfo.setPayConfirmDate(payConfirmDate);
		//TODO 账户的解冻操作
		accountManager.unfrozenAccount(orderInfo.getTradeExternalNo(), this.haveConfirmDate(orderInfo), orderInfo.getOrderSubject(), orderInfo.getAccountNo(), orderInfo.getStoreCode(),orderInfo.getId(), orderInfo.getOrderAmount());
		result.put(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE, payConfirmDate);
		return result;
	}
	@Override
	public Map<String ,Object> accountDeductMoneyDirectHandle(OrderInfo orderInfo)throws UPPException{
		 return this.accountDeductMoneyHandle( orderInfo,false);
	}
	@Override
	public Map<String ,Object> accountDeductMoneyFromFreeze(OrderInfo orderInfo)throws UPPException{
		 return this.accountDeductMoneyHandle( orderInfo,true);
	}
	
	@Override
	public Map<String ,Object> accountDeductMoneyHandle(OrderInfo orderInfo,Boolean payoutFrozenBalance)throws UPPException{
		Map<String,Object> result = new HashMap<String,Object>();
		Long payConfirmDate = System.currentTimeMillis();

		AccountChange payout=new AccountChange();
		payout.setAccountDate(this.haveConfirmDate(orderInfo));
		payout.setAccountNo(orderInfo.getAccountNo());
		payout.setBankCardCode(orderInfo.getBankCardCode());
		payout.setBankCardType(orderInfo.getBankCardType());
		payout.setServiceProviderCode(orderInfo.getServiceProviderCode());
		payout.setStoreCode(orderInfo.getStoreCode());
		payout.setSubject(orderInfo.getOrderSubject());
		payout.setTradeInternalNo(orderInfo.getTradeExternalNo());
		payout.setMoney(orderInfo.getOrderAmount());
		payout.setOrderId(orderInfo.getId());
		accountManager.payout(payout, payoutFrozenBalance);
		result.put(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE, payConfirmDate);
		return result;
	}
	@Override
	public Map<String ,Object> accountRecordedMoneyHandle(OrderInfo orderInfo)throws UPPException{
		Map<String,Object> result = new HashMap<String,Object>();
		Long payConfirmDate = System.currentTimeMillis();

		AccountChange income = new AccountChange();
		income.setAccountDate(this.haveConfirmDate(orderInfo));
		income.setAccountNo(orderInfo.getCollectMoneyAccountNo());
		income.setBankCardCode(orderInfo.getBankCardCode());
		income.setBankCardType(orderInfo.getBankCardType());
		income.setServiceProviderCode(orderInfo.getServiceProviderCode());
		income.setStoreCode(orderInfo.getStoreCode());
		income.setSubject(orderInfo.getOrderSubject());
		income.setTradeInternalNo(StringUtils.isBlank(orderInfo.getTradeExternalNo())?TradeNumberHelper.getTradeExternalNo():orderInfo.getTradeExternalNo());
		income.setMoney(orderInfo.getOrderAmount());
		income.setOrderId(orderInfo.getId());
		accountManager.income(income);
		result.put(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE, payConfirmDate);
		return result;
	}
	@Override
	public Map<String, Object> transferAccounts(String storeCode, PaymentTrade paymentTrade, boolean isForzen)
			throws UPPException {
		Map<String,Object> result = new HashMap<String,Object>();
		Long payConfirmDate = paymentTrade.getPayConfirmDate();//System.currentTimeMillis();

		
		AccountChange change = new AccountChange();
		change.setAccountDate(new SimpleDateFormat("yyyy-MM-dd").format(payConfirmDate));//确认时间
		change.setAccountNo(paymentTrade.getAccountNo());
		change.setBankCardCode(paymentTrade.getBankCardCode());
		change.setBankCardType(paymentTrade.getBankCardType());
		change.setServiceProviderCode(paymentTrade.getServiceProviderCode());
		change.setStoreCode(storeCode);
		change.setSubject(paymentTrade.getTradeSubject());//科目
		change.setTradeInternalNo(paymentTrade.getTradeExternalNo());
		change.setMoney(paymentTrade.getOrderAmount());
		change.setOrderId(paymentTrade.getOrderId());
		
		accountManager.payoutAndIncome(paymentTrade.getAccountNo(),paymentTrade.getCollectMoneyAccountNo(),change, isForzen);
		result.put(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE, payConfirmDate);
		return result;
	}
	@Override
	public Map<String, Object> transferAccountsDirectHandle(PaymentTrade paymentTrade,OrderInfo orderInfo)
			throws UPPException {
		Map<String,Object> result = new HashMap<String,Object>();
		Long payConfirmDate = System.currentTimeMillis();

		AccountChange change = new AccountChange();
		change.setAccountDate(this.haveConfirmDate(orderInfo));
		change.setAccountNo(paymentTrade.getAccountNo());
		change.setBankCardCode(paymentTrade.getBankCardCode());
		change.setBankCardType(paymentTrade.getBankCardType());
		change.setServiceProviderCode(paymentTrade.getServiceProviderCode());
		change.setStoreCode(orderInfo.getStoreCode());
		change.setSubject(orderInfo.getOrderSubject());
		change.setTradeInternalNo(paymentTrade.getTradeExternalNo());
		change.setMoney(paymentTrade.getOrderAmount());

		accountManager.payoutAndIncome(paymentTrade.getAccountNo(),paymentTrade.getCollectMoneyAccountNo(),change, false);
		result.put(OrderAndTradeAttributeKey.PAY_CONFIRM_DATE, payConfirmDate);
		return result;
	}
	@Override
	public Map<String, Object> callGateWayForWithdrawCash(
			PaymentTrade paymentTrade) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> callGateWayForWithdrawCashRapid(
			PaymentTrade paymentTrade) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String callGateWayForNetRecharge(PaymentTrade paymentTrade, OrderInfo orderInfo)throws UPPException{
		try {
			String url = super.getYeepayWebRechargeFacade().recharge(paymentTrade.getPayNo(), this.chargeFenToYuan(paymentTrade.getOrderAmount()), paymentTrade.getBankCode(), orderInfo.getProductName());
			if(url != null){
				return url;
			}else{
				//发送请求超时
				throw new UPPException("向网银网关发送充值请求，网关返回null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(e);
		}
	}
	
	@Override
	public String callGateWayForFastRecharge(PaymentTrade paymentTrade, OrderInfo orderInfo)throws UPPException{
		//TODO 需要补充网关调用方式
		try {
			String url = null;
			if(orderInfo.getClentType().equals(PayDict.CLIENT_TYPE_PC)) {
				url = super.getYeepayFastRechargeFacade().rechargeByPC(orderInfo.getUserId(), paymentTrade.getPayNo(), this.chargeFenToYuan(paymentTrade.getOrderAmount()), orderInfo.getProductName(), orderInfo.getCreateTime());
			}
			if(orderInfo.getClentType().equals(PayDict.CLIENT_TYPE_MOBILE)) {
				url = super.getYeepayFastRechargeFacade().rechargeByMobile(orderInfo.getUserId(), paymentTrade.getPayNo(), this.chargeFenToYuan(paymentTrade.getOrderAmount()), orderInfo.getProductName(), orderInfo.getCreateTime());
			}
			if(url != null){
				return url;
			}else{
				//发送请求超时
				throw new UPPException("向快捷支付PC|Mobile网关发送充值请求，网关返回null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(e);
		}
	}

	@Override
	public String callGateWayForFastApiRecharge(PaymentTrade paymentTrade, OrderInfo orderInfo, String type, Map<String, String> param) throws UPPException {
		// TODO Auto-generated method stub
		try {
			String phone = null;
			if(type.equals("1")) {
				phone = super.getYeepayFastRechargeFacade().rechargeByApiRequest(orderInfo.getUserId(), param.get("bindCardId"), paymentTrade.getPayNo(), this.chargeFenToYuan(paymentTrade.getOrderAmount()), orderInfo.getProductName(), paymentTrade.getCreateTime());
			}
			if(type.equals("2")) {
				phone = super.getYeepayFastRechargeFacade().rechargeByApiCreditRequest(orderInfo.getUserId(), param.get("phone"), param.get("cardNo"), param.get("endTime"), param.get("cvv"), paymentTrade.getPayNo(), this.chargeFenToYuan(paymentTrade.getOrderAmount()), orderInfo.getProductName(), paymentTrade.getCreateTime());
			}
			if(type.equals("3")) {
				phone = super.getYeepayFastRechargeFacade().rechargeByApiSaveRequest(orderInfo.getUserId(), param.get("phone"), param.get("cardNo"), param.get("idCard"), paymentTrade.getPayNo(), this.chargeFenToYuan(paymentTrade.getOrderAmount()), orderInfo.getProductName(), paymentTrade.getCreateTime());
			}
			if(phone != null){
				return phone;
			}else{
				//发送请求超时
				throw new UPPException("向快捷支付Api网关发送充值请求，网关返回null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(e);
		}
	}

	@Override
	public String callGateWayForFastApiRechargeSure(PaymentTrade paymentTrade, OrderInfo orderInfo, String messageCode) throws UPPException {
		// TODO Auto-generated method stub
		try {
			String result = null;
			result = super.getYeepayFastRechargeFacade().rechargeByApiSure(paymentTrade.getPayNo(), messageCode);
			if(result != null){
				return result;
			}else{
				//发送请求超时
				throw new UPPException("向快捷支付Api网关发送充值请求，网关返回null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(e);
		}
	}

	@Override
	public String callGateWayForFastQueryRechargeOverOrNot(
			String payNo) throws UPPException {
		// TODO Auto-generated method stub
		try {
			String result = null;
			result = super.getYeepayFastRechargeFacade().queryRecharge(payNo);
			if(result != null){
				if(result.equals("1") || result.equals("2")) {
					return "1";
				}else {
					return "0";
				}
			}else{
				//发送请求超时
				throw new UPPException("向快捷支付网关发送查询充值请求，网关返回null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(e);
		}
	}

	@Override
	public String callGateWayForNetQueryRechargeOverOrNot(String payNo)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			String result = null;
			result = super.getYeepayWebRechargeFacade().query(payNo);
			if(result != null){
				if(result.equals("1") || result.equals("2")) {
					return "1";
				}else {
					return "0";
				}
			}else{
				//发送请求超时
				throw new UPPException("向网银网关发送查询充值请求，网关返回null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(e);
		}
	}

	
}
