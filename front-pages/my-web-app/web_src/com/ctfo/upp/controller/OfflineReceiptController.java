package com.ctfo.upp.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ctfo.file.bean.AttachmentBean;
import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.approve.ApproveService;
import com.ctfo.upp.service.receiptRecharge.ReceiptRechargeService;
import com.ctfo.upp.service.recharge.RechargeService;
import com.ctfo.upp.service.recharge.bean.TradeApplyDto;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.ApplyDto;
import com.sinoiov.upp.service.dto.ApprovalDto;
import com.sinoiov.upp.service.dto.VoucherDto;

/***
 * 类描述：线下流程管理-小票充值Controller
 * 
 * @author：liuguozhong
 * @date：2014年12月3日下午5:29:45
 * @version 1.0
 * @since JDK1.6
 */
@Component
@Controller
@Scope("prototype")
@RequestMapping("/receipt")
public class OfflineReceiptController extends BaseController {

	private static Log logger = LogFactory.getLog(OfflineReceiptController.class);

	@Resource(name = "receiptRechargeService", description = "service充值接口")
	private ReceiptRechargeService receiptRechargeService;
	
	private static final String RECHARGE_SUCC_SHORT_MSG_CODE = "tyzfpt1006";
	
	private PaginationResult<?> result = new PaginationResult<Object>();

	public OfflineReceiptController() {
		model = new TradeApplyDto();
	}

