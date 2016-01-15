package com.ctfo.upp.service.merchant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.base.dao.beans.UPParam;
import com.ctfo.base.dao.beans.UPParamExampleExtended;
import com.ctfo.base.dao.beans.UPPlatform;
import com.ctfo.base.dao.beans.UPPlatformExampleExtended;
import com.ctfo.base.intf.internal.ParamManager;
import com.ctfo.base.intf.internal.PlatformRegisterManager;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.encryptkey.EncryptKeyUtils;
import com.ctfo.upp.encryptkey.KeyBean;
import com.ctfo.upp.excelbeans.MerchantExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.KeyCache;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.UUIDUtil;
import com.ctfo.upp.service.ServiceImpl;
import com.ctfo.upp.service.simplecode.SimplecodeServiceImpl;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DateUtil;
import com.ctfo.upp.util.DefaultConfig;
import com.ctfo.upp.utils.SpringBUtils;
import com.google.code.morphia.query.Query;
@Service("merchantService")
public class MerchantServiceImpl extends ServiceImpl implements MerchantService {
	private static Log logger = LogFactory.getLog(SimplecodeServiceImpl.class);
	private PlatformRegisterManager manager = null;
	private PaginationResult<UPPlatform> result = new PaginationResult<UPPlatform>();
	private PlatformRegisterManager getManager() {
		if (this.manager == null) {
			manager = (PlatformRegisterManager) ServiceFactory.getFactory().getService(PlatformRegisterManager.class);
		}
		return this.manager;
	}
	
	
	private void setCacheParam(){
		try{			
				IMongoService<KeyCache> mongoService = (IMongoService<KeyCache>)SpringBUtils.getBean("mongoService");	
				Query<KeyCache> keyQuery = mongoService.getQuery(KeyCache.class);
				List<KeyCache> list = mongoService.query(KeyCache.class, keyQuery);
				for(KeyCache tem : list){
					mongoService.delete(KeyCache.class, tem.getId());
				}				
				////
				UPParamExampleExtended exampleExtended = new UPParamExampleExtended();
				List<String> tem = new ArrayList<String>();
				tem.add("MOBILE_API_APP_CACHE");
				tem.add("PROTAL_API_APP_CACHE");
				tem.add("CASHIER_APP_CACHE");
				tem.add("WEB_APP_CACHE");
				tem.add("ACCOUNT_SERVICE_CACHE");
				tem.add("INTERFACE_APP_CACHE");
				tem.add("BASE_SERVICE_CACHE");
				tem.add("GATEWAY_SERVICE_CACHE");
				tem.add("WEB_GATEWAY_SERVICE_CACHE");
				tem.add("FAST_GATEWAY_SERVICE_CACHE");
				exampleExtended.createCriteria().andParamNameIn(tem);
				ParamManager paramManager = (ParamManager) ServiceFactory.getFactory().getService(ParamManager.class);
				List<UPParam> dbList = paramManager.queryParam(exampleExtended);
				KeyCache keyCache = null;
				String[] arrs = null;
				String ip="", post="",uri="";
				for(UPParam uppParam : dbList){
					arrs = StringUtils.isNotBlank(uppParam.getParamValue())?uppParam.getParamValue().split(";"):null;
					for(String ss : arrs){
						ip = ss.split("://")[1].split(":")[0];
						post = ss.split("://")[1].split(":")[1].split("/")[0];
						uri = ss.split("://")[1].split(":")[1].split("/")[1];
						keyCache = new KeyCache();
						keyCache.setId(UUIDUtil.newUUID());
						keyCache.setCache("1");
						keyCache.setIp(ip);
						keyCache.setPost(post);
						keyCache.setUri(uri);
						mongoService.save(keyCache);
					}
				}
			
		}catch(Exception e){
			logger.warn("获取系统配置参数缓存标识位异常！");
		}
	}
	
	/**
	 * 保存商户信息到mongo中，如果mongo异常，不影响程序执行流程
	 * @param upplatform
	 */
	@SuppressWarnings("all")
	private void saveOrUpdateUPPlatformFromMongo(UPPlatform upplatform){		
		try{			
			IMongoService mongoService = (IMongoService) SpringBUtils.getBean("mongoService");	
			String id = upplatform==null?"":upplatform.getId();
			if(StringUtils.isNotBlank(id)){
				this.deleteUPPlatformFromMongo(upplatform);	
			}
			mongoService.save(upplatform);
			this.setCacheParam();//清除缓存
		}catch(Exception e){
			logger.warn("保存或修改mongoDB商户信息时异常！", e);
		}
	}
	
