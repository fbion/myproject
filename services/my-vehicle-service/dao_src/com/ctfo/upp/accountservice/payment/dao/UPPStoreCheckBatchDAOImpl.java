package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.UPPStoreCheckBatch;
import com.ctfo.payment.dao.beans.UPPStoreCheckBatchExample;
import com.ctfo.payment.dao.beans.UPPStoreCheckBatchExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UPPStoreCheckBatchDAOImpl extends SqlMapClientDaoSupport implements UPPStoreCheckBatchDAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public UPPStoreCheckBatchDAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public void insert(UPPStoreCheckBatch record) {
        getSqlMapClientTemplate().insert("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public int updateByPrimaryKey(UPPStoreCheckBatch record) {
        int rows = getSqlMapClientTemplate().update("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public int updateByPrimaryKeySelective(UPPStoreCheckBatch record) {
        int rows = getSqlMapClientTemplate().update("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public List selectByExample(UPPStoreCheckBatchExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public UPPStoreCheckBatch selectByPrimaryKey(String id) {
        UPPStoreCheckBatch key = new UPPStoreCheckBatch(){};
        key.setId(id);
        UPPStoreCheckBatch record = (UPPStoreCheckBatch) getSqlMapClientTemplate().queryForObject("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public int deleteByExample(UPPStoreCheckBatchExample example) {
        int rows = getSqlMapClientTemplate().delete("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public int deleteByPrimaryKey(String id) {
        UPPStoreCheckBatch key = new UPPStoreCheckBatch(){};
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public int countByExample(UPPStoreCheckBatchExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public int updateByExampleSelective(UPPStoreCheckBatch record, UPPStoreCheckBatchExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public int updateByExample(UPPStoreCheckBatch record, UPPStoreCheckBatchExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public List selectByExampleWithPage(UPPStoreCheckBatchExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_selectByExamplePage", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public List selectByExampleWithPage(UPPStoreCheckBatchExampleExtended exampleExtended) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_selectByExampleExtendedPage", exampleExtended);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public List getKeyBy(UPPStoreCheckBatchExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_selectKeyBy", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public String getNameSpace() {
         return "TB_UPP_STORE_CHECK_BATCH";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public List getKeyBy(UPPStoreCheckBatchExampleExtended example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_selectKeyByExtended", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    public int countByExample(UPPStoreCheckBatchExampleExtended example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("TB_UPP_STORE_CHECK_BATCH.abatorgenerated_countByExampleExtended", example);
        return count.intValue();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table UPP.TB_UPP_STORE_CHECK_BATCH
     *
     * @abatorgenerated Sat Jan 31 10:59:05 CST 2015
     */
    private static class UpdateByExampleParms extends UPPStoreCheckBatchExample {
        private Object record;

        public UpdateByExampleParms(Object record, UPPStoreCheckBatchExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}