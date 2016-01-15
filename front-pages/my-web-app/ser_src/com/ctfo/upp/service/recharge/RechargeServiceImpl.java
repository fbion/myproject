package com.ctfo.upp.service.recharge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.ctfo.base.dao.beans.SimpleCode;
import com.ctfo.file.bean.AttachmentBean;
import com.ctfo.file.bean.ImageSizeBean;
import com.ctfo.file.boss.IFileService;
import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExampleExtended;
import com.ctfo.payment.dao.beans.RechargeApplyVoucher;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.excelbeans.OfflineReceiptExcel;
import com.ctfo.upp.excelbeans.RechargeExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.root.utils.TimeHandleUtil;
import com.ctfo.upp.service.AbstractService;
import com.ctfo.upp.service.TaskServiceImpl;
import com.ctfo.upp.service.recharge.bean.TradeApplyDto;
import com.ctfo.upp.util.CnUpperCaserUtils;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DefaultConfig;
import com.ctfo.upp.utils.SpringBUtils;
import com.sinoiov.upp.service.dto.ApplyDto;
import com.sinoiov.upp.service.dto.ApprovalDto;
import com.sinoiov.upp.service.dto.VoucherDto;


/***
* 类描述：线下流程管理接口
* @author：liuguozhong  
* @version 1.0
* @since JDK1.6
*/ 
@Service("rechargeService")
public class RechargeServiceImpl extends TaskServiceImpl<ApplyDto, RechargeExcel> implements RechargeService {

	private static Log logger = LogFactory.getLog(RechargeServiceImpl.class);
	// 获取soa 线下充值服务Manager
//	private IRechargeOfflineManager manager = null;

//	private IRechargeOfflineManager getManager() {
//		if (this.manager == null) {
//			manager = (IRechargeOfflineManager) ServiceFactory.getFactory().getService(IRechargeOfflineManager.class);
//		}
//		return this.manager;
//	}

	private static ImageSizeBean imageSizeBean = new ImageSizeBean();
	static {
		imageSizeBean.setBigWidth(400);
		imageSizeBean.setBigHeight(200);
		imageSizeBean.setMaxWater(true);
		imageSizeBean.setMinWidth(110);
		imageSizeBean.setMinHeight(55);
	}

