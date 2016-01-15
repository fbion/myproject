package com.ctfo.upp.payment.intf.thirdpart.query.internal.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.payment.dto.beans.AccountInfoDto;
import com.ctfo.payment.dto.beans.YbAccountStatusAggregate;
import com.ctfo.upp.accountservice.payment.internal.impl.AbstractPayment;
import com.ctfo.upp.dict.AccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.payment.intf.thirdpart.query.internal.ThirdPartAccountInspectManager;
import com.ctfo.upp.payment.intf.thirdpart.query.internal.ThirdPartyPaymentQueryManager;

//@Service("thirdPartAccountInspectManager")
public class ThirdPartAccountInspectManagerImpl extends AbstractPayment implements ThirdPartAccountInspectManager{
	private static Log logger = LogFactory.getLog(ThirdPartAccountInspectManagerImpl.class);
	@Autowired
	@Qualifier("thirdPartyPaymentQueryManager")
	private ThirdPartyPaymentQueryManager thirdPartyPaymentQueryManager;
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("mongoService") 
	private IMongoService mongoService;
	
	@Override
	public void dealWithAccountInspect() throws UPPException{
		try {
			AccountExampleExtended accountExampleExtended = new AccountExampleExtended();
			
			accountExampleExtended.createCriteria().andAccountStatusEqualTo(AccountDict.ACCOUNT_STATUS_NORMAL);
			List<AccountInfoDto> dtoList =new ArrayList<AccountInfoDto>();
			List<Account> collectMoneyAccountList = super.getModels(accountExampleExtended);
			String ignoreProperties[] = {};
			for(Account account:collectMoneyAccountList){
				try{
				QueryAccountInfoResponseYB res = thirdPartyPaymentQueryManager.queryThirdPartyAccountInfo(PayDict.PLATFORM_CODE_YEE_PAY, account.getInsideAccountNo()	);
					if("FROZEN".equals(res.getAccountStatus())){
						AccountInfoDto dto= new AccountInfoDto();
						BeanUtils.copyProperties(res, dto, ignoreProperties);
						
						dtoList.add(dto);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(dtoList.size()>0){
				YbAccountStatusAggregate accountStatusAggregate = new YbAccountStatusAggregate();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				accountStatusAggregate.setAccountList(dtoList);
				accountStatusAggregate.setQueryDate(sf.format(System.currentTimeMillis()));
				accountStatusAggregate.setQueryTime(System.currentTimeMillis());
				accountStatusAggregate.setId(java.util.UUID.randomUUID().toString());
				mongoService.save(accountStatusAggregate);
			}
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}
	@Override
	public List<String> queryAccountStatusFromYb (List<String> collectMoneyAccountList,List<AccountInfoDto> dtoList,boolean isDto){
		String ignoreProperties[] = {};
		
		List<String> accountNoList=new ArrayList<String>();
		
		for(String accountNo:collectMoneyAccountList){
			try{
			QueryAccountInfoResponseYB res = thirdPartyPaymentQueryManager.queryThirdPartyAccountInfo(PayDict.PLATFORM_CODE_YEE_PAY, accountNo);
				if("FROZEN".equals(res.getAccountStatus())){
					AccountInfoDto dto= new AccountInfoDto();
					BeanUtils.copyProperties(res, dto, ignoreProperties);
					if(isDto){
						dtoList.add(dto);
					}else{
						accountNoList.add(accountNo);
					}				
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return accountNoList;
	}
}
