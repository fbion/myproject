package com.ctfo.upp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.csm.local.DynamicSqlParameter;
import com.ctfo.upp.bean.RiskRule;
import com.ctfo.upp.dto.RiskRuleDto;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.riskmanager.IRiskManagerService;
import com.ctfo.upp.util.ClassUtil;
import com.ctfo.upp.util.Converter;

@Controller
@Scope("prototype")
@RequestMapping("/riskmanager")
public class RiskController extends BaseController{
	
	private static Log logger = LogFactory.getLog(RiskController.class);
	
//	private static final String RISK_GUIDELINE_IP = "IP纬度";
//	private static final String RISK_GUIDELINE_FREQUENCY = "频率纬度";
//	private static final String RISK_GUIDELINE_AMOUNT = "额度纬度";
//	private static final String RISK_GUIDELINE_ACCOUNT = "账户纬度";
//	
//	private static final String RISK_GUIDELINE_VAR_CREATOR_SUFFIX = "_creator";
//	private static final String RISK_GUIDELINE_VAR_TIME_SUFFIX = "_time";
//	private static final String RISK_GUIDELINE_VAR_NO_SUFFIX = "_no";
	
	private static final String RISK_GUIDELINE_IP_VAR_PRE = "ip_";
	private static final String RISK_GUIDELINE_FREQUENCY_VAR_PRE = "fre_";
	private static final String RISK_GUIDELINE_AMOUNT_VAR_PRE = "amount_";
	private static final String RISK_GUIDELINE_ACCOUNT_VAR_PRE = "acc_";
	private static final String RISK_GUIDELINE_PASS_END_STR = "_no";
	
	private static final String RISK_GUIDELINE_TYPE_IP = "1";
	private static final String RISK_GUIDELINE_TYPE_FREQUENCY = "2";
	private static final String RISK_GUIDELINE_TYPE_AMOUNT = "3";
	private static final String RISK_GUIDELINE_TYPE_ACCOUNT = "4";
	
//	private static final String RISK_STATUS_OPEN = "1";
	
	private RiskController(){
			model = new RiskRule();
	}
	
	@Autowired 
	private IRiskManagerService riskManagerService; 
	
