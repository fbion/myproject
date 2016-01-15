package com.ctfo.sinoiov.mobile.webapi.bean.request;

import java.io.Serializable;

/**
 * <p>
 * -----------------------------------------------------------------------------
 * <br>
 * 工程名 ： pltp-webapi <br>
 * 功能：基础实体 <br>
 * 描述：包括各实体中一些公共的属性 <br>
 * 授权 : (C) Copyright (c) 2011 <br>
 * 公司 : 北京中交车联科技服务有限公司 <br>
 * -----------------------------------------------------------------------------
 * <br>
 * 修改历史 <br>
 * <table width="432" border="1">
 * <tr>
 * <td>版本</td>
 * <td>时间</td>
 * <td>作者</td>
 * <td>改变</td>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014-3-3</td>
 * <td>JiangXF</td>
 * <td>创建</td>
 * </tr>
 * </table>
 * <br>
 * <font color="#FF0000">注意: 本内容仅限于[北京中交兴路信息科技有限公司]内部使用，禁止转发</font> <br>
 * 
 * @author JiangXF
 */
public class BaseBeanReq implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tokenId; // 用户认证

	private String userId; // 用户ID

	private String userType; // 用户类型

	private String userName; // 用户名称

	private String userLogin; // 登陆账户

	private String imageClass; // 用于上传图片

	// 车后会员id
	private String memberId;

	// 手机号
	private String mobile;

	// 乐观锁
	private String version;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getImageClass() {
		return imageClass;
	}

	public void setImageClass(String imageClass) {
		this.imageClass = imageClass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

}
