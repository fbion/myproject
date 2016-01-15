package com.ctfo.upp.service.riskmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.csm.local.DynamicSqlParameter;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.bean.RiskRule;
import com.ctfo.upp.bean.RiskWarningRecord;
import com.ctfo.upp.dto.RiskRuleDto;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.util.ClassUtil;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.utils.SpringBUtils;

@Service
public class RiskManagerServiceImpl implements IRiskManagerService {
	private static Log logger = LogFactory.getLog(RiskManagerServiceImpl.class);
	
	private IMongoService<RiskRule> riskRuleService;
	
	private static final String RISK_GUIDELINE_IP = "IP纬度";
	private static final String RISK_GUIDELINE_FREQUENCY = "频率纬度";
	private static final String RISK_GUIDELINE_AMOUNT = "额度纬度";
	private static final String RISK_GUIDELINE_ACCOUNT = "账户纬度";
	
	private static final String RISK_GUIDELINE_VAR_CREATOR_SUFFIX = "_creator";
	private static final String RISK_GUIDELINE_VAR_TIME_SUFFIX = "_time";
	private static final String RISK_GUIDELINE_VAR_NO_SUFFIX = "_no";
	
	private static final String RISK_GUIDELINE_IP_VAR_PRE = "ip_";
	private static final String RISK_GUIDELINE_FREQUENCY_VAR_PRE = "fre_";
	private static final String RISK_GUIDELINE_AMOUNT_VAR_PRE = "amount_";
	private static final String RISK_GUIDELINE_ACCOUNT_VAR_PRE = "acc_";
	
	private static final String RISK_GUIDELINE_TYPE_IP = "1";
	private static final String RISK_GUIDELINE_TYPE_FREQUENCY = "2";
	private static final String RISK_GUIDELINE_TYPE_AMOUNT = "3";
	private static final String RISK_GUIDELINE_TYPE_ACCOUNT = "4";
	
	private static final String RISK_STATUS_OPEN = "1";
	
	@Autowired
	@Qualifier("mongoService") 
	private IMongoService mongoService;
	
	public RiskManagerServiceImpl() {
		riskRuleService = (IMongoService) SpringBUtils.getBean("mongoService");
		if(riskRuleService!=null)
			riskRuleService.setDatasource("BUSINESS");
	}
	
