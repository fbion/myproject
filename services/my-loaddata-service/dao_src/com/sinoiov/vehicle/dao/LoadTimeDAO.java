package com.sinoiov.vehicle.dao;

import com.sinoiov.vehicle.dao.beans.LoadTime;
import com.sinoiov.vehicle.dao.beans.LoadTimeExample;
import com.sinoiov.vehicle.dao.beans.LoadTimeExampleExtended;
import java.util.List;

public interface LoadTimeDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	void insert(LoadTime record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	int updateByPrimaryKey(LoadTime record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	int updateByPrimaryKeySelective(LoadTime record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	List selectByExample(LoadTimeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	LoadTime selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	int deleteByExample(LoadTimeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	int countByExample(LoadTimeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	int updateByExampleSelective(LoadTime record, LoadTimeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	int updateByExample(LoadTime record, LoadTimeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	List selectByExampleWithPage(LoadTimeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	List selectByExampleWithPage(LoadTimeExampleExtended exampleExtended);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	List getKeyBy(LoadTimeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	String getNameSpace();

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	List getKeyBy(LoadTimeExampleExtended example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	int countByExample(LoadTimeExampleExtended example);
}