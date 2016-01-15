package com.sinoiov.vehicle.dao.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZoneCodeExample implements Serializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected List oredCriteria;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int skipNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int endNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int limitNum;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public ZoneCodeExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected ZoneCodeExample(ZoneCodeExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
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
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String tableName() {
		return "TB_ZONE_CODE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String daoInterface() {
		return "com.sinoiov.vehicle.dao.ZoneCodeDAO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setSkipNum(int skipNum) {
		this.skipNum = skipNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getSkipNum() {
		return this.skipNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setEndNum(int endNum) {
		this.endNum = endNum;
		this.limitNum = this.endNum - this.skipNum - 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getEndNum() {
		return this.endNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getLimitNum() {
		return this.limitNum;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table TB_ZONE_CODE
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

		public Criteria andZoneCodeIsNull() {
			addCriterion("ZONE_CODE is null");
			return this;
		}

		public Criteria andZoneCodeIsNotNull() {
			addCriterion("ZONE_CODE is not null");
			return this;
		}

		public Criteria andZoneCodeEqualTo(String value) {
			addCriterion("ZONE_CODE =", value, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeNotEqualTo(String value) {
			addCriterion("ZONE_CODE <>", value, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeGreaterThan(String value) {
			addCriterion("ZONE_CODE >", value, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeGreaterThanOrEqualTo(String value) {
			addCriterion("ZONE_CODE >=", value, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeLessThan(String value) {
			addCriterion("ZONE_CODE <", value, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeLessThanOrEqualTo(String value) {
			addCriterion("ZONE_CODE <=", value, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeLike(String value) {
			addCriterion("ZONE_CODE like", value, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeNotLike(String value) {
			addCriterion("ZONE_CODE not like", value, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeIn(List values) {
			addCriterion("ZONE_CODE in", values, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeNotIn(List values) {
			addCriterion("ZONE_CODE not in", values, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeBetween(String value1, String value2) {
			addCriterion("ZONE_CODE between", value1, value2, "zoneCode");
			return this;
		}

		public Criteria andZoneCodeNotBetween(String value1, String value2) {
			addCriterion("ZONE_CODE not between", value1, value2, "zoneCode");
			return this;
		}

		public Criteria andZoneNameIsNull() {
			addCriterion("ZONE_NAME is null");
			return this;
		}

		public Criteria andZoneNameIsNotNull() {
			addCriterion("ZONE_NAME is not null");
			return this;
		}

		public Criteria andZoneNameEqualTo(String value) {
			addCriterion("ZONE_NAME =", value, "zoneName");
			return this;
		}

		public Criteria andZoneNameNotEqualTo(String value) {
			addCriterion("ZONE_NAME <>", value, "zoneName");
			return this;
		}

		public Criteria andZoneNameGreaterThan(String value) {
			addCriterion("ZONE_NAME >", value, "zoneName");
			return this;
		}

		public Criteria andZoneNameGreaterThanOrEqualTo(String value) {
			addCriterion("ZONE_NAME >=", value, "zoneName");
			return this;
		}

		public Criteria andZoneNameLessThan(String value) {
			addCriterion("ZONE_NAME <", value, "zoneName");
			return this;
		}

		public Criteria andZoneNameLessThanOrEqualTo(String value) {
			addCriterion("ZONE_NAME <=", value, "zoneName");
			return this;
		}

		public Criteria andZoneNameLike(String value) {
			addCriterion("ZONE_NAME like", value, "zoneName");
			return this;
		}

		public Criteria andZoneNameNotLike(String value) {
			addCriterion("ZONE_NAME not like", value, "zoneName");
			return this;
		}

		public Criteria andZoneNameIn(List values) {
			addCriterion("ZONE_NAME in", values, "zoneName");
			return this;
		}

		public Criteria andZoneNameNotIn(List values) {
			addCriterion("ZONE_NAME not in", values, "zoneName");
			return this;
		}

		public Criteria andZoneNameBetween(String value1, String value2) {
			addCriterion("ZONE_NAME between", value1, value2, "zoneName");
			return this;
		}

		public Criteria andZoneNameNotBetween(String value1, String value2) {
			addCriterion("ZONE_NAME not between", value1, value2, "zoneName");
			return this;
		}

		public Criteria andShortNameIsNull() {
			addCriterion("SHORT_NAME is null");
			return this;
		}

		public Criteria andShortNameIsNotNull() {
			addCriterion("SHORT_NAME is not null");
			return this;
		}

		public Criteria andShortNameEqualTo(String value) {
			addCriterion("SHORT_NAME =", value, "shortName");
			return this;
		}

		public Criteria andShortNameNotEqualTo(String value) {
			addCriterion("SHORT_NAME <>", value, "shortName");
			return this;
		}

		public Criteria andShortNameGreaterThan(String value) {
			addCriterion("SHORT_NAME >", value, "shortName");
			return this;
		}

		public Criteria andShortNameGreaterThanOrEqualTo(String value) {
			addCriterion("SHORT_NAME >=", value, "shortName");
			return this;
		}

		public Criteria andShortNameLessThan(String value) {
			addCriterion("SHORT_NAME <", value, "shortName");
			return this;
		}

		public Criteria andShortNameLessThanOrEqualTo(String value) {
			addCriterion("SHORT_NAME <=", value, "shortName");
			return this;
		}

		public Criteria andShortNameLike(String value) {
			addCriterion("SHORT_NAME like", value, "shortName");
			return this;
		}

		public Criteria andShortNameNotLike(String value) {
			addCriterion("SHORT_NAME not like", value, "shortName");
			return this;
		}

		public Criteria andShortNameIn(List values) {
			addCriterion("SHORT_NAME in", values, "shortName");
			return this;
		}

		public Criteria andShortNameNotIn(List values) {
			addCriterion("SHORT_NAME not in", values, "shortName");
			return this;
		}

		public Criteria andShortNameBetween(String value1, String value2) {
			addCriterion("SHORT_NAME between", value1, value2, "shortName");
			return this;
		}

		public Criteria andShortNameNotBetween(String value1, String value2) {
			addCriterion("SHORT_NAME not between", value1, value2, "shortName");
			return this;
		}

		public Criteria andLonIsNull() {
			addCriterion("LON is null");
			return this;
		}

		public Criteria andLonIsNotNull() {
			addCriterion("LON is not null");
			return this;
		}

		public Criteria andLonEqualTo(BigDecimal value) {
			addCriterion("LON =", value, "lon");
			return this;
		}

		public Criteria andLonNotEqualTo(BigDecimal value) {
			addCriterion("LON <>", value, "lon");
			return this;
		}

		public Criteria andLonGreaterThan(BigDecimal value) {
			addCriterion("LON >", value, "lon");
			return this;
		}

		public Criteria andLonGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("LON >=", value, "lon");
			return this;
		}

		public Criteria andLonLessThan(BigDecimal value) {
			addCriterion("LON <", value, "lon");
			return this;
		}

		public Criteria andLonLessThanOrEqualTo(BigDecimal value) {
			addCriterion("LON <=", value, "lon");
			return this;
		}

		public Criteria andLonIn(List values) {
			addCriterion("LON in", values, "lon");
			return this;
		}

		public Criteria andLonNotIn(List values) {
			addCriterion("LON not in", values, "lon");
			return this;
		}

		public Criteria andLonBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("LON between", value1, value2, "lon");
			return this;
		}

		public Criteria andLonNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("LON not between", value1, value2, "lon");
			return this;
		}

		public Criteria andLatIsNull() {
			addCriterion("LAT is null");
			return this;
		}

		public Criteria andLatIsNotNull() {
			addCriterion("LAT is not null");
			return this;
		}

		public Criteria andLatEqualTo(BigDecimal value) {
			addCriterion("LAT =", value, "lat");
			return this;
		}

		public Criteria andLatNotEqualTo(BigDecimal value) {
			addCriterion("LAT <>", value, "lat");
			return this;
		}

		public Criteria andLatGreaterThan(BigDecimal value) {
			addCriterion("LAT >", value, "lat");
			return this;
		}

		public Criteria andLatGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("LAT >=", value, "lat");
			return this;
		}

		public Criteria andLatLessThan(BigDecimal value) {
			addCriterion("LAT <", value, "lat");
			return this;
		}

		public Criteria andLatLessThanOrEqualTo(BigDecimal value) {
			addCriterion("LAT <=", value, "lat");
			return this;
		}

		public Criteria andLatIn(List values) {
			addCriterion("LAT in", values, "lat");
			return this;
		}

		public Criteria andLatNotIn(List values) {
			addCriterion("LAT not in", values, "lat");
			return this;
		}

		public Criteria andLatBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("LAT between", value1, value2, "lat");
			return this;
		}

		public Criteria andLatNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("LAT not between", value1, value2, "lat");
			return this;
		}

		public Criteria andZoneLevelIsNull() {
			addCriterion("ZONE_LEVEL is null");
			return this;
		}

		public Criteria andZoneLevelIsNotNull() {
			addCriterion("ZONE_LEVEL is not null");
			return this;
		}

		public Criteria andZoneLevelEqualTo(Short value) {
			addCriterion("ZONE_LEVEL =", value, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelNotEqualTo(Short value) {
			addCriterion("ZONE_LEVEL <>", value, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelGreaterThan(Short value) {
			addCriterion("ZONE_LEVEL >", value, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelGreaterThanOrEqualTo(Short value) {
			addCriterion("ZONE_LEVEL >=", value, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelLessThan(Short value) {
			addCriterion("ZONE_LEVEL <", value, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelLessThanOrEqualTo(Short value) {
			addCriterion("ZONE_LEVEL <=", value, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelIn(List values) {
			addCriterion("ZONE_LEVEL in", values, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelNotIn(List values) {
			addCriterion("ZONE_LEVEL not in", values, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelBetween(Short value1, Short value2) {
			addCriterion("ZONE_LEVEL between", value1, value2, "zoneLevel");
			return this;
		}

		public Criteria andZoneLevelNotBetween(Short value1, Short value2) {
			addCriterion("ZONE_LEVEL not between", value1, value2, "zoneLevel");
			return this;
		}

		public Criteria andZoneFlagIsNull() {
			addCriterion("ZONE_FLAG is null");
			return this;
		}

		public Criteria andZoneFlagIsNotNull() {
			addCriterion("ZONE_FLAG is not null");
			return this;
		}

		public Criteria andZoneFlagEqualTo(Short value) {
			addCriterion("ZONE_FLAG =", value, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagNotEqualTo(Short value) {
			addCriterion("ZONE_FLAG <>", value, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagGreaterThan(Short value) {
			addCriterion("ZONE_FLAG >", value, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagGreaterThanOrEqualTo(Short value) {
			addCriterion("ZONE_FLAG >=", value, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagLessThan(Short value) {
			addCriterion("ZONE_FLAG <", value, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagLessThanOrEqualTo(Short value) {
			addCriterion("ZONE_FLAG <=", value, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagIn(List values) {
			addCriterion("ZONE_FLAG in", values, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagNotIn(List values) {
			addCriterion("ZONE_FLAG not in", values, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagBetween(Short value1, Short value2) {
			addCriterion("ZONE_FLAG between", value1, value2, "zoneFlag");
			return this;
		}

		public Criteria andZoneFlagNotBetween(Short value1, Short value2) {
			addCriterion("ZONE_FLAG not between", value1, value2, "zoneFlag");
			return this;
		}
	}
}