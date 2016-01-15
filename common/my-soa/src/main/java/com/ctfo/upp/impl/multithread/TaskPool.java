package com.ctfo.upp.impl.multithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TaskPool {
	
	public static final String DefaultPool = "__default_taskpool__";
	
	private static Map<String, TaskPool> namedPools = Collections.synchronizedMap(new HashMap<String,TaskPool>());
	public static TaskPool named(String poolName){
		if( namedPools.containsKey(poolName)) return namedPools.get(poolName);
		synchronized(namedPools){
			namedPools.put(poolName, new TaskPool(poolName));
		}
		return namedPools.get(poolName);
	}
	
	
	private String name =null;
	private TaskPool(String name){
		this.name = name;
	}
	
	
	
	
	private  ExecutorService _pool = null;
	private  LinkedHashMap<String, ExecutorService> _poollist = new LinkedHashMap<String, ExecutorService>();

	private    ExecutorService getPool(){
		
		synchronized(this){
			if( _pool == null){
				setPoolSize(50);
				return _pool;
			}else{
				//select a pool according current thread
				Thread current = Thread.currentThread();
				String id = current.toString();
				int ind = id.indexOf("-");
				String poolname = (ind>0)? id.substring(0,ind) : ""	;
				if( "".equals(poolname) || !_poollist.containsKey(poolname)){ //thread not in pool
					return _pool;
				}else{
					int i =0; //find next pool index
					boolean found = false;
					ExecutorService target = null;
					for( String es : _poollist.keySet()){
						if( found){
							target = _poollist.get(es);
							break;
						}else{
							found = es.equals(poolname);
							i++;
						}
					}
					if( target == null){
						final int poolindex = i;
						ThreadFactory factory = new ThreadFactory(){

							@Override
							public Thread newThread(Runnable paramRunnable) {
								// TODO Auto-generated method stub
								
								return new Thread(paramRunnable ,TaskPool.this.name + poolindex + "-"){
									public String toString(){
										return TaskPool.this.name + poolindex + "-" + super.toString();
									}
								};
							}
							
						};
						target = new ThreadPoolExecutor(0,Math.max(0, ((ThreadPoolExecutor)_pool).getMaximumPoolSize()), 5000L,TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),factory, new ThreadPoolExecutor.AbortPolicy()); 
						_poollist.put(TaskPool.this.name + poolindex, target);
					}
					return target;
					
				}
			}
		}

		
	}
	
	 public  TaskPool setPoolSize(int poolSize){
		if( poolSize <1 ) return this;
		synchronized(this){
			if( _pool == null){
				int workers = poolSize;
				ThreadFactory factory = new ThreadFactory(){

					@Override
					public Thread newThread(Runnable paramRunnable) {
						// TODO Auto-generated method stub
						
						return new Thread(paramRunnable, TaskPool.this.name + "0-"){
							public String toString(){
								return TaskPool.this.name + "0-" + super.toString();
							}
						};
					}
					
				};
				_pool = new ThreadPoolExecutor(Math.max(1, poolSize),Math.max(4, poolSize), 5000L,TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),factory, new ThreadPoolExecutor.AbortPolicy()); 
				_poollist.put(TaskPool.this.name + "0", _pool);
			
				System.out.println("TaskPool max: " + workers + "   started.");
			}
		}
		return this;
		
	}
	 
	 public TaskPool complete(final  List<Runnable> tasks){

			ArrayList<Future<?>> calls = new ArrayList<Future<?>>();
			
			for( Runnable r : tasks){
				calls.add( this.getPool().submit(r) );
			}
			
			try {
				long last = System.currentTimeMillis();
				for( Future<?> f : calls){
					//System.out.println("F.GET: " + (System.currentTimeMillis()-last));
					//last = System.currentTimeMillis();
					f.get();
				}

			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("TaskPool completeTask Error",e);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("TaskPool completeTask Error",e);
			}
			
			return this;
		
	 }
	 public TaskPool submit( Runnable task){
		 this.getPool().submit(task);
		 return this;
	 }
	 
	 
	 
	 /****** DEFAULT POOL API ********/
	 
	 
	static public synchronized void setPool(int poolSize){
		named(DefaultPool).setPoolSize(poolSize);
	}
	static public void submitTask( Runnable task){
		named(DefaultPool).getPool().submit(task);
	}
	/*
	 * 
	 */
	static public void completeTask(final  List<Runnable> tasks){
		
		named(DefaultPool).complete(tasks);

	}
	
	 /****** DEFAULT POOL API ********/
	
	
	
	
	
	static private void completeTask2( List<Runnable> tasks) {
		ArrayList<Callable<Object>> calls = new ArrayList<Callable<Object>>();
		
		for( Runnable r : tasks){
			calls.add( Executors.callable(r) );
		}
		
		try {
			named(DefaultPool).getPool().invokeAll(calls);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("TaskPool completeTask2 Error",e);
		}
	}
	
	
	static public void main(String[] args){
		TaskPool.named(DefaultPool).getPool();
		
		
		List<Runnable> rs = new ArrayList<Runnable>();
		for( int i=0; i<1000; i++){
			final int fi = i;
			rs.add( new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("Task: " + fi + " is completed.");
				}
				
			});
		}
		
		new Thread( new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.currentThread().sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TaskPool.named(DefaultPool).setPool(10);
			}
			
		}).start();
		
		long t1 = System.currentTimeMillis();
		TaskPool.completeTask(rs);
		
		long t2 = System.currentTimeMillis() - t1;
		System.out.println("time elapsed : " + t2 +" ms");
		
		TaskPool.named(DefaultPool).getPool().shutdown();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
