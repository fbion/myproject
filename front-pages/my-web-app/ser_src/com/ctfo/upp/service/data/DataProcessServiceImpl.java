package com.ctfo.upp.service.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.base.intf.external.IExternalSystem;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.PayOrderSubjectDict;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.service.AbstractService;
import com.ctfo.upp.service.login.ILoginService;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.base.external.uaa.User;
import com.sinoiov.base.external.uaa.UserExt;
import com.sinoiov.upp.business.account.IAccountBusiness;
import com.sinoiov.upp.business.payment.IOrderBusiness;
import com.sinoiov.upp.business.payment.IPaymentTradeOfflineBusiness;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.service.dto.ApplyDto;
import com.sinoiov.upp.service.dto.OrderDto;

@Service("dataProcessService")
public class DataProcessServiceImpl extends AbstractService implements IDataProcessService{

	private static Log logger = LogFactory.getLog(DataProcessServiceImpl.class);

	private Map<String, String> ownerUserNoMap = new HashMap<String,String>();

	private Map<String, String> ownerUserPostMap = new HashMap<String,String>();

	@Resource(name = "loginService", description = "service登录接口")
	private ILoginService loginService;

	private IAccountBusiness accountBusiness;

	private IOrderBusiness orderBusiness;

	private IPaymentTradeOfflineBusiness ptoBusiness;

	private IExternalSystem externalSystem;

	private DataProcessServiceImpl(){	
		accountBusiness = (IAccountBusiness) ServiceFactory.getFactory().getService(IAccountBusiness.class);
		orderBusiness = (IOrderBusiness) ServiceFactory.getFactory().getService(IOrderBusiness.class);
		ptoBusiness = (IPaymentTradeOfflineBusiness) ServiceFactory.getFactory().getService(IPaymentTradeOfflineBusiness.class);
		externalSystem = (IExternalSystem) ServiceFactory.getFactory().getService(IExternalSystem.class);
	}

	/**
	 * 根据账号和用户ID获取用户编号
	 * @param accountNo
	 * @param ownerUserId
	 * @return
	 * @throws Exception
	 */
	private String getOwnerUserNo(String accountNo, String ownerUserId){
		String ownerUserNo = "";
		try{
			ownerUserNo = ownerUserNoMap.get(accountNo);
			if(StringUtils.isBlank(ownerUserNo)){
				if(StringUtils.isNotBlank(accountNo)){
					AccountDto dto = accountBusiness.getAccountByAccountNo(accountNo);
					if(StringUtils.isNotBlank(dto.getOwnerUserNo())){
						ownerUserNoMap.put(accountNo, dto.getOwnerUserNo());
						return dto.getOwnerUserNo();
					}
					ownerUserId = dto.getOwnerUserId();
				}
				
				if(StringUtils.isNotBlank(ownerUserId)){
					UserExt userExt = externalSystem.getOwnerUserExtByUserId(ownerUserId);
					ownerUserNo = userExt.getOwnerUserNo();
					if(StringUtils.isNotBlank(ownerUserNo) && StringUtils.isNotBlank(accountNo)){
						ownerUserNoMap.put(accountNo, ownerUserNo);
					}
				}
				
			}
		}catch(Exception e){
			logger.error("获取用户编号异常", e);
		}
		return ownerUserNo;
	}
	/**
	 * 根据账号和用户ID获取用户编号
	 * @param accountNo
	 * @param ownerUserId
	 * @return
	 * @throws Exception
	 */
	private String getOwnerUserPost(String applyName, String ownerUserId){
		String post = "";
		try{
			post = ownerUserPostMap.get(ownerUserId);
			if(StringUtils.isBlank(post)){
				if(StringUtils.isBlank(ownerUserId) && StringUtils.isNotBlank(applyName)){
					User user = externalSystem.queryUserByUserLogin(applyName);
					ownerUserId = user.getId();
				}
				if(StringUtils.isNotBlank(ownerUserId)){
					UserExt userExt = externalSystem.getOwnerUserExtByUserId(ownerUserId);
					post = userExt==null?"":userExt.getOwnerUserPost();
					if(StringUtils.isNotBlank(post)){
						ownerUserPostMap.put(ownerUserId, post);
					}
				}	
			}
		}catch(Exception e){
			logger.error("获取用户所属地区异常", e);
		}
		return post;
	}

