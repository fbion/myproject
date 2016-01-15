package com.ctfo.upp.soa;

/**
 * 
 * 
 * 
 * <p>
 * ----------------------------------------------------------------------------- <br>
 * 工程名 ： CSM-SOA <br>
 * 功能:服务创建接口 <br>
 * 描述： <br>
 * 授权 : (C) Copyright (c) 2011 <br>
 * 公司 : 北京中交兴路信息科技有限公司 <br>
 * ----------------------------------------------------------------------------- <br>
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
 * <td>wangpeng</td>
 * <td>创建</td>
 * </tr>
 * </table>
 * <br>
 * <font color="#FF0000">注意: 本内容仅限于[北京中交兴路信息科技有限公司]内部使用，禁止转发</font> <br>
 * 
 * @version 1.0
 * 
 * @author wangpeng
 * @since JDK1.6
 */
public interface IServiceCreator {
	/**
	 * 新建一个服务对象根据Class 与 版本号
	 * @param interf
	 * @param version
	 * @return
	 */

	public Object newService(Class interf, String version);
	
	/**
	 * 
	 * @param interf
	 * @param version
	 * @param policy  "SLR"  S: spring bean;  L: local jdk service;  R: remote hessian service;  null : "SLR";
	 * @return
	 */
	public Object newService(Class interf, String version, String policy);
}
