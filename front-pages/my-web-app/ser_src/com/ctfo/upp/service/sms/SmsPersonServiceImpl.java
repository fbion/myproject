package com.ctfo.upp.service.sms;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.file.bean.DynamicMongoParameter;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.UUIDUtil;
import com.ctfo.upp.service.sms.bean.Sms;
import com.ctfo.upp.service.sms.bean.SmsPerson;
import com.ctfo.upp.service.smscode.CommonBaseManager;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.utils.SpringBUtils;
import com.google.code.morphia.query.Query;

/**
 * 短信模板实现
 * 
 * @author lipeng
 */
@Service("smsPersonService")
@SuppressWarnings("all")
public class SmsPersonServiceImpl implements SmsPersonService {

	private static Log logger = LogFactory.getLog(SmsPersonServiceImpl.class);
	
	private IMongoService mongoService;
	private CommonBaseManager commonBaseManager;
	public SmsPersonServiceImpl() {
		mongoService = (IMongoService) SpringBUtils.getBean("mongoService");
		mongoService.setDatasource("BUSINESS");
	} 

	private CommonBaseManager getCommonBaseManager() {
		if (this.commonBaseManager == null) {
			commonBaseManager = (CommonBaseManager) ServiceFactory.getFactory().getService(CommonBaseManager.class);
		}
		logger.info("webAPP中交账户-调用发送短信服务manager"+commonBaseManager);
		return this.commonBaseManager;
	}
	
	@Override
	public void add(Object model) throws UPPException {
		try {
			mongoService.save(model);
		} catch (Exception e) {
			logger.error("添加短信模板失败！");
			throw new UPPException("添加短信模板失败！", e);
		}
	}

	@Override
	public void update(Object model) throws UPPException {
		try {
			mongoService.update(model);
		} catch (Exception e) {
			logger.error("更新短信模板失败！");
			throw new UPPException("更新短信模板失败！", e);
		}
	}

	@Override
	public boolean delete(String ids) throws UPPException {
		//ids = templateCodes;
		boolean flag = false;
		String[] idArr = ids.split(",");
		for(String uuid :idArr){
			try {
				mongoService.delete(SmsPerson.class, uuid);
				flag = true;
			} catch (Exception e) {
				logger.error("删除短信模板失败！oid:"+ uuid);
				throw new UPPException("删除短信模板失败！", e);
			}
		}
		
		return flag;
	}

	@Override
	public SmsPerson queryById(String uuid) throws UPPException {
		SmsPerson sms=null;
		try {
			sms = (SmsPerson)mongoService.get(SmsPerson.class, uuid);
			
		} catch (Exception e) {
			logger.error("按ID查询短信模板失败！");
			throw new UPPException("按ID查询短信模板失败！", e);
		}
		return sms;
	}

	@Override
	public PaginationResult<SmsPerson> query(DynamicMongoParameter requestParam) throws UPPException {
		PaginationResult<SmsPerson> result = new PaginationResult();
		try {
			Query<SmsPerson> query = mongoService.getQuery(SmsPerson.class);
			mongoService.convertParamToQuery(query, requestParam);
			
			List<SmsPerson> list = mongoService.query(SmsPerson.class, query);
			result.setData(list);
			result.setStart(requestParam.getStartNum());
			result.setTotal((int)mongoService.getCount());
		} catch (Exception e) {
			logger.error("查询短信模板异常");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("查询参数异常", e);
		}
		return result;
	}


/*
	@Override
	public void sendShortMess(String mobileNum,String templetNo,List<String> contents)
			throws UPPException {
		this.getCommonBaseManager().sendShortMess(mobileNum, templetNo, contents);
	}
	@Override
	public void sendShortMessByAccountNo(String accountNo,String templetNo,List<String> contents)
			throws UPPException {
		this.getCommonBaseManager().sendShortMessByAccountNo(accountNo, templetNo, contents);
	}*/

}
