package com.sinoiov.upp.business.account;

import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.AuthenApplyInfo;
import com.ctfo.upp.exception.UPPException;

@Service("realNameAuthenBusiness")
public class RealNameAuthenBusinessImpl implements IRealNameAuthenBusiness {

	@Override
	public AuthenApplyInfo addAuthenApplyInfo(AuthenApplyInfo authenApplyInfo)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modifyAuthenApplyInfo(AuthenApplyInfo authenApplyInfo)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAuthenState(String idCard) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

}
