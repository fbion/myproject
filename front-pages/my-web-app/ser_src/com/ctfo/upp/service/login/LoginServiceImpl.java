package com.ctfo.upp.service.login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.csm.impl.multithread.TaskPool;
import com.ctfo.csm.soa.ServiceFactory;
import com.ctfo.csm.uaa.dao.beans.UAAOrg;
import com.ctfo.csm.uaa.dao.beans.UAAOrgRole;
import com.ctfo.csm.uaa.dao.beans.UAAPermission;
import com.ctfo.csm.uaa.dao.beans.UAAPermissionExampleExtended;
import com.ctfo.csm.uaa.dao.beans.UAASimpleCode;
import com.ctfo.csm.uaa.dao.beans.UAASimpleCodeExampleExtended;
import com.ctfo.csm.uaa.dao.beans.UAASystem;
import com.ctfo.csm.uaa.dao.beans.UAASystemExampleExtended;
import com.ctfo.csm.uaa.dao.beans.UAAUser;
import com.ctfo.csm.uaa.dao.beans.UAA_Relation_OrgRole_PermGroup;
import com.ctfo.csm.uaa.dao.beans.UAA_Relation_OrgRole_PermGroupExampleExtended;
import com.ctfo.csm.uaa.dao.beans.UAA_Relation_PermGroup_Perm;
import com.ctfo.csm.uaa.dao.beans.UAA_Relation_PermGroup_PermExampleExtended;
import com.ctfo.csm.uaa.external.intf.IUAABusinessManager;
import com.ctfo.csm.uaa.external.intf.IUAASystemManager;
import com.ctfo.csm.uaa.intf.authentication.IAuthentication;
import com.ctfo.csm.uaa.intf.support.AAException;
import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.csm.uaa.meta.beans.UAAOrgMeta;
import com.ctfo.csm.uaa.meta.beans.UAARoleMeta;
import com.ctfo.crm.intf.ICustomerService;
import com.ctfo.crm.meta.beans.CustomerMetaBean;
import com.ctfo.upp.service.AbstractService;
import com.ctfo.upp.util.BeanCopierUtils;
import com.ctfo.upp.view.beans.System;

@Service("loginService")
public class LoginServiceImpl extends AbstractService implements ILoginService {
	private static Log logger = LogFactory.getLog(LoginServiceImpl.class);

