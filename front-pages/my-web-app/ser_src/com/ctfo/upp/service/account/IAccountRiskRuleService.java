package com.ctfo.upp.service.account;

import com.ctfo.upp.bean.AccountRiskRule;

/**
 * 
 * @author ziyu.wei
 *
 * 2015年5月21日 下午2:02:11
 * 
 * 账户风控规则
 * 
 */
public interface IAccountRiskRuleService {
	
	/**
	 * 根据账户号查询风控规则
	 * @param accNo
	 * @return
	 * @throws Exception
	 */
	public AccountRiskRule queryRiskRuleByAccNo(String accNo)throws Exception;
	
	/**
	 * 保存风控规则
	 * 
	 * @param accountRiskRule
	 * @throws Exception
	 */
	public void saveRiskRule(AccountRiskRule accountRiskRule)throws Exception;
	
	/**
	 * 按_id删除风控规则
	 * @param id
	 * @throws Exception
	 */
	public void delRiskRule(String id)throws Exception;
	
}
