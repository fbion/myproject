package com.sinoiov.upp.manager.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountChange;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.account.dto.beans.BalanceDto;
import com.ctfo.upp.accountservice.utils.AccountingType;
import com.ctfo.upp.dict.AccountDict;
import com.ctfo.upp.dict.CardType;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.root.utils.SmsSender;
import com.sinoiov.upp.manager.AbstractManager;
import com.sinoiov.upp.util.DefaultConfig;

@Service("accountManager")
public class AccountManagerImpl extends AbstractManager implements IAccountManager {

	private static final Log logger = LogFactory.getLog(AccountManagerImpl.class);

	static final private ReadWriteLock lock = new ReentrantReadWriteLock();

	@Autowired
	@Qualifier("accountHistoryManager")
	private IAccountHistoryManager accountHistoryManager;

	@Autowired
	@Qualifier("accountDetailManager")
	private IAccountDetailManager accountDetailManager;

	private Account getAccount(AccountExampleExtended exampleExtended)throws Exception{		
		List<Account> list = super.getModels(exampleExtended);
		if(list!=null && list.size()==1)
			return list.get(0);
		else{
			logger.warn("查询不到符合条件的账户信息");
		}
		return null;
	}

	//设置账户默认值
	private void setDefultValue(Account account)throws Exception{		
		account.setVersion(0);
		account.setAccountStatus(PayDict.ACCOUNT_STATUS_Y);//正常
		account.setCreateTime(System.currentTimeMillis());
		account.setTotalBalance(0l);
		account.setUnableTakecashBalance(0l);
		account.setFrozenBalance(0l);
		account.setUsableBalance(0l);
	}
	
