package com.sinoiov.upp.portal.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.csm.soa.ServiceFactory;
import com.ctfo.csm.uaa.dao.beans.UAAPermission;
import com.ctfo.csm.uaa.dao.beans.UAAPermissionExampleExtended;
import com.ctfo.csm.uaa.dao.beans.UAASystem;
import com.ctfo.csm.uaa.dao.beans.UAASystemExampleExtended;
import com.ctfo.csm.uaa.dao.beans.UAA_Relation_OrgRole_PermGroup;
import com.ctfo.csm.uaa.dao.beans.UAA_Relation_OrgRole_PermGroupExampleExtended;
import com.ctfo.csm.uaa.dao.beans.UAA_Relation_PermGroup_Perm;
import com.ctfo.csm.uaa.dao.beans.UAA_Relation_PermGroup_PermExampleExtended;
import com.ctfo.csm.uaa.external.intf.IUAASystemManager;
import com.ctfo.csm.uaa.intf.support.Operator;
import com.sinoiov.upp.portal.utils.Converter;
import com.sinoiov.upp.portal.viex.beans.Index;


@WebListener
public class AccessSandboxFilter implements Filter ,HttpSessionListener {
	private static Log logger = LogFactory.getLog(AccessSandboxFilter.class);
	String errorUrl = null;
	static String systemSign = null;
	int errorCode = HttpServletResponse.SC_FORBIDDEN;
	@Override
	public void destroy() {
		Sandbox.destroy();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filters) throws IOException, ServletException {
		 final HttpServletRequest request = (HttpServletRequest) servletRequest;
		 if( Sandbox.isInBox(request)){
			 filters.doFilter(servletRequest, servletResponse);
		 }else{
			 logger.warn("Invalid Access to url: " + request.getRequestURL() + "  ,  user: " + request.getRemoteUser() + " , IP:" + request.getRemoteAddr());
			 
			 if( this.errorUrl != null){
				 HttpServletResponse response = ( HttpServletResponse)servletResponse;
				 String toUrl = request.getContextPath() + errorUrl.trim();
				 logger.warn("REDIRECT Access to url: " +toUrl + "  , from url:"+ request.getRequestURL() + "  ,  user: " + request.getRemoteUser() + " , IP:" + request.getRemoteAddr());
				 //The asynchronous request
				 if(request.getHeader("X-Requested-With")!=null && request.getHeader("X-Requested-With").equalsIgnoreCase("xmlhttprequest"))
		         {
					response.setStatus(500);
		          	response.setHeader("forwardPath", toUrl);
		          	response.setCharacterEncoding("UTF-8");
					response.getWriter().append("您所请求的资源不合法或者发生错误");
					response.getWriter().close();
		         }else//The Synchronous request
				    response.sendRedirect(toUrl);
				 return;
			 }else{
				 HttpServletResponse response = ( HttpServletResponse)servletResponse;
				 response.setStatus(errorCode); 
				 logger.warn("FORBIDDEND Access to url: " + request.getRequestURL() + "  ,  user: " + request.getRemoteUser() + " , IP:" + request.getRemoteAddr());
				 response.sendError(errorCode);
				 response.setCharacterEncoding("UTF-8");
				 response.getWriter().append("你没有访问此资源的权限");
				 response.getWriter().close();
				 return;
			 }
		 }
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String url = config.getInitParameter("errorUrl");
		if( url != null && !url.trim().equals("")){
			this.errorUrl = url;
			logger.info("When invalid accessing to action , errorUrl is :" + this.errorUrl);
		}
		
		String systemSign = config.getInitParameter("systemSign");
		if(systemSign!=null && !systemSign.trim().equals("") ){
			AccessSandboxFilter.systemSign = systemSign;
		}
		
		Sandbox.init();
	}
	
	public static void frushUserAccess(HttpServletRequest request){
		Index index = (Index)request.getSession().getAttribute(Converter.SESSION_INDEX);
		Operator oper = Converter.getOperator(request);
		Sandbox.queryUserAccess(index,oper, request);
	}
	
	 static  class Sandbox{
		 
		 //存储所有权限<url,permId>
		 static private Map<String,String>  allAccess = new HashMap<String,String>();
		 static private Map<String,String>  allAccessBK = new HashMap<String, String>();
		 static String lastSystem = null;
		 
