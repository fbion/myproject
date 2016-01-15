package com.ctfo.upp.accountservice.payment.internal.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.payment.dto.beans.SmsSendHistoryRecord;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.Sms;
import com.ctfo.upp.root.utils.SmsSender;
import com.ctfo.upp.service.smscode.CommonBaseManager;
import com.sinoiov.upp.manager.account.IAccountManager;

//@Service("commonBaseManager")
public class CommonBaseManagerImpl implements CommonBaseManager{
	static private final Log logger = LogFactory.getLog(CommonBaseManagerImpl.class);
	
	
	@Autowired
	@Qualifier("accountManagerService")
	private IAccountManager internalAccountManager;
	
	@Autowired
	@Qualifier("mongoService") 
	private IMongoService mongoService;
	
	public boolean sendShortMessByAccountNo(String accountNo,String templetNo,List<String> contents) throws UPPException{
		Account account = internalAccountManager.getAccountByAccountNo(accountNo);
		
		return this.sendShortMess(account.getMobileNo(), templetNo, contents);
	}
	public boolean sendShortMess(String mobileNo,String templetNo,List<String> contents) throws UPPException{
		
		String text = this.getSmsText(templetNo);
		if("".equals(text)){
			return false;
		}
		if(StringUtils.isNotBlank(text)){
			for(String content:contents){
				text = text.replaceFirst("<_>", content);
			}
		}	
		String sendBackCode = SmsSender.getInstance().sendSms(mobileNo, text);
		logger.info("发送短信验证码:" + mobileNo+"["+text+"], sendBackCode:["+sendBackCode+"]");
		
		try {mongoService.save(new SmsSendHistoryRecord(mobileNo,text));} catch (Exception e) {logger.error("存放短信发送历史失败！"+e.getMessage(),e);}
		return true;
		
	}
	public String getSmsText(String oid) throws UPPException {
		try {
			
			Sms sms = (Sms)mongoService.get(Sms.class, oid);
			if(sms == null || sms.getStatus()!=1){
				return "";
			}
			return sms.getTemplateContent();
			
		} catch (Exception e) {
			logger.error("按ID查询短信验证码失败！");
			throw new UPPException("按ID查询短信验证码失败！", e);
		}
	}
}
