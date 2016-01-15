package com.ctfo.upp.service.payment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExampleExtended;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.service.ServiceImpl;
import com.ctfo.upp.service.recharge.bean.TradeApplyDto;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.ApplyDto;

/**
 * 线下流程管理-扣款 service实现层
 * 
 * @author lipeng01
 * 
 */
@Service("paymentService")
public class PaymentServiceImpl extends ServiceImpl implements PaymentService {

	private static Log logger = LogFactory.getLog(PaymentServiceImpl.class);
	private PaginationResult<OfflineRechargeApply> result = new PaginationResult<OfflineRechargeApply>();
//	private IRechargeOfflineManager manager = null;
//	private IRechargeOfflineManager getManager() {
//		if (this.manager == null) {
//			manager =  (IRechargeOfflineManager) ServiceFactory.getFactory().getService(IRechargeOfflineManager.class);
//		}
//		return this.manager;
//	}
	
	@Override
	public PaginationResult<OfflineRechargeApply> addPayment(TradeApplyDto model,String loginUser) throws UPPException {
		PaginationResult<OfflineRechargeApply> pgresult = new PaginationResult<OfflineRechargeApply>();
		try {
//			COMMENT ON 20150608
//			OfflineRechargeApply applyBean=new OfflineRechargeApply();
//			Long time = new Date().getTime();
//			applyBean.setDescription(model.getDescription());
//			applyBean.setStoreId(model.getStoreId());
//			applyBean.setTradeAmount(AmountUtil.getAmount(model.getTradeAmount()));
//			applyBean.setRemitterName(loginUser);
//			applyBean.setPayType(model.getPayType());
//			//申请时间为当前系统时间
//			applyBean.setApplyTime(time);
//			//资金到账时间
//			//applyBean.setAmountArriveTime(time);
//			//申请人
//			applyBean.setApplyName(loginUser);
//			applyBean.setApplyStatus(model.getApplyStatus());
//			applyBean.setStepNo(model.getStepNo());
//			applyBean.setTradeType(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_KK);
//			applyBean.setFundsConfirmId(loginUser);
//			applyBean.setApprovalPersonName(loginUser);
//			applyBean.setApprovalPersonName(loginUser);
			
		//	applyBean.setFundsConfirmTime(new Date().getTime());
			
			//ADD ON 20150608
			ApplyDto applyDto = new ApplyDto();
			Long time = new Date().getTime();
			applyDto.setDescription(model.getDescription());
			applyDto.setStoreId(model.getStoreId());
			applyDto.setTradeAmount(model.getTradeAmount());
	//		applyDto.setRemitterName(loginUser);
			applyDto.setPayType(model.getPayType());
			applyDto.setApplyTime(time.toString());
			applyDto.setAmountArriveTime(time.toString());
			applyDto.setApplyName(loginUser);
			applyDto.setApplyStatus(model.getApplyStatus());
			applyDto.setStepNo(model.getStepNo());
			applyDto.setTradeType(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_KK);
			applyDto.setFundsConfirmId(loginUser);
			applyDto.setApprovalPersonName(loginUser);
			applyDto.setApprovalPersonName(loginUser);
			applyDto.setInsideAccountNo(model.getStoreId());
//			OfflineRechargeApply result = getManager().addRechargeApply(applyBean);
			logger.info("UPP_HTTP_INTERFACE_UPP_ADD_RECHARGE_APPLY_CUT_PARAM: " + JSONObject.fromObject(applyDto).toString());
			String json = super.sendRequest(JSONObject.fromObject(applyDto)
					.toString(), DefaultConfig
					.getValue("UPP_ADD_RECHARGE_APPLY"));
			logger.info("UPP_HTTP_INTERFACE_UPP_ADD_RECHARGE_CUT_APPLY: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			OfflineRechargeApply result = null;
			if (dataObj != null) {
				result = (OfflineRechargeApply)JSONObject.toBean(dataObj, OfflineRechargeApply.class);
			}
			
			if(result!=null){
				pgresult.setMessage(Converter.OP_SUCCESS);
			}else{
				pgresult.setMessage(Converter.OP_FAILED);
			}
		} catch (Exception e) {
			pgresult.setMessage(Converter.OP_FAILED);
			logger.error("添加扣款信息异常",e);
			throw new UPPException("添加扣款信息异常",e);
		}
		return pgresult;
	}

	@Override
	public PaginationResult<OfflineRechargeApply> queryPayment(DynamicSqlParameter requestParam) throws UPPException {
		try {
			OfflineRechargeApplyExampleExtended off = new OfflineRechargeApplyExampleExtended();
//			Converter.paramToExampleExtended(requestParam, off);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_RECHARGE_APPLY_BY_PAGE_PARAM: " + JSONObject.fromObject(requestParam).toString());
//			result = getManager().queryRechargeApplyByPage(off);
			String json = super.sendRequest(requestParam, DefaultConfig.getValue("UPP_QUERY_RECHARGE_APPLY_BY_PAGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_RECHARGE_APPLY_BY_PAGE: " + json);
			result = super.getPaginationResult(json, OfflineRechargeApply.class);
//			JSONObject dataObj = super.processReturnResult(json);
//			if (dataObj != null) {
//				result = (PaginationResult<OfflineRechargeApply>)JSONObject.toBean(dataObj, PaginationResult.class);
//			}

		} catch (Exception e) {
			logger.error("分页查询扣款信息异常",e);
			throw new UPPException("分页查询扣款信息异常",e);
		}
		return result;
	}

	@Override
	public OfflineRechargeApply queryPaymentById(String id) throws UPPException {
		OfflineRechargeApply offline = new OfflineRechargeApply();
		try {
//			offline = getManager().getRechargeApplyById(id);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("applyId", id);
			logger.info("UPP_HTTP_INTERFACE_UPP_GET_RECHARGE_APPLY_BY_ID_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_GET_RECHARGE_APPLY_BY_ID"));
			logger.info("UPP_HTTP_INTERFACE_UPP_GET_RECHARGE_APPLY_BY_ID: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				offline = (OfflineRechargeApply)JSONObject.toBean(dataObj, OfflineRechargeApply.class);
			}
			
		} catch (Exception e) {
			logger.error("通过ID查询扣款对象异常",e);
			throw new UPPException("通过ID查询扣款对象异常",e);
		}
		return offline;
	}

	@Override
	public PaginationResult<OfflineRechargeApply> updateStepNp(OfflineRechargeApply model,String user) throws UPPException {
		try {
			model.setApprovalPersonName(user);
//			getManager().modifyRechargeApply(model);
			super.sendRequest(JSONObject.fromObject(model)
					.toString(), DefaultConfig
					.getValue("UPP_MODIFY_RECHARGE_APPLY"));

			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("修改操作步骤失败",e);
			throw new UPPException("修改操作步骤失败",e);
		}
		return result;
	}

	@Override
	public PaginationResult<OfflineRechargeApply> modifyPayment(TradeApplyDto model, String loginUser) throws UPPException {
		
		try {
			OfflineRechargeApply applyBean=new OfflineRechargeApply();
			applyBean.setDescription(model.getDescription());
			applyBean.setStoreId(model.getStoreId());
			applyBean.setTradeAmount(AmountUtil.getAmount(model.getTradeAmount()));
			applyBean.setRemitterName(model.getRemitterName());
			applyBean.setPayType(model.getPayType());
			applyBean.setApplyTime(model.getApplyTime());
			applyBean.setAmountArriveTime(model.getAmountArriveTime());
			applyBean.setApplyStatus(model.getApplyStatus());
			applyBean.setStepNo(model.getStepNo());
			applyBean.setTradeType(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_KK);
			applyBean.setFundsConfirmId(loginUser);
			applyBean.setApprovalPersonName(loginUser);
			applyBean.setApprovalPersonName(loginUser);
			applyBean.setFundsConfirmTime(new Date().getTime());
			applyBean.setId(model.getId());
//			getManager().modifyRechargeApply(applyBean);
			super.sendRequest(JSONObject.fromObject(applyBean)
					.toString(), DefaultConfig
					.getValue("UPP_MODIFY_RECHARGE_APPLY"));
			
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("修改扣款信息失败",e);
			throw new UPPException("修改扣款信息失败",e);
		}
		return result;
	}
}
