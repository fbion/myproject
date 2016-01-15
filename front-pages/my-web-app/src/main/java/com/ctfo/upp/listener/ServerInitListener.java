package com.ctfo.upp.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.csm.uaa.dao.beans.UAAPermission;
import com.ctfo.csm.uaa.external.intf.IUAASystemManager;
import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.ResourceBundleUtil;

public class ServerInitListener implements ServletContextListener {
	
	private static Log logger = LogFactory.getLog(ServerInitListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.debug("server is shout down !");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		IUAASystemManager manager = (IUAASystemManager)ServiceFactory.getFactory().getService(IUAASystemManager.class);
		List<UAAPermission> list = manager.getUAAPermissionsBySystemSign(ResourceBundleUtil.getSystemString(Converter.SYS_SIGN), new Operator());
		Map<String, UAAPermission> allMap = new HashMap<String, UAAPermission>();
		for (int i = 0; list!=null && i < list.size(); i++) {
			if(StringUtils.isNotBlank(list.get(i).getPageUrl())){
				allMap.put(list.get(i).getPageUrl(), list.get(i));
			}
		}
		logger.debug("system All Permissions:"+allMap);
		arg0.getServletContext().setAttribute(Converter.CONTEXT_FULL_PERMISSION, allMap);
		logger.debug("server is started !");
	}

}
