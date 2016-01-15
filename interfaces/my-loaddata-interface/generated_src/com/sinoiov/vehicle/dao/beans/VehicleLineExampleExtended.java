package com.sinoiov.vehicle.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleLineExampleExtended implements Serializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected List oredCriteria;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected String selectedField;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int skipNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int endNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int limitNum;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public VehicleLineExampleExtended() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected VehicleLineExampleExtended(VehicleLineExampleExtended example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setSelectedField(String selectedField) {
		this.selectedField = selectedField;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getSelectedField() {
		return selectedField;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String tableName() {
		return "TB_VEHICLE_LINE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String daoInterface() {
		return "com.sinoiov.vehicle.dao.VehicleLineDAO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setSkipNum(int skipNum) {
		this.skipNum = skipNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getSkipNum() {
		return this.skipNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setEndNum(int endNum) {
		this.endNum = endNum;
		this.limitNum = this.endNum - this.skipNum - 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getEndNum() {
		return this.endNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getLimitNum() {
		return this.limitNum;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table TB_VEHICLE_LINE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
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

		public Criteria andIdEqualTo(String value) {
			addCriterion("ID =", value, "id");
			return this;
		}

		public Criteria andIdNotEqualTo(String value) {
			addCriterion("ID <>", value, "id");
			return this;
		}

		public Criteria andIdGreaterThan(String value) {
			addCriterion("ID >", value, "id");
			return this;
		}

		public Criteria andIdGreaterThanOrEqualTo(String value) {
			addCriterion("ID >=", value, "id");
			return this;
		}

		public Criteria andIdLessThan(String value) {
			addCriterion("ID <", value, "id");
			return this;
		}

		public Criteria andIdLessThanOrEqualTo(String value) {
			addCriterion("ID <=", value, "id");
			return this;
		}

		public Criteria andIdLike(String value) {
			addCriterion("ID like", value, "id");
			return this;
		}

		public Criteria andIdNotLike(String value) {
			addCriterion("ID not like", value, "id");
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

		public Criteria andIdBetween(String value1, String value2) {
			addCriterion("ID between", value1, value2, "id");
			return this;
		}

		public Criteria andIdNotBetween(String value1, String value2) {
			addCriterion("ID not between", value1, value2, "id");
			return this;
		}

		public Criteria andVIdIsNull() {
			addCriterion("V_ID is null");
			return this;
		}

		public Criteria andVIdIsNotNull() {
			addCriterion("V_ID is not null");
			return this;
		}

		public Criteria andVIdEqualTo(String value) {
			addCriterion("V_ID =", value, "vId");
			return this;
		}

		public Criteria andVIdNotEqualTo(String value) {
			addCriterion("V_ID <>", value, "vId");
			return this;
		}

		public Criteria andVIdGreaterThan(String value) {
			addCriterion("V_ID >", value, "vId");
			return this;
		}

		public Criteria andVIdGreaterThanOrEqualTo(String value) {
			addCriterion("V_ID >=", value, "vId");
			return this;
		}

		public Criteria andVIdLessThan(String value) {
			addCriterion("V_ID <", value, "vId");
			return this;
		}

		public Criteria andVIdLessThanOrEqualTo(String value) {
			addCriterion("V_ID <=", value, "vId");
			return this;
		}

		public Criteria andVIdLike(String value) {
			addCriterion("V_ID like", value, "vId");
			return this;
		}

		public Criteria andVIdNotLike(String value) {
			addCriterion("V_ID not like", value, "vId");
			return this;
		}

		public Criteria andVIdIn(List values) {
			addCriterion("V_ID in", values, "vId");
			return this;
		}

		public Criteria andVIdNotIn(List values) {
			addCriterion("V_ID not in", values, "vId");
			return this;
		}

		public Criteria andVIdBetween(String value1, String value2) {
			addCriterion("V_ID between", value1, value2, "vId");
			return this;
		}

		public Criteria andVIdNotBetween(String value1, String value2) {
			addCriterion("V_ID not between", value1, value2, "vId");
			return this;
		}

		public Criteria andBasecampIsNull() {
			addCriterion("BASECAMP is null");
			return this;
		}

		public Criteria andBasecampIsNotNull() {
			addCriterion("BASECAMP is not null");
			return this;
		}

		public Criteria andBasecampEqualTo(String value) {
			addCriterion("BASECAMP =", value, "basecamp");
			return this;
		}

		public Criteria andBasecampNotEqualTo(String value) {
			addCriterion("BASECAMP <>", value, "basecamp");
			return this;
		}

		public Criteria andBasecampGreaterThan(String value) {
			addCriterion("BASECAMP >", value, "basecamp");
			return this;
		}

		public Criteria andBasecampGreaterThanOrEqualTo(String value) {
			addCriterion("BASECAMP >=", value, "basecamp");
			return this;
		}

		public Criteria andBasecampLessThan(String value) {
			addCriterion("BASECAMP <", value, "basecamp");
			return this;
		}

		public Criteria andBasecampLessThanOrEqualTo(String value) {
			addCriterion("BASECAMP <=", value, "basecamp");
			return this;
		}

		public Criteria andBasecampLike(String value) {
			addCriterion("BASECAMP like", value, "basecamp");
			return this;
		}

		public Criteria andBasecampNotLike(String value) {
			addCriterion("BASECAMP not like", value, "basecamp");
			return this;
		}

		public Criteria andBasecampIn(List values) {
			addCriterion("BASECAMP in", values, "basecamp");
			return this;
		}

		public Criteria andBasecampNotIn(List values) {
			addCriterion("BASECAMP not in", values, "basecamp");
			return this;
		}

		public Criteria andBasecampBetween(String value1, String value2) {
			addCriterion("BASECAMP between", value1, value2, "basecamp");
			return this;
		}

		public Criteria andBasecampNotBetween(String value1, String value2) {
			addCriterion("BASECAMP not between", value1, value2, "basecamp");
			return this;
		}

		public Criteria andBasecampNameIsNull() {
			addCriterion("BASECAMP_NAME is null");
			return this;
		}

		public Criteria andBasecampNameIsNotNull() {
			addCriterion("BASECAMP_NAME is not null");
			return this;
		}

		public Criteria andBasecampNameEqualTo(String value) {
			addCriterion("BASECAMP_NAME =", value, "basecampName");
			return this;
		}

		public Criteria andBasecampNameNotEqualTo(String value) {
			addCriterion("BASECAMP_NAME <>", value, "basecampName");
			return this;
		}

		public Criteria andBasecampNameGreaterThan(String value) {
			addCriterion("BASECAMP_NAME >", value, "basecampName");
			return this;
		}

		public Criteria andBasecampNameGreaterThanOrEqualTo(String value) {
			addCriterion("BASECAMP_NAME >=", value, "basecampName");
			return this;
		}

		public Criteria andBasecampNameLessThan(String value) {
			addCriterion("BASECAMP_NAME <", value, "basecampName");
			return this;
		}

		public Criteria andBasecampNameLessThanOrEqualTo(String value) {
			addCriterion("BASECAMP_NAME <=", value, "basecampName");
			return this;
		}

		public Criteria andBasecampNameLike(String value) {
			addCriterion("BASECAMP_NAME like", value, "basecampName");
			return this;
		}

		public Criteria andBasecampNameNotLike(String value) {
			addCriterion("BASECAMP_NAME not like", value, "basecampName");
			return this;
		}

		public Criteria andBasecampNameIn(List values) {
			addCriterion("BASECAMP_NAME in", values, "basecampName");
			return this;
		}

		public Criteria andBasecampNameNotIn(List values) {
			addCriterion("BASECAMP_NAME not in", values, "basecampName");
			return this;
		}

		public Criteria andBasecampNameBetween(String value1, String value2) {
			addCriterion("BASECAMP_NAME between", value1, value2,
					"basecampName");
			return this;
		}

		public Criteria andBasecampNameNotBetween(String value1, String value2) {
			addCriterion("BASECAMP_NAME not between", value1, value2,
					"basecampName");
			return this;
		}

		public Criteria andLineCodeIsNull() {
			addCriterion("LINE_CODE is null");
			return this;
		}

		public Criteria andLineCodeIsNotNull() {
			addCriterion("LINE_CODE is not null");
			return this;
		}

		public Criteria andLineCodeEqualTo(String value) {
			addCriterion("LINE_CODE =", value, "lineCode");
			return this;
		}

		public Criteria andLineCodeNotEqualTo(String value) {
			addCriterion("LINE_CODE <>", value, "lineCode");
			return this;
		}

		public Criteria andLineCodeGreaterThan(String value) {
			addCriterion("LINE_CODE >", value, "lineCode");
			return this;
		}

		public Criteria andLineCodeGreaterThanOrEqualTo(String value) {
			addCriterion("LINE_CODE >=", value, "lineCode");
			return this;
		}

		public Criteria andLineCodeLessThan(String value) {
			addCriterion("LINE_CODE <", value, "lineCode");
			return this;
		}

		public Criteria andLineCodeLessThanOrEqualTo(String value) {
			addCriterion("LINE_CODE <=", value, "lineCode");
			return this;
		}

		public Criteria andLineCodeLike(String value) {
			addCriterion("LINE_CODE like", value, "lineCode");
			return this;
		}

		public Criteria andLineCodeNotLike(String value) {
			addCriterion("LINE_CODE not like", value, "lineCode");
			return this;
		}

		public Criteria andLineCodeIn(List values) {
			addCriterion("LINE_CODE in", values, "lineCode");
			return this;
		}

		public Criteria andLineCodeNotIn(List values) {
			addCriterion("LINE_CODE not in", values, "lineCode");
			return this;
		}

		public Criteria andLineCodeBetween(String value1, String value2) {
			addCriterion("LINE_CODE between", value1, value2, "lineCode");
			return this;
		}

		public Criteria andLineCodeNotBetween(String value1, String value2) {
			addCriterion("LINE_CODE not between", value1, value2, "lineCode");
			return this;
		}

		public Criteria andLineNameIsNull() {
			addCriterion("LINE_NAME is null");
			return this;
		}

		public Criteria andLineNameIsNotNull() {
			addCriterion("LINE_NAME is not null");
			return this;
		}

		public Criteria andLineNameEqualTo(String value) {
			addCriterion("LINE_NAME =", value, "lineName");
			return this;
		}

		public Criteria andLineNameNotEqualTo(String value) {
			addCriterion("LINE_NAME <>", value, "lineName");
			return this;
		}

		public Criteria andLineNameGreaterThan(String value) {
			addCriterion("LINE_NAME >", value, "lineName");
			return this;
		}

		public Criteria andLineNameGreaterThanOrEqualTo(String value) {
			addCriterion("LINE_NAME >=", value, "lineName");
			return this;
		}

		public Criteria andLineNameLessThan(String value) {
			addCriterion("LINE_NAME <", value, "lineName");
			return this;
		}

		public Criteria andLineNameLessThanOrEqualTo(String value) {
			addCriterion("LINE_NAME <=", value, "lineName");
			return this;
		}

		public Criteria andLineNameLike(String value) {
			addCriterion("LINE_NAME like", value, "lineName");
			return this;
		}

		public Criteria andLineNameNotLike(String value) {
			addCriterion("LINE_NAME not like", value, "lineName");
			return this;
		}

		public Criteria andLineNameIn(List values) {
			addCriterion("LINE_NAME in", values, "lineName");
			return this;
		}

		public Criteria andLineNameNotIn(List values) {
			addCriterion("LINE_NAME not in", values, "lineName");
			return this;
		}

		public Criteria andLineNameBetween(String value1, String value2) {
			addCriterion("LINE_NAME between", value1, value2, "lineName");
			return this;
		}

		public Criteria andLineNameNotBetween(String value1, String value2) {
			addCriterion("LINE_NAME not between", value1, value2, "lineName");
			return this;
		}

		public Criteria andStartPositionIsNull() {
			addCriterion("START_POSITION is null");
			return this;
		}

		public Criteria andStartPositionIsNotNull() {
			addCriterion("START_POSITION is not null");
			return this;
		}

		public Criteria andStartPositionEqualTo(String value) {
			addCriterion("START_POSITION =", value, "startPosition");
			return this;
		}

		public Criteria andStartPositionNotEqualTo(String value) {
			addCriterion("START_POSITION <>", value, "startPosition");
			return this;
		}

		public Criteria andStartPositionGreaterThan(String value) {
			addCriterion("START_POSITION >", value, "startPosition");
			return this;
		}

		public Criteria andStartPositionGreaterThanOrEqualTo(String value) {
			addCriterion("START_POSITION >=", value, "startPosition");
			return this;
		}

		public Criteria andStartPositionLessThan(String value) {
			addCriterion("START_POSITION <", value, "startPosition");
			return this;
		}

		public Criteria andStartPositionLessThanOrEqualTo(String value) {
			addCriterion("START_POSITION <=", value, "startPosition");
			return this;
		}

		public Criteria andStartPositionLike(String value) {
			addCriterion("START_POSITION like", value, "startPosition");
			return this;
		}

		public Criteria andStartPositionNotLike(String value) {
			addCriterion("START_POSITION not like", value, "startPosition");
			return this;
		}

		public Criteria andStartPositionIn(List values) {
			addCriterion("START_POSITION in", values, "startPosition");
			return this;
		}

		public Criteria andStartPositionNotIn(List values) {
			addCriterion("START_POSITION not in", values, "startPosition");
			return this;
		}

		public Criteria andStartPositionBetween(String value1, String value2) {
			addCriterion("START_POSITION between", value1, value2,
					"startPosition");
			return this;
		}

		public Criteria andStartPositionNotBetween(String value1, String value2) {
			addCriterion("START_POSITION not between", value1, value2,
					"startPosition");
			return this;
		}

		public Criteria andEndPostitionIsNull() {
			addCriterion("END_POSTITION is null");
			return this;
		}

		public Criteria andEndPostitionIsNotNull() {
			addCriterion("END_POSTITION is not null");
			return this;
		}

		public Criteria andEndPostitionEqualTo(String value) {
			addCriterion("END_POSTITION =", value, "endPostition");
			return this;
		}

		public Criteria andEndPostitionNotEqualTo(String value) {
			addCriterion("END_POSTITION <>", value, "endPostition");
			return this;
		}

		public Criteria andEndPostitionGreaterThan(String value) {
			addCriterion("END_POSTITION >", value, "endPostition");
			return this;
		}

		public Criteria andEndPostitionGreaterThanOrEqualTo(String value) {
			addCriterion("END_POSTITION >=", value, "endPostition");
			return this;
		}

		public Criteria andEndPostitionLessThan(String value) {
			addCriterion("END_POSTITION <", value, "endPostition");
			return this;
		}

		public Criteria andEndPostitionLessThanOrEqualTo(String value) {
			addCriterion("END_POSTITION <=", value, "endPostition");
			return this;
		}

		public Criteria andEndPostitionLike(String value) {
			addCriterion("END_POSTITION like", value, "endPostition");
			return this;
		}

		public Criteria andEndPostitionNotLike(String value) {
			addCriterion("END_POSTITION not like", value, "endPostition");
			return this;
		}

		public Criteria andEndPostitionIn(List values) {
			addCriterion("END_POSTITION in", values, "endPostition");
			return this;
		}

		public Criteria andEndPostitionNotIn(List values) {
			addCriterion("END_POSTITION not in", values, "endPostition");
			return this;
		}

		public Criteria andEndPostitionBetween(String value1, String value2) {
			addCriterion("END_POSTITION between", value1, value2,
					"endPostition");
			return this;
		}

		public Criteria andEndPostitionNotBetween(String value1, String value2) {
			addCriterion("END_POSTITION not between", value1, value2,
					"endPostition");
			return this;
		}

		public Criteria andCountIsNull() {
			addCriterion("COUNT is null");
			return this;
		}

		public Criteria andCountIsNotNull() {
			addCriterion("COUNT is not null");
			return this;
		}

		public Criteria andCountEqualTo(Long value) {
			addCriterion("COUNT =", value, "count");
			return this;
		}

		public Criteria andCountNotEqualTo(Long value) {
			addCriterion("COUNT <>", value, "count");
			return this;
		}

		public Criteria andCountGreaterThan(Long value) {
			addCriterion("COUNT >", value, "count");
			return this;
		}

		public Criteria andCountGreaterThanOrEqualTo(Long value) {
			addCriterion("COUNT >=", value, "count");
			return this;
		}

		public Criteria andCountLessThan(Long value) {
			addCriterion("COUNT <", value, "count");
			return this;
		}

		public Criteria andCountLessThanOrEqualTo(Long value) {
			addCriterion("COUNT <=", value, "count");
			return this;
		}

		public Criteria andCountIn(List values) {
			addCriterion("COUNT in", values, "count");
			return this;
		}

		public Criteria andCountNotIn(List values) {
			addCriterion("COUNT not in", values, "count");
			return this;
		}

		public Criteria andCountBetween(Long value1, Long value2) {
			addCriterion("COUNT between", value1, value2, "count");
			return this;
		}

		public Criteria andCountNotBetween(Long value1, Long value2) {
			addCriterion("COUNT not between", value1, value2, "count");
			return this;
		}

		public Criteria andAverageDurationIsNull() {
			addCriterion("AVERAGE_DURATION is null");
			return this;
		}

		public Criteria andAverageDurationIsNotNull() {
			addCriterion("AVERAGE_DURATION is not null");
			return this;
		}

		public Criteria andAverageDurationEqualTo(Long value) {
			addCriterion("AVERAGE_DURATION =", value, "averageDuration");
			return this;
		}

		public Criteria andAverageDurationNotEqualTo(Long value) {
			addCriterion("AVERAGE_DURATION <>", value, "averageDuration");
			return this;
		}

		public Criteria andAverageDurationGreaterThan(Long value) {
			addCriterion("AVERAGE_DURATION >", value, "averageDuration");
			return this;
		}

		public Criteria andAverageDurationGreaterThanOrEqualTo(Long value) {
			addCriterion("AVERAGE_DURATION >=", value, "averageDuration");
			return this;
		}

		public Criteria andAverageDurationLessThan(Long value) {
			addCriterion("AVERAGE_DURATION <", value, "averageDuration");
			return this;
		}

		public Criteria andAverageDurationLessThanOrEqualTo(Long value) {
			addCriterion("AVERAGE_DURATION <=", value, "averageDuration");
			return this;
		}

		public Criteria andAverageDurationIn(List values) {
			addCriterion("AVERAGE_DURATION in", values, "averageDuration");
			return this;
		}

		public Criteria andAverageDurationNotIn(List values) {
			addCriterion("AVERAGE_DURATION not in", values, "averageDuration");
			return this;
		}

		public Criteria andAverageDurationBetween(Long value1, Long value2) {
			addCriterion("AVERAGE_DURATION between", value1, value2,
					"averageDuration");
			return this;
		}

		public Criteria andAverageDurationNotBetween(Long value1, Long value2) {
			addCriterion("AVERAGE_DURATION not between", value1, value2,
					"averageDuration");
			return this;
		}

		public Criteria andBusinessAreaIsNull() {
			addCriterion("BUSINESS_AREA is null");
			return this;
		}

		public Criteria andBusinessAreaIsNotNull() {
			addCriterion("BUSINESS_AREA is not null");
			return this;
		}

		public Criteria andBusinessAreaEqualTo(String value) {
			addCriterion("BUSINESS_AREA =", value, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaNotEqualTo(String value) {
			addCriterion("BUSINESS_AREA <>", value, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaGreaterThan(String value) {
			addCriterion("BUSINESS_AREA >", value, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaGreaterThanOrEqualTo(String value) {
			addCriterion("BUSINESS_AREA >=", value, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaLessThan(String value) {
			addCriterion("BUSINESS_AREA <", value, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaLessThanOrEqualTo(String value) {
			addCriterion("BUSINESS_AREA <=", value, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaLike(String value) {
			addCriterion("BUSINESS_AREA like", value, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaNotLike(String value) {
			addCriterion("BUSINESS_AREA not like", value, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaIn(List values) {
			addCriterion("BUSINESS_AREA in", values, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaNotIn(List values) {
			addCriterion("BUSINESS_AREA not in", values, "businessArea");
			return this;
		}

		public Criteria andBusinessAreaBetween(String value1, String value2) {
			addCriterion("BUSINESS_AREA between", value1, value2,
					"businessArea");
			return this;
		}

		public Criteria andBusinessAreaNotBetween(String value1, String value2) {
			addCriterion("BUSINESS_AREA not between", value1, value2,
					"businessArea");
			return this;
		}

		public Criteria andBusinessAreaNameIsNull() {
			addCriterion("BUSINESS_AREA_NAME is null");
			return this;
		}

		public Criteria andBusinessAreaNameIsNotNull() {
			addCriterion("BUSINESS_AREA_NAME is not null");
			return this;
		}

		public Criteria andBusinessAreaNameEqualTo(String value) {
			addCriterion("BUSINESS_AREA_NAME =", value, "businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameNotEqualTo(String value) {
			addCriterion("BUSINESS_AREA_NAME <>", value, "businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameGreaterThan(String value) {
			addCriterion("BUSINESS_AREA_NAME >", value, "businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameGreaterThanOrEqualTo(String value) {
			addCriterion("BUSINESS_AREA_NAME >=", value, "businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameLessThan(String value) {
			addCriterion("BUSINESS_AREA_NAME <", value, "businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameLessThanOrEqualTo(String value) {
			addCriterion("BUSINESS_AREA_NAME <=", value, "businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameLike(String value) {
			addCriterion("BUSINESS_AREA_NAME like", value, "businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameNotLike(String value) {
			addCriterion("BUSINESS_AREA_NAME not like", value,
					"businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameIn(List values) {
			addCriterion("BUSINESS_AREA_NAME in", values, "businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameNotIn(List values) {
			addCriterion("BUSINESS_AREA_NAME not in", values,
					"businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameBetween(String value1, String value2) {
			addCriterion("BUSINESS_AREA_NAME between", value1, value2,
					"businessAreaName");
			return this;
		}

		public Criteria andBusinessAreaNameNotBetween(String value1,
				String value2) {
			addCriterion("BUSINESS_AREA_NAME not between", value1, value2,
					"businessAreaName");
			return this;
		}

		public Criteria andBusinessTimeIsNull() {
			addCriterion("BUSINESS_TIME is null");
			return this;
		}

		public Criteria andBusinessTimeIsNotNull() {
			addCriterion("BUSINESS_TIME is not null");
			return this;
		}

		public Criteria andBusinessTimeEqualTo(Long value) {
			addCriterion("BUSINESS_TIME =", value, "businessTime");
			return this;
		}

		public Criteria andBusinessTimeNotEqualTo(Long value) {
			addCriterion("BUSINESS_TIME <>", value, "businessTime");
			return this;
		}

		public Criteria andBusinessTimeGreaterThan(Long value) {
			addCriterion("BUSINESS_TIME >", value, "businessTime");
			return this;
		}

		public Criteria andBusinessTimeGreaterThanOrEqualTo(Long value) {
			addCriterion("BUSINESS_TIME >=", value, "businessTime");
			return this;
		}

		public Criteria andBusinessTimeLessThan(Long value) {
			addCriterion("BUSINESS_TIME <", value, "businessTime");
			return this;
		}

		public Criteria andBusinessTimeLessThanOrEqualTo(Long value) {
			addCriterion("BUSINESS_TIME <=", value, "businessTime");
			return this;
		}

		public Criteria andBusinessTimeIn(List values) {
			addCriterion("BUSINESS_TIME in", values, "businessTime");
			return this;
		}

		public Criteria andBusinessTimeNotIn(List values) {
			addCriterion("BUSINESS_TIME not in", values, "businessTime");
			return this;
		}

		public Criteria andBusinessTimeBetween(Long value1, Long value2) {
			addCriterion("BUSINESS_TIME between", value1, value2,
					"businessTime");
			return this;
		}

		public Criteria andBusinessTimeNotBetween(Long value1, Long value2) {
			addCriterion("BUSINESS_TIME not between", value1, value2,
					"businessTime");
			return this;
		}

		public Criteria andUpdateTimeIsNull() {
			addCriterion("UPDATE_TIME is null");
			return this;
		}

		public Criteria andUpdateTimeIsNotNull() {
			addCriterion("UPDATE_TIME is not null");
			return this;
		}

		public Criteria andUpdateTimeEqualTo(Long value) {
			addCriterion("UPDATE_TIME =", value, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeNotEqualTo(Long value) {
			addCriterion("UPDATE_TIME <>", value, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeGreaterThan(Long value) {
			addCriterion("UPDATE_TIME >", value, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeGreaterThanOrEqualTo(Long value) {
			addCriterion("UPDATE_TIME >=", value, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeLessThan(Long value) {
			addCriterion("UPDATE_TIME <", value, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeLessThanOrEqualTo(Long value) {
			addCriterion("UPDATE_TIME <=", value, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeIn(List values) {
			addCriterion("UPDATE_TIME in", values, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeNotIn(List values) {
			addCriterion("UPDATE_TIME not in", values, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeBetween(Long value1, Long value2) {
			addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
			return this;
		}

		public Criteria andUpdateTimeNotBetween(Long value1, Long value2) {
			addCriterion("UPDATE_TIME not between", value1, value2,
					"updateTime");
			return this;
		}
	}
}