package com.sinoiov.vehicle.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleAuthenticationExampleExtended implements Serializable {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    protected List oredCriteria;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    protected String selectedField;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    protected int skipNum;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    protected int endNum;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    protected int limitNum;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public VehicleAuthenticationExampleExtended() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    protected VehicleAuthenticationExampleExtended(VehicleAuthenticationExampleExtended example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setSelectedField(String selectedField) {
        this.selectedField = selectedField;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public String getSelectedField() {
        return selectedField;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public static String tableName() {
        return "TB_VEHICLE_AUTHENTICATION";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public static String daoInterface() {
        return "com.sinoiov.vehicle.dao.VehicleAuthenticationDAO";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setSkipNum(int skipNum) {
        this.skipNum = skipNum;
        this.endNum = this.skipNum + this.limitNum +1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public int getSkipNum() {
        return this.skipNum;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setEndNum(int endNum) {
        this.endNum = endNum;
        this.limitNum = this.endNum - this.skipNum -1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public int getEndNum() {
        return this.endNum;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
        this.endNum = this.skipNum + this.limitNum +1;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public int getLimitNum() {
        return this.limitNum;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
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

        public Criteria andVehiclePlateNoIsNull() {
            addCriterion("VEHICLE_PLATE_NO is null");
            return this;
        }

        public Criteria andVehiclePlateNoIsNotNull() {
            addCriterion("VEHICLE_PLATE_NO is not null");
            return this;
        }

        public Criteria andVehiclePlateNoEqualTo(String value) {
            addCriterion("VEHICLE_PLATE_NO =", value, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoNotEqualTo(String value) {
            addCriterion("VEHICLE_PLATE_NO <>", value, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoGreaterThan(String value) {
            addCriterion("VEHICLE_PLATE_NO >", value, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoGreaterThanOrEqualTo(String value) {
            addCriterion("VEHICLE_PLATE_NO >=", value, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoLessThan(String value) {
            addCriterion("VEHICLE_PLATE_NO <", value, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoLessThanOrEqualTo(String value) {
            addCriterion("VEHICLE_PLATE_NO <=", value, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoLike(String value) {
            addCriterion("VEHICLE_PLATE_NO like", value, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoNotLike(String value) {
            addCriterion("VEHICLE_PLATE_NO not like", value, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoIn(List values) {
            addCriterion("VEHICLE_PLATE_NO in", values, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoNotIn(List values) {
            addCriterion("VEHICLE_PLATE_NO not in", values, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoBetween(String value1, String value2) {
            addCriterion("VEHICLE_PLATE_NO between", value1, value2, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateNoNotBetween(String value1, String value2) {
            addCriterion("VEHICLE_PLATE_NO not between", value1, value2, "vehiclePlateNo");
            return this;
        }

        public Criteria andVehiclePlateColorIsNull() {
            addCriterion("VEHICLE_PLATE_COLOR is null");
            return this;
        }

        public Criteria andVehiclePlateColorIsNotNull() {
            addCriterion("VEHICLE_PLATE_COLOR is not null");
            return this;
        }

        public Criteria andVehiclePlateColorEqualTo(String value) {
            addCriterion("VEHICLE_PLATE_COLOR =", value, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorNotEqualTo(String value) {
            addCriterion("VEHICLE_PLATE_COLOR <>", value, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorGreaterThan(String value) {
            addCriterion("VEHICLE_PLATE_COLOR >", value, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorGreaterThanOrEqualTo(String value) {
            addCriterion("VEHICLE_PLATE_COLOR >=", value, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorLessThan(String value) {
            addCriterion("VEHICLE_PLATE_COLOR <", value, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorLessThanOrEqualTo(String value) {
            addCriterion("VEHICLE_PLATE_COLOR <=", value, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorLike(String value) {
            addCriterion("VEHICLE_PLATE_COLOR like", value, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorNotLike(String value) {
            addCriterion("VEHICLE_PLATE_COLOR not like", value, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorIn(List values) {
            addCriterion("VEHICLE_PLATE_COLOR in", values, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorNotIn(List values) {
            addCriterion("VEHICLE_PLATE_COLOR not in", values, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorBetween(String value1, String value2) {
            addCriterion("VEHICLE_PLATE_COLOR between", value1, value2, "vehiclePlateColor");
            return this;
        }

        public Criteria andVehiclePlateColorNotBetween(String value1, String value2) {
            addCriterion("VEHICLE_PLATE_COLOR not between", value1, value2, "vehiclePlateColor");
            return this;
        }

        public Criteria andAuthenticationKeyIsNull() {
            addCriterion("AUTHENTICATION_KEY is null");
            return this;
        }

        public Criteria andAuthenticationKeyIsNotNull() {
            addCriterion("AUTHENTICATION_KEY is not null");
            return this;
        }

        public Criteria andAuthenticationKeyEqualTo(String value) {
            addCriterion("AUTHENTICATION_KEY =", value, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyNotEqualTo(String value) {
            addCriterion("AUTHENTICATION_KEY <>", value, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyGreaterThan(String value) {
            addCriterion("AUTHENTICATION_KEY >", value, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyGreaterThanOrEqualTo(String value) {
            addCriterion("AUTHENTICATION_KEY >=", value, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyLessThan(String value) {
            addCriterion("AUTHENTICATION_KEY <", value, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyLessThanOrEqualTo(String value) {
            addCriterion("AUTHENTICATION_KEY <=", value, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyLike(String value) {
            addCriterion("AUTHENTICATION_KEY like", value, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyNotLike(String value) {
            addCriterion("AUTHENTICATION_KEY not like", value, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyIn(List values) {
            addCriterion("AUTHENTICATION_KEY in", values, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyNotIn(List values) {
            addCriterion("AUTHENTICATION_KEY not in", values, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyBetween(String value1, String value2) {
            addCriterion("AUTHENTICATION_KEY between", value1, value2, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationKeyNotBetween(String value1, String value2) {
            addCriterion("AUTHENTICATION_KEY not between", value1, value2, "authenticationKey");
            return this;
        }

        public Criteria andAuthenticationValueIsNull() {
            addCriterion("AUTHENTICATION_VALUE is null");
            return this;
        }

        public Criteria andAuthenticationValueIsNotNull() {
            addCriterion("AUTHENTICATION_VALUE is not null");
            return this;
        }

        public Criteria andAuthenticationValueEqualTo(String value) {
            addCriterion("AUTHENTICATION_VALUE =", value, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueNotEqualTo(String value) {
            addCriterion("AUTHENTICATION_VALUE <>", value, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueGreaterThan(String value) {
            addCriterion("AUTHENTICATION_VALUE >", value, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueGreaterThanOrEqualTo(String value) {
            addCriterion("AUTHENTICATION_VALUE >=", value, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueLessThan(String value) {
            addCriterion("AUTHENTICATION_VALUE <", value, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueLessThanOrEqualTo(String value) {
            addCriterion("AUTHENTICATION_VALUE <=", value, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueLike(String value) {
            addCriterion("AUTHENTICATION_VALUE like", value, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueNotLike(String value) {
            addCriterion("AUTHENTICATION_VALUE not like", value, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueIn(List values) {
            addCriterion("AUTHENTICATION_VALUE in", values, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueNotIn(List values) {
            addCriterion("AUTHENTICATION_VALUE not in", values, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueBetween(String value1, String value2) {
            addCriterion("AUTHENTICATION_VALUE between", value1, value2, "authenticationValue");
            return this;
        }

        public Criteria andAuthenticationValueNotBetween(String value1, String value2) {
            addCriterion("AUTHENTICATION_VALUE not between", value1, value2, "authenticationValue");
            return this;
        }

        public Criteria andSystemCodeIsNull() {
            addCriterion("SYSTEM_CODE is null");
            return this;
        }

        public Criteria andSystemCodeIsNotNull() {
            addCriterion("SYSTEM_CODE is not null");
            return this;
        }

        public Criteria andSystemCodeEqualTo(String value) {
            addCriterion("SYSTEM_CODE =", value, "systemCode");
            return this;
        }

        public Criteria andSystemCodeNotEqualTo(String value) {
            addCriterion("SYSTEM_CODE <>", value, "systemCode");
            return this;
        }

        public Criteria andSystemCodeGreaterThan(String value) {
            addCriterion("SYSTEM_CODE >", value, "systemCode");
            return this;
        }

        public Criteria andSystemCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SYSTEM_CODE >=", value, "systemCode");
            return this;
        }

        public Criteria andSystemCodeLessThan(String value) {
            addCriterion("SYSTEM_CODE <", value, "systemCode");
            return this;
        }

        public Criteria andSystemCodeLessThanOrEqualTo(String value) {
            addCriterion("SYSTEM_CODE <=", value, "systemCode");
            return this;
        }

        public Criteria andSystemCodeLike(String value) {
            addCriterion("SYSTEM_CODE like", value, "systemCode");
            return this;
        }

        public Criteria andSystemCodeNotLike(String value) {
            addCriterion("SYSTEM_CODE not like", value, "systemCode");
            return this;
        }

        public Criteria andSystemCodeIn(List values) {
            addCriterion("SYSTEM_CODE in", values, "systemCode");
            return this;
        }

        public Criteria andSystemCodeNotIn(List values) {
            addCriterion("SYSTEM_CODE not in", values, "systemCode");
            return this;
        }

        public Criteria andSystemCodeBetween(String value1, String value2) {
            addCriterion("SYSTEM_CODE between", value1, value2, "systemCode");
            return this;
        }

        public Criteria andSystemCodeNotBetween(String value1, String value2) {
            addCriterion("SYSTEM_CODE not between", value1, value2, "systemCode");
            return this;
        }
    }
}