		 //存储所有用户的权限<sessionId,<url,permId>>
		 static private Map<String,Map<String,String>>  userAccess = new ConcurrentHashMap<String,Map<String,String>>();
		 
		static void init(){
			initAllAccess();
		}
		static void destroy(){
			allAccess.clear();
			allAccessBK.clear();
			userAccess.clear();
			lastSystem = null;
			inited = false;
		}
		
		static boolean isInBox(HttpServletRequest request){
			
			boolean isLogin = request.getSession().getAttribute(Converter.SESSION_INDEX) != null  
					&& request.getSession().getAttribute(Converter.SESSION_REMOTE_USER) != null;
			
			if( !isLogin){
				logger.warn("it's not my duty on this time, Why do filter before loginned?");
				return true; // it's not my duty on this time
			}
			
			Operator oper = Converter.getOperator(request);
			logger.debug("Logined Operator is : " +  ( oper  == null? "Null" : oper.getUserId() ));
			if( oper == null){
				request.getSession().invalidate();
				return false;
			}
			
			Map<String,String> userAcc = getUserAccess(oper, request);
			
			boolean isControlled = accessInRequest(allAccess, request, false);
			logger.debug("Request : " +  request.getRequestURL()   + "  isControlled ? " + isControlled );
			if( !isControlled) return true;
			
			boolean isValid = accessInRequest(userAcc, request, true);
			logger.debug("Request : " +  request.getRequestURL()   + "  isValid ? " + isValid );
			if( isValid) return true;
			
			return false;
			
		}
		
