package com.ctfo.upp.service.logmanager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.ctfo.csm.local.DynamicSqlParameter;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.log.WebApiServiceLog;
import com.ctfo.upp.logmanager.bean.LogSet;
import com.ctfo.upp.logmanager.bean.SystemLogBean;
import com.ctfo.upp.models.InteractiveRecord;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.UUIDUtil;
import com.ctfo.upp.utils.SpringBUtils;
import com.google.code.morphia.query.Query;
import com.sinoiov.upp.mongoBD.LogGateway;

@Service
public class LogManagerServiceImpl implements ILogManagerService {
	
	private IMongoService<InteractiveRecord> interactiveRecordService;
	
	private IMongoService<LogGateway> TL_YB_logService;
	
	private IMongoService<SystemLogBean> systemLogService;
	
	private IMongoService<WebApiServiceLog> mobileLogService;
	
	private IMongoService<LogSet> logSetService;
	
	public LogManagerServiceImpl() {
		interactiveRecordService = (IMongoService) SpringBUtils.getBean("mongoService");
		TL_YB_logService = (IMongoService) SpringBUtils.getBean("mongoService");
		systemLogService = (IMongoService) SpringBUtils.getBean("mongoService");
		logSetService = (IMongoService) SpringBUtils.getBean("mongoService");
		mobileLogService = (IMongoService) SpringBUtils.getBean("mongoService");
		if(interactiveRecordService!=null)
			interactiveRecordService.setDatasource("LOGS");
		if(systemLogService!=null)
			systemLogService.setDatasource("LOGS");
		if(TL_YB_logService!=null)
			TL_YB_logService.setDatasource("LOGS");
		if(logSetService!=null)
			logSetService.setDatasource("LOGS");
		if(mobileLogService!=null)
			mobileLogService.setDatasource("LOGS");
	}
	
	public PaginationResult<?> queryInteractiveRecord(DynamicSqlParameter requestParam) throws Exception {
		//创建PaginationResult对象
		PaginationResult result = new PaginationResult();
		String bizzOrderNo = requestParam.getEqual()==null || requestParam.getEqual().get("bizzOrderNo")==null?"":requestParam.getEqual().remove("bizzOrderNo");
		if(StringUtils.isNotBlank(bizzOrderNo)){
			Map<String, String> like = requestParam.getLike()==null?new HashMap<String,String>():requestParam.getLike();
			like.put("jsonData", "%"+bizzOrderNo+"%");
		}
		//从数据库获取数据集合
		Query<InteractiveRecord> query = interactiveRecordService.getQuery(InteractiveRecord.class);
		interactiveRecordService.convertParamToQuery(query, requestParam);
		List<InteractiveRecord> InteractiveRecordList = interactiveRecordService.query(InteractiveRecord.class, query);
		//从数据库获取数据总数
		long count = interactiveRecordService.getCount();
		//将获取的数据放入PaginationResult对象
		result.setData(InteractiveRecordList);
		result.setTotal((int)count);
		return result;
	}
	
	public List queryInteractiveRecordDetail(DynamicSqlParameter requestParam) throws Exception {
		//从数据库根据ID获取数据
		List<InteractiveRecord> InteractiveRecordList = interactiveRecordService.query(InteractiveRecord.class, requestParam);
		return InteractiveRecordList;
	}

	public PaginationResult<?> queryTL_YB_log(DynamicSqlParameter requestParam) throws Exception {
		//创建PaginationResult对象
		PaginationResult result = new PaginationResult();
		//从数据库获取数据集合
		Query<LogGateway> query = TL_YB_logService.getQuery(LogGateway.class);
		TL_YB_logService.convertParamToQuery(query, requestParam);
		List<LogGateway> YBLogquery = TL_YB_logService.query(LogGateway.class, query);
		long count = TL_YB_logService.getCount(LogGateway.class, requestParam);
		//将获取的数据放入PaginationResult对象
		result.setData(YBLogquery);
		result.setTotal((int)count);
		
		return result;
	}

	public List queryTL_YB_logDetail(DynamicSqlParameter requestParam)throws Exception {
		//从数据库根据ID获取数据
		List<LogGateway> query = TL_YB_logService.query(LogGateway.class, requestParam);
		return query;
	}

