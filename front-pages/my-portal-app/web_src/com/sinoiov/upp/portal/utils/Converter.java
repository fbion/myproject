package com.sinoiov.upp.portal.utils;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.ctfo.base.dao.beans.SimpleCodeExampleExtended;
import com.ctfo.csm.local.DynamicSqlParameter;
import com.ctfo.csm.local.Rule;
import com.ctfo.csm.uaa.intf.support.Operator;
import com.sinoiov.upp.portal.viex.beans.Index;



@SuppressWarnings(value = { "all" })
public class Converter {
	
	private static Log logger = LogFactory.getLog(Converter.class);
	
	//public static Map<String, Object> RESULT_MAP=new HashMap<String, Object>();//调试数据
	
	public static final String OP_SUCCESS="操作成功";
	public static final String OP_FAILED="操作失败";
	public static final String SYS_SIGN="system.code";
	
	public static final String SESSION_INDEX="session-index";
	public static final String SESSION_REMOTE_USER="session-remote-user";
	
	public static final String SESSION_MENU_LIST="session-menuList";
	public static final String SESSION_FUNC_LIST="session-funList";
	public static final String CONTEXT_FULL_PERMISSION="context-fullList";
	
	//存储session，为切换角色准备数据
	public static final String SESSION_ROLE_LIST="session-roleList";
	
	
	@SuppressWarnings("finally")
	public static final Operator getOperator(HttpServletRequest request){
		
		Operator result = null;
		try {
			if( request == null) throw new IllegalArgumentException("the request is null!");
			
			String principal = request.getRemoteUser();
			if( principal == null){
				HttpSession ses = request.getSession(false);
				if( ses != null){
					principal = (String) ses.getAttribute(SESSION_REMOTE_USER);
				}
			} 
			if( principal == null) throw new IllegalStateException("用户还没有成功登录!");
			
			if(request.getSession().getAttribute(SESSION_REMOTE_USER) == null ){
				request.getSession().setAttribute(SESSION_REMOTE_USER, principal);
			}
			result = parseOperator(request,principal);
		} catch (Exception e) {	
			logger.warn("获取登录用户信息异常",e);			
		}
		finally{
			if( result == null) 
				return new Operator();
			else{
				return result;
			}
				
		}
	}
	private static Operator parseOperator(HttpServletRequest request,String principal)throws Exception{
		Index index = (Index)request.getSession().getAttribute(SESSION_INDEX);
		if(index==null){
			return null;
		}
		Operator oper = new Operator();
		oper.setRoleId(index.getRoleId());
		oper.setSystemsign(index.getSystemSign());
		oper.setUserId(index.getUserId());
		return oper;
	}
	
//	
//	
//	
//	/**
//	 * 从企业树转化为包含省 － 市 － 区县 － 企业 － 企业 的树
//	 * @param orgTree
//	 * @return
//	 */
//	public static final OrgTree toZoneTree( OrgTree  orgTree){
//		
//		 OrgTree result = new OrgTree();
//		Iterator<OrgTree> ite = iterateTree(orgTree);
//		while( ite.hasNext()){
//			OrgTree orgt = ite.next();
//			if( ! (orgt.getNode() instanceof Corporation) ) continue;
//			
//			Corporation corp = (Corporation)orgt.getNode();
//			String prov = str( corp.getCorpProvince() ); 
//			
//			if( !result.getChildTree().containsKey( prov )){
//				result.getChildTree().put(prov,  new OrgTree() );
//			}
//			
//			OrgTree cities = (OrgTree) result.getChildTree().get(prov);
//			String city = str(corp.getCorpCity());
//			if( !cities.getChildTree().containsKey(city	)){
//				cities.getChildTree().put(city, new OrgTree());
//			}
//			
//			OrgTree dists = (OrgTree) cities.getChildTree().get(city);
//			String dist = str( corp.getCorpDistrict()) ; // change it
//			if( !dists.getChildTree().containsKey(dist)){
//				dists.getChildTree().put(dist, new OrgTree());
//			}
//			
//			OrgTree distTree = (OrgTree) dists.getChildTree().get(dist);
//			OrgTree leaf = (orgt.getParentNode()==null)? orgt : null;
//			
//			if( leaf != null)
//				distTree.getChildTree().put(corp.getId(), leaf);
//			
//		}
//		return result;
//	}

