package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.UnbalanceHandlerWithBusPlatform;
import com.ctfo.payment.dao.beans.UnbalanceHandlerWithBusPlatformExample;
import com.ctfo.payment.dao.beans.UnbalanceHandlerWithBusPlatformExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UnbalanceHandlerWithBusPlatformDAOImpl extends SqlMapClientDaoSupport implements UnbalanceHandlerWithBusPlatformDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public UnbalanceHandlerWithBusPlatformDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public void insert(UnbalanceHandlerWithBusPlatform record) {
		getSqlMapClientTemplate().insert(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public int updateByPrimaryKey(UnbalanceHandlerWithBusPlatform record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public int updateByPrimaryKeySelective(
			UnbalanceHandlerWithBusPlatform record) {
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_CHECKORDER_HANDLE.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public List selectByExample(UnbalanceHandlerWithBusPlatformExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_selectByExample",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public UnbalanceHandlerWithBusPlatform selectByPrimaryKey(String id) {
		UnbalanceHandlerWithBusPlatform key = new UnbalanceHandlerWithBusPlatform() {
		};
		key.setId(id);
		UnbalanceHandlerWithBusPlatform record = (UnbalanceHandlerWithBusPlatform) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public int deleteByExample(UnbalanceHandlerWithBusPlatformExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public int deleteByPrimaryKey(String id) {
		UnbalanceHandlerWithBusPlatform key = new UnbalanceHandlerWithBusPlatform() {
		};
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_deleteByPrimaryKey",
				key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public int countByExample(UnbalanceHandlerWithBusPlatformExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_countByExample",
				example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public int updateByExampleSelective(UnbalanceHandlerWithBusPlatform record,
			UnbalanceHandlerWithBusPlatformExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_CHECKORDER_HANDLE.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public int updateByExample(UnbalanceHandlerWithBusPlatform record,
			UnbalanceHandlerWithBusPlatformExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_updateByExample",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public List selectByExampleWithPage(
			UnbalanceHandlerWithBusPlatformExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_selectByExamplePage",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public List selectByExampleWithPage(
			UnbalanceHandlerWithBusPlatformExampleExtended exampleExtended) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_selectByExampleExtendedPage",
						exampleExtended);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public List getKeyBy(UnbalanceHandlerWithBusPlatformExample example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_selectKeyBy",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public String getNameSpace() {
		return "TB_UPP_CHECKORDER_HANDLE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public List getKeyBy(UnbalanceHandlerWithBusPlatformExampleExtended example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_selectKeyByExtended",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	public int countByExample(
			UnbalanceHandlerWithBusPlatformExampleExtended example) {
		Integer count = (Integer) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_CHECKORDER_HANDLE.abatorgenerated_countByExampleExtended",
						example);
		return count.intValue();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table UPP.TB_UPP_CHECKORDER_HANDLE
	 * @abatorgenerated  Thu Jan 22 14:02:13 CST 2015
	 */
	private static class UpdateByExampleParms extends
			UnbalanceHandlerWithBusPlatformExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				UnbalanceHandlerWithBusPlatformExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}