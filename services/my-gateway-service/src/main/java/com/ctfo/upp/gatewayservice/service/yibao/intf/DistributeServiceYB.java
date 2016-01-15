package com.ctfo.upp.gatewayservice.service.yibao.intf;

import java.util.List;

public interface DistributeServiceYB {
	
	//type=1：发往账户通
	//type=2：发往委托结算
	public List<String> distributeRequest(Object object, int type) throws Exception;
	
	//type=1：账户通回调
	//type=2：委托结算回调
	public void distributeResponse(Object object, int type) throws Exception;
}