	/***
	 * 主页面
	 * 
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value = "/queryList.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryList(DynamicSqlParameter requestParam) {
		try {
			result = receiptRechargeService.queryByPage(requestParam);
		} catch (UPPException e) {
			result.setMessage("分页查询线下充值信息异常!");
			logger.error("分页查询线下充值信息异常!", e);
		}
		return result;
	}
	//查找当前登录用户
	@RequestMapping(value = "/findUser.action", produces = "application/json")
	@ResponseBody
	public String findUser (){
		return index.getUserLogin();
	}
	/**
	 * 验证凭证信息是否唯一
	 * 
	 * "-1" 不存在
	 * "1" 存在
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value = "/isVoucherUnique.action", produces = "application/json")
	@ResponseBody
	public String isVoucherUnique(DynamicSqlParameter requestParam) {
		try {
			return receiptRechargeService.isVoucherUnique(requestParam);
		} catch (UPPException e) {
			logger.error("验证凭证信息是否唯一异常!", e);
		}
		return "";
	}

	@RequestMapping(value = "/upload.action", method = RequestMethod.POST)
	@ResponseBody
	public void upload(
			@RequestParam(required = false, value = "file") final MultipartFile file,HttpServletResponse response) {
		String flag = "0";
		File f = null;
		try {			
			logger.info("<<<<<文件名称:"+file.getOriginalFilename());		
			CommonsMultipartFile cf = (CommonsMultipartFile) file;
			f = new File(cf.getName());
			cf.transferTo(f);			
			//DiskFileItem fi = (DiskFileItem) cf.getFileItem();
			//f = fi.getStoreLocation();
			AttachmentBean bean = new AttachmentBean();
			bean.setFile(f);
			bean.setFileName(file.getOriginalFilename());
			String imgName = receiptRechargeService.uploadFile(bean);
			
			if(StringUtils.isBlank(imgName))
				throw new UPPException("保存图片到mongo失败");
			String imagePath = DefaultConfig.getValue("IMAGE_URL");
			if(StringUtils.isBlank(imagePath))
				throw new UPPException("获取图片配置路径失败");
			flag = imagePath + imgName;
			//flag = "<script>parent.window.uploadCallback('"+flag+"')</script>";
			this.sendAjaxInfo(flag, response);
		} catch (Exception e) {
			result.setMessage("线下小票充值上传汇款凭证异常!");
			logger.error("线下小票充值上传汇款凭证异常!", e);
			this.sendAjaxInfo("线下充值小票上传汇款凭证异常!", response);
		}finally{
			try{
				if(f!=null)f.delete();
			}catch(Exception e){
				logger.warn("删除临时文件失败！");
			}
		}
		
		
	}

	/**
	 * 线下小票充值申请-添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> add() {
		try {
			String loginUser = index.getUserLogin();
			result = receiptRechargeService.add((TradeApplyDto) model, loginUser);
		} catch (Exception e) {
			result.setMessage("线下充值申请添加异常!");
			logger.error("线下充值申请添加异常!", e);
		}
		return result;
	}

	/**
	 * 数字金额转大写字符串
	 * 
	 * @return
	 */
	@RequestMapping(value = "/convert.action", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getNum2CNString(DynamicSqlParameter requestParam) {
		String temp = "";
		try {
			temp = receiptRechargeService.getNum2CNString(requestParam.getEqual().get(
					"tradeAmount"));

		} catch (Exception e) {
			result.setMessage("线下充值申请添加异常!");
			logger.error("线下充值申请添加异常!", e);
		}
		return temp;
	}

	/**
	 * 分页查询线下充值信息
	 * 
	 * @param requestParam
	 * @return PaginationResult
	 */
	@RequestMapping(value = "/queryDetail.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryDetail(DynamicSqlParameter requestParam) {
		try {
			result = receiptRechargeService.queryByPage(requestParam);
		} catch (UPPException e) {
			result.setMessage("分页查询线下充值信息异常!");
			logger.error("分页查询线下充值信息异常!", e);
		}
		return result;
	}

	/**
	 * 通过ID查询
	 * 
	 * @param id
	 * @return TradeApplyDto
	 */
	@RequestMapping(value = "/queryById.action", produces = "application/json")
	@ResponseBody
	public TradeApplyDto queryById(@RequestParam String id) {
		TradeApplyDto trade = new TradeApplyDto();
		try {
			trade = receiptRechargeService.queryById(id);
		} catch (UPPException e) {
			logger.error("通过ID查找充值信息异常", e);
		}
		return trade;

	}

	/**
	 * 根据申请ID查询凭证信息
	 * 凭证图片路径
	 * @return
	 */
	@RequestMapping(value = "/queryVoucher.action", produces = "application/json")
	@ResponseBody
	public Object queryRechargeApplyVoucher(@RequestParam String applyId) {
		String voucherFileName = "";
		try {
			VoucherDto bean = receiptRechargeService
					.queryVoucherById(applyId);
			System.out.println("id:" + applyId + "\tinfo: " + JSONObject.fromObject(bean).toString());
			if (bean != null) {
//				voucherFileName = bean.getVoucherFileName();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (StringUtils.isNotBlank(bean.getVoucherTime())) {
					bean.setVoucherTime(sdf.format(new Date(Long.valueOf(bean.getVoucherTime()))));
				}
				System.out.println("---: " + JSONObject.fromObject(bean).toString());
				return bean;
			}
		} catch (UPPException e) {
			logger.error("根据申请ID查询凭证信息异常", e);
		}
//		return voucherFileName;
		return null;
	}
	
