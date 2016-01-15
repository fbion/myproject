package com.ctfo.upp.service.data;


public interface IDataProcessService {
	
	public void handleMemberNoForAccount();
	
	public void handleMemberNoForOrderInfo();
	
	public void handleMemberNoForPaymentTradeOffline();
	
	public void handleOrderInfoBatchRechargeToUserAccount();
	
	public String countBatchRechargeToUserAccount();
	
}
