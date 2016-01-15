package com.sinoiov.upp.manager.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.AccountChange;
import com.ctfo.account.dao.beans.HSpecialAccount;
import com.ctfo.account.dao.beans.HSpecialAccountExampleExtended;
import com.ctfo.account.dao.beans.SpecialAccount;
import com.ctfo.account.dao.beans.SpecialAccountDetail;
import com.ctfo.account.dao.beans.SpecialAccountDetailExampleExtended;
import com.ctfo.account.dao.beans.SpecialAccountExampleExtended;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.manager.AbstractManager;
import com.sinoiov.upp.manager.account.ISpecialAccountManager;

@Service("specialAccountManager")
public class SpecialAccountManagerImpl extends AbstractManager implements ISpecialAccountManager {

	@Override
	public SpecialAccount createSpecialAccount(SpecialAccount specialAccount)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int frozenSpecialAccount(String bookAccInternalNo,
			String accountDate, String subject, String tradeInternalNo,
			String storeCode, String mainAccountId, String accountNo, Long money)
			throws UPPException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int unfrozenSpecialAccount(String bookAccInternalNo,
			String accountDate, String subject, String tradeInternalNo,
			String storeCode, String mainAccountId, String accountNo, Long money)
			throws UPPException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int incomeSpecialAccount(String bookAccInternalNo,
			String accountDate, String subject, String tradeInternalNo,
			String insideAccountNo, String storeCode, String mainAccountId,
			AccountChange income) throws UPPException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int payoutSpecialAccount(String bookAccInternalNo,
			String accountDate, String subject, String tradeInternalNo,
			String insideAccountNo, String storeCode, String mainAccountId,
			AccountChange payout) throws UPPException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SpecialAccount> querySpecialAccount(
			SpecialAccountExampleExtended exampleExtended) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<SpecialAccount> querySpecialAccountByPage(
			SpecialAccountExampleExtended exampleExtended) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpecialAccountDetail addSpecialAccountDetail(
			SpecialAccountDetail specialAccountDetail) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpecialAccountDetail> queryBookSpecialAccount(
			SpecialAccountDetailExampleExtended exampleExtended)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<SpecialAccountDetail> queryBookSpecialAccountByPage(
			SpecialAccountDetailExampleExtended exampleExtended)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSpecialAccountHistory(HSpecialAccount hSpecialAccount)
			throws UPPException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<HSpecialAccount> queryHisSpecialAccount(
			HSpecialAccountExampleExtended exampleExtended) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<HSpecialAccount> queryHisSpecialAccountByPage(
			HSpecialAccountExampleExtended exampleExtended) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

}
