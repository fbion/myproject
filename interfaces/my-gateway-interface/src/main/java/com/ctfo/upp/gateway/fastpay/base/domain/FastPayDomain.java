package com.ctfo.upp.gateway.fastpay.base.domain;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public abstract class FastPayDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		Method[] methods = this.getClass().getMethods();
		Set<String> fs = new HashSet<String>();
		for (Method f : methods) {
			if (!f.getName().startsWith("get"))
				continue;
			fs.add(f.getName().toLowerCase());
		}

		for (Method m : methods) {
			String mn = m.getName();
			if (!mn.startsWith("get"))
				continue;

			if (fs.contains(mn.toLowerCase())) {
				buf.append("{[" + m.getName() + "]:[");
				try {
					Object ret = m.invoke(this);
					if (ret == null) {
						buf.append("NULL]}; ");
					} else {
						buf.append(ret.toString() + "]}; ");
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return buf.toString();
	}
}
