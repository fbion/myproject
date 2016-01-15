package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.HPaymentTrade;
import com.ctfo.payment.dao.beans.HPaymentTradeExample;
import com.ctfo.payment.dao.beans.HPaymentTradeExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class HPaymentTradeDAOImpl extends SqlMapClientDaoSupport implements HPaymentTradeDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public HPaymentTradeDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public void insert(HPaymentTrade record) {
		getSqlMapClientTemplate().insert(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public int updateByPrimaryKey(HPaymentTrade record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public int updateByPrimaryKeySelective(HPaymentTrade record) {
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public List selectByExample(HPaymentTradeExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_selectByExample",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public HPaymentTrade selectByPrimaryKey(String id) {
		HPaymentTrade key = new HPaymentTrade() {
		};
		key.setId(id);
		HPaymentTrade record = (HPaymentTrade) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public int deleteByExample(HPaymentTradeExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public int deleteByPrimaryKey(String id) {
		HPaymentTrade key = new HPaymentTrade() {
		};
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_deleteByPrimaryKey",
				key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public int countByExample(HPaymentTradeExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_countByExample",
				example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public int updateByExampleSelective(HPaymentTrade record,
			HPaymentTradeExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public int updateByExample(HPaymentTrade record,
			HPaymentTradeExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_updateByExample",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public List selectByExampleWithPage(HPaymentTradeExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_selectByExamplePage",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public List selectByExampleWithPage(
			HPaymentTradeExampleExtended exampleExtended) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_selectByExampleExtendedPage",
						exampleExtended);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public List getKeyBy(HPaymentTradeExample example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_selectKeyBy",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public String getNameSpace() {
		return "TB_UPP_PAY_TRADE_HISTORY";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public List getKeyBy(HPaymentTradeExampleExtended example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_selectKeyByExtended",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	public int countByExample(HPaymentTradeExampleExtended example) {
		Integer count = (Integer) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_PAY_TRADE_HISTORY.abatorgenerated_countByExampleExtended",
						example);
		return count.intValue();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table UPP.TB_UPP_PAY_TRADE_HISTORY
	 * @abatorgenerated  Thu May 21 15:53:47 CST 2015
	 */
	private static class UpdateByExampleParms extends HPaymentTradeExample {
		private Object record;

		public UpdateByExampleParms(Object record, HPaymentTradeExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}