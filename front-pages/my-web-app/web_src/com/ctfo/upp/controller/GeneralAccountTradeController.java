package com.ctfo.upp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.simplecode.ISimplecodeService;
import com.ctfo.upp.service.trade.GeneralAccountTradeService;
import com.sinoiov.upp.service.dto.AccountDetailDto;

/***
 * 类描述：账户管理-普通账户交易流水查询
 * 
 * @author：liuguozhong
 * @date：2014年12月9日下午3:22:35
 * @version 1.0
 * @since JDK1.6
 */
@Component
@Controller
@Scope("prototype")
@RequestMapping("/generaltrade")
public class GeneralAccountTradeController extends BaseController {

	private static Log logger = LogFactory.getLog(GeneralAccountTradeController.class);
	@Resource(name = "generalAccountTradeService", description = "service参数接口")
	private GeneralAccountTradeService generalAccountTradeService;
	@Resource
	private ISimplecodeService simplecodeService;
	private PaginationResult<?> result = new PaginationResult<Object>();

	/**
	 * 分页账户管理-普通账户交易流水信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryList.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryParamByPage(DynamicSqlParameter requestParam) {
		try {
			result = generalAccountTradeService.queryGeneralAccountTradeByPage(requestParam);
		} catch (Exception e) {
			result.setMessage("分页查询账户管理-普通账户交易流水信息异常!");
			logger.error("分页查询账户管理-普通账户交易流水信息异常!", e);
		}
		return result;
	}
	
	/**
	 * 查询下载任务列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryTaskList.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryTaskList(DynamicSqlParameter requestParam) {
		try {
			
			result = generalAccountTradeService.queryTaskList(requestParam);
			
		} catch (Exception e) {
			result.setMessage("查询下载任务列表异常!");
			logger.error("查询下载任务列表异常!", e);
		}
		return result;
	}
	
	@RequestMapping(value = "/queryById.action", produces = "application/json")
	@ResponseBody
	public List<AccountDetailDto> queryById(DynamicSqlParameter requestParam) {
		List<AccountDetailDto> list = null;
		try {
			list = generalAccountTradeService.queryById(requestParam);
		} catch (Exception e) {
			result.setMessage("按id查询普通账户交易流水信息异常!");
			logger.error("按id查询普通账户交易流水信息异常!", e);
		}
		return list;
	}
	/**
	 * 普通账户，总出项，总进项查询
	 * 
	 * @return json
	 */
	@RequestMapping(value = "/queryCount.action", produces = "application/json")
	@ResponseBody
	public String queryCount(DynamicSqlParameter requestParam) {
		String count = "";
		try {
		//	count = generalAccountTradeService.queryCount(requestParam);
		} catch (Exception e) {
			count = "-1";
			logger.error("分页查询账户管理-普通账户交易流水信息异常!", e);
		}
		return count;
	}
	/**
	 * 通过条件导出EXCEL
	 * 
	 * @param requestParam
	 */
	@RequestMapping(value = "/exportExcelAll.action", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(DynamicSqlParameter requestParam) {
		try {
			//添加排序
			requestParam.setOrder("accountTime");
			requestParam.setSort("desc");
			generalAccountTradeService.exportExcel(requestParam);
		} catch (Exception e) {
			logger.error("导出普通账户流水EXCEL异常",e);
		}
	}
//	/**
//	 * 通过条件导出EXCEL
//	 * 
//	 * @param requestParam
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(value = "/exportExcelAll.action", method = RequestMethod.POST)
//	@ResponseBody
//	public void exportExcel(DynamicSqlParameter requestParam) {
//		//添加排序
//		requestParam.setOrder("accountTime");
//		requestParam.setSort("desc");
//		try {
//			response.setDateHeader("Expires", 0);
//			response.addHeader("Pragma", "no-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//			String encodedfileName = new String("普通账户交易流水".getBytes("GBK"), "ISO8859-1");
//			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");
//			OutputStream out = response.getOutputStream();
//			
//			ExportExcel<GeneralAccountExcel> exp = new ExportExcel<GeneralAccountExcel>();
//			List<ExpObj> expObjs = ExcelUtil.getExpObjs(GeneralAccountExcel.class, 0, "EXP");
//			// 定义的方法
//			List<Method> methods = new ArrayList<Method>(expObjs.size());
//			// 定义执行方法的对象
//			List<Object> objs = new ArrayList<Object>(expObjs.size());
//			List<GeneralAccountExcel> result = new ArrayList<GeneralAccountExcel>();
//			
//			result = generalAccountTradeService.exportExcel(requestParam);
//			for (GeneralAccountExcel excel : result) {
//				// 科目
//				String accountSubject = excel.getAccountSubject();
//				SimpleCode code = new SimpleCode();
//				if(accountSubject!=null){
//					code = simplecodeService.getByTypeCodeAndCode("ORDER_SUBJECT", accountSubject);
//					if(code!=null){
//						excel.setAccountSubject(code.getName());
//					}else{
//						excel.setAccountSubject("");
//					}
//				}else{
//					excel.setAccountSubject("");
//				}
//				// 记账类型
//				String bookaccountType = excel.getBookaccountType();
//				
//				if(bookaccountType!=null){
//					code = simplecodeService.getByTypeCodeAndCode("BOOK_ACCOUNT_TYPE", bookaccountType.toUpperCase());
//					if(code!=null){
//						excel.setBookaccountType(code.getName());
//					}else{
//						excel.setBookaccountType("");
//					}
//				}else{
//					excel.setBookaccountType("");
//				}
//			}
//			for (int i = 0; i < expObjs.size(); i++) {
//				objs.add(null);
//				methods.add(null);
//			}
//			
//			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
//			exp.singleExportExcel(result, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
//			response.getOutputStream().flush();
//			response.getOutputStream().close();
//		} catch (Exception e) {
//			logger.error("导出普通账户流水EXCEL异常",e);
//		}
//	}
}
