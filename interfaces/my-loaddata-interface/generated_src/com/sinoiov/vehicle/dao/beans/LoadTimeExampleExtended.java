package com.sinoiov.vehicle.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadTimeExampleExtended implements Serializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	protected List oredCriteria;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	protected String selectedField;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	protected int skipNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	protected int endNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	protected int limitNum;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public LoadTimeExampleExtended() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	protected LoadTimeExampleExtended(LoadTimeExampleExtended example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setSelectedField(String selectedField) {
		this.selectedField = selectedField;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public String getSelectedField() {
		return selectedField;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String tableName() {
		return "TB_LOAD_TIME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static String daoInterface() {
		return "com.sinoiov.vehicle.dao.LoadTimeDAO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setSkipNum(int skipNum) {
		this.skipNum = skipNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int getSkipNum() {
		return this.skipNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setEndNum(int endNum) {
		this.endNum = endNum;
		this.limitNum = this.endNum - this.skipNum - 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int getEndNum() {
		return this.endNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public int getLimitNum() {
		return this.limitNum;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table TB_LOAD_TIME
	 * @abatorgenerated  Sun Jan 10 19:24:12 CST 2016
	 */
	public static class Criteria implements Serializable {
		protected List criteriaWithoutValue;
		protected List criteriaWithSingleValue;
		protected List criteriaWithListValue;
		protected List criteriaWithBetweenValue;

		protected Criteria() {
			super();
			criteriaWithoutValue = new ArrayList();
			criteriaWithSingleValue = new ArrayList();
			criteriaWithListValue = new ArrayList();
			criteriaWithBetweenValue = new ArrayList();
		}

		public boolean isValid() {
			return criteriaWithoutValue.size() > 0
					|| criteriaWithSingleValue.size() > 0
					|| criteriaWithListValue.size() > 0
					|| criteriaWithBetweenValue.size() > 0;
		}

		public List getCriteriaWithoutValue() {
			return criteriaWithoutValue;
		}

		public List getCriteriaWithSingleValue() {
			return criteriaWithSingleValue;
		}

		public List getCriteriaWithListValue() {
			return criteriaWithListValue;
		}

		public List getCriteriaWithBetweenValue() {
			return criteriaWithBetweenValue;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteriaWithoutValue.add(condition);
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition, List values,
				String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			List list = new ArrayList();
			list.add(value1);
			list.add(value2);
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", list);
			criteriaWithBetweenValue.add(map);
		}

		public Criteria andIdIsNull() {
			addCriterion("ID is null");
			return this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("ID is not null");
			return this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("ID =", value, "id");
			return this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("ID <>", value, "id");
			return this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("ID >", value, "id");
			return this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("ID >=", value, "id");
			return this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("ID <", value, "id");
			return this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("ID <=", value, "id");
			return this;
		}

		public Criteria andIdIn(List values) {
			addCriterion("ID in", values, "id");
			return this;
		}

		public Criteria andIdNotIn(List values) {
			addCriterion("ID not in", values, "id");
			return this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("ID between", value1, value2, "id");
			return this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("ID not between", value1, value2, "id");
			return this;
		}

		public Criteria andDataTypeIsNull() {
			addCriterion("DATA_TYPE is null");
			return this;
		}

		public Criteria andDataTypeIsNotNull() {
			addCriterion("DATA_TYPE is not null");
			return this;
		}

		public Criteria andDataTypeEqualTo(String value) {
			addCriterion("DATA_TYPE =", value, "dataType");
			return this;
		}

		public Criteria andDataTypeNotEqualTo(String value) {
			addCriterion("DATA_TYPE <>", value, "dataType");
			return this;
		}

		public Criteria andDataTypeGreaterThan(String value) {
			addCriterion("DATA_TYPE >", value, "dataType");
			return this;
		}

		public Criteria andDataTypeGreaterThanOrEqualTo(String value) {
			addCriterion("DATA_TYPE >=", value, "dataType");
			return this;
		}

		public Criteria andDataTypeLessThan(String value) {
			addCriterion("DATA_TYPE <", value, "dataType");
			return this;
		}

		public Criteria andDataTypeLessThanOrEqualTo(String value) {
			addCriterion("DATA_TYPE <=", value, "dataType");
			return this;
		}

		public Criteria andDataTypeLike(String value) {
			addCriterion("DATA_TYPE like", value, "dataType");
			return this;
		}

		public Criteria andDataTypeNotLike(String value) {
			addCriterion("DATA_TYPE not like", value, "dataType");
			return this;
		}

		public Criteria andDataTypeIn(List values) {
			addCriterion("DATA_TYPE in", values, "dataType");
			return this;
		}

		public Criteria andDataTypeNotIn(List values) {
			addCriterion("DATA_TYPE not in", values, "dataType");
			return this;
		}

		public Criteria andDataTypeBetween(String value1, String value2) {
			addCriterion("DATA_TYPE between", value1, value2, "dataType");
			return this;
		}

		public Criteria andDataTypeNotBetween(String value1, String value2) {
			addCriterion("DATA_TYPE not between", value1, value2, "dataType");
			return this;
		}

		public Criteria andFirstTimeIsNull() {
			addCriterion("FIRST_TIME is null");
			return this;
		}

		public Criteria andFirstTimeIsNotNull() {
			addCriterion("FIRST_TIME is not null");
			return this;
		}

		public Criteria andFirstTimeEqualTo(Date value) {
			addCriterion("FIRST_TIME =", value, "firstTime");
			return this;
		}

		public Criteria andFirstTimeNotEqualTo(Date value) {
			addCriterion("FIRST_TIME <>", value, "firstTime");
			return this;
		}

		public Criteria andFirstTimeGreaterThan(Date value) {
			addCriterion("FIRST_TIME >", value, "firstTime");
			return this;
		}

		public Criteria andFirstTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("FIRST_TIME >=", value, "firstTime");
			return this;
		}

		public Criteria andFirstTimeLessThan(Date value) {
			addCriterion("FIRST_TIME <", value, "firstTime");
			return this;
		}

		public Criteria andFirstTimeLessThanOrEqualTo(Date value) {
			addCriterion("FIRST_TIME <=", value, "firstTime");
			return this;
		}

		public Criteria andFirstTimeIn(List values) {
			addCriterion("FIRST_TIME in", values, "firstTime");
			return this;
		}

		public Criteria andFirstTimeNotIn(List values) {
			addCriterion("FIRST_TIME not in", values, "firstTime");
			return this;
		}

		public Criteria andFirstTimeBetween(Date value1, Date value2) {
			addCriterion("FIRST_TIME between", value1, value2, "firstTime");
			return this;
		}

		public Criteria andFirstTimeNotBetween(Date value1, Date value2) {
			addCriterion("FIRST_TIME not between", value1, value2, "firstTime");
			return this;
		}

		public Criteria andLastTimeIsNull() {
			addCriterion("LAST_TIME is null");
			return this;
		}

		public Criteria andLastTimeIsNotNull() {
			addCriterion("LAST_TIME is not null");
			return this;
		}

		public Criteria andLastTimeEqualTo(Date value) {
			addCriterion("LAST_TIME =", value, "lastTime");
			return this;
		}

		public Criteria andLastTimeNotEqualTo(Date value) {
			addCriterion("LAST_TIME <>", value, "lastTime");
			return this;
		}

		public Criteria andLastTimeGreaterThan(Date value) {
			addCriterion("LAST_TIME >", value, "lastTime");
			return this;
		}

		public Criteria andLastTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("LAST_TIME >=", value, "lastTime");
			return this;
		}

		public Criteria andLastTimeLessThan(Date value) {
			addCriterion("LAST_TIME <", value, "lastTime");
			return this;
		}

		public Criteria andLastTimeLessThanOrEqualTo(Date value) {
			addCriterion("LAST_TIME <=", value, "lastTime");
			return this;
		}

		public Criteria andLastTimeIn(List values) {
			addCriterion("LAST_TIME in", values, "lastTime");
			return this;
		}

		public Criteria andLastTimeNotIn(List values) {
			addCriterion("LAST_TIME not in", values, "lastTime");
			return this;
		}

		public Criteria andLastTimeBetween(Date value1, Date value2) {
			addCriterion("LAST_TIME between", value1, value2, "lastTime");
			return this;
		}

		public Criteria andLastTimeNotBetween(Date value1, Date value2) {
			addCriterion("LAST_TIME not between", value1, value2, "lastTime");
			return this;
		}

		public Criteria andLoadCountIsNull() {
			addCriterion("LOAD_COUNT is null");
			return this;
		}

		public Criteria andLoadCountIsNotNull() {
			addCriterion("LOAD_COUNT is not null");
			return this;
		}

		public Criteria andLoadCountEqualTo(Integer value) {
			addCriterion("LOAD_COUNT =", value, "loadCount");
			return this;
		}

		public Criteria andLoadCountNotEqualTo(Integer value) {
			addCriterion("LOAD_COUNT <>", value, "loadCount");
			return this;
		}

		public Criteria andLoadCountGreaterThan(Integer value) {
			addCriterion("LOAD_COUNT >", value, "loadCount");
			return this;
		}

		public Criteria andLoadCountGreaterThanOrEqualTo(Integer value) {
			addCriterion("LOAD_COUNT >=", value, "loadCount");
			return this;
		}

		public Criteria andLoadCountLessThan(Integer value) {
			addCriterion("LOAD_COUNT <", value, "loadCount");
			return this;
		}

		public Criteria andLoadCountLessThanOrEqualTo(Integer value) {
			addCriterion("LOAD_COUNT <=", value, "loadCount");
			return this;
		}

		public Criteria andLoadCountIn(List values) {
			addCriterion("LOAD_COUNT in", values, "loadCount");
			return this;
		}

		public Criteria andLoadCountNotIn(List values) {
			addCriterion("LOAD_COUNT not in", values, "loadCount");
			return this;
		}

		public Criteria andLoadCountBetween(Integer value1, Integer value2) {
			addCriterion("LOAD_COUNT between", value1, value2, "loadCount");
			return this;
		}

		public Criteria andLoadCountNotBetween(Integer value1, Integer value2) {
			addCriterion("LOAD_COUNT not between", value1, value2, "loadCount");
			return this;
		}

		public Criteria andLoadSuccessCountIsNull() {
			addCriterion("LOAD_SUCCESS_COUNT is null");
			return this;
		}

		public Criteria andLoadSuccessCountIsNotNull() {
			addCriterion("LOAD_SUCCESS_COUNT is not null");
			return this;
		}

		public Criteria andLoadSuccessCountEqualTo(Integer value) {
			addCriterion("LOAD_SUCCESS_COUNT =", value, "loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountNotEqualTo(Integer value) {
			addCriterion("LOAD_SUCCESS_COUNT <>", value, "loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountGreaterThan(Integer value) {
			addCriterion("LOAD_SUCCESS_COUNT >", value, "loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountGreaterThanOrEqualTo(Integer value) {
			addCriterion("LOAD_SUCCESS_COUNT >=", value, "loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountLessThan(Integer value) {
			addCriterion("LOAD_SUCCESS_COUNT <", value, "loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountLessThanOrEqualTo(Integer value) {
			addCriterion("LOAD_SUCCESS_COUNT <=", value, "loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountIn(List values) {
			addCriterion("LOAD_SUCCESS_COUNT in", values, "loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountNotIn(List values) {
			addCriterion("LOAD_SUCCESS_COUNT not in", values,
					"loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountBetween(Integer value1,
				Integer value2) {
			addCriterion("LOAD_SUCCESS_COUNT between", value1, value2,
					"loadSuccessCount");
			return this;
		}

		public Criteria andLoadSuccessCountNotBetween(Integer value1,
				Integer value2) {
			addCriterion("LOAD_SUCCESS_COUNT not between", value1, value2,
					"loadSuccessCount");
			return this;
		}

		public Criteria andLoadFailCountIsNull() {
			addCriterion("LOAD_FAIL_COUNT is null");
			return this;
		}

		public Criteria andLoadFailCountIsNotNull() {
			addCriterion("LOAD_FAIL_COUNT is not null");
			return this;
		}

		public Criteria andLoadFailCountEqualTo(Integer value) {
			addCriterion("LOAD_FAIL_COUNT =", value, "loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountNotEqualTo(Integer value) {
			addCriterion("LOAD_FAIL_COUNT <>", value, "loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountGreaterThan(Integer value) {
			addCriterion("LOAD_FAIL_COUNT >", value, "loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountGreaterThanOrEqualTo(Integer value) {
			addCriterion("LOAD_FAIL_COUNT >=", value, "loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountLessThan(Integer value) {
			addCriterion("LOAD_FAIL_COUNT <", value, "loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountLessThanOrEqualTo(Integer value) {
			addCriterion("LOAD_FAIL_COUNT <=", value, "loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountIn(List values) {
			addCriterion("LOAD_FAIL_COUNT in", values, "loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountNotIn(List values) {
			addCriterion("LOAD_FAIL_COUNT not in", values, "loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountBetween(Integer value1, Integer value2) {
			addCriterion("LOAD_FAIL_COUNT between", value1, value2,
					"loadFailCount");
			return this;
		}

		public Criteria andLoadFailCountNotBetween(Integer value1,
				Integer value2) {
			addCriterion("LOAD_FAIL_COUNT not between", value1, value2,
					"loadFailCount");
			return this;
		}
	}
}