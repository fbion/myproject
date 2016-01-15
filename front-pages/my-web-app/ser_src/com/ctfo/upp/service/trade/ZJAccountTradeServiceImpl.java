package com.ctfo.upp.service.trade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountDetail;
import com.ctfo.account.dao.beans.AccountDetailExampleExtended;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.upp.excelbeans.GeneralAccountExcel;
import com.ctfo.upp.excelbeans.ZJAccountTradExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.service.AbstractService;
import com.ctfo.upp.service.TaskServiceImpl;
import com.ctfo.upp.service.simplecode.ISimplecodeService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DateUtil;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.AccountDetailDto;

@Service("zJAccountTradeService")
public class ZJAccountTradeServiceImpl extends TaskServiceImpl<AccountDetailDto, ZJAccountTradExcel> implements ZJAccountTradeService {

	private static Log logger = LogFactory.getLog(ZJAccountTradeServiceImpl.class);
	private Map<String, String> codeMap = new HashMap<String, String>();
	@Resource
	private ISimplecodeService simpleCodeService;
//	// 获取资金账户表manager
//	private InternalAccountManager manager = null;
//
//	private InternalAccountManager getManager() {
//		if (this.manager == null) {
//			manager = (InternalAccountManager) ServiceFactory.getFactory().getService(InternalAccountManager.class);
//		}
//		return this.manager;
//	}
	
