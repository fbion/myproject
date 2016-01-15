package com.ctfo.upp.service.riskmanager;

import java.util.List;

import com.ctfo.csm.local.DynamicSqlParameter;
import com.ctfo.upp.bean.RiskRule;
import com.ctfo.upp.bean.RiskWarningRecord;
import com.ctfo.upp.models.PaginationResult;

public interface IRiskManagerService {
	
	void saveRiskRule(RiskRule riskRule);
	
	void delRiskRule(RiskRule riskRule);
	
	PaginationResult<Object> queryRiskRule(DynamicSqlParameter requestParam);
	
	List<RiskRule> getRiskRuleFullInfo(DynamicSqlParameter requestParam);
	
	List<String> queryRiskRuleSetting(DynamicSqlParameter requestParam);
	
	void updateRiskRule(RiskRule riskRule);
	
	List<RiskWarningRecord> queryWarningRecord(DynamicSqlParameter requestParam);
	
	void processWarning(String ids, String msg);
	
	boolean isRepeat(RiskRule newRiskRule);
}
