package com.ctfo.upp.soa;

/**
 * 
 * 
 * 
 * <p>
 * ----------------------------------------------------------------------------- <br>
 * 工程名 ： CSM-SOA <br>
 * 功能：接口服务工厂类，创建服务接口 <br>
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
public interface IServiceFactory {

	/**
	 * 根据Class类获取接口对象
	 *  interf 接口类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract Object getService(Class interf);

	/**
	 * 根据Class类和版本号获取接口对象
	 * @param interf 接口类
	 * @param version 版本号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract Object getService(Class interf, String version);
	
	public abstract Object getRemoteService(Class interf);
	


}