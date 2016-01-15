package com.ctfo.upp.soa.support;

import static java.util.ServiceLoader.load;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.ctfo.upp.soa.IServiceCreator;
import com.ctfo.upp.soa.SOAProperties;
import com.ctfo.upp.utils.SpringBUtils;

public class ServiceCreatorAdaptor implements IServiceCreator {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	/**
	 * 负责实现远程服务的本地代理
	 */
	public Object newService(Class interf, String version) {
		return this.newService(interf, version, null);
	}

	public Object newService(Class interf, String version, String policy) {
		
		
		Object svr = null;
		
		if(policy == null || "".equals(policy)) policy =" ";
		
		for(int i =0; i< policy.length(); i++){
			
			// 取sping中的本地服务实现bean
			if(' '== policy.charAt(i) || 'L' ==(policy.charAt(i))){
				try {
					svr = SpringBUtils.getBean(interf.getName());
					if (svr != null)
						return svr;
				} catch (Exception e) {
					//logger.debug("spring中没有设置这个本地服务："+interf.getName());
				}
			}


			// 取JDK本地服务实现
			if(' '== policy.charAt(i) || 'J' ==(policy.charAt(i))){
				svr = this.getLocalService(interf, version);
				if (svr != null)
					return svr;
			}

			
			
			// 取远程hessian服务
			if(' '== policy.charAt(i) || 'R' ==(policy.charAt(i))){
				try{
					svr = this.getRemoteService(interf, version);
					if (svr != null)
						return svr;
				}catch (Exception e) {e.printStackTrace();}
			}

			
		}

		


		
		
		return  svr;
	}
	private Map cachedServicesLocal = Collections.synchronizedMap(new HashMap());
	
	protected Object getLocalService(Class interf, String version){
		
		if( cachedServicesLocal.containsKey(interf.getName()+version)){
			return cachedServicesLocal.get(interf.getName()+version);
		}
		
		Object svr = null;
		Iterator<? extends Class> creators =  load(interf.getClass(), this.getClass().getClassLoader()).iterator();
        while ( creators.hasNext() )
        {
            // try getting the current service and return
            try
            {
                 svr = creators.next();
                 cachedServicesLocal.put(interf.getName()+version, svr);
                break;
            }
            catch ( Throwable t )
            {
                // just ignore, skip and try getting the next
            }
        }
        
        return svr;
	}
	
	private Set hibernatedSrvs = Collections.synchronizedSet(new HashSet());
	private Map cachedServicesRemote = Collections.synchronizedMap(new HashMap());
	protected Object getRemoteService(Class interf, String version){
		if( cachedServicesRemote.containsKey(interf.getName()+version)){
			return selectOne((List)cachedServicesRemote.get(interf.getName()+version) );
		}
		
		SOAProperties props = (SOAProperties) SpringBUtils.getBean("soaProperties") ;
		List<String> locations = whichLocations(props, interf);
		
		HessianProxyFactory factory = new HessianProxyFactory(); 
		//factory.setHessian2Reply(false);
		//factory.setHessian2Request(false);
		factory.setOverloadEnabled(true);
		
		//factory.setDebug(true);
		if( 0L != props.getGlobalTimeout() ){
			factory.setReadTimeout(props.getGlobalTimeout());
		}
		//factory.setReadTimeout(3000L);
		
		if( locations == null || locations.isEmpty()) return null;
		List svrs = new ArrayList();
		for( String loc : locations){
			if( loc == null || "".equals(loc.trim())) continue;
			
	        String url = loc + "/"+interf.getName();  
	         
	        Object svr = null;
	        try {  
	            svr =  factory.create(  interf, url, interf.getClassLoader());  
	            svrs.add( new MySvr(url, interf, svr, this.hibernatedSrvs, svrs)	);
	            
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        } 
		}
		
		if( svrs == null || svrs.isEmpty()) return null;
		cachedServicesRemote.put(interf.getName()+version, svrs);
		return selectOne(svrs);
	}
	
	
	private Map _slc_policy = null;
	private Map getServiceLocationChoosingPolicy(SOAProperties props){
		if( this._slc_policy != null ) return this._slc_policy;
		
		Map choosing = new LinkedHashMap();
		for( Object key : props.getServiceLocations().keySet()){
			if( !((String)key).startsWith("choosing-policies")) continue;
			Map mm = props.getServiceLocations();
			Object oo = mm.get(key);
			
			List<String> policies =  (List<String>) oo;
			for( Object policy : policies){
				StringTokenizer t = new StringTokenizer((String)policy,":"); 
				if( t.countTokens()>1){
					choosing.put(t.nextToken(), t.nextToken());
				}
			}

		}
		this._slc_policy = choosing;
		return choosing;
	}
	/** 
	 * set your service location choosing policy
	 * @param props
	 * @param interf
	 * @return
	 */
	protected List<String> whichLocations(SOAProperties props, Class interf){
		if( interf != null){
			String classn = interf.getName();
			String locationsName = "";
			Map policy = this.getServiceLocationChoosingPolicy(props);
			Iterator ite = policy.keySet().iterator();
			while( ite.hasNext()){
				Object key = ite.next();
				if( classn.startsWith(key.toString())){
					locationsName = (String)policy.get(key);
					
					if( !"".equals(locationsName)){
						Object ret =  props.getServiceLocations().get(locationsName);
						if( ret != null && !((List)ret).isEmpty() )
							return (List<String>) ret;
					}
					
				}
			}


			
		}
		
		//default 
		return (List<String>)  null; //props.getServiceLocations().get("aa-service-locations");
	}
	