	/**
	 * 详情
	 */
	@Override
	public List<AccountDetailDto> queryZJAccountTrade(
			DynamicSqlParameter requestParam) throws UPPException {
		try {
			List<AccountDetailDto> list = new ArrayList<AccountDetailDto>();
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", requestParam.getEqual().get("tradeExternalNo"));
			
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_DETAIL_INFO_BY_ID_PARAM: " 
					+ JSONObject.fromObject(map).toString());
			String json = super.sendRequest(map, DefaultConfig.getValue("UPP_QUERY_ACCOUNT_DETAIL_INFO_BY_ID"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_DETAIL_INFO_BY_ID： " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				AccountDetailDto adto = (AccountDetailDto)JSONObject.toBean(dataObj, AccountDetailDto.class);
				list.add(adto);
			}
			
			return list;
		} catch (Exception e) {
			logger.error("queryZJAccountHistoryById 异常", e);
			throw new UPPException("queryAccountHistoryById 异常", e);
		}
	}
	/***
	 * 分页查询中交账户交易流水列表
	 * @param requestParam
	 */
	public PaginationResult<?> queryZJAccountTradeByPage(DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<?> result = new PaginationResult<Object>();
		try {
			
			Map<String, String> equalMap = requestParam.getEqual();
			if (equalMap == null) {
				equalMap = new HashMap<String, String>();
			}
			equalMap.put("accountType", "0");
			requestParam.setEqual(equalMap);
			
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_BOOK_ACCOUNT_BY_PAGE_PARAM" 
						+ JSONObject.fromObject(requestParam).toString());
			String jsonString = super.sendRequest(requestParam, DefaultConfig.getValue("UPP_QUERY_BOOK_ACCOUNT_BY_PAGE"));
			
			result = super.getPaginationResult(jsonString, AccountDetailDto.class);

//			AccountExampleExtended accEE = new AccountExampleExtended();
//			accEE.setSelectedField(Account.fieldInsideAccountNo());
//			accEE.createCriteria().andAccountTypeEqualTo("0");
//
//			AccountDetailExampleExtended exampleExtended = new AccountDetailExampleExtended();
//			AccountDetailExampleExtended.Criteria criteria = (AccountDetailExampleExtended.Criteria) Converter.paramToExampleExtendedCriteria(requestParam, exampleExtended);
//			criteria.andInsideAccountNoIn(Arrays.asList(accEE));
//
////			result = getManager().queryBookAccountByPage(exampleExtended);
//			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_BOOK_ACCOUNT_BY_PAGE_PARAM" 
//								+ JSONObject.fromObject(requestParam).toString());
//			String jsonString = super.sendRequest(requestParam, DefaultConfig.getValue("UPP_QUERY_BOOK_ACCOUNT_BY_PAGE"));
//			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_BOOK_ACCOUNT_BY_PAGE" + jsonString);
//			
//			result = super.getPaginationResult(jsonString, AccountDetail.class);
//			
////			result = (PaginationResult<Object>)JSONObject.toBean(JSONObject.fromObject(jsonString), PaginationResult.class);
//			
////			String json = super.sendRequest(exampleExtended, "");
////			result = (PaginationResult<Account>)JSONObject.toBean(JSONObject.fromObject(json), PaginationResult.class);
//			
//			AccountDetail ad = null;
//			List<String> accountNoList = new ArrayList<String>();
//			//Step 1.查询交易流水表信息，账户号列表
//			for (Object obj : result.getData()) {
//				ad = (AccountDetail) obj;
//				accountNoList.add(ad.getInsideAccountNo());
//			}
//			//Step 2.根据账户号，查询账户对应账户号所属用户名称
//			if (accountNoList.size() > 0) {
//				AccountExampleExtended temEE = new AccountExampleExtended();
//				temEE.createCriteria().andInsideAccountNoIn(accountNoList);
////				PaginationResult<Account> paginator = manager.queryAccountByPage(temEE);
//				logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_BY_PAGE_PARAM" + JSONObject.fromObject(temEE).toString());
//				String jsonStr = super.sendRequest(temEE, DefaultConfig.getValue("UPP_QUERY_ACCOUNT_BY_PAGE"));
//				logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_BY_PAGE" + jsonStr);
////				PaginationResult<Account> paginator = 
////						(PaginationResult<Account>)JSONObject.toBean(JSONObject.fromObject(jsonStr), 
////								PaginationResult.class);
//				PaginationResult<Account> paginator = super.getPaginationResult(jsonStr, Account.class);
//
//				for (Object obj : result.getData()) {
//					ad = (AccountDetail) obj;
//					String loginName = "";
//					for (Account acc : paginator.getData()) {
//						if (ad.getInsideAccountNo().equals(acc.getInsideAccountNo())) {
//							loginName = acc.getOwnerLoginName();
//							break;
//						}
//					}
//					ad.setStoreCode(StringUtils.isNotBlank(loginName) ? loginName : "");
//				}
//
//			}
		} catch (Exception e) {
			logger.error("条件分页查询参数配置集合异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("条件分页查询参数配置集合异常", e);
		}
		return result;
	}
	@Override
	public List<ZJAccountTradExcel> exportExcel(DynamicSqlParameter requestParam) throws UPPException {
		List<?> list = new ArrayList<Object>();
		List<ZJAccountTradExcel> result = new ArrayList<ZJAccountTradExcel>();
		list =queryBookAccount(requestParam);
		result = copy(list);
		return result;
	}
	@SuppressWarnings("rawtypes")
	private List<ZJAccountTradExcel> copy(List list) throws UPPException{
		List<ZJAccountTradExcel> result = new ArrayList<ZJAccountTradExcel>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				ZJAccountTradExcel excel = new ZJAccountTradExcel();
				AccountDetail obj = new AccountDetail();
				obj = (AccountDetail) list.get(i);
				//流水号
				excel.setTradeExternalNo(obj.getTradeExternalNo());
				//账户号
				excel.setInsideAccountNo(obj.getInsideAccountNo());
				//账户名称
				excel.setOwnerLoginName(obj.getStoreCode());
				//记账类型
				excel.setBookaccountType(obj.getBookaccountType());
				//科目
				excel.setAccountSubject(obj.getAccountSubject());
				//交易金额（元）
				if(obj.getAccountMoney()!=null&&obj.getAccountMoney()>0){
					excel.setAccountMoney(AmountUtil.getAmount(obj.getAccountMoney()));
				}else{
					excel.setAccountMoney("0.00");
				}
				//交易时间
				excel.setAccountTime(DateUtil.longToDate(obj.getAccountTime(), "yyyy-MM-dd HH:mm:ss"));
				//交易后总余额（元）
				if(obj.getCurrentMoney()!=null &&obj.getCurrentMoney()>0){
					excel.setTotalBalance(AmountUtil.getAmount(obj.getCurrentMoney()));
				}else{
					excel.setTotalBalance("0.00");
				}
				result.add(excel);
			}
		}
		return result;
	}
	
	
	
	/***
	 * 导出中交账户交易流水
	 * 
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public List<AccountDetail> queryBookAccount(DynamicSqlParameter requestParam) throws UPPException {
		List<AccountDetail> list=new ArrayList<AccountDetail>();
		try {
			AccountExampleExtended accEE = new AccountExampleExtended();
			accEE.setSelectedField(Account.fieldInsideAccountNo());
			accEE.createCriteria().andAccountTypeEqualTo("0");

			AccountDetailExampleExtended exampleExtended = new AccountDetailExampleExtended();
			AccountDetailExampleExtended.Criteria criteria = (AccountDetailExampleExtended.Criteria) Converter.paramToExampleExtendedCriteria(requestParam, exampleExtended);
			criteria.andInsideAccountNoIn(Arrays.asList(accEE));

//			List<AccountDetail> result = getManager().queryBookAccount(exampleExtended);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_DETAIL_INFO_PARAM" + JSONObject.fromObject(exampleExtended).toString());
			String json = super.sendRequest(exampleExtended, DefaultConfig.getValue("UPP_QUERY_ACCOUNT_DETAIL_INFO"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_DETAIL_INFO" + json);
			List<AccountDetail> result = (List<AccountDetail>)JSONObject.toBean(JSONObject.fromObject(json), List.class);
			AccountDetail ad = null;
			List<String> accountNoList = new ArrayList<String>();
			for (Object obj : result) {
				ad = (AccountDetail) obj;
				accountNoList.add(ad.getInsideAccountNo());
			}

			if (accountNoList.size() > 0) {
				AccountExampleExtended temEE = new AccountExampleExtended();
				temEE.createCriteria().andInsideAccountNoIn(accountNoList);
//				List<Account> paginator = manager.queryAccount(temEE);
				String jsonString = super.sendRequest(temEE, DefaultConfig.getValue("UPP_QUERY_ACCOUNT"));
				List<Account> paginator = 
						(List<Account>)JSONObject.toBean(JSONObject.fromObject(jsonString), 
								List.class);
				
				for (Object obj : result) {
					ad = (AccountDetail) obj;
					String loginName = "";
					for (Account acc : paginator) {
						if (ad.getInsideAccountNo().equals(acc.getInsideAccountNo())) {
							loginName = acc.getOwnerLoginName();
							break;
						}
					}
					ad.setStoreCode(StringUtils.isNotBlank(loginName) ? loginName : "");
					list.add(ad);
				}

			}
		return result;
		} catch (Exception e) {
			logger.error("导出中交账户交易流水异常。");
			throw new UPPException("导出中交账户交易流水异常", e);
		}
	}
	@Override
	public String queryCount(DynamicSqlParameter requestParam) throws UPPException {
		
		try {
			logger.info("UPP_HTTP_INTERFACE_UPP_ACCOUNT_SUM_INT_OUT_DETAIL_PARAM: " 
						+ JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(requestParam, 
					DefaultConfig.getValue("UPP_ACCOUNT_SUM_INT_OUT_DETAIL"));
			logger.info("UPP_HTTP_INTERFACE_UPP_ACCOUNT_SUM_INT_OUT_DETAIL: " + json);
			Object obj = (Object)super.processReturnResult(json);
			return JSONObject.fromObject(obj).toString();
		} catch (Exception e) {
			logger.error("查询账户进出项异常", e);
			throw new UPPException("查询账户进出项异常");
		}
		
//		List<AccountDetail> accountDetail = new ArrayList<AccountDetail>();
//		//查找交易订单
//		try {
//			AccountExampleExtended accEE = new AccountExampleExtended();
//			accEE.setSelectedField(Account.fieldInsideAccountNo());
//			accEE.createCriteria().andAccountTypeEqualTo("0");
//
//			AccountDetailExampleExtended exampleExtended = new AccountDetailExampleExtended();
//			AccountDetailExampleExtended.Criteria criteria = (AccountDetailExampleExtended.Criteria) Converter.paramToExampleExtendedCriteria(requestParam, exampleExtended);
//			criteria.andInsideAccountNoIn(Arrays.asList(accEE));
//			//List<Account> paginator = manager.queryAccount(accEE);
//		//	accountDetail = getManager().queryBookAccount(exampleExtended);
//		} catch (Exception e) {
//			logger.error("查询普通账户交易流水异常");
//			throw new UPPException("查询普通账户交易流水异常", e);
//		} 
//		Long deductionCount = 0L;
//		Long recordedCount = 0L;
//		String result = "";
//		Map<String,String> map = new HashMap<String,String>();
//		JSONObject json = null;
//		for(AccountDetail detail : accountDetail){
//			if("deduction".equals(detail.getBookaccountType())){
//				deductionCount += detail.getAccountMoney();
//			}
//			if("recorded".equals(detail.getBookaccountType())){
//				recordedCount += detail.getAccountMoney();
//			}
//			map.put("deduction", deductionCount+"");
//			map.put("recorded", recordedCount+"");
//			json = JSONObject.fromObject(map);
//			result = json.toString();
//		}
//		result = json.toString();
//		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String exportExcelNew(DynamicSqlParameter requestParam)
			throws UPPException {
		String taskId = "";
		try{
			long aa = System.currentTimeMillis();
			
			String taskName = "中交账户交易流水下载";
			String countUrl = DefaultConfig.getValue("UPP_COUNT_BOOK_ACCOUNT_DETAIL");
			String queryUrl = DefaultConfig.getValue("UPP_QUERY_ACCOUNT_DETAIL_INFO");
		//	taskId = super.runExportExcelTask(taskName, countUrl, queryUrl, requestParam, AccountDetailDto.class);
			super.runExportExcelTask(taskName, countUrl, queryUrl, requestParam, AccountDetailDto.class,ZJAccountTradExcel.class);
			logger.info("---->>>导出用时:"+(System.currentTimeMillis()-aa));
			
		}catch(Exception e){
			logger.error("导出账户流水异常："+e.getLocalizedMessage(),e);
		}
		return taskId;
	}
	@Override
	public List<ZJAccountTradExcel> copyTask(List<?> list) throws Exception {
		List<ZJAccountTradExcel> result = new ArrayList<ZJAccountTradExcel>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				ZJAccountTradExcel excel = new ZJAccountTradExcel();
				AccountDetailDto obj = new AccountDetailDto();
				obj = (AccountDetailDto) list.get(i);
				//流水号
				excel.setTradeExternalNo(obj.getTradeExternalNo());
				//账户号
				excel.setInsideAccountNo(obj.getInsideAccountNo());
				//账户所属
				excel.setOwnerLoginName(obj.getOwnerLoginName());
				
				// 科目
				String accountSubject = obj.getOrderTypeLuc();
				excel.setAccountSubject(StringUtils.isNotBlank(accountSubject)?this.getCodeName("ORDER_SUBJECT", accountSubject):"");
				// 记账类型
				String bookaccountType = obj.getBookAccountType();
				excel.setBookaccountType(StringUtils.isNotBlank(bookaccountType)?this.getCodeName("BOOK_ACCOUNT_TYPE", bookaccountType.toUpperCase()):"");
				
				//交易金额（元）
				if(obj.getBookAccountMoney()!=null&& !"".equals(obj.getBookAccountMoney())){
					excel.setAccountMoney(obj.getBookAccountMoney());
				}else{
					excel.setAccountMoney("0.00");
				}
				//交易时间
				excel.setAccountTime(DateUtil.longToDate(Long.parseLong(obj.getAccountTime()), "yyyy-MM-dd HH:mm:ss"));
				//交易后总余额（元）
				if(obj.getBookCurrentMoney()!=null && !"".equals(obj.getBookCurrentMoney())){
					excel.setTotalBalance(obj.getBookCurrentMoney());
				}else{
					excel.setTotalBalance("0.00");
				}
				result.add(excel);
			}
		}
		return result;
	}
	

}
