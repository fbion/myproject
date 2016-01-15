package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.MainWithThirdPartyAccount;
import com.ctfo.payment.dao.beans.MainWithThirdPartyAccountExample;
import com.ctfo.payment.dao.beans.MainWithThirdPartyAccountExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class MainWithThirdPartyAccountDAOImpl extends SqlMapClientDaoSupport implements MainWithThirdPartyAccountDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public MainWithThirdPartyAccountDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public void insert(MainWithThirdPartyAccount record) {
		getSqlMapClientTemplate().insert(
				"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public int updateByPrimaryKey(MainWithThirdPartyAccount record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public int updateByPrimaryKeySelective(MainWithThirdPartyAccount record) {
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public List selectByExample(MainWithThirdPartyAccountExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_selectByExample",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public MainWithThirdPartyAccount selectByPrimaryKey(String id) {
		MainWithThirdPartyAccount key = new MainWithThirdPartyAccount() {
		};
		key.setId(id);
		MainWithThirdPartyAccount record = (MainWithThirdPartyAccount) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public int deleteByExample(MainWithThirdPartyAccountExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public int deleteByPrimaryKey(String id) {
		MainWithThirdPartyAccount key = new MainWithThirdPartyAccount() {
		};
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_deleteByPrimaryKey",
				key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public int countByExample(MainWithThirdPartyAccountExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_countByExample",
				example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public int updateByExampleSelective(MainWithThirdPartyAccount record,
			MainWithThirdPartyAccountExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public int updateByExample(MainWithThirdPartyAccount record,
			MainWithThirdPartyAccountExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_updateByExample",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public List selectByExampleWithPage(MainWithThirdPartyAccountExample example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_selectByExamplePage",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public List selectByExampleWithPage(
			MainWithThirdPartyAccountExampleExtended exampleExtended) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_selectByExampleExtendedPage",
						exampleExtended);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public List getKeyBy(MainWithThirdPartyAccountExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_selectKeyBy",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public String getNameSpace() {
		return "TB_UPP_CKECK_ACCOUNT_MESS";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public List getKeyBy(MainWithThirdPartyAccountExampleExtended example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_selectKeyByExtended",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	public int countByExample(MainWithThirdPartyAccountExampleExtended example) {
		Integer count = (Integer) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_CKECK_ACCOUNT_MESS.abatorgenerated_countByExampleExtended",
						example);
		return count.intValue();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table UPP.TB_UPP_CKECK_ACCOUNT_MESS
	 * @abatorgenerated  Thu Jan 22 14:02:16 CST 2015
	 */
	private static class UpdateByExampleParms extends
			MainWithThirdPartyAccountExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				MainWithThirdPartyAccountExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}