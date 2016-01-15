package com.sinoiov.upp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ctfo.base.intf.internal.ISimpleCodeManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service
public class SimpleCodeServiceImpl extends AbstractService implements ISimpleCodeService {
	private ISimpleCodeManager simpleCodeManager;

	private SimpleCodeServiceImpl() {
		simpleCodeManager = (ISimpleCodeManager) ServiceFactory.getFactory().getService(ISimpleCodeManager.class);
	}

	@Override
	public List<?> queryList(String type) throws UPPServiceException {
		try {
			return simpleCodeManager.getSimpleCodeByType(type);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getLocalizedMessage(), ue);
		} catch (Exception e) {
			logger.error("查询码表异常：" + e.getLocalizedMessage(), e);
			throw new UPPServiceException("查询码表异常：" + e.getLocalizedMessage(), e);
		}
	}

}
