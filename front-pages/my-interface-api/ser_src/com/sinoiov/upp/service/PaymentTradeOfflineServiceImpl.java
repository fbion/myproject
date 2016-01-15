package com.sinoiov.upp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.upp.business.payment.IPaymentTradeOfflineBusiness;
import com.sinoiov.upp.service.dto.ApplyDto;
import com.sinoiov.upp.service.dto.ApprovalDto;
import com.sinoiov.upp.service.dto.VoucherDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service
public class PaymentTradeOfflineServiceImpl extends AbstractService implements IPaymentTradeOfflineService {

	private IPaymentTradeOfflineBusiness paymentTradeOfflineBusiness;

	private PaymentTradeOfflineServiceImpl() {
		paymentTradeOfflineBusiness = (IPaymentTradeOfflineBusiness) ServiceFactory.getFactory().getService(
				IPaymentTradeOfflineBusiness.class);
		logger.info("---->>>:" + paymentTradeOfflineBusiness);
	}

	@Override
	public Map<String, Object> createApply(ApplyDto applyDto) throws UPPServiceException {
		try {
			
			applyDto = paymentTradeOfflineBusiness.createApply(applyDto);

			Map<String, Object> mpReturn = new HashMap<String, Object>();
			mpReturn.put("orderId", applyDto.getOrderId());
			mpReturn.put("applyStatus", applyDto.getApplyStatus());
			mpReturn.put("id", applyDto.getId());
			
			return mpReturn;
		} catch (Exception e) {
			logger.error("创建充值申请异常", e);
			throw new UPPServiceException("创建充值申请异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public ApplyDto createApply(ApplyDto applyDto, VoucherDto voucherDto) throws UPPServiceException {
		try {
			return paymentTradeOfflineBusiness.createApply(applyDto);
		} catch (UPPException e) {
			logger.error("创建线下申请异常", e);
			throw new UPPServiceException("创建线下申请异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public String modifyApply(ApplyDto applyDto) throws UPPServiceException {
		try {
			return paymentTradeOfflineBusiness.modifyApply(applyDto);
		} catch (UPPException e) {
			logger.error("修改线下申请异常", e);
			throw new UPPServiceException("修改线下申请异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public PaginationResult<ApplyDto> queryApplyByPage(DynamicSqlParameter parameter) throws UPPServiceException {
		try {			
			return paymentTradeOfflineBusiness.queryApplyByPage(parameter);	
		} catch (UPPException e) {
			logger.error("根据条件查询符合条件的分页查询异常", e);
			throw new UPPServiceException("根据条件查询符合条件的分页查询异常" + e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("根据条件查询符合条件的分页查询异常", e);
			throw new UPPServiceException("根据条件查询符合条件的分页查询异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public ApplyDto getApplyById(String applyUUID) throws UPPServiceException {
		try {
			return paymentTradeOfflineBusiness.getApplyById(applyUUID);
		} catch (UPPException e) {
			logger.error("根据ID获取单个线下申请表对象接口异常", e);
			throw new UPPServiceException("根据ID获取单个线下申请表对象接口异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public VoucherDto addVoucher(VoucherDto voucherDto) throws UPPServiceException {
		try {
			return paymentTradeOfflineBusiness.addVoucher(voucherDto);	
		} catch (UPPException e) {
			logger.error("提交申请凭证信息异常", e);
			throw new UPPServiceException("提交申请凭证信息异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public List<VoucherDto> getVoucherByApplyId(String applyId) throws UPPServiceException {
		try {
			return paymentTradeOfflineBusiness.getVoucherByApplyId(applyId);
		} catch (UPPException e) {
			logger.error("根据申请ID查询凭证信息异常", e);
			throw new UPPServiceException("根据申请ID查询凭证信息异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public String removeVoucherById(String voucherUUID) throws UPPServiceException {
		try {
			return paymentTradeOfflineBusiness.removeVoucherById(voucherUUID);
		} catch (UPPException e) {
			logger.error("删除交易凭证信息异常", e);
			throw new UPPServiceException("删除交易凭证信息异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public ApprovalDto addApproval(ApprovalDto rechargeApprovalDto) throws UPPServiceException {
		try {
			return paymentTradeOfflineBusiness.addApproval(rechargeApprovalDto);
		} catch (UPPException e) {
			logger.error("增加审批信息异常", e);
			throw new UPPServiceException("增加审批信息异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public String modifyApproval(ApprovalDto rechargeApprovalDto) throws UPPServiceException {
		try {
			 return paymentTradeOfflineBusiness.modifyApproval(rechargeApprovalDto);
		} catch (UPPException e) {
			logger.error("修改审批信息异常", e);
			throw new UPPServiceException("修改审批信息异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public List<ApprovalDto> queryApproval(DynamicSqlParameter parameter) throws UPPServiceException {
		try {
			 return paymentTradeOfflineBusiness.queryApproval(parameter);
		} catch (UPPException e) {
			logger.error("根据条件查询符合条件的全部信息异常", e);
			throw new UPPServiceException("根据条件查询符合条件的全部信息异常" + e.getLocalizedMessage());
		}
	}
	
	@Override
	public ApprovalDto getApprovalById(String approvalUUID) throws UPPServiceException {
		try {
			 return paymentTradeOfflineBusiness.getApprovalById(approvalUUID);
		} catch (UPPException e) {
			logger.error("根据ID获取单个线下审批表对象接口异常", e);
			throw new UPPServiceException("根据ID获取单个线下审批表对象接口异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public boolean isVoucherUnique(VoucherDto dto) throws UPPServiceException {
		try {
			 return paymentTradeOfflineBusiness.isVoucherUnique(dto);
		} catch (UPPException e) {
			logger.error("根据ID获取单个线下审批表对象接口异常", e);
			throw new UPPServiceException("根据ID获取单个线下审批表对象接口异常" + e.getLocalizedMessage());
		}
	}

	@Override
	public int countApply(DynamicSqlParameter parameter)
			throws UPPServiceException {
		try {
			return paymentTradeOfflineBusiness.countApply(parameter);
		} catch (UPPException e) {
			logger.error("统计线下流程申请数量异常", e);
			throw new UPPServiceException("统计线下流程申请数量异常" + e.getLocalizedMessage());
		}
	}

}
