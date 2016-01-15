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
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.TaskServiceImpl;
import com.ctfo.upp.service.account.IAccountService;
import com.ctfo.upp.service.simplecode.ISimplecodeService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DateUtil;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.AccountDetailDto;
@Service("generalAccountTradeService")
public class GeneralAccountTradeServiceImpl extends TaskServiceImpl<AccountDetailDto, GeneralAccountExcel> implements GeneralAccountTradeService {
	
	private static Log logger = LogFactory.getLog(GeneralAccountTradeServiceImpl.class);
	
	private Map<String, String> codeMap = new HashMap<String, String>();

	@Resource
	private IAccountService accountService;
	
	@Resource
	private ISimplecodeService simpleCodeService;
	
	
	@Override
	public List<AccountDetailDto> queryById(DynamicSqlParameter requestParam)
			throws UPPException {
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
			logger.error("queryGeneralAccountHistoryById 异常", e);
			throw new UPPException("queryGeneralAccountHistoryById 异常", e);
		}
	}
	
	public PaginationResult<?> queryGeneralAccountTradeByPage(DynamicSqlParameter requestParam)throws UPPException{
		PaginationResult<?> result = new PaginationResult<Object>();
		try {
			Map<String, String> equalMap = null;
			equalMap = requestParam.getEqual();
			if (equalMap != null && equalMap.size() > 0) {
			} else {
				equalMap = new HashMap<String, String>();
			}
			
			equalMap.put("accountType", "2");
			requestParam.setEqual(equalMap);
			
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_DETAIL_INFO_GENERAL_PARAM: " 
							+ JSONObject.fromObject(requestParam).toString());
			
			String json = super.sendRequest(requestParam,                        
					DefaultConfig.getValue("UPP_QUERY_ACCOUNT_DETAIL_INFO"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_ACCOUNT_DETAIL_INFO_GENERAL: " +  json);
			result = super.getPaginationResult(json, AccountDetailDto.class);
					
		} catch (Exception e) {
			logger.error("条件分页查询普通账户交易流水异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("条件分页查询普通账户交易流水异常", e);
		}
		return result;
	}
	
	@Override
	public String queryCount(DynamicSqlParameter requestParam) throws UPPException {
		List<AccountDetail> accountDetail = new ArrayList<AccountDetail>();
		//查找交易订单
		try {
			AccountExampleExtended accEE = new AccountExampleExtended();
			accEE.setSelectedField(Account.fieldInsideAccountNo());
			accEE.createCriteria().andAccountTypeNotEqualTo("0");
			AccountDetailExampleExtended exampleExtended = new AccountDetailExampleExtended();
			AccountDetailExampleExtended.Criteria criteria = (AccountDetailExampleExtended.Criteria) Converter.paramToExampleExtendedCriteria(requestParam, exampleExtended);
			criteria.andInsideAccountNoIn(Arrays.asList(accEE));
		//	accountDetail = getManager().queryBookAccount(exampleExtended);
		} catch (Exception e) {
			logger.error("查询普通账户交易流水异常");
			throw new UPPException("查询普通账户交易流水异常", e);
		} 
		Long deductionCount = 0L;
		Long recordedCount = 0L;
		String result = "";
		Map<String,String> map = new HashMap<String,String>();
		JSONObject json = null;
		for(AccountDetail detail : accountDetail){
			if("deduction".equals(detail.getBookaccountType())){
				deductionCount += detail.getAccountMoney();
			}
			if("recorded".equals(detail.getBookaccountType())){
				recordedCount += detail.getAccountMoney();
			}
			map.put("deduction", deductionCount+"");
			map.put("recorded", recordedCount+"");
			json = JSONObject.fromObject(map);
			result = json.toString();
		}
		result = json.toString();
		return result;
	}
	
	/**
	 * 1.添加下载任务
	 * 2.查询账户流水
	 * 3.写文件到Mongo中
	 * 4.更新任务状态
	 */
	@Override
	public void exportExcel(DynamicSqlParameter requestParam) throws UPPException {
		try{
			long aa = System.currentTimeMillis();
			
			String taskName = "普通账户交易流水下载";
			String countUrl = DefaultConfig.getValue("UPP_COUNT_BOOK_ACCOUNT_DETAIL");
			String queryUrl = DefaultConfig.getValue("UPP_QUERY_ACCOUNT_DETAIL_INFO");
			super.runExportExcelTask(taskName, countUrl, queryUrl, requestParam, AccountDetailDto.class, GeneralAccountExcel.class);
		
			logger.info("---->>>导出用时:"+(System.currentTimeMillis()-aa));
			
		}catch(Exception e){
			logger.error("导出账户流水异常："+e.getLocalizedMessage(),e);
		}
	}
	
	@Override
	public List<GeneralAccountExcel> copyTask(List<?> list) throws Exception{
		List<GeneralAccountExcel> result = new ArrayList<GeneralAccountExcel>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				GeneralAccountExcel excel = new GeneralAccountExcel();
				AccountDetailDto obj = new AccountDetailDto();
				obj = (AccountDetailDto) list.get(i);
				//流水号
				excel.setTradeExternalNo(obj.getTradeExternalNo());
				//账户号
				excel.setInsideAccountNo(obj.getInsideAccountNo());
				//账户所属
//				excel.setOwnerLoginName(obj.getOwnerLoginName());
				
				// 科目
				String accountSubject = obj.getOrderTypeLuc();
				
				excel.setAccountSubject(StringUtils.isNotBlank(accountSubject)?this.codeName(accountSubject):"");
				// 记账类型
				String bookaccountType = obj.getBookAccountType();
				excel.setBookaccountType(StringUtils.isNotBlank(bookaccountType)?this.getCodeName("BOOK_ACCOUNT_TYPE", bookaccountType.toUpperCase()):"");
				
				//交易金额（元）
				if(obj.getBookAccountMoney()!=null&& !"".equals(obj.getBookAccountMoney())){
					excel.setAccountMoney(obj.getBookAccountMoney());
				}else{
					excel.setAccountMoney("0.00");
				}
				String ownerUserNo = obj.getOwnerUserNo();
				excel.setOwnerUserNo(StringUtils.isNotBlank(ownerUserNo)?ownerUserNo:"");
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
	
	private List<SimpleCode> getCode(String type)  throws Exception{
		List<SimpleCode> list = new ArrayList<SimpleCode>();
		DynamicSqlParameter requestParam = new DynamicSqlParameter();
		Map<String,String> map = new HashMap<String, String>();
		map.put("typeCode", type);
		requestParam.setEqual(map);
		list = (List<SimpleCode>) simpleCodeService.queryCodeByParam(requestParam);
		return list;
		
	}
	private String codeName(String accountSubject) throws Exception{
		List<SimpleCode> subjectList = this.getCode("ORDER_SUBJECT_NEW");
		String codeName = "";
		for(SimpleCode simpleCode : subjectList){
			String name = simpleCode.getCode();
			String[] str = name.split(",");
			for(int y=0;y<str.length;y++){
				if(str[y].equals(accountSubject)){
					codeName = simpleCode.getName();
				}
			}
		}
		return codeName;
	}
	
}
