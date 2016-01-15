package com.ctfo.upp.accountservice.account.dao;

import com.ctfo.payment.dao.beans.AccountingMonth;
import com.ctfo.payment.dao.beans.AccountingMonthExample;
import com.ctfo.payment.dao.beans.AccountingMonthExampleExtended;
import java.util.List;

public interface AccountingMonthDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    void insert(AccountingMonth record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    int updateByPrimaryKey(AccountingMonth record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    int updateByPrimaryKeySelective(AccountingMonth record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    List selectByExample(AccountingMonthExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    AccountingMonth selectByPrimaryKey(String id);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    int deleteByExample(AccountingMonthExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    int countByExample(AccountingMonthExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    int updateByExampleSelective(AccountingMonth record, AccountingMonthExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    int updateByExample(AccountingMonth record, AccountingMonthExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    List selectByExampleWithPage(AccountingMonthExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    List selectByExampleWithPage(AccountingMonthExampleExtended exampleExtended);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    List getKeyBy(AccountingMonthExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    String getNameSpace();

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    List getKeyBy(AccountingMonthExampleExtended example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_BOOK_MONTH_ACCOUNT
     *
     * @abatorgenerated Thu Mar 19 09:14:44 CST 2015
     */
    int countByExample(AccountingMonthExampleExtended example);
}