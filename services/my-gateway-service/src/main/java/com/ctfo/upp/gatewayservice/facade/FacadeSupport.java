package com.ctfo.upp.gatewayservice.facade;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ctfo.gateway.bean.base.Necessary;

public class FacadeSupport {
	
	protected void validateObjectNecessary(Object object) throws Exception {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		for(Field f : fields){
			if(f.getAnnotation(Necessary.class) != null ){
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), object.getClass());  
	            Method getMethod = pd.getReadMethod();
	            Object o = getMethod.invoke(object);
	            if(pd.getPropertyType().equals(java.lang.String.class)){
	            	if(StringUtils.isBlank((String)o)){
		            	throw new Exception(">>>>>>>>>> " + f.getName() + " 不能为空 <<<<<<<<<<");
		            }
	            }
	            if(pd.getPropertyType().equals(java.util.List.class)){
	            	if(o == null || ((List)o).size() == 0){
		            	throw new Exception(">>>>>>>>>> " + f.getName() + " 不能为空 <<<<<<<<<<");
		            }
	            }
			}
		}
	}
}
