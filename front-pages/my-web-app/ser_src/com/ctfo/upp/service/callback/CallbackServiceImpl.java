package com.ctfo.upp.service.callback;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.base.dao.beans.UPPlatformCallbackUrl;
import com.ctfo.base.dao.beans.UPPlatformCallbackUrlExampleExtended;
import com.ctfo.base.intf.internal.PlatformRegisterManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.ServiceImpl;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.Converter;

/**
 * 商户管理service实现层
 * 
 * @author lipeng01
 * 
 */
@Service("callbackService")
public class CallbackServiceImpl extends ServiceImpl implements CallbackService {

	private static Log logger = LogFactory.getLog(CallbackServiceImpl.class);
	private PlatformRegisterManager manager = null;
	private PaginationResult<UPPlatformCallbackUrl> result = new PaginationResult<UPPlatformCallbackUrl>();
	private PlatformRegisterManager getManager() {
		if (this.manager == null) {
			manager = (PlatformRegisterManager) ServiceFactory.getFactory().getService(PlatformRegisterManager.class);
		}
		return this.manager;
	}
	@Override
	public PaginationResult<UPPlatformCallbackUrl> callbackAdd(UPPlatformCallbackUrl model) throws UPPException {
		try {
			getManager().addCallbackURL(model);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("添加回调数据异常",e);
			throw new UPPException("添加回调数据异常",e);
		}
		return result;
	}
	@Override
	public PaginationResult<UPPlatformCallbackUrl> callbackList(DynamicSqlParameter requestParam) throws UPPException {
		try {
			UPPlatformCallbackUrlExampleExtended uppEE = new UPPlatformCallbackUrlExampleExtended();
			Converter.paramToExampleExtended(requestParam, uppEE);
			result = getManager().queryCallbackURLByPage(uppEE);
		} catch (Exception e) {
			logger.error("分页查询回调信息异常", e);
			throw new UPPException("分页查询回调信息异常",e);
		}
		return result;
	}
	@Override
	public UPPlatformCallbackUrl findCallbackById(String id) throws UPPException {
		UPPlatformCallbackUrl url = new UPPlatformCallbackUrl();
		try {
			url = getManager().getCallbackURLById(id);
		} catch (Exception e) {
			logger.error("查询回调信息失败",e);
			throw new UPPException("查询回调信息失败",e);
		}
		return url;
	}
	@Override
	public PaginationResult<UPPlatformCallbackUrl> callbackEdit(UPPlatformCallbackUrl model) throws UPPException {
		try {
			getManager().modifyCallbackURL(model);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("修改回调对象失败",e);
			throw new UPPException("修改回调对象失败",e);
		}
		return result;
	}
	@Override
	public PaginationResult<UPPlatformCallbackUrl> delCallback(String id) throws UPPException {
		try {
			getManager().removeCallbackURL(id);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("删除回调对象异常",e);
			throw new UPPException("删除回调对象异常",e);
		}
		return result;
	}



}
