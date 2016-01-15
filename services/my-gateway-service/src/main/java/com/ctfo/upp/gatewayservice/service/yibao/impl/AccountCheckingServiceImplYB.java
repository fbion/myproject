package com.ctfo.upp.gatewayservice.service.yibao.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.yibao.business.accountaccess.CheckAccountBeanDetailRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CheckAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CheckAccountBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.FireClientNoticeBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.FireClientNoticeBeanResponseYB;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.AccountDataSyncDetail;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.FireClientNoticeRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.SyncAccountRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.SyncAccountResponse;
import com.ctfo.upp.gatewayservice.service.yibao.intf.AccountCheckingServiceYB;
import com.ctfo.upp.gatewayservice.service.yibao.intf.DistributeServiceYB;
import com.ctfo.upp.gatewayservice.util.yibao.PareseObjectParameter;

@Service("accountCheckingServiceYB")
public class AccountCheckingServiceImplYB extends BaseServiceImpl implements AccountCheckingServiceYB {
	
	protected static final Log log = LogFactory.getLog(AccountCheckingServiceImplYB.class);
	
	@Autowired(required=false)
	private DistributeServiceYB distributeServiceYB;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public CheckAccountBeanResponseYB checkAccount(
			CheckAccountBeanRequestYB checkAccountBeanRequest) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		SyncAccountRequest request = new SyncAccountRequest();
		request.setReq03_requestId(checkAccountBeanRequest.getRequestId());
		
		try {
			formatter.parse(checkAccountBeanRequest.getDate());
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(">>>>>>>>>> 日期格式不正确  <<<<<<<<<<");
		}
		
		request.setReq37_syncDate(checkAccountBeanRequest.getDate());
		request.setReq38_syncCount(String.valueOf(checkAccountBeanRequest.getCheckAccountDetailList().size()));
		request.setReq41_isLastBatch(checkAccountBeanRequest.getIsLastBatch());
		
		String syncData = "";
		for(int i = 0 ; i < checkAccountBeanRequest.getCheckAccountDetailList().size() ; i++){
			
			CheckAccountBeanDetailRequestYB checkAccountBeanDetailRequest = (CheckAccountBeanDetailRequestYB) checkAccountBeanRequest.getCheckAccountDetailList().get(i);
			AccountDataSyncDetail detail = new AccountDataSyncDetail();
			detail.setRecordId(checkAccountBeanDetailRequest.getRequestId());
			detail.setUserNumber(checkAccountBeanDetailRequest.getAccountId());
			detail.setBalance(checkAccountBeanDetailRequest.getBalance());
			detail.setTrxout(checkAccountBeanDetailRequest.getTrxout());
			detail.setTrxin(checkAccountBeanDetailRequest.getTrxin());
			detail.setRecharge(checkAccountBeanDetailRequest.getRecharge());
			detail.setCash(checkAccountBeanDetailRequest.getCash());
			detail.setRechargeRefund(checkAccountBeanDetailRequest.getRechargeRefund());
			detail.setAdjust(checkAccountBeanDetailRequest.getAdjuset());
			detail.setRemarks("0");
			syncData += PareseObjectParameter.getObjectParameterContentStringByChar(detail, "&");
			if(i != (checkAccountBeanRequest.getCheckAccountDetailList().size() - 1)){
				syncData += "|";
			}
			if(i == 0){
				request.setReq39_startRecordId(checkAccountBeanDetailRequest.getRequestId());
			}
			if(i == (checkAccountBeanRequest.getCheckAccountDetailList().size() - 1)){
				request.setReq40_endRecordId(checkAccountBeanDetailRequest.getRequestId());
			}
		}
		
		request.setReq42_dataSyncDetail(syncData);
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		SyncAccountResponse response = (SyncAccountResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!StringUtils.isBlank(response.getResp39_errorRecordId())){
				log.error(">>>>>>>>>> 存在对账不匹配的记录：" + response.getResp39_errorRecordId() + " <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		CheckAccountBeanResponseYB checkAccountBeanResponseYB = new CheckAccountBeanResponseYB();
		checkAccountBeanResponseYB.setFlag(flag);
		return checkAccountBeanResponseYB;
	}

	@Override
	public FireClientNoticeBeanResponseYB fireClientNotice(
			FireClientNoticeBeanRequestYB fireClientNoticeBeanRequest)
			throws Exception {
		// TODO Auto-generated method stub
		FireClientNoticeRequest request = new FireClientNoticeRequest();
		request.setReq03_requestId(fireClientNoticeBeanRequest.getRequestId());
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		FireClientNoticeBeanResponseYB fireClientNoticeBeanResponseYB = new FireClientNoticeBeanResponseYB();
		if(result == null || result.size() == 0){
			fireClientNoticeBeanResponseYB.setFlag(true);
		}else{
			fireClientNoticeBeanResponseYB.setFlag(false);
		}
		return fireClientNoticeBeanResponseYB;
	}
}
