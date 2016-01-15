package com.ctfo.upp.soa;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * 
 * <p>
 * -----------------------------------------------------------------------------
 * <br>
 * 工程名 ： CSM-SOA <br>
 * 功能：获取SOA远程访问的配置文件 <br>
 * 描述： <br>
 * 授权 : (C) Copyright (c) 2011 <br>
 * 公司 : 北京中交兴路信息科技有限公司 <br>
 * -----------------------------------------------------------------------------
 * <br>
 * 修改历史 <br>
 * <table width="432" border="1">
 * <tr>
 * <td>版本</td>
 * <td>时间</td>
 * <td>作者</td>
 * <td>改变</td>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2012-6-14</td>
 * <td>wangjd</td>
 * <td>创建</td>
 * </tr>
 * </table>
 * <br>
 * <font color="#FF0000">注意: 本内容仅限于[北京中交兴路信息科技有限公司]内部使用，禁止转发</font> <br>
 * 
 * @version 1.0
 * 
 * @author wangjd
 * @since JDK1.6
 */
public class SOAProperties extends HashMap<String, Object> {

	private static String serviceLocations = "serviceLocations";

	public Map getServiceLocations() {
		return (Map) this.get(serviceLocations);
	}

	public void setServiceLocations(Map locations) {
		this.put(serviceLocations, locations);
	}
	
	private Long globalTimeout = 0L;

	public Long getGlobalTimeout() {
		return globalTimeout;
	}

	public void setGlobalTimeout(Long globalTimeout) {
		this.globalTimeout = globalTimeout;
	}
	
}
