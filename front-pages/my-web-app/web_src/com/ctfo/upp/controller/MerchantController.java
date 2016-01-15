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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.base.dao.beans.UPPlatform;
import com.ctfo.excel.exp.ExportExcel;
import com.ctfo.excel.parse.ExpObj;
import com.ctfo.excel.util.ExcelUtil;
import com.ctfo.excel.util.ExcelVersion;
import com.ctfo.upp.excelbeans.MerchantExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.merchant.MerchantService;
import com.ctfo.upp.service.simplecode.ISimplecodeService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DateUtil;

/**
 * 商户控制层
 * 
 * @author lipeng01
 * 
 */

@Scope("prototype")
@Controller
@RequestMapping(value = "/merchant")
public class MerchantController extends BaseController {

	private static Log logger = LogFactory.getLog(MerchantController.class);
	@Resource
	private MerchantService merchantService;// 商户service
	@Resource
	private ISimplecodeService simplecodeService;// 码表service
	private PaginationResult<UPPlatform> result = new PaginationResult<UPPlatform>();

	public MerchantController() {
		model = new UPPlatform();
	}

	/**
	 * 查询商户名称是否可用
	 * 
	 * @param model
	 * @return String true 可用 false 已存在此商户名
	 */
	@RequestMapping(value = "/checkExist.action")
	@ResponseBody
	public String checkExist(UPPlatform model) {
		boolean checkResult = false;
		// 如果model的ID存在值，则为修改，不用校验。
		if (model.getId() != null && !"".equals(model.getId())) {
			return "true";
		} else {
			try {
				checkResult = merchantService.checkExist(model);
				if (checkResult) {
					return "true";
				}
			} catch (Exception e) {
				logger.error("查找此名称是否存在异常", e);
			}
		}
		return "false";
	}

	/**
	 * 添加商户信息，返回秘钥，商户编码。
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addMerchant.action", produces = "application/json")
	@ResponseBody
	public PaginationResult addMerchant() {
		PaginationResult result = new PaginationResult();
		try {
			result = merchantService.addMerchant((UPPlatform) model, index.getUserLogin());
		} catch (UPPException e) {
			logger.error("查询商户信息失败", e);
		}
		return result;
	}

	@SuppressWarnings("all")
	@RequestMapping(value = "/getMerchantList.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<UPPlatform> getMerchantList(DynamicSqlParameter requestParam) {
		PaginationResult result = null;
		try {
			result = merchantService.queryMerchantAll(requestParam);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("分页查询商户信息异常", e);
		}
		return result;
	}

	@RequestMapping(value = "/deleteMerchant.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<UPPlatform> delMerchant(@RequestParam("id") String id) {
		try {
			result = merchantService.delMerchant(id);
		} catch (Exception e) {
			logger.error("删除商户信息异常", e);
		}
		return result;
	}

	@RequestMapping(value = "/getMerchantById.action", produces = "application/json")
	@ResponseBody
	public UPPlatform getMerchantById(@RequestParam("id") String id) {
		UPPlatform upp = null;
		try {
			upp = merchantService.getMerchantById(id);
		} catch (UPPException e) {
			logger.error("通过ID查询商户信息异常", e);
		}
		return upp;
	}

	@RequestMapping(value = "/editMerchant.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<UPPlatform> editMerchant() {
		PaginationResult<UPPlatform> result = new PaginationResult<UPPlatform>();
		try {
			merchantService.editMerchant((UPPlatform) model);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (UPPException e) {
			logger.error("修改商户信息失败", e);
			result.setMessage(Converter.OP_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/initkeys.action", produces = "application/json")
	@ResponseBody
	public String initKeys() {
		try {
			
			merchantService.initKeys();
			
		} catch (UPPException e) {
			logger.error("初始化商户密钥异常", e);
			return "FAIL";
		}
		return "SUCCESS";
	}
	@RequestMapping(value = "/modifyStatusMerchant.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<UPPlatform> modifyStatusMerchant() {
		try {
			result = merchantService.modifyStatusMerchant((UPPlatform) model);
		} catch (UPPException e) {
			logger.error("修改商户状态失败", e);
		}
		return result;
	}

	// 回调
	@RequestMapping(value = "/callback.action", method = RequestMethod.GET)
	public ModelAndView index(@RequestParam String id, ModelAndView mv) {
		mv.addObject("id", id);
		mv.setViewName("pages/merchant/callback-list");
		return mv;
	}

	/**
	 * 导出单条数据
	 * 
	 * @param id
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/exportExcel.action", method = RequestMethod.GET)
	@ResponseBody
	public void exportExcel(@RequestParam String id) {
		try {
			response.setDateHeader("Expires", 0);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String encodedfileName = new String("商户数据".getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");

			OutputStream out = response.getOutputStream();
			ExportExcel<MerchantExcel> exp = new ExportExcel<MerchantExcel>();
			List<ExpObj> expObjs = ExcelUtil.getExpObjs(MerchantExcel.class, 0, "EXP");
			// 定义的方法
			List<Method> methods = new ArrayList<Method>(expObjs.size());
			// 定义执行方法的对象
			List<Object> objs = new ArrayList<Object>(expObjs.size());
			List<MerchantExcel> result = new ArrayList<MerchantExcel>();
			// 通过ID查找到商户信息
			result = merchantService.exportById(id);
			MerchantExcel excelObj = new MerchantExcel();
			excelObj = result.get(0);
			Map<String, String> storeTypeMap = new HashMap<String, String>();
			// 通过编码查找码表对应的名称(商户类型)
			String storeTypeName = simplecodeService.getNameBycode(excelObj.getStoreType());
			storeTypeMap.put(excelObj.getStoreType(), storeTypeName);
			// 商户状态
			Map<String, String> storeStatusMap = new HashMap<String, String>();
			String storeStatusName = simplecodeService.getNameBycode(excelObj.getStoreStatus());
			storeStatusMap.put(excelObj.getStoreStatus(), storeStatusName);
			// 接入时间
			Map<String, String> regTimeMap = new HashMap<String, String>();
			String regTime = DateUtil.longToDate(Long.parseLong(excelObj.getRegTime()), "yyyy-MM-dd HH:mm:ss");
			regTimeMap.put(excelObj.getRegTime(), regTime);
			for (int i = 0; i < expObjs.size(); i++) {
				objs.add(null);
				methods.add(null);
				switch (i) {
				case 2:
					objs.set(i, storeTypeMap);
					break;
				case 5:
					objs.set(i, storeStatusMap);
					break;
				case 7:
					objs.set(i, regTimeMap);
					break;
				default:
					break;
				}
			}

			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
			exp.singleExportExcel(result, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("导出单条商户信息异常", e);
		}

	}

	/**
	 * 通过条件导出所有数据
	 * 
	 * @param requestParam
	 */

