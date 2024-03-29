package com.ctfo.upp.accountservice.account.dao;

import com.ctfo.account.dao.beans.UnbalanceTradeWithThirdPartyAccount;
import com.ctfo.account.dao.beans.UnbalanceTradeWithThirdPartyAccountExample;
import com.ctfo.account.dao.beans.UnbalanceTradeWithThirdPartyAccountExampleExtended;
import java.util.List;

public interface UnbalanceTradeWithThirdPartyAccountDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    void insert(UnbalanceTradeWithThirdPartyAccount record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    int updateByPrimaryKey(UnbalanceTradeWithThirdPartyAccount record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    int updateByPrimaryKeySelective(UnbalanceTradeWithThirdPartyAccount record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    List selectByExample(UnbalanceTradeWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    UnbalanceTradeWithThirdPartyAccount selectByPrimaryKey(String id);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    int deleteByExample(UnbalanceTradeWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    int countByExample(UnbalanceTradeWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    int updateByExampleSelective(UnbalanceTradeWithThirdPartyAccount record, UnbalanceTradeWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    int updateByExample(UnbalanceTradeWithThirdPartyAccount record, UnbalanceTradeWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    List selectByExampleWithPage(UnbalanceTradeWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    List selectByExampleWithPage(UnbalanceTradeWithThirdPartyAccountExampleExtended exampleExtended);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    List getKeyBy(UnbalanceTradeWithThirdPartyAccountExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    String getNameSpace();

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    List getKeyBy(UnbalanceTradeWithThirdPartyAccountExampleExtended example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_TRADE_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:49 CST 2014
     */
    int countByExample(UnbalanceTradeWithThirdPartyAccountExampleExtended example);
}