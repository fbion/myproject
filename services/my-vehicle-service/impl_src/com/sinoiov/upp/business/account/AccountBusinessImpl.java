package com.sinoiov.upp.business.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountDetail;
import com.ctfo.account.dao.beans.AccountDetailExampleExtended;
import com.ctfo.account.dao.beans.AccountDetailExampleExtended.Criteria;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.account.dao.beans.AccountSafetyMess;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.accountservice.utils.RandomUtil;
import com.ctfo.upp.dict.AccountSafetyDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.root.utils.MD5Util;
import com.ctfo.upp.root.utils.SmsSender;
import com.ctfo.upp.root.utils.TimeHandleUtil;
import com.sinoiov.upp.business.AbstractBusiness;
import com.sinoiov.upp.manager.account.IAccountDetailManager;
import com.sinoiov.upp.manager.account.IAccountHistoryManager;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.manager.account.IAccountSafetyManager;
import com.sinoiov.upp.manager.payment.IOrderManager;
import com.sinoiov.upp.service.dto.AccountDetailDto;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.util.DefaultConfig;

@Service("accountBusiness")
public class AccountBusinessImpl extends AbstractBusiness implements IAccountBusiness {

	private static final Log logger = LogFactory.getLog(AccountBusinessImpl.class);
	
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;	
	@Autowired
	@Qualifier("accountDetailManager")
	private IAccountDetailManager accountDetailManager;
	@Autowired
	@Qualifier("orderManager")
	private IOrderManager orderManager;
	@Autowired
	@Qualifier("accountSafetyManager")
	private IAccountSafetyManager accountSafetyManager;
	@Autowired
	@Qualifier("accountHistoryManager")
	private IAccountHistoryManager accountHistoryManager;
	
	//根据用户ID和手机号，验证账户是否存在
	private boolean isAccount(String userId, String mobileNo)throws Exception{
		AccountExampleExtended exampleExtended = new AccountExampleExtended();
		exampleExtended.createCriteria().andOwnerUserIdEqualTo(userId);
		exampleExtended.or(exampleExtended.createCriteria().andMobileNoEqualTo(mobileNo));
		int count = accountManager.countAccount(exampleExtended);
		return count<1 ? false : true;
	}
	
	private List<AccountDetailDto> coptAccountDetail(Collection<AccountDetail> collection) throws Exception {
		if (collection == null || collection.size() == 0)
			return null;
		
		List<AccountDetailDto> temlist = new ArrayList<AccountDetailDto>();
		AccountDetailDto dto = null;
		for (AccountDetail detail : collection) {
			dto = new AccountDetailDto();
			copyAccountDetailProperties(detail, dto);
			temlist.add(dto);
		}
		
//		List<Runnable> tasks = new ArrayList<Runnable>();
//		final List<AccountDetailDto> temlist = new ArrayList<AccountDetailDto>();
//		for (final AccountDetail detail : collection) {
//			final AccountDetailDto dto = new AccountDetailDto();
//			Runnable task = new Runnable() {
//				@Override
//				public void run() {
//					try {					
//						copyAccountDetailProperties(detail, dto);
//						temlist.add(dto);
//					} catch (Exception e) {
//						logger.error("多线程组装参数异常：" + e);
//					}
//				}
//			};
//			tasks.add(task);
//		}
//		TaskPool.completeTask(tasks);

		return temlist;
	}
	
