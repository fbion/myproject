package com.ctfo.upp.gatewayservice.facade;


import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CheckAccountBeanDetailRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CheckAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.FireClientNoticeBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.GateWayFailNoticRecordRequest;
import com.ctfo.gateway.bean.yibao.business.accountaccess.GateWayFailNoticRecordResponse;
import com.ctfo.gateway.bean.yibao.callback.CallbackRecord;
import com.ctfo.gateway.intf.AccountCheckingServiceFacade;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gatewayservice.bean.mongoDB.log.CallBackToBusinessLog;
import com.ctfo.upp.gatewayservice.service.base.intf.LogService;
import com.ctfo.upp.gatewayservice.service.yibao.intf.AccountCheckingServiceYB;

@Service("accountCheckingServiceFacade")
public class AccountCheckingServiceFacadeImpl extends FacadeSupport implements AccountCheckingServiceFacade {
	
	@Autowired(required=false)
	private AccountCheckingServiceYB  accountCheckingServiceYB;
	@Autowired(required=false)
	private LogService logService;
	@Override
	public ResponseBean checkAccount(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof CheckAccountBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				for(CheckAccountBeanDetailRequestYB bean : ((CheckAccountBeanRequestYB)requestBean).getCheckAccountDetailList()){
					super.validateObjectNecessary(bean);
				}
				return accountCheckingServiceYB.checkAccount((CheckAccountBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 对账发生错误  <<<<<<<<<<", e);
		}
	}
	
	@Override
	public ResponseBean fireClientNotice(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof FireClientNoticeBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return accountCheckingServiceYB.fireClientNotice((FireClientNoticeBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误 <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 触发通知发生错误  <<<<<<<<<<", e);
		}
	}
	public ResponseBean queryFailNoticeRecords(RequestBean requestBean) throws UPPException{
		if(requestBean instanceof GateWayFailNoticRecordRequest){
			try {
				GateWayFailNoticRecordRequest gfr=(GateWayFailNoticRecordRequest) requestBean;
				GateWayFailNoticRecordResponse responseBean=new GateWayFailNoticRecordResponse();
				List<CallbackRecord> list=new ArrayList<CallbackRecord>();
				CallBackToBusinessLog callBackToBusinessLog=new CallBackToBusinessLog();
				callBackToBusinessLog.setStatus("1");
			
				GateWayFailNoticRecordResponse response = new GateWayFailNoticRecordResponse();
				
				List<CallBackToBusinessLog> logList = logService.query(callBackToBusinessLog, gfr.getStartTime(), gfr.getEndTime());
				for(CallBackToBusinessLog record:logList){
					CallbackRecord bean = (CallbackRecord) JSONObject.toBean(
							JSONObject.fromObject(record.getCallBackParam()), CallbackRecord.class);
					list.add(bean);
				}
				response.setBeanList(list);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public AccountCheckingServiceYB getAccountCheckingServiceYB() {
		return accountCheckingServiceYB;
	}

	public void setAccountCheckingServiceYB(AccountCheckingServiceYB accountCheckingServiceYB) {
		this.accountCheckingServiceYB = accountCheckingServiceYB;
	}
}
