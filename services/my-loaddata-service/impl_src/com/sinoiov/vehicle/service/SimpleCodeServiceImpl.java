package com.sinoiov.vehicle.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.ctfo.vims.boss.beans.SystemCode;
import com.ctfo.vims.boss.beans.SystemCodeExampleExtended;
import com.ctfo.vims.common.utils.SpringBUtils;
import com.ctfo.vims.datacenter.interfaces.manager.IVimsGenericManager;
import com.sinoiov.vehicle.ISimpleCodeService;
import com.sinoiov.vehicle.dao.ImageDAOImpl;
import com.sinoiov.vehicle.dao.LoadTimeDAOImpl;
import com.sinoiov.vehicle.dao.SimpleCodeDAO;
import com.sinoiov.vehicle.dao.SimpleCodeDAOImpl;
import com.sinoiov.vehicle.dao.VehicleDAOImpl;
import com.sinoiov.vehicle.dao.beans.Image;
import com.sinoiov.vehicle.dao.beans.ImageExample;
import com.sinoiov.vehicle.dao.beans.LoadTime;
import com.sinoiov.vehicle.dao.beans.LoadTimeExample;
import com.sinoiov.vehicle.dao.beans.SimpleCode;
import com.sinoiov.vehicle.dao.beans.Vehicle;
import com.sinoiov.vehicle.dao.beans.VehicleExample;

@Service("simpleCodeService")
public class SimpleCodeServiceImpl extends AbstractService implements ISimpleCodeService {
	
