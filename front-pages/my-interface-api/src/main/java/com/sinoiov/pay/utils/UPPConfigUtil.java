package com.sinoiov.pay.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.business.account.IAccountBusiness;
import com.sinoiov.upp.config.DefaultConfig;
import com.sinoiov.upp.service.dto.AccountDto;

public class UPPConfigUtil{

	private static Log logger = LogFactory.getLog(UPPConfigUtil.class);

	private static String myURI = "interface";

	
	// 取系统参数：缓存　－－　数据库　－－　配置文件
	public static String getValue(String key) {
		
		return DefaultConfig.getValue(key, myURI);
		
	}

	/**
	 * 根据编号获取公钥
	 * 
	 * @param merchantcode
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(String merchantcode) {
		
		return DefaultConfig.getPublicKey(merchantcode, myURI);
		
	}

	public static String getPrivateKey(String merchantcode) {
		
		return DefaultConfig.getPrivateKey(merchantcode, myURI);
		
	}

	/**
	 * 获取账户号
	 * 
	 * @param userId
	 * @return
	 */
	public static String getUPPAccountNo(String userId) throws Exception {

		IAccountBusiness accountBusiness = (IAccountBusiness) ServiceFactory.getFactory().getService(IAccountBusiness.class);

		AccountDto accountDto = accountBusiness.getAccountByUserId(userId);

		return accountDto.getInsideAccountNo();
	}

}
