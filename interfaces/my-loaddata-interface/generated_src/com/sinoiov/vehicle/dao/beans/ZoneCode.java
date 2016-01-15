package com.sinoiov.vehicle.dao.beans;

import com.sinoiov.beans.BaseSerializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;

public class ZoneCode extends BaseSerializable {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_ZONE_CODE.ID DB Comment: 
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	@XmlElement(name = "", required = false)
	private String id;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_ZONE_CODE.ZONE_CODE DB Comment: 
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	@XmlElement(name = "", required = false)
	private String zoneCode;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_ZONE_CODE.ZONE_NAME DB Comment: 
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	@XmlElement(name = "", required = false)
	private String zoneName;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_ZONE_CODE.SHORT_NAME DB Comment: 
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	@XmlElement(name = "", required = false)
	private String shortName;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_ZONE_CODE.LON DB Comment: 
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	@XmlElement(name = "", required = false)
	private BigDecimal lon;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_ZONE_CODE.LAT DB Comment: 
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	@XmlElement(name = "", required = false)
	private BigDecimal lat;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_ZONE_CODE.ZONE_LEVEL DB Comment: 
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	@XmlElement(name = "", required = false)
	private Short zoneLevel;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database column TB_ZONE_CODE.ZONE_FLAG DB Comment: 
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	@XmlElement(name = "", required = false)
	private Short zoneFlag;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_ZONE_CODE.ID
	 * @return  the value of TB_ZONE_CODE.ID
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_ZONE_CODE.ID
	 * @param id  the value for TB_ZONE_CODE.ID
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String fieldId() {
		return "ID";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_ZONE_CODE.ZONE_CODE
	 * @return  the value of TB_ZONE_CODE.ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getZoneCode() {
		return zoneCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_ZONE_CODE.ZONE_CODE
	 * @param zoneCode  the value for TB_ZONE_CODE.ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String fieldZoneCode() {
		return "ZONE_CODE";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_ZONE_CODE.ZONE_NAME
	 * @return  the value of TB_ZONE_CODE.ZONE_NAME
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_ZONE_CODE.ZONE_NAME
	 * @param zoneName  the value for TB_ZONE_CODE.ZONE_NAME
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String fieldZoneName() {
		return "ZONE_NAME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_ZONE_CODE.SHORT_NAME
	 * @return  the value of TB_ZONE_CODE.SHORT_NAME
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_ZONE_CODE.SHORT_NAME
	 * @param shortName  the value for TB_ZONE_CODE.SHORT_NAME
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String fieldShortName() {
		return "SHORT_NAME";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_ZONE_CODE.LON
	 * @return  the value of TB_ZONE_CODE.LON
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public BigDecimal getLon() {
		return lon;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_ZONE_CODE.LON
	 * @param lon  the value for TB_ZONE_CODE.LON
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String fieldLon() {
		return "LON";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_ZONE_CODE.LAT
	 * @return  the value of TB_ZONE_CODE.LAT
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public BigDecimal getLat() {
		return lat;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_ZONE_CODE.LAT
	 * @param lat  the value for TB_ZONE_CODE.LAT
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String fieldLat() {
		return "LAT";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_ZONE_CODE.ZONE_LEVEL
	 * @return  the value of TB_ZONE_CODE.ZONE_LEVEL
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public Short getZoneLevel() {
		return zoneLevel;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_ZONE_CODE.ZONE_LEVEL
	 * @param zoneLevel  the value for TB_ZONE_CODE.ZONE_LEVEL
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setZoneLevel(Short zoneLevel) {
		this.zoneLevel = zoneLevel;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String fieldZoneLevel() {
		return "ZONE_LEVEL";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the value of the database column TB_ZONE_CODE.ZONE_FLAG
	 * @return  the value of TB_ZONE_CODE.ZONE_FLAG
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public Short getZoneFlag() {
		return zoneFlag;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the value of the database column TB_ZONE_CODE.ZONE_FLAG
	 * @param zoneFlag  the value for TB_ZONE_CODE.ZONE_FLAG
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public void setZoneFlag(Short zoneFlag) {
		this.zoneFlag = zoneFlag;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public static String fieldZoneFlag() {
		return "ZONE_FLAG";
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_ZONE_CODE
	 * @abatorgenerated  Mon Jan 11 19:46:59 CST 2016
	 */
	public ZoneCode() {
		super();
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
}