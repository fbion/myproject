package com.ctfo.upp.accountservice.account.dao;

import com.ctfo.account.dao.beans.UnbalanceDetailWithThirdPartyAccount;
import com.ctfo.account.dao.beans.UnbalanceDetailWithThirdPartyAccountExample;
import com.ctfo.account.dao.beans.UnbalanceDetailWithThirdPartyAccountExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UnbalanceDetailWithThirdPartyAccountDAOImpl extends SqlMapClientDaoSupport implements UnbalanceDetailWithThirdPartyAccountDAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public UnbalanceDetailWithThirdPartyAccountDAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public void insert(UnbalanceDetailWithThirdPartyAccount record) {
        getSqlMapClientTemplate().insert("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public int updateByPrimaryKey(UnbalanceDetailWithThirdPartyAccount record) {
        int rows = getSqlMapClientTemplate().update("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public int updateByPrimaryKeySelective(UnbalanceDetailWithThirdPartyAccount record) {
        int rows = getSqlMapClientTemplate().update("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public List selectByExample(UnbalanceDetailWithThirdPartyAccountExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public UnbalanceDetailWithThirdPartyAccount selectByPrimaryKey(String id) {
        UnbalanceDetailWithThirdPartyAccount key = new UnbalanceDetailWithThirdPartyAccount(){};
        key.setId(id);
        UnbalanceDetailWithThirdPartyAccount record = (UnbalanceDetailWithThirdPartyAccount) getSqlMapClientTemplate().queryForObject("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public int deleteByExample(UnbalanceDetailWithThirdPartyAccountExample example) {
        int rows = getSqlMapClientTemplate().delete("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public int deleteByPrimaryKey(String id) {
        UnbalanceDetailWithThirdPartyAccount key = new UnbalanceDetailWithThirdPartyAccount(){};
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public int countByExample(UnbalanceDetailWithThirdPartyAccountExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public int updateByExampleSelective(UnbalanceDetailWithThirdPartyAccount record, UnbalanceDetailWithThirdPartyAccountExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public int updateByExample(UnbalanceDetailWithThirdPartyAccount record, UnbalanceDetailWithThirdPartyAccountExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public List selectByExampleWithPage(UnbalanceDetailWithThirdPartyAccountExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_selectByExamplePage", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public List selectByExampleWithPage(UnbalanceDetailWithThirdPartyAccountExampleExtended exampleExtended) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_selectByExampleExtendedPage", exampleExtended);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public List getKeyBy(UnbalanceDetailWithThirdPartyAccountExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_selectKeyBy", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public String getNameSpace() {
         return "TB_UPP_CHECK_ACC_DIVERGENCE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public List getKeyBy(UnbalanceDetailWithThirdPartyAccountExampleExtended example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_selectKeyByExtended", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    public int countByExample(UnbalanceDetailWithThirdPartyAccountExampleExtended example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("TB_UPP_CHECK_ACC_DIVERGENCE.abatorgenerated_countByExampleExtended", example);
        return count.intValue();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table UPP.TB_UPP_CHECK_ACC_DIVERGENCE
     *
     * @abatorgenerated Mon Nov 03 13:56:48 CST 2014
     */
    private static class UpdateByExampleParms extends UnbalanceDetailWithThirdPartyAccountExample {
        private Object record;

        public UpdateByExampleParms(Object record, UnbalanceDetailWithThirdPartyAccountExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}