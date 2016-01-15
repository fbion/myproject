package com.ctfo.upp.controller;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.excel.exp.ExportExcel;
import com.ctfo.excel.parse.ExpObj;
import com.ctfo.excel.util.ExcelUtil;
import com.ctfo.excel.util.ExcelVersion;
import com.ctfo.upp.excelbeans.ZJAccountTradExcel;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.simplecode.ISimplecodeService;
import com.ctfo.upp.service.trade.ZJAccountTradeService;
import com.sinoiov.upp.service.dto.AccountDetailDto;

/***
 * 类描述：账户管理-中交账户交易流水查询
 * 
 * @author：liuguozhong
 * @date：2014年12月9日下午3:22:35
 * @version 1.0
 * @since JDK1.6
 */
@Component
@Controller
@Scope("prototype")
@RequestMapping("/zjtrade")
public class ZJAccountTradeController extends BaseController {
	
	private static Log logger = LogFactory.getLog(ZJAccountTradeController.class);
	@Resource(name = "zJAccountTradeService", description = "service参数接口")
	private ZJAccountTradeService zJAccountTradeService;
	@Resource
	private ISimplecodeService simplecodeService;
	private PaginationResult<?> result = new PaginationResult<Object>();
	
	/**
	 * 分页账户管理-中交账户交易流水信息
	 * @return
	 */
	@RequestMapping(value="/queryList.action" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryParamByPage(DynamicSqlParameter requestParam){
		try {
			result = zJAccountTradeService.queryZJAccountTradeByPage(requestParam);
		} catch (Exception e) {
			result.setMessage("分页查询账户管理-中交账户交易流水信息异常!");
			logger.error("分页查询账户管理-中交账户交易流水信息异常!",e);
		}		
		return result;	
	}
	
	/**
	 * 分页账户管理-中交账户交易流水信息
	 * @return
	 */
	@RequestMapping(value="/queryDetailInfo.action" ,produces = "application/json")
	@ResponseBody
	public List<AccountDetailDto> queryDetailInfo(DynamicSqlParameter requestParam){
		List<AccountDetailDto> list = null;
		try {
			list = zJAccountTradeService.queryZJAccountTrade(requestParam);
		} catch (Exception e) {
			result.setMessage("分页查询账户管理-中交账户交易流水信息异常!");
			logger.error("分页查询账户管理-中交账户交易流水信息异常!",e);
		}		
		return list;	
	}
	/**
	 * 中交账户，总出项，总进项查询
	 * 
	 * @return json
	 */
	@RequestMapping(value = "/queryCount.action", produces = "application/json")
	@ResponseBody
	public String queryCount(DynamicSqlParameter requestParam) {
		String count = "";
		try {
//			Map map = requestParam.getEqual();
//			if (map == null) {
//				map = new HashMap<String, String>();
//			} 
//			map.put("accountType", "0");
//			requestParam.setEqual(map);
			count = zJAccountTradeService.queryCount(requestParam);
		} catch (Exception e) {
			count = "-1";
			logger.error("统计账户流水异常!", e);
		}
		return count;
	}
	
	/**
	 * 通过条件导出EXCEL
	 * 
	 * @param requestParam
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/exportExcelAll.action", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(DynamicSqlParameter requestParam){
		try {
			//添加排序
			requestParam.setOrder("accountTime");
			requestParam.setSort("desc");
			zJAccountTradeService.exportExcelNew(requestParam);

		} catch (Exception e) {
			logger.error("导出中交账户流水EXCEL异常",e);
		}
	}
	/*public void exportExcel(DynamicSqlParameter requestParam) {
		try {
			response.setDateHeader("Expires", 0);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String encodedfileName = new String("中交账户交易流水".getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");
			OutputStream out = response.getOutputStream();

			ExportExcel<ZJAccountTradExcel> exp = new ExportExcel<ZJAccountTradExcel>();
			List<ExpObj> expObjs = ExcelUtil.getExpObjs(ZJAccountTradExcel.class, 0, "EXP");
			// 定义的方法
			List<Method> methods = new ArrayList<Method>(expObjs.size());
			// 定义执行方法的对象
			List<Object> objs = new ArrayList<Object>(expObjs.size());
			List<ZJAccountTradExcel> result = new ArrayList<ZJAccountTradExcel>();

			result = zJAccountTradeService.exportExcel(requestParam);
			for (ZJAccountTradExcel excel : result) {
				// 科目
				String accountSubject = excel.getAccountSubject();
				SimpleCode code = new SimpleCode();
				if(accountSubject!=null){
					code = simplecodeService.getByTypeCodeAndCode("ORDER_SUBJECT", accountSubject);
					if(code!=null){
						excel.setAccountSubject(code.getName());
					}else{
						excel.setAccountSubject("");
					}
				}else{
					excel.setAccountSubject("");
				}
				// 记账类型
				String bookaccountType = excel.getBookaccountType();
				
				if(bookaccountType!=null){
					code = simplecodeService.getByTypeCodeAndCode("BOOK_ACCOUNT_TYPE", bookaccountType.toUpperCase());
					if(code!=null){
						excel.setBookaccountType(code.getName());
					}else{
						excel.setBookaccountType("");
					}
				}else{
					excel.setBookaccountType("");
				}
			}
			for (int i = 0; i < expObjs.size(); i++) {
				objs.add(null);
				methods.add(null);
			}

			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
			exp.singleExportExcel(result, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("导出中交账户流水EXCEL异常",e);
		}
	}*/
}
