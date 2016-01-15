package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExample;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class OfflineRechargeApplyDAOImpl extends SqlMapClientDaoSupport implements OfflineRechargeApplyDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public OfflineRechargeApplyDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public void insert(OfflineRechargeApply record) {
		getSqlMapClientTemplate().insert(
				"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public int updateByPrimaryKey(OfflineRechargeApply record) {
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_updateByPrimaryKey",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public int updateByPrimaryKeySelective(OfflineRechargeApply record) {
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public List selectByExample(OfflineRechargeApplyExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_selectByExample",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public OfflineRechargeApply selectByPrimaryKey(String id) {
		OfflineRechargeApply key = new OfflineRechargeApply() {
		};
		key.setId(id);
		OfflineRechargeApply record = (OfflineRechargeApply) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public int deleteByExample(OfflineRechargeApplyExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public int deleteByPrimaryKey(String id) {
		OfflineRechargeApply key = new OfflineRechargeApply() {
		};
		key.setId(id);
		int rows = getSqlMapClientTemplate()
				.delete("TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_deleteByPrimaryKey",
						key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public int countByExample(OfflineRechargeApplyExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_countByExample",
				example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public int updateByExampleSelective(OfflineRechargeApply record,
			OfflineRechargeApplyExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public int updateByExample(OfflineRechargeApply record,
			OfflineRechargeApplyExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_updateByExample",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public List selectByExampleWithPage(OfflineRechargeApplyExample example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_selectByExamplePage",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public List selectByExampleWithPage(
			OfflineRechargeApplyExampleExtended exampleExtended) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_selectByExampleExtendedPage",
						exampleExtended);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public List getKeyBy(OfflineRechargeApplyExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_selectKeyBy",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public String getNameSpace() {
		return "TB_UPP_OFFLINE_TRADE_APPLY";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public List getKeyBy(OfflineRechargeApplyExampleExtended example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_selectKeyByExtended",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	public int countByExample(OfflineRechargeApplyExampleExtended example) {
		Integer count = (Integer) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_OFFLINE_TRADE_APPLY.abatorgenerated_countByExampleExtended",
						example);
		return count.intValue();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_APPLY
	 * @abatorgenerated  Wed Jul 29 11:07:39 CST 2015
	 */
	private static class UpdateByExampleParms extends
			OfflineRechargeApplyExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				OfflineRechargeApplyExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}