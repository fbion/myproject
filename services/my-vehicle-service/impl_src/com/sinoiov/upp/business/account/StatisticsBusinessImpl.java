package com.sinoiov.upp.business.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dto.beans.BalanceDto;
import com.ctfo.account.dto.beans.RecordedDeductionDto;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.sinoiov.upp.business.AbstractBusiness;
import com.sinoiov.upp.manager.account.IAccountDetailManager;
import com.sinoiov.upp.manager.account.IAccountManager;

@Service("statisticsBusiness")
public class StatisticsBusinessImpl extends AbstractBusiness implements IStatisticsBusiness {

	private static final Log logger = LogFactory.getLog(StatisticsBusinessImpl.class);
	
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;	
	@Autowired
	@Qualifier("accountDetailManager")
	private IAccountDetailManager accountDetailManager;
	
	
	@Override
	public BalanceDto sumAccountBalance(DynamicSqlParameter parameter) throws UPPException {
		try{
			Map<String,String> equal = parameter.getEqual()!=null?parameter.getEqual():new HashMap<String,String>();
			String startTime = parameter.getStartwith()!=null && parameter.getStartwith().get("createTime")!=null?parameter.getStartwith().get("createTime"):"";
			String endTime = parameter.getEndwith()!=null && parameter.getEndwith().get("createTime")!=null?parameter.getEndwith().get("createTime"):"";
			if(StringUtils.isBlank(equal.get("startTime")) && StringUtils.isNotBlank(startTime))
				equal.put("startTime", startTime);
			if(StringUtils.isBlank(equal.get("endTime")) && StringUtils.isNotBlank(endTime))
				equal.put("endTime", endTime);
			
			Map<String,Object> dbmap = new HashMap<String,Object>();
			String insideAccountNo = equal.get("accountNo")!=null?equal.get("accountNo"):
				equal.get("insideAccountNo")!=null?equal.get("insideAccountNo"):"";
			dbmap.put("insideAccountNo", insideAccountNo);
			dbmap.put("accountType", equal.get("accountType"));
			dbmap.put("ownerUserNo",equal.get("ownerUserNo"));
			dbmap.put("mobileNo", equal.get("mobileNo"));
			dbmap.put("accountStatus", equal.get("accountStatus"));
			if(StringUtils.isNotBlank(equal.get("startTime")))
				dbmap.put("startTime", Long.parseLong(equal.get("startTime")));
			if(StringUtils.isNotBlank(equal.get("endTime")))
				dbmap.put("endTime", Long.parseLong(equal.get("endTime")));
			if(parameter.getLike()!=null){
				dbmap.put("mobileNo", parameter.getLike().get("mobileNo"));
			}
			if(!dbmap.isEmpty()){
				return accountManager.sumAccountBalance(dbmap);
			}
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件统计账户余额！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件统计账户余额:"+e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public RecordedDeductionDto sumAccountDetailRecordedDeduction(DynamicSqlParameter parameter)
			throws UPPException {
		try{			
			Map<String,String> equal = parameter.getEqual()!=null?parameter.getEqual():new HashMap<String,String>();
			String startTime = parameter.getStartwith()!=null && parameter.getStartwith().get("accountTime")!=null?parameter.getStartwith().get("accountTime"):"";
			String endTime = parameter.getEndwith()!=null && parameter.getEndwith().get("accountTime")!=null?parameter.getEndwith().get("accountTime"):"";
			String ownerUserNo = parameter.getLike()!=null && parameter.getLike().get("ownerUserNo")!=null?parameter.getLike().remove("ownerUserNo"):"";
			if(StringUtils.isBlank(equal.get("startTime")) && StringUtils.isNotBlank(startTime))
				equal.put("startTime", startTime);
			if(StringUtils.isBlank(equal.get("endTime")) && StringUtils.isNotBlank(endTime))
				equal.put("endTime", endTime);
			Map<String,Object> dbmap = new HashMap<String,Object>();
			String insideAccountNo = equal.get("accountNo")!=null?equal.get("accountNo"):
				equal.get("insideAccountNo")!=null?equal.get("insideAccountNo"):"";
			dbmap.put("insideAccountNo", insideAccountNo);
			dbmap.put("bookaccountType", equal.get("bookaccountType"));
			dbmap.put("accountSubject", equal.get("accountSubject"));
			if(StringUtils.isNotBlank(ownerUserNo))
				dbmap.put("ownerUserNo",ownerUserNo);
			if(StringUtils.isNotBlank(equal.get("startTime")))
				dbmap.put("startTime", Long.parseLong(equal.get("startTime")));
			if(StringUtils.isNotBlank(equal.get("endTime")))
				dbmap.put("endTime", Long.parseLong(equal.get("endTime")));
			dbmap.put("bookaccountType", "recorded");
			Long recorded = accountDetailManager.sumAccountBookBalance(dbmap);
			dbmap.put("bookaccountType", "deduction");
			Long deduction = accountDetailManager.sumAccountBookBalance(dbmap);
			
			RecordedDeductionDto dto = new RecordedDeductionDto();
			dto.setDeduction(deduction);
			dto.setRecorded(recorded);
			return dto;
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件统计账户余额！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件统计账户余额:"+e.getLocalizedMessage());
		}
	}
}
