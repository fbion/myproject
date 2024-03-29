package com.sinoiov.vehicle.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageExampleExtended implements Serializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected List oredCriteria;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected String selectedField;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int skipNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int endNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected int limitNum;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public ImageExampleExtended() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected ImageExampleExtended(ImageExampleExtended example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
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
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setSelectedField(String selectedField) {
		this.selectedField = selectedField;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getSelectedField() {
		return selectedField;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String tableName() {
		return "TB_IMAGE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String daoInterface() {
		return "com.sinoiov.vehicle.dao.ImageDAO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setSkipNum(int skipNum) {
		this.skipNum = skipNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getSkipNum() {
		return this.skipNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setEndNum(int endNum) {
		this.endNum = endNum;
		this.limitNum = this.endNum - this.skipNum - 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getEndNum() {
		return this.endNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_IMAGE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public int getLimitNum() {
		return this.limitNum;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table TB_IMAGE
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

		public Criteria andOwnerIdIsNull() {
			addCriterion("OWNER_ID is null");
			return this;
		}

		public Criteria andOwnerIdIsNotNull() {
			addCriterion("OWNER_ID is not null");
			return this;
		}

		public Criteria andOwnerIdEqualTo(String value) {
			addCriterion("OWNER_ID =", value, "ownerId");
			return this;
		}

		public Criteria andOwnerIdNotEqualTo(String value) {
			addCriterion("OWNER_ID <>", value, "ownerId");
			return this;
		}

		public Criteria andOwnerIdGreaterThan(String value) {
			addCriterion("OWNER_ID >", value, "ownerId");
			return this;
		}

		public Criteria andOwnerIdGreaterThanOrEqualTo(String value) {
			addCriterion("OWNER_ID >=", value, "ownerId");
			return this;
		}

		public Criteria andOwnerIdLessThan(String value) {
			addCriterion("OWNER_ID <", value, "ownerId");
			return this;
		}

		public Criteria andOwnerIdLessThanOrEqualTo(String value) {
			addCriterion("OWNER_ID <=", value, "ownerId");
			return this;
		}

		public Criteria andOwnerIdLike(String value) {
			addCriterion("OWNER_ID like", value, "ownerId");
			return this;
		}

		public Criteria andOwnerIdNotLike(String value) {
			addCriterion("OWNER_ID not like", value, "ownerId");
			return this;
		}

		public Criteria andOwnerIdIn(List values) {
			addCriterion("OWNER_ID in", values, "ownerId");
			return this;
		}

		public Criteria andOwnerIdNotIn(List values) {
			addCriterion("OWNER_ID not in", values, "ownerId");
			return this;
		}

		public Criteria andOwnerIdBetween(String value1, String value2) {
			addCriterion("OWNER_ID between", value1, value2, "ownerId");
			return this;
		}

		public Criteria andOwnerIdNotBetween(String value1, String value2) {
			addCriterion("OWNER_ID not between", value1, value2, "ownerId");
			return this;
		}

		public Criteria andAttachSourceUrlIsNull() {
			addCriterion("ATTACH_SOURCE_URL is null");
			return this;
		}

		public Criteria andAttachSourceUrlIsNotNull() {
			addCriterion("ATTACH_SOURCE_URL is not null");
			return this;
		}

		public Criteria andAttachSourceUrlEqualTo(String value) {
			addCriterion("ATTACH_SOURCE_URL =", value, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlNotEqualTo(String value) {
			addCriterion("ATTACH_SOURCE_URL <>", value, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlGreaterThan(String value) {
			addCriterion("ATTACH_SOURCE_URL >", value, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlGreaterThanOrEqualTo(String value) {
			addCriterion("ATTACH_SOURCE_URL >=", value, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlLessThan(String value) {
			addCriterion("ATTACH_SOURCE_URL <", value, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlLessThanOrEqualTo(String value) {
			addCriterion("ATTACH_SOURCE_URL <=", value, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlLike(String value) {
			addCriterion("ATTACH_SOURCE_URL like", value, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlNotLike(String value) {
			addCriterion("ATTACH_SOURCE_URL not like", value, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlIn(List values) {
			addCriterion("ATTACH_SOURCE_URL in", values, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlNotIn(List values) {
			addCriterion("ATTACH_SOURCE_URL not in", values, "attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlBetween(String value1, String value2) {
			addCriterion("ATTACH_SOURCE_URL between", value1, value2,
					"attachSourceUrl");
			return this;
		}

		public Criteria andAttachSourceUrlNotBetween(String value1,
				String value2) {
			addCriterion("ATTACH_SOURCE_URL not between", value1, value2,
					"attachSourceUrl");
			return this;
		}

		public Criteria andAttachUrlIsNull() {
			addCriterion("ATTACH_URL is null");
			return this;
		}

		public Criteria andAttachUrlIsNotNull() {
			addCriterion("ATTACH_URL is not null");
			return this;
		}

		public Criteria andAttachUrlEqualTo(String value) {
			addCriterion("ATTACH_URL =", value, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlNotEqualTo(String value) {
			addCriterion("ATTACH_URL <>", value, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlGreaterThan(String value) {
			addCriterion("ATTACH_URL >", value, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlGreaterThanOrEqualTo(String value) {
			addCriterion("ATTACH_URL >=", value, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlLessThan(String value) {
			addCriterion("ATTACH_URL <", value, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlLessThanOrEqualTo(String value) {
			addCriterion("ATTACH_URL <=", value, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlLike(String value) {
			addCriterion("ATTACH_URL like", value, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlNotLike(String value) {
			addCriterion("ATTACH_URL not like", value, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlIn(List values) {
			addCriterion("ATTACH_URL in", values, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlNotIn(List values) {
			addCriterion("ATTACH_URL not in", values, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlBetween(String value1, String value2) {
			addCriterion("ATTACH_URL between", value1, value2, "attachUrl");
			return this;
		}

		public Criteria andAttachUrlNotBetween(String value1, String value2) {
			addCriterion("ATTACH_URL not between", value1, value2, "attachUrl");
			return this;
		}

		public Criteria andAttachTypeIsNull() {
			addCriterion("ATTACH_TYPE is null");
			return this;
		}

		public Criteria andAttachTypeIsNotNull() {
			addCriterion("ATTACH_TYPE is not null");
			return this;
		}

		public Criteria andAttachTypeEqualTo(String value) {
			addCriterion("ATTACH_TYPE =", value, "attachType");
			return this;
		}

		public Criteria andAttachTypeNotEqualTo(String value) {
			addCriterion("ATTACH_TYPE <>", value, "attachType");
			return this;
		}

		public Criteria andAttachTypeGreaterThan(String value) {
			addCriterion("ATTACH_TYPE >", value, "attachType");
			return this;
		}

		public Criteria andAttachTypeGreaterThanOrEqualTo(String value) {
			addCriterion("ATTACH_TYPE >=", value, "attachType");
			return this;
		}

		public Criteria andAttachTypeLessThan(String value) {
			addCriterion("ATTACH_TYPE <", value, "attachType");
			return this;
		}

		public Criteria andAttachTypeLessThanOrEqualTo(String value) {
			addCriterion("ATTACH_TYPE <=", value, "attachType");
			return this;
		}

		public Criteria andAttachTypeLike(String value) {
			addCriterion("ATTACH_TYPE like", value, "attachType");
			return this;
		}

		public Criteria andAttachTypeNotLike(String value) {
			addCriterion("ATTACH_TYPE not like", value, "attachType");
			return this;
		}

		public Criteria andAttachTypeIn(List values) {
			addCriterion("ATTACH_TYPE in", values, "attachType");
			return this;
		}

		public Criteria andAttachTypeNotIn(List values) {
			addCriterion("ATTACH_TYPE not in", values, "attachType");
			return this;
		}

		public Criteria andAttachTypeBetween(String value1, String value2) {
			addCriterion("ATTACH_TYPE between", value1, value2, "attachType");
			return this;
		}

		public Criteria andAttachTypeNotBetween(String value1, String value2) {
			addCriterion("ATTACH_TYPE not between", value1, value2,
					"attachType");
			return this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("STATUS is null");
			return this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("STATUS is not null");
			return this;
		}

		public Criteria andStatusEqualTo(String value) {
			addCriterion("STATUS =", value, "status");
			return this;
		}

		public Criteria andStatusNotEqualTo(String value) {
			addCriterion("STATUS <>", value, "status");
			return this;
		}

		public Criteria andStatusGreaterThan(String value) {
			addCriterion("STATUS >", value, "status");
			return this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(String value) {
			addCriterion("STATUS >=", value, "status");
			return this;
		}

		public Criteria andStatusLessThan(String value) {
			addCriterion("STATUS <", value, "status");
			return this;
		}

		public Criteria andStatusLessThanOrEqualTo(String value) {
			addCriterion("STATUS <=", value, "status");
			return this;
		}

		public Criteria andStatusLike(String value) {
			addCriterion("STATUS like", value, "status");
			return this;
		}

		public Criteria andStatusNotLike(String value) {
			addCriterion("STATUS not like", value, "status");
			return this;
		}

		public Criteria andStatusIn(List values) {
			addCriterion("STATUS in", values, "status");
			return this;
		}

		public Criteria andStatusNotIn(List values) {
			addCriterion("STATUS not in", values, "status");
			return this;
		}

		public Criteria andStatusBetween(String value1, String value2) {
			addCriterion("STATUS between", value1, value2, "status");
			return this;
		}

		public Criteria andStatusNotBetween(String value1, String value2) {
			addCriterion("STATUS not between", value1, value2, "status");
			return this;
		}

		public Criteria andOwnerTypeIsNull() {
			addCriterion("OWNER_TYPE is null");
			return this;
		}

		public Criteria andOwnerTypeIsNotNull() {
			addCriterion("OWNER_TYPE is not null");
			return this;
		}

		public Criteria andOwnerTypeEqualTo(String value) {
			addCriterion("OWNER_TYPE =", value, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeNotEqualTo(String value) {
			addCriterion("OWNER_TYPE <>", value, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeGreaterThan(String value) {
			addCriterion("OWNER_TYPE >", value, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeGreaterThanOrEqualTo(String value) {
			addCriterion("OWNER_TYPE >=", value, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeLessThan(String value) {
			addCriterion("OWNER_TYPE <", value, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeLessThanOrEqualTo(String value) {
			addCriterion("OWNER_TYPE <=", value, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeLike(String value) {
			addCriterion("OWNER_TYPE like", value, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeNotLike(String value) {
			addCriterion("OWNER_TYPE not like", value, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeIn(List values) {
			addCriterion("OWNER_TYPE in", values, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeNotIn(List values) {
			addCriterion("OWNER_TYPE not in", values, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeBetween(String value1, String value2) {
			addCriterion("OWNER_TYPE between", value1, value2, "ownerType");
			return this;
		}

		public Criteria andOwnerTypeNotBetween(String value1, String value2) {
			addCriterion("OWNER_TYPE not between", value1, value2, "ownerType");
			return this;
		}

		public Criteria andCreateTimeIsNull() {
			addCriterion("CREATE_TIME is null");
			return this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("CREATE_TIME is not null");
			return this;
		}

		public Criteria andCreateTimeEqualTo(Long value) {
			addCriterion("CREATE_TIME =", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotEqualTo(Long value) {
			addCriterion("CREATE_TIME <>", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeGreaterThan(Long value) {
			addCriterion("CREATE_TIME >", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(Long value) {
			addCriterion("CREATE_TIME >=", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeLessThan(Long value) {
			addCriterion("CREATE_TIME <", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(Long value) {
			addCriterion("CREATE_TIME <=", value, "createTime");
			return this;
		}

		public Criteria andCreateTimeIn(List values) {
			addCriterion("CREATE_TIME in", values, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotIn(List values) {
			addCriterion("CREATE_TIME not in", values, "createTime");
			return this;
		}

		public Criteria andCreateTimeBetween(Long value1, Long value2) {
			addCriterion("CREATE_TIME between", value1, value2, "createTime");
			return this;
		}

		public Criteria andCreateTimeNotBetween(Long value1, Long value2) {
			addCriterion("CREATE_TIME not between", value1, value2,
					"createTime");
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

		public Criteria andAttachMiniUrlIsNull() {
			addCriterion("ATTACH_MINI_URL is null");
			return this;
		}

		public Criteria andAttachMiniUrlIsNotNull() {
			addCriterion("ATTACH_MINI_URL is not null");
			return this;
		}

		public Criteria andAttachMiniUrlEqualTo(String value) {
			addCriterion("ATTACH_MINI_URL =", value, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlNotEqualTo(String value) {
			addCriterion("ATTACH_MINI_URL <>", value, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlGreaterThan(String value) {
			addCriterion("ATTACH_MINI_URL >", value, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlGreaterThanOrEqualTo(String value) {
			addCriterion("ATTACH_MINI_URL >=", value, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlLessThan(String value) {
			addCriterion("ATTACH_MINI_URL <", value, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlLessThanOrEqualTo(String value) {
			addCriterion("ATTACH_MINI_URL <=", value, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlLike(String value) {
			addCriterion("ATTACH_MINI_URL like", value, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlNotLike(String value) {
			addCriterion("ATTACH_MINI_URL not like", value, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlIn(List values) {
			addCriterion("ATTACH_MINI_URL in", values, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlNotIn(List values) {
			addCriterion("ATTACH_MINI_URL not in", values, "attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlBetween(String value1, String value2) {
			addCriterion("ATTACH_MINI_URL between", value1, value2,
					"attachMiniUrl");
			return this;
		}

		public Criteria andAttachMiniUrlNotBetween(String value1, String value2) {
			addCriterion("ATTACH_MINI_URL not between", value1, value2,
					"attachMiniUrl");
			return this;
		}

		public Criteria andImagesCleanStatusIsNull() {
			addCriterion("IMAGES_CLEAN_STATUS is null");
			return this;
		}

		public Criteria andImagesCleanStatusIsNotNull() {
			addCriterion("IMAGES_CLEAN_STATUS is not null");
			return this;
		}

		public Criteria andImagesCleanStatusEqualTo(String value) {
			addCriterion("IMAGES_CLEAN_STATUS =", value, "imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusNotEqualTo(String value) {
			addCriterion("IMAGES_CLEAN_STATUS <>", value, "imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusGreaterThan(String value) {
			addCriterion("IMAGES_CLEAN_STATUS >", value, "imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusGreaterThanOrEqualTo(String value) {
			addCriterion("IMAGES_CLEAN_STATUS >=", value, "imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusLessThan(String value) {
			addCriterion("IMAGES_CLEAN_STATUS <", value, "imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusLessThanOrEqualTo(String value) {
			addCriterion("IMAGES_CLEAN_STATUS <=", value, "imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusLike(String value) {
			addCriterion("IMAGES_CLEAN_STATUS like", value, "imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusNotLike(String value) {
			addCriterion("IMAGES_CLEAN_STATUS not like", value,
					"imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusIn(List values) {
			addCriterion("IMAGES_CLEAN_STATUS in", values, "imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusNotIn(List values) {
			addCriterion("IMAGES_CLEAN_STATUS not in", values,
					"imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusBetween(String value1, String value2) {
			addCriterion("IMAGES_CLEAN_STATUS between", value1, value2,
					"imagesCleanStatus");
			return this;
		}

		public Criteria andImagesCleanStatusNotBetween(String value1,
				String value2) {
			addCriterion("IMAGES_CLEAN_STATUS not between", value1, value2,
					"imagesCleanStatus");
			return this;
		}
	}
}