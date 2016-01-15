package com.sinoiov.upp.business;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.base.intf.external.IExternalSystem;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.base.external.uaa.User;
import com.sinoiov.base.external.uaa.UserExt;

public abstract class AbstractBusiness{
	
	private static final Log logger = LogFactory.getLog(AbstractBusiness.class);
	
	private Map<String, UserExt> userMap = new HashMap<String, UserExt>();

	//基础服务接口
	protected IExternalSystem externalSystem;

	
	//获取会员
	private UserExt getOwnerUser(String userId){
		UserExt userExt = null;
		try{
			if(StringUtils.isBlank(userId))return null;

			userExt = userMap.get(userId);
			if(userExt==null){
				externalSystem = externalSystem==null?(IExternalSystem) ServiceFactory.getFactory().getService(IExternalSystem.class):externalSystem;
				userExt = externalSystem.getOwnerUserExtByUserId(userId);
				userMap.put(userId, userExt);
			}
		}catch(Exception e){
			logger.warn("获取会员信息时异常", e);
		}
		return userExt;
	}


	//获取会员编号
	protected String getOwnerUserNo(String ownerUserId){
		String ownerUserNo = "";
		try{
			ownerUserNo = this.getOwnerUser(ownerUserId)!=null?this.getOwnerUser(ownerUserId).getOwnerUserNo():"";
		}catch(Exception e){
			logger.warn("获取会员编号时异常", e);
		}
		return ownerUserNo;
	}
	protected String getUserName(String ownerUserId){
		String userName = "";
		try{
			userName = this.getOwnerUser(ownerUserId)!=null?this.getOwnerUser(ownerUserId).getUserName():"";
		}catch(Exception e){
			logger.warn("获取会员编号时异常", e);
		}
		return userName;
	}
	//获取会员编号
	protected String getOwnerUserPost(String userLogin){
		String ownerUserPost = "";
		try{
			externalSystem = externalSystem==null?(IExternalSystem) ServiceFactory.getFactory().getService(IExternalSystem.class):externalSystem;
			User user = externalSystem.queryUserByUserLogin(userLogin);
			ownerUserPost = this.getOwnerUser(user.getId())!=null?this.getOwnerUser(user.getId()).getOwnerUserPost():"";
		}catch(Exception e){
			logger.warn("获取业务员所属地区时异常", e);
		}
		return ownerUserPost;
	}

	
	
}
