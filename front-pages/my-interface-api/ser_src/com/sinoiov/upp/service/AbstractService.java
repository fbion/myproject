package com.sinoiov.upp.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.soa.ServiceFactory;

public abstract class AbstractService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@SuppressWarnings("unchecked")
	protected static <S> S getService(Class<S> clazz) {
		return (S) ServiceFactory.getFactory().getService(clazz);
	}
}
