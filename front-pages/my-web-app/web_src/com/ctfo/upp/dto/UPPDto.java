package com.ctfo.upp.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UPPDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(UPPDto.class);
	
	@SuppressWarnings("all")
	public String toString(){
		StringBuffer buf = new StringBuffer();
		Method[] methods = this.getClass().getMethods();
		Set<String> fs = new HashSet<String>();
		for( Method f: methods){
			if( !f.getName().startsWith("get")) continue;
			fs.add(f.getName().toLowerCase());
		}
		
		for( Method m : methods){
			String mn = m.getName();
			if( !mn.startsWith("get")) continue;
			
			if(fs.contains(mn.toLowerCase())){
				buf.append("{["+m.getName() +"]:[");
				try {
					Object ret = m.invoke(this, null);
					if( ret == null){
						buf.append("NULL]}; ");
					}else{
						buf.append(ret.toString() + "]}; ");
					}
				} catch (Exception e) {
					logger.error(e);
				} 
			}
		}
		return buf.toString();
	}
}