	@Override
	public Account getAccountByAccountNo(String accountNo) throws UPPException {
		try{		
			AccountExampleExtended exampleExtended = new AccountExampleExtended();
			exampleExtended.createCriteria().andInsideAccountNoEqualTo(accountNo);

			Account account = this.getAccount(exampleExtended);

			if(account == null)throw new UPPException(ReturnCodeDict.ACCOUNT_NOT_EXIST, "找不到["+accountNo+"]对应的账户信息");

			return account;
			
		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据账号查询账户信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账号查询账户信息异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public Account getAccountByUserId(String userId) throws UPPException {
		try{		
			AccountExampleExtended exampleExtended = new AccountExampleExtended();
			exampleExtended.createCriteria().andOwnerUserIdEqualTo(userId);

			return this.getAccount(exampleExtended);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据账户所属ID查询账户信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账户所属ID查询账户信息异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public String getUserIdByAccountNo(String accountNo) throws UPPException {
		try{		
			AccountExampleExtended exampleExtended = new AccountExampleExtended();
			exampleExtended.createCriteria().andOwnerUserIdEqualTo(accountNo);
			Account account = this.getAccount(exampleExtended);
			return account==null?"":account.getOwnerUserId();

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据账号查询账户所属ID异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账号查询账户所属ID异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public Account getAccountById(String id) throws UPPException {
		try{
			
			Account account = new Account();
			account.setId(id);
			return super.getModelById(account);
			
		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("根据ID查询账户信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据ID查询账户信息异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public List<Account> queryAccount(AccountExampleExtended exampleExtended)
			throws UPPException {
		try{		

			return super.getModels(exampleExtended);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询账户集合异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询账户集合异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public PaginationResult<Account> queryAccountByPage(
			AccountExampleExtended exampleExtended) throws UPPException {
		try{		

			return super.paginateModels(exampleExtended);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询账户分页异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询账户分页异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public int countAccount(AccountExampleExtended exampleExtended)
			throws UPPException {
		try{		

			return super.countModels(exampleExtended);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件统计账户数据异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件统计账户数据异常:"+e.getLocalizedMessage());
		}
	}


	@Override
	public Account createAccount(Account account) throws UPPException {
		try{
			//非空验证
			super.notNull(account,
					account.getPayPassword(),
					account.getOwnerUserId(), 
					account.getOwnerLoginName(),
					account.getMobileNo());

			this.setDefultValue(account);//设置默认值

			String uuid = super.addModel(account);//保存

			account.setId(uuid);

			return account;

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建主账户异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "创建主账户异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public void updateAccountById(Account account) throws UPPException {
		try{

			//账户
			//account.setTotalBalance(0l);
			//account.setUnableTakecashBalance(0l);
			//account.setFrozenBalance(0l);
			//account.setUsableBalance(0l);
			//String accountNo = StringUtils.isBlank(account.getInsideAccountNo())?String.format("%s%s%s", "MA", System.currentTimeMillis(), RandomUtil.buildThreeBitRandomNumber()) : account.getInsideAccountNo();
			//account.setInsideAccountNo();			
			//			account.setPayPassword(null);
			//			account.getOwnerUserId(); 
			//			account.getOwnerLoginName();
			//			account.getMobileNo();

			Account dbAccount = this.getAccountById(account.getId());

			super.updateModelPart(account);

			//记录账户历史
			accountHistoryManager.recordHistory(dbAccount);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("修改账户信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改账户信息异常:"+e.getLocalizedMessage());
		}

	}

	@Override
	public void updateAccountState(String accountNo, String state)
			throws UPPException {
		try{

			Account dbAccount = this.getAccountByAccountNo(accountNo);			
			Account account = new Account();
			account.setId(dbAccount.getId());
			account.setAccountStatus(state);			

			super.updateModelPart(account);

			//记录账户历史
			accountHistoryManager.recordHistory(dbAccount);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据账号修改账户状态异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账号修改账户状态异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public void lockAccount(String accountNo, String lockReasons)
			throws UPPException {
		try{

			this.updateAccountState(accountNo, AccountDict.ACCOUNT_STATUS_LOCKED);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("锁定账户异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "锁定账户异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public void unlockAccount(String accountNo) throws UPPException {
		try{

			this.updateAccountState(accountNo, AccountDict.ACCOUNT_STATUS_NORMAL);
		
		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("解锁账户异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "解锁账户异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public void revokeAccount(String accountNo) throws UPPException {
		try{

			this.updateAccountState(accountNo, AccountDict.ACCOUNT_STATUS_REVOKE);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("根据账号吊销账户异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账号吊销账户异常:"+e.getLocalizedMessage());
		}

	}

	@Override
	public void recoveryAccount(String accountNo) throws UPPException {
		try{

			this.updateAccountState(accountNo, AccountDict.ACCOUNT_STATUS_NORMAL);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("根据账号解除吊销账户异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账号解除吊销账户异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public boolean isWarnValue(String accountNo) throws UPPException {
		try{

			Long balance = this.getBalance(accountNo);

			if(balance <= AmountUtil.getAmount(DefaultConfig.getValue("BALANCE_WARN_VALUE"))){
				//发短信告警
				String mobiles = DefaultConfig.getValue("BALANCE_WARN_MOBILES");
				List<String> param = new ArrayList<String>();
				param.add(DefaultConfig.getValue("BALANCE_WARN_VALUE"));
				for(String mobile : mobiles.split(",")){
					SmsSender.getInstance().sendSmsByTemplate(mobile, "tyzfpt1011", param);
				}
				return true;
			}

			return false;

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("检查账户是否接近阀值异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "检查账户是否接近阀值异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public Long getBalance(String accountNo) throws UPPException {
		try{

			Account account = this.getAccountByAccountNo(accountNo);

			return account.getUsableBalance();

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("根据账户号查询可用余额异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据账户号查询可用余额异常:"+e.getLocalizedMessage());
		}
	}

	//1 验证  2.进账  3.记流水 4.记历史
	@Override
	public void income(AccountChange income) throws UPPException {
		try{		
			//非空验证
			super.notNull(income,
					income.getAccountNo(),
					income.getMoney(), 
					income.getSubject(),
					income.getStoreCode(),
					income.getOrderId(),
					income.getTradeInternalNo());
			if (income.getMoney() <= 0)
				throw new UPPException("入账金额必须大于0");
			//锁
			lock.writeLock().lock();

			final Account account = this.getAccountByAccountNo(income.getAccountNo());
			//账户状态为锁定时，允许进账
			//if(account.getAccountStatus().equalsIgnoreCase(AccountDict.ACCOUNT_STATUS_LOCKED))
			//	throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED, "账户已被锁定");

			Account _account = new Account();
			//总余额
			_account.setTotalBalance(income.getMoney() + account.getTotalBalance());
			//可用余额
			_account.setUsableBalance(income.getMoney() + account.getUsableBalance());	
			//不可提现余额
			if(CardType.CREDIT_CARD.equalsIgnoreCase(income.getCardType()))
				_account.setUnableTakecashBalance(income.getMoney() + account.getUnableTakecashBalance());
			//乐观锁
			_account.setVersion(account.getVersion() + 1);

			Account where = new Account();
			where.setId(account.getId());
			where.setVersion(account.getVersion());
			//
			int result = super.updateModelByOtherModel(_account, where);
			if(result == 0)
				throw new UPPException(ReturnCodeDict.ACCOUNT_CONCURRENT_OPERATION, "不能对同一个账户同时进行操作");

			//流水
			accountDetailManager.recordAccountDetail(account.getId(), 
					income.getAccountNo(), AccountingType.INCOME, income.getSubject(), 
					income.getMoney(), _account.getTotalBalance(), 
					income.getAccountDate(), income.getTradeInternalNo(), income.getStoreCode(), income.getOrderId());

			//历史
			accountHistoryManager.recordHistory(account);		

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("入账操作时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "入账操作时异常:"+e.getLocalizedMessage());
		} finally {
			lock.writeLock().unlock();
		}
	}


	/**
	 * 除提现外的所有出账接口
	 * 1 验证  2.出账  3.记流水 4.记历史
	 */
	@Override
	public void payoutForWithdrawCash(AccountChange payout) throws UPPException {
		try{			
			//非空验证
			super.notNull(payout,
					payout.getAccountNo(),
					payout.getMoney(), 
					payout.getSubject(),
					payout.getStoreCode(),
					payout.getOrderId(),
					payout.getTradeInternalNo());
			if (payout.getMoney() <= 0)
				throw new UPPException("出账金额必须大于0");
			//锁
			lock.writeLock().lock();

			final Account account = this.getAccountByAccountNo(payout.getAccountNo());
			//锁定状态不允许出账
			if (account.getAccountStatus().equalsIgnoreCase(AccountDict.ACCOUNT_STATUS_LOCKED))
				throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED, "账户已被锁定");

			Account _account = new Account();
			_account.setTotalBalance((account.getTotalBalance() - payout.getMoney()));		
			//可用余额
			if (payout.getMoney() > account.getUsableBalance())
				throw new UPPException(ReturnCodeDict.ACCOUNT_FROZEN_AMOUNT_INSUFFICIENT, "账户可用余额不足");
			_account.setUsableBalance((account.getUsableBalance() - payout.getMoney()));				
			//可提现余额
			Long ableTakecashBalance = (account.getUsableBalance() - account.getUnableTakecashBalance());
			if (payout.getMoney() > ableTakecashBalance)
				throw new UPPException(ReturnCodeDict.ACCOUNT_TAKECASH_INSUFFICIENT, "账户可提现余额不足");		
			//乐观锁
			_account.setVersion(account.getVersion() + 1);

			Account where = new Account();
			where.setId(account.getId());
			where.setVersion(account.getVersion());
			int result = super.updateModelByOtherModel(_account, where);
			if(result == 0)
				throw new UPPException(ReturnCodeDict.ACCOUNT_CONCURRENT_OPERATION, "不能对同一个账户同时进行操作");

			//流水
			accountDetailManager.recordAccountDetail(account.getId(), 
					payout.getAccountNo(), AccountingType.DEDUCTION, payout.getSubject(), 
					payout.getMoney(), _account.getTotalBalance(), 
					payout.getAccountDate(), payout.getTradeInternalNo(), payout.getStoreCode(),payout.getOrderId());

			//历史
			accountHistoryManager.recordHistory(account);	

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("出账（提现专用）操作时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "出账（提现专用）操作时异常:"+e.getLocalizedMessage());
		} finally {
			lock.writeLock().unlock();
		}
	}
	/**
	 * 除提现外的所有出账接口
	 * 1 验证  2.出账  3.记流水 4.记历史
	 */
	@Override
	public void payout(AccountChange payout, boolean payoutFrozenBalance) throws UPPException {
		try{

			//非空验证
			super.notNull(payout,
					payout.getAccountNo(),
					payout.getMoney(), 
					payout.getSubject(),
					payout.getStoreCode(),
					payout.getOrderId(),
					payout.getTradeInternalNo());
			if (payout.getMoney() <= 0)
				throw new UPPException("出账金额必须大于0");

			//锁
			lock.writeLock().lock();

			final Account account = this.getAccountByAccountNo(payout.getAccountNo());
			//锁定状态不允许出账
			if (account.getAccountStatus().equalsIgnoreCase(AccountDict.ACCOUNT_STATUS_LOCKED))
				throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED, "账户已被锁定");

			Account _account = new Account();
			_account.setTotalBalance((account.getTotalBalance() - payout.getMoney()));					
			//从冻结金额中出账
			if(payoutFrozenBalance){		
				if(payout.getMoney() > account.getFrozenBalance())
					throw new UPPException(ReturnCodeDict.ACCOUNT_FROZEN_AMOUNT_INSUFFICIENT, "账户冻结余额不足");
				_account.setFrozenBalance((account.getFrozenBalance() - payout.getMoney()));
			}else{
				//可用余额
				if (payout.getMoney() > account.getUsableBalance())
					throw new UPPException(ReturnCodeDict.ACCOUNT_FROZEN_AMOUNT_INSUFFICIENT, "账户可用余额不足");
				_account.setUsableBalance((account.getUsableBalance() - payout.getMoney()));
				//不可提现余额
				Long unableTakecashBalance = (payout.getMoney() > account.getUnableTakecashBalance())?
						0:(account.getUnableTakecashBalance() - payout.getMoney());
				_account.setUnableTakecashBalance(unableTakecashBalance);
			}
			//乐观锁
			_account.setVersion(account.getVersion() + 1);

			Account where = new Account();
			where.setId(account.getId());
			where.setVersion(account.getVersion());
			int result = super.updateModelByOtherModel(_account, where);
			if(result == 0)
				throw new UPPException(ReturnCodeDict.ACCOUNT_CONCURRENT_OPERATION, "不能对同一个账户同时进行操作");

			//流水
			accountDetailManager.recordAccountDetail(account.getId(), 
					payout.getAccountNo(), AccountingType.DEDUCTION, payout.getSubject(), 
					payout.getMoney(), _account.getTotalBalance(), 
					payout.getAccountDate(), payout.getTradeInternalNo(), payout.getStoreCode(), payout.getOrderId());

			//历史
			accountHistoryManager.recordHistory(account);	

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("出账操作时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "出账操作时异常:"+e.getLocalizedMessage());
		} finally {
			lock.writeLock().unlock();
		}
	}

	
	@Override
	public void payoutAndIncome(String payoutAccountNo, String incomeAccountNo, AccountChange change, boolean payoutFrozenBalance) throws UPPException {

		try{
			super.notNull(change, payoutAccountNo, incomeAccountNo);
			
			if(payoutAccountNo.equals(incomeAccountNo))
				throw new UPPException(ReturnCodeDict.PARAM_NOT_STANDARD, "进出项账号不能是同一个账号");
			
			change.setAccountNo(payoutAccountNo);
			
			this.payout(change, payoutFrozenBalance);

			change.setAccountNo(incomeAccountNo);
			
			this.income(change);
			
		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;		
		}catch(Exception e){
			logger.error("进出账操作时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "进出账操作时异常:"+e.getLocalizedMessage());
		}
		
	}
	
	
	@Override
	public void frozenAccount(String tradeInternalNo, String accountDate,
			String subject, String accountNo, String storeCode, String orderId, Long money)
					throws UPPException {
		try{

			//非空验证
			super.notNull(accountNo, storeCode, orderId, subject, tradeInternalNo);			
			if (money <= 0)
				throw new UPPException("冻结金额必须大于0");

			lock.writeLock().lock();

			final Account account = this.getAccountByAccountNo(accountNo);
			//锁定状态不允许出账
			if (AccountDict.ACCOUNT_STATUS_LOCKED.equalsIgnoreCase(account.getAccountStatus()))
				throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED, "账户已被锁定");
			if (money > account.getUsableBalance())
				throw new UPPException(ReturnCodeDict.ACCOUNT_USABLE_AMOUNT_INSUFFICIENT, "账户可用余额不足");

			Account _account = new Account();
			_account.setVersion(account.getVersion() + 1);
			_account.setUsableBalance(account.getUsableBalance() - money);
			_account.setFrozenBalance(account.getFrozenBalance() + money);

			Account where = new Account();
			where.setId(account.getId());
			where.setVersion(account.getVersion());

			int result = updateModelByOtherModel(_account, where);

			if(result == 0)
				throw new UPPException(ReturnCodeDict.ACCOUNT_CONCURRENT_OPERATION, "不能对同一个账户同时进行操作");

			//流水
			accountDetailManager.recordAccountDetail(account.getId(), 
					accountNo, AccountingType.FROZEN, subject, 
					money, account.getTotalBalance(), 
					accountDate, tradeInternalNo, storeCode, orderId);

			//历史
			accountHistoryManager.recordHistory(account);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("冻结账户金额时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "冻结账户金额时异常:"+e.getLocalizedMessage());
		}finally{
			lock.writeLock().unlock();
		} 
	}

	@Override
	public void unfrozenAccount(String tradeInternalNo, String accountDate,
			String subject, String accountNo, String storeCode, String orderId, Long money)
					throws UPPException {
		try{
			//非空验证
			super.notNull(accountNo, storeCode, orderId, subject, tradeInternalNo);			
			if (money <= 0)
				throw new UPPException("冻结金额必须大于0");

			lock.writeLock().lock();

			final Account account = this.getAccountByAccountNo(accountNo);
			//锁定状态不允许出账
			if (AccountDict.ACCOUNT_STATUS_LOCKED.equalsIgnoreCase(account.getAccountStatus()))
				throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_LOCKED, "账户已被锁定");	
			if (money > account.getFrozenBalance())
				throw new UPPException(ReturnCodeDict.ACCOUNT_FROZEN_AMOUNT_INSUFFICIENT, "账户冻结余额不足");

			Account _account = new Account();
			_account.setVersion(account.getVersion() + 1);
			_account.setUsableBalance(account.getUsableBalance() + money);
			_account.setFrozenBalance(account.getFrozenBalance() - money);

			Account where = new Account();
			where.setId(account.getId());
			where.setVersion(account.getVersion());

			int result = updateModelByOtherModel(_account, where);

			if(result == 0)
				throw new UPPException(ReturnCodeDict.ACCOUNT_CONCURRENT_OPERATION, "不能对同一个账户同时进行操作");

			//流水
			accountDetailManager.recordAccountDetail(account.getId(), 
					accountNo, AccountingType.UNFROZEN, subject, 
					money, account.getTotalBalance(), 
					accountDate, tradeInternalNo, storeCode, orderId);

			//历史
			accountHistoryManager.recordHistory(account);


		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("解冻账户金额时异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "解冻账户金额时异常:"+e.getLocalizedMessage());
		}finally{
			lock.writeLock().unlock();
		} 

	}

	@Override
	public void modifyPayPassword(String accountNo, String payPassword)
			throws UPPException {
		try{

			String id = this.getAccountByAccountNo(accountNo).getId();
			Account account = new Account();
			account.setId(id);
			account.setPayPassword(payPassword);

			super.updateModelPart(account);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("修改支付密码异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改支付密码异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public void modifyPayPasswordErrorCount(String accountNo, int pwdErrorCount)
			throws UPPException {
		try{

			String id = this.getAccountByAccountNo(accountNo).getId();
			Account account = new Account();
			account.setId(id);
			account.setPwdErrorCount(pwdErrorCount+"");
			account.setPwdErrorTime(System.currentTimeMillis());

			super.updateModelPart(account);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("修改支付密码错误次数异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改支付密码错误次数异常:"+e.getLocalizedMessage());
		}
		
	}

	@Override
	public void modifyPayMobileNo(String accountNo, String mobileNo)
			throws UPPException {
		try{

			String id = this.getAccountByAccountNo(accountNo).getId();
			Account account = new Account();
			account.setId(id);
			account.setMobileNo(mobileNo);
			super.updateModelPart(account);

		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("修改支付手机号码异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改支付手机号码异常:"+e.getLocalizedMessage());
		}
		
	}

	@Override
	public BalanceDto sumAccountBalance(Map<String, Object> parameter)
			throws UPPException {
		try{
			 BalanceDto dto = new BalanceDto();
			 List<Map<? extends Object, ? extends Object>> recordedList=super.queryListBySQL("STAT_TB_UPP_ACCOUNT_INFO.sumAccountBalance", parameter);
			 if(recordedList!=null&&recordedList.size()>0){
				 Map<? extends Object, ? extends Object> tem = recordedList.get(0);				 
				 dto.setTotalBalance(tem.get("TOTALBALANCE")==null?0:((BigDecimal) tem.get("TOTALBALANCE")).longValue());
				 dto.setUnableTakecashBalance(tem.get("UNABLETAKECASHBALANCE")==null?0:((BigDecimal) tem.get("UNABLETAKECASHBALANCE")).longValue());
				 dto.setFrozenBalance(tem.get("FROZENBALANCE")==null?0:((BigDecimal)tem.get("FROZENBALANCE")).longValue());
				 dto.setUsableBalance(tem.get("USABLEBALANCE")==null?0:((BigDecimal)tem.get("USABLEBALANCE")).longValue());		 
			 }else{
				 logger.warn("未统计到账户余额信息");
			 }
			  
			return dto;
			
		}catch (UPPException ue) {
			logger.error(ue.getMessage(), ue);
			throw ue;	
		}catch(Exception e){
			logger.error("统计账户余额异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "统计账户余额异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public int updateOwnerUserNoById(String id, String ownerUserNo)
			throws UPPException {
		try{
			super.notNull(id, ownerUserNo);
			Account account = new Account();
			account.setId(id);
			account.setOwnerUserNo(ownerUserNo);			
			return super.updateModelPart(account);
			
		}catch(Exception e){
			logger.error("修改会员编号异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改会员编号异常:"+e.getLocalizedMessage());
		}
		
	}

}