	@Override
	public void saveRiskRule(RiskRule riskRule) {
		try {
			long now = new Date().getTime();
			riskRule.setIp_time(now);
			riskRule.setFre_time(now);
			riskRule.setAmount_time(now);
			riskRule.setAcc_time(now);
			this.riskRuleService.save(riskRule);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateRiskRule(RiskRule riskRule) {
		try {
			this.riskRuleService.update(riskRule);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PaginationResult<Object> queryRiskRule(DynamicSqlParameter requestParam) {
		try {
			List<RiskRule> riskRuleList = this.riskRuleService.query(RiskRule.class, requestParam);
			
			if (riskRuleList != null && riskRuleList.size() > 0) {
				RiskRule riskRule = riskRuleList.get(0);
				int ruleCount = 0;
				RiskRuleDto rrd = null;
				List list = new ArrayList();
				
				String riskTypeQueryParam = "";
				String riskStatusQueryParam = "";
				String startTimeQueryParam = "";
				String endTimeQueryParam = "";
				Map<String, String> equalMap = requestParam.getEqual();
				Map<String, String> startwithMap = requestParam.getStartwith();
				Map<String, String> endwithMap = requestParam.getEndwith();
				
				if (equalMap != null && equalMap.size() > 0) {
					Set<String> keySet = equalMap.keySet();
					for (String key : keySet) {
						//按纬度查询
						if ("riskType".equals(key)) {
							riskTypeQueryParam = equalMap.get(key);
						}
						
						//按状态查询
						if ("riskStatus".equals(key)) {
							riskStatusQueryParam = equalMap.get(key);
						}
					}
				} 
				
				if (startwithMap != null && startwithMap.size() > 0) {
					startTimeQueryParam = startwithMap.get("timeStart");
				}
				
				if (endwithMap != null && endwithMap.size() > 0) {
					endTimeQueryParam = endwithMap.get("timeEnd");
				}
				
				
				List<String> endWithList = new ArrayList<String>();
				endWithList.add(RISK_GUIDELINE_VAR_TIME_SUFFIX);
				endWithList.add(RISK_GUIDELINE_VAR_CREATOR_SUFFIX);
				endWithList.add(RISK_GUIDELINE_VAR_NO_SUFFIX);
				
				if (showIpItem(riskTypeQueryParam, riskStatusQueryParam, 
						startTimeQueryParam, endTimeQueryParam, riskRule)) {
					addIpItem(riskRule, endWithList, list, ruleCount);
				}
				
				if (showFreItem(riskTypeQueryParam, riskStatusQueryParam, 
						startTimeQueryParam, endTimeQueryParam, riskRule)) {
					addFreItem(riskRule, endWithList, list, ruleCount);
				}
				
				if (showAmountItem(riskTypeQueryParam, riskStatusQueryParam, 
						startTimeQueryParam, endTimeQueryParam, riskRule)) {
					addAmountItem(riskRule, endWithList, list, ruleCount);
				}
				
				if (showAccountItem(riskTypeQueryParam, riskStatusQueryParam, 
						startTimeQueryParam, endTimeQueryParam, riskRule)) {
					addAccountItem(riskRule, endWithList, list, ruleCount);
				}
				
				PaginationResult<Object> result = new PaginationResult<Object>();
				result.setData(list);
				result.setTotal(ruleCount);
				result.setMessage(Converter.OP_SUCCESS);
				
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void addIpItem(RiskRule riskRule, List endWithList, List list, int ruleCount) {
		try {
			if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
					RISK_GUIDELINE_IP_VAR_PRE, endWithList)) {
			this.addRiskItem(list, ruleCount, riskRule.getIp_no(), RISK_GUIDELINE_IP, riskRule.getIp_time()+"", 
					riskRule.getIp_creator(), showRiskStatus(riskRule.getIp_risk_status()), RISK_GUIDELINE_TYPE_IP);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
}

private void addFreItem(RiskRule riskRule, List endWithList, List list, int ruleCount) {
	//频率纬度
	try {
		if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
				RISK_GUIDELINE_FREQUENCY_VAR_PRE, endWithList)) {
			this.addRiskItem(list, ruleCount, riskRule.getFre_no(), 
					RISK_GUIDELINE_FREQUENCY, riskRule.getFre_time() + "", 
					riskRule.getFre_creator(), showRiskStatus(riskRule.getFre_risk_status()), 
					RISK_GUIDELINE_TYPE_FREQUENCY);
		}
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	}
}

private void addAmountItem(RiskRule riskRule, List endWithList, List list, int ruleCount) {
	//额度纬度
	try {
		if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
				RISK_GUIDELINE_AMOUNT_VAR_PRE, endWithList)) {
			this.addRiskItem(list, ruleCount, riskRule.getAmount_no(), 
					RISK_GUIDELINE_AMOUNT, riskRule.getAmount_time() + "", 
					riskRule.getAmount_creator(), showRiskStatus(riskRule.getAmount_risk_status()), 
					RISK_GUIDELINE_TYPE_AMOUNT);
		}
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	}
}

private void addAccountItem(RiskRule riskRule, List endWithList, List list, int ruleCount) {
	//账户纬度
	try {
		if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
				RISK_GUIDELINE_ACCOUNT_VAR_PRE, endWithList)) {
			this.addRiskItem(list, ruleCount, riskRule.getAcc_no(), 
					RISK_GUIDELINE_ACCOUNT, riskRule.getAcc_time() + "", 
					riskRule.getAcc_creator(), showRiskStatus(riskRule.getAcc_risk_status()), 
					RISK_GUIDELINE_TYPE_ACCOUNT);
		}
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	}
}

	/**
	 * 根据查询条件判定是否显示IP纬度
	 * @param riskTypeQueryParam
	 * @param riskStatusQueryParam
	 * @param startTimeQueryParam
	 * @param endTimeQueryParam
	 * @param riskRule
	 * @return
	 */
	private boolean showIpItem(String riskTypeQueryParam, String riskStatusQueryParam, 
			String startTimeQueryParam, String endTimeQueryParam, RiskRule riskRule) {
		boolean showIpItemFlag = true;
		//IP纬度
		if (StringUtils.isNotBlank(riskTypeQueryParam) 
				&& !RISK_GUIDELINE_TYPE_IP.equals(riskTypeQueryParam)) {
			showIpItemFlag = false;
		}
		
		if (showIpItemFlag) {
			String ipRiskStatus = riskRule.getIp_risk_status() == null ? "0" : riskRule.getIp_risk_status();
			if (StringUtils.isNotBlank(riskStatusQueryParam) && !ipRiskStatus.equals(riskStatusQueryParam)) {
				showIpItemFlag = false;
			}
			
			if (showIpItemFlag) {
				long ipStartTime =  riskRule.getIp_time();
				//存在ip纬度记录
				if (ipStartTime > 0) {
					if (StringUtils.isNotBlank(startTimeQueryParam) && !(ipStartTime > Long.valueOf(startTimeQueryParam))) {
						showIpItemFlag = false;
					} 
					if (showIpItemFlag) {
						if (StringUtils.isNotBlank(endTimeQueryParam) && !(ipStartTime <= Long.valueOf(endTimeQueryParam))) {
							showIpItemFlag = false;
						}
					}
					
				}
			}
			
		}
		return showIpItemFlag;
	}
	
	/**
	 * 根据查询条件判定是否显示频率纬度
	 * @param riskTypeQueryParam
	 * @param riskStatusQueryParam
	 * @param startTimeQueryParam
	 * @param endTimeQueryParam
	 * @param riskRule
	 * @return
	 */
	private boolean showFreItem(String riskTypeQueryParam, String riskStatusQueryParam, 
			String startTimeQueryParam, String endTimeQueryParam, RiskRule riskRule) {
		boolean showFreItemFlag = true;
		//IP纬度
		if (StringUtils.isNotBlank(riskTypeQueryParam) 
				&& !RISK_GUIDELINE_TYPE_FREQUENCY.equals(riskTypeQueryParam)) {
			showFreItemFlag = false;
		}
		
		if (showFreItemFlag) {
			String freRiskStatus = riskRule.getFre_risk_status() == null ? "0" : riskRule.getFre_risk_status();
			if (StringUtils.isNotBlank(riskStatusQueryParam) && !freRiskStatus.equals(riskStatusQueryParam)) {
				showFreItemFlag = false;
			}
			
			if (showFreItemFlag) {
				long freTime =  riskRule.getFre_time();
				//存在ip纬度记录
				if (freTime > 0) {
					if (StringUtils.isNotBlank(startTimeQueryParam) && !(freTime > Long.valueOf(startTimeQueryParam))) {
						showFreItemFlag = false;
					} 
					if (showFreItemFlag) {
						if (StringUtils.isNotBlank(endTimeQueryParam) && !(freTime <= Long.valueOf(endTimeQueryParam))) {
							showFreItemFlag = false;
						}
					}
					
				}
			}
			
		}
		return showFreItemFlag;
	}
	
	/**
	 * 根据查询条件判定是否显示额度纬度
	 * @param riskTypeQueryParam
	 * @param riskStatusQueryParam
	 * @param startTimeQueryParam
	 * @param endTimeQueryParam
	 * @param riskRule
	 * @return
	 */
	private boolean showAmountItem(String riskTypeQueryParam, String riskStatusQueryParam, 
			String startTimeQueryParam, String endTimeQueryParam, RiskRule riskRule) {
		boolean showAmountItemFlag = true;
		//IP纬度
		if (StringUtils.isNotBlank(riskTypeQueryParam) 
				&& !RISK_GUIDELINE_TYPE_AMOUNT.equals(riskTypeQueryParam)) {
			showAmountItemFlag = false;
		}
		
		if (showAmountItemFlag) {
			String amountRiskStatus = riskRule.getAmount_risk_status() == null ? "0" : riskRule.getAmount_risk_status();
			if (StringUtils.isNotBlank(riskStatusQueryParam) && !amountRiskStatus.equals(riskStatusQueryParam)) {
				showAmountItemFlag = false;
			}
			
			if (showAmountItemFlag) {
				long amountTime =  riskRule.getAmount_time();
				//存在ip纬度记录
				if (amountTime > 0) {
					if (StringUtils.isNotBlank(startTimeQueryParam) && !(amountTime > Long.valueOf(startTimeQueryParam))) {
						showAmountItemFlag = false;
					} 
					if (showAmountItemFlag) {
						if (StringUtils.isNotBlank(endTimeQueryParam) && !(amountTime <= Long.valueOf(endTimeQueryParam))) {
							showAmountItemFlag = false;
						}
					}
					
				}
			}
			
		}
		return showAmountItemFlag;
	}
	
	/**
	 * 根据查询条件判定是否显示账户
	 * @param riskTypeQueryParam
	 * @param riskStatusQueryParam
	 * @param startTimeQueryParam
	 * @param endTimeQueryParam
	 * @param riskRule
	 * @return
	 */
	private boolean showAccountItem(String riskTypeQueryParam, String riskStatusQueryParam, 
			String startTimeQueryParam, String endTimeQueryParam, RiskRule riskRule) {
		boolean showAccountItemFlag = true;
		//IP纬度
		if (StringUtils.isNotBlank(riskTypeQueryParam) 
				&& !RISK_GUIDELINE_TYPE_ACCOUNT.equals(riskTypeQueryParam)) {
			showAccountItemFlag = false;
		}
		
		if (showAccountItemFlag) {
			String accRiskStatus = riskRule.getAcc_risk_status() == null ? "0" : riskRule.getAcc_risk_status();
			if (StringUtils.isNotBlank(riskStatusQueryParam) && !accRiskStatus.equals(riskStatusQueryParam)) {
				showAccountItemFlag = false;
			}
			
			if (showAccountItemFlag) {
				long accTime =  riskRule.getAcc_time();
				//存在ip纬度记录
				if (accTime > 0) {
					if (StringUtils.isNotBlank(startTimeQueryParam) && !(accTime > Long.valueOf(startTimeQueryParam))) {
						showAccountItemFlag = false;
					} 
					if (showAccountItemFlag) {
						if (StringUtils.isNotBlank(endTimeQueryParam) && !(accTime <= Long.valueOf(endTimeQueryParam))) {
							showAccountItemFlag = false;
						}
					}
					
				}
			}
			
		}
		return showAccountItemFlag;
	}
	
	private void addRiskItem(List list, int ruleCount, String no, String name, String createTime, 
			String creator, String status, String type) {
	RiskRuleDto rrd = new RiskRuleDto();
	rrd.setNo(no);
	rrd.setRiskItem(name);
	rrd.setCreateTime(createTime);
	rrd.setCreator(creator);
	rrd.setStatus(status);
	rrd.setType(type);
	list.add(rrd);
	ruleCount++;
}
	private String showRiskStatus(String riskStatusCode) {
		if (RISK_STATUS_OPEN.equals(riskStatusCode)) {
			return "启用";
		} else {
			return "停用";
		}
	}
	@Override
	public void delRiskRule(RiskRule riskRule) {
		try {
			this.mongoService.delete(RiskRule.class, new ObjectId(riskRule.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<RiskWarningRecord> queryWarningRecord(
			DynamicSqlParameter requestParam) {
		try {
			return this.mongoService.query(RiskWarningRecord.class, requestParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void processWarning(String ids, String msg) {
		try {
			List<RiskWarningRecord> recordList = null;
			if (StringUtils.isNotBlank(ids)) {
				String[] _ids = ids.split(",");
				RiskWarningRecord newRecord = null;
				for (String id : _ids) {
					if (StringUtils.isNotBlank(id) && id.length() > 0) {
						Map<String, String> map = new HashMap<String, String>();
						DynamicSqlParameter dynaSql = new DynamicSqlParameter();
						map.put("_id", id);
						dynaSql.setEqual(map);
						recordList = this.mongoService.query(RiskWarningRecord.class, dynaSql);
						if (recordList != null && recordList.size() > 0) {
							newRecord = recordList.get(0);
							newRecord.setProcess_result("1");
							newRecord.setProcess_result(msg);
						}
						this.mongoService.delete(RiskWarningRecord.class, id);
						this.mongoService.save(newRecord);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<RiskRule> getRiskRuleFullInfo(DynamicSqlParameter requestParam) {
		try {
			return (List<RiskRule>)this.mongoService.query(RiskRule.class, requestParam);
		} catch (Exception e) {
			logger.error("获取风险规则信息异常", e);
		}
		return null;
	}

	@Override
	public List<String> queryRiskRuleSetting(DynamicSqlParameter requestParam) {
		List<String> returnList = new ArrayList<String>(); 
		try {
			
			List<RiskRule> ruleList = (List<RiskRule>)this.mongoService.query(RiskRule.class, requestParam);
			if (ruleList != null && ruleList.size() > 0) {
				List<String> endWithList = new ArrayList<String>();
				endWithList.add(RISK_GUIDELINE_VAR_TIME_SUFFIX);
				endWithList.add(RISK_GUIDELINE_VAR_CREATOR_SUFFIX);
				endWithList.add(RISK_GUIDELINE_VAR_NO_SUFFIX);

				RiskRule riskRule = ruleList.get(0);
				//ip
				if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
						RISK_GUIDELINE_IP_VAR_PRE, endWithList)) {
					returnList.add(RISK_GUIDELINE_TYPE_IP + "-" + "1");
				} else {
					returnList.add(RISK_GUIDELINE_TYPE_IP + "-" + "0");
				}
				
				//频率
				if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
						RISK_GUIDELINE_FREQUENCY_VAR_PRE, endWithList)) {
					returnList.add(RISK_GUIDELINE_TYPE_FREQUENCY + "-" + "1");
				} else {
					returnList.add(RISK_GUIDELINE_TYPE_FREQUENCY + "-" + "0");
				}
				
				//额度
				if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
						RISK_GUIDELINE_AMOUNT_VAR_PRE, endWithList)) {
					returnList.add(RISK_GUIDELINE_TYPE_AMOUNT + "-" + "1");
				} else {
					returnList.add(RISK_GUIDELINE_TYPE_AMOUNT + "-" + "0");
				}
				
				//账户
				if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
						RISK_GUIDELINE_ACCOUNT_VAR_PRE, endWithList)) {
					returnList.add(RISK_GUIDELINE_TYPE_ACCOUNT + "-" + "1");
				} else {
					returnList.add(RISK_GUIDELINE_TYPE_ACCOUNT + "-" + "0");
				}
				
			}
		} catch (Exception e) {
			logger.error("查询风控设置情况异常", e);
		}
		return returnList;
	}

	@Override
	public boolean isRepeat(RiskRule newRiskRule) {
		
		try {
			DynamicSqlParameter dynaParam = new DynamicSqlParameter();
			List<RiskRule> riskRuleList =  this.riskRuleService.query(RiskRule.class, dynaParam);
			if (riskRuleList != null && riskRuleList.size() > 0) {
				RiskRule oldRiskRule = riskRuleList.get(0);
				
				String _oldRule = settingString(oldRiskRule) == null ? "" : settingString(oldRiskRule);
				String _newRule = settingString(newRiskRule) == null ? "" : settingString(newRiskRule);
				
				if (_oldRule.equals(_newRule)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			logger.error("获取新老规则设置情况异常", e);
		}
		
		return false;
	}
	
	private String settingString(RiskRule riskRule) {
		StringBuffer buffer = new StringBuffer();
		
		List<String> endWithList = new ArrayList<String>();
		endWithList.add(RISK_GUIDELINE_VAR_TIME_SUFFIX);
		endWithList.add(RISK_GUIDELINE_VAR_CREATOR_SUFFIX);
		endWithList.add(RISK_GUIDELINE_VAR_NO_SUFFIX);
		
		try {
			//ip
			if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
					RISK_GUIDELINE_IP_VAR_PRE, endWithList)) {
				buffer.append("1");
			} else {
				buffer.append("0");
			}
			
			//频率
			if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
					RISK_GUIDELINE_FREQUENCY_VAR_PRE, endWithList)) {
				buffer.append("1");
			} else {
				buffer.append("0");
			}
			
			//额度
			if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
					RISK_GUIDELINE_AMOUNT_VAR_PRE, endWithList)) {
				buffer.append("1");
			} else {
				buffer.append("0");
			}
			
			//账户
			if (ClassUtil.startWithSpecMethodHasValue(riskRule, 
					RISK_GUIDELINE_ACCOUNT_VAR_PRE, endWithList)) {
				buffer.append("1");
			} else {
				buffer.append("0");
			}
			
			return buffer.toString();
		} catch (Exception e) {
			logger.error("拼接规则设置情况异常", e);
		} 
		
		return "";
	}
}
