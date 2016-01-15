package com.ctfo.upp.accountservice.account.dao;

import com.ctfo.account.dao.beans.SpecialAccount;
import com.ctfo.account.dao.beans.SpecialAccountExample;
import com.ctfo.account.dao.beans.SpecialAccountExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class SpecialAccountDAOImpl extends SqlMapClientDaoSupport implements SpecialAccountDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public SpecialAccountDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void insert(SpecialAccount record) {
		getSqlMapClientTemplate().insert("TB_UPP_SUB_ACCOUNT.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int updateByPrimaryKey(SpecialAccount record) {
		int rows = getSqlMapClientTemplate().update("TB_UPP_SUB_ACCOUNT.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int updateByPrimaryKeySelective(SpecialAccount record) {
		int rows = getSqlMapClientTemplate().update("TB_UPP_SUB_ACCOUNT.abatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public List selectByExample(SpecialAccountExample example) {
		List list = getSqlMapClientTemplate().queryForList("TB_UPP_SUB_ACCOUNT.abatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public SpecialAccount selectByPrimaryKey(String id) {
		SpecialAccount key = new SpecialAccount() {
		};
		key.setId(id);
		SpecialAccount record = (SpecialAccount) getSqlMapClientTemplate().queryForObject("TB_UPP_SUB_ACCOUNT.abatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int deleteByExample(SpecialAccountExample example) {
		int rows = getSqlMapClientTemplate().delete("TB_UPP_SUB_ACCOUNT.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int deleteByPrimaryKey(String id) {
		SpecialAccount key = new SpecialAccount() {
		};
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("TB_UPP_SUB_ACCOUNT.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int countByExample(SpecialAccountExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("TB_UPP_SUB_ACCOUNT.abatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int updateByExampleSelective(SpecialAccount record, SpecialAccountExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update("TB_UPP_SUB_ACCOUNT.abatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int updateByExample(SpecialAccount record, SpecialAccountExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update("TB_UPP_SUB_ACCOUNT.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public List selectByExampleWithPage(SpecialAccountExample example) {
		List list = getSqlMapClientTemplate().queryForList("TB_UPP_SUB_ACCOUNT.abatorgenerated_selectByExamplePage", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public List selectByExampleWithPage(SpecialAccountExampleExtended exampleExtended) {
		List list = getSqlMapClientTemplate().queryForList("TB_UPP_SUB_ACCOUNT.abatorgenerated_selectByExampleExtendedPage", exampleExtended);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public List getKeyBy(SpecialAccountExample example) {
		List list = getSqlMapClientTemplate().queryForList("TB_UPP_SUB_ACCOUNT.abatorgenerated_selectKeyBy", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getNameSpace() {
		return "TB_UPP_SUB_ACCOUNT";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public List getKeyBy(SpecialAccountExampleExtended example) {
		List list = getSqlMapClientTemplate().queryForList("TB_UPP_SUB_ACCOUNT.abatorgenerated_selectKeyByExtended", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int countByExample(SpecialAccountExampleExtended example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("TB_UPP_SUB_ACCOUNT.abatorgenerated_countByExampleExtended", example);
		return count.intValue();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	private static class UpdateByExampleParms extends SpecialAccountExample {
		private Object record;

		public UpdateByExampleParms(Object record, SpecialAccountExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}