	@SuppressWarnings("all")
	@RequestMapping(value = "/exportExcelAll.action", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcelAll(DynamicSqlParameter requestParam) {
		try {
			//解决中文乱码问题
			String storeName = "";
			String contactUser = "";
			if(requestParam.getLike()!=null){
			if(requestParam.getLike().get("storeName")!=null && !"".equals(requestParam.getLike().get("storeName"))){
				storeName = new String(requestParam.getLike().get("storeName").getBytes("ISO8859-1"),"UTF-8");
				requestParam.getLike().remove("storeName");
				requestParam.getLike().put("storeName", storeName);
			}
			if(requestParam.getLike().get("contactUser")!=null && !"".equals(requestParam.getLike().get("contactUser"))){
				contactUser = new String(requestParam.getLike().get("contactUser").getBytes("ISO8859-1"),"UTF-8");
				requestParam.getLike().put("contactUser", contactUser);
			}
			}
			response.setDateHeader("Expires", 0);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String encodedfileName = new String("商户数据".getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");

			OutputStream out = response.getOutputStream();
			ExportExcel<MerchantExcel> exp = new ExportExcel<MerchantExcel>();
			List<ExpObj> expObjs = ExcelUtil.getExpObjs(MerchantExcel.class, 0, "EXP");
			// 定义的方法
			List<Method> methods = new ArrayList<Method>(expObjs.size());
			// 定义执行方法的对象
			List<Object> objs = new ArrayList<Object>(expObjs.size());
			List<MerchantExcel> result = new ArrayList<MerchantExcel>();
			// 通过条件查找到商户信息
			result = merchantService.exportAll(requestParam);
			MerchantExcel excelObj = new MerchantExcel();

			Map<String, String> storeTypeMap = new HashMap<String, String>();
			// 通过编码查找码表对应的名称(商户类型) 商户类型code 2000
			List<SimpleCode> storeType = simplecodeService.getByFCode("2000");
			for(SimpleCode code:storeType){
				String storeTypeName = simplecodeService.getNameBycode(code.getCode());
				storeTypeMap.put(code.getCode(), storeTypeName);
			}
			// 商户状态
			Map<String, String> storeStatusMap = new HashMap<String, String>();
			List<SimpleCode> statusList = simplecodeService.getByFCode("3000");
			for(SimpleCode code:statusList){
				String storeTypeName = simplecodeService.getNameBycode(code.getCode());
				storeStatusMap.put(code.getCode(), storeTypeName);
			}
			for (int i = 0; i < expObjs.size(); i++) {
				objs.add(null);
				methods.add(null);
				switch (i) {
				case 2:
					objs.set(i, storeTypeMap);
					break;
				case 5:
					objs.set(i, storeStatusMap);
					break;
				default:
					break;
				}
			}
			expObjs = new ExcelUtil().setExpObjMethodObjects(expObjs, methods, objs, null);
			exp.singleExportExcel(result, out, expObjs, 0, true, ExcelVersion.VERSION_2003);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("按搜索条件导出所有商户信息异常", e);
		}

	}

}
