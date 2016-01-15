package com.ctfo.upp.controller;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.base.dao.beans.UPParam;
import com.ctfo.excel.exp.ExportExcel;
import com.ctfo.excel.parse.ExpObj;
import com.ctfo.excel.util.ExcelUtil;
import com.ctfo.excel.util.ExcelVersion;
import com.ctfo.upp.dict.AccountDict;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dto.UPPAccountDto;
import com.ctfo.upp.dto.UPPOrderRechargeDto;
import com.ctfo.upp.excelbeans.ZJAccountExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.account.IAccountService;
import com.ctfo.upp.service.param.ParamService;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.AccountDto;

/***
 * 类描述：中交账户列表Controller
 * @author：liuguozhong
 * @date：2015年1月16日下午2:48:00
 * @version 1.0
 * @since JDK1.6
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/ZJAccount")
public class ZJAccountController extends BaseController {

	private static Log logger = LogFactory.getLog(ZJAccountController.class);
	@Resource(name = "accountService", description = "service参数接口")
	private IAccountService accountService;
	@Resource(name = "paramService", description = "service参数接口")
	private ParamService paramService;
	private PaginationResult<Account> result = new PaginationResult<Account>();
	
	
	public ZJAccountController() {
		model=new UPParam();
	}
	/**
	 * 中交账户-充值
	 * @param uppAccountDto
	 * @return
	 */
	@RequestMapping(value = "/recharge.action", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> recharge(@ModelAttribute("form1")UPPOrderRechargeDto orderDto){
		ResponseEntity<String> responseEntity = null;
		String json="";
		try {
			Map<String, Object> map = accountService.recharge(orderDto);
			String url = (String) map.get("url");
			json = "{\"data\":\"1\", \"url\":\""+url+"\"}";
			logger.debug("json3:"+ json);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			json = "{\"data\":\"-1\", \"error\":\""+ue.getLocalizedMessage()+"\"}";
		} catch (Exception e) {
			logger.error("充值错误！",e);
			json = "{\"data\":\"-1\", \"error\":\"充值错误\"}";
		}finally{			
			HttpHeaders httpHeaders = new HttpHeaders();
			responseEntity = new ResponseEntity<String>(json, httpHeaders, HttpStatus.CREATED);			
		}
		return responseEntity;
	}
	/**
	 * 中交账户-开户
	 * @param uppAccountDto
	 * @return
	 */
	@RequestMapping(value = "/open.action", method = RequestMethod.POST)
	@ResponseBody
	public PaginationResult<?> createAccount(@ModelAttribute UPPAccountDto uppAccountDto) {
		PaginationResult<?> result = new PaginationResult<Object>();
		try {
			//检查开户传参数非空校验
			accountService.checkOpenAccount(uppAccountDto);
			String accountNo = accountService.createAccount(uppAccountDto);
			if (StringUtils.isNotBlank(accountNo)){
				result.setMessage("开户成功");
				return result;
			}else{
				result.setMessage("开户失败.");
				return result;
			}
		} catch (Exception e) {
			result.setMessage(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
			return result;
		}
	}
	
	/**
	 * 查询列表
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value = "/queryZJAccount.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<AccountDto> queryList(DynamicSqlParameter requestParam) {
		PaginationResult<AccountDto> resultData = null;
		try {
			resultData = accountService.queryZJAccount(requestParam);
		} catch (UPPException e) {
			resultData.setMessage("分页查询线下充值信息异常!");
			logger.error("分页查询线下充值信息异常!", e);
		}
		return resultData;
	}
	/**
	 * 查询总余额
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value = "/ZJAccountBalance.action", produces = "application/json")
	@ResponseBody
	public double zJAccountBalance(DynamicSqlParameter requestParam) {
		double balance = 0L;
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("accountType", PayDict.ACCOUNT_TYPE_CTFO);
			requestParam.setEqual(map);
			balance = accountService.getTotalCommonCountBalance(requestParam);
		} catch (UPPException e) {
			balance = -1l;
			logger.error("分页查询线下充值信息异常!", e);
		}
		return balance;
	}
	@RequestMapping(value = "/getDetailById.action", produces = "application/json")
	@ResponseBody
	public AccountDto getMerchantById(@RequestParam("id") String id) {
		AccountDto acc = null;
		try {
			acc = accountService.getAccountById(id);
		} catch (UPPException e) {
			logger.error("通过ID查询商户信息异常", e);
		}
		return acc;
	}
	/**
	 * 预警阀值设置
	 * @return
	 */
	@RequestMapping(value="/addAlarmAmount" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> addAlarmAmount(){
		PaginationResult<?> result = new PaginationResult<Object>();
		try {	
			result = paramService.addParam((UPParam)model);
		} catch (Exception e) {
			result.setMessage("添加参数设置异常!");
			logger.error("添加参数设置异常!",e);
		}		
		return result;	
	}
	/**
	 * 预警阀值设置
	 * @return
	 */
	@RequestMapping(value="/editAlarmAmount" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> editAlarmAmount(){
		PaginationResult<?> result = new PaginationResult<Object>();
		try {	
			result = paramService.updateParam((UPParam)model);
		} catch (Exception e) {
			result.setMessage("更新参数设置异常!");
			logger.error("更新参数设置异常!",e);
		}		
		return result;	
	}
	/***
	 * 查询预警阀值金额模型
	 * @return
	 */
	@RequestMapping(value="/queryAlarmAmount" ,produces = "application/json")
	@ResponseBody
	public UPParam  queryAlarmAmount(){
		UPParam param= new UPParam();
		try {
			DynamicSqlParameter requestParam=new DynamicSqlParameter();
			Map<String,String> equal=new HashMap<String,String>();
			equal.put("paramName", ((UPParam)model).getParamName());
			requestParam.setEqual(equal);
			List<UPParam> list = paramService.queryParam(requestParam);
			if(list!=null&&list.size()>0){
				param=list.get(0);
			}
		} catch (Exception e) {
			logger.error("添加参数设置异常!",e);
		}		
		return param;	
	}
	/**
	 * 获取平台商户编号
	 * @return
	 */
	@RequestMapping(value = "/getStoreCode.action", method = RequestMethod.POST)
	@ResponseBody
	public String getPlatStoreCode(){
		return DefaultConfig.getValue("ZJZF_PAYMENT_STORE_NO");
	}
	/**
	 * 通过条件导出EXCEL
	 * 
	 * @param requestParam
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/exportExcelAll.action", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcel(DynamicSqlParameter requestParam) {
		try {
			response.setDateHeader("Expires", 0);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String encodedfileName = new String("中交账户数据".getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");
			OutputStream out = response.getOutputStream();

			ExportExcel<ZJAccountExcel> exp = new ExportExcel<ZJAccountExcel>();
			List<ExpObj> expObjs = ExcelUtil.getExpObjs(ZJAccountExcel.class, 0, "EXP");
			// 定义的方法
			List<Method> methods = new ArrayList<Method>(expObjs.size());
			// 定义执行方法的对象
			List<Object> objs = new ArrayList<Object>(expObjs.size());
			List<ZJAccountExcel> result = new ArrayList<ZJAccountExcel>();
			result = accountService.exportZJExcel(requestParam);
			for (ZJAccountExcel excel : result) {
				// 账户状态
				if (StringUtils.isNotEmpty(excel.getAccountStatus())) {
					String status = excel.getAccountStatus();
					if (status.equals(AccountDict.ACCOUNT_STATUS_INIT)) {
						excel.setAccountStatus("未实名");
					} else if (status.equals(AccountDict.ACCOUNT_STATUS_NORMAL)) {
						excel.setAccountStatus("正常");
					} else if (status.equals(AccountDict.ACCOUNT_STATUS_REVOKE)) {
						excel.setAccountStatus("已锁定");
					} else if (status.equals(AccountDict.ACCOUNT_STATUS_LOCKED)) {
						excel.setAccountStatus("已注销");
					}
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
			logger.error("导出账户EXCEL异常",e);
		}
	}
}
