package com.sinoiov.upp.util.payment;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.dict.ReturnCodeDict;

/**
 * 订单数据验证
 * @author majingyuan
 *
 */
public class CheckOrderByTypeHelper {
	public static String netPayCheckOrder(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}
		if(orderInfo.getCollectMoneyAccountNo()==null || "".equals(orderInfo.getCollectMoneyAccountNo())){
			return ReturnCodeDict.ACCOUNTNO_IS_NULL;
		}
		if(orderInfo.getServiceProviderCode()==null ||"".equals(orderInfo.getServiceProviderCode())){
			return ReturnCodeDict.ORDER_SERVICE_PROVIDER_CODE_IS_NULL;
		}
		return ReturnCodeDict.SUCCESS;
	}
	public static String netRechargeCheckOrder(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}	
		if(orderInfo.getCollectMoneyAccountNo()==null || "".equals(orderInfo.getCollectMoneyAccountNo())){
			return ReturnCodeDict.ACCOUNTNO_IS_NULL;
		}
		if(orderInfo.getServiceProviderCode()==null ||"".equals(orderInfo.getServiceProviderCode())){
			return ReturnCodeDict.ORDER_SERVICE_PROVIDER_CODE_IS_NULL;
		}
		return ReturnCodeDict.SUCCESS;
	}	
	public static String fastpayRecharge(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}	
		if(orderInfo.getServiceProviderCode()==null ||"".equals(orderInfo.getServiceProviderCode())){
			return ReturnCodeDict.ORDER_SERVICE_PROVIDER_CODE_IS_NULL;
		}
		return ReturnCodeDict.SUCCESS;
	}
	public static String transferAccounts(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}	
		return ReturnCodeDict.SUCCESS;
	}
	public static String freezeAccountMoney(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}	
		return ReturnCodeDict.SUCCESS;
	}
	public static String unFreezeAccountMoney(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}	
		return ReturnCodeDict.SUCCESS;
	}
	public static String accountPay(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}	
		return ReturnCodeDict.SUCCESS;
	}
	public static String fastPay(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}	
		if(orderInfo.getServiceProviderCode()==null ||"".equals(orderInfo.getServiceProviderCode())){
			return ReturnCodeDict.ORDER_SERVICE_PROVIDER_CODE_IS_NULL;
		}
		return ReturnCodeDict.SUCCESS;
	}
	
	public static String accountRecharge(OrderInfo orderInfo){
		String commonReturnCode = commonCheckOrder(orderInfo);
		if(!ReturnCodeDict.THROUGH_VERIFICATION.equals(commonReturnCode)){
			return commonReturnCode;
		}
		return ReturnCodeDict.SUCCESS;
	}
	private static String commonCheckOrder(OrderInfo orderInfo){
		if(orderInfo.getOrderAmount()==null || orderInfo.getOrderAmount()==0){
			return ReturnCodeDict.ORDER_AMOUNT_NO_CORRECT;
		}
		if(orderInfo.getWorkOrderNo()==null||"".equals(orderInfo.getWorkOrderNo())){
			return ReturnCodeDict.ORDER_WORK_NO_N;
		}
		return ReturnCodeDict.THROUGH_VERIFICATION;
	}
}
