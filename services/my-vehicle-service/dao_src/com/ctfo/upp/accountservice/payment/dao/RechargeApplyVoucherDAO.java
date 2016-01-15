package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.RechargeApplyVoucher;
import com.ctfo.payment.dao.beans.RechargeApplyVoucherExample;
import com.ctfo.payment.dao.beans.RechargeApplyVoucherExampleExtended;
import java.util.List;

public interface RechargeApplyVoucherDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	void insert(RechargeApplyVoucher record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	int updateByPrimaryKey(RechargeApplyVoucher record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	int updateByPrimaryKeySelective(RechargeApplyVoucher record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	List selectByExample(RechargeApplyVoucherExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	RechargeApplyVoucher selectByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	int deleteByExample(RechargeApplyVoucherExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	int countByExample(RechargeApplyVoucherExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	int updateByExampleSelective(RechargeApplyVoucher record,
			RechargeApplyVoucherExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	int updateByExample(RechargeApplyVoucher record,
			RechargeApplyVoucherExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	List selectByExampleWithPage(RechargeApplyVoucherExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	List selectByExampleWithPage(
			RechargeApplyVoucherExampleExtended exampleExtended);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	List getKeyBy(RechargeApplyVoucherExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	String getNameSpace();

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	List getKeyBy(RechargeApplyVoucherExampleExtended example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_VOUCHER
	 * @abatorgenerated  Wed Jul 29 11:07:40 CST 2015
	 */
	int countByExample(RechargeApplyVoucherExampleExtended example);
}