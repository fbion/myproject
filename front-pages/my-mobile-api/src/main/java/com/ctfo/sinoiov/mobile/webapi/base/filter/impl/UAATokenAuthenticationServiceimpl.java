package com.ctfo.sinoiov.mobile.webapi.base.filter.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.notification.util.PropertyUtils;
import com.ctfo.sinoiov.mobile.webapi.base.filter.IUAATokenAuthenticationService;
import com.ctfo.sinoiov.mobile.webapi.base.filter.UaaTokenRsult;
import com.ctfo.sinoiov.mobile.webapi.util.HttpUtil;

/**
 * 统一Token
 */
@Service("tokenAuthenticationService")
public class UAATokenAuthenticationServiceimpl implements IUAATokenAuthenticationService {
	private static Log logger = LogFactory.getLog(UAATokenAuthenticationServiceimpl.class);

	private String uaaURL;

	public String validateTokenId(String tokenId) {
		uaaURL = PropertyUtils.getValueByKey("uaa.provingTokenIdURL");
		String res = HttpUtil.sendHttpPostResquest(uaaURL, "tokenId=" + tokenId);
		return parseUserName(res);
	}

	private String parseUserName(String res) {
		if (StringUtils.isBlank(res)) {
			return null;
		}
		// String userName = "chpt_3001002080";//TODO 登陆人信息, 写死,待统一TOKEN 解析解决后变更
		String userName = null;// TODO 登陆人信息, 写死,待统一TOKEN 解析解决后变更
		try {
			UaaTokenRsult uaaTokenRsult = (UaaTokenRsult) JSONObject.toBean(JSONObject.fromObject(res),
					UaaTokenRsult.class);
			if (uaaTokenRsult != null) {
				userName = uaaTokenRsult.getUserName();
			}
		} catch (Exception e) {
			logger.warn("解析统一认证返回tokenId发生异常, 统一认证返回结果为:" + res, e);
		}
		return userName;
	}

}