	/**
	 * 转换对象(把accountDetail属性值复制到AccountDetailDto)
	 * 
	 * @param accountDetail
	 * @param AccountDetailDto
	 */
	private void copyAccountDetailProperties(AccountDetail accountDetail, AccountDetailDto accountDetailDto) throws Exception {
		if(accountDetailDto==null)throw new Exception("目标对象不能为 null");
		
		if(accountDetail!=null){
			accountDetailDto.setId(accountDetail.getId());
			accountDetailDto.setInsideAccountNo(accountDetail.getInsideAccountNo());
			accountDetailDto.setTradeExternalNo(accountDetail.getTradeExternalNo());
			accountDetailDto.setAccountDate(accountDetail.getAccountDate());
			accountDetailDto.setAccountTime(accountDetail.getAccountTime() + "");
			accountDetailDto.setBookAccountTime(accountDetail.getAccountTime() + "");
			accountDetailDto.setBookAccountType(accountDetail.getBookaccountType());
			accountDetailDto.setBookAccountMoney(accountDetail.getAccountMoney()==null?"":AmountUtil.getAmount(accountDetail.getAccountMoney()));// 交易金额
			accountDetailDto.setBookCurrentMoney(accountDetail.getCurrentMoney()==null?"":AmountUtil.getAmount(accountDetail.getCurrentMoney()));// 交易后余额
			//accountDetailDto.setOrderTypeLuc(accountDetail.getAccountSubject());
		}
		
		// 订单信息
		if(StringUtils.isNotBlank(accountDetail.getOrderId())){
			OrderInfo order = orderManager.getOrderInfoById(accountDetail.getOrderId());
			if(order!=null){
				accountDetailDto.setOrderId(order.getId());
				accountDetailDto.setOrderNo(order.getOrderNo());
				accountDetailDto.setOrderType(order.getOrderType());
				accountDetailDto.setPayChannel(StringUtils.isBlank(order.getPayChannel())?"ACCOUNT":order.getPayChannel());
				accountDetailDto.setProductName(order.getProductName());
				accountDetailDto.setOrderRemarks(order.getRemarks());
				accountDetailDto.setOrderTypeLuc(StringUtils.isBlank(order.getOrderSubject()) ? accountDetail.getAccountSubject() : order.getOrderSubject());
			}
		}		
		//账户信息
		if(StringUtils.isNotBlank(accountDetail.getAccountId())){
			Account account = accountManager.getAccountById(accountDetail.getAccountId());
			accountDetailDto.setOwnerLoginName(account==null?"":account.getOwnerLoginName());
			accountDetailDto.setOwnerUserNo(account.getOwnerUserNo());
		}
	}
	
