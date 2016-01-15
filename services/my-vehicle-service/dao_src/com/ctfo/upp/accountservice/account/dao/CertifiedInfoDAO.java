package com.ctfo.upp.accountservice.account.dao;

import com.ctfo.account.dao.beans.CertifiedInfo;
import com.ctfo.account.dao.beans.CertifiedInfoExample;
import com.ctfo.account.dao.beans.CertifiedInfoExampleExtended;
import java.util.List;

public interface CertifiedInfoDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	void insert(CertifiedInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	int updateByPrimaryKey(CertifiedInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	int updateByPrimaryKeySelective(CertifiedInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	List selectByExample(CertifiedInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	CertifiedInfo selectByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	int deleteByExample(CertifiedInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	int countByExample(CertifiedInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	int updateByExampleSelective(CertifiedInfo record, CertifiedInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	int updateByExample(CertifiedInfo record, CertifiedInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	List selectByExampleWithPage(CertifiedInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	List selectByExampleWithPage(CertifiedInfoExampleExtended exampleExtended);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	List getKeyBy(CertifiedInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	String getNameSpace();

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	List getKeyBy(CertifiedInfoExampleExtended example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_AUTHENTICA_MESS
	 * @abatorgenerated  Tue Nov 11 14:16:54 CST 2014
	 */
	int countByExample(CertifiedInfoExampleExtended example);
}