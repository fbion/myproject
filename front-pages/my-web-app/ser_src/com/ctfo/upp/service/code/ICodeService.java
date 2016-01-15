package com.ctfo.upp.service.code;

import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.upp.service.IService;

public interface ICodeService extends IService{
	/**
	 * 修改码表状态
	 * @param id
	 * @param status
	 * @param op
	 */
	public void modifyStatus(String id, String status,  Operator op);
	
}
