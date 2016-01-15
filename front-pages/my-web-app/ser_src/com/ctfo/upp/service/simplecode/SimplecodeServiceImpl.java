package com.ctfo.upp.service.simplecode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.base.dao.beans.SimpleCodeExampleExtended;
import com.ctfo.base.intf.internal.ISimpleCodeManager;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.ServiceImpl;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.Converter;

/**
 * 码表service实现层
 * 
 * @author lipeng01
 * 
 */
@Service("simpleCodeService")
public class SimplecodeServiceImpl extends ServiceImpl implements ISimplecodeService {

	private static Log logger = LogFactory.getLog(SimplecodeServiceImpl.class);

	private ISimpleCodeManager manager = null;
	private PaginationResult<SimpleCode> result = new PaginationResult<SimpleCode>();
	private ISimpleCodeManager getManager() {
		if (this.manager == null) {
			manager = (ISimpleCodeManager) ServiceFactory.getFactory().getService(ISimpleCodeManager.class);
		}
		return this.manager;
	}

	@Override
	public SimpleCode queryCodeById(String id) throws UPPException {
		try {
			manager = getManager();
			return manager.getSimpleCodeById(id);

		} catch (Exception e) {
			logger.error("查询码表对象异常", e);
			new UPPException("查询码表对象异常");
		}
		return null;

	}

	@Override
	public PaginationResult<?> addCode(SimpleCode simpleCode) throws UPPException {
		if (getManager().addSimpleCode(simpleCode) != null) {
			result.setMessage(Converter.OP_SUCCESS);
		} else {
			result.setMessage(Converter.OP_FAILED);
		}
		return result;
	}

	@Override
	public PaginationResult<?> updateCode(SimpleCode simpleCode) throws UPPException {
		try {
			getManager().modifySimpleCode(simpleCode);
			result.setMessage(Converter.OP_SUCCESS);

		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("更新码表数据异常", e);
		}
		return result;
	}

	@Override
	public PaginationResult<?> deleteCode(String ids) throws UPPException {
		try {
			getManager().removeSimpleCode(ids);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("删除码表数据异常",e);
		}
		return result;
	}

	@Override
	public List<?> queryCodeByParam(DynamicSqlParameter requestParam) throws UPPException {
		List<SimpleCode> list = new ArrayList<SimpleCode>();
		try {
			SimpleCodeExampleExtended scEE = new SimpleCodeExampleExtended();
			Converter.paramToExampleExtended(requestParam, scEE);
			list = getManager().getSimpleCodeByExampleExtended(scEE);
		} catch (Exception e) {
			logger.error("查询代码集合异常", e);
			throw new UPPException("查询代码集合异常");
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginationResult<?> queryCodeByPage(DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult result = null;
		try {
			SimpleCodeExampleExtended scEE = new SimpleCodeExampleExtended();
			Converter.paramToExampleExtended(requestParam, scEE);
			result = getManager().getSimpleCodePageByExampleExtended(scEE);
		} catch (Exception e) {
			logger.error("查询代码页面异常", e);
			throw new UPPException("查询代码页面异常");
		}
		return result;
	}
	public boolean checkExist(String typeCode,String code) {

		try {
			SimpleCode simpleCode = getManager().getSimpleCodeByTypeAndCode(typeCode,code);
			if(simpleCode==null){
				return true;
			}
		} catch (Exception e) {
			logger.error("app service checkExixt ERROR", e);
		}
		return false;
	}

	@Override
	public PaginationResult<?> modifyStatus(SimpleCode simpleCode) throws UPPException {
		int status = -1;
		status = getManager().modifySimpleCodeStatus(simpleCode);
		if(status==1){
			result.setMessage(Converter.OP_SUCCESS);
		}else{
			result.setMessage(Converter.OP_FAILED);
		}
		return result;
		
	}

	@Override
	public String getNameBycode(String code) throws UPPException {
		String name="";
		try {
			SimpleCode simpleCode = getManager().getSimpleCodeByCode(code);
			name = simpleCode.getName();
		} catch (Exception e) {
			logger.error("通过编码查询名称异常",e);
			throw new UPPException("通过编码查询名称异常",e);
		}
		return name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleCode> getByFCode(String code) throws UPPException {
		SimpleCode simpleCode = getManager().getSimpleCodeByCode(code);
		DynamicSqlParameter requestParam = new DynamicSqlParameter();
		Map<String,String> map = new HashMap<String, String>();
		map.put("pid", simpleCode.getId());
		requestParam.setEqual(map);
		return (List<SimpleCode>) queryCodeByParam(requestParam);
	}

	@Override
	public SimpleCode getByTypeCodeAndCode(String typeCode, String code) throws UPPException {
		SimpleCode simpleCode = new SimpleCode();
		simpleCode = getManager().getSimpleCodeByTypeAndCode(typeCode, code);
		return simpleCode;
	}

}
