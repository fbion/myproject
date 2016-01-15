package com.sinoiov.upp.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ctfo.account.dto.beans.BalanceDto;
import com.ctfo.account.dto.beans.RecordedDeductionDto;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.business.account.IStatisticsBusiness;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service("statisticsService")
public class StatisticsServiceImpl extends AbstractService implements IStatisticsService {

	private IStatisticsBusiness statisticsBusiness;

	private StatisticsServiceImpl() {
		statisticsBusiness = (IStatisticsBusiness) ServiceFactory.getFactory().getService(IStatisticsBusiness.class);
	}
	
	
	@Override
	public  Map<String, String> sumAccount(DynamicSqlParameter parameter)
			throws UPPServiceException {
		try {
			Map<String, String> result = new HashMap<String,String>();
			BalanceDto balanceDto = statisticsBusiness.sumAccountBalance(parameter);
			if(balanceDto != null){
				result.put("totalBalance", AmountUtil.getAmount(balanceDto.getTotalBalance()));
				result.put("unableTakecashBalance", AmountUtil.getAmount(balanceDto.getUnableTakecashBalance()));
				result.put("usableBalance", AmountUtil.getAmount(balanceDto.getUsableBalance()));
				result.put("frozenBalance", AmountUtil.getAmount(balanceDto.getFrozenBalance()));
			}
			return result;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new UPPServiceException(StringUtils.isBlank(e.getErrorCode()) ? ReturnCodeDict.FAIL
					: e.getErrorCode(), e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("根据条件统计账户余额异常：" + e.getLocalizedMessage(), e);
			throw new UPPServiceException(ReturnCodeDict.FAIL, "根据条件统计账户余额异常：" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public  Map<String, String> sumAccountDetail(DynamicSqlParameter parameter)
			throws UPPServiceException {
		try {
			String accountNo = parameter.getEqual()!=null && parameter.getEqual().get("accountNo")!=null?
					parameter.getEqual().get("accountNo"):"";
			Map<String, String> result = new HashMap<String,String>();
			RecordedDeductionDto dto = statisticsBusiness.sumAccountDetailRecordedDeduction(parameter);
			if(dto != null){
				result.put("accountNo", accountNo);
				result.put("paySumBalance", AmountUtil.getAmount(dto.getDeduction()));
				result.put("incomeSumBalance", AmountUtil.getAmount(dto.getRecorded()));
			}
			return result;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new UPPServiceException(StringUtils.isBlank(e.getErrorCode()) ? ReturnCodeDict.FAIL
					: e.getErrorCode(), e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("根据条件统计账户流水异常：" + e.getLocalizedMessage(), e);
			throw new UPPServiceException(ReturnCodeDict.FAIL, "根据条件统计账户流水异常：" + e.getLocalizedMessage(), e);
		}
	}

}
