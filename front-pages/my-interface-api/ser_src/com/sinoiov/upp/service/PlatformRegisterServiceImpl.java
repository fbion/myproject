package com.sinoiov.upp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ctfo.base.dao.beans.UPPlatform;
import com.ctfo.base.intf.internal.PlatformRegisterManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.service.dto.PlatformDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service
public class PlatformRegisterServiceImpl extends AbstractService implements IPlatformRegisterService {

	private PlatformRegisterManager platformRegisterManager = null;

	private PlatformRegisterServiceImpl() {
		platformRegisterManager = (PlatformRegisterManager) ServiceFactory.getFactory().getService(
				PlatformRegisterManager.class);
	}

	@Override
	public PlatformDto register(PlatformDto platformDto) throws UPPServiceException {
		UPPlatform flatformNew = new UPPlatform();

		try {
			BeanUtils.copyProperties(platformDto, flatformNew);
			UPPlatform platformDB = platformRegisterManager.register(flatformNew);
			BeanUtils.copyProperties(platformDB, platformDto);
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("充值接口发布服务异常", e);
			throw new UPPServiceException("充值接口发布服务异常");
		}

		return platformDto;
	}

}
