package com.sinoiov.upp.service;

import org.springframework.stereotype.Service;

import com.sinoiov.upp.service.IRealNameAuthenService;
import com.sinoiov.upp.service.dto.IdentityDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

/**
 * TODO 本期不用实现
 * 
 * @author sunchuanfu
 */
@Service
public class RealNameAuthenServiceImpl extends AbstractService implements IRealNameAuthenService {

	@Override
	public boolean submitAuthenApplyInfo(IdentityDto uppIdentityDto) throws UPPServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyAuthenApplyInfo(IdentityDto uppIdentityDto) throws UPPServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String queryAuthenState(String idCard) throws UPPServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
