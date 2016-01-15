package com.ctfo.upp.view.beans;

import java.util.List;

import com.ctfo.csm.uaa.dao.beans.UAASystem;

/**
 * 
 * @ClassName: System
 * @Description:系统实体
 * @author Zhu.TW
 * @date:  2014年3月6日 下午4:08:35
 */
public class System extends UAASystem{

	private static final long serialVersionUID = 1L;
	
	//添加组织ID集合
	private String addOrgIds;
	
	//删除组织ID集合
	private String delOrgIds;
	
	//系统平台名称
	private String platformName;
	
	//组织类型
	private String orgType;
	
	private List<?> children;
	
	public List<?> getChildren() {
		return children;
	}

	public void setChildren(List<?> children) {
		this.children = children;
	}
	
	public String getAddOrgIds() {
		return addOrgIds;
	}

	public void setAddOrgIds(String addOrgIds) {
		this.addOrgIds = addOrgIds;
	}

	public String getDelOrgIds() {
		return delOrgIds;
	}

	public void setDelOrgIds(String delOrgIds) {
		this.delOrgIds = delOrgIds;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
}
