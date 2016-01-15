package com.ctfo.upp.accountservice.account.dao;

import com.ctfo.account.dao.beans.UnbalanceDetailWithThirdPartyAccount;
import com.ctfo.account.dao.beans.UnbalanceDetailWithThirdPartyAccountExample;
import com.ctfo.account.dao.beans.UnbalanceDetailWithThirdPartyAccountExampleExtended;
import java.util.List;

public interface UnbalanceDetailWithThirdPartyAccountDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    void insert(UnbalanceDetailWithThirdPartyAccount record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    int updateByPrimaryKey(UnbalanceDetailWithThirdPartyAccount record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    int updateByPrimaryKeySelective(UnbalanceDetailWithThirdPartyAccount record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    List selectByExample(UnbalanceDetailWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    UnbalanceDetailWithThirdPartyAccount selectByPrimaryKey(String id);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    int deleteByExample(UnbalanceDetailWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    int countByExample(UnbalanceDetailWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    int updateByExampleSelective(UnbalanceDetailWithThirdPartyAccount record, UnbalanceDetailWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    int updateByExample(UnbalanceDetailWithThirdPartyAccount record, UnbalanceDetailWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    List selectByExampleWithPage(UnbalanceDetailWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    List selectByExampleWithPage(UnbalanceDetailWithThirdPartyAccountExampleExtended exampleExtended);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    List getKeyBy(UnbalanceDetailWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    String getNameSpace();

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    List getKeyBy(UnbalanceDetailWithThirdPartyAccountExampleExtended example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    int countByExample(UnbalanceDetailWithThirdPartyAccountExampleExtended example);
}