	private Random rand = new Random();
	/**
	 * set your location road routing policy
	 * @param locations
	 * @return
	 */
	protected synchronized Object  selectOne(List locations){
		if( locations.size() == 0) throw new OutOfServiceException("没有可用的服务");

		return selectOne(locations,5);
	}
	
	
	protected Object selectOne(List locations, int limit){
		if( locations.size() == 0) throw new OutOfServiceException("没有可用的服务");
		if( limit==0) throw new OutOfServiceException("没有可用的服务");
		
		Object ret = locations.get(rand.nextInt(locations.size()));
		
		if( ret instanceof MySvr){
			if( !((MySvr)ret).isHibernated() ){
				return ((MySvr)ret).getSrv();
			}else{
				List locs = new ArrayList(); locs.addAll(locations);
				locs.remove(ret);
				return selectOne(locs, --limit);
			}
		}else{
			return ret;
		}

	}
	
	static private final ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
	
	  class MySvr implements Runnable{
		
		public MySvr(String id, Class interf, Object srv, Set hibernated, List svrs ){
			this.id = id; this.svr = srv;
			this.hibernated = hibernated;
			this._svrs = svrs;
			final MySvr _mySvr = this;
			this.monitorSvr = Proxy.newProxyInstance(interf.getClassLoader(), new Class[]{interf}, new InvocationHandler(){

				
				public Object invoke(Object paramObject, Method paramMethod,
						Object[] paramArrayOfObject) throws Throwable {
					boolean needHibernated = _mySvr.hibernated.contains(this);
					
					if( needHibernated){
						logger.error("暂时不可用的服务"+_mySvr.id+ ", 持续休眠...,检查网络设置:"+_mySvr.svr	);
						Object svr = ServiceCreatorAdaptor.this.selectOne(_svrs);
						return paramMethod.invoke(svr, paramArrayOfObject);
					}
					
					Throwable targetT = null;
					try{
						return paramMethod.invoke(_mySvr.svr, paramArrayOfObject);
						
					}catch(InvocationTargetException ite){
						Throwable t = ite.getCause();
						if( t == null){
							t = ite.getTargetException();
						}
						if( t!=null &&
							(
								HessianConnectionException.class.getName().equals(t.getClass().getName())
								|| 
								( HessianRuntimeException.class.getName().equals(t.getClass().getName())  &&
								  isConnectionCaused(t) ) 
							)
						){
							targetT = t;
							needHibernated = true;
						}else if( t != null	){
							throw t;
						}

						
					}catch(HessianConnectionException he){
						targetT = he;
						needHibernated = true;
					}catch(UndeclaredThrowableException ue	){
						if( ue.getUndeclaredThrowable() != null){
							throw ue.getUndeclaredThrowable();
						}
						throw ue;
					}
					
					if( needHibernated){
						logger.error("发现暂时不可用的服务"+_mySvr.id+ ", 休眠15s,检查网络设置:"+_mySvr.svr, targetT	);
						_mySvr.hibernated.add(_mySvr);
						exe.schedule(_mySvr, 15L, TimeUnit.SECONDS);
						
						Object svr = ServiceCreatorAdaptor.this.selectOne(_svrs);
						return paramMethod.invoke(svr, paramArrayOfObject);
					}
					
					return null;
					
				}

				
			});
		}
		
		boolean isConnectionCaused(Throwable t){
			if( t == null ) return false;
			
			if( t instanceof SocketException) return true;
			
			return isConnectionCaused(t.getCause());
			
		}
		
		public void run() {
			// TODO Auto-generated method stub
			this.hibernated.remove(this);
		}
		private Set hibernated = null;
		private List _svrs = null;
		private String id = null;
		private Object svr = null;
		private Object monitorSvr = null;
		public Object getSrv(){
			return (monitorSvr != null)?monitorSvr:svr;
		}
		public String getId(){
			return this.id;
		}
		public boolean isHibernated(){
			return this.hibernated.contains(this);
		}
		
		public boolean equals( Object o){
			if( o== null) return false;
			if( !( o instanceof MySvr)) return false;
			
			return this.id.equals(((MySvr)o).getId() );
			
		}
		
		public int hashCode(){
			return this.id.hashCode();
		}
		
		
	}
	
	
	static class OutOfServiceException extends RuntimeException{

		public OutOfServiceException(String string) {
			// TODO Auto-generated constructor stub
		}
		
	}

}
