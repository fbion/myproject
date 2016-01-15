package com.sinoiov.upp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.pay.utils.UPPJsonUtil;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.service.IPaymentTradeOfflineService;
import com.sinoiov.upp.service.dto.ApplyDto;
import com.sinoiov.upp.service.dto.ApprovalDto;
import com.sinoiov.upp.service.dto.VoucherDto;

/**
 * 线下交易接口
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/paymentTradeApply")
public class PaymentTradeOffline extends BaseController {
	@Autowired
	private IPaymentTradeOfflineService paymentTradeOfflineService;

	/**
	 * 线下帐户充值申请
	 * <p>
	 * 描述：在收到用户线下转账方式（POS或线下汇款）将业务所需的金额已经转到中交银行帐号中，由业务人员或用户提交充值申请，上传作证，经审核成功后，
	 * 将从中交账户转到用户账户中申请的金额
	 */
	@RequestMapping(value = "/createApply", produces = "text/plain;charset=utf-8")
	public List<String> createApply(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());

		ApplyDto applyDto = new ApplyDto();
		applyDto = (ApplyDto) UPPJsonUtil.jsonToObject(paramJson, applyDto);

		Map<String, Object> mpReturn = paymentTradeOfflineService.createApply(applyDto);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, mpReturn);
	}

	/**
	 * 增加审批
	 */
	@RequestMapping(value = "/addApproval", produces = "text/plain;charset=UTF-8")
	public List<String> addApproval(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		ApprovalDto approvalDto = (ApprovalDto) JSONObject.toBean(jsonMap, ApprovalDto.class);

		paymentTradeOfflineService.addApproval(approvalDto);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, approvalDto);
	}

	/**
	 * 条件查询审批信息
	 */
	@RequestMapping(value = "/queryApproval", produces = "text/plain;charset=UTF-8")
	public List<String> queryApproval(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		List<ApprovalDto> lstReturn = paymentTradeOfflineService.queryApproval(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, lstReturn);
	}

	/**
	 * 根据ID查询线下申请
	 */
	@RequestMapping(value = "/getApprovalById", produces = "text/plain;charset=UTF-8")
	public List<String> getApprovalById(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String applyId = super.getStringFromJsonMap(jsonMap, "applyId");
		ApplyDto applyDto = paymentTradeOfflineService.getApplyById(applyId);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, applyDto);
	}

	/**
	 * 分页查询线下申请
	 */
	@RequestMapping(value = "/queryApplyByPage", produces = "text/plain;charset=UTF-8")
	public List<String> queryApplyByPage(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		PaginationResult<ApplyDto> prApplyDto = paymentTradeOfflineService.queryApplyByPage(parameter);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, prApplyDto);
	}

	/**
	 * 根据Id查询线下充值申请
	 */
	@RequestMapping(value = "/getApplyById", produces = "text/plain;charset=UTF-8")
	public List<String> getApplyById(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String applyId = super.getStringFromJsonMap(jsonMap, "applyId");
		ApplyDto applyDto = paymentTradeOfflineService.getApplyById(applyId);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, applyDto);
	}

	/**
	 * 添加审批凭证
	 */
	@RequestMapping(value = "/addVoucher", produces = "text/plain;charset=UTF-8")
	public List<String> addVoucher(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		VoucherDto voucherDto = (VoucherDto) JSONObject.toBean(jsonMap, VoucherDto.class);

		paymentTradeOfflineService.addVoucher(voucherDto);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, voucherDto);
	}

	/**
	 * 修改审批信息
	 */
	@RequestMapping(value = "/modifyApproval", produces = "text/plain;charset=UTF-8")
	public List<String> modifyApproval(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		ApprovalDto approvalDto = (ApprovalDto) JSONObject.toBean(jsonMap, ApprovalDto.class);

		String strReturn = paymentTradeOfflineService.modifyApproval(approvalDto);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", strReturn);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 根据申请ID查询凭证信息(用于webAPP查看凭证图片)
	 */
	@RequestMapping(value = "/getVoucherByApplyId", produces = "text/plain;charset=UTF-8")
	public List<String> getVoucherByApplyId(HttpServletResponse response, PayCommonRequestParams params)
			throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String applyId = super.getStringFromJsonMap(jsonMap, "applyId");
		List<VoucherDto> lstReturn = paymentTradeOfflineService.getVoucherByApplyId(applyId);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, lstReturn);
	}

	/**
	 * 验证凭证信息是否重复
	 * 
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/isVoucherUnique", produces = "text/plain;charset=UTF-8")
	public List<String> isVoucherUnique(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		VoucherDto voucherDto = (VoucherDto) JSONObject.toBean(jsonMap, VoucherDto.class);
		boolean result = paymentTradeOfflineService.isVoucherUnique(voucherDto);

		Map<String, String> resultMap = new HashMap<String, String>();
		if (result) {
			resultMap.put("data", "1");
		} else {
			resultMap.put("data", "-1");
		}

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 删除交易凭证信息
	 */
	@RequestMapping(value = "/removeVoucherById", produces = "text/plain;charset=UTF-8")
	public List<String> removeVoucherById(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String voucherId = super.getStringFromJsonMap(jsonMap, "voucherId");

		String strReturn = paymentTradeOfflineService.removeVoucherById(voucherId);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", strReturn);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}

	/**
	 * 修改线下申请
	 */
	@RequestMapping(value = "/modifyApply", produces = "text/plain;charset=UTF-8")
	public List<String> modifyApply(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		ApplyDto applyDto = (ApplyDto) JSONObject.toBean(jsonMap, ApplyDto.class);
		String strReturn = paymentTradeOfflineService.modifyApply(applyDto);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("data", strReturn);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
	}
	/**
	 * 查询统计线下流程数量
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/countApply", produces = "text/plain;charset=UTF-8")
	public List<String> countApply(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		DynamicSqlParameter parameter = UPPJsonUtil.getDynamicSqlParameter(paramJson);

		int count = paymentTradeOfflineService.countApply(parameter);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("count", String.valueOf(count));
		return super.commonControllerReturn(params.getMerchantcode(), paramJson, resultMap);
		
	
	}

}