	/**
	 * 从mongo中删除商户信息，如果mongo异常，不影响程序执行流程
	 * @param upplatform
	 */
	@SuppressWarnings("all")
	private void deleteUPPlatformFromMongo(UPPlatform upplatform){
		try{
			IMongoService mongoService = (IMongoService) SpringBUtils.getBean("mongoService");
			Query<UPPlatform> query = mongoService.getQuery(UPPlatform.class);			
			String id = upplatform==null?"":upplatform.getId();
			if(StringUtils.isNotBlank(id)){
				List<UPPlatform> list = mongoService.query(UPPlatform.class, query);
				if(list!=null && list.size()>0){
					upplatform = list.get(0);
					mongoService.delete(upplatform.getClass(), id);
				}
			}		
		}catch(Exception e){
			logger.warn("删除mongoDB商户信息时异常！",e);
		}
	}
	
	@Override
	public List<UPPlatform> queryMerchantByParam(DynamicSqlParameter requestParam) throws UPPException {
		List<UPPlatform> list = new ArrayList<UPPlatform>();
		try {
			UPPlatformExampleExtended scEE = new UPPlatformExampleExtended();
			Converter.paramToExampleExtended(requestParam, scEE);
			list = getManager().queryRegisterPlatform(scEE);
		} catch (Exception e) {
			logger.error("查询商户集合异常", e);
			throw new UPPException("查询商户集合异常");
		}
		return list;
	}
	@Override
	public boolean checkExist(UPPlatform model) throws UPPException {
		UPPlatform upplatform = null;
		try {
			upplatform = getManager().queryUPPlatformBystoryName(model);
			if(upplatform==null){
				return true;
			}
		} catch (Exception e) {
			logger.error("查询此商户名称是否存在异常", e);
		}
		return false;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public PaginationResult addMerchant(UPPlatform model,String userName) throws UPPException {
	
		PaginationResult result = new PaginationResult();
		try {
			
			model.setOpUser(userName);
			//添加注册时间
			model.setRegTime(new Date().getTime());
			
			//加密密钥
			KeyBean keybean = new KeyBean();
			keybean.setPublicKey(model.getPublicKey());
			keybean.setPrivateKey(model.getPrivateKey());
			keybean = EncryptKeyUtils.encryptKeys(keybean, DefaultConfig.getValue("UPP_KEY"));
			model.setPublicKey(keybean.getPublicKey());
			model.setPrivateKey(keybean.getPrivateKey());
			
			model = getManager().register(model);
			
			//保存到mongo中
			this.saveOrUpdateUPPlatformFromMongo(model);
			
			result.setMessage(Converter.OP_SUCCESS);
			
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("添加商户信息失败",e);
		}
		return result;
	}
	@Override
	public PaginationResult<UPPlatform> queryMerchantAll(DynamicSqlParameter requestParam) throws UPPException {
		UPPlatformExampleExtended uppEE = new UPPlatformExampleExtended();
		try {
			Converter.paramToExampleExtended(requestParam, uppEE);
			result = getManager().queryRegPlatformByPage(uppEE);
		} catch (Exception e) {
			logger.error("分页查询商户信息失败",e);
			throw new UPPException("分页查询商户信息失败",e);
		}
		return result;
	}
	@Override
	public PaginationResult<UPPlatform> delMerchant(String ids) throws UPPException {
		try {
			getManager().removeRegisterPlatform(ids);
			
			//保存到mongo中
			UPPlatform upplatform= new UPPlatform();
			upplatform.setId(ids);
			this.deleteUPPlatformFromMongo(upplatform);
			
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("删除商户信息失败",e);
		}
		return result;
	}
	@Override
	public UPPlatform getMerchantById(String id) throws UPPException {
		UPPlatform upp = null;
		try {
			upp = getManager().getRegPlatformById(id);
			
			//解密密钥
			KeyBean keybean = new KeyBean();
			keybean.setPublicKey(upp.getPublicKey());
			keybean.setPrivateKey(upp.getPrivateKey());
			keybean = EncryptKeyUtils.decryptKeys(keybean, DefaultConfig.getValue("UPP_KEY"));
			upp.setPublicKey(keybean.getPublicKey());
			upp.setPrivateKey(keybean.getPrivateKey());
			
		} catch (Exception e) {
			logger.error("通过ID查询商户信息异常",e);
			throw new UPPException("通过ID查询商户信息异常",e);
		}
		return upp;
	}
	
	@Override
	public void initKeys() throws UPPException {
		
		try {
			UPPlatformExampleExtended uppEE = new UPPlatformExampleExtended();
			List<UPPlatform> list = getManager().queryRegisterPlatform(uppEE);
			UPPlatform tem = null;
			for(UPPlatform uf : list){
				tem = new UPPlatform();
				tem.setId(uf.getId());
				tem.setPublicKey(uf.getPublicKey());
				tem.setPrivateKey(uf.getPrivateKey());
				this.editMerchant(uf);
			}
			
		} catch (Exception e) {
			logger.error("初始化商户密钥异常",e);
			throw new UPPException("初始化商户密钥异常",e);
		}
	}
	
	
	@Override
	public void editMerchant(UPPlatform model) throws UPPException {
		try {			
			//加密密钥
			KeyBean keybean = new KeyBean();
			keybean.setPublicKey(model.getPublicKey());
			keybean.setPrivateKey(model.getPrivateKey());
			keybean = EncryptKeyUtils.encryptKeys(keybean, DefaultConfig.getValue("UPP_KEY"));
			model.setPublicKey(keybean.getPublicKey());
			model.setPrivateKey(keybean.getPrivateKey());
			
			getManager().modifyRegisterPlatform(model);
			
			this.saveOrUpdateUPPlatformFromMongo(model);
			
		} catch (Exception e) {
			logger.error("修改商户信息异常",e);
			throw new UPPException("修改商户信息异常",e);
		}
		
	}
	@Override
	public PaginationResult<UPPlatform> modifyStatusMerchant(UPPlatform model) throws UPPException {
		try {
			getManager().modifyRegisterPlatform(model);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("修改商户状态异常",e);
			throw new UPPException("修改商户状态异常",e);
		}
		return result;
	}
	@Override
	public List<MerchantExcel> exportById(String id) throws UPPException {
	    List<MerchantExcel> result = new ArrayList<MerchantExcel>();
	    MerchantExcel mer = new MerchantExcel();
	    UPPlatform upp = new UPPlatform();
	    upp = getManager().getRegPlatformById(id);
	    //赋值
	    mer.setContactPhone(upp.getContactPhone());
	    mer.setContactUser(upp.getContactUser());
	    mer.setOpUser(upp.getOpUser());
	    mer.setRegTime(upp.getRegTime().toString());
	    mer.setStoreCode(upp.getStoreCode());
	    mer.setStoreName(upp.getStoreName());
	    mer.setStoreStatus(upp.getStoreStatus());
	    mer.setStoreType(upp.getStoreType());
/*	    ArrayList<Runnable> tasks = new ArrayList<Runnable>();
	    Runnable task = new Runnable() {
			
			@Override
			public void run() {
				result.add(mer);
				
			}
		};
		tasks.add(task);
		TaskPool.completeTask(tasks);*/
	    result.add(mer);
	    return result;
	}
	@Override
	public List<MerchantExcel> exportAll(DynamicSqlParameter requestParam) throws UPPException {
		List<UPPlatform> list = new ArrayList<UPPlatform>();
		List<MerchantExcel> excelList = new ArrayList<MerchantExcel>(); 
		list = queryMerchantByParam(requestParam);
		excelList = upplatformToMerchantExcel(list);
		return excelList;
	}
	private List<MerchantExcel> upplatformToMerchantExcel(List<UPPlatform> list){
		List<MerchantExcel> excelList = new ArrayList<MerchantExcel>();
		if(list.size()>=1){
		for(int i=0;i<list.size();i++){
			UPPlatform upp = new UPPlatform();
			MerchantExcel mer = new MerchantExcel();
			upp = list.get(i);
			mer.setContactPhone(upp.getContactPhone());
		    mer.setContactUser(upp.getContactUser());
		    mer.setOpUser(upp.getOpUser());
		    String regTime = DateUtil.longToDate(upp.getRegTime(), "yyyy-MM-dd HH:mm:ss");
		    mer.setRegTime(regTime);
		    mer.setStoreCode(upp.getStoreCode());
		    mer.setStoreName(upp.getStoreName());
		    mer.setStoreStatus(upp.getStoreStatus());
		    mer.setStoreType(upp.getStoreType());
		    excelList.add(mer);
		}
		}
		return excelList;
	}
	
}
