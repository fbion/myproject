package com.sinoiov.upp.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.AccountSafetyMess;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.soa.ServiceFactory;
import com.google.code.morphia.query.Query;
import com.sinoiov.pay.utils.UPPConfigUtil;
import com.sinoiov.upp.bean.SecurityTicketInfo;
import com.sinoiov.upp.business.account.IAccountBusiness;
import com.sinoiov.upp.business.account.IAccountSafetyBusiness;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.service.dto.AccountSafetyDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service("accountSafetyService")
public class AccountSafetyServiceImpl extends AbstractService implements IAccountSafetyService {

	private IAccountSafetyBusiness accountSafetyBusiness;

	private IAccountBusiness accountBusiness;

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("mongoService")
	private IMongoService mongoService;

	@Autowired
	private ISmsCodeService smsCodeService;

	private AccountSafetyServiceImpl() {
		accountSafetyBusiness = (IAccountSafetyBusiness) ServiceFactory.getFactory().getService(
				IAccountSafetyBusiness.class);
		accountBusiness = (IAccountBusiness) ServiceFactory.getFactory().getService(IAccountBusiness.class);
	}

	/**
	 * 把safetyDto对象中属性复制到safetyMess
	 */
	private void copyAccountSafety(AccountSafetyDto safetyDto, AccountSafetyMess safetyMess) throws UPPServiceException {
		if (safetyMess == null || safetyDto == null)
			throw new UPPServiceException(ReturnCodeDict.IS_NULL, "账户安全问题对象不能为null");

		String[] ignoreProperties = { "safeLevel" };
		BeanUtils.copyProperties(safetyDto, safetyMess, ignoreProperties);
		safetyMess.setSafeLevel(StringUtils.isBlank(safetyDto.getSafeLevel()) ? 0 : Long.valueOf(safetyDto
				.getSafeLevel()));
		// 其它值
	}

	private String getAccountIdByAccountNo(String accountNo) throws Exception {
		AccountDto accountDto = accountBusiness.getAccountByAccountNo(accountNo);
		return accountDto == null ? "" : accountDto.getId();
	}

