package com.sinoiov.upp.manager.account;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.AccountSafetyMess;
import com.ctfo.account.dao.beans.AccountSafetyMessExampleExtended;
import com.ctfo.upp.dict.AccountSafetyDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.manager.AbstractManager;

@Service("accountSafetyManager")
public class AccountSafetyManagerImpl extends AbstractManager implements IAccountSafetyManager {

	private boolean checkSafetyAnswers(AccountSafetyMess sourceSafetyMess, AccountSafetyMess safetyMess) {
		
		if(StringUtils.isNotBlank(safetyMess.getSecurityQuestion1()) 
				&& StringUtils.isNotBlank(safetyMess.getSecurityQuestion2())
				&& StringUtils.isNotBlank(safetyMess.getSecurityQuestion3())
				){			
			return sourceSafetyMess.getSecurityAnswer1().equals(safetyMess.getSecurityAnswer1())
					&& sourceSafetyMess.getSecurityAnswer2().equals(safetyMess.getSecurityAnswer2())
					&& sourceSafetyMess.getSecurityAnswer3().equals(safetyMess.getSecurityAnswer3());
		}else if(StringUtils.isNotBlank(safetyMess.getSecurityQuestion1()) 
				&& StringUtils.isNotBlank(safetyMess.getSecurityQuestion2())){
			return sourceSafetyMess.getSecurityAnswer1().equals(safetyMess.getSecurityAnswer1())
					&& sourceSafetyMess.getSecurityAnswer2().equals(safetyMess.getSecurityAnswer2());
		}else if(StringUtils.isNotBlank(safetyMess.getSecurityQuestion1()) 
				&& StringUtils.isNotBlank(safetyMess.getSecurityQuestion3())){
			return sourceSafetyMess.getSecurityAnswer1().equals(safetyMess.getSecurityAnswer1())
				&& sourceSafetyMess.getSecurityAnswer3().equals(safetyMess.getSecurityAnswer3());
		}else if(StringUtils.isNotBlank(safetyMess.getSecurityQuestion2()) 
				&& StringUtils.isNotBlank(safetyMess.getSecurityQuestion3())){
			return sourceSafetyMess.getSecurityAnswer2().equals(safetyMess.getSecurityAnswer2())
				&& sourceSafetyMess.getSecurityAnswer3().equals(safetyMess.getSecurityAnswer3());
		}else if(StringUtils.isNotBlank(safetyMess.getSecurityQuestion1())){
			return sourceSafetyMess.getSecurityAnswer1().equals(safetyMess.getSecurityAnswer1());
		}else if(StringUtils.isNotBlank(safetyMess.getSecurityQuestion2())){
			return sourceSafetyMess.getSecurityAnswer2().equals(safetyMess.getSecurityAnswer2());
		}else if(StringUtils.isNotBlank(safetyMess.getSecurityQuestion3())){
			return sourceSafetyMess.getSecurityAnswer3().equals(safetyMess.getSecurityAnswer3());
		}
		
		return false;
	}


	@Override
	public String saveAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException {
		try {
			
			return this.addModel(safetyMess);
			
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public String updateAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException {
		try {
			int count = this.updateModelPart(safetyMess);
			if (count > 0) {
				return ReturnCodeDict.SUCCESS;
			}
			return ReturnCodeDict.FAIL;
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public String checkAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException {
		try {

			AccountSafetyMessExampleExtended exampleExtended = new AccountSafetyMessExampleExtended();
			exampleExtended.createCriteria().andAccountIdEqualTo(safetyMess.getAccountId());
			List<AccountSafetyMess> sourceList = this.getModels(exampleExtended);
			if (sourceList != null && sourceList.size() > 0) {
				AccountSafetyMess sourceSafetyMess = sourceList.get(0);

				if (this.checkSafetyAnswers(sourceSafetyMess, safetyMess)) {
					return ReturnCodeDict.SUCCESS;
				}
			}
			return ReturnCodeDict.FAIL;
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public String resetAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException {
		try {
			safetyMess = this.getAccountSafetyMessByAccountId(safetyMess.getAccountId());

			AccountSafetyMess newSafetyMess = new AccountSafetyMess();
			newSafetyMess.setId(safetyMess.getId());
			newSafetyMess.setSafetyMessStatus(AccountSafetyDict.ACCOUNT_SAFETY_MESS_STATUS_RESETTING);
			int count = this.updateModelPart(newSafetyMess);
			if (count > 0) {
				return ReturnCodeDict.SUCCESS;
			}
			return ReturnCodeDict.FAIL;
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public AccountSafetyMess getAccountSafetyMessByAccountId(String accountId) throws UPPException {
		try {
			AccountSafetyMessExampleExtended exampleExtended = new AccountSafetyMessExampleExtended();
			exampleExtended.createCriteria().andAccountIdEqualTo(accountId).andSafetyMessStatusNotEqualTo(AccountSafetyDict.ACCOUNT_SAFETY_MESS_STATUS_RESETTING);
			List<AccountSafetyMess> sourceList = this.getModels(exampleExtended);
			if (sourceList != null && sourceList.size() > 0) {
				return sourceList.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

}
