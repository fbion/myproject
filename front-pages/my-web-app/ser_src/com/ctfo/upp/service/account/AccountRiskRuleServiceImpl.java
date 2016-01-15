package com.ctfo.upp.service.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.csm.local.DynamicSqlParameter;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.bean.AccountRiskRule;
import com.google.code.morphia.query.Query;

/**
 * 
 * @author ziyu.wei
 *
 * 2015年5月21日 下午2:02:11
 * 
 * 账户风控规则实现类
 * 
 */

@Service("accountRiskRuleService")
public class AccountRiskRuleServiceImpl implements IAccountRiskRuleService {

	private static Log logger = LogFactory.getLog(AccountRiskRuleServiceImpl.class);
	
	@Autowired
	@Qualifier("mongoService") 
	private IMongoService<AccountRiskRule> mongoService;
	
	@Override
	public AccountRiskRule queryRiskRuleByAccNo(String accNo)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("acc_no", accNo);
		DynamicSqlParameter requestParam = new DynamicSqlParameter();
		requestParam.setEqual(map);
		
		Query<AccountRiskRule> query = mongoService.getQuery(AccountRiskRule.class);
		mongoService.convertParamToQuery(query, requestParam);
		List<AccountRiskRule> accountRiskRuleList = mongoService.query(AccountRiskRule.class, query);
		if (accountRiskRuleList != null && accountRiskRuleList.size() > 0) {
			return accountRiskRuleList.get(0);
		}
		return null;
	}

	@Override
	public void saveRiskRule(AccountRiskRule accountRiskRule)
			throws Exception {
		try {
			this.mongoService.setDatasource("BUSINESS");
			this.mongoService.save(accountRiskRule);
		} catch (Exception e) {
			logger.error("save_acc_risk_rule_err: ", e);
		}
	}

	@Override
	public void delRiskRule(String id) throws Exception {
		this.mongoService.delete(AccountRiskRule.class, id);
	}

}