	@Override
	public PaginationResult<?> querySystemLog(DynamicSqlParameter requestParam) throws Exception{
		//创建PaginationResult对象
		PaginationResult result = new PaginationResult();
		//从数据库获取数据集合
		Query<SystemLogBean> query = systemLogService.getQuery(SystemLogBean.class);
		systemLogService.convertParamToQuery(query, requestParam);
		List<SystemLogBean> systemLogquery = systemLogService.query(SystemLogBean.class, query);
		long count = systemLogService.getCount(SystemLogBean.class, requestParam);
		//将获取的数据放入PaginationResult对象
		result.setData(systemLogquery);
		result.setTotal((int)count);
		
		return result;
	}

	@Override
	public List querySystemLogDetail(String id)throws Exception {
		//从数据库根据ID获取数据
		Query<SystemLogBean> query = systemLogService.getQuery(SystemLogBean.class);
		query.field("_id").equal(new ObjectId(id));
		List<SystemLogBean> systemLogquery = systemLogService.query(SystemLogBean.class, query);
		return systemLogquery;
	}

	
	
	@Override
	public List<LogSet> querySystemLogSetList() throws Exception {
		
		Query<LogSet> query = logSetService.getQuery(LogSet.class);
		query.order("systemName");
		List<LogSet> logSets = logSetService.query(LogSet.class, query);
		
		return logSets;
	}