	/**
	 * 验证并删除短信验证码
	 * 
	 * @param mobileNo
	 * @param smsCodeNeedVefify
	 * @throws UPPServiceException
	 */
	private void verifyAndDeleteSmsCode(String mobileNo, String smsCodeNeedVefify) throws UPPServiceException {
		// 从Mongo中查询对应的验证码
		String smsCodeInMogo = smsCodeService.getSmsCodeByMoblieNo(mobileNo);
		if (!smsCodeNeedVefify.equals(smsCodeInMogo)) {
			// smsCodeNeedVefify为空的验证已在controller层做了验证
			throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "短信验证码不正确");
		}
		// 删除验证码
		smsCodeService.removeSmsCodeByMoblieNo(mobileNo);
	}

	/**
	 * 验证并删除securityTicket
	 * 
	 * @param accountNo
	 * @param securityTicket
	 * @throws UPPServiceException
	 */
	@SuppressWarnings("unchecked")
	private void verifyAndDeleteSecurityTicket(String accountNo, String securityTicket) throws UPPServiceException {
		try {
			Query<SecurityTicketInfo> query = mongoService.getQuery(SecurityTicketInfo.class);
			query.field("accountNo").equal(accountNo);
			List<SecurityTicketInfo> lst = mongoService.query(SecurityTicketInfo.class, query);

			if (lst == null || lst.size() != 1) {
				throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "账户[" + accountNo + "]操作未授权");// TODO
			}

			SecurityTicketInfo securityTicketInfoDB = lst.get(0);
			if (!securityTicket.equals(securityTicketInfoDB.getSecurityTicket())) {
				throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "操作未授权");// TODO
			}

			// 注:因为SecurityTicketInfo记录id是自动生成的，所以这里需要new ObjectId
			mongoService.delete(SecurityTicketInfo.class, new ObjectId(securityTicketInfoDB.getId()));
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (Exception e) {
			logger.error("从mongo中删除securityTicket时异常", e);
			throw new UPPServiceException("从mongo中删除securityTicket时异常");
		}
	}

	@Override
	public String addAccountSafetyMess(AccountSafetyDto safetyDto) throws UPPServiceException {
		try {
			this.verifyAndDeleteSecurityTicket(safetyDto.getAccountNo(), safetyDto.getSecurityTicket());

			AccountSafetyMess safetyMess = new AccountSafetyMess();
			this.copyAccountSafety(safetyDto, safetyMess);

			safetyMess.setAccountId(this.getAccountIdByAccountNo(safetyDto.getAccountNo()));
			return accountSafetyBusiness.createAccountSafetyMess(safetyMess);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("增加账户安全问题异常", e);
			throw new UPPServiceException("增加账户安全问题异常");
		}
	}

	@Override
	public boolean modifyAccountSafetyMess(AccountSafetyDto safetyDto) throws UPPServiceException {
		try {
			this.verifyAndDeleteSecurityTicket(safetyDto.getAccountNo(), safetyDto.getSecurityTicket());

			String accountId = this.getAccountIdByAccountNo(safetyDto.getAccountNo());
			AccountSafetyMess accountSafetyMessDB = accountSafetyBusiness.getSecurityQuestionByAccountId(accountId);

			this.copyAccountSafety(safetyDto, accountSafetyMessDB);

			accountSafetyBusiness.modifyAccountSafetyMess(accountSafetyMessDB);
			return true;
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("修改账户安全问题异常", e);
			throw new UPPServiceException("修改账户安全问题异常");
		}
	}

	@Override
	public boolean resetAccountSafetyByAccountNo(String accountNo) throws UPPServiceException {
		try {
			AccountSafetyMess safetyMess = new AccountSafetyMess();
			safetyMess.setAccountId(this.getAccountIdByAccountNo(accountNo));

			return accountSafetyBusiness.resetAccountSafetyMess(safetyMess);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("重置账户安全问题异常", e);
			throw new UPPServiceException("重置账户安全问题异常");
		}
	}

	@Override
	public String querySecurityQuestion(String accountNo) throws UPPServiceException {
		try {
			String questions = "";
			AccountDto accountDto = accountBusiness.getAccountByAccountNo(accountNo);
			if (accountDto == null) {
				throw new UPPServiceException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "账户[" + accountNo + "]不存在");
			}
			AccountSafetyMess accountSafetyMess = accountSafetyBusiness.getSecurityQuestionByAccountId(accountDto
					.getId());

			if (accountSafetyMess != null) {
				questions = accountSafetyMess.getSecurityQuestion1() + "," + accountSafetyMess.getSecurityQuestion2()
						+ "," + accountSafetyMess.getSecurityQuestion3();
			}
			return questions;
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("查询账户安全信息时异常", e);
			throw new UPPServiceException("查询账户安全信息时异常");
		}
	}

	@Override
	public String validate(String accountNo, String mobileNo, String smsCode, String payPassword,
			String securityQuestionNeedVerify, String securityAnswerNeedVerify) throws UPPServiceException {
		try {
			if (StringUtils.isNotBlank(mobileNo) && StringUtils.isNotBlank(smsCode))
				this.verifyAndDeleteSmsCode(mobileNo, smsCode);

			if (StringUtils.isNotBlank(payPassword)) {
				boolean result = accountBusiness.isPayPassword(accountNo, payPassword);
				if (!result){
					AccountDto dto = accountBusiness.getAccountByAccountNo(accountNo);
					int errorLimit = Integer.parseInt(UPPConfigUtil.getValue("PAY_PASSWORD_ERROR_NUMBER"));
					int error = StringUtils.isBlank(dto.getPwdErrorCount())?0:Integer.parseInt(dto.getPwdErrorCount().trim());
					throw new UPPServiceException(ReturnCodeDict.PAY_PASSWORD_ERROR, "支付密码错误, 还可输入"+(errorLimit-error)+"次");
				}
					
			}

			if (StringUtils.isNotBlank(securityQuestionNeedVerify) && StringUtils.isNotBlank(securityAnswerNeedVerify)) {
				AccountSafetyMess safetyMess = new AccountSafetyMess();
				String accountId = this.getAccountIdByAccountNo(accountNo);
				safetyMess.setAccountId(accountId);
				// 判断需要验证的是3个问题中的哪个问题
				AccountSafetyMess dbAccountSafetyMess = accountSafetyBusiness.getSecurityQuestionByAccountId(accountId);
				if (dbAccountSafetyMess == null)
					throw new UPPServiceException(ReturnCodeDict.SAFET_QUERY_ANSWER_ERROR, "用户未设置过账户安全问题");

				if (dbAccountSafetyMess.getSecurityQuestion1().equals(securityQuestionNeedVerify)) {
					safetyMess.setSecurityQuestion1(securityQuestionNeedVerify);
					safetyMess.setSecurityAnswer1(securityAnswerNeedVerify);
				} else if (dbAccountSafetyMess.getSecurityQuestion2().equals(securityQuestionNeedVerify)) {
					safetyMess.setSecurityQuestion2(securityQuestionNeedVerify);
					safetyMess.setSecurityAnswer2(securityAnswerNeedVerify);
				} else {
					safetyMess.setSecurityQuestion3(securityQuestionNeedVerify);
					safetyMess.setSecurityAnswer3(securityAnswerNeedVerify);
				}

				boolean result = accountSafetyBusiness.checkAccountSafetyMess(safetyMess);
				if (!result){
					int error = StringUtils.isBlank(dbAccountSafetyMess.getAnswerErrorCount())?0:Integer.parseInt(dbAccountSafetyMess.getAnswerErrorCount().trim());
					int errorLimit = Integer.parseInt(UPPConfigUtil.getValue("PAY_QUESTION_ERROR_NUMBER").trim());
					throw new UPPServiceException(ReturnCodeDict.SAFET_QUERY_ANSWER_ERROR, "安全问题答案不正确,还可回答"+(errorLimit - error -1)+"次");
				}		
			}

			// 随机生成新的不重复的securityTicket，保存到MongDB中，并删除之前对应账户的ticket
			return this.generateNewSecurityTicket(accountNo);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("验证账户相关各种信息(短信\\支付密码\\单个安全问题)时异常", e);
			throw new UPPServiceException("验证账户相关各种信息(短信\\支付密码\\单个安全问题)时异常");
		}
	}

	/**
	 * 随机生成不重复的securityTicket，并保存到MongDB中
	 * 
	 * @return securityTicket
	 */
	@SuppressWarnings("unchecked")
	private String generateNewSecurityTicket(String accountNo) throws UPPServiceException {
		String uuid = null;
		try {
			Query<SecurityTicketInfo> query = mongoService.getQuery(SecurityTicketInfo.class);
			query.field("accountNo").equal(accountNo);
			List<SecurityTicketInfo> lst = mongoService.query(SecurityTicketInfo.class, query);

			if (lst != null && lst.size() == 1) {
				// 删除旧的ticket
				SecurityTicketInfo securityTicketInfoDB = lst.get(0);
				// 注:因为SecurityTicketInfo记录id是自动生成的，所以这里需要new ObjectId
				mongoService.delete(SecurityTicketInfo.class, new ObjectId(securityTicketInfoDB.getId()));
			}

			SecurityTicketInfo securityTicketInfo = new SecurityTicketInfo();
			uuid = UUID.randomUUID().toString();
			securityTicketInfo.setAccountNo(accountNo);
			securityTicketInfo.setSecurityTicket(uuid);
			mongoService.save(securityTicketInfo);
		} catch (Exception e) {
			logger.error("保存securityTicket到MongDB中时报错");
			throw new UPPServiceException("保存securityTicket到MongDB中时报错");
		}
		return uuid;
	}

}
