package com.ctfo.upp.accountservice.payment.dao;

import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApprovalExample;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApprovalExampleExtended;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class OfflineRechargeApplyApprovalDAOImpl extends SqlMapClientDaoSupport implements OfflineRechargeApplyApprovalDAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public OfflineRechargeApplyApprovalDAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public void insert(OfflineRechargeApplyApproval record) {
        getSqlMapClientTemplate().insert("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public int updateByPrimaryKey(OfflineRechargeApplyApproval record) {
        int rows = getSqlMapClientTemplate().update("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public int updateByPrimaryKeySelective(OfflineRechargeApplyApproval record) {
        int rows = getSqlMapClientTemplate().update("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public List selectByExample(OfflineRechargeApplyApprovalExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public OfflineRechargeApplyApproval selectByPrimaryKey(String id) {
        OfflineRechargeApplyApproval key = new OfflineRechargeApplyApproval(){};
        key.setId(id);
        OfflineRechargeApplyApproval record = (OfflineRechargeApplyApproval) getSqlMapClientTemplate().queryForObject("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public int deleteByExample(OfflineRechargeApplyApprovalExample example) {
        int rows = getSqlMapClientTemplate().delete("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public int deleteByPrimaryKey(String id) {
        OfflineRechargeApplyApproval key = new OfflineRechargeApplyApproval(){};
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public int countByExample(OfflineRechargeApplyApprovalExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public int updateByExampleSelective(OfflineRechargeApplyApproval record, OfflineRechargeApplyApprovalExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public int updateByExample(OfflineRechargeApplyApproval record, OfflineRechargeApplyApprovalExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public List selectByExampleWithPage(OfflineRechargeApplyApprovalExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_selectByExamplePage", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public List selectByExampleWithPage(OfflineRechargeApplyApprovalExampleExtended exampleExtended) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_selectByExampleExtendedPage", exampleExtended);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public List getKeyBy(OfflineRechargeApplyApprovalExample example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_selectKeyBy", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public String getNameSpace() {
         return "TB_UPP_OFFLINE_TRADE_INFO";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public List getKeyBy(OfflineRechargeApplyApprovalExampleExtended example) {
        List list = getSqlMapClientTemplate().queryForList("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_selectKeyByExtended", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    public int countByExample(OfflineRechargeApplyApprovalExampleExtended example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("TB_UPP_OFFLINE_TRADE_INFO.abatorgenerated_countByExampleExtended", example);
        return count.intValue();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table UPP.TB_UPP_OFFLINE_TRADE_INFO
     *
     * @abatorgenerated Mon Nov 03 11:52:23 CST 2014
     */
    private static class UpdateByExampleParms extends OfflineRechargeApplyApprovalExample {
        private Object record;

        public UpdateByExampleParms(Object record, OfflineRechargeApplyApprovalExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}