package com.sinoiov.upp.business.account;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountSafetyMess;
import com.ctfo.upp.dict.AccountSafetyDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.root.utils.SmsSender;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.manager.account.IAccountSafetyManager;
import com.sinoiov.upp.util.DefaultConfig;

@Service("accountSafetyBusiness")
public class AccountSafetyBusinessImpl implements IAccountSafetyBusiness {
	
	private static final Log logger = LogFactory.getLog(AccountSafetyBusinessImpl.class);
			
	@Autowired
	@Qualifier("accountSafetyManager")
	private IAccountSafetyManager accountSafetyManager;
	
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;	
	
	private void checkData(AccountSafetyMess safetyMess) throws UPPException {
		if (StringUtils.isEmpty(safetyMess.getAccountId())) 
			throw new UPPException("账户ID不能为空");
		if (StringUtils.isEmpty(safetyMess.getSecurityQuestion1()))
			throw new UPPException("安全问题未完整");
		if (StringUtils.isEmpty(safetyMess.getSecurityAnswer1()))
			throw new UPPException("安全问题未完整");
		if (StringUtils.isEmpty(safetyMess.getSecurityQuestion2()))
			throw new UPPException("安全问题未完整");
		if (StringUtils.isEmpty(safetyMess.getSecurityAnswer2()))
			throw new UPPException("安全问题未完整");
		if (StringUtils.isEmpty(safetyMess.getSecurityQuestion3()))
			throw new UPPException("安全问题未完整");
		if (StringUtils.isEmpty(safetyMess.getSecurityAnswer3()))
			throw new UPPException("安全问题未完整");

	}

	@Override
	public String createAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException {	
		try{	
			this.checkData(safetyMess);
			
			AccountSafetyMess dbBean = accountSafetyManager.getAccountSafetyMessByAccountId(safetyMess
					.getAccountId());
			String uuid = "";
			if (dbBean == null) {
				safetyMess.setSafetyMessStatus(AccountSafetyDict.ACCOUNT_SAFETY_MESS_STATUS_NORMAL);
				uuid = accountSafetyManager.saveAccountSafetyMess(safetyMess);
			} else {
				uuid = dbBean.getId();
				safetyMess.setId(uuid);
				safetyMess.setSafetyMessStatus(AccountSafetyDict.ACCOUNT_SAFETY_MESS_STATUS_NORMAL);
				this.modifyAccountSafetyMess(safetyMess);
			}
			return uuid;
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("增加账户安全问题接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "增加账户安全问题接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public String modifyAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException {
		try{
			if(safetyMess==null)
				throw new UPPException(ReturnCodeDict.IS_NULL, "参数不能为空");
			if(StringUtils.isBlank(safetyMess.getId())){
				if(StringUtils.isBlank(safetyMess.getAccountId()))
					throw new UPPException(ReturnCodeDict.IS_NULL, "参数账户ID不能为空");
				AccountSafetyMess mess = accountSafetyManager.getAccountSafetyMessByAccountId(safetyMess.getAccountId());
				safetyMess.setId(mess.getId());
			}
			
			return accountSafetyManager.updateAccountSafetyMess(safetyMess);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("修改账户安全问题接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改账户安全问题接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public boolean resetAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException {
		try{
			
			 String is = accountSafetyManager.resetAccountSafetyMess(safetyMess);
			if(ReturnCodeDict.SUCCESS.equals(is)){
				//发送短信
				Account account = accountManager.getAccountById(safetyMess.getAccountId());
				 if(StringUtils.isNotBlank(account.getMobileNo())){
					 List<String> list = new ArrayList<String>();
					 SmsSender.getInstance().sendSmsByTemplate(account.getMobileNo(), "pay967052", list);				
				 }
				 return true;
			}			 
			 return false;		
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("重置账户安全问题接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "重置账户安全问题接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public boolean checkAccountSafetyMess(AccountSafetyMess safetyMess) throws UPPException {		
		try{
			if(StringUtils.isBlank(safetyMess.getAccountId()))
				throw new UPPException(ReturnCodeDict.IS_NULL, "账户ID不能为空");
			Account account = accountManager.getAccountById(safetyMess.getAccountId());
			if (account == null)
				throw new UPPException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "找不到［" + safetyMess.getAccountId() + "］对应的账户");			
			//限定错误次数
			int errorLimit = Integer.parseInt(DefaultConfig.getValue("PAY_QUESTION_ERROR_NUMBER"));
			AccountSafetyMess dbBean = accountSafetyManager.getAccountSafetyMessByAccountId(safetyMess.getAccountId());
			if (dbBean == null)
				throw new UPPException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "找不到［" + safetyMess.getAccountId() + "］对应的账户安全问题");
			
			int errorCount = dbBean.getAnswerErrorCount()==null?0:Integer.parseInt(dbBean.getAnswerErrorCount());
			//清除超过24小时的错误次数, 解锁
			if(dbBean.getAnswerErrorTime()!=null 
					&& errorCount > 0
					&& (dbBean.getAnswerErrorTime()+86400000) < System.currentTimeMillis()){
				dbBean.setAnswerErrorCount("0");
				//dbBean.setAnswerErrorTime(0l);
				accountSafetyManager.updateAccountSafetyMess(safetyMess);
				if(errorCount >= errorLimit){//判断是否是因错误次数锁定的账户
					accountManager.unlockAccount(account.getInsideAccountNo());
				}
			}
			
			if (PayDict.ACCOUNT_STATUS_L.equals(account.getAccountStatus()))
				throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED, "账户被锁定");

			String is = accountSafetyManager.checkAccountSafetyMess(safetyMess);
			if(ReturnCodeDict.SUCCESS.equals(is)){
				dbBean.setAnswerErrorCount("0");
				accountSafetyManager.updateAccountSafetyMess(safetyMess);
				return true;
			}else{
				//错误次数
				++errorCount;
				AccountSafetyMess update = new AccountSafetyMess();
				update.setId(dbBean.getId());
				update.setAnswerErrorCount(errorCount+"");
				update.setAnswerErrorTime(System.currentTimeMillis());
				this.modifyAccountSafetyMess(update);				
				if(errorCount >= errorLimit){
					//锁定
					accountManager.lockAccount(account.getInsideAccountNo(), "24小时内账户安全问题连续回答错误"+errorLimit+"次！");
					//发送短信
					String mobileNo = account==null?"":account.getMobileNo();
					if(StringUtils.isNotBlank(mobileNo))
						SmsSender.getInstance().sendSmsByTemplate(mobileNo, "tyzfpt1004", new ArrayList<String>());				
					
				}
			}	
				
			return false;
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("验证账户安全问题接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "验证账户安全问题接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public AccountSafetyMess getSecurityQuestionByAccountId(
			String accountId) throws UPPException {
		try{
			
			return accountSafetyManager.getAccountSafetyMessByAccountId(accountId);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据账户ID查询安全问题接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账户ID查询安全问题接口异常:"+e.getLocalizedMessage());
		}
	}

}