	/**
	 * 转换对象(把Account属性值复制到AccountDto)
	 * 
	 * @param account
	 * @param accountDto
	 */
	private void copyAccountProperties(Account account, AccountDto accountDto) throws Exception {
		String[] ignoreProperties = {"payPassword", "partShowIdcardNo", "isOperMess", "version", "createTime",
				"usableBalance", "frozenBalance", "unableTakecashBalance", "totalBalance", "pwdErrorCount",
				"pwdErrorTime" };
		BeanUtils.copyProperties(account, accountDto, ignoreProperties);
		accountDto.setAccountNo(account.getInsideAccountNo());
		accountDto.setTotalBalance(account.getTotalBalance() == null ? "0.00" : AmountUtil.getAmount(account
				.getTotalBalance()));
		accountDto.setUsableBalance(account.getUsableBalance() == null ? "0.00" : AmountUtil.getAmount(account
				.getUsableBalance()));
		accountDto.setUnableTakecashBalance(account.getUnableTakecashBalance() == null ? "0.00" : AmountUtil
				.getAmount(account.getUnableTakecashBalance()));
		accountDto.setFrozenBalance(account.getFrozenBalance() == null ? "0.00" : AmountUtil.getAmount(account
				.getFrozenBalance()));
		accountDto.setCreateTime(account.getCreateTime() == null ? "" : TimeHandleUtil.longToDate(
				account.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		accountDto.setAccountSafetyLevel(getAccountSafetyLevel(account.getId(), account.getPayPassword()));
		accountDto.setPwdErrorCount(account.getPwdErrorCount());
	}
	private String getAccountSafetyLevel(String accountId, String payPassword)throws Exception{		
		AccountSafetyMess tem = accountSafetyManager.getAccountSafetyMessByAccountId(accountId);
		if((!payPassword.equals(MD5Util.getEncryptedPwd(DefaultConfig.getValue("INIT_PAYPWD"))))
				&& tem !=null && AccountSafetyDict.ACCOUNT_SAFETY_MESS_STATUS_NORMAL.equals(tem.getSafetyMessStatus())
				){
			return "2";
		}else if((!payPassword.equals(MD5Util.getEncryptedPwd(DefaultConfig.getValue("INIT_PAYPWD"))))
				&& (tem ==null || (!AccountSafetyDict.ACCOUNT_SAFETY_MESS_STATUS_NORMAL.equals(tem.getSafetyMessStatus())))
				){
			return "1";
		}else if(payPassword.equals(MD5Util.getEncryptedPwd(DefaultConfig.getValue("INIT_PAYPWD")))
			&& tem !=null && AccountSafetyDict.ACCOUNT_SAFETY_MESS_STATUS_NORMAL.equals(tem.getSafetyMessStatus())
			){
			return "1";
		}
		return "0";
	}
	private void copyAccountDtoProperties(AccountDto accountDto, Account account) throws Exception {
		String[] ignoreProperties = {"partShowIdcardNo", "version", "createTime",
				"usableBalance", "frozenBalance", "unableTakecashBalance", "totalBalance" };
		BeanUtils.copyProperties(accountDto, account, ignoreProperties);
		String accountType = StringUtils.isBlank(accountDto.getAccountType()) ? PayDict.ACCOUNT_TYPE_GOODS_OWNER
				: accountDto.getAccountType();
		account.setAccountType(accountType);
		account.setFrozenBalance(StringUtils.isBlank(accountDto.getFrozenBalance()) ? 0 : AmountUtil
				.getAmount(accountDto.getFrozenBalance()));
		account.setUsableBalance(StringUtils.isBlank(accountDto.getUsableBalance()) ? 0 : AmountUtil
				.getAmount(accountDto.getUsableBalance()));
		account.setOwnerUserNo(StringUtils.isNotBlank(accountDto.getOwnerUserNo())?accountDto.getOwnerUserNo():this.getOwnerUserNo(accountDto.getOwnerUserId()));
	}
	private List<AccountDto> coptAccount(Collection<Account> collection) throws Exception {
		if (collection == null || collection.size() == 0)
			return null;
		List<AccountDto> temlist = new ArrayList<AccountDto>();
		AccountDto dto = null;
		for(Account account : collection){
			dto = new AccountDto();			
			copyAccountProperties(account, dto);
			temlist.add(dto);		
		}
		return temlist;
	}
	
	//设置账户流水查询条件
	private AccountDetailExampleExtended AdapterAccountDetailExampleExtended(DynamicSqlParameter requestParam)throws Exception{
		
		String accountType = requestParam.getEqual()==null || requestParam.getEqual().get("accountType")==null?"":requestParam.getEqual().remove("accountType");
		String ownerUserNo = requestParam.getLike()==null || requestParam.getLike().get("ownerUserNo")==null?"":requestParam.getLike().remove("ownerUserNo");
		AccountDetailExampleExtended exampleExtended = new AccountDetailExampleExtended();			
		AccountDetailExampleExtended.Criteria criteria = (Criteria) Converter.paramToExampleExtendedCriteria(requestParam, exampleExtended);			
		if(StringUtils.isNotBlank(accountType) || StringUtils.isNotBlank(ownerUserNo)){
			AccountExampleExtended temEE = new AccountExampleExtended();
			temEE.setSelectedField(Account.fieldId());
			AccountExampleExtended.Criteria temc = temEE.createCriteria();
			if(StringUtils.isNotBlank(accountType))
				temc.andAccountTypeEqualTo(accountType);
			if(StringUtils.isNotBlank(ownerUserNo))
				temc.andOwnerUserNoLike(ownerUserNo);
			criteria.andAccountIdIn(Arrays.asList(temEE));
		}
		return exampleExtended;
	}
	
	//设置账户流水查询条件
	private AccountExampleExtended AdapterAccountExampleExtended(DynamicSqlParameter requestParam)throws Exception{		
		//兼容
		//this.AdapterDynamicSqlParameter(requestParam);
		String accountNo = requestParam.getEqual()!=null &&  requestParam.getEqual().get("accountNo")!=null?
				requestParam.getEqual().remove("accountNo"):"";
		if(StringUtils.isNotBlank(accountNo)){
			requestParam.getEqual().put("insideAccountNo", accountNo);
		}
		
		AccountExampleExtended exampleExtended = new AccountExampleExtended();			
		Converter.paramToExampleExtended(requestParam, exampleExtended);
		//AccountExampleExtended.Criteria criteria = (Criteria) Converter.paramToExampleExtendedCriteria(requestParam, exampleExtended);			
		
		return exampleExtended;
	}
	
	/**
	 * 1.创建主账户
	 * 2.创建子账户(暂不实现)
	 */
	@Override
	public AccountDto createAccount(AccountDto accountDto) throws UPPException {
		try{
			
			//检查重复账户
			if (this.isAccount(accountDto.getOwnerUserId(), accountDto.getMobileNo()))
				throw new UPPException(ReturnCodeDict.ACCOUNT_CREATE_R, "用户ID或手机号码已经在支付系统创建了账户");			
			Account account = new Account();
			this.copyAccountDtoProperties(accountDto, account);			
			//支付密码处理
			String payPassword = StringUtils.isBlank(account.getPayPassword())?MD5Util.getEncryptedPwd(DefaultConfig.getValue("INIT_PAYPWD")):account.getPayPassword();
			payPassword = MD5Util.getEncryptedPwd(payPassword);//二次md5加密
			account.setPayPassword(payPassword);
			//默认为发送短信
			account.setIsOperMess(StringUtils.isBlank(account.getIsOperMess())?DefaultConfig.getValue("IS_MESS_Y"):account.getIsOperMess());//是否发送消息			
			//生成账号
			String insideAccountNo = String.format("%s%s%s", "MA", System.currentTimeMillis(), RandomUtil.buildThreeBitRandomNumber());
			account.setInsideAccountNo(insideAccountNo);			
			account = accountManager.createAccount(account);
			this.copyAccountProperties(account, accountDto);

			return accountDto;
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建账户服务接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "创建账户服务接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public AccountDto getAccountByAccountNo(String accountNo) throws UPPException {
		try{			
			Account account = accountManager.getAccountByAccountNo(accountNo);
			if(account !=null){
				AccountDto accountDto = new AccountDto();
				this.copyAccountProperties(account, accountDto);			
				return accountDto;	
			}
			//if (account == null)
			//	throw new UPPException(ReturnCodeDict.ACCOUNT_NOT_EXIST, String.format("根据账号[%s]未查询到任何账户信息", accountNo));
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;		
		}catch(Exception e){
			logger.error("根据账号查询账户接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账号查询账户接口异常:"+e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public AccountDto getAccountByUserId(String userId) throws UPPException {
		try{			
			Account account = accountManager.getAccountByUserId(userId);
			if (account != null){
				AccountDto accountDto = new AccountDto();
				this.copyAccountProperties(account, accountDto);			
				return accountDto;	
			}
			//throw new UPPException(ReturnCodeDict.ACCOUNT_NOT_EXIST, String.format("根据用户[%s]未查询到任何账户信息.", userId));			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据账户所属ID查询账户接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账户所属ID查询账户接口异常:"+e.getLocalizedMessage());
		}
		return null;
	}

	/**
	 * 验证支付密码是否正确
	 * 如果一定时间内（24小时）内连续输入错误配置的数次（3次），则会锁定对应的账户
	 */
	@Override
	public boolean isPayPassword(String accountNo, String MD5)
			throws UPPException {
		try{
			//限定错误次数
			int errorLimit = Integer.parseInt(DefaultConfig.getValue("PAY_PASSWORD_ERROR_NUMBER"));
			Account account = accountManager.getAccountByAccountNo(accountNo);
			if (account == null)
				throw new UPPException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "找不到［" + accountNo + "］对应的账户");
			
			int errorCount = account.getPwdErrorCount()==null?0:Integer.parseInt(account.getPwdErrorCount());
			//清除超过24小时的错误次数, 解锁
			if(account.getPwdErrorTime()!=null 
					&& errorCount > 0
					&& (account.getPwdErrorTime()+86400000) < System.currentTimeMillis()){
				accountManager.modifyPayPasswordErrorCount(accountNo, 0);
				if(errorCount >= errorLimit){//判断是否是因错误次数锁定的账户
					accountManager.unlockAccount(accountNo);
				}
			}
			
			if (PayDict.ACCOUNT_STATUS_L.equals(account.getAccountStatus()))
				throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED, "账户已经锁定");

			
			if(MD5Util.getEncryptedPwd(MD5).equals(account.getPayPassword())){
				accountManager.modifyPayPasswordErrorCount(accountNo, 0);//清次数
				return true;
			}else{
				//错误次数
				++errorCount;
				accountManager.modifyPayPasswordErrorCount(accountNo, errorCount);				
				if(errorCount >= errorLimit){
					//锁定
					this.lockAccount(accountNo, "您已经连续"+errorLimit+"次输错密码，账户已经锁定，请24小时后再尝试");
				}
			}			
			return false;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("验证支付密码是否正确接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "验证支付密码是否正确接口异常:"+e.getLocalizedMessage());
		}
	}
	
	@Override
	public boolean isSetPayPassword(String accountNo) throws UPPException {
		try{
			
			Account account = accountManager.getAccountByAccountNo(accountNo);
			if (account == null)
				throw new UPPException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "找不到［" + accountNo + "］对应的账户");
			
			String initPwd = MD5Util.getEncryptedPwd(MD5Util.getEncryptedPwd(DefaultConfig.getValue("INIT_PAYPWD")));
			
			return initPwd.equals(account.getPayPassword())?false:true;	
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("验证是否设置了支付密码接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "验证是否设置了支付密码接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public String modifyPayPassword(String accountNo, String newMD5)
			throws UPPException {
		try{
			
			Account account = accountManager.getAccountByAccountNo(accountNo);
			if (account == null)
				throw new UPPException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "找不到［" + accountNo + "］对应的账户");

			accountManager.modifyPayPassword(accountNo, MD5Util.getEncryptedPwd(newMD5));
			
			return "SUCCESS";
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("验证是否设置了支付密码接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "验证是否设置了支付密码接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public String modifyPayPassword(String accountNo, String oldMD5,
			String newMD5) throws UPPException {
		try{
			
			if(!isPayPassword(accountNo, oldMD5))
				throw new UPPException(ReturnCodeDict.OLD_MD5, "原支付密码不正确");
			
			accountManager.modifyPayPassword(accountNo, MD5Util.getEncryptedPwd(newMD5));
			
			return "SUCCESS";
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("修改支付密码接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改支付密码接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public void lockAccount(String accountNo, String lockReasons)
			throws UPPException {
		try{
			
			accountManager.lockAccount(accountNo, lockReasons);			
			//发送短信
			Account account = accountManager.getAccountByAccountNo(accountNo);
			String mobileNo = account==null?"":account.getMobileNo();
			if(StringUtils.isNotBlank(mobileNo))
				SmsSender.getInstance().sendSmsByTemplate(mobileNo, "tyzfpt1004", new ArrayList<String>());				
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("锁定账户接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "锁定账户接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public void unLockAccount(String accountNo) throws UPPException {
		try{
			accountManager.unlockAccount(accountNo);			
			//清空支付密码错误次数
			accountManager.modifyPayPasswordErrorCount(accountNo, 0);
			//清空账户安全问题错误次数
			Account account = accountManager.getAccountByAccountNo(accountNo);
			AccountSafetyMess dbBean = accountSafetyManager.getAccountSafetyMessByAccountId(account.getId());
			if(dbBean!=null){
				AccountSafetyMess tem = new AccountSafetyMess();
				tem.setId(dbBean.getId());
				tem.setAnswerErrorCount("0");
				accountSafetyManager.updateAccountSafetyMess(tem);
			}
			
			//发送短信
			String mobileNo = account==null?"":account.getMobileNo();
			if(StringUtils.isNotBlank(mobileNo))
				SmsSender.getInstance().sendSmsByTemplate(mobileNo, "tyzfpt1005", new ArrayList<String>());			
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("解锁账户接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "解锁账户接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public PaginationResult<AccountDetailDto> queryAccountDetailByPage(
			DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<AccountDetailDto> result = new PaginationResult<AccountDetailDto>();
		try{
			AccountDetailExampleExtended exampleExtended = this.AdapterAccountDetailExampleExtended(requestParam);
			
			PaginationResult<AccountDetail> tem = accountDetailManager.queryAccountDetailPage(exampleExtended);
			if(tem!=null){
				result.setData(this.coptAccountDetail(tem.getData()));
				result.setTotal(tem.getTotal());
			}		
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询账户流水分页接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询账户流水分页接口异常:"+e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public AccountDetailDto getAccountDetailById(String id) throws UPPException {
		AccountDetailDto dto = null;
		try{			
			AccountDetail accountDetail = accountDetailManager.getAccountDetailById(id);
			dto = new AccountDetailDto();	
			this.copyAccountDetailProperties(accountDetail, dto);			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据ID查询账户流水明细接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据ID查询账户流水明细接口异常:"+e.getLocalizedMessage());
		}
		return dto;
	}
	
	

	@Override
	public PaginationResult<AccountDto> queryAccountByPage(
			DynamicSqlParameter requestParam) throws UPPException {
		try{
			PaginationResult<AccountDto> result = new PaginationResult<AccountDto>();
			AccountExampleExtended exampleExtended = this.AdapterAccountExampleExtended(requestParam);		
			PaginationResult<Account> tem = accountManager.queryAccountByPage(exampleExtended);
			result.setTotal(tem.getTotal());
			result.setData(this.coptAccount(tem.getData()));
			return result;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询账户分页信息接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询账户分页信息接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public List<AccountDto> queryAccountList(DynamicSqlParameter requestParam)
			throws UPPException {
		try{
			AccountExampleExtended exampleExtended = this.AdapterAccountExampleExtended(requestParam);
			List<Account> list = accountManager.queryAccount(exampleExtended);
			return this.coptAccount(list);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询账户信息接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询账户信息接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public void revokeAccount(String accountNo) throws UPPException {
		try{			
			
			accountManager.revokeAccount(accountNo);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("吊销账户异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "吊销账户异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public void recoveryAccount(String accountNo) throws UPPException {
		try{			
			
			accountManager.recoveryAccount(accountNo);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("回恢账户状态异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "回恢账户状态异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public List<AccountDetailDto> queryAccountDetailList(
			DynamicSqlParameter requestParam) throws UPPException {
		List<AccountDetailDto> result = new ArrayList<AccountDetailDto>();
		try{	
			AccountDetailExampleExtended exampleExtended = this.AdapterAccountDetailExampleExtended(requestParam);		
			List<AccountDetail> list = accountDetailManager.queryBookAccount(exampleExtended);
			result = this.coptAccountDetail(list);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询账户流水信息接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询账户流水信息接口异常:"+e.getLocalizedMessage());
		}
		return result;
	}
	
	@Override
	public int countAccountDetail(DynamicSqlParameter requestParam)
			throws UPPException {
		try{		
			AccountDetailExampleExtended exampleExtended = this.AdapterAccountDetailExampleExtended(requestParam);
			
			return accountDetailManager.countBookAccount(exampleExtended);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件统计账户流水数据接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件统计账户流水数据接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public String modifyPayMobileNo(String accountNo, String mobileNo)
			throws UPPException {
		try{
			 accountManager.modifyPayMobileNo(accountNo, mobileNo); 
			 //发送短信
			 if(StringUtils.isNotBlank(mobileNo)){
				 List<String> list = new ArrayList<String>();
				 list.add(mobileNo.substring(0, 3)+"****"+mobileNo.substring(7));
				 SmsSender.getInstance().sendSmsByTemplate(mobileNo, "pay206028", list);				
			 }
			 return "SUCCESS";
			 
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("修改支付手机号码异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改支付手机号码异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public int countOfAccount(DynamicSqlParameter requestParam)
			throws UPPException {
		try{			
			AccountExampleExtended exampleExtended = this.AdapterAccountExampleExtended(requestParam);		
			return accountManager.countAccount(exampleExtended);	
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件统计账户总数接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件统计账户总数接口异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public List<AccountDto> handleAccountData(List<AccountDto> list)throws UPPException {
		try{
			for(AccountDto dto : list){
				if(StringUtils.isNotBlank(dto.getOwnerUserNo())){
					accountManager.updateOwnerUserNoById(dto.getId(), dto.getOwnerUserNo());
					accountHistoryManager.updateAccountHistoryById(dto.getId(), dto.getOwnerUserNo());
				}
			}
			return list;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("处理旧数据接口异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "处理旧数据接口异常接口异常:"+e.getLocalizedMessage());
		}
		
	}

	
	
}