	public void addApproval(ApprovalDto approvalDto) throws UPPException{
		try {
			logger.info("UPP_HTTP_INTERFACE_UPP_APPROVAL_RECHARGE_AUDIT_PARAM: " + JSONObject.fromObject(approvalDto).toString());
			String json = super.sendRequest(JSONObject.fromObject(approvalDto)
					.toString(), DefaultConfig
					.getValue("UPP_APPROVAL_RECHARGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_APPROVAL_RECHARGE_AUDIT: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				OfflineRechargeApply resultBean = (OfflineRechargeApply)JSONObject.toBean(dataObj, OfflineRechargeApply.class);
			} else {
				throw new UPPException("添加的充值申请接口异常");
			}
		} catch (Exception e) {
			logger.error("添加的充值申请接口异常", e);
			throw new UPPException("添加的充值申请接口异常");
		}
	}
	/**
	 * 添加线下充值申请
	 */
	@Override
	public PaginationResult<?> add(TradeApplyDto model,String loginUser) throws UPPException {
		model.setApprovalPersonName(loginUser);
		PaginationResult<Object> result = new PaginationResult<Object>();
		OfflineRechargeApply bean = null;
		try {
			
			// 1.存储线下充值信息
			ApplyDto applyDto = new ApplyDto();
			String[] ignoreProperties = { "applyId", "voucherDesc", 
										"voucherDesc", "voucherType",
										"tradeAmount", "applyTime", "amountArriveTime", "fundsConfirmTime"};
			BeanUtils.copyProperties(model, applyDto, ignoreProperties);
			//将金额元转成分
//			applyDto.setTradeAmount(AmountUtil.getAmount(model.getTradeAmount()) + "");
			applyDto.setTradeType(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_CZ);
			applyDto.setTradeAmount(model.getTradeAmount());
			applyDto.setApplyName(loginUser);
			applyDto.setApplyTime(model.getApplyTime() == null ? "" : String.valueOf(model.getApplyTime()));
			applyDto.setFundsConfirmTime(model.getFundsConfirmTime() == null 
											? "" : String.valueOf(model.getFundsConfirmTime()));
			applyDto.setAmountArriveTime(model.getAmountArriveTime() == null 
											? "" : String.valueOf(model.getAmountArriveTime()));
			
//			// 1.存储线下充值信息
//			OfflineRechargeApply offlineRechargeApply = new OfflineRechargeApply();
//			String[] ignoreProperties = { "applyId", "voucherDesc", "voucherDesc", "voucherType","tradeAmount"};
//			BeanUtils.copyProperties(model, offlineRechargeApply, ignoreProperties);
//			//将金额元转成分
//			offlineRechargeApply.setTradeAmount(AmountUtil.getAmount(model.getTradeAmount()));
//			offlineRechargeApply.setApplyName(loginUser);
			
			
//			bean = getManager().addRechargeApply(offlineRechargeApply);
			logger.info("UPP_HTTP_INTERFACE_UPP_ADD_RECHARGE_APPLY_PARAM: " + JSONObject.fromObject(applyDto).toString());
			String json = super.sendRequest(JSONObject.fromObject(applyDto)
					.toString(), DefaultConfig
					.getValue("UPP_ADD_RECHARGE_APPLY"));
			logger.info("UPP_HTTP_INTERFACE_UPP_ADD_RECHARGE_APPLY: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				bean = (OfflineRechargeApply)JSONObject.toBean(dataObj, OfflineRechargeApply.class);
			} else {
				logger.error("添加的充值申请接口异常", null);
				throw new UPPException("添加的充值申请接口异常");
			}
			
			// 2.存储凭证信息
			String voucherFileName=model.getVoucherFileName();//凭证图片
			if(!StringUtils.isEmpty(voucherFileName)){
				VoucherDto voucherDto = new VoucherDto();
				voucherDto.setApplyId(bean.getId());
				voucherDto.setVoucherFileName(model.getVoucherFileName());
				
				logger.info("UPP_HTTP_INTERFACE_UPP_SUBMIT_TRADE_VOUCHER_PARAM: " 
									+ JSONObject.fromObject(voucherDto).toString());
				
				String addVoucherJson = super.sendRequest(voucherDto, DefaultConfig
						.getValue("UPP_SUBMIT_TRADE_VOUCHER"));
				if (super.processReturnResult(addVoucherJson) == null) {
					logger.error("添加凭证信息异常", null);
					throw new UPPException("添加凭证信息异常");
				}
				logger.info("UPP_HTTP_INTERFACE_UPP_SUBMIT_TRADE_VOUCHER: " + addVoucherJson);
			}
			
//			// 2.存储凭证信息
//			String voucherFileName=model.getVoucherFileName();//凭证图片
//			if(!StringUtils.isEmpty(voucherFileName)){
//				RechargeApplyVoucher rechargeApplyVoucher = new RechargeApplyVoucher();
//				rechargeApplyVoucher.setApplyId(bean.getId());
//				rechargeApplyVoucher.setVoucherFileName(model.getVoucherFileName());
////				getManager().submitTradeVoucher(rechargeApplyVoucher);
//				logger.info("UPP_HTTP_INTERFACE_UPP_SUBMIT_TRADE_VOUCHER_PARAM: " 
//									+ JSONObject.fromObject(rechargeApplyVoucher).toString());
//				
//				String addVoucherJson = super.sendRequest(JSONObject.fromObject(rechargeApplyVoucher).toString(), DefaultConfig
//						.getValue("UPP_SUBMIT_TRADE_VOUCHER"));
//				logger.info("UPP_HTTP_INTERFACE_UPP_SUBMIT_TRADE_VOUCHER: " + addVoucherJson);
//			}

			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("添加的充值申请OfflineRechargeApply表异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("添加的充值申请OfflineRechargeApply表异常", e);
		}
		return result;

	}

	/***
	 * 上传凭证信息
	 */
	@Override
	public String uploadFile(AttachmentBean attachmentBean) throws UPPException {
		String imgUrl = "";
		try {
			IFileService fileService = (IFileService) SpringBUtils.getBean("fileService");
			
			imgUrl = fileService.addFile(attachmentBean, imageSizeBean);
		} catch (Exception e) {
			logger.error("上传凭证信息异常。");
			throw new UPPException("上传凭证信息异常", e);
		}
		return imgUrl;
	}
	/**
	 * 根据申请ID查询申请信息
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	@Override
	public OfflineRechargeApply getRechargeApplyById(String id) throws UPPException{
		try {
//			return getManager().getRechargeApplyById(id);
			Map<String, String> map = new HashMap<String, String>();
//			map.put("id", id);
			map.put("applyId", id);
			logger.info("UPP_HTTP_INTERFACE_UPP_GET_RECHARGE_APPLY_BY_ID_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_GET_RECHARGE_APPLY_BY_ID"));
			logger.info("UPP_HTTP_INTERFACE_UPP_GET_RECHARGE_APPLY_BY_ID: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				return (OfflineRechargeApply)JSONObject.toBean(dataObj, OfflineRechargeApply.class);
			}
			
		} catch (Exception e) {
			logger.error("根据申请ID查询申请信息异常。",e);
			throw new UPPException("根据申请ID查询申请信息异常", e);
		}
		return null;
	}
	/**
	 * 线下流程管理-充值-审批
	 * 1.修改申请表信息。
	 * 2.增加审批信息。
	 */
	@Override
	public void modifyRechargeApply(OfflineRechargeApply rechargeApply,OfflineRechargeApplyApproval rechargeApplyApproval) throws UPPException {
		try {
			
//			getManager().modifyOfflineApplyApprove(rechargeApply, rechargeApplyApproval);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("apply", rechargeApply);
			map.put("applyApproval", rechargeApplyApproval);
			
			super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_MODIFY_RECHARGE_APPLY"));
						
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;	
		} catch (Exception e) {
			logger.error("线下流程管理-充值-审批信息异常。");
			throw new UPPException("线下流程管理-充值-审批信息异常", e);
		}
	}

	@Override
	public TradeApplyDto queryById(String templateCode) throws UPPException {
		TradeApplyDto trade = new TradeApplyDto();
		try {
			OfflineRechargeApply offlineRechargeApply = new OfflineRechargeApply();
//			offlineRechargeApply = getManager().getRechargeApplyById(templateCode);
			Map<String, String> map = new HashMap<String, String>();
//			map.put("id", templateCode);
			map.put("applyId", templateCode);
			logger.info("UPP_HTTP_INTERFACE_UPP_GET_RECHARGE_APPLY_BY_ID_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_GET_RECHARGE_APPLY_BY_ID"));
			logger.info("UPP_HTTP_INTERFACE_UPP_GET_RECHARGE_APPLY_BY_ID: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				offlineRechargeApply = (OfflineRechargeApply)JSONObject.toBean(dataObj, OfflineRechargeApply.class);
			}
			
			String[] ignoreProperties = { "applyId", "voucherDesc", "voucherDesc", "voucherType","tradeAmount"};
			BeanUtils.copyProperties(offlineRechargeApply, trade, ignoreProperties);
			trade.setTradeAmount(AmountUtil.getAmount(offlineRechargeApply.getTradeAmount()));
		} catch (BeansException e) {
			logger.error("通过ID查询充值申请对象异常",e);
			throw new UPPException("通过ID查询充值申请对象异常",e);
		} catch (Exception e) {
			logger.error("通过ID查询充值申请对象异常",e);
		}
		
		return trade;
	}
	
	/**
	 * 查询凭证信息
	 */
	@Override
	public VoucherDto queryVoucherById(String appyId) throws UPPException{
		VoucherDto bean = new VoucherDto();
		try {
//			List<RechargeApplyVoucher> list=getManager().getRechargeApplyVoucherByApplyId(appyId);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("applyId", appyId);
			logger.info("UPP_HTTP_INTERFACE_UPP_GET_RECHARGE_APPLY_VOUCHER_BY_APPLY_ID_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_GET_RECHARGE_APPLY_VOUCHER_BY_APPLY_ID"));
			logger.info("UPP_HTTP_INTERFACE_UPP_GET_RECHARGE_APPLY_VOUCHER_BY_APPLY_ID: " + json);
//			JSONObject dataObj = super.processReturnResult(json);
//			List<RechargeApplyVoucher> list = null;
//			if (dataObj != null) {
//				list = (List<RechargeApplyVoucher>)JSONObject.toBean(dataObj, List.class);
//			}
			
			List<VoucherDto> list = null;
			JSONArray dataObj = super.getJSONArrayResult(json);
			if (dataObj != null) {
				list = (List<VoucherDto>)JSONArray.toCollection(dataObj, VoucherDto.class);
			}
						
			if(list!=null&&list.size()>0){
				bean = list.get(0);
			}
		} catch (BeansException e) {
			logger.error("根据申请ID查询凭证信息异常",e);
			throw new UPPException("根据申请ID查询凭证信息异常",e);
		} catch (Exception e) {
			logger.error("根据申请ID查询凭证信息异常",e);
		}
		return bean;
	}
	/**
	 * 根据申请ID，删除凭证信息
	 */
	public void removeVoucherById(String applyId) throws UPPException{
		try {
//			getManager().removeTradeVoucherByApply(applyId);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("applyId", applyId);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_REMOVE_TRADE_VOUCHER_BY_APPLY"));
						
		} catch (BeansException e) {
			logger.error("根据申请ID查询凭证信息异常",e);
			throw new UPPException("根据申请ID查询凭证信息异常",e);
		} catch (Exception e) {
			logger.error("根据申请ID查询凭证信息异常",e);
		}
	}
	@Override
	public PaginationResult<?> queryByPage(DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<ApplyDto> result = new PaginationResult<ApplyDto>();

		try {
			OfflineRechargeApplyExampleExtended example = new OfflineRechargeApplyExampleExtended();
			Converter.paramToExampleExtended(requestParam, example);
//			result = getManager().queryRechargeApplyByPage(example);
			
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_RECHARGE_APPLY_BY_PAGE_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(requestParam, DefaultConfig
																.getValue("UPP_QUERY_RECHARGE_APPLY_BY_PAGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_RECHARGE_APPLY_BY_PAGE: " + json);
			result = super.getPaginationResult(json, ApplyDto.class);
//			JSONObject dataObj = super.processReturnResult(json);
//			if (dataObj != null) {
//				result = (PaginationResult<OfflineRechargeApply>)JSONObject.toBean(dataObj, PaginationResult.class);
//			}
			
		} catch (Exception e) {
			logger.error("查询充值页面异常", e);
			throw new UPPException("查询充值页面异常");
		}
		return result;
	}

	/***
	 * 根据输入的金额小写数字转为中文大写金额字符串
	 * 
	 * @param str
	 * @return
	 * @throws UPPException
	 */
	@Override
	public String getNum2CNString(String str) throws UPPException {
		try {
			if (!StringUtils.isEmpty(str)) {
				str=CnUpperCaserUtils.getNum2CnString(str);
			}
		} catch (Exception e) {
			logger.error("数字金额转大写字符串异常", e);
			throw new UPPException("数字金额转大写字符串异常");
		}
		return str;
	}
	
	@Override
	public PaginationResult<TradeApplyDto> editStepNo(TradeApplyDto model, String user) throws UPPException {
		model.setApprovalPersonName(user);
		OfflineRechargeApply offlineRechargeApply = new OfflineRechargeApply();
		PaginationResult<TradeApplyDto> result = new PaginationResult<TradeApplyDto>();
		String[] ignoreProperties = { "applyId", "voucherDesc", "voucherDesc", "voucherType" ,"storeId","remitterId",
				"remitterName","remitterBankCode","remitterBankcardNo","insideAccountNo","applyTime",
				"tradeAmount","tradeType","fundsConfirmId","fundsConfirmTime","tradeExternalNo","orderId","payType","tradeAmountUpper","description",
				"payeeName","payeeNo"};
		BeanUtils.copyProperties(model, offlineRechargeApply, ignoreProperties);
		try {
//			getManager().modifyRechargeApply(offlineRechargeApply);
			super.sendRequest(JSONObject.fromObject(offlineRechargeApply)
					.toString(), DefaultConfig
					.getValue("UPP_MODIFY_OFFLINE_RECHARGE_APPLY"));
			
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("修改充值操作步骤号失败",e);
		}
		return result;
	}
	/***
	 * 更改线下充值申请信息
	 */
	@Override
	public PaginationResult<TradeApplyDto> update(TradeApplyDto model, String user) throws UPPException {
		model.setApprovalPersonName(user);
		PaginationResult<TradeApplyDto> result = new PaginationResult<TradeApplyDto>();
		try {
			// 1.存储线下充值信息
			OfflineRechargeApply offlineRechargeApply = new OfflineRechargeApply();
			String[] ignoreProperties = { "applyId", "voucherDesc", "voucherDesc", "voucherType","tradeAmount"};
			BeanUtils.copyProperties(model, offlineRechargeApply, ignoreProperties);
			//将金额元转成分
			offlineRechargeApply.setTradeAmount(AmountUtil.getAmount(model.getTradeAmount()));
//			getManager().modifyRechargeApply(offlineRechargeApply);
			super.sendRequest(JSONObject.fromObject(offlineRechargeApply)
					.toString(), DefaultConfig
					.getValue("UPP_MODIFY_OFFLINE_RECHARGE_APPLY"));
			
			// 2.更新存储凭证信息
			String voucherFileName=model.getVoucherFileName();//凭证图片
			if(!StringUtils.isEmpty(voucherFileName)){
				VoucherDto rechargeApplyVoucher= queryVoucherById(model.getId());
				if(rechargeApplyVoucher!=null){
					removeVoucherById(model.getId());
				}
				RechargeApplyVoucher bean=new RechargeApplyVoucher();
				bean.setApplyId(model.getId());
				bean.setVoucherFileName(model.getVoucherFileName());
				
//				getManager().submitTradeVoucher(bean);
				super.sendRequest(JSONObject.fromObject(bean)
						.toString(), DefaultConfig
						.getValue("UPP_SUBMIT_TRADE_VOUCHER"));
			}
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			logger.error("修改充值申请OfflineRechargeApply表异常。");
			result.setMessage(Converter.OP_FAILED);
			throw new UPPException("修改充值申请OfflineRechargeApply表异常", e);
		}
		return result;
	}

	@Override
	public String isVoucherUnique(DynamicSqlParameter requestParam) throws UPPException {
		try {
			
			VoucherDto dto = null;
			Map<String, String> equalMap =  requestParam.getEqual();
			if (equalMap != null) {
				String payChannel = equalMap.get("channel");
				dto = new VoucherDto();
				dto.setChannel(payChannel);
				dto.setSubChannel(equalMap.get("subChannel"));
				dto.setApplyId(equalMap.get("applyId"));
				if ("CHANNEL_01".equals(payChannel)) {
					dto.setChannel(equalMap.get("channel"));
					dto.setVoucherTradeNo(equalMap.get("voucherTradeNo"));
					dto.setVoucherTime(equalMap.get("pubVoucherTime"));
				} else if ("CHANNEL_02".equals(payChannel)) {
					dto.setMerchantCode(equalMap.get("merchantCode"));
					dto.setTerminalCode(equalMap.get("terminalCode"));
					dto.setVoucherCode(equalMap.get("voucherCode"));
					dto.setVoucherTime(equalMap.get("voucherTime"));
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dto.setVoucherTime(String.valueOf(sdf.parse(dto.getVoucherTime()).getTime()));
			}
			
			logger.info("UPP_HTTP_INTERFACE_IS_UNIQUE_VOUCHER_PARAM: " 
							+ JSONObject.fromObject(dto).toString());
			String json = super.sendRequest(JSONObject.fromObject(dto).toString(), DefaultConfig
					.getValue("UPP_IS_UNIQUE_VOUCHER"));
			logger.info("UPP_HTTP_INTERFACE_IS_UNIQUE_VOUCHER: " + json);
			return super.getReturnString(json, "data");
		} catch (Exception e) {
			logger.error("check voucher unique err", e);
			throw new UPPException("验证凭证唯一性异常");
		}
	}
	@Override
	public void addVoucher(VoucherDto voucherDto) throws UPPException {
		
		try {
			logger.info("UPP_HTTP_INTERFACE_UPP_SUBMIT_TRADE_VOUCHER_PARAM: " 
					+ JSONObject.fromObject(voucherDto).toString());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			voucherDto.setVoucherTime(String.valueOf(sdf.parse(voucherDto.getVoucherTime()).getTime()));
			
			String addVoucherJson = super.sendRequest(voucherDto, DefaultConfig.getValue("UPP_SUBMIT_TRADE_VOUCHER"));
			logger.info("UPP_HTTP_INTERFACE_UPP_SUBMIT_TRADE_VOUCHER: " + addVoucherJson);
			if (super.processReturnResult(addVoucherJson) == null) {
				logger.error("审批，添加凭证信息失败", null);
			}
			
		} catch (Exception e) {
			logger.error("审批，添加凭证信息失败", e);
			throw new UPPException("审批，添加凭证信息失败");
		}
	}
	@Override
	public void exportExcel(DynamicSqlParameter requestParam)
			throws UPPException {
		try{
			long aa = System.currentTimeMillis();
			
			String taskName = "线下充值EXCEL下载";
			String countUrl = DefaultConfig.getValue("UPP_OFFLINE_COUNT");
			String queryUrl = DefaultConfig.getValue("UPP_QUERY_RECHARGE_APPLY_BY_PAGE");
			super.runExportExcelTask(taskName, countUrl, queryUrl, requestParam, ApplyDto.class, RechargeExcel.class);
		
			logger.info("---->>>导出用时:"+(System.currentTimeMillis()-aa));
			
		}catch(Exception e){
			logger.error("导出线下小票充值异常："+e.getLocalizedMessage(),e);
		}
		
	}
	@Override
	public List<RechargeExcel> copyTask(List<?> list) throws Exception {
		List<RechargeExcel> result = new ArrayList<RechargeExcel>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				RechargeExcel excel = new RechargeExcel();
				ApplyDto dto = new ApplyDto();
				dto = (ApplyDto) list.get(i);
				
				String time = dto.getApplyTime();
				String applyTime = "";
				String channel = dto.getChannel();
				String subChannel = dto.getSubChannel();
				String amount = dto.getTradeAmount();
				if(StringUtils.isBlank(amount)){
					amount = "0.00";
				}else{
					amount = AmountUtil.getAmount(Long.parseLong(amount));
				}
				if(StringUtils.isNotBlank(subChannel)){
					if("CHANNEL_01".equals(channel)){
						subChannel = this.getCodeName("PUB_BANK", subChannel);
					}else if("CHANNEL_02".equals(channel)){
						subChannel = this.getCodeName("CHANNEL_02", subChannel);
					}
				}
				if(StringUtils.isNotBlank(time)){
					applyTime = TimeHandleUtil.longToDate(Long.parseLong(time),"yyyy-MM-dd HH:mm:ss");
				}
				
				excel.setApplyNo(StringUtils.isNotBlank(dto.getApplyNo())?dto.getApplyNo():"");
				excel.setOwnerUserNo(StringUtils.isNotBlank(dto.getOwnerUserNo())?dto.getOwnerUserNo():"");
				excel.setChannel(StringUtils.isNotBlank(channel)?this.getCodeName("VOUCHER_CHANNEL", channel):"");
				excel.setSubChannel(StringUtils.isNotBlank(subChannel)?subChannel:"");
				excel.setStoreId(StringUtils.isNotBlank(dto.getStoreId())?dto.getStoreId():"");
				excel.setRemitterBankcardNo(StringUtils.isNotBlank(dto.getRemitterBankcardNo())?dto.getRemitterBankcardNo():"");
				excel.setApplyTime(applyTime);
				excel.setTradeAmount(amount);
				excel.setPayType(StringUtils.isNotBlank(dto.getPayType())?this.getCodeName("OFFLINE_PAY_TYPE", dto.getPayType()):"");
				excel.setApplyStatus(StringUtils.isNotBlank(dto.getApplyStatus())?this.getCodeName("OFFLINE_APPLY_STATUS", dto.getApplyStatus()):"");
				excel.setApprovalPersonName(StringUtils.isNotBlank(dto.getApprovalPersonName())?dto.getApprovalPersonName():"");
				result.add(excel);
			}
		}
		return result;
	}


}
