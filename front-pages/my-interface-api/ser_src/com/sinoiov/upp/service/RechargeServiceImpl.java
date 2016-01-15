package com.sinoiov.upp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.PayOrderSubjectDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.soa.ServiceFactory;
import com.sinoiov.pay.utils.UPPConfigUtil;
import com.sinoiov.upp.business.account.IAccountBusiness;
import com.sinoiov.upp.business.payment.IPaymentTradeBusiness;
import com.sinoiov.upp.service.dto.AccountDto;
import com.sinoiov.upp.service.dto.OrderDto;
import com.sinoiov.upp.service.exception.UPPServiceException;

@Service("rechargeServiceImpl")
public class RechargeServiceImpl extends AbstractService implements IRechargeService {
	private IPaymentTradeBusiness paymentTradeBusiness;

	private IAccountBusiness accountBusiness;

	private static String zjzc_accountNo = null;// 中交支出账户
	private static String zjzc_userId = null;// 中交支出用户Id

	private RechargeServiceImpl() {
		paymentTradeBusiness = (IPaymentTradeBusiness) ServiceFactory.getFactory().getService(
				IPaymentTradeBusiness.class);
		accountBusiness = (IAccountBusiness) ServiceFactory.getFactory().getService(IAccountBusiness.class);

		try {
			zjzc_userId = UPPConfigUtil.getValue("ZJZF_PAYMENT_PAYOUT_ACCOUT_USERID");
			zjzc_accountNo = UPPConfigUtil.getUPPAccountNo(zjzc_userId);
		} catch (Exception e) {
			logger.error("从配置文件中读取中交支出账户信息报错!", e);
		}
	}

	private OrderDto transferAccounts(OrderDto orderDto) throws UPPServiceException {
		try {
			// 检查参数合法
			if(StringUtils.isBlank(orderDto.getOrderAmount())) throw new UPPServiceException(ReturnCodeDict.ORDER_SERVICE_PROVIDER_CODE_IS_NULL,"订单金额不能为空！");
			
			// 转换
			OrderInfo orderInfo = new OrderInfo();
			String[] ignoreProperties = { "orderAmount", "createTime", "payConfirmDate", "identityType"};
			BeanUtils.copyProperties(orderDto, orderInfo, ignoreProperties);
			orderInfo.setOrderAmount(AmountUtil.getAmount(orderDto.getOrderAmount()));
			if(StringUtils.isNotBlank(orderDto.getIdentityType()))
				orderInfo.setIdentityType(Integer.parseInt(orderDto.getIdentityType().trim()));
			
			orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_PAY);
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_TRANSFER_ACCOUNT);// 订单类型
			if (StringUtils.isBlank(orderInfo.getOrderSubject()))
				orderInfo.setOrderSubject(PayDict.ORDER_BUSINESS_SUBJECT_ACCOUNT_PAYOUT);

			orderInfo = paymentTradeBusiness.transferAccounts(orderInfo);		
			orderDto.setId(orderInfo.getId());
			orderDto.setOrderStatus(orderInfo.getOrderStatus());

