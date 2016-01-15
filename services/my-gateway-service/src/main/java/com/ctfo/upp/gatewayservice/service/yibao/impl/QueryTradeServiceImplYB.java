package com.ctfo.upp.gatewayservice.service.yibao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountHistoryRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountHistoryResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeResultQueryResponseYB;
import com.ctfo.upp.dict.EpayZHTReturnCode;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.QueryAccountHistoryRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.QueryAccountHistoryResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.QueryAccountInfoRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.QueryAccountInfoResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.RechargeResultQueryRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.RechargeResultQueryResponse;
import com.ctfo.upp.gatewayservice.service.yibao.intf.DistributeServiceYB;
import com.ctfo.upp.gatewayservice.service.yibao.intf.QueryTradeServiceYB;
import com.ctfo.upp.gatewayservice.util.base.RandomUtil;
import com.ctfo.upp.gatewayservice.util.yibao.PareseObjectParameter;

@Service("queryTradeServiceYB")
public class QueryTradeServiceImplYB extends BaseServiceImpl implements QueryTradeServiceYB {
	@Autowired(required=false)
	private DistributeServiceYB distributeServiceYB;
	
	@Override
	public QueryAccountInfoResponseYB queryAccount(
			QueryAccountInfoRequestYB queryAccountInfoRequestYB)
			throws Exception {
		
		boolean flag = true;
		Date date = new Date();//current time
		String recordId = RandomUtil.getRandomNumberWithDate(3);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		QueryAccountInfoRequest request = new QueryAccountInfoRequest();
		request.setReq02_userIdentityNumber(queryAccountInfoRequestYB.getAccountNo());	
		request.setReq15_merchantBonusAccount(queryAccountInfoRequestYB.getIsMerchantBonusAccount());
		request.setReq16_merchantMonitorAccount(queryAccountInfoRequestYB.getIsMerchantMonitorAccount());
		request.setReq24_merchantClearAccount(queryAccountInfoRequestYB.getIsMerchantClearAccount());
		request.setReq07_desc(queryAccountInfoRequestYB.getDesc());

		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		QueryAccountInfoResponse response = (QueryAccountInfoResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			
			if(EpayZHTReturnCode.SUCCESS.equals(response.getResp01_code())){
				log.error(">>>>>>>>>> 返回异常,返回码：" + response.getResp01_code() + " <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		QueryAccountInfoResponseYB queryAccountInfoResponseYB = new QueryAccountInfoResponseYB();
		queryAccountInfoResponseYB.setAccountNo(response.getResp03_userIdentityNumber());
		queryAccountInfoResponseYB.setTotalBalance(response.getResp12_totalBalance());
		queryAccountInfoResponseYB.setCashableBalance(response.getResp13_cashableBalance());
		queryAccountInfoResponseYB.setNoncashableBalance(response.getResp14_noncashableBalance());
		queryAccountInfoResponseYB.setAccountStatus(response.getResp26_accountStatus());
		queryAccountInfoResponseYB.setAccountFreezeReason(response.getResp27_accountFreezeReason());

		return queryAccountInfoResponseYB;
	}

	@Override
	public RechargeResultQueryResponseYB rechargeResultQuery(
			RechargeResultQueryRequestYB rechargeResultQueryRequestYB)
			throws Exception {
		boolean flag = true;
		Date date = new Date();//current time
		String recordId = RandomUtil.getRandomNumberWithDate(3);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		RechargeResultQueryRequest request =new RechargeResultQueryRequest();
//		request.setReq01_merchantIdentityNumber(rechargeResultQueryRequestYB.getReq01_merchantIdentityNumber());
		request.setReq03_requestId(rechargeResultQueryRequestYB.getReq03_requestId());
		
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		
		RechargeResultQueryResponse response = (RechargeResultQueryResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		
		RechargeResultQueryResponseYB responseYB= new RechargeResultQueryResponseYB();
		
		String[] ignoreProperties = {"hmac"};
		BeanUtils.copyProperties(response, responseYB, ignoreProperties);
		
		return responseYB;
	}
	@Override
	public QueryAccountHistoryResponseYB queryAccountHistory(QueryAccountHistoryRequestYB queryAccountHistoryRequestYB)
			throws Exception {
		boolean flag = true;
		
		QueryAccountHistoryRequest request = new QueryAccountHistoryRequest();
		request.setReq02_userIdentityNumber(queryAccountHistoryRequestYB.getReq02_userIdentityNumber());
		request.setReq51_startPoint(queryAccountHistoryRequestYB.getReq51_startPoint());
		request.setReq52_endPoint(queryAccountHistoryRequestYB.getReq52_endPoint());
		request.setHmac(createHMAC(request, 1));
		
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		
//		QueryAccountHistoryResponse response = (QueryAccountHistoryResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		
		
		QueryAccountHistoryResponseYB responseYB=new QueryAccountHistoryResponseYB();
		responseYB.setRecordList(result);
//		String[] ignoreProperties = {"hmac"};
//		BeanUtils.copyProperties(response, responseYB, ignoreProperties);
		
		return responseYB;
	}

}
