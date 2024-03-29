package com.sinoiov.vehicle.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.sinoiov.vehicle.dao.beans.SimpleCode;
import com.sinoiov.vehicle.dao.beans.SimpleCodeExample;
import com.sinoiov.vehicle.dao.beans.SimpleCodeExampleExtended;

//@Repository(value="simpleCodeDAO")
//@Service("SimpleCodeDAO")
public class SimpleCodeDAOImpl extends SqlMapClientDaoSupport implements SimpleCodeDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public SimpleCodeDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void insert(SimpleCode record) {
		getSqlMapClientTemplate().insert(
				"TB_SIMPLE_CODE.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int updateByPrimaryKey(SimpleCode record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_SIMPLE_CODE.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int updateByPrimaryKeySelective(SimpleCode record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_SIMPLE_CODE.abatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public List selectByExample(SimpleCodeExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_SIMPLE_CODE.abatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public SimpleCode selectByPrimaryKey(String id) {
		SimpleCode key = new SimpleCode() {
		};
		key.setId(id);
		SimpleCode record = (SimpleCode) getSqlMapClientTemplate()
				.queryForObject(
						"TB_SIMPLE_CODE.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int deleteByExample(SimpleCodeExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_SIMPLE_CODE.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int deleteByPrimaryKey(String id) {
		SimpleCode key = new SimpleCode() {
		};
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"TB_SIMPLE_CODE.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int countByExample(SimpleCodeExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_SIMPLE_CODE.abatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int updateByExampleSelective(SimpleCode record,
			SimpleCodeExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_SIMPLE_CODE.abatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int updateByExample(SimpleCode record, SimpleCodeExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_SIMPLE_CODE.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public List selectByExampleWithPage(SimpleCodeExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_SIMPLE_CODE.abatorgenerated_selectByExamplePage", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public List selectByExampleWithPage(
			SimpleCodeExampleExtended exampleExtended) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_SIMPLE_CODE.abatorgenerated_selectByExampleExtendedPage",
				exampleExtended);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public List getKeyBy(SimpleCodeExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_SIMPLE_CODE.abatorgenerated_selectKeyBy", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getNameSpace() {
		return "TB_SIMPLE_CODE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public List getKeyBy(SimpleCodeExampleExtended example) {
		List list = getSqlMapClientTemplate().queryForList(
				"TB_SIMPLE_CODE.abatorgenerated_selectKeyByExtended", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int countByExample(SimpleCodeExampleExtended example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_SIMPLE_CODE.abatorgenerated_countByExampleExtended",
				example);
		return count.intValue();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table TB_SIMPLE_CODE
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	private static class UpdateByExampleParms extends SimpleCodeExample {
		private Object record;

		public UpdateByExampleParms(Object record, SimpleCodeExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}