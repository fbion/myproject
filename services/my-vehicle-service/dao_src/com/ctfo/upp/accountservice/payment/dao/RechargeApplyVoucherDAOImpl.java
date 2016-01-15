package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.RechargeApplyVoucher;
import com.ctfo.payment.dao.beans.RechargeApplyVoucherExample;
import com.ctfo.payment.dao.beans.RechargeApplyVoucherExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class RechargeApplyVoucherDAOImpl extends SqlMapClientDaoSupport implements RechargeApplyVoucherDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public RechargeApplyVoucherDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public void insert(RechargeApplyVoucher record) {
		getSqlMapClientTemplate().insert(
				"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public int updateByPrimaryKey(RechargeApplyVoucher record) {
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_updateByPrimaryKey",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public int updateByPrimaryKeySelective(RechargeApplyVoucher record) {
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public List selectByExample(RechargeApplyVoucherExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_selectByExample",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public RechargeApplyVoucher selectByPrimaryKey(String id) {
		RechargeApplyVoucher key = new RechargeApplyVoucher() {
		};
		key.setId(id);
		RechargeApplyVoucher record = (RechargeApplyVoucher) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public int deleteByExample(RechargeApplyVoucherExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_deleteByExample",
				example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public int deleteByPrimaryKey(String id) {
		RechargeApplyVoucher key = new RechargeApplyVoucher() {
		};
		key.setId(id);
		int rows = getSqlMapClientTemplate()
				.delete("TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_deleteByPrimaryKey",
						key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public int countByExample(RechargeApplyVoucherExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_countByExample",
				example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public int updateByExampleSelective(RechargeApplyVoucher record,
			RechargeApplyVoucherExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update("TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public int updateByExample(RechargeApplyVoucher record,
			RechargeApplyVoucherExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_updateByExample",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public List selectByExampleWithPage(RechargeApplyVoucherExample example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_selectByExamplePage",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public List selectByExampleWithPage(
			RechargeApplyVoucherExampleExtended exampleExtended) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_selectByExampleExtendedPage",
						exampleExtended);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public List getKeyBy(RechargeApplyVoucherExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_selectKeyBy",
				example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public String getNameSpace() {
		return "TB_UPP_OFFLINE_TRADE_VOUCHER";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public List getKeyBy(RechargeApplyVoucherExampleExtended example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_selectKeyByExtended",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	public int countByExample(RechargeApplyVoucherExampleExtended example) {
		Integer count = (Integer) getSqlMapClientTemplate()
				.queryForObject(
						"TB_UPP_OFFLINE_TRADE_VOUCHER.abatorgenerated_countByExampleExtended",
						example);
		return count.intValue();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	private static class UpdateByExampleParms extends
			RechargeApplyVoucherExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				RechargeApplyVoucherExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}