	@Override
	public void deleteLogs(String dbName, String dateStr, String systemName, String level) throws Exception {
		try{
			
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> map = new HashMap<String,String>();
			map.put("logTime", dateStr);
			if(StringUtils.isNotBlank(systemName))
				map.put("systemName", systemName);
			if(StringUtils.isNotBlank(level))
				map.put("level", level);
			requestParam.setEndwith(map);
			int page = 1, rows = 500;
			Query<SystemLogBean> query = null;
			List<SystemLogBean> logs = new ArrayList<SystemLogBean>();
			do{
				requestParam.setPage(page);
				requestParam.setRows(rows);
			    query = systemLogService.getQuery(SystemLogBean.class);
				systemLogService.convertParamToQuery(query, requestParam);
				logs = systemLogService.query(SystemLogBean.class, query);
				for(SystemLogBean tem : logs){
					systemLogService.delete(SystemLogBean.class, new ObjectId(tem.getId()));
				}
				page++;
				Thread.sleep(5000);
			 }while(logs.size() >= rows);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveOrUpdateLogSet(LogSet logSet) throws Exception {
		try{
			
		
		if(StringUtils.isBlank(logSet.getSystemName()))
			throw new UPPException("系统名称不能为空！");
		
		if(StringUtils.isBlank(logSet.getId())){
			logSet.setId(UUIDUtil.newUUID());
		}else{
			Query<LogSet> query = logSetService.getQuery(LogSet.class);
			query.field("systemName").equal(logSet.getSystemName());
			List<LogSet> list = logSetService.query(LogSet.class, query);
			logSet.setId(list.get(0).getId());
			for(LogSet tem : list){
				logSetService.delete(LogSet.class, tem.getId());
			}
		}
		
		logSetService.save(logSet);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public PaginationResult<?> queryMobileLog(DynamicSqlParameter requestParam)
			throws Exception {
			//创建PaginationResult对象
				PaginationResult result = new PaginationResult();
				
				String bizzOrderNo = requestParam.getEqual()==null || requestParam.getEqual().get("bizzOrderNo")==null?"":requestParam.getEqual().remove("bizzOrderNo");
				if(StringUtils.isNotBlank(bizzOrderNo)){
					Map<String, String> like = requestParam.getLike()==null?new HashMap<String,String>():requestParam.getLike();
					like.put("request", "%"+bizzOrderNo+"%");
				}
				//从数据库获取数据集合
				Query<WebApiServiceLog> query = mobileLogService.getQuery(WebApiServiceLog.class);
				mobileLogService.convertParamToQuery(query, requestParam);
				List<WebApiServiceLog> logquery = mobileLogService.query(WebApiServiceLog.class, query);
				long count = mobileLogService.getCount(WebApiServiceLog.class, requestParam);
				//将获取的数据放入PaginationResult对象
				result.setData(logquery);
				result.setTotal((int)count);
				
				return result;
	}

	@Override
	public WebApiServiceLog queryMobileLogDetail(String id) throws Exception {
		//从数据库根据ID获取数据
		//Query<WebApiServiceLog> query = mobileLogService.getQuery(WebApiServiceLog.class);
		WebApiServiceLog mobileLog = mobileLogService.get(WebApiServiceLog.class, id);
		//query.field("_id").equal(new ObjectId(id));
		//List<SystemLogBean> systemLogquery = mobileLogService.query(WebApiServiceLog.class, query);
		return mobileLog;
	}

	@Override
	public void deleteInterfaceServiceLogs(String dateStr, String operCode)
			throws Exception {
		try{
			
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> map = new HashMap<String,String>();
			map.put("createTime", dateStr);
			requestParam.setEndwith(map);
			
			if(StringUtils.isNotBlank(operCode)){
				Map<String,String> equal = new HashMap<String,String>();
				equal.put("operCode", operCode);
				requestParam.setEqual(equal);
			}
			
			long count = interactiveRecordService.getCount(InteractiveRecord.class, requestParam);
			int page = 1, rows = 500;
			Query<InteractiveRecord> query = null;
			List<InteractiveRecord> logs = new ArrayList<InteractiveRecord>();
			do{
				requestParam.setPage(page);
				requestParam.setRows(rows);
			    query = interactiveRecordService.getQuery(InteractiveRecord.class);
			    interactiveRecordService.convertParamToQuery(query, requestParam);
				logs = interactiveRecordService.query(InteractiveRecord.class, query);
				for(InteractiveRecord tem : logs){
					interactiveRecordService.delete(InteractiveRecord.class, tem.getId());
				}
				page = (count - page * rows) >0?page+1:0;
				Thread.sleep(5000);//暂停一段时间
			}while(page > 0);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteMobileApiServiceLogs(String handleTime, String channelCode,
			String state) throws Exception {
		try{//IMongoService<WebApiServiceLog> mobileLogService;
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> map = new HashMap<String,String>();
			map.put("handleTime", handleTime);
			if(StringUtils.isNotBlank(channelCode) || StringUtils.isNotBlank(state)){
				Map<String,String> equal = new HashMap<String,String>();
				if(StringUtils.isNotBlank(channelCode))
					equal.put("channelCode", channelCode);
				if(StringUtils.isNotBlank(state))
					equal.put("state", state);
				requestParam.setEqual(equal);
			}
			
			requestParam.setEndwith(map);
			long count = mobileLogService.getCount(WebApiServiceLog.class, requestParam);
			int page = 1, rows = 500;
			Query<WebApiServiceLog> query = null;
			List<WebApiServiceLog> logs = new ArrayList<WebApiServiceLog>();
			do{
				requestParam.setPage(page);
				requestParam.setRows(rows);
			    query = mobileLogService.getQuery(WebApiServiceLog.class);
			    mobileLogService.convertParamToQuery(query, requestParam);
				logs = mobileLogService.query(WebApiServiceLog.class, query);
				for(WebApiServiceLog tem : logs){
					mobileLogService.delete(WebApiServiceLog.class, tem.getId());
				}
				page = (count - page * rows) >0?page+1:0;
				Thread.sleep(5000);//暂停一段时间
			}while(page > 0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteGatewayServiceLogs(String requestTime, String name,
			String type) throws Exception {
		try{//IMongoService<LogGateway> TL_YB_logService
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> map = new HashMap<String,String>();
			map.put("requestTime", requestTime);
			requestParam.setEndwith(map);
			if(StringUtils.isNotBlank(name) || StringUtils.isNotBlank(type)){
				Map<String,String> equal = new HashMap<String,String>();
				if(StringUtils.isNotBlank(type))
					equal.put("type", type);
				if(StringUtils.isNotBlank(name))
					equal.put("name", name);
				requestParam.setEqual(equal);
			}
			long count = TL_YB_logService.getCount(LogGateway.class, requestParam);
			int page = 1, rows = 500;
			Query<LogGateway> query = null;
			List<LogGateway> logs = new ArrayList<LogGateway>();
			do{
				requestParam.setPage(page);
				requestParam.setRows(rows);
			    query = TL_YB_logService.getQuery(LogGateway.class);
			    TL_YB_logService.convertParamToQuery(query, requestParam);
				logs = TL_YB_logService.query(LogGateway.class, query);
				for(LogGateway tem : logs){
					TL_YB_logService.delete(LogGateway.class, tem.getId());
				}
				page = (count - page * rows) >0?page+1:0;
				Thread.sleep(5000);//暂停一段时间
			}while(page > 0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
