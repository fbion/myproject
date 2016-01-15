package com.ctfo.upp.service.login;

import java.util.List;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.csm.uaa.dao.beans.UAAOrg;
import com.ctfo.csm.uaa.dao.beans.UAAOrgRole;
import com.ctfo.csm.uaa.dao.beans.UAAPermission;
import com.ctfo.csm.uaa.dao.beans.UAAUser;
import com.ctfo.csm.uaa.meta.beans.UAARoleMeta;
/**
 * 
 * 登录管理接口类
 *
 * @version 1.0
 * @author malongqing
 * @date    2013-12-26
 * @since JDK1.6
 */
@AnnotationName(name = "登陆管理")
public interface ILoginService {

	/**
	 * 根据当前角色,获取该角色下的所有权限
	 * @param roleId
	 * @param systemId
	 * @param op
	 * @return List<UAAPermission>
	 * @author malongqing
	 * @date  2014-03-28
	 */
	@AnnotationName(name = "根据角色获取权限集合")
	List<UAAPermission> queryPermissionList(String roleId,String systemId);
	
	
	/**
	 * 获取当前用户所有角色集合
	 * @param systemSign
	 * @param userId
	 * @param op
	 * @return List<UAAOrgRole>
	 * @author malongqing
	 * @date  2014-04-01
	 */
	@AnnotationName(name = "根据系统标示，用户Id获取角色集合")
	List<UAARoleMeta> queryOrgRoleList(String systemSign,String userId);
	
	
	/**
	 * 根据登录用户名获取用户信息
	 * @param loginName
	 * @param op
	 * @return UAAUser
	 * @author malongqing
	 * @date  2014-04-01
	 */
	@AnnotationName(name = "根据登录用户名获取用户信息")
	UAAUser queryUserByUserLogin(String userLogin);
	
	
	/**
	 * 根据系统标识获取系统信息
	 * @param systemSign
	 * @param op
	 * @return System
	 * @author malongqing
	 * @date  2014-04-01
	 */
	@AnnotationName(name = "根据系统标识获取系统信息")
	com.ctfo.csm.uaa.dao.beans.UAASystem getSystemBySystemSign(String systemSign);
	
	/**
	 * 根据系统标识获取系统信息
	 * @param systemSign
	 * @param op
	 * @return System
	 * @author zhutianwei
	 * @date  2014-04-01
	 */
	@AnnotationName(name = "根据系统标识获取系统信息")
	com.ctfo.upp.view.beans.System querySystemBySystemSign(String systemSign);
	
	
	/**
	 * 根据组织Id获取组织信息
	 * @param orgId
	 * @param op
	 * @return UaaOrg
	 * @author malongqing
	 * @date  2014-04-01
	 */
	@AnnotationName(name = "根据组织Id获取组织信息")
	UAAOrg queryOrgById(String orgId);
	
	/**
	 * 根据角色Id获取角色信息
	 * @param roleId
	 * @param op
	 * @return UAAOrgRole
	 * @author malongqing
	 * @date  2014-04-01
	 */
	@AnnotationName(name = "根据角色Id获取角色信息")
	UAAOrgRole queryOrgRoleById(String roleId);
	
	
	/**
	 * 判断用户密码是否修改过
	 * 
	 * @param userId
	 * 			用户主键
	 * @return boolean true:密码没改过；false：密码改过
	 * 			
	 * 
	 */
	@AnnotationName(name = "用户密码是否修改过")
	boolean isModifyPassWord(String userId );

	/**
	 * 
	 * @param userLogin 登录名
	 * @param roleId 角色id
	 * @param systemId 系统id
	 * @return
	 */
	@AnnotationName(name = "用户是否拥有角色id")
	boolean hasUserLoginRole(String userLogin, String roleId,String systemId);
	/**
	 * 
	 * @param userId
	 * @param oldMD5
	 * @param newMD5
	 */
	@AnnotationName(name = "修改当前登录用户密码")
	public String modifyPassWord(String userId,String oldMD5,String newMD5);
	/**
	 * 根据用户ID查找会员编号
	 * @param ownerUserId
	 * @return
	 */
	public String getOwnerUserNo(String ownerUserId);
	
}
