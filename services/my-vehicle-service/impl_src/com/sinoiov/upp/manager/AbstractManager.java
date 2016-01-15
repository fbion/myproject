package com.sinoiov.upp.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.base.intf.external.IExternalSystem;
import com.ctfo.upp.manager.support.UppGenericManagerImpl;
import com.ctfo.upp.soa.ServiceFactory;

public abstract class AbstractManager extends UppGenericManagerImpl<Object, Object>{

	private Map<String, String> userNoMap = new HashMap<String, String>();

	private static final Log logger = LogFactory.getLog(AbstractManager.class);

	//基础服务接口
	protected IExternalSystem externalSystem;

	//获取会员编号
	protected String getOwnerUserNo(String ownerUserId){
		String ownerUserNo = "";
		try{
			if(StringUtils.isBlank(ownerUserId))return "";
			ownerUserNo = userNoMap.get(ownerUserId);
			if(StringUtils.isBlank(ownerUserNo)){
				externalSystem = externalSystem==null?(IExternalSystem) ServiceFactory.getFactory().getService(IExternalSystem.class):externalSystem;
				ownerUserNo = externalSystem.getOwnerUserNo(ownerUserId);
				userNoMap.put(ownerUserId, ownerUserNo);
			}
		}catch(Exception e){
			logger.warn("获取会员编号时异常", e);
		}
		return ownerUserNo;
	}
	
	
}
