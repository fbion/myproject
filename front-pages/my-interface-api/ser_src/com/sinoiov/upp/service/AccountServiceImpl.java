package com.sinoiov.upp.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.soa.ServiceFactory;
import com.google.code.morphia.query.Query;
import com.sinoiov.upp.bean.SecurityTicketInfo;
import com.sinoiov.upp.business.account.IAccountBusiness;
import com.sinoiov.upp.service.dto.AccountDetailDto;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service("accountService")
public class AccountServiceImpl extends AbstractService implements IAccountService {

	@Autowired
	private ISmsCodeService smsCodeService;

	private IAccountBusiness accountBusiness;

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("mongoService")
	private IMongoService mongoService;

	private AccountServiceImpl() {
		accountBusiness = (IAccountBusiness) ServiceFactory.getFactory().getService(IAccountBusiness.class);
	}

	/**
	 * 验证并删除短信验证码
	 * 
	 * @param mobileNo
	 * @param smsCodeNeedVefify
	 * @throws UPPServiceException
	 */
	private void verifyAndDeleteSmsCode(String mobileNo, String smsCodeNeedVefify) throws UPPServiceException {
		// 从mogo中查询对应的验证码
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
				throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "账户[" + accountNo
						+ "]存在多余的securityTicket");// TODO
			}

			SecurityTicketInfo securityTicketInfoDB = lst.get(0);
			if (!securityTicket.equals(securityTicketInfoDB.getSecurityTicket())) {
				throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "securityTicket不正确");// TODO
			}

			// 注:因为SecurityTicketInfo记录id是自动生成的，所以这里需要new ObjectId
			mongoService.delete(SecurityTicketInfo.class, new ObjectId(securityTicketInfoDB.getId()));
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (Exception e) {
			logger.error("从mongo中删除securityTicket时异常", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "从mongo中删除securityTicket时异常");
		}
	}

	@Override
	public String createAccount(AccountDto accountDto) throws Exception {
		try {
			accountDto = accountBusiness.createAccount(accountDto);
			return (accountDto == null) ? "" : accountDto.getInsideAccountNo();
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("创建账户时异常", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "创建账户时异常");
		}
	}

	@Override
	public String createAccount(String smsCode, AccountDto accountDto) throws Exception {
		try {
			
			this.verifyAndDeleteSmsCode(accountDto.getMobileNo(), smsCode);
			return this.createAccount(accountDto);
			
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("创建账户时异常", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "创建账户时异常");
		}
	}

	@Override
	public AccountDto getAccountByAccountNo(String accountNo) throws Exception {
		try {
			return accountBusiness.getAccountByAccountNo(accountNo);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(String.format("根据账号[%s]查询账户信息时产生错误，原因：" + e.getLocalizedMessage(), accountNo), e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, String.format(
					"根据账号[%s]查询账户信息时产生错误，原因：" + e.getLocalizedMessage(), accountNo));
		}
	}

	@Override
	public AccountDto getAccountByUserId(String userId) throws Exception {
		try {
			return accountBusiness.getAccountByUserId(userId);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(String.format("根据用户ID[%s]查询账户信息时产生错误，原因：" + e.getLocalizedMessage(), userId), e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, String.format(
					"根据用户ID[%s]查询账户信息时产生错误，原因：" + e.getLocalizedMessage(), userId));
		}
	}

	@Override
	public void setPayPassword(String accountNo, String firstMD5) throws Exception {
		try {
			accountBusiness.modifyPayPassword(accountNo, firstMD5);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("设置支付密码异常", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "设置支付密码异常");
		}
	}

	@Override
	public void setPayPassword(String accountNo, String firstMD5, String mobileNo, String smsCode) throws Exception {
		try {
			this.verifyAndDeleteSmsCode(mobileNo, smsCode);
			accountBusiness.modifyPayPassword(accountNo, firstMD5);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("设置支付密码异常", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "设置支付密码异常");
		}
	}

	@Override
	public void setPayPassword(String accountNo, String firstMD5, String securityTicket) throws Exception {
		try {
			this.verifyAndDeleteSecurityTicket(accountNo, securityTicket);
			accountBusiness.modifyPayPassword(accountNo, firstMD5);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("设置支付密码异常", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "设置支付密码异常");
		}
	}

	@Override
	public void modifyPayPassword(String accountNo, String oldMD5, String newMD5, String mobileNo, String smsCode) throws Exception {
		try {
			this.verifyAndDeleteSmsCode(mobileNo, smsCode);
			accountBusiness.modifyPayPassword(accountNo, oldMD5, newMD5);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		}catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(String.format("修改账号[%s]交易密码失败，原因：" + e.getLocalizedMessage(), accountNo), e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, String.format(
					"修改账号[%s]交易密码失败，原因：" + e.getLocalizedMessage(), accountNo));
		}
	}

	@Override
	public void modifyPayPasswordByTicket(String accountNo, String newPassword, String securityTicket) throws Exception {
		try {
			if (StringUtils.isNotBlank(securityTicket)) {
				this.verifyAndDeleteSecurityTicket(accountNo, securityTicket);
			}// 如果值为空，则是支付后台调用此接口，不需要验证ticket
			accountBusiness.modifyPayPassword(accountNo, newPassword);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(String.format("根据安全票据修改账号[%s]交易密码失败，原因：" + e.getLocalizedMessage(), accountNo), e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, String.format(
					"根据安全票据修改账号[%s]交易密码失败，原因：" + e.getLocalizedMessage(), accountNo));
		}
	}

	@Override
	public boolean isPayPassword(String accountNo, String smsCode, String mobileNo, String MD5) throws Exception {
		try {
			this.verifyAndDeleteSmsCode(mobileNo, smsCode);
			return accountBusiness.isPayPassword(accountNo, MD5);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("验证支付密码异常", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "验证支付密码异常");
		}
	}

	@Override
	public boolean isSetPayPassword(String accountNo) throws Exception {
		try {
			return accountBusiness.isSetPayPassword(accountNo);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("验证是否设置过支付密码异常", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "验证是否设置过支付密码异常");
		}
	}

	@Override
	public void lockAccount(String accountNo, String lockReasons) throws Exception {
		try {
			accountBusiness.lockAccount(accountNo, lockReasons);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(String.format("根据账号[%s]锁定账户失败，原因：", accountNo), e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, String.format("根据账号[%s]锁定账户失败，原因：", accountNo));
		}
	}

	@Override
	public void unLockAccount(String accountNo) throws Exception {
		try {
			accountBusiness.unLockAccount(accountNo);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error(String.format("根据账号[%s]解锁账户失败，原因：", accountNo), e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, String.format("根据账号[%s]解锁账户失败，原因：", accountNo));
		}
	}

	@Override
	public PaginationResult<AccountDetailDto> queryAccountDetailByPage(DynamicSqlParameter parameter) throws Exception {
		try {
			return accountBusiness.queryAccountDetailByPage(parameter);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("根据账号查询账户流水信息失败，原因：", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "根据账号查询账户流水信息失败，原因：");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getAccountDetailById(String recordId) throws Exception {
		try {
			AccountDetailDto dto = accountBusiness.getAccountDetailById(recordId);

			Map<String, String> map = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(dto), Map.class);

			return map;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("根据账号查询账户流水信息失败，原因：", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "根据账号查询账户流水信息失败，原因：");
		}
	}

	@Override
	public PaginationResult<AccountDto> queryAccountByPage(DynamicSqlParameter parameter) throws Exception {
		try {
			return accountBusiness.queryAccountByPage(parameter);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("分页查询账户信息时报错", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "分页查询账户信息时报错");
		}
	}

	@Override
	public List<AccountDto> queryAccountList(DynamicSqlParameter parameter) throws Exception {
		try {
			return accountBusiness.queryAccountList(parameter);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("查询账户列表时报错", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "查询账户列表时报错");
		}
	}

	@Override
	public void revokeAccount(String accountNo) throws Exception {
		try {
			accountBusiness.revokeAccount(accountNo);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("注销账户时报错", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "注销账户时报错");
		}
	}

	@Override
	public List<AccountDetailDto> queryAccountDetailList(DynamicSqlParameter parameter) throws Exception {
		try {
			return accountBusiness.queryAccountDetailList(parameter);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("分页查询账户流水时报错", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "分页查询账户流水时报错");
		}
	}

	@Override
	public void modifyMobileNo(String accountNo, String mobileNo, String securityTicket, String smsCode)
			throws Exception {
		try {
			if (StringUtils.isNotBlank(securityTicket) && StringUtils.isNotBlank(smsCode)) {
				// 非支付后台调用此接口时，则进行验证
				this.verifyAndDeleteSecurityTicket(accountNo, securityTicket);
				this.verifyAndDeleteSmsCode(mobileNo, smsCode);
			}
			accountBusiness.modifyPayMobileNo(accountNo, mobileNo);
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("修改支付手机号时报错", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "修改支付手机号时报错");
		}
	}

	@Override
	public int countAccountDetail(DynamicSqlParameter parameter)
			throws Exception {
		try{
			return accountBusiness.countAccountDetail(parameter);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("统计账户流水数量失败，原因：", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "统计账户流水数量失败，原因：");
		}
	}

	@Override
	public int countOfAccount(DynamicSqlParameter parameter) throws Exception {
		try{
			return accountBusiness.countOfAccount(parameter);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getErrorCode(), ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("统计账户流水数量失败，原因：", e);
			throw new UPPServiceException(ReturnCodeDict.ERROR, "统计账户流水数量失败，原因：");
		}
	}
}
