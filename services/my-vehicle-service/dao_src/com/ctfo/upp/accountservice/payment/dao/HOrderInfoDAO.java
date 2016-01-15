package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.HOrderInfo;
import com.ctfo.payment.dao.beans.HOrderInfoExample;
import com.ctfo.payment.dao.beans.HOrderInfoExampleExtended;
import java.util.List;

public interface HOrderInfoDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	void insert(HOrderInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	int updateByPrimaryKey(HOrderInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	int updateByPrimaryKeySelective(HOrderInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	List selectByExample(HOrderInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	HOrderInfo selectByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	int deleteByExample(HOrderInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	int countByExample(HOrderInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	int updateByExampleSelective(HOrderInfo record, HOrderInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	int updateByExample(HOrderInfo record, HOrderInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	List selectByExampleWithPage(HOrderInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	List selectByExampleWithPage(HOrderInfoExampleExtended exampleExtended);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	List getKeyBy(HOrderInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	String getNameSpace();

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	List getKeyBy(HOrderInfoExampleExtended example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_PAY_ORDER_HISTORY
	 * @abatorgenerated  Tue Jul 21 14:56:04 CST 2015
	 */
	int countByExample(HOrderInfoExampleExtended example);
}