	@RequestMapping(value="/queryRiskRule.action",
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object queryRiskRule(DynamicSqlParameter requestParam){
		
		try {
			List<RiskRule> riskRuleList = this.riskManagerService.getRiskRuleFullInfo(requestParam);
			if (riskRuleList != null && riskRuleList.size() > 0) {
				return riskRuleList.get(0);
			}
		} catch (Exception e) {
			logger.error("查询风控规则错误！", e);
		}
		return "";
	}
	
	/**
	 * 查询风控的设置情况，当用户选择纬度时，用于提醒纬度是否已经设置
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value="/queryRiskRuleSetting.action",
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object queryRiskRuleSetting(DynamicSqlParameter requestParam){
		
		try {
			return this.riskManagerService.queryRiskRuleSetting(requestParam);
		} catch (Exception e) {
			logger.error("查询风控规则错误！", e);
		}
		return "";
	}
	
	@RequestMapping(value="/isRepeat.action",
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public String checkRepeat(DynamicSqlParameter requestParam){
		
		try {
			RiskRule rr = (RiskRule) model;
			return this.riskManagerService.isRepeat(rr) ? "1" : "-1";
		} catch (Exception e) {
			logger.error("查询风控规则错误！", e);
		}
		return "";
	}
	
	@RequestMapping(value="/queryRiskItems.action", 
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object queryRiskItems(DynamicSqlParameter requestParam){
		
		try {
			return this.riskManagerService.queryRiskRule(requestParam);
		} catch (Exception e) {
			logger.error("查询风控规则错误！", e);
		}
		return "";
	}
	
	@RequestMapping(value="/saveRiskRule.action", 
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public PaginationResult<?> saveRiskRule(){
		
		PaginationResult<?> result = new PaginationResult();
		try {
			RiskRule oldRiskRule = null;
			DynamicSqlParameter dynaParam = new DynamicSqlParameter();
			List<RiskRule> riskRuleList = this.riskManagerService.getRiskRuleFullInfo(dynaParam);
			if (riskRuleList != null && riskRuleList.size() > 0) {
				for (RiskRule rule : riskRuleList) {
					oldRiskRule = rule;
					this.riskManagerService.delRiskRule(rule);
				}
			}
			assignNo(oldRiskRule, (RiskRule) model);
			this.riskManagerService.saveRiskRule((RiskRule) model);
			result.setMessage(Converter.OP_SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("保存风控规则错误！", e);
			result.setMessage(Converter.OP_FAILED);
		}
		return result;
	}
	
	private void assignNo(RiskRule old, RiskRule _new) {
		
		//已经插入过风控规则
		if (old != null) {
			_new.setIp_no(old.getIp_no());
			_new.setFre_no(old.getFre_no());
			_new.setAmount_no(old.getAmount_no());
			_new.setAcc_no(old.getAcc_no());
		} else {
			int index = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
			String geneNo = sdf.format(new Date());
			_new.setIp_no(geneNo + (index++));
			_new.setFre_no(geneNo + (index++));
			_new.setAmount_no(geneNo + (index++));
			_new.setAcc_no(geneNo + (index++));
		}
	}
	
	@RequestMapping(value="/updateRiskRule.action", 
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public PaginationResult<?> updateRiskRule(HttpServletRequest request){
		
		PaginationResult<?> result = new PaginationResult();
		try {
			String type = request.getParameter("type");
			if (StringUtils.isBlank(type)) {
				result.setMessage(Converter.OP_FAILED);
				return result;
			}
			
			RiskRule oldRiskRule = null;
			DynamicSqlParameter dynaParam = new DynamicSqlParameter();
			List<RiskRule> riskRuleList = this.riskManagerService.getRiskRuleFullInfo(dynaParam);
			if (riskRuleList != null && riskRuleList.size() > 0) {
				oldRiskRule = riskRuleList.get(0);
				for (RiskRule rule : riskRuleList) {
					this.riskManagerService.delRiskRule(rule);
				}
			}
			if (oldRiskRule != null) {
				if (RISK_GUIDELINE_TYPE_IP.equals(type)) {
					ClassUtil.copySpecNewValue2OldObj(oldRiskRule, (RiskRule)model, 
							RISK_GUIDELINE_IP_VAR_PRE, RISK_GUIDELINE_PASS_END_STR);
					oldRiskRule.setIp_time(new Date().getTime());
				}
				if (RISK_GUIDELINE_TYPE_FREQUENCY.equals(type)) {
					ClassUtil.copySpecNewValue2OldObj(oldRiskRule, (RiskRule)model, 
							RISK_GUIDELINE_FREQUENCY_VAR_PRE, RISK_GUIDELINE_PASS_END_STR);
					oldRiskRule.setFre_time(new Date().getTime());
				}
				if (RISK_GUIDELINE_TYPE_AMOUNT.equals(type)) {
					ClassUtil.copySpecNewValue2OldObj(oldRiskRule, (RiskRule)model, 
							RISK_GUIDELINE_AMOUNT_VAR_PRE, RISK_GUIDELINE_PASS_END_STR);
					oldRiskRule.setAmount_time(new Date().getTime());
				}
				if (RISK_GUIDELINE_TYPE_ACCOUNT.equals(type)) {
					ClassUtil.copySpecNewValue2OldObj(oldRiskRule, (RiskRule)model, 
							RISK_GUIDELINE_ACCOUNT_VAR_PRE, RISK_GUIDELINE_PASS_END_STR);
					oldRiskRule.setAcc_time(new Date().getTime());
				}
				oldRiskRule.setId(null);
				this.riskManagerService.saveRiskRule(oldRiskRule);
				result.setMessage(Converter.OP_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("保存风控规则错误！", e);
			result.setMessage(Converter.OP_FAILED);
		}
		return result;
	}
	
	@RequestMapping(value="/queryWarningRecord.action",
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object queryWarningRecord(DynamicSqlParameter requestParam){
		
		try {
			List recordList = this.riskManagerService.queryWarningRecord(requestParam);
			
			PaginationResult<Object> result = new PaginationResult<Object>();
			result.setData(recordList);
			result.setTotal(recordList.size());
			result.setMessage(Converter.OP_SUCCESS);
			
			return result;
		} catch (Exception e) {
			logger.error("查询风控规则错误！", e);
		}
		return "";
	}
	
	/**
	 * 处理预警记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/processWarning.action", produces = "application/json;charset=utf-8")
	@ResponseBody
	public PaginationResult processWarning(HttpServletRequest request) {

		PaginationResult<?> result = new PaginationResult();
		try {
			String ids = request.getParameter("ids");
			String msg = request.getParameter("processResult");
			this.riskManagerService.processWarning(ids, msg);
			result.setMessage(Converter.OP_SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("查询风控规则错误！", e);
			result.setMessage(Converter.OP_FAILED);
		}
		return result;
	}
	
}