	private static Log logger = LogFactory.getLog(SimpleCodeServiceImpl.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	@Qualifier("IVimsGenericManager")
	private IVimsGenericManager iVimsGenericManager;
	
	//@Autowired
	private SimpleCodeDAO simpleCodeDAO;
	
	
	@Override
	public void loadVihecleData(String dataType, String start) throws Exception {
		logger.info("========开始下载车辆数据==dataType【"+dataType+"】start【"+start+"】==========");
		if("SystemCode".equals(dataType)){
			this.loadSystemCodeData();
		}else if("Vehicle".equals(dataType)){
			long loadStart = 0;
			long loadEnd = 0;			
			if(StringUtils.isBlank(start)){
				loadStart = sdf.parse("2016-01-02 00:00:00").getTime();
				//loadStart = this.getLastLoadDataTime(dataType);
				do{
					loadStart = loadEnd==0?loadStart:loadEnd;
					loadEnd = loadStart + (1000*60*60*24);
					this.loadVehicleData(loadStart, loadEnd);
					Thread.sleep(1000);
					
				}while(loadEnd <= new Date().getTime());	
			}else{
				loadStart = sdf.parse(start).getTime();
				loadEnd = loadStart + (1000*60*60*24);
				this.loadVehicleData(loadStart, loadEnd);
			}
			
		}else if("Vehicle2".equals(dataType)){
			long loadStart = 0;
			long loadEnd = 0;			
			loadStart = sdf.parse("2015-12-24 00:00:00").getTime();
			//loadStart = this.getLastLoadDataTime(dataType);
			do{
				loadStart = loadEnd==0?loadStart:loadEnd;
				loadEnd = loadStart + (1000*60*60*24);
				this.loadVehicleData2(loadStart, loadEnd);
				Thread.sleep(1000);		
			}while(loadEnd <= new Date().getTime());
		}else if("VehicleImage".equals(dataType)){
			int loadCount = this.getLastLoadCount();
			do{
				//loadCount = loadCount==0?0:this.getLastLoadCount();
				this.loadVehicleImages(loadCount);
				Thread.sleep(1000);		
			}while(loadCount <= 5000000);
			
		}else if("VehicleAuthentication".equals(dataType)){
			
		}else if("VehicleLabel".equals(dataType)){
			
		}else if("VehicleLine".equals(dataType)){
			
		}else if("ZoneCode".equals(dataType)){
			
		}else if("VehicleLastTrack".equals(dataType)){
			
		}
		
	}
		
/////////////////////vehicle images start////////////////////////////
	
	private int getLastLoadCount(){
		int count = 0;
		try{
			
			ImageExample ie = new ImageExample();
			count = getMyImageDao().countByExample(ie);
			
		}catch(Exception e){
			logger.error("获取下载图片最后数量异常",e);
		}
		return count;
	}
	
	
	private void loadVehicleImages(int startCount){
		int count = 0;
		int success = 0;
		int fail = 0;
		Date startDate = new Date();
		try{
			com.ctfo.vims.boss.beans.ImageExampleExtended viEE = new com.ctfo.vims.boss.beans.ImageExampleExtended();
			count = iVimsGenericManager.countModels(viEE);
			if(count>0){
				int page = 1;
				int rows = 500;
				int pagesize = count/500+1;
				int skipnum = startCount;
				int endnum = 0;
				List<com.ctfo.vims.boss.beans.Image> list = null;
				Image image = null;
				do{
					skipnum = endnum==0?skipnum:endnum;
					endnum = skipnum+rows;
					viEE.setSkipNum(skipnum);
					viEE.setEndNum(endnum);
					list = iVimsGenericManager.getModels(viEE);
					endnum --;
					for(com.ctfo.vims.boss.beans.Image remoImage : list){
						try{
							image = new Image();
							image.setId(remoImage.getId());
							image.setAttachMiniUrl(remoImage.getAttachMiniUrl());
							image.setAttachSourceUrl(remoImage.getAttachSourceUrl());
							image.setAttachTitle(remoImage.getAttachTitle());
							image.setAttachType(remoImage.getAttachType());
							image.setAttachUrl(remoImage.getAttachUrl());
							image.setOwnerId(remoImage.getOwnerId());
							image.setOwnerType(remoImage.getOwnerType());
							image.setStatus(remoImage.getStatus());
							image.setImagesCleanStatus(remoImage.getImagesCleanStatus());
							image.setCreateTime(remoImage.getCreateTime());
							image.setUpdateTime(remoImage.getUpdateTime());
						}catch(Exception e){
							fail++;
							logger.error("转化图片数据异常", e);
						}
						
						if(StringUtils.isNotBlank(image.getId()) && this.saveOrUpdateImage(image)){
							success++;	
						}else{
							fail++;
							logger.info("=======image【"+remoImage.getAttachSourceUrl()+"】保存或更新失败");
						}
					}
				
					logger.info("=======500条数据分隔线====成功【"+(success+startCount)+"】，还有【"+(count-startCount-success)+"】=========");
					Thread.sleep(1000);
				}while((startCount + success + fail)<count);
				
				logger.info("========本次下载图片数据【"+count+"】成功【"+success+"】 失败【"+fail+"】==========");
				
			}
			
			this.setLastLoadDataTime("Images", startDate, new Date(), count, success, fail);
			
		}catch(Exception e){
			logger.error("下载车辆图片数据异常", e);
		}
	}
	
	private boolean saveOrUpdateImage(Image record){
		try{
			ImageExample ie = new ImageExample();
			ie.createCriteria().andIdEqualTo(record.getId());
			int count = getMyImageDao().countByExample(ie);
			if(count>0){
				getMyImageDao().updateByPrimaryKeySelective(record);
			}else{
				getMyImageDao().insert(record);
			}
		}catch(Exception e){
			logger.error("保存或更新车辆图片数据异常", e);
			return false;
		}	
		return true;
	}
	
/////////////////////vehicle images end////////////////////////////
/////////////////////VehicleAuthentication start////////////////////////////
	
	
/////////////////////VehicleAuthentication end////////////////////////////
/////////////////////VehicleLabel start////////////////////////////
	
	
/////////////////////VehicleLabel end////////////////////////////
/////////////////////VehicleLine start////////////////////////////
	
	
/////////////////////VehicleLine end////////////////////////////
/////////////////////ZoneCode start////////////////////////////
	
	
/////////////////////ZoneCode end////////////////////////////
/////////////////////VehicleLastTrack start////////////////////////////
	
	
/////////////////////VehicleLastTrack end////////////////////////////
/////////////////////vehicle start////////////////////////////
	private long getLastLoadDataTime(String dataType)throws Exception{
		long lastLoadDataTime = 0;
		LoadTimeExample example = new LoadTimeExample();
		example.createCriteria().andDataTypeEqualTo(dataType);
		example.setOrderByClause(" LAST_TIME DESC ");
		List<LoadTime> loadTimeList = this.getMyLoadTimeDAOImpl().selectByExample(example);
		if(loadTimeList!=null && loadTimeList.size()>0){
			LoadTime loadTime = loadTimeList.get(0);
			lastLoadDataTime = loadTime.getLastTime().getTime();
		}
		return lastLoadDataTime>0?lastLoadDataTime:sdf.parse("2012-01-01 00:00:00").getTime();	
	}
	private void setLastLoadDataTime(String dataType, Date start, Date end, int loadCount, int loadSuccessCount, int fail)throws Exception{
		LoadTimeExample example = new LoadTimeExample();
		example.createCriteria().andDataTypeEqualTo(dataType).andLastTimeEqualTo(end);
		List<LoadTime> loadTimeList = this.getMyLoadTimeDAOImpl().selectByExample(example);
		if(loadTimeList!=null && loadTimeList.size()==1){
			LoadTime loadTime = loadTimeList.get(0);
			loadTime.setFirstTime(start);
			loadTime.setLastTime(end);
			loadTime.setLoadCount(loadCount);
			loadTime.setLoadSuccessCount(loadSuccessCount);
			loadTime.setLoadFailCount(fail);
			this.getMyLoadTimeDAOImpl().updateByPrimaryKey(loadTime);
		}else{
			LoadTime loadTime = new LoadTime();
			loadTime.setDataType(dataType);
			loadTime.setFirstTime(start);
			loadTime.setLastTime(end);
			loadTime.setLoadCount(loadCount);
			loadTime.setLoadSuccessCount(loadSuccessCount);
			loadTime.setLoadFailCount(fail);
			this.getMyLoadTimeDAOImpl().insert(loadTime);
		}
	}
	
	private int loadVehicleData(long start, long end){
		int count = 0;
		int success = 0;
		int fail = 0;
		try{
			com.ctfo.vims.boss.beans.VehicleExampleExtended remoVEED = new com.ctfo.vims.boss.beans.VehicleExampleExtended();
			com.ctfo.vims.boss.beans.VehicleExampleExtended.Criteria remoVEEDCriteria = remoVEED.createCriteria();
			remoVEEDCriteria.andUpdateTimeBetween(start, end);
			count = iVimsGenericManager.countModels(remoVEED);
			logger.info("========本次预计下载车辆数据【"+count+"】start【"+sdf.format(new Date(start))+"】end【"+sdf.format(new Date(end))+"】==========");
			if(count>0){
				int page = 1;
				int rows = 500;
				int pagesize = count/500+1;
				int skipnum = 0;
				int endnum = 0;
				
				List<com.ctfo.vims.boss.beans.Vehicle> remoList = null;
				Vehicle myvehicle = null;
				do{			
					skipnum = page==1?(page-1) * rows : (page-1) * rows - page;
					endnum = skipnum + rows;
					remoVEED.setSkipNum(skipnum);
					remoVEED.setEndNum(endnum);
					
					remoList = iVimsGenericManager.getModels(remoVEED);
					for(com.ctfo.vims.boss.beans.Vehicle rv : remoList){
						if(rv !=null && StringUtils.isNotBlank(rv.getVehiclePlateNo())){
							myvehicle = this.vehicleRemoToLocal(rv);
							if(myvehicle!=null){
								if(StringUtils.isBlank(myvehicle.getVehiclePlateColor())){
									myvehicle.setVehiclePlateColor("1");
								}
								if(this.saveOrUpdateVehicle(myvehicle)){
									success++;
								}else{
									fail++;
									logger.info("=======车辆牌【"+rv.getVehiclePlateNo()+"】的车辆保存或更新失败");
								}
									
							}else{
								fail++;
								logger.info("=======车辆牌【"+rv.getVehiclePlateNo()+"】转换对象后为null");
							}
						}else{
							fail++;
							logger.info("=======车辆【"+(rv==null?"对象为null":rv.getVehiclePlateNo())+"】");
						}
					}
					
					page++;
					logger.info("=======500条数据分隔线====成功【"+success+"】，还有【"+(count-success)+"】=========");
					Thread.sleep(1000);
					
				}while(page <= pagesize);
				
				logger.info("========本次下载车辆数据【"+count+"】成功【"+success+"】 失败【"+fail+"】start【"+sdf.format(new Date(start))+"】end【"+sdf.format(new Date(end))+"】==========");
				
			}
			
			this.setLastLoadDataTime("Vehicle", new Date(start), new Date(end), count, success, fail);
			
		}catch(Exception e){
			logger.error("下载车辆数据异常", e);
		}
		return count;
	}
	
	private void getLoadList(List<String> remo, List<String> db, List<String> loadKeysList){
		for(String remoKey : remo){
			boolean is = false;
			for(String dbKey : db){
				if(remoKey.equals(dbKey)){
					is = true;
					break;
				}
			}
			if(!is){
				loadKeysList.add(remoKey);
			}
		}
	}
	
	
	private int loadVehicleData2(long start, long end){
		int count = 0;
		int success = 0;
		int loadcount = 0;
		int fail = 0;
		try{
			com.ctfo.vims.boss.beans.VehicleExampleExtended remoVEED = new com.ctfo.vims.boss.beans.VehicleExampleExtended();
			com.ctfo.vims.boss.beans.VehicleExampleExtended.Criteria remoVEEDCriteria = remoVEED.createCriteria();
			remoVEEDCriteria.andUpdateTimeBetween(start, end);
			count = iVimsGenericManager.countModels(remoVEED);
			logger.info("========本次预计下载车辆数据【"+count+"】start【"+sdf.format(new Date(start))+"】end【"+sdf.format(new Date(end))+"】==========");
			if(count>0){
				int page = 1;
				int rows = 3000;
				int pagesize = count/3000+1;
				int skipnum = 0;
				int endnum = 0;
				
				List<com.ctfo.vims.boss.beans.Vehicle> remoList = null;
				Vehicle myvehicle = null;
				List<String> remoKeysList = null;
				List<String> myKeysList = null;
				List<String> loadKeysList = new ArrayList<String>();	
				List<String> temloadKeysList = new ArrayList<String>();	
				
				VehicleExample myve = new VehicleExample();
				myve.createCriteria().andUpdateTimeBetween(new Date(start), new Date(end));
				myKeysList = this.getMyVehicleDao().getKeyBy(myve);
				int tem = 0;
				do{			
					skipnum = page==1?(page-1) * rows : (page-1) * rows - page;
					endnum = skipnum + rows;
					remoVEED.setSkipNum(skipnum);
					remoVEED.setEndNum(endnum);
					remoKeysList = iVimsGenericManager.getKeys(remoVEED);
					
					this.getLoadList(remoKeysList, myKeysList, loadKeysList);
					tem += remoKeysList.size();
					page++;
					logger.info("=======3000条数据分隔线====已处理【"+tem+"】，还有【"+(count-tem)+"】=========");
					Thread.sleep(1000);
					
				}while(page <= pagesize);
				
				if(loadKeysList.size()>0){
					loadcount = loadKeysList.size();
					success = 0;
					int myttt = loadcount;
					logger.info("=======本次需要下载车辆【"+loadcount+"】=========");
					remoVEED = new com.ctfo.vims.boss.beans.VehicleExampleExtended();
					int ii = loadcount/1000+1;
					int j=0;
					do{
						temloadKeysList = new ArrayList<String>();
						int kk = myttt>1000?1000:myttt;
						if(kk>0){
							for(int i=0;i<kk;i++){
								temloadKeysList.add(loadKeysList.get(j+i));
							}
							//temloadKeysList.addAll( kk-1, loadKeysList);
							myttt -= kk;
							j+=kk;
						}	
						remoVEED.createCriteria().andIdIn(temloadKeysList);
						remoList = iVimsGenericManager.getModels(remoVEED);
						
						for(com.ctfo.vims.boss.beans.Vehicle rv : remoList){
							if(rv !=null && StringUtils.isNotBlank(rv.getVehiclePlateNo())){
								myvehicle = this.vehicleRemoToLocal(rv);
								if(myvehicle!=null){
									if(StringUtils.isBlank(myvehicle.getVehiclePlateColor())){
										myvehicle.setVehiclePlateColor("1");
									}
									if(this.saveOrUpdateVehicle(myvehicle)){
										success++;
									}else{
										fail++;
										logger.info("=======车辆牌【"+rv.getVehiclePlateNo()+"】的车辆保存或更新失败");
									}
									
								}else{
									fail++;
									logger.info("=======车辆牌【"+rv.getVehiclePlateNo()+"】转换对象后为null");
								}
							}else{
								fail++;
								logger.info("=======车辆【"+(rv==null?"对象为null":rv.getVehiclePlateNo())+"】");
							}
						}
						ii--;
					}while(ii>0);	
				}
				
				logger.info("========本次下载车辆数据【"+loadcount+"】成功【"+success+"】 失败【"+fail+"】start【"+sdf.format(new Date(start))+"】end【"+sdf.format(new Date(end))+"】==========");
				
			}
			
			this.setLastLoadDataTime("Vehicle2", new Date(start), new Date(end), loadcount, success, fail);
			
		}catch(Exception e){
			logger.error("下载车辆数据异常", e);
		}
		return count;
	}
	
	private boolean saveOrUpdateVehicle(Vehicle myvehicle){
		try{
			VehicleExample example = new VehicleExample();
			example.createCriteria().andVehiclePlateNoEqualTo(myvehicle.getVehiclePlateNo()).andVehiclePlateColorEqualTo(myvehicle.getVehiclePlateColor());
			int count = this.getMyVehicleDao().countByExample(example);
			if(count==0){
				this.getMyVehicleDao().insert(myvehicle);
			}else{
				this.getMyVehicleDao().updateByPrimaryKey(myvehicle);
			}	
			
		}catch(Exception e){
			logger.error("保存或更新车辆数据时异常！", e);
			return false;
		}
		return true;
	}
	
	private Vehicle vehicleRemoToLocal(com.ctfo.vims.boss.beans.Vehicle rv)throws Exception{
		if(rv !=null){
			Vehicle db = new Vehicle();
			String[] ignoreProperties = {
					"roadTransportBegin",       
					"roadTransportEnd",         
					"drivePermitBegin",         
					"drivePermitEnd",           
					"insuranceStartTime",       
					"insuranceEndTime",         
					"registrationTime",          
					"power",                      
					"registrationStartTime",    
					"hostlingTon",               
					"vehicleTon",                         
					"loadTon",                  
					"vehicleDrawTon",          
					"ratifyLoadTon",           
					"volumeOut",                
					"vehicleLength",            
					"vehicleHeight",            
					"vehicleWidth",             
					"volumeIn",                 
					"boxLength",                
					"boxHeight",                
					"boxWidth",                 
					"outFactoryTime",          
					"registrationDate",         
					"purchasingDate",           
					"vehicleValidToDate",     
					"vehicleInspectTime",      
					"semitrailerSaddleTon",    
					"createTime",               
					"updateTime",               
					"driveRegisterTime",       
					"driveStartTime"
					};
			BeanUtils.copyProperties(rv, db, ignoreProperties);
			//String to int
			db.setPower(rv.getPower());
			db.setHostlingTon(toInt(rv.getHostlingTon()));
			db.setVehicleTon(toInt(rv.getVehicleTon()));
			db.setLoadTon(toInt(rv.getLoadTon()));
			db.setVehicleDrawTon(toInt(rv.getVehicleDrawTon()));
			db.setVolumeOut(toInt(rv.getVolumeOut()));
			db.setVehicleHeight(toInt(rv.getVehicleHeight()));
			db.setVehicleWidth(toInt(rv.getVehicleWidth()));
			db.setVolumeIn(toInt(rv.getVolumeIn()));
			db.setBoxLength(toInt(rv.getBoxLength()));
			db.setBoxHeight(toInt(rv.getBoxHeight()));
			db.setBoxWidth(toInt(rv.getBoxWidth()));
			db.setSemitrailerSaddleTon(toInt(rv.getSemitrailerSaddleTon()));
			//long to int
			db.setVehicleLength(rv.getVehicleLength()==null?0:rv.getVehicleLength().intValue());
			db.setRatifyLoadTon(rv.getRatifyLoadTon()==null?0:rv.getRatifyLoadTon().intValue());
			//long to date
			db.setRoadTransportBegin(rv.getRoadTransportBegin()==null?null:new Date(rv.getRoadTransportBegin()));
			db.setRoadTransportEnd(rv.getRoadTransportEnd()==null?null:new Date(rv.getRoadTransportEnd()));
			db.setDrivePermitBegin(rv.getDrivePermitBegin()==null?null:new Date(rv.getDrivePermitBegin()));
			db.setDrivePermitEnd(rv.getDrivePermitEnd()==null?null:new Date(rv.getDrivePermitEnd()));
			db.setInsuranceStartTime(rv.getInsuranceStartTime()==null?null:new Date(rv.getInsuranceStartTime()));
			db.setInsuranceEndTime(rv.getInsuranceEndTime()==null?null:new Date(rv.getInsuranceEndTime()));
			db.setRegistrationTime(rv.getRegistrationTime()==null?null:new Date(rv.getRegistrationTime()));
			db.setRegistrationStartTime(rv.getRegistrationStartTime()==null?null:new Date(rv.getRegistrationStartTime()));
			db.setOutFactoryTime(rv.getOutFactoryTime()==null?null:new Date(rv.getOutFactoryTime()));
			db.setRegistrationDate(rv.getRegistrationDate()==null?null:new Date(rv.getRegistrationDate()));
			db.setPurchasingDate(rv.getPurchasingDate()==null?null:new Date(rv.getPurchasingDate()));
			db.setVehicleValidToDate(rv.getVehicleValidToDate()==null?null:new Date(rv.getVehicleValidToDate()));
			db.setVehicleInspectTime(rv.getVehicleInspectTime()==null?null:new Date(rv.getVehicleInspectTime()));
			db.setCreateTime(rv.getCreateTime()==null?null:new Date(rv.getCreateTime()));
			db.setUpdateTime(rv.getUpdateTime()==null?null:new Date(rv.getUpdateTime()));
			db.setDriveRegisterTime(rv.getDriveRegisterTime()==null?null:new Date(rv.getDriveRegisterTime()));
			db.setDriveStartTime(rv.getDriveStartTime()==null?null:new Date(rv.getDriveStartTime()));

			return db;
		}
		
		return null;
	}
	
	private int toInt(String str){
		if(StringUtils.isNotBlank(str)){
			str = str.length()>9 ?str.substring(0, 9):str;
			return isNumeric(str)?Integer.parseInt(str.trim()):0;
		}else{
			return 0;
		}
	}
	
	private boolean isNumeric(String str){
		   Pattern pattern = Pattern.compile("[0-9]*");
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false;
		   }
		   return true;
	}	
	
	
/////////////////////vehicle////////////////////////////
	
/////////////////////systemCode////////////////////////////
	private void loadSystemCodeData(){
		try{
			SystemCodeExampleExtended exampleExtended = new SystemCodeExampleExtended();
			int count = iVimsGenericManager.countModels(exampleExtended);
			int page = 1;
			int rows = 500;
			int pagesize = count/500;
			int skipnum = 0;
			int endnum = 0;
			List<SystemCode> list = null;
			SimpleCode mysc = null;
			do{
				skipnum = (page-1) * rows;
				endnum = skipnum + rows;
				exampleExtended.setSkipNum(skipnum);
				exampleExtended.setEndNum(endnum);
				
				list = iVimsGenericManager.getModels(exampleExtended);
				for(SystemCode sc : list){
					mysc = new SimpleCode();
					mysc.setId(sc.getId());
					mysc.setCode(sc.getCode());
					mysc.setLevel("1");
					mysc.setName(sc.getName());
					mysc.setPid(sc.getTypeCode());
					mysc.setRemark(sc.getName());
					mysc.setTypeCode(sc.getTypeCode());
					mysc.setTypeName(sc.getTypeName());
					
					this.getMySimpleCodeDao().insert(mysc);
				}

				count -= 500;
				page++;
				logger.info("=======500条数据分隔线====成功=========");
				Thread.sleep(1000);
				
			}while(page <= pagesize);
			
			logger.info("=======下载码表数据成功【"+count+"】");
			
		}catch(Exception e){
			logger.error("下载码表数据异常", e);
		}
	}
/////////////////////systemCode////////////////////////////
	
	
	private static ImageDAOImpl imagedao = null;
	public ImageDAOImpl getMyImageDao() throws Exception{
	
		if(imagedao == null){
			imagedao =  new ImageDAOImpl();

				Object clientTemplate = getClientTemplate();
				if (imagedao instanceof SqlMapClientDaoSupport) {
					((SqlMapClientDaoSupport) imagedao)
					.setSqlMapClientTemplate((SqlMapClientTemplate) clientTemplate);
				}
		}
		return imagedao;
		
	}
	private static VehicleDAOImpl vehicledao = null;
	public VehicleDAOImpl getMyVehicleDao() throws Exception{
		
		if(vehicledao == null){
			vehicledao =  new VehicleDAOImpl();
			
			Object clientTemplate = getClientTemplate();
			if (vehicledao instanceof SqlMapClientDaoSupport) {
				((SqlMapClientDaoSupport) vehicledao)
				.setSqlMapClientTemplate((SqlMapClientTemplate) clientTemplate);
			}
		}
		return vehicledao;
		
	}
	private static LoadTimeDAOImpl loadTimedao = null;
	public LoadTimeDAOImpl getMyLoadTimeDAOImpl() throws Exception{
		
		if(loadTimedao == null){
			loadTimedao =  new LoadTimeDAOImpl();
			
			Object clientTemplate = getClientTemplate();
			if (loadTimedao instanceof SqlMapClientDaoSupport) {
				((SqlMapClientDaoSupport) loadTimedao)
				.setSqlMapClientTemplate((SqlMapClientTemplate) clientTemplate);
			}
		}
		return loadTimedao;
		
	}
	
	private static SimpleCodeDAOImpl scdao = null;
	public SimpleCodeDAOImpl getMySimpleCodeDao() throws Exception{
		
		if(scdao == null){
			scdao =  new SimpleCodeDAOImpl();
			
			Object clientTemplate = getClientTemplate();
			if (scdao instanceof SqlMapClientDaoSupport) {
				((SqlMapClientDaoSupport) scdao)
				.setSqlMapClientTemplate((SqlMapClientTemplate) clientTemplate);
			}
		}
		return scdao;
		
	}
	
	public static Object getClientTemplate() throws Exception {
		return SpringBUtils.getBean("vimsMysqlSqlClientTemplate");
		//return SpringBUtils.getBean("vimsOracleClientTemplate");
	}
	
	
	
	
	
	
}