	public static String str(String ins){
		if( ins == null || "".equals(ins.trim())) return "";
		return ins;
	}
	
//	public static Iterator<OrgTree> iterateTree(final OrgTree tree){
//		return new Iterator<OrgTree>(){
//			private ArrayList<OrgTree> listTree = null;
//			private synchronized ArrayList<OrgTree> getList(){
//				if( listTree !=null) return listTree;
//				
//				this.listTree = new ArrayList<OrgTree>();
//				this.makeList(this.listTree, tree);
//				
//				return this.listTree;
//				
//			}
//			private Iterator<OrgTree> iteTree = null;
//			private synchronized Iterator<OrgTree> getIte(){
//				if( iteTree != null) return iteTree;
//				
//				iteTree = this.getList().iterator();
//				return iteTree;
//			}
//			
//			private void makeList( List<OrgTree> list, OrgTree orgtree	){
//				if( orgtree.getNode() != null){
//					list.add(orgtree);
//				}
//				for( Tree<Org> tree : orgtree.getChildTree().values()){
//					makeList(list, (OrgTree) tree);
//				}
//			}
//			@Override
//			public boolean hasNext() {
//				// TODO Auto-generated method stub
//				return this.getIte().hasNext();
//			}
//
//			@Override
//			public OrgTree next() {
//				// TODO Auto-generated method stub
//				return this.getIte().next();
//			}
//
//			@Override
//			public void remove() {
//				// TODO Auto-generated method stub
//				throw new UnsupportedOperationException();
//			}
//			
//		};
//	}
	
	/**
	 * 将model对象转化成Extended对象，用于构造查询条件
	 * @param objExtended
	 * @param model
	 * @return Extended对象
	 * @throws Exception
	 */
	public static final Object copyModelToExtended(Object objExtended, Object model)throws Exception{
		
		Object criteria =   Converter.publicCall(objExtended, "createCriteria", new Class[]{}, new Object[]{});
		if( criteria == null) throw new IllegalArgumentException("newExample is not Ibatis Example parameter.");
		
		Method[] methods = model.getClass().getMethods();
		String methodName ="";
		Object valObj;
		Method andMethod;
		for(Method met : methods){
			if(!"getClass".equals(met.getName()) && "get".equals(met.getName().substring(0, 3))){
				if(StringUtils.hasText((String)met.invoke(model)))continue;
				methodName = met.getName();				
				valObj = met.invoke(model);
				andMethod = Converter.getMethod(criteria.getClass(), "and"+methodName.substring(3,methodName.length())+"EqualTo", false);				
				if( andMethod == null)continue;				
				andMethod.invoke(criteria, new Object[]{valObj});		
			}
		}
		
		return objExtended;
	}

