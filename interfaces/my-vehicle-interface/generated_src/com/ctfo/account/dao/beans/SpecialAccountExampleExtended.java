package com.ctfo.account.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialAccountExampleExtended implements Serializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	protected List oredCriteria;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	protected String selectedField;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	protected int skipNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	protected int endNum;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	protected int limitNum;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public SpecialAccountExampleExtended() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	protected SpecialAccountExampleExtended(SpecialAccountExampleExtended example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setSelectedField(String selectedField) {
		this.selectedField = selectedField;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public String getSelectedField() {
		return selectedField;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String tableName() {
		return "TB_UPP_SUB_ACCOUNT";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static String daoInterface() {
		return "com.ctfo.upp.accountservice.account.dao.SpecialAccountDAO";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setSkipNum(int skipNum) {
		this.skipNum = skipNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int getSkipNum() {
		return this.skipNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setEndNum(int endNum) {
		this.endNum = endNum;
		this.limitNum = this.endNum - this.skipNum - 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int getEndNum() {
		return this.endNum;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
		this.endNum = this.skipNum + this.limitNum + 1;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public int getLimitNum() {
		return this.limitNum;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
	 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
	 */
	public static class Criteria implements Serializable {
		/**
		 * This field was generated by Abator for iBATIS. This field corresponds to the database table UPP.TB_UPP_SUB_ACCOUNT
		 * @abatorgenerated  Mon Nov 10 15:19:33 CST 2014
		 */
		private static final long serialVersionUID = 1L;
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
			return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0 || criteriaWithListValue.size() > 0 || criteriaWithBetweenValue.size() > 0;
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

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition, List values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property + " cannot be null or empty");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
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

		public Criteria andParentAccountIdIsNull() {
			addCriterion("PARENT_ACCOUNT_ID is null");
			return this;
		}

		public Criteria andParentAccountIdIsNotNull() {
			addCriterion("PARENT_ACCOUNT_ID is not null");
			return this;
		}

		public Criteria andParentAccountIdEqualTo(String value) {
			addCriterion("PARENT_ACCOUNT_ID =", value, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdNotEqualTo(String value) {
			addCriterion("PARENT_ACCOUNT_ID <>", value, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdGreaterThan(String value) {
			addCriterion("PARENT_ACCOUNT_ID >", value, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdGreaterThanOrEqualTo(String value) {
			addCriterion("PARENT_ACCOUNT_ID >=", value, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdLessThan(String value) {
			addCriterion("PARENT_ACCOUNT_ID <", value, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdLessThanOrEqualTo(String value) {
			addCriterion("PARENT_ACCOUNT_ID <=", value, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdLike(String value) {
			addCriterion("PARENT_ACCOUNT_ID like", value, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdNotLike(String value) {
			addCriterion("PARENT_ACCOUNT_ID not like", value, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdIn(List values) {
			addCriterion("PARENT_ACCOUNT_ID in", values, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdNotIn(List values) {
			addCriterion("PARENT_ACCOUNT_ID not in", values, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdBetween(String value1, String value2) {
			addCriterion("PARENT_ACCOUNT_ID between", value1, value2, "parentAccountId");
			return this;
		}

		public Criteria andParentAccountIdNotBetween(String value1, String value2) {
			addCriterion("PARENT_ACCOUNT_ID not between", value1, value2, "parentAccountId");
			return this;
		}

		public Criteria andInsideAccountNoIsNull() {
			addCriterion("INSIDE_ACCOUNT_NO is null");
			return this;
		}

		public Criteria andInsideAccountNoIsNotNull() {
			addCriterion("INSIDE_ACCOUNT_NO is not null");
			return this;
		}

		public Criteria andInsideAccountNoEqualTo(String value) {
			addCriterion("INSIDE_ACCOUNT_NO =", value, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoNotEqualTo(String value) {
			addCriterion("INSIDE_ACCOUNT_NO <>", value, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoGreaterThan(String value) {
			addCriterion("INSIDE_ACCOUNT_NO >", value, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoGreaterThanOrEqualTo(String value) {
			addCriterion("INSIDE_ACCOUNT_NO >=", value, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoLessThan(String value) {
			addCriterion("INSIDE_ACCOUNT_NO <", value, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoLessThanOrEqualTo(String value) {
			addCriterion("INSIDE_ACCOUNT_NO <=", value, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoLike(String value) {
			addCriterion("INSIDE_ACCOUNT_NO like", value, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoNotLike(String value) {
			addCriterion("INSIDE_ACCOUNT_NO not like", value, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoIn(List values) {
			addCriterion("INSIDE_ACCOUNT_NO in", values, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoNotIn(List values) {
			addCriterion("INSIDE_ACCOUNT_NO not in", values, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoBetween(String value1, String value2) {
			addCriterion("INSIDE_ACCOUNT_NO between", value1, value2, "insideAccountNo");
			return this;
		}

		public Criteria andInsideAccountNoNotBetween(String value1, String value2) {
			addCriterion("INSIDE_ACCOUNT_NO not between", value1, value2, "insideAccountNo");
			return this;
		}

		public Criteria andAccountTypeIsNull() {
			addCriterion("ACCOUNT_TYPE is null");
			return this;
		}

		public Criteria andAccountTypeIsNotNull() {
			addCriterion("ACCOUNT_TYPE is not null");
			return this;
		}

		public Criteria andAccountTypeEqualTo(String value) {
			addCriterion("ACCOUNT_TYPE =", value, "accountType");
			return this;
		}

		public Criteria andAccountTypeNotEqualTo(String value) {
			addCriterion("ACCOUNT_TYPE <>", value, "accountType");
			return this;
		}

		public Criteria andAccountTypeGreaterThan(String value) {
			addCriterion("ACCOUNT_TYPE >", value, "accountType");
			return this;
		}

		public Criteria andAccountTypeGreaterThanOrEqualTo(String value) {
			addCriterion("ACCOUNT_TYPE >=", value, "accountType");
			return this;
		}

		public Criteria andAccountTypeLessThan(String value) {
			addCriterion("ACCOUNT_TYPE <", value, "accountType");
			return this;
		}

		public Criteria andAccountTypeLessThanOrEqualTo(String value) {
			addCriterion("ACCOUNT_TYPE <=", value, "accountType");
			return this;
		}

		public Criteria andAccountTypeLike(String value) {
			addCriterion("ACCOUNT_TYPE like", value, "accountType");
			return this;
		}

		public Criteria andAccountTypeNotLike(String value) {
			addCriterion("ACCOUNT_TYPE not like", value, "accountType");
			return this;
		}

		public Criteria andAccountTypeIn(List values) {
			addCriterion("ACCOUNT_TYPE in", values, "accountType");
			return this;
		}

		public Criteria andAccountTypeNotIn(List values) {
			addCriterion("ACCOUNT_TYPE not in", values, "accountType");
			return this;
		}

		public Criteria andAccountTypeBetween(String value1, String value2) {
			addCriterion("ACCOUNT_TYPE between", value1, value2, "accountType");
			return this;
		}

		public Criteria andAccountTypeNotBetween(String value1, String value2) {
			addCriterion("ACCOUNT_TYPE not between", value1, value2, "accountType");
			return this;
		}

		public Criteria andServiceProviderCodeIsNull() {
			addCriterion("SERVICE_PROVIDER_CODE is null");
			return this;
		}

		public Criteria andServiceProviderCodeIsNotNull() {
			addCriterion("SERVICE_PROVIDER_CODE is not null");
			return this;
		}

		public Criteria andServiceProviderCodeEqualTo(String value) {
			addCriterion("SERVICE_PROVIDER_CODE =", value, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeNotEqualTo(String value) {
			addCriterion("SERVICE_PROVIDER_CODE <>", value, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeGreaterThan(String value) {
			addCriterion("SERVICE_PROVIDER_CODE >", value, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeGreaterThanOrEqualTo(String value) {
			addCriterion("SERVICE_PROVIDER_CODE >=", value, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeLessThan(String value) {
			addCriterion("SERVICE_PROVIDER_CODE <", value, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeLessThanOrEqualTo(String value) {
			addCriterion("SERVICE_PROVIDER_CODE <=", value, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeLike(String value) {
			addCriterion("SERVICE_PROVIDER_CODE like", value, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeNotLike(String value) {
			addCriterion("SERVICE_PROVIDER_CODE not like", value, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeIn(List values) {
			addCriterion("SERVICE_PROVIDER_CODE in", values, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeNotIn(List values) {
			addCriterion("SERVICE_PROVIDER_CODE not in", values, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeBetween(String value1, String value2) {
			addCriterion("SERVICE_PROVIDER_CODE between", value1, value2, "serviceProviderCode");
			return this;
		}

		public Criteria andServiceProviderCodeNotBetween(String value1, String value2) {
			addCriterion("SERVICE_PROVIDER_CODE not between", value1, value2, "serviceProviderCode");
			return this;
		}

		public Criteria andExternalAccountNoIsNull() {
			addCriterion("EXTERNAL_ACCOUNT_NO is null");
			return this;
		}

		public Criteria andExternalAccountNoIsNotNull() {
			addCriterion("EXTERNAL_ACCOUNT_NO is not null");
			return this;
		}

		public Criteria andExternalAccountNoEqualTo(String value) {
			addCriterion("EXTERNAL_ACCOUNT_NO =", value, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoNotEqualTo(String value) {
			addCriterion("EXTERNAL_ACCOUNT_NO <>", value, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoGreaterThan(String value) {
			addCriterion("EXTERNAL_ACCOUNT_NO >", value, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoGreaterThanOrEqualTo(String value) {
			addCriterion("EXTERNAL_ACCOUNT_NO >=", value, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoLessThan(String value) {
			addCriterion("EXTERNAL_ACCOUNT_NO <", value, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoLessThanOrEqualTo(String value) {
			addCriterion("EXTERNAL_ACCOUNT_NO <=", value, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoLike(String value) {
			addCriterion("EXTERNAL_ACCOUNT_NO like", value, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoNotLike(String value) {
			addCriterion("EXTERNAL_ACCOUNT_NO not like", value, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoIn(List values) {
			addCriterion("EXTERNAL_ACCOUNT_NO in", values, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoNotIn(List values) {
			addCriterion("EXTERNAL_ACCOUNT_NO not in", values, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoBetween(String value1, String value2) {
			addCriterion("EXTERNAL_ACCOUNT_NO between", value1, value2, "externalAccountNo");
			return this;
		}

		public Criteria andExternalAccountNoNotBetween(String value1, String value2) {
			addCriterion("EXTERNAL_ACCOUNT_NO not between", value1, value2, "externalAccountNo");
			return this;
		}

		public Criteria andAccountStatusIsNull() {
			addCriterion("ACCOUNT_STATUS is null");
			return this;
		}

		public Criteria andAccountStatusIsNotNull() {
			addCriterion("ACCOUNT_STATUS is not null");
			return this;
		}

		public Criteria andAccountStatusEqualTo(String value) {
			addCriterion("ACCOUNT_STATUS =", value, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusNotEqualTo(String value) {
			addCriterion("ACCOUNT_STATUS <>", value, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusGreaterThan(String value) {
			addCriterion("ACCOUNT_STATUS >", value, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusGreaterThanOrEqualTo(String value) {
			addCriterion("ACCOUNT_STATUS >=", value, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusLessThan(String value) {
			addCriterion("ACCOUNT_STATUS <", value, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusLessThanOrEqualTo(String value) {
			addCriterion("ACCOUNT_STATUS <=", value, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusLike(String value) {
			addCriterion("ACCOUNT_STATUS like", value, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusNotLike(String value) {
			addCriterion("ACCOUNT_STATUS not like", value, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusIn(List values) {
			addCriterion("ACCOUNT_STATUS in", values, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusNotIn(List values) {
			addCriterion("ACCOUNT_STATUS not in", values, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusBetween(String value1, String value2) {
			addCriterion("ACCOUNT_STATUS between", value1, value2, "accountStatus");
			return this;
		}

		public Criteria andAccountStatusNotBetween(String value1, String value2) {
			addCriterion("ACCOUNT_STATUS not between", value1, value2, "accountStatus");
			return this;
		}

		public Criteria andTotalBalanceIsNull() {
			addCriterion("TOTAL_BALANCE is null");
			return this;
		}

		public Criteria andTotalBalanceIsNotNull() {
			addCriterion("TOTAL_BALANCE is not null");
			return this;
		}

		public Criteria andTotalBalanceEqualTo(Long value) {
			addCriterion("TOTAL_BALANCE =", value, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceNotEqualTo(Long value) {
			addCriterion("TOTAL_BALANCE <>", value, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceGreaterThan(Long value) {
			addCriterion("TOTAL_BALANCE >", value, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceGreaterThanOrEqualTo(Long value) {
			addCriterion("TOTAL_BALANCE >=", value, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceLessThan(Long value) {
			addCriterion("TOTAL_BALANCE <", value, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceLessThanOrEqualTo(Long value) {
			addCriterion("TOTAL_BALANCE <=", value, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceIn(List values) {
			addCriterion("TOTAL_BALANCE in", values, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceNotIn(List values) {
			addCriterion("TOTAL_BALANCE not in", values, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceBetween(Long value1, Long value2) {
			addCriterion("TOTAL_BALANCE between", value1, value2, "totalBalance");
			return this;
		}

		public Criteria andTotalBalanceNotBetween(Long value1, Long value2) {
			addCriterion("TOTAL_BALANCE not between", value1, value2, "totalBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceIsNull() {
			addCriterion("UNABLE_TAKECASH_BALANCE is null");
			return this;
		}

		public Criteria andUnableTakecashBalanceIsNotNull() {
			addCriterion("UNABLE_TAKECASH_BALANCE is not null");
			return this;
		}

		public Criteria andUnableTakecashBalanceEqualTo(Long value) {
			addCriterion("UNABLE_TAKECASH_BALANCE =", value, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceNotEqualTo(Long value) {
			addCriterion("UNABLE_TAKECASH_BALANCE <>", value, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceGreaterThan(Long value) {
			addCriterion("UNABLE_TAKECASH_BALANCE >", value, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceGreaterThanOrEqualTo(Long value) {
			addCriterion("UNABLE_TAKECASH_BALANCE >=", value, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceLessThan(Long value) {
			addCriterion("UNABLE_TAKECASH_BALANCE <", value, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceLessThanOrEqualTo(Long value) {
			addCriterion("UNABLE_TAKECASH_BALANCE <=", value, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceIn(List values) {
			addCriterion("UNABLE_TAKECASH_BALANCE in", values, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceNotIn(List values) {
			addCriterion("UNABLE_TAKECASH_BALANCE not in", values, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceBetween(Long value1, Long value2) {
			addCriterion("UNABLE_TAKECASH_BALANCE between", value1, value2, "unableTakecashBalance");
			return this;
		}

		public Criteria andUnableTakecashBalanceNotBetween(Long value1, Long value2) {
			addCriterion("UNABLE_TAKECASH_BALANCE not between", value1, value2, "unableTakecashBalance");
			return this;
		}

		public Criteria andFrozenBalanceIsNull() {
			addCriterion("FROZEN_BALANCE is null");
			return this;
		}

		public Criteria andFrozenBalanceIsNotNull() {
			addCriterion("FROZEN_BALANCE is not null");
			return this;
		}

		public Criteria andFrozenBalanceEqualTo(Long value) {
			addCriterion("FROZEN_BALANCE =", value, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceNotEqualTo(Long value) {
			addCriterion("FROZEN_BALANCE <>", value, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceGreaterThan(Long value) {
			addCriterion("FROZEN_BALANCE >", value, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceGreaterThanOrEqualTo(Long value) {
			addCriterion("FROZEN_BALANCE >=", value, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceLessThan(Long value) {
			addCriterion("FROZEN_BALANCE <", value, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceLessThanOrEqualTo(Long value) {
			addCriterion("FROZEN_BALANCE <=", value, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceIn(List values) {
			addCriterion("FROZEN_BALANCE in", values, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceNotIn(List values) {
			addCriterion("FROZEN_BALANCE not in", values, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceBetween(Long value1, Long value2) {
			addCriterion("FROZEN_BALANCE between", value1, value2, "frozenBalance");
			return this;
		}

		public Criteria andFrozenBalanceNotBetween(Long value1, Long value2) {
			addCriterion("FROZEN_BALANCE not between", value1, value2, "frozenBalance");
			return this;
		}

		public Criteria andUsableBalanceIsNull() {
			addCriterion("USABLE_BALANCE is null");
			return this;
		}

		public Criteria andUsableBalanceIsNotNull() {
			addCriterion("USABLE_BALANCE is not null");
			return this;
		}

		public Criteria andUsableBalanceEqualTo(Long value) {
			addCriterion("USABLE_BALANCE =", value, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceNotEqualTo(Long value) {
			addCriterion("USABLE_BALANCE <>", value, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceGreaterThan(Long value) {
			addCriterion("USABLE_BALANCE >", value, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceGreaterThanOrEqualTo(Long value) {
			addCriterion("USABLE_BALANCE >=", value, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceLessThan(Long value) {
			addCriterion("USABLE_BALANCE <", value, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceLessThanOrEqualTo(Long value) {
			addCriterion("USABLE_BALANCE <=", value, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceIn(List values) {
			addCriterion("USABLE_BALANCE in", values, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceNotIn(List values) {
			addCriterion("USABLE_BALANCE not in", values, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceBetween(Long value1, Long value2) {
			addCriterion("USABLE_BALANCE between", value1, value2, "usableBalance");
			return this;
		}

		public Criteria andUsableBalanceNotBetween(Long value1, Long value2) {
			addCriterion("USABLE_BALANCE not between", value1, value2, "usableBalance");
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
			addCriterion("CREATE_TIME not between", value1, value2, "createTime");
			return this;
		}

		public Criteria andVersionIsNull() {
			addCriterion("VERSION is null");
			return this;
		}

		public Criteria andVersionIsNotNull() {
			addCriterion("VERSION is not null");
			return this;
		}

		public Criteria andVersionEqualTo(Integer value) {
			addCriterion("VERSION =", value, "version");
			return this;
		}

		public Criteria andVersionNotEqualTo(Integer value) {
			addCriterion("VERSION <>", value, "version");
			return this;
		}

		public Criteria andVersionGreaterThan(Integer value) {
			addCriterion("VERSION >", value, "version");
			return this;
		}

		public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
			addCriterion("VERSION >=", value, "version");
			return this;
		}

		public Criteria andVersionLessThan(Integer value) {
			addCriterion("VERSION <", value, "version");
			return this;
		}

		public Criteria andVersionLessThanOrEqualTo(Integer value) {
			addCriterion("VERSION <=", value, "version");
			return this;
		}

		public Criteria andVersionIn(List values) {
			addCriterion("VERSION in", values, "version");
			return this;
		}

		public Criteria andVersionNotIn(List values) {
			addCriterion("VERSION not in", values, "version");
			return this;
		}

		public Criteria andVersionBetween(Integer value1, Integer value2) {
			addCriterion("VERSION between", value1, value2, "version");
			return this;
		}

		public Criteria andVersionNotBetween(Integer value1, Integer value2) {
			addCriterion("VERSION not between", value1, value2, "version");
			return this;
		}
	}
}