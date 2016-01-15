package com.ctfo.upp.service.approve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApprovalExampleExtended;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.ServiceImpl;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.ApprovalDto;
@Service("approveService")
public class ApproveServiceImpl extends ServiceImpl implements ApproveService{
	private static Log logger = LogFactory.getLog(ApproveServiceImpl.class);
	private PaginationResult<OfflineRechargeApplyApproval> result = new PaginationResult<OfflineRechargeApplyApproval>();
//	private IRechargeOfflineManager manager = null;
//	private IRechargeOfflineManager getManager() {
//		if (this.manager == null) {
//			manager =  (IRechargeOfflineManager) ServiceFactory.getFactory().getService(IRechargeOfflineManager.class);
//		}
//		return this.manager;
//	}
	@Override
	public PaginationResult<OfflineRechargeApplyApproval> addPayment(OfflineRechargeApplyApproval model, String loginUser) throws UPPException {
		try {
			model.setApprovalPersonName(loginUser);
			model.setOperTime(new Date().getTime());
//			getManager().approvalRecharge(model);
			logger.info("UPP_HTTP_INTERFACE_UPP_APPROVAL_RECHARGE_PARAM: " + JSONObject.fromObject(model).toString());
			String json = super.sendRequest(JSONObject.fromObject(model).toString(), DefaultConfig
					.getValue("UPP_APPROVAL_RECHARGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_APPROVAL_RECHARGE: " + json);
			
			result.setMessage(Converter.OP_SUCCESS);
		} catch (Exception e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("添加审批信息异常",e);
			throw new UPPException("添加审批信息异常",e);
		}
		return result;
	}
	@Override
	public List<OfflineRechargeApplyApproval> queryByRechargeApplyId(DynamicSqlParameter requestParam) throws UPPException {
		List<OfflineRechargeApplyApproval> list = new ArrayList<OfflineRechargeApplyApproval>();
		try {
			OfflineRechargeApplyApprovalExampleExtended off = new OfflineRechargeApplyApprovalExampleExtended();
			Converter.paramToExampleExtended(requestParam, off);
			
//			list = getManager().queryRechargeApproval(off);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_RECHARGE_APPROVAL_PARAM: " + JSONObject.fromObject(off).toString());
			String json = super.sendRequest(JSONObject.fromObject(off)
					.toString(), DefaultConfig
					.getValue("UPP_QUERY_RECHARGE_APPROVAL"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_RECHARGE_APPROVAL: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				list = (List<OfflineRechargeApplyApproval>)JSONObject.toBean(dataObj, List.class);
			}
			
		} catch (Exception e) {
			logger.error("通过条件查询审批表异常",e);
			throw new UPPException("通过条件查询审批表异常",e);
		}
		return list;
	}
	@Override
	public OfflineRechargeApplyApproval findById(String id) throws UPPException {
		OfflineRechargeApplyApproval offApproval = new OfflineRechargeApplyApproval();
		try {
//			offApproval = getManager().getRechargeApprovalById(id);
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_RECHARGE_APPROVAL_BY_ID_PARAM: " + map);
			String json = super.sendRequest(JSONObject.fromObject(map)
								.toString(), DefaultConfig
								.getValue("UPP_QUERY_RECHARGE_APPROVAL_BY_ID"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_RECHARGE_APPROVAL_BY_ID: " + json);
			JSONObject dataObj = super.processReturnResult(json);
			if (dataObj != null) {
				offApproval = (OfflineRechargeApplyApproval)JSONObject.toBean(dataObj, OfflineRechargeApplyApproval.class);
			}
						
		} catch (Exception e) {
			logger.error("通过ID查询审批详情失败",e);
			throw new UPPException("通过ID查询审批详情失败",e);
		}
		return offApproval;
	}
	@Override
	public List<ApprovalDto> findByRechargeApplyId(String id,String approvalUserId) throws UPPException {
		List<ApprovalDto> list = new ArrayList<ApprovalDto>();
		try {
			DynamicSqlParameter requestParam = new DynamicSqlParameter();
			Map<String,String> map = new HashMap<String,String>();
			map.put("rechargeApplyId", id);
			map.put("approvalUserId", approvalUserId);
			requestParam.setEqual(map);
			OfflineRechargeApplyApprovalExampleExtended off = new OfflineRechargeApplyApprovalExampleExtended();
			Converter.paramToExampleExtended(requestParam, off);
//			list = getManager().queryRechargeApproval(off);
			
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_OFFLINE_RECHARGE_APPROVAL_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(requestParam, DefaultConfig
								.getValue("UPP_QUERY_OFFLINE_RECHARGE_APPROVAL"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_OFFLINE_RECHARGE_APPROVAL: " + json);
			
//			JSONObject dataObj = super.processReturnResult(json);
//			if (dataObj != null) {
//				list = (List<OfflineRechargeApplyApproval>)JSONObject.toBean(dataObj, List.class);
//			}
			JSONArray dataObj = super.getJSONArrayResult(json);
			if (dataObj != null) {
				list = (List<ApprovalDto>)JSONArray.toCollection(dataObj, ApprovalDto.class);
			}
			
		} catch (Exception e) {
			logger.error("通过扣款ID查询审批失败",e);
			throw new UPPException("通过扣款ID查询审批失败",e);
		}
		return list;
	}
}
