package com.ctfo.upp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ctfo.csm.uaa.dao.beans.UAAOrg;
import com.ctfo.csm.uaa.dao.beans.UAAPermission;
import com.ctfo.csm.uaa.dao.beans.UAAUser;
import com.ctfo.csm.uaa.intf.support.AAException;
import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.csm.uaa.meta.beans.UAARoleMeta;
import com.ctfo.upp.service.login.ILoginService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.view.beans.Index;

@Component
@Controller
@Scope("prototype")
@RequestMapping("/login")
public class LoginControler extends BaseController {
	private static Log logger = LogFactory.getLog(LoginControler.class);
	@Resource(name = "loginService", description = "service登录接口")
	private ILoginService loginService;

	@RequestMapping("/login")
	public String login(String systemSgin) {
		String userLogin = request.getRemoteUser();
		Operator op = new Operator();
		logger.debug("login system----->>>" + systemSgin);
		Index index = new Index();
		// 用户
		try {
			UAAUser user = loginService.queryUserByUserLogin(userLogin);
			if (user == null) {
				throw new AAException("获取用户信息为空");
			}
			op.setUserId(user.getId());
			op.setSystemsign(systemSgin);
			index.setUserId(user.getId());
			index.setUserLogin(userLogin);
			index.setUserName(user.getUserName());
			index.setUserType(user.getUserType());
			index.setUserOwningOrgId(user.getOwningOrgid());
			index.setUserPasswd(user.getMd5passwd());
		} catch (Exception e) {
			logger.error("获取用户信息异常!", e);
			return forwardError("获取用户信息失败，请稍后再试或联系系统管理员");
		}

		List<UAARoleMeta> roleList = null;
		// 角色
		try {
			roleList = queryRoles(systemSgin, index.getUserId());
			// 默认取第一个角色
			if (roleList == null || roleList.size() == 0) {
				return forwardError("获取用户角色信息为空,用户【"+userLogin+"】没有访问统一支付平台系统权限");
			}
			op.setRoleId(roleList.get(0).getUaaOrgRole().getId());
			index.setRoleId(roleList.get(0).getUaaOrgRole().getId());
			index.setRoleName(roleList.get(0).getUaaOrgRole().getName());

		} catch (Exception e) {
			logger.error("获取用户角色异常!", e);
			return forwardError("获取角色信息失败，请稍后再试或联系系统管理员");
		}

		// 组织
		try {
			UAAOrg org = loginService.queryOrgById(roleList.get(0).getUaaOrgRole().getOrgid());
			if (org == null) {
				throw new AAException("获取用户组织信息为空");
			}
			index.setOrgId(org.getId());
			index.setOrgName(org.getOrgShortName());
		} catch (Exception e) {
			logger.error("获取组织信息异常!", e);
			return forwardError("获取组织信息失败，请稍后再试或联系系统管理员");
		}

		// 系统
		try {
			com.ctfo.upp.view.beans.System system = loginService.querySystemBySystemSign(systemSgin);
			if (system == null) {
				throw new AAException("获取系统信息为空");
			}
			index.setSystemId(system.getId());
			index.setSystemName(system.getName());
			index.setSystemSign(system.getSystemUri());
			index.setPlatform(system.getPlatform());
			index.setPlatformName(system.getPlatformName());
		} catch (Exception e) {
			logger.error("获取系统信息异常!", e);
			return forwardError("获取系统信息失败，请稍后再试或联系系统管理员");
		}
		request.getSession().setAttribute(Converter.SESSION_INDEX, index);
		request.getSession().setAttribute(Converter.SESSION_REMOTE_USER, userLogin);
		return this.redirectJsp("/pages/index");
	}
	//修改当前登录用户密码
	@RequestMapping(value = "/modifyPassWord", produces = "application/json")
	@ResponseBody
	public String modifyPassWord(String userId,String oldPasswd,String newPasswd) {
		String result = "";
		System.out.println("修改支付密码!");
		try {
			result = loginService.modifyPassWord(userId, oldPasswd, newPasswd);
		} catch (Exception e) {
			logger.error("修改密码异常",e);
			result = "-1";
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMenuList", produces = "application/json")
	@ResponseBody
	public List<UAAPermission> queryMenuList() {
		try {
			List<UAAPermission> menuList = session.getAttribute(Converter.SESSION_MENU_LIST) == null ? this.queryPermission("menu") : (List<UAAPermission>) session.getAttribute(Converter.SESSION_MENU_LIST);
			return menuList;
		} catch (Exception e) {
			logger.error("查询用户菜单异常!", e);
		}
		return null;
	}

	/***
	 * 获取统一认证权限
	 * 
	 * @param systemId
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMyPageFunction", produces = "application/json")
	@ResponseBody
	public List<UAAPermission> queryFunList(String systemId, String roleId) {
		List<UAAPermission> funList = null;
		try {
			if (StringUtils.isBlank(systemId)) {
				funList = session.getAttribute(Converter.SESSION_FUNC_LIST) == null ? this.queryPermission("func") : (List<UAAPermission>) session.getAttribute(Converter.SESSION_FUNC_LIST);
			} else {
				funList = this.queryPermission("func", systemId, roleId);
			}

		} catch (Exception e) {
			logger.error("查询用户权限异常!", e);
		}
		return funList;
	}

	/***
	 * 获取权限
	 * 
	 * @param type
	 * @param systemId
	 * @param roleId
	 * @return
	 */
	private List<UAAPermission> queryPermission(String type, String systemId, String roleId) {
		try {
			List<UAAPermission> menuList = new ArrayList<UAAPermission>();
			List<UAAPermission> funList = new ArrayList<UAAPermission>();

			String userLogin = request.getRemoteUser();
			if (!loginService.hasUserLoginRole(userLogin, roleId, systemId)) {
				return null;
			}
			menuList = new ArrayList<UAAPermission>();
			funList = new ArrayList<UAAPermission>();
			List<UAAPermission> list = loginService.queryPermissionList(roleId, systemId);
			for (UAAPermission per : list) {
				if ("menu".equals(per.getType()))
					menuList.add(per);
				else
					funList.add(per);
			}
			if (menuList.size() > 0)
				session.setAttribute(Converter.SESSION_MENU_LIST, menuList);
			if (funList.size() > 0)
				session.setAttribute(Converter.SESSION_FUNC_LIST, funList);
			return "menu".equals(type) ? menuList : funList;

		} catch (Exception e) {
			logger.error("查询权限异常!", e);
		}

		return null;
	}

	private List<UAAPermission> queryPermission(String type) {

		try {

			List<UAAPermission> menuList = new ArrayList<UAAPermission>();
			List<UAAPermission> funList = new ArrayList<UAAPermission>();

			Index index = (Index) session.getAttribute(Converter.SESSION_INDEX);

			List<UAAPermission> list = loginService.queryPermissionList(index.getRoleId(), index.getSystemId());
			for (UAAPermission per : list) {
				if ("menu".equals(per.getType()))
					menuList.add(per);
				else
					funList.add(per);
			}

			if (menuList.size() > 0)
				session.setAttribute(Converter.SESSION_MENU_LIST, menuList);
			if (funList.size() > 0)
				session.setAttribute(Converter.SESSION_FUNC_LIST, funList);

			return "menu".equals(type) ? menuList : funList;

		} catch (Exception e) {
			logger.error("查询权限异常!", e);
		}

		return null;
	}

	private List<UAARoleMeta> queryRoles(String systemSign, String userId) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		List<UAARoleMeta> roleList = loginService.queryOrgRoleList(systemSign, userId);
		if (roleList != null) {
			session.setAttribute(Converter.SESSION_ROLE_LIST, roleList);
		}
		return roleList;
	}

}