		static boolean inited = false;
		static  synchronized void initAllAccess(){
			if( inited) return;
			new Thread( new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					synchronized(Sandbox.class){
						
						if( inited) return ;
						
						List<UAAPermission> perms = getAllPermissions();
						for( UAAPermission perm : perms){
							String access = extractFromUrl( null, perm.getPageUrl() );
							if( access != null){
								allAccess.put(access, perm.getId());

							}
						}
						
						allAccessBK.putAll(allAccess);
						
						logger.debug("allAccess:" + allAccess);
						//todo
						//set message handler to update UIFunction add/remove
						
						inited = true;
					}
				}
				
			}).start();
		}
		
		//获取用户权限
		static Map<String,String> getUserAccess(Operator oper, HttpServletRequest request){
			
			if( userAccess.containsKey(request.getSession().getId())){
				return userAccess.get(request.getSession().getId());
			}
			
			Index index = (Index)request.getSession().getAttribute(Converter.SESSION_INDEX);
			if( inited && !index.getSystemSign().equals(lastSystem)){
				allAccess = filterSystemAccess(allAccessBK , index.getSystemId(),oper);
				lastSystem = index.getSystemSign();
				logger.debug("SYSTEM: " + index.getSystemSign() + " ,allAccess:" + allAccess);
			}
			return queryUserAccess(index,oper,request);
		}
		
		static Map<String,String> queryUserAccess(Index index,Operator oper, HttpServletRequest request){
			Map<String,String> ret = new HashMap<String,String>();
			List<UAAPermission> perms = getPermsByRoleIdAndSystemId(index.getRoleId(), index.getSystemId(),oper);
			for( UAAPermission perm : perms){
				String access = extractFromUrl(null, perm.getPageUrl());
				if( access != null){
					ret.put(access, perm.getId());
				}
			}
			userAccess.put(request.getSession().getId(), ret);
			logger.debug("UserAccess:" + ret);
			return ret;
		}
		
		
		static boolean accessInRequest(Map<String,String> access, HttpServletRequest request, boolean returnTrueWhenIgnore){
			String acc = extractFromUrl(request.getServletContext().getContextPath(), request.getRequestURL().toString());
			if( acc == null ) return returnTrueWhenIgnore;
			return access.containsKey( acc);
		}
		
		//获取某系统权限
		static Map<String,String> filterSystemAccess( Map<String,String> all, String systemId,Operator op){
			Map<String, String> ret = new HashMap<String,String>();
			IUAASystemManager manage = (IUAASystemManager) ServiceFactory.getFactory().getService(IUAASystemManager.class);
			
			UAAPermissionExampleExtended exampleExtended = new UAAPermissionExampleExtended();
			exampleExtended.createCriteria().andSystemIdEqualTo(systemId).andStatusEqualTo("1");
			
			List<UAAPermission> permList = manage.getUAAPermissionByExampleExtended(exampleExtended, op);
			
			HashSet<String> filter = new HashSet<String>();
			for( UAAPermission perm : permList){
				filter.add(perm.getId());
			}
			
			for( Map.Entry<String, String> entry : all.entrySet()){
				if( filter.contains(entry.getValue())){
					ret.put(entry.getKey(), entry.getValue());
				}
			}
			return ret;
		}
		
		//获取所有权限
		static List<UAAPermission> getAllPermissions(){
			IUAASystemManager manage = (IUAASystemManager) ServiceFactory.getFactory().getService(IUAASystemManager.class);
			UAASystemExampleExtended  usee = new UAASystemExampleExtended(); 
			if(AccessSandboxFilter.systemSign!=null){
				usee.createCriteria().andSystemUriEqualTo(AccessSandboxFilter.systemSign);
			}
			usee.setSelectedField(UAASystem.fieldId());
			
			UAAPermissionExampleExtended exampleExtended = new UAAPermissionExampleExtended();
			exampleExtended.createCriteria().andStatusEqualTo("1").andSystemIdIn(Arrays.asList(usee));
			return manage.getUAAPermissionByExampleExtended(exampleExtended, new Operator());
		}
		
		//根据角色和系统Id获取用户权限
		static List<UAAPermission> getPermsByRoleIdAndSystemId( String roleId, String systemId,Operator op){
			IUAASystemManager businessManager = (IUAASystemManager) ServiceFactory.getFactory().getService(IUAASystemManager.class);
			
			UAA_Relation_OrgRole_PermGroupExampleExtended uropee = new UAA_Relation_OrgRole_PermGroupExampleExtended();
			uropee.createCriteria().andRoleIdEqualTo(roleId);
			uropee.setSelectedField(UAA_Relation_OrgRole_PermGroup.fieldGroupId());
			
			UAA_Relation_PermGroup_PermExampleExtended urppee = new UAA_Relation_PermGroup_PermExampleExtended();
			urppee.createCriteria().andGroupIdIn(Arrays.asList(uropee));
			urppee.setSelectedField(UAA_Relation_PermGroup_Perm.fieldPermId());
			
			UAAPermissionExampleExtended exampleExtended = new UAAPermissionExampleExtended();
			exampleExtended.createCriteria().andIdIn(Arrays.asList(urppee)).andSystemIdEqualTo(systemId).andStatusEqualTo("1");
			return businessManager.getUAAPermissionByExampleExtended(exampleExtended, op);
			
		}
		
		//提取过滤url
		static String extractFromUrl(String start, String url){
			logger.debug("extractFromUrl,  url:" + url + " , start:" + start );
			
			if( url == null) return null;
			
			int leftindx = (start == null || url.indexOf(start) ==-1)? 0 : url.indexOf(start)+start.length();
			
			String  ret = url.substring(leftindx);
			if( ret == null || "".equals(ret) ) return null;
			if( ret.indexOf(".action") > -1 ){
/*				ret = ret.substring(0, ret.indexOf(".action")+7);
				int left = ret.lastIndexOf("/");
				if( left > -1){
					ret = ret.substring(left+1);
				}*/
				ret = ret.substring((ret.charAt(0)=='/')?1:0, ret.indexOf(".action")+7);
				String ret1 = ret;
				while(!allAccess.containsKey(ret1)){
					int next = ret1.indexOf("/");
					if( next == -1){
						ret1 = null;
						break;
					}
					ret1 = ret1.substring(next+1);
				}
				if( ret1 != null){
					ret = ret1;
				}
				
				
			}else if( ret.indexOf( ".jsp") > -1){
				ret = ret.substring((ret.charAt(0)=='/')?1:0, ret.indexOf(".jsp")+4);
			}else{
				return null ; //not make sense
			}
			logger.debug("extractFromUrl,  url:" + url + " , start:" + start + " , return:" + ret);
			return ret;
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent paramHttpSessionEvent) {
		logger.debug("Session created.");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent paramHttpSessionEvent) {
		Sandbox.userAccess.remove(paramHttpSessionEvent.getSession().getId());
		logger.debug("Session destroyed, remove user's perm data.");
	}
}