package com.ctfo.upp.soa;

import static java.util.ServiceLoader.load;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ctfo.upp.soa.support.ServiceCreatorAdaptor;


/**
 * 
 * 
 * 
 * <p>
 * -----------------------------------------------------------------------------
 * <br>
 * 工程名 ： CSM-SOA <br>
 * 功能：代理服务工厂类 <br>
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
public class ProxyServiceFactory extends ServiceFactory {
	
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private IServiceCreator creator = null;

	ProxyServiceFactory() {
		super();

		Iterator<IServiceCreator> creators = load(IServiceCreator.class)
				.iterator();
		while (creators.hasNext()) {
			// try getting the current service and return
			try {
				creator = creators.next();
				break;
			} catch (Throwable t) {
				// just ignore, skip and try getting the next
			}
		}
		
		if( creator == null){
			creator = new ServiceCreatorAdaptor();
		}

	}

	@SuppressWarnings("rawtypes")
	public Object getService(Class interf, String version) {

		if (this.creator == null) {
			logger.debug("this.creator is null when create "+interf+" :return null!");
			return null;
		}

		return this.creator.newService(interf, version);
	}
	

}
