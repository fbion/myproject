package com.ctfo.upp.gatewayservice.service.yibao.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.yibao.business.accountaccess.CertifyAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CertifyAccountBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CreateAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.CreateAccountBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.LockAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.LockAccountBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.SetAccountCertifyBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.SetAccountCertifyBeanResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.UnlockAccountBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.UnlockAccountBeanResponseYB;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.CertifyAccountRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.CertifyAccountResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.CreateAccountRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.CreateAccountResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.FreezeAccountRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.FreezeAccountResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.SetAccountCertifyRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.SetAccountCertifyResponse;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.UnFreezeAccountRequest;
import com.ctfo.upp.gatewayservice.bean.yibao.remote.accountaccess.UnFreezeAccountResponse;
import com.ctfo.upp.gatewayservice.service.yibao.intf.AccountServiceYB;
import com.ctfo.upp.gatewayservice.service.yibao.intf.DistributeServiceYB;
import com.ctfo.upp.gatewayservice.util.yibao.PareseObjectParameter;

@Service("accountServiceYB")
public class AccountServiceImplYB extends BaseServiceImpl implements AccountServiceYB {
	
	protected static final Log log = LogFactory.getLog(AccountServiceImplYB.class);
	
	@Autowired(required=false)
	private DistributeServiceYB distributeServiceYB;
	