			return orderDto;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("转账接口发布服务异常", e);
			throw new UPPServiceException("转账接口发布服务异常");
		}
	}

	@Override
	public OrderDto rechargeToUserAccount(OrderDto orderDto) throws UPPServiceException {
		try {
			orderDto.setAccountNo(zjzc_accountNo);
			return this.transferAccounts(orderDto);
		} catch (Exception e) {
			logger.error("转账接口发布服务异常", e);
			throw new UPPServiceException("转账接口发布服务异常");
		}
	}

	@Override
	public List<Map<String, Object>> batchRechargeToUserAccount(List<OrderDto> orderDtos) throws UPPServiceException {
		try {
			List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
			OrderInfo orderInfo = null;
			String[] ignoreProperties = {"orderAmount","payConfirmDate","createTime"};
			for (OrderDto orderDto : orderDtos) {
				orderInfo = new OrderInfo();

				BeanUtils.copyProperties(orderDto, orderInfo,ignoreProperties);
				if(StringUtils.isBlank(orderInfo.getAccountNo()))
					orderInfo.setAccountNo(orderDto.getCollectMoneyAccountNo());
				orderInfo.setOrderAmount(AmountUtil.getAmount(orderDto.getOrderAmount()));
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_TRANSFER_ACCOUNT);
				orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_PAY);
				orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_BATCH_RECHARGE);

				orderInfos.add(orderInfo);
			}

			List<OrderInfo> orderList = paymentTradeBusiness.batchAccountRecharge(orderInfos);

			List<Map<String, Object>> lstMap = new ArrayList<Map<String, Object>>();
			Map<String, Object> tempMap = null;
			for (OrderInfo info : orderList) {
				tempMap = new HashMap<String, Object>();
				tempMap.put("workOrderNo", info.getWorkOrderNo());
				tempMap.put("orderNo", info.getOrderNo());
				lstMap.add(tempMap);
			}

			return lstMap;
		} catch (Exception e) {
			logger.error("批量转账service异常:" + e.getLocalizedMessage(), e);
			throw new UPPServiceException(e.getLocalizedMessage());
		}
	}

	@Override
	public Map<String, Object> recharge(OrderDto orderDto) throws UPPServiceException {
		try {
			// 检查参数合法
			// TODO
			// String check = this.checkOrderDtoProperties(orderDto);

			// if(!super.CHECK_TRUE.equals(check)) return check;
			// if (!super.CHECK_TRUE.equals(check))
			// throw new UPPServiceException(check);

			// 转换
			OrderInfo orderInfo = new OrderInfo();

			// TODO
			// this.copyProperties(orderDto, orderInfo);

			String payChannel = orderDto.getPayChannel();// 支付渠道
			if (payChannel.equals(PayDict.CHANNEL_FASTPAY)) {
				orderInfo.setAccountNo(zjzc_accountNo);// 中交支出账户
				orderInfo.setUserId(zjzc_userId);// 中交支出用户Id
				orderInfo.setCollectMoneyAccountNo(orderDto.getAccountNo());
				orderInfo.setCollectMoneyUserId(orderDto.getUserId());
				orderInfo.setIdentityId(orderDto.getIdentityId());
				orderInfo.setOrderType(PayDict.ORDER_SUBJECT_MOBILE_FASTPAY_PAYOUT);
				orderInfo.setIdentityType(6);
				orderInfo.setClentType(orderDto.getClentType());

				orderInfo.setClentId("2");
				Long amount = AmountUtil.getAmount(orderDto.getOrderAmount());
				orderInfo.setOrderAmount(amount);
				orderInfo.setPayChannel(orderDto.getPayChannel());
				orderInfo.setProductCatalog(orderDto.getProductCatalog());
				orderInfo.setProductName(orderDto.getProductName());
				orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_FAST_PAY);
				orderInfo.setStoreCode(orderDto.getStoreCode());
				orderInfo.setUserId(orderDto.getUserId());
				orderInfo.setUserUa("test.jsp");
				orderInfo.setWorkOrderNo(orderDto.getWorkOrderNo());
				String userIp = orderDto.getUserIp();
				if (StringUtils.isBlank(userIp) || userIp.length() < 5 || userIp.substring(0, 4).indexOf("127.") > -1
						|| userIp.substring(0, 4).indexOf("0.") > -1) {
					userIp = "58.83.203.207";
				}
				orderInfo.setUserIp(userIp);
				orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_YEE_FAST_PAY);
				orderInfo.setCallbackUrl(StringUtils.isBlank(orderDto.getCallbackUrl()) ? "-1" : orderDto
						.getCallbackUrl());

				if ("MOBILE".equals(orderDto.getClentType())) {
					orderInfo.setFcallbackUrl(UPPConfigUtil.getValue("UPP_MOBILE_CALLBACK"));
					orderInfo.setOrderSubject(PayDict.ORDER_BUSINESS_SUBJECT_MB_FAST_RECHARGE);
				} else {
					orderInfo.setFcallbackUrl(orderDto.getFcallbackUrl());
					orderInfo.setOrderSubject(PayDict.ORDER_BUSINESS_SUBJECT_FAST_RECHARGE);
				}
			}

			if (StringUtils.isNotBlank(orderInfo.getAccountNo())) {
				AccountDto payAcc = accountBusiness.getAccountByAccountNo(orderInfo.getAccountNo());
				if (!PayDict.ACCOUNT_STATUS_Y.equals(payAcc.getAccountStatus()))
					throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_ERROR, "账户被锁定");
			}
			if (StringUtils.isNotBlank(orderInfo.getCollectMoneyAccountNo())) {
				AccountDto incAcc = accountBusiness.getAccountByAccountNo(orderInfo.getCollectMoneyAccountNo());
				if (!PayDict.ACCOUNT_STATUS_Y.equals(incAcc.getAccountStatus()))
					throw new UPPException(ReturnCodeDict.ACCOUNT_STATUS_ERROR, "用户账户被锁定");
			}

			if (payChannel.equals(PayDict.CHANNEL_FASTPAY)) {
				// 快捷充值

				paymentTradeBusiness.fastPayRecharge(orderInfo);
			} else if (payChannel.equals(PayDict.CHANNEL_NET)) {
				// 网银充值
				paymentTradeBusiness.netRecharge(orderInfo);
			}

			Map<String, Object> mpReturn = new HashMap<String, Object>();
			// TODO 找之前的代码确定需要返回什么属性
			mpReturn.put("", "");

			return mpReturn;
		} catch (UPPException ue) {
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPServiceException(ue.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("充值接口发布服务异常", e);
			throw new UPPServiceException("充值接口发布服务异常");
		}
	}

}
