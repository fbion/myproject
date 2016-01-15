package com.ctfo.upp.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.csm.uaa.dao.beans.UAAPermission;
import com.ctfo.upp.service.login.ILoginService;
import com.ctfo.upp.service.login.LoginServiceImpl;
import com.ctfo.upp.util.Converter;

public class LoginFilter implements Filter {
	
	private static Log logger = LogFactory.getLog(LoginFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String userLogin = request.getRemoteUser();
		String roleId=request.getParameter("uaa_roleId");
		String systemId=request.getParameter("uaa_systemId");
		String uri = request.getRequestURI();
		String contextName = request.getServletContext().getServletContextName();
		if(uri.indexOf("/"+contextName+"/")!=-1){
			uri=uri.substring(uri.indexOf("/"+contextName+"/")+("/"+contextName+"/").length());
		}else if(uri.substring(0,1).equals("/")){
			uri=uri.substring(1);
		}
		Map<String, UAAPermission> allPermission=(Map<String, UAAPermission>)request.getServletContext().getAttribute(Converter.CONTEXT_FULL_PERMISSION);
		boolean flag = false;
		if(allPermission.get(uri)==null){
			flag = true;
		}else{
			logger.debug("user:["+userLogin+"]=======send=========>>"+uri);//只对需要验证权限的uri进行log输出
			List<UAAPermission> menuList = (List<UAAPermission>)request.getSession().getAttribute(Converter.SESSION_MENU_LIST);
			List<UAAPermission> funList = (List<UAAPermission>)request.getSession().getAttribute(Converter.SESSION_FUNC_LIST);
			if(menuList==null && funList==null){//第一次进入时 权限为空的情况
				ILoginService loginService=new LoginServiceImpl();
				if(loginService.hasUserLoginRole(userLogin,roleId,systemId)){
					menuList = new ArrayList<UAAPermission>();
					funList = new ArrayList<UAAPermission>();
					List<UAAPermission> list = loginService.queryPermissionList(roleId,systemId);
					for (UAAPermission per : list) {
						if ("menu".equals(per.getType()))
							menuList.add(per);
						else
							funList.add(per);
					}
					if (menuList.size() > 0)
						request.getSession().setAttribute(Converter.SESSION_MENU_LIST, menuList);
					if (funList.size() > 0)
						request.getSession().setAttribute(Converter.SESSION_FUNC_LIST, funList);
				}
			}
			
			for (int i = 0; menuList!=null && i < menuList.size(); i++) {
				if(StringUtils.isBlank(menuList.get(i).getPageUrl())){
					continue ;
				}
				if( menuList.get(i).getPageUrl().endsWith(uri)){
					flag =true ;
					break ;
				}
			}
			if(!flag){
				for (int i = 0; menuList!=null &&  i < funList.size(); i++) {
					if(StringUtils.isBlank(funList.get(i).getPageUrl())){
						continue ;
					}
					if( funList.get(i).getPageUrl().endsWith(uri)){
						flag =true ;
						break ;
					}
				}
			}
		}
		if(flag){
			arg2.doFilter(request, response);
		}else{
			logger.warn("No permission to access. url=["+uri+"]");
			String header = request.getHeader("x-requested-with");
			if(header==null){
				response.sendRedirect(request.getContextPath()+"/pages/error.jsp");
			}else{
				response.getWriter().write("-10");
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
