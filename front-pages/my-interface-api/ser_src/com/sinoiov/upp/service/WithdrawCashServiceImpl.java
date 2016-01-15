package com.sinoiov.upp.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sinoiov.upp.service.IWithdrawCashService;
import com.sinoiov.upp.service.dto.OrderDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

/**
 * TODO 本期不用实现
 * 
 * @author sunchuanfu
 */
@Service
public class WithdrawCashServiceImpl extends AbstractService implements IWithdrawCashService {

	@Override
	public Map<String, Object> withdrawCash(OrderDto orderDto) throws UPPServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> withdrawCashRapid(OrderDto orderDto) throws UPPServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
