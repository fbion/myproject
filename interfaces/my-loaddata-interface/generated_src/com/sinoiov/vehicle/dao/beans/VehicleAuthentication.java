package com.sinoiov.vehicle.dao.beans;

import com.sinoiov.beans.BaseSerializable;
import javax.xml.bind.annotation.XmlElement;

public class VehicleAuthentication extends BaseSerializable {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column TB_VEHICLE_AUTHENTICATION.ID
     * DB Comment: ID
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    @XmlElement(name="ID", required=false )
    private String id;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_NO
     * DB Comment: 车辆车牌号
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    @XmlElement(name="车辆车牌号", required=false )
    private String vehiclePlateNo;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_COLOR
     * DB Comment: 车牌号颜色
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    @XmlElement(name="车牌号颜色", required=false )
    private String vehiclePlateColor;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_KEY
     * DB Comment: 认证字段
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    @XmlElement(name="认证字段", required=false )
    private String authenticationKey;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_VALUE
     * DB Comment: 认证字段值
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    @XmlElement(name="认证字段值", required=false )
    private String authenticationValue;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column TB_VEHICLE_AUTHENTICATION.SYSTEM_CODE
     * DB Comment: 认证来源系统编码
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    @XmlElement(name="认证来源系统编码", required=false )
    private String systemCode;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column TB_VEHICLE_AUTHENTICATION.ID
     *
     * @return the value of TB_VEHICLE_AUTHENTICATION.ID
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column TB_VEHICLE_AUTHENTICATION.ID
     *
     * @param id the value for TB_VEHICLE_AUTHENTICATION.ID
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public static String fieldId() {
        return "ID";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_NO
     *
     * @return the value of TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_NO
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public String getVehiclePlateNo() {
        return vehiclePlateNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_NO
     *
     * @param vehiclePlateNo the value for TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_NO
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setVehiclePlateNo(String vehiclePlateNo) {
        this.vehiclePlateNo = vehiclePlateNo;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public static String fieldVehiclePlateNo() {
        return "VEHICLE_PLATE_NO";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_COLOR
     *
     * @return the value of TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_COLOR
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public String getVehiclePlateColor() {
        return vehiclePlateColor;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_COLOR
     *
     * @param vehiclePlateColor the value for TB_VEHICLE_AUTHENTICATION.VEHICLE_PLATE_COLOR
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setVehiclePlateColor(String vehiclePlateColor) {
        this.vehiclePlateColor = vehiclePlateColor;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public static String fieldVehiclePlateColor() {
        return "VEHICLE_PLATE_COLOR";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_KEY
     *
     * @return the value of TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_KEY
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public String getAuthenticationKey() {
        return authenticationKey;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_KEY
     *
     * @param authenticationKey the value for TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_KEY
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public static String fieldAuthenticationKey() {
        return "AUTHENTICATION_KEY";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_VALUE
     *
     * @return the value of TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_VALUE
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public String getAuthenticationValue() {
        return authenticationValue;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_VALUE
     *
     * @param authenticationValue the value for TB_VEHICLE_AUTHENTICATION.AUTHENTICATION_VALUE
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setAuthenticationValue(String authenticationValue) {
        this.authenticationValue = authenticationValue;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public static String fieldAuthenticationValue() {
        return "AUTHENTICATION_VALUE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column TB_VEHICLE_AUTHENTICATION.SYSTEM_CODE
     *
     * @return the value of TB_VEHICLE_AUTHENTICATION.SYSTEM_CODE
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column TB_VEHICLE_AUTHENTICATION.SYSTEM_CODE
     *
     * @param systemCode the value for TB_VEHICLE_AUTHENTICATION.SYSTEM_CODE
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public static String fieldSystemCode() {
        return "SYSTEM_CODE";
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table TB_VEHICLE_AUTHENTICATION
     *
     * @abatorgenerated Mon Jan 11 19:46:59 CST 2016
     */
    public VehicleAuthentication() {
        super();
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
}