package com.sinoiov.upp.manager.payment;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.payment.dao.beans.AccountFrozenDetail;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.OrderInfoExampleExtended;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.util.payment.CheckOrderByTypeHelper;
import com.sinoiov.upp.util.payment.CheckOrderMethodCategories;

@Service("paymentCheckManager")
public class PaymentCheckManagerImpl extends AbstractPaymentManager implements IPaymentCheckManager,InitializingBean{
	private static Log logger = LogFactory.getLog(PaymentCheckManagerImpl.class);
	@Autowired
	@Qualifier("frozenDetailManager")
	private IFrozenDetailManager frozenDetailManager;
	
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;
	
	@Autowired
	@Qualifier("orderManager")
	private IOrderManager orderManager;

	private HashMap<String,String> methodNameMap;
	
	private final ConcurrentHashMap<String,Method> methodMap= new ConcurrentHashMap<String,Method>();
	
	private final Class[] classArray= new Class[]{OrderInfo.class};
	
	private final CheckOrderByTypeHelper checkOrderByTypeHelper = new CheckOrderByTypeHelper();
	@Override
	public boolean accountFrozenDetailCheck(String storeCode,String workOrderNo,Long money) throws UPPException{
		AccountFrozenDetail accountFrozenDetail = frozenDetailManager.queryAccountFrozenDetailMess(storeCode, workOrderNo);
		if (accountFrozenDetail == null) {
			logger.error(workOrderNo + ",未查到业务订单对应的冻结记录");
			UPPException uppe = new UPPException("未查到业务订单对应的冻结记录");
			uppe.setErrorCode(ReturnCodeDict.FREEZE_RECORD_NOT_EXIST);
			throw uppe;
		}
		if ((accountFrozenDetail.getFrozenAmount() - accountFrozenDetail.getUnfrozenAmount()) < money) {
			logger.error(workOrderNo + ",该业务订单冻结的金额不足");
			UPPException uppe = new UPPException("该业务订单冻结的金额不足");
			uppe.setErrorCode(ReturnCodeDict.FREEZE_RECORD_NOT_EXIST);
			throw uppe;
		} 
		return true;
	}
	@Override
	public OrderInfo orderIsExits(String workOrderNo,String storeCode)throws UPPException{
		OrderInfoExampleExtended exampleExtended = new OrderInfoExampleExtended();
		exampleExtended.createCriteria().andWorkOrderNoEqualTo(workOrderNo).andStoreCodeEqualTo(storeCode);
		List<OrderInfo> orderInfoList = orderManager.queryOrderInfo(exampleExtended);
		if(orderInfoList==null||orderInfoList.size()==0){
			return null;
		}else{
			OrderInfo orderInfo = orderInfoList.get(0);
			if(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(orderInfo.getOrderStatus())){
				UPPException e =new UPPException("订单已经支付完成，请勿重复支付");
				e.setErrorCode(ReturnCodeDict.ORDER_IS_PAYED);
				throw e;
			}else{
				return orderInfo;
			}
		}
	}
	
