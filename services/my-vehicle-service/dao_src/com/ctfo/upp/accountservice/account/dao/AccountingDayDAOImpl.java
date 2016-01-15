package com.ctfo.upp.accountservice.account.dao;

import com.ctfo.payment.dao.beans.AccountingDay;
import com.ctfo.payment.dao.beans.AccountingDayExample;
import com.ctfo.payment.dao.beans.AccountingDayExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AccountingDayDAOImpl extends SqlMapClientDaoSupport implements AccountingDayDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public AccountingDayDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public void insert(AccountingDay record) {
		getSqlMapClientTemplate().insert(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public int updateByPrimaryKey(AccountingDay record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public int updateByPrimaryKeySelective(AccountingDay record) {
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public List selectByExample(AccountingDayExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_selectByExample",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public AccountingDay selectByPrimaryKey(String id) {
		AccountingDay key = new AccountingDay() {
		};
		key.setId(id);
		AccountingDay record = (AccountingDay) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public int deleteByExample(AccountingDayExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public int deleteByPrimaryKey(String id) {
		AccountingDay key = new AccountingDay() {
		};
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_deleteByPrimaryKey",
				key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public int countByExample(AccountingDayExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_countByExample",
				example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public int updateByExampleSelective(AccountingDay record,
			AccountingDayExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public int updateByExample(AccountingDay record,
			AccountingDayExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_updateByExample",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public List selectByExampleWithPage(AccountingDayExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_selectByExamplePage",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public List selectByExampleWithPage(
			AccountingDayExampleExtended exampleExtended) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_selectByExampleExtendedPage",
						exampleExtended);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public List getKeyBy(AccountingDayExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_selectKeyBy", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public String getNameSpace() {
		return "TB_UPP_BOOK_DAY_ACCOUNT";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public List getKeyBy(AccountingDayExampleExtended example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_selectKeyByExtended",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	public int countByExample(AccountingDayExampleExtended example) {
		Integer count = (Integer) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_BOOK_DAY_ACCOUNT.abatorgenerated_countByExampleExtended",
						example);
		return count.intValue();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table UPP.TB_UPP_BOOK_DAY_ACCOUNT
	 * @abatorgenerated  Tue Apr 07 14:19:09 CST 2015
	 */
	private static class UpdateByExampleParms extends AccountingDayExample {
		private Object record;

		public UpdateByExampleParms(Object record, AccountingDayExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}