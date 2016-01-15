package com.sinoiov.upp.portal.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.crm.intf.ICustomerService;
import com.ctfo.crm.meta.beans.CustomerMetaBean;
import com.ctfo.csm.soa.ServiceFactory;
import com.ctfo.csm.uaa.dao.beans.UAAUser;
import com.ctfo.csm.uaa.intf.authentication.IAuthentication;
import com.sinoiov.upp.portal.controller.vo.CashierAccountDTO;
@Service
public class QueryUaaUserInfo implements IQueryUaaUserInfo {
	
	private static Log logger = LogFactory.getLog(QueryUaaUserInfo.class);

	@Override
	public CashierAccountDTO getUaaUserInfo(String loginName) {
		
		UAAUser user = null;
		CashierAccountDTO userDto = new CashierAccountDTO();
		//用户所属地区
		String applyPersonPost = "";
		try{
			//获取认证接口
		//	IAuthentication manager = (IAuthentication)ServiceFactory.getFactory().getService(IAuthentication.class);
			ICustomerService customerServicemanager = (ICustomerService)ServiceFactory.getFactory().getService(ICustomerService.class);
			//通过登录用户名获取uaa用户信息
	//		user = manager.getUserByAlias(loginName);
			CustomerMetaBean customerById = customerServicemanager.queryCustomerByLogin(1,loginName, "com.ctfo.upp.cashierApp");
			String mobileNo = customerById.getCustomerInfo().getMobile();
			String userId = customerById.getCustomerInfo().getFirstSigninUserId();
			//用户的会员编号
			String ownerUserNo = customerById.getCustomerInfo().getCode();
			if(StringUtils.isNotBlank(customerById.getCustomerInfo().getPltpCountyCode())){
				applyPersonPost = customerById.getCustomerInfo().getPltpCountyCode();
			}else if(StringUtils.isNotBlank(customerById.getCustomerInfo().getPltpCityCode())){
				applyPersonPost = customerById.getCustomerInfo().getPltpCityCode();
			}else{
				applyPersonPost = customerById.getCustomerInfo().getPltpProvinceCode();
			}
			userDto.setOwnerUserId(userId);
			userDto.setUserId(userId);
			userDto.setOwnerUserNo(ownerUserNo);
			userDto.setOwnerLoginName(loginName);
			userDto.setApplyPersonPost(applyPersonPost);
			userDto.setMobileNo(mobileNo);
			userDto.setUserName(StringUtils.isBlank(customerById.getCustomerInfo().getPltpPersonName()) ? loginName:customerById.getCustomerInfo().getPltpPersonName());
		} catch (Exception e) {
			logger.error("获取统一认证用户信息异常！", e);
		}
		return userDto;
	}

}