	@Override
	public void handleMemberNoForAccount() {
		try{	
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> equal = new HashMap<String,String>();
			equal.put("accountType", "2");
			equal.put("ownerUserNo", null);
			requestParam.setEqual(equal);
			requestParam.setOrder("createTime");
			requestParam.setSort("DESC");
			int count = accountBusiness.countOfAccount(requestParam);
			int page = 1;
			int rows = Integer.parseInt(DefaultConfig.getValue("HANDLE_OWNER_USER_NO_ROWS"));
			do{
				requestParam.setRows(rows);
				requestParam.setPage(page);	
				List<AccountDto> list = accountBusiness.queryAccountList(requestParam);
				String ownerUserNo = "";
				List<AccountDto> newList = new ArrayList<AccountDto>();
				for(AccountDto dto : list){
					if(StringUtils.isBlank(dto.getOwnerUserNo())){
						ownerUserNo = this.getOwnerUserNo(dto.getAccountNo(), dto.getOwnerUserId());
						if(StringUtils.isNotBlank(ownerUserNo)){
							dto.setOwnerUserNo(ownerUserNo);
							newList.add(dto);
						}	
					}
				}
				if(newList!=null && newList.size()>0)
					accountBusiness.handleAccountData(newList);	
				page = (count - page * rows) >0?page+1:0;
				Thread.sleep(5000);//暂停一段时间
			}while(page > 0);

			logger.warn("======处理账户旧数据成功！=========");

		} catch (Exception e) {
			logger.error("处理会员编号数据异常", e);
		}

	}

	@Override
	public void handleMemberNoForOrderInfo() {
		try{	
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> equal = new HashMap<String,String>();
			equal.put("ownerUserNo", null);
			requestParam.setEqual(equal);
			requestParam.setOrder("createTime");
			requestParam.setSort("DESC");
			int count = orderBusiness.getOrderCount(requestParam);
			int page = 1;
			int rows = Integer.parseInt(DefaultConfig.getValue("HANDLE_OWNER_USER_NO_ROWS"));
			do{
				requestParam.setRows(rows);
				requestParam.setPage(page);	
				List<OrderDto> list = orderBusiness.queryOrderInfo(requestParam);
				String ownerUserNo = "";
				List<OrderDto> newList = new ArrayList<OrderDto>();
				for(OrderDto dto : list){
					if(StringUtils.isBlank(dto.getOwnerUserNo())){
						ownerUserNo = this.getOwnerUserNo(dto.getAccountNo(),"");
						if(StringUtils.isNotBlank(ownerUserNo)){
							dto.setOwnerUserNo(ownerUserNo);
							newList.add(dto);
						}	
					}
				}
				if(newList!=null && newList.size()>0)
					orderBusiness.handleOrderData(newList);	
				page = (count - page * rows) >0?page+1:0;
				Thread.sleep(5000);//暂停一段时间
			}while(page > 0);

			logger.warn("======处理订单旧数据成功！=========");

		} catch (Exception e) {
			logger.error("处理会员编号数据异常", e);
		}


	}