	/**
	 * 添加充值审批信息
	 * @return
	 */
	@RequestMapping(value = "/addApproval.action",produces = "application/json")
	@ResponseBody
	public PaginationResult<TradeApplyDto> addApproval(){
		//TODO  
		PaginationResult<TradeApplyDto> result = new PaginationResult<TradeApplyDto>();
		String loginUser = index.getUserLogin();//申请人
		if(StringUtils.isEmpty(loginUser)){
			this.redirectJsp("/pages/error");
		}
		try {
			//Step 1.更新申请表资金确认人和资金确认时间
			TradeApplyDto dto=(TradeApplyDto)model;
		
			OfflineRechargeApply bean = receiptRechargeService.getRechargeApplyById(dto.getId());
			
			ApprovalDto approvalDto = new ApprovalDto();
			approvalDto.setRechargeApplyId(bean.getId());
			
			ApplyDto applyDto = new ApplyDto();
//			applyDto.setId(bean.getId());
			applyDto.setApplyId(dto.getId());
			
			//线下流程管理-申请状态:4103-未通过;4100-已申请;4101-已审核;4102-已完成    
			//线下流程管理-处理步骤号:4302-财务审批;4304-出纳-到款确认;4303-审批完成;4301-业务审批
			Long tradeAmount = bean.getTradeAmount();
			//获得阈值
			String maxAmount = DefaultConfig.getValue("OFFLINE_CHECK_AMOUNT");
			//当是出纳时
			if("0".equals(dto.getIdentifier())){
				if("同意".equals(dto.getApprovalResult())){
						approvalDto.setApprovalResult("1");
						approvalDto.setStepNo("4302");
						approvalDto.setApplyStatus("4101");
					
				}else{
//					dto.setStepNo("4303");
//					dto.setApplyStatus("4103");
					approvalDto.setStepNo("4303");
					approvalDto.setApplyStatus("4103");
					approvalDto.setApprovalResult("-1");
				}
			}else{
				if("同意".equals(dto.getApprovalResult())){
//					dto.setStepNo("4303");
//					dto.setApplyStatus("4102");
					approvalDto.setStepNo("4303");
					approvalDto.setApplyStatus("4102");
					approvalDto.setApprovalResult("1");
				}else{
//					dto.setStepNo("4303");
//					dto.setApplyStatus("4103");
					approvalDto.setStepNo("4303");
					approvalDto.setApplyStatus("4104");
					approvalDto.setApprovalResult("-1");
				}
			}
			if("4303".equals(dto.getStepNo())){
//				bean.setAmountArriveTime(dto.getAmountArriveTime());
				applyDto.setAmountArriveTime(String.valueOf(dto.getAmountArriveTime()));
//				bean.setFundsConfirmTime(new Date().getTime());
				applyDto.setFundsConfirmTime(new Date().getTime()+"");
//				approvalDto.setApprovalResult("1");
			}
//			bean.setFundsConfirmId(loginUser);
			approvalDto.setApprovalUserId(dto.getApprovalUserId());
//			bean.setStepNo(dto.getStepNo());
//			bean.setApplyStatus(dto.getApplyStatus());
//			bean.setApprovalPersonName(loginUser);
			approvalDto.setApprovalPersonName(loginUser);
//			approvalDto.setApprovalSuggest("同意");
			approvalDto.setApprovalSuggest(dto.getApprovalSuggest());
			approvalDto.setOperTime(new Date().getTime());
			
			//添加凭证信息
			if ("0".equals(dto.getIdentifier())) {
				VoucherDto voucherDto = new VoucherDto();
				voucherDto.setApplyId(bean.getId());
				voucherDto.setChannel(dto.getChannel());
				voucherDto.setSubChannel(dto.getSubChannel());
				voucherDto.setMerchantCode(dto.getMerchantCode());
				voucherDto.setTerminalCode(dto.getTerminalCode());
				voucherDto.setVoucherCode(dto.getVoucherCode());
				voucherDto.setVoucherTime(dto.getVoucherTime());
				voucherDto.setVoucherTradeNo(dto.getVoucherTradeNo());
				this.receiptRechargeService.addVoucher(voucherDto);
			}
			
			this.receiptRechargeService.addApproval(approvalDto);
			result.setMessage("操作成功");
			

			
			//Step 2.增加审批处理意见
//			OfflineRechargeApplyApproval approvalBean=new OfflineRechargeApplyApproval();
//			approvalBean.setApprovalResult(dto.getApprovalResult());
//			approvalBean.setRechargeApplyId(dto.getId());
//			approvalBean.setApprovalSuggest(dto.getApprovalSuggest());
//			approvalBean.setApprovalUserId(dto.getApprovalUserId());
//			approvalBean.setApprovalPersonName(loginUser);
//			approvalBean.setOperTime(new Date().getTime());
//			receiptRechargeService.modifyRechargeApply(bean,approvalBean);
//			result.setMessage(Converter.OP_SUCCESS);
			
			//审核通过， 短信通知
//			if(dto.getStepNo()=="4303"){
//				notifyReceiveMoney(dto.getAmountArriveTime(), dto.getTradeAmount(), dto.getPayeeNo());
//			}
			
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			result.setMessage(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("审批失败", e);
			result.setMessage(Converter.OP_FAILED);
		}
		
		return result;
	}
	
//	private void notifyReceiveMoney(long amountArriveTime, String tradeAmount, String payeeNo) {
//		try {
//			List<String> contents = new ArrayList<String>();
//			
////			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
////			Date date = new Date(amountArriveTime);
////			contents.add(sdf.format(date)); // 到账时间
//			
//			contents.add(tradeAmount); //交易金额
//
//			DynamicSqlParameter dsp = new DynamicSqlParameter();
//			Map<String, String> equalMap = new HashMap<String, String>();
//			equalMap.put("insideAccountNo", payeeNo);
//			dsp.setEqual(equalMap);
//			PaginationResult<Account> pageAccount = this.accountService.queryAccountByPage(dsp);
//			if (pageAccount != null) {
//				Collection<Account> accounts = pageAccount.getData();
//				if (accounts != null && accounts.size() > 0) {
//					contents.add(((Account)accounts.toArray()[0]).getTotalBalance() + "");
//				}
//			}
//			
//			this.smsService.sendShortMessByAccountNo(payeeNo, RECHARGE_SUCC_SHORT_MSG_CODE, contents);
//		} catch (UPPException ue) {
//			logger.error(ue.getLocalizedMessage(), ue);
//		} catch(Exception e) {
//			logger.error("发送短信失败", e);
//		}
//	}
	
	/**
	 * 修改线下充值步骤号失败
	 * 
	 * @return PaginationResult<TradeApplyDto>
	 */
	@RequestMapping(value = "/editStepNo.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<TradeApplyDto> editStepNo() {
		String user = index.getUserLogin();
		PaginationResult<TradeApplyDto> result = new PaginationResult<TradeApplyDto>();
		try {
			result = receiptRechargeService.editStepNo((TradeApplyDto) model, user);
		} catch (UPPException e) {
			logger.error("修改线下充值步骤号失败", e);
		}
		return result;
	}

	/**
	 * 修改线下流程-充值申请
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modify.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<TradeApplyDto> modify() {
		String user = index.getUserLogin();
		PaginationResult<TradeApplyDto> result = new PaginationResult<TradeApplyDto>();
		try {
			result = receiptRechargeService.update((TradeApplyDto) model, user);
			result.setMessage(Converter.OP_SUCCESS);
		} catch (UPPException e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("修改离线申请异常", e);
		}
		return result;
	}
	/**
	 * 添加扣款审批信息
	 * @return
	 */
	@RequestMapping(value = "/addPaymentApproval.action",produces = "application/json")
	@ResponseBody
	public PaginationResult<TradeApplyDto> addPaymentApproval(){
		//TODO  
		PaginationResult<TradeApplyDto> result = new PaginationResult<TradeApplyDto>();
		String loginUser = index.getUserLogin();
		if(StringUtils.isEmpty(loginUser)){
			this.redirectJsp("/pages/error");
		}
		try {
			//Step 1.更新申请表资金确认人和资金确认时间
			TradeApplyDto dto=(TradeApplyDto)model;
		
//			OfflineRechargeApply bean=receiptRechargeService.getRechargeApplyById(dto.getId());
			
			ApprovalDto approvalDto = new ApprovalDto();
//			approvalDto.setRechargeApplyId(bean.getId());
			
			//线下流程管理-申请状态:4103-未通过;4100-已申请;4101-已审核;4102-已完成
			//线下流程管理-处理步骤号:4302-财务审批;4304-出纳-到款确认;4303-审批完成;4301-业务审批
//			Long tradeAmount = bean.getTradeAmount();
			//获得阈值
			String maxAmount = DefaultConfig.getValue("OFFLINE_CHECK_AMOUNT");
			//当是出纳时
			if("2".equals(dto.getIdentifier())){
				//线下流程管理-申请状态:4103-未通过;4100-已申请;4101-已审核;4102-已完成
				//线下流程管理-处理步骤号:4302-财务审批;4304-出纳-到款确认;4303-审批完成;4301-业务审批;4300-业务审批未通过
				if("同意".equals(dto.getApprovalResult())){
//					dto.setStepNo("4302");
//					dto.setApplyStatus("4101");
					approvalDto.setApprovalResult("1");
//					approvalDto.setStepNo("4302");
					approvalDto.setStepNo("4301"); // modify on 20150611
					approvalDto.setApplyStatus("4101");
				}else{
//					dto.setStepNo("4300");
//					dto.setApplyStatus("4103");
					approvalDto.setApprovalResult("-1");
//					approvalDto.setStepNo("4300");
//					approvalDto.setApplyStatus("4103");
					approvalDto.setStepNo("4303");
					approvalDto.setApplyStatus("4103");
				}
			}else{
				if ("同意".equals(dto.getApprovalResult())) {
//					dto.setStepNo("4303");
//					dto.setApplyStatus("4102");
					approvalDto.setApprovalResult("1");
//					approvalDto.setStepNo("4303");
					approvalDto.setStepNo("4302"); //modify on 20150611
					approvalDto.setApplyStatus("4102");
				} else {
//					dto.setStepNo("4303");
//					dto.setApplyStatus("4103");
					approvalDto.setApprovalResult("-1");
					approvalDto.setStepNo("4303");
					approvalDto.setApplyStatus("4103");
				}
			}
			approvalDto.setOperTime(new Date().getTime());
			approvalDto.setApprovalPersonName(loginUser);
			approvalDto.setApprovalSuggest(dto.getApprovalSuggest());
//			approvalDto.setApprovalUserId(loginUser);
			approvalDto.setApprovalUserId(dto.getApprovalUserId());
			approvalDto.setRechargeApplyId(dto.getId());
			this.receiptRechargeService.addApproval(approvalDto);
			result.setMessage(Converter.OP_SUCCESS);
//			logger.info("addApproval_req_param: " + dto);
//			if("4303".equals(dto.getStepNo())){
//				bean.setAmountArriveTime(dto.getAmountArriveTime());
//				bean.setFundsConfirmTime(new Date().getTime());
//			}
//			bean.setFundsConfirmId(loginUser);
//			bean.setStepNo(dto.getStepNo());
//			bean.setApplyStatus(dto.getApplyStatus());
//			bean.setApprovalPersonName(loginUser);
//			//Step 2.增加审批处理意见
//			OfflineRechargeApplyApproval approvalBean=new OfflineRechargeApplyApproval();
//			approvalBean.setApprovalResult(dto.getApprovalResult());
//			approvalBean.setRechargeApplyId(dto.getId());
//			approvalBean.setApprovalSuggest(dto.getApprovalSuggest());
//			approvalBean.setApprovalUserId(dto.getApprovalUserId());
//			approvalBean.setApprovalPersonName(loginUser);
//			approvalBean.setOperTime(new Date().getTime());
//			receiptRechargeService.modifyRechargeApply(bean,approvalBean);
//			result.setMessage(Converter.OP_SUCCESS);
			
			//审核通过， 短信通知
//			if(dto.getStepNo()=="4303"){
//				notifyReceiveMoney(dto.getAmountArriveTime(), dto.getTradeAmount(), dto.getPayeeNo());
//			}
			
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			result.setMessage(ue.getLocalizedMessage());
		}catch(Exception e){
			logger.error("审批失败", e);
			result.setMessage(Converter.OP_FAILED);
		}
		
		return result;
	}
	/**
	 * 通过KEY查找配置文件对应的value
	 * @return
	 */
	@RequestMapping(value = "/findProperties.action",produces = "application/json")
	@ResponseBody
	public String findProperties(@RequestParam String key){
		String value = DefaultConfig.getValue(key);
		return value;
	}

	@RequestMapping(value = "/exportExcelAll.action", method = RequestMethod.POST)
	@ResponseBody
	public void exportExcelAll(DynamicSqlParameter requestParam){
		try {
			//添加排序
			requestParam.setOrder("applyTime");
			requestParam.setSort("desc");
			receiptRechargeService.exportExcel(requestParam);
		} catch (Exception e) {
			logger.error("导出交易订单列表EXCEL异常",e);
		}
	}
	private void sendAjaxInfo(Object obj,HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(obj);
			out.flush();
		} catch (Exception e) {
			logger.error("发送"+obj.getClass().getName()+"ajax消息 Exception", e);
		}finally {
			logger.info("发送"+obj.getClass().getName()+"ajax消息-----end-----");
			try {
				response.getWriter().close();
			} catch (IOException e) {
				logger.info("发送"+obj.getClass().getName()+"ajax消息 PrintWriter IOException",e);
			}
		}
	}
}