	/**
	 * 转化 DynamicSqlParameter 参数 为 ibatis example 对象，用于构造查询条件
	 * @param dsp 原有查询条件
	 * @param newExample 创建新的查询对象
	 * @return  返回相应查询条件的查询对象
	 * @throws ClassNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static final Object paramToExampleExtendedNoException(DynamicSqlParameter dsp, Object newExample){
		try{
			return paramToExampleExtended( dsp,  newExample);
		}catch(Exception e){ 
			logger.warn("转化 DynamicSqlParameter 参数 为example 对象失败!", e);
		}
		return null;
	}
	public static final Object paramToExampleExtendedCriteriaNoException(DynamicSqlParameter dsp, Object newExample){
		try{
			return paramToExampleExtendedCriteria( dsp,  newExample);
		}catch(Exception e){ 
			logger.warn("转化 DynamicSqlParameter 参数 为example 对象失败!", e);
		}
		return null;
	}
	public static final Object paramToExampleExtended(DynamicSqlParameter dsp, Object newExample) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		paramToExampleExtendedCriteria(dsp,newExample);
		return newExample;
	}
	public static final Object paramToExampleExtendedCriteria(DynamicSqlParameter dsp, Object newExample) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if( dsp == null && newExample == null) return null;
		
		if(dsp.getEqual()!=null && dsp.getEqual().get("modelName")!=null)//删除之前所需的参数
			dsp.getEqual().remove("modelName");
		
		Object criteria =   Converter.publicCall(newExample, "createCriteria", new Class[]{}, new Object[]{});
		if( criteria == null) throw new IllegalArgumentException("newExample is not Ibatis Example parameter.");
		if( dsp == null) return criteria;
		
		String exaName = newExample.getClass().getName();
		String beanName = ( exaName.endsWith("Example"))? exaName.substring(0, exaName.length()-7)   :
			( ( exaName.endsWith("ExampleExtended"))?  exaName.substring(0, exaName.length()-15)  : null ) ;
		if( beanName == null) throw new IllegalArgumentException("newExample :" + exaName + " , cann't found bean  XXXExampleExtended.");
		
		Class beanClass =  Class.forName(beanName) ;
		
		if( beanClass == null) throw new IllegalArgumentException("newExample :" + exaName + " , cann't found bean");
		

		
		Map<String, String> params = null;
		//// handle  EqualTo
		params = dsp.getEqual();
		if( params != null )
			andCriteria(criteria, beanClass, params, "EqualTo");
		
		params = dsp.getNotequal();
		if( params != null )
			andCriteria(criteria, beanClass, params, "NotEqualTo");
		
		params = dsp.getLike();
		if( params != null )
			andCriteria(criteria, beanClass, params, "Like");
		
		Map<String,Object> temp = dsp.getInMap();
		if( temp != null ){
			params = new HashMap<String,String>();
			for( String key: temp.keySet()){
				Object tempv = temp.get(key);
				if( tempv instanceof Object[]){
					tempv = Arrays.asList((Object[])tempv);
				}
				if( tempv instanceof List){
					List invals = (List) tempv;
					for(int i=0; i<invals.size(); i++){
						if( i==0){
							tempv =  ""+ invals.get(i);
						}else{
							tempv = tempv + "," + invals.get(i) ;
						}
					}
				}
				
				params.put(key, tempv.toString());
			}
			andCriteria(criteria, beanClass, params, "In");
		}
		List<Rule> ruleList=dsp.getRules();
		if( ruleList != null ){
			andCriteria(criteria, beanClass, ruleList);
		}
		Map<String,Object> nottemp = dsp.getNotInMap();
		if( nottemp != null ){
			params = new HashMap<String,String>();
			for( String key: nottemp.keySet()){
				Object tempv = nottemp.get(key);
				if( tempv instanceof Object[]){
					tempv = Arrays.asList((Object[])tempv);
				}
				if( tempv instanceof List){
					List invals = (List) tempv;
					for(int i=0; i<invals.size(); i++){
						if( i==0){
							tempv =  ""+ invals.get(i);
						}else{
							tempv = tempv + "," + invals.get(i) ;
						}
					}
				}
				
				params.put(key, tempv.toString());
			}
			andCriteria(criteria, beanClass, params, "NotIn");
		}

		params = dsp.getStartwith();
		if( params != null )
			andCriteria(criteria, beanClass, params, "GreaterThanOrEqualTo");
		
		params = dsp.getEndwith();
		if( params != null ){
			andCriteria(criteria, beanClass, params, "LessThanOrEqualTo");
			//andCriteria(criteria, beanClass, params, "LessThan");
		}
		////handle orderBy
		String orderField = dsp.getOrder();
		String orderSort = dsp.getSort();
		if( orderField != null && !"".equals(orderField)){
			Method m = getMethod(beanClass, "field" + orderField, true);
			
			String field = orderField;
			if( m != null){
				Object ofield = m.invoke(null , new Object[]{}	);
				 field = ofield.toString();
				 Converter.publicCall(newExample, "setOrderByClause", new Class[]{String.class}, new Object[]{ field + " " + ((orderSort!=null)?orderSort : " ") } ) ;
			}
			
			
		}
		
		//handle pagination
		int start = dsp.getStartNum();
		int end = dsp.getEndNum();
		if( end != 0 ){
			Converter.publicCall(newExample, "setSkipNum", new Class[]{int.class}, new Object[]{ Integer.valueOf(start) } ) ;
			Converter.publicCall(newExample, "setEndNum", new Class[]{int.class}, new Object[]{ Integer.valueOf(end+1) } ) ;
		}
		
		
		return criteria;
	}
	
	
	private static void andCriteria(Object criteria, Class beanClass, Map<String, String> params , String operator ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		if( params !=null){
			for( String key : params.keySet() ){
				//ignore the "" string
				String v = params.get(key);
				if( v == null || "".equals(v.trim())) continue;
				
				String mkey = (key.charAt(0)+"").toUpperCase() + key.substring(1);
				Method getM = Converter.getMethod(beanClass, "get"+mkey, false);
				if( getM == null){
					LogFactory.getLog(Converter.class).warn("当拼写查询条件时有无效属性", new IllegalArgumentException("没有找到对应的GET方法："+key)) ;
					continue;
				}
				Class fieldClaz = getM.getReturnType();
				
				Method m = Converter.getMethod(criteria.getClass(), "and"+mkey+operator , false);
				if( m == null){
					continue;
				}
				
				Class[] pts = m.getParameterTypes();
				Object[] vals = new Object[pts.length];
				
				
				int i =0;
				for( Class pt : pts){
					
					vals[i] = convertType( pt, fieldClaz, params.get(key) ); 
					i++;
				}
				
				m.invoke(criteria, vals);
				
			}
			
		}
	}
	
	/**
	 * 拼写查询条件方法，此方法是重载方法
	 * @param criteria
	 * @param beanClass
	 * @param ruleList
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author malq
	 * @date 2012-12-18
	 */
	private static void andCriteria(Object criteria, Class beanClass, List<Rule> ruleList) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
			for(Rule rule : ruleList){
				if(rule.getValue()==null || "".equals(rule.getValue())) continue;
				
				String mkey = (rule.getField().charAt(0)+"").toUpperCase() + rule.getField().substring(1);
				Method getM = Converter.getMethod(beanClass, "get"+mkey, false);
				if( getM == null){
					LogFactory.getLog(Converter.class).warn("当拼写查询条件时有无效属性", new IllegalArgumentException("没有找到对应的GET方法："+rule.getField())) ;
					continue;
				}
				Class fieldClaz = getM.getReturnType();
				
				Method m = Converter.getMethod(criteria.getClass(), "and"+mkey+rule.getOp() , false);
				if( m == null){
					continue;
				}
				
				Class[] pts = m.getParameterTypes();
				Object[] vals = new Object[pts.length];
								
				int i =0;
				for( Class pt : pts){					
					vals[i] = convertType( pt, fieldClaz, rule.getValue()); 
					i++;
				}				
				m.invoke(criteria, vals);				
			}		
	}
	
	
	private static Object convertType(Class claz, Class fieldclaz,  String v){
		if( Long.class.equals(claz)){
			return Long.valueOf( v);
		}else if( Integer.class.equals(claz)){
			return Integer.valueOf(v);
		}else if( Short.class.equals(claz)){
			return Short.valueOf(v);
			
		}else if( BigDecimal.class.equals(claz)){
			return new BigDecimal(v);
			
		}else if( List.class.equals(claz)){
			StringTokenizer stoken = new StringTokenizer( v , ",");
			List ret = new ArrayList();
			while( stoken.hasMoreTokens()){
				ret.add( convertType(fieldclaz, fieldclaz, stoken.nextToken()));
			}
			return ret;
		}else{
			return v;
		}
		
		
	}
	private static Map<Class, Map<String,Method>> cachedM = Collections.synchronizedMap(new HashMap<Class,Map<String,Method>>());
	public static final Method getMethod(Class claz, String methodname, boolean isStatic){
		if( methodname == null) return null;
		if( cachedM.containsKey(claz) && cachedM.get(claz).containsKey(methodname.toLowerCase())){
			return cachedM.get(claz).get(methodname.toLowerCase());
		}
		
		Map<String,Method> methods = cachedM.get(claz);
		if( methods == null){
			methods = new HashMap<String,Method>();
			cachedM.put( claz, methods );
		}
		
		for( Method m : claz.getMethods() ){
			if( !Modifier.isPublic(m.getModifiers()) || isStatic && !Modifier.isStatic(m.getModifiers())  || !isStatic && Modifier.isStatic(m.getModifiers())){
				continue;
			}
			
			if( m.getName().equalsIgnoreCase(methodname)){
				methods.put(m.getName().toLowerCase(), m);
				return m;
			}
			
		}
		
		return null;
	}
	
	public static final Object staticCall(Class claz, String method, Class[] ptypes, Object[] params){
		
		try {
			Method m = claz.getMethod(method, ptypes);
			if( Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) ){
				return m.invoke(null, params);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		}
		return null;
	}
	//(newExample, "createCriteria", new Class[]{}, new Object[]{});
	public static final Object publicCall(Object This, String method, Class[] ptypes, Object[] params){
		
		try {
			Method m = This.getClass().getMethod(method, ptypes);
			if( Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()) ){
				return m.invoke(This, params);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.warn("反射方法值失败!", e);
		}
		return null;
	}
	
	
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception{
		DynamicSqlParameter dp = new DynamicSqlParameter();
		Rule r=new Rule();
		r.setField("code");
		r.setOp("Like");
		r.setType("String");
		r.setValue("1010");
		Rule r1=new Rule();
		r1.setField("pid");
		r1.setOp("EqualTo");
		r1.setType("String");
		r1.setValue("222");
		List<Rule> ruleList=new ArrayList<Rule>();
		ruleList.add(r);
		ruleList.add(r1);
		dp.setPage(1);
		dp.setPagesize(20);
		dp.setRows(100);
		dp.setRules(ruleList);
		SimpleCodeExampleExtended see=new SimpleCodeExampleExtended();
		Object obj=Converter.paramToExampleExtended(dp, see);
		System.out.println(obj);
//		try {
//			//@SuppressWarnings("unused")
//			//Object example2 = Converter.paramToExampleExtended(dp, new AccessibleObjectExampleExtended());
//			//Object example1 = Converter.paramToExampleExtended(dp, new AccessibleObjectExample());
//			
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			logger.warn("SYS_ERROR!", e);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			logger.warn("SYS_ERROR!", e);
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			logger.warn("SYS_ERROR!", e);
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			logger.warn("SYS_ERROR!", e);
//		}
//		
		
	}
	
	
}