	@Override
	public void handleMemberNoForPaymentTradeOffline() {
		try{	
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> equal = new HashMap<String,String>();
			equal.put("ownerUserNo", null);
			requestParam.setEqual(equal);
			requestParam.setOrder("createTime");
			requestParam.setSort("DESC");
			int count = orderBusiness.getOrderCount(requestParam);
			int page = 1;
			int rows = Integer.parseInt(DefaultConfig.getValue("HANDLE_OWNER_USER_NO_ROWS"));
			do{
				requestParam.setRows(rows);
				requestParam.setPage(page);	
				List<ApplyDto> list = ptoBusiness.queryApply(requestParam);
				String ownerUserNo = "";
				String accountNo = "";
				String applyName = "";
				String applyPersonPost = "";
				List<ApplyDto> newList = new ArrayList<ApplyDto>();
				boolean is = false;
				for(ApplyDto dto : list){
					if(StringUtils.isBlank(dto.getOwnerUserNo())){
						accountNo = dto.getInsideAccountNo();
						if(accountNo.indexOf("MA")<0 && dto.getStoreId().indexOf("MA")>-1){
							accountNo = dto.getStoreId();
						}
						ownerUserNo = this.getOwnerUserNo(accountNo,"");
						if(StringUtils.isNotBlank(ownerUserNo)){
							dto.setOwnerUserNo(ownerUserNo);
							is = true;
						}
					}
					//所属地区
					if(StringUtils.isBlank(dto.getApplyPersonPost())){
						applyName = dto.getApplyName();
						applyPersonPost = this.getOwnerUserPost(applyName, "");
						if(StringUtils.isNotBlank(applyPersonPost)){
							dto.setApplyPersonPost(applyPersonPost);
							is = true;
						}
					}
					if(is)
						newList.add(dto);
				}

				if(newList!=null && newList.size()>0)
					ptoBusiness.handleApplyData(newList);
				page = (count - page * rows) >0?page+1:0;
				Thread.sleep(5000);//暂停一段时间
			}while(page > 0);

			logger.warn("======处理申请旧数据成功！=========");

		} catch (Exception e) {
			logger.error("处理会员编号数据异常", e);
		}
	}

	@Override
	public String countBatchRechargeToUserAccount(){
		try{
			List<OrderDto> result = new ArrayList<OrderDto>();
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> equal = new HashMap<String,String>();
			equal.put("orderStatus", PayDict.PAY_ORDER_STATUS_INIT);
			equal.put("orderSubject", PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_BATCH_RECHARGE);
			equal.put("productCatalog", PayDict.PAY_ORDER_PRODUCT＿CATALOG_FL);
			
			requestParam.setEqual(equal);
			requestParam.setOrder("createTime");
			requestParam.setSort("DESC");
			int count = orderBusiness.getOrderCount(requestParam);
			
			String[] arrs = StringUtils.isNotBlank(DefaultConfig.getValue("HANDLE_ORDERINFO_NOT_ROWS"))?DefaultConfig.getValue("HANDLE_ORDERINFO_NOT_ROWS").split(","):null;
		
			return arrs!=null?(count-arrs.length)+"":count+"";
			
		} catch (Exception e) {
			logger.error("查询油卡返利数据异常", e);
		}
		return null;
	}
	
	@Override
	public void handleOrderInfoBatchRechargeToUserAccount(){
		try{
			
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> equal = new HashMap<String,String>();
			equal.put("orderStatus", PayDict.PAY_ORDER_STATUS_INIT);
			equal.put("orderSubject", PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_BATCH_RECHARGE);
			equal.put("productCatalog", PayDict.PAY_ORDER_PRODUCT＿CATALOG_FL);
			
			requestParam.setEqual(equal);
			requestParam.setOrder("createTime");
			requestParam.setSort("DESC");
			int count = orderBusiness.getOrderCount(requestParam);
			int page = 1;
			int rows = Integer.parseInt(DefaultConfig.getValue("HANDLE_OWNER_USER_NO_ROWS"));
			String[] arrs = StringUtils.isNotBlank(DefaultConfig.getValue("HANDLE_ORDERINFO_NOT_ROWS"))?DefaultConfig.getValue("HANDLE_ORDERINFO_NOT_ROWS").split(","):null;
			do{
				requestParam.setRows(rows);
				requestParam.setPage(page);	
				List<OrderDto> list = orderBusiness.queryOrderInfo(requestParam);
				List<OrderDto> newlist = new ArrayList<OrderDto>();
				for(OrderDto dto : list){
					if(arrs!=null && arrs.length>0){
						for(String tem : arrs){
							if(tem.equals(dto.getOrderNo())){
								dto = null;
								break;
							}
						}
					}
					if(dto!=null){
						newlist.add(dto);
					}
				}
				if(newlist!=null && newlist.size()>0){
					orderBusiness.handleOrderInfoBatchRechargeToUserAccount(newlist);	
				}	
				page = (count - page * rows) >0?page+1:0;
				Thread.sleep(5000);//暂停一段时间
			}while(page > 0);

			logger.warn("======处理油卡返利数据成功！=========");

		} catch (Exception e) {
			logger.error("处理油卡返利数据异常", e);
		}
	}




}
