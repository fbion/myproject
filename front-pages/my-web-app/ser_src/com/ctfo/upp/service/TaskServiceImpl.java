package com.ctfo.upp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.excel.exp.ExportExcel;
import com.ctfo.excel.parse.ExpObj;
import com.ctfo.excel.util.ExcelUtil;
import com.ctfo.excel.util.ExcelVersion;
import com.ctfo.file.bean.AttachmentBean;
import com.ctfo.file.boss.IFileService;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.excelbeans.UploadTaskBean;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.simplecode.ISimplecodeService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DefaultConfig;
import com.ctfo.upp.utils.SpringBUtils;
import com.google.code.morphia.query.Query;


public abstract class TaskServiceImpl<T, TExcel> extends AbstractService implements ITaskService {
	
	private static Log logger = LogFactory.getLog(TaskServiceImpl.class);
	
	private Map<String, String> codeMap = new HashMap<String, String>();
	
	protected IMongoService<UploadTaskBean> mongoService;
	
	protected IFileService iFileService;
	
	@Resource
	private ISimplecodeService simpleCodeService;
	
	public PaginationResult<?> queryTaskList(DynamicSqlParameter requestParam)
			throws UPPException {
		PaginationResult result = new PaginationResult();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			com.ctfo.csm.local.DynamicSqlParameter csmDP = new com.ctfo.csm.local.DynamicSqlParameter();
			Map<String,String> like = requestParam.getLike()==null?null:requestParam.getLike();
			Map<String,String> equal = requestParam.getEqual()==null?null:requestParam.getEqual();
			if(equal!=null)csmDP.setEqual(equal);
			if(like!=null)csmDP.setEqual(like);
			csmDP.setOrder(requestParam.getOrder());
			csmDP.setSort(requestParam.getSort());
			mongoService = mongoService==null?(IMongoService)SpringBUtils.getBean("mongoService"):mongoService;
			//从数据库获取数据集合
			Query<UploadTaskBean> query = mongoService.getQuery(UploadTaskBean.class);
			mongoService.convertParamToQuery(query, csmDP);
			List<UploadTaskBean> taskList = mongoService.query(UploadTaskBean.class, query);
			String path = DefaultConfig.getValue("FILE_MONGO_PATH");
			for(UploadTaskBean uploadTask : taskList){
				if(StringUtils.isNotBlank(uploadTask.getFilePath()))
					uploadTask.setFilePath(path+uploadTask.getFilePath());
			}
			long count = mongoService.getCount(UploadTaskBean.class, csmDP);
			//将获取的数据放入PaginationResult对象
			result.setData(taskList);
			result.setTotal((int)count);
		} catch (Exception e) {
			logger.error("查询下载任务列表异常", e);
			throw new UPPException("查询下载任务列表异常", e);
		} 
		return result;
	}
	
	/**
	 * 1.添加下载任务
	 * 2.统计查询
	 * 3.写文件到Mongo中
	 * 4.更新任务状态
	 * @param taskName
	 * @param countUrl
	 * @param queryUrl
	 * @param requestParam
	 * @param myClass
	 * @return
	 * @throws Exception
	 */
	protected void runExportExcelTask(String taskName, String countUrl, final String queryUrl, final DynamicSqlParameter requestParam, final Class<?> t, final Class<?> te)throws Exception{
		
		final String taskId = this.saveTaskMongo(taskName);
		
		final int count = this.countTask(countUrl, requestParam);
		
		if(count>0){
			new Runnable() {
				@Override
				public void run() {
					try{
						
						List<T> list = queryTask(queryUrl, requestParam, count, t);
						
						List<TExcel> result = copyTask(list);
						//保存文件
						String filePath = saveFile(result, taskId, te);
						//更新下载任务
						updateTaskStatusMongo(taskId, filePath);
						
					} catch (Exception e) {
						logger.error("导出文件异常！", e);		
					}
				}
			}.run();
		}
	}
	
	
	/***
	 * 1.统计导出的条数
	 * 2.分页查询账户交易流水
	 */
	private List<T> queryTask(String url, DynamicSqlParameter requestParam, int count, Class<?> myClass) throws UPPException {
		List<T> list=new ArrayList<T>();
		try {
			List<T> tem = null;
			String json = "";
			JSONObject dataObj = null;
			JSONArray jArray = null;
			int page = 1;
			int rows = 500;
			do{
				requestParam.setRows(rows);
				requestParam.setPage(page);
				json = super.sendRequest(requestParam, url);
				dataObj = super.processReturnResult(json);
				jArray = (JSONArray)(( dataObj ).get(RESULT_DATA_NAME));
				tem = (List)JSONArray.toCollection(jArray, myClass);
				if(tem!=null && tem.size()>0){
					list.addAll(tem);
				}
				page = (count - page * rows) >0?page+1:0;
				Thread.sleep(5000);//暂停一段时间
			}while(page > 0);
			
			return list;
			
		} catch (Exception e) {
			logger.error("查询账户交易流水异常:"+e.getLocalizedMessage(), e);
			throw new UPPException("查询账户交易流水异常:"+e.getLocalizedMessage(), e);
		}
	}
	
	private int countTask(String url, DynamicSqlParameter requestParam)throws Exception{
		String json = super.sendRequest(requestParam, url);
		JSONObject jsonMap = (JSONObject)(JSONObject.fromObject(json)).get(RESULT_DATA_NAME);
		String count = (jsonMap==null || jsonMap.get("count")==null)?"0":jsonMap.getString("count");
		return Integer.parseInt(count);
	}
	
	private String saveFileToTmp(List<TExcel> list, String taskId, Class<?> excelClass)throws Exception{
		FileOutputStream out = null;
		try{
			String filePath = DefaultConfig.getValue("UPLOAD_TMP_FILE_PATH")+taskId+".xls";
		    out = new FileOutputStream(filePath);
			ExportExcel<TExcel> exp = new ExportExcel<TExcel>();
			List<ExpObj> expObjs = ExcelUtil.getExpObjs(excelClass, 0, "EXP");
			// 定义的方法
			List<Method> methods = new ArrayList<Method>(expObjs.size());
			// 定义执行方法的对象
			List<Object> objs = new ArrayList<Object>(expObjs.size());
			for (int i = 0; i < expObjs.size(); i++) {
				objs.add(null);
				methods.add(null);
			}
			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
			exp.singleExportExcel(list, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
			out.flush();
			return filePath;
		}catch(Exception e){
			logger.error("保存临时文件异常", e);
		}finally{
			 out.close();
		}
		return null;
	}
	private void deleFile(File file){
		FileOutputStream out = null;
		    // 路径为文件且不为空则进行删除  
		if (file.isFile() && file.exists()) {  
		      file.delete(); 
		  }  
		  
	}
	private String saveFile(List<TExcel> list, String taskId, Class<?> excelClass)throws Exception{
		//保存临时目录
		String filePath = this.saveFileToTmp(list, taskId, excelClass);
		File file = new File(filePath);
		//保存到mongo中
		AttachmentBean bean = new AttachmentBean();
		bean.setFile(file);
		bean.setFileName(file.getName());
		iFileService = iFileService==null?(IFileService)SpringBUtils.getBean("fileService"):iFileService;		
		String mogoPath = iFileService.addFile(bean);
		try{
			if (file.isFile() && file.exists()) file.delete();//删除文件 
		}catch(Exception e){
			logger.warn("删除文件异常", e);
		}	
		return mogoPath;
	}
	
	private String saveTaskMongo(String taskName)throws Exception{
		UploadTaskBean tesk = new UploadTaskBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String temp = sdf.format(new Date());
		HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String loginName = (String)request.getSession().getAttribute(Converter.SESSION_REMOTE_USER);
		tesk.setCreateOp(loginName);
		tesk.setCreateTime(System.currentTimeMillis()+"");
		//tesk.setFilePath(filePath);
		tesk.setTaskId(temp+String.valueOf(Math.random()).substring(2).substring(0, 3));
		tesk.setTaskName(taskName);
		tesk.setStatus("1");
		mongoService = mongoService==null?(IMongoService)SpringBUtils.getBean("mongoService"):mongoService;
		mongoService.save(tesk);
		
		return tesk.getTaskId();
	}
	
	private void updateTaskStatusMongo(String taskId, String filePath)throws Exception{
		mongoService = mongoService==null?(IMongoService)SpringBUtils.getBean("mongoService"):mongoService;
		UploadTaskBean update = mongoService.get(UploadTaskBean.class, taskId);	
		update.setFilePath(filePath);
		update.setStatus("2");
		mongoService.save(update);
	}
	
	
	/**
	 * 需要继承的类实现
	 * @param list
	 * @return
	 * @throws UPPException
	 * @throws Exception 
	 */
	public abstract List<TExcel> copyTask(List<?> list) throws Exception;
	public String getCodeName(String type, String code)throws Exception{
		String value = codeMap.get(code);
		if(StringUtils.isNotBlank(value))
			return value;
		SimpleCode sc = simpleCodeService.getByTypeCodeAndCode(type, code);
		value = sc==null?"":sc.getName();
		if(StringUtils.isNotBlank(value)){
			codeMap.put(sc.getCode(), value);
			return value;
		}
		return "";
	}

}
