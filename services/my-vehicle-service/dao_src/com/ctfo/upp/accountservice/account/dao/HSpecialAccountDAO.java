package com.ctfo.upp.accountservice.account.dao;

import com.ctfo.account.dao.beans.HSpecialAccount;
import com.ctfo.account.dao.beans.HSpecialAccountExample;
import com.ctfo.account.dao.beans.HSpecialAccountExampleExtended;
import java.util.List;

public interface HSpecialAccountDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	void insert(HSpecialAccount record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	int updateByPrimaryKey(HSpecialAccount record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	int updateByPrimaryKeySelective(HSpecialAccount record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	List selectByExample(HSpecialAccountExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	HSpecialAccount selectByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	int deleteByExample(HSpecialAccountExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	int countByExample(HSpecialAccountExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	int updateByExampleSelective(HSpecialAccount record, HSpecialAccountExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	int updateByExample(HSpecialAccount record, HSpecialAccountExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	List selectByExampleWithPage(HSpecialAccountExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	List selectByExampleWithPage(HSpecialAccountExampleExtended exampleExtended);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	List getKeyBy(HSpecialAccountExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	String getNameSpace();

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	List getKeyBy(HSpecialAccountExampleExtended example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT_HISTORY
	 * @abatorgenerated  Mon Nov 10 15:19:40 CST 2014
	 */
	int countByExample(HSpecialAccountExampleExtended example);
}