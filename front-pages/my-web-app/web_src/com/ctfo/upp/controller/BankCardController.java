package com.ctfo.upp.controller;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.base.dao.beans.BankCardInfo;
import com.ctfo.excel.exp.ExportExcel;
import com.ctfo.excel.parse.ExpObj;
import com.ctfo.excel.util.ExcelUtil;
import com.ctfo.excel.util.ExcelVersion;
import com.ctfo.upp.excelbeans.BankCardExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.bankcard.BankCardService;
/**
 * 银行卡信息控制层
 * @author lipeng01
 *
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/bankCard")
public class BankCardController extends BaseController{
	private static Log logger = LogFactory.getLog(BankCardController.class);
	PaginationResult<BankCardInfo> result = new PaginationResult<BankCardInfo>();
	public BankCardController() {
		model = new BankCardInfo();
	}
	@Resource(name="bankCardService",description="银行卡service")
	private BankCardService bankCardService;
	
	@SuppressWarnings("all")
	@RequestMapping(value = "/queryAllBankCard.action", produces = "application/json")
	@ResponseBody
	public PaginationResult queryAllByPage(DynamicSqlParameter requestParam){
		PaginationResult pgResult = new PaginationResult();
		try {
			pgResult = bankCardService.queryBankCardByPage(requestParam);
		} catch (UPPException e) {
			logger.error("分页查询银行卡信息异常",e);
		}
		return pgResult;
	}
	/**
	 * 通过条件导出所有数据
	 * 
	 * @param requestParam
	 */

	@SuppressWarnings("all")
	@RequestMapping(value = "/exportExcel.action", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(DynamicSqlParameter requestParam){
		try {
			response.setDateHeader("Expires", 0);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String encodedfileName = new String("银行卡数据".getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");

			OutputStream out = response.getOutputStream();
			ExportExcel<BankCardExcel> exp = new ExportExcel<BankCardExcel>();
			List<ExpObj> expObjs = ExcelUtil.getExpObjs(BankCardExcel.class, 0, "EXP");
			// 定义的方法
			List<Method> methods = new ArrayList<Method>(expObjs.size());
			// 定义执行方法的对象
			List<Object> objs = new ArrayList<Object>(expObjs.size());
			List<BankCardExcel> result = new ArrayList<BankCardExcel>();
			// 通过条件查找到商户信息
			result = bankCardService.exportExcel(requestParam);
			BankCardExcel excelObj = new BankCardExcel();
			excelObj = result.get(0);
			for (int i = 0; i < expObjs.size(); i++) {
				objs.add(null);
				methods.add(null);
			}

			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
			exp.singleExportExcel(result, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("按条件导出所有银行卡信息异常", e);
		}
	}

}