	@Override
	public boolean addOrUpdateAccountFrozenDetail(OrderInfo orderInfo) throws UPPException{
		AccountFrozenDetail accountFrozenDetail = frozenDetailManager.queryAccountFrozenDetailMess(orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
		if (accountFrozenDetail == null) {
			accountFrozenDetail = new AccountFrozenDetail();

			accountFrozenDetail.setStoreCode(orderInfo.getStoreCode());
			accountFrozenDetail.setWorkOrderNo(orderInfo.getWorkOrderNo());
			accountFrozenDetail.setFrozenAmount(orderInfo.getOrderAmount());
			accountFrozenDetail.setUnfrozenAmount(0l);
			accountFrozenDetail.setParentAccountNo(orderInfo.getAccountNo());
			accountFrozenDetail.setServiceProviderCode(orderInfo.getServiceProviderCode());
			accountFrozenDetail.setVersion(0);
			frozenDetailManager.internalAddFrozenDetail(accountFrozenDetail);
		}else{
			frozenDetailManager.increaseFrozenAmount(orderInfo.getOrderAmount(), orderInfo.getStoreCode(), orderInfo.getWorkOrderNo());
		}
		
		
		return true;
	}
	@Override
	public String checkAccountNo(OrderInfo orderInfo)throws UPPException{
		String accountNo = orderInfo.getAccountNo();
		if(accountNo!=null){
			Account account =accountManager.getAccountByAccountNo(accountNo);
			if(account==null){
				
				UPPException uppe = new UPPException("该业务订单冻结的金额不足");
				uppe.setErrorCode(ReturnCodeDict.ACCOUNT_NOT_EXIST);
				throw uppe;
			}
		}
		String collectMoneyAccountNo = orderInfo.getCollectMoneyAccountNo();
		if(collectMoneyAccountNo!=null){
			Account account =accountManager.getAccountByAccountNo(accountNo);
			if(account==null){
				UPPException uppe = new UPPException("该业务订单冻结的金额不足");
				uppe.setErrorCode(ReturnCodeDict.ACCOUNT_NOT_EXIST);
				throw uppe;
			}
		}
		accountNo=accountNo==null?"":accountNo;
		collectMoneyAccountNo=collectMoneyAccountNo==null?"":collectMoneyAccountNo;
		if("".equals(accountNo)&&"".equals(collectMoneyAccountNo)){
			UPPException uppe = new UPPException("该业务订单冻结的金额不足");
			uppe.setErrorCode(ReturnCodeDict.ACCOUNTNO_IS_NULL);
			throw uppe;
		}
		
		return ReturnCodeDict.THROUGH_VERIFICATION;
	}
	
	/**
	 * 检查订单数据
	 * @param orderInfo
	 * @param orderType
	 * @return
	 * @throws UPPException
	 */
	@Override
	public String checkOrder(OrderInfo orderInfo, String orderSubject)
			throws UPPException{
		try {
			String returnCode=(String) this.methodMap.get(orderSubject).invoke(checkOrderByTypeHelper,orderInfo );
			if(ReturnCodeDict.SUCCESS.equals(returnCode)){
				return returnCode;
			}else{
				UPPException e = new UPPException("数据验证未通过");
				e.setErrorCode(returnCode);
				throw e;
			}			
		} catch (UPPException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		for(Method method:checkOrderByTypeHelper.getClass().getMethods()){		
			String orderType = CheckOrderMethodCategories.methodMap.get(method.getName());
			if(orderType!=null){
				methodMap.put(orderType, method);
			}
		}
	}

	@Override
	public boolean orderIsHandled(OrderInfo orderInfo) throws UPPException{
		
		
		
		
		if(orderInfo==null){
			UPPException ue =new  UPPException("根据id找不到对应的支付订单");
			ue.setErrorCode(ReturnCodeDict.ORDER_N);
			throw ue;
		}
		if(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(orderInfo.getOrderStatus())){
			return  false;
//			UPPException ue =new  UPPException("订单已经处理完成了");
//			ue.setErrorCode(ReturnCodeDict.ORDER_IS_HANDLED);
//			throw ue;
		}
		return true;
	}
	@Override
	public Integer tradeIsHandled(String tradeId) throws UPPException{
		try {
			PaymentTradeExampleExtended exampleExtended = new PaymentTradeExampleExtended();
			exampleExtended.createCriteria().andIdEqualTo(tradeId);
			List<PaymentTrade> paymentTradeList=super.getModels(exampleExtended);
			
			
			if(paymentTradeList==null||paymentTradeList.size()==0){
				UPPException ue =new  UPPException("根据id找不到对应的支付交易");
				ue.setErrorCode(ReturnCodeDict.ORDER_N);
				throw ue;
			}
			if(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS.equals(paymentTradeList.get(0).getTradeStatus())){
				UPPException ue =new  UPPException("交易已经处理完成了");
				ue.setErrorCode(ReturnCodeDict.ORDER_IS_HANDLED);
				throw ue;
			}
			return paymentTradeList.get(0).getVersion();
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}
}