	public List<UAAPermission> queryPermissionList(String roleId,String systemId) {
		List<UAAPermission> result = null;
		try{
			//根据roleId查权限组，根据权限组查权限
			IUAASystemManager manager = (IUAASystemManager)ServiceFactory.getFactory().getService(IUAASystemManager.class);
			
			//设置角色与权限组的关联查询条件
			UAA_Relation_OrgRole_PermGroupExampleExtended roleGroupEE = new UAA_Relation_OrgRole_PermGroupExampleExtended(); 
			roleGroupEE.setSelectedField(UAA_Relation_OrgRole_PermGroup.fieldGroupId());
			roleGroupEE.createCriteria().andRoleIdEqualTo(roleId);
			
			//设置权限组与权限的关联查询条件
			UAA_Relation_PermGroup_PermExampleExtended groupPermEE = new UAA_Relation_PermGroup_PermExampleExtended();
			groupPermEE.setSelectedField(UAA_Relation_PermGroup_Perm.fieldPermId());
			groupPermEE.createCriteria().andGroupIdIn(Arrays.asList(roleGroupEE));
			
			//设置权限的查询条件
			UAAPermissionExampleExtended perEE = new UAAPermissionExampleExtended();
			perEE.createCriteria().andIdIn(Arrays.asList(groupPermEE)).andSystemIdEqualTo(systemId).andStatusEqualTo("1");
			Operator operator=new Operator();
			try {
				operator=this.getOnlieOperator();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			result =  manager.getUAAPermissionByExampleExtended(perEE, operator);
			
			//排序
			Collections.sort(result, new Comparator<Object>(){
				public int compare(Object o1, Object o2) {
					return ((UAAPermission)o1).getOrderNum().compareTo(((UAAPermission)o2).getOrderNum());
				}
			 });
			
		} catch (Exception e) {
			logger.error("查询当前登录用户权限失败！", e);
		}
		return result;
	}

	public List<UAARoleMeta> queryOrgRoleList(String systemSign,String userId) {
		List<UAARoleMeta> result = new ArrayList<UAARoleMeta>();
		try{
			final Operator op=this.getOnlieOperator();
			IUAABusinessManager manager = (IUAABusinessManager)ServiceFactory.getFactory().getService(IUAABusinessManager.class);
			List<UAAOrgRole> list = manager.getRolesBySystemSignAndUserId(systemSign, userId, op);
			if(list!=null && list.size() > 0){
				ArrayList<Runnable> tasks = new ArrayList<Runnable>();
				for(UAAOrgRole role : list){
					final UAARoleMeta roleMeta = new UAARoleMeta();
					roleMeta.setUaaOrgRole(role);
					result.add(roleMeta);
					Runnable task = new Runnable() {
						public void run() {
							IUAABusinessManager manager = (IUAABusinessManager)ServiceFactory.getFactory().getService(IUAABusinessManager.class);
							UAAOrgMeta orgMeta = manager.getUAAOrgById(roleMeta.getUaaOrgRole().getOrgid(), op);
							roleMeta.setOrgName(orgMeta.getUaaOrg().getOrgShortName());
						}
					};
					tasks.add(task);
				}
				TaskPool.completeTask(tasks);
			}
			
		} catch (Exception e) {
			logger.error("app service query model object fail", e);
		}
		return result;
	}

	public UAAUser queryUserByUserLogin(String userLogin) {
		UAAUser user = null;
		try{
			IAuthentication manager = (IAuthentication)ServiceFactory.getFactory().getService(IAuthentication.class);
			user = manager.getUserByAlias(userLogin);
			
		} catch (Exception e) {
			logger.error("找不到当前登录用户信息", e);
		}
		return user;
	}
	
	
	public String getOwnerUserNo(String ownerUserId) {
		String ownerUserNo = "";
		try{
			ICustomerService customerServicemanager = (ICustomerService)ServiceFactory.getFactory().getService(ICustomerService.class);
			
			CustomerMetaBean customerMetaBean = customerServicemanager.getCustomerById(ownerUserId, "com.ctfo.upp.webApp");
			
			//用户的会员编号
			ownerUserNo = customerMetaBean!=null && customerMetaBean.getCustomerInfo()!=null? customerMetaBean.getCustomerInfo().getCode():"";
			if(StringUtils.isBlank(ownerUserNo)){
				logger.warn("找不到["+ownerUserId+"]对应的会员编号！");
			}	
		} catch (Exception e) {
			logger.error("根据用户ID查找会员编号时异常！", e);
		}
		return ownerUserNo;
	}

	public com.ctfo.upp.view.beans.System querySystemBySystemSign(String systemSign) {
		System system =null;
		try {
			IUAASystemManager manager = (IUAASystemManager)ServiceFactory.getFactory().getService(IUAASystemManager.class);
			
			UAASystemExampleExtended  usee = new UAASystemExampleExtended();
			usee.createCriteria().andSystemUriEqualTo(systemSign);
			
			List<UAASystem> usList = manager.getUAASystemByExampleExtended(usee, this.getOnlieOperator());
			if(usList!=null && usList.size()>0){
				system = new System();
				BeanCopierUtils.copyProperties(usList.get(0), system);
				fillSystemPlatformName(system,this.getOnlieOperator());
			}
		} catch (Exception e) {
			logger.error("app service query model object fail", e);
		}
		return system;
	}
	
	/**
	 * 
	 * 填充System实体PlatformName属性值
	 * @param: @param system 系统实体
	 * @param: @param op   当前用户
	 */
	private void fillSystemPlatformName(final System system,final Operator op){
		IUAASystemManager systemManager = (IUAASystemManager)ServiceFactory.getFactory().getService(IUAASystemManager.class);
		UAASimpleCodeExampleExtended uscee = new UAASimpleCodeExampleExtended();
		uscee.createCriteria().andCodeEqualTo(system.getPlatform());
		List<UAASimpleCode> simpleCodeList = systemManager.getUAASimpleCodeByExampleExtended(uscee, op);
		system.setPlatformName(simpleCodeList!=null&& simpleCodeList.size() >0 ?  simpleCodeList.get(0).getName() : "");
	}
	
	public UAAOrg queryOrgById(String orgId) {
		UAAOrg org = null;
		try {
			IUAABusinessManager manager = (IUAABusinessManager)ServiceFactory.getFactory().getService(IUAABusinessManager.class);
			UAAOrgMeta orgMeta = manager.getUAAOrgById(orgId,this.getOnlieOperator());
			if(orgMeta!=null){
				org = orgMeta.getUaaOrg();
			}
		} catch (Exception e) {
			logger.error("app service query model object fail", e);
		}
		return org;
	}

	public UAAOrgRole queryOrgRoleById(String roleId) {
		UAAOrgRole role = null;
		try {
			IUAABusinessManager manager = (IUAABusinessManager)ServiceFactory.getFactory().getService(IUAABusinessManager.class);
			role = manager.getUAAOrgRoleById(roleId, super.getOnlieOperator());
		} catch (Exception e) {
			logger.error("app service query model object fail", e);
		}
		return role;
	}

	public boolean isModifyPassWord(String userId) {
		IUAABusinessManager manager = (IUAABusinessManager)ServiceFactory.getFactory().getService(IUAABusinessManager.class);
		return manager.isUAAPassWord(userId, this.getOnlieOperator());
	}

	public UAASystem getSystemBySystemSign(String systemSign) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasUserLoginRole(String userLogin, String roleId,String systemId) {
		// TODO Auto-generated method stub
		boolean flag =false;
		UAAUser user=queryUserByUserLogin(userLogin);
		IUAABusinessManager manager = (IUAABusinessManager)ServiceFactory.getFactory().getService(IUAABusinessManager.class);
		IUAASystemManager systemManager = (IUAASystemManager)ServiceFactory.getFactory().getService(IUAASystemManager.class);
		List<UAAOrgRole> list = manager.getRolesBySystemSignAndUserId(systemManager.getUAASystemById(systemId, new Operator()).getSystemUri(), user.getId(), new Operator());
		if(list!=null && list.size()>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public String modifyPassWord(String userId, String oldMD5, String newMD5) {
		try {
			IUAABusinessManager manager = (IUAABusinessManager)ServiceFactory.getFactory().getService(IUAABusinessManager.class);
			Operator operator = new Operator();
			operator = this.getOnlieOperator();
			manager.modifyPassword(userId, oldMD5, newMD5, operator);
		} catch (AAException e) {
			return "-1";
		}
		return "1";
		
	}



}
