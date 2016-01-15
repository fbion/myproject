//package com.ctfo.upp.service.code;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.ctfo.base.dao.beans.SimpleCode;
//import com.ctfo.base.dao.beans.SimpleCodeExampleExtended;
//import com.ctfo.base.intf.internal.ISimpleCodeManager;
//import com.ctfo.csm.uaa.dao.beans.UAASimpleCode;
//import com.ctfo.csm.uaa.dao.beans.UAASimpleCodeExampleExtended;
//import com.ctfo.csm.uaa.external.intf.IUAASystemManager;
//import com.ctfo.csm.uaa.intf.support.Operator;
//import com.ctfo.upp.exceptions.UPPException;
//import com.ctfo.upp.service.ServiceImpl;
//import com.ctfo.upp.util.PaginationResult;
//
//public class CodeServiceImpl extends ServiceImpl implements ICodeService{
//		
//	private static Log logger = LogFactory.getLog(CodeServiceImpl.class);
//	
//	
//	private CodeServiceImpl(){
//		
//		writeCodeFile();
//	}
//	
//	public List<SimpleCode> getAllCodeList() throws UPPException{
//		
//		final ISimpleCodeManager manager = this.getRemoManager(ISimpleCodeManager.class);
//		SimpleCodeExampleExtended exampleExtended = new SimpleCodeExampleExtended();
//		
//		return null;//manager.getSimpleCodeByExampleExtended(exampleExtended);
//		
//	}
//
//	
//	private void fulshJavaScriptCache(){
//		new Thread(){				
//			@Override
//			public void run() {
//				try {
//					writeCodeFile();
//				} catch (Exception e){					
//					logger.error("刷新JavaScript缓存异常:", e);
//				}				
//			}
//		 }.start();
//	}
//	
//	
//	/**
//	 * 取系统中全部代码，生成js文件，用户页面js缓存
//	 */
//	private final void writeCodeFile(){
//		      
//		OutputStreamWriter out = null;
//		try {
//			
//			logger.info("--->>>init code start...");
//			
//			List<UAASimpleCode> list = this.getAllCodeList();//查询所有的代码信息			
//			
//			String strPath = this.getClass().getResource("/").getPath();			
//			strPath = strPath.substring(0, strPath.indexOf("/WEB-INF/"))+"/js/csm/";		
//			String strFile = "codeData.js";
//			//String areaFile = "areaData.js";
//			
//			File file = new File(strPath);
//			if(!file.exists())
//				file.mkdirs();
//			
//			out = new OutputStreamWriter(new FileOutputStream(strPath+strFile),"UTF-8"); 			
//			
//			//fw = new FileWriter(strPath+strFile);  
//			String str = "/**\n* 码表数据信息 \n* @author malq \n* @version 3.0 \n* "+(new Date())+" \n*/\nwindow.CodeData="+JSONUtil.serialize(list).replaceAll("\"", "'")+";";				
//			//fw.write(str);		
//			out.write(str);
//			out.flush();
//			
//			logger.info("--->>>init simple code file:"+strPath+strFile);
//			
//			//行政区划代码信息
//			//list = this.getZoneList();
//			//out = new OutputStreamWriter(new FileOutputStream(strPath+areaFile),"UTF-8");
//			//str = "/**\n* 行政区划数据信息 \n* @author malq \n* @version 2.0 \n*/\nwindow.CodeData="+JSONUtil.serialize(list).replaceAll("\"", "'")+";";				
//			//out.write(str);
//			//logger.info("--->>>init zone code file:"+strPath+areaFile);
//			//logger.debug("--->>>init zone code data:"+JSONUtil.serialize(JSONUtil.serialize(list)));
//			
//			
//			logger.info("--->>>init code end");
//						
//		} catch (Exception e) {			
//			logger.error("app service init code js fail", e);
//		}finally{
//			try {
//				if(out!=null)out.close();
//			} catch (IOException e) {				
//				logger.error("init code io Exception",e);
//			}
//		}
//	}
//
//	@Override
//	public void modifyStatus(String id, String status, Operator op) {
//		try{
//			final IUAASystemManager manager = this.getRemoManager(IUAASystemManager.class);
//			
//			manager.modifyUAASimpleStatus(id, status, op);
//			
//		} catch (Exception e) {			
//			logger.error("修改码表状态异常", e);
//		}
//	}
//	@Override
//	public PaginationResult<?> add(Object model, Operator op)throws UPPException {
//		PaginationResult<?> result = new PaginationResult<Object>();
//		try{
//			final IUAASystemManager manager = this.getRemoManager(IUAASystemManager.class);
//			
//			String id = manager.addUAASimpleCode((UAASimpleCode)model, op);
//			
//			this.fulshJavaScriptCache();
//			
//			result.setMessage("操作成功");
//			
//		} catch (Exception e) {			
//			logger.error("修改码表状态异常", e);
//		}
//		return result;
//	}
//
//	@Override
//	public PaginationResult<?> update(Object model, Operator op) {
//		PaginationResult<?> result = new PaginationResult<Object>();
//		try{
//			final IUAASystemManager manager = this.getRemoManager(IUAASystemManager.class);
//			
//			manager.modifyUAASimpleCode((UAASimpleCode)model, op);
//			
//			this.fulshJavaScriptCache();
//			
//			result.setMessage("操作成功");
//			
//		} catch (Exception e) {			
//			logger.error("修改码表状态异常", e);
//		}
//		return result;
//	}
//
//	@Override
//	public PaginationResult<?> delete(Object model, Operator op) {
//		PaginationResult<?> result = new PaginationResult<Object>();
//		try{
//			final IUAASystemManager manager = this.getRemoManager(IUAASystemManager.class);
//			UAASimpleCode usc = (UAASimpleCode)model;
//			manager.removeUAASimpleCode(usc.getId(), op);
//			
//			this.fulshJavaScriptCache();
//			
//			result.setMessage("操作成功");
//			
//		} catch (Exception e) {			
//			logger.error("修改码表状态异常", e);
//		}
//		return result;
//	}
//
//
//
//	@Override
//	public boolean checkExist(Object model, String fieldName, Operator op) {
//		try{
//			IUAASystemManager manager = this.getRemoManager(IUAASystemManager.class);
//			UAASimpleCode simple = (UAASimpleCode)model;
//			String check = simple.getId();
//			String code = simple.getCode();
//			String pid = simple.getPid();
//			if (StringUtils.isNotBlank(check) && check.equals(code))
//				return true;// 修改的时候，如果和修改前的值一样的话，跳过查重
//
//			UAASimpleCodeExampleExtended codeEE = new UAASimpleCodeExampleExtended();
//			codeEE.createCriteria().andCodeEqualTo(code).andPidEqualTo(pid);	
//			int count = manager.countUAASimpleCodeByExampleExtended(codeEE, op);
//			
//			codeEE.clear();
//			codeEE.createCriteria().andCodeEqualTo(code).andPidEqualTo("0");//根类型下唯一
//			int count2 = manager.countUAASimpleCodeByExampleExtended(codeEE, op);
//			
//			if(count>0 || count2>0)
//				return false;
//	
//		} catch (Exception e) {			
//			logger.error("验证码表唯一性异常", e);
//		}		
//		return true;
//	}
//	
//	
//}
