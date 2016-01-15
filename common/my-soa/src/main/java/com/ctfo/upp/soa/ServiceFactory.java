package com.ctfo.upp.soa;

import static java.util.ServiceLoader.load;

import java.util.Iterator;

import com.ctfo.upp.soa.support.ServiceCreatorAdaptor;

/**
 * 
 * 
 * 
 * <p>
 * ----------------------------------------------------------------------------- <br>
 * 工程名 ： CSM-SOA <br>
 * 功能：服务工厂类 <br>
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
public class ServiceFactory implements IServiceFactory {
		static public final String NEWEST_VERSION = "0.0" ;
		static private IServiceFactory _self = null;
		static public IServiceFactory getFactory(){
			if( _self == null ){
				synchronized(ServiceFactory.class){
					if(_self == null)
					_self = new ProxyServiceFactory();
				}
			}
			return _self;
		}
		
		protected ServiceFactory(){}
		

		/* (non-Javadoc)
		 * @see com.ctfo.csm.soa.aa.IServiceFactory#getService(java.lang.Class)
		 */
		@SuppressWarnings("rawtypes")
		public Object getService(Class interf){
			return this.getService(interf ,NEWEST_VERSION);
		}
		
		
		/* (non-Javadoc)
		 * @see com.ctfo.csm.soa.aa.IServiceFactory#getService(java.lang.Class, java.lang.String)
		 */
		@SuppressWarnings("rawtypes")
		public Object getService(Class interf, String version){
			
			IServiceCreator creator = this.loadCreator();
	        
	        if( creator == null) return null;
	        
	        return creator.newService(interf, version);

	        
		}
		
		private IServiceCreator loadCreator(){
			Iterator<IServiceCreator> creators = load( IServiceCreator.class ).iterator();
			IServiceCreator creator = null;
	        // iterate over all found services
	        while ( creators.hasNext() )
	        {
	            // try getting the current service and return
	            try
	            {
	                creator = creators.next();
	                break;
	            }
	            catch ( Throwable t )
	            {
	                // just ignore, skip and try getting the next
	            }
	        }
	        
	         return creator;
		}

		@Override
		public Object getRemoteService(Class interf) {
			// TODO Auto-generated method stub
			IServiceCreator creator = this.loadCreator();
	        
	        if( creator == null) return null;
	        
	        return creator.newService(interf, NEWEST_VERSION, "R");
		}


}
