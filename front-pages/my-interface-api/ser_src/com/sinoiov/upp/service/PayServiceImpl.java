package com.sinoiov.upp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.AccountFrozenDetail;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.pay.utils.OrderInfoUtil;
import com.sinoiov.upp.business.account.IAccountBusiness;
import com.sinoiov.upp.business.payment.IPaymentTradeBusiness;
import com.sinoiov.upp.service.dto.OrderDto;
import com.sinoiov.upp.service.dto.PayDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service
public class PayServiceImpl extends AbstractService implements IPayService {

	@Autowired
	private ISmsCodeService smsCodeService;
	@Autowired
	private IOrderService orderService;

	private IPaymentTradeBusiness paymentTradeBusiness;

	private IAccountBusiness accountBusiness;
	
	private PayServiceImpl() {
		paymentTradeBusiness = (IPaymentTradeBusiness) ServiceFactory.getFactory().getService(
				IPaymentTradeBusiness.class);
		accountBusiness = (IAccountBusiness) ServiceFactory.getFactory().getService(IAccountBusiness.class);
		//orderService = (IOrderService) ServiceFactory.getFactory().getService(IOrderService.class);

	}

	/**
	 * 账户支付申请(需要和支付确认组合完成一项业务)
	 * 
	 * @param payDto
	 * @param mobile
	 * @param smsCode
	 * @param MD5
	 * @return
	 * @throws UPPServiceException
	 */
	private Map<String, Object> payApplyForAccount(PayDto payDto, String mobileNo, String smsCode, String MD5)
			throws UPPServiceException {
		try {
			// 验证短信验证码
			String mogoSmsCode = smsCodeService.getSmsCodeByMoblieNo(mobileNo);
			if (mogoSmsCode == null || !mogoSmsCode.equals(smsCode))
				throw new UPPServiceException(ReturnCodeDict.MOBILE_NO_SMSCODE, "短信验证码不正确");
			boolean is = accountBusiness.isPayPassword(payDto.getAccountNo(), MD5);
			if (!is)
				throw new UPPServiceException(ReturnCodeDict.OLD_MD5, "支付密码不正确");
			// 删除短信验证码
			smsCodeService.removeSmsCodeByMoblieNo(mobileNo);

			OrderInfo orderInfo = new OrderInfo();

			OrderInfoUtil.copyPayDtoToOrderInfo(payDto, orderInfo);

			orderInfo = paymentTradeBusiness.accountPay(orderInfo);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("orderNo", orderInfo.getOrderNo());
			map.put("result", "1");
			map.put("payConfirmDate", orderInfo.getPayConfirmDate());
			map.put("fcallbackUrl", orderInfo.getFcallbackUrl());// 跳业务系统前台地址

			return map;

		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (Exception e) {
			logger.error("账户支付异常", e);
			throw new UPPServiceException("账户支付异常");
		}
	}

	@Override
	public Map<String, Object> payApplyForCashierNet(PayDto payDto) throws UPPServiceException {
		try{			
			OrderInfo orderInfo = new OrderInfo();
			OrderInfoUtil.copyPayDtoToOrderInfo(payDto, orderInfo);	
			String url = paymentTradeBusiness.netPay(orderInfo);
			Map<String, Object> mpReturn = new HashMap<String, Object>();
			mpReturn.put("fcallbackUrl", url);	
			return mpReturn;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new UPPServiceException(StringUtils.isBlank(e.getErrorCode()) ? ReturnCodeDict.FAIL
					: e.getErrorCode(), e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("PC网银支付接口发生异常：" + e.getLocalizedMessage(), e);
			throw new UPPServiceException(ReturnCodeDict.FAIL, "PC网银支付接口发生异常：" + e.getLocalizedMessage(), e);
		}

	}

	@Override
	public Map<String, Object> payApplyForCashierFast(PayDto payDto) throws UPPServiceException {
		try{
			OrderInfo orderInfo = new OrderInfo();
			OrderInfoUtil.copyPayDtoToOrderInfo(payDto, orderInfo);
			String url = paymentTradeBusiness.fastPay(orderInfo);
			Map<String, Object> mpReturn = new HashMap<String, Object>();
			mpReturn.put("fcallbackUrl", url);
			return mpReturn;		
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new UPPServiceException(StringUtils.isBlank(e.getErrorCode()) ? ReturnCodeDict.FAIL
					: e.getErrorCode(), e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("PC快捷支付接口发生异常：" + e.getLocalizedMessage(), e);
			throw new UPPServiceException(ReturnCodeDict.FAIL, "PC快捷支付接口发生异常：" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Map<String, Object> payApplyForCashierAccount(PayDto payDto, String mobileNo, String smsCode, String MD5)
			throws UPPServiceException {
		try{
			return this.payApplyForAccount(payDto, mobileNo, smsCode, MD5);

		} catch (UPPServiceException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("PC账户支付接口发生异常：" + e.getLocalizedMessage(), e);
			throw new UPPServiceException(ReturnCodeDict.FAIL, "PC账户支付接口发生异常：" + e.getLocalizedMessage(), e);
		}

	}

	@Override
	public Map<String, Object> payApplyForMobileFast(PayDto payDto) throws UPPServiceException {
		try{
			OrderInfo orderInfo = new OrderInfo();
			OrderInfoUtil.copyPayDtoToOrderInfo(payDto, orderInfo);
			String url = paymentTradeBusiness.fastPay(orderInfo);

			Map<String, Object> mpReturn = new HashMap<String, Object>();
			mpReturn.put("fcallbackUrl", url);
			return mpReturn;

		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new UPPServiceException(StringUtils.isBlank(e.getErrorCode()) ? ReturnCodeDict.FAIL
					: e.getErrorCode(), e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("移动端快捷支付接口发生异常：" + e.getLocalizedMessage(), e);
			throw new UPPServiceException(ReturnCodeDict.FAIL, "移动端快捷支付接口发生异常：" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Map<String, Object> payApplyForMobileAccount(PayDto payDto, String mobileNo, String smsCode, String MD5) throws UPPServiceException {
		try{

			return this.payApplyForAccount(payDto, mobileNo, smsCode, MD5);	
		} catch (UPPServiceException e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;		
		} catch (Exception e) {
			logger.error("移动端账户支付接口发生异常：" + e.getLocalizedMessage(), e);
			throw new UPPServiceException(ReturnCodeDict.FAIL, "移动端账户支付接口发生异常：" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Map<String, Object> paySure(PayDto payDto) throws UPPServiceException {
		Map<String, Object> mpReturn = null;

		try {
			mpReturn = new HashMap<String, Object>();

			List<OrderDto> orderDtoList= orderService.queryPayOrderInfoByWorkOrderNo(payDto.getStoreCode(),payDto.getMerchantOrderNo());
			if (orderDtoList == null||orderDtoList.size()==0)
				throw new UPPServiceException(ReturnCodeDict.ORDER_N, "找不到对应的支付订单");
//			if (!orderDto.getWorkOrderNo().equals(payDto.getMerchantOrderNo()))
//				throw new UPPServiceException(ReturnCodeDict.ORDER_WORK_NO_N, "业务订单号不正确");

			AccountFrozenDetail accountFrozenDetail = paymentTradeBusiness.queryAccountFrozenDetailMess(
					payDto.getStoreCode(), payDto.getMerchantOrderNo());
			if (accountFrozenDetail == null) {
				throw new UPPServiceException(ReturnCodeDict.WORK_NO_FROZEN_DETAIL_UN_EXIST, "业务订单号未进行过冻结");
			}

			if (AmountUtil.getAmount(payDto.getMerchantOrderAmount()) > accountFrozenDetail.getFrozenAmount())
				throw new UPPServiceException(ReturnCodeDict.ACCOUNT_FROZEN_AMOUNT_INSUFFICIENT, "扣款确认订单金额不能大于冻结金额");

			OrderInfo orderInfo = new OrderInfo();
			OrderInfoUtil.copyPayDtoToOrderInfo(payDto, orderInfo);// 复制属性
			// 确认成功－－扣除对应的已冻结的金额
			if (PayDict.DEUCT_BUSINESS_STATUS_SUCCESS.equals(payDto.getResult())) {
				orderInfo.setCollectMoneyAccountNo(payDto.getAccountNo());
				orderInfo.setAccountNo(payDto.getAccountNo());
				orderInfo = paymentTradeBusiness.payConfirm(orderInfo);
			} else {
				// 确认失败－－解冻对应的账户冻结金额
				orderInfo.setAccountNo(orderDtoList.get(0).getAccountNo());
				orderInfo.setCollectMoneyAccountNo(orderDtoList.get(0).getAccountNo());
				orderInfo.setOrderAmount(AmountUtil.getAmount(payDto.getMerchantOrderAmount()));
				orderInfo.setWorkOrderNo(payDto.getMerchantOrderNo());
				orderInfo.setStoreCode(payDto.getStoreCode());
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_UNFREEZE_MONEY);
				orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_ZJXL_PAY);
				orderInfo = paymentTradeBusiness.payConfirmCancel(orderInfo);
			}
			// 返回数据
			mpReturn.put("tradeExternalNo", orderInfo.getTradeExternalNo());
			mpReturn.put("result", ReturnCodeDict.SUCCESS);
			mpReturn.put("payConfirmDate", orderInfo.getPayConfirmDate()==null?new Date().getTime()+"" :orderInfo.getPayConfirmDate());
		} catch (UPPServiceException use) {
			logger.error(use.getLocalizedMessage(), use);
			throw use;
		} catch (Exception e) {
			logger.error("扣款确认接口异常", e);
			throw new UPPServiceException("扣款确认接口异常", e);
		}

		return mpReturn;
	}

	@Override
	public Map<String, Object> payApplyAndSure(PayDto payDto) throws UPPServiceException {
		// TODO 未实现
		return null;
	}

}