	@Override
	public CreateAccountBeanResponseYB createAccount(
			CreateAccountBeanRequestYB createAccountBeanRequest)
			throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		CreateAccountRequest request = new CreateAccountRequest();
		request.setReq02_userIdentityNumber(createAccountBeanRequest.getAccountId());
		request.setReq08_userName(createAccountBeanRequest.getUserName());
		request.setReq11_mobile(createAccountBeanRequest.getMobile());
		request.setReq10_idCardType(createAccountBeanRequest.getIdCardType());
		request.setReq09_idCardNo(createAccountBeanRequest.getIdCardNo());
		request.setReq50_authStatus(createAccountBeanRequest.getAuthStatus());
		request.setReq13_trxPsd(createAccountBeanRequest.getTrxPsd());
		request.setReq03_requestId(createAccountBeanRequest.getRequestId());
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		CreateAccountResponse response = (CreateAccountResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq02_userIdentityNumber().equals(response.getResp03_userIdentityNumber())){
				log.error(">>>>>>>>>> 请求与返回的userIdentityNumber不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		CreateAccountBeanResponseYB r = new CreateAccountBeanResponseYB();
		r.setFlag(flag);
		return r;
	}

	@Override
	public LockAccountBeanResponseYB lockAccount(
			LockAccountBeanRequestYB lockAccountBeanRequest) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		FreezeAccountRequest request = new FreezeAccountRequest();
		request.setReq02_userIdentityNumber(lockAccountBeanRequest.getAccountId());
		request.setReq03_requestId(lockAccountBeanRequest.getRequestId());
		request.setReq27_accountFreezeReason(lockAccountBeanRequest.getAccountFreezeReason());
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		FreezeAccountResponse response = (FreezeAccountResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq02_userIdentityNumber().equals(response.getResp03_userIdentityNumber())){
				log.error(">>>>>>>>>> 请求与返回的userIdentityNumber不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		LockAccountBeanResponseYB lockAccountBeanResponseYB = new LockAccountBeanResponseYB();
		lockAccountBeanResponseYB.setFlag(flag);
		return lockAccountBeanResponseYB;
	}

	@Override
	public UnlockAccountBeanResponseYB unlockAccount(
			UnlockAccountBeanRequestYB unlockAccountBeanRequest)
			throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		UnFreezeAccountRequest request = new UnFreezeAccountRequest();
		request.setReq02_userIdentityNumber(unlockAccountBeanRequest.getAccountId());
		request.setReq03_requestId(unlockAccountBeanRequest.getRequestId());
		request.setReq27_accountFreezeReason(unlockAccountBeanRequest.getAccountUnFreezeReason());
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		UnFreezeAccountResponse response = (UnFreezeAccountResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq02_userIdentityNumber().equals(response.getResp03_userIdentityNumber())){
				log.error(">>>>>>>>>> 请求与返回的userIdentityNumber不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		UnlockAccountBeanResponseYB unFreezeAccountBeanResponseYB = new UnlockAccountBeanResponseYB();
		unFreezeAccountBeanResponseYB.setFlag(flag);
		return unFreezeAccountBeanResponseYB;
	}

	@Override
	public CertifyAccountBeanResponseYB certifyAccount(
			CertifyAccountBeanRequestYB certifyAccountBeanRequest)
			throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		CertifyAccountRequest request = new CertifyAccountRequest();
		request.setReq02_userIdentityNumber(certifyAccountBeanRequest.getAccountId());
		request.setReq08_userName(certifyAccountBeanRequest.getUserName());
		request.setReq09_idCardNo(certifyAccountBeanRequest.getIdCardNo());
		request.setReq11_mobile(certifyAccountBeanRequest.getMobile());
		request.setReq18_bankCardNumber(certifyAccountBeanRequest.getBankCardNumber());
		request.setReq19_bankCode(certifyAccountBeanRequest.getBankCode());
		request.setReq20_branchName(certifyAccountBeanRequest.getBranchName());
		request.setReq21_province(certifyAccountBeanRequest.getProvince());
		request.setReq22_city(certifyAccountBeanRequest.getCity());
		request.setReq23_idCardEndDate(certifyAccountBeanRequest.getIdCardEndDate());
		request.setReq03_requestId(certifyAccountBeanRequest.getRequestId());
		request.setHmac(createHMAC(request, 1));
		log.info("submitAuthenApplyInfo_reqYB_param: " + JSONObject.fromObject(request).toString());
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		CertifyAccountResponse response = (CertifyAccountResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq02_userIdentityNumber().equals(response.getResp03_userIdentityNumber())){
				log.error(">>>>>>>>>> 请求与返回的userIdentityNumber不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		if(response.getResp01_code().equals("503")){//重复认证也算认证成功
			flag = true;
		}
		CertifyAccountBeanResponseYB certifyAccountBeanResponseYB = new CertifyAccountBeanResponseYB();
		certifyAccountBeanResponseYB.setFlag(flag);
		return certifyAccountBeanResponseYB;
	}

	@Override
	public SetAccountCertifyBeanResponseYB setAccountCertify(
			SetAccountCertifyBeanRequestYB setAccountCertifyBeanRequest)
			throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		SetAccountCertifyRequest request = new SetAccountCertifyRequest();
		request.setReq02_userIdentityNumber(setAccountCertifyBeanRequest.getAccountId());
		request.setReq03_requestId(setAccountCertifyBeanRequest.getRequestId());
		request.setReq08_userName(setAccountCertifyBeanRequest.getUserName());
		request.setReq10_idCardType(setAccountCertifyBeanRequest.getIdCardType());
		request.setReq09_idCardNo(setAccountCertifyBeanRequest.getIdCardNo());
		request.setHmac(createHMAC(request, 1));
		List<String> result = distributeServiceYB.distributeRequest(request, 1);
		SetAccountCertifyResponse response = (SetAccountCertifyResponse) PareseObjectParameter.pareseStringToObject(result, request.getClass().getName());
		if(super.validateResponse(request, response)){
			if(!request.getReq02_userIdentityNumber().equals(response.getResp03_userIdentityNumber())){
				log.error(">>>>>>>>>> 请求与返回的userIdentityNumber不一致  <<<<<<<<<<");
				flag = false;
			}
			if(!request.getReq03_requestId().equals(response.getResp04_requestId())){
				log.error(">>>>>>>>>> 请求与返回的requestId不一致  <<<<<<<<<<");
				flag = false;
			}
		}else{
			flag = false;
		}
		SetAccountCertifyBeanResponseYB setAccountCertifyBeanResponseYB = new SetAccountCertifyBeanResponseYB();
		setAccountCertifyBeanResponseYB.setFlag(flag);
		return setAccountCertifyBeanResponseYB;
	}

	public DistributeServiceYB getDistributeServiceYB() {
		return distributeServiceYB;
	}

	public void setDistributeServiceYB(DistributeServiceYB distributeServiceYB) {
		this.distributeServiceYB = distributeServiceYB;
